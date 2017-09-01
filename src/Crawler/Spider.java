package Crawler;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Spider {

    private static final int MAX_PAGES_TO_SEARCH = 100;
    private Set<String> pageVisited = new HashSet<>();
    private List<String> pageToVisit = new LinkedList<>();

    /**
     * Main launching point for the Spider's functionality
     * It creates spider legs that make an HTTP request
     * and parse the response
     *
     * @param url
     *  -starting url
     *
     * @param searchWord
     *  -word to search
     */
    public void search(String url, String searchWord) {
        while(this.pageVisited.size() < MAX_PAGES_TO_SEARCH) {
            String currentUrl;
            SpiderLeg leg = new SpiderLeg();

            if (this.pageVisited.isEmpty()) {
                currentUrl = url;
                this.pageVisited.add(currentUrl);
            } else {
                currentUrl = nextUrl();
            }

            leg.crawl(currentUrl);
            boolean success = leg.searchForWord(searchWord);

            if(success) {
                System.out.println("**Success** Word " + searchWord + "found at " + currentUrl);
                break;
            } else {
                this.pageToVisit.addAll(leg.getLinks());
            }
        }
        System.out.println("**Done** Visited " + this.pageVisited.size() + "web page(s)");
    }

    /**
     * To determine the next URL to visit
     * if the URL is already visited, skip it
     * and not, get the URL and save it to pageVisited
     *
     * @return nextUrl
     */
    private String nextUrl() {
        String nextUrl;

        do {
            nextUrl = this.pageToVisit.remove(0);
        } while(this.pageVisited.contains(nextUrl));

        this.pageVisited.add(nextUrl);
        return nextUrl;
    }



}
