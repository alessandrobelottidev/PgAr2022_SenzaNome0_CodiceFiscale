package SenzaNome0.com;

import SenzaNome0.com.Comuni;
import SenzaNome0.com.Persona;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Main {
    private final static ArrayList<Persona> persone = new ArrayList<>();

    public static void init_persone() {
        DocumentBuilder builder;
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            // Fetch XML File
            Document document = builder.parse(new File("./assets/inputPersone.xml"));
            document.getDocumentElement().normalize();

            //Get all person
            NodeList nList = document.getElementsByTagName("persona");

            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    int id = Integer.parseInt(element.getAttribute("id"));
                    String nome = element.getElementsByTagName("nome").item(0).getTextContent();
                    String cognome = element.getElementsByTagName("cognome").item(0).getTextContent();
                    Sesso sesso = (element.getElementsByTagName("sesso").item(0).getTextContent().equals("M")) ? Sesso.M : Sesso.F;
                    String comune = element.getElementsByTagName("comune_nascita").item(0).getTextContent();
                    LocalDate nascita = LocalDate.parse(element.getElementsByTagName("data_nascita").item(0).getTextContent());
                    persone.add(new Persona(id, nome, cognome, sesso, comune, nascita));
                }
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    public static void main(String[] args){
        Comuni.init_comuni("./assets/comuni.xml");
        init_persone();
        for (Persona p : persone)
            System.out.println(p.getCodiceFiscale());
    }
}