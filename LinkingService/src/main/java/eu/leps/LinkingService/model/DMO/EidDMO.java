/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.leps.LinkingService.model.DMO;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author nikos
 */
@Entity
@Table(name = "link")
public class EidDMO implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String eid;
    private String source;

//    @ManyToOne
//    @JoinColumn(name = "link_id", nullable = false)
//    private LinkedSetDMO link;

    public EidDMO() {
    }

    public EidDMO(Long id, String eid, String source) {
        this.id = id;
        this.eid = eid;
        this.source = source;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
