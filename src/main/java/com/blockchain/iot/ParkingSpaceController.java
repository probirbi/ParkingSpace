package com.blockchain.iot;

import com.blockchain.iot.data.TestData;
import com.blockchain.iot.model.ParkingSpace;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class ParkingSpaceController {

    List<ParkingSpace> parkingSpaces = new ArrayList<ParkingSpace>();

    @GetMapping("/get-respose-from-3")
    public String getData() {

        return "response from node 3";
    }

    @GetMapping("/parkingspaces")
    public List<ParkingSpace> getSmartHome() {
        return parkingSpaces;
    }

    @PostMapping("/parkingspaces")
    public String saveSmartHome(@RequestBody ParkingSpace parkingSpace) {
        parkingSpaces.add(parkingSpace);
        return "success";
    }

    @PostMapping("/evaluatetemperature")
    public String evaluatetemperature() {
        try {
            String url = "http://localhost:8082/evaluate";
            String result = "";
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-type", "application/json");

            CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
            CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpPost);
            HttpEntity entity = closeableHttpResponse.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
                System.out.println(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    @PostMapping("/evaluate")
    public String evaluate() {

        String result = "";
        for (ParkingSpace parkingSpace : parkingSpaces) {
            if (parkingSpace.getParkedSpace() > 200) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    String url = "http://localhost:8084/blockchain";
                    HttpPost httpPost = new HttpPost(url);
                    httpPost.setHeader("Content-type", "application/json");
                    String json = "{" +
                            "\"hash\":" + "\"" + "" + "\"," +
                            "\"previousHash\":" + "\"" + "" + "\"," +
                            "\"description\":" + "\"" + "ParkingSpace Block" + "\"," +
                            "\"data\":" + "{" +
                            "\"timestamp\":" + "\"" + parkingSpace.getTimestamp() + "\"," +
                            "\"totalSpace\":" + parkingSpace.getTotalSpace() + "," +
                            "\"parkedSpace\":" + parkingSpace.getParkedSpace() + "," +
                            "\"freeSpace\":" + parkingSpace.getFreeSpace() +
                            "} ," +
                            "\"timeStamp\":" + new Date().getTime() + "," +
                            "\"nonce\":" + 0 + "," +
                            "\"node\":" + 3 +
                            "}";
                    System.out.println(json);
                    httpPost.setEntity(new StringEntity(json));
                    CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
                    CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpPost);
                    HttpEntity entity = closeableHttpResponse.getEntity();
                    if (entity != null) {
                        result = EntityUtils.toString(entity);
                        System.out.println(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println(parkingSpace.getParkedSpace() + " Parking space data is verified and found invalid");
            }

        }
        return "node evaluated";
    }

    @PostMapping("/evaluatesmarthome")
    public String evaluatesmarthome() {
        try {
            String url = "http://localhost:8081/evaluate";
            String result = "";
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-type", "application/json");

            CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
            CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpPost);
            HttpEntity entity = closeableHttpResponse.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
                System.out.println(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "node evaluated";
    }

    @GetMapping("/seeddata")
    public void insertData() {
        TestData.callPost();
    }
}
