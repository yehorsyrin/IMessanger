
import Model.Obj;
import Model.Parser;
import Model.User;
import Model.UserList;
import org.w3c.dom.Document;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {
	private static UserList list = new UserList();
	private static HashMap<User,Socket> users= new HashMap<>();
	private static ArrayList<AlternativeServerThread> threads = new ArrayList<>();
	private static boolean work = true;
	private static Thread listen = new Thread(new Runnable() {
	@Override
	public void run() {
		ServerSocket socketListener = null;
		try {
			socketListener = new ServerSocket(7777);
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (true) {
			if (!socketListener.isBound())
				System.out.println("+");
			Socket socket = null;
			try {
				socket = socketListener.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(socket.getLocalAddress().getHostAddress());
			AlternativeServerThread thread = new AlternativeServerThread(socket, list, users);
			threads.add(thread);
		}
	}
});
	public Server(){
		listen.setDaemon(true);
		listen.start();
	}

private static HashMap<User, Socket> getUsers() {
	return users;
}
private static ArrayList<AlternativeServerThread> getThreads(){
		return threads;
}
private static void end(){
	System.out.println("try");
	listen.interrupt();
}
public static void stopServer(){
		Parser parser = new Parser();
	    Obj obj = new Obj();
	    obj.setAction("server stop");
		Document document = parser.create(obj);
		Set<User> users  = getUsers().keySet();
	for (User user:users) {
		AlternativeServerThread.sendXML(document,getUsers().get(user));
		try {
			getUsers().get(user).close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		getUsers().remove(user);
	}
	for (AlternativeServerThread thread:getThreads()) {
		thread.interrupt();
		getThreads().remove(thread);
	}
	end();
	System.out.println("stopped");
	
}
public static boolean commands(){
		String help = "#/help";
		String admin = "#/admin username y/n";
		Scanner scanner = new Scanner(System.in);
		String command = scanner.nextLine().trim();
		if(command.equals("/help")){
			System.out.println(help+", "+admin+", /online");
		}else if(command.startsWith("/admin")) {
			String name = command.substring("/admin ".length(), command.length() - 1).trim();
			if (list.getUserByName(name) != null) {
				boolean result = false;
				if (command.endsWith("y")) {
					list.getUserByName(name).setAdmin(true);
					result = true;
					System.out.println("#"+name+" is admin now");
				} else if (command.endsWith("n") && list.getUserByName(name).isAdmin().equals("true")) {
					list.getUserByName(name).setAdmin(false);
					result = false;
					System.out.println("#"+name+"is not admin anymore");
				} else System.out.println("#wrong format");
				list.writeFile();
				if (users.containsKey(list.getUserByName(name)))
					AlternativeServerThread.admin(name, result);
			} else System.out.println("#there is no such user");
		}else if(command.startsWith("/online")){
			Iterator<User> iterator = users.keySet().iterator();
			while (iterator.hasNext()){
				System.out.println("    "+iterator.next().getName());
			}
		}else if(command.startsWith("/stop")) {
			stopServer();
			return false;
		}
		else System.out.println("#there is no such command");
		return true;
	}
public static void main(String[] args) {
	Thread commands = new Thread(){
		@Override
		public void run(){
			System.out.println("#print /help to see all server commands");
			boolean cont= true;
			while (cont){
				System.out.print("#");
				cont =commands();
				if(!cont) end();
			}
		}
	};commands.start();
		System.out.println("server started");
	Server server = new Server();
	
	
}
}
