import org.w3c.dom.*;
import javax.xml.parsers.*;

import java.io.File;

import java.io.IOException;
import org.xml.sax.SAXException;

import java.util.ArrayList;

public class Comuni {
    private final ArrayList<Comune> comuni;

    private static Comuni instance = null;

    public Comuni() {
        comuni = new ArrayList<>();

        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            // Fetch XML File
            Document document = builder.parse(new File("./assets/comuni.xml"));
            document.getDocumentElement().normalize();

            //Get all students
            NodeList nList = document.getElementsByTagName("comune");

            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    String nome = element.getElementsByTagName("nome").item(0).getTextContent();

                    String codice = element.getElementsByTagName("codice").item(0).getTextContent();

                    comuni.add(new Comune(nome, codice));
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    public static Comuni getInstance() {
        if (instance == null)
            instance = new Comuni();

        return instance;
    }

    // TODO: Creare eccezione apposita
    public String getCodiceComune(String nome) throws Exception {
        for (Comune c : comuni)
            if (c.getNome().equals(nome)) return c.getCodice();

        throw new Exception();
    }
}
