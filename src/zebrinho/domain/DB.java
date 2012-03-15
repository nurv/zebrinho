/* Central access to the DB for lazy init
   Copyright (C) 2012  Artur Ventura

Author: Artur Ventura

This file is part of Zebrinho.

Zebrinho is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Zebrinho is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with GNU Emacs.  If not, see <http://www.gnu.org/licenses/>.  */
package zebrinho.domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class DB {
	private static Connection conn = null;
	private static DBEntity[] ENTITIES = new DBEntity[] { new Tweet(),
			new User(), new Follow() };
	public static Object lock = new Object();
	public static HashMap<Class, Integer> ids = new HashMap<Class, Integer>();

	public static void init() {
		try {
			String userName = "root";
			String password = "";
			String url = "jdbc:mysql://localhost/zebrinho";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, userName, password);
			System.out.println("Database connection established");
		} catch (Exception e) {
			System.out.println("ERROR : Cannot start DB connection");
			e.printStackTrace();
		}
		try {
			// getting max ids;
			for (DBEntity e : ENTITIES) {
				TableConfig c = e.getConfig();
				ResultSet rs = conn.createStatement().executeQuery(
						"select MAX(id) from " + c.table());
				rs.next();
				ids.put(e.getClass(), rs.getInt(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		if (conn == null) {
			init();
		}
		return conn;
	}

	public static int idFor(Class cl) {
		if (conn == null) {
			init();
		}
		synchronized (lock) {
			int i = ids.get(cl) + 1;
			ids.put(cl, i);
			return i;
		}
	}

	public static void load() {
		try {
			Statement stmt = getConnection().createStatement();
			for (DBEntity entity : ENTITIES) {
				TableConfig c = entity.getConfig();
				System.out.println("Droping " + c.table() + " ...");
				stmt.executeUpdate("DROP TABLE IF EXISTS " + c.table());
				System.out.println("Creating " + c.table() + " ...");
				stmt.executeUpdate(c.description());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
