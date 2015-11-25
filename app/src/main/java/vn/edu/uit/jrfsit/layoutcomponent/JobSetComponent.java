package vn.edu.uit.jrfsit.layoutcomponent;

import android.support.v7.internal.widget.TintImageView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by LeDuy on 11/16/2015.
 */
public class JobSetComponent {
    AppCompatTextView tvJobTile;

    public AppCompatTextView getTvJobTile() {
        return tvJobTile;
    }

    public JobSetComponent(AppCompatTextView tvJobTile, AppCompatTextView tvJobCompany, AppCompatTextView tvJobLocation,
                           AppCompatTextView tvJobSalary, AppCompatTextView tvJobDateEx, AppCompatTextView tvJob_detail_description,
                           AppCompatTextView tvJob_detail_require, AppCompatTextView tvJob_detail_benifit, AppCompatTextView tvJob_detail_contact,
                           AppCompatTextView tvJob_detail_link, ImageView ivJobLogo, LinearLayout lnBlank,LinearLayout lnScreen,RatingBar rbRating,
                           LinearLayout btSave,TextView btJobSaveText ) {
        this.tvJobTile = tvJobTile;
        this.tvJobCompany = tvJobCompany;
        this.tvJobLocation = tvJobLocation;
        this.tvJobSalary = tvJobSalary;
        this.tvJobDateEx = tvJobDateEx;
        this.tvJob_detail_description = tvJob_detail_description;
        this.tvJob_detail_require = tvJob_detail_require;
        this.tvJob_detail_benifit = tvJob_detail_benifit;
        this.tvJob_detail_contact = tvJob_detail_contact;
        this.tvJob_detail_link = tvJob_detail_link;
        this.ivJobLogo = ivJobLogo;
        this.lnBlank = lnBlank;
        this.lnScreen=lnScreen;
        this.rbRating = rbRating;
        this.btJobSave =btSave;
        this.btJobSaveText = btJobSaveText;
    }

    public void setTvJobTile(AppCompatTextView tvJobTile) {
        this.tvJobTile = tvJobTile;
    }

    public AppCompatTextView getTvJobCompany() {
        return tvJobCompany;
    }

    public void setTvJobCompany(AppCompatTextView tvJobCompany) {
        this.tvJobCompany = tvJobCompany;
    }

    public AppCompatTextView getTvJobLocation() {
        return tvJobLocation;
    }

    public void setTvJobLocation(AppCompatTextView tvJobLocation) {
        this.tvJobLocation = tvJobLocation;
    }

    public AppCompatTextView getTvJobSalary() {
        return tvJobSalary;
    }

    public void setTvJobSalary(AppCompatTextView tvJobSalary) {
        this.tvJobSalary = tvJobSalary;
    }

    public AppCompatTextView getTvJobDateEx() {
        return tvJobDateEx;
    }

    public void setTvJobDateEx(AppCompatTextView tvJobDateEx) {
        this.tvJobDateEx = tvJobDateEx;
    }

    public RatingBar getRbRating() {
        return rbRating;
    }

    public void setRbRating(RatingBar rbRating) {
        this.rbRating = rbRating;
    }

    public AppCompatTextView getTvJobRating() {
        return tvJobRating;
    }

    public void setTvJobRating(AppCompatTextView tvJobRating) {
        this.tvJobRating = tvJobRating;
    }

    public TintImageView getIvJobRating() {
        return ivJobRating;
    }

    public void setIvJobRating(TintImageView ivJobRating) {
        this.ivJobRating = ivJobRating;
    }

    public AppCompatButton getBtJobShare() {
        return btJobShare;
    }

    public void setBtJobShare(AppCompatButton btJobShare) {
        this.btJobShare = btJobShare;
    }

    public LinearLayout getBtJobSave() {
        return btJobSave;
    }

    public void setBtJobSave(LinearLayout btJobSave) {
        this.btJobSave = btJobSave;
    }

    public AppCompatTextView getTvJob_detail_description() {
        return tvJob_detail_description;
    }

    public void setTvJob_detail_description(AppCompatTextView tvJob_detail_description) {
        this.tvJob_detail_description = tvJob_detail_description;
    }

    public AppCompatTextView getTvJob_detail_require() {
        return tvJob_detail_require;
    }

    public void setTvJob_detail_require(AppCompatTextView tvJob_detail_require) {
        this.tvJob_detail_require = tvJob_detail_require;
    }

    public AppCompatTextView getTvJob_detail_benifit() {
        return tvJob_detail_benifit;
    }

    public void setTvJob_detail_benifit(AppCompatTextView tvJob_detail_benifit) {
        this.tvJob_detail_benifit = tvJob_detail_benifit;
    }

    public AppCompatTextView getTvJob_detail_contact() {
        return tvJob_detail_contact;
    }

    public void setTvJob_detail_contact(AppCompatTextView tvJob_detail_contact) {
        this.tvJob_detail_contact = tvJob_detail_contact;
    }

    public AppCompatTextView getTvJob_detail_link() {
        return tvJob_detail_link;
    }

    public void setTvJob_detail_link(AppCompatTextView tvJob_detail_link) {
        this.tvJob_detail_link = tvJob_detail_link;
    }

    public ImageView getIvJobLogo() {
        return ivJobLogo;
    }

    public void setIvJobLogo(ImageView ivJobLogo) {
        this.ivJobLogo = ivJobLogo;
    }

    AppCompatTextView tvJobCompany;
    AppCompatTextView tvJobLocation;
    AppCompatTextView tvJobSalary;
    AppCompatTextView tvJobDateEx;
    RatingBar rbRating;
    AppCompatTextView tvJobRating;
    TintImageView ivJobRating;
    AppCompatButton btJobShare;
    LinearLayout btJobSave;

    public TextView getBtJobSaveText() {
        return btJobSaveText;
    }

    public void setBtJobSaveText(TextView btJobSaveText) {
        this.btJobSaveText = btJobSaveText;
    }

    TextView btJobSaveText;
    AppCompatTextView tvJob_detail_description;
    AppCompatTextView tvJob_detail_require;
    AppCompatTextView tvJob_detail_benifit;
    AppCompatTextView tvJob_detail_contact;
    AppCompatTextView tvJob_detail_link;
    ImageView ivJobLogo;
    LinearLayout lnBlank;
    LinearLayout lnScreen;


    public LinearLayout getLnBlank() {
        return lnBlank;
    }
    public void setLnBlank(LinearLayout lnBlank) {
        this.lnBlank = lnBlank;
    }
    public LinearLayout getLnScreen() {
        return lnScreen;
    }
    public void setLnScreen(LinearLayout lnScreen) {
        this.lnScreen = lnScreen;
    }
}
