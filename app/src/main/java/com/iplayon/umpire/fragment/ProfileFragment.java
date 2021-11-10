package com.iplayon.umpire.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v4.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iplayon.umpire.HomeActivity;
import com.iplayon.umpire.ProfileSettings1;
import com.iplayon.umpire.R;
import com.iplayon.umpire.TournamentDisplay1;
import com.iplayon.umpire.adapter.CertificationAdapter;
import com.iplayon.umpire.adapter.LanguageAdapter;
import com.iplayon.umpire.adapter.SportAdapter;
import com.iplayon.umpire.modal.CertificationJson;
import com.iplayon.umpire.modal.DomainJson;
import com.iplayon.umpire.modal.LanguageJson;
import com.iplayon.umpire.modal.SportJson;
import com.iplayon.umpire.util.AsyncResponse;
import com.iplayon.umpire.util.Constants;
import com.iplayon.umpire.util.ExecuteURLTask;
import com.iplayon.umpire.util.SessionManager;
import com.iplayon.umpire.util.Util;
import com.iplayon.umpire.util.Validation;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements AsyncResponse{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    SessionManager mSession;
    Util mUtil;
    ExecuteURLTask asyncTask;
    String response = "";


    LinearLayout step1Layout;
    LinearLayout step2Layout;
    EditText profileName;
    EditText profileGuardian;
    TextView profileMail;
    EditText profilePhoneNo;
    EditText profileDOB;
    EditText profileCity;
    Spinner profileState;
    EditText profilePinCode;

    Button profileContinue;
    Button step1Previous;
    Button profileCancel;
    Button profileUpdate;
    ListView profileSport;
    ListView profileCertification;
    ListView profileLanguage;
    CheckBox travelAssignment;
    List<DomainJson> domainJsonList;
    ArrayAdapter<DomainJson> domainAdapter;
    List<SportJson> sportJsonList;
    List<LanguageJson> languageJsonList;
    LanguageAdapter languageAdapter;
    List<CertificationJson> certificationJsonList;
    CertificationAdapter certificationAdapter;
    SportAdapter sportAdapter;
    List<String> checkedLanguage;
    List<String> checkedCertification;
    List<String> checkedSport;
    JSONObject jsonObject;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.profile_settings, container, false);

        mUtil = new Util(getActivity().getApplicationContext(), getActivity());
        mSession = new SessionManager(getActivity().getApplicationContext());

        checkedLanguage = new ArrayList<String>();
        checkedCertification = new ArrayList<String>();
        checkedSport = new ArrayList<String>();
        step1Layout = (LinearLayout) view.findViewById(R.id.step1Layout);
        step2Layout = (LinearLayout) view.findViewById(R.id.step2Layout);

        profileName = (EditText) view.findViewById(R.id.profileName);
        profileMail = (TextView) view.findViewById(R.id.profileMail);
        profilePhoneNo = (EditText) view.findViewById(R.id.profilePhoneNo);
        profileCity = (EditText) view.findViewById(R.id.profileCity);
        profileState = (Spinner) view.findViewById(R.id.profileState);
        profilePinCode = (EditText) view.findViewById(R.id.profilePinCode);
        profileGuardian = (EditText) view.findViewById(R.id.profileGuardian);
        profileDOB = (EditText) view.findViewById(R.id.profileDOB);
        profileContinue = (Button) view.findViewById(R.id.profileContinue);
        profileCancel = (Button) view.findViewById(R.id.profileCancel);
        profileUpdate = (Button) view.findViewById(R.id.profileUpdate);

        step1Previous =(Button) view.findViewById(R.id.step1Previous);
        profileSport = (ListView) view.findViewById(R.id.profileSport);
        profileCertification = (ListView) view.findViewById(R.id.profileCertification);
        profileLanguage = (ListView) view.findViewById(R.id.profileLanguage);
        travelAssignment = (CheckBox) view.findViewById(R.id.travelAssignment);

        String params= "fetchProfileSettings?";
        JSONObject param = new JSONObject();
        param.put("emailAddress", mSession.ypGetUserMail());
        param.put("userId", mSession.ypGetUserID());
        String queryparams= "caller="+ Constants.caller+"&apiKey="+Constants.apiKey+"&data="+param;
        String requestType = "POST";
        String methodType = "fetchProfileSettings";
        asyncTask = new ExecuteURLTask(getActivity().getApplicationContext(),methodType);
        try {
            String result = asyncTask.execute(params,queryparams,requestType).get();
            if(result != null)
            {
                JSONParser jsonParser = new JSONParser();
                try {
                    JSONObject obj = (JSONObject) jsonParser.parse(result);
                    if(obj.containsKey("status"))
                    {
                        if(obj.get("status").toString().equalsIgnoreCase("success"))
                        {
                            System.out.println("home activity fetchProfileSettings response .. "+result);
                            response = obj.get("result").toString();
                            System.out.println("resposne .. "+response);
                            //Intent lIntent = new Intent(), ProfileSettings1.class);
                            //lIntent.putExtra("report",methodType);
                            //lIntent.putExtra("response",obj.get("result").toString());
                            //lIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            //startActivity(lIntent);
                        }
                    }


                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if(response != null)
        {
            JSONParser parser = new JSONParser();
            try {
                jsonObject = (JSONObject) parser.parse(response);
                if(jsonObject.containsKey("userName"))
                    profileName.setText(jsonObject.get("userName").toString());
                if(jsonObject.containsKey("guardianName"))
                    profileGuardian.setText(jsonObject.get("guardianName").toString());


                if(jsonObject.containsKey("dateOfBirth"))
                {
                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
                    formatter.setTimeZone(TimeZone.getTimeZone("IST"));
                    Date result1 = null;
                    try {
                        result1 = formatter.parse(jsonObject.get("dateOfBirth").toString());
                        profileDOB.setText(new SimpleDateFormat("dd MMM yyyy").format(result1));

                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }

                }

                if(jsonObject.containsKey("emailAddress"))
                    profileMail.setText(jsonObject.get("emailAddress").toString());
                if(jsonObject.containsKey("phoneNumber"))
                    profilePhoneNo.setText(jsonObject.get("phoneNumber").toString());
                if(jsonObject.containsKey("city"))
                    profileCity.setText(jsonObject.get("city").toString());
                if(jsonObject.containsKey("pinCode"))
                    profilePinCode.setText(jsonObject.get("pinCode").toString());



                if(jsonObject.get("domainList") != null && jsonObject.get("domainList").toString().length() > 0)
                {
                    Gson gson1 = new Gson();
                    Type listType1 = new TypeToken<List<DomainJson>>(){}.getType();
                    domainJsonList = gson1.fromJson(jsonObject.get("domainList").toString(), listType1);
                    System.out.println("domainJsonList .. "+domainJsonList.size());
                    domainAdapter = new ArrayAdapter<DomainJson>(getContext(),
                            R.layout.header_spinner, domainJsonList);
                    domainAdapter.notifyDataSetChanged();
                    profileState.setAdapter(domainAdapter);

                    if(jsonObject.containsKey("state"))
                    {
                        int spinnerPosition = domainJsonList.indexOf(new DomainJson(jsonObject.get("state").toString(),true));
                        profileState.setSelection(spinnerPosition);
                    }
                }

                if(jsonObject.get("sportList") != null && jsonObject.get("sportList").toString().length() > 0)
                {
                    Gson gson1 = new Gson();
                    Type listType1 = new TypeToken<List<SportJson>>(){}.getType();
                    sportJsonList = gson1.fromJson(jsonObject.get("sportList").toString(), listType1);
                    System.out.println("sportJsonList .. "+sportJsonList.size()+" ... "+jsonObject.get("interestedProjectName"));
                    ArrayList<String> selectedProject;
                    if(jsonObject.containsKey("interestedProjectName"))
                        selectedProject = (ArrayList<String>) jsonObject.get("interestedProjectName");
                    else
                        selectedProject = new ArrayList<String>();
                    sportAdapter = new SportAdapter(getContext(), getActivity(),sportJsonList,selectedProject);


                    sportAdapter.notifyDataSetChanged();
                    profileSport.setAdapter(sportAdapter);




                }

                if(jsonObject.get("languageList") != null && jsonObject.get("languageList").toString().length() > 0)
                {
                    Gson gson1 = new Gson();
                    Type listType1 = new TypeToken<List<LanguageJson>>(){}.getType();
                    languageJsonList = gson1.fromJson(jsonObject.get("languageList").toString(), listType1);
                    System.out.println("languageJsonList .. "+languageJsonList.size());
                    ArrayList<String> selectedProject;
                    if(jsonObject.containsKey("languages"))
                        selectedProject = (ArrayList<String>) jsonObject.get("languages");
                    else
                        selectedProject = new ArrayList<String>();

                    languageAdapter = new LanguageAdapter(getContext(), getActivity(),languageJsonList,selectedProject);
                    languageAdapter.notifyDataSetChanged();

                    profileLanguage.setAdapter(languageAdapter);


                }
                if(jsonObject.get("certificationList") != null && jsonObject.get("certificationList").toString().length() > 0)
                {
                    Gson gson1 = new Gson();
                    Type listType1 = new TypeToken<List<CertificationJson>>(){}.getType();
                    certificationJsonList = gson1.fromJson(jsonObject.get("certificationList").toString(), listType1);
                    System.out.println("certificationList .. "+certificationJsonList.size());
                    ArrayList<String> selectedProject;
                    if(jsonObject.containsKey("certifications"))
                        selectedProject = (ArrayList<String>) jsonObject.get("certifications");
                    else
                        selectedProject = new ArrayList<String>();
                    certificationAdapter = new CertificationAdapter(getContext(), getActivity(),certificationJsonList,selectedProject);
                    certificationAdapter.notifyDataSetChanged();
                    profileCertification.setAdapter(certificationAdapter);
                }

                System.out.println("travelAssignment .. "+jsonObject.get("travelAssignment"));
                if(jsonObject.containsKey("travelAssignment"))
                {
                    if(jsonObject.get("travelAssignment").equals(true))
                        travelAssignment.setChecked(true);
                }



            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        profileName.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.hasText(profileName);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });


        profilePhoneNo.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if(profilePhoneNo.getText().toString().length() > 0)
                    Validation.isPhoneNumber(profilePhoneNo, false);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        profileDOB.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.isDate(profileDOB, false);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        profileContinue.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if ( checkValidation () )
                {
                    step1Layout.setVisibility(View.GONE);
                    step2Layout.setVisibility(View.VISIBLE);
                }
                else
                {
                    mUtil.toastMessage("Form contains error",getContext());
                }



            }
        });

        step1Previous.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                step1Layout.setVisibility(View.VISIBLE);
                step2Layout.setVisibility(View.GONE);
            }
        });

        profileCancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                ///Intent lIntent = new Intent(getContext(), HomeActivity.class);
                //lIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                //startActivity(lIntent);


            }
        });

        profileUpdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                JSONObject param = new JSONObject();

                int stateIndex = domainJsonList.indexOf(new DomainJson(profileState.getSelectedItem().toString()));


                param.put("userId", mSession.ypGetUserID());
                param.put("userName", profileName.getText().toString());
                param.put("guardianName", profileGuardian.getText().toString());
                param.put("phoneNumber", profilePhoneNo.getText().toString());
                param.put("dateOfBirth", profileDOB.getText().toString());
                param.put("city", profileCity.getText().toString());
                param.put("state", domainJsonList.get(stateIndex).getUserId());
                param.put("pinCode", profilePinCode.getText().toString());
                param.put("languages",checkedLanguage );
                param.put("interestedProjectName",checkedSport );
                param.put("certifications",checkedCertification);
                if(travelAssignment.isChecked())
                    param.put("travelAssignment",true);
                else
                    param.put("travelAssignment",false);



                ArrayList<String> selectedProject;
                if(jsonObject.containsKey("interestedDomainName"))
                    selectedProject = (ArrayList<String>) jsonObject.get("interestedDomainName");
                else
                {
                    selectedProject = new ArrayList<String>();
                    selectedProject.add("");
                }

                param.put("interestedDomainName", selectedProject);



                System.out.println("params .. "+param.toJSONString());
                String params= "profileUpdateViaApp?";
                String queryparams= "caller="+Constants.caller+"&apiKey="+Constants.apiKey+"&data="+param;
                String requestType = "POST";
                String methodType = "profileUpdateViaApp";
                asyncTask = new ExecuteURLTask(getContext(),"profileUpdateViaApp");
                asyncTask.delegate = ProfileFragment.this;
                asyncTask.execute(params,queryparams,requestType);

                // TODO Auto-generated method stub

            }
        });

        if(languageAdapter != null)
        {

            languageAdapter.setOnDataChangeListener(new LanguageAdapter.OnDataChangeListener() {

                public void onDataLanguage(ArrayList<String> lMessageIDList) {
                    // TODO Auto-generated method stub
                    checkedLanguage = lMessageIDList;

                }
            });
        }

        if(certificationAdapter != null)
        {
            certificationAdapter.setOnDataChangeListener(new CertificationAdapter.OnDataChangeListener() {

                public void onDataCertification(ArrayList<String> lMessageIDList) {
                    // TODO Auto-generated method stub
                    checkedCertification = lMessageIDList;
                }
            });
        }

        if(sportAdapter != null)
        {
            sportAdapter.setOnDataChangeListener(new SportAdapter.OnDataChangeListener() {

                public void onDataSport(ArrayList<String> lMessageIDList) {
                    // TODO Auto-generated method stub
                    checkedSport = lMessageIDList;

                }
            });
        }


        return view;

    }

    protected boolean checkValidation() {
        boolean ret = true;

        if (!Validation.hasText(profileName)) ret = false;
        if(profilePhoneNo.getText().toString().length() > 0)
        {
            if (!Validation.isPhoneNumber(profilePhoneNo, false)) ret = false;
        }
        if (!Validation.isDate(profileDOB, false)) ret = false;

        return ret;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void processFinish(String result, String methodName) {
        if(result != null)
        {
            if(methodName.equalsIgnoreCase(""))
            {
                JSONParser jsonParser = new JSONParser();
                try {
                    JSONObject obj = (JSONObject) jsonParser.parse(result);
                    if(obj.containsKey("status"))
                    {
                        if(obj.get("status").toString().equalsIgnoreCase("success"))
                        {
                            System.out.println("home activity fetchProfileSettings response .. "+result);
                            //Intent lIntent = new Intent(), ProfileSettings1.class);
                            //lIntent.putExtra("report",methodType);
                            //lIntent.putExtra("response",obj.get("result").toString());
                            //lIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            //startActivity(lIntent);
                        }
                    }


                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            else if(methodName.equalsIgnoreCase("profileUpdateViaApp"))
            {
                JSONParser parser = new JSONParser();
                JSONObject resultObj;
                try {
                    resultObj = (JSONObject) parser.parse(result);
                    if(resultObj.containsKey("status"))
                    {
                        if(resultObj.get("status").toString().equalsIgnoreCase("success"))
                        {
                            if(resultObj.containsKey("response"))
                                mUtil.toastMessage(resultObj.get("response").toString(),getContext());

                        }
                        else
                        {
                            if(resultObj.containsKey("response"))
                                mUtil.toastMessage(resultObj.get("response").toString(),getContext());
                        }
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
