package com.petfolio.infinitus.serviceprovider;


import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import com.petfolio.infinitus.R;
import com.petfolio.infinitus.activity.LoginActivity;
import com.petfolio.infinitus.interfaces.SoSCallListener;
import com.petfolio.infinitus.petlover.PetLoverEditProfileActivity;
import com.petfolio.infinitus.petlover.PetLoverProfileScreenActivity;
import com.petfolio.infinitus.petlover.PetMyappointmentsActivity;
import com.petfolio.infinitus.sessionmanager.SessionManager;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class ServiceProviderNavigationDrawer extends AppCompatActivity implements View.OnClickListener, SoSCallListener {


    public NavigationView navigationView;
    private DrawerLayout drawerLayout;
    LayoutInflater inflater;
    View view, header;
    Toolbar toolbar;


    ImageView drawerImg;
    CircleImageView nav_header_imageView;
    FrameLayout frameLayout;
    TextView nav_header_profilename, nav_header_emailid,nav_header_edit;
    //SessionManager session;
    String name, image_url, phoneNo;


     public TextView tvWelcomeName;

     public Menu menu;

    BroadcastReceiver imgReceiver;





    SessionManager session;


    String emailid = "";


    @SuppressLint({"InflateParams", "LongLogTag"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        String TAG = "ServiceProviderNavigationDrawer";
        Log.w(TAG,"onCreate---->");

        inflater = LayoutInflater.from(this);
        view = inflater.inflate(R.layout.navigation_drawer_sp_layout, null);
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getProfileDetails();
        name = user.get(SessionManager.KEY_FIRST_NAME);
        emailid = user.get(SessionManager.KEY_EMAIL_ID);
        phoneNo = user.get(SessionManager.KEY_MOBILE);
       String userid = user.get(SessionManager.KEY_ID);
       Log.w(TAG,"userid : "+userid);





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

    @SuppressLint("NonConstantResourceId")
    private void initUI(View view) {

        //Initializing NavigationView
        navigationView = view.findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);

        frameLayout = view.findViewById(R.id.base_container);

        navigationView.setNavigationItemSelectedListener(menuItem -> {
            //Closing drawer on item click
            drawerLayout.closeDrawers();
            //Check to see which item was being clicked and perform appropriate action
            switch (menuItem.getItemId()) {


                //Replacing the main content with ContentFragment Which is our Inbox View;
                case R.id.nav_item_one:

                    return true;

                // For rest of the options we just show a toast on click
                case R.id.nav_item_two:

                    return true;

                case R.id.nav_item_three:
                    gotoMyAppointments();
                    return true;

                case R.id.nav_item_four:
                    return true;

                case R.id.nav_item_five:
                    return true;

                case R.id.nav_item_six:
                    return true;
                case R.id.nav_item_seven:
                    confirmLogoutDialog();
                    return true;




                default:
                    return true;

            }
        });

         menu = navigationView.getMenu();



        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = view.findViewById(R.id.drawer_layout);
        header = navigationView.getHeaderView(0);
        nav_header_imageView = header.findViewById(R.id.nav_header_imageView);
        nav_header_emailid = header.findViewById(R.id.nav_header_emailid);
        nav_header_profilename = header.findViewById(R.id.nav_header_profilename);
        nav_header_edit = header.findViewById(R.id.nav_header_edit);
        // Glide.with(this).load(image_url).into(nav_header_imageView);

        nav_header_emailid.setText(emailid);
        nav_header_profilename.setText(name);

        LinearLayout llheader = header.findViewById(R.id.llheader);
        llheader.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), PetLoverProfileScreenActivity.class)));

        nav_header_edit.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), PetLoverEditProfileActivity.class)));


       /* if (!image_url.isEmpty()) {
            Glide.with(this)
                    .load(image_url)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .error(R.drawable.logo_white)
                    .into(nav_header_imageView);
        }*/
    }


    @SuppressLint("SetTextI18n")
    private void initToolBar(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerImg = toolbar.findViewById(R.id.img_menu);


        tvWelcomeName = toolbar.findViewById(R.id.toolbar_title);

        tvWelcomeName.setText("Home");




        toggleView();
    }






    private void toggleView() {
        drawerImg.setOnClickListener(v -> {
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
        if (v.getId() == R.id.img_menu) {
            drawerMethod();
        }
    }




    private void confirmLogoutDialog(){

        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(ServiceProviderNavigationDrawer.this);
        alertDialogBuilder.setMessage("Are you sure want to logout?");
        alertDialogBuilder.setPositiveButton("yes",
                (arg0, arg1) -> gotoLogout());

        alertDialogBuilder.setNegativeButton("No", (dialog, which) -> alertDialogBuilder.setCancelable(true));

        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }






    private void gotoMyAppointments() {
        startActivity(new Intent(getApplicationContext(), PetMyappointmentsActivity.class));

    }










    private void gotoLogout() {
        session.logoutUser();
        session.setIsLogin(false);
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();




    }


    @Override
    public void soSCallListener(long phonenumber) {
        /*if(phonenumber != 0){
            String sosPhonenumber = String.valueOf(phonenumber);
        }*/
    }
}
