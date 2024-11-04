package test;

import dao.TblEmployeesDAO;
import dao.TblParentsDAO;
import model.Login;
import model.TblEmployees;
import model.TblParents;

public class TblParentsDAOTest {
	public static void main(String[] args) {
		System.out.println("↓Parentsのテスト");
		testFindByLoginOK(); // ユーザーが見つかる場合のテスト
		testFindByLoginNG(); // ユーザーが見つからない場合のテスト
		System.out.println("↓Employeesのテスト");
		testFindByLoginEmpOK();
		testFindByLoginEmpNG();
	}

	public static void testFindByLoginOK() {
		Login login = new Login("2001", "password1234567");
		TblParentsDAO dao = new TblParentsDAO();
		TblParents result = dao.findByLogin(login);
		if (result != null && result.getPareId().equals("2001") && result.getParePass().equals("password1234567")) {
			System.out.println("testFindByLoginOK:成功しました");
		} else {
			System.out.println("testFindByLoginOK:失敗しました");
		}
	}

	public static void testFindByLoginNG() {
		Login login = new Login("minato", "12345");
		TblParentsDAO dao = new TblParentsDAO();
		TblParents result = dao.findByLogin(login);
		if (result == null) {
			System.out.println("testFindByLoginNG:成功しました");
		} else {
			System.out.println("testFindByLoginNG:失敗しました");
		}
	}

	public static void testFindByLoginEmpOK() {
		Login login = new Login("3001", "teachpass123456");
		TblEmployeesDAO dao = new TblEmployeesDAO();
		TblEmployees result = dao.findByLogin(login);
		if (result != null && result.getEmplId().equals("3001") && result.getEmplPass().equals("teachpass123456")) {
			System.out.println("testFindByLoginEmpOK:成功しました");
		} else {
			System.out.println("testFindByLoginEmpOK:失敗しました");
		}
	}

	public static void testFindByLoginEmpNG() {
		Login login = new Login("minato", "12345");
		TblEmployeesDAO dao = new TblEmployeesDAO();
		TblEmployees result = dao.findByLogin(login);
		if (result == null) {
			System.out.println("testFindByLoginEmpNG:成功しました");
		} else {
			System.out.println("testFindByLoginEmpNG:失敗しました");
		}
	}
}