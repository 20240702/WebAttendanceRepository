package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;

import dao.TblAttendanceDAO;

public class AttendanceLogic {
	public List<TblAttendance> findAtteByPare(TblParents tblParents) {
		TblAttendanceDAO dao = new TblAttendanceDAO();
		List<TblAttendance> atteList = dao.findAtteByPare(tblParents);
		return atteList;
	}

	public List<TblAttendance> findAtteByEmpl(TblEmployees tblEmployees) {
		TblAttendanceDAO dao = new TblAttendanceDAO();
		List<TblAttendance> atteList = dao.findAtteByEmpl(tblEmployees);
		return atteList;
	}

	public boolean updateDatabyEmpl(TblAttendance tblAttendance) {
		TblAttendanceDAO dao = new TblAttendanceDAO();
		boolean isSuccess = dao.updateDatabyEmpl(tblAttendance);
		return isSuccess;
	}

	public boolean updateDatabyPare(TblAttendance tblAttendance) {
		TblAttendanceDAO dao = new TblAttendanceDAO();
		boolean isSuccess = dao.updateDatabyPare(tblAttendance);
		return isSuccess;
	}
	
	public boolean insertDatabyPare(TblAttendance tblAttendance, TblParents tblParents) {
		TblAttendanceDAO dao = new TblAttendanceDAO();
		boolean isSuccess = dao.insertDatabyPare(tblAttendance, tblParents);
		return isSuccess;
	}
	
	public boolean insertDatabyEmpl(TblAttendance tblAttendance, TblEmployees tblEmployees) {
		TblAttendanceDAO dao = new TblAttendanceDAO();
		boolean isSuccess = dao.insertDatabyEmpl(tblAttendance, tblEmployees);
		return isSuccess;
	}
	
	public boolean deleteDatabyPare(TblAttendance tblAttendance, TblParents tblParents) {
		TblAttendanceDAO dao = new TblAttendanceDAO();
		boolean isSuccess = dao.deleteDataByPare(tblAttendance, tblParents);
		return isSuccess;
	}
	
	public boolean deleteDatabyEmpl(TblAttendance tblAttendance, TblEmployees tblEmployees) {
		TblAttendanceDAO dao = new TblAttendanceDAO();
		boolean isSuccess = dao.deleteDataByEmpl(tblAttendance, tblEmployees);
		return isSuccess;
	}
	
	// 出席簿出力用
	public List<MonthlyAttendance> getMonthlyAttendances(TblEmployees tblEmployees, String yearMonthKey){
		TblAttendanceDAO dao = new TblAttendanceDAO();
		Integer daysOfMonth = getDaysOfMonth(yearMonthKey);
		Integer openDays = getOpenDays(yearMonthKey);
		
		List<MonthlyAttendance> atteList = dao.getMonthlyData(tblEmployees, yearMonthKey, daysOfMonth, openDays);
		return atteList;
	}
	
	public Integer getOpenDays(String yearMonthKey) {
		int year = Integer.parseInt(yearMonthKey.split("-")[0]);
		int month = Integer.parseInt(yearMonthKey.split("-")[1]);
		YearMonth yearMonth = YearMonth.of(year, month);
		int daysOfMonth = yearMonth.lengthOfMonth();
		int weekendAndHolidays = 0;
		
		LocalDate startOfMonth = yearMonth.atDay(1);
		LocalDate endOfMonth = yearMonth.atEndOfMonth();
		for (LocalDate date = startOfMonth; !date.isAfter(endOfMonth); date=date.plusDays(1)) {
			DayOfWeek dayOfWeek = date.getDayOfWeek();
			
			// 土日または祝日を判定
			boolean isWeekend = (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY);
			// TODO: boolean isHoliday = ...
			if(isWeekend) {
				weekendAndHolidays += 1;
			}
		}
		Integer openDays = daysOfMonth - weekendAndHolidays;
		return openDays;
	}
	
	public Integer getDaysOfMonth(String yearMonthKey) {
		int year = Integer.parseInt(yearMonthKey.split("-")[0]);
		int month = Integer.parseInt(yearMonthKey.split("-")[1]);
		YearMonth yearMonth = YearMonth.of(year, month);
		int daysOfMonth = yearMonth.lengthOfMonth();
		return daysOfMonth;
	}

	public String formatJavaDateToHtmlTime(java.util.Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
		String htmlDate = dateFormat.format(date);
		return htmlDate;
	}

	public Date formatHtmlTimeToJavaDate(String htmlTime) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
		try {
			return dateFormat.parse(htmlTime);
		} catch (ParseException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return null;
	}

	public Date formatHtmlDateToJavaDate(String htmlDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return dateFormat.parse(htmlDate);
		} catch (ParseException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return null;
	}

}

//10/29変更なし
