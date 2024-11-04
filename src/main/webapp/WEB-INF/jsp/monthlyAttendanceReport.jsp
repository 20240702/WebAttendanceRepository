<%@page import="java.io.Console"%>
<%@page import="java.time.YearMonth"%>
<%@page import="model.TblEmployees"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="model.MonthlyAttendance, java.util.List, java.util.Arrays, java.time.LocalDate"%>
<%
// セッションスコープに保存された出欠情報を取得
List<MonthlyAttendance> monthlyAtteList = (List<MonthlyAttendance>) session.getAttribute("monthlyAtteList");
TblEmployees tblEmployees = (TblEmployees) session.getAttribute("loginUser");
int year = (Integer) session.getAttribute("year");
int month = (Integer) session.getAttribute("month");
int openDays = (Integer) session.getAttribute("openDays");
YearMonth yearMonth = YearMonth.of(year, month);
int daysOfMonth = yearMonth.lengthOfMonth();
// 曜日リスト
List<String> weekdays = Arrays.asList("日", "月", "火", "水", "木", "金", "土");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>出席簿出力</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f2f4f7;
	color: #333;
	display: flex;
	justify-content: center;
	padding: 20px;
	margin: 0;
}

.container {
	max-width: 1500px;
	width: 100%;
	background-color: #ffffff;
	padding: 20px;
	border-radius: 10px;
	box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

h1 {
	text-align: center;
	color: #283b5b;
}

table {
	width: 100%;
	border-collapse: collapse;
	margin-top: 20px;
	display: flex;
	justify-content: center;
}

th, td {
	padding: 10px;
	text-align: center;
	border: 1px solid #ccc;
}

th {<!--
	background-color: #283b5b; --> <!--
	color: #fff; -->
	color: #283b5b;
}

a {
	display: inline-block;
	color: #ffffff;
	background-color: #283b5b;
	padding: 10px 20px;
	text-decoration: none;
	border-radius: 5px;
	transition: background-color 0.3s;
	margin: 10px;
}

a:hover {
	background-color: #4f738e;
	transform: translateY(-3px);
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
		<h1><%=year%>年<%=month%>月 出席簿
		</h1>
		<table>
			<tr>
				<th colspan="2"><%=year%>年<%=month%>月</th>
				<th colspan="4"><%=tblEmployees.getBeloGrade()%>年<%=tblEmployees.getBeloClass()%>組</th>
				<th colspan="<%=daysOfMonth - 13%>"></th>
				<th colspan="2">担任</th>
				<th colspan="5"><%=tblEmployees.getEmplName()%></th>
				<th colspan="4">授業日数</th>
				<th colspan="3"><%=openDays%>日</th>
			</tr>
			<!-- ここからヘッダーとデータ行を作成 -->
			<tr>
				<th rowspan="2">出席番号</th>
				<th rowspan="2">氏名</th>
				<%
				for (int day = 1; day <= daysOfMonth; day++) {
					LocalDate date = yearMonth.atDay(day);
					int dayOfWeekValue = date.getDayOfWeek().getValue() % 7;
				%>
				<th rowspan="2"><%=day%><br><%=weekdays.get(dayOfWeekValue)%></th>
				<%
				}
				%>
				<th rowspan="2">出席</th>
				<th colspan="2">欠席</th>
				<th rowspan="2">出停</th>
				<th rowspan="2">忌引き</th>
			</tr>
			<tr>
				<th>病欠</th>
				<th>家事</th>
			</tr>
			<%
			for (int i = 0; i < monthlyAtteList.size(); i++) {
				MonthlyAttendance monthlyAttendance = monthlyAtteList.get(i);
			%>
			<tr>
				<td><%=monthlyAttendance.getStudCode()%></td>
				<td><%=monthlyAttendance.getStudName()%></td>
				<%
				for (String atteStatus : monthlyAttendance.getAtteRecords()) {
				%>
				<td><%=atteStatus%></td>
				<%
				}
				%>
				<td><%=monthlyAttendance.getSumShusseki()%></td>
				<td><%=monthlyAttendance.getSumByoketsu()%></td>
				<td><%=monthlyAttendance.getSumKaji()%></td>
				<td><%=monthlyAttendance.getSumShuttei()%></td>
				<td><%=monthlyAttendance.getSumKibiki()%></td>
			</tr>
			<%
			}
			%>
		</table>
		<div style="text-align: center; margin-top: 20px;">
			<a href="EmployeesMainServlet">戻る</a><a href="LogoutServlet">ログアウト</a>
		<div class="footer">
            © OKOCHI 2024
        </div>
		</div>
	</div>
</body>
</html>
