package com.example.demo.controller;

import com.example.demo.dto.AccessTokenDTO;
import com.example.demo.dto.GithubUser;
import com.example.demo.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.url}")
    private String clientUrl;
    @Value("${github.client.secret}")
    private String clientSecret;


    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state){
        AccessTokenDTO atDTO = new AccessTokenDTO();
        atDTO.setCode(code);
        atDTO.setRedirect_url(clientUrl);
        atDTO.setState(state);
        atDTO.setClient_id(clientId);
        atDTO.setClient_secret(clientSecret);
        String accessToken = githubProvider.getAccessToken(atDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        return "index";
    }
}
