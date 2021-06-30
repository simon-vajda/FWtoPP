import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.w3c.dom.traversal.NodeIterator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

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
                    Element lines = verse.element("lines");
                    Iterator<Node> iterator = lines.nodeIterator();

                    while(iterator.hasNext()) {
                        Node n = iterator.next();

                        // NodeType=3 is text, and NodeType=1 is a line break <br/>
                        if(n.getNodeType() == 3)
                            writer.println(n.getText());
                    }

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
