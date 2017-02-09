package com.meinvwo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 描述:
 * <p>
 * Created by ruanzb on 2017/2/8.
 */

public class GankBo {
    @SerializedName("error")
    private boolean error;
    @SerializedName("results")
    private List<BeautyBo> mBeautyBos;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<BeautyBo> getBeautyBos() {
        return mBeautyBos;
    }

    public void setBeautyBos(List<BeautyBo> beautyBos) {
        mBeautyBos = beautyBos;
    }
}
