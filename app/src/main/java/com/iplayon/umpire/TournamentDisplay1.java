package com.iplayon.umpire;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.iplayon.umpire.modal.DomainJson;
import com.iplayon.umpire.util.AsyncResponse;
import com.iplayon.umpire.util.Constants;
import com.iplayon.umpire.util.ExecuteURLTask;
import com.iplayon.umpire.util.SessionManager;
import com.iplayon.umpire.util.Util;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

public class TournamentDisplay1 extends Activity implements AsyncResponse{

	ListView mListView;
	JSONArray array;
	JSONObject jsonObject;
	public static String intentReport;
	public static String intentResponse;
	TextView mTitleHeaderUI;
	TextView mBackMenuUI;
	TextView mHomeMenuUI;
	private Util mUtil;
	private SessionManager mSession;
	TournamentAdapter tournamentAdapter;
	List<DomainJson> domainJsonList;
	
	String params = "";
	String requestType = "";
	String methodType = "";
	String urlResponse = "";
	String queryparams = "";
	String fetchParam = "";
	ExecuteURLTask asyncTask;
	String paramHeader= "";
	JSONObject methodParam;
	 Long date;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tournament_display);
		String report = getIntent().getExtras().getString("report");
		String response = getIntent().getExtras().getString("response");
		intentReport = report;
		intentResponse = response;
		
		mUtil = new Util(getApplicationContext(), this);
		mSession = new SessionManager(getApplicationContext());
		//title bar
		mTitleHeaderUI = (TextView) findViewById(R.id.title);
		mTitleHeaderUI.setText("");
		mBackMenuUI = (TextView) findViewById(R.id.home_chats);
		mHomeMenuUI = (TextView) findViewById(R.id.home);
		mListView = (ListView) findViewById(R.id.list_view);
		TextView reportHeader = (TextView) findViewById(R.id.reportHeader);
		if(intentReport.equalsIgnoreCase("upcomingTournament"))
			reportHeader.setText("Upcoming Tournaments");
		else
			reportHeader.setText("Past Tournaments");
		
		mBackMenuUI.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {		
				Intent lIntent = new Intent(getApplicationContext(), HomeActivity.class);
				lIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(lIntent);		
				}
			});
		
		mHomeMenuUI.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {		
					Intent lIntent = new Intent(getApplicationContext(), HomeActivity.class);
					lIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivity(lIntent);		
					}
				});
		
		
		if(response != null)
		{
			JSONParser jsonParser = new JSONParser();
			Object obj;
			try {
				obj = jsonParser.parse(response);
				JSONArray array = (JSONArray) obj;					
				if(array != null)
				{
					tournamentAdapter = new TournamentAdapter(getApplicationContext(),TournamentDisplay1.this,array);
					mListView.setAdapter(tournamentAdapter);
					tournamentAdapter.notifyDataSetChanged();						
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
	     
		}	
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
			
			params= "fetchProfileSettings?";	
			JSONObject param = new JSONObject();
			param.put("emailAddress", mSession.ypGetUserMail());
			param.put("userId", mSession.ypGetUserID());
	    	queryparams= "caller="+Constants.caller+"&apiKey="+Constants.apiKey+"&data="+param;
			requestType = "POST";
    		methodType = "fetchProfileSettings";
    		asyncTask = new ExecuteURLTask(getApplicationContext(),"");
			asyncTask.delegate = TournamentDisplay1.this;
			asyncTask.execute(params,queryparams,requestType);	
		}
		return super.onOptionsItemSelected(item);
	}




	public class TournamentAdapter extends BaseAdapter implements ListAdapter{

		private Context mContext;
		private Activity mActivity;
	    private  JSONArray mJsonArray;
	   
	    SessionManager mSession;
		public TournamentAdapter(Context lContext, Activity lActivity, JSONArray array) {
			// TODO Auto-generated constructor stub
			this.mContext = lContext;
			this.mActivity = lActivity;
			this.mJsonArray = array;
			mSession = new SessionManager(mContext);
		}

		public int getCount() {
			if(null==mJsonArray) 
		         return 0;
		     else
		        return mJsonArray.size();	
		}

		public JSONObject getItem(int position) {
		         if(null==mJsonArray) return null;
		         else
		           return (JSONObject) mJsonArray.get(position);
		}


		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView == null)
			{
				
				LayoutInflater lInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
				convertView = lInflater.inflate(R.layout.tournament_listview, null);
			}
			
			final LinearLayout customLinearLayout = (LinearLayout) convertView.findViewById(R.id.customLinearLayout);
			final ImageView tournamentInfo = (ImageView) convertView.findViewById(R.id.tournamentInfo);

			TextView eventName = (TextView) convertView.findViewById(R.id.textView1);
			TextView eventDate = (TextView) convertView.findViewById(R.id.textView2);
			TextView eventPlace = (TextView) convertView.findViewById(R.id.textView3);
																							            

				
			final JSONObject json_data = getItem(position); 
			

		    if(null!=json_data )
		    {	        	
		        if(json_data.containsKey("eventName")) 	        
		        	eventName.setText(json_data.get("eventName").toString());
					
		        if(json_data.containsKey("eventStartDate") &&  json_data.containsKey("eventEndDate"))
		        	eventDate.setText(json_data.get("eventStartDate")+" to "+json_data.get("eventEndDate"));
	            
		        if(json_data.containsKey("domainName")) 	        
		        	eventPlace.setText(json_data.get("domainName").toString());
		        
		    
		    }
		    final PopupMenu popup = new PopupMenu(TournamentDisplay1.this, convertView);
	        popup.getMenuInflater().inflate(R.menu.tournament_menu, popup.getMenu()); 
	       
		    tournamentInfo.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
								           
			  		 popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			  			public boolean onMenuItemClick(MenuItem item) {
			  						if(item.getTitle().toString().equalsIgnoreCase("Subscribe"))
						  			{
			  						
			  								LayoutInflater inflater = getLayoutInflater();
											final View viewSequenceStrokeLayout = inflater.inflate(R.layout.calendar_layout, null);
			  								final CalendarView displayCalendar= (CalendarView) viewSequenceStrokeLayout.findViewById(R.id.calendarView);
			  								Date startDateVal;
			  								 Date endDateVal;
			  								 SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd");
			  								try {
			  								startDateVal = sdf.parse(json_data.get("eventStartDate").toString());
			  								endDateVal = sdf.parse(json_data.get("eventEndDate").toString());
			  								displayCalendar.setMinDate(startDateVal.getTime());           	
			  								displayCalendar.setMaxDate(endDateVal.getTime());    
			  								
			  								

			  								
			  							            	
			  							} catch (java.text.ParseException e1) {
			  								e1.printStackTrace();
			  							}
			  								/*date = displayCalendar.getDate();
			  								displayCalendar.setOnDateChangeListener(new OnDateChangeListener(){
			  							        public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
			  							            if(displayCalendar.getDate() != date){
			  							                date = displayCalendar.getDate();
			  							                Toast.makeText(view.getContext(), "Year=" + year + " Month=" + month + " Day=" + dayOfMonth, Toast.LENGTH_SHORT).show();
			  							            }               
			  							        }
			  							    });	*/
			  								
			  							AlertDialog.Builder calendarDialog = new AlertDialog.Builder(TournamentDisplay1.this);
			  							calendarDialog.setView(viewSequenceStrokeLayout);

										calendarDialog.setTitle(json_data.get("eventName").toString()+" Subscription");
										calendarDialog.setCancelable(false);
										calendarDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
											public void onClick(DialogInterface dialog, int which) {
												 dialog.cancel();
											}
										});

										calendarDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
											public void onClick(DialogInterface dialog, int which) {
												
																																									
											}						
										});
										calendarDialog.show();	
										
										
			  							//displayCalendar.setVisibility(View.VISIBLE);  							
						  			}
			  						
			  				return true;
			  			}
			  		 });
			  		 popup.show();
					
				}
				
			});
		    
		  
			return convertView;
		}

	

	}


	@Override
	public void processFinish(String result, String methodName) {
		try{
			
			if(result != null)
			{
				if(requestType.equalsIgnoreCase("POST") && methodType.equalsIgnoreCase("fetchProfileSettings"))
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
		
						
		
	}catch(Exception e)
	{
		System.out.println("file path analytics .."+e.getMessage());

	}
	
	
				
			
	
		
	}

}
