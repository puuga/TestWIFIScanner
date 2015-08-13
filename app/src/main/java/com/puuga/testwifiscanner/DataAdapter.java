package com.puuga.testwifiscanner;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by siwaweswongcharoen on 8/13/2015 AD.
 */
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {

    private Context context;
    private String[] datas;
    private List<ScanResult> scanResults;
    private OnDataItemClickListener listener;

    public DataAdapter(String[] datas) {
        this.datas = datas;
    }

    public DataAdapter(Context context, List<ScanResult> scanResults) {
        this.context = context;
        this.scanResults = scanResults;
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_wifi_data, viewGroup, false);
        return new DataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DataViewHolder dataViewHolder, final int i) {
//        dataViewHolder.tvSsid.setText(datas[i]);

        // TODO: 8/13/2015 AD
        final ScanResult scanResult = scanResults.get(i);
        dataViewHolder.tvSsid.setText(scanResult.SSID);
        dataViewHolder.tvSignal.setText(String.valueOf(scanResult.level));

        if (listener != null) {
            dataViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDataItemClick(scanResult, i);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
//        return datas.length;
        int size = scanResults == null ? 0 : scanResults.size();
        return size;
    }

    public class DataViewHolder extends RecyclerView.ViewHolder{

        public TextView tvSsid;
        public TextView tvSignal;

        public DataViewHolder(View itemView) {
            super(itemView);

            tvSsid = (TextView) itemView.findViewById(R.id.tv_ssid);
            tvSignal = (TextView) itemView.findViewById(R.id.tv_signal);
        }
    }

    public void setOnDataItemClickListener(OnDataItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnDataItemClickListener {
        void onDataItemClick(ScanResult scanResult, int position);
    }


}
