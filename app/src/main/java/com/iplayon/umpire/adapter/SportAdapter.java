package com.iplayon.umpire.adapter;

import java.util.ArrayList;
import java.util.List;

import com.iplayon.umpire.R;
import com.iplayon.umpire.modal.SportJson;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class SportAdapter  extends BaseAdapter {
	private Context mContext;
	private Activity mActivity;
	private List<SportJson> mMessagesList;
	OnDataChangeListener mOnDataChangeListener;
	private ArrayList<String> onSportAdd = new ArrayList<String>();

	public SportAdapter(Context lContext, Activity lActivity,List<SportJson> lMessageList, ArrayList<String> selectedProject) {
		this.mContext = lContext;
		this.mActivity = lActivity;
		this.mMessagesList = lMessageList;
		if(selectedProject.size() > 0)
		{
			onSportAdd = selectedProject;
		}
		
	}

	public interface OnDataChangeListener {
		public void onDataSport(ArrayList<String> lMessageIDList);
	}

	public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener) {
		mOnDataChangeListener = onDataChangeListener;
		if(mOnDataChangeListener != null)
			mOnDataChangeListener.onDataSport(onSportAdd);
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
	@Override
	  public int getViewTypeCount() {

	   int count;
       if (mMessagesList.size() > 0) {
           count = getCount();
       } else {
           count = 1;
       }
       return count;
       
       
	  }

	  @Override
	  public int getItemViewType(int position) {

	   return position;
	  }


	@Override
	public View getView(int lPosition, View lConvertView, ViewGroup lParent) {
		// TODO Auto-generated method stub
		if(lConvertView == null)
		{
			LayoutInflater lInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			lConvertView = lInflater.inflate(R.layout.multi_spinner, null);
		}
		final CheckBox spinnerCheck = (CheckBox) lConvertView.findViewById(R.id.spinnerCheck);
		final TextView spinnerText = (TextView) lConvertView.findViewById(R.id.spinnerText);
	
		final SportJson lMessageObj = mMessagesList.get(lPosition);
		spinnerText.setText(lMessageObj.getProjectName());
		
		if(onSportAdd.contains(lMessageObj.getUserId()))
			spinnerCheck.setChecked(true);
		else
			spinnerCheck.setChecked(false);
		
		spinnerCheck.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				
				// TODO Auto-generated method stub
				if(spinnerCheck.isChecked())
				{
					lMessageObj.setChecked(true);
					onSportAdd.add(lMessageObj.getUserId());
				}
				else
				{
					lMessageObj.setChecked(false);
					onSportAdd.remove(lMessageObj.getUserId());

				}
						
				if(mOnDataChangeListener != null)
				{
					mOnDataChangeListener.onDataSport(onSportAdd);
				}
			}

		});
		return lConvertView;
	}
}
