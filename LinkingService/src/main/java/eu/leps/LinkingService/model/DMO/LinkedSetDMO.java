/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.leps.LinkingService.model.DMO;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author nikos
 */
@Entity
@Table(name = "link_set")
public class LinkedSetDMO implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "eids_id")
    private Set<EidDMO> eids;

    public LinkedSetDMO() {
    }

    public LinkedSetDMO(Long id, Set<EidDMO> linkedEids) {
        this.id = id;
        this.eids = linkedEids;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<EidDMO> getEids() {
        return eids;
    }

    public void setEids(Set<EidDMO> eids) {
        this.eids = eids;
    }

}
