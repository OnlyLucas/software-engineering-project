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

/**
 * The RetrofitClient class provides static methods to obtain instances of the Retrofit library
 * for making HTTP requests to the Flat Fusion backend. It includes methods for creating instances
 * with and without authentication, as well as a special instance for login with direct credentials.
 * Additionally, it contains a method for obtaining an OkHttpClient.Builder that trusts all SSL certificates
 * for development and testing purposes.
 *
 * Responsibilities:
 * - Create a Retrofit instance for various use cases.
 * - Configure OkHttpClient for handling HTTP requests.
 * - Allow trusting all SSL certificates for development/testing.
 *
 * Note: The use of trusting all SSL certificates is recommended only for development and testing,
 * and it should be replaced with proper SSL certificates in a production environment.
 */
public class RetrofitClient {
    private static Retrofit retrofit = null;
    private static final String BASE_URL = "https://10.0.2.2:1337/v1/";

    private RetrofitClient() {
    }

    /**
     * This retrofit instance is for the login.
     * A different way of obtaining the credentials is used (direct insertion of username and password).
     *
     * @param username
     * @param password
     * @return
     */
    public static Retrofit getInstanceWithCredentials(String username, String password) {

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

    /**
     * Returns a Retrofit instance configured without authentication.
     *
     * @return Retrofit An instance of Retrofit configured without authentication.
     */
    public static Retrofit getInstanceWithoutAuth(){
        OkHttpClient.Builder httpClient = getAllSslTrustingOkHttpBuilder();

        // Logging
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(httpClient.build())
                .build();

        return retrofit;
    }

    /**
     * Returns a singleton Retrofit instance configured with authentication.
     *
     * @return Retrofit A singleton instance of Retrofit configured with authentication.
     */
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
     * <p>
     * As our backend server runs only locally, we need to use a self-signed certificate for it. That is why we need to allow the use here.
     * <p>
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
