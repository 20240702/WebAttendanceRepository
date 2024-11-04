package model;

import java.io.Serializable;

public class TblParents implements Serializable {
	private String pareId;
	private String parePass;
	private String schoCode;
	private String pareName;
	private String pareAddress;
	private String pareTel;

	public TblParents() {
	}

	public TblParents(String schoCode, String pareId, String pareName, String pareAddress, String pareTel,
			String parePass) {
		this.schoCode = schoCode;
		this.pareId = pareId;
		this.pareName = pareName;
		this.pareAddress = pareAddress;
		this.pareTel = pareTel;
		this.parePass = parePass;
	}

	public String getSchoId() {
		return this.schoCode;
	}

	public String getPareId() {
		return this.pareId;
	}

	public String getPareName() {
		return this.pareName;
	}

	public String getPareAddress() {
		return this.pareAddress;
	}

	public String getPareTel() {
		return this.pareTel;
	}

	public String getParePass() {
		return this.parePass;
	}

}
