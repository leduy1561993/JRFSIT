// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package vn.edu.uit.jrfsit.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;

import vn.edu.uit.jrfsit.R;
import vn.edu.uit.jrfsit.activity.ChangePassActivity;
import vn.edu.uit.jrfsit.adapter.AutoCompleteAdapter;
import vn.edu.uit.jrfsit.entity.Account;
import vn.edu.uit.jrfsit.entity.Setting;
import vn.edu.uit.jrfsit.layoutcomponent.AutoCompletePlace;
import vn.edu.uit.jrfsit.preferences.AccountPreferences;
import vn.edu.uit.jrfsit.service.SettingService;
import vn.edu.uit.jrfsit.utils.Utils;
public class SettingFragment extends BaseFragment
    implements com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks, com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener
{

    public static final String PARAM_EXTRA_QUERY = "place_picker_extra_query";
    int PLACE_PICKER_REQUEST = 1;
    AppCompatAutoCompleteTextView acLocation;
    Account account;
    AccountPreferences accountPreferences;
    AppCompatButton btChangePass;
    AppCompatButton btLocation;
    AppCompatButton btSave;
    ProgressDialog dialog;
    private GoogleApiClient mGoogleApiClient;
    SwitchCompat scSetting;
    Setting setting;
    SettingService settingService;
    AppCompatSpinner snNumberJob;
    AppCompatSpinner snSpecicaly;
    AppCompatSpinner snTimeCommend;
    View v;
    AutoCompleteAdapter autocompleteadapter;

    @Override
    public View onCreateView(LayoutInflater layoutinflater, ViewGroup viewgroup, Bundle bundle)
    {
        v = layoutinflater.inflate(R.layout.content_setting, viewgroup, false);
        load();
        return v;
    }

    private void load()
    {
        super.loadActivity(R.string.title_activity_setting);
        dialog = ProgressDialog.show(activity, "", "Vui lòng chờ....", true);
        initControlOnView();
        settingService = new SettingService();
        setting = new Setting();
        account = new Account();
        accountPreferences = new AccountPreferences(activity);
        account = accountPreferences.getAccount();
        String[] array_skill = this.getResources().getStringArray(R.array.specialy_array);
        final ArrayAdapter<CharSequence>  tempAdapterSkill = new ArrayAdapter(activity
                ,R.layout.spinner_item1, array_skill) {
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
        tempAdapterSkill.setDropDownViewResource(R.layout.spinner_item1);
        snSpecicaly.setAdapter(tempAdapterSkill);
        String[] array_number_rec = this.getResources().getStringArray(R.array.number_rec_array);
        final ArrayAdapter<CharSequence> tempAdapterNumberJob = new ArrayAdapter(activity
                ,R.layout.spinner_item1, array_number_rec) {
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
        tempAdapterNumberJob.setDropDownViewResource(R.layout.spinner_item1);
        snNumberJob.setAdapter(tempAdapterNumberJob);

        String[] array_time_rec = this.getResources().getStringArray(R.array.time_rec_array);
        final ArrayAdapter<CharSequence> tempAdapterTimeRec = new ArrayAdapter(activity
                ,R.layout.spinner_item1, array_time_rec) {
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
        tempAdapterTimeRec.setDropDownViewResource(R.layout.spinner_item1);
        snTimeCommend.setAdapter(tempAdapterTimeRec);
        (new Thread(new Runnable() {
            @Override
            public void run()
            {
                setting = settingService.getSetting(account.getUserId());
                if (setting != null)
                {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run()
                        {
                            if ("1".equals(setting.getState()))
                            {
                                scSetting.setChecked(true);
                            }
                            if (!"0".equals(setting.getSkillID()))
                            {
                                snSpecicaly.setSelection(tempAdapterSkill.getPosition(setting.getSkill()));
                            } else
                            {
                                snSpecicaly.setSelection(1);
                            }
                            if (!setting.getLocation().equals("null") && !setting.getLocation().equals(""))
                            {
                                acLocation.setText(setting.getLocation());
                            } else
                            {
                                acLocation.setHint("M\u1EB7c \u0111\u1ECBnh");
                            }
                            snNumberJob.setSelection(tempAdapterNumberJob.getPosition(setting.getNumberRec()));
                            snTimeCommend.setSelection(tempAdapterTimeRec.getPosition(setting.getTimeRec()));
                        }
                    });
                } else
                {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run()
                        {
                            Utils.print(activity, "Thất bại, kiểm tra kết nối");
                        }
                    });
                }
                dialog.dismiss();
            }
        })).start();
        if (!scSetting.isChecked())
        {
            snSpecicaly.setEnabled(false);
            acLocation.setEnabled(false);
            btLocation.setEnabled(false);
            snNumberJob.setEnabled(false);
            snTimeCommend.setEnabled(false);
        }
        autocompleteadapter = new AutoCompleteAdapter(activity);
        mGoogleApiClient = (new
                com.google.android.gms.common.api.GoogleApiClient.Builder(activity))
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        mGoogleApiClient.connect();
        autocompleteadapter.setGoogleApiClient(mGoogleApiClient);
        acLocation.setAdapter(autocompleteadapter);
        initListener();
    }

    String getCity(String address) {
        String[] temp;
        String city = null;
        if (address != null) {
            temp = address.split(",");
            if (temp != null && temp.length >= 2) {
                city = temp[temp.length - 2];
            }
            return city;
        }
        return null;
    }

    public void initControlOnView()
    {
        scSetting = (SwitchCompat)v.findViewById(R.id.scSetting);
        btSave = (AppCompatButton)v.findViewById(R.id.btSetting_save);
        snSpecicaly = (AppCompatSpinner)v.findViewById(R.id.snSetting_specicaly);
        acLocation = (AppCompatAutoCompleteTextView)v.findViewById(R.id.acSetting_location);
        btLocation = (AppCompatButton)v.findViewById(R.id.btSetting_location);
        snNumberJob = (AppCompatSpinner)v.findViewById(R.id.snSetting_number_job);
        snTimeCommend = (AppCompatSpinner)v.findViewById(R.id.snSetting_time_recommend);
        btChangePass = (AppCompatButton)v.findViewById(R.id.btSetting_change_pass);
    }

    public void initListener()
    {
        btLocation.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        acLocation.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AutoCompletePlace selection = (AutoCompletePlace) parent.getItemAtPosition(position);
                acLocation.setText(getCity(selection.getDescription()));
            }
        });
        scSetting.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundbutton, boolean flag)
            {
                if (flag)
                {
                    snSpecicaly.setEnabled(true);
                    acLocation.setEnabled(true);
                    btLocation.setEnabled(true);
                    snNumberJob.setEnabled(true);
                    snTimeCommend.setEnabled(true);
                    return;
                } else
                {
                    snSpecicaly.setEnabled(false);
                    acLocation.setEnabled(false);
                    btLocation.setEnabled(false);
                    snNumberJob.setEnabled(false);
                    snTimeCommend.setEnabled(false);
                    return;
                }
            }
        });
        btSave.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(final View v)
            {
                final String checkSetting;
                String spec = snSpecicaly.getSelectedItem().toString();
                final String location = acLocation.getText().toString();
                final String numJobRec = snNumberJob.getSelectedItem().toString();
                final String timeRec = snTimeCommend.getSelectedItem().toString();
                String temSkillId;
                if (spec.equals("Mặc định"))
                {
                    temSkillId = "0";
                }else {
                    temSkillId= String.valueOf(snSpecicaly.getSelectedItemPosition()-1);
                }
                if (scSetting.isChecked())
                {
                    checkSetting = "1";
                } else
                {
                    checkSetting = "0";
                }
                final String skillId =temSkillId;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final boolean flag = settingService.updateSetting(account.getUserId(), location, numJobRec, skillId, timeRec, checkSetting);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (flag) {
                                    Utils.print(activity, "Cập nhật thành công");
                                } else {
                                    Utils.print(activity, "Cập nhật thất bại");
                                }
                                dialog.dismiss();
                            }
                        });
                    }
                }).start();
            }
        });
        btChangePass.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(activity.getApplicationContext(), ChangePassActivity.class));
            }
        });
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
                Place place = PlacePicker.getPlace(data, activity);
                acLocation.setText(getCity((String) place.getAddress()));
            }
        }
    }

    @Override
    public void onConnected(Bundle bundle)
    {
        if (mGoogleApiClient != null)
        {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionresult)
    {
    }

    @Override
    public void onConnectionSuspended(int i)
    {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected())
        {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mGoogleApiClient.connect();
    }
}
