package com.iplayon.umpire.modal;

import java.util.ArrayList;

/**
 * Created by shalinibr on 8/21/17.
 */

public class EventInfo {
    private String eventName;
    private String _id;
    private String eventSubscriptionLastDate;
    private String eventStartDate;
    private String eventEndDate;
    private String prize;
    private String eventSubscriptionLastDate1;
    private String eventStartDate1;
    private String eventEndDate1;
    private ArrayList<String> projectId;
    private String projectType;

    public ArrayList<String> getProjectId() {
        return projectId;
    }

    public void setProjectId(ArrayList<String> projectId) {
        this.projectId = projectId;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getEventSubscriptionLastDate1() {
        return eventSubscriptionLastDate1;
    }

    public void setEventSubscriptionLastDate1(String eventSubscriptionLastDate1) {
        this.eventSubscriptionLastDate1 = eventSubscriptionLastDate1;
    }

    public String getEventStartDate1() {
        return eventStartDate1;
    }

    public void setEventStartDate1(String eventStartDate1) {
        this.eventStartDate1 = eventStartDate1;
    }

    public String getEventEndDate1() {
        return eventEndDate1;
    }

    public void setEventEndDate1(String eventEndDate1) {
        this.eventEndDate1 = eventEndDate1;
    }

    public String getEventEndDate() {
        return eventEndDate;
    }

    public void setEventEndDate(String eventEndDate) {
        this.eventEndDate = eventEndDate;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public String getEventStartDate() {
        return eventStartDate;
    }

    public void setEventStartDate(String eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    public String getEventSubscriptionLastDate() {
        return eventSubscriptionLastDate;
    }

    public void setEventSubscriptionLastDate(String eventSubscriptionLastDate) {
        this.eventSubscriptionLastDate = eventSubscriptionLastDate;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public boolean equals(Object o) {
        if (!(o instanceof EventInfo)) {
            return false;
        }
        EventInfo other = (EventInfo) o;
        if(eventName != null && _id != null)
            return (other._id.equalsIgnoreCase(_id) && other.eventName.equalsIgnoreCase(eventName));
        else if(eventName != null)
            return eventName.equalsIgnoreCase(other.getEventName());
        else if(_id != null)
            return _id.equalsIgnoreCase(other.get_id());

        return false;
    }

    @Override
    public String toString() {
        return this.eventName;
    }
}
