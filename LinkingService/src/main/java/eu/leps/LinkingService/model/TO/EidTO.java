/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.leps.LinkingService.model.TO;

/**
 *
 * @author nikos
 */
public class EidTO {
    private String eid;
    private String source;

    public EidTO(String eid, String source) {
        this.eid = eid;
        this.source = source;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
    
    
    
}
