package com.meinvwo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {


    private static final String URL_A = "URL_A";
    @BindView(R.id.pageTv)
    TextView mPageTv;
    @BindView(R.id.prePage)
    AppCompatButton prePage;
    @BindView(R.id.nextPage)
    AppCompatButton nextPage;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private int page = 0;

    private BeautyListAdapter mBeautyListAdapter = new BeautyListAdapter(new OnItemClick() {
        @Override
        public void itemClick(int position, ItemBo itemBo) {
            startActivity(new Intent(MainActivity.this, BigBeautyActivity.class)
                    .putExtra(URL_A, itemBo.getUrl()));
        }
    });

    private Subscription mSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mBeautyListAdapter);
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.YELLOW);
        mSwipeRefreshLayout.setEnabled(false);
    }

    @OnClick(R.id.nextPage)
    void onNextClick() {
        loadpage(++page);
        if (page == 2) {
            prePage.setEnabled(true);
        }
    }

    @OnClick(R.id.prePage)
    void onPreClick() {
        loadpage(--page);
        if (page == 1) {
            prePage.setEnabled(false);
        }
    }

    private void loadpage(final int page) {
        mSwipeRefreshLayout.setRefreshing(true);
        unsubscripe();
        mSubscription = NetworkUtils.getGankNetApi()
                .getGankBos(10, page)
                .map(new Func1<GankBo, List<ItemBo>>() {
                    @Override
                    public List<ItemBo> call(GankBo gankBo) {
                        List<BeautyBo> beautyBos = gankBo.getBeautyBos();
                        List<ItemBo> itemBos = new ArrayList<>(beautyBos.size());
                        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'");
                        SimpleDateFormat outFormat = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
                        for (BeautyBo beauty : beautyBos) {
                            ItemBo itemBo = new ItemBo();
                            try {
                                Date date = inputFormat.parse(beauty.getCreatedAt());
                                String format = outFormat.format(date);
                                itemBo.setTime(format);
                            } catch (ParseException e) {
                                e.printStackTrace();
                                itemBo.setTime("unknown date");
                            }
                            itemBo.setUrl(beauty.getUrl());
                            itemBos.add(itemBo);
                        }
                        return itemBos;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ItemBo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(MainActivity.this, R.string.loading_failed, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(List<ItemBo> itemBos) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mPageTv.setText("第0页".replace("0", page + ""));
                        mBeautyListAdapter.setItemBos(itemBos);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unsubscripe();
    }

    private void unsubscripe() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.isUnsubscribed();
        }
    }
}
