package com.puuga.testwifiscanner;

import android.app.Activity;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private RecyclerView rcvList;
    private String[] datas;
    private List<ScanResult> scanResults;
    private DataAdapter dataAdapter;
    OnDataItemClickListener mListener;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        datas = Data.datas;
        scanResults = new ArrayList<>();

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        initInstances(view);

        return view;
    }

    private void initInstances(View view) {
        rcvList = (RecyclerView) view.findViewById(R.id.rcv_list);
        rcvList.setLayoutManager(new LinearLayoutManager(getActivity()));

//        rcvList.setAdapter(new DataAdapter(datas));
        dataAdapter = new DataAdapter(getActivity(), scanResults);
        dataAdapter.setOnDataItemClickListener(new DataAdapter.OnDataItemClickListener() {
            @Override
            public void onDataItemClick(ScanResult scanResult, int position) {
                mListener.onDataItemClick(scanResult, position);
            }
        });
        rcvList.setAdapter(dataAdapter);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnDataItemClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }
    }

    void setScanResult(List<ScanResult> list) {
        scanResults.clear();
        scanResults.addAll(list);
        rcvList.getAdapter().notifyDataSetChanged();
    }

    public interface OnDataItemClickListener {
        void onDataItemClick(ScanResult scanResult, int position);
    }
}
