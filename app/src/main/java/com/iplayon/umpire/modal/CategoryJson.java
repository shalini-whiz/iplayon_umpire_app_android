package com.iplayon.umpire.modal;

import java.io.Serializable;



public class CategoryJson implements Serializable{
	
	
	
	private static final long serialVersionUID = 1L;
	public String projectName;
	public String _id;
	public String abbName;
	public String gender;
	public String dob;
	public Boolean categoryCheck = false;
	public String eventFee;
	public String categoryStartDate;
	public String categoryEndDate;
	public String categoryFilterDate;
	public String categoryRank;
	public String projectType;
	
	
	/* "_id" : "ksHHDWReSe7N2uux7", 
	 "projectName" : "Cadet Boy's Singles",
	 "abbName" : "CB", "projectType" : "1", 
	 "gender" : "Male", "dob" : ISODate("1992-10-08T00:00:00Z"), 
	 "dobType" : "A" */
		 
	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getCategoryRank() {
		return categoryRank;
	}

	public void setCategoryRank(String categoryRank) {
		this.categoryRank = categoryRank;
	}



	public String getEventFee() {
		return eventFee;
	}

	public void setEventFee(String eventFee) {
		this.eventFee = eventFee;
	}

	public Boolean getCategoryCheck() {
		return categoryCheck;
	}

	public void setCategoryCheck(Boolean categoryCheck) {
		this.categoryCheck = categoryCheck;
	}

	public CategoryJson(String projectName)
	{
		this.projectName = projectName;
	}
	
	public CategoryJson(String id,Boolean check)
	{
		this._id = id;
	}
	
	public CategoryJson(String projectName,String id) {
		this.projectName = projectName;
		this._id = id;
	}
	public String getCategoryStartDate() {
		return categoryStartDate;
	}

	public void setCategoryStartDate(String categoryStartDate) {
		this.categoryStartDate = categoryStartDate;
	}

	public String getCategoryEndDate() {
		return categoryEndDate;
	}

	public void setCategoryEndDate(String categoryEndDate) {
		this.categoryEndDate = categoryEndDate;
	}

	public String getCategoryName() {
		return projectName;
	}
	public void setCategoryName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getCategoryGender(){
		return gender;
	}
	public void setCategoryGender(String gender){
		this.gender = gender;
	}
	
	public String getCategoryDate(){
		return dob;
	}
	public void setCategoryDate(String projectDate){
		
		this.dob = projectDate;
	}
	
	public String getUserId() {
		return _id;
	}
	public void setUserId(String id) {
		this._id = id;
	}
	
	public boolean equals(Object o) {
		if (!(o instanceof CategoryJson)) {
		    return false;
		}
		CategoryJson other = (CategoryJson) o;		
		if(projectName != null)
			return projectName.equalsIgnoreCase(other.getCategoryName());
		else if(_id != null)
			return _id.equalsIgnoreCase(other.getUserId());
		return false;
		
		
	}
	 
	@Override
    public String toString() {
        return this.projectName;
    }
	public int hashCode() {
		  return projectName.hashCode();
	}
}
