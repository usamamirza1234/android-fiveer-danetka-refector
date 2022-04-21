package com.armoomragames.denketa.IntroAuxilaries.WebServices;

import android.util.Log;

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.Utils.ApiMethod;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.DModel_PaginationInfo;
import com.armoomragames.denketa.Utils.IWebCallback;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.nio.charset.StandardCharsets;

import cz.msebera.android.httpclient.Header;

public class Intro_WebHit_Get_GameCredits {
    public static ResponseModel responseObject = null;
    public static DModel_PaginationInfo mPaginationInfo = new DModel_PaginationInfo();
    private final AsyncHttpClient mClient = new AsyncHttpClient();

    public void getGameCredits(final IWebCallback iWebCallback) {
        String myUrl = "";
        myUrl = AppConfig.getInstance().getBaseUrlApi() + ApiMethod.GET.fetchGameCredits;

        Log.d("LOG_AS", "getGameCredits:  " + myUrl);

        mClient.addHeader(ApiMethod.HEADER.Authorization, AppConfig.getInstance().mUser.Authorization);

        mClient.setMaxRetriesAndTimeout(AppConstt.LIMIT_API_RETRY, AppConstt.LIMIT_TIMOUT_MILLIS);
        mClient.get(myUrl, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String strResponse;
                        try {
                            Gson gson = new Gson();
                            strResponse = new String(responseBody, StandardCharsets.UTF_8);
                            Log.d("LOG_AS", "getGameCredits:strResponse  " + strResponse);
                            responseObject = gson.fromJson(strResponse, ResponseModel.class);

                            switch (statusCode) {

                                case AppConstt.ServerStatus.OK:
                                    iWebCallback.onWebResult(true, "");
                                    break;

                                default:
                                    iWebCallback.onWebResult(false, responseObject.getStatus());
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


                        switch (statusCode) {
                            case AppConstt.ServerStatus.NETWORK_ERROR:
                                iWebCallback.onWebResult(false, AppConfig.getInstance().getNetworkErrorMessage());
                                break;

                            case AppConstt.ServerStatus.CONFLICT:
                                AppConfig.getInstance().navtoLogin();
                                break;

                            case AppConstt.ServerStatus.DATABASE_NOT_FOUND:
                            default:
                                iWebCallback.onWebResult(false, AppConfig.getInstance().getNetworkErrorMessage());
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

        public class UserCredits {
            private int id;

            private int userId;

            private int credits;

            private int danetkasPurchased;

            private int danetkasPlayed;

            private boolean status;

            private int updatedTime;

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

            public int getCredits() {
                return this.credits;
            }

            public void setCredits(int credits) {
                this.credits = credits;
            }

            public int getDanetkasPurchased() {
                return this.danetkasPurchased;
            }

            public void setDanetkasPurchased(int danetkasPurchased) {
                this.danetkasPurchased = danetkasPurchased;
            }

            public int getDanetkasPlayed() {
                return this.danetkasPlayed;
            }

            public void setDanetkasPlayed(int danetkasPlayed) {
                this.danetkasPlayed = danetkasPlayed;
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

        public class Data {
            private UserCredits userCredits;

            private int toatalDanetkas;

            public UserCredits getUserCredits() {
                return this.userCredits;
            }

            public void setUserCredits(UserCredits userCredits) {
                this.userCredits = userCredits;
            }

            public int getToatalDanetkas() {
                return this.toatalDanetkas;
            }

            public void setToatalDanetkas(int toatalDanetkas) {
                this.toatalDanetkas = toatalDanetkas;
            }
        }


    }

}