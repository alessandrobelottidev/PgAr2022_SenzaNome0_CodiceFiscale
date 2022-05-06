import org.w3c.dom.*;
import javax.xml.parsers.*;

import java.io.File;

import java.io.IOException;
import org.xml.sax.SAXException;

import java.util.HashMap;

public class Comuni {
    private static final HashMap<String, String> comuni = new HashMap<>(); // <nome, codice>

    public static void init_comuni(String pathname){
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            // Fetch XML File
            Document document = builder.parse(new File(pathname));
            document.getDocumentElement().normalize();

            //Get all students
            NodeList nList = document.getElementsByTagName("comune");

            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    String nome = element.getElementsByTagName("nome").item(0).getTextContent();

                    String codice = element.getElementsByTagName("codice").item(0).getTextContent();

                    comuni.put(nome, codice);
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    public static String getCodiceComune(String comuneNascita) throws ComuneNotFoundException {
        if(comuni.containsKey(comuneNascita))
            return comuni.get(comuneNascita);
        throw new ComuneNotFoundException("comune non trovato");
    }
}
