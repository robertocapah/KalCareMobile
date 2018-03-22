package com.kalbenutritionals.kalcaremobie.Data.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kalbenutritionals.kalcaremobie.R;
import com.kalbenutritionals.kalcaremobie.ViewModel.VMItems;

import java.util.List;


/**
 * Created by Dewi Oktaviani on 1/17/2018.
 */

public class CardAppAdapter extends BaseAdapter {
    private Context context;
    private List<VMItems> mAppList;
    int color;
    public CardAppAdapter(Context context, List<VMItems> mAppList, int color){
        this.context = context;
        this.mAppList = mAppList;
        this.color = color;
    }

    @Override
    public int getCount() {
        return mAppList.size();
    }

    @Override
    public VMItems getItem(int position) {
        return mAppList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = View.inflate(context, R.layout.card_list_app, null);
            new ViewHolder(convertView);
        }
        VMItems item = getItem(position);
        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.tvItemName.setText(item.getItemName());
        holder.tvHeaderProductName.setText(item.getItemName());
        holder.tvItemCode.setText(item.getItemCode());
        holder.tvQty.setText(String.valueOf(item.getQty()));
        holder.tvitemPrice.setText(String.valueOf(item.getPrice()));
        holder.tvDiscount.setText(String.valueOf(item.getDiscount()));
        holder.tvTotalPrice.setText(String.valueOf(item.getTotalPrice()));
        holder.tvTaxAmount.setText(String.valueOf(item.getTaxAmount()));
        holder.tvNetPrice.setText(String.valueOf(item.getNetPrice()));
        holder.tvBasePoint.setText(item.getBasePoint());
        holder.tvBonusPoint.setText(item.getBonusPoint());
        holder.cardView.setCardBackgroundColor(color);
        return convertView;
    }

    class ViewHolder {
        TextView tvHeaderProductName,tvItemCode, tvItemName, tvQty, tvitemPrice, tvDiscount, tvTotalPrice, tvTaxAmount, tvNetPrice, tvBasePoint, tvBonusPoint;
        CardView cardView;
        ImageView iv_icon;
        public ViewHolder(View view) {
            tvHeaderProductName = (TextView) view.findViewById(R.id.tvHeaderProductName);
            tvItemCode = (TextView) view.findViewById(R.id.tvItemCode);
            tvItemName = (TextView) view.findViewById(R.id.tvItemName);
            tvQty = (TextView) view.findViewById(R.id.tvQty);
            tvitemPrice = (TextView) view.findViewById(R.id.tvPrice);
            tvDiscount = (TextView) view.findViewById(R.id.tvDiscount);
            tvTotalPrice = (TextView) view.findViewById(R.id.tvTotalPrice);
            tvTaxAmount = (TextView) view.findViewById(R.id.tvTax);
            tvNetPrice = (TextView) view.findViewById(R.id.tvNetPrice);
            tvBasePoint = (TextView) view.findViewById(R.id.tvBasePoint);
            tvBonusPoint = (TextView) view.findViewById(R.id.tvBonusPoint);
            iv_icon = (ImageView) view.findViewById(com.kalbe.mobiledevknlibs.R.id.iv_icon);
            cardView = (CardView) view.findViewById(R.id.cdv_list);
            iv_icon.setVisibility(View.GONE);

            view.setTag(this);
        }
    }
}
