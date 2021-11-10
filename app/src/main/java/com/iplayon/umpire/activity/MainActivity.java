package com.iplayon.umpire.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.iplayon.umpire.R;
import com.iplayon.umpire.Registration1;
import com.iplayon.umpire.util.SessionManager;
import com.iplayon.umpire.util.Util;


public class MainActivity extends Activity {
	SessionManager mSession;
	Util mUtil;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//customActionBar.setElevation(0);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
		}

		mSession = new SessionManager(getApplicationContext());
		mUtil = new Util(this);
		mUtil.setStatusBarTransparent(this);
		setContentView(R.layout.activity_main);


		if(mSession.ypGetUserName() != null)
		{
			new Handler(Looper.getMainLooper()).post(new Runnable() {
				@Override
				public void run() {
					Intent lIntent = new Intent(getApplicationContext(), HomeActivity.class);
					lIntent.putExtra("display", "tournamentList");
					lIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivity(lIntent);


				}
			});


		}
		else
		{
			Button login = (Button) findViewById(R.id.login);
			Button register = (Button) findViewById(R.id.register);
			
			login.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {


					new Handler(Looper.getMainLooper()).post(new Runnable() {
						@Override
						public void run() {
							Intent lIntent = new Intent(getApplicationContext(), LoginActivity.class);
							lIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
							startActivity(lIntent);
						}
					});


					
				}
			});
			
			register.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					new Handler(Looper.getMainLooper()).post(new Runnable() {
						@Override
						public void run() {
							Intent lIntent = new Intent(getApplicationContext(), Registeration.class);
							lIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
							startActivity(lIntent);
						}
					});


				}
			});
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		return super.onOptionsItemSelected(item);
	}
	
	public void onBackPressed() {
		mSession.clearSession();


		new Handler(Looper.getMainLooper()).post(new Runnable() {
			@Override
			public void run() {
				Intent startMain = new Intent(Intent.ACTION_MAIN);
				startMain.addCategory(Intent.CATEGORY_HOME);
				startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(startMain);
			}
		});

		/*new Handler(Looper.getMainLooper()).post(new Runnable() {
			@Override
			public void run() {
				Intent lIntent = new Intent(getApplicationContext(), WelcomeActivity.class);
				lIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(lIntent);


			}
		});*/




	}
	
}
