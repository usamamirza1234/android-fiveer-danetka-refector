package com.armoomragames.denketa.IntroAuxilaries.Admin.DanetkaDetails;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.IntroAuxilaries.DModelCustomDanetka;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.GameSession.RegiltoRCVAdapter;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Post_AddAdminsDanetkas;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.CustomToast;
import com.armoomragames.denketa.Utils.IWebCallback;
import com.bumptech.glide.Glide;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static androidx.core.content.PermissionChecker.checkSelfPermission;
import static com.armoomragames.denketa.Utils.IAdapterCallback.EVENT_A;

public class CreateAdminDenketaFragment extends Fragment implements View.OnClickListener {

    private static final String KEY_POSITION = "position";
    private static final int QUESTION_IMAGE = 111;
    private static final int ANSWER_IMAGE = 222;
    LinearLayout insert1, insert2;
    ImageView imvInsert1, imvInsert2;
    ImageView imvAddMainRegitlto, imvAddSingle;
    RecyclerView rcvRegilto;
    EditText edtRegilto;
    RelativeLayout rlRegiltoitem;
    LinearLayout llSubmit;
    TextView txvSubmit;
    ArrayList<String> lstRegilto;
    RegiltoRCVAdapter regiltoRCVAdapter;
    EditText edtTitle, edtQuestion, edtAns;
    private String QUESTION_IMAGE_FILE = "";
    private String ANSWER_IMAGE_FILE = "";
    private Dialog progressDialog;

    public static Fragment newInstance(int position) {
        Fragment frag = new CreateAdminDenketaFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        frag.setArguments(args);
        return (frag);
    }

    public static File saveImage(final Context context, final String imageData) throws IOException {
        final byte[] imgBytesData = Base64.decode(imageData,
                Base64.DEFAULT);

        final File file = File.createTempFile("image", null, context.getCacheDir());
        final FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
                fileOutputStream);
        try {
            bufferedOutputStream.write(imgBytesData);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                bufferedOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_create_admin_denketa, container, false);

        init();
        bindviews(frg);

        populatePopulationList();
        return frg;
    }

    private void init() {
        lstRegilto = new ArrayList<>();
    }
    RelativeLayout rlToolbar, rlBack, rlCross;
    private void bindviews(View frg) {
        rlToolbar = frg.findViewById(R.id.act_intro_rl_toolbar);
        rlBack = frg.findViewById(R.id.act_intro_lay_toolbar_rlBack);
        rlCross = frg.findViewById(R.id.act_intro_lay_toolbar_rlCross);

        rlBack.setOnClickListener(this);
        rlCross.setOnClickListener(this);




        rcvRegilto = frg.findViewById(R.id.frg_make_rcv_regilto);
        imvAddMainRegitlto = frg.findViewById(R.id.frg_make_imv_addRegiltoMain);
        edtRegilto = frg.findViewById(R.id.frg_make_edtRegilto);


        edtTitle = frg.findViewById(R.id.frg_make_edtTitle);
        edtQuestion = frg.findViewById(R.id.frg_make_edtRidle);
        edtAns = frg.findViewById(R.id.frg_make_edtAnswer);


        rlRegiltoitem = frg.findViewById(R.id.frg_make_rlRegiltoItem);
        imvAddSingle = frg.findViewById(R.id.frg_make_imvRegilto);

        llSubmit = frg.findViewById(R.id.frg_make_llSubmit);
        txvSubmit = frg.findViewById(R.id.frg_make_txvSubmit);


        insert1 = frg.findViewById(R.id.frg_make_llInsert1);
        insert2 = frg.findViewById(R.id.frg_make_llInsert2);
        imvInsert1 = frg.findViewById(R.id.frg_make_inset1Imv);
        imvInsert2 = frg.findViewById(R.id.frg_make_inset2Imv);


        insert1.setOnClickListener(this);
        insert2.setOnClickListener(this);
        imvAddSingle.setOnClickListener(this);
        imvAddMainRegitlto.setOnClickListener(this);
        llSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.act_intro_lay_toolbar_rlBack:
                ((IntroActivity) getActivity()).onBackPressed();

                break;
            case R.id.act_intro_lay_toolbar_rlCross:
                ((IntroActivity) getActivity()).navToPreSignInVAFragment();

                break;

            case R.id.frg_make_llInsert1:
                if (isGrantedPermCamera()) {
                    if (isGrantedPermWriteExternalStorage()) {
                        if (getActivity() != null) {
                            selectQuestionImage();
//                            showProgDialog();
//                            getGalleryPic(true);
                        }
                    }
                }


                break;
            case R.id.frg_make_llInsert2:
                if (isGrantedPermCamera()) {
                    if (isGrantedPermWriteExternalStorage()) {
                        if (getActivity() != null) {
                            selectAnswerImage();
//                            showProgDialog();
//                            getGalleryPic(false);
                        }
                    }
                }

                break;


            case R.id.frg_make_llSubmit:
                if (AppConfig.getInstance().mUser.isLoggedIn()) {
                    if (!edtTitle.getText().toString().equals("") && !edtQuestion.getText().toString().equals("") && !edtAns.getText().toString().equals("")
                    ) {
                        showProgDialog();

                        String hints = android.text.TextUtils.join("=", lstRegilto);
                        DModelCustomDanetka dModelCustomDanetka = null;
                        try {
                            dModelCustomDanetka = new DModelCustomDanetka(
                                    edtTitle.getText().toString(),
                                    edtAns.getText().toString(),
                                    saveImage(getContext(), QUESTION_IMAGE_FILE),
                                    saveImage(getContext(), ANSWER_IMAGE_FILE),
                                    hints,
                                    edtQuestion.getText().toString(),
                                    "l",
                                    AppConfig.getInstance().mUser.getUser_Id() + ""
                            );
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        requestAddCustomDanteka(dModelCustomDanetka);
                    } else
                        CustomToast.showToastMessage(getActivity(), "Please fill all fields", Toast.LENGTH_LONG);
                } else
                    CustomToast.showToastMessage(getActivity(), "Please login first!", Toast.LENGTH_LONG);

                break;
            case R.id.frg_make_imv_addRegiltoMain:
                if (lstRegilto.size() <= 4) {
                    rlRegiltoitem.setVisibility(View.VISIBLE);
                    imvAddMainRegitlto.setVisibility(View.GONE);
                } else
                    CustomToast.showToastMessage(getActivity(), "Maximum Added", Toast.LENGTH_LONG);
                break;
            case R.id.frg_make_imvRegilto:
                if (lstRegilto.size() <= 4) {
                    if (!edtRegilto.getText().toString().equals("")) {
                        lstRegilto.add(edtRegilto.getText().toString());
                        populatePopulationList();
                        rlRegiltoitem.setVisibility(View.GONE);
                        edtRegilto.setText("");
                        imvAddMainRegitlto.setVisibility(View.VISIBLE);
                        AppConfig.getInstance().closeKeyboard(getActivity());
                    } else
                        CustomToast.showToastMessage(getActivity(), "Enter some text", Toast.LENGTH_LONG);

                } else
                    CustomToast.showToastMessage(getActivity(), "Maximum Added", Toast.LENGTH_LONG);
                break;
        }
    }

    private void requestAddCustomDanteka(DModelCustomDanetka dModelCustomDanetka) {
        Intro_WebHit_Post_AddAdminsDanetkas intro_webHit_post_addAdminsDanetkas = new Intro_WebHit_Post_AddAdminsDanetkas();
        intro_webHit_post_addAdminsDanetkas.postCustomDanetka(getContext(), new IWebCallback() {
            @Override
            public void onWebResult(boolean isSuccess, String strMsg) {
                if (isSuccess) {
                    dismissProgDialog();
                    CustomToast.showToastMessage(getActivity(), "ADDED as an admin SUCCESSFULLY.", Toast.LENGTH_SHORT);
                    llSubmit.setBackground(getActivity().getResources().getDrawable(R.drawable.shp_rect_rounded_app_green));
                    txvSubmit.setText("Submitted");
                    lstRegilto.clear();
                    imvInsert1.setVisibility(View.GONE);
                    imvInsert2.setVisibility(View.GONE);
                    edtAns.setText("");
                    edtQuestion.setText("");
                    edtTitle.setText("");
                    edtAns.setText("");
                    llSubmit.setOnClickListener(null);
                } else {
                    dismissProgDialog();
                    CustomToast.showToastMessage(getActivity(), strMsg, Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onWebException(Exception ex) {
                dismissProgDialog();
                CustomToast.showToastMessage(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT);
            }
        }, dModelCustomDanetka);
    }

    private void populatePopulationList() {


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        if (regiltoRCVAdapter == null) {

            regiltoRCVAdapter = new RegiltoRCVAdapter(getActivity(), lstRegilto, (eventId, position) -> {

                switch (eventId) {
                    case EVENT_A:


                        break;
                }

            });


            rcvRegilto.setLayoutManager(linearLayoutManager);
            rcvRegilto.setAdapter(regiltoRCVAdapter);

        } else {
            regiltoRCVAdapter.notifyDataSetChanged();
        }
    }

    private void dismissProgDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void showProgDialog() {

        progressDialog = new Dialog(getActivity(), R.style.AppTheme);
//        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress_loading);
        WindowManager.LayoutParams wmlp = progressDialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER | Gravity.CENTER;
        wmlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wmlp.height = ViewGroup.LayoutParams.MATCH_PARENT;

        ImageView imageView = progressDialog.findViewById(R.id.img_anim);
        Glide.with(getContext()).asGif().load(R.raw.loading).into(imageView);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();


    }

    //region GalleryFunctionality
    private void selectAnswerImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, ANSWER_IMAGE);

    }

    private void selectQuestionImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, QUESTION_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == QUESTION_IMAGE) {
                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getActivity().getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                thumbnail = getResizedBitmap(thumbnail, 400);
                Log.w("image", picturePath + "");
                imvInsert1.setImageBitmap(thumbnail);
                BitMapToStringQuestionImage(thumbnail);
            } else if (requestCode == ANSWER_IMAGE) {
                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getActivity().getContentResolver().query(selectedImage,
                        filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                thumbnail = getResizedBitmap(thumbnail, 400);
                Log.w("image", picturePath + "");
                imvInsert2.setImageBitmap(thumbnail);
                BitMapToStringAnswerImage(thumbnail);
            }
        }
    }

    public String BitMapToStringAnswerImage(Bitmap userImage1) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImage1.compress(Bitmap.CompressFormat.PNG, 60, baos);
        byte[] b = baos.toByteArray();
        ANSWER_IMAGE_FILE = Base64.encodeToString(b, Base64.DEFAULT);
        return ANSWER_IMAGE_FILE;
    }

    public String BitMapToStringQuestionImage(Bitmap userImage1) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImage1.compress(Bitmap.CompressFormat.PNG, 60, baos);
        byte[] b = baos.toByteArray();
        QUESTION_IMAGE_FILE = Base64.encodeToString(b, Base64.DEFAULT);
        return QUESTION_IMAGE_FILE;
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
    //endregion

    //region Permissions
    private boolean isGrantedPermWriteExternalStorage() {
        if (Build.VERSION.SDK_INT >= 23) {
            int hasWritePermission = checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (hasWritePermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        AppConstt.REQ_CODE_WRITE_EXTERNAL_STORAGE);
                return false;
            }
            return true;
        } else
            return true;
    }

    private boolean isGrantedPermCamera() {
        if (Build.VERSION.SDK_INT >= 23) {
            int hasCameraPermission = checkSelfPermission(getContext(), Manifest.permission.CAMERA);
            if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        AppConstt.REQ_CODE_PERM_CAMERA);
                return false;
            }
            return true;
        } else
            return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case AppConstt.REQ_CODE_PERM_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Strorage Permission Granted
//                    getGalleryPic(true);
                    CustomToast.showToastMessage(getActivity(),
                            "Permission granted", Toast.LENGTH_SHORT);
                } else {
                    // Permission Denied
                    CustomToast.showToastMessage(getActivity(),
                            "Permission denied", Toast.LENGTH_SHORT);
                }
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

    //endregion


}
