package model;

import java.io.Serializable;
import java.util.Date;

public class TblAttendance implements Serializable {
	private String schoCode;
	private String beloGrade;
	private String beloClass;
	private String studCode;
	private String studName;
	private Date atteRecord;
	private String atteRecordHtml;
	private String atteStatus;
	private String atteInf;
	private Date atteArriTime;
	private String atteAttiTimeHtml;
	private Date atteDepaTime;
	private String atteDepaTimeHtml;
	private String atteRemarks;

	
	public TblAttendance(String schoCode, String beloGrade, String beloClass, String studCode, String studName,
			Date atteRecoad, String atteStatus, String atteInf, Date atteArriTime, Date atteDepaTime,
			String atteRemarks) {
		this.schoCode = schoCode;
		this.beloClass = beloClass;
		this.beloGrade = beloGrade;
		this.studCode = studCode;
		this.studName = studName;
		this.atteRecord = atteRecoad;
		this.atteStatus = atteStatus;
		this.atteInf = atteInf;
		this.atteArriTime = atteArriTime;
		this.atteDepaTime = atteDepaTime;
		this.atteRemarks = atteRemarks;
	}
	
	
	public void setAtteStatus(String atteStatus) {
		this.atteStatus = atteStatus;
	}
	public void setAtteInf(String atteInf) {
		this.atteInf = atteInf;
	}
	public void setAtteArriTime(Date atteArriTime) {
		this.atteArriTime = atteArriTime;
	}
	public void setAtteDepaTime(Date atteDepaTime) {
		this.atteDepaTime = atteDepaTime;
	}
	public void setAtteRemarks(String atteRemark) {
		this.atteRemarks = atteRemark;
	}
	public void setAtteArriTimeHtml(String atteArriTimeHtml) {
		this.atteAttiTimeHtml = atteArriTimeHtml;
	}
	public void setAtteDepaTimeHtml(String atteDepaTimeHtml) {
		this.atteDepaTimeHtml = atteDepaTimeHtml;
	}
	public void setAtteRecordHtml(String atteRecordHtml) {
		this.atteRecordHtml = atteRecordHtml;
	}

	public String getSchoCode() {
		return schoCode;
	}
	public String getBeloGrade() {
		return beloGrade;
	}

	public String getBeloClass() {
		return beloClass;
	}

	public String getStudCode() {
		return studCode;
	}

	public String getStudName() {
		return studName;
	}

	public Date getAtteRecord() {
		return atteRecord;
	}
	
	public String getAtteRecordHtml() {
		return atteRecordHtml;
	}
	
	public String getAtteStatus() {
		return atteStatus;
	}

	public String getAtteInf() {
		return atteInf;
	}

	public Date getAtteArriTime() {
		return atteArriTime;
	}
	
	public String getAtteArriTimeHtml() {
		return atteAttiTimeHtml;
	}


	public Date getAtteDepaTime() {
		return atteDepaTime;
	}
	
	public String getAtteDepaTimeHtml() {
		return atteDepaTimeHtml;
	}


	public String getAtteRemarks() {
		return atteRemarks;
	}
	
	
}
