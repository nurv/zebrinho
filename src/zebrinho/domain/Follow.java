/* Follow entity
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
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

public class Follow implements DBEntity {
	private Integer id;
	private Integer from;

	public Integer getFrom() {
		return from;
	}

	public void setFrom(Integer from) {
		this.from = from;
	}

	public Integer getTo() {
		return to;
	}

	public void setTo(Integer to) {
		this.to = to;
	}

	private Integer to;

	@Override
	public TableConfig getConfig() {
		return new TableConfig() {

			@Override
			public String table() {
				return "FOLLOW";
			}

			@Override
			public String description() {
				String string = "create table " + this.table() + " ("
						+ "id INT," + "`fr` INT," + "`to` INT" + ");";
				return string;
			}
		};
	}

	@Override
	public void save() {
		try {

			if (id == null) {
				id = DB.idFor(Follow.class);
				PreparedStatement ps = DB.getConnection().prepareStatement(
						"INSERT INTO FOLLOW(id, `fr`, `to`) VALUES (?, ?, ?)");
				ps.setInt(1, id);
				ps.setInt(2, this.from);
				ps.setInt(3, this.to);
				ps.execute();
			} else {
				PreparedStatement ps = DB.getConnection().prepareStatement(
						"UPDATE FOLLOW SET `fr`=?, `to`=? WHERE id = ?");
				ps.setInt(1, this.from);
				ps.setInt(2, this.to);
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

	public void setId(Integer id) {
		this.id = id;
	}

	public static List<Follow> forUser(User user) {
		try {
			ResultSet rs = DB
					.getConnection()
					.createStatement()
					.executeQuery(
							"select id, fr, `to` from FOLLOW where `fr` = "
									+ user.getId());
			List<Follow> list = new ArrayList<Follow>();
			while (rs.next()) {
				Follow f = new Follow();
				f.id = rs.getInt(1);
				f.from = rs.getInt(2);
				f.to = rs.getInt(3);
				list.add(f);
			}
			return list;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean userFollowsUser(User follower, User followed) {
		try {
			ResultSet rs = DB
					.getConnection()
					.createStatement()
					.executeQuery(
							"select id, fr, `to` from FOLLOW where fr = "
									+ follower.getId() + " and `to`="
									+ followed.getId());
			if (!rs.first()) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void unfollow(User follower, User followed) {
		try {
			DB.getConnection()
					.createStatement()
					.executeUpdate(
							"delete from FOLLOW where fr = " + follower.getId()
									+ " and `to` = " + followed.getId());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static int followers(User user) {
		try {
			ResultSet rs = DB
					.getConnection()
					.createStatement()
					.executeQuery(
							"select count(id) from FOLLOW where `to`= "
									+ user.getId());
			rs.next();
			return rs.getInt(1);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
	public static int following(User user) {
		try {
			ResultSet rs = DB
					.getConnection()
					.createStatement()
					.executeQuery(
							"select count(id) from FOLLOW where fr= "
									+ user.getId());
			rs.next();
			return rs.getInt(1);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
}
