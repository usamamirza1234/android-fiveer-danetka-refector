package com.armoomragames.denketa.IntroAuxilaries.WebServices;


import android.content.Context;
import android.util.Log;

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.Utils.ApiMethod;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IWebCallback;
import com.armoomragames.denketa.Utils.RModel_onFailureError;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.nio.charset.StandardCharsets;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;


public class Intro_WebHit_Post_AddUserProfile {

    public static ResponseModel responseObject = null;
    public static RModel_onFailureError rModel_onFailureError = null;
    private final AsyncHttpClient mClient = new AsyncHttpClient();
    public Context mContext;

    public void postAddProfile(Context context, final IWebCallback iWebCallback,
                               final String _signInEntity) {
        mContext = context;
        String myUrl = AppConfig.getInstance().getBaseUrlApi() + ApiMethod.POST.addProfile;
        Log.d("LOG_AS", "postAddUserProfile: " + myUrl + _signInEntity);
        StringEntity entity = null;
        entity = new StringEntity(_signInEntity, "UTF-8");
        mClient.setMaxRetriesAndTimeout(AppConstt.LIMIT_API_RETRY, AppConstt.LIMIT_TIMOUT_MILLIS);
        Log.d("currentLang", AppConfig.getInstance().loadDefLanguage());
        mClient.addHeader(ApiMethod.HEADER.Authorization, AppConfig.getInstance().mUser.getAuthorization());
        mClient.post(mContext, myUrl, entity, "application/json", new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String strResponse;
                        try {
                            Gson gson = new Gson();
                            strResponse = new String(responseBody, StandardCharsets.UTF_8);
                            Log.d("LOG_AS", "postAddUserProfile: strResponse " + strResponse);
                            responseObject = gson.fromJson(strResponse, ResponseModel.class);

                            switch (statusCode) {

                                case AppConstt.ServerStatus.OK:
                                case AppConstt.ServerStatus.CREATED:
                                    iWebCallback.onWebResult(true, "");
                                    break;

                                default:
                                    AppConfig.getInstance().parsErrorMessage(iWebCallback, responseBody);
                                    break;
                            }

                        } catch (Exception ex) {

                            String strResponse1;
                            try {
                                Gson gson = new Gson();
                                strResponse1 = new String(responseBody, StandardCharsets.UTF_8);
                                Log.d("LOG_AS", "postAddUserProfile: strResponseException " + strResponse1);
                                rModel_onFailureError = gson.fromJson(strResponse1, RModel_onFailureError.class);
                                if (rModel_onFailureError.getCode() == 422) {


                                    iWebCallback.onWebResult(false, "Enter Valid Date of birth");
                                } else {
                                    iWebCallback.onWebException(ex);

                                }
                            } catch (Exception e) {

                            }


//                            iWebCallback.onWebResult(false, "Enter Valid Date of birth");
                            Log.d("LOG_AS", "postAddUserProfile: Exception " + statusCode);
                            ex.printStackTrace();

//                            AppConfig.getInstance().parsErrorMessage(iWebCallback, responseBody);
                        }
                    }


                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Log.d("LOG_AS", "postAddUserProfile: onFailure " + statusCode);
                        switch (statusCode) {


                            case AppConstt.ServerStatus.NETWORK_ERROR:
                                iWebCallback.onWebResult(false, AppConfig.getInstance().getNetworkErrorMessage());
                                break;

                            case AppConstt.ServerStatus.FORBIDDEN:
                            case AppConstt.ServerStatus.UNAUTHORIZED:
                            default:
                                String strResponse;
//                                try {
//                                    Gson gson = new Gson();
//                                    strResponse = new String(responseBody, StandardCharsets.UTF_8);
//                                    Log.d("LOG_AS", "postAddUserProfile: strResponse " + strResponse);
//                                    rModel_onFailureError = gson.fromJson(strResponse, RModel_onFailureError.class);
//                                    CustomToast.showToastMessage(mContext.getApplicationContext(),""+rModel_onFailureError.getMessage(), Toast.LENGTH_LONG);
//
//                                }catch (Exception e)
//                                {
//                                }

                                AppConfig.getInstance().parsErrorMessage(iWebCallback, responseBody);
                                break;
                        }
                    }
                }

        );
    }


    public class ResponseModel {
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

        public class Data {
            private int id;

            private String name;

            private String userName;

            private String dateOfBirth;

            private String gender;

            private String nationality;

            private String language;

            private String email;

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

}
