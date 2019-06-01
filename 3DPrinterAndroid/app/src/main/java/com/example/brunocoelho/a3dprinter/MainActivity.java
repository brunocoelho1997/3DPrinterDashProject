package com.example.brunocoelho.a3dprinter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView tempTextView;
    private StateThread stateThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tempTextView = (TextView) findViewById(R.id.tv_temp);
        stateThread = new StateThread(tempTextView);



        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            stateThread.start();
        } else {
            tempTextView.setText("No network connection available.");
        }


    }
}


class StateThread extends Thread {
    private TextView tempTextView;
    private DownloadTask downloadTask;

    StateThread(TextView tempTextView) {
        this.tempTextView = tempTextView;

    }

    public void run() {

        try {

            while(true)
            {
                this.downloadTask = new DownloadTask(tempTextView);
                downloadTask.execute("http://192.168.1.69:8080/getPrinterState");
                sleep(Configs.REFRESH_SECONDS);
//                Log.d("StateThread", "Refreshed states.");

            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

