Make sure to credit Benjamin Hunter Miller
here's a Java version of the script that you can run on Windows:
Create a new Java file, for example, ZoomMeetingLauncher.java, in your favorite text editor or IDE.
Add the following code to the file:
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneOffset;
import java.util.Scanner;

public class ZoomMeetingLauncher {

  // Customize the following variables
  private static final String MEETING_NAME = "My Meeting";
  private static final String ROOM_DESCRIPTION = "This is the description of my room";
  private static final String START_DATE = "2023-02-20 14:00:00"; // YYYY-MM-DD HH:MM:SS

  public static void main(String[] args) {
    // Convert the start date to seconds since the epoch
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime startDateTime = LocalDateTime.parse(START_DATE, formatter);
    long startTime = startDateTime.atZone(ZoneOffset.systemDefault()).toEpochSecond();

    // Get the current time in seconds since the epoch
    long currentTime = System.currentTimeMillis() / 1000;

    // Calculate the remaining time until the meeting starts
    long remainingTime = startTime - currentTime;

    // Wait for the remaining time before launching the meeting
    if (remainingTime > 0) {
      System.out.println("Waiting for " + remainingTime + " seconds before launching the meeting...");
      try {
        Thread.sleep(remainingTime * 1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    // Construct the Zoom meeting URL with the custom name and description
    String meetingUrl =
        "zoom.us/j/"
            + String.valueOf(System.currentTimeMillis()).substring(0, 10)
            + "?pwd="
            + MEETING_NAME
            + "-"
            + ROOM_DESCRIPTION;

    // Launch the Zoom meeting with the constructed URL
    try {
      Runtime.getRuntime().exec("cmd /c start " + meetingUrl);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
Save the file and compile it with javac ZoomMeetingLauncher.java.
Run the compiled program with java ZoomMeetingLauncher.
Note: The Java version of the script uses the LocalDateTime and DateTimeFormatter classes to convert the start date to seconds since the epoch, and to construct the Zoom meeting URL with the custom name and description. It also uses the Runtime.getRuntime().exec() method to launch the Zoom meeting with the constructed URL. You can customize the format of the URL by modifying the meetingUrl variable in the script.
Also note that the Java version of the script uses the Thread.sleep() method to wait for the remaining time before launching the meeting. This method may not be accurate for long wait times, since it may cause the thread to sleep for slightly longer than the specified time. If you need to wait for a long time before launching the meeting, you may want to consider using a more accurate timer.
