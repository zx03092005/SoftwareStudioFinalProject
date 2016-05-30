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
	public TableWrite(GameServer gs) {
		gameServer = gs;
	}
	public void setup() {
		userData = new Table();
	}
	public void newData(String account, String password) {
		if(new File("user.csv").exists()) {
			userData = loadTable("user.csv", "header");
			if(userData.findRow(account, "account") != null) {
				System.out.println("repeat file");
				return;
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
		gameServer.ta.append("new user : " + account + "\n");
		gameServer.ta.append("           " + password + "\n");
		saveTable(userData, "user.csv");
	}
	public boolean findUser(String account, String password) {
		if(new File("user.csv").exists()) {
			userData = loadTable("user.csv", "header");
			gameServer.ta.append("find user : " + account + "\n");
			gameServer.ta.append("            " + password + "\n");
			TableRow row = userData.findRow(account, "account");
			if(row == null) return false;
			else {
				if(row.getString(0).equals(account)
						&&row.getString(1).equals(password)) {
					return true;
				}
				else return false;
			}
		} else {
			return false;
		}
	}
}
