package com.iplayon.umpire.fragment;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iplayon.umpire.R;
import com.iplayon.umpire.adapter.UpcomingTournamentAdapter;
import com.iplayon.umpire.modal.DomainJson;
import com.iplayon.umpire.modal.SportJson;
import com.iplayon.umpire.modal.TournamentJson;
import com.iplayon.umpire.util.Constants;
import com.iplayon.umpire.util.ExecuteURLTask;
import com.iplayon.umpire.util.SessionManager;
import com.iplayon.umpire.util.Util;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UpcomingTournFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UpcomingTournFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpcomingTournFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQUEST_WRITE_STORAGE = 112;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    ArrayList<TournamentJson> pastTournamentList;
    private Util mUtil;
    private SessionManager mSession;
    UpcomingTournamentAdapter tournamentAdapter;
    ListView tournamentListView;
    ExecuteURLTask asyncTask;

    ArrayList<DomainJson> domainJsonList = new ArrayList<>();
    ArrayList<SportJson> sportJsonList = new ArrayList<>();

    public UpcomingTournFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpcomingTournFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpcomingTournFragment newInstance(String param1, String param2) {
        UpcomingTournFragment fragment = new UpcomingTournFragment();
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
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_WRITE_STORAGE: {

                if (grantResults.length == 0
                        || grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED) {

                    mUtil.toastMessage("Permission has been denied by user",getContext());

                } else {
                    mUtil.toastMessage("Permission has been granted by user",getContext());



                }
                return;
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_upcoming_tourn, container, false);
        //tournamentListView
        pastTournamentList = new ArrayList<TournamentJson>();
        mUtil = new Util(getActivity().getApplicationContext(), getActivity());
        mSession = new SessionManager(getActivity().getApplicationContext());
        tournamentAdapter = new UpcomingTournamentAdapter(getActivity().getApplicationContext(), getActivity(), pastTournamentList, domainJsonList, sportJsonList);
        tournamentListView = (ListView) view.findViewById(R.id.tournamentListView);
        tournamentListView.setAdapter(tournamentAdapter);


        String params= "listOfUpcomingEventsBasedOnRole?caller="+Constants.caller+"&apiKey="+Constants.apiKey+"&userId="+mSession.ypGetUserID();
        String requestType="GET";
        String methodType = "upcomingTournament";





        asyncTask = new ExecuteURLTask(getContext(),methodType);
        try {
            String result = asyncTask.execute(params,"", requestType).get();
            System.out.println("upcoming result new .."+result);
            if(result != null)
            {
                JSONParser jsonParser = new JSONParser();



                    JSONArray array = (JSONArray)jsonParser.parse(result);
                    if(array != null && array.size() == 0)
                    {
                        Toast.makeText(getContext(), "No upcoming tournaments",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {

                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<TournamentJson>>() {}.getType();
                        pastTournamentList = gson.fromJson(array.toString(), listType);
                        tournamentAdapter = new UpcomingTournamentAdapter(getActivity().getApplicationContext(), getActivity(), pastTournamentList,domainJsonList,sportJsonList);
                        tournamentAdapter.notifyDataSetChanged();
                        tournamentListView.setAdapter(tournamentAdapter);



                    }








            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return view;
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
