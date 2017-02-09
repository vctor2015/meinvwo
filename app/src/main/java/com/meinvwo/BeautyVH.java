package com.meinvwo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述:
 * <p>
 * Created by ruanzb on 2017/2/8.
 */

public class BeautyVH extends RecyclerView.ViewHolder {

    @BindView(R.id.image_view)
    public ImageView mImageView;
    @BindView(R.id.tv_time)
    public TextView tvTime;

    public BeautyVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
