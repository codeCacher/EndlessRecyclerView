package com.cs.endlessrecyclerviewtest;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.erv)
    EndlessRecyclerView erv;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initRecyclerView();
        firstFetchData();
        setRefreshListener();
    }

    private void setRefreshListener() {
        erv.setOnDropDownRefeshListener(new EndlessRecyclerView.OnDropDownRefeshListener() {
            @Override
            public void onDonDropDownRefesh() {
                NetUtils.simulatePostRequest(new NetUtils.CallBack() {
                    @Override
                    public void onSuccess() {
                        myAdapter.stringList.clear();
                        for (int i = 0; i < 50; i++) {
                            myAdapter.stringList.add(i + "");
                        }

                        erv.finishDropDownRefreshOnSuccess(MainActivity.this);
                    }

                    @Override
                    public void onFailure() {
                        erv.finishDropDownRefreshOnFailure(MainActivity.this);
                    }
                });
            }
        });

        erv.setOnPullUpRefeshListener(new EndlessRecyclerView.OnPullUpRefeshListener() {
            @Override
            public void onPullUpRefesh() {
                NetUtils.simulatePostRequest(new NetUtils.CallBack() {
                    @Override
                    public void onSuccess() {
                        for (int i = 0; i < 50; i++) {
                            myAdapter.stringList.add(i + "");
                        }
                        erv.finishPullUpRefreshOnSuccess(MainActivity.this);
                    }

                    @Override
                    public void onFailure() {
                        erv.finishPullUpRefreshOnFailure(MainActivity.this);
                    }
                });
            }
        });
    }

    private void firstFetchData() {
        NetUtils.simulatePostRequest(new NetUtils.CallBack() {
            @Override
            public void onSuccess() {
                myAdapter.stringList.clear();
                for (int i = 0; i < 50; i++) {
                    myAdapter.stringList.add(i + "");
                }
                erv.finishDropDownRefreshOnSuccess(MainActivity.this);
            }

            @Override
            public void onFailure() {
                erv.finishDropDownRefreshOnFailure(MainActivity.this);
            }
        });
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        erv.setLayoutManager(linearLayoutManager);
        ArrayList<String> mStringList = new ArrayList<>();
        myAdapter = new MyAdapter(this, mStringList);
        erv.setAdapter(myAdapter);
    }


    private class MyAdapter extends EndlessRecyclerViewAdapter<MyViewHolder> {
        ArrayList<String> stringList;

        MyAdapter(Context context, ArrayList<String> stringList) {
            super(context);
            this.stringList = stringList;
        }

        @Override
        public int getEndlessItemCount() {
            return stringList.size();
        }

        @Override
        public int getEndlessItemViewType(int position) {
            return 1;
        }

        @Override
        public MyViewHolder onCreateEndlessViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(getApplicationContext()).inflate(R.layout.recyclerview_item, null, false), 1);
        }

        @Override
        public void onBindEndlessViewHolder(MyViewHolder holder, int position) {
            holder.tv.setText(position + "");
        }

    }

    class MyViewHolder extends EndlessRecyclerViewHolder {

        @BindView(R.id.tv)
        TextView tv;

        MyViewHolder(View itemView, int viewType) {
            super(itemView, viewType);
            ButterKnife.bind(this, itemView);
        }
    }
}
