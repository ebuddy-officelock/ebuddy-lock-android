package org.thoughtcrime.securesms;

import android.util.Log;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleAnalytics {
    final private String baseUrl = "https://api.simpleanalytics.io";
    final private String version = "1";
    final private String hostName = "android.app.lock.ebuddy.com";
    final private String userAgent = "android";

    private static SimpleAnalytics instance = null;
    private CloseableHttpClient client;

    private SimpleAnalytics() {
        client = HttpClients.createDefault();
    }

    public static SimpleAnalytics getInstance() {
        if (instance == null) {
            return new SimpleAnalytics();
        }
        return instance;
    }

    public void logEvent(String event) {
        String json = "{" +
                "\"v\":" + version + "," +
                "\"date\":\"" + getDateString() + "\"," +
                "\"events\":[\"" + event + "\"]," +
                "\"hostname\":\"" + hostName + "\"," +
                "\"ua\":\"" + userAgent + "\"," +
                "\"platform\":\"" + userAgent + "\"" +
                "}";

        makeRequest(baseUrl + "/events", json);
    }

    private void makeRequest(String url, String json) {
        HttpPost httpPost = new HttpPost(url);

        StringEntity entity;
        try {
            entity = new StringEntity(json);
        } catch (Exception exception) {
            Log.e("SimpleAnalytics", exception.toString());
            return;
        }
        httpPost.setEntity(entity);
        httpPost.setHeader("User-Agent", "eBuddy.lock Android");
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        try {
            CloseableHttpResponse response = client.execute(httpPost);
            Log.d("SimpleAnalytics", response.toString());
        } catch (Exception exception) {
            Log.e("SimpleAnalytics", exception.toString());
        }

//        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
//        client.close();
    }

    private String getDateString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
