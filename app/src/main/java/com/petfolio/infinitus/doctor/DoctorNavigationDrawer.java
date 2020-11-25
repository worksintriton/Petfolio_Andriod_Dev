package com.petfolio.infinitus.doctor;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.petfolio.infinitus.R;
//import com.petfolio.infinitus.sessionmanager.SessionManager;

import de.hdodenhof.circleimageview.CircleImageView;


public class DoctorNavigationDrawer extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    public NavigationView navigationView;
    private DrawerLayout drawerLayout;
    LayoutInflater inflater;
    View view, header;
    Toolbar toolbar;

    // Make sure to be using android.support.v7.app.ActionBarDrawerToggle version.
    // The android.support.v4.app.ActionBarDrawerToggle has been deprecated.
    private ActionBarDrawerToggle drawerToggle;
    ImageView drawerImg;
    CircleImageView nav_header_imageView;
    FrameLayout frameLayout;
    TextView header_title, nav_header_textView;
    //SessionManager session;
    String name, image_url, phoneNo;
    private Integer jockey_id;
   // private APIInterface apiInterface;
   // private AppliedJockeyResponse appliedJockeyResponse;
    ProgressDialog pDialog;

     public TextView tvWelcomeName;
     Button btnNotificationPatient;
    public LinearLayout lladdfamilyheader;
     public Menu menu;
     public MenuItem becomeajockey,jockeyoptions;

    BroadcastReceiver imgReceiver;

    private String TAG ="NavigationDrawer";


    ProgressDialog progressDialog;


//    SessionManager session;

    private double latitude, longitude;
    private String addressLine = "";

    String emailid = "",patientid = "";






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        Log.w(TAG,"onCreate---->");

        inflater = LayoutInflater.from(this);
        view = inflater.inflate(R.layout.doctor_navigation_drawer_layout, null);
       /* session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        name = user.get(SessionManager.KEY_USER_NAME);
        user_mode = user.get(SessionManager.KEY_USER_MODE);
        phoneNo = user.get(SessionManager.KEY_MOBILE);
        image_url = session.getImagePath();
        jockey_id = Integer.valueOf(user.get(SessionManager.JOCKEY_ID));*/




        Log.w(TAG,"user details--->"+"name :"+" "+ name+" " +"image_url :"+ image_url);

        initUI(view);
        initToolBar(view);

//        session = new SessionManager(getApplicationContext());
//        session.checkLogin();

       // myBoradcastReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (imgReceiver != null) {
            unregisterReceiver(imgReceiver);
        }
    }

    private void initUI(View view) {
        pDialog = new ProgressDialog(DoctorNavigationDrawer.this);
        pDialog.setMessage(DoctorNavigationDrawer.this.getString(R.string.please_wait));
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(true);
        //Initializing NavigationView
        navigationView = view.findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);

        frameLayout = view.findViewById(R.id.base_container);

        navigationView.setNavigationItemSelectedListener(this);
         menu = navigationView.getMenu();
       // becomeajockey = menu.findItem(R.id.nav_item_seven);
      //  jockeyoptions = menu.findItem(R.id.nav_item_eight);


        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = view.findViewById(R.id.drawer_layout);
        header = navigationView.getHeaderView(0);
        nav_header_imageView = header.findViewById(R.id.nav_header_imageView);
        nav_header_textView = header.findViewById(R.id.nav_header_emailid);
        // Glide.with(this).load(image_url).into(nav_header_imageView);
        nav_header_textView.setText(name);


       /* if (!image_url.isEmpty()) {
            Glide.with(this)
                    .load(image_url)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .error(R.drawable.logo_white)
                    .into(nav_header_imageView);
        }*/
    }


    private void initToolBar(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerImg = toolbar.findViewById(R.id.img_menu);
       // header_title = (TextView) toolbar.findViewById(R.id.header_title);






        toggleView();
    }




    private void toggleView() {
        drawerImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isClickable()) {
                    drawerMethod();
                } else {

                    Intent intent_re = getIntent();
                    overridePendingTransition(0, 0);
                    intent_re.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(intent_re);

                }
            }
        });
    }

    public void drawerMethod() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);

        } else {
            drawerLayout.openDrawer(GravityCompat.START);
        }

    }

    public void setContentView(int layoutId) {

        Log.e("BaseOncreate", "setContentView");
        View activityView = inflater.inflate(layoutId, null);
        frameLayout.addView(activityView);
        super.setContentView(view);
        //drawerMethod();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_menu:
                drawerMethod();
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


        //Closing drawer on item click
        drawerLayout.closeDrawers();

        //Check to see which item was being clicked and perform appropriate action
        switch (menuItem.getItemId()) {

            //Replacing the main content with ContentFragment Which is our Inbox View;
            case R.id.nav_item_one:
                gotoMyFamily();
                return true;

            // For rest of the options we just show a toast on click
            case R.id.nav_item_two:
                gotoMyAppointments();
                return true;

                case R.id.nav_item_three:
                gotoHealthFiles();
                return true;

            case R.id.nav_item_five:
                gotoMyFamily();
                return true;

            case R.id.nav_item_six:
                gotoInvoices();
                return true;

            case R.id.nav_item_seven:
                gotoTermsandConditions();
                return true;
            case R.id.nav_item_eight:
                confirmLogoutDialog();
                 return true;




            default:
                return true;

        }

    }



    private void confirmLogoutDialog(){

        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(DoctorNavigationDrawer.this);
        alertDialogBuilder.setMessage("Are you sure want to logout?");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1)
                    {

                        gotoLogout();


                    }
                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialogBuilder.setCancelable(true);
            }
        });

        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }


    private void gotoMyFamily() {
      // finish();
    }



    private void gotoMyAppointments() {

    }

    private void gotoHealthFiles() {


    }

    private void gotoInvoices() {

    }
    private void gotoAboutSalveoHealthCare() {

    }

    private void gotoTermsandConditions() {
      /*  Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse("http://www.mysalveo.com/#/terms"));
        startActivity(intent);*/
        try
        {
             String pdfUrl = "http://mysalveo.com/api/uploads/Salveo%20Terms%20&%20Conditions,%20Privacy%20Policy.pdf";
            Intent intentUrl = new Intent(Intent.ACTION_VIEW);
            intentUrl.setDataAndType(Uri.parse(pdfUrl), "application/pdf");
            intentUrl.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentUrl);
        }
        catch (ActivityNotFoundException e)
        {
            Toast.makeText(DoctorNavigationDrawer.this, "No PDF Viewer Installed", Toast.LENGTH_LONG).show();
        }

    }



    private void gotoProfile() {


    }


    private void gotoLogout() {



    }



    @Override
    protected void onResume() {
        super.onResume();
        try{
            Log.w(TAG,"onResume--->");

       /* session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        name = user.get(SessionManager.KEY_USER_NAME);
        user_mode = user.get(SessionManager.KEY_USER_MODE);
        phoneNo = user.get(SessionManager.KEY_MOBILE);
        image_url = session.getImagePath();
        jockey_id = Integer.valueOf(user.get(SessionManager.JOCKEY_ID));*/


            //  checkisAppliedForJockeyandCountAPI();

            Menu menu = navigationView.getMenu();
            MenuItem target = menu.findItem(R.id.nav_item_seven);

           // checkLocation();
        }catch (Exception e){
            e.printStackTrace();
        }


    }






}
