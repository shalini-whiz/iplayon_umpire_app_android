package com.iplayon.umpire.modal;

import java.util.ArrayList;

public class TournamentJson {

    private String _id;
    private String eventName;
    private String subscriptionTypeHyper = "0";
    private String hyperLinkValue = "";
    private String domainName;
    private String eventStartDate;
    private String eventEndDate;
    private String eventSubscriptionLastDate;
    private ArrayList<String> drawEvents;
    private String tournamentType;
    private String subscriptionTypeDirect;
    private String subscriptionTypeMail;

    public String getId() {
        return _id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getSubscriptionTypeHyper() {
        return subscriptionTypeHyper;
    }

    public void setSubscriptionTypeHyper(String subscriptionTypeHyper) {
        this.subscriptionTypeHyper = subscriptionTypeHyper;
    }

    public String getHyperLinkValue() {
        return hyperLinkValue;
    }

    public void setHyperLinkValue(String hyperLinkValue) {
        this.hyperLinkValue = hyperLinkValue;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getEventStartDate() {
        return eventStartDate;
    }

    public void setEventStartDate(String eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    public String getEventEndDate() {
        return eventEndDate;
    }

    public void setEventEndDate(String eventEndDate) {
        this.eventEndDate = eventEndDate;
    }

    public String getEventSubscriptionLastDate() {
        return eventSubscriptionLastDate;
    }

    public void setEventSubscriptionLastDate(String eventSubscriptionLastDate) {
        this.eventSubscriptionLastDate = eventSubscriptionLastDate;
    }

    public ArrayList<String> getDrawEvents() {
        return drawEvents;
    }

    public void setDrawEvents(ArrayList<String> drawEvents) {
        this.drawEvents = drawEvents;
    }

    public String getTournamentType() {
        return tournamentType;
    }

    public void setTournamentType(String tournamentType) {
        this.tournamentType = tournamentType;
    }

    public String getSubscriptionTypeDirect() {
        return subscriptionTypeDirect;
    }

    public void setSubscriptionTypeDirect(String subscriptionTypeDirect) {
        this.subscriptionTypeDirect = subscriptionTypeDirect;
    }

    public String getSubscriptionTypeMail() {
        return subscriptionTypeMail;
    }

    public void setSubscriptionTypeMail(String subscriptionTypeMail) {
        this.subscriptionTypeMail = subscriptionTypeMail;
    }

    public TournamentJson(String _id)
    {
        this._id = _id;

    }


    public boolean equals(Object o) {
        if (!(o instanceof TournamentJson)) {
            return false;
        }
        TournamentJson other = (TournamentJson) o;
        if(_id != null)
            return _id.equalsIgnoreCase(other.get_id());

        return false;


    }
}
