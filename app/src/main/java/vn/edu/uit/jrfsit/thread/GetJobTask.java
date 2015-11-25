package vn.edu.uit.jrfsit.thread;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;

import org.json.JSONException;

import vn.edu.uit.jrfsit.constants.Param;
import vn.edu.uit.jrfsit.entity.Job;
import vn.edu.uit.jrfsit.layoutcomponent.JobSetComponent;
import vn.edu.uit.jrfsit.service.BitmapService;
import vn.edu.uit.jrfsit.service.JobService;

/**
 * Created by LeDuy on 11/13/2015.
 */
public class GetJobTask extends AsyncTask<JobSetComponent, Void, Job> {
    private Context mContext;
    public GetJobTask(Context context, String jobId) {
        mContext = context;
        this.jobID = jobId;
    }
    Job job;
    String jobID;
    JobSetComponent jobSetComponent=null;
    ProgressDialog pDialog;
    BitmapService bitmapService;
    JobService jobService;
    Bitmap image;

    @Override
    protected void onPreExecute() {
        // Showing progress dialog before sending http request
        pDialog = new ProgressDialog(mContext);
        bitmapService = new BitmapService();
        jobService = new JobService();
        pDialog.setMessage("Đang lấy dữ liệu");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.show();

    }

    @Override
    protected Job doInBackground(JobSetComponent... params) {
        jobSetComponent = params[0];
        try {
            job = jobService.getJob(jobID, Param.user.getUserId());
            if(job!=null&&job.getLogo()!=null){
                image = bitmapService.getBitmapFromURL(job.getLogo());
            }
            return job;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  null;
    }
    @Override
    protected void onPostExecute(Job result) {
        if(job!=null){
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
            //tvJob_detail_contact.setText(job.getSource());
            jobSetComponent.getTvJob_detail_link().setText(job.getSource());
            jobSetComponent.getLnScreen().setVisibility(View.VISIBLE);

            if(job.getRate()>=0){
                jobSetComponent.getRbRating().setRating(job.getRate());
                jobSetComponent.getRbRating().setTag(String.valueOf(job.getRate()));
            }else
                jobSetComponent.getRbRating().setTag("-1");

            if(job.getIsSave()==0){
                jobSetComponent.getBtJobSave().setTag("0");
            }else if(job.getIsSave()==1){
                jobSetComponent.getBtJobSave().setTag("1");
            }else {
                jobSetComponent.getBtJobSaveText().setText("Đã Lưu");
                jobSetComponent.getBtJobSave().setTag("2");
            }
        }
        pDialog.dismiss();
    }
}
