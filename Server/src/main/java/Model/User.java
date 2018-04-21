package Model;

/**
 * class of user, every instance contains information about user
 */
public class User {
private String id = "0000";
private String name;
private String password;
private boolean isAdmin = false;
private boolean ban = false;
public User(String name, String password) {
	this.name = name;
	this.password = password;
	isAdmin = false;
	ban = false;
}
public String getName() {
	return name;
}
public boolean login(String password) {
	if (this.password.equals(password)) return true;
	return false;
}
public String getId() {
	return id;
}
public String getPassword() {
	return password;
}
public String isAdmin() {
	if (isAdmin) return "true";
	return "false";
}
public String isBanned() {
	if (ban) return "true";
	return "false";
}
public void setAdmin(boolean admin) {
	isAdmin = admin;
}
public void setBan(boolean ban) {
	this.ban = ban;
}
public void setName(String name) {
	this.name = name;
}
public void setPassword(String password) {
	this.password = password;
}
}