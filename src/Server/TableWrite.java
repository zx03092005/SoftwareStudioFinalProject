package Server;

import java.io.File;
import processing.core.PApplet;
import processing.data.Table;
import processing.data.TableRow;

public class TableWrite extends PApplet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Table userData;
	private TableRow row;
	private GameServer gameServer;
	private int indexAccount = 0, indexPassword = 1;

	public TableWrite(GameServer gs) {
		gameServer = gs;
	}
	public void setup() {
		userData = new Table();
	}
	public int newUser(String account, String password) {
		account = account.substring(4);
		password = password.substring(4);
		if(hasSpecialCharacter(account, password) == true) return -1;
		if(new File("user.csv").exists()) {
			userData = loadTable("user.csv", "header");
			if(userData.findRow(account, "account") != null) {
				gameServer.ta.append("account " + account + " is repeat\n");
				return 0;
			}
			row = userData.addRow();
		} else {
			userData.addColumn("account");
			userData.addColumn("password");
			//userData.addColumn("");
			row = userData.addRow();
		}
		row.setString("account", account);
		row.setString("password", password);
		saveTable(userData, "user.csv");
		gameServer.ta.append("account " + account + " create success\n");
		return 1;
	}
	public boolean findUser(String account, String password) {
		if(account.equals("") || password.equals("")) return false;
		if(account.equals("account") || password.equals("password")) return false;
		if(new File("user.csv").exists()) {
			userData = loadTable("user.csv", "header");
			gameServer.ta.append("find user : " + account + "\n");
			gameServer.ta.append("            " + password + "\n");
			TableRow row = userData.findRow(account, "account");
			if(row == null) return false;
			else {
				if(row.getString(indexAccount).equals(account)
						&&row.getString(indexPassword).equals(password)) { 
						return true;
				}
				else return false;
			}
		} else {
			return false;
		}
	}
	private boolean hasSpecialCharacter(String account, String password) {
		int i = 0, j = 0;
		while(i != account.length() || j != password.length()) {
			//System.out.println(account.charAt(i) + " " + password.charAt(j));
			if(i != account.length())
				if(!(Character.isAlphabetic(account.charAt(i)) || Character.isDigit(account.charAt(i)) || account.charAt(i) == '_'))
					return true;
			if(j != password.length())
				if(!(Character.isAlphabetic(password.charAt(j)) || Character.isDigit(password.charAt(j)) || password.charAt(j) == '_'))	
					return true;
			if(i < account.length()) i++; 
			if(j < password.length()) j++;
		}
		return false;
	}
	
}
