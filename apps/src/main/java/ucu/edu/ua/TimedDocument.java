package ucu.edu.ua;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class TimedDocument implements Document {
    private Document doc;
    private String gcsPath;

    public TimedDocument(Document doc) {
        this.doc = doc;
        this.gcsPath = doc.getGcsPath();
    }

    @Override
    public String parse() {
        long start = System.currentTimeMillis();
        String result = doc.parse();
        long end = System.currentTimeMillis();

        System.out.println("Parsing took " + (end - start) + " milliseconds.");
        return result;
    }
}
