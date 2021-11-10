package com.iplayon.umpire.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.iplayon.umpire.R;
import com.iplayon.umpire.adapter.PastTournamentAdapter;
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
 * {@link PastTournFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PastTournFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PastTournFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    ArrayList<TournamentJson> pastTournamentList;
    private Util mUtil;
    private SessionManager mSession;
    PastTournamentAdapter tournamentAdapter;
    ListView tournamentListView;
    ExecuteURLTask asyncTask;


    public PastTournFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PastTournFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PastTournFragment newInstance(String param1, String param2) {
        PastTournFragment fragment = new PastTournFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_past_tourn, container, false);
        //tournamentListView
        pastTournamentList = new ArrayList<TournamentJson>();
        mUtil = new Util(getActivity().getApplicationContext(), getActivity());
        mSession = new SessionManager(getActivity().getApplicationContext());
        tournamentAdapter = new PastTournamentAdapter(getActivity().getApplicationContext(), getActivity(), pastTournamentList);
        tournamentListView = (ListView) view.findViewById(R.id.tournamentListView);
        tournamentListView.setAdapter(tournamentAdapter);





        String params= "listOfPastTournaments?caller="+Constants.caller+"&apiKey="+Constants.apiKey+"&userId="+mSession.ypGetUserID();
        String requestType="GET";
        String methodType = "pastTournament";
        ExecuteURLTask asyncTask = new ExecuteURLTask(getContext(),methodType);


        try {
            String result = asyncTask.execute(params, "", requestType).get();
            System.out.println("past result new  .."+result);
            if(result != null)
            {
                JSONParser jsonParser = new JSONParser();



                    JSONArray jsonArray = (JSONArray) jsonParser.parse(result);
                    System.out.println(jsonArray.size());
                    System.out.println("333 .."+jsonArray.toString());
                    if (jsonArray.size() == 0) {
                        mUtil.toastMessage("No past tournaments", getContext());
                    } else {

                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<TournamentJson>>() {}.getType();
                        pastTournamentList = gson.fromJson(jsonArray.toString(), listType);
                        tournamentAdapter = new PastTournamentAdapter(getActivity().getApplicationContext(), getActivity(), pastTournamentList);
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
