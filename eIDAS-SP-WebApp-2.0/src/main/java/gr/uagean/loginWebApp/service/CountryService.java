/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uagean.loginWebApp.service;


import gr.uagean.loginWebApp.model.pojo.Country;
import java.util.List;

/**
 *
 * @author nikos
 */
public interface CountryService {
    
    public List<Country> getEnabled();
    
    
}
