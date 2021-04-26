package com.petfolio.infinitus.vendor;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.petfolio.infinitus.R;
import com.petfolio.infinitus.petlover.AddYourPetActivity;
import com.petfolio.infinitus.utils.ConnectionDetector;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class EditManageProdcutsActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_back)
    ImageView img_back;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.avi_indicator)
    AVLoadingIndicatorView avi_indicator;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_product_title)
    EditText edt_product_title;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_product_price)
    EditText edt_product_price;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_product_thresould)
    EditText edt_product_thresould;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_product_descriptions)
    EditText edt_product_descriptions;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_update)
    Button btn_update;

    private String productid,producttitle,productthreshold;
    private int productprice;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_manage_prodcuts);
        ButterKnife.bind(this);

        avi_indicator.setVisibility(View.GONE);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            productid = extras.getString("productid");
            producttitle = extras.getString("producttitle");
            productprice = extras.getInt("productprice");
            productthreshold = extras.getString("productthreshold");

            if(producttitle != null){
                edt_product_title.setText(producttitle);
            }if(productprice != 0){
                edt_product_price.setText(productprice+"");
            }else{
                edt_product_price.setText("0");
            }
            if(productthreshold != null){
                edt_product_thresould.setText(productthreshold);
            }

            btn_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addYourProductValidator();
                }
            });

            img_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });



        }

    }

    public void addYourProductValidator() {
        boolean can_proceed = true;



        if (edt_product_title.getText().toString().isEmpty() && edt_product_price.getText().toString().isEmpty() && edt_product_thresould.getText().toString().isEmpty() && edt_product_descriptions.getText().toString().isEmpty()) {
            Toasty.warning(getApplicationContext(), "Please enter the fields", Toast.LENGTH_SHORT, true).show();
            can_proceed = false;
        } else if (edt_product_title.getText().toString().trim().equals("")) {
            edt_product_title.setError("Please enter product title");
            edt_product_title.requestFocus();
            can_proceed = false;
        }
        else if (edt_product_price.getText().toString().trim().equals("")) {
            edt_product_price.setError("Please enter product price");
            edt_product_price.requestFocus();
            can_proceed = false;
        }else if (edt_product_thresould.getText().toString().trim().equals("")) {
            edt_product_thresould.setError("Please enter product thresould");
            edt_product_thresould.requestFocus();
            can_proceed = false;
        }else if (edt_product_descriptions.getText().toString().trim().equals("")) {
            edt_product_descriptions.setError("Please enter product descriptions");
            edt_product_descriptions.requestFocus();
            can_proceed = false;
        }


        if (can_proceed) {
            if (new ConnectionDetector(EditManageProdcutsActivity.this).isNetworkAvailable(EditManageProdcutsActivity.this)) {

            }

        }





    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}