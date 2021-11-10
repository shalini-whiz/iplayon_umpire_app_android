package com.iplayon.umpire.util;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {

	private Context mContext;
	private SharedPreferences mSharedPref;
	private static final String mKEY_SHARED_PREF = "IPlayOn";
	private static final int mKEY_MODE_PRIVATE = 0;
	private static final String mUser = "userName";
	private static final String mUserID = "userID";
	private static final String mURL = "userID";
	private static final String mURLSite = "url";
	private static final String mMatchDate="matchDate";
	private static final String mPlayer1Name = "player1Name";
	private static final String mPlayer2Name = "player2Name";
	private static final String mPlayer1ID = "player1ID";
	private static final String mPlayer2ID = "player2ID";
	private static final String mDestination = "destinationPoint";
	private static final String mUserRole = "role";
	private static final String mUserGender = "gender";
	private static final String mUserDob = "dob";
	private static final String mUserMail = "userMail";
	private String mUserAffId = "affilitionid";


	public SessionManager(Context lContext) {
		this.mContext = lContext;
		mSharedPref = this.mContext.getSharedPreferences(mKEY_SHARED_PREF, mKEY_MODE_PRIVATE);
	}



	public void ypStoreSessionUserName(String lUserName) {
		Editor lEditor = mSharedPref.edit();
		lEditor.putString(mUser, lUserName);
		lEditor.commit();
	}

	public String ypGetUserMail() {
		return mSharedPref.getString(mUserMail, null);
	}


	public void ypStoreSessionUserMail(String lUserMail) {
		Editor lEditor = mSharedPref.edit();
		lEditor.putString(mUserMail, lUserMail);
		lEditor.commit();
	}

	public String ypGetUserName() {
		return mSharedPref.getString(mUser, null);
	}


	public String ypGetUserAffiliationId() {
		return mSharedPref.getString(mUserAffId, null);
	}

	public void ypStoreSessionUserAffiliationId(String lAfId) {
		Editor lEditor = mSharedPref.edit();
		lEditor.putString(mUserAffId, lAfId);
		lEditor.commit();
	}

	public void ypStoreSessionUserGender(String lGender) {
		Editor lEditor = mSharedPref.edit();
		lEditor.putString(mUserGender, lGender);
		lEditor.commit();
	}

	public String ypGetUserGender() {
		return mSharedPref.getString(mUserGender, null);
	}
	
	public void ypStoreSessionUserDOB(String lDob) {
		Editor lEditor = mSharedPref.edit();
		lEditor.putString(mUserDob, lDob);
		lEditor.commit();
	}

	public String ypGetUserDOB() {
		return mSharedPref.getString(mUserDob, null);
	}

	public void ypStoreSessionUserID(String lUserID) {
		Editor lEditor = mSharedPref.edit();
		lEditor.putString(SessionManager.mUserID, lUserID);
		lEditor.commit();
	}

	public String ypGetUserID() {
		return mSharedPref.getString(SessionManager.mUserID, null);
	}
	
	
	public void ypStoreSessionPlayer1Name(String lUserID) {
		Editor lEditor = mSharedPref.edit();
		lEditor.putString(SessionManager.mPlayer1Name, lUserID);
		lEditor.commit();
	}

	public String ypGetPlayer1Name() {
		return mSharedPref.getString(SessionManager.mPlayer1Name, null);
	}
	
	public void ypStoreSessionPlayer2Name(String lUserID) {
		Editor lEditor = mSharedPref.edit();
		lEditor.putString(SessionManager.mPlayer2Name, lUserID);
		lEditor.commit();
	}

	public String ypGetPlayer2Name() {
		return mSharedPref.getString(SessionManager.mPlayer2Name, null);
	}
	
	public void ypStoreSessionPlayer1ID(String lUserID) {
		Editor lEditor = mSharedPref.edit();
		lEditor.putString(SessionManager.mPlayer1ID, lUserID);
		lEditor.commit();
	}

	public String ypGetPlayer1ID() {
		return mSharedPref.getString(SessionManager.mPlayer1ID, null);
	}
	
	public void ypStoreSessionPlayer2ID(String lUserID) {
		Editor lEditor = mSharedPref.edit();
		lEditor.putString(SessionManager.mPlayer2ID, lUserID);
		lEditor.commit();
	}

	public String ypGetPlayer2ID() {
		return mSharedPref.getString(SessionManager.mPlayer2ID, null);
	}
	
	public void ypStoreSessionMatchDate(String lUserID) {
		Editor lEditor = mSharedPref.edit();
		lEditor.putString(SessionManager.mMatchDate, lUserID);
		lEditor.commit();
	}

	public String ypGetMatchDate() {
		return mSharedPref.getString(SessionManager.mMatchDate, null);
	}
	
	public void ypStoreSessionDestinationPoint(String lUserID) {
		Editor lEditor = mSharedPref.edit();
		lEditor.putString(SessionManager.mDestination, lUserID);
		lEditor.commit();
	}

	public String ypGetDestinationPoint() {
		return mSharedPref.getString(SessionManager.mDestination, null);
	}

	public void ypStoreSessionURL(String lUserID) {
		Editor lEditor = mSharedPref.edit();
		lEditor.putString(SessionManager.mURLSite, lUserID);
		lEditor.commit();
	}

	public String ypGetURL() {
		return mSharedPref.getString(SessionManager.mURLSite, null);
	}
	
	
	public void ypStoreUserRole(String lUserID) {
		Editor lEditor = mSharedPref.edit();
		lEditor.putString(SessionManager.mUserRole, lUserID);
		lEditor.commit();
	}

	public String ypGetUserRole() {
		return mSharedPref.getString(SessionManager.mUserRole, null);
	}


	public boolean clearSession() {
		return mSharedPref.edit().clear().commit();
	}

}
