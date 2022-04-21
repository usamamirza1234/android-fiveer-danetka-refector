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

public class Intro_WebHit_Get_User_Danektas {
    public static ResponseModel responseObject = null;
    public static ResponseModelGuest responseObjectGuest = null;
    public static DModel_PaginationInfo mPaginationInfo = new DModel_PaginationInfo();
    private final AsyncHttpClient mClient = new AsyncHttpClient();

    public void getMyDanekta(final IWebPaginationCallback iWebPaginationCallback, final int _index) {
        String myUrl = "";
        if (AppConfig.getInstance().mUser.isLoggedIn())
            myUrl = AppConfig.getInstance().getBaseUrlApi() + ApiMethod.GET.fetchUserDanetkas;
        else
            myUrl = AppConfig.getInstance().getBaseUrlApi() + ApiMethod.GET.fetchFreeDanetkas;

        RequestParams params = new RequestParams();

        params.put("page", _index);
        params.put("per_page", "10");
        params.put("sortBy", "title");
        params.put("sortOrder", "DESC");


        Log.d("LOG_AS", "getAllUserDanetkas:  " + myUrl + params + AppConfig.getInstance().mUser.getAuthorization());

        mClient.addHeader(ApiMethod.HEADER.Authorization, AppConfig.getInstance().mUser.getAuthorization());
        mClient.setMaxRetriesAndTimeout(AppConstt.LIMIT_API_RETRY, AppConstt.LIMIT_TIMOUT_MILLIS);
        mClient.get(myUrl, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String strResponse;
                        try {
                            Gson gson = new Gson();
                            strResponse = new String(responseBody, StandardCharsets.UTF_8);
                            Log.d("LOG_AS", "getAllUserDanetkas: onSuccess: " + strResponse);
                            ResponseModel responseObjectLocal = null;
                            ResponseModelGuest responseObjectLocalGuest = null;

                            if (AppConfig.getInstance().mUser.isLoggedIn())
                                responseObjectLocal = gson.fromJson(strResponse, ResponseModel.class);
                            else
                                responseObjectLocalGuest = gson.fromJson(strResponse, ResponseModelGuest.class);

                            switch (statusCode) {
                                case AppConstt.ServerStatus.CREATED:
                                case AppConstt.ServerStatus.OK:
                                    if (_index == mPaginationInfo.currIndex) {
                                        //First page
                                        if (AppConfig.getInstance().mUser.isLoggedIn()) {
                                            responseObject = responseObjectLocal;
                                            mPaginationInfo.isCompleted = false;
                                            iWebPaginationCallback.onWebInitialResult(true, responseObject.getMessage());
                                        } else {
                                            responseObjectGuest = responseObjectLocalGuest;
                                            mPaginationInfo.isCompleted = false;
                                            iWebPaginationCallback.onWebInitialResult(true, responseObjectGuest.getMessage());
                                        }
                                    } else {
//                                    //Subsequent pages
                                        boolean tmpIsDataFetched = (statusCode == AppConstt.ServerStatus.OK);
                                        if (tmpIsDataFetched) {
//                                            for (int i = 0; i < responseObjectLocal.getData().size(); i++)
//                                                responseObject.getData().add(responseObjectLocal.getData().get(i));


                                            if (AppConfig.getInstance().mUser.isLoggedIn())
                                                responseObject = responseObjectLocal;
                                            else
                                                responseObjectGuest = responseObjectLocalGuest;


                                            mPaginationInfo.currIndex = _index;
                                        }
                                        Log.d("LOG_AS", "getAllUserDanetkas: onSuccess: tmpIsDataFetched " + tmpIsDataFetched);
                                        //No need to save

                                        if (mPaginationInfo != null) {
                                            if (AppConfig.getInstance().mUser.isLoggedIn())
                                                iWebPaginationCallback.onWebSuccessiveResult(true, !tmpIsDataFetched, responseObjectLocal.getMessage());
                                            else
                                                iWebPaginationCallback.onWebSuccessiveResult(true, !tmpIsDataFetched, responseObjectLocalGuest.getMessage());
                                        }


                                    }
                                    break;

                                default:
                                    //Server error
                                    if (AppConfig.getInstance().mUser.isLoggedIn()){
                                        if (_index == mPaginationInfo.currIndex)
                                            iWebPaginationCallback.onWebInitialResult(false, responseObjectLocal.getMessage());
                                        else
                                            iWebPaginationCallback.onWebSuccessiveResult(false, false, responseObjectLocal.getMessage());
                                    }
                                       else{
                                        if (_index == mPaginationInfo.currIndex)
                                            iWebPaginationCallback.onWebInitialResult(false, responseObjectLocalGuest.getMessage());
                                        else
                                            iWebPaginationCallback.onWebSuccessiveResult(false, false, responseObjectLocalGuest.getMessage());
                                    }


                                    break;
                            }
                        } catch (Exception ex) {
                            if (_index == mPaginationInfo.currIndex)
                                iWebPaginationCallback.onWebInitialException(ex);
                            else
                                iWebPaginationCallback.onWebSuccessiveException(ex);
                            Log.d("LOG_AS", "getAllUserDanetkas: exception: " + ex.toString());
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable
                            error) {

                        Log.d("LOG_AS", "getAllUserDanetkas: onFailure called: " + error.toString() + "   " + statusCode + "");


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

        public class Danetkas {
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

            private boolean isPopular;

            private boolean isPlayed;

            private String danetkaType;

            private int resultCount;

            private boolean status;

            private int updatedTime;

            public int getId() {
                return this.id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getMasterId() {
                return this.masterId;
            }

            public void setMasterId(String masterId) {
                this.masterId = masterId;
            }

            public String getTitle() {
                return this.title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getQuestion() {
                return this.question;
            }

            public void setQuestion(String question) {
                this.question = question;
            }

            public String getAnswer() {
                return this.answer;
            }

            public void setAnswer(String answer) {
                this.answer = answer;
            }

            public String getHint() {
                return this.hint;
            }

            public void setHint(String hint) {
                this.hint = hint;
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

            public String getPaymentStatus() {
                return this.paymentStatus;
            }

            public void setPaymentStatus(String paymentStatus) {
                this.paymentStatus = paymentStatus;
            }

            public String getLearnMore() {
                return this.learnMore;
            }

            public void setLearnMore(String learnMore) {
                this.learnMore = learnMore;
            }

            public boolean getIsPopular() {
                return this.isPopular;
            }

            public void setIsPopular(boolean isPopular) {
                this.isPopular = isPopular;
            }

            public boolean getIsPlayed() {
                return this.isPlayed;
            }

            public void setIsPlayed(boolean isPlayed) {
                this.isPlayed = isPlayed;
            }

            public String getDanetkaType() {
                return this.danetkaType;
            }

            public void setDanetkaType(String danetkaType) {
                this.danetkaType = danetkaType;
            }

            public int getResultCount() {
                return this.resultCount;
            }

            public void setResultCount(int resultCount) {
                this.resultCount = resultCount;
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

        public class User {
            private int id;

            private String name;

            private String userName;

            private String dateOfBirth;

            private String gender;

            private String nationality;

            private String language;

            private String email;

            private boolean isPlayed;

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

            public boolean getIsPlayed() {
                return this.isPlayed;
            }

            public void setIsPlayed(boolean isPlayed) {
                this.isPlayed = isPlayed;
            }
        }

        public class Listing {
            private boolean isPlayed;
            private int id;

            private int userId;

            private int danetkasId;

            private boolean status;

            private String updatedTime;

            private Danetkas danetkas;

            private User user;

            public int getId() {
                return this.id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUserId() {
                return this.userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public int getDanetkasId() {
                return this.danetkasId;
            }

            public void setDanetkasId(int danetkasId) {
                this.danetkasId = danetkasId;
            }

            public boolean getStatus() {
                return this.status;
            }

            public void setStatus(boolean status) {
                this.status = status;
            }

            public String getUpdatedTime() {
                return this.updatedTime;
            }

            public void setUpdatedTime(String updatedTime) {
                this.updatedTime = updatedTime;
            }

            public Danetkas getDanetkas() {
                return this.danetkas;
            }

            public void setDanetkas(Danetkas danetkas) {
                this.danetkas = danetkas;
            }

            public User getUser() {
                return this.user;
            }

            public void setUser(User user) {
                this.user = user;
            }

            public boolean getIsPlayed() {
                return this.isPlayed;
            }

            public void setIsPlayed(boolean isPlayed) {
                this.isPlayed = isPlayed;
            }
        }

        public class Pagination {
            private int page;

            private int count;

            private int pages;

            private String sortBy;

            private String sortOrder;

            public int getPage() {
                return this.page;
            }

            public void setPage(int page) {
                this.page = page;
            }

            public int getCount() {
                return this.count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public int getPages() {
                return this.pages;
            }

            public void setPages(int pages) {
                this.pages = pages;
            }

            public String getSortBy() {
                return this.sortBy;
            }

            public void setSortBy(String sortBy) {
                this.sortBy = sortBy;
            }

            public String getSortOrder() {
                return this.sortOrder;
            }

            public void setSortOrder(String sortOrder) {
                this.sortOrder = sortOrder;
            }
        }

        public class Data {
            private List<Listing> listing;

            private Pagination pagination;

            public List<Listing> getListing() {
                return this.listing;
            }

            public void setListing(List<Listing> listing) {
                this.listing = listing;
            }

            public Pagination getPagination() {
                return this.pagination;
            }

            public void setPagination(Pagination pagination) {
                this.pagination = pagination;
            }
        }
    }

    public class ResponseModelGuest {

        private int code;
        private String status;
        private String message;
        private Intro_WebHit_Get_Guest_Danektas.ResponseModel.Data data;

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

        public Intro_WebHit_Get_Guest_Danektas.ResponseModel.Data getData() {
            return this.data;
        }

        public void setData(Intro_WebHit_Get_Guest_Danektas.ResponseModel.Data data) {
            this.data = data;
        }

        public class Listing {
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

            public int getId() {
                return this.id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getMasterId() {
                return this.masterId;
            }

            public void setMasterId(String masterId) {
                this.masterId = masterId;
            }

            public String getTitle() {
                return this.title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getQuestion() {
                return this.question;
            }

            public void setQuestion(String question) {
                this.question = question;
            }

            public String getAnswer() {
                return this.answer;
            }

            public void setAnswer(String answer) {
                this.answer = answer;
            }

            public String getHint() {
                return this.hint;
            }

            public void setHint(String hint) {
                this.hint = hint;
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

            public String getPaymentStatus() {
                return this.paymentStatus;
            }

            public void setPaymentStatus(String paymentStatus) {
                this.paymentStatus = paymentStatus;
            }

            public String getLearnMore() {
                return this.learnMore;
            }

            public void setLearnMore(String learnMore) {
                this.learnMore = learnMore;
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

        public class Pagination {
            private String page;

            private int count;

            private int pages;

            private String sortBy;

            private String sortOrder;

            public String getPage() {
                return this.page;
            }

            public void setPage(String page) {
                this.page = page;
            }

            public int getCount() {
                return this.count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public int getPages() {
                return this.pages;
            }

            public void setPages(int pages) {
                this.pages = pages;
            }

            public String getSortBy() {
                return this.sortBy;
            }

            public void setSortBy(String sortBy) {
                this.sortBy = sortBy;
            }

            public String getSortOrder() {
                return this.sortOrder;
            }

            public void setSortOrder(String sortOrder) {
                this.sortOrder = sortOrder;
            }
        }

        public class Data {
            private List<Intro_WebHit_Get_Guest_Danektas.ResponseModel.Listing> listing;

            private Intro_WebHit_Get_Guest_Danektas.ResponseModel.Pagination pagination;

            public List<Intro_WebHit_Get_Guest_Danektas.ResponseModel.Listing> getListing() {
                return this.listing;
            }

            public void setListing(List<Intro_WebHit_Get_Guest_Danektas.ResponseModel.Listing> listing) {
                this.listing = listing;
            }

            public Intro_WebHit_Get_Guest_Danektas.ResponseModel.Pagination getPagination() {
                return this.pagination;
            }

            public void setPagination(Intro_WebHit_Get_Guest_Danektas.ResponseModel.Pagination pagination) {
                this.pagination = pagination;
            }
        }
    }

}
