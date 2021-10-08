package com.petfolio.infinitus.interfaces;

public interface OnAppointmentCancel {
    void onAppointmentCancel(String id,String appointmenttype,String userid, String doctorid,String appointmentid,String spid, String cost, String paymentmethod);
}
