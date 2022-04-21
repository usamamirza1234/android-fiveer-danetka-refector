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


public class Intro_WebHit_Post_Update_Results {
    public static RModel_ERROR responseObjectError = null;
    public static RModel_SignIn responseObject = null;
    private final AsyncHttpClient mClient = new AsyncHttpClient();
    public Context mContext;

    public void postUpdateResults(Context context, final IWebCallback iWebCallback,
                           final String _signInEntity,int _id) {
        mContext = context;
        String myUrl = AppConfig.getInstance().getBaseUrlApi() + ApiMethod.Patch.updateResult+_id;
        Log.d("LOG_AS", "postUpdateResults: " + myUrl + _signInEntity);
        StringEntity entity = null;
        entity = new StringEntity(_signInEntity, "UTF-8");
        mClient.setMaxRetriesAndTimeout(AppConstt.LIMIT_API_RETRY, AppConstt.LIMIT_TIMOUT_MILLIS);
        mClient.addHeader(ApiMethod.HEADER.Authorization, AppConfig.getInstance().mUser.getAuthorization());

        mClient.patch(mContext, myUrl, entity, "application/json", new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String strResponse;



                        try {
                            Gson gson = new Gson();
                            strResponse = new String(responseBody, StandardCharsets.UTF_8);
                            Log.d("LOG_AS", "postUpdateResults: strResponse" + strResponse);
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
                            Gson gson = new Gson();
                            strResponse = new String(responseBody, StandardCharsets.UTF_8);
                            Log.d("LOG_AS", "postSignIn: strResponse" + strResponse);
                            responseObjectError = gson.fromJson(strResponse, RModel_ERROR.class);
                            iWebCallback.onWebResult(false, responseObjectError.getMessage());
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

        public class Data
        {
            private String danetkaId;

            private String masterId;

            private boolean status;

            private int id;

            private String investigatorName;

            private String riglettosUsed;

            private String date;

            private String time;

            private int updatedTime;

            public void setDanetkaId(String danetkaId){
                this.danetkaId = danetkaId;
            }
            public String getDanetkaId(){
                return this.danetkaId;
            }
            public void setMasterId(String masterId){
                this.masterId = masterId;
            }
            public String getMasterId(){
                return this.masterId;
            }
            public void setStatus(boolean status){
                this.status = status;
            }
            public boolean getStatus(){
                return this.status;
            }
            public void setId(int id){
                this.id = id;
            }
            public int getId(){
                return this.id;
            }
            public void setInvestigatorName(String investigatorName){
                this.investigatorName = investigatorName;
            }
            public String getInvestigatorName(){
                return this.investigatorName;
            }
            public void setRiglettosUsed(String riglettosUsed){
                this.riglettosUsed = riglettosUsed;
            }
            public String getRiglettosUsed(){
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

            private Data data;

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
            public void setData(Data data){
                this.data = data;
            }
            public Data getData(){
                return this.data;
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
