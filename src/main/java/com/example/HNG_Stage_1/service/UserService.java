//package com.example.HNG_Stage_1.service;
//
//
//import com.example.HNG_Stage_1.model.Visitor;
//import com.maxmind.geoip2.DatabaseReader;
//import com.maxmind.geoip2.exception.GeoIp2Exception;
//import com.maxmind.geoip2.model.CityResponse;
//import com.maxmind.geoip2.record.Country;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import java.io.File;
//import java.io.IOException;
//import java.net.InetAddress;
//import static java.util.Objects.nonNull;
//
//
//@Service
//public class UserService{
//
//    private Visitor user;
//
//    public ResponseEntity<Visitor> greetings(String visitor_name, HttpServletRequest request)  {
//        Visitor visitor = new Visitor();
//        String clientIp = request.getRemoteAddr();
////        String city = getIpLocation(clientIp);
//        String temperature = request.getLocalName();
//
//        visitor.setClient_ip(clientIp);
//        visitor.setLocation("Uyo");
//        visitor.setGreeting("Hello, " + visitor_name + "! , " + "the temperature is " + temperature + " degrees Celsius in ");
//
//        user.setGreeting(visitor.getGreeting());
//        user.setLocation(visitor.getLocation());
//        user.setClient_ip(visitor.getClient_ip());
//
//        return ResponseEntity.ok(visitor);
//    }
//
//    private String extractIp(HttpServletRequest request) {
//        String clientIp;
//        String clientXForwardedForIp = request.getHeader("x-forwarded-for");
//        if (nonNull(clientXForwardedForIp)) {
//            clientIp = parseXForwardedHeader(clientXForwardedForIp);
//        } else {
//            clientIp = request.getRemoteAddr();
//        }
//        return clientIp;
//    }
//
//    private String parseXForwardedHeader(String xForwardedHeader) {
//        return xForwardedHeader.split(",")[0].trim();
//    }
//
//    private String getIpLocation(String ip) throws IOException, GeoIp2Exception {
//        File database = new File("src/main/resources/static/GeoLite2-City_20240628/GeoLite2-City.mmdb");
//        DatabaseReader reader = new DatabaseReader.Builder(database).build();
//
////        InetAddress ipAddress = InetAddress.getByName(ip);
////
////        CityResponse response = null;
////        try {
////            response = reader.city(ipAddress);
////        } catch (GeoIp2Exception e) {
////            throw new RuntimeException(e);
////        }
////
////        Country country = response.getCountry();
////        System.out.println(country.getIsoCode());
//        InetAddress ipAddress = InetAddress.getByName(ip);
//        CityResponse response = reader.city(ipAddress);
//        return response.getCity().getName();
//    }
//
//
//}
