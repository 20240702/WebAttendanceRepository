package dao;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.Login;
import model.TblParents;

public class TblParentsDAO {
	// データベース接続に使用する情報
	private final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521/orclpdb";
	private final String DB_USER = "test";
	private final String DB_PASS = "Pass123";

	public TblParents findByLogin(Login login) {
		TblParents tblParents = null;
		//JDBCドライバを読み込む
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		//データベースへ接続
		try (java.sql.Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {

			//SELECT文を準備
			String sql = "SELECT scho_code,pare_code,pare_name,pare_address,pare_tel,pare_pass FROM TBL_Parents WHERE pare_code = ? AND pare_pass = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, login.getUserId());
			pStmt.setString(2, login.getPass());

			//SELECT文を実行し、結果表を取得
			ResultSet rs = pStmt.executeQuery();

			if (rs.next()) {
				//ユーザーが存在したらデータを取得
				//そのユーザーを表すTblParentsインスタンスを生成
				String schoCode = rs.getString("scho_code");
				String pareId = rs.getString("pare_code");
				String pareName = rs.getString("pare_name");
				String pareAddress = rs.getString("pare_address");
				String pareTel = rs.getString("pare_tel");
				String parePass = rs.getString("pare_pass");
				tblParents = new TblParents(schoCode, pareId, pareName, pareAddress, pareTel, parePass);
			}
		} catch (java.sql.SQLException e) {
			System.out.println("接続失敗");
			e.printStackTrace();
			return null;
		}

		return tblParents;
	}
}

//保護者登録(PareCodeはシーケンスで自動発行される）
//INSERT INTO TBL_Parents (scho_code, pare_name, pare_address, pare_tel, pare_pass, pare_upda_time)
//VALUES ((SELECT scho_code FROM TBL_School WHERE scho_name = ?), ？, ？, ？, ？, CURRENT _TIMESTAMP);
//pStmt.setString(1, tblAttendance.getSchoName());
//pStmt.setString(2, tblAttendance.getPareName());
//pStmt.setString(3, tblAttendance.getPareAddress());
//pStmt.setString(4, tblAttendance.getPareTel());
//pStmt.setString(5, tblAttendance.getParePass());

//生徒登録(保護者に対して生徒の人数分必要)
//INSERT INTO TBL_Students (scho_code, belo_grade, belo_class, stud_code, stud_name, pare_code, stud_upda_time)
//VALUES ((SELECT scho_code FROM TBL_School WHERE scho_name = ?), ？, ？, ？, ？, ？, CURRENT _TIMESTAMP);
//pStmt.setString(1, tblAttendance.getSchoName());
//pStmt.setString(2, tblAttendance.getBeloGrade());
//pStmt.setString(3, tblAttendance.getBeloClass());
//pStmt.setString(4, tblAttendance.getStudName());
//pStmt.setString(5, tblAttendance.getPareName());

