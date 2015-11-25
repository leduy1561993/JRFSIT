package vn.edu.uit.jrfsit.thread;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import vn.edu.uit.jrfsit.service.RateService;


/**
 * Created by LeDuy on 11/13/2015.
 */
public class RatingTask extends AsyncTask<Void, Void, Void> {
    private Context mContext;
    public RatingTask(Context context, String idUser,String jobId,String rate, boolean isInsert) {
        mContext = context;
        this.idUser = idUser;
        this.jobId = jobId;
        this.rate = rate;
        this.isInser = isInsert;
    }
    String idUser;
    String jobId;
    String rate;
    boolean isInser;
    ProgressDialog pDialog;
    RateService rateService;
    boolean resultRate;

    @Override
    protected void onPreExecute() {
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Rating...");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.show();
        rateService =new RateService();
    }

    @Override
    protected Void doInBackground(Void... params) {
        if(isInser){
            resultRate = rateService.insertRate(idUser,jobId,rate);
        }else {
            resultRate = rateService.updateRate(idUser,jobId,rate);
        }

        return null;
    }
    @Override
    protected void onPostExecute(Void result) {
        if(resultRate){
            Toast.makeText(mContext, "Đánh giá thành công", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(mContext, "Đánh giá thất bại, có thể do kết nối mạng, vui lòng thử lại", Toast.LENGTH_SHORT).show();
        }
        pDialog.dismiss();
    }
}
