package com.crackpot.easy_permission;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class PermissionActivity extends AppCompatActivity {
    ImageView backBtn;
    TextView toolbarTitle;
    RecyclerView recyclerView;
    AdapterPermission adapter;
    ArrayList<ModelPermission> permissionArrayList = new ArrayList<>();

    SwitchCompat permissionSwitch;

    static final Integer LOCATION = 0x1;
    static final Integer CALL = 0x2;
    static final Integer READ_CONTACT = 0x3;
    static final Integer READ_EXST = 0x4;
    static final Integer CAMERA = 0x5;
    static final Integer ACCOUNTS = 0x6;
    static final Integer GPS_SETTINGS = 0x7;


    private static final String TAG = "PermissionActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        initComponent();
        loadPermission(permissionArrayList);


        adapter.setOnItemClickListener(new AdapterPermission.OnItemClickListener() {
            @Override
            public void onItemClick(CompoundButton switch_btn, ModelPermission permission, int position, boolean isChecked) {


                permissionSwitch = (SwitchCompat) switch_btn;

                if (isChecked) {
                    askForPermission(permission.getPermissionName(), permission.getPermissionCode());
                    setSwitchChecked(permission.getPermissionName(), (SwitchCompat) switch_btn);
//                    activity.startActivityForResult(new Intent(activity, PermissionActivity.class),0x1);


                } else {

                    permissionSwitch.setChecked(true);
                    denyForPermission();
//                    setSwitchChecked(Manifest.permission.ACCESS_FINE_LOCATION, (SwitchCompat) switch_btn);
//                    Toast.makeText(activity, "" + permission.getPermissionSwitch() + " DENIED.", Toast.LENGTH_SHORT).show();

                }


            }
        });


    }


    private void initComponent() {
        recyclerView = findViewById(R.id.id_permission_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


    public void loadPermission(ArrayList<ModelPermission> permissionArrayList) {
//        permissionArrayList.add(new ModelPermission(R.drawable.ic_location_access, "#68D5C9", "Location Access", "For batter pickup experience", "location", Manifest.permission.ACCESS_FINE_LOCATION, LOCATION));
//        permissionArrayList.add(new ModelPermission(R.drawable.ic_camera_access, "#398FE4", "Camera Access", "Directly capture image for your profile picture", "camera", Manifest.permission.CAMERA, CAMERA));
//        permissionArrayList.add(new ModelPermission(R.drawable.ic_contact_access, "#FC597A", "Contact Access", "Get contact list for easy top Up recharges", "contact", Manifest.permission.READ_CONTACTS, READ_CONTACT));
//        permissionArrayList.add(new ModelPermission(R.drawable.ic_gallery_access, "#9F68D5", "Gallery Access", "Select Image as profile photo from gallery", "storage", Manifest.permission.READ_EXTERNAL_STORAGE, READ_EXST));
//        permissionArrayList.add(new ModelPermission("",0,"Location Access","","phone", Manifest.permission.CALL_PHONE,CALL));

        adapter = new AdapterPermission(PermissionActivity.this, permissionArrayList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED) {

            permissionSwitch.setChecked(true);

        } else {
            permissionSwitch.setChecked(false);

        }
    }


    private void askForPermission(String permission, Integer requestCode) {
//        check = (ContextCompat.checkSelfPermission(PermissionActivity.this, permission) != PackageManager.PERMISSION_GRANTED);
        if (ContextCompat.checkSelfPermission(PermissionActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(PermissionActivity.this, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(PermissionActivity.this, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(PermissionActivity.this, new String[]{permission}, requestCode);
            }
        }
    }

    private void denyForPermission() {
        try {
            Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            startActivity(intent);
        }

    }


    private void setSwitchChecked(String permission, SwitchCompat permissionSwitch) {
        if (ContextCompat.checkSelfPermission(PermissionActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {
            permissionSwitch.setChecked(false);
        } else {
            permissionSwitch.setChecked(true);
        }
    }


}
