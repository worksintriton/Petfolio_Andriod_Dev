package com.petfolio.infinitus.interfaces;

public interface SPServiceCheckedListener {

    void onItemSPServiceCheck(int position, String specValue,String timeSlot);

    void onItemSPServiceUnCheck(int position, String specValue,String timeSlot);

}