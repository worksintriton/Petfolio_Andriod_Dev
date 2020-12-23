package com.petfolio.infinitus.interfaces;

import com.petfolio.infinitus.responsepojo.SPServiceListResponse;

import java.util.List;

public interface SPServiceChckedListener {

    void onItemSPServiceCheck(int position, String specValue);

    void onItemSPServiceUnCheck(int position, String specValue);

}