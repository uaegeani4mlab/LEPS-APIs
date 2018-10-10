/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.leps.LinkingService.model.service;

import eu.leps.LinkingService.model.TO.LinkedSetTO;

/**
 *
 * @author nikos
 */
public interface LinksService {
    
    
    public void addEidToLinkById(String eid,String source, Long linkId);

    public void addEidToLinkByObj(String eid,String source, LinkedSetTO set);
    
    public LinkedSetTO findByEid(String eid) throws IndexOutOfBoundsException;
    
    public Long saveLinkSet(LinkedSetTO link);
}