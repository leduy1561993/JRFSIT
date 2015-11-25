package vn.edu.uit.jrfsit;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.widget.TintImageView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

/**
 * Created by LeDuy on 10/26/2015.
 */
public class Demo extends AppCompatActivity{
    int PLACE_PICKER_REQUEST = 1;
    RatingBar rb;
    AppCompatTextView tvJobRating;
    TintImageView ivJobRating;
    AppCompatButton btJobShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_job);
        rb = (RatingBar) findViewById(R.id.jobRating);

         tvJobRating = (AppCompatTextView) findViewById(R.id.tvJobRating);
         ivJobRating = (TintImageView) findViewById(R.id.ivJobRating);

         btJobShare = (AppCompatButton) findViewById(R.id.btJobShare);
         btJobShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "Công việc này thật là hấp đãn, hãy cũng thăm gia nào \n nguồn: leduy.com";// to do here
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, message);

                startActivity(Intent.createChooser(share, "Bạn muốn chia sẻ lên đâu?"));
            }
        });
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                if(rating<=1f){
                    ivJobRating.setImageResource(R.drawable.em1);
                    tvJobRating.setText("Không liên quan");
                }else if(rating<=2f){
                    ivJobRating.setImageResource(R.drawable.em2);
                    tvJobRating.setText("Không phù hợp");
                }else if(rating<=3f){
                    ivJobRating.setImageResource(R.drawable.em3);
                    tvJobRating.setText("Chấp nhận");
                }else if(rating<=4f){
                    ivJobRating.setImageResource(R.drawable.em4);
                    tvJobRating.setText("Khá phù hợp");
                }else if(rating<=5f){
                    ivJobRating.setImageResource(R.drawable.em5);
                    tvJobRating.setText("Rất phù hợp");
                }
            }

        });
    }
}
