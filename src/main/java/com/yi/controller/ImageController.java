package com.yi.controller;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class ImageController {

    @Autowired
    private Environment environment;
    private static final Map<String, byte[]> CACHE = new HashMap<>();
// 이미지 캐싱 구현 아직 안됨.
//    @GetMapping("/images/{imageName}")
//    public byte[] getImage(@PathVariable String imageName, HttpServletRequest req) throws URISyntaxException {
//        if (CACHE.containsKey(imageName)) {
//            return CACHE.get(imageName);
//        }
//        RestTemplate restTemplate = new RestTemplate();
//
//        URI uri = new URI(req.getRequestURL().toString());
//        String hostname = uri.getScheme()+"://"+uri.getHost() +":"+ uri.getPort();
//        String imageUrl = hostname+"/images/"+imageName;
//        System.out.println("222222->"+ imageUrl);
//
//        byte[] imageBytes = restTemplate
//                .getForObject(imageUrl, byte[].class);
//        CACHE.put(imageName, imageBytes);
//        return imageBytes;
//    }

}