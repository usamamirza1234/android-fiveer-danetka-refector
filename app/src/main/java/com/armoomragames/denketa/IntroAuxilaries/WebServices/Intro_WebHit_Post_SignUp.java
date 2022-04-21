package com.armoomragames.denketa.IntroAuxilaries.WebServices;


import android.content.Context;
import android.util.Log;

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.Utils.ApiMethod;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IWebCallback;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.nio.charset.StandardCharsets;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;


public class Intro_WebHit_Post_SignUp {

    public static RModel_SignIn responseObject = null;
    private final AsyncHttpClient mClient = new AsyncHttpClient();
    public Context mContext;

    public void postSignIn(Context context, final IWebCallback iWebCallback,
                           final String _signInEntity) {
        mContext = context;
        String myUrl = AppConfig.getInstance().getBaseUrlApi() + ApiMethod.POST.signUp;
        Log.d("LOG_AS", "postSignIn: " + myUrl + _signInEntity);
        StringEntity entity = null;
        entity = new StringEntity(_signInEntity, "UTF-8");
        mClient.setMaxRetriesAndTimeout(AppConstt.LIMIT_API_RETRY, AppConstt.LIMIT_TIMOUT_MILLIS);
        Log.d("currentLang", AppConfig.getInstance().loadDefLanguage());

        mClient.post(mContext, myUrl, entity, "application/json", new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String strResponse;



                        try {
                            Gson gson = new Gson();
                            strResponse = new String(responseBody, StandardCharsets.UTF_8);
                            Log.d("LOG_AS", "postSignIn: strResponse" + strResponse);
                            responseObject = gson.fromJson(strResponse, RModel_SignIn.class);

                            switch (statusCode)
                            {

                                case AppConstt.ServerStatus.OK:
                                case AppConstt.ServerStatus.CREATED:
                                    iWebCallback.onWebResult(true, "");
                                    break;

                                default:
                                    AppConfig.getInstance().parsErrorMessage(iWebCallback, responseBody);
                                    break;
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            iWebCallback.onWebException(ex);
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

        public Data getData() {
            return this.data;
        }

        public void setData(Data data) {
            this.data = data;
        }

        public class User {
            private boolean isProfileSet;

            private boolean status;

            private int id;

            private String email;

            private String userType;

            private int updatedTime;

            private String accessToken;

            public boolean getIsProfileSet() {
                return this.isProfileSet;
            }

            public void setIsProfileSet(boolean isProfileSet) {
                this.isProfileSet = isProfileSet;
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

            public String getEmail() {
                return this.email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getUserType() {
                return this.userType;
            }

            public void setUserType(String userType) {
                this.userType = userType;
            }

            public int getUpdatedTime() {
                return this.updatedTime;
            }

            public void setUpdatedTime(int updatedTime) {
                this.updatedTime = updatedTime;
            }

            public String getAccessToken() {
                return this.accessToken;
            }

            public void setAccessToken(String accessToken) {
                this.accessToken = accessToken;
            }
        }

        public class Data {
            private User user;

            public User getUser() {
                return this.user;
            }

            public void setUser(User user) {
                this.user = user;
            }
        }

    }

}
