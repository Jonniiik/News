package com.eugene.newstest.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eugene.newstest.NewsActivity;
import com.eugene.newstest.R;
import com.eugene.newstest.common.Common;
import com.eugene.newstest.model.RootObject;
import com.squareup.picasso.Picasso;

public class AdapterNews extends RecyclerView.Adapter<AdapterNews.MyViewHolder> {

    private Context mContext;
    private RootObject mRootObject;

    public AdapterNews(Context mContext, RootObject mRootObject) {
        this.mContext = mContext;
        this.mRootObject = mRootObject;
    }

    @NonNull
    @Override
    public AdapterNews.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_list_news, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterNews.MyViewHolder holder, final int position) {
        holder.textViewNameNews.setText(new StringBuffer(String.valueOf(mRootObject.getArticles().get(position).getTitle())));
        holder.textViewDescriptions.setText(new StringBuffer(String.valueOf(mRootObject.getArticles().get(position).getDescription())));
        holder.textViewData.setText(new StringBuffer(Common.convertUnixToDate(mRootObject.getArticles().get(position).getPublishedAt())));
        Picasso.get().load(mRootObject.getArticles().get(position).getUrlToImage()).into(holder.imageViewImageNews);
        holder.linearLayoutListNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NewsActivity.class);
                intent.putExtra("LinkNews", mRootObject.getArticles().get(position).getUrl());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mRootObject.getArticles().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNameNews;
        ImageView imageViewImageNews;
        TextView textViewDescriptions;
        TextView textViewData;
        LinearLayout linearLayoutListNews;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNameNews = (TextView) itemView.findViewById(R.id.textViewNameNews);
            imageViewImageNews = (ImageView) itemView.findViewById(R.id.imageViewImageNews);
            textViewDescriptions = (TextView) itemView.findViewById(R.id.textViewDescriptions);
            textViewData = (TextView) itemView.findViewById(R.id.textViewData);
            linearLayoutListNews = (LinearLayout) itemView.findViewById(R.id.linearLayoutListNews);
        }
    }
}
