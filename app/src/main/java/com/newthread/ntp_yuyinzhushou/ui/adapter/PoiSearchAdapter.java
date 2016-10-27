package com.newthread.ntp_yuyinzhushou.ui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.newthread.ntp_yuyinzhushou.R;


import java.util.List;

/**
 * Created by MoChan on 2016/10/17.
 */

public class PoiSearchAdapter extends RecyclerView.Adapter<PoiSearchAdapter.ViewHolder> {

    private Context context;
    private RvAdapterImpl rvAdapterImpl;
    private List<PoiItem> poiItemList;
    boolean isDistanceVisible;

    public PoiSearchAdapter(Context context, RvAdapterImpl rvAdapterImpl, List<PoiItem> poiItemList, boolean isDistanceVisible) {
        this.context = context;
        this.rvAdapterImpl = rvAdapterImpl;
        this.poiItemList = poiItemList;
        this.isDistanceVisible = isDistanceVisible;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_rv_poi_search, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final PoiItem poiItem = poiItemList.get(position);
        holder.title.setText(1 + position + ". " + poiItem.getTitle());
        holder.type.setText(poiItem.getTypeDes());
        holder.address.setText(poiItem.getAdName() + poiItem.getSnippet());
        holder.distance.setText(poiItem.getDistance() + "米");
        if (!isDistanceVisible) {
            holder.itemDistance.setVisibility(View.GONE);
        }
        rvAdapterImpl.RvWholeItemClicked(holder.cardView,poiItem,position);
        rvAdapterImpl.RvRougeItemClicked(holder.itemRouge,poiItem,position);
        rvAdapterImpl.RvNaviItemClicked(holder.itemNavi,poiItem,position);
        rvAdapterImpl.RvDetailItemClicked(holder.itemDetail,poiItem,position);
    }

    @Override
    public int getItemCount() {
        return poiItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        public TextView title;
        public TextView type;
        public TextView address;
        public TextView distance;
        public LinearLayout itemDistance;
        public LinearLayout itemRouge;
        public LinearLayout itemNavi;
        public LinearLayout itemDetail;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view_item_rv_fragment_location);
            title = (TextView) itemView.findViewById(R.id.tx_title_item_rv_fragment_location);
            type = (TextView) itemView.findViewById(R.id.tx_type_item_rv_fragment_location);
            address = (TextView) itemView.findViewById(R.id.tx_address_item_rv_fragment_location);
            distance = (TextView) itemView.findViewById(R.id.tx_distance_item_rv_fragment_location);
            itemDistance = (LinearLayout) itemView.findViewById(R.id.item_distance_rv_fragment_location);
            itemRouge = (LinearLayout) itemView.findViewById(R.id.item_rouge_rv_fragment_location);
            itemNavi = (LinearLayout) itemView.findViewById(R.id.item_navi_rv_fragment_location);
            itemDetail = (LinearLayout) itemView.findViewById(R.id.item_detail_rv_fragment_location);
        }
    }

    public interface RvAdapterImpl {

        void RvWholeItemClicked(View view,PoiItem poiItem, int position);

        void RvRougeItemClicked(View view,PoiItem poiItem, int position);

        void RvNaviItemClicked(View view,PoiItem poiItem, int position);

        void RvDetailItemClicked(View view,PoiItem poiItem, int position);

    }
}
