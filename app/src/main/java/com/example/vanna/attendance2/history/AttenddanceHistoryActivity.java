package com.example.vanna.attendance2.history;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.vanna.attendance2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AttenddanceHistoryActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private AttendanceArrayAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attenddance_history);

        initLayout();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_view);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(this);

        // setup adapter
        Attendance[] data = getAttendanceData();
        adapter = new AttendanceArrayAdapter(this, data);
        adapter.setAttendanceAdapterListener(new AttendanceAdapterListener() {
            @Override
            public void onStatusClick(int position) {
                Log.d("AttendanceActivity: ", "on Listener, received position: " + position);
                visibleLayout(position);
            }
        });
        recyclerView.setAdapter(adapter);

        // setup layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        loadAttendancesFromServer();
    }

    private void initLayout(){
        TextView buttonClose = (TextView)findViewById(R.id.statusClose);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                invisibleLayout();
            }
        });
    }

    private void invisibleLayout(){
        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.statusDetailLayout);
        relativeLayout.setVisibility(View.INVISIBLE);
    }

    private void visibleLayout(int position){
        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.statusDetailLayout);
        relativeLayout.setVisibility(View.VISIBLE);

        TextView textView = (TextView)findViewById(R.id.statusDetailView);
        textView.setText("Clicked on position: " + position);

    }

    private Attendance[] getAttendanceData() {
        //return DBConnector.getInstance(this).getAttendances();
        return new Attendance[0];
    }

    private void loadAttendancesFromServer(){
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://www.dropbox.com/s/0x4pql0iayvp0er/attendance-list.json?dl=1";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                swipeRefreshLayout.setRefreshing(false);
                try {
                    JSONArray jsonArray = response.getJSONArray("payload");
                    Attendance[] attendances = new Attendance[jsonArray.length()];
                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Attendance attendance = Attendance.fromJsonObject(jsonObject);
                        attendances[i] = attendance;
                    }
                    adapter.setData(attendances);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(AttenddanceHistoryActivity.this, "Error reading data from server.", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AttenddanceHistoryActivity.this, "Error loading data from server.", Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onRefresh() {
        // do anything maybe check for update, ....
        swipeRefreshLayout.setRefreshing(false);
    }
}
