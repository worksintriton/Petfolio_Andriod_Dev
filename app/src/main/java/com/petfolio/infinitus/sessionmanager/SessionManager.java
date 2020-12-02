package com.petfolio.infinitus.sessionmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;

public class SessionManager {
    public static final String KEY_DOCTOR_TITLE = "doctortitle";
    public static final String KEY_DOCTOR_COMMUNICATION_TYPE = "doctorcommunicationtype";
    public static final String KEY_DOCTOR_TIME_TYPE = "doctortimetype";
    public static final String KEY_DOCTOR_PROFILE_STATUS = "profilestatus";
    public static final String KEY_FIRST_NAME = "firstname" ;
    public static final String KEY_LAST_NAME = "lastname";
    String TAG = "SessionManager";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "Session Manager";
    public static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_LOGIN_TYPE = "logintype";

    public static final String KEY_TOKEN = "keytoken";
    public static final String KEY_PROFILE_STATUS= "profilestatus";




    public static final String KEY_EMAIL_ID = "emailid";
    public static final String KEY_MOBILE = "phoneno";
    public static final String KEY_TYPE = "type";
    public static final String KEY_ID = "id";

    public static final String KEEPLOGIN = "keeplogin";





    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void updateToken(String refreshedToken) {

        editor.putString(KEY_TOKEN, refreshedToken);
        editor.commit();

        Log.d(TAG, "------------->update token" + refreshedToken);
    }







    public void createLoginSession(String id, String firstname, String lastname, String useremail,String userphone,String usertype,
                                 String userstatus) {


        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_ID, id);
        editor.putString(KEY_FIRST_NAME, firstname);
        editor.putString(KEY_LAST_NAME, lastname);
        editor.putString(KEY_EMAIL_ID, useremail);
        editor.putString(KEY_MOBILE, userphone);
        editor.putString(KEY_TYPE, usertype);
        editor.putString(KEY_PROFILE_STATUS, userstatus);
        Log.e(TAG, "................................>> session Login Details " + "KEY_ID" + id);

        editor.commit();

    }







    public HashMap<String, String> getProfileDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_ID, pref.getString(KEY_ID, ""));
        user.put(KEY_FIRST_NAME, pref.getString(KEY_FIRST_NAME, ""));
        user.put(KEY_LAST_NAME, pref.getString(KEY_LAST_NAME, ""));
        user.put(KEY_EMAIL_ID, pref.getString(KEY_EMAIL_ID, ""));
        user.put(KEY_MOBILE, pref.getString(KEY_MOBILE, ""));
        user.put(KEY_TYPE, pref.getString(KEY_TYPE, ""));
        user.put(KEY_PROFILE_STATUS, pref.getString(KEY_PROFILE_STATUS, ""));
        return user;
    }




    public boolean checkLogin() {

        if (!this.isLoggedIn()) {


        } else if (this.isLoggedIn()) {

        }

        return false;
    }

    public void logoutUser() {

        editor.clear();
        editor.commit();


    }




    public void setIsLogin(boolean isLoogedIn){
        editor.putBoolean(KEEPLOGIN,isLoogedIn);
        editor.apply();
    }

    public boolean isLoggedIn() {

        return pref.getBoolean(KEEPLOGIN, false);
    }




}