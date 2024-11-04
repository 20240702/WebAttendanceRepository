package test;

import model.Login;
import model.LoginLogic;

public class LoginLogicTest {
  public static void main(String[] args) {
    testExecuteParentsOK(); // ログイン成功のテスト
    testExecuteEmployeesOK(); // ログイン失敗のテスト
  }
  public static void testExecuteParentsOK() {
    Login login = new Login("2001", "password1234567");
    LoginLogic bo = new LoginLogic();
    boolean result = bo.execute(login, "parents");
    if (result) {
      System.out.println("testExecuteParentsOK:成功しました");
    } else {
      System.out.println("testExecuteParentsOK:失敗しました");
    }
  }
  public static void testExecuteEmployeesOK() {
    Login login = new Login("3001", "teachpass123456");
    LoginLogic bo = new LoginLogic();
    boolean result = bo.execute(login, "employees");
    if (result) {
      System.out.println("testExecuteEmployeesOK:成功しました");
    } else {
      System.out.println("testExecuteEmployeesOK:失敗しました");
    }
  }
}