package com.example.projetc2application.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.example.projetc2application.BuildConfig;
import com.example.projetc2application.beans.HttpResponseBean;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class GlobalFunctions {

    private static final int MAX_LOG_SIZE = 65516;
    private static final int TIME_OUT = 45000;
    private static String HASH = "TBxk8zxu3uCfSKcg8c7K2MxSfsXdeDdc";


    public static boolean CheckNetwork(Context context) {
        try {
            ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Network[] networks = connec.getAllNetworks();
                boolean isConnected = false;
                for (Network network : networks) {
                    NetworkInfo netInfo = connec.getNetworkInfo(network);
                    if ((netInfo.getType() == ConnectivityManager.TYPE_WIFI || netInfo.getType() == ConnectivityManager.TYPE_MOBILE) && netInfo.isConnected()) {
                        return true;
                    }
                }
            } else {
                NetworkInfo wifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                NetworkInfo mobile = connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                if ((wifi != null && wifi.isConnected()) || (mobile != null && mobile.isConnected()))
                    return true;
            }
            return false;
        } catch (Exception e) {
            GlobalFunctions.printException(e);

            return false;
        }
    }
    public static void printException(Exception e) {
        if (BuildConfig.DEBUG || BuildConfig.FLAVOR.equals("dev")) {
            if (e != null) {
                Log.d(GlobalVars.LOG_TAG, Log.getStackTraceString(e));
            }
        }
    }
    public static String PrintStream(InputStream in) throws Exception {
        InputStreamReader isr = new InputStreamReader(in, "UTF-8");
        BufferedReader buffer = new BufferedReader(isr);
        String str = "";
        StringBuilder strbuild = new StringBuilder();
        while ((str = buffer.readLine()) != null) {
            strbuild.append(str);
        }
        return strbuild.toString();
    }

    public static String encryptData(String text) {
        try {
            byte[] ivBytes = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                    0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
            byte[] keyBytes = HASH
                    .getBytes("UTF-8");
            byte[] textBytes = text.getBytes("UTF-8");
            byte[] encryptedData = AES256Cipher.AES_Encode(ivBytes, keyBytes,
                    textBytes);
            encryptedData = Base64.encode(encryptedData, 0);
            return new String(encryptedData, "UTF-8");
        } catch (Exception e) {
            GlobalFunctions.printException(e);

            return "";
        }
    }

    public static String decryptData(String text) {
        try {
            byte[] ivBytes = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                    0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
            byte[] encryptedData = Base64.decode(text, 0);
            return new String(AES256Cipher.AES_Decode(ivBytes,
                    HASH.getBytes("UTF-8"),
                    encryptedData), "UTF-8");
        } catch (Exception e) {
            GlobalFunctions.printException(e);

            return "";
        }
    }

    public static void showToast(Activity activity, String Message) {
        if (activity != null) {
            Toast.makeText(activity, Message, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showToast(Activity activity, String Message, int length) {
        if (activity != null) {
            Toast.makeText(activity, Message, length).show();
        }
    }
    public static HttpResponseBean Get_StreamHttp(String urlStr, HashMap<String, String> headers, boolean isGet) throws Exception {

        HttpResponseBean httpResponseBean = new HttpResponseBean();

        URL url = new URL(urlStr);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(TIME_OUT /* milliseconds */);
        connection.setConnectTimeout(TIME_OUT /* milliseconds */);
        connection.setRequestMethod(isGet ? "GET" : "DELETE");
        connection.setDoInput(true);

        if (!headers.isEmpty()) {
            for (String key : headers.keySet()) {
                if (!headers.get(key).isEmpty()) {
                    connection.setRequestProperty(key, headers.get(key));
                }
            }
        }

        connection.connect();

        int status = 0;
        try {
            status = connection.getResponseCode();
        } catch (Exception e) {
            if (GlobalVars.IS_DEBUG) {
                GlobalFunctions.printException(e);
            }
            if (e.getMessage().equalsIgnoreCase("No authentication challenges found")) {
                status = HttpURLConnection.HTTP_UNAUTHORIZED;
            }
        }
        if (GlobalVars.IS_DEBUG) {
            largeLog("HttpStatus", String.valueOf(status));
        }

        httpResponseBean.setStatus(status);

        if (status >= HttpURLConnection.HTTP_OK && status < 400) {
            InputStream inputStream = connection.getInputStream();
            if (inputStream != null) {
                httpResponseBean.setResponse(PrintStream(inputStream));
            } else {
                httpResponseBean.setResponse("");
            }
        } else {
            httpResponseBean.setResponse(PrintStream(connection.getErrorStream()));
        }

        return httpResponseBean;
    }

    public static HttpResponseBean Post_StreamHttp(JSONObject jsonObject, HashMap<String, String> headers, String urlStr, String method) throws Exception {

        HttpResponseBean httpResponseBean = new HttpResponseBean();

        URL url = new URL(urlStr);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(TIME_OUT /* milliseconds */);
        connection.setConnectTimeout(TIME_OUT /* milliseconds */);
        connection.setRequestMethod(method);

        if (!headers.isEmpty()) {
            for (String key : headers.keySet()) {
                if (!headers.get(key).isEmpty()) {
                    connection.setRequestProperty(key, headers.get(key));
                }
            }
        }

        connection.addRequestProperty("Connection", "Keep-Alive");
        connection.addRequestProperty("Accept", "application/json");
        connection.addRequestProperty("Content-Type", "application/json; charset=UTF-8");
        connection.setUseCaches(false);
        connection.setDoOutput(true);
        connection.setDoInput(true);

        OutputStream os = connection.getOutputStream();
        os.write(jsonObject.toString().getBytes("UTF-8"));
        os.close();

//        OutputStream out = new BufferedOutputStream(connection.getOutputStream());
//        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
//        writer.write(URLEncoder.encode(jsonObject.toString(), "UTF-8"));
//        writer.flush();
//        writer.close();
//        out.close();

        connection.connect();

        int status = 0;
        try {
            status = connection.getResponseCode();
        } catch (Exception e) {
            if (GlobalVars.IS_DEBUG) {
                GlobalFunctions.printException(e);
            }
            if (e.getMessage().equalsIgnoreCase("No authentication challenges found")) {
                status = HttpURLConnection.HTTP_UNAUTHORIZED;
            }
        }
        if (GlobalVars.IS_DEBUG) {
            largeLog("HttpStatus", String.valueOf(status));
        }

        httpResponseBean.setStatus(status);

        if (status >= HttpURLConnection.HTTP_OK && status < 400) {
            InputStream inputStream = connection.getInputStream();
            if (inputStream != null) {
                httpResponseBean.setResponse(PrintStream(inputStream));
            } else {
                httpResponseBean.setResponse("");
            }
        } else {
            httpResponseBean.setResponse(PrintStream(connection.getErrorStream()));
        }

        return httpResponseBean;
    }

    public static void largeLog(String tag, String content) {
        if (content.length() > 4000) {
            final int chunkSize = 2048;
            final int maxSize = Math.min(content.length(), MAX_LOG_SIZE);
            for (int i = 0; i < maxSize; i += chunkSize) {
                Log.d(tag, content.substring(i, Math.min(maxSize, i + chunkSize)));
            }
        } else {
            Log.d(tag, content);
        }
    }

    public static void largeLog2(String tag, String content) {
        if (content.length() > 4000) {
            Log.d(tag, content.substring(0, 4000));
            largeLog2(tag, content.substring(4000));
        } else {
            Log.d(tag, content);
        }
    }
}
