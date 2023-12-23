package ucu.edu.ua;

public class Main {
    public static void main(String[] args) {
        Document doc = new SmartDocument("gs://oop-course-photo/123.png");

        Document timedDoc = new TimedDocument(doc);
        Document cache = new CachedDocument(timedDoc);
        System.out.println(cache.parse());
    }
}
