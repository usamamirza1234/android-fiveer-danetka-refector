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
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class Intro_WebHit_Get_All_Results {

    public static ResponseModel responseObject = null;
    private final AsyncHttpClient mClient = new AsyncHttpClient();

    public void getResults(final IWebCallback iWebCallback, final int _ID) {

        String myUrl= AppConfig.getInstance().getBaseUrlApi() +  ApiMethod.GET.fetchResults +_ID;

        Log.d("LOG_AS", "getAllResults:  " + myUrl +" Header "+AppConfig.getInstance().mUser.getAuthorization());

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

                            Log.d("LOG_AS", "onSuccess: "+strResponse);

                            responseObject = gson.fromJson(strResponse, ResponseModel.class);


                            switch (statusCode){

                                case AppConstt.ServerStatus.OK:


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
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable
                            error) {
                        Log.d("LOG_AS", "onFailure: "+statusCode);
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

            private int danetkaId;

            private int masterId;

            private String investigatorName;

            private int riglettosUsed;

            private String date;

            private String time;

            private boolean status;

            private int updatedTime;

            public void setId(int id){
                this.id = id;
            }
            public int getId(){
                return this.id;
            }
            public void setDanetkaId(int danetkaId){
                this.danetkaId = danetkaId;
            }
            public int getDanetkaId(){
                return this.danetkaId;
            }
            public void setMasterId(int masterId){
                this.masterId = masterId;
            }
            public int getMasterId(){
                return this.masterId;
            }
            public void setInvestigatorName(String investigatorName){
                this.investigatorName = investigatorName;
            }
            public String getInvestigatorName(){
                return this.investigatorName;
            }
            public void setRiglettosUsed(int riglettosUsed){
                this.riglettosUsed = riglettosUsed;
            }
            public int getRiglettosUsed(){
                return this.riglettosUsed;
            }
            public void setDate(String date){
                this.date = date;
            }
            public String getDate(){
                return this.date;
            }
            public void setTime(String time){
                this.time = time;
            }
            public String getTime(){
                return this.time;
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

        private List<Intro_WebHit_Get_Results.ResponseModel.Data> data;

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
        public void setData(List<Intro_WebHit_Get_Results.ResponseModel.Data> data){
            this.data = data;
        }
        public List<Intro_WebHit_Get_Results.ResponseModel.Data> getData(){
            return this.data;
        }
    }


}
