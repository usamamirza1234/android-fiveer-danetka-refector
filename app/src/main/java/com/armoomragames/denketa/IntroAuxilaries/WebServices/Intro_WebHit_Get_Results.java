package com.armoomragames.denketa.IntroAuxilaries.WebServices;

import android.util.Log;

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.Utils.ApiMethod;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.DModel_PaginationInfo;
import com.armoomragames.denketa.Utils.IWebPaginationCallback;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class Intro_WebHit_Get_Results {
    private AsyncHttpClient mClient = new AsyncHttpClient();
    public static ResponseModel responseObject = null;
    public static DModel_PaginationInfo mPaginationInfo = new DModel_PaginationInfo();

    public void getCategory(final IWebPaginationCallback iWebPaginationCallback, final int _index, final int _ID)  {
        String myUrl="";
             myUrl = AppConfig.getInstance().getBaseUrlApi() +  ApiMethod.GET.fetchResults +_ID;



        RequestParams params = new RequestParams();

        params.put("page", _index);
        params.put("per_page", "10");
        params.put("sortBy", "id");
        params.put("sortOrder", "DESC");


        Log.d("LOG_AS", "getAllResults:  " + myUrl +" "+params +" Header "+AppConfig.getInstance().mUser.getAuthorization());

        mClient.addHeader(ApiMethod.HEADER.Authorization, AppConfig.getInstance().mUser.getAuthorization());
        mClient.setMaxRetriesAndTimeout(AppConstt.LIMIT_API_RETRY, AppConstt.LIMIT_TIMOUT_MILLIS);
        mClient.get(myUrl, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String strResponse;
                        try {
                            Gson gson = new Gson();
                            strResponse = new String(responseBody, "UTF-8");
                            Log.d("LOG_AS", "getAllResults: onSuccess: " + strResponse);
                            ResponseModel responseObjectLocal = null;

                            responseObjectLocal = gson.fromJson(strResponse, ResponseModel.class);

                            switch (statusCode) {

                                case AppConstt.ServerStatus.CREATED:
                                case AppConstt.ServerStatus.OK:
                                    if (_index == mPaginationInfo.currIndex) {
                                        //First page
                                        responseObject = responseObjectLocal;

                                        mPaginationInfo.isCompleted = false;

                                        iWebPaginationCallback.onWebInitialResult(true, responseObject.getMessage());
                                    } else {
//                                    //Subsequent pages
                                        boolean tmpIsDataFetched = (statusCode == AppConstt.ServerStatus.OK);
                                        if (tmpIsDataFetched) {
//                                            for (int i = 0; i < responseObjectLocal.getData().size(); i++)
//                                                responseObject.getData().add(responseObjectLocal.getData().get(i));
                                            responseObject = responseObjectLocal;
                                            mPaginationInfo.currIndex = _index;
                                        }
                                        Log.d("LOG_AS", "getAllResults: onSuccess: tmpIsDataFetched " + tmpIsDataFetched);
                                        //No need to save

                                        if (mPaginationInfo != null) {
                                            iWebPaginationCallback.onWebSuccessiveResult(true, !tmpIsDataFetched, responseObjectLocal.getMessage());
                                        }


                                    }
                                    break;

                                default:
                                    //Server error
                                    if (_index == mPaginationInfo.currIndex)
                                        iWebPaginationCallback.onWebInitialResult(false, responseObjectLocal.getMessage());
                                    else
                                        iWebPaginationCallback.onWebSuccessiveResult(false, false, responseObjectLocal.getMessage());
                                    break;
                            }
                        } catch (Exception ex) {
                            if (_index == mPaginationInfo.currIndex)
                                iWebPaginationCallback.onWebInitialException(ex);
                            else
                                iWebPaginationCallback.onWebSuccessiveException(ex);
                            Log.d("LOG_AS", "getAllResults: exception: " + ex.toString());
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable
                            error) {

                        Log.d("LOG_AS", "getAllResults: onFailure called: " + error.toString() + "   " + statusCode + "");


                        switch (statusCode) {
                            case AppConstt.ServerStatus.NETWORK_ERROR:
                                if (_index == mPaginationInfo.currIndex)
                                    iWebPaginationCallback.onWebInitialResult(false, AppConfig.getInstance().getNetworkErrorMessage());
                                else
                                    iWebPaginationCallback.onWebSuccessiveResult(false, false, AppConfig.getInstance().getNetworkErrorMessage());
                                break;

                            case AppConstt.ServerStatus.UNAUTHORIZED:
                                AppConfig.getInstance().navtoLogin();
                                break;

                            case AppConstt.ServerStatus.DATABASE_NOT_FOUND:
                                if (_index == mPaginationInfo.currIndex) {
                                    //Save orders data
//                                    AppConfig.getInstance().saveHomeOrders("");
                                }
                                AppConfig.getInstance().parsErrorMessage(iWebPaginationCallback, responseBody, _index, mPaginationInfo.currIndex);
                                break;

                            default:
                                AppConfig.getInstance().parsErrorMessage(iWebPaginationCallback, responseBody, _index, mPaginationInfo.currIndex);
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

            private String investegorNumber;

            private String masterName;

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

            public String getInvestegorNumber() {
                return investegorNumber;
            }

            public void setInvestegorNumber(String investegorNumber) {
                this.investegorNumber = investegorNumber;
            }

            public String getMasterName() {
                return masterName;
            }

            public void setMasterName(String masterName) {
                this.masterName = masterName;
            }

            public boolean isStatus() {
                return status;
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