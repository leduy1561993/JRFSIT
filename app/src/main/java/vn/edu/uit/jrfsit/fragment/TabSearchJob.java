package vn.edu.uit.jrfsit.fragment;

/**
 * Created by LeDuy on 10/27/2015.
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.ArrayList;
import java.util.List;

import vn.edu.uit.jrfsit.R;
import vn.edu.uit.jrfsit.activity.ResultJobSearchActivity;
import vn.edu.uit.jrfsit.adapter.AutoCompleteAdapter;
import vn.edu.uit.jrfsit.dtosql.DatabaseHandler;
import vn.edu.uit.jrfsit.entity.JobSearch;
import vn.edu.uit.jrfsit.layoutcomponent.AutoCompletePlace;

/**
 * Created by hp1 on 21-01-2015.
 */
public class TabSearchJob extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    ////////////////
    AppCompatSpinner snStyleSearch;
    AppCompatSpinner snspecialy;
    AppCompatAutoCompleteTextView tvJob;
    AppCompatAutoCompleteTextView tvLocation;
    View v;
    AppCompatButton btSearch;
    private AppCompatActivity activity;

    int PLACE_PICKER_REQUEST = 1;
    private GoogleApiClient mGoogleApiClient;
    public static final String PARAM_EXTRA_QUERY = "place_picker_extra_query";

    AppCompatButton btLocation;
    DatabaseHandler db;
    List<JobSearch> list = new ArrayList<JobSearch>();
    public TabSearchJob() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.content_search_job, container, false);
        // -----------an ban phim
        setupUI(v.findViewById(R.id.lv_job_search));
        initControlOnView();
        initAdapter();
        initListener();
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == -1) {
                Place place = PlacePicker.getPlace(data, activity);
                tvLocation.setText(getCity((String) place.getAddress()));
            }
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionSuspended(int i) {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onPause() {
        super.onPause();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    String getCity(String address){
        String []temp;
        String city=null;
        if(address!=null) {
            temp = address.split(",");
            if(temp!=null&&temp.length>=2){
                city = temp[temp.length-2];
            }
            return city;
        }
        return null;
    }

    public void setupUI(View view) {
        if(!(view instanceof AppCompatAutoCompleteTextView)) {
            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(activity);
                    return false;
                }
            });
        }
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }
    public void hideSoftKeyboard(AppCompatActivity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        View v = activity.getWindow().getDecorView();
        v.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
    private void initControlOnView(){
        activity = (AppCompatActivity) getActivity();
        tvJob = (AppCompatAutoCompleteTextView) v.findViewById(R.id.tvJob_search_job_name);
        tvLocation = (AppCompatAutoCompleteTextView) v.findViewById(R.id.tvJob_search_location);
        snStyleSearch = (AppCompatSpinner) v.findViewById(R.id.snJob_search_style);
        snspecialy = (AppCompatSpinner) v.findViewById(R.id.snJob_search_specialy);
        btSearch = (AppCompatButton) v.findViewById(R.id.btJob_search);
        btLocation = (AppCompatButton) v.findViewById(R.id.btJob_location);

    }
    private void initListener(){
        //button press
        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!String.valueOf(tvJob.getText()).equals("") || !tvLocation.getText().toString().equals("")
                        /*|| !(snSalary.getSelectedItem().toString().equals("Mặc định")
                        || snSalary.getSelectedItem().toString().equals("Tìm theo mức lương"))*/
                        || !(snspecialy.getSelectedItem().toString().equals("Mặc định")
                        || snspecialy.getSelectedItem().toString().equals("Chọn chuyên ngành"))) {

                    Intent intent = new Intent(activity.getApplicationContext(), ResultJobSearchActivity.class);
                    intent.putExtra("jobName",tvJob.getText().toString());
                    intent.putExtra("location",tvLocation.getText().toString());
                    intent.putExtra("specialy",snspecialy.getSelectedItem().toString());
                    intent.putExtra("styleSearch",snStyleSearch.getSelectedItem().toString());
                    intent.putExtra("mode","0");
                    startActivity(intent);
                } else {
                    Toast.makeText(activity, "Từ khóa không hợp lệ", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int PLACE_PICKER_REQUEST = 1;
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    Intent intent = builder.build(activity.getApplicationContext());
                    intent.putExtra(PARAM_EXTRA_QUERY, "&components=country:gh&types=(cities)");
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
        tvLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AutoCompletePlace selection = (AutoCompletePlace) parent.getItemAtPosition(position);
                tvLocation.setText(getCity(selection.getDescription()));
            }
        });
    }
    private void initAdapter(){
        String[] array_style = getResources().getStringArray(R.array.style_search_array);
        ArrayAdapter<CharSequence> adapterStyleSearch = new ArrayAdapter(activity
                ,R.layout.spinner_item, array_style) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adapterStyleSearch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        snStyleSearch.setAdapter(adapterStyleSearch);

        String[] array_special = getResources().getStringArray(R.array.specialy_array);
        ArrayAdapter<CharSequence> adapter_specialy = new ArrayAdapter(activity,
                R.layout.spinner_item, array_special) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        // Specify the layout to use when the list of choices appears
        adapter_specialy.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        snspecialy.setAdapter(adapter_specialy);

        /**set auto complex*/
        db = new DatabaseHandler(activity);
        list = db.getAllContacts();
        String[] jobkey =new String[list.size()];
        int i=0;
        for (JobSearch key:list) {
            jobkey[i]=key.getJobName();
            i++;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_dropdown_item_1line, jobkey);
        tvJob.setAdapter(adapter);

        //Google adapter
        AutoCompleteAdapter mAdapter = new AutoCompleteAdapter(activity);
        mGoogleApiClient = new GoogleApiClient
                .Builder(activity)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
        mAdapter.setGoogleApiClient(mGoogleApiClient);
        tvLocation.setAdapter(mAdapter);
    }
}