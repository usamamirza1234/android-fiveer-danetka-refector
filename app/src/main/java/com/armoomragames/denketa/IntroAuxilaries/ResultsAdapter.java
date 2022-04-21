package com.armoomragames.denketa.IntroAuxilaries;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.Results.DModelResults;

import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.IAdapterCallback;


import java.util.ArrayList;


public class ResultsAdapter  extends BaseAdapter {

    private IAdapterCallback iAdapterCallback;
    private LayoutInflater inflater;
    private ArrayList<DModelResults> mData;
    private float cornerRadius;
    private Context context;

    public ResultsAdapter( Context context, ArrayList<DModelResults> mData,IAdapterCallback iAdapterCallback) {
        this.iAdapterCallback = iAdapterCallback;
        inflater = LayoutInflater.from(context);
        this.mData = mData;
        this.cornerRadius = dpToPx(context, 15);
        this.context = context;
    }
    public int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        context = parent.getContext();

        final ResultsAdapter.ViewHolder viewHolder;

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.lay_results_item, null);

            viewHolder = new ResultsAdapter.ViewHolder();
            viewHolder.txv_master = convertView.findViewById(R.id.lay_results_txv_master);
            viewHolder.txv_invest = convertView.findViewById(R.id.lay_results_txv_invest);
            viewHolder.txv_invest_numb = convertView.findViewById(R.id.lay_results_txv_invest_numb);
            viewHolder.txv_regilto_used = convertView.findViewById(R.id.lay_results_txv_regilto_used);
            viewHolder.txv_Time = convertView.findViewById(R.id.lay_results_txv_time);
            viewHolder.txv_Date = convertView.findViewById(R.id.lay_results_txv_date);
            viewHolder.rlLowExtended = convertView.findViewById(R.id.lay_results_ll_extended);




            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ResultsAdapter.ViewHolder) convertView.getTag();
        }

        viewHolder.txv_master.setText(mData.get(position).getMasterName()+"");
        viewHolder.txv_invest_numb.setText(mData.get(position).getInvestegorNumber()+"");
        viewHolder.txv_invest.setText(mData.get(position).getInvestigator()+"");
        viewHolder.txv_regilto_used.setText(mData.get(position).getRegiltor_used()+"");
        viewHolder.txv_Time.setText(mData.get(position).getTime()+"");
        viewHolder.txv_Date.setText(mData.get(position).getDate()+"");
        convertView.setOnClickListener(v -> {
            iAdapterCallback.onAdapterEventFired(IAdapterCallback.EVENT_A, position);
//            if (viewHolder.rlLowExtended.getVisibility() == View.GONE)
//                viewHolder.rlLowExtended.setVisibility(View.VISIBLE);
//            else viewHolder.rlLowExtended.setVisibility(View.GONE);
        });
        return convertView;
    }

    class ViewHolder {
        TextView txv_master;
        TextView txv_invest;
        TextView txv_regilto_used;
        TextView txv_Time;
        TextView txv_Date;
        TextView txv_invest_numb;
        LinearLayout rlLowExtended;
    }
}
