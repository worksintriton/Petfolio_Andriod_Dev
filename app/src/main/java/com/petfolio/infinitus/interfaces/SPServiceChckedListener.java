package com.petfolio.infinitus.interfaces;

import com.petfolio.infinitus.responsepojo.SPServiceListResponse;

import java.util.List;

public interface SPServiceChckedListener {

    void onItemSpecCheck(int position, String specValue, List<SPServiceListResponse.DataBean> dataBeanList);

    void onItemSpecUnCheck(int position, String specValue);

}