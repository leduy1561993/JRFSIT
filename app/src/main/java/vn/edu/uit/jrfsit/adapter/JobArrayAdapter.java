package vn.edu.uit.jrfsit.adapter;

/**
 * Created by LeDuy on 10/23/2015.
 */
import java.util.List;
import android.app.Activity;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import vn.edu.uit.jrfsit.R;
import vn.edu.uit.jrfsit.entity.JobSearch;
import vn.edu.uit.jrfsit.layoutcomponent.PbAndImage;
import vn.edu.uit.jrfsit.thread.DownloadImageTask;

public class JobArrayAdapter extends ArrayAdapter<JobSearch> {

    private final Activity context;
    private final List<JobSearch> list;

    public JobArrayAdapter(Activity context, List<JobSearch> list) {
        super(context, R.layout.list_layout, list);
        this.context = context;
        this.list = list;
    }

    static class ViewHolder {
        protected AppCompatTextView tvCongViec;
        protected AppCompatTextView tvCongTy;
        protected AppCompatTextView tvDiaDiem;
        protected AppCompatTextView tvInformation;
        protected ImageView image;
        protected ProgressBar pb;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.list_layout, null);
            final ViewHolder viewHolder = new ViewHolder();

            viewHolder.tvCongViec = (AppCompatTextView) view.findViewById(R.id.etDscv_TenCV);
            viewHolder.tvCongTy = (AppCompatTextView) view.findViewById(R.id.etDscv_TenCT);
            viewHolder.tvDiaDiem = (AppCompatTextView) view.findViewById(R.id.etDscv_ViTri);
            viewHolder.tvInformation = (AppCompatTextView) view.findViewById(R.id.etDscv_information);
            viewHolder.image = (ImageView) view.findViewById(R.id.image);
            viewHolder.image.setVisibility(View.GONE);
            viewHolder.pb = (ProgressBar) view.findViewById(R.id.progressBar1);
            view.setTag(viewHolder);
        } else {
            view = convertView;
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.tvCongViec.setText(list.get(position).getJobName());
        holder.tvCongTy.setText(list.get(position).getCompany());
        holder.tvDiaDiem.setText(list.get(position).getLocation());
        holder.tvInformation.setText(list.get(position).getInformation());
        holder.image.setTag(list.get(position).getLogoURL());
        holder.image.setId(position);
        PbAndImage pb_and_image = new PbAndImage();
        pb_and_image.setImg(holder.image);
        pb_and_image.setPb(holder.pb);
        DownloadImageTask downloadImageTask = new DownloadImageTask(getContext());
        downloadImageTask.execute(pb_and_image);
        return view;
    }
}