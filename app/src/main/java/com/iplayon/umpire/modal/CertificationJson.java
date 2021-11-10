package com.iplayon.umpire.modal;

public class CertificationJson {
	
	public String _id;
	public String sporId;
	public String certification;
	private Boolean checked = false;
	
	public CertificationJson(String _id) {
		this._id = _id;
	}

	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getSporId() {
		return sporId;
	}
	public void setSporId(String sporId) {
		this.sporId = sporId;
	}
	public String getCertification() {
		return certification;
	}
	public void setCertification(String certification) {
		this.certification = certification;
	}
	
	public boolean equals(Object o) {
		if (!(o instanceof CertificationJson)) {
		    return false;
		}
		CertificationJson other = (CertificationJson) o;
		return _id.equalsIgnoreCase(other.get_id());
	}
	 
	@Override
    public String toString() {
        return this.sporId;
    }
	public int hashCode() {
		  return sporId.hashCode();
		}

}
