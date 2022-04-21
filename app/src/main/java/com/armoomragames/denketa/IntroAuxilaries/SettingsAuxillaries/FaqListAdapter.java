package com.armoomragames.denketa.IntroAuxilaries.SettingsAuxillaries;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.armoomragames.denketa.IntroAuxilaries.DModelDictionary;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.IAdapterCallback;

import java.util.ArrayList;


public class FaqListAdapter extends RecyclerView.Adapter<FaqListAdapter.ViewHolder>  {

    private static View lastClicked = null;
    private final ArrayList<DModelDictionary> mData;
    private final Context mContext;
    private final IAdapterCallback iAdapterCallback;


    public FaqListAdapter(Context mContext, ArrayList<DModelDictionary> mData,
                          IAdapterCallback iAdapterCallback) {
        this.mContext = mContext;
        this.mData = mData;

        this.iAdapterCallback = iAdapterCallback;


    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lay_faq_item, null);


        return new FaqListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.txv_word.setText(mData.get(position).getWord());
        holder.txvDetails.setText(mData.get(position).getMeaning());

        holder.imvExtended.setOnClickListener(v -> {
            iAdapterCallback.onAdapterEventFired(IAdapterCallback.EVENT_A, position);
            if (holder.rlBottom.getVisibility() == View.GONE)
                holder.rlBottom.setVisibility(View.VISIBLE);
            else holder.rlBottom.setVisibility(View.GONE);
        });
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rlParent,rlBottom;
        ImageView imvExtended;
        TextView txv_word;
        TextView txvDetails;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);




           rlParent = itemView.findViewById(R.id.lay_dictionary_ll_parrent);
           rlBottom = itemView.findViewById(R.id.lay_dictionary_rlBottom);
            imvExtended = itemView.findViewById(R.id.lay_dictionary_ll_extended);
           txv_word = itemView.findViewById(R.id.lay_dictionary_txv_word);
            txvDetails = itemView.findViewById(R.id.txvDetails);

        }
    }
}
