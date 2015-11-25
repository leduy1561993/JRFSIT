package vn.edu.uit.jrfsit.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.text.method.Touch;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;

import vn.edu.uit.jrfsit.R;
import vn.edu.uit.jrfsit.adapter.AutoCompleteAdapter;
import vn.edu.uit.jrfsit.entity.User;
import vn.edu.uit.jrfsit.layoutcomponent.AutoCompletePlace;

/**
 * Created by LeDuy on 11/20/2015.
 */
public class  EditUserProfileActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
    //region defination
    AppCompatEditText mEtName;
    AppCompatEditText mEtEmail;
    AppCompatEditText mEtPhone;
    AppCompatEditText mEtBirtday;
    AppCompatSpinner mSnGender;
    AppCompatButton mBtSave;
    AppCompatButton mBtCancel;

    AppCompatAutoCompleteTextView mAtAddress;
    AppCompatButton mBtAddress;
    int PLACE_PICKER_REQUEST = 1;
    private GoogleApiClient mGoogleApiClient;
    public static final String PARAM_EXTRA_QUERY = "place_picker_extra_query";

    //definition
    private int day =15;
    private int month=06;
    private int year =1993;

    //get user
    User user;
    //endregion

    /**
     *
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_edit_profile);
        initControlOnView();
        initListener();
        load();
    }

    /**
     * initControlOnView
     */
    private void initControlOnView(){
        mEtName = (AppCompatEditText) findViewById(R.id.et_name_profile);
        mEtEmail= (AppCompatEditText) findViewById(R.id.et_email_profile);
        mEtPhone = (AppCompatEditText) findViewById(R.id.et_mobilenumber_profile);
        mAtAddress = (AppCompatAutoCompleteTextView) findViewById(R.id.tv_location__profile);
        mBtAddress = (AppCompatButton) findViewById(R.id.bt_location__profile);
        mEtBirtday = (AppCompatEditText) findViewById(R.id.et_birthday_profile);
        mSnGender = (AppCompatSpinner) findViewById(R.id.sn_gender_profile);
        mBtSave = (AppCompatButton) findViewById(R.id.btSave_Profile);
        mBtCancel = (AppCompatButton) findViewById(R.id.btCancel_Profile);
    }

    /**
     * initListener
     */
    private void initListener(){
        mEtBirtday.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showDialog(1);
                return false;
            }
        });
        mBtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                User userReturn = getUserFromView();
                returnIntent.putExtra("result", userReturn);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });

        mBtAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    Intent intent = builder.build(EditUserProfileActivity.this.getApplicationContext());
                    intent.putExtra(PARAM_EXTRA_QUERY, "&components=country:gh&types=(cities)");
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
        mAtAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AutoCompletePlace selection = (AutoCompletePlace) parent.getItemAtPosition(position);
                mAtAddress.setText(getCity(selection.getDescription()));
            }
        });
    }

    /**
     * load
     */
    private void load(){
        user = new User();
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        loadDatoToView(user);
        String[] gender =new String[]{"Nam","Nữ"};
        ArrayAdapter<String> gen = new ArrayAdapter<String>(EditUserProfileActivity.this,R.layout.spinner_item,gender);
        mSnGender.setAdapter(gen);

        //location
        //region Google adapter
        AutoCompleteAdapter mAdapter = new AutoCompleteAdapter(this);
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) EditUserProfileActivity.this)
                .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) EditUserProfileActivity.this)
                .build();
        mGoogleApiClient.connect();
        mAdapter.setGoogleApiClient(mGoogleApiClient);
        mAtAddress.setAdapter(mAdapter);

        //endregion
    }

    //region getdate
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 1:
                return new DatePickerDialog(this, datePickerListener,
                        year, (month), day);
        }
        return null;
    }

    /**
     * DatePickerDialog
     */
    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            //region set
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;
            String strDay=String.valueOf(day);
            String strMonth = String.valueOf(month+1);
            if(day<10){
                strDay="0"+strDay;
            }
            if(month<10){
                strMonth="0"+strMonth;
            }
            mEtBirtday.setText(new StringBuilder().append(year)
                    .append("-").append(strMonth).append("-").append(strDay)
                    .append(" "));
            //endregion
        }
    };
    //endregion get date

    /**
     *
     * @param user loadDatoToView
     */
    private void loadDatoToView(User user){
        mEtName.setText(user.getFullName());
        mEtEmail.setText(user.getEmail());
        mEtPhone.setText(user.getPhone());
        mAtAddress.setText(user.getAddress());
        mEtBirtday.setText(user.getBirthday());
        if(user.getGender().equals("Nam")){
            mSnGender.setSelection(0);
        }else {
            mSnGender.setSelection(1);
        }
    }

    /**
     *
     * @return getUserFromView
     */
    private User getUserFromView(){
        User user= new User();
        user.setFullName(mEtName.getText().toString());
        user.setEmail(mEtEmail.getText().toString());
        user.setPhone(mEtPhone.getText().toString());
        user.setAddress(mAtAddress.getText().toString());
        user.setBirthday(mEtBirtday.getText().toString());
        user.setGender(mSnGender.getSelectedItem().toString());
        return user;
    }

    /**
     * onActivityResult
     * @param requestCode
     * @param resultCode
     * @param data
     */
    //region google place
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == -1) {
                Place place = PlacePicker.getPlace(data, EditUserProfileActivity.this);
                mAtAddress.setText(getCity((String) place.getAddress()));
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
    //endregion
}
