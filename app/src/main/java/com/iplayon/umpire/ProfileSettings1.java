package com.iplayon.umpire;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iplayon.umpire.adapter.CertificationAdapter;
import com.iplayon.umpire.adapter.LanguageAdapter;
import com.iplayon.umpire.adapter.SportAdapter;
import com.iplayon.umpire.modal.CertificationJson;
import com.iplayon.umpire.modal.DomainJson;
import com.iplayon.umpire.modal.LanguageJson;
import com.iplayon.umpire.modal.SportJson;
import com.iplayon.umpire.util.AsyncResponse;
import com.iplayon.umpire.util.Constants;
import com.iplayon.umpire.util.ExecuteURLTask;
import com.iplayon.umpire.util.SessionManager;
import com.iplayon.umpire.util.Util;
import com.iplayon.umpire.util.Validation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileSettings1 extends Activity implements AsyncResponse{

	TextView mTitleHeaderUI;
	TextView mBackMenuUI;
	TextView mHomeMenuUI;
	
	LinearLayout step1Layout;
	LinearLayout step2Layout;
	EditText profileName;
	EditText profileGuardian;
	TextView profileMail;
	EditText profilePhoneNo;
	EditText profileDOB;
	EditText profileCity;
	Spinner profileState;
	EditText profilePinCode;
	
	Button profileContinue;
	Button step1Previous;
	Button profileCancel;
	Button profileUpdate;
	ListView profileSport;
	ListView profileCertification;
	ListView profileLanguage;
	CheckBox travelAssignment;
	List<DomainJson> domainJsonList;
	ArrayAdapter<DomainJson> domainAdapter;
	List<SportJson> sportJsonList;
	List<LanguageJson> languageJsonList;
	LanguageAdapter languageAdapter;
	List<CertificationJson> certificationJsonList;
	CertificationAdapter certificationAdapter;
	SportAdapter sportAdapter;
	List<String> checkedLanguage;
	List<String> checkedCertification;
	List<String> checkedSport;

	
	Util mUtil;
	SessionManager mSession;
	JSONObject jsonObject;
	
	ExecuteURLTask asyncTask;
	String requestType = "";
	String params = "";
	String queryparams = "";
	String methodType = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_settings);
		mUtil = new Util(getApplicationContext(), this);
		mSession = new SessionManager(getApplicationContext());
		//title bar
		mTitleHeaderUI = (TextView) findViewById(R.id.title);
		mTitleHeaderUI.setText("");
		mBackMenuUI = (TextView) findViewById(R.id.home_chats);
		mHomeMenuUI = (TextView) findViewById(R.id.home);
				
		mBackMenuUI.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {		
				
				onBackPressed();	
			}
		});
				
		mHomeMenuUI.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {		
				Intent lIntent = new Intent(getApplicationContext(), HomeActivity.class);
				lIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(lIntent);		
				}
		});
				
		String report = getIntent().getExtras().getString("report");
		String response = getIntent().getExtras().getString("response");
		checkedLanguage = new ArrayList<String>();	
		checkedCertification = new ArrayList<String>();	
		checkedSport = new ArrayList<String>();
		step1Layout = (LinearLayout) findViewById(R.id.step1Layout);
		step2Layout = (LinearLayout) findViewById(R.id.step2Layout);

		profileName = (EditText) findViewById(R.id.profileName);
		profileMail = (TextView) findViewById(R.id.profileMail);
		profilePhoneNo = (EditText) findViewById(R.id.profilePhoneNo);
		profileCity = (EditText) findViewById(R.id.profileCity);
		profileState = (Spinner) findViewById(R.id.profileState);
		profilePinCode = (EditText) findViewById(R.id.profilePinCode);
		profileGuardian = (EditText) findViewById(R.id.profileGuardian);
		profileDOB = (EditText) findViewById(R.id.profileDOB);
		profileContinue = (Button) findViewById(R.id.profileContinue);
		profileCancel = (Button) findViewById(R.id.profileCancel);
		profileUpdate = (Button) findViewById(R.id.profileUpdate);
		
		step1Previous =(Button) findViewById(R.id.step1Previous);
		profileSport = (ListView) findViewById(R.id.profileSport);
		profileCertification = (ListView) findViewById(R.id.profileCertification);
		profileLanguage = (ListView) findViewById(R.id.profileLanguage);
		travelAssignment = (CheckBox) findViewById(R.id.travelAssignment);
		if(response != null)
		{
			JSONParser parser = new JSONParser();
			try {
				 jsonObject = (JSONObject) parser.parse(response);
				 System.out.println("profile response .. "+jsonObject.toJSONString());
				if(jsonObject.containsKey("userName"))
					profileName.setText(jsonObject.get("userName").toString());
				if(jsonObject.containsKey("guardianName"))
					profileGuardian.setText(jsonObject.get("guardianName").toString());
 
				 
				if(jsonObject.containsKey("dateOfBirth"))
				{
					DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",Locale.ENGLISH);				
					formatter.setTimeZone(TimeZone.getTimeZone("IST"));
					Date result1 = null;
					try {
							result1 = formatter.parse(jsonObject.get("dateOfBirth").toString());
							profileDOB.setText(new SimpleDateFormat("dd MMM yyyy").format(result1));

					} catch (java.text.ParseException e) {
						e.printStackTrace();
					}

				}
				
				if(jsonObject.containsKey("emailAddress"))
					profileMail.setText(jsonObject.get("emailAddress").toString());
				if(jsonObject.containsKey("phoneNumber"))
					profilePhoneNo.setText(jsonObject.get("phoneNumber").toString());
				if(jsonObject.containsKey("city"))
					profileCity.setText(jsonObject.get("city").toString());
				if(jsonObject.containsKey("pinCode"))
					profilePinCode.setText(jsonObject.get("pinCode").toString());
				
				
				
				if(jsonObject.get("domainList") != null && jsonObject.get("domainList").toString().length() > 0)
				{
					Gson gson1 = new Gson();
					Type listType1 = new TypeToken<List<DomainJson>>(){}.getType();
					domainJsonList = gson1.fromJson(jsonObject.get("domainList").toString(), listType1);	
					System.out.println("domainJsonList .. "+domainJsonList.size());
					domainAdapter = new ArrayAdapter<DomainJson>(getApplicationContext(),
							R.layout.header_spinner, domainJsonList);
					domainAdapter.notifyDataSetChanged();
					profileState.setAdapter(domainAdapter);

					if(jsonObject.containsKey("state"))
					{
						int spinnerPosition = domainJsonList.indexOf(new DomainJson(jsonObject.get("state").toString(),true));
						profileState.setSelection(spinnerPosition);
					}	
				}
				
				if(jsonObject.get("sportList") != null && jsonObject.get("sportList").toString().length() > 0)
				{
					Gson gson1 = new Gson();
					Type listType1 = new TypeToken<List<SportJson>>(){}.getType();
					sportJsonList = gson1.fromJson(jsonObject.get("sportList").toString(), listType1);	
					System.out.println("sportJsonList .. "+sportJsonList.size()+" ... "+jsonObject.get("interestedProjectName"));
					ArrayList<String> selectedProject;
					if(jsonObject.containsKey("interestedProjectName"))					
						selectedProject = (ArrayList<String>) jsonObject.get("interestedProjectName");
					else
						selectedProject = new ArrayList<String>();
					sportAdapter = new SportAdapter(getApplicationContext(), ProfileSettings1.this,sportJsonList,selectedProject);

					
					sportAdapter.notifyDataSetChanged();
					profileSport.setAdapter(sportAdapter);
					
					


				}
				
				if(jsonObject.get("languageList") != null && jsonObject.get("languageList").toString().length() > 0)
				{
					Gson gson1 = new Gson();
					Type listType1 = new TypeToken<List<LanguageJson>>(){}.getType();
					languageJsonList = gson1.fromJson(jsonObject.get("languageList").toString(), listType1);	
					System.out.println("languageJsonList .. "+languageJsonList.size());	
					ArrayList<String> selectedProject;
					if(jsonObject.containsKey("languages"))					
						selectedProject = (ArrayList<String>) jsonObject.get("languages");
					else
						selectedProject = new ArrayList<String>();
					
					languageAdapter = new LanguageAdapter(getApplicationContext(), ProfileSettings1.this,languageJsonList,selectedProject);
					languageAdapter.notifyDataSetChanged();

					profileLanguage.setAdapter(languageAdapter);


				}
				if(jsonObject.get("certificationList") != null && jsonObject.get("certificationList").toString().length() > 0)
				{
					Gson gson1 = new Gson();
					Type listType1 = new TypeToken<List<CertificationJson>>(){}.getType();
					certificationJsonList = gson1.fromJson(jsonObject.get("certificationList").toString(), listType1);	
					System.out.println("certificationList .. "+certificationJsonList.size());
					ArrayList<String> selectedProject;
					if(jsonObject.containsKey("certifications"))					
						selectedProject = (ArrayList<String>) jsonObject.get("certifications");
					else
						selectedProject = new ArrayList<String>();
					certificationAdapter = new CertificationAdapter(getApplicationContext(), ProfileSettings1.this,certificationJsonList,selectedProject);
					certificationAdapter.notifyDataSetChanged();
					profileCertification.setAdapter(certificationAdapter);
				}

				System.out.println("travelAssignment .. "+jsonObject.get("travelAssignment"));
				if(jsonObject.containsKey("travelAssignment"))
				{
					if(jsonObject.get("travelAssignment").equals(true))
						travelAssignment.setChecked(true);
				}
				
				
				
			
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
		}
		
		profileName.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.hasText(profileName);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
		
		
		profilePhoneNo.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            	if(profilePhoneNo.getText().toString().length() > 0)
            		Validation.isPhoneNumber(profilePhoneNo, false);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
		
		profileDOB.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.isDate(profileDOB, false);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
		
		profileContinue.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 if ( checkValidation () )
				 {
					 step1Layout.setVisibility(View.GONE);
					 step2Layout.setVisibility(View.VISIBLE);
				 }
	             else
	             {
	            	 toastMessage("Form contains error");
	             }
				
				
				
			}
		});
		
		step1Previous.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				step1Layout.setVisibility(View.VISIBLE);
				step2Layout.setVisibility(View.GONE);				
			}
		});
		
		profileCancel.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent lIntent = new Intent(getApplicationContext(), HomeActivity.class);
				lIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(lIntent);	
			}
		});
		
		profileUpdate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				JSONObject param = new JSONObject();
				
				int stateIndex = domainJsonList.indexOf(new DomainJson(profileState.getSelectedItem().toString()));
				
				
				param.put("userId", mSession.ypGetUserID());
				param.put("userName", profileName.getText().toString());
				param.put("guardianName", profileGuardian.getText().toString());
				param.put("phoneNumber", profilePhoneNo.getText().toString());
				param.put("dateOfBirth", profileDOB.getText().toString());
				param.put("city", profileCity.getText().toString());
				param.put("state", domainJsonList.get(stateIndex).getUserId());
				param.put("pinCode", profilePinCode.getText().toString());
				param.put("languages",checkedLanguage );
				param.put("interestedProjectName",checkedSport );
				param.put("certifications",checkedCertification);
				if(travelAssignment.isChecked())
					param.put("travelAssignment",true);
				else
					param.put("travelAssignment",false);


				
				ArrayList<String> selectedProject;
				if(jsonObject.containsKey("interestedDomainName"))					
					selectedProject = (ArrayList<String>) jsonObject.get("interestedDomainName");
				else
				{
					selectedProject = new ArrayList<String>();
					selectedProject.add("");
				}
				
				param.put("interestedDomainName", selectedProject);
				
				

				System.out.println("params .. "+param.toJSONString());
				params= "profileUpdateViaApp?";	
				queryparams= "caller="+Constants.caller+"&apiKey="+Constants.apiKey+"&data="+param;					    							
				requestType = "POST";
	    		methodType = "profileUpdateViaApp";
	    		asyncTask = new ExecuteURLTask(getApplicationContext(),"");
				asyncTask.delegate = ProfileSettings1.this;
				asyncTask.execute(params,queryparams,requestType);

				// TODO Auto-generated method stub
				
			}
		});
		
		if(languageAdapter != null)
		{
			
			languageAdapter.setOnDataChangeListener(new LanguageAdapter.OnDataChangeListener() {
				
				public void onDataLanguage(ArrayList<String> lMessageIDList) {
					// TODO Auto-generated method stub
					checkedLanguage = lMessageIDList;
					
				}
			});
		}
		
		if(certificationAdapter != null)
		{
			certificationAdapter.setOnDataChangeListener(new CertificationAdapter.OnDataChangeListener() {
				
				public void onDataCertification(ArrayList<String> lMessageIDList) {
					// TODO Auto-generated method stub
					checkedCertification = lMessageIDList;	
				}
			});
		}
		
		if(sportAdapter != null)
		{
			sportAdapter.setOnDataChangeListener(new SportAdapter.OnDataChangeListener() {
				
				public void onDataSport(ArrayList<String> lMessageIDList) {
					// TODO Auto-generated method stub
					checkedSport = lMessageIDList;
					
				}
			});
		}
		


		
	}

	protected boolean checkValidation() {
		boolean ret = true;
		 
        if (!Validation.hasText(profileName)) ret = false;
    	if(profilePhoneNo.getText().toString().length() > 0)	
    	{
            if (!Validation.isPhoneNumber(profilePhoneNo, false)) ret = false;
    	}
        if (!Validation.isDate(profileDOB, false)) ret = false;

        return ret;
	}
	public void toastMessage(String message)
	{
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.profile_settings, menu);
		return true;
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int id = item.getItemId();
		if(item.getItemId() == R.id.logout)
		{
			mSession.clearSession();
			mUtil.ypIntentGenericView("MainActivity");
		}
		return super.onOptionsItemSelected(item);
		
		
	}
	@Override
    public void onBackPressed() {
        super.onBackPressed();   
        //    finish();

    }

	public void processFinish(String result, String methodName) {
		// TODO Auto-generated method stub
		if(result != null)
		{
			if(methodType.equalsIgnoreCase("profileUpdateViaApp"))
			{
				JSONParser parser = new JSONParser();
				JSONObject resultObj;
				try {
					resultObj = (JSONObject) parser.parse(result);
					if(resultObj.containsKey("status"))
					{
						if(resultObj.get("status").toString().equalsIgnoreCase("success"))
						{
							if(resultObj.containsKey("response"))
								toastMessage(resultObj.get("response").toString());
							onBackPressed();
							
						}
						else
						{	
							if(resultObj.containsKey("response"))
								toastMessage(resultObj.get("response").toString());
						}
					}
					
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
			}
		}
		
	}


}
