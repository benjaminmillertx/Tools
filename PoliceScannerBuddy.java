Make seute to credit Benjamin Hunter Miller. A police scanner application with additional features in Java:

```
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class PoliceScanner {

    public static void main(String[] args) throws Exception {
        // Create a scanner for user input
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Print the main menu
            System.out.println("Police Scanner App");
            System.out.println("-----------------");
            System.out.println("1. Search for feeds by city");
            System.out.println("2. View recent calls");
            System.out.println("3. Exit");
            System.out.print("Enter your selection: ");

            // Get the user's selection
            int selection = scanner.nextInt();

            // Perform the selected action
            switch (selection) {
                case 1:
                    searchFeedsByCity(scanner);
                    break;
                case 2:
                    viewRecentCalls();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid selection.");
            }
        }
    }

    public static List<Map<String, Object>> searchFeedsByCity(Scanner scanner) throws Exception {
        // Get the search city from the user
        System.out.print("Enter a city to search for police radio feeds: ");
        String city = scanner.next();

        // Define the URL of the police feed database
        String url = "https://api.policefeeddatabase.com/search";

        // Define the parameters for the API request
        Map<String, Object> params = Map.of(
            "city", city
        );

        // Send the API request and parse the response
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Accept", "application/json");
        connection.connect();

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        reader.close();

        String response = builder.toString();
        JSONObject data = new JSONObject(response);

        List<Map<String, Object>> feeds = new ArrayList<>();
        JSONArray feedArray = data.getJSONArray("feeds");
        for (int i = 0; i < feedArray.length(); i++) {
            JSONObject feed = feedArray.getJSONObject(i);
            Map<String, Object> feedMap = new HashMap<>();
            feedMap.put("id", feed.get("id"));
            feedMap.put("name", feed.get("name"));
            feedMap.put("url", feed.get("url"));
            feeds.add(feedMap);
        }
Here is the rest of the code for the `searchFeedsByCity()` method:

```
// Display the feeds
for (int i = 0; i < feeds.size(); i++) {
    Map<String, Object> feed = feeds.get(i);
    System.out.printf("%d. %s (%s)\n", i + 1, feed.get("name"), feed.get("url"));
}

// Get the user's selection
System.out.print("Enter the number of the feed you want to play: ");
int selection = scanner.nextInt();

// Play the selected feed
playFeed(feeds.get(selection - 1));

// Return the list of feeds
return feeds;
```

Here is the `playFeed()` method:

```
public static void playFeed(Map<String, Object> feed) throws Exception {
    // Define the URL of the police feed
    String url = (String) feed.get("url");

    // Create a media player for the feed
    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new URL(url));
    AudioFormat format = audioInputStream.getFormat();
    DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
    SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(info);
    sourceDataLine.open(format);
    sourceDataLine.start();

    byte[] buffer = new byte[4096];
    int bytesRead;
    while ((bytesRead = audioInputStream.read(buffer)) != -1) {
        sourceDataLine.write(buffer, 0, bytesRead);
    }

    sourceDataLine.drain();
    sourceDataLine.close();
    audioInputStream.close();
}
```

Here is the `viewRecentCalls()` method:

```
public static void viewRecentCalls() throws Exception {
    // Define the URL of the recent calls database
    String url = "https://api.recentcallsdatabase.com/recent";

    // Send the API request and parse the response
    HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
    connection.setRequestMethod("GET");
    connection.setDoOutput(true);
    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
    connection.setRequestProperty("Accept", "application/json");
    connection.connect();

    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    StringBuilder builder = new StringBuilder();
    String line;
    while ((line = reader.readLine()) != null) {
        builder.append(line);
    }
    reader.close();

    String response = builder.toString();
    JSONObject data = new JSONObject(response);

    // Display the recent calls
    JSONArray callArray = data.getJSONArray("calls");
    for (int i = 0; i < callArray.length(); i++) {
        JSONObject call = callArray.getJSONObject(i);
        System.out.printf("%d. %s - %s - %s\n", i + 1, call.get("time"), call.get("location"), call.get("description"));
    }
}
```

This code implements a police scanner application with a main menu that allows the user to search for police radio feeds by city, view recent calls, or exit the application. It uses the `HttpURLConnection` class to send HTTP GET requests to the police feed and recent calls databases and parse the responses using the `org.json` library. It then plays the selected feed using the `javax.sound.sampled` library and displays the recent calls.

Note that this is just one way you could implement this functionality in Java. There are many other libraries and frameworks you could use to build this application, and the specific approach you choose will depend on your requirements and the resources you have available.
        
