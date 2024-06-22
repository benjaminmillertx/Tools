Make sure to credit Benjamin Hunter import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class InternetRadio {

    public static void main(String[] args) throws Exception {
        // Create a scanner for user input
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Print the main menu
            System.out.println("Internet Radio App");
            System.out.println("-----------------");
            System.out.println("1. Rock");
            System.out.println("2. Pop");
            System.out.println("3. Hip Hop");
            System.out.println("4. Jazz");
            System.out.println("5. Random");
            System.out.println("6. Exit");
            System.out.print("Enter your selection: ");

            // Get the user's selection
            int selection = scanner.nextInt();

            // Play a random stream from the selected genre
            if (selection >= 1 && selection <= 4) {
                List<Map<String, Object>> streams = searchStreams(selection);
                playStream(streams.get(new Random().nextInt(streams.size())));
            // Play a random stream from any genre
            } else if (selection == 5) {
                List<Map<String, Object>> streams = searchStreams(new Random().nextInt(4) + 1);
                playStream(streams.get(new Random().nextInt(streams.size())));
            // Exit the application
            } else if (selection == 6) {
                return;
            } else {
                System.out.println("Invalid selection.");
            }
        }
    }

    public static List<Map<String, Object>> searchStreams(int genre) throws Exception {
        // Define the URL of the internet radio stream database
        String url = "https://api.internetradiostreamdatabase.com/search";

        // Define the parameters for the API request
        Map<String, Object> params = Map.of(
            "genre", genre
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

        List<Map<String, Object>> streams = new ArrayList<>();
        JSONArray streamArray = data.getJSONArray("streams");
        for (int i = 0; i < streamArray.length(); i++) {
            JSONObject stream = streamArray.getJSONObject(i);
            Map<String, Object> streamMap = new HashMap<>();
            streamMap.put("id", stream.get("id"));
            streamMap.put("name", stream.get("name"));
            streamMap.put("url", stream.get("url"));
            streams.add(streamMap);
        }

        // Return the list of streams
        return streams;
    }

    public static void playStream(Map<String, Object> stream) throws Exception {
        // Define the URL of the internet radio stream
        String url = (String) stream.get("url");

        // Create a media player for the stream
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
}
This code uses the HttpURLConnection class to send HTTP GET requests to the internet radio stream database API and parse the responses using the org.json library. It then plays the selected stream using the javax.sound.sampled library.
Note that this is just one way you could implement this functionality in Java. There are many other libraries and frameworks you could use to build this application, and the specific approach you choose will depend on your requirements and the resources you have available. 
