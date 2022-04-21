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


public class AnwserRegiltoRCVAdapter extends RecyclerView.Adapter<AnwserRegiltoRCVAdapter.ViewHolder> {

    private static final View lastClicked = null;
    private final Context mContext;
    private final IAdapterCallback iAdapterCallback;
    private final String[] mData;

    public AnwserRegiltoRCVAdapter(Context mContext, String[] mData,
                                   IAdapterCallback iAdapterCallback) {
        this.mContext = mContext;
        this.mData = mData;
        this.iAdapterCallback = iAdapterCallback;
    }

    @NonNull
    @Override
    public AnwserRegiltoRCVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lay_item_anwserregilto, null);
        return new AnwserRegiltoRCVAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final AnwserRegiltoRCVAdapter.ViewHolder holder, final int position) {

        holder.txv_word.setText(mData[position]);
    }
    @Override
    public int getItemCount() {
        return mData.length;
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
