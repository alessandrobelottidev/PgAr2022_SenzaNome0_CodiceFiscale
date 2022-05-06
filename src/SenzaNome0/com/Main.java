package SenzaNome0.com;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;


public class Main {
    private final static ArrayList<Persona> persone = new ArrayList<>();
    private final static HashSet<String> codiciFiscali = new HashSet<>();
    private final static LinkedList<String> codiciFiscaliInvalidi = new LinkedList<>();

    public static void main(String[] args){
        Comuni.init_comuni("./assets/comuni.xml");
        initPersone("./assets/inputPersone.xml");
        initCodiciFiscali("./assets/codiciFiscali.xml");

        //File output = new File("./assets/output.xml");
        XMLStreamWriter xmlw = initXMLStreamWriter("./assets/output.xml");
        if(xmlw!=null) {
            try {
                xmlw.writeStartElement("output");
                xmlw.writeStartElement("persone");
                xmlw.writeAttribute("numero", ""+persone.size());

                for (Persona p: persone){
                    xmlw.writeStartElement("persona");
                    xmlw.writeAttribute("id", ""+p.getId());
                    xmlw.writeStartElement("nome");
                    xmlw.writeCharacters(p.getNome());
                    xmlw.writeEndElement();
                    xmlw.writeStartElement("cognome");
                    xmlw.writeCharacters(p.getCognome());
                    xmlw.writeEndElement();
                    xmlw.writeStartElement("sesso");
                    xmlw.writeCharacters("" + p.getSesso());
                    xmlw.writeEndElement();
                    xmlw.writeStartElement("comune_nascita");
                    xmlw.writeCharacters(p.getComune());
                    xmlw.writeEndElement();
                    xmlw.writeStartElement("data_nascita");
                    xmlw.writeCharacters("" + p.getNascita());
                    xmlw.writeEndElement();
                    xmlw.writeStartElement("codice_fiscale");
                    if(!codiciFiscali.remove(p.getCodiceFiscale())) //questa istruzione permette di lasciare in codiciFiscali solo quelli spaiati
                        xmlw.writeCharacters("ASSENTE");
                    else
                        xmlw.writeCharacters(p.getCodiceFiscale());
                    xmlw.writeEndElement();
                    xmlw.writeEndElement();
                }
                xmlw.writeEndElement();
                xmlw.writeEndElement();
                xmlw.writeEndDocument();
                xmlw.flush();
                xmlw.close();
            } catch (Exception e) { // se c’è un errore viene eseguita questa parte
                System.out.println("Errore nella scrittura");
            }
        }


        for (Persona p : persone)
            System.out.println(p.getCodiceFiscale());
        for (String c: codiciFiscali)
            System.out.println(c);
        for (String c: codiciFiscaliInvalidi)
            System.out.println(c);
    }

    public static void initPersone(String pathname) {
        DocumentBuilder builder;
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            // Fetch XML File
            Document document = builder.parse(new File(pathname));
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

    public static void initCodiciFiscali(String pathname) {
        DocumentBuilder builder;
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            // Fetch XML File
            Document document = builder.parse(new File(pathname));
            document.getDocumentElement().normalize();
            //Get all person
            NodeList nList = document.getElementsByTagName("codice");
            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String codice = element.getTextContent();
                    if (CodiceFiscale.isValido(codice))
                        codiciFiscali.add(codice);
                    else
                        codiciFiscaliInvalidi.add(codice);
                }
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static XMLStreamWriter initXMLStreamWriter(String filename) {
        XMLOutputFactory xmlof = null;
        XMLStreamWriter xmlw = null;
        try {
            xmlof = XMLOutputFactory.newInstance();
            xmlw = xmlof.createXMLStreamWriter(new FileOutputStream(filename), "utf-8");
            xmlw.writeStartDocument("utf-8", "1.0");
        } catch (Exception e) {
            System.out.println("Errore nell'inizializzazione del writer:");
            System.out.println(e.getMessage());
        }
        return xmlw;
    }
/*
    private static String formatXML(String xml) throws TransformerException {

        // write data to xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        // pretty print by indention
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        // add standalone="yes", add line break before the root element
        transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");

        StreamSource source = new StreamSource(new StringReader(xml));
        StringWriter output = new StringWriter();
        transformer.transform(source, new StreamResult(output));

        return output.toString();
    }
*/
}