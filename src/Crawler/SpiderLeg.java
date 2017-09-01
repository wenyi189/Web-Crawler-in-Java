package Crawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpiderLeg {

    // pretend as Mozilla browser
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.52 Safari/536.5";
    private  List<String> links = new LinkedList<>();
    private Document htmlDocument;
    private String url;


    public SpiderLeg(String url) {
        this.url = url;
    }
    /**
     * This does all the work
     * It makes a HTTP request, checks the response, and collects all the links on the page
     *
     * @return boolean
     *  -successful crawl
     */
    public boolean crawl() {
        try {
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            htmlDocument = connection.get();
//            this.htmlDocument = htmlDocument;
            // If the connection responded a 200 code
            if (connection.response().statusCode() == 200) {
                System.out.println("**Visiting** Received web page at " + url);
            } else if (!connection.response().contentType().contains("text/html")) {
                System.out.println("**Failure** Retrived non HTML");
                return false;
            }

            System.out.println("Received web page at " + url);

            Elements linksOnPage = htmlDocument.select("a[href]");
            System.out.println("Found (" + linksOnPage.size() + ") links");

            for (Element link : linksOnPage) {
                links.add(link.absUrl("href"));
            }

            return true;
        } catch (IOException e){
            // Not successful
            System.out.println("Error in HTTP: " + e);
            return false;
        }
    }

    /**
     * Perform word searching on the body.
     * This method should only be called after a successful crawl.
     *
     * @param searchWord
     * @return boolean whether or not the word is found
     */
    public boolean searchForWord(String searchWord, HashMap<String, Integer> wordResult) {
        System.out.println("Searching for the word " + searchWord + "...");

        if (htmlDocument == null) {
            System.out.println("**Error** Call crawl() before performing analysis on the document");
            return false;
        }

        String bodyText = htmlDocument.body().text();

        if(bodyText.toLowerCase().contains(searchWord.toLowerCase())) {
            int counter = 0;

            final String REGEX = "\\b" + searchWord.toLowerCase() + "\\b";
            Pattern pattern = Pattern.compile(REGEX);
            Matcher matcher = pattern.matcher(bodyText);
            while (matcher.find()) {
                counter++;
            }

            wordResult.put(url, counter);
            return true;
        } else {
            return false;
        }

    }

    /**
     *
     * @return List of url on the page
     */
    public List<String> getLinks() {
        return links;
    }
}
