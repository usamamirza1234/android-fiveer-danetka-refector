package com.armoomragames.denketa.IntroAuxilaries.WebServices;

import android.util.Log;

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.Utils.ApiMethod;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IWebCallback;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class Intro_WebHit_Get_All_Promos {

    public static ResponseModel responseObject = null;
    private final AsyncHttpClient mClient = new AsyncHttpClient();

    public void getPromo(final IWebCallback iWebCallback) {

        String myUrl = AppConfig.getInstance().getBaseUrlApi() + ApiMethod.GET.fetchPromo;

        Log.d("LOG_AS", "getPromo:  " + myUrl + " Header " + AppConfig.getInstance().mUser.getAuthorization());

        mClient.addHeader(ApiMethod.HEADER.Authorization, AppConfig.getInstance().mUser.getAuthorization());
        mClient.setMaxRetriesAndTimeout(AppConstt.LIMIT_API_RETRY, AppConstt.LIMIT_TIMOUT_MILLIS);


        mClient.setMaxRetriesAndTimeout(AppConstt.LIMIT_API_RETRY, AppConstt.LIMIT_TIMOUT_MILLIS);

        mClient.get(myUrl, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                        String strResponse;
                        try {
                            Gson gson = new Gson();
                            strResponse = new String(responseBody, StandardCharsets.UTF_8);

                            Log.d("LOG_AS", "onSuccess: " + strResponse);

                            responseObject = gson.fromJson(strResponse, ResponseModel.class);


                            switch (statusCode) {

                                case AppConstt.ServerStatus.OK:


                                    iWebCallback.onWebResult(true, responseObject.getMessage());
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
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable
                            error) {
                        Log.d("LOG_AS", "onFailure: " + statusCode);
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

    public class ResponseModel {
        public class Data
        {
            private int id;

            private String promoCode;

            private int discount;

            private int redemption;

            private String startDate;

            private String endDate;

            private boolean status;

            private int updatedTime;

            public void setId(int id){
                this.id = id;
            }
            public int getId(){
                return this.id;
            }
            public void setPromoCode(String promoCode){
                this.promoCode = promoCode;
            }
            public String getPromoCode(){
                return this.promoCode;
            }
            public void setDiscount(int discount){
                this.discount = discount;
            }
            public int getDiscount(){
                return this.discount;
            }
            public void setRedemption(int redemption){
                this.redemption = redemption;
            }
            public int getRedemption(){
                return this.redemption;
            }
            public void setStartDate(String startDate){
                this.startDate = startDate;
            }
            public String getStartDate(){
                return this.startDate;
            }
            public void setEndDate(String endDate){
                this.endDate = endDate;
            }
            public String getEndDate(){
                return this.endDate;
            }
            public void setStatus(boolean status){
                this.status = status;
            }
            public boolean getStatus(){
                return this.status;
            }
            public void setUpdatedTime(int updatedTime){
                this.updatedTime = updatedTime;
            }
            public int getUpdatedTime(){
                return this.updatedTime;
            }
        }


            private int code;

            private String status;

            private String message;

            private List<Data> data;

            public void setCode(int code){
                this.code = code;
            }
            public int getCode(){
                return this.code;
            }
            public void setStatus(String status){
                this.status = status;
            }
            public String getStatus(){
                return this.status;
            }
            public void setMessage(String message){
                this.message = message;
            }
            public String getMessage(){
                return this.message;
            }
            public void setData(List<Data> data){
                this.data = data;
            }
            public List<Data> getData(){
                return this.data;
            }


    }

}
