package com.example.software_engineering_project.dataservice;

import com.example.software_engineering_project.interceptor.AuthInterceptor;
import com.example.software_engineering_project.interceptor.LoginInterceptor;

import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitClient{
    private static Retrofit retrofit = null;

    private static final String BASE_URL = "https://10.0.2.2:1337/v1/";

    private RetrofitClient() {}


    /**
     * This retrofit instance is for the login.
     * A different way of obtaining the credentials is used (direct insertion of username and password).
     * @param username
     * @param password
     * @return
     */
    public static Retrofit getInstanceWithCredentials(String username, String password){
        OkHttpClient.Builder httpClient = getAllSslTrustingOkHttpBuilder();

        // Logging
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);

        // Authorization
        Interceptor loginInterceptor = new LoginInterceptor(username, password);
        httpClient.addInterceptor(loginInterceptor);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(httpClient.build())
                .build();

        return retrofit;
    }

    public static synchronized Retrofit getInstance() {
        if (retrofit == null) {

            OkHttpClient.Builder httpClient = getAllSslTrustingOkHttpBuilder();

            // Logging
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);

            // Authorization
            Interceptor authInterceptor = new AuthInterceptor();
            httpClient.addInterceptor(authInterceptor);

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }


    /**
     * !!!USE ONLY FOR DEVELOPMENT/TESTING!!!
     * This method returns a http builder that trusts all SSL certificates, also self-signed ones.
     *
     * As our backend server runs only locally, we need to use a self-signed certificate for it. That is why we need to allow the use here.
     *
     * For production: Just use a proper ssl certificate in the backend and use the default OkHttpBuilder
     *
     * @return All-trusting OkHttpBuilder
     */
    public static OkHttpClient.Builder getAllSslTrustingOkHttpBuilder() {

        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            return builder;
        } catch (Exception e) {
            // TODO handle error properly by making toast and notifying user
            throw new RuntimeException(e);
        }
    }
}
