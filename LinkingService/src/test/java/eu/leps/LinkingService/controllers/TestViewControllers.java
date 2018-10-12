/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.leps.LinkingService.controllers;

import eu.leps.LinkingService.LinkingServiceApplication;
import eu.leps.LinkingService.model.service.ConfigProperties;
import eu.leps.LinkingService.model.service.LinksService;
import javax.servlet.http.Cookie;
import static org.hamcrest.Matchers.equalTo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

/**
 *
 * @author nikos
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(ConsumerControllers.class)
@ContextConfiguration(classes = {TestRestControllers.TestConfig.class, LinkingServiceApplication.class})
public class TestViewControllers {


    /*
    LinkedIn JWT: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJlaWRcIjpcInlBMXJnekMxeHZcIixcImZpcnN0TmFtZVwiOlwiTmlrb2xhb3NcIixcImxhc3ROYW1lXCI6XCJUcmlhbnRhZnlsbG91XCIsXCJjdXJyZW50R2l2ZW5OYW1lXCI6XCJOaWtvbGFvc1wiLFwiY3VycmVudEZhbWlseU5hbWVcIjpcIlRyaWFudGFmeWxsb3VcIixcImVtYWlsXCI6XCJnZTAxMTE3QHlhaG9vLmdyXCJ9Iiwib3JpZ2luIjoibGlua2VkSW4iLCJlbWFpbCI6ImdlMDExMTdAeWFob28uZ3IifQ.Bka-M3-4qe71Hk5W6tVtXL1BXWrg3qHnhlt2u3BEbDU
    UAegean JWT: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJlaWRcIjpcInRyaWFudGFmeWxsb3UubmlAYWVnZWFuLmdyXCIsXCJwZXJzb25JZGVudGlmaWVyXCI6XCJ0cmlhbnRhZnlsbG91Lm5pQGFlZ2Vhbi5nclwiLFwiY3VycmVudEZhbWlseU5hbWVcIjpcIlRyaWFudGFmeWxsb3VcIixcImN1cnJlbnRHaXZlbk5hbWVcIjpcIk5pa29zXCJ9Iiwib3JpZ2luIjoiVUFlZ2VhbiIsImVtYWlsIjoidHJpYW50YWZ5bGxvdS5uaUBhZWdlYW4uZ3IifQ.5wMnyEMT4KDNGHzDVmrnHfgTjgmIv6dAeiBoNMqaHqc
     */
    final Cookie linkedInJwtCookie = new Cookie("access_token", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJlaWRcIjpcInlBMXJnekMxeHZcIixcImZpcnN0TmFtZVwiOlwiTmlrb2xhb3NcIixcImxhc3ROYW1lXCI6XCJUcmlhbnRhZnlsbG91XCIsXCJjdXJyZW50R2l2ZW5OYW1lXCI6XCJOaWtvbGFvc1wiLFwiY3VycmVudEZhbWlseU5hbWVcIjpcIlRyaWFudGFmeWxsb3VcIixcImVtYWlsXCI6XCJnZTAxMTE3QHlhaG9vLmdyXCJ9Iiwib3JpZ2luIjoibGlua2VkSW4iLCJlbWFpbCI6ImdlMDExMTdAeWFob28uZ3IifQ.Bka-M3-4qe71Hk5W6tVtXL1BXWrg3qHnhlt2u3BEbDU");
    final Cookie uAegeanJwtCookie = new Cookie("access_token", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJlaWRcIjpcInRyaWFudGFmeWxsb3UubmlAYWVnZWFuLmdyXCIsXCJwZXJzb25JZGVudGlmaWVyXCI6XCJ0cmlhbnRhZnlsbG91Lm5pQGFlZ2Vhbi5nclwiLFwiY3VycmVudEZhbWlseU5hbWVcIjpcIlRyaWFudGFmeWxsb3VcIixcImN1cnJlbnRHaXZlbk5hbWVcIjpcIk5pa29zXCJ9Iiwib3JpZ2luIjoiVUFlZ2VhbiIsImVtYWlsIjoidHJpYW50YWZ5bGxvdS5uaUBhZWdlYW4uZ3IifQ.5wMnyEMT4KDNGHzDVmrnHfgTjgmIv6dAeiBoNMqaHqc");

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LinksService linkServ;

    @Autowired
    private CacheManager cacheManager;

    @MockBean
    private ConfigProperties props;

    @Configuration
    static class TestConfig {

        @Bean
        public CacheManager cacheManager() {
            return PowerMockito.mock(CacheManager.class);
        }
    }

    @Test
    public void testGetJWTByCookie() throws Exception {

        mvc.perform(get("/eidResponse")
                .cookie(uAegeanJwtCookie)
        )
                .andExpect(status().isOk())
                .andExpect(model().attribute("eid", equalTo("triantafyllou.ni@aegean.gr")));

    }
}
