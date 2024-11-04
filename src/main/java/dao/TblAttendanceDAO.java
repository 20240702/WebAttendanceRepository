package dao;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.MonthlyAttendance;
import model.TblAttendance;
import model.TblEmployees;
import model.TblParents;

public class TblAttendanceDAO {
	// データベース接続に使用する情報
	private final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521/orclpdb";
	private final String DB_USER = "test";
	private final String DB_PASS = "Pass123";

	public List<TblAttendance> findAtteByPare(TblParents tblParents) {
		List<TblAttendance> atteList = new ArrayList<>(); // 検索結果を保存するArrayList
		String pareId = tblParents.getPareId(); // 検索キーとする保護者ID
		//String scho_code = tblParents.getSchoId(); // 検索キーとする学校コード
		//JDBCドライバを読み込む
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		//データベースへ接続
		try (java.sql.Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {

			//SELECT文を準備
			String sql = "SELECT scho_code,belo_grade,belo_class,stud_code,stud_name,atte_recoad,atte_status,"
					+ "atte_information,atte_arri_time, atte_depa_time,atte_remarks,atte_upda_time FROM TBL_Attendance "
					+ "JOIN TBL_Students USING(scho_code,belo_grade,belo_class,stud_code) WHERE pare_code = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, pareId);

			//SELECT文を実行し、結果表を取得
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				//ユーザーが存在したらデータを取得
				//そのユーザーを表すTblAttendanceインスタンスを生成
				String schoCode = rs.getString("scho_code");
				String beloGrade = rs.getString("belo_grade");
				String beloClass = rs.getString("belo_class");
				String studCode = rs.getString("stud_code");
				String studName = rs.getString("stud_name");
				Date atteRecoadSql = rs.getDate("atte_recoad");
				java.util.Date atteRecoad = new java.util.Date(atteRecoadSql.getTime());
				String atteStatus = rs.getString("atte_status");
				String atteInf = rs.getString("atte_information");
				Date atteArriTimeSql = rs.getDate("atte_arri_time");
				Date atteDepaTimeSql = rs.getDate("atte_depa_time");
				java.util.Date atteArriTime = new java.util.Date(atteArriTimeSql.getTime());
				java.util.Date atteDepaTime = new java.util.Date(atteDepaTimeSql.getTime());
				String atteRemarks = rs.getString("atte_remarks");
				TblAttendance tblAttendance = new TblAttendance(schoCode, beloGrade, beloClass, studCode, studName,
						atteRecoad,
						atteStatus, atteInf, atteArriTime, atteDepaTime, atteRemarks);
				atteList.add(tblAttendance);
				//				System.out.println("One record found.");
			}
		} catch (java.sql.SQLException e) {
			System.out.println("出欠情報データベースへの接続失敗");
			e.printStackTrace();
			return null;
		}

		return atteList;
	}

	public List<TblAttendance> findAtteByEmpl(TblEmployees tblEmployees) {
		List<TblAttendance> atteList = new ArrayList<>(); // 検索結果を保存するArrayList
		String beloGrade = tblEmployees.getBeloGrade(); // 検索キーとする学年
		String beloClass = tblEmployees.getBeloClass(); // 検索キーとする組
		String emplCode = tblEmployees.getEmplId();
		//String scho_code = tblParents.getSchoId(); // 検索キーとする学校コード

		//JDBCドライバを読み込む
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		//データベースへ接続
		try (java.sql.Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {

			//SELECT文を準備
			String sql = "SELECT scho_code,belo_grade,belo_class,stud_code,stud_name,atte_recoad,atte_status,"
					+ "atte_information,atte_arri_time,atte_depa_time,atte_remarks,atte_upda_time FROM TBL_Attendance "
					+ "JOIN TBL_Students USING(scho_code,belo_grade,belo_class,stud_code) "
					+ "WHERE belo_grade = ? AND belo_class = ? AND scho_code = (SELECT scho_code FROM TBL_Employees WHERE empl_code = ?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, beloGrade);
			pStmt.setString(2, beloClass);
			pStmt.setString(3, emplCode);

			//SELECT文を実行し、結果表を取得
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				//ユーザーが存在したらデータを取得
				//そのユーザーを表すTblAttendanceインスタンスを生成
				String schoCode = rs.getString("scho_code");
				String studCode = rs.getString("stud_code");
				String studName = rs.getString("stud_name");
				Date atteRecoadSql = rs.getDate("atte_recoad");
				java.util.Date atteRecoad = new java.util.Date(atteRecoadSql.getTime());
				String atteStatus = rs.getString("atte_status");
				String atteInf = rs.getString("atte_information");
				Date atteArriTimeSql = rs.getDate("atte_arri_time");
				Date atteDepaTimeSql = rs.getDate("atte_depa_time");
				java.util.Date atteArriTime = new java.util.Date(atteArriTimeSql.getTime());
				java.util.Date atteDepaTime = new java.util.Date(atteDepaTimeSql.getTime());
				String atteRemarks = rs.getString("atte_remarks");
				TblAttendance tblAttendance = new TblAttendance(schoCode, beloGrade, beloClass, studCode, studName,
						atteRecoad,
						atteStatus, atteInf, atteArriTime, atteDepaTime, atteRemarks);
				atteList.add(tblAttendance);
				//				System.out.println("One record found.");
			}
		} catch (java.sql.SQLException e) {
			System.out.println("出欠情報データベースへの接続失敗");
			e.printStackTrace();
			return null;
		}

		return atteList;
	}

	public boolean updateDatabyEmpl(TblAttendance tblAttendance) {
		boolean isSuccess = false;
		//JDBCドライバを読み込む
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		//データベースへ接続
		try (java.sql.Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {

			//UPDATE文を準備
			String sql = "UPDATE TBL_Attendance "
					+ "SET atte_status = ?,atte_information = ?,atte_arri_time = ?,atte_depa_time =?,atte_remarks = ?,"
					+ "atte_upda_time = CURRENT_TIMESTAMP WHERE belo_grade = ? AND belo_class = ? AND stud_code = ? "
					+ "AND scho_code = ? AND atte_recoad = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, tblAttendance.getAtteStatus());
			pStmt.setString(2, tblAttendance.getAtteInf());
			Date atteArriTimeSql = new Date(tblAttendance.getAtteArriTime().getTime());
			Date atteDepaTimeSql = new Date(tblAttendance.getAtteDepaTime().getTime());
			pStmt.setDate(3, atteArriTimeSql);
			pStmt.setDate(4, atteDepaTimeSql);
			pStmt.setString(5, tblAttendance.getAtteRemarks());
			pStmt.setString(6, tblAttendance.getBeloGrade());
			pStmt.setString(7, tblAttendance.getBeloClass());
			pStmt.setString(8, tblAttendance.getStudCode());
			pStmt.setString(9, tblAttendance.getSchoCode());
			Date atteRecoadSql = new Date(tblAttendance.getAtteRecoad().getTime());
			pStmt.setDate(10, atteRecoadSql);

			pStmt.executeUpdate();
			isSuccess = true;
		} catch (java.sql.SQLException e) {
			System.out.println("データ更新失敗");
			e.printStackTrace();
			return false;
		}
		return isSuccess;
	}

	public boolean updateDatabyPare(TblAttendance tblAttendance) {
		boolean isSuccess = false;
		//JDBCドライバを読み込む
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		//データベースへ接続
		try (java.sql.Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {

			//UPDATE文を準備
			String sql = "UPDATE TBL_Attendance SET atte_status = ?,atte_information = ?,atte_arri_time = ?,"
					+ "atte_depa_time = ?,atte_remarks = ?,atte_upda_time = CURRENT_TIMESTAMP WHERE belo_grade = ? "
					+ "AND belo_class = ? AND stud_code = ? AND scho_code = ? AND atte_recoad = ?";

			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, tblAttendance.getAtteStatus());
			pStmt.setString(2, tblAttendance.getAtteInf());
			Date atteArriTimeSql = new Date(tblAttendance.getAtteArriTime().getTime());
			Date atteDepaTimeSql = new Date(tblAttendance.getAtteDepaTime().getTime());
			pStmt.setDate(3, atteArriTimeSql);
			pStmt.setDate(4, atteDepaTimeSql);
			pStmt.setString(5, tblAttendance.getAtteRemarks());
			pStmt.setString(6, tblAttendance.getBeloGrade());
			pStmt.setString(7, tblAttendance.getBeloClass());
			pStmt.setString(8, tblAttendance.getStudCode());
			pStmt.setString(9, tblAttendance.getSchoCode());
			Date atteRecoadSql = new Date(tblAttendance.getAtteRecoad().getTime());
			pStmt.setDate(10, atteRecoadSql);

			pStmt.executeUpdate();
			isSuccess = true;
		} catch (java.sql.SQLException e) {
			System.out.println("データ更新失敗");
			e.printStackTrace();
			return false;
		}
		return isSuccess;
	}

	public boolean insertDatabyPare(TblAttendance tblAttendance, TblParents tblParents) {
		boolean isSuccess = false;
		//JDBCドライバを読み込む
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		//データベースへ接続
		try (java.sql.Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {

			//INSERT文を準備
			String sql = "INSERT INTO TBL_Attendance (scho_code, belo_grade, belo_class, stud_code, atte_recoad, "
					+ "atte_status, atte_information, atte_arri_time, atte_depa_time, atte_remarks, atte_upda_time) "
					+ "VALUES ((SELECT scho_code FROM TBL_Parents WHERE pare_code= ?),?,?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP)";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, tblParents.getPareId());
			pStmt.setString(2, tblAttendance.getBeloGrade());
			pStmt.setString(3, tblAttendance.getBeloClass());
			pStmt.setString(4, tblAttendance.getStudCode());
			Date atteRecoadSql = new Date(tblAttendance.getAtteRecoad().getTime());
			pStmt.setDate(5, atteRecoadSql);
			pStmt.setString(6, tblAttendance.getAtteStatus());
			pStmt.setString(7, tblAttendance.getAtteInf());
			Date atteArriTimeSql = new Date(tblAttendance.getAtteArriTime().getTime());
			Date atteDepaTimeSql = new Date(tblAttendance.getAtteDepaTime().getTime());
			pStmt.setDate(8, atteArriTimeSql);
			pStmt.setDate(9, atteDepaTimeSql);
			pStmt.setString(10, tblAttendance.getAtteRemarks());
			pStmt.executeUpdate();
			isSuccess = true;
		} catch (java.sql.SQLException e) {
			System.out.println("データ挿入失敗");
			e.printStackTrace();
			return false;
		}
		return isSuccess;
	}

	public boolean insertDatabyEmpl(TblAttendance tblAttendance, TblEmployees tblEmployees) {
		boolean isSuccess = false;
		//JDBCドライバを読み込む
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		//データベースへ接続
		try (java.sql.Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {

			//INSERT文を準備
			String sql = "INSERT INTO TBL_Attendance (scho_code, belo_grade, belo_class, stud_code, atte_recoad, "
					+ "atte_status, atte_information, atte_arri_time, atte_depa_time, atte_remarks, atte_upda_time) "
					+ "VALUES ((SELECT scho_code FROM TBL_Employees WHERE empl_code = ?),?,?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP)";

			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, tblEmployees.getEmplId());
			pStmt.setString(2, tblAttendance.getBeloGrade());
			pStmt.setString(3, tblAttendance.getBeloClass());
			pStmt.setString(4, tblAttendance.getStudCode());
			Date atteRecoadSql = new Date(tblAttendance.getAtteRecoad().getTime());
			pStmt.setDate(5, atteRecoadSql);
			pStmt.setString(6, tblAttendance.getAtteStatus());
			pStmt.setString(7, tblAttendance.getAtteInf());
			Date atteArriTimeSql = new Date(tblAttendance.getAtteArriTime().getTime());
			Date atteDepaTimeSql = new Date(tblAttendance.getAtteDepaTime().getTime());
			pStmt.setDate(8, atteArriTimeSql);
			pStmt.setDate(9, atteDepaTimeSql);
			pStmt.setString(10, tblAttendance.getAtteRemarks());

			pStmt.executeUpdate();
			isSuccess = true;
		} catch (java.sql.SQLException e) {
			System.out.println("データ挿入失敗");
			e.printStackTrace();
			return false;
		}
		return isSuccess;
	}

	public boolean deleteDataByPare(TblAttendance tblAttendance, TblParents tblParents) {
		boolean isSuccess = false;
		//JDBCドライバを読み込む
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		//データベースへ接続
		try (java.sql.Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {

			//DELETE文を準備
			String sql = "DELETE FROM TBL_Attendance WHERE scho_code =(SELECT scho_code FROM TBL_Parents "
					+ "WHERE pare_code= ?)AND belo_grade =? AND belo_class = ? "
					+ "AND stud_code = ? AND atte_recoad = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, tblParents.getPareId());
			pStmt.setString(2, tblAttendance.getBeloGrade());
			pStmt.setString(3, tblAttendance.getBeloClass());
			pStmt.setString(4, tblAttendance.getStudCode());
			Date atteRecoadSql = new Date(tblAttendance.getAtteRecoad().getTime());
			pStmt.setDate(5, atteRecoadSql);

			pStmt.executeUpdate();
			isSuccess = true;
		} catch (java.sql.SQLException e) {
			System.out.println("データ削除失敗");
			e.printStackTrace();
			return false;
		}
		return isSuccess;
	}

	public boolean deleteDataByEmpl(TblAttendance tblAttendance, TblEmployees tblEmployees) {
		boolean isSuccess = false;
		//JDBCドライバを読み込む
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		//データベースへ接続
		try (java.sql.Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {

			//DELATE文を準備
			String sql = "DELETE FROM TBL_Attendance WHERE scho_code =(SELECT scho_code FROM TBL_Employees "
					+ "WHERE empl_code= ?) AND belo_grade =? AND belo_class = ? AND stud_code = ? AND atte_recoad = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, tblEmployees.getEmplId());
			pStmt.setString(2, tblAttendance.getBeloGrade());
			pStmt.setString(3, tblAttendance.getBeloClass());
			pStmt.setString(4, tblAttendance.getStudCode());
			Date atteRecoadSql = new Date(tblAttendance.getAtteRecoad().getTime());
			pStmt.setDate(5, atteRecoadSql);

			pStmt.executeUpdate();
			isSuccess = true;
		} catch (java.sql.SQLException e) {
			System.out.println("データ削除失敗");
			e.printStackTrace();
			return false;
		}
		return isSuccess;
	}

	public List<MonthlyAttendance> getMonthlyData(TblEmployees tblEmployees, String yearMonthKey, Integer daysOfMonth,
			Integer openDays) {
		List<MonthlyAttendance> atteList = new ArrayList<>(); // 検索結果を保存するArrayList
		String beloGrade = tblEmployees.getBeloGrade(); // 検索キーとする学年
		String beloClass = tblEmployees.getBeloClass(); // 検索キーとする組

		//JDBCドライバを読み込む
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		//データベースへ接続
		try (java.sql.Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {

			//SELECT文を準備。結果は生徒名でソートする。ほしいのは生徒名、出欠内容（日付も）、理由
//			本当はWHERE句にscho_codeも必要
			String sql = "SELECT stud_code,stud_name, TO_CHAR(atte_recoad,'DD') AS atte_day,atte_status,atte_information "
					+ "FROM TBL_Attendance "
//					+ "RIGHT "
					+ "JOIN TBL_Students USING (belo_grade,belo_class,stud_code) WHERE belo_grade=? AND belo_class=? "
					+ "AND (TO_CHAR(atte_recoad,'YYYY-MM')=? OR atte_recoad IS NULL) ORDER BY stud_code";
			
			PreparedStatement pStmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			pStmt.setString(1, beloGrade);
			pStmt.setString(2, beloClass);
			pStmt.setString(3, yearMonthKey); 
			// yyyy-mm 例えば2024-10は2024年10月
//			System.out.println(beloGrade + "年" + beloClass + "組");

			//SELECT文を実行し、結果表を取得
			ResultSet rs = pStmt.executeQuery();
		

			String previousStudName = ""; // 1つ前のレコードの生徒名を保持するための変数。
			Integer previousStudCode = 0;
			String[] monthlyAtteStatus = new String[daysOfMonth];
			java.util.Arrays.fill(monthlyAtteStatus, "");
			int sumByoketsu = 0;
			int sumKaji = 0;
			int sumShuttei = 0;
			int sumKibiki = 0;

			while (rs.next()) {
				String studName = rs.getString("stud_name");

				// 前の生徒名と違うかつ1行目ではない場合、これまでのデータをインスタンスに保存する。
				if (!rs.isFirst() && !studName.equals(previousStudName)) {
					String[] data = monthlyAtteStatus.clone();
					MonthlyAttendance monthlyAttendance = new MonthlyAttendance(previousStudCode, previousStudName, data);
					monthlyAttendance.setSumByoketsu(sumByoketsu);
					monthlyAttendance.setSumKaji(sumKaji);
					monthlyAttendance.setSumShuttei(sumShuttei);
					monthlyAttendance.setSumKibiki(sumKibiki);
					monthlyAttendance.setSumShusseki(openDays - sumByoketsu - sumKaji - sumKibiki - sumShuttei);

					// ArrayListに追加
					atteList.add(monthlyAttendance);

					// 初期化
					for (int i = 0; i < monthlyAtteStatus.length; i++) {
						monthlyAtteStatus[i] = "";
					}
					sumByoketsu = 0;
					sumKaji = 0;
					sumKibiki = 0;
					sumShuttei = 0;
				}

				Integer studCode = rs.getInt("stud_code");
				Integer atteDay = rs.getInt("atte_day");
				String atteStatus = rs.getString("atte_status");
				String atteInf = rs.getString("atte_information");
				if (atteStatus.equals("欠席")) {
					if (atteInf.equals("病欠")) {
						atteStatus = "病欠";
						sumByoketsu++;
					} else if (atteInf.equals("家事都合")) {
						atteStatus = "家事";
						sumKaji++;
					} else if (atteInf.equals("忌引き")) {
						atteStatus = "忌引";
						sumKibiki++;
					} else if (atteInf.equals("出席停止")) {
						atteStatus = "出停";
						sumShuttei++;
					}
				} else if (atteStatus.equals("遅刻／早退")) {
					atteStatus = "遅早";
				}
				monthlyAtteStatus[atteDay - 1] = atteStatus;
				previousStudName = studName;
				previousStudCode = studCode;

				// 最終行の場合、上と同じくインスタンスに保存。
				if (rs.isLast()) {
					String[] data = monthlyAtteStatus.clone();
					MonthlyAttendance monthlyAttendance = new MonthlyAttendance(studCode, studName, data);
					monthlyAttendance.setSumByoketsu(sumByoketsu);
					monthlyAttendance.setSumKaji(sumKaji);
					monthlyAttendance.setSumShuttei(sumShuttei);
					monthlyAttendance.setSumKibiki(sumKibiki);
					monthlyAttendance.setSumShusseki(openDays - sumByoketsu - sumKaji - sumKibiki - sumShuttei);

					// ArrayListに追加
					atteList.add(monthlyAttendance);
				}
			}
		} catch (java.sql.SQLException e) {
			System.out.println("出欠情報データベースへの接続失敗");
			e.printStackTrace();
			return null;
		}

		return atteList;
	}

}

//保護者側UPDATE文
//String sql = " UPDATE TBL_Attendance SET atte_status = ?,atte_information = ?,atte_arri_time = ?,atte_depa_time = ?,atte_remarks = ?,atte_upda_time = CURRENT_TIMESTAMP WHERE belo_grade = ? AND belo_class = ? AND stud_code = ? AND scho_code = ? AND atte_recoad = ?";
//PreparedStatement pStmt = conn.prepareStatement(sql);
//pStmt.setString(1, tblAttendance.getAtteStatus());
//pStmt.setString(2, tblAttendance.getAtteInf());
//pStmt.setString(3, tblAttendance.getAtteArriTime());
//pStmt.setString(4, tblAttendance.getAtteDepaTime());
//pStmt.setString(5, tblAttendance.getAtteRemarks());
//pStmt.setString(6, tblAttendance.getBeloGrade());
//pStmt.setString(7, tblAttendance.getBeloClass());
//pStmt.setString(8, tblAttendance.getStudCode());
//pStmt.setString(9, tblAttendance.getSchoCode());
//pStmt.setString(10, tblAttendance.getAtteRecoad());

//教員側UPDATE文
//String sql = "UPDATE TBL_Attendance SET atte_status = ?,atte_information = ?,atte_arri_time = ?,atte_depa_time =?,atte_remarks = ?,atte_upda_time = CURRENT_TIMESTAMP WHERE belo_grade = ? AND belo_class = ? AND stud_code = ? AND scho_code = ? AND atte_recoad = ?";
//PreparedStatement pStmt = conn.prepareStatement(sql);
//pStmt.setString(1, tblAttendance.getAtteStatus());
//pStmt.setString(2, tblAttendance.getAtteInf());
//pStmt.setString(3, tblAttendance.getAtteArriTime());
//pStmt.setString(4, tblAttendance.getAtteDepaTime());
//pStmt.setString(5, tblAttendance.getAtteRemarks());
//pStmt.setString(6, tblAttendance.getBeloGrade());
//pStmt.setString(7, tblAttendance.getBeloClass());
//pStmt.setString(8, tblAttendance.getStudCode());
//pStmt.setString(9, tblAttendance.getSchoCode());
//pStmt.setString(10, tblAttendance.getAtteRecoad());

//保護者側insert文
//String sql = "INSERT INTO TBL_Attendance (scho_code, belo_grade, belo_class, stud_code, atte_recoad, atte_status, atte_information, atte_arri_time, atte_depa_time, atte_remarks, atte_upda_time)
//VALUES
//((SELECT scho_code
//FROM TBL_Parents
//WHERE pare_code= ?),?,?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP)";
//PreparedStatement pStmt = conn.prepareStatement(
//		sql);pStmt.setString(1,tblAttendance.getPareCode());pStmt.setString(2,tblAttendance.getBeloGrade());pStmt.setString(3,tblAttendance.getBeloClass());pStmt.setString(4,tblAttendance.getStudCode());pStmt.setString(5,tblAttendance.getAtteRecoad());pStmt.setString(6,tblAttendance.getAtteStatus());pStmt.setString(7,tblAttendance.getAtteInf());pStmt.setString(8,tblAttendance.getAtteArriTime());pStmt.setString(9,tblAttendance.AtteDepaTime());pStmt.setString(10,tblAttendance.getAtteRemarks());

//職員側insert文
//String sql = "INSERT INTO TBL_Attendance (scho_code, belo_grade, belo_class, stud_code, atte_recoad, atte_status, atte_information, atte_arri_time, atte_depa_time, atte_remarks, atte_upda_time)
//VALUES
//((SELECT scho_code
//FROM TBL_Employees
//WHERE empl_code = ?),?,?,?,?,?,?,CURRENT_TIMESTAMP)";
//PreparedStatement pStmt = conn.prepareStatement(
//		sql);pStmt.setString(1,tblAttendance.getEmplCode());pStmt.setString(2,tblAttendance.getBeloGrade());pStmt.setString(3,tblAttendance.getBeloClass());pStmt.setString(4,tblAttendance.getStudCode());pStmt.setString(5,tblAttendance.getAtteRecoad());pStmt.setString(6,tblAttendance.getAtteStatus());pStmt.setString(7,tblAttendance.getAtteInf());pStmt.setString(8,tblAttendance.getAtteArriTime());pStmt.setString(9,tblAttendance.AtteDepaTime());pStmt.setString(10,tblAttendance.getAtteRemarks());

//保護者側DELATE文
//String sql = "DELATE FROM TBL_Attendance WHERE pare_code=？AND belo_grade =? AND belo_class = ? AND stud_code AND atte_recoad = ?";
//pStmt.setString(1, tblAttendance.getPareCode ());
//pStmt.setString(2, tblAttendance.getBeloGrade());
//pStmt.setString(3, tblAttendance.getBeloClass());
//pStmt.setString(4, tblAttendance.getStudCode());
//pStmt.setString(5, tblAttendance.getAtteRecoad());

//職員側DELATE文
//String sql = "DELATE FROM TBL_Attendance WHERE scho_code =(SELECT scho_code FROM TBL_Employees WHERE empl_code= ?) AND belo_grade =? AND belo_class = ? AND stud_code AND atte_recoad = ?";
//pStmt.setString(1, tblAttendance.getEmplCode ());
//pStmt.setString(2, tblAttendance.getBeloGrade());
//pStmt.setString(3, tblAttendance.getBeloClass());
//pStmt.setString(4, tblAttendance.getStudCode());
//pStmt.setString(5, tblAttendance.getAtteRecoad());