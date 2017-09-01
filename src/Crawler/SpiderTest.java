package Crawler;

public class SpiderTest {
    /**
     * This is a test client. It creates a spider and crawl the web.
     * @param args
     *  -not used
     */
    public static void main(String[] args) {
        Spider spider = new Spider();
        spider.search("http://www.amberengine.com", "product data");
    }
}
