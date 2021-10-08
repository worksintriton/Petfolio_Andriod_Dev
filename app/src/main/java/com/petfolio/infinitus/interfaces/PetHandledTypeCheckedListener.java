package com.petfolio.infinitus.interfaces;

import com.petfolio.infinitus.responsepojo.DropDownListResponse;

import java.util.List;

public interface PetHandledTypeCheckedListener {

    void onItemPetCheck(int position, String pethandleValue, List<DropDownListResponse.DataBean.PetHandleBean> petHandleBeanList);

    void onItemPetUnCheck(int position, String pethandleValue);

}
