package model;

public class MonthlyAttendance {
	private Integer studCode;
	private String studName;
	private String[] atteRecords;
	private Integer sumShusseki;
	private Integer sumByoketsu;
	private Integer sumKaji;
	private Integer sumShuttei;
	private Integer sumKibiki;
	
	public MonthlyAttendance() {}
	public MonthlyAttendance(Integer studCode, String studName, String[] atteRecords) {
		this.studCode = studCode;
		this.studName = studName;
		this.atteRecords = atteRecords;
	}
	
	public void setSumShusseki(Integer sumShusseki) {
		this.sumShusseki = sumShusseki;
	}
	public void setSumByoketsu(Integer sumByoketsu) {
		this.sumByoketsu = sumByoketsu;
	}
	public void setSumKaji(Integer sumKaji) {
		this.sumKaji = sumKaji;
	}
	public void setSumShuttei(Integer sumShuttei) {
		this.sumShuttei = sumShuttei;
	}
	public void setSumKibiki(Integer sumKibiki) {
		this.sumKibiki = sumKibiki;
	}
	public Integer getStudCode() {
		return studCode;
	}
	public String getStudName() {
		return studName;
	}
	public String[] getAtteRecords() {
		return atteRecords;
	}
	public Integer getSumShusseki() {
		return sumShusseki;
	}
	public Integer getSumByoketsu() {
		return sumByoketsu;
	}
	public Integer getSumKaji() {
		return sumKaji;
	}
	public Integer getSumShuttei() {
		return sumShuttei;
	}
	public Integer getSumKibiki() {
		return sumKibiki;
	}
}
