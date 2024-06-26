package db;

import java.sql.Date;
import java.sql.Time;

public class Appear {
	//ID
	private int id;
	//番号
	private int number;
	//名前
	private String name;
	//県名
	private String ken;
	//市名
	private String shi;
	//日付
	private Date date;
	//時刻
	private Time time;
	//タイプ
	private String type;
	
	//コンストラクタ
	public Appear(int id, int number, String name, String ken, String shi, Date date, Time time, String type) {
		this.id = id;
		this.number = number;
		this.name = name;
		this.ken = ken;
		this.shi = shi;
		this.date = date;
		this.time = time;
		this.type = type;
	}
	
	public int getId() {
		return id;
	}
	public int getNumber() {
		return number;
	}
	public String getName() {
		return name;
	}
	public String getKen() {
		return ken;
	}
	public String getShi() {
		return shi;
	}
	public Date getDate() {
		return date;
	}
	public Time getTime() {
		return time;
	}
	public String getType() {
		return type;
	}
}
