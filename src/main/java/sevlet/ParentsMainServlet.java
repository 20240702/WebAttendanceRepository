package sevlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.AttendanceLogic;
import model.TblAttendance;
import model.TblParents;

/**
 * Servlet implementation class ParentsMainServlet
 */
@WebServlet("/ParentsMainServlet")
public class ParentsMainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// セッションスコープから情報を取得
		HttpSession session = request.getSession();
		TblParents loginParents = (TblParents) session.getAttribute("loginUser");

		// ログインしているか確かめたうえで、保護者メインページへフォワード
		if (loginParents == null) {
			response.sendRedirect("index.jsp");
		} else {
			// 自分の子どもの出欠情報を取得し、リクエストスコープに保存
			AttendanceLogic attendanceLogic = new AttendanceLogic();
			List<TblAttendance> atteList = attendanceLogic.findAtteByPare(loginParents);
			for(TblAttendance tblAttendance: atteList) {
				tblAttendance.setAtteArriTimeHtml(attendanceLogic.formatJavaDateToHtmlTime(tblAttendance.getAtteArriTime()));
				tblAttendance.setAtteDepaTimeHtml(attendanceLogic.formatJavaDateToHtmlTime(tblAttendance.getAtteDepaTime()));
				tblAttendance.setAtteRecordHtml(attendanceLogic.formatJavaDateToHtmlTimeMMDD(tblAttendance.getAtteRecord()));
			}
			session.setAttribute("atteList", atteList);

			// フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/parentsMain.jsp");
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

		AttendanceLogic attendanceLogic = new AttendanceLogic();

		// 出欠情報修正・登録の際のリクエストパラメータを取得
		String atteRecord = request.getParameter("atteRecord");
		String atteStatus = request.getParameter("atteStatus");
		String atteInf = request.getParameter("atteInf");
		String atteArriTime = request.getParameter("atteArriTime");
		String atteDepaTime = request.getParameter("atteDepaTime");
		String atteRemark = request.getParameter("atteRemarks");
		String beloGrade = request.getParameter("beloGrade");
		String beloClass = request.getParameter("beloClass");
		String studCode = request.getParameter("studCode");

		if (process.equals("requestModify")) {
			// 修正する出欠情報のレコードをセッションスコープ(ArrayList)から取得し、セッションスコープ(TblAttendance)に保存
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
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/modifyRecordPare.jsp");
			dispatcher.forward(request, response);
		} else if (process.equals("applyModify")) {
			// 出欠情報の修正(DBのUPDATE)を行う
			TblAttendance modTblAttendance = (TblAttendance) session.getAttribute("modTblAttendance");
			modTblAttendance.setAtteStatus(atteStatus);
			modTblAttendance.setAtteInf(atteInf);
			modTblAttendance.setAtteArriTime(attendanceLogic.formatHtmlTimeToJavaDate(atteArriTime));
			modTblAttendance.setAtteDepaTime(attendanceLogic.formatHtmlTimeToJavaDate(atteDepaTime));
			modTblAttendance.setAtteRemarks(atteRemark);

			Boolean isSuccess = attendanceLogic.updateDatabyPare(modTblAttendance);
			if (isSuccess) {
				// TODO 修正成功のメッセージを飛ばす処理
				doGet(request, response);
			} else {
				doGet(request, response);
			}
		} else if (process.equals("requestNewAtte")) { // 出欠を新たに入力するページをフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/addNewRecordPare.jsp");
			dispatcher.forward(request, response);
		} else if (process.equals("insertNewAtte")) { // 入力された出欠情報を登録する
			// ログインしている保護者の情報をセッションスコープから取得
			TblParents tblParents = (TblParents) session.getAttribute("loginUser");

			// 登録出欠情報をインスタンス化
			TblAttendance newTblAttendance = new TblAttendance(tblParents.getSchoId(), beloGrade, beloClass,
					studCode, "", attendanceLogic.formatHtmlDateToJavaDate(atteRecord), atteStatus, atteInf,
					attendanceLogic.formatHtmlTimeToJavaDate(atteArriTime),
					attendanceLogic.formatHtmlTimeToJavaDate(atteDepaTime), atteRemark);

			// データベースへ登録
			Boolean isSuccess = attendanceLogic.insertDatabyPare(newTblAttendance, tblParents);
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
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/deleteRecordPare.jsp");
			dispatcher.forward(request, response);
		} else if (process.equals("executeDeleteAtte")) { // 対象の出欠情報の削除実行
			// ログインしている保護者の情報をセッションスコープから取得
			TblParents tblParents = (TblParents) session.getAttribute("loginUser");

			// 削除する出欠情報をセッションスコープから取得
			TblAttendance tblAttendance = (TblAttendance) session.getAttribute("delTblAttendance");

			// 削除の実行
			Boolean isSuccess = attendanceLogic.deleteDatabyPare(tblAttendance, tblParents);
			if (isSuccess) {
				// TODO 削除成功のメッセージを飛ばす処理
				System.out.println("DELETE成功");
				doGet(request, response);
			} else {
				doGet(request, response);
			}
		}
	}

}
