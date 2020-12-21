package com.petfolio.infinitus.interfaces;

import com.petfolio.infinitus.responsepojo.SPServiceListResponse;

import java.util.List;

public interface SPSpecialzationChckedListener {

    void onItemSPSpecialzationCheck(int position, String specValue, List<SPServiceListResponse.DataBean.SpecializationBean> dataBeanList);

    void onItemSPSpecialzationUnCheck(int position, String specValue);

}