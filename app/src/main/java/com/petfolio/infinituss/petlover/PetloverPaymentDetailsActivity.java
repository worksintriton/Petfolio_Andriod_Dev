package com.petfolio.infinituss.petlover;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.print.PrintAttributes;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.petfolio.infinituss.R;
import com.petfolio.infinituss.activity.NotificationActivity;
import com.petfolio.infinituss.adapter.PetLoverPaymDetailsAdapter;
import com.petfolio.infinituss.adapter.PetLoverSOSAdapter;
import com.petfolio.infinituss.api.APIClient;
import com.petfolio.infinituss.api.RestApiInterface;
import com.petfolio.infinituss.interfaces.SoSCallListener;
import com.petfolio.infinituss.requestpojo.FetchPetloverPaymDetaRequest;

import com.petfolio.infinituss.responsepojo.FetchPetloverPaymDetaResponse;
import com.petfolio.infinituss.responsepojo.PetLoverDashboardResponse;
import com.petfolio.infinituss.sessionmanager.SessionManager;
import com.petfolio.infinituss.utils.ConnectionDetector;
import com.petfolio.infinituss.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;
import com.wwdablu.soumya.simplypdf.DocumentInfo;
import com.wwdablu.soumya.simplypdf.SimplyPdf;
import com.wwdablu.soumya.simplypdf.SimplyPdfDocument;
import com.wwdablu.soumya.simplypdf.composers.TableComposer;
import com.wwdablu.soumya.simplypdf.composers.TextComposer;
import com.wwdablu.soumya.simplypdf.composers.models.TableProperties;
import com.wwdablu.soumya.simplypdf.composers.models.TextProperties;
import com.wwdablu.soumya.simplypdf.composers.models.cell.Cell;
import com.wwdablu.soumya.simplypdf.composers.models.cell.TextCell;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetloverPaymentDetailsActivity extends AppCompatActivity implements View.OnClickListener, SoSCallListener {

    private final String TAG = "PetloverPaymentDetailsActivity";

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_norecords)
    TextView txt_no_records;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_recenttransc)
    RecyclerView rv_recenttransc;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refresh_layout;

    SessionManager session;
    String userid = "";
    private Context mContext;

    List<FetchPetloverPaymDetaResponse.DataBean> dataBeanList;
    private Dialog alertDialog;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.include_petlover_footer)
    View include_petlover_footer;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_download)
    ImageView img_download;


    private String active_tag = "1";


    String tag;

    String fromactivity;
    private Dialog dialog;

    private static final int REQUEST_PHONE_CALL =1 ;
    private String sosPhonenumber;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.include_petlover_header)
    View include_petlover_header;

    private TextComposer textComposer;
    private TableComposer tableComposer;
    private SimplyPdfDocument simplyPdfDocument;


     /* Petlover Bottom Navigation */
    /* Petlover Bottom Navigation */

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rl_home)
    RelativeLayout rl_home;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rl_care)
    RelativeLayout rl_care;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.title_care)
    TextView title_care;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_care)
    ImageView img_care;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rl_service)
    RelativeLayout rl_service;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.title_serv)
    TextView title_serv;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_serv)
    ImageView img_serv;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rl_shop)
    RelativeLayout rl_shop;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.title_shop)
    TextView title_shop;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_shop)
    ImageView img_shop;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rl_comn)
    RelativeLayout rl_comn;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.title_community)
    TextView title_community;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_community)
    ImageView img_community;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rl_homes)
    RelativeLayout rl_homes;




    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petlover_payment_details);

        ButterKnife.bind(this);

        avi_indicator.setVisibility(View.GONE);

        session = new SessionManager(this);
        HashMap<String, String> user = session.getProfileDetails();

        userid = user.get(SessionManager.KEY_ID);

        //userid = "603e27792c2b43125f8cb802";

        Log.w(TAG," userid : "+userid);





        if (new ConnectionDetector(PetloverPaymentDetailsActivity.this).isNetworkAvailable(PetloverPaymentDetailsActivity.this)) {
            fetchpaymntdetailsResponseCall();
        }

        img_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                simplyPdfDocument = SimplyPdf.with(PetloverPaymentDetailsActivity.this, new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/test.pdf"))
                        .colorMode(DocumentInfo.ColorMode.COLOR)
                        .paperSize(PrintAttributes.MediaSize.ISO_A4)
                        .margin(DocumentInfo.Margins.DEFAULT)
                        .paperOrientation(DocumentInfo.Orientation.PORTRAIT)
                        .build();

                textComposer = new TextComposer(simplyPdfDocument);

                tableComposer = new TableComposer(simplyPdfDocument);

                testTextComposed();
            }
        });

        refresh_layout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        refresh_layout.setEnabled(false);
                        if (new ConnectionDetector(PetloverPaymentDetailsActivity.this).isNetworkAvailable(PetloverPaymentDetailsActivity.this)) {
                            fetchpaymntdetailsResponseCall();
                        }

                    }
                }
        );

        ImageView img_back = include_petlover_header.findViewById(R.id.img_back);
        ImageView img_sos = include_petlover_header.findViewById(R.id.img_sos);
        ImageView img_notification = include_petlover_header.findViewById(R.id.img_notification);
        ImageView img_cart = include_petlover_header.findViewById(R.id.img_cart);
        ImageView img_profile = include_petlover_header.findViewById(R.id.img_profile);
        TextView toolbar_title = include_petlover_header.findViewById(R.id.toolbar_title);
        toolbar_title.setText("Payment Details");

        img_back.setOnClickListener(v -> onBackPressed());

        img_sos.setVisibility(View.GONE);
        img_cart.setVisibility(View.GONE);
        img_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), NotificationActivity.class));
            }
        });
        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PetLoverProfileScreenActivity.class);
                intent.putExtra("fromactivity",TAG);
                startActivity(intent);
            }
        });



        /*home*/
        title_care.setTextColor(getResources().getColor(R.color.darker_grey_new,getTheme()));
        img_care.setImageResource(R.drawable.grey_care);
        title_serv.setTextColor(getResources().getColor(R.color.darker_grey_new,getTheme()));
        img_serv.setImageResource(R.drawable.grey_servc);
        title_shop.setTextColor(getResources().getColor(R.color.darker_grey_new,getTheme()));
        img_shop.setImageResource(R.drawable.grey_shop);
        title_community.setTextColor(getResources().getColor(R.color.darker_grey_new,getTheme()));
        img_community.setImageResource(R.drawable.grey_community);


        rl_home.setOnClickListener(this);
        rl_care.setOnClickListener(this);
        rl_service.setOnClickListener(this);
        rl_shop.setOnClickListener(this);
        rl_comn.setOnClickListener(this);
        rl_homes.setOnClickListener(this);


    }


    private void testTextComposed() {

        TextProperties textProperties = new TextProperties();
        textProperties.textSize = 24;
        textProperties.alignment = Layout.Alignment.ALIGN_CENTER;

        int w_50_cent = simplyPdfDocument.pageWidth() / 2;

        TableProperties colProperties = new TableProperties();
        colProperties.borderWidth = 1;
        colProperties.borderColor = "#000000";
        tableComposer.setProperties(colProperties);

        List<List<Cell>> composedList = new ArrayList<>();
        ArrayList<Cell> rowList = new ArrayList<>();

        //1st row
        rowList.add(new TextCell("Likes", textProperties, w_50_cent));
        rowList.add(new TextCell("Dislikes", textProperties, w_50_cent));
        composedList.add(rowList);

        textProperties = new TextProperties();
        textProperties.textSize = 24;
        textProperties.alignment = Layout.Alignment.ALIGN_CENTER;
        textProperties.alignment = Layout.Alignment.ALIGN_NORMAL;
        textProperties.bulletSymbol = "•";
        textProperties.isBullet = true;

        //2nd row
        rowList = new ArrayList<>();
        Cell cell = new TextCell("Apple", textProperties, w_50_cent);
        rowList.add(cell);
        rowList.add(new TextCell("Guava", textProperties, w_50_cent));
        composedList.add(rowList);

        //3rd row
        rowList = new ArrayList<>();
        rowList.add(new TextCell("Banana", textProperties, w_50_cent));
        rowList.add(new TextCell("Coconut", textProperties, w_50_cent));
        composedList.add(rowList);

        //4th row
        rowList = new ArrayList<>();
        rowList.add(new TextCell("Mango", textProperties, w_50_cent));
        composedList.add(rowList);
        tableComposer.draw(composedList);

        simplyPdfDocument.insertEmptySpace(25);
        textProperties.isBullet = false;

        //new table
        composedList.clear();
        rowList = new ArrayList<>();
        rowList.add(new TextCell("Small Left Text", textProperties, w_50_cent));
        rowList.add(new TextCell("This is a big text on the right column which will be multiple lines.",
                textProperties, w_50_cent));
        composedList.add(rowList);
        tableComposer.draw(composedList);

        simplyPdfDocument.insertEmptySpace(25);

        //new table
        composedList.clear();
        rowList = new ArrayList<>();

        cell = new TextCell(
                "This is a big text a a the right column which will be multiple lines.", textProperties, w_50_cent);
        cell.setHorizontalPadding(25);
        cell.setVerticalPadding(50);
        rowList.add(cell);

        cell = new TextCell("Small right text", textProperties, w_50_cent);
        cell.setHorizontalPadding(25);
        cell.setVerticalPadding(50);
        rowList.add(cell);
        composedList.add(rowList);
        tableComposer.draw(composedList);

        try {
            simplyPdfDocument.finish();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("LongLogTag")
    private void fetchpaymntdetailsResponseCall() {

        avi_indicator.setVisibility(View.VISIBLE);
        avi_indicator.smoothToShow();
        RestApiInterface apiInterface = APIClient.getClient().create(RestApiInterface.class);
        Call<FetchPetloverPaymDetaResponse> call = apiInterface.fetchpetlvrpaymdetaillistResponseCall(RestUtils.getContentType(), fetchPetloverPaymDetaRequest());
        Log.w(TAG,"FetchPetloverPaymDetaResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<FetchPetloverPaymDetaResponse>() {
            @SuppressLint({"SetTextI18n", "LogNotTimber"})
            @Override
            public void onResponse(@NonNull Call<FetchPetloverPaymDetaResponse> call, @NonNull Response<FetchPetloverPaymDetaResponse> response) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"SPFavCreateResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {

                    if (200 == response.body().getCode()) {
                        if (response.body().getData() != null&&!response.body().getData().isEmpty()) {

                            dataBeanList = response.body().getData();

                            if(dataBeanList.size()>0){

                                setViewPaymtDetails(dataBeanList);
                            }
                            else {

                                txt_no_records.setText("No Payments Found");

                                rv_recenttransc.setVisibility(View.GONE);
                            }
                        }
                    }

                }


            }

            @Override
            public void onFailure(@NonNull Call<FetchPetloverPaymDetaResponse> call,@NonNull Throwable t) {
                avi_indicator.smoothToHide();
                Log.w(TAG,"FetchPetloverPaymDetaResponse flr"+ t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setViewPaymtDetails(List<FetchPetloverPaymDetaResponse.DataBean> dataBeanList) {

        rv_recenttransc.setLayoutManager(new LinearLayoutManager(PetloverPaymentDetailsActivity.this));
        rv_recenttransc.setItemAnimator(new DefaultItemAnimator());
        PetLoverPaymDetailsAdapter petLoverDoctorNewFavAdapter = new PetLoverPaymDetailsAdapter(PetloverPaymentDetailsActivity.this, dataBeanList,dataBeanList.size());
        rv_recenttransc.setAdapter(petLoverDoctorNewFavAdapter);
    }

    @SuppressLint({"LogNotTimber", "LongLogTag"})
    private FetchPetloverPaymDetaRequest fetchPetloverPaymDetaRequest() {

        /**
         * user_id : 603e27792c2b43125f8cb802
         */

        FetchPetloverPaymDetaRequest fetchPetloverPaymDetaRequest = new FetchPetloverPaymDetaRequest();
        fetchPetloverPaymDetaRequest.setUser_id(userid);

        Log.w(TAG,"fetchPetloverPaymDetaRequest "+ new Gson().toJson(fetchPetloverPaymDetaRequest));
        return fetchPetloverPaymDetaRequest;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(PetloverPaymentDetailsActivity.this, PetLoverDashboardActivity.class);
        startActivity(i);
        finish();
    }


    public void callDirections(String tag){
        Intent intent = new Intent(getApplicationContext(), PetLoverDashboardActivity.class);
        intent.putExtra("tag",tag);
        startActivity(intent);
        finish();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_sos:
                showSOSAlert(APIClient.sosList);
                break;
            case R.id.img_notification:
                startActivity(new Intent(getApplicationContext(), NotificationActivity.class));
                break;
            case R.id.img_cart:
                break;
            case R.id.img_profile:
                Intent intent = new Intent(getApplicationContext(),PetLoverProfileScreenActivity.class);
                intent.putExtra("fromactivity",TAG);
                startActivity(intent);
                break;

            case R.id.rl_homes:
                callDirections("1");
                break;

            case R.id.rl_home:
                callDirections("1");
                break;


            case R.id.rl_shop:
                callDirections("2");
                break;

            case R.id.rl_service:
                callDirections("3");
                break;

            case R.id.rl_care:
                callDirections("4");
                break;


            case R.id.rl_comn:
                callDirections("5");
                break;

        }

    }

    @Override
    public void soSCallListener(long phonenumber) {
        if(phonenumber != 0){
            sosPhonenumber = String.valueOf(phonenumber);
        }

    }

    private void showSOSAlert(List<PetLoverDashboardResponse.DataBean.SOSBean> sosList) {

        try {

            dialog = new Dialog(PetloverPaymentDetailsActivity.this);
            dialog.setContentView(R.layout.sos_popup_layout);
            RecyclerView rv_sosnumbers = (RecyclerView)dialog.findViewById(R.id.rv_sosnumbers);
            Button btn_call = (Button)dialog.findViewById(R.id.btn_call);
            TextView txt_no_records = (TextView)dialog.findViewById(R.id.txt_no_records);
            ImageView img_close = (ImageView)dialog.findViewById(R.id.img_close);
            img_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            if(sosList != null && sosList.size()>0){
                rv_sosnumbers.setVisibility(View.VISIBLE);
                btn_call.setVisibility(View.VISIBLE);
                txt_no_records.setVisibility(View.GONE);
                rv_sosnumbers.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                rv_sosnumbers.setItemAnimator(new DefaultItemAnimator());
                PetLoverSOSAdapter petLoverSOSAdapter = new PetLoverSOSAdapter(getApplicationContext(), sosList,this);
                rv_sosnumbers.setAdapter(petLoverSOSAdapter);
            }else{
                rv_sosnumbers.setVisibility(View.GONE);
                btn_call.setVisibility(View.GONE);
                txt_no_records.setVisibility(View.VISIBLE);
                txt_no_records.setText(getResources().getString(R.string.no_phone_numbers));

            }

            btn_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(PetloverPaymentDetailsActivity.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
                    }
                    else
                    {
                        gotoPhone();
                    }

                }
            });



            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();


        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }




    }
    private void gotoPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + sosPhonenumber));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(intent);
    }
    private void setMargins(RelativeLayout rl_layout, int i, int i1, int i2, int i3) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)rl_layout.getLayoutParams();
        params.setMargins(i, i1, i2, i3);
        rl_layout.setLayoutParams(params);
    }
}