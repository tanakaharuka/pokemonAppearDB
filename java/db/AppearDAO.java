package db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/** テーブルappear用のDAO */
public class AppearDAO {
/** プログラム起動時に一度だけ実行される */
	static {
		try {
			Class.forName("org.h2.Driver"); // JDBC Driverの読み込み
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Appear> findAll() {
		List<Appear> list = new ArrayList<>();
		String url = "jdbc:h2:tcp://localhost/./s2232098";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, "user", "pass");
			String sql = "select ID, appear.番号, 名前, 県名, 市名, 日付, 時刻"
					+ " from appear"
					+ " join pokemon using(番号)"
					+ " join ( select 県名 , 市名, 市コード from shi"
					+ " join ken using(県コード)) using(市コード)";
			PreparedStatement pre = conn.prepareStatement(sql);
			ResultSet rs = pre.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("ID");
				int number = rs.getInt("番号");
				String name = rs.getString("名前");
				String ken = rs.getString("県名");
				String shi = rs.getString("市名");
				Date date = rs.getDate("日付");
				Time time = rs.getTime("時刻");
				Appear a = new Appear(id, number, name, ken, shi, date, time);
				list.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		return list;
	}
	
	//並び替え対応
	public List<Appear> findAll(String item, String order) {
		List<Appear> list = new ArrayList<>();
		String url = "jdbc:h2:tcp://localhost/./s2232098";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, "user", "pass");
			String sql = "select ID, appear.番号, 名前, 県名, 市名, 日付, 時刻"
					+ " from appear"
					+ " join pokemon using(番号)"
					+ " join ( select 県名 , 市名, 市コード from shi"
					+ " join ken using(県コード)) using(市コード)";
			if(item != null && order != null) sql +=" order by " + item + " " +  order ;
			PreparedStatement pre = conn.prepareStatement(sql);
			ResultSet rs = pre.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("ID");
				int number = rs.getInt("番号");
				String name = rs.getString("名前");
				String ken = rs.getString("県名");
				String shi = rs.getString("市名");
				Date date = rs.getDate("日付");
				Time time = rs.getTime("時刻");
				Appear a = new Appear(id, number, name, ken, shi, date, time);
				list.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		return list;
	}
	
	/** 1件のデータを追加する．成功ならtrueを返す． */
	public boolean insert(int number, int shicode) {
		String url = "jdbc:h2:tcp://localhost/./s2232098";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, "user", "pass");
			String sql = "INSERT INTO appear(番号,市コード) VALUES(?,?)";
			PreparedStatement pre = conn.prepareStatement(sql);
			pre.setInt(1, number);
			pre.setInt(2, shicode);
			int result = pre.executeUpdate();
			if (result == 1) return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	/** 1件のデータを日時を指定して追加する．成功ならtrueを返す． */
	public boolean insert(int number, int shicode, int year, int month, int day, int hour, int minute, int second) {
			String url = "jdbc:h2:tcp://localhost/./s2232098";
			Connection conn = null;
			try {
				conn = DriverManager.getConnection(url, "user", "pass");
				String sql = "INSERT INTO appear(番号,市コード,日付,時刻) VALUES(?,?,?,?)";
				PreparedStatement pre = conn.prepareStatement(sql);
				String datestr = String.format("%4d-%02d-%02d", year, month, day);
				Date date = Date.valueOf(datestr);
				String timestr = String.format("%02d:%02d:%02d", hour, minute, second);
				Time time = Time.valueOf(timestr);
				pre.setInt(1, number);
				pre.setInt(2, shicode);
				pre.setDate(3, date);
				pre.setTime(4, time);;
				int result = pre.executeUpdate();
				if (result == 1) return true;
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	//delete
	public boolean deleat(int id) {
		String url = "jdbc:h2:tcp://localhost/./s2232098";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, "user", "pass");
			String sql = "DELETE FROM appear WHERE ID=?";
			PreparedStatement pre = conn.prepareStatement(sql);
			pre.setInt(1, id);
			int result = pre.executeUpdate();
			if (result == 1) return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	//絞り込み
	public List<Appear> filter(String typelist) {
		List<Appear> list = new ArrayList<>();
		String url = "jdbc:h2:tcp://localhost/./s2232098";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, "user", "pass");
			String sql = "select ID, appear.番号, 名前, 県名, 市名, 日付, 時刻, タイプ"
					+ " from appear"
					+ " join pokemon using(番号)"
					+ " join ( select 県名 , 市名, 市コード from shi"
					+ " join ken using(県コード)) using(市コード)"
					+ " join type using(番号)"
					+ " where タイプ in";
			sql += "(" + typelist + ")";
			PreparedStatement pre = conn.prepareStatement(sql);
			ResultSet rs = pre.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("ID");
				int number = rs.getInt("番号");
				String name = rs.getString("名前");
				String ken = rs.getString("県名");
				String shi = rs.getString("市名");
				Date date = rs.getDate("日付");
				Time time = rs.getTime("時刻");
				String type = rs.getString("タイプ");
				Appear a = new Appear(id, number, name, ken, shi, date, time, type);
				list.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	public List<Appear> filter(int start, int end) {
		List<Appear> list = new ArrayList<>();
		String url = "jdbc:h2:tcp://localhost/./s2232098";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, "user", "pass");
			String sql = "select ID, appear.番号, 名前, 県名, 市名, 日付, 時刻"
					+ " from appear"
					+ " join pokemon using(番号)"
					+ " join ( select 県名 , 市名, 市コード from shi"
					+ " join ken using(県コード) where shi.県コード between " + start + " and " + end + " ) using(市コード)";
			PreparedStatement pre = conn.prepareStatement(sql);
			ResultSet rs = pre.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("ID");
				int number = rs.getInt("番号");
				String name = rs.getString("名前");
				String ken = rs.getString("県名");
				String shi = rs.getString("市名");
				Date date = rs.getDate("日付");
				Time time = rs.getTime("時刻");
				Appear a = new Appear(id, number, name, ken, shi, date, time);
				list.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	public boolean runInsert(int number, int city) {
		int shicode = 0;
		String url = "jdbc:h2:tcp://localhost/./s2232098";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, "user", "pass");
			String sql = "with ranked as (select 市コード, row_number() over (order by 県コード) as num from shi)"
					+ " select 市コード from ranked where num=?";
			PreparedStatement pre = conn.prepareStatement(sql);
			pre.setInt(1, city);
			ResultSet rs = pre.executeQuery();
			while(rs.next()) {
				shicode = rs.getInt("市コード");
			}
			return insert(number, shicode);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
} 