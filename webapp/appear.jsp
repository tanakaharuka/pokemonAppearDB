<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="db.Appear, java.util.List" %>
<%
	List<Appear> appearList = (List<Appear>)request.getAttribute("list");
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<style>
		table{
			border-collapse: collapse;
			border: 1px solid #333;
			margin-top: 10px;
		}
		.item {
			margin-right: 30px;
		}
		.serch, .appear {
			display: flex;
		}
	</style>
</head>
<body>
	<h1> <a href="AppearServlet">ポケモン出現DB</a></h1>
	<hr>
	<form action="AppearServlet" method="POST">
		<label><input type="radio" name="item" value="ID" checked="checked">ID</label>
		<label><input type="radio" name="item" value="番号">番号</label>
		<label><input type="radio" name="item" value="名前">名前</label>
		<br>
		<label><input type="radio" name="order" value="asc" checked="checked">昇順</label>
		<label><input type="radio" name="order" value="desc">降順</label>
		<br>
		<input type="submit" name="submit" value="並び替え">
		<hr>
		番号<input type="text" name="newnumber">
		市コード<input type="text" name="newshicode">
		<input type="submit" name="submit" value="登録">
		<hr>
		ID<input type="text" name="deleteid">
		<input type="submit" name="submit" value="削除">
	</form>
	<hr>
	<!-- <a href="AppearServlet?shimei=習志野市">習志野市</a>
	<a href="AppearServlet?shimei=船橋市">船橋市</a> 
	<hr>-->
	<div class="serch">
	<div class="type item">
	<form action="AppearServlet" method="POST">
		<label><input type="checkbox" name="type" value="ノーマル">ノーマル</label>
		<label><input type="checkbox" name="type" value="ほのお">ほのお</label>
		<label><input type="checkbox" name="type" value="みず">みず</label>
		<label><input type="checkbox" name="type" value="くさ">くさ</label>
		<label><input type="checkbox" name="type" value="でんき">でんき</label>
		<label><input type="checkbox" name="type" value="こおり">こおり</label><br>
		<label><input type="checkbox" name="type" value="かくとう">かくとう</label>
		<label><input type="checkbox" name="type" value="どく">どく</label>
		<label><input type="checkbox" name="type" value="じめん">じめん</label>
		<label><input type="checkbox" name="type" value="ひこう">ひこう</label>
		<label><input type="checkbox" name="type" value="エスパー">エスパー</label>
		<label><input type="checkbox" name="type" value="むし">むし</label><br>
		<label><input type="checkbox" name="type" value="いわ">いわ</label>
		<label><input type="checkbox" name="type" value="ゴースト">ゴースト</label>
		<label><input type="checkbox" name="type" value="ドラゴン">ドラゴン</label>
		<label><input type="checkbox" name="type" value="あく">あく</label>
		<label><input type="checkbox" name="type" value="はがね">はがね</label>
		<label><input type="checkbox" name="type" value="フェアリー">フェアリー</label>
		<br>
		<input type="submit" name="submit" value="絞り込み">
	</form>
	</div>
	<div class="area item">
	<form action="AppearServlet" method="POST">
		<label>地域　<select name="area">
			<option value="">すべて</option>
			<option value="1-7">北海道・東北</option>
			<option value="8-14">関東</option>
			<option value="15-23">中部</option>
			<option value="24-30">近畿</option>
			<option value="31-35">中国</option>
			<option value="36-39">四国</option>
			<option value="40-47">九州・沖縄</option>
		</select></label>
		<br>
		<input type="submit" name="submit" value="地域検索">
	</form>
	</div>
	</div>
	<hr>
	<% if (appearList != null) { %>
	<div class="appear">
		<div class="item">出現情報</div>
		<div class="item"><form action="AppearServlet" method="POST"><input type="submit" value="更新"></form></div>
	</div>
	<table border="1">
		<tr><th>ID</th><th>番号</th><th>名前</th><th>県名</th><th>市名</th><th>日付</th><th>時刻</th>
		<% if (appearList.get(0).getType() != null) { %><th>タイプ</th><% } %></tr>
		<% for (Appear appear: appearList) { %>
		<tr>
			<td><%=appear.getId() %></td>
			<td><%=appear.getNumber() %></td>
			<td><%=appear.getName() %></td>
			<td><%=appear.getKen() %></td>
			<td><%=appear.getShi() %></td>
			<td><%=appear.getDate() %></td>
			<td><%=appear.getTime() %></td>
			<% if (appear.getType() != null) { %>
			<td><%=appear.getType() %></td>
			<%} %>
		</tr>
		<% } %>
	</table>
	<% } %>
</body>
</html> 