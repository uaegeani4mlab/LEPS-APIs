/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uagean.loginWebApp.service.impl;

import gr.uagean.loginWebApp.service.EidasPropertiesService;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 *
 * @author nikos
 */
@Service
public class EidasPropertiesServiceImpl implements EidasPropertiesService {

    private final static String propertiesEnvVar = "EIDAS_PROPERTIES";

    @Override
    public List<String> getEidasProperties() throws NullPointerException {
        String properties = System.getenv().get(propertiesEnvVar);
        if (properties != null && properties.length() > 0) {
            return Arrays.stream(properties.split(",")).map(property -> {
                switch (property) {
                    case "CurrentFamilyName":
                        return "http://eidas.europa.eu/attributes/naturalperson/CurrentFamilyName";
                    case "CurrentGivenName":
                        return "http://eidas.europa.eu/attributes/naturalperson/CurrentGivenName";
                    case "DateOfBirth":
                        return "http://eidas.europa.eu/attributes/naturalperson/DateOfBirth";
                    case "PersonIdentifier":
                        return "http://eidas.europa.eu/attributes/naturalperson/PersonIdentifier";
                    default:
                        return property;
                }
            }).collect(Collectors.toList());
        } else {
            throw new NullPointerException("properties string was empty");
        }
    }

    @Override
    public List<String> getNaturalProperties() throws NullPointerException {
        return getEidasProperties().stream().filter(prop -> {
            return prop.contains("naturalperson");
        }).map(prop -> {
            String[] segments = prop.split("/");
            return segments[segments.length - 1];
        }).map(prop -> {
            return String.join(" ", prop.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])"));
        }).collect(Collectors.toList());
    }

    @Override
    public List<String> getLegalProperties() throws NullPointerException {
        return getEidasProperties().stream().filter(prop -> {
            return prop.contains("legal");
        }).map(prop -> {
            String[] segments = prop.split("/");
            return segments[segments.length - 1];
        }).map(prop -> {
            return String.join(" ", prop.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])"));
        }).collect(Collectors.toList());
    }

}
