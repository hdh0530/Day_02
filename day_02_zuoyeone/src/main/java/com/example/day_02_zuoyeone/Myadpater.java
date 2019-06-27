package com.example.day_02_zuoyeone;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2019/6/27.
 */

public class Myadpater extends RecyclerView.Adapter<Myadpater.ViewHolder> {
    private Context mContext;
    public ArrayList<Shipingbean.DataBean>  shuju = new ArrayList<>();

    public Myadpater(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Shipingbean.DataBean bean = shuju.get(position);
        Glide.with(mContext).load(bean.getPic()).into(holder.iv);
        holder.tv.setText(bean.getNum()+"");
        holder.checkbox.setChecked(bean.isChecked());
        holder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bean.setChecked(!bean.isChecked());
                if (mOnClickCheck != null) {
                    mOnClickCheck.oncheckbox(bean);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return shuju.size();
    }

    public void addData(List<Shipingbean.DataBean> data) {
        shuju.clear();
        shuju.addAll(data);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView iv;
        private final TextView tv;
        private final CheckBox checkbox;

        public ViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            tv = itemView.findViewById(R.id.tv);
            checkbox = itemView.findViewById(R.id.checkBox);

        }
    }

    public interface onClickCheck{
        void oncheckbox(Shipingbean.DataBean  shiping);
    }
    private onClickCheck mOnClickCheck;

    public void setOnClickCheck(onClickCheck onClickCheck) {
        this.mOnClickCheck = onClickCheck;
    }
}
