/* User entity
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

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Hex;

public class User implements DBEntity {

	private Integer id;
	private String username;
	private String password;

	@Override
	public TableConfig getConfig() {
		return new TableConfig() {
			@Override
			public String description() {
				return "create table " + this.table() + " (" + "id INT,"
						+ "username TEXT," + "password TEXT" + ")";
			}

			@Override
			public String table() {
				return "USER";
			}
		};
	}

	@Override
	public void save() {
		try {

			if (id == null) {
				id = DB.idFor(User.class);
				PreparedStatement ps = DB
						.getConnection()
						.prepareStatement(
								"INSERT INTO USER(id, username, password) VALUES (?, ?, ?)");
				ps.setInt(1, id);
				ps.setString(2, username);
				ps.setString(3, password);
				ps.execute();
			} else {
				PreparedStatement ps = DB.getConnection().prepareStatement(
						"UPDATE USER SET username=?, password=? WHERE id = ?");
				ps.setString(1, username);
				ps.setString(2, password);
				ps.setInt(3, id);
				ps.execute();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int getId() {
		return id;
	}

	public static User fromId(int id) {
		try {
			ResultSet rs = DB
					.getConnection()
					.createStatement()
					.executeQuery(
							"select id, username, password from USER where id = "
									+ id);
			rs.next();
			User user = new User();
			user.id = rs.getInt(1);
			user.username = rs.getString(2);
			user.password = rs.getString(3);
			return user;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static User fromUsername(String username) {
		try {
			PreparedStatement ps = DB
					.getConnection()
					.prepareStatement(
							"select id, username, password from USER where username = ?");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if (!rs.first()) {
				return null;
			}
			User user = new User();
			user.id = rs.getInt(1);
			user.username = rs.getString(2);
			user.password = rs.getString(3);
			return user;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static List<User> all() {
		List<User> list = new ArrayList<User>();
		ResultSet rs;
		try {
			rs = DB.getConnection().createStatement()
					.executeQuery("select id, username, password from USER");
			while (rs.next()) {
				User user = new User();
				user.id = rs.getInt(1);
				user.username = rs.getString(2);
				user.password = rs.getString(3);
				list.add(user);
			}
			return list;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Tweet tweet(String content){
		Tweet tweet = new Tweet();
		tweet.setContent(content);
		tweet.setUser(this);
		tweet.save();
		return tweet;
	}

	
	
	public static String genHash(String password)  {
		try {
			MessageDigest cript = MessageDigest.getInstance("SHA-1");
			cript.reset();
			cript.update(("salt:" + password + ":cenas").getBytes("utf8"));
			return new String(Hex.encodeHex(cript.digest()));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Tweet> getMyTweets(){
		return Tweet.forUser(this);
	}
	
	public void follow(User user){
		if (Follow.userFollowsUser(this, user)){
			return;
		}
		Follow f = new Follow();
		f.setFrom(this.getId());
		f.setTo(user.getId());
		f.save();
	}
	
	public void unfollow(User user){
		Follow.unfollow(this, user);
	}
	
	public boolean follows(User user){
		return Follow.userFollowsUser(this, user);
	}
	
	public int getFollowers(){
		return Follow.followers(this);
	}
	
	public int getFollowing(){
		return Follow.following(this);
	}
	
}
