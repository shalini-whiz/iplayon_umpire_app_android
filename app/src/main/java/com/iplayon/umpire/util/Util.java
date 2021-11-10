package com.iplayon.umpire.util;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.iplayon.umpire.R;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;



public class Util {
	private Context mContext;
	private Activity mActivity;
	
	public Util(Context lContext) {
		this.mContext = lContext;
	}
	public Util(Context lContext, Activity lActivity) {
		this.mContext = lContext;
		this.mActivity = lActivity;
	}
	
	public void ypIntentGenericView(String className)
	{
		try {
			String temp = mContext.getPackageName() + ".activity." + className;
			System.out.println("temp .. "+temp);
			Class<?> callerClass = Class.forName(temp);
			Intent lIntent = new Intent(mContext, callerClass);
			lIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			mContext.startActivity(lIntent);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	
	}
	
	public void ypOnBackPressed() {
		Intent startMain = new Intent(Intent.ACTION_MAIN);
		startMain.addCategory(Intent.CATEGORY_HOME);
		startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startMain.putExtra("display", "tournamentList");					
		mContext.startActivity(startMain);
	}
	
	public boolean isOnline() {
	    ConnectivityManager cm =
	        (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    return netInfo != null && netInfo.isConnectedOrConnecting();
	}
	public ArrayList<JSONObject> sortJsonArray(String key, final String paramType, final ArrayList<JSONObject> sortedArray)
	{
		final String searchKey = key;
    	if(paramType.equalsIgnoreCase("string"))
    	{
    		Collections.sort(sortedArray, new Comparator<JSONObject>() {
		        @Override		      		        
		        public int compare(JSONObject lhs, JSONObject rhs) {
					if(lhs.get(searchKey) != null && rhs.get(searchKey) != null)
					{
						String s1 = lhs.get(searchKey).toString();
						String s2 = rhs.get(searchKey).toString();
						return s1.compareToIgnoreCase(s2);
					}

					return 0;
				}
		    });
    		return sortedArray;
    	}
    	else
    	{
    		Collections.sort(sortedArray, new Comparator<JSONObject>() {
    		    @Override
    		    public int compare(JSONObject lhs, JSONObject rhs) {

					if(lhs.get(searchKey) != null && rhs.get(searchKey) != null) {
						float valA = Float.parseFloat(lhs.get(searchKey).toString());
						float valB = Float.parseFloat(rhs.get(searchKey).toString());
						int result = 0;
						if (valA > valB)
							result = -1;
						if (valA < valB)
							result = 1;
						return result;
					}
					else
						return 0;
    		    }
    		});
    		return sortedArray;

    	}
		
	}













	public void toastMessage(String message,Context mContext){

		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View customToastLayout = inflater.inflate(R.layout.custom_toast, null);

		TextView  toastText = (TextView) customToastLayout.findViewById(R.id.toastText);
		toastText.setText(message);

		Toast customToast=Toast.makeText(mContext,message,Toast.LENGTH_LONG);
		customToast.setView(customToastLayout);
		customToast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL,0, 0);
		customToast.setDuration(Toast.LENGTH_SHORT);
		customToast.show();
	}

	public void setStatusBar(Activity lActivity){



		if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
			/*Window w = lActivity.getWindow(); // in Activity's onCreate() for instance
			w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
			lActivity.getWindow().getDecorView().setSystemUiVisibility(
					View.SYSTEM_UI_FLAG_LAYOUT_STABLE
							| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
			w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
			w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			*/
			Window window = lActivity.getWindow();
			window.getDecorView().setSystemUiVisibility(
					View.SYSTEM_UI_FLAG_LAYOUT_STABLE
							| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
			//window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
			//window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

			//window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}


		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
		{
			//lActivity.getWindow().setNavigationBarColor(Color.BLACK);
			//lActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
			//lActivity.getWindow().getDecorView().setSystemUiVisibility(
			//		View.SYSTEM_UI_FLAG_LAYOUT_STABLE
			//				| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
			//lActivity.getWindow().setStatusBarColor(Color.TRANSPARENT);


			Window window = lActivity.getWindow();
			window.getDecorView().setSystemUiVisibility(
					View.SYSTEM_UI_FLAG_LAYOUT_STABLE
							| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
			window.setStatusBarColor(ContextCompat.getColor(lActivity.getApplicationContext(),R.color.statusBarColor));
			//window.setStatusBarColor(Color.TRANSPARENT);
			//setStatusBarTranslucent(true);
		}
	}



	public void setStatusBarTransparent(Activity lActivity){



		if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
			/*Window w = lActivity.getWindow(); // in Activity's onCreate() for instance
			w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
			lActivity.getWindow().getDecorView().setSystemUiVisibility(
					View.SYSTEM_UI_FLAG_LAYOUT_STABLE
							| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
			w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
			w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			*/
			Window window = lActivity.getWindow();
			window.getDecorView().setSystemUiVisibility(
					View.SYSTEM_UI_FLAG_LAYOUT_STABLE
							| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
			//window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
			window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

			//window.setStatusBarColor(Color.TRANSPARENT);
			window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

		}


		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
		{
			Window window = lActivity.getWindow();
			window.getDecorView().setSystemUiVisibility(
					View.SYSTEM_UI_FLAG_LAYOUT_STABLE
							| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
			window.setStatusBarColor(Color.TRANSPARENT);
		}
	}


	public void setWindowStyle(Activity lActivity)
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			Window window = lActivity.getWindow(); // in Activity's onCreate() for instance
			window.requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
			window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_OVERSCAN);
		}
	}





}
