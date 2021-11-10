package com.iplayon.umpire.modal;

import java.io.Serializable;
import java.util.List;


public class SportJson implements Serializable{
	public String projectMainName;
	public String _id;
	public List<CategoryJson> projectSubName;
	private Boolean checked = false;

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public SportJson(String projectMainName)
	{
		this.projectMainName = projectMainName;
	}
	
	public SportJson(String projectMainName,String id,List<CategoryJson> projectSubName) {
		System.out.println("sport json ... "+projectMainName+"  .... "+id+" ... "+projectSubName);
		this.projectMainName = projectMainName;
		this._id = id;
		this.projectSubName = projectSubName;
	}
	public String getProjectName() {
		return projectMainName;
	}
	public void setSportName(String projectMainName) {
		this.projectMainName = projectMainName;
	}
	public List<CategoryJson> getEventList(){
		return projectSubName;
	}
	public void setEventList(List<CategoryJson> projectSubName){
		this.projectSubName = projectSubName;
	}
	public String getUserId() {
		return _id;
	}
	public void setUserId(String id) {
		this._id = id;
	}
	
	public boolean equals(Object o) {
		if (!(o instanceof SportJson)) {
		    return false;
		}
		SportJson other = (SportJson) o;
		if(projectMainName != null)
			return projectMainName.equalsIgnoreCase(other.getProjectName());
		else if(_id != null)
			return _id.equalsIgnoreCase(other.getUserId());
		return false;
		
		
	}
	 
	@Override
    public String toString() {
        return this.projectMainName;
    }
	public int hashCode() {
		  return projectMainName.hashCode();
		}
}
