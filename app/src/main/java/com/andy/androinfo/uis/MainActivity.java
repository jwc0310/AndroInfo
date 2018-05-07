package com.andy.androinfo.uis;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andy.androinfo.R;
import com.andy.androinfo.beans.TitleBean;
import com.andy.androinfo.utils.FileUtil;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView title_rv;
    LinearLayoutManager linearLayoutManager;
    TitleAdapter titleAdapter;
    ArrayList<TitleBean> titleBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FileUtil.checkFileExist("/data/data/");
        FileUtil.checkFileExist("/data/data/com.microvirt.market");
        FileUtil.checkFileExist("/data/data/com.microvirt.launcher");
        FileUtil.checkFileExist("/data/data/com.microvirt.bugeili");
        titleBeans = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TitleBean titleBean = new TitleBean();
            titleBean.setSubTitle("AA " + i);
            titleBeans.add(titleBean);
        }

        title_rv = (RecyclerView) findViewById(R.id.andy_title_rv);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        title_rv.addItemDecoration(new DividerItemDecoration(this, LinearLayout.HORIZONTAL));
        title_rv.setLayoutManager(linearLayoutManager);
        titleAdapter = new TitleAdapter(titleBeans);
        title_rv.setAdapter(titleAdapter);

    }

    public interface TitleClickListener {
        public void titleClick(final TitleHolder holder, int pos);
    }

    private class TitleAdapter extends RecyclerView.Adapter<TitleHolder> {
        private ArrayList<TitleBean> titleBeans;
        private TitleClickListener clickListener;
        private int lastTitle;

        public TitleAdapter(ArrayList<TitleBean> titleBeans) {
            this.titleBeans = titleBeans;
            lastTitle = -1;
        }

        public void setClickListener(TitleClickListener clickListener) {
            this.clickListener = clickListener;
        }

        @Override
        public TitleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.andy_item_title, parent, false);
            TitleHolder holder = new TitleHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(final TitleHolder holder, final int position) {
            holder.subTitle_tv.setText(titleBeans.get(position).getSubTitle());
            if (lastTitle == -1 && position == 0) {
                lastTitle = 0;
                holder.subTitle_tv.setSelected(true);
            } else {
                holder.subTitle_tv.setSelected(false);
            }

            holder.subTitle_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!holder.subTitle_tv.isSelected()) {
                        holder.subTitle_tv.setSelected(true);
                        notifyItemChanged(lastTitle);
                        lastTitle = holder.getAdapterPosition();

                        if (null != clickListener) {
                            clickListener.titleClick(holder, position);
                        }
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return titleBeans.size();
        }
    }

    private class TitleHolder extends RecyclerView.ViewHolder {

        private TextView subTitle_tv;

        public TitleHolder(View itemView) {
            super(itemView);
            this.subTitle_tv = (TextView) itemView.findViewById(R.id.andy_item_title_id);
        }
    }
}
