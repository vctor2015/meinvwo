package com.meinvwo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 描述:
 * <p>
 * Created by ruanzb on 2017/2/8.
 */

public class BeautyListAdapter extends RecyclerView.Adapter<BeautyVH> {
    private List<ItemBo> mItemBos;
    private OnItemClick mOnItemClick;

    public BeautyListAdapter(OnItemClick onItemClick) {
        mOnItemClick = onItemClick;
    }

    @Override
    public BeautyVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_beauty, parent, false);
        return new BeautyVH(view);
    }

    @Override
    public void onBindViewHolder(BeautyVH holder, final int position) {
        final ItemBo itemBo = mItemBos.get(position);
        Glide.with(holder.itemView.getContext())
                .load(itemBo.getUrl())
                .into(holder.mImageView);
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClick.itemClick(position,itemBo);
            }
        });
        holder.tvTime.setText(itemBo.getTime());
    }

    @Override
    public int getItemCount() {
        return mItemBos == null ? 0 : mItemBos.size();
    }

    public void setItemBos(List<ItemBo> itemBos) {
        mItemBos = itemBos;
        notifyDataSetChanged();
    }
}
