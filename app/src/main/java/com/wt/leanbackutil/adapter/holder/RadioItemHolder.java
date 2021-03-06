package com.wt.leanbackutil.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wt.leanbackutil.R;
import com.wt.leanbackutil.util.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by DELL on 2018/8/8.
 */

public class RadioItemHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.img_view)
    public SimpleDraweeView imageView;
    @BindView(R.id.title_view)
    public TextView titleView;

    public RadioItemHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        ViewUtils.onFocus(imageView);
    }
}
