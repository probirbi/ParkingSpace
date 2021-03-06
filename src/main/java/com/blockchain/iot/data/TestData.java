package com.blockchain.iot.data;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class TestData {

    public static void callPost() {
        for (int i = 0 ; i < 10 ; i++) {
           Random random = new Random();
            int rangeMin = 0;
            int rangeMax = 500;
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            int totalSpace = rangeMax;
            int parkedSpace = random.nextInt((rangeMax - rangeMin) + 1) + rangeMin;
            int freeSpace = totalSpace - parkedSpace;
            String result = "";
            try {
                String url = "http://localhost:8083/parkingspaces";
                HttpPost httpPost = new HttpPost(url);
                httpPost.setHeader("Content-type", "application/json");
                String json = "{" +
                        "\"timestamp\":" + "\"" + sdf.format(new Date()) + "\"," +
                        "\"totalSpace\":" + "" + totalSpace + "," +
                        "\"parkedSpace\":" + "" + parkedSpace + "," +
                        "\"freeSpace\":" + "" + freeSpace +
                        "}";
                System.out.println(json);
                httpPost.setEntity(new StringEntity(json));
                CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
                CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpPost);
                HttpEntity entity = closeableHttpResponse.getEntity();
                System.out.println("Transaction: "+i);
                if (entity != null) {
                    result = EntityUtils.toString(entity);
                    System.out.println(result);
                }
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
