/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.leps.LinkingService.model.wrappers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.leps.LinkingService.model.pojo.EidCredentials;
import java.io.IOException;

/**
 *
 * @author nikos
 */
public class WrapEids {
    

    public static EidCredentials parseJWTSubject(String subject) throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES, false);
        return objectMapper.readValue(subject, EidCredentials.class);  
    }



}
