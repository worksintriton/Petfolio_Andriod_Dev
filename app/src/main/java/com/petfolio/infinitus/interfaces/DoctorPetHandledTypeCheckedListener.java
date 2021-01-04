package com.petfolio.infinitus.interfaces;

import com.petfolio.infinitus.responsepojo.DropDownListResponse;

import java.util.List;

public interface DoctorPetHandledTypeCheckedListener {

    void onItemPetCheck(int position, String pethandleValue);

    void onItemPetUnCheck(int position, String pethandleValue);

}
