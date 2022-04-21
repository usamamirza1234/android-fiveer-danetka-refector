package com.armoomragames.denketa.Example;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.armoomragames.denketa.R;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;

public class BlankFragment extends Fragment implements View.OnClickListener{

    public BlankFragment() {
    }

    public static final String KEY_User_Document1 = "doc1";
    ImageView IDProf;
    ImageView IdProfaaa;
    Button Upload_Btn;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_blank, container, false);


        return  view;
    }

    private void selectAnswerImage() {
        Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, ANSWER_IMAGE);

    }

    private void selectQuestionImage() {
        Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, QUESTION_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == QUESTION_IMAGE)
            {
                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c =getActivity(). getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                thumbnail=getResizedBitmap(thumbnail, 400);
                Log.w("image", picturePath+"");
                IDProf.setImageBitmap(thumbnail);
                BitMapToStringQuestionImage(thumbnail);
            }
            else  if (requestCode == ANSWER_IMAGE)
            {
                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c =getActivity(). getContentResolver().query(selectedImage,
                        filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                thumbnail=getResizedBitmap(thumbnail, 400);
                Log.w("image", picturePath+"");
                IdProfaaa.setImageBitmap(thumbnail);
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

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
    private String QUESTION_IMAGE_FILE ="";
    private String ANSWER_IMAGE_FILE ="";

    private static final int QUESTION_IMAGE= 111;
    private static final int ANSWER_IMAGE= 222;



    @Override
    public void onClick(View v) {
        if (QUESTION_IMAGE_FILE.equals("") || QUESTION_IMAGE_FILE.equals(null)) {

            ContextThemeWrapper ctw = new ContextThemeWrapper( getContext(), R.style.Theme_AppCompat);
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctw);
            alertDialogBuilder.setTitle("Id Prof Can't Empty ");
            alertDialogBuilder.setMessage("Id Prof Can't empty please select any one document");
            alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                }
            });
            alertDialogBuilder.show();
            return;
        }
        else{

//            if (AppStatus.getInstance(this).isOnline()) {
//                SendDetail();
//
//
//                //           Toast.makeText(this,"You are online!!!!",Toast.LENGTH_LONG).show();
//
//            } else {
//
//                Toast.makeText(this,"You are not online!!!!",Toast.LENGTH_LONG).show();
//                Log.v("Home", "############################You are not online!!!!");
//            }

        }
    }
}