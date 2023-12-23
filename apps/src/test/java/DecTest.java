import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import ucu.edu.ua.CachedDocument;
import ucu.edu.ua.TimedDocument;
import ucu.edu.ua.SmartDocument;
import ucu.edu.ua.Document;

public class DecTest {
    private String gcsPath = "gs://oop-course-photo/123.png";

    @Test
    public void testCachedDocument() {
        Document cachedDocument = new CachedDocument(new TimedDocument(new SmartDocument(gcsPath))); 
        String cachedContent = cachedDocument.parse();
        Assertions.assertNotNull(cachedContent);
    }

    @Test
    public void testSmartDocument() {
        Document smartDocument = new SmartDocument(gcsPath);
        String smartContent = smartDocument.parse();
        Assertions.assertNotNull(smartContent);
    }

    @Test
    public void testCombinedDecorators() {
        Document combinedDocument = new CachedDocument(new TimedDocument(new SmartDocument(gcsPath))); 
        String combinedContent = combinedDocument.parse();
        Assertions.assertNotNull(combinedContent);
    }
}
