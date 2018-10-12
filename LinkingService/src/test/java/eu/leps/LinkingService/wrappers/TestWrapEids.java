/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.leps.LinkingService.wrappers;

import eu.leps.LinkingService.model.pojo.EidCredentials;
import eu.leps.LinkingService.model.wrappers.WrapEids;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author nikos
 */
public class TestWrapEids {
   
    
    @Test
    public void testEidasEid() throws IOException{
        String eidas = "{\"eid\": \"ES/GR/99999142H\",\"personIdentifier\":\"ES/GR/99999142H\",\"dateOfBirth\":\"2000-01-01\",\"currentFamilyName\":\"PAPELLIDO142 SAPELLIDO142 - DNI 99999142H\",\"currentGivenName\":\"NOMBRE142\"}";
        EidCredentials creds = WrapEids.parseJWTSubject(eidas);
        assertEquals(creds.getEid(),"ES/GR/99999142H");
        assertEquals(creds.getCurrentGivenName(),"NOMBRE142");
        assertEquals(creds.getEmail(),null);
    }
    
    
    @Test
    public void testUAegeanEid() throws IOException{
        String eidas = "{\"eid\":\"triantafyllou.ni@aegean.gr\",\"personIdentifier\":\"triantafyllou.ni@aegean.gr\",\"currentFamilyName\":\"Triantafyllou\",\"currentGivenName\":\"Nikos\"}";
        EidCredentials creds = WrapEids.parseJWTSubject(eidas);
        assertEquals(creds.getEid(),"triantafyllou.ni@aegean.gr");
        assertEquals(creds.getCurrentGivenName(),"Nikos");
        assertEquals(creds.getEmail(),null);
    }
    
}
