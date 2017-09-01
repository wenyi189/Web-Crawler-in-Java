package Crawler;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.LinkedList;
import java.util.List;

public class SpiderLeg {

    private  List<String> links = new LinkedList<>();
    private Document htmlDocument;

    /**
     *
     * @param nextUrl
     */
    public void crawl(String url) {
        try {
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            Document htmlDocument = connection.get();
            this.htmmlDocument = htmlDocument;

            System.out.println("Received web page at " + url);

            Elements linksOnPage = htmlDocument.select("a[href]");
            System.out.println("Found (" + linksOnPage.size() + ") links");
        }
    }

    /**
     *
     * @param searchWord
     * @return
     */
    public boolean searchForWord(String searchWord) {

    }

    /**
     *
     * @return List of url on the page
     */
    public List<String> getLinks() {

    }
}
