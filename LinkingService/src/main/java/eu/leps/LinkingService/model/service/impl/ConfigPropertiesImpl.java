/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.leps.LinkingService.model.service.impl;

import eu.leps.LinkingService.model.service.ConfigProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 *
 * @author nikos
 */
@Service
public class ConfigPropertiesImpl implements ConfigProperties {

    @Override
    public String getProperty(String propertyName) {
        String res = System.getenv(propertyName);
        if(!StringUtils.isEmpty(res)){
            return res;
        }
        return "errorValue";
    }
    
}
