package com.iplayon.umpire;


import java.lang.reflect.Type;
import java.util.List;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iplayon.umpire.adapter.LanguageAdapter;
import com.iplayon.umpire.modal.CertificationJson;
import com.iplayon.umpire.modal.DomainJson;
import com.iplayon.umpire.modal.LanguageJson;
import com.iplayon.umpire.modal.SportJson;
import com.iplayon.umpire.util.AsyncResponse;
import com.iplayon.umpire.util.Constants;
import com.iplayon.umpire.util.ExecuteURLTask;
import com.iplayon.umpire.util.SessionManager;
import com.iplayon.umpire.util.Util;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Registration1 extends Activity implements AsyncResponse{

	SessionManager mSession;
	Util mUtil;
	ExecuteURLTask asyncTask;
	String requestType = "";
	String params = "";
	String queryparams = "";
	String methodType = "";
	LinearLayout registerLayout;
	LinearLayout registerFormLayout;
	LinearLayout stepLayout;
	LinearLayout registerSubmitLayout;
	EditText registerUserMail;
	Button registerContinue;
	Spinner registerSport;
	Spinner registerState;
	ListView languageSpinner;
	ListView certificationSpinner;
	TextView registerMail;
	EditText registerName;
	EditText registerPhoneNumber;
	EditText registerCity;
	EditText registerPincode;
	EditText registerOtp;
	EditText registerPassword;
	EditText confirmregisterPassword;
	Button userRegister;
	Button step1Continue;
	Button step1Previous;
	String otp = "";
	List<SportJson> sportJsonList;
	List<DomainJson> domainJsonList;
	List<LanguageJson> languageJsonList;
	List<CertificationJson> certificationJsonList;
	ArrayAdapter<SportJson> sportAdapter;
	ArrayAdapter<DomainJson> domainAdapter;
	ArrayAdapter<LanguageJson> languageAdapter;
	ArrayAdapter<CertificationJson> certificationAdapter;
	 LanguageAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_activity1);
		mSession = new SessionManager(getApplicationContext());
		mUtil = new Util(getApplicationContext(), this);
		
		registerLayout = (LinearLayout) findViewById(R.id.registerLayout);
		registerFormLayout = (LinearLayout) findViewById(R.id.registerFormLayout);
		
		registerUserMail = (EditText) findViewById(R.id.registerUserMail);
		registerUserMail.setText("umpire1@gmail.com");
		registerContinue = (Button) findViewById(R.id.registerContinue);
		registerName = (EditText) findViewById(R.id.registerName);
		registerMail = (TextView) findViewById(R.id.registerMail);
		registerPhoneNumber = (EditText) findViewById(R.id.registerPhoneNumber);
		
		registerSport = (Spinner) findViewById(R.id.registerSport);
		registerState = (Spinner) findViewById(R.id.registerState);
		registerCity = (EditText) findViewById(R.id.registerCity);
		registerPincode = (EditText) findViewById(R.id.registerPincode);
		//registerOtp = (EditText) findViewById(R.id.registerOtp);
		////registerPassword = (EditText) findViewById(R.id.registerPassword);
		//confirmregisterPassword = (EditText) findViewById(R.id.confirmregisterPassword);
		userRegister = (Button) findViewById(R.id.userRegister);
		mSession.ypStoreSessionURL(Constants.urlSite);
		
		registerContinue.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View arg0) {
				
				if(android.util.Patterns.EMAIL_ADDRESS.matcher(registerUserMail.getText().toString()).matches())
				{					
					
					registerMail.setText(registerUserMail.getText().toString());
					if(mUtil.isOnline())
					{
						params= "registerOtp?caller="+Constants.caller+"&apiKey="+Constants.apiKey+"&emailId="+registerUserMail.getText().toString();					    							
						requestType = "GET";
			    		methodType = "registerOtp";
			    		asyncTask = new ExecuteURLTask(getApplicationContext(),"");
						asyncTask.delegate = Registration1.this;
						asyncTask.execute(params,queryparams,requestType);	
					}
					else
					{
						toastMessage("No Internet");
					}
				}
				else
				{
					toastMessage("Invalid Email ID");
				}			
			}
		});
		
		userRegister.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				
				if(registerName.getText().length() > 0)
				{
   
					    
					LayoutInflater inflater = getLayoutInflater();
					final View viewSequenceStrokeLayout = inflater.inflate(R.layout.password_layout, null);

					registerOtp = (EditText) viewSequenceStrokeLayout.findViewById(R.id.registerOtp);
					registerPassword = (EditText) viewSequenceStrokeLayout.findViewById(R.id.registerPassword);
					confirmregisterPassword = (EditText) viewSequenceStrokeLayout.findViewById(R.id.confirmregisterPassword);

					AlertDialog.Builder passwordCheckDialog = new AlertDialog.Builder(Registration1.this);

					passwordCheckDialog.setTitle("Enter password and otp");
					passwordCheckDialog.setView(viewSequenceStrokeLayout);
					passwordCheckDialog.setCancelable(false);
					passwordCheckDialog.setPositiveButton("Ok", null);
					passwordCheckDialog.setNegativeButton("Cancel", null);

					final AlertDialog mAlertDialog = passwordCheckDialog.create();
					
					mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

				        public void onShow(DialogInterface dialog) {

				            Button positiveButton = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
				            Button cancelButton = mAlertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

				            cancelButton.setOnClickListener(new View.OnClickListener() {
								
								
								public void onClick(View v) {
									// TODO Auto-generated method stub
									mAlertDialog.dismiss();
									
								}
							});
				            positiveButton.setOnClickListener(new View.OnClickListener() {

				                public void onClick(View view) {
				                	if(registerOtp.getText().length() > 0)
									{
										if(registerOtp.getText().toString().equalsIgnoreCase(otp))
										{
											if(registerPassword.getText().length() > 0)
											{
												if(registerPassword.getText().toString().equalsIgnoreCase(confirmregisterPassword.getText().toString()))
												{
													if(mUtil.isOnline())
													{
														mAlertDialog.dismiss();
														
														int sportIndex = sportJsonList.indexOf(new SportJson(registerSport.getSelectedItem().toString()));
														int stateIndex = domainJsonList.indexOf(new DomainJson(registerState.getSelectedItem().toString()));
														
														
														JSONObject param = new JSONObject();
														param.put("userName", registerName.getText().toString());
														param.put("emailAddress", registerMail.getText().toString());
														param.put("phoneNumber", registerPhoneNumber.getText().toString());
														param.put("interestedProjectName",sportJsonList.get(sportIndex).getUserId());
														param.put("role", "Umpire");
														param.put("city", registerCity.getText().toString());
														param.put("pinCode", registerPincode.getText().toString());
														param.put("state", domainJsonList.get(stateIndex).getUserId());
														param.put("password", registerPassword.getText().toString());

			
														params= "umpireRegisterViaApp?";	
														queryparams= "caller="+Constants.caller+"&apiKey="+Constants.apiKey+"&data="+param;					    							
														requestType = "POST";
											    		methodType = "umpireRegisterViaApp";
											    		asyncTask = new ExecuteURLTask(getApplicationContext(),"");
														asyncTask.delegate = Registration1.this;
														asyncTask.execute(params,queryparams,requestType);
														userRegister.setEnabled(false);
													}
													else
														toastMessage("No Internet");																				
												}
												else							
													toastMessage("Please reconfirm password");								
											}
											else						
												toastMessage("Please enter password");						
										}
										else					
											toastMessage("Invalid verification code");										
									}
									else				
										toastMessage("Please enter verification code");	
				                }
				            });
				        }
				    });
					
					

					
				    mAlertDialog.show();

				}
				else
				{
					toastMessage("Please enter name");

				}
				
				
										
				
				
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.registeration, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	public void toastMessage(String message)
	{
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}
	@Override
	public void processFinish(String result, String methodName) {
		// TODO Auto-generated method stub
		
		if(methodType.equalsIgnoreCase("registerOtp"))
		{
			JSONParser jsonParser = new JSONParser();
			try {
				JSONObject obj = (JSONObject) jsonParser.parse(result);
				if(obj.containsKey("registerStatus"))
				{
					if(obj.get("registerStatus").toString().equalsIgnoreCase("New register"))
					{		
						if(obj.get("message").toString().equalsIgnoreCase("Verification code sent to"+obj.get("mailID").toString()))
						{
							toastMessage("Code need to be done");
							if(obj.containsKey("verificationCode"))
								otp = obj.get("verificationCode").toString();
							if(obj.get("sportList") != null && obj.get("sportList").toString().length() > 0)
							{
								Gson gson1 = new Gson();
								Type listType1 = new TypeToken<List<SportJson>>(){}.getType();
								sportJsonList = gson1.fromJson(obj.get("sportList").toString(), listType1);	
								System.out.println("sportJsonList .. "+sportJsonList.size());
								sportAdapter = new ArrayAdapter<SportJson>(getApplicationContext(),
										R.layout.header_spinner, sportJsonList);
								sportAdapter.notifyDataSetChanged();
								registerSport.setAdapter(sportAdapter);

							}
							if(obj.get("sportList") != null && obj.get("sportList").toString().length() > 0)
							{
								Gson gson1 = new Gson();
								Type listType1 = new TypeToken<List<SportJson>>(){}.getType();
								sportJsonList = gson1.fromJson(obj.get("sportList").toString(), listType1);	
								System.out.println("sportJsonList .. "+sportJsonList.size());
								sportAdapter = new ArrayAdapter<SportJson>(getApplicationContext(),
										R.layout.header_spinner, sportJsonList);
								sportAdapter.notifyDataSetChanged();
								registerSport.setAdapter(sportAdapter);

							}
							System.out.println("domanin response .. "+obj.get("domainList").toString());

							if(obj.get("domainList") != null && obj.get("domainList").toString().length() > 0)
							{
								Gson gson1 = new Gson();
								Type listType1 = new TypeToken<List<DomainJson>>(){}.getType();
								domainJsonList = gson1.fromJson(obj.get("domainList").toString(), listType1);	
								System.out.println("domainJsonList .. "+domainJsonList.size());
								domainAdapter = new ArrayAdapter<DomainJson>(getApplicationContext(),
										R.layout.header_spinner, domainJsonList);
								domainAdapter.notifyDataSetChanged();
								registerState.setAdapter(domainAdapter);
								
							}
							
							
							registerLayout.setVisibility(View.GONE);
							registerFormLayout.setVisibility(View.VISIBLE);		
						}
						else if(obj.get("message").toString().equalsIgnoreCase("Could not send an email!! Please try again"))						
							toastMessage(obj.get("message").toString());								
						else					
							toastMessage("Could not send an email!! Please try again");											
					}
					else 					
						toastMessage("You are already registered.Please login.");
				}
			}catch(Exception e)
			{
				System.out.println("register exception .. "+e.getMessage());
			}
		}
		if(methodType.equalsIgnoreCase("umpireRegisterViaApp"))
		{
			if(result != null)
			{
				JSONParser jsonParser = new JSONParser();
				try {
					JSONObject obj = (JSONObject) jsonParser.parse(result);
					if(obj.containsKey("message"))
					{
						if(obj.get("message").toString().equalsIgnoreCase("Registered"))
						{		
							toastMessage("Successfully Registered!!Please login");
							Intent lIntent = new Intent(getApplicationContext(), LoginActivity1.class);
							lIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
							startActivity(lIntent);
						}
						else 					
							toastMessage("Could not register!! Please try again");											
					}
				}catch(Exception e)
				{
					
				}
			}
			else
			{
				userRegister.setEnabled(true);
			}
			
		}	
	}


}
