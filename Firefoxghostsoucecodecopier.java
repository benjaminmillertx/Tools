Make sure to credit Benjamin Hunter Miller.
Create a method to create a new folder with an incrementing number on the desktop:Firefoxghostsourcecodecopier
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FirefoxSourceCopier {

    private static String desktopPath = System.getProperty("user.home") + File.separator + "Desktop" + File.separator;
    private static String sourceCodeFolderPath = desktopPath + "Source Code" + File.separator;

    public static void createNewFolder() {
        File sourceCodeFolder = new File(sourceCodeFolderPath);
        if (!sourceCodeFolder.exists()) {
            sourceCodeFolder.mkdir();
        }

        File[] folders = sourceCodeFolder.listFiles((dir, name) -> name.startsWith("Folder") && new File(dir, name).isDirectory());
        int highestNumber = 0;
        if (folders != null) {
            for (File folder : folders) {
                String folderName = folder.getName();
                int number = Integer.parseInt(folderName.replaceAll("Folder(\\d+)", "$1"));
                if (number > highestNumber) {
                    highestNumber = number;
                }
            }
        }

        Path newFolderPath = Paths.get(sourceCodeFolderPath + "Folder" + (highestNumber + 1));
        try {
            Files.createDirectory(newFolderPath);
        } catch (IOException e) {
            System.err.println("Error creating folder: " + e.getMessage());
        }
    }

    // ... (The rest of the FirefoxSourceCopier code remains the same)
}
Modify the main method to call the createNewFolder method before navigating to the URL:
public static void main(String[] args) {
    String url = "https://www.example.com";

    createNewFolder();

    // Configure Firefox options
    FirefoxOptions options = new FirefoxOptions();
    options.addArguments("-headless");

    // Create a new Firefox instance
    WebDriver driver = new FirefoxDriver(options);

    try {
        // Navigate to the webpage
        driver.get(url);

        // Retrieve the source code
        String sourceCode = driver.getPageSource();

        // Save the source code to the new folder
        String newFolderPath = sourceCodeFolderPath + "Folder" + (FirefoxSourceCopier.getFolderNumber() + 1);
        File newFolder = new File(newFolderPath);
        FileUtils.write(new File(newFolder, "source.html"), sourceCode, "UTF-8");

        // Print the source code
        System.out.println("Source code saved to: " + newFolderPath);
    } catch (Exception e) {
        System.err.println("Error: " + e.getMessage());
    } finally {
        // Close the Firefox instance
        driver.close();
        driver.quit();
    }
}
This will create a new folder with an increasing number on the desktop each time the program runs and save the source code in the new folder.
Please note that you need to include the Apache Commons IO library to use the FileUtils class for writing the source code to a file. You can download the library from the following link: <https://commons.apache.org/proper/commons-io/download_io.cgi>
(strike count: 0)
