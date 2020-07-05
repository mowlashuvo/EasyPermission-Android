package com.crackpot.easy_permission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class AdapterPermission extends RecyclerView.Adapter<AdapterPermission.ViewHolder> {
    private Activity activity;
    private ArrayList<ModelPermission> permissionArrayList;
    static boolean check;

    private OnItemClickListener mOnItemClickListener;


    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }


    public AdapterPermission(Activity activity, ArrayList<ModelPermission> permissionArrayList) {
        this.activity = activity;
        this.permissionArrayList = permissionArrayList;
    }

    @NonNull
    @Override
    public AdapterPermission.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.permission_list_item, parent, false);
        return new ViewHolder(view);
    }
    private int[][] states = new int[][] {
            new int[] {-android.R.attr.state_checked},
            new int[] {android.R.attr.state_checked},
    };

    private int[] thumbColors = new int[] {
            Color.WHITE,//off
            Color.WHITE,//on
    };


    @Override
    public void onBindViewHolder(@NonNull AdapterPermission.ViewHolder holder, final int position) {
        final ModelPermission permission = permissionArrayList.get(position);
        int[] trackColors = new int[] {
                Color.GRAY,//off
                Color.parseColor(permission.getColor())//on
        };
        DrawableCompat.setTintList(DrawableCompat.wrap(holder.permissionSwitch.getThumbDrawable()), new ColorStateList(states, thumbColors));
        DrawableCompat.setTintList(DrawableCompat.wrap(holder.permissionSwitch.getTrackDrawable()), new ColorStateList(states, trackColors));


        setSwitchChecked(permission.getPermissionName(), holder.permissionSwitch);


        Picasso.get().load(permission.getImage()).into(holder.imageView);


//        String ItemName = tv.getText().toString();
//        String qty = quantity.getText().toString();
//        Intent intent = new Intent("custom-message");
//        //            intent.putExtra("quantity",Integer.parseInt(quantity.getText().toString()));
//        intent.putExtra("quantity",qty);
//        intent.putExtra("item",ItemName);
//        LocalBroadcastManager.getInstance(activity).sendBroadcast(intent);


        holder.title.setText(permission.getTitle());
        holder.description.setText(permission.getDescription());
//        holder.permissionSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    AdapterPermission.this.askForPermission(permission.getPermissionName(), permission.getPermissionCode());
//                    AdapterPermission.this.setSwitchChecked(permission.getPermissionName(), holder.permissionSwitch);
////                    activity.startActivityForResult(new Intent(activity, PermissionActivity.class),0x1);
//
//
//                } else {
//                    AdapterPermission.this.denyForPermission();
//                    AdapterPermission.this.setSwitchChecked(Manifest.permission.ACCESS_FINE_LOCATION, holder.permissionSwitch);
////                    Toast.makeText(activity, "" + permission.getPermissionSwitch() + " DENIED.", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        });



        holder.permissionSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(buttonView, permission, position,isChecked);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return permissionArrayList.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title, description;
        SwitchCompat permissionSwitch;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.permission_image);
            title = itemView.findViewById(R.id.permission_title);
            description = itemView.findViewById(R.id.permission_description);
            permissionSwitch = itemView.findViewById(R.id.permission_switch);

        }
    }





    public interface OnItemClickListener {
        void onItemClick(CompoundButton switch_btn, ModelPermission permission, int position, boolean isChecked);
    }


    private void setSwitchChecked(String permission, SwitchCompat permissionSwitch) {
        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            permissionSwitch.setChecked(false);
        } else {
            permissionSwitch.setChecked(true);
        }
    }

}
