package com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.GameSession;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.DModel_MyDenketa;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.IAdapterCallback;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MoreDenketaLsvAdapter extends BaseAdapter implements Filterable {
    RelativeLayout rlToolbar, rlBack, rlCross;
    private IAdapterCallback iAdapterCallback;
    private LayoutInflater inflater;
    private ArrayList<DModel_MyDenketa> mData;
    private ArrayList<DModel_MyDenketa> mDataFiltered;
    private float cornerRadius;
    private Context context;

    public MoreDenketaLsvAdapter(IAdapterCallback iAdapterCallback, Context context, ArrayList<DModel_MyDenketa> mData) {
        this.iAdapterCallback = iAdapterCallback;
        inflater = LayoutInflater.from(context);
        this.mData = mData;
        this.mDataFiltered = mData;
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

        final ViewHolder viewHolder;

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.lay_item_more_denketa, null);

            viewHolder = new ViewHolder();

            viewHolder.txvName  = convertView.findViewById(R.id.lay_item_my_denekta_txvName);
            viewHolder.imvDanetka = convertView.findViewById(R.id.imv);
            viewHolder.imvDanetka1 = convertView.findViewById(R.id.imv1);



            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

//        viewHolder.txvName.setText(mData.get(position).getStrName());

        if (mData.get(position).getStrName().equalsIgnoreCase("disloyal singer"))
            viewHolder.txvName.setText((position+1)+". (Dis)loyal singer");
        else
            viewHolder.txvName.setText((position+1)+". "+mData.get(position).getStrName());




        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_logo)
                .error(R.drawable.ic_logo)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform();

        String danetka_Image = "http://18.119.55.236:2000/images/"+mData.get(position).getStrImage();
        String danetka_Image1 = "http://18.119.55.236:2000/images/"+mData.get(position).getAnswerImage();

        Glide.with(context)
                .load(danetka_Image)
                .apply(options)
                .into(viewHolder.imvDanetka);
        Glide.with(context)
                .load(danetka_Image1)
                .apply(options)
                .into(viewHolder.imvDanetka1);


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iAdapterCallback.onAdapterEventFired(IAdapterCallback.EVENT_A,position );
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView txvName;
        ImageView imvDanetka;
        ImageView imvDanetka1;
    }


    public void filterList(ArrayList<DModel_MyDenketa> filterllist) {
        // below line is to add our filtered
        // list in our course array list.
        mData = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }



    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mDataFiltered = mData;
                } else {
                    ArrayList<DModel_MyDenketa> filteredList = new ArrayList<>();
                    for (DModel_MyDenketa row : mData) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getStrName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    mDataFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mDataFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mDataFiltered = (ArrayList<DModel_MyDenketa>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
