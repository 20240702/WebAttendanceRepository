package sevlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Login;
import model.LoginLogic;
import model.TblEmployees;
import model.TblParents;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// リクエストパラメータを取得
		request.setCharacterEncoding("UTF-8");
		String userType = request.getParameter("userType");
		String forwardPath = "";

		if (userType.equals("parents")) {
			forwardPath = "WEB-INF/jsp/parentsLogin.jsp";
		} else if (userType.equals("employees")) {
			forwardPath = "WEB-INF/jsp/employeesLogin.jsp";
		}

		// ログインページにフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// リクエストパラメータを取得
		request.setCharacterEncoding("UTF-8");
		String userType = request.getParameter("userType");
		String userId = request.getParameter("userId");
		String pass = request.getParameter("pass");

		// ログイン処理
		Login login = new Login(userId, pass);
		LoginLogic loginLogic = new LoginLogic();
		boolean result = loginLogic.execute(login, userType);
		
		// セッションスコープの取得
		HttpSession session = request.getSession();
		
		if (result) { // ログイン成功の場合、保護者、教員それぞれのメインサーブレットにリダイレクトする準備
			String redirectPath = "";
			if (userType.equals("parents")) {
				redirectPath = "ParentsMainServlet";
				TblParents loginUser = loginLogic.getPareInf();
				
				// セッションスコープにログインしたユーザーの情報を保存
				session.setAttribute("loginUser", loginUser);
			} else if (userType.equals("employees")) {
				redirectPath = "EmployeesMainServlet";
				TblEmployees loginUser = loginLogic.getEmplInf();
				
				// セッションスコープにログインしたユーザーの情報を保存
				session.setAttribute("loginUser", loginUser);
			}
			// リダイレクト
			response.sendRedirect(redirectPath);
			

		} else { // ログイン失敗の場合、○○にリダイレクトする準備
			String forwardPath = "";
			login.setMsg("ユーザーIDまたはパスワードが正しくありません");
			if (userType.equals("parents")) {
				forwardPath = "WEB-INF/jsp/parentsLogin.jsp";
			} else if (userType.equals("employees")) {
				forwardPath = "WEB-INF/jsp/employeesLogin.jsp";
			}
			// リクエストスコープにログイン情報を保存
			request.setAttribute("login", login);
			// フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
			dispatcher.forward(request, response);
		}
		
		
		
	}

}
