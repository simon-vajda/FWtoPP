import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Main {

    public static void main(String[] args) {
        try {
            File xmlFolder = new File("xml");
            SAXReader reader = new SAXReader();

            for (File f : xmlFolder.listFiles()) {
                Document document = reader.read(f);
                Element root = document.getRootElement();
                Element lyrics = root.element("lyrics");

                String title = root.element("properties").element("titles").elementText("title");
                String txtFileName = "txt/" + title.replaceAll("[^a-zA-Z0-9á-űÁ-Ű.() ]|[ ]{2,}", "") + ".txt";
                PrintWriter writer = new PrintWriter(txtFileName);
                writer.println("Title: " + title);
                writer.println();

                for (Element verse : lyrics.elements()) {
                    String lines = verse.element("lines").asXML();
                    lines = lines.substring(58, lines.length()-8).replaceAll("<br/>", "\n");
                    writer.println(lines);
                    writer.println();
                }

                System.out.println(title);
                writer.close();
            }
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
