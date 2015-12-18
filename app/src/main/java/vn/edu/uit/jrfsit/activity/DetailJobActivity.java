// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package vn.edu.uit.jrfsit.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.widget.TintImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import vn.edu.uit.jrfsit.R;
import vn.edu.uit.jrfsit.entity.Account;
import vn.edu.uit.jrfsit.layoutcomponent.JobSetComponent;
import vn.edu.uit.jrfsit.preferences.AccountPreferences;
import vn.edu.uit.jrfsit.service.JobService;
import vn.edu.uit.jrfsit.service.RateService;
import vn.edu.uit.jrfsit.thread.GetJobTask;
import vn.edu.uit.jrfsit.utils.Utils;

public class DetailJobActivity extends AppCompatActivity
{

    //region defination
    private static String RATE_FAIL = "Lưu thất bại, kiểm tra kết nối";
    private static String RATE_SUCCESS = "Lưu thành công";
    Account account;
    AccountPreferences accountPreferences;
    LinearLayout btJobSave;
    TextView btJobSaveText;
    LinearLayout btJobShare;
    boolean isFirstIsert;
    ImageView ivJobLogo;
    TintImageView ivJobRating;
    JobService jobService;
    LinearLayout lnBlank;
    LinearLayout lnJob;
    private Toolbar mToolbar;
    private AppCompatTextView mTvJobCompany;
    private AppCompatTextView mTvJobTile;
    RateService rateService;
    RatingBar rbRating;
    private AppCompatTextView tvJobDateEx;
    private AppCompatTextView tvJobLocation;
    AppCompatTextView tvJobRating;
    private AppCompatTextView tvJobSalary;
    AppCompatTextView tvJob_detail_benifit;
    AppCompatTextView tvJob_detail_contact;
    AppCompatTextView tvJob_detail_description;
    AppCompatTextView tvJob_detail_link;
    AppCompatTextView tvJob_detail_require;

    //endregion

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);

        initControlOnView();
        initListener();

        //set value
        isFirstIsert = true;
        load();
    }

    /**
     * initControlOnView
     */
    private void initControlOnView()
    {
        mToolbar = (Toolbar) findViewById(R.id.toolbarJob);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getString(R.string.title_activity_job_detail));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTvJobTile = (AppCompatTextView) findViewById(R.id.tvJobTile);
        mTvJobCompany = (AppCompatTextView) findViewById(R.id.tvJobCompany);
        tvJobLocation = (AppCompatTextView) findViewById(R.id.tvJobLocation);
        tvJobSalary = (AppCompatTextView) findViewById(R.id.tvJobSalary);
        tvJobDateEx = (AppCompatTextView) findViewById(R.id.tvJobDateEx);
        rbRating = (RatingBar) findViewById(R.id.jobRating);
        btJobShare = (LinearLayout) findViewById(R.id.btJobShare);
        btJobSave = (LinearLayout) findViewById(R.id.btJobSave);
        btJobSaveText = (TextView) findViewById(R.id.btJobSaveText);
        tvJobRating = (AppCompatTextView) findViewById(R.id.tvJobRating);
        ivJobRating = (TintImageView) findViewById(R.id.ivJobRating);
        tvJob_detail_description = (AppCompatTextView) findViewById(R.id.tvJob_detail_description);
        tvJob_detail_require = (AppCompatTextView) findViewById(R.id.tvJob_detail_require);
        tvJob_detail_benifit = (AppCompatTextView) findViewById(R.id.tvJob_detail_benifit);
        tvJob_detail_contact = (AppCompatTextView) findViewById(R.id.tvJob_detail_contact);
        tvJob_detail_link = (AppCompatTextView) findViewById(R.id.tvJob_detail_link);
        ivJobLogo = (ImageView) findViewById(R.id.ivJobLogo);
        lnBlank = (LinearLayout) findViewById(R.id.ln_job_blank);
        lnJob = (LinearLayout) findViewById(R.id.ln_job_screen);
        lnJob.setVisibility(View.INVISIBLE);
    }

    /**
     * initListener
     */
    private void initListener()
    {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("1".equals(btJobSave.getTag())) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("result", "1");
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
                onBackPressed();
            }
        });
        btJobShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "Công việc này thật là hấp dẫn, hãy cũng thăm gia nào \n " +
                        "Nguồn: "+tvJob_detail_link.getText().toString();
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(share, "Bạn muốn chia sẻ lên đâu?"));
            }
        });
        rbRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                final float rate = rating;
                if (rating <= 1f) {
                    ivJobRating.setImageResource(R.drawable.em1);
                    tvJobRating.setText("Không phù hợp");
                } else if (rating <= 2f) {
                    ivJobRating.setImageResource(R.drawable.em2);
                    tvJobRating.setText("Có thể phù hợp");
                } else if (rating <= 3f) {
                    ivJobRating.setImageResource(R.drawable.em3);
                    tvJobRating.setText("Phù hợp");
                } else if (rating <= 4f) {
                    ivJobRating.setImageResource(R.drawable.em4);
                    tvJobRating.setText("Khá phù hợp");
                } else if (rating <= 5f) {
                    ivJobRating.setImageResource(R.drawable.em5);
                    tvJobRating.setText("Rất phù hợp");
                }
                if (fromUser) {
                    if (rbRating.getTag().equals("-1") && isFirstIsert) {
                        isFirstIsert = false;
                        new Thread(new Runnable() {
                            public void run() {
                                final boolean message = rateService.insertRate(account.getUserId(), getIntent().getStringExtra("JobId"), String.valueOf(rate));
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (message) {
                                            rbRating.setTag("1");
                                            Utils.print(DetailJobActivity.this, RATE_SUCCESS);
                                        } else {
                                            Utils.print(DetailJobActivity.this, RATE_FAIL);
                                        }
                                    }
                                });
                            }
                        }).start();
                    } else {
                        new Thread(new Runnable() {
                            public void run() {
                                final boolean message = rateService.updateRate(account.getUserId(), getIntent().getStringExtra("JobId"), String.valueOf(rate));
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (message) {
                                            rbRating.setTag("1");
                                            Utils.print(DetailJobActivity.this, RATE_SUCCESS);
                                        } else {
                                            Utils.print(DetailJobActivity.this, RATE_FAIL);
                                        }
                                    }
                                });
                            }
                        }).start();
                    }

                }
            }

        });

        btJobSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btJobSave.getTag().equals("0")) {
                    if (isFirstIsert) {
                        isFirstIsert = false;
                        new Thread(new Runnable() {
                            public void run() {
                                final boolean message = jobService.insertSaveJob(account.getUserId(), getIntent().getStringExtra("JobId"));
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (message) {
                                            btJobSaveText.setText("Đã Lưu");
                                            btJobSave.setTag("2");
                                            Utils.print(DetailJobActivity.this, "Đã lưu công việc này");
                                        } else {
                                            Utils.print(DetailJobActivity.this, "Lưu công việc thấy bại");
                                        }
                                    }
                                });
                            }
                        }).start();
                    } else {
                        new Thread(new Runnable() {
                            public void run() {
                                final boolean message = jobService.updateSaveJob(account.getUserId(), getIntent().getStringExtra("JobId"), "1");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (message) {
                                            btJobSaveText.setText("Đã Lưu");
                                            btJobSave.setTag("2");
                                            Utils.print(DetailJobActivity.this, "Đã lưu công việc này");
                                        } else {
                                            Utils.print(DetailJobActivity.this, "Lưu công việc thấy bại");
                                        }

                                    }
                                });
                            }
                        }).start();
                    }
                } else if (btJobSave.getTag().equals("2")) {
                    new Thread(new Runnable() {
                        public void run() {
                            final boolean message = jobService.updateSaveJob(account.getUserId(), getIntent().getStringExtra("JobId"), "0");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (message) {
                                        btJobSaveText.setText("Lưu");
                                        btJobSave.setTag("1");
                                        Utils.print(DetailJobActivity.this, "Đã bỏ lưu công việc này");
                                    } else {
                                        Utils.print(DetailJobActivity.this, "Hủy lưu công việc thấy bại");
                                    }

                                }
                            });
                        }
                    }).start();
                } else {
                    new Thread(new Runnable() {
                        public void run() {
                            final boolean message = jobService.updateSaveJob(account.getUserId(), getIntent().getStringExtra("JobId"), "1");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (message) {
                                        btJobSaveText.setText("Đã Lưu");
                                        btJobSave.setTag("2");
                                        Utils.print(DetailJobActivity.this, "Đã lưu công việc này");
                                    } else {
                                        Utils.print(DetailJobActivity.this, "Lưu công việc thấy bại");
                                    }

                                }
                            });
                        }
                    }).start();
                }
            }
        });
    }

    /**
     * load
     */
    void load()
    {
        rateService = new RateService();
        jobService = new JobService();
        accountPreferences = new AccountPreferences(this);
        account = accountPreferences.getAccount();

        GetJobTask getJobTask = new GetJobTask(this, getIntent().getStringExtra("JobId"));
        JobSetComponent jobSetComponent = new JobSetComponent(mTvJobTile, mTvJobCompany, tvJobLocation, tvJobSalary, tvJobDateEx, tvJob_detail_description, tvJob_detail_require, tvJob_detail_benifit, tvJob_detail_contact, tvJob_detail_link, ivJobLogo, lnBlank, lnJob, rbRating,btJobSave, btJobSaveText);
        getJobTask.execute(jobSetComponent);
    }
}
