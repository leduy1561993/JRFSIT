// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package vn.edu.uit.jrfsit.thread;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import org.json.JSONException;
import vn.edu.uit.jrfsit.entity.Account;
import vn.edu.uit.jrfsit.entity.Job;
import vn.edu.uit.jrfsit.layoutcomponent.JobSetComponent;
import vn.edu.uit.jrfsit.preferences.AccountPreferences;
import vn.edu.uit.jrfsit.service.BitmapService;
import vn.edu.uit.jrfsit.service.JobService;

public class GetJobTask extends AsyncTask<JobSetComponent, Void, Job>
{

    private Account account;
    private AccountPreferences accountPreferences;
    private Context mContext;
    Job job;
    String jobID;
    JobSetComponent jobSetComponent=null;
    ProgressDialog pDialog;
    BitmapService bitmapService;
    JobService jobService;
    Bitmap image;

    public GetJobTask(Context context, String jobId)
    {
        jobSetComponent = null;
        mContext = context;
        jobID = jobId;
        accountPreferences = new AccountPreferences(context);
        account = accountPreferences.getAccount();
    }

    @Override
    protected Job doInBackground(JobSetComponent... params)
    {
        jobSetComponent = params[0];
        try
        {
            job = jobService.getJob(jobID, account.getUserId());
            if (job != null && job.getLogo() != null)
            {
                image = bitmapService.getBitmapFromURL(job.getLogo());
            }
        }catch (JSONException e) {
            e.printStackTrace();
            job =null;
        }
        return job;
    }

    @Override
    protected void onPostExecute(Job job)
    {
        if (job != null)
        {
            jobSetComponent.getLnBlank().setVisibility(View.INVISIBLE);
            jobSetComponent.getIvJobLogo().setImageBitmap(image);
            jobSetComponent.getTvJobTile().setText(job.getJobName());
            jobSetComponent.getTvJobCompany().setText(job.getCompany());
            jobSetComponent.getTvJobLocation().setText(job.getLocation());
            jobSetComponent.getTvJobSalary().setText(job.getSalary());
            jobSetComponent.getTvJobDateEx().setText(job.getExpired());
            jobSetComponent.getTvJob_detail_description().setText(job.getDescription());
            jobSetComponent.getTvJob_detail_require().setText(job.getRequirement());
            jobSetComponent.getTvJob_detail_benifit().setText(job.getBenifit());
            jobSetComponent.getTvJob_detail_link().setText(job.getSource());
            jobSetComponent.getLnScreen().setVisibility(View.VISIBLE);
            if (job.getRate() >= 0.0F)
            {
                jobSetComponent.getRbRating().setRating(job.getRate());
                jobSetComponent.getRbRating().setTag(String.valueOf(job.getRate()));
            } else
            {
                jobSetComponent.getRbRating().setTag("-1");
            }
            if (job.getIsSave() == 0)
            {
                jobSetComponent.getBtJobSave().setTag("0");
            } else
            if (job.getIsSave() == 1)
            {
                jobSetComponent.getBtJobSave().setTag("1");
            } else
            {
                jobSetComponent.getBtJobSaveText().setText("Đã lưu");
                jobSetComponent.getBtJobSave().setTag("2");
            }
        }
        pDialog.dismiss();
    }

    @Override
    protected void onPreExecute()
    {
        pDialog = new ProgressDialog(mContext);
        bitmapService = new BitmapService();
        jobService = new JobService();
        pDialog.setMessage("Đang lấy dữ liệu");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.show();
    }
}
