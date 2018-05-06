package Model.Server;

/**
 * class of user, every instance contains information about user
 */
public class User {
private String name;
private String password;
private boolean isAdmin = false;
private boolean ban = false;

/**
 * creates new user
 * user is not admin and not banned
 * @param name name of user
 * @param password password of user
 */
public User(String name, String password) {
	this.name = name;
	this.password = password;
	isAdmin = false;
	ban = false;
}

/**
 * for getting name of user
 * @return name of user
 */
public String getName() {
	return name;
}

/**
 * check if passwords are the same
 * @param password password to check
 * @return true if password correct and false if not
 */
public boolean login(String password) {
	if (this.password.equals(password)) return true;
	return false;
}

/**
 * for getting password
 * @return password of user
 */
public String getPassword() {
	return password;
}

/**
 * for checking is user admin or not
 * @return "true" if user admin and "false" if not
 */
public String isAdmin() {
	if (isAdmin) return "true";
	return "false";
}

/**
 * for checking is user banned or not
 * @return "true" if user banned and "false" if not
 */
public String isBanned() {
	if (ban) return "true";
	return "false";
}

/**
 * for making user admin or making admin a regular user
 * @param admin if true - make user admin, if false - make user a regular user
 */
public void setAdmin(boolean admin) {
	isAdmin = admin;
}

/**
 * for banning user
 * @param ban if true - make banned
 */
public void setBan(boolean ban) {
	this.ban = ban;
}

/**
 * for setting user a new name
 * @param name new name of user
 */
public void setName(String name) {
	this.name = name;
}

/**
 * for setting user a new password
 * @param password new password of user
 */
public void setPassword(String password) {
	this.password = password;
}
}