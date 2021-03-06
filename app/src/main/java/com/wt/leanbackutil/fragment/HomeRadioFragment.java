package com.wt.leanbackutil.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.open.leanback.widget.OnChildSelectedListener;
import com.open.leanback.widget.VerticalGridView;
import com.wt.leanbackutil.R;
import com.wt.leanbackutil.adapter.RadioInfoAdapter;
import com.wt.leanbackutil.model.RadioInfo;
import com.wt.leanbackutil.model.RadioItem;
import com.wt.leanbackutil.model.RadioResponse;
import com.wt.leanbackutil.util.FileJsonUtils;
import com.wt.leanbackutil.util.LogUtil;
import com.wt.leanbackutil.util.PagerUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by DELL on 2018/8/8.
 *
 * @author junyan
 *         电台
 */

public class HomeRadioFragment extends BaseFragment {

    @BindView(R.id.vertical_grid_view)
    VerticalGridView verticalGridView;

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_home_radio, container, false);
        }
        ButterKnife.bind(this, view);
        initData();
        LogUtil.d("onCreateView-----------------" + HomeRadioFragment.class.getSimpleName());
        return view;
    }

    private void initData() {
        String json = FileJsonUtils.inputStreamToString(getResources().openRawResource(R.raw.radio_data));
        RadioResponse radioResponse = new Gson().fromJson(json, RadioResponse.class);
        RadioInfoAdapter radioInfoAdapter = new RadioInfoAdapter(this);
        radioInfoAdapter.setData(getRadioInfos(radioResponse));
        verticalGridView.getBaseGridViewLayoutManager().setFocusOutAllowed(true, true);
        verticalGridView.getBaseGridViewLayoutManager().setFocusOutSideAllowed(false, false);

        verticalGridView.setAdapter(radioInfoAdapter);
    }

    @Override
    public void refreshRecyclerUi() {
        if (verticalGridView != null) {
            verticalGridView.scrollToPosition(0);
        }
    }

    /**
     * 数据整理
     *
     * @param radioResponse
     * @return
     */
    private List<RadioInfo> getRadioInfos(RadioResponse radioResponse) {
        List<RadioInfo> radioInfos = new ArrayList<>();
        for (int i = 0; i < radioResponse.getData().size(); i++) {
            RadioInfo radioInfo = radioResponse.getData().get(i);
            PagerUtil<RadioItem> pagerUtil = PagerUtil.create(radioInfo.getRadios(), 5);
            for (int j = 1; j <= pagerUtil.getTotalPage(); j++) {
                RadioInfo copeRadioInfo = new RadioInfo();
                copeRadioInfo.setId(radioInfo.getId());
                copeRadioInfo.setRadio_group_name(radioInfo.getRadio_group_name());
                if (j == 1) {
                    copeRadioInfo.setType(1);
                } else {
                    copeRadioInfo.setType(0);
                }
                copeRadioInfo.setRadios(pagerUtil.getPagedList(j));
                if (copeRadioInfo != null) {
                    radioInfos.add(copeRadioInfo);
                }
            }
        }
        return radioInfos;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != view) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }
}
