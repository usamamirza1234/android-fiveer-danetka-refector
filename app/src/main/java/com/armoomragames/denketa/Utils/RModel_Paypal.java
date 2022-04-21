package com.armoomragames.denketa.Utils;

public class RModel_Paypal {
    public class Client {
        private String environment;

        private String paypal_sdk_version;

        private String platform;

        private String product_name;

        public void setEnvironment(String environment) {
            this.environment = environment;
        }

        public String getEnvironment() {
            return this.environment;
        }

        public void setPaypal_sdk_version(String paypal_sdk_version) {
            this.paypal_sdk_version = paypal_sdk_version;
        }

        public String getPaypal_sdk_version() {
            return this.paypal_sdk_version;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public String getPlatform() {
            return this.platform;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public String getProduct_name() {
            return this.product_name;
        }
    }


    public class Response {
        private String create_time;

        private String id;

        private String intent;

        private String state;

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getCreate_time() {
            return this.create_time;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }

        public void setIntent(String intent) {
            this.intent = intent;
        }

        public String getIntent() {
            return this.intent;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getState() {
            return this.state;
        }
    }


    private Client client;

    private Response response;

    private String response_type;

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return this.client;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return this.response;
    }

    public void setResponse_type(String response_type) {
        this.response_type = response_type;
    }

    public String getResponse_type() {
        return this.response_type;
    }
}
