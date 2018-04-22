package Model;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * class that contain all users, write them to file and read file with users
 */
public class UserList {
private static Logger logger = Logger.getRootLogger();
private ArrayList < User > users = new ArrayList < > ();

/**
 * create new userList and read users from file
 */
public UserList() {
	readFile();
}

/**
 * rewrite a file with users to save changes
 */
public void writeFile() {
	File Users = new File("userList.xml");
	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	DocumentBuilder builder = null;
	try {
		builder = factory.newDocumentBuilder();
	} catch (ParserConfigurationException e) {
		logger.error("    problem with parsing XML user base", e);
	}
	Document list = builder.newDocument();
	Element main = list.createElement("users");
	list.appendChild(main);
	for (int i = 0; i < users.size(); i++) {
		Element user = list.createElement("user");
		main.appendChild(user);
		
		Element name = list.createElement("name");
		name.setTextContent(users.get(i).getName());
		user.appendChild(name);
		
		Element password = list.createElement("password");
		password.setTextContent(users.get(i).getPassword());
		user.appendChild(password);
		
		Element isAdmin = list.createElement("isAdmin");
		isAdmin.setTextContent(users.get(i).isAdmin());
		user.appendChild(isAdmin);
		
		Element isBanned = list.createElement("isBanned");
		isBanned.setTextContent(users.get(i).isBanned());
		user.appendChild(isBanned);
	}
	list.normalize();
	Transformer t = null;
	try {
		t = TransformerFactory.newInstance().newTransformer();
	} catch (TransformerConfigurationException e) {
		logger.error("    problem with parsing XML user base", e);
	}
	try {
		t.transform(new DOMSource(list), new StreamResult(new FileOutputStream(Users)));
	} catch (TransformerException | FileNotFoundException e) {
		logger.error("    problem with parsing XML user base", e);
	}
}

/**
 * read users from file
 */
public void readFile() {
	File file = new File("userList.xml");
	if (!file.exists()) {
		try {
			file.createNewFile();
		} catch (IOException e) {
			logger.error("    problem with creating userList", e);
		}
	} else if (file.length() != 0) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			logger.error("    problem with parsing XML user base", e);
		}
		Document doc = null;
		try {
			doc = db.parse(file);
		} catch (SAXException | IOException e) {
			logger.error("    problem with parsing XML user base", e);
		}
		doc.getDocumentElement().normalize();
		NodeList nodeList = doc.getElementsByTagName("user");
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			Element element = (Element) node;
			String name = element.getElementsByTagName("name").item(0).getTextContent();
			String password = element.getElementsByTagName("password").item(0).getTextContent();String isBanned = element.getElementsByTagName("isBanned").item(0).getTextContent();
			String isAdmin = element.getElementsByTagName("isAdmin").item(0).getTextContent();
			User user = new User(name, password);
			if (isBanned.equals("true")) user.setBan(true);
			if (isAdmin.equals("true")) user.setAdmin(true);
			addUser(user);
			
		}
	}
}

/**
 * search user by its name and return user
 * @param name name of user to find
 * @return user if exist or null if not
 */
public User getUserByName(String name) {
	for (User user: users) {
		if (user.getName().equals(name))
			return user;
	}
	return null;
}

/**
 * add user to userList and rewrite file to save changes
 * @param user is user to add
 */
public void addUser(User user) {
	users.add(user);
	writeFile();
}


}