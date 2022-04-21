package com.armoomragames.denketa.IntroAuxilaries.WebServices;


import android.content.Context;
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

import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;

import cz.msebera.android.httpclient.Header;


public class Intro_WebHit_Post_AddAdminsDanetkas {

    public static RModel_ERROR responseObjectError = null;
    public static RModel_SignIn responseObject = null;
    private final AsyncHttpClient mClient = new AsyncHttpClient();
    public Context mContext;

    public void postCustomDanetka(Context context, final IWebCallback iWebCallback,
                                  DModelCustomDanetka dModelCustomDanetka) {
        mContext = context;
        String myUrl = AppConfig.getInstance().getBaseUrlApi() + ApiMethod.POST.addCustomDanetkas;

        mClient.setMaxRetriesAndTimeout(AppConstt.LIMIT_API_RETRY, AppConstt.LIMIT_TIMOUT_MILLIS_UPLOAD);
        mClient.addHeader(ApiMethod.HEADER.Authorization, AppConfig.getInstance().mUser.getAuthorization());


        RequestParams params = new RequestParams();
        try {


            if (AppConfig.getInstance().mUser.isAdmin())
                params.put("danetkaType", "standard");
            else
                params.put("danetkaType", "custom");
            params.put("title", dModelCustomDanetka.getTitle());
            params.put("answer", dModelCustomDanetka.getAnswer());
            params.put("hint", dModelCustomDanetka.getHint());
            params.put("question", dModelCustomDanetka.getQuestion());
            params.put("learnMore", dModelCustomDanetka.getLearnMore());
            params.put("masterId", dModelCustomDanetka.getMasterId());
            params.put("image", dModelCustomDanetka.getImage(), "image/jpeg");
            params.put("answerImage", dModelCustomDanetka.getAnswerImage(), "image/jpeg");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        Log.d("LOG_AS", "postCustomDanetka: " + myUrl + params);

        mClient.post(myUrl, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String strResponse;


                        try {
                            Gson gson = new Gson();
                            strResponse = new String(responseBody, StandardCharsets.UTF_8);
                            Log.d("LOG_AS", "postCustomDanetka: strResponse" + strResponse);
                            responseObject = gson.fromJson(strResponse, RModel_SignIn.class);

                            switch (statusCode) {

                                case AppConstt.ServerStatus.OK:
                                case AppConstt.ServerStatus.CREATED:
                                    iWebCallback.onWebResult(true, "");
                                    break;

                                default:
                                    gson = new Gson();
                                    strResponse = new String(responseBody, StandardCharsets.UTF_8);
                                    Log.d("LOG_AS", "postSignIn: strResponse" + strResponse);
                                    responseObjectError = gson.fromJson(strResponse, RModel_ERROR.class);
                                    iWebCallback.onWebResult(false, responseObjectError.getData());
                                    break;
                            }
                        } catch (Exception ex) {
                            Gson gson = new Gson();
                            strResponse = new String(responseBody, StandardCharsets.UTF_8);
                            Log.d("LOG_AS", "postSignIn: strResponse" + strResponse);
                            responseObjectError = gson.fromJson(strResponse, RModel_ERROR.class);
                            iWebCallback.onWebResult(false, responseObjectError.getData());
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

        private String message;

        private String data;

        public int getCode() {
            return this.code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return this.message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getData() {
            return this.data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }

}
