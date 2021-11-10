package com.iplayon.umpire;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.iplayon.umpire.activity.MainActivity;
import com.iplayon.umpire.util.AsyncResponse;
import com.iplayon.umpire.util.Constants;
import com.iplayon.umpire.util.ExecuteURLTask;
import com.iplayon.umpire.util.SessionManager;
import com.iplayon.umpire.util.Util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity1 extends Activity implements AsyncResponse{

	SessionManager mSession;
	Util mUtil;
	ExecuteURLTask asyncTask;
	String requestType = "";
	String params = "";
	String queryparams = "";
	String methodType = "";
	TextView ipAddress;
	LinearLayout loginFormLayout;
	LinearLayout forgotPasswordLayout;
	TextView forgotUserMail;
	EditText forgotOtp;
	EditText newPassword;
	EditText confirmPassword;
	TextView redirectLogin;
	Button forgotPasswordSubmit;
	
	TextView userPassword;
	TextView userForgotPassword;
	TextView userMail;
	String otp ="";
	Button loginButton;
	ProgressBar progressBar;
	LinearLayout progressBarLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_old);
		mSession = new SessionManager(getApplicationContext());
		mUtil = new Util(getApplicationContext(), this);
		
		
		progressBar = new ProgressBar(LoginActivity1.this);
		progressBarLayout = new LinearLayout(LoginActivity1.this);
		progressBarLayout.setGravity(Gravity.CENTER);
	    addContentView(progressBarLayout,new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	    progressBarLayout.removeAllViews();
	    progressBarLayout.addView(progressBar);
	    progressBar.setVisibility(View.GONE);
	    
		loginFormLayout = (LinearLayout) findViewById(R.id.loginFormLayout);
		forgotPasswordLayout = (LinearLayout) findViewById(R.id.forgotPasswordLayout);
		forgotPasswordLayout.setVisibility(View.GONE);
		
		userMail = (TextView) findViewById(R.id.loginUserMail);
		userPassword = (TextView) findViewById(R.id.loginPassword);
		ipAddress = (TextView) findViewById(R.id.ipAddress);
		userForgotPassword = (TextView) findViewById(R.id.userForgotPassword);
		
		//forgot password related
		forgotUserMail = (TextView) findViewById(R.id.forgotUserMail);
		forgotOtp = (EditText) findViewById(R.id.forgotOtp);
		newPassword = (EditText) findViewById(R.id.newPassword);
		confirmPassword = (EditText) findViewById(R.id.confirmPassword);
		forgotPasswordSubmit = (Button) findViewById(R.id.forgotPasswordSubmit);
		redirectLogin = (TextView) findViewById(R.id.redirectLogin);
		loginButton = (Button) findViewById(R.id.userLogin);
		
		
		userMail.setText("umpire@gmail.com");
		userPassword.setText("abcdef");
		ipAddress.setText("http://192.168.0.52:9090/dev/");

		
		
		loginButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				TextView userMail = (TextView) findViewById(R.id.loginUserMail);
				TextView userPassword = (TextView) findViewById(R.id.loginPassword);
				if(mUtil.isOnline())
				{
					mSession.ypStoreSessionURL(ipAddress.getText().toString());
				    progressBar.setVisibility(View.VISIBLE);
					String userName = userMail.getText().toString();
					String userPwd =  userPassword.getText().toString();
					params= "userLogin?";	
			    	queryparams= "caller="+Constants.caller+"&apiKey="+Constants.apiKey+"&userName="+userName+"&userPassword="+userPwd+"&emailOrPhone=1&loginRole=Umpire";
					requestType = "POST";
		    		methodType = "playerLogin";
		    		asyncTask = new ExecuteURLTask(getApplicationContext(),"");
					asyncTask.delegate = LoginActivity1.this;
					asyncTask.execute(params,queryparams,requestType);
					
				}
				else
				{
					toastMessage("No Internet");
				}
				
			}
		});
		
		userForgotPassword.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				
				if(userMail.getText().length() > 0)
				{
					if(android.util.Patterns.EMAIL_ADDRESS.matcher(userMail.getText().toString()).matches())
					{
						if(mUtil.isOnline())
						{
							mSession.ypStoreSessionURL(ipAddress.getText().toString());
							String userName = userMail.getText().toString();
							forgotUserMail.setText(userName);
							params= "otpForgotPassword?caller="+Constants.caller+"&apiKey="+Constants.apiKey+"&emailId="+userName;					    							
							requestType = "GET";
				    		methodType = "otpForgotPassword";
				    		asyncTask = new ExecuteURLTask(getApplicationContext(),"");
							asyncTask.delegate = LoginActivity1.this;
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
				else
				{
					toastMessage("Please enter Email ID");
				}
			}
		});
		
		
		forgotPasswordSubmit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				if(forgotOtp.getText().length() > 0)
				{
					if(forgotOtp.getText().toString().equalsIgnoreCase(otp))
					{
						if(newPassword.getText().length() > 0)
						{
							if(newPassword.getText().toString().equalsIgnoreCase(confirmPassword.getText().toString()))
							{
								if(mUtil.isOnline())
								{
									String userMailID = userMail.getText().toString();
									String newPasswordText = newPassword.getText().toString();
									String verificationCode = forgotOtp.getText().toString();
									params= "setNewPassword?";	
									queryparams= "caller="+Constants.caller+"&apiKey="+Constants.apiKey+"&userId="+userMailID+"&password="+newPasswordText+"&verificationCode="+verificationCode;					    							
									requestType = "POST";
						    		methodType = "setNewPassword";
						    		asyncTask = new ExecuteURLTask(getApplicationContext(),"");
									asyncTask.delegate = LoginActivity1.this;
									asyncTask.execute(params,queryparams,requestType);
								}
								else
								{
									toastMessage("No Internet");
								}
																						

							}
							else
							{
								toastMessage("Please reconfirm password");

							}
						}
						else
						{
							toastMessage("Please enter password");

						}
					}
					else
					{
						toastMessage("Invalid verification code");
					}				
				}
				else
				{
					toastMessage("Please enter verification code");
				}
				
				
			}
		});
		
		redirectLogin.setOnClickListener(new View.OnClickListener() {		
			public void onClick(View arg0) {
				forgotPasswordLayout.setVisibility(View.GONE);
				loginFormLayout.setVisibility(View.VISIBLE);
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {	
		if(item.getItemId() == R.id.close)
		{	
			mSession.clearSession();
			mUtil.ypIntentGenericView("MainActivity");
			
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onBackPressed() {
		Intent lIntent = new Intent(getApplicationContext(), MainActivity.class);
		lIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(lIntent);
	}
	public void toastMessage(String message)
	{
		Toast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT).show();
	}


	@Override
	public void processFinish(String result, String methodName) {
		progressBar.setVisibility(View.GONE);

		if(result != null)
		{
			System.out.println("on processFinish : "+result);
			if(methodType.equalsIgnoreCase("otpForgotPassword"))
			{
				JSONParser jsonParser = new JSONParser();
				try {
					JSONObject obj = (JSONObject) jsonParser.parse(result);
					if(obj.containsKey("message") && obj.containsKey("mailID"))
					{
						if(obj.get("message").toString().equalsIgnoreCase("Verification code sent to "+obj.get("mailID").toString()))
						{
							if(obj.containsKey("verificationCode"))
								otp = obj.get("verificationCode").toString();
							forgotPasswordLayout.setVisibility(View.VISIBLE);
							loginFormLayout.setVisibility(View.GONE);
							Toast.makeText(getApplicationContext(), "redirect screen", Toast.LENGTH_SHORT).show();

						}
						else if(obj.get("message").toString().equalsIgnoreCase("Could not send an email!! Please try again"))
						{
							toastMessage(obj.get("message").toString());

						}
						else if(obj.get("message").toString().equalsIgnoreCase("Email ID not registered"))
						{
							toastMessage(obj.get("message").toString());
						}
						else
						{
							toastMessage("Could not send an email!! Please try again");
						}

					}
				}catch(Exception e)
				{

				}
			}
			else if(methodType.equalsIgnoreCase("setNewPassword"))
			{
				JSONParser jsonParser = new JSONParser();
				try {
					JSONObject obj = (JSONObject) jsonParser.parse(result);
					if(obj.containsKey("message"))
					{
						if(obj.get("message").toString().equalsIgnoreCase("Password Set"))
						{
							toastMessage("Successfully password updated!! Please login");
							forgotPasswordLayout.setVisibility(View.GONE);
							loginFormLayout.setVisibility(View.VISIBLE);
						}
						else
							toastMessage("Could not set password!! Please try again");


					}
				}catch(Exception e)
				{

				}
			}
			else if(methodType.equalsIgnoreCase("playerLogin"))
			{
				System.out.println("response on login "+result);
				try {
					if(result == null)
						toastMessage("Network not reachable");
					else if(result.contains("Invalid user"))
						toastMessage(result);
					else if(result.equals("\"Invalid password\""))
						toastMessage(result);
					else if(result.equals("\"Incorrect password\""))
						toastMessage(result);
					else if(result.contains("can login"))
					{
						toastMessage(result.replaceAll("\"",""));
					}
					else
					{
						JSONParser jsonParser = new JSONParser();
						try {

							System.out.println("result on login .. "+result);
							JSONObject obj = (JSONObject) jsonParser.parse(result);

							if(obj.containsKey("role"))
							{
								String role = obj.get("role").toString();

								if(role.equalsIgnoreCase("Umpire"))
								{
									if(obj.containsKey("emailAddress"))
										mSession.ypStoreSessionUserMail(obj.get("emailAddress").toString());
									if(obj.containsKey("userId"))
										mSession.ypStoreSessionUserID(obj.get("userId").toString());
									if(obj.containsKey("userName"))
										mSession.ypStoreSessionUserName(obj.get("userName").toString());

									mSession.ypStoreUserRole(role);
									System.out.println("userDetails .. "+result);
									if(obj.containsKey("affiliationId"))
									{
										mSession.ypStoreSessionUserAffiliationId(obj.get("affiliationId").toString());
									}
									else
										mSession.ypStoreSessionUserAffiliationId("0");


									System.out.println(obj.get("_id")+" ........... "+obj.get("userName"));

									toastMessage("Logged in successfully");
									Intent lIntent = new Intent(getApplicationContext(), HomeActivity.class);
									lIntent.putExtra("display", "tournamentList");
									lIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
									startActivity(lIntent);
								}
								else
									toastMessage("Only Umpire can use this application");
							}


						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}
}
