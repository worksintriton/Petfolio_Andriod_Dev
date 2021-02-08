package com.petfolio.infinitus.doctor;

import android.annotation.SuppressLint;
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
import android.widget.RelativeLayout;
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
import com.petfolio.infinitus.activity.LoginActivity;
import com.petfolio.infinitus.activity.NotificationActivity;
import com.petfolio.infinitus.petlover.PetLoverEditProfileActivity;
import com.petfolio.infinitus.petlover.PetLoverProfileScreenActivity;
import com.petfolio.infinitus.sessionmanager.SessionManager;
//import com.petfolio.infinitus.sessionmanager.SessionManager;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class DoctorNavigationDrawer extends AppCompatActivity implements View.OnClickListener {

    private String TAG ="DoctorNavigationDrawer";

    public NavigationView navigationView;
    private DrawerLayout drawerLayout;
    LayoutInflater inflater;
    View view, header;
    Toolbar toolbar;


    ImageView drawerImg;
    CircleImageView nav_header_imageView;
    TextView nav_header_profilename, nav_header_emailid;

    FrameLayout frameLayout;

    //SessionManager session;
    String name, image_url, phoneNo;



     public Menu menu;


    BroadcastReceiver imgReceiver;






//    SessionManager session;



    String emailid = "";
    private SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        Log.w(TAG,"onCreate---->");

        inflater = LayoutInflater.from(this);
        view = inflater.inflate(R.layout.doctor_navigation_drawer_layout, null);

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getProfileDetails();
        name = user.get(SessionManager.KEY_FIRST_NAME);
        emailid = user.get(SessionManager.KEY_EMAIL_ID);
        phoneNo = user.get(SessionManager.KEY_MOBILE);



        Log.w(TAG,"user details--->"+"name :"+" "+ name+" " +"image_url :"+ image_url);

        initUI(view);
        initToolBar(view);


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

        //Initializing NavigationView
        navigationView = view.findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);

        frameLayout = view.findViewById(R.id.base_container);




        menu = navigationView.getMenu();


        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = view.findViewById(R.id.drawer_layout);
        header = navigationView.getHeaderView(0);
        nav_header_imageView = header.findViewById(R.id.nav_header_imageView);
        nav_header_emailid = header.findViewById(R.id.nav_header_emailid);
        nav_header_profilename = header.findViewById(R.id.nav_header_profilename);
        // Glide.with(this).load(image_url).into(nav_header_imageView);

        nav_header_emailid.setText(emailid);
        nav_header_profilename.setText(name);
        RelativeLayout llheader = header.findViewById(R.id.llheader);
        llheader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DoctorProfileScreenActivity.class));
            }
        });

        TextView nav_header_edit = header.findViewById(R.id.nav_header_edit);
        nav_header_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DoctorEditProfileActivity.class));
            }
        });


       /* if (!image_url.isEmpty()) {
            Glide.with(this)
                    .load(image_url)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .error(R.drawable.logo_white)
                    .into(nav_header_imageView);
        }*/

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                drawerLayout.closeDrawers();



                //Check to see which item was being clicked and perform appropriate action
                switch (item.getItemId()) {

                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_item_one:
                        return true;

                    // For rest of the options we just show a toast on click
                    case R.id.nav_item_two:
                        gotoMyCalendar();
                        return true;

                    case R.id.nav_item_three:
                        return true;

                    case R.id.nav_item_five:
                        return true;

                    case R.id.nav_item_six:
                        return true;

                    case R.id.nav_item_seven:
                        return true;
                    case R.id.nav_item_eight:
                        confirmLogoutDialog();
                        return true;


                    default:
                        return true;
                }
            }
        });

    }


    private void initToolBar(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerImg = toolbar.findViewById(R.id.img_menu);
       // header_title = (TextView) toolbar.findViewById(R.id.header_title);

        ImageView img_notification = toolbar.findViewById(R.id.img_notification);
        ImageView img_cart = toolbar.findViewById(R.id.img_cart);
        ImageView img_profile = toolbar.findViewById(R.id.img_profile);

        img_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NotificationActivity.class));

            }
        });







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

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_menu) {
            drawerMethod();
        }
    }



    private void gotoMyCalendar() {
        Intent i = new Intent(DoctorNavigationDrawer.this, DoctorMyCalendarActivity.class);
        startActivity(i);

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







    private void gotoLogout() {
        session.logoutUser();
        session.setIsLogin(false);
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();

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
