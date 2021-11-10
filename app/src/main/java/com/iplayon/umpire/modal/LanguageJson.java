package com.iplayon.umpire.modal;

public class LanguageJson {
	
	private String _id;
	private String language;
	private Boolean checked = false;
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	public boolean equals(Object o) {
		if (!(o instanceof LanguageJson)) {
		    return false;
		}
		LanguageJson other = (LanguageJson) o;
		return language.equalsIgnoreCase(other.getLanguage());
	}
	 
	@Override
    public String toString() {
        return this.language;
    }
	public int hashCode() {
		  return language.hashCode();
		}

}
