package vn.edu.uit.jrfsit.thread;

/**
 * Created by LeDuy on 10/23/2015.
 */
import java.io.InputStream;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import vn.edu.uit.jrfsit.R;
import vn.edu.uit.jrfsit.layoutcomponent.PbAndImage;
import vn.edu.uit.jrfsit.service.BitmapService;

public class DownloadImageTask extends AsyncTask<PbAndImage, Void, Bitmap> {

    private Context mContext;

    public DownloadImageTask(Context context) {
        mContext = context;
    }
    ImageView imageView = null;
    ProgressBar pb = null;

    protected Bitmap doInBackground(PbAndImage... pb_and_images) {
        this.imageView = (ImageView)pb_and_images[0].getImg();
        this.pb = (ProgressBar)pb_and_images[0].getPb();
        return getBitmapDownloaded((String) imageView.getTag());
    }

    protected void onPostExecute(Bitmap result) {
        imageView.setVisibility(View.VISIBLE);
        pb.setVisibility(View.GONE);
        imageView.setImageBitmap(result);
    }

    /** This function downloads the image and returns the Bitmap **/
    private Bitmap getBitmapDownloaded(String url) {
        Bitmap bitmap;
        BitmapService bitmapService =new BitmapService();
        bitmap = bitmapService.getBitmapFromURL(url);
       if(bitmap==null){
           bitmap = BitmapFactory.decodeResource( mContext.getResources(), R.drawable.logo_defaut);
       }
        bitmap = getResizedBitmap(bitmap,100, 140);
        return bitmap;
    }

    /** decodes image and scales it to reduce memory consumption **/
    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);
        // RECREATE THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }
}