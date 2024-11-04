package model;

import java.io.Serializable;

public class TblEmployees implements Serializable {
	private String schoCode;
	private String beloGrade;
	private String beloClass;
	private String emplId;
	private String emplJob;
	private String emplName;
	private String emplAddress;
	private String emplTel;
	private String emplPass;

	public TblEmployees() {
	}

	public TblEmployees(String schoCode, String beloGrade, String beloClass, String emplId, String emplJob,
			String emplName, String emplAddress, String emplTel, String emplPass) {
		this.schoCode = schoCode;
		this.beloGrade = beloGrade;
		this.beloClass = beloClass;
		this.emplId = emplId;
		this.emplJob = emplJob;
		this.emplName =emplName;
		this.emplAddress = emplAddress;
		this.emplTel =emplTel;
		this.emplPass =emplPass;
	}

	public String getSchoId() {
		return this.schoCode;
	}

	public String getBeloGrade() {
		return this.beloGrade;
	}

	public String getBeloClass() {
		return this.beloClass;
	}

	public String getEmplId() {
		return this.emplId;
	}

	public String getEmplJob() {
		return this.emplJob;
	}

	public String getEmplName() {
		return this.emplName;
	}

	public String getEmplAddress() {
		return this.emplAddress;
	}

	public String getEmplTel() {
		return this.emplTel;
	}

	public String getEmplPass() {
		return this.emplPass;
	}
}
