/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.leps.LinkingService.wrappers;

import eu.leps.LinkingService.model.DMO.EidDMO;
import eu.leps.LinkingService.model.DMO.LinkedSetDMO;
import eu.leps.LinkingService.model.TO.EidTO;
import eu.leps.LinkingService.model.TO.LinkedSetTO;
import eu.leps.LinkingService.model.wrappers.WrapTO;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author nikos
 */
public class TestWrappers {

    @Test
    public void testWrapLinkEntry() {
        EidDMO dm = new EidDMO(Long.valueOf(0), "eid", "source");
        EidTO entry = WrapTO.wrapLinkEntry(dm);
        assertEquals(dm.getEid(), entry.getEid());
        assertEquals(dm.getSource(), entry.getSource());
    }

    @Test
    public void testWrapLinkSet() {
        EidDMO dm = new EidDMO(Long.valueOf(0), "eid", "source");
        EidDMO dm2 = new EidDMO(Long.valueOf(0), "eid2", "source2");
        Set<EidDMO> entries = new HashSet(Arrays.asList(dm2, dm));
        LinkedSetDMO links = new LinkedSetDMO(Long.valueOf(0), entries);

        LinkedSetTO linksTo = WrapTO.wrapLinkSet(links);

        assertEquals(links.getEids().size(), linksTo.getLinked().size());
        assertEquals(links.getEids().iterator().next().getEid(), linksTo.getLinked().iterator().next().getEid());
        assertEquals(links.getEids().iterator().next().getSource(), linksTo.getLinked().iterator().next().getSource());

        assertEquals(links.getEids().iterator().next().getEid(), linksTo.getLinked().iterator().next().getEid());
        assertEquals(links.getEids().iterator().next().getSource(), linksTo.getLinked().iterator().next().getSource());

    }

}
