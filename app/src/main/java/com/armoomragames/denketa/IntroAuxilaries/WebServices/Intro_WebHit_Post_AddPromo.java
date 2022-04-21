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


public class Intro_WebHit_Post_AddPromo {


    public static RModel_SignIn responseObject = null;
    public static RModel_ERROR responseObjectError = null;
    private final AsyncHttpClient mClient = new AsyncHttpClient();
    public Context mContext;

    public void postPromo(Context context, final IWebCallback iWebCallback,
                          final String _signInEntity) {
        mContext = context;
        String myUrl = AppConfig.getInstance().getBaseUrlApi() + ApiMethod.POST.add_promo;
        Log.d("LOG_AS", "postPromo: " + myUrl + _signInEntity);
        StringEntity entity = null;
        entity = new StringEntity(_signInEntity, "UTF-8");

        mClient.setMaxRetriesAndTimeout(AppConstt.LIMIT_API_RETRY, AppConstt.LIMIT_TIMOUT_MILLIS);
        mClient.addHeader(ApiMethod.HEADER.Authorization, AppConfig.getInstance().mUser.getAuthorization());

        mClient.post(mContext, myUrl, entity, "application/json", new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String strResponse;


                        try {
                            Gson gson = new Gson();
                            strResponse = new String(responseBody, StandardCharsets.UTF_8);
                            Log.d("LOG_AS", "postPromo: strResponse" + strResponse);
                            responseObject = gson.fromJson(strResponse, RModel_SignIn.class);

                            switch (statusCode) {

                                case AppConstt.ServerStatus.OK:
                                case AppConstt.ServerStatus.CREATED:
                                    iWebCallback.onWebResult(true, responseObject.getMessage());
                                    break;

                                default:
                                    AppConfig.getInstance().parsErrorMessage(iWebCallback, responseBody);
                                    break;
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
//                            Gson gson = new Gson();
//                            strResponse = new String(responseBody, StandardCharsets.UTF_8);
//                            Log.d("LOG_AS", "postPromo: strResponse" + strResponse);
//                            responseObjectError = gson.fromJson(strResponse, RModel_ERROR.class);
                            iWebCallback.onWebResult(false, "Exception");
//                            iWebCallback.onWebException(ex);
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
            private int id;

            private String name;

            private String userName;

            private String dateOfBirth;

            private String gender;

            private String nationality;

            private String language;

            private String email;

            private String otpCode;

            private String accessToken;

            private String userType;

            private boolean isProfileSet;

            private boolean status;

            private int updatedTime;

            public int getId() {
                return this.id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return this.name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUserName() {
                return this.userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getDateOfBirth() {
                return this.dateOfBirth;
            }

            public void setDateOfBirth(String dateOfBirth) {
                this.dateOfBirth = dateOfBirth;
            }

            public String getGender() {
                return this.gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getNationality() {
                return this.nationality;
            }

            public void setNationality(String nationality) {
                this.nationality = nationality;
            }

            public String getLanguage() {
                return this.language;
            }

            public void setLanguage(String language) {
                this.language = language;
            }

            public String getEmail() {
                return this.email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getOtpCode() {
                return this.otpCode;
            }

            public void setOtpCode(String otpCode) {
                this.otpCode = otpCode;
            }

            public String getAccessToken() {
                return this.accessToken;
            }

            public void setAccessToken(String accessToken) {
                this.accessToken = accessToken;
            }

            public String getUserType() {
                return this.userType;
            }

            public void setUserType(String userType) {
                this.userType = userType;
            }

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
