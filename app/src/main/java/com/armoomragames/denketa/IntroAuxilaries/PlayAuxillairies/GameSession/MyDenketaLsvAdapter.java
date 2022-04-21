package com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.GameSession;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.DModel_MyDenketa;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.IAdapterCallback;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class MyDenketaLsvAdapter extends BaseAdapter implements Filterable {

    private IAdapterCallback iAdapterCallback;
    private LayoutInflater inflater;
    private ArrayList<DModel_MyDenketa> mData;
    private float cornerRadius;
    private Context context;

    public MyDenketaLsvAdapter(IAdapterCallback iAdapterCallback, Context context, ArrayList<DModel_MyDenketa> mData) {
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

        final ViewHolder viewHolder;

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.lay_item_my_denketa, null);

            viewHolder = new ViewHolder();

            viewHolder.txvName  = convertView.findViewById(R.id.lay_item_my_denekta_txvName);
            viewHolder.imvDanetka = convertView.findViewById(R.id.imvDanetka);
            viewHolder.imvDanetka1 = convertView.findViewById(R.id.imvDanetka1);
            viewHolder.imvResult = convertView.findViewById(R.id.lay_item_my_denekta_imvResults);
            viewHolder.imvDelete = convertView.findViewById(R.id.lay_item_my_denekta_imvDelete);



            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (mData.get(position).getStrName().equalsIgnoreCase("disloyal singer"))
        viewHolder.txvName.setText((position+1)+". (Dis)loyal singer");
        else
        viewHolder.txvName.setText((position+1)+". "+mData.get(position).getStrName());

//        viewHolder.txvName.setText(mData.get(position).getStrName());
        convertView.setOnClickListener(v -> iAdapterCallback.onAdapterEventFired(IAdapterCallback.EVENT_A,position ));
        viewHolder.imvResult.setOnClickListener(v -> iAdapterCallback.onAdapterEventFired(IAdapterCallback.EVENT_B,position ));


        try{
        if (mData.get(position).getDanetkaType().equalsIgnoreCase("custom"))
        {
            viewHolder.imvDelete.setVisibility(View.VISIBLE);
            viewHolder.imvDelete.setOnClickListener(v -> iAdapterCallback.onAdapterEventFired(IAdapterCallback.EVENT_C,position ));

        }
        }catch (Exception e)
        {}
        Log.d("LOG_AS", "getView: "+mData.get(position).getStrImage());
//        Picasso.get()
//                .load(mData.get(position).getStrImage())
//                .into( viewHolder.imvDanetka);
//

        if (AppConfig.getInstance().mUser.isLoggedIn())
        {
            viewHolder.imvResult.setVisibility(View.VISIBLE);
        }
        else {
            viewHolder.imvResult.setVisibility(View.GONE);

        }

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_logo)
                .error(R.drawable.ic_logo)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform();

        String danetka_Image = "http://18.119.55.236:2000/images/"+mData.get(position).getStrImage();
        String danetka_Image2 = "http://18.119.55.236:2000/images/"+mData.get(position).getAnswerImage();

        Glide.with(context)
                .load(danetka_Image)
                .apply(options)
                .into(viewHolder.imvDanetka);
 Glide.with(context)
                .load(danetka_Image2)
                .apply(options)
                .into(viewHolder.imvDanetka1);

        return convertView;
    }

    class ViewHolder {
        TextView txvName;
        ImageView imvDanetka;
        ImageView imvDanetka1;
        ImageView imvResult;
        ImageView imvDelete;
    }

    public void filterList(ArrayList<DModel_MyDenketa> filterllist) {
        // below line is to add our filtered
        // list in our course array list.
        mData = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }



    public Filter getFilter() {

        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                mData = (ArrayList<DModel_MyDenketa>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();
                ArrayList<DModel_MyDenketa> FilteredArrayNames = new ArrayList<>();

                // perform your search here using the searchConstraint String.

                constraint = constraint.toString().toLowerCase();
                for (int i = 0; i < mData.size(); i++) {
                    String dataNames = mData.get(i).getStrName();
                    if (dataNames.toLowerCase().startsWith(constraint.toString()))  {
                        FilteredArrayNames.add(mData.get(i));
                    }
                }

                results.count = FilteredArrayNames.size();
                results.values = FilteredArrayNames;
                Log.e("VALUES", results.values.toString());

                return results;
            }
        };

        return filter;
    }
}
