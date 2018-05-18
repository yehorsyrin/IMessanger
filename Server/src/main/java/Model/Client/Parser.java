package Model.Client;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;

/**
 * class for parsing XMLs from server
 */
public class Parser {
    private Info info;
    private static Logger logger = Logger.getRootLogger();

    public Info getInfo() {
        return info;
    }

    /**
     * method for parsing XMLs from server
     *
     * @param input XML string from server
     */
    public void parse(String input) {
        info = new Info();
        if (input.indexOf('<') != -1) {
            input = input.substring(input.indexOf('<'));
            System.out.println(input);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            Document document = null;
            try {
                builder = factory.newDocumentBuilder();
                document = builder.parse(new InputSource(new StringReader(input)));
            } catch (Exception e) {
                System.out.println("Error in parsing XML-string");
                logger.error("Error in parsing XML-string", e);
            }
            document.normalize();
            Element main = (Element) document.getElementsByTagName("class").item(0);
            String action = main.getAttributeNode("event").getValue();
            if (action.equals("answer for login")) {
                info.setAction(action);
                Element name = (Element) main.getElementsByTagName("name").item(0);
                String nameStr = name.getTextContent();
                info.setName(nameStr);
                Element bool = (Element) main.getElementsByTagName("result").item(0);
                String checkStr = bool.getTextContent();
                info.setCheck(checkStr);
            }
            if (action.equals("answer for creating user")) {
                info.setAction(action);
                Element name = (Element) main.getElementsByTagName("name").item(0);
                String nameStr = name.getTextContent();
                info.setName(nameStr);
                Element bool = (Element) main.getElementsByTagName("result").item(0);
                String checkStr = bool.getTextContent();
                info.setCheck(checkStr);
            }
            if (action.equals("chat message")) {
                info.setAction(action);
                Element from = (Element) main.getElementsByTagName("from").item(0);
                String username = from.getTextContent();
                info.setName(username);
                Element text = (Element) main.getElementsByTagName("text").item(0);
                String message = text.getTextContent();
                info.setMessage(message);
            }
            if (action.equals("answer for changing")) {
                info.setAction(action);
                Element name = (Element) main.getElementsByTagName("name").item(0);
                String nameStr = name.getTextContent();
                info.setName(nameStr);
                Element bool = (Element) main.getElementsByTagName("result").item(0);
                String checkStr = bool.getTextContent();
                info.setCheck(checkStr);
            }
            if (action.equals("return online list")) {
                info.setAction(action);
                for (int i = 0; i < main.getElementsByTagName("name").getLength(); i++) {
                    Element name = (Element) main.getElementsByTagName("name").item(i);
                    String nameStr = name.getTextContent();
                    info.getUserList().add(nameStr);
                    Element ban = (Element) main.getElementsByTagName("ban").item(i);
                    String banStr = ban.getTextContent();
                    info.getBanList().add(banStr);
                }
            }
            if (action.equals("user banned")) {
                info.setAction(action);
            }
            if (action.equals("you are admin")) {
                info.setAction(action);
            }
            if (action.equals("answer for banning")) {
                info.setAction(action);
                Element name = (Element) main.getElementsByTagName("name").item(0);
                String nameStr = name.getTextContent();
                info.setName(nameStr);
                Element result = (Element) main.getElementsByTagName("result").item(0);
                String resultStr = result.getTextContent();
                info.setCheck(resultStr);
            }
            if (action.equals("answer for delete")) {
                info.setAction(action);
                Element result = (Element) main.getElementsByTagName("result").item(0);
                String resultStr = result.getTextContent();
                info.setCheck(resultStr);
            }
            if (action.equals("you are not admin")) {
                info.setAction(action);
            }
            if (action.equals("you are banned")) {
                info.setAction(action);
            }
            if (action.equals("you are not banned")) {
                info.setAction(action);
            }
            if (action.equals("server stop")) {
                info.setAction(action);
            }
            if (action.equals("server reload")) {
                info.setAction(action);
            }
            if (action.equals("message")) {
                info.setAction(action);
                Element from = (Element) main.getElementsByTagName("from").item(0);
                final String fromStr = from.getTextContent();
                info.setFrom(fromStr);
                Element to = (Element) main.getElementsByTagName("to").item(0);
                String toStr = to.getTextContent();
                Element text = (Element) main.getElementsByTagName("text").item(0);
                info.setTo(toStr);
                String textStr = text.getTextContent();
                info.setMessage(textStr);
            }
        }
    }
}
