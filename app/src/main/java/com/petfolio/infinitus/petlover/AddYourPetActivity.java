package com.petfolio.infinitus.petlover;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.activity.LoginActivity;
import com.petfolio.infinitus.api.APIClient;
import com.petfolio.infinitus.api.RestApiInterface;
import com.petfolio.infinitus.requestpojo.AddYourPetRequest;
import com.petfolio.infinitus.requestpojo.BreedTypeRequest;
import com.petfolio.infinitus.responsepojo.AddYourPetResponse;
import com.petfolio.infinitus.responsepojo.BreedTypeResponse;
import com.petfolio.infinitus.responsepojo.DropDownListResponse;
import com.petfolio.infinitus.responsepojo.PetDetailsResponse;
import com.petfolio.infinitus.responsepojo.PetTypeListResponse;
import com.petfolio.infinitus.sessionmanager.SessionManager;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.petfolio.infinitus.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddYourPetActivity extends AppCompatActivity {
    private static final String TAG = "AddYourPetActivity";

    @BindView(R.id.img_back)
    ImageView img_back;

    @BindView(R.id.txt_skip)
    TextView txt_skip;

    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @BindView(R.id.edt_petname)
    EditText edt_petname;

    @BindView(R.id.sprpettype)
    Spinner sprpettype;

    @BindView(R.id.sprpetbreed)
    Spinner sprpetbreed;

    @BindView(R.id.sprpetgender)
    Spinner sprpetgender;

    @BindView(R.id.edt_petcolor)
    EditText edt_petcolor;

    @BindView(R.id.edt_petweight)
    EditText edt_petweight;

    @BindView(R.id.edt_petage)
    EditText edt_petage;

    @BindView(R.id.rgvaccinated)
    RadioGroup rgvaccinated;

    @BindView(R.id.rlpetlastvaccinatedagedate)
    RelativeLayout rlpetlastvaccinatedagedate;

    @BindView(R.id.llpetlastvaccinatedagedate)
    LinearLayout llpetlastvaccinatedagedate;

    @BindView(R.id.txt_petlastvaccinatedage)
    TextView txt_petlastvaccinatedage;

    @BindView(R.id.btn_continue)
    Button btn_continue;

    private List<DropDownListResponse.DataBean.PetTypeBean> petTypeList;
    private List<DropDownListResponse.DataBean.PetBreedBean> petBreedTypeList;
    private List<DropDownListResponse.DataBean.GenderBean> genderTypeList;
    private List<DropDownListResponse.DataBean.ColorBean> petColorTypeList;

    private String strPetType;
    private String strPetBreedType;
    private String strPetGenderType;
    private String strPetColorType;
    private String selectedRadioButton = "Yes";

    private int year, month, day;
    String SelectedLastVaccinateddate = "";
    private static final int DATE_PICKER_ID = 0 ;
    Boolean isvaccinated = true;
    private Dialog alertDialog;
    private String userid;
    private List<DropDownListResponse.DataBean.SpecialzationBean> petSpecilaziationList;

    private List<PetTypeListResponse.DataBean.UsertypedataBean> usertypedataBeanList;

    private String petTypeid;

    private String strSelectyourPetType;

    HashMap<String, String> hashMap_PetTypeid = new HashMap<>();
    private String petTypeId;
    private List<PetDetailsResponse.DataBean> petDetailsResponseByUserIdList;
    private List<BreedTypeResponse.DataBean> breedTypedataBeanList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_your_pet);
        ButterKnife.bind(this);
        avi_indicator.setVisibility(View.GONE);

        SessionManager sessionManager = new SessionManager(AddYourPetActivity.this);
        HashMap<String, String> user = sessionManager.getProfileDetails();
        userid = user.get(SessionManager.KEY_ID);


        if (new ConnectionDetector(AddYourPetActivity.this).isNetworkAvailable(AddYourPetActivity.this)) {
            petTypeListResponseCall();

        }

        sprpettype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int arg2, long arg3) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.green));
                strPetType = sprpettype.getSelectedItem().toString();
                petTypeId = hashMap_PetTypeid.get(strPetType);
                breedTypeResponseByPetIdCall(petTypeId);

                Log.w(TAG,"petTypeId : "+petTypeId+" strPetType :"+strPetType);


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        sprpetbreed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int arg2, long arg3) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.green));
                strPetBreedType = sprpetbreed.getSelectedItem().toString();
                Log.w(TAG,"strPetBreedType :"+strPetBreedType);


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        sprpetgender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int arg2, long arg3) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.green));
                strPetGenderType = sprpetgender.getSelectedItem().toString();
                Log.w(TAG,"strPetGenderType :"+strPetGenderType);


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        rgvaccinated.setOnCheckedChangeListener((group, checkedId) -> {
            int radioButtonID = rgvaccinated.getCheckedRadioButtonId();
            RadioButton radioButton = rgvaccinated.findViewById(radioButtonID);
            selectedRadioButton = (String) radioButton.getText();
            Log.w(TAG,"selectedRadioButton" + selectedRadioButton);
            if(selectedRadioButton.equalsIgnoreCase("Yes")){
                isvaccinated = true;
                rlpetlastvaccinatedagedate.setVisibility(View.VISIBLE);
                llpetlastvaccinatedagedate.setVisibility(View.VISIBLE);
            }else{
                isvaccinated = false;
                rlpetlastvaccinatedagedate.setVisibility(View.GONE);
                llpetlastvaccinatedagedate.setVisibility(View.GONE);

            }

        });

        rlpetlastvaccinatedagedate.setOnClickListener(v -> SelectDate());
        btn_continue.setOnClickListener(v -> addYourPetValidator());
        img_back.setOnClickListener(v -> onBackPressed());
        txt_skip.setOnClickListener(v -> {
            Intent intent = new Intent(AddYourPetActivity.this,PetLoverDashboardActivity.class);
            startActivity(intent);
        });



    }
    public void addYourPetValidator() {
        boolean can_proceed = true;

        int petnamelength = edt_petname.getText().toString().trim().length();
        int petweightlength = edt_petweight.getText().toString().trim().length();

        if (Objects.requireNonNull(edt_petname.getText()).toString().trim().equals("") && Objects.requireNonNull(edt_petweight.getText()).toString().trim().equals("") &&
                Objects.requireNonNull(edt_petage.getText()).toString().trim().equals("")) {
            Toasty.warning(getApplicationContext(), "Please enter the fields", Toast.LENGTH_SHORT, true).show();
            can_proceed = false;
        } else if (edt_petname.getText().toString().trim().equals("")) {
            edt_petname.setError("Please enter pet name");
            edt_petname.requestFocus();
            can_proceed = false;
        }else if (petnamelength > 25) {
            edt_petname.setError("The maximum length for an pet name is 25 characters.");
            edt_petname.requestFocus();
            can_proceed = false;
        }
        else if (edt_petweight.getText().toString().trim().equals("")) {
            edt_petweight.setError("Please enter pet weight");
            edt_petweight.requestFocus();
            can_proceed = false;
        }
        else if (petweightlength > 5) {
            edt_petweight.setError("The maximum length for an pet weight is 5 characters.");
            edt_petweight.requestFocus();
            can_proceed = false;
        }
        else if (Objects.requireNonNull(edt_petage.getText()).toString().trim().equals("")) {
            edt_petage.setError("Please enter pet age");
            edt_petage.requestFocus();
            can_proceed = false;
        } else if (selectedRadioButton.equalsIgnoreCase("Yes") && SelectedLastVaccinateddate.isEmpty()) {
            showErrorLoading("Please select pet last vaccinated age");
            can_proceed = false;
        }

        if (can_proceed) {
            if (new ConnectionDetector(AddYourPetActivity.this).isNetworkAvailable(AddYourPetActivity.this)) {

                if(validdSelectPetType() && validdSelectPetBreedType() && validdSelectPetGenderType() ) {
                    addYourPetResponseCall();
                }
            }

        }





    }
    public void dropDownListResponseCall(){
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<DropDownListResponse> call = apiInterface.dropDownListResponseCall(RestUtils.getContentType());
        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<DropDownListResponse>() {
            @Override
            public void onResponse(@NonNull Call<DropDownListResponse> call, @NonNull Response<DropDownListResponse> response) {
                avi_indicator.smoothToHide();


                if (response.body() != null) {
                    if(200 == response.body().getCode()){
                        Log.w(TAG,"DropDownListResponse" + new Gson().toJson(response.body()));

                        petTypeList = response.body().getData().getPet_type();
                        petBreedTypeList = response.body().getData().getPet_breed();
                        genderTypeList = response.body().getData().getGender();
                        petColorTypeList = response.body().getData().getColor();
                        petSpecilaziationList = response.body().getData().getSpecialzation();
                        Log.w(TAG,"petSpecilaziationList : "+new Gson().toJson(petSpecilaziationList));
                        if(petSpecilaziationList != null && petSpecilaziationList.size()>0){

                        }

                        if(genderTypeList != null && genderTypeList.size()>0){
                            setPetGenderType(genderTypeList);
                        }


                    }

                }








            }


            @Override
            public void onFailure(@NonNull Call<DropDownListResponse> call,@NonNull  Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"DropDownListResponse flr"+t.getMessage());
            }
        });

    }
    private void setPetGenderType(List<DropDownListResponse.DataBean.GenderBean> genderTypeList) {
        ArrayList<String> petGendertypeArrayList = new ArrayList<>();
        petGendertypeArrayList.add("Select Pet Gender");
        for (int i = 0; i < genderTypeList.size(); i++) {

            String petGenderType = genderTypeList.get(i).getGender();
            Log.w(TAG,"petGenderType-->"+petGenderType);
            petGendertypeArrayList.add(petGenderType);

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(AddYourPetActivity.this, R.layout.spinner_item, petGendertypeArrayList);
            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
            sprpetgender.setAdapter(spinnerArrayAdapter);


        }
    }
    private void SelectDate() {

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);


        showDialog(DATE_PICKER_ID);

    }
    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DATE_PICKER_ID) {// open datepicker dialog.
            // set date picker for current date
            // add pickerListener listner to date picker
            // return new DatePickerDialog(this, pickerListener, year, month,day);
            DatePickerDialog dialog = new DatePickerDialog(this, pickerListener, year, month, day);
            dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            return dialog;
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year  = selectedYear;
            month = selectedMonth;
            day   = selectedDay;



            String strdayOfMonth;
            String strMonth;
            int month1 =(month + 1);
            if(day == 9 || day <9){
                strdayOfMonth = "0"+day;
                Log.w(TAG,"Selected dayOfMonth-->"+strdayOfMonth);
            }else{
                strdayOfMonth = String.valueOf(day);
            }

            if(month1 == 9 || month1 <9){
                strMonth = "0"+month1;
                Log.w(TAG,"Selected month1-->"+strMonth);
            }else{
                strMonth = String.valueOf(month1);
            }

            SelectedLastVaccinateddate = strdayOfMonth + "-" + strMonth + "-" + year;

            // Show selected date
            txt_petlastvaccinatedage.setText(SelectedLastVaccinateddate);

        }
    };
    public boolean validdSelectPetType() {
        if(strPetType.equalsIgnoreCase("Select Pet Type")){
            final AlertDialog alertDialog = new AlertDialog.Builder(AddYourPetActivity.this).create();
            alertDialog.setMessage(getString(R.string.err_msg_type_of_pettype));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                    (dialog, which) -> alertDialog.cancel());
            alertDialog.show();

            return false;
        }

        return true;
    }
    public boolean validdSelectPetBreedType() {
        if(strPetBreedType.equalsIgnoreCase("Select Pet Breed")){
            final AlertDialog alertDialog = new AlertDialog.Builder(AddYourPetActivity.this).create();
            alertDialog.setMessage(getString(R.string.err_msg_type_of_petbreedtype));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                    (dialog, which) -> alertDialog.cancel());
            alertDialog.show();

            return false;
        }

        return true;
    }
    public boolean validdSelectPetGenderType() {
        if(strPetGenderType.equalsIgnoreCase("Select Pet Gender")){
            final AlertDialog alertDialog = new AlertDialog.Builder(AddYourPetActivity.this).create();
            alertDialog.setMessage(getString(R.string.err_msg_type_of_petgendertype));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                    (dialog, which) -> alertDialog.cancel());
            alertDialog.show();

            return false;
        }

        return true;
    }


    private void addYourPetResponseCall() {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<AddYourPetResponse> call = apiInterface.addYourPetResponseCall(RestUtils.getContentType(), addYourPetRequest());
        Log.w(TAG,"AddYourPetResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<AddYourPetResponse>() {
            @Override
            public void onResponse(@NonNull Call<AddYourPetResponse> call, @NonNull Response<AddYourPetResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"AddYourPetResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    if (200 == response.body().getCode()) {
                        Toasty.success(getApplicationContext(),response.body().getMessage(), Toast.LENGTH_SHORT, true).show();
                        Intent intent = new Intent(AddYourPetActivity.this,RegisterYourPetActivity.class);
                        intent.putExtra("petid",response.body().getData().get_id());
                        startActivity(intent);

                    } else {
                        showErrorLoading(response.body().getMessage());
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<AddYourPetResponse> call,@NonNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.e("AddYourPetResponse flr", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private AddYourPetRequest addYourPetRequest() {
        /*
         * user_id : 5fb36ca169f71e30a0ffd3f7
         * pet_img : http://mysalveo.com/api/uploads/images.jpeg
         * pet_name : POP
         * pet_type : Dog
         * pet_breed : breed 1
         * pet_gender : Male
         * pet_color : white
         * pet_weight : 120
         * pet_age : 20
         * vaccinated : true
         * last_vaccination_date : 23-10-1996
         * default_status : true
         * date_and_time : 23-10-1996 12:09 AM
         */
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        
        AddYourPetRequest addYourPetRequest = new AddYourPetRequest();
        addYourPetRequest.setUser_id(userid);
        addYourPetRequest.setPet_img("http://mysalveo.com/api/uploads/images.jpeg");
        addYourPetRequest.setPet_name(edt_petname.getText().toString());
        addYourPetRequest.setPet_type(strPetType);
        addYourPetRequest.setPet_breed(strPetBreedType);
        addYourPetRequest.setPet_gender(strPetGenderType);
        addYourPetRequest.setPet_color(edt_petcolor.getText().toString());
        addYourPetRequest.setPet_weight(Integer.parseInt(edt_petweight.getText().toString()));
        addYourPetRequest.setPet_age(Integer.parseInt(edt_petage.getText().toString()));
        addYourPetRequest.setVaccinated(isvaccinated);
        addYourPetRequest.setLast_vaccination_date(SelectedLastVaccinateddate);
        addYourPetRequest.setDefault_status(true);
        addYourPetRequest.setDate_and_time(currentDateandTime);
        addYourPetRequest.setMobile_type("Android");
        Log.w(TAG,"addYourPetRequest"+ new Gson().toJson(addYourPetRequest));
        return addYourPetRequest;
    }
    public void showErrorLoading(String errormesage){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(errormesage);
        alertDialogBuilder.setPositiveButton("ok",
                (arg0, arg1) -> hideLoading());




        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    public void hideLoading(){
        try {
            alertDialog.dismiss();
        }catch (Exception ignored){

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AddYourPetActivity.this,LoginActivity.class));
        finish();
    }


    public void petTypeListResponseCall(){
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        //Creating an object of our api interface
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<PetTypeListResponse> call = apiInterface.petTypeListResponseCall(RestUtils.getContentType());
        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<PetTypeListResponse>() {
            @Override
            public void onResponse(@NonNull Call<PetTypeListResponse> call, @NonNull Response<PetTypeListResponse> response) {
                avi_indicator.smoothToHide();


                if (response.body() != null) {
                    if(200 == response.body().getCode()){
                        Log.w(TAG,"PetTypeListResponse" + new Gson().toJson(response.body()));
                        dropDownListResponseCall();
                        usertypedataBeanList = response.body().getData().getUsertypedata();
                        if(usertypedataBeanList != null && usertypedataBeanList.size()>0){
                            setPetType(usertypedataBeanList);
                        }
                    }



                }








            }


            @Override
            public void onFailure(@NonNull Call<PetTypeListResponse> call,@NonNull  Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"PetTypeListResponse flr"+t.getMessage());
            }
        });

    }
    private void setPetType(List<PetTypeListResponse.DataBean.UsertypedataBean> usertypedataBeanList) {
        ArrayList<String> pettypeArrayList = new ArrayList<>();
        pettypeArrayList.add("Select Pet Type");
        for (int i = 0; i < usertypedataBeanList.size(); i++) {

            String petType = usertypedataBeanList.get(i).getPet_type_title();
            hashMap_PetTypeid.put(usertypedataBeanList.get(i).getPet_type_title(), usertypedataBeanList.get(i).get_id());

            Log.w(TAG,"petType-->"+petType);
            pettypeArrayList.add(petType);

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(AddYourPetActivity.this, R.layout.spinner_item, pettypeArrayList);
            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
            sprpettype.setAdapter(spinnerArrayAdapter);


        }
    }

    private void breedTypeResponseByPetIdCall(String petTypeId) {
        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface ApiService = APIClient.getClient().create(RestApiInterface.class);
        Call<BreedTypeResponse> call = ApiService.breedTypeResponseByPetIdCall(RestUtils.getContentType(),breedTypeRequest(petTypeId));
        Log.w(TAG,"url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<BreedTypeResponse>() {
            @Override
            public void onResponse(@NonNull Call<BreedTypeResponse> call, @NonNull Response<BreedTypeResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG, "BreedTypeResponse" + "--->" + new Gson().toJson(response.body()));


                if (response.body() != null) {
                    if (200 == response.body().getCode()) {
                        breedTypedataBeanList = response.body().getData();
                        if(breedTypedataBeanList != null && breedTypedataBeanList.size()>0){
                            setBreedType(breedTypedataBeanList);
                        }

                    }

                } else {

                }


            }



            @Override
            public void onFailure(@NonNull Call<BreedTypeResponse> call, @NonNull Throwable t) {
                avi_indicator.smoothToHide();

                Log.w(TAG,"BreedTypeResponse flr"+"--->" + t.getMessage());
            }
        });

    }
    private void setBreedType(List<BreedTypeResponse.DataBean> breedTypedataBeanList) {
        ArrayList<String> pettypeArrayList = new ArrayList<>();
        pettypeArrayList.add("Select Pet Breed");
        for (int i = 0; i < breedTypedataBeanList.size(); i++) {

            String petType = breedTypedataBeanList.get(i).getPet_breed();

            Log.w(TAG,"petType-->"+petType);
            pettypeArrayList.add(petType);

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(AddYourPetActivity.this, R.layout.spinner_item, pettypeArrayList);
            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
            sprpetbreed.setAdapter(spinnerArrayAdapter);


        }
    }
    private BreedTypeRequest breedTypeRequest(String petTypeId) {
        BreedTypeRequest breedTypeRequest = new BreedTypeRequest();
        breedTypeRequest.setPet_type_id(petTypeId);
        Log.w(TAG,"breedTypeRequest"+ "--->" + new Gson().toJson(breedTypeRequest));
        return breedTypeRequest;
    }
}