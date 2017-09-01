package Crawler;

import java.util.*;

public class Spider {

    private static final int MAX_PAGES_TO_SEARCH = 200;
    private Set<String> pageVisited = new HashSet<>();
    private List<String> pageToVisit = new LinkedList<>();
    private HashMap<String, Integer> wordResult = new HashMap<>();

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
        while(pageVisited.size() < MAX_PAGES_TO_SEARCH) {
            String currentUrl;

            if (pageVisited.isEmpty()) {
                currentUrl = url;
                pageVisited.add(currentUrl);
            } else {
                currentUrl = nextUrl();
                if (currentUrl == null) {
                    System.out.println("**Done** No more URL to work with");
                    break;
                }
            }

            SpiderLeg leg = new SpiderLeg(currentUrl);

            leg.crawl();
            boolean success = leg.searchForWord(searchWord, wordResult);

            if(success) {
                System.out.println("**Success** Word " + searchWord + "found at " + currentUrl);
                pageToVisit.addAll(leg.getLinks());
            }

            System.out.println();
        }
        System.out.println("**Done**");
        System.out.println("Visited " + pageVisited.size() + " web page(s)");
        System.out.println("Found keyword on " + wordResult.size() + " page(s)");
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
            if (pageToVisit.size() > 0) {
                nextUrl = pageToVisit.remove(0);
            } else {
                nextUrl = null;
            }
        } while(pageVisited.contains(nextUrl));

        pageVisited.add(nextUrl);
        return nextUrl;
    }



}
