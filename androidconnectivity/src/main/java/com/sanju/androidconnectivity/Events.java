package com.sanju.androidconnectivity;

public class Events {

    /**
     *************** if no internet connection available **************************
     */
    public static class NoInternetConnection {
        private String message;
        public NoInternetConnection(String message) {
            this.message = message;
        }
        public String getMessage() {
            return message;
        }
    }

    /**
     *********************** internet connection available *************************
     */
    public static class InternetConnectionAvailable {
        private String message;
        public InternetConnectionAvailable(String message) {
            this.message = message;
        }
        public String getMessage() {
            return message;
        }
    }

}