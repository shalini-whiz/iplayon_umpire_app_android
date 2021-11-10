package com.iplayon.umpire.adapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.iplayon.umpire.R;
import com.iplayon.umpire.modal.EventInfo;
import com.iplayon.umpire.modal.TournamentJson;
import com.iplayon.umpire.util.CircleForm;
import com.iplayon.umpire.util.Constants;
import com.iplayon.umpire.util.ExecuteURLTask;
import com.iplayon.umpire.util.SessionManager;
import com.iplayon.umpire.util.Util;
import com.opencsv.CSVWriter;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;


public class PastTournamentAdapter extends BaseAdapter{
	
	private Context mContext;
	private Util mUtil;
	private List<TournamentJson> mMessagesList;
	private Activity mActivity;
	private SessionManager mSession;
	ProgressBar progressBar;
	LinearLayout progressBarLayout;
	 AlertDialog viewEventsDialog;
	String paramHeader= "";
	private static final int REQUEST_WRITE_STORAGE = 112;





	public PastTournamentAdapter(Context lContext, Activity lActivity, List<TournamentJson> lMessageList) {
		this.mContext = lContext;
		this.mMessagesList = lMessageList;
		this.mActivity = lActivity;
		this.mSession = new SessionManager(lContext);
		this.mUtil = new Util(lActivity);

		progressBar = new ProgressBar(lActivity);
		progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(mContext, R.color.progressBarColor), android.graphics.PorterDuff.Mode.MULTIPLY);

		progressBarLayout = new LinearLayout(lActivity);
		progressBarLayout.setGravity(Gravity.CENTER);
		lActivity.addContentView(progressBarLayout,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
		progressBarLayout.removeAllViews();
		progressBarLayout.addView(progressBar);
		progressBar.setVisibility(View.GONE);
	
	}

	public void makeRequest() {
		System.out.println("make request here");
		ActivityCompat.requestPermissions(mActivity,
				new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
				REQUEST_WRITE_STORAGE);
	}



	@Override
	public int getCount() {
		return mMessagesList.size();
	}

	@Override
	public Object getItem(int lPosition) {
		return mMessagesList.get(lPosition);
	}

	@Override
	public long getItemId(int lPosition) {
		return lPosition;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView( int lPosition, View lConvertView, ViewGroup lParent) {

		if(lConvertView == null)
		{
			LayoutInflater lInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			lConvertView = lInflater.inflate(R.layout.custom_list,null);
		}
		

		final TournamentJson lMessageObj = mMessagesList.get(lPosition);
		lMessageObj.setTournamentType("past");
		TextView eventName = (TextView) lConvertView.findViewById(R.id.textView1);
		TextView eventDate = (TextView) lConvertView.findViewById(R.id.textView2);
		final TextView viewEvents = (TextView) lConvertView.findViewById(R.id.viewEvents);
		TextView eventPlace = (TextView) lConvertView.findViewById(R.id.textView3);
		TextView moreAction = (TextView) lConvertView.findViewById(R.id.moreAction);


		eventName.setText(lMessageObj.getEventName());
		eventDate.setText(lMessageObj.getEventStartDate()+" to "+lMessageObj.getEventEndDate());
		eventPlace.setText(lMessageObj.getDomainName());



		final PopupMenu popup = new PopupMenu(mContext, lConvertView);
		popup.getMenuInflater().inflate(R.menu.tournament_menu, popup.getMenu());



		moreAction.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if( viewEventsDialog != null && viewEventsDialog.isShowing() ) return;

				popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {

						Toast.makeText(mContext,"past "+item.getTitle().toString(),Toast.LENGTH_SHORT).show();
						/*if(item.getTitle().toString().equalsIgnoreCase("Download Entries"))
						{

						}*/

						return true;
					}
				});
				popup.show();
			}
		});



		viewEvents.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if( viewEventsDialog != null && viewEventsDialog.isShowing() ) return;
				progressBar.setVisibility(View.VISIBLE);
				System.out.println("tournamentId .. "+lMessageObj.getId()+" ... "+lMessageObj.getEventName());

				String report = "getEventsOfTourn";
				String urlResponse = "";
				String params= "getEventsOfTourn?caller="+ Constants.caller+"&apiKey="+Constants.apiKey+"&tournamentId="+lMessageObj.getId()+"&userId="+mSession.ypGetUserID();
				String requestType = "GET";
				String methodType = "getEventsOfTourn";

				ExecuteURLTask asyncTask = new ExecuteURLTask(mContext, methodType);
				try {

					String result = asyncTask.execute(params, "", requestType).get();
					System.out.println("view events result .."+result);
					progressBar.setVisibility(View.GONE);

					if(result != null)
					{
						JSONParser jsonParser = new JSONParser();
						org.json.simple.JSONObject resultJson = (org.json.simple.JSONObject) jsonParser.parse(result);
						if(resultJson.containsKey("status"))
						{
							if(resultJson.get("status").toString().equalsIgnoreCase("success"))
							{
								if(resultJson.containsKey("data"))
								{
									Gson gson = new Gson();
									Type listType = new TypeToken<List<EventInfo>>(){}.getType();
									ArrayList<EventInfo> playerJsonList = gson.fromJson(resultJson.get("data").toString(), listType);
									EventInfoAdapter connectedMembersAdapter = new EventInfoAdapter(mContext,
											mActivity, playerJsonList);

									LayoutInflater inflater = mActivity.getLayoutInflater();
									View customLayout = inflater.inflate(R.layout.custom_listview_events, null);
									customLayout.setBackgroundColor(ContextCompat.getColor(mContext,android.R.color.transparent));
									ListView customListView = (ListView) customLayout.findViewById(R.id.customListView);
									ImageView closeDialog = (ImageView) customLayout.findViewById(R.id.closeDialog);
									TextView okButton = (TextView) customLayout.findViewById(R.id.okButton);
									//AlertDialog.Builder sharedMembersDialog = new AlertDialog.Builder(mActivity);

									AlertDialog.Builder sharedMembersDialog;


									sharedMembersDialog = new AlertDialog.Builder(mActivity,
												R.style.AlertDialogTheme);


									 //AlertDialog.Builder sharedMembersDialog = new AlertDialog.Builder(mActivity, android.R.style.Theme_Holo_Light);



									sharedMembersDialog.setView(customLayout);


									Glide.with(mActivity).load(R.drawable.close)
											.crossFade()
											.thumbnail(0.5f)
											.bitmapTransform(new CircleForm(mActivity))
											.diskCacheStrategy(DiskCacheStrategy.ALL)
											.into(closeDialog);




									sharedMembersDialog.setCancelable(false);
									customListView.setAdapter(connectedMembersAdapter);








									viewEventsDialog = sharedMembersDialog.show();

									okButton.setOnClickListener(new OnClickListener() {
										@Override
										public void onClick(View v) {
											viewEventsDialog.cancel();
										}
									});
									viewEventsDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
									closeDialog.setOnClickListener(new OnClickListener() {
										@Override
										public void onClick(View v) {
											viewEventsDialog.cancel();
										}
									});

								}
								else
								{
									//Toast.makeText(mContext, "No Events under this tournament", Toast.LENGTH_SHORT).show();
									mUtil.toastMessage("No Events under this tournament",mContext);

								}
							}
							else if(resultJson.get("status").toString().equalsIgnoreCase("failure")){
								mUtil.toastMessage("No Events under this tournament",mContext);


							}
						}
					}
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				} catch (ExecutionException e1) {
					e1.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}

			});


		
		
		
		

		return lConvertView;
	}

	private void processFinish(String result,String methodType) {
		if(result != null)
		{
			if(methodType.equalsIgnoreCase("individual_team") || methodType.equalsIgnoreCase("consolidated_team"))
			{
				File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
				final File dwldsPath;
				if(methodType.equalsIgnoreCase("individual_team"))
					dwldsPath = new File(path+"/"+paramHeader+"_Events.csv");
				else
					dwldsPath = new File(path+"/"+paramHeader+"_TeamEvents.csv");

				if ( dwldsPath.exists() == false ) {
					try {
						dwldsPath.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				if (dwldsPath.exists())
				{
					try {
						CSVWriter writer = new CSVWriter(new FileWriter(dwldsPath));
						result = result.replace("\\r\\n","newline");
						System.out.println("before replace .. "+result);
						result = result.replace("\"", "");
						System.out.println("after replace .. "+result);
						String[] check= result.split("newline");
						for(int x=0; x< check.length;x++)
						{
							String [] temp = check[x].split(",");
							writer.writeNext(temp);
						}
						writer.close();

					} catch (Exception e) {
						e.printStackTrace();
					}
				}


				android.app.AlertDialog.Builder openFileDialog = new android.app.AlertDialog.Builder(mActivity);
				openFileDialog.setTitle("Open file ? ");
				openFileDialog.setCancelable(false);
				openFileDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				openFileDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {

						Uri path = Uri.fromFile(dwldsPath);
						System.out.println("open pdf file url path"+path);
						Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
						pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
						pdfOpenintent.setDataAndType(path, "text/csv");
						try {
							mActivity.startActivity(pdfOpenintent);
						}
						catch (ActivityNotFoundException e) {

							System.out.println("activity not found exception .. "+e.getMessage());
							Toast.makeText(mContext,"Please install csv reader to read downloaded file",Toast.LENGTH_SHORT).show();
						}
					}

				});
				openFileDialog.show();
			}
		}
	}


	public class EventInfoAdapter extends BaseAdapter {
		private Context mContext;
		private Activity mActivity;
		private List<EventInfo> mEventList;
		public  int selectedItem;
		public SessionManager mSession;



		public EventInfoAdapter(Context lContext, Activity lActivity, List<EventInfo> lMessageList) {


			this.mContext = lContext;
			this.mActivity = lActivity;
			this.mEventList = lMessageList;
			mSession = new SessionManager(lContext);

		}

		@Override
		public int getCount() {
			return mEventList.size();
		}

		@Override
		public Object getItem(int position) {
			return mEventList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		public void setSelectedItem(int position) {
			selectedItem = position;
		}

		public int getSelectedItem() {
			return selectedItem;
		}

		@SuppressLint("InflateParams")
		@Override
		public View getView(final int lPosition, View lConvertView, ViewGroup lParent) {

			final EventInfo eventInfoObj = mEventList.get(lPosition);
			LayoutInflater lInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			lConvertView = lInflater.inflate(R.layout.event_info_layout, null);
			TextView eventName = (TextView) lConvertView.findViewById(R.id.eventName);
			TextView eventDate = (TextView) lConvertView.findViewById(R.id.eventDate);
			TextView eventEndDate = (TextView) lConvertView.findViewById(R.id.eventEndDate);

			eventName.setText(eventInfoObj.getEventName());
			eventDate.setText(eventInfoObj.getEventStartDate());
			eventEndDate.setText(eventInfoObj.getEventEndDate());

			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",Locale.ENGLISH);
			formatter.setTimeZone(TimeZone.getTimeZone("IST"));
			Date result1 = null;
			Date result2 = null;
			try {
				if(eventInfoObj.getEventEndDate1() != null)
				{
					result1 = formatter.parse(eventInfoObj.getEventStartDate1());
					eventDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(result1));

				}
				if(eventInfoObj.getEventEndDate1() != null)
				{
					result2 = formatter.parse(eventInfoObj.getEventEndDate1());
					eventEndDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(result2));
				}

			}catch (java.text.ParseException e) {
				e.printStackTrace();
			}




			return lConvertView;
		}

	}



}
