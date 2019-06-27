package com.example.day_02_zuoyeone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

//侯登昊
public class MainActivity extends AppCompatActivity {

    /**
     * Hello World!
     */
    private RecyclerView xr;
    private Myadpater mMyadpater;
    private TextView mTv;
    private CheckBox mCbA;
    private RelativeLayout mx;
    private Shipingbean.DataBean mShipingbean = new Shipingbean.DataBean();
    private int mSum;
    private ArrayList<Shipingbean.DataBean> mDataBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApIService.url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        retrofit.create(ApIService.class).initCai().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Shipingbean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Shipingbean shipingbean) {
                        List<Shipingbean.DataBean> data = shipingbean.getData();
                        mMyadpater.addData(data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private static final String TAG = "MainActivity";

    private void initView() {
        xr = (RecyclerView) findViewById(R.id.xr);
        xr.setLayoutManager(new LinearLayoutManager(this));
        mMyadpater = new Myadpater(this);
        xr.setAdapter(mMyadpater);
        mTv = (TextView) findViewById(R.id.tv);
        mCbA = (CheckBox) findViewById(R.id.cb_a);
        mx = (RelativeLayout) findViewById(R.id.x);

        mMyadpater.setOnClickCheck(new Myadpater.onClickCheck() {
            @Override
            public void oncheckbox(Shipingbean.DataBean shiping) {
                mShipingbean = shiping;
                int num = shiping.getNum();
                boolean checked = shiping.isChecked();
                gnum(num , checked);
            }
        });

        mDataBeans = new ArrayList<>();
        mCbA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    mDataBeans.clear();
                    mDataBeans.addAll(mMyadpater.shuju);
                    for (int i = 0; i < mMyadpater.shuju.size(); i++) {
                        mMyadpater.shuju.get(i).setChecked(true);
                    }
                    mMyadpater.notifyDataSetChanged();
                }else {
                    mDataBeans.clear();
                    for (int i = 0; i < mMyadpater.shuju.size(); i++) {
                        mMyadpater.shuju.get(i).setChecked(false);
                    }
                    mMyadpater.notifyDataSetChanged();
                }
                int b = 0 ;
                for (int i = 0; i < mDataBeans.size(); i++) {
                    int num = mDataBeans.get(i).getNum();
                    b+=num;
                }
                mTv.setText(b+"");
            }
        });
    }

    private void gnum(int num, boolean checked) {
        if (checked){
            mSum += num;
        }else {
            mSum-=num;
        }
        mTv.setText(mSum+"");
    }
}
