
import Model.Obj;
import Model.Parser;
import Model.User;
import Model.UserList;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Set;

public class AlternativeServerThread extends Thread{
private Socket socket;
private static UserList list;
private User user;
private static HashMap<User, Socket> userSocket;
private boolean logedIn = false;
private Parser parser = new Parser();
	public AlternativeServerThread(Socket socket, UserList list, HashMap<User,Socket> userSocket) {
	this.socket = socket;
	this.list=list;
	this.userSocket = userSocket;
	this.start();
	}
	
	public void run(){
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while (true){
				if(in.ready()){
					String get = in.readLine();
					System.out.println(get);
					process(stringToXxl(get));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	private void process(Document document){
	Obj parsed = parser.parser(document);
	Obj toCreate = new Obj();
		System.out.println(parsed.getAction());
	String action = parsed.getAction();
	if(action.equals("login")){
		if (list.getUserByName(parsed.getName())!=null) {
			logedIn = list.getUserByName(parsed.getName()).login(parsed.getPassword());
			if (logedIn) {
				user = list.getUserByName(parsed.getName());
				userSocket.put(user, socket);
				System.out.println(userSocket);
			}
			//logedIn = user.login(parsed.getPassword());
			toCreate.setAction("answer for login");
			toCreate.setName(list.getUserByName(parsed.getName()).getName());
			if (logedIn)
				toCreate.setResult("true");
			else toCreate.setResult("false");
			sendXML(parser.create(toCreate), socket);
			//notify all
			if(logedIn){
				if(user.isAdmin().equals("true")) admin(user.getName(),true);
				else admin(user.getName(),false);
			}
			updOnline();
		}
	}
	if(action.equals("create user")){
		toCreate.setAction("answer for creating user");
		toCreate.setName(parsed.getName());
		if(list.getUserByName(parsed.getName())!=null){
			toCreate.setResult("false");
		}else{
			toCreate.setResult("true");
			User user = new User(parsed.getName(),parsed.getPassword());
			list.addUser(user);
			list.writeFile();
		}
	sendXML(parser.create(toCreate),socket);
	updOnline();
	}if(action.equals("message")&&logedIn) {
			if (user.isBanned().equals("false")) {
				if(list.getUserByName(parsed.getTo()).isBanned().equals("false")) {
					toCreate.setAction("message");
					toCreate.setFrom(parsed.getFrom());
					toCreate.setTo(parsed.getTo());
					toCreate.setText(parsed.getText());
					sendXML(parser.create(toCreate),userSocket.get(list.getUserByName(parsed.getTo())));
					//send to
				}else{
					toCreate.setAction("user banned");
					toCreate.setName(parsed.getTo());
					sendXML(parser.create(toCreate),userSocket.get(user));
					//send from
				}
			} else {
				toCreate.setAction("you are banned");
				toCreate.setName(user.getName());
				sendXML(parser.create(toCreate),userSocket.get(user));
				//send from
			}
	}
	if(action.equals("chat message")&&logedIn){
		if (user.isBanned().equals("false")) {
			toCreate.setAction("chat message");
			toCreate.setFrom(parsed.getFrom());
			toCreate.setText(parsed.getText());
			Set<User> users = userSocket.keySet();
			for (User user:users) {
				if(user.isBanned().equals("false")) sendXML(parser.create(toCreate),userSocket.get(user));
			}
		}else {
			sendXML(parser.create(youAreBanned(user.getName())),userSocket.get(user));
		}
	}
	
	if(action.equals("change name")&&logedIn){
		if(user.isBanned().equals("false")){
			toCreate.setAction("answer for changing");
			if(list.getUserByName(parsed.getNewName())==null){
				toCreate.setResult("true");
				toCreate.setName(parsed.getNewName());
				user.setName(parsed.getNewName());
				list.writeFile();
			}else{
				toCreate.setName(user.getName());
				toCreate.setResult("false");
			}
			sendXML(parser.create(toCreate),userSocket.get(user));
			updOnline();
			//send to chat
		}else{
			sendXML(parser.create(youAreBanned(user.getName())),userSocket.get(user));
		}
	}
	if(action.equals("change password")&&logedIn){
		if(user.isBanned().equals("false")){
			toCreate.setAction("answer for changing");
			toCreate.setName(user.getName());
			toCreate.setResult("true");
			user.setPassword(parsed.getNewPassword());
			list.writeFile();
			sendXML(parser.create(toCreate),userSocket.get(user));
			//send
		}else{
			sendXML(parser.create(youAreBanned(user.getName())),userSocket.get(user));
			//check ban
		}
	}
	if(action.equals("get online list")&&logedIn) {
		if (user.isBanned().equals("false")) {
			toCreate.setAction("return online list");
			Set<User> set = userSocket.keySet();
			for (User user : set) {
				toCreate.add(user.getName(), user.isBanned());
			}
			sendXML(parser.create(toCreate), userSocket.get(user));
		} else {
			sendXML(parser.create(youAreBanned(user.getName())), userSocket.get(user));
		}
	}
	if(action.equals("ban")&&logedIn&&user.isAdmin().equals("true")){
			System.out.println("here");
			toCreate.setAction("answer for banning");
			toCreate.setName(parsed.getName());
			toCreate.setResult("true");
			if(list.getUserByName(parsed.getName()).isBanned().equals("true"))
			list.getUserByName(parsed.getName()).setBan(false);
			else list.getUserByName(parsed.getName()).setBan(true);
			list.writeFile();
			sendXML(parser.create(toCreate),userSocket.get(user));
			youAreBanned(parsed.getName());
		}
		if (action.equals("logout")){
			try {
				userSocket.get(user).close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			userSocket.remove(user);
			updOnline();
		}
	}
	

private Obj youAreBanned(String name){
		Obj obj = new Obj();
		obj.setAction("you are banned");
		obj.setName(name);
		return obj;
}

public static void  sendXML(Document document, Socket socket){
	String toSend = XmlToString(document);
	try {
		PrintWriter out = new PrintWriter(new BufferedWriter(
				new OutputStreamWriter(socket.getOutputStream())), true);
		out.println(toSend);
	} catch (IOException e) {
		e.printStackTrace();
	}
}

public void updOnline(){
	Obj obj = new Obj();
	obj.setAction("return online list");
	
	Set<User> users = userSocket.keySet();
	for (User user:users) {
		obj.add(user.getName(),user.isBanned());
	}
	Document document= new Parser().create(obj);
	for (User user:users) {
		sendXML(document,userSocket.get(user));
	}
}
	

public static void admin(String name, boolean result){
	Obj obj = new Obj();
	if(userSocket.containsKey(list.getUserByName(name))) {
		if (result ) {
			obj.setAction("you are admin");
			obj.setName(name);
			sendXML(new Parser().create(obj), userSocket.get(list.getUserByName(name)));
		} else if (!result ) {
			obj.setAction("you are not admin");
			obj.setName(name);
			sendXML(new Parser().create(obj), userSocket.get(list.getUserByName(name)));
		}
	}
}
public static Document stringToXxl(String string) throws ParserConfigurationException, IOException, SAXException {
	DocumentBuilderFactory fctr = DocumentBuilderFactory.newInstance();
	DocumentBuilder bldr = fctr.newDocumentBuilder();
	InputSource insrc = new InputSource(new StringReader(string));
	return bldr.parse(insrc);
}
public static String  XmlToString(Document doc){
	TransformerFactory tf = TransformerFactory.newInstance();
	Transformer transformer = null;
	try {
		transformer = tf.newTransformer();
	} catch (TransformerConfigurationException e) {
		e.printStackTrace();
	}
	transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	StringWriter writer = new StringWriter();
	try {
		transformer.transform(new DOMSource(doc), new StreamResult(writer));
	} catch (TransformerException e) {
		e.printStackTrace();
	}
	String output = writer.getBuffer().toString().replaceAll("\n|\r", "");
	return output;
}

}
