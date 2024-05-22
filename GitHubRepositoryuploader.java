import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GitHubUploader {

    private static final String REPO_NAME = "my-repo";
    private static final String REPO_DESCRIPTION = "My new repository";
    private static final String GITHUB_TOKEN = "your-github-token";
    private static final String GITHUB_API_URL = "https://api.github.com";
    private static final HttpClient CLIENT = HttpClient.newHttpClient();

    public static void main(String[] args) throws IOException, InterruptedException {
        Path repoPath = Paths.get(REPO_NAME);
        if (!Files.exists(repoPath)) {
            System.err.println("Error: Repository path does not exist.");
            return;
        }

        // Create a new repository
        HttpRequest createRepoRequest = HttpRequest.newBuilder()
                .uri(URI.create(GITHUB_API_URL + "/user/repos"))
                .header("Authorization", "Token " + GITHUB_TOKEN)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{\"name\":\"" + REPO_NAME + "\",\"description\":\"" + REPO_DESCRIPTION + "\"}"))
                .build();
        HttpResponse<String> createRepoResponse = CLIENT.send(createRepoRequest, HttpResponse.BodyHandlers.ofString());
        String repoId = "";
        if (createRepoResponse.statusCode() == 201) {
            repoId = createRepoResponse.headers().firstValue("Location").get();
            repoId = repoId.substring(repoId.lastIndexOf("/") + 1);
            System.out.println("Created new repository: " + REPO_NAME);
        } else {
            System.err.println("Error: Failed to create new repository.");
            return;
        }

        // Upload the repository contents
        Path repoContentsPath = repoPath.resolve(".git");
        Files.walk(repoContentsPath)
                .filter(path -> !path.equals(repoContentsPath)) // Exclude the .git directory
                .forEach(path -> {
                    try {
                        String pathRelativeToRepo = repoPath.relativize(path).toString();
                        byte[] fileContent = Files.readAllBytes(path);

                        // Create a new blob
                        HttpRequest createBlobRequest = HttpRequest.newBuilder()
                                .uri(URI.create(GITHUB_API_URL + "/repos/" + GITHUB_USERNAME + "/" + repoId + "/contents/" + pathRelativeToRepo))
                                .header("Authorization", "Token " + GITHUB_TOKEN)
                                .header("Content-Type", "application/json")
                                .PUT(HttpRequest.BodyPublishers.ofString("{\"message\":\"Initial commit\",\"content\":\"" + Base64.getEncoder().encodeToString(fileContent) + "\"}"))
                                .build();
                        HttpResponse<String> createBlobResponse = CLIENT.send(createBlobRequest, HttpResponse.BodyHandlers.ofString());
                        if (createBlobResponse.statusCode() != 201) {
                            System.err.println("Error: Failed to create new blob.");
                        }

                        System.out.println("Uploaded file: " + pathRelativeToRepo);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                });

        System.out.println("Finished uploading repository: " + REPO_NAME);
    }
}
This code creates a new GitHub repository using the GitHub REST API, and then uploads the contents of a local directory to the repository. The code uses the Java HTTP client library to send HTTP requests and handles the responses.
Please note that you need to replace the GITHUB_TOKEN variable with your own GitHub personal access token. You can generate a new token from the GitHub settings page.
