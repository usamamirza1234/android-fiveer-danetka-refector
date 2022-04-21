package com.armoomragames.denketa.IntroAuxilaries;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.IAdapterCallback;

import java.util.ArrayList;


public class DictionaryRCVAdapter extends RecyclerView.Adapter<DictionaryRCVAdapter.ViewHolder> {

    private static View lastClicked = null;
    private ArrayList<DModelDictionary> mData;
    private final Context mContext;
    private final IAdapterCallback iAdapterCallback;


    public DictionaryRCVAdapter(Context mContext, ArrayList<DModelDictionary> mData,
                                IAdapterCallback iAdapterCallback) {
        this.mContext = mContext;
        this.mData = mData;

        this.iAdapterCallback = iAdapterCallback;


    }



    public void filterList(ArrayList<DModelDictionary> filterllist) {
        // below line is to add our filtered
        // list in our course array list.
        mData = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DictionaryRCVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lay_dictionary_item, null);


        return new DictionaryRCVAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final DictionaryRCVAdapter.ViewHolder holder, final int position) {

        holder.txv_word.setText(mData.get(position).getWord());
        holder.txv_meaning.setText(mData.get(position).getMeaning());

        holder.rlLow.setOnClickListener(v -> {
            iAdapterCallback.onAdapterEventFired(IAdapterCallback.EVENT_A, position);
            if (holder.rlLowExtended.getVisibility() == View.GONE)
                holder.rlLowExtended.setVisibility(View.VISIBLE);
            else holder.rlLowExtended.setVisibility(View.GONE);
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
        RelativeLayout rlLow;
        LinearLayout rlLowExtended;
        TextView txv_word;
        TextView txv_meaning;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            rlLow = itemView.findViewById(R.id.lay_dictionary_ll_parrent);
            rlLowExtended = itemView.findViewById(R.id.lay_dictionary_ll_extended);
            txv_word = itemView.findViewById(R.id.lay_dictionary_txv_word);
            txv_meaning = itemView.findViewById(R.id.lay_dictionary_txv_meaning);


        }
    }


//    private static View lastClicked = null;
//    private final IAdapterCallback iAdapterCallback;
//    private final Context mContext;
//    private final ArrayList<DModelDictionary> mData;
//    private final LayoutInflater mInflater;
//    ViewHolder viewHolder;
//
//
//    public DictionaryListAdapter(Context _mContext, IAdapterCallback iAdapterCallback, ArrayList<DModelDictionary> _mData) {
//        this.iAdapterCallback = iAdapterCallback;
//        this.mContext = _mContext;
//        this.mData = _mData;
//        mInflater = LayoutInflater.from(_mContext);
//
//
//    }
//
//    @Override
//    public int getCount() {
//        return mData.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return position;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        if (convertView == null) {
//            convertView = mInflater.inflate(R.layout.lay_dictionary_item, null);
//            viewHolder = new ViewHolder();
//
//            viewHolder.rlLow = convertView.findViewById(R.id.lay_dictionary_ll_parrent);
//            viewHolder.rlLowExtended = convertView.findViewById(R.id.lay_dictionary_ll_extended);
//            viewHolder.txv_word = convertView.findViewById(R.id.lay_dictionary_txv_word);
//            viewHolder.txv_meaning = convertView.findViewById(R.id.lay_dictionary_txv_meaning);
//
//
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//
//
//        viewHolder.txv_word.setText(mData.get(position).getWord());
//        viewHolder.txv_meaning.setText(mData.get(position).getMeaning());
//
//        viewHolder.rlLow.setOnClickListener(v -> {
//            iAdapterCallback.onAdapterEventFired(IAdapterCallback.EVENT_A, position);
//            View parentRow = (View) v.getParent();
//            ViewHolder viewHolder = (ViewHolder) parentRow.getTag();
//
//            if (viewHolder.rlLowExtended.getVisibility() != View.VISIBLE) {
//                //clear last clicked image
////                if (lastClicked != null) {
////
////                    ViewHolder lastHolder = (ViewHolder) lastClicked.getTag();
////                    lastHolder.rlLowExtended.setVisibility(View.GONE);
////                }
//            viewHolder.rlLowExtended.setVisibility(View.VISIBLE);
//                lastClicked = parentRow;
//            }
//        });
//
//
//        return convertView;
//    }
//
//
//    public static class ViewHolder {
//
//        RelativeLayout rlLow;
//        LinearLayout rlLowExtended;
//        TextView txv_word;
//        TextView txv_meaning;
//
//
//    }
}
