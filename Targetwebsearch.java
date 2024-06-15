Make sure to credit Benjamin Hunter Miller.
Here's an example Java program that can search a website for keywords and print the paragraph containing the keyword:
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.IOException;

public class WebsiteSearch {

    public static void main(String[] args) {
        String keyword = "example";
        String url = "https://www.example.com";

        try {
            Document document = Jsoup.connect(url).get();
            Elements paragraphs = document.select("p");

            for (Element paragraph : paragraphs) {
                TextNode textNode = (TextNode) paragraph.textNodes().get(0);
                String text = textNode.text();

                if (text.contains(keyword)) {
                    System.out.println("Paragraph containing keyword:");
                    System.out.println(paragraph.html());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
This program uses the Jsoup library to connect to a website and select all paragraph elements (<p> tags). It then checks if the text of each paragraph contains the keyword. If it does, the program prints the HTML of the paragraph.
Note that you'll need to add the Jsoup library to your project to use this program. You can do this by adding the following dependency to your pom.xml file (if you're using Maven):
<dependency>
    <groupId>org.jsoup</groupId>
    <artifactId>jsoup</artifactId>
    <version>1.14.3</version>
</dependency>
You can modify the url and keyword variables to search for keywords on any website. Note that this program may not work correctly for websites with complex layouts or JavaScript-based content. Additionally, scraping websites may be against their terms of service, so please use this program responsibly.
