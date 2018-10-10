/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.leps.LinkingService.model.wrappers;

import eu.leps.LinkingService.model.DMO.EidDMO;
import eu.leps.LinkingService.model.DMO.LinkedSetDMO;
import eu.leps.LinkingService.model.TO.EidTO;
import eu.leps.LinkingService.model.TO.LinkedSetTO;
import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author nikos
 */
public class WrapTO {

    public static EidTO wrapLinkEntry(EidDMO linkDMO) {
        return new EidTO(linkDMO.getEid(), linkDMO.getSource());
    }

    public static LinkedSetTO wrapLinkSet(LinkedSetDMO entries) {
        List<EidTO> linked = new ArrayList();
        entries.getEids().stream().forEach(entry -> {
            linked.add(wrapLinkEntry(entry));
        });
        return new LinkedSetTO(null, linked, entries.getId());
    }

}
