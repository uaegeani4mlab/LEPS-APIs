/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.leps.LinkingService.service;

import eu.leps.LinkingService.model.DAO.LinkedSetRepository;
import eu.leps.LinkingService.model.DMO.EidDMO;
import eu.leps.LinkingService.model.DMO.LinkedSetDMO;
import eu.leps.LinkingService.model.service.impl.LinksServiceImpl;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.assertj.core.util.Arrays;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author nikos
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class TestLinksService {

    private LinkedSetRepository linksRepo;
    private LinksServiceImpl linkServ;

    @Before
    public void before() {
        linksRepo = mock(LinkedSetRepository.class);
        EidDMO link = new EidDMO();
        link.setEid("eid1");
        link.setSource("source1");
        EidDMO link2 = new EidDMO();
        link2.setEid("eid2");
        link2.setSource("source2");
        Set links = new HashSet<>(Arrays.asList(new EidDMO[]{link, link2}));
        LinkedSetDMO linkSet = new LinkedSetDMO();
        linkSet.setEids(links);
        List<LinkedSetDMO> mockResult = new ArrayList();
        mockResult.add(linkSet);
        when(linksRepo.selectIfContainsEid("eid1")).thenReturn(mockResult);

        linkServ = new LinksServiceImpl(linksRepo);
    }

    @Test
    public void testGetLinksByEid() {
        assertEquals(linkServ.findByEid("eid1").getLinked().size(), 2);
        assertEquals(linkServ.findByEid("e1").getLinked().size(), 0);
    }

    
    
    
}
