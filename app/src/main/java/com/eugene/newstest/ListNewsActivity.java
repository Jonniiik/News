package com.eugene.newstest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.eugene.newstest.Adapter.AdapterNews;
import com.eugene.newstest.Retrofit.IRetrofitClient;
import com.eugene.newstest.Retrofit.RetrofitClient;
import com.eugene.newstest.common.Common;
import com.eugene.newstest.model.Article;
import com.eugene.newstest.model.RootObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class ListNewsActivity extends AppCompatActivity {

    CompositeDisposable compositeDisposable;
    IRetrofitClient iRetrofit;

    AdapterNews mAdapter;
    RecyclerView rvListNews;

    RootObject rootObjectTest = new RootObject();
    final boolean[] isLoading = {false};

    LinearLayout linearLayoutCheckConnector;
    Button buttonRestartConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_news);

        initComponents();
        getInformationAdapterInto(1);
        onClick();
    }

    private void initComponents() {
        rvListNews = (RecyclerView) findViewById(R.id.rvListNews);
        rvListNews.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListNewsActivity.this, LinearLayoutManager.VERTICAL, false);
        rvListNews.setLayoutManager(linearLayoutManager);
        linearLayoutCheckConnector = (LinearLayout) findViewById(R.id.linearLayoutCheckConnector);
        buttonRestartConnection = (Button) findViewById(R.id.buttonRestartConnection);


        RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = linearLayoutManager.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();
                int firstVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();

                if (!isLoading[0]) {
                    if ((visibleItemCount + firstVisibleItems) >= totalItemCount
                            && firstVisibleItems >= 0
                            && totalItemCount >= 10) {
                        isLoading[0] = true;

                        if (Common.page > 0 && Common.page < 5) {
                            if (!isOnline()) {
                                linearLayoutCheckConnector.setVisibility(View.VISIBLE);
                            }
                            Common.page = Common.page + 1;
                            Log.e("myLogs", "onScrolled: " + Common.page);
                            getInformationAdapterInto(Common.page);
                        }
                    }
                }


            }
        };
        rvListNews.addOnScrollListener(onScrollListener);
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getRetrofit();
        iRetrofit = retrofit.create(IRetrofitClient.class);


    }

    private void getAdapter(RootObject rootObject) {
        if (mAdapter == null) {
            rootObjectTest = rootObject;
            mAdapter = new AdapterNews(ListNewsActivity.this, rootObjectTest);
            rvListNews.setAdapter(mAdapter);
        } else {
            List<Article> list = rootObjectTest.getArticles();
            list.addAll(rootObject.getArticles());
            rootObjectTest.setArticles(list);
            mAdapter.notifyDataSetChanged();
        }
    }

    //https://newsapi.org/v2/everything?q=android&from=2019-04-00&sortBy=publishedAt&apiKey=26eddb253e7840f988aec61f2ece2907&page=
    private void getInformationAdapterInto(final int page) {
        compositeDisposable.add(iRetrofit.getNews(String.valueOf(Common.from), Common.sortBy, Common.API_KEY, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RootObject>() {
                               @Override
                               public void accept(RootObject rootObject) throws Exception {
                                   isLoading[0] = false;
                                   Toast.makeText(ListNewsActivity.this, "" + page, Toast.LENGTH_SHORT).show();
                                   getAdapter(rootObject);

                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   Log.e("Test", Objects.requireNonNull(throwable.getMessage()));
                                   Toast.makeText(ListNewsActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                               }
                           }
                ));

    }

    protected boolean isOnline() {
        String cs = Context.CONNECTIVITY_SERVICE;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(cs);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    private void onClick() {
        buttonRestartConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnline()) {
                    linearLayoutCheckConnector.setVisibility(View.GONE);
                    getInformationAdapterInto(Common.page);
                }
            }
        });
    }


}
