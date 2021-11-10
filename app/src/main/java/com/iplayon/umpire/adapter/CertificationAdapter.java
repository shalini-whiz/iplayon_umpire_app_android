package com.iplayon.umpire.adapter;



import java.util.ArrayList;
import java.util.List;

import com.iplayon.umpire.modal.CertificationJson;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import com.iplayon.umpire.R;



public class CertificationAdapter extends BaseAdapter{
	
	private Context mContext;
	private Activity mActivity;
	private List<CertificationJson> mMessagesList;
	OnDataChangeListener mOnDataChangeListener;
	private ArrayList<String> onCertificationAdd = new ArrayList<String>();

	public CertificationAdapter(Context lContext, Activity lActivity,List<CertificationJson> lMessageList, ArrayList<String> selectedProject) {
		this.mContext = lContext;
		this.mActivity = lActivity;
		this.mMessagesList = lMessageList;
		if(selectedProject.size() > 0 && lMessageList.size() > 0)
		{
			for(int j =0; j< selectedProject.size(); j++)
			{

				int position = lMessageList.indexOf(new CertificationJson(selectedProject.get(j).toString()));
				System.out.println("prior certification .. "+selectedProject.get(j)+" ... "+position);

				lMessageList.get(position).setChecked(true);
			}
			
			onCertificationAdd = selectedProject;

		}
		
	}

	public interface OnDataChangeListener {
		public void onDataCertification(ArrayList<String> lMessageIDList);
	}

	public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener) {
		mOnDataChangeListener = onDataChangeListener;
		if(mOnDataChangeListener != null)
		{
			System.out.println("certification size prior .. "+onCertificationAdd.size());

			mOnDataChangeListener.onDataCertification(onCertificationAdd);

		}
		
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
		TextView spinnerText = (TextView) lConvertView.findViewById(R.id.spinnerText);
	
		final CertificationJson lMessageObj = mMessagesList.get(lPosition);
		
		if(lMessageObj.getChecked() != null)
		{
			if(lMessageObj.getChecked())
				spinnerCheck.setChecked(true);

		}
		
		
		spinnerText.setText(lMessageObj.getCertification());
		spinnerCheck.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(spinnerCheck.isChecked())
				{
					lMessageObj.setChecked(true);
					if( ! (onCertificationAdd.contains(lMessageObj.get_id())))
						onCertificationAdd.add(lMessageObj.get_id());
				}
				else
				{
					lMessageObj.setChecked(false);
					onCertificationAdd.remove(lMessageObj.get_id());

				}
				
				if(mOnDataChangeListener != null)
				{
					System.out.println("certification size .. "+onCertificationAdd.size());
					mOnDataChangeListener.onDataCertification(onCertificationAdd);
				}

			}
		});
		return lConvertView;
	}

}
