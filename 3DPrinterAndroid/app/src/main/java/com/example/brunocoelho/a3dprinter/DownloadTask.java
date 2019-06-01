package com.example.brunocoelho.a3dprinter;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class DownloadTask extends AsyncTask<String, Void, String> {
    TextView texto;
    public DownloadTask(TextView texto) {
        this.texto = texto;
    }
    @Override
    protected String doInBackground(String... params) {
        String resultado = "Error";
        try {
            resultado = getData(params[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }
    @Override
    protected void onPostExecute(String resultado) {


        texto.setText(resultado);



    }

    String getData(String endereco)
    {
        StringBuilder resp=new StringBuilder();
        try {
            URL url = new URL(endereco);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milisegundos */);
            conn.setConnectTimeout(15000 /* milisegundos */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            int codigo = conn.getResponseCode();
            if (codigo == 200) {
                InputStream is=conn.getInputStream();
                BufferedReader br=new BufferedReader(new InputStreamReader(is));
                String line;
                while ( (line = br.readLine()) != null )
                    resp.append(line + "\n");
            } else {
                resp.append("Error accessing to the page: "+codigo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp.toString();
    }


};

