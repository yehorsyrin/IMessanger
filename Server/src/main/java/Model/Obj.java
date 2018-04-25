package Model;

import java.util.ArrayList;

/**
 * class that is more comfortable than xml, instance creating for each xml
 */
public class Obj {
private String action;
private String name;
private String newName;
private String password;

public String getNewName() {
	return newName;
}

public void setNewName(String newName) {
	this.newName = newName;
}

private String newPassword;
private String result;
private String text;
private String from;
private String to;
private ArrayList < String > user = new ArrayList < > ();
private ArrayList < String > ban = new ArrayList < > ();

/**
 * add user to list with usernames of obj and add is user banned to ban list
 * @param user name of user to add
 * @param banned is user banned or not
 */
public void add(String user, String banned) {
	this.user.add(user);
	this.ban.add(banned);
}

/**
 * for getting ban of all users
 * @return list same size of getUser() method and it has only two variant "true" and "false"
 */
public ArrayList < String > getBan() {
	return ban;
}

/**
 * for getting all names of users from obj
 * @return list with names of users
 */
public ArrayList < String > getUser() {
	return user;
}

public String getAction() {
	return action;
}

public void setAction(String action) {
	this.action = action;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public String getNewPassword() {
	return newPassword;
}

public void setNewPassword(String newPassword) {
	this.newPassword = newPassword;
}

public String getResult() {
	return result;
}

public void setResult(String result) {
	this.result = result;
}

public String getText() {
	return text;
}

public void setText(String text) {
	this.text = text;
}

public String getFrom() {
	return from;
}

public void setFrom(String from) {
	this.from = from;
}

public String getTo() {
	return to;
}

public void setTo(String to) {
	this.to = to;
}
}