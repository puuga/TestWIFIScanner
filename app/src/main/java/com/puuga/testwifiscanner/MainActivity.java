package com.puuga.testwifiscanner;

import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.github.pwittchen.reactivenetwork.library.ReactiveNetwork;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements MainActivityFragment.OnDataItemClickListener {

    CoordinatorLayout rootLayout;
    Toolbar toolbar;

    ReactiveNetwork reactiveNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initInstances();

        reactiveNetwork = new ReactiveNetwork();
    }

    private void initInstances() {
        rootLayout = (CoordinatorLayout) findViewById(R.id.rootLayout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void showSnackBar(String text) {
        Snackbar.make(rootLayout, text, Snackbar.LENGTH_LONG)
                .show();
    }

    protected void registerObserveWifiAccessPoints() {
        reactiveNetwork.observeWifiAccessPoints(this)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<List<ScanResult>>() {
                    @Override
                    public void call(List<ScanResult> scanResults) {
                        displayAccessPoints(scanResults);
                    }
                });
    }

    protected void unregisterObserveWifiAccessPoints() {
        reactiveNetwork.observeWifiAccessPoints(this).unsubscribeOn(Schedulers.io());
    }

    private void displayAccessPoints(List<ScanResult> scanResults) {
        MainActivityFragment fragment = (MainActivityFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fg_main);

        fragment.setScanResult(scanResults);
    }

    @Override
    protected void onResume() {
        super.onResume();

        registerObserveWifiAccessPoints();
    }

    @Override
    protected void onPause() {
//        unregisterObserveWifiAccessPoints();

        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDataItemClick(ScanResult scanResult, int position) {
        showSnackBar(scanResult.toString());
    }
}
