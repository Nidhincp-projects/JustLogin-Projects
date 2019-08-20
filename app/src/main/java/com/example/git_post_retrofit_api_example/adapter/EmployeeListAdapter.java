package com.example.git_post_retrofit_api_example.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.git_post_retrofit_api_example.R;
import com.example.git_post_retrofit_api_example.pojo.EmployeeDetails;
import com.example.git_post_retrofit_api_example.utils.CircleImageView;

import java.util.ArrayList;

public class EmployeeListAdapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<EmployeeDetails> alertList;

    public EmployeeListAdapter(Context context, ArrayList<EmployeeDetails> myDataset) {
        this.context = context;
        this.alertList = myDataset;
    }
    @Override
    public EmployeeListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_item_details, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, final int i) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        EmployeeDetails m = alertList.get(i);
        final ViewHolder holder = (ViewHolder) holder1;
        holder.tv_Name.setText(m.getFullName());
        holder.tv_Designation.setText(m.getDesignation());
        if (!m.getImage().equals("")) {
            Glide.with(context)
                    .load(m.getImage())
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            Drawable d = context.getResources().getDrawable(R.drawable.user_profile_avatar);
                            d.setBounds(0, 0, 80, 80);
                            holder.img_user.setImageDrawable(d);
                            return false;
                        }
                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(holder.img_user);
        } else {
            Drawable d = context.getResources().getDrawable(R.drawable.user_profile_avatar);
            d.setBounds(0, 0, 80, 80);
            holder.img_user.setImageDrawable(d);
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
       CircleImageView img_user;
       TextView tv_Name, tv_Designation;
        public ViewHolder(View convertView) {
            super(convertView);
            img_user = convertView.findViewById(R.id.img_user);
            tv_Name = convertView.findViewById(R.id.tv_Name);
            tv_Designation = convertView.findViewById(R.id.tv_Designation);
            itemView.setTag(itemView);
        }
    }
    @Override
    public int getItemCount() {
        return alertList.size() > 0 ? alertList.size() : 0;
    }
}