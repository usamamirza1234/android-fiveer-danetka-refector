package com.armoomragames.denketa.IntroAuxilaries.Admin.PromoCode;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.IAdapterCallback;

import java.util.ArrayList;

public class PromoLsvAdapter extends BaseAdapter {

    private final IAdapterCallback iAdapterCallback;
    private final LayoutInflater inflater;
    private final ArrayList<DModel_Promo> mData;
    private final float cornerRadius;
    private Context context;

    public PromoLsvAdapter(IAdapterCallback iAdapterCallback, Context context, ArrayList<DModel_Promo> mData) {
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

            convertView = inflater.inflate(R.layout.lay_item_promo, null);

            viewHolder = new ViewHolder();

            viewHolder.txvName = convertView.findViewById(R.id.lay_item_my_denekta_txvName);
            viewHolder.rl_active = convertView.findViewById(R.id.rl_active);
            viewHolder.rl_parent = convertView.findViewById(R.id.rl_parent);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.txvName.setText((position + 1) + ". " + mData.get(position).getPromoCode());

        if (mData.get(position).isStatus())
        {
            viewHolder.rl_active.setBackground(context.getResources().getDrawable( R.drawable.shp_circle_green1));
        }
        else
            viewHolder.rl_active.setBackground(context.getResources().getDrawable( R.drawable.shp_circle_red));

        viewHolder.rl_parent.setOnClickListener(v -> iAdapterCallback.onAdapterEventFired(IAdapterCallback.EVENT_A, position));

        return convertView;
    }

    class ViewHolder {
        TextView txvName;
        RelativeLayout rl_active,rl_parent;
    }


}
