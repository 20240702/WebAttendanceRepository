package model;

import dao.TblEmployeesDAO;
import dao.TblParentsDAO;

public class LoginLogic {
	private TblEmployees tblEmployees;
	private TblParents tblParents;
	public boolean execute(Login login, String userType) {
		boolean isLogin = false;
		if (userType.equals("employees")) {
			TblEmployeesDAO dao = new TblEmployeesDAO();
			tblEmployees = dao.findByLogin(login);
			isLogin = (tblEmployees != null);
		} else if (userType.equals("parents")) {
			TblParentsDAO dao = new TblParentsDAO();
			tblParents = dao.findByLogin(login);
			isLogin = (tblParents != null);
		} else {
			System.out.println("ユーザータイプが見つかりません");
		}
		return isLogin;
	}
	
	public TblEmployees getEmplInf() {
		return tblEmployees;
	}
	public TblParents getPareInf() {
		return tblParents;
	}
}