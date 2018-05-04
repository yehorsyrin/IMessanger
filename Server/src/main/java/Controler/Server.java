package Controler;
import Model.Obj;
import Model.Parser;
import Model.User;
import Model.UserList;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * class of server. contain threads of all clients
 */
public class Server {
private static Logger logger = Logger.getRootLogger();
private static UserList list = new UserList();
private static HashMap < User, Socket > users = new HashMap < > ();
private static ArrayList < AlternativeServerThread > threads = new ArrayList < > ();
private static ServerSocket socketListener;
private static int initSocket;
private static Thread listen = new Thread(new Runnable() {
	@Override
	public void run() {
		try {
			socketListener = new ServerSocket(initSocket);
		} catch (IOException e) {
			logger.error("    problem with socket  ", e);
			System.out.println("(!)some problems with socket, server stop work");
			System.exit(1);
			return;
		}
		System.out.println("server started");
		System.out.println("print /help to see all commands");
		while (true) {
			Socket socket = null;
			try {
				socket = socketListener.accept();
			} catch (IOException e) {
				logger.error("    problem with connecting to client socket  ", e);
				System.out.println("(!)some problems with connecting to client socket " + socket.toString());
			}
			AlternativeServerThread thread = new AlternativeServerThread(socket, list, users);
			threads.add(thread);
		}
	}
});

/**
 * create thread of server and run it
 * @param initSocket socket on which server runs
 */
public Server(int initSocket) {
	this.initSocket = initSocket;
	listen.setDaemon(true);
	listen.start();
}

private static HashMap < User, Socket > getUsers() {
	return users;
}
private static ArrayList < AlternativeServerThread > getThreads() {
	return threads;
}
private static void end() {
	listen.interrupt();
}
private static void stopServer() {
	Parser parser = new Parser();
	Obj obj = new Obj();
	obj.setAction("server stop");
	Document document = parser.create(obj);
	Set < User > users = getUsers().keySet();
	int i = 0;
	for (User user: users) {
		AlternativeServerThread.sendXML(document, getUsers().get(user));
		try {
			getUsers().get(user).close();
		} catch (IOException e) {
			logger.error("    problem with closing thread "+getUsers().get(user).getInetAddress().getHostAddress(), e);
			System.out.println("problem with closing thread");
		}
		getUsers().remove(user);
		if (getThreads().size() >= i)
			getThreads().get(i).interrupt();
	}
	end();
	System.out.println("stopped");
	
}
private static void reload() {
	Parser parser = new Parser();
	Obj obj = new Obj();
	obj.setAction("server reload");
	Document document = parser.create(obj);
	Set < User > users = getUsers().keySet();
	int i = 0;
	for (User user: users) {
		AlternativeServerThread.sendXML(document, getUsers().get(user));
		try {
			getUsers().get(user).close();
		} catch (IOException e) {
			logger.error("    problem with closing some thread", e);
			System.out.println("problem with closing thread");
		}
		getUsers().remove(user);
		if (getThreads().size() >= i)
			getThreads().get(i).interrupt();
	}
	System.out.println("restarted");
}
private static boolean commands() {
	String help = "help";
	String admin = "admin username y/n";
	Scanner scanner = new Scanner(System.in);
	String command = scanner.nextLine().trim();
	if (command.equals("/help")) {
		System.out.println("/online                 show all online users\n" +
				"/admin username y/n     if \"y\" gives user admin permission or take back admin permission if \"n\"\n" +
				"/stop                   stop server\n" +
				"/restart                restart server");
	} else if (command.startsWith("/admin") && command.length() > 9) {
		String name = command.substring("/admin ".length(), command.length() - 1).trim();
		if (list.getUserByName(name) != null) {
			boolean result = false;
			if (command.endsWith("y")) {
				list.getUserByName(name).setAdmin(true);
				result = true;
				System.out.println(name + " is admin now");
			} else if (command.endsWith("n") && list.getUserByName(name).isAdmin().equals("true")) {
				list.getUserByName(name).setAdmin(false);
				result = false;
				System.out.println(name + "is not admin anymore");
			} else System.out.println("wrong format");
			list.writeFile();
			if (users.containsKey(list.getUserByName(name)))
				AlternativeServerThread.admin(name, result);
		} else System.out.println("there is no such user");
	} else if (command.startsWith("/online")) {
		Iterator < User > iterator = users.keySet().iterator();
		if (users.keySet().size() == 0) {
			System.out.println("no online users");
			return true;
		}
		while (iterator.hasNext()) {
			System.out.println("    " + iterator.next().getName());
		}
	} else if (command.startsWith("/stop")) {
		stopServer();
		return false;
	} else if (command.equals("/restart")) {
		reload();
		return true;
	} else System.out.println("there is no such command");
	return true;
}

public static void main(String[] args) {
	logger.info("Started");
	System.out.println("print socket or print \"default\" to use default 7777 socket");
	Scanner scanner = new Scanner(System.in);
	String line = scanner.nextLine();
	int s;
	if (line.equals("default")) {
		s = 7777;
	} else {
		s = Integer.parseInt(line);
	}
	Server server = new Server(s);
	
	Thread commands = new Thread() {
		@Override
		public void run() {
			
			boolean cont = true;
			while (cont) {
				cont = commands();
				if (!cont) end();
			}
		}
	};
	commands.start();
}
}