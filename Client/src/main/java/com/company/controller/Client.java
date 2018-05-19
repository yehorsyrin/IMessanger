package com.company.controller;

import Model.Client.Parser;
import com.company.view.AnswerForPrivateChat;
import com.company.view.PrivateChat;
import com.company.view.Start;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.util.Map;

/**
 * class for interaction with server
 */
public class Client implements Runnable {
    private boolean check;
    private boolean logedIn;
    private boolean created;
    private static Logger logger = Logger.getRootLogger();

    public boolean isCreated() {
        return created;
    }

    public boolean isLogedIn() {
        return logedIn;
    }

    public boolean isCheck() {
        return check;
    }

    public Client(String host, int port) {
        setup(host, port);
    }

    /**
     * method for setuping Socket
     * @param host host of server
     * @param port port of server
     */
    public void setup(String host, int port) {
        if (Main.getSocket() != null && Main.getOut() != null && Main.getIn() != null) {
            try {
                Main.getSocket().close();
                Main.getOut().close();
                Main.getIn().close();
            } catch (IOException e) {
                System.out.println("Error in \"Client.setup\" during close of Socket or IO streams");
                logger.error("Error in \"Client.setup\" during close of Socket or IO streams", e);
            }
        }
        try {
            Main.setSocket(new Socket(host, port));
            System.out.println("Connected");
        } catch (IOException e) {
            System.out.println("Error in \"Client.setup\" during connecting to Server");
            logger.error("Error in \"Client.setup\" during connecting to Server", e);
            System.out.println("Not connected");
            JOptionPane.showMessageDialog(null, "Can't connect to server");
        }
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(Main.getSocket().getInputStream());
        } catch (IOException e) {
            System.out.println("Error in \"Client.setup\" during initialization of reader");
            logger.error("Error in \"Client.setup\" during initialization of reader", e);
        }
        Main.setIn(new BufferedReader(reader));

        try {
            Main.setOut(new PrintWriter(Main.getSocket().getOutputStream()));
        } catch (IOException e) {
            System.out.println("Error in \"Client.setup\" during initialization of writer");
            logger.error("Error in \"Client.setup\" during initialization of writer", e);
        }
        Thread listenThread = new Thread(this);
        listenThread.start();
    }

    /**
     * method for for receiving and processing XMLs from server
     */
    @Override
    public void run() {
        String input;
        Parser parser = new Parser();
        try {
            while ((input = Main.getIn().readLine()) != null) {
                parser.parse(input);
                String action = parser.getInfo().getAction();

                if (action.equals("answer for login")) {
                    String checkStr = parser.getInfo().getCheck();
                    if (checkStr.equals("true")) {
                        logedIn = true;
                    } else {
                        logedIn = false;
                    }
                }
                if (action.equals("answer for delete")) {
                    String checkStr = parser.getInfo().getCheck();
                    if (checkStr.equals("true")) {
                        check = true;
                    } else {
                        check = false;
                    }
                }
                if (action.equals("answer for creating user")) {
                    String checkStr = parser.getInfo().getCheck();
                    if (checkStr.equals("true")) {
                        created = true;
                    } else {
                        created = false;
                    }
                }
                if (action.equals("chat message")) {
                    String username = parser.getInfo().getName();
                    String message = parser.getInfo().getMessage();
                    Main.getMainChat().getTextPane().setText(Main.getMainChat().getTextPane().getText() + "\n" + username + ": " + message);
                    Main.getMainChat().getTextPane().setCaretPosition(Main.getMainChat().getTextPane().getText().length());
                }
                if (action.equals("answer for changing")) {
                    String nameStr = parser.getInfo().getName();
                    String checkStr = parser.getInfo().getCheck();
                    if (checkStr.equals("true")) {
                        check = true;
                        Main.setNick(nameStr);
                    } else {
                        check = false;
                    }
                }
                if (action.equals("return online list")) {
                    Main.getUsers().clear();
                    for (int i = 0; i < parser.getInfo().getUserList().size(); i++) {
                        String nameStr = parser.getInfo().getUserList().get(i);
                        String banStr = parser.getInfo().getBanList().get(i);
                        if (banStr.equals("true")) {
                            Main.addUser("<html><strike>" + nameStr + "</html></strike>");
                            Main.setBan(true);
                        } else {
                            Main.addUser(nameStr);
                            Main.setBan(false);
                        }
                    }
                }
                if (action.equals("user banned")) {
                    check = false;
                }
                if (action.equals("you are admin")) {
                    Main.setAdmin(true);
                    Main.setStatus("Your status: Admin");
                }
                if (action.equals("answer for banning")) {
                    String nameStr = parser.getInfo().getName();
                    String resultStr = parser.getInfo().getCheck();
                    if(resultStr.equals("true")) {
                        check = true;
                    } else {
                        check = false;
                    }
                }
                if (action.equals("you are not admin")) {
                    Main.setAdmin(false);
                    Main.setStatus("Your status: User");
                }
                if (action.equals("you are banned")) {
                    Main.setBan(true);
                    Main.setStatus("Your status: User(banned)");
                    Main.getMainWindow().getChatBtn().setEnabled(false);
                    Main.getMainWindow().getListOfUsersBtn().setEnabled(false);
                    Main.getMainWindow().getProfileBtn().setEnabled(false);
                    JOptionPane.showMessageDialog(null, "You are banned");
                }
                if (action.equals("you are not banned")) {
                    Main.setBan(false);
                    Main.setStatus("Your status: User");
                    Main.getMainWindow().getChatBtn().setEnabled(true);
                    Main.getMainWindow().getListOfUsersBtn().setEnabled(true);
                    Main.getMainWindow().getProfileBtn().setEnabled(true);
                    JOptionPane.showMessageDialog(null, "You are unbanned");
                }
                if (action.equals("server stop")) {
                    Main.getMainChat().getTextPane().setText(Main.getMainChat().getTextPane().getText() + "\nServer stopped!");
                    Main.getSocket().close();
                    Main.getIn().close();
                    Main.getOut().close();
                }
                if (action.equals("server reload")) {
                    JFrame parent;
                    for (Map.Entry<String, PrivateChat> entry : Main.getChats().entrySet()) {
                        parent = (JFrame) entry.getValue().getTopLevelAncestor();
                        parent.dispose();
                    }
                    parent = (JFrame) Main.getMainChat().getTopLevelAncestor();
                    parent.dispose();
                    Main.setStart(new Start());
                    Main.getStart().setVisible(true);
                    setup(Main.getHost(), Main.getPort());
                    Main.getMainChat().getTextPane().setText(Main.getMainChat().getTextPane().getText() + "\nServer reloaded!");
                }
                if(action.equals("answer for private chat")) {
                    if(parser.getInfo().getCheck().equals("true")) {
                        PrivateChat chat = new PrivateChat(parser.getInfo().getFrom());
                        chat.setCheck(true);
                        Main.getChats().put(parser.getInfo().getFrom(), chat);
                        JFrame frame = new JFrame();
                        frame.addWindowListener(new WindowAdapter() {
                            public void windowClosing(WindowEvent e) {
                                Main.getChats().remove(parser.getInfo().getFrom());
                            }
                        });
                        frame.setSize(398, 300);
                        frame.setTitle(parser.getInfo().getFrom());
                        frame.add(chat);
                        frame.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "User " + parser.getInfo().getFrom()
                                + " declined your invitation to private chat!");
                    }
                }
                if(action.equals("ask for private chat")) {
                    AnswerForPrivateChat answer = new AnswerForPrivateChat(parser.getInfo().getFrom());
                    answer.setVisible(true);
                }
                if (action.equals("message")) {
                    final String fromStr = parser.getInfo().getFrom();
                    String toStr = parser.getInfo().getTo();
                    String textStr = parser.getInfo().getMessage();
                    if (!Main.getChats().containsKey(fromStr)) {
                        PrivateChat chat = new PrivateChat(fromStr);
                        Main.getChats().put(fromStr, chat);
                        JFrame frame = new JFrame();
                        frame.addWindowListener(new WindowAdapter() {
                            public void windowClosing(WindowEvent e) {
                                Main.getChats().remove(fromStr);
                            }
                        });
                        frame.setSize(398, 300);
                        frame.setTitle(fromStr);
                        frame.add(chat);
                        frame.setVisible(true);
                    }
                    Main.getChats().get(fromStr).getTextPane().setText(Main.getChats().get(fromStr).getTextPane().getText()
                            + "\n" + fromStr + ": " + textStr);
                    Main.getChats().get(fromStr).getTextPane().setCaretPosition(Main.getChats().get(fromStr).getTextPane().getText().length());
                }
            }
        } catch (IOException e) {
            System.out.println("Error in \"Client.run\" during reading from input stream");
            logger.error("Error in \"Client.run\" during reading from input stream", e);
        }
    }
}
