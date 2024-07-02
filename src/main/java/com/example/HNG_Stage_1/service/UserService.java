package com.example.HNG_Stage_1.service;


import com.example.HNG_Stage_1.model.Visitor;
import com.example.HNG_Stage_1.model.WeatherData;
import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class UserService{
    private static final String API_URL = "http://api.weatherapi.com/v1/current.json";
    private static final String API_KEY = "e79b5861eab94b51a75214742240107";


    public ResponseEntity<Visitor> greetings(String visitor_name, HttpServletRequest request)  {
        String cleanVisitorName = removeSurroundingQuotes(visitor_name);
        String clientIp = getClientIpAddress(request);
        WeatherData weatherData = getWeatherData(clientIp);
        String city = weatherData.getName();
        double temp = weatherData.getTemperatureC();

        Visitor visitor = new Visitor();
        visitor.setClient_ip(clientIp);
        visitor.setLocation(city);
        visitor.setGreeting("Hello, " + cleanVisitorName + "! , " + "the temperature is " + temp + " degrees Celsius in " + city);

        return ResponseEntity.ok(visitor);
    }

    private String removeSurroundingQuotes(String input) {
        // Use regex to remove leading and trailing escaped quotes
        Pattern pattern = Pattern.compile("^\"(.*)\"$");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return input;
    }

    public static String getClientIpAddress(HttpServletRequest request) {
        String xForwardedForHeader = request.getHeader("X-Forwarded-For");
        if (xForwardedForHeader != null && !xForwardedForHeader.isEmpty()) {
            return xForwardedForHeader.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

    public WeatherData getWeatherData(String ip) {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = API_URL + "?key=" + API_KEY + "&q=" + ip + "&aqi=no";
        String response = restTemplate.getForObject(apiUrl, String.class);

        JSONObject jsonResponse = new JSONObject(response);
        String locationName = jsonResponse.getJSONObject("location").getString("name");
        double temperatureC = jsonResponse.getJSONObject("current").getDouble("temp_c");

        return new WeatherData(locationName, temperatureC);
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

        // Parse the JSON response to extract the public IP address using a JSON library
        String response = ipAddress.toString();
        JSONObject jsonResponse = new JSONObject(response);
        String publicIp = jsonResponse.getString("origin");

        // Remove any trailing backslash if present
        publicIp = publicIp.endsWith("\\") ? publicIp.substring(0, publicIp.length() - 1) : publicIp;

        return publicIp;
    }

}
