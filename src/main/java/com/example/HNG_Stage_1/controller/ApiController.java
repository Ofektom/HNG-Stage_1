package com.example.HNG_Stage_1.controller;

import com.example.HNG_Stage_1.model.Visitor;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.Scanner;

import static java.util.Objects.nonNull;


@RestController
@RequestMapping("/api")
public class ApiController {

    private static final String API_URL = "http://ip-api.com/json/";


    @GetMapping("/hello")
    public ResponseEntity<Visitor> greeting(@RequestParam(name = "visitor_name") String visitor_name, HttpServletRequest request) throws IOException, GeoIp2Exception {
        return greetings(visitor_name, request);
    }

    private ResponseEntity<Visitor> greetings(String visitor_name, HttpServletRequest request) throws IOException, GeoIp2Exception {
        String clientIp = getClientIpAddress(request);
        String city = getIpLocation(clientIp);
        String temperature = request.getLocalName();

        Visitor visitor = new Visitor();
        visitor.setClient_ip(clientIp);
        visitor.setLocation(city);
        visitor.setGreeting("Hello, " + visitor_name + "! , " + "the temperature is " + temperature + " degrees Celsius in ");
        return ResponseEntity.ok(visitor);
    }

    public static String getClientIpAddress(HttpServletRequest request) {
        String xForwardedForHeader = request.getHeader("X-Forwarded-For");
        if (xForwardedForHeader != null && !xForwardedForHeader.isEmpty()) {
            return xForwardedForHeader.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }


//        private String getIpLocation(String ip) throws IOException, GeoIp2Exception {
//        File database = new File("src/main/resources/static/GeoLite2-City_20240628/GeoLite2-City.mmdb");
//        DatabaseReader reader = new DatabaseReader.Builder(database).build();
//        InetAddress ipAddress = InetAddress.getByName(ip);
//        CityResponse response = reader.city(ipAddress);
//        return response.getCity().getName();
//    }



    public String getIpLocation(String ip) {
        StringBuilder location = new StringBuilder();
        try {
            URL url = new URL(API_URL + ip);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                location.append(scanner.nextLine());
            }

            scanner.close();
            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return location.toString();
    }

    public String getPublicIpAddress() {
        StringBuilder ipAddress = new StringBuilder();
        try {
            URL url = new URL("https://httpbin.org/ip");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                ipAddress.append(line);
            }

            reader.close();
            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Parse the JSON response to extract the public IP address
        String response = ipAddress.toString();
        String publicIp = response.substring(response.indexOf("\"origin\":") + 11, response.indexOf("\"origin\":") + 26);

        return publicIp;
    }

}
