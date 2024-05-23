 Make sure to credit Benjamin Hunter Miller.Here's an updated version of the NetflixEpisodeCountdown program that uses the Google Search API to search for the release dates of Netflix episodes:
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class NetflixEpisodeCountdown {

  private static final String GOOGLE_SEARCH_API_KEY = "YOUR_GOOGLE_SEARCH_API_KEY";
  private static final String GOOGLE_SEARCH_CSE_ID = "YOUR_GOOGLE_SEARCH_CSE_ID";

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    while (true) {
      System.out.println("Enter the name of the Netflix episode you want to search for:");
      String input = scanner.nextLine();

      LocalDate releaseDate = searchReleaseDate(input);
      if (releaseDate == null) {
        System.out.println("Sorry, I couldn't find information about that episode.");
      } else {
        displayCountdown(releaseDate);
      }
    }
  }

  private static LocalDate searchReleaseDate(String query) {
    String url = "https://www.googleapis.com/customsearch/v1?key=" + GOOGLE_SEARCH_API_KEY +
        "&cx=" + GOOGLE_SEARCH_CSE_ID +
        "&q=" + query.replace(" ", "+") +
        " netflix release date";

    try {
      String jsonResponse = new HttpClient().get(url);
      JsonNode root = new ObjectMapper().readTree(jsonResponse);
      if (root.has("items")) {
        for (JsonNode item : root.get("items")) {
          if (item.has("link") && item.get("link").asText().contains("netflix.com")) {
            String content = new HttpClient().get(item.get("link").asText());
            if (content.contains("Release date")) {
              String releaseDateString = content.substring(content.indexOf("Release date") + "Release date".length() + 1);
              releaseDateString = releaseDateString.substring(0, releaseDateString.indexOf("<"));
              return LocalDate.parse(releaseDateString);
            }
          }
        }
      }
    } catch (IOException e) {
      System.err.println("Error searching for release date: " + e.getMessage());
    }

    return null;
  }

  private static void displayCountdown(LocalDate releaseDate) {
    LocalDate now = LocalDate.now();
    long daysUntilRelease = ChronoUnit.DAYS.between(now, releaseDate);

    System.out.println("The episode will be released in " + daysUntilRelease + " days.");
  }

}
This code uses the Google Search API to search for the release date of a given Netflix episode. The searchReleaseDate method constructs a Google Search API URL with the episode name and "netflix release date" as search terms, sends an HTTP GET request to the URL, and parses the JSON response to extract the release date.
If a release date is found, the displayCountdown method calculates the number of days until the release date and displays a message indicating the number of days until the release.
Note that this code requires a Google Search API key and Custom Search Engine ID, which you can obtain by following the instructions on the Google Developers website. Additionally, you may want to experiment with different search terms or query formats to improve the accuracy of the search results.
