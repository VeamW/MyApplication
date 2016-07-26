package com.example.apractice;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by viper02 on 2016/7/26.
 */
public class TabFragment extends Fragment {

    private View mView;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private MyAdapter mMyAdapter;


    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mRefreshLayout.isRefreshing()) {
                mRefreshLayout.setRefreshing(false);
                mMyAdapter.notifyDataSetChanged();
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_tabb, container, false);
        }
        mRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swiperefresh);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                final ExecutorService service = Executors.newSingleThreadExecutor();
                service.submit(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(">>>", "正在执行");
                        try {
                            service.awaitTermination(3, TimeUnit.SECONDS);
                            Log.e(">>>", "执行成功");
                            mHandler.sendEmptyMessage(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            service.shutdown();
                        }
                    }
                });


            }
        });



        mMyAdapter = new MyAdapter(getActivity());

        mMyAdapter.setOnclickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                Snackbar.make(mView, "dianle", Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClickListener(View view, int position) {

            }
        });
        mRecyclerView.setAdapter(mMyAdapter);
        return mView;
    }
}
