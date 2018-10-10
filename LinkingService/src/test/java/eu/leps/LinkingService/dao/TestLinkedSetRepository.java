/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.leps.LinkingService.dao;

import eu.leps.LinkingService.model.DAO.LinkedSetRepository;
import eu.leps.LinkingService.model.DMO.EidDMO;
import eu.leps.LinkingService.model.DMO.LinkedSetDMO;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import javax.transaction.Transactional;
import org.assertj.core.util.Arrays;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import eu.leps.LinkingService.model.DAO.EidRepository;

/**
 *
 * @author nikos
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@SpringBootTest
public class TestLinkedSetRepository {

    @Resource
    private LinkedSetRepository lSetRepo;

    @Resource
    private EidRepository linkRepo;

    @Test
    public void testSaveLinkSet() {
        EidDMO link = new EidDMO();
        link.setEid("eid1");
        link.setSource("source1");

        EidDMO link2 = new EidDMO();
        link2.setEid("eid2");
        link2.setSource("source2");

        Set links = new HashSet<>(Arrays.asList(new EidDMO[]{link, link2}));
        LinkedSetDMO linkSet = new LinkedSetDMO();
        linkSet.setEids(links);

        lSetRepo.save(linkSet);

    }

    @Test
    public void updateLinkSet() {
        EidDMO link = new EidDMO();
        link.setEid("eid1");
        link.setSource("source1");
        Set links = new HashSet<>(Arrays.asList(new EidDMO[]{link}));
        LinkedSetDMO linkSet = new LinkedSetDMO();
        linkSet.setEids(links);
        lSetRepo.save(linkSet);

        LinkedSetDMO saved = lSetRepo.findAll().get(0);
        assertEquals(saved.getEids().size(), 1);
        assertEquals(lSetRepo.findAll().size(), 1);

        EidDMO secondLink = new EidDMO();
        secondLink.setEid("eid1");
        secondLink.setSource("source1");
        saved.getEids().add(secondLink);

        lSetRepo.save(saved);

        LinkedSetDMO result = lSetRepo.findAll().get(0);
        assertEquals(result.getEids().size(), 2);

        assertEquals(lSetRepo.findAll().size(), 1);

    }

    @Test
    public void findByIdSaveLinkSet() {
        EidDMO link1 = new EidDMO();
        link1.setEid("eid1");
        link1.setSource("source1");

        EidDMO link2 = new EidDMO();
        link2.setEid("eid2");
        link2.setSource("source2");

        EidDMO link3 = new EidDMO();
        link3.setEid("eid3");
        link3.setSource("source3");

        Set links = new HashSet<>(Arrays.asList(new EidDMO[]{link1, link2}));
        LinkedSetDMO linkSet = new LinkedSetDMO();
        linkSet.setEids(links);
        lSetRepo.save(linkSet);

        Set links2 = new HashSet<>(Arrays.asList(new EidDMO[]{link3}));
        LinkedSetDMO linkSet2 = new LinkedSetDMO();
        linkSet2.setEids(links2);
        lSetRepo.save(linkSet2);

        List<LinkedSetDMO> rsAll = lSetRepo.findAll();
        List<LinkedSetDMO> rs = lSetRepo.selectIfContainsEid("eid1");
        assertEquals(rs.size(), 1);
        assertEquals(rsAll.size(), 2);
        assertEquals(rs.get(0).getEids().size(), 2);
    }

}
