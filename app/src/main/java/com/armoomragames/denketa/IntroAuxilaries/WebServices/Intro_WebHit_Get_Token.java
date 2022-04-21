package com.armoomragames.denketa.IntroAuxilaries.WebServices;

import android.util.Log;

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.Utils.ApiMethod;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.DModel_PaginationInfo;
import com.armoomragames.denketa.Utils.IWebCallback;
import com.armoomragames.denketa.Utils.IWebPaginationCallback;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class Intro_WebHit_Get_Token {
    private AsyncHttpClient mClient = new AsyncHttpClient();
    public static ResponseModel responseObject = null;
    public static DModel_PaginationInfo mPaginationInfo = new DModel_PaginationInfo();

    public void getToken(final IWebCallback iWebPaginationCallback) {
        String myUrl = AppConfig.getInstance().getBaseUrlApi() + ApiMethod.POST.card_noice;
        mClient.addHeader(ApiMethod.HEADER.Authorization, AppConfig.getInstance().mUser.getAuthorization());
        mClient.setMaxRetriesAndTimeout(AppConstt.LIMIT_API_RETRY, AppConstt.LIMIT_TIMOUT_MILLIS);
        mClient.get(myUrl, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String strResponse;
                        try {
                            Gson gson = new Gson();
                            strResponse = new String(responseBody, "UTF-8");
                            Log.d("LOG_AS", "getAllResults: onSuccess: " + strResponse);

                            responseObject = gson.fromJson(strResponse, ResponseModel.class);

                            switch (statusCode) {

                                case AppConstt.ServerStatus.CREATED:
                                case AppConstt.ServerStatus.OK:
                                    iWebPaginationCallback.onWebResult(true, responseObject.getMessage());
                                    break;
                                default:
                                    break;
                            }
                        } catch (Exception ex) {
                            iWebPaginationCallback.onWebException(ex);
                            Log.d("LOG_AS", "getAllResults: exception: " + ex.toString());
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable
                            error) {

                        Log.d("LOG_AS", "getAllResults: onFailure called: " + error.toString() + "   " + statusCode + "");


                        switch (statusCode) {
                            case AppConstt.ServerStatus.NETWORK_ERROR:
                                iWebPaginationCallback.onWebResult(false, AppConfig.getInstance().getNetworkErrorMessage());
                                break;

                            case AppConstt.ServerStatus.UNAUTHORIZED:
                                AppConfig.getInstance().navtoLogin();
                                break;


                        }
                    }
                }
        );
    }


    public class ResponseModel {

        public class Data {
            private String clientToken;

            private boolean success;

            public void setClientToken(String clientToken) {
                this.clientToken = clientToken;
            }

            public String getClientToken() {
                return this.clientToken;
            }

            public void setSuccess(boolean success) {
                this.success = success;
            }

            public boolean getSuccess() {
                return this.success;
            }
        }


        private int code;

        private String message;

        private Data data;

        public void setCode(int code) {
            this.code = code;
        }

        public int getCode() {
            return this.code;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return this.message;
        }

        public void setData(Data data) {
            this.data = data;
        }

        public Data getData() {
            return this.data;

        }

    }
}
