package Model.Server;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * class for parsing xml and creating more comfortable object to work with
 */
public class Parser {
private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getRootLogger();

/**
 * create object with information from xml
 * @param document xml to parse
 * @return obj with information
 */
public Obj parser(Document document) {
	Obj toReturn = new Obj();
	document.normalize();
	Element main = (Element) document.getElementsByTagName("class").item(0);
	String action = main.getAttributeNode("event").getValue();
	toReturn.setAction(action);
	if (action.startsWith("answer")) {
		Element res = (Element) document.getElementsByTagName("result").item(0);
		String result = res.getTextContent();
		toReturn.setResult(result);
	}
	if (action.equals("create user") || action.equals("login")) {
		Element name = (Element) main.getElementsByTagName("name").item(0);
		String username = name.getTextContent();
		Element password = (Element) main.getElementsByTagName("password").item(0);
		String userPassword = password.getTextContent();
		toReturn.setName(username);
		toReturn.setPassword(userPassword);
	}
	if (action.equals("message")) {
		Element from = (Element) main.getElementsByTagName("from").item(0);
		toReturn.setFrom(from.getTextContent());
		Element to = (Element) main.getElementsByTagName("to").item(0);
		String receiver = to.getTextContent();
		toReturn.setTo(receiver);
		Element text = (Element) main.getElementsByTagName("text").item(0);
		toReturn.setText(text.getTextContent());
	}
	if (action.equals("chat message")) {
		Element from = (Element) main.getElementsByTagName("from").item(0);
		toReturn.setFrom(from.getTextContent());
		Element text = (Element) main.getElementsByTagName("text").item(0);
		toReturn.setText(text.getTextContent());
	}
	if (action.equals("change name")) {
		Element name = (Element) main.getElementsByTagName("name").item(0);
		Element newName = (Element) main.getElementsByTagName("newname").item(0);
		toReturn.setName(name.getTextContent());
		toReturn.setNewName(newName.getTextContent());
	}
	if (action.equals("change password")) {
		Element oldPassword = (Element) main.getElementsByTagName("password").item(0);
		String password = oldPassword.getTextContent();
		Element newPassword = (Element) main.getElementsByTagName("newpassword").item(0);
		String npassword = newPassword.getTextContent();
		toReturn.setPassword(password);
		toReturn.setNewPassword(npassword);
	}
	if (action.equals("get online list")) {
		//
	}
	if (action.equals("ban")) {
		Element name = (Element) main.getElementsByTagName("name").item(0);
		toReturn.setName(name.getTextContent());
	}
	return toReturn;
}

/**
 * create document from obj
 * @param toCreate odj to get information from
 * @return created xml
 */
public Document create(Obj toCreate) {
	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	DocumentBuilder builder = null;
	try {
		builder = factory.newDocumentBuilder();
	} catch (ParserConfigurationException e) {
		logger.error("    problem with creating parser", e);
	}
	Document toReturn = builder.newDocument();
	String action = toCreate.getAction();
	Element main = toReturn.createElement("class");
	main.setAttribute("event", action);
	toReturn.appendChild(main);
	if (action.equals("login") || action.equals("create user")) {
		Element name = toReturn.createElement("name");
		name.setTextContent(toCreate.getName());
		main.appendChild(name);
		Element password = toReturn.createElement("password");
		password.setTextContent(toCreate.getPassword());
		main.appendChild(password);
	}
	if (action.equals("answer for creating user") || action.equals("answer for login") || action.equals("answer for changing") || action.equals("answer for banning")) {
		
		Element name = toReturn.createElement("name");
		name.setTextContent(toCreate.getName());
		main.appendChild(name);
		Element result = toReturn.createElement("result");
		result.setTextContent(toCreate.getResult());
		main.appendChild(result);
	}
	if (action.equals("message")) {
		Element from = toReturn.createElement("from");
		from.setTextContent(toCreate.getFrom());
		main.appendChild(from);
		Element to = toReturn.createElement("to");
		to.setTextContent(toCreate.getTo());
		main.appendChild(to);
		Element text = toReturn.createElement("text");
		text.setTextContent(toCreate.getText());
		main.appendChild(text);
	}
	if (action.equals("chat message")) {
		Element from = toReturn.createElement("from");
		from.setTextContent(toCreate.getFrom());
		main.appendChild(from);
		Element text = toReturn.createElement("text");
		text.setTextContent(toCreate.getText());
		main.appendChild(text);
	}
	if (action.equals("you are banned") || action.equals("user banned")) {
		Element name = toReturn.createElement("name");
		name.setTextContent(toCreate.getName());
		main.appendChild(name);
	}
	if (action.equals("you are not banned")) {
		Element name = toReturn.createElement("name");
		name.setTextContent(toCreate.getName());
		main.appendChild(name);
	}
	if (action.equals("return online list")) {
		for (int i = 0; i < toCreate.getUser().size(); i++) {
			String name = toCreate.getUser().get(i);
			String banR = toCreate.getBan().get(i);
			Element nameOfUser = toReturn.createElement("name");
			Element ban = toReturn.createElement("ban");
			nameOfUser.setTextContent(name);
			ban.setTextContent(banR);
			main.appendChild(nameOfUser);
			main.appendChild(ban);
		}
	}
	if (action.equals("you are admin") || action.equals("you are not admin")) {
		Element name = toReturn.createElement("name");
		name.setTextContent(toCreate.getName());
		main.appendChild(name);
	}
	return toReturn;
}

/**
 * create string from xml
 * @param doc xml to transform to string
 * @return created string
 */
public static String XmlToString(Document doc) {
	TransformerFactory tf = TransformerFactory.newInstance();
	Transformer transformer = null;
	try {
		transformer = tf.newTransformer();
	} catch (TransformerConfigurationException e) {
		logger.error("    problem with parsing XML", e);
	}
	transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	StringWriter writer = new StringWriter();
	try {
		transformer.transform(new DOMSource(doc), new StreamResult(writer));
	} catch (TransformerException e) {
		logger.error("    problem with parsing XML", e);
	}
	String output = writer.getBuffer().toString().replaceAll("\n|\r", "");
	return output;
}

/**
 * parse string to xml document
 * @param string string with information to xml
 * @return created xml document
 * @throws ParserConfigurationException
 * @throws IOException
 * @throws SAXException
 */
public static Document stringToXxl(String string) throws ParserConfigurationException, IOException, SAXException {
	DocumentBuilderFactory fctr = DocumentBuilderFactory.newInstance();
	DocumentBuilder bldr = fctr.newDocumentBuilder();
	InputSource insrc = new InputSource(new StringReader(string));
	return bldr.parse(insrc);
}
}