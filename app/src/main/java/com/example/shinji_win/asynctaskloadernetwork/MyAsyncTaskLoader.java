package com.example.shinji_win.asynctaskloadernetwork;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MyAsyncTaskLoader extends AbstractAsyncTaskLoader<String> {

    private String mExtraParam;

    public MyAsyncTaskLoader(Context context, String extraParam) {
        super(context);
        mExtraParam = extraParam;
    }

    @Override
    public String loadInBackground() {

        Log.d("SampleLoader", "loadInBackground");
/*
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ignored) {}
*/

        HttpURLConnection con = null;
        URL url = null;
        String urlSt = "http://zipcloud.ibsnet.co.jp/api/search?zipcode=1640003";

        try {
            // URLの作成
            url = new URL(urlSt);
            // 接続用HttpURLConnectionオブジェクト作成
            con = (HttpURLConnection)url.openConnection();
            // リクエストメソッドの設定
            con.setRequestMethod("POST");
            // リダイレクトを自動で許可しない設定
            con.setInstanceFollowRedirects(false);
            // URL接続からデータを読み取る場合はtrue
            con.setDoInput(true);
            // URL接続にデータを書き込む場合はtrue
            con.setDoOutput(true);

            // 接続
            con.connect(); // ①

            // 本文の取得
            InputStream in = con.getInputStream();
            String readSt = readInputStream(in);


            return "result: " + readSt;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String readInputStream(InputStream in) throws IOException{
        StringBuffer sb = new StringBuffer();
        String st = "";

        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        while((st = br.readLine()) != null)
        {
            Log.w( "DEBUG_DATA", "st = " + st);
            sb.append(st);
        }
        try
        {
            in.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        Log.w( "DEBUG_DATA", "sb.toString() = " + sb.toString());
        return sb.toString();
    }
}
