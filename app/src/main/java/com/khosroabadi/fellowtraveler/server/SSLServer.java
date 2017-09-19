package com.khosroabadi.fellowtraveler.server;

import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.khosroabadi.fellowtraveler.R;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

/**
 * Created by khosroabadi on 9/17/2017.
 */

public class SSLServer {

    private SSLServerSocketFactory serverSocketFactory;
    private SSLServerSocket serverSocket;
    private boolean isRunning = true;
    private AppCompatActivity activity;
    private final String deviceName;
    final int socketServerPORT ;
    private final GoogleMap mMap;

    public SSLServer(AppCompatActivity activity , String deviceName , GoogleMap googleMap){

        this.activity=activity;
        this.deviceName = deviceName;
        this.mMap = googleMap;
        this.socketServerPORT = Integer.parseInt(activity.getString(R.string.PORT));

        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            KeyStore keyStore = KeyStore.getInstance("PKCS12");



        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
    }
}
