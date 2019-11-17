package com.example.projetc2application.fragments;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.projetc2application.R;
import com.example.projetc2application.activities.ProfileActivity;
import com.example.projetc2application.beans.UserBean;
import com.example.projetc2application.utils.GlobalFunctions;

public class BottomSheetChangeProfileDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    public UserBean profileBean;
    LinearLayout llDeletePicture;
    LinearLayout llViewPicture;
    TextView tvChoosePicture, tvTakePicture;
    Activity activity;


    public BottomSheetChangeProfileDialogFragment() {
    }

    public static BottomSheetChangeProfileDialogFragment newInstance(UserBean profileBean) {
        BottomSheetChangeProfileDialogFragment frag = new BottomSheetChangeProfileDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("profileBean", profileBean);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileBean = (UserBean) getArguments().getSerializable("profileBean");
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(final Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        //Get the content View
        activity = getActivity();
        View contentView = View.inflate(getContext(), R.layout.fragment_bottom_sheet_change_profile_dialog, null);

        dialog.setContentView(contentView);
        ((View) contentView.getParent()).setBackgroundColor(Color.TRANSPARENT);

        llDeletePicture = contentView.findViewById(R.id.llDeletePicture);
        llViewPicture = contentView.findViewById(R.id.llViewPicture);
        tvChoosePicture = contentView.findViewById(R.id.tvChoosePicture);
        tvTakePicture = contentView.findViewById(R.id.tvTakePicture);

        if (!profileBean.getImage().equals("")) {
            llViewPicture.setVisibility(View.VISIBLE);
            llDeletePicture.setVisibility(View.VISIBLE);
        }

        tvTakePicture.setOnClickListener(this);
        tvChoosePicture.setOnClickListener(this);
        llDeletePicture.setOnClickListener(this);
        llViewPicture.setOnClickListener(this);

        setCancelable(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvTakePicture:
                if (activity instanceof ProfileActivity) {
                    ((ProfileActivity) activity).takePicture();
                }
                this.dismiss();
                break;
            case R.id.tvChoosePicture:
                if (activity instanceof ProfileActivity) {
                    ((ProfileActivity) activity).choosePicture();
                }
                this.dismiss();
                break;
            case R.id.llDeletePicture:
                if (activity instanceof ProfileActivity) {
                    ((ProfileActivity) activity).deleteImage();
                }
                this.dismiss();
                break;
            case R.id.llViewPicture:
                GlobalFunctions.goToViewImage(activity, profileBean.getImage());
                this.dismiss();
                break;
        }
    }
}
