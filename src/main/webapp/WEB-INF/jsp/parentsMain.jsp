<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="model.TblAttendance, java.util.List"%>
<%
// セッションスコープに保存された出欠情報を取得
List<TblAttendance> atteList = (List<TblAttendance>) session.getAttribute("atteList");
%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>保護者メインページ</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f2f4f7;
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
	color: #283b5b;
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
	background-color: #4f738e;
	transform: translateY(-3px);
}

.form-inline {
	display: inline;
}

.logout-link {
	display: block;
	color: #283b5b;
	text-decoration: none;
	margin-top: 20px;
	font-weight: bold;
}

.logout-link:hover {
	color: #4f738e;
}

.footer {
	margin-top: 20px;
	font-size: 0.9em;
	color: #4f738e; /* フッターの補助的な色 */
}
</style>
</head>
<body>
	<div class="container">
		<h1>
			ようこそ、
			<c:out value="${loginUser.pareName }"></c:out>
			さん
		</h1>
		<form action="ParentsMainServlet" method="post">
			<input type="hidden" name="process" value="requestNewAtte"> <input
				type="submit" class="button" value="出欠入力">
		</form>
		<%
		if (atteList != null) {
			for (int i = 0; i < atteList.size(); i++) {
				TblAttendance atte = atteList.get(i);
		%>
		<div class="attendance-record">
			<p>
				名前: <strong><%=atte.getStudName()%></strong>
			</p>
			<p>
				出欠:
				<%=t(atte.%>, ステータス:
				<%=atte.getAtteStatus()%></p>
			<p>
				到着時刻:
				<%=atte.getAtteArriTime()%>, 退室時刻:
				<%=atte.getAtteDepaTime()%></p>
			<p>
				備考:
				<%=atte.getAtteInf()%></p>

			<form action="ParentsMainServlet" method="post" class="form-inline">
				<input type="hidden" name="indexData" value="<%=i%>"> <input
					type="hidden" name="process" value="requestModify"> <input
					type="submit" class="button" value="修正">
			</form>
			<form action="ParentsMainServlet" method="post" class="form-inline">
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
