package com.armoomragames.denketa.IntroAuxilaries.WebServices;


import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.IntroAuxilaries.DModelCustomDanetka;
import com.armoomragames.denketa.Utils.ApiMethod;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IWebCallback;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import cz.msebera.android.httpclient.Header;


public class Intro_WebHit_Delete_AdminDanetkas {

    public static RModel_SignIn responseObject = null;
    public static RModel_ERROR responseObjectError = null;
    private final AsyncHttpClient mClient = new AsyncHttpClient();
    public Context mContext;

    public void deleteDanetka(Context context, final IWebCallback iWebCallback,
                                   int _id) {
        mContext = context;
        String myUrl = AppConfig.getInstance().getBaseUrlApi() + ApiMethod.Delete.removeDanetkas + _id;

        mClient.setMaxRetriesAndTimeout(AppConstt.LIMIT_API_RETRY, AppConstt.LIMIT_TIMOUT_MILLIS);
        mClient.addHeader(ApiMethod.HEADER.Authorization, AppConfig.getInstance().mUser.getAuthorization());





        Log.d("LOG_AS", "deleteDanetka: " + myUrl);

        mClient.delete(myUrl, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String strResponse;


                        try {
                            Gson gson = new Gson();
                            strResponse = new String(responseBody, StandardCharsets.UTF_8);
                            Log.d("LOG_AS", "deleteDanetka: strResponse" + strResponse);
                            responseObject = gson.fromJson(strResponse, RModel_SignIn.class);
                            switch (statusCode) {
                                case AppConstt.ServerStatus.OK:
                                case AppConstt.ServerStatus.CREATED:
                                    iWebCallback.onWebResult(true, "");
                                    break;

                                default:
                                    strResponse = new String(responseBody, StandardCharsets.UTF_8);
                                    Log.d("LOG_AS", "postSignIn: strResponse" + strResponse);
                                    responseObjectError = gson.fromJson(strResponse, RModel_ERROR.class);
                                    iWebCallback.onWebResult(false, responseObjectError.getMessage());
//                                    iWebCallback.onWebResult(false, responseObject.getMessage());
//                                    AppConfig.getInstance().parsErrorMessage(iWebCallback, responseBody);
                                    break;
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            Gson gson = new Gson();
                            strResponse = new String(responseBody, StandardCharsets.UTF_8);
                            Log.d("LOG_AS", "postSignIn: strResponse" + strResponse);
                            responseObjectError = gson.fromJson(strResponse, RModel_ERROR.class);
                            iWebCallback.onWebResult(false, responseObjectError.getMessage());
                        }
                    }


                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        switch (statusCode) {
                            case AppConstt.ServerStatus.NETWORK_ERROR:
                                iWebCallback.onWebResult(false, AppConfig.getInstance().getNetworkErrorMessage());
                                break;

                            case AppConstt.ServerStatus.FORBIDDEN:
                            case AppConstt.ServerStatus.UNAUTHORIZED:
                            default:
                                AppConfig.getInstance().parsErrorMessage(iWebCallback, responseBody);
                                break;
                        }
                    }
                }

        );
    }

    File saveBitmapToFile(File dir, String fileName, Bitmap bm,
                          Bitmap.CompressFormat format, int quality) {

        File imageFile = new File(dir, fileName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(imageFile);

            bm.compress(format, quality, fos);

            fos.close();
        } catch (Exception e) {
            Log.e("app", e.getMessage());
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return imageFile;
    }

    public class RModel_SignIn {

        private int code;
        private String status;
        private String message;
        private Data data;

        public int getCode() {
            return this.code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getStatus() {
            return this.status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return this.message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Data getData() {
            return this.data;
        }

        public void setData(Data data) {
            this.data = data;
        }

        public class Data {
            private String masterId;

            private boolean status;

            private int id;

            private String title;

            private String hint;

            private String answer;

            private String question;

            private String image;

            private String answerImage;

            private String learnMore;

            private int updatedTime;

            public String getMasterId() {
                return this.masterId;
            }

            public void setMasterId(String masterId) {
                this.masterId = masterId;
            }

            public boolean getStatus() {
                return this.status;
            }

            public void setStatus(boolean status) {
                this.status = status;
            }

            public int getId() {
                return this.id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return this.title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getHint() {
                return this.hint;
            }

            public void setHint(String hint) {
                this.hint = hint;
            }

            public String getAnswer() {
                return this.answer;
            }

            public void setAnswer(String answer) {
                this.answer = answer;
            }

            public String getQuestion() {
                return this.question;
            }

            public void setQuestion(String question) {
                this.question = question;
            }

            public String getImage() {
                return this.image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getAnswerImage() {
                return this.answerImage;
            }

            public void setAnswerImage(String answerImage) {
                this.answerImage = answerImage;
            }

            public String getLearnMore() {
                return this.learnMore;
            }

            public void setLearnMore(String learnMore) {
                this.learnMore = learnMore;
            }

            public int getUpdatedTime() {
                return this.updatedTime;
            }

            public void setUpdatedTime(int updatedTime) {
                this.updatedTime = updatedTime;
            }
        }
    }

    public class RModel_ERROR {
        private int code;
        private String status;
        private String message;
        private Data data;

        public int getCode() {
            return this.code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getStatus() {
            return this.status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return this.message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Data getData() {
            return this.data;
        }

        public void setData(Data data) {
            this.data = data;
        }

        public class Data {
        }
    }

}
