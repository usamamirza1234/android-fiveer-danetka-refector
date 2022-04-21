package com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.GameSession;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.IAdapterCallback;

import java.util.ArrayList;


public class RegiltoRCVAdapter extends RecyclerView.Adapter<RegiltoRCVAdapter.ViewHolder> {

    private static final View lastClicked = null;
    private final Context mContext;
    private final IAdapterCallback iAdapterCallback;
    private final ArrayList<String> mData;

    public RegiltoRCVAdapter(Context mContext, ArrayList<String> mData,
                             IAdapterCallback iAdapterCallback) {
        this.mContext = mContext;
        this.mData = mData;
        this.iAdapterCallback = iAdapterCallback;
    }

    @NonNull
    @Override
    public RegiltoRCVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lay_item_regilto, null);
        return new RegiltoRCVAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final RegiltoRCVAdapter.ViewHolder holder, final int position) {

        holder.txv_word.setText(mData.get(position));
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
        TextView txv_word;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txv_word = itemView.findViewById(R.id.edt_Search); }
    }

}
