package com.iplayon.umpire;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.iplayon.umpire.util.AsyncResponse;
import com.iplayon.umpire.util.Constants;
import com.iplayon.umpire.util.ExecuteURLTask;
import com.iplayon.umpire.util.SessionManager;
import com.iplayon.umpire.util.Util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class HomeActivity extends Activity  implements AsyncResponse{

	String urlResponse;
	String report;
	ArrayList<String> menuArray = new ArrayList<String>();

	Util mUtil;
	SessionManager mSession;
	ExecuteURLTask asyncTask;
	String requestType = "";
	String params = "";
	String queryparams = "";
	String methodType = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
	
		
		mUtil = new Util(getApplicationContext(), this);
		mSession = new SessionManager(getApplicationContext());
		
		if(mSession.ypGetUserRole().equalsIgnoreCase("Umpire"))	
		{
			menuArray.add("Upcoming Tournaments");
			menuArray.add("Past Tournaments");

		}
		
		


		ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.home_menulist, menuArray);
	      
	    final ListView listView = (ListView) findViewById(R.id.menu_list_view);
	    listView.setAdapter(adapter);

        listView.setOnItemClickListener(new OnItemClickListener() {

              public void onItemClick(AdapterView<?> parent, View view,
                 int position, long id) {
                
	               String  itemValue    = (String) listView.getItemAtPosition(position);
	               if(itemValue.equalsIgnoreCase("Upcoming Tournaments"))
	               {
	            	   if(mUtil.isOnline())
		            	  {
		            		  System.out.println("entered upcoming tournament");
			   	              report = "upcomingTournament";
			   	   			  urlResponse = "";
			   	   			  params= "listOfUpcomingEventsBasedOnRole?caller="+Constants.caller+"&apiKey="+Constants.apiKey+"&userId="+mSession.ypGetUserID();  			
			   	   			  requestType="GET";
			   			      methodType = "upcomingTournament";
			   			   ExecuteURLTask asyncTask = new ExecuteURLTask(getApplicationContext(),"");
			   				  asyncTask.delegate = HomeActivity.this;
			   				  asyncTask.execute(params,queryparams,requestType);
		            	   }
		            	   else
		            		   toastMessage("No Internet");
	               }
	               else if(itemValue.equalsIgnoreCase("Past Tournaments"))
	               {
	            	   
	            	   
	            	   if(mUtil.isOnline())
		            	  {

		            		  System.out.println("entered Past tournament");
			   	              report = "pastTournament";
			   	   			  urlResponse = "";
			   	   			  params= "listOfPastTournaments?caller="+Constants.caller+"&apiKey="+Constants.apiKey+"&userId="+mSession.ypGetUserID();  			
			   	   			  requestType="GET";
			   			      methodType = "pastTournament";
			   			   ExecuteURLTask asyncTask = new ExecuteURLTask(getApplicationContext(),"");
			   			   		asyncTask.delegate = HomeActivity.this;
			   				  asyncTask.execute(params,queryparams,requestType);
		            	   }
		            	   else
		            		   toastMessage("No Internet");
	               }

             }
              
         }); 
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		if(item.getItemId() == R.id.logout)
		{
			mSession.clearSession();
			mUtil.ypIntentGenericView("MainActivity");
		}
		else if(item.getItemId() == R.id.profileSettings)
		{
			/*
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

			ArrayList<String> collecDates = new ArrayList<String>();
			collecDates.add(dateFormat.format(new Date()).toString());
			collecDates.add(dateFormat.format(new Date()).toString());
			collecDates.add(dateFormat.format(new Date()).toString());

           
            downloadUmpireEntries

			params= "umpireSubscriptionApp?";
			params= "downloadUmpireEntries?";
				*/
			
			
			params= "fetchProfileSettings?";

			requestType = "POST";
    		methodType = "fetchProfileSettings";

			JSONObject param = new JSONObject();
			param.put("emailAddress", mSession.ypGetUserMail());
			param.put("userId", mSession.ypGetUserID());
			param.put("tournamentId","GG6qt6B6GacPD4z2R");
			//param.put("subscribedDates",collecDates);
			
			
	    	queryparams= "caller="+Constants.caller+"&apiKey="+Constants.apiKey+"&data="+param;
	    	System.out.println("queryparams .. "+queryparams);

			//methodType = "downloadUmpireEntries";
			//methodType = "umpireSubscriptionApp";
    		asyncTask = new ExecuteURLTask(getApplicationContext(),"");
			asyncTask.delegate = HomeActivity.this;
			asyncTask.execute(params,queryparams,requestType);	
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed() {
		mUtil.ypOnBackPressed();
	}

	public void toastMessage(String message)
	{
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void processFinish(String result, String methodName) {

		System.out.println("Activity report .. "+result);

		if(result != null)
		{
			if(requestType.equalsIgnoreCase("GET") && (methodType.equalsIgnoreCase("upcomingTournament") || methodType.equalsIgnoreCase("pastTournament")))
			{
				JSONParser jsonParser = new JSONParser();
				try {
					
					if(methodType.equalsIgnoreCase("pastTournament"))
					{
						JSONArray obj = (JSONArray) jsonParser.parse(result);
						System.out.println("obj size .."+obj.size());
						if(obj.size() == 0)
						{		
							toastMessage("No past events");
						}
						else
						{
							System.out.println("home activity response .. "+result);
							Intent lIntent = new Intent(getApplicationContext(), TournamentDisplay1.class);
							lIntent.putExtra("report",methodType);
							lIntent.putExtra("response",result);
							lIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
							startActivity(lIntent);	
						}
					}
					else if(methodType.equalsIgnoreCase("upcomingTournament"))
					{
						JSONArray obj = (JSONArray) jsonParser.parse(result);
						JSONArray array = obj;
						if(array != null && array.size() == 0)
						{
							toastMessage("No upcoming events");
						}
						else
						{
							System.out.println("home activity response .. "+result);
							Intent lIntent = new Intent(getApplicationContext(), TournamentDisplay1.class);
							lIntent.putExtra("report",methodType);
							lIntent.putExtra("response",result);
							lIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
							startActivity(lIntent);	
						}

					}
					
					
					
				} catch (ParseException e) {
					e.printStackTrace();	
				}	
				
			}
			else if(requestType.equalsIgnoreCase("POST") && methodType.equalsIgnoreCase("fetchProfileSettings"))
			{
				JSONParser jsonParser = new JSONParser();
				try {
					JSONObject obj = (JSONObject) jsonParser.parse(result);
					obj.get("tournamentList");
					if(obj.containsKey("status"))
					{
						if(obj.get("status").toString().equalsIgnoreCase("success"))
						{
							System.out.println("home activity fetchProfileSettings response .. "+result);
							Intent lIntent = new Intent(getApplicationContext(), ProfileSettings1.class);
							lIntent.putExtra("report",methodType);
							lIntent.putExtra("response",obj.get("result").toString());
							lIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
							startActivity(lIntent);	
						}
					}

					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
			
	
   		
		
	}




}
