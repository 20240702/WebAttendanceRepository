package dao;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.Login;
import model.TblEmployees;


public class TblEmployeesDAO {
	// データベース接続に使用する情報
	private final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521/orclpdb";
	private final String DB_USER = "test";
	private final String DB_PASS = "Pass123";

	public TblEmployees findByLogin(Login login) {
		TblEmployees tblEmployees = null;
		//JDBCドライバを読み込む
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		//データベースへ接続
		try (java.sql.Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {

			//SELECT文を準備
			String sql = "SELECT scho_code,belo_grade,belo_class,empl_code,empl_job,empl_name,empl_address,empl_tel,empl_pass FROM TBL_Employees WHERE empl_code = ? AND empl_pass = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, login.getUserId());
			pStmt.setString(2, login.getPass());

			//SELECT文を実行し、結果表を取得
			ResultSet rs = pStmt.executeQuery();

			if (rs.next()) {
				//ユーザーが存在したらデータを取得
				//そのユーザーを表すTblParentsインスタンスを生成
				String schoCode = rs.getString("scho_code");
				String beloGrade = rs.getString("belo_grade");
				String beloClass= rs.getString("belo_class");
				String emplId = rs.getString("empl_code");
				String emplJob = rs.getString("empl_job");
				String emplName = rs.getString("empl_name");
				String emplAddress = rs.getString("empl_address");
				String emplTel= rs.getString("empl_tel");
				String emplPass = rs.getString("empl_pass");
				tblEmployees = new TblEmployees(schoCode, beloGrade, beloClass,emplId, emplJob,emplName,emplAddress, emplTel, emplPass);
			}
		} catch (java.sql.SQLException e) {
			System.out.println("接続失敗");
			e.printStackTrace();
			return null;
		}

		return tblEmployees;
	}
}

//職員登録(EmplCodeはシーケンスで自動発行される）
//INSERT INTO TBL_Employees (scho_code, belo_grade,belo_class,empl_job, empl_name, empl_address, empl_tel, empl_pass, empl_upda_time)
//VALUES ((SELECT scho_code FROM TBL_School WHERE scho_name = ?), ?,?,?,?,?,?,?, CURRENT_TIMESTAMP);
//pStmt.setString(1, tblAttendance.getSchoName());
//pStmt.setString(2, tblAttendance.getBeloGrade());
//pStmt.setString(3, tblAttendance.getBeloClass());
//pStmt.setString(4, tblAttendance.getEmplJob());
//pStmt.setString(5, tblAttendance.getEmplName());
//pStmt.setString(6, tblAttendance.getEmplAddress());
//pStmt.setString(7, tblAttendance.getEmplTel());
//pStmt.setString(8, tblAttendance.getEmplPass());

