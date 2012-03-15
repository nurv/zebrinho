/* Tweet Entity
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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class Tweet implements DBEntity {
	private Integer id;
	private String content;
	private DateTime date;
	private VBox<User> user = new VBox<User>() {

		@Override
		public User load() {
			return User.fromId(getId());
		}

	};

	public Tweet() {
		setDate(new DateTime());
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public DateTime getDate() {
		return date;
	}

	private void setDate(DateTime date) {
		this.date = date;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setUser(User u) {
		user.setObject(u);
	}

	public User getUser() {
		return user.getObject();
	}

	public String getFormatedDate() {

		return this.getDate().toString(
				DateTimeFormat.forPattern("dd-MM-yyyy hh:mm"));
	}

	@Override
	public TableConfig getConfig() {
		return new TableConfig() {
			@Override
			public String description() {
				return "create table " + this.table() + "(" + "id INT,"
						+ "content TEXT," + "date TIMESTAMP," + "user INT"
						+ ")";
			}

			@Override
			public String table() {
				return "TWEET";
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
								"INSERT INTO TWEET(id, content, date, user) VALUES (?, ?, ?, ?)");
				ps.setInt(1, id);
				ps.setString(2, content);
				ps.setTimestamp(3, new Timestamp(date.getMillis()));
				ps.setInt(4, user.getId());
				ps.execute();
			} else {
				PreparedStatement ps = DB
						.getConnection()
						.prepareStatement(
								"UPDATE TWEET SET content=?, date=?, user=? WHERE id = ?");
				ps.setString(1, content);
				ps.setTimestamp(3, new Timestamp(date.getMillis()));
				ps.setInt(4, user.getId());
				ps.setInt(4, id);
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

	public static Tweet fromId(int id) {
		try {
			ResultSet rs = DB
					.getConnection()
					.createStatement()
					.executeQuery(
							"select id, content, date, user from TWEET where id = "
									+ id);
			rs.next();
			Tweet tweet = new Tweet();
			tweet.id = rs.getInt(1);
			tweet.content = rs.getString(2);
			tweet.date = new DateTime(rs.getTimestamp(3).getTime());
			tweet.user.setId(rs.getInt(4));
			return tweet;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static List<Tweet> forUser(User user) {
		try {

			ResultSet rs = DB
					.getConnection()
					.createStatement()
					.executeQuery(
							"select id, content, date, user from TWEET where user = "
									+ user.getId() + " ORDER BY date DESC");
			List<Tweet> list = new ArrayList<Tweet>();
			while (rs.next()) {
				Tweet tweet = new Tweet();
				tweet.id = rs.getInt(1);
				tweet.content = rs.getString(2);
				tweet.date = new DateTime(rs.getTimestamp(3).getTime());
				tweet.user.setId(rs.getInt(4));
				list.add(tweet);
			}
			return list;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static List<Tweet> forUserWall(User user) {
		try {
			String query = "select id, content, date, user from TWEET where user = "
					+ user.getId() + " ";

			List<Follow> follows = Follow.forUser(user);
			for (Follow follow : follows) {
				query += "OR user = " + follow.getTo() + " ";
			}
			query += " ORDER BY date DESC";
			ResultSet rs = DB.getConnection().createStatement()
					.executeQuery(query);
			List<Tweet> list = new ArrayList<Tweet>();
			while (rs.next()) {
				Tweet tweet = new Tweet();
				tweet.id = rs.getInt(1);
				tweet.content = rs.getString(2);
				tweet.date = new DateTime(rs.getTimestamp(3).getTime());
				tweet.user.setId(rs.getInt(4));
				list.add(tweet);
			}
			return list;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
