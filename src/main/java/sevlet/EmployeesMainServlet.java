package sevlet;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.AttendanceLogic;
import model.MonthlyAttendance;
import model.TblAttendance;
import model.TblEmployees;

/**
 * Servlet implementation class EmployeesMainServlet
 */
@WebServlet("/EmployeesMainServlet")
public class EmployeesMainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// セッションスコープから情報を取得
		HttpSession session = request.getSession();
		TblEmployees loginEmployees = (TblEmployees) session.getAttribute("loginUser");

		// ログインしているか確かめたうえで、教員メインページへフォワード
		if (loginEmployees == null) {
			response.sendRedirect("index.jsp");
		} else {
			LocalDate startDate = LocalDate.now();
			// 担当クラスの出欠情報を取得し、セッションスコープに保存
			if (request.getParameter("startDate") != null) {
				startDate = LocalDate.parse(request.getParameter("startDate"));
			}
			startDate = startDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
			LocalDate endDate = startDate.plusDays(6);
			AttendanceLogic attendanceLogic = new AttendanceLogic();
			List<TblAttendance> atteList = attendanceLogic.findAtteByEmpl(loginEmployees, startDate, endDate);
			for(TblAttendance tblAttendance: atteList) {
				tblAttendance.setAtteArriTimeHtml(attendanceLogic.formatJavaDateToHtmlTime(tblAttendance.getAtteArriTime()));
				tblAttendance.setAtteDepaTimeHtml(attendanceLogic.formatJavaDateToHtmlTime(tblAttendance.getAtteDepaTime()));
				tblAttendance.setAtteRecordHtml(attendanceLogic.formatJavaDateToHtmlTimeMMDD(tblAttendance.getAtteRecord()));
			}
			session.setAttribute("atteList", atteList);
			session.setAttribute("startDate", startDate);

			// フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/employeesMain.jsp");
			dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// リクエストパラメータを取得
		request.setCharacterEncoding("UTF-8");
		String process = request.getParameter("process");

		HttpSession session = request.getSession();
		List<TblAttendance> atteList = (List<TblAttendance>) session.getAttribute("atteList");

		// 出欠情報修正・登録の際のリクエストパラメータを取得
		String atteRecord = request.getParameter("atteRecord");
		String atteStatus = request.getParameter("atteStatus");
		String atteInf = request.getParameter("atteInf");
		String atteArriTime = request.getParameter("atteArriTime");
		String atteDepaTime = request.getParameter("atteDepaTime");
		String atteRemark = request.getParameter("atteRemarks");
		String studCode = request.getParameter("studCode");

		AttendanceLogic attendanceLogic = new AttendanceLogic();

		if (process.equals("requestModify")) { // 出欠情報の修正用ページへフォワード
			// 修正する出欠情報のレコードをセッションスコープ(ArrayList)から取得
			Integer indexData = Integer.parseInt(request.getParameter("indexData"));
			TblAttendance tblAttendance = atteList.get(indexData);

			// tblAttendanceにHTMLの日付型情報を追加
			tblAttendance
					.setAtteArriTimeHtml(attendanceLogic.formatJavaDateToHtmlTime(tblAttendance.getAtteArriTime()));
			tblAttendance
					.setAtteDepaTimeHtml(attendanceLogic.formatJavaDateToHtmlTime(tblAttendance.getAtteDepaTime()));

			// セッションスコープに保存
			session.setAttribute("modTblAttendance", tblAttendance);

			// 修正用ビューにフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/modifyRecordEmpl.jsp");
			dispatcher.forward(request, response);
		} else if (process.equals("applyModify")) { // 出欠情報の修正を適用
			TblAttendance modTblAttendance = (TblAttendance) session.getAttribute("modTblAttendance");
			modTblAttendance.setAtteStatus(atteStatus);
			modTblAttendance.setAtteInf(atteInf);
			modTblAttendance.setAtteArriTime(attendanceLogic.formatHtmlTimeToJavaDate(atteArriTime));
			modTblAttendance.setAtteDepaTime(attendanceLogic.formatHtmlTimeToJavaDate(atteDepaTime));
			modTblAttendance.setAtteRemarks(atteRemark);

			Boolean isSuccess = attendanceLogic.updateDatabyEmpl(modTblAttendance);
			if (isSuccess) {
				// TODO 修正成功のメッセージを飛ばす処理
				doGet(request, response);
			} else {
				doGet(request, response);
			}
		} else if (process.equals("requestNewAtte")) { // 出欠を新たに入力するページをフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/addNewRecordEmpl.jsp");
			dispatcher.forward(request, response);
		} else if (process.equals("insertNewAtte")) { // 入力された出欠情報を登録する
			// ログインしている教員の情報をセッションスコープから取得
			TblEmployees tblEmployees = (TblEmployees) session.getAttribute("loginUser");

			// 登録出欠情報をインスタンス化
			TblAttendance newTblAttendance = new TblAttendance(tblEmployees.getSchoId(), tblEmployees.getBeloGrade(),
					tblEmployees.getBeloClass(), studCode, "", attendanceLogic.formatHtmlDateToJavaDate(atteRecord),
					atteStatus, atteInf, attendanceLogic.formatHtmlTimeToJavaDate(atteArriTime),
					attendanceLogic.formatHtmlTimeToJavaDate(atteDepaTime), atteRemark);

			// データベースへ登録
			Boolean isSuccess = attendanceLogic.insertDatabyEmpl(newTblAttendance, tblEmployees);
			if (isSuccess) {
				// TODO 修正成功のメッセージを飛ばす処理
				System.out.println("INSERT成功");
				doGet(request, response);
			} else {
				doGet(request, response);
			}
		} else if (process.equals("requestDeleteAtte")) { // 対象の出欠情報の削除前確認
			// 削除する出欠情報のレコードをセッションスコープ(ArrayList)から取得
			Integer indexData = Integer.parseInt(request.getParameter("indexData"));
			TblAttendance tblAttendance = atteList.get(indexData);

			// セッションスコープに保存
			session.setAttribute("delTblAttendance", tblAttendance);

			// フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/deleteRecordEmpl.jsp");
			dispatcher.forward(request, response);
		} else if (process.equals("executeDeleteAtte")) { // 対象の出欠情報の削除実行
			// ログインしている教員の情報をセッションスコープから取得
			TblEmployees tblEmployees = (TblEmployees) session.getAttribute("loginUser");
			// 削除する出欠情報をセッションスコープから取得
			TblAttendance tblAttendance = (TblAttendance) session.getAttribute("delTblAttendance");

			// 削除の実行
			Boolean isSuccess = attendanceLogic.deleteDatabyEmpl(tblAttendance, tblEmployees);
			if (isSuccess) {
				// TODO 削除成功のメッセージを飛ばす処理
				System.out.println("DELETE成功");
				doGet(request, response);
			} else {
				doGet(request, response);
			}
		} else if (process.equals("requestMonthlyAttendance")) {
			// 対象年月をリクエストパラメータから取得
			String yearMonthKey = request.getParameter("yearMonthKey"); // YYYY-MM形式
			// 年月を型変換して、セッションスコープに保存
			Integer year = Integer.parseInt(yearMonthKey.split("-")[0]);
			Integer month = Integer.parseInt(yearMonthKey.split("-")[1]);
			Integer openDays = attendanceLogic.getOpenDays(yearMonthKey);
			session.setAttribute("year", year);
			session.setAttribute("month", month);
			session.setAttribute("openDays", openDays);
			// ログインしている教員の情報をセッションスコープから取得
			TblEmployees tblEmployees = (TblEmployees) session.getAttribute("loginUser");

			// 出席簿用データ取得
			List<MonthlyAttendance> monthlyAtteList = attendanceLogic.getMonthlyAttendances(tblEmployees, yearMonthKey);
			if (monthlyAtteList != null) {
				session.setAttribute("monthlyAtteList", monthlyAtteList);
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/monthlyAttendanceReport.jsp");
				dispatcher.forward(request, response);
			} else {
				System.out.println("出席簿用データ取得失敗");
				doGet(request, response);
			}
		}
	}

}
