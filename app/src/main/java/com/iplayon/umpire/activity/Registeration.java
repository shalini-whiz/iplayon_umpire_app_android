package com.iplayon.umpire.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iplayon.umpire.LoginActivity1;
import com.iplayon.umpire.R;
import com.iplayon.umpire.Registration1;
import com.iplayon.umpire.modal.DomainJson;
import com.iplayon.umpire.modal.SportJson;
import com.iplayon.umpire.util.AsyncResponse;
import com.iplayon.umpire.util.Constants;
import com.iplayon.umpire.util.ExecuteURLTask;
import com.iplayon.umpire.util.SessionManager;
import com.iplayon.umpire.util.Util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.lang.reflect.Type;
import java.util.List;


public class Registeration extends Activity implements AsyncResponse {

	SessionManager mSession;
	Util mUtil;
	ExecuteURLTask asyncTask;
	String requestType = "";
	String params = "";
	String queryparams = "";
	String methodType = "";
	LinearLayout registerLayout;
	LinearLayout registerFormLayout;
	EditText registerUserMail;
	Button registerContinue;
	TextView registerMail;
	EditText registerName;
	EditText registerOtp;
	EditText registerPassword;
	EditText confirmregisterPassword;
	Button userRegister;
	String otp = "";
	ProgressBar progressBar;
	LinearLayout progressBarLayout;
	TextView verficationMessage;
	EditText registerCity;
	EditText registerPincode;
	Spinner registerSport;
	Spinner registerState;
	EditText registerPhoneNumber;

	List<SportJson> sportJsonList;
	List<DomainJson> domainJsonList;
	ArrayAdapter<SportJson> sportAdapter;
	ArrayAdapter<DomainJson> domainAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
		setContentView(R.layout.register_activity);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
		}

		mSession = new SessionManager(getApplicationContext());
		mUtil = new Util(getApplicationContext(), this);
		mUtil.setStatusBarTransparent(this);


		progressBar = new ProgressBar(Registeration.this);
		progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.progressBarColor), PorterDuff.Mode.MULTIPLY);

		progressBarLayout = new LinearLayout(Registeration.this);
		progressBarLayout.setGravity(Gravity.CENTER);
	    addContentView(progressBarLayout,new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	    progressBarLayout.removeAllViews();
	    progressBarLayout.addView(progressBar);
	    progressBar.setVisibility(View.GONE);

		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

			Drawable drawableProgress = DrawableCompat.wrap(progressBar.getIndeterminateDrawable());
			DrawableCompat.setTint(drawableProgress, ContextCompat.getColor(getApplicationContext(), R.color.progressBarColor));
			progressBar.setIndeterminateDrawable(DrawableCompat.unwrap(drawableProgress));

		} else {
			progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.progressBarColor), PorterDuff.Mode.SRC_IN);
		}

	    
		
		registerLayout = (LinearLayout) findViewById(R.id.registerLayout);
		registerFormLayout = (LinearLayout) findViewById(R.id.registerFormLayout);
		registerUserMail = (EditText) findViewById(R.id.registerUserMail);
		registerContinue = (Button) findViewById(R.id.registerContinue);
		registerName = (EditText) findViewById(R.id.registerName);
		registerPhoneNumber = (EditText) findViewById(R.id.registerPhoneNumber);

		registerSport = (Spinner) findViewById(R.id.registerSport);
		registerState = (Spinner) findViewById(R.id.registerState);
		registerCity = (EditText) findViewById(R.id.registerCity);
		registerPincode = (EditText) findViewById(R.id.registerPincode);


		registerOtp = (EditText) findViewById(R.id.registerOtp);
		registerPassword = (EditText) findViewById(R.id.registerPassword);
		confirmregisterPassword = (EditText) findViewById(R.id.confirmregisterPassword);
		userRegister = (Button) findViewById(R.id.userRegister);
		registerMail = (TextView) findViewById(R.id.registerMail);

		registerOtp = (EditText) findViewById(R.id.registerOtp);
		registerPassword = (EditText) findViewById(R.id.registerPassword);
		confirmregisterPassword = (EditText) findViewById(R.id.confirmregisterPassword);


		mSession.ypStoreSessionURL(Constants.urlSite);

		verficationMessage = (TextView) findViewById(R.id.verficationMessage);

		if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT)
		{
			registerOtp.getBackground().mutate().setColorFilter(ContextCompat.getColor(getApplicationContext(),android.R.color.white), PorterDuff.Mode.SRC_ATOP);
            registerPassword.getBackground().mutate().setColorFilter(ContextCompat.getColor(getApplicationContext(),android.R.color.white), PorterDuff.Mode.SRC_ATOP);
            confirmregisterPassword.getBackground().mutate().setColorFilter(ContextCompat.getColor(getApplicationContext(),android.R.color.white), PorterDuff.Mode.SRC_ATOP);
			registerUserMail.getBackground().mutate().setColorFilter(ContextCompat.getColor(getApplicationContext(),android.R.color.white), PorterDuff.Mode.SRC_ATOP);
			registerName.getBackground().mutate().setColorFilter(ContextCompat.getColor(getApplicationContext(),android.R.color.white), PorterDuff.Mode.SRC_ATOP);
		}


		registerUserMail.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String result = s.toString().replaceAll(" ", "");
			    if (!s.toString().equals(result)) {
			    	registerUserMail.setText(result);
			    	registerUserMail.setSelection(result.length());
			    }
			   
			    
				
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
				
			}
			
		});

		registerContinue.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				
				String temp = registerUserMail.getText().toString().trim();
				registerUserMail.setText(temp);
				if(android.util.Patterns.EMAIL_ADDRESS.matcher(registerUserMail.getText().toString()).matches())
				{					
					
					registerMail.setText(registerUserMail.getText().toString());
					if(mUtil.isOnline())
					{
						verficationMessage.setText("Sending verification code to "+registerMail.getText().toString().trim());
						verficationMessage.setVisibility(View.VISIBLE);
					    progressBar.setVisibility(View.VISIBLE);

						params= "registerOtp?caller="+Constants.caller+"&apiKey="+Constants.apiKey+"&emailId="+registerUserMail.getText().toString().trim();					    							
						requestType = "GET";
			    		methodType = "registerOtp";
			    		asyncTask = new ExecuteURLTask(getApplicationContext(),methodType);
						asyncTask.delegate = Registeration.this;
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
			@Override
			public void onClick(View arg0) {
				if(registerName.getText().length() > 0)
				{
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
										asyncTask = new ExecuteURLTask(getApplicationContext(),"umpireRegisterViaApp");
										asyncTask.delegate = Registeration.this;
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
				else
				{
					toastMessage("Please enter name");

				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		return super.onOptionsItemSelected(item);
	}
	
	
	public void toastMessage(String message)
	{
		mUtil.toastMessage(message,getApplicationContext());
	}

	@Override
	public void processFinish(String result,String methodName) {
		System.out.println("processfinish result .. "+result);
		// TODO Auto-generated method stub
	    progressBar.setVisibility(View.GONE);
	    if(result != null)
	    {
	    	if(methodName.equalsIgnoreCase("registerOtp"))
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
			else if(methodName.equalsIgnoreCase("umpireRegisterViaApp"))
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
								Intent lIntent = new Intent(getApplicationContext(), LoginActivity.class);
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
			if(methodName.equalsIgnoreCase("registerIndividual"))
			{
				JSONParser jsonParser = new JSONParser();
				try {
                    System.out.println("registerIndividual .. "+result);
					JSONObject obj = (JSONObject) jsonParser.parse(result);
					//{"status":"success","resultID":"6yP26rG33kueQcjjC","response":"Reporter created"}


					if(obj.containsKey("status"))
					{
						if(obj.get("status").toString().equalsIgnoreCase("success"))
						{		
							toastMessage("Successfully Registered!!Please login");
							Intent lIntent = new Intent(getApplicationContext(), LoginActivity.class);
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
	    }

			
	}
}
