package com.armoomragames.denketa.IntroAuxilaries.Admin.DanetkaDetails;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.IntroAuxilaries.DModelCustomDanetka;
import com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies.DModel_MyDenketa;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Post_UpdateAdminDanetkas;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.CustomToast;
import com.armoomragames.denketa.Utils.IWebCallback;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static androidx.core.content.PermissionChecker.checkSelfPermission;

public class EditDenketaFragment extends Fragment implements View.OnClickListener {
    private static final int QUESTION_IMAGE = 111;
    private static final int ANSWER_IMAGE = 222;
    Bundle bundle;
    LinearLayout insert1, insert2;
    ImageView imvInsert1, imvInsert2;
    EditText edtRegilto;
    LinearLayout llSubmit;
    TextView txvSubmit;
    ArrayList<String> lstRegilto;
    EditText edtTitle, edtQuestion, edtAns, edtLearnmore;

    ArrayList<DModel_MyDenketa> lst_MyDenketa;
    int position = 0;

    private Dialog progressDialog;
    private String QUESTION_IMAGE_FILE = "";
    private String ANSWER_IMAGE_FILE = "";
    RelativeLayout rlToolbar, rlBack, rlCross;



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_edit_denketa, container, false);

        init();
        bindviews(frg);


        return frg;
    }

    private void init() {
        bundle = this.getArguments();
        if (bundle != null) {
            position = bundle.getInt("key_danetka_position");
            lst_MyDenketa = bundle.getParcelableArrayList("list");

        }
        lstRegilto = new ArrayList<>();

    }

    private void bindviews(View frg) {
        rlToolbar = frg.findViewById(R.id.act_intro_rl_toolbar);
        rlBack = frg.findViewById(R.id.act_intro_lay_toolbar_rlBack);
        rlCross = frg.findViewById(R.id.act_intro_lay_toolbar_rlCross);

        rlBack.setOnClickListener(this);
        rlCross.setOnClickListener(this);

        edtRegilto = frg.findViewById(R.id.frg_make_edtRegilto);
        edtTitle = frg.findViewById(R.id.frg_make_edtTitle);
        edtQuestion = frg.findViewById(R.id.frg_make_edtRidle);
        edtAns = frg.findViewById(R.id.frg_make_edtAnswer);
        edtLearnmore = frg.findViewById(R.id.frg_make_edtLearnmore);


        llSubmit = frg.findViewById(R.id.frg_make_llSubmit);
        txvSubmit = frg.findViewById(R.id.frg_make_txvSubmit);


        insert1 = frg.findViewById(R.id.frg_make_llInsert1);
        insert2 = frg.findViewById(R.id.frg_make_llInsert2);
        imvInsert1 = frg.findViewById(R.id.frg_make_inset1Imv);
        imvInsert2 = frg.findViewById(R.id.frg_make_inset2Imv);


        insert1.setOnClickListener(this);
        insert2.setOnClickListener(this);

        llSubmit.setOnClickListener(this);

        setdata();

    }

    private void setdata() {

        try {
            edtTitle.setText(lst_MyDenketa.get(position).getStrName());
            edtQuestion.setText(lst_MyDenketa.get(position).getQuestion());
            edtAns.setText(lst_MyDenketa.get(position).getAnswer());
            edtRegilto.setText(lst_MyDenketa.get(position).getHint());
            edtLearnmore.setText(lst_MyDenketa.get(position).getLearnmore());
            String danetka_Image = "http://18.119.55.236:2000/images/" + lst_MyDenketa.get(position).getStrImage();
            String answer_Image = "http://18.119.55.236:2000/images/" + lst_MyDenketa.get(position).getAnswerImage();

            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.ic_logo)
                    .error(R.drawable.ic_logo)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .dontAnimate()
                    .dontTransform();

//            Glide.with(getContext())
//                    .load(new URL(danetka_Image))
//                    .apply(options)
//                    .into(imvInsert1);
//            Glide.with(getContext())
//                    .load(new URL(answer_Image))
//                    .apply(options)
//                    .into(imvInsert2);


            Glide.with(getContext())
                    .load(new URL(danetka_Image))
                    .apply(options).listener(new RequestListener<Drawable>() {

                @Override
                public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    BitmapDrawable drawable = (BitmapDrawable) imvInsert1.getDrawable();
                    BitMapToStringQuestionImage(drawable.getBitmap());

//
//                            setPicPathForQuestion(true);
//                            convert(drawable.getBitmap(), true);
//                            filePhotoForQuestion=      bitmapToFile(getContext(),drawable.getBitmap(),"xyz-0");
                    return false;
                }


            })
                    .into(imvInsert1);
            Glide.with(getContext())
                    .load(new URL(answer_Image))
                    .apply(options).listener(new RequestListener<Drawable>() {

                @Override
                public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    BitmapDrawable drawable = (BitmapDrawable) imvInsert2.getDrawable();
                    BitMapToStringAnswerImage(drawable.getBitmap());

//
//                            setPicPathForQuestion(true);
//                            convert(drawable.getBitmap(), true);
//                            filePhotoForQuestion=      bitmapToFile(getContext(),drawable.getBitmap(),"xyz-0");
                    return false;
                }


            }).into(imvInsert2);


//            Glide.with(getContext())
//                    .load(danetka_Image)
//                    .apply(options)
//                    .listener(new RequestListener<Drawable>() {
//
//                        @Override
//                        public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                            return false;
//                        }
//
//                        @Override
//                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                            BitmapDrawable drawable = (BitmapDrawable) imvInsert1.getDrawable();
////
////                            setPicPathForQuestion(true);
////                            convert(drawable.getBitmap(), true);
////                            filePhotoForQuestion=      bitmapToFile(getContext(),drawable.getBitmap(),"xyz-0");
//                            return false;
//                        }
//
//
//                    })
//                    .into(imvInsert1);
//
//            Glide.with(getContext())
//                    .load(answer_Image)
//                    .apply(options)
//                    .listener(new RequestListener<Drawable>() {
//
//                        @Override
//                        public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                            return false;
//                        }
//
//                        @Override
//                        public boolean onResourceReady(Drawable resource, Object model,
//                                                       Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                            BitmapDrawable drawable = (BitmapDrawable) imvInsert2.getDrawable();
////                            setPicPathForQuestion(false);
////                            convert(drawable.getBitmap(), false);
////                            filePhotoForAnswer=      bitmapToFile(getContext(),drawable.getBitmap(),"xyz-0");
//                            return false;
//                        }
//
//
//                    })
//                    .into(imvInsert2);


            Log.d("image", "danetka_Image " + danetka_Image);
            Log.d("image", "answer_Image " + answer_Image);

        } catch (Exception e) {

        }


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
                    if (!edtTitle.getText().toString().equals("") &&
                            !edtQuestion.getText().toString().equals("")
                            && !edtAns.getText().toString().equals("")
                            && !edtRegilto.getText().toString().equals("")
                            && !edtLearnmore.getText().toString().equals("")
                    ) {
                        showProgDialog();

                        DModelCustomDanetka dModelCustomDanetka = null;
                        try {
                            dModelCustomDanetka = new DModelCustomDanetka(
                                    edtTitle.getText().toString(),
                                    edtAns.getText().toString(),
                                    saveImage(getContext(), QUESTION_IMAGE_FILE),
                                    saveImage(getContext(), ANSWER_IMAGE_FILE),
                                    edtRegilto.getText().toString(),
                                    edtQuestion.getText().toString(),
                                    edtLearnmore.getText().toString(),
                                    AppConfig.getInstance().mUser.getUser_Id() + ""
                            );
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        requestUpdateDanetka(dModelCustomDanetka);
                    } else
                        CustomToast.showToastMessage(getActivity(), "Please fill all fields", Toast.LENGTH_LONG);
                } else
                    CustomToast.showToastMessage(getActivity(), "Please login first!", Toast.LENGTH_LONG);

                break;


        }
    }

    private void requestUpdateDanetka(DModelCustomDanetka dModelCustomDanetka) {


        Intro_WebHit_Post_UpdateAdminDanetkas intro_webHit_post_updateAdminDanetkas = new Intro_WebHit_Post_UpdateAdminDanetkas();
        intro_webHit_post_updateAdminDanetkas.postCustomDanetka(getContext(), new IWebCallback() {
            @Override
            public void onWebResult(boolean isSuccess, String strMsg) {
                if (isSuccess) {
                    dismissProgDialog();
                    CustomToast.showToastMessage(getActivity(), "UPDATE SUCCESSFULLY.", Toast.LENGTH_SHORT);
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
        }, dModelCustomDanetka, Integer.parseInt(lst_MyDenketa.get(position).getStrId()));
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

    private void selectAnswerImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, ANSWER_IMAGE);

    }

    private void selectQuestionImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, QUESTION_IMAGE);
    }


    //region GalleryFunctionality
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

    public static File saveImage(final Context context, final String imageData) throws IOException {
        final byte[] imgBytesData = android.util.Base64.decode(imageData,
                android.util.Base64.DEFAULT);

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
