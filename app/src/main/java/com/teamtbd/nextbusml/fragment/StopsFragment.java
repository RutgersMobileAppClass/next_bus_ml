package com.teamtbd.nextbusml.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.teamtbd.nextbusml.MainActivity;
import com.teamtbd.nextbusml.R;
import com.teamtbd.nextbusml.model.Course;
import com.teamtbd.nextbusml.model.Stop;
import com.teamtbd.nextbusml.model.retrofit.ApiEndpoints;
import com.teamtbd.nextbusml.model.retrofit.RetrofitStop;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StopsFragment extends Fragment {
    private ArrayList<Stop> stops;
    private ArrayAdapter<Stop> adapter;
    private ListView stopsListView;

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

    @Override
    public void onStart() {
        super.onStart();
        setup();
    }

    private void setup() {
        // get stops by calling apis
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiEndpoints apiService =
                retrofit.create(ApiEndpoints.class);

        Call<List<RetrofitStop>> call = apiService.getStops("a");

        call.enqueue(new Callback<List<RetrofitStop>>() {
            @Override
            public void onResponse(Call<List<RetrofitStop>> call, Response<List<RetrofitStop>> response) {
                // we have the stops
                Log.d("RESPONSE", response.body().toString());
            }

            @Override
            public void onFailure(Call<List<RetrofitStop>> call, Throwable t) {
                // Log error here since request failed
            }
        });
        adapter = new StopsArrayAdapter();
        stopsListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    private class StopsArrayAdapter extends ArrayAdapter<Stop> {
        public StopsArrayAdapter() {
            super(getActivity(), R.layout.stops_list_item, stops);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null)
                view = getActivity().getLayoutInflater().inflate(R.layout.stops_list_item, parent, false);

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
