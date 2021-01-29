package com.github.chanming2015.microcloud.security.web;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;

import net.minidev.json.JSONObject;

/**
 * Description: 授权服务接口 <br/> 
 * Create Date:2021年1月28日 <br/> 
 * @author XuMaoSen
 */
@RestController
public class AuthorizationController
{
    @Autowired
    private KeyPair keyPair;

    @GetMapping(value = "/.public/jwks.json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JSONObject> getKey()
    {
        RSAPublicKey publicKey = (RSAPublicKey) this.keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new ResponseEntity<>(new JWKSet(key).toJSONObject(), HttpStatus.OK);
    }
}
