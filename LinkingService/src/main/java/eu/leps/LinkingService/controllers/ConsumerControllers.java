/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.leps.LinkingService.controllers;

import eu.leps.LinkingService.model.TO.LinkedSetTO;
import eu.leps.LinkingService.model.pojo.EidCredentials;
import eu.leps.LinkingService.model.service.ConfigProperties;
import eu.leps.LinkingService.model.service.LinksService;
import eu.leps.LinkingService.model.wrappers.WrapEids;
import io.jsonwebtoken.Jwts;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

/**
 *
 * @author nikos
 */
@Controller
public class ConsumerControllers {

    @Autowired
    private ConfigProperties props;

    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private LinksService linkServ;

    private final static String encodedKey = "QjG+wP1CbAH2z4PWlWIDkxP4oRlgK2vos5/jXFfeBw8=";

    private final static String LOGIN_URL = "LOGIN_URL";

    /**
     * accepts a JWT containing an eId response (from the eIDAS webApp)
     * the  jwt can come either as a url param or a cookie
     */
    @GetMapping(value = {"/eidResponse", "/"})
    public String getEidResponse(@RequestParam(value = "access_token", required = false) String jwsParam,
            @CookieValue(value = "access_token", required = false) String cookieJWS, Model model) throws UnsupportedEncodingException {//(@CookieValue("access_token") String jwt){

//        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//        byte[] keyBytes = key.getEncoded();
//        String base64Encoded = new String(Base64.getEncoder().encode(keyBytes), StandardCharsets.UTF_8);
        // decode the base64 encoded string
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        // rebuild key using SecretKeySpec
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "HmacSHA256");

        String jws = !StringUtils.isEmpty(jwsParam) ? jwsParam : cookieJWS;
        try {
            if (jws != null) {
                String sub = Jwts.parser().setSigningKey(originalKey).parseClaimsJws(jws).getBody().getSubject();
                String origin = Jwts.parser().setSigningKey(originalKey).parseClaimsJws(jws).getBody().get("origin", String.class);
                String email = Jwts.parser().setSigningKey(originalKey).parseClaimsJws(jws).getBody().get("email", String.class);

                EidCredentials creds = WrapEids.parseJWTSubject(sub);
                creds.setEmail(email);
                creds.setOrigin(origin);

                model.addAttribute("eid", creds.getEid());
                model.addAttribute("source", creds.getOrigin());
            }

            model.addAttribute("login", props.getProperty(LOGIN_URL));
            return "home";
        } catch (Exception exception) {
            //Invalid signature/claims
            return "home";
        }

    }

    @GetMapping(value = {"/linked"})
    public String linked(Model model, @CookieValue("session") String sessionId) {
        Cache.ValueWrapper existingLinksWrapper = cacheManager.getCache("sessions").get(sessionId);
        if (existingLinksWrapper != null && existingLinksWrapper.get() != null) {
            LinkedSetTO cachedSet = (LinkedSetTO) existingLinksWrapper.get();
            model.addAttribute("links", linkServ.findById(cachedSet.getId()));
        }

        return "linked";
    }

}
