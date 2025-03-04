package db;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AppearServlet")
public class AppearServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Thread t;
	public AppearServlet() {
		super();
		t = new MyTread();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String item = request.getParameter("item"); // 並び替えの項目
		String order = request.getParameter("order"); // asc:昇順 desc:降順
		String submit = request.getParameter("submit"); // "並び替え" "登録" "削除"
		String newnumber = request.getParameter("newnumber"); // 登録するポケモン番号
		String newshicode = request.getParameter("newshicode"); // 登録する市コード
		String deleteid = request.getParameter("deleteid"); // 削除するID
		String shimei = request.getParameter("shimei"); // 市名をクリックした場合
		String[] checkedType = request.getParameterValues("type"); //ポケモンのタイプ
		String typelist = "";
		String areaName = request.getParameter("area");
		String[] area = new String[2];
		System.out.printf("\n%s:%s:%s:\n", item, order, submit);
		System.out.printf("%s:%s:\n", newnumber, newshicode);
		System.out.printf("%s:%s:\n", deleteid, shimei);
		if(!t.isAlive()) t.start();
		
		if (submit != null) {
			if (submit.equals("並び替え")) { // この場合は特に何もしない
				
			} else if (submit.equals("登録")) {
				insert(newnumber, newshicode);
			} else if (submit.equals("削除")) {
				delete(deleteid);
			}else if(submit.equals("絞り込み")) {
				try {
					for(int i = 0; i < checkedType.length; i++) {
						typelist += "'" + checkedType[i] + "'";
						if(i + 1 != checkedType.length) typelist += ", ";
					}
				}catch(NullPointerException e) {
					System.out.println("タイプが選択されていません" + e.getMessage());
				}
				System.out.println(typelist);
			}else if(submit.equals("地域検索")) {
				area = areaName.split("-");
			}
		}
		
		if(typelist != "") selectType(request, response, typelist);
		else if(areaName != null && areaName != "") selectArea(request, response, area);
		else selectAll(request, response, item, order);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/appear.jsp");
		dispatcher.forward(request, response);
	}
	/** サーブレットがPOSTメソッドで実行された場合でもdoGet()で処理する */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	/** DAOを呼び出す */
	void selectAll(HttpServletRequest request, HttpServletResponse response, String item, String order) throws ServletException {
		AppearDAO appearDAO = new AppearDAO();
		List<Appear> list = appearDAO.findAll(item, order);
		request.setAttribute("list", list);
	}
	//タイプ絞り込み用
	void selectType(HttpServletRequest request, HttpServletResponse response, String typelist) throws ServletException{
		AppearDAO appearDAO = new AppearDAO();
		List<Appear> list = appearDAO.filter(typelist);
		request.setAttribute("list", list);
	}
	//地域検索用
	void selectArea(HttpServletRequest request, HttpServletResponse response, String[] area) throws ServletException {
		AppearDAO appearDAO = new AppearDAO();
		List<Appear> list = appearDAO.filter(Integer.parseInt(area[0]), Integer.parseInt(area[1]));
		request.setAttribute("list", list);
	}
	/** DAOを呼び出す */
	void insert(String newnumber, String newshicode) {
		try {
			int num = Integer.parseInt(newnumber);
			int code = Integer.parseInt(newshicode);
			AppearDAO appearDAO = new AppearDAO();
			appearDAO.insert(num, code);
		} catch (NumberFormatException e) {
			System.out.println("不正な番号または市コードが入力されました"+e.getMessage());
		}
	}
	/** DAOを呼び出す */
	void delete(String deleteid) {
		try {
			int id = Integer.parseInt(deleteid);
			AppearDAO appearDAO = new AppearDAO();
			appearDAO.deleat(id);
		} catch (NumberFormatException e) {
			System.out.println("不正なIDが入力されました"+e.getMessage());
		}
	}
	
	
}
//自動でポケモンを追加するためのクラス
class MyTread extends Thread {
	final int appearNum = 3; //一度に追加されるポケモンの数
	final int sleepTime = 5 * 60000; //次の追加までの時間
	
	public void run() {
		Random rand = new Random();
		AppearDAO appearDAO = new AppearDAO();
		for(;;) {
			for(int i = 0; i < appearNum; i++) {
				//ランダムにポケモンの番号と市の番号を取得
				int num = rand.nextInt(385) + 1;
				int city = rand.nextInt(1895) + 1;
				appearDAO.runInsert(num, city);
			}
			try {
				Thread.sleep(sleepTime);
			}catch (Exception e){
				System.out.println(e);
			}
		}
	}
}
