package com.teamtbd.nextbusml.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.teamtbd.nextbusml.R;
import com.teamtbd.nextbusml.model.Course;
import com.teamtbd.nextbusml.model.Stop;

import java.util.ArrayList;
import java.util.List;

public class StopsFragment extends Fragment {
    private ArrayList<Stop> stops;
    private ArrayAdapter<Stop> adapter;
    private ListView stopsListView;

    private OnFragmentInteractionListener mListener;

    public StopsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stops, container, false);

        stopsListView = (ListView) view.findViewById(R.id.stops_list_view);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        setup();
    }

    private void setup() {
        adapter = new StopsArrayAdapter();
        stopsListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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

    private class StopsArrayAdapter extends ArrayAdapter<Stop> {
        public StopsArrayAdapter() {
            super(getActivity(), R.layout.stops_list_item, stops);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.stops_list_item, parent, false);

            Stop stop = stops.get(position);

            TextView name = (TextView) view.findViewById(R.id.name_text_view);
            TextView busName = (TextView) view.findViewById(R.id.bus_name_text_view);
            TextView arrivals = (TextView) view.findViewById(R.id.arrivals_text_view);

            name.setText(stop.getName());
            busName.setText(stop.getBusName());
            arrivals.setText(stop.getArrivals().toString());

            return view;
        }

    }
}
