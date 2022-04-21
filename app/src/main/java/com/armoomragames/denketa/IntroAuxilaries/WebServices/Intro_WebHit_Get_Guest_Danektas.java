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

import java.nio.charset.StandardCharsets;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class Intro_WebHit_Get_Guest_Danektas {
    public static ResponseModel responseObject = null;
    public static DModel_PaginationInfo mPaginationInfo = new DModel_PaginationInfo();
    private final AsyncHttpClient mClient = new AsyncHttpClient();

    public void getGuestDanekta(final IWebPaginationCallback iWebPaginationCallback, final int _index) {
        String myUrl = AppConfig.getInstance().getBaseUrlApi() + ApiMethod.GET.fetchFreeDanetkas;

        RequestParams params = new RequestParams();

        params.put("page", _index);
        params.put("per_page", "10");
        params.put("sortBy", "id");
        params.put("sortOrder", "DESC");


        Log.d("LOG_AS", "getGuestDanekta:  " + myUrl + params);

        mClient.addHeader(ApiMethod.HEADER.Authorization, AppConfig.getInstance().mUser.getAuthorization());
        mClient.setMaxRetriesAndTimeout(AppConstt.LIMIT_API_RETRY, AppConstt.LIMIT_TIMOUT_MILLIS);
        mClient.get(myUrl, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String strResponse;
                        try {
                            Gson gson = new Gson();
                            strResponse = new String(responseBody, StandardCharsets.UTF_8);
                            Log.d("LOG_AS", "getGuestDanekta: onSuccess: " + strResponse);
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
                                        Log.d("LOG_AS", "getGuestDanekta: onSuccess: tmpIsDataFetched " + tmpIsDataFetched);
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
                            Log.d("LOG_AS", "getGuestDanekta: exception: " + ex.toString());
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable
                            error) {

                        Log.d("LOG_AS", "getGuestDanekta: onFailure called: " + error.toString() + "   " + statusCode + "");


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

        public class Listing
        {
            private int id;

            private String masterId;

            private String title;

            private String question;

            private String answer;

            private String hint;

            private String image;

            private String answerImage;

            private String paymentStatus;

            private String learnMore;

            private boolean status;

            private int updatedTime;

            public void setId(int id){
                this.id = id;
            }
            public int getId(){
                return this.id;
            }
            public void setMasterId(String masterId){
                this.masterId = masterId;
            }
            public String getMasterId(){
                return this.masterId;
            }
            public void setTitle(String title){
                this.title = title;
            }
            public String getTitle(){
                return this.title;
            }
            public void setQuestion(String question){
                this.question = question;
            }
            public String getQuestion(){
                return this.question;
            }
            public void setAnswer(String answer){
                this.answer = answer;
            }
            public String getAnswer(){
                return this.answer;
            }
            public void setHint(String hint){
                this.hint = hint;
            }
            public String getHint(){
                return this.hint;
            }
            public void setImage(String image){
                this.image = image;
            }
            public String getImage(){
                return this.image;
            }
            public void setAnswerImage(String answerImage){
                this.answerImage = answerImage;
            }
            public String getAnswerImage(){
                return this.answerImage;
            }
            public void setPaymentStatus(String paymentStatus){
                this.paymentStatus = paymentStatus;
            }
            public String getPaymentStatus(){
                return this.paymentStatus;
            }
            public void setLearnMore(String learnMore){
                this.learnMore = learnMore;
            }
            public String getLearnMore(){
                return this.learnMore;
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

        public class Pagination
        {
            private String page;

            private int count;

            private int pages;

            private String sortBy;

            private String sortOrder;

            public void setPage(String page){
                this.page = page;
            }
            public String getPage(){
                return this.page;
            }
            public void setCount(int count){
                this.count = count;
            }
            public int getCount(){
                return this.count;
            }
            public void setPages(int pages){
                this.pages = pages;
            }
            public int getPages(){
                return this.pages;
            }
            public void setSortBy(String sortBy){
                this.sortBy = sortBy;
            }
            public String getSortBy(){
                return this.sortBy;
            }
            public void setSortOrder(String sortOrder){
                this.sortOrder = sortOrder;
            }
            public String getSortOrder(){
                return this.sortOrder;
            }
        }

        public class Data
        {
            private List<Listing> listing;

            private Pagination pagination;

            public void setListing(List<Listing> listing){
                this.listing = listing;
            }
            public List<Listing> getListing(){
                return this.listing;
            }
            public void setPagination(Pagination pagination){
                this.pagination = pagination;
            }
            public Pagination getPagination(){
                return this.pagination;
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



    }
