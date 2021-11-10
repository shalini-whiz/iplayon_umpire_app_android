package com.iplayon.umpire.modal;

import java.io.Serializable;


public class DomainJson implements Serializable{
	public String domainName;
	public String _id;
	
	public DomainJson(String domainName)
	{
		this.domainName = domainName;
	}
	public DomainJson(String _id,Boolean temp)
	{
		this._id = _id;
	}
	
	public DomainJson(String domainName,String id) {
		this.domainName = domainName;
		this._id = id;
		// TODO Auto-generated constructor stub
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	public String getUserId() {
		return _id;
	}
	public void setUserId(String id) {
		this._id = id;
	}
	
	public boolean equals(Object o) {
		if (!(o instanceof DomainJson)) {
		    return false;
		}
		DomainJson other = (DomainJson) o;
		if(domainName != null)
			return domainName.equalsIgnoreCase(other.getDomainName());
		else if(_id != null)
			return _id.equalsIgnoreCase(other.getUserId());
		return false;
		
		
		
		
		
	}
	 
	@Override
    public String toString() {
        return this.domainName;
    }
	public int hashCode() {
		  return domainName.hashCode();
		}
}
