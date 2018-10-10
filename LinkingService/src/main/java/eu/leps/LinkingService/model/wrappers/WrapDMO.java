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
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author nikos
 */
public class WrapDMO {

    public static EidDMO wrapLinkEntry(EidTO eidTO) {
        return new EidDMO(null, eidTO.getEid(), eidTO.getSource());
    }

    public static LinkedSetDMO wrapLinkSet(LinkedSetTO entries) {
        Set<EidDMO> linked = new HashSet();

        entries.getLinked().stream().forEach(entry -> {
            linked.add(wrapLinkEntry(entry));
        });

        return new LinkedSetDMO(null, linked);
    }
}
