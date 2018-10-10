/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.leps.LinkingService.service;

import eu.leps.LinkingService.model.DMO.EidDMO;
import eu.leps.LinkingService.model.DMO.LinkedSetDMO;
import eu.leps.LinkingService.model.TO.LinkedSetTO;
import eu.leps.LinkingService.model.service.LinksService;
import java.util.HashSet;
import java.util.Set;
import javax.transaction.Transactional;
import org.assertj.core.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import eu.leps.LinkingService.model.DAO.LinkedSetRepository;
import eu.leps.LinkingService.model.TO.EidTO;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author nikos
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@SpringBootTest
public class TestLinkServiceIntegration {

    @Autowired
    private LinksService linkServ;

    @Autowired
    private LinkedSetRepository linkRepo;

    @Test
    public void testAddToLinkTO() {

        EidDMO link1 = new EidDMO();
        link1.setEid("eid1");
        link1.setSource("source1");
        EidDMO link2 = new EidDMO();
        link2.setEid("eid2");
        link2.setSource("source2");
        Set links = new HashSet<>(Arrays.asList(new EidDMO[]{link1, link2}));
        LinkedSetDMO linkSet = new LinkedSetDMO();
        linkSet.setEids(links);

        linkRepo.save(linkSet);

        LinkedSetTO link = linkServ.findByEid("eid1");
        linkServ.addEidToLinkById("eidTest", "eidas", link.getId());

//        assertEquals(linkServ.findByEid("eidTest").getLinked().get(2).getEid(), "eidTest");
    }

    @Test
    public void testSaveLinkeSet() {
        EidTO link1 = new EidTO("eid1", "source1");
        List<EidTO> links = new ArrayList();
        links.add(link1);
        LinkedSetTO linkSet = new LinkedSetTO(null, links, null);
        Long id = linkServ.saveLinkSet(linkSet);
        assertNotNull(id);
        
        assertEquals(linkRepo.findById(id).get().getEids().iterator().next().getEid(),"eid1");
    }

}
