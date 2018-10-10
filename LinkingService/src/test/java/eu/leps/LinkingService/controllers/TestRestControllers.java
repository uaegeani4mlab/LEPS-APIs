/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.leps.LinkingService.controllers;

import eu.leps.LinkingService.LinkingServiceApplication;
import eu.leps.LinkingService.controllers.TestRestControllers.TestConfig;
import eu.leps.LinkingService.model.TO.EidTO;
import eu.leps.LinkingService.model.TO.LinkedSetTO;
import eu.leps.LinkingService.model.service.LinksService;
import java.util.ArrayList;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 *
 * @author nikos
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(RestControllers.class)
@ContextConfiguration(classes = {TestConfig.class, LinkingServiceApplication.class})
public class TestRestControllers {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LinksService linkServ;

    @Autowired
    private CacheManager cacheManager;

    private Cache.ValueWrapper mockWrapper;
    private Cache cache;

    @Configuration
    static class TestConfig {

        @Bean
        public CacheManager cacheManager() {
            return PowerMockito.mock(CacheManager.class);
        }
    }

    public static String NOT_FOUND_EID = "notFoundEid";
    public static String FOUND_EID = "foundEid";
    public static String VALID_SESSION_ID = "validSession";

    @Before
    public void before() {
        when(linkServ.findByEid(NOT_FOUND_EID)).thenReturn(new LinkedSetTO(null, new ArrayList(), null));

        ArrayList<EidTO> results = new ArrayList();
        EidTO res1 = new EidTO("eid1", "eidas");
        EidTO res2 = new EidTO("eid2", "uaegean");
        results.add(res1);
        results.add(res2);
        when(linkServ.findByEid(FOUND_EID)).thenReturn(new LinkedSetTO(null, results, Long.valueOf(0)));
        when(linkServ.saveLinkSet((LinkedSetTO) org.mockito.ArgumentMatchers.any(LinkedSetTO.class))).thenReturn(Long.valueOf(0));

        mockWrapper = Mockito.mock(Cache.ValueWrapper.class);
        cache = PowerMockito.mock(Cache.class);
        Mockito.when(cacheManager.getCache("sessions")).thenReturn(cache);
        Mockito.when(cache.get(VALID_SESSION_ID)).thenReturn(mockWrapper);
    }

    @Test
    public void testGetEid_NotFound() throws Exception {
        mvc.perform(get("/match?eIdentifier=" + NOT_FOUND_EID))
                .andExpect(status().isOk())
                //                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.code", is("ERROR")))
                .andExpect(jsonPath("$.error", is("no match found")));
        assertEquals(true, true);
    }

    @Test
    public void testGetEid_Match_Found() throws Exception {
        mvc.perform(get("/match?eIdentifier=" + FOUND_EID))
                .andExpect(status().isOk())
                //                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.code", is("OK")))
                .andExpect(jsonPath("$.error", nullValue()))
                .andExpect(jsonPath("$.result", hasSize(2)))
                .andExpect(jsonPath("$.result[0].eid", is("eid1")))
                .andExpect(jsonPath("$.result[1].eid", is("eid2")));
        assertEquals(true, true);
    }

    @Test
    public void saveWithNoSession() throws Exception {
        mvc.perform(post("/link").param("eid", "postedValue"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is("NEW")));
    }

    @Test
    public void saveWithExistingSession() throws Exception {
        ArrayList<EidTO> results = new ArrayList();
        EidTO res1 = new EidTO("eid11", "eidas");
        EidTO res2 = new EidTO("eid21", "uaegean");
        results.add(res1);
        results.add(res2);
        LinkedSetTO cachedSet = new LinkedSetTO(null, results, null);

        Mockito.when(mockWrapper.get()).thenReturn(cachedSet);

        mvc.perform(
                post("/link")
                        .header("sesionId", VALID_SESSION_ID)
                        .param("eid", "postedValue"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is("OK")));

    }

    public void saveWithErrorSsession() {

    }

}
