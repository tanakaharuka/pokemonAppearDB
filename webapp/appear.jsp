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
			border: 1px solid # 333;
		}
	</style>
</head>
<body>
	<h1> <a href="AppearServlet">ポケモン出現DB</a></h1>
	<hr>
	<form action="AppearServlet" method="POST">
		<input type="radio" name="item" value="ID" checked="checked">ID
		<input type="radio" name="item" value="番号">番号
		<input type="radio" name="item" value="名前">名前
		<br>
		<input type="radio" name="order" value="asc" checked="checked">昇順
		<input type="radio" name="order" value="desc">降順
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
	<a href="AppearServlet?shimei=習志野市">習志野市</a>
	<a href="AppearServlet?shimei=船橋市">船橋市</a>
	<hr>
	<% if (appearList != null) { %>
	出現情報
	<table border="1">
		<tr><th>ID</th><th>番号</th><th>名前</th><th>県名</th><th>市名</th><th>日付</th><th>時刻</th></tr>
		<% for (Appear appear: appearList) { %>
		<tr>
			<td><%=appear.getId() %></td>
			<td><%=appear.getNumber() %></td>
			<td><%=appear.getName() %></td>
			<td><%=appear.getKen() %></td>
			<td><%=appear.getShi() %></td>
			<td><%=appear.getDate() %></td>
			<td><%=appear.getTime() %></td>
		</tr>
		<% } %>
	</table>
	<% } %>
</body>
</html> 