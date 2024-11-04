<%@page import="java.time.LocalDate"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="model.TblAttendance, java.util.List"%>
<%
// セッションスコープに保存された出欠情報を取得
List<TblAttendance> atteList = (List<TblAttendance>) session.getAttribute("atteList");
LocalDate startDate = (LocalDate)session.getAttribute("startDate");
%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>教員メインページ</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f2f4f7; /* 淡い背景色 */
	margin: 0;
	padding: 0;
	display: flex;
	justify-content: center;
	align-items: center;
	min-height: 100vh;
}

.container {
	width: 90%;
	max-width: 600px;
	background-color: #ffffff;
	padding: 20px;
	border-radius: 10px;
	box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
	text-align: center;
}

h1 {
	color: #283b5b; /* メインカラー */
	font-size: 1.5em;
	margin-bottom: 20px;
}

.attendance-record {
	border: 1px solid #d3edf9;
	border-radius: 5px;
	padding: 15px;
	margin: 15px 0;
	text-align: left;
	background-color: #f9fafb;
}

.attendance-record p {
	margin: 5px 0;
	color: #333;
}

.button {
	display: inline-block;
	background-color: #283b5b;
	color: #ffffff;
	padding: 10px 20px;
	text-decoration: none;
	border-radius: 5px;
	transition: background-color 0.3s, transform 0.3s;
	margin: 5px 0;
}

.button:hover {
	background-color: #4f738e; /* ホバー時の色 */
	transform: translateY(-3px);
}

.footer {
	margin-top: 20px;
	font-size: 0.9em;
	color: #4f738e; /* フッターの補助的な色 */
}

.logout-link {
	display: block;
	color: #283b5b;
	text-decoration: none;
	margin-top: 20px;
	font-weight: bold;
}

.logout-link:hover {
	color: #4f738e; /* ホバー時の色 */
}
</style>
</head>
<body>
	<div class="container">
		<h1>
			ようこそ、
			<c:out value="${loginUser.emplName }"></c:out>
			先生
		</h1>
		<form action="EmployeesMainServlet" method="post">
			<input type="hidden" name="process" value="requestNewAtte"> <input
				type="submit" class="button" value="出欠入力">
		</form>
		<form action="EmployeesMainServlet" method="post">
			<input type="hidden" name="process" value="requestMonthlyAttendance">
			<label for="yearMonthKey">対象月を選択:</label> <input type="month"
				name="yearMonthKey" required> <input type="submit"
				class="button" value="出席簿を表示">
		</form>
		<form action="EmployeesMainServlet" method="get">
		<input type="hidden" name="startDate" value="<%= startDate.minusDays(7) %>">
		<button type="submit">前の週へ</button>
		</form>
		<form action="EmployeesMainServlet" method="get">
		<input type="hidden" name="startDate" value="<%= startDate.plusDays(7) %>">
		<button type="submit">次の週へ</button>
		</form>
		<%
		if (atteList.size()!=0) {
			for (int i = 0; i < atteList.size(); i++) {
				TblAttendance atte = atteList.get(i);
		%>
		<div class="attendance-record">
			<p>
				名前: <strong><%=atte.getStudName()%></strong>
			</p>
			<p>
				出欠:
				<%=atte.getAtteRecordHtml()%>, ステータス:
				<%=atte.getAtteStatus()%></p>
			<p>
				到着時刻:
				<%=atte.getAtteArriTimeHtml()%>, 退室時刻:
				<%=atte.getAtteDepaTimeHtml()%></p>
			<p>
				備考:
				<%=atte.getAtteInf()%></p>

			<form action="EmployeesMainServlet" method="post" class="form-inline">
				<input type="hidden" name="indexData" value="<%=i%>"> <input
					type="hidden" name="process" value="requestModify"> <input
					type="submit" class="button" value="修正">
			</form>
			<form action="EmployeesMainServlet" method="post" class="form-inline">
				<input type="hidden" name="indexData" value="<%=i%>"> <input
					type="hidden" name="process" value="requestDeleteAtte"> <input
					type="submit" class="button" value="削除">
			</form>
		</div>
		<%
		}
		} else {
		%>
		<p>該当する出欠データがありません。</p>
		<%
		}
		%>




		<a href="LogoutServlet" class="logout-link">ログアウト</a>
		<div class="footer">© OKOCHI 2024</div>
	</div>
</body>
</html>
