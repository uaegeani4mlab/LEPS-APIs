/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.leps.LinkingService.controllers;

import eu.leps.LinkingService.model.TO.EidTO;
import eu.leps.LinkingService.model.TO.LinkedSetTO;
import eu.leps.LinkingService.model.pojo.LinkingResponse;
import eu.leps.LinkingService.model.service.LinksService;
import eu.leps.LinkingService.pojo.enums.ResponseCodes;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author nikos
 */
@RestController
public class APIControllers {

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private LinksService linkServ;

    private final static Logger LOG = LoggerFactory.getLogger(APIControllers.class);

    private final static String SESSION_NOT_FOUND = "session not found";

    @RequestMapping(value = "/match", method = RequestMethod.GET)
    @ApiOperation(value = "Checks for an existing eid linking match, and if found it returns it", response = LinkingResponse.class)
    public LinkingResponse getMatch(String eIdentifier) {
        LinkedSetTO match = linkServ.findByEid(eIdentifier);
        if (match.getId() != null) {
            return new LinkingResponse(ResponseCodes.OK, match.getLinked(), null, null);
        }
        return new LinkingResponse(ResponseCodes.ERROR, null, null, "no match found");
    }

    @RequestMapping(value = "/link", method = RequestMethod.POST)
    @ApiOperation(value = "Accepts an eID. If there exists a pending session, then this is linked to the other sessions eids. "
            + "Else a new session is generated", response = LinkingResponse.class)
    public LinkingResponse link(String eid, String source, @RequestHeader(value = "sesionId", required = false) String sessionIdentifier) {

        if (sessionIdentifier != null) {
            ValueWrapper existingLinksWrapper = cacheManager.getCache("sessions").get(sessionIdentifier);
            if (existingLinksWrapper != null && existingLinksWrapper.get() != null) {
                LinkedSetTO cachedSet = (LinkedSetTO) existingLinksWrapper.get();
                if (cachedSet != null) {
                    ResponseCodes response = linkServ.addEidToLinkById(eid, source, cachedSet.getId());
                    cacheManager.getCache("sessions").put(sessionIdentifier, cachedSet);
                    return new LinkingResponse(response, null, null, null);
                }
            }

            return new LinkingResponse(ResponseCodes.ERROR, null, null, SESSION_NOT_FOUND);

        } else {
            UUID uuid = UUID.randomUUID();
            EidTO link = new EidTO(eid, source);
            ArrayList<EidTO> links = new ArrayList();
            links.add(link);
            LinkedSetTO linkSet = new LinkedSetTO(uuid.toString(), links, null);

            linkSet.setId(linkServ.saveLinkSet(linkSet));
            cacheManager.getCache("sessions").put(uuid.toString(), linkSet);
            return new LinkingResponse(ResponseCodes.NEW, null, uuid.toString(), null);

        }
    }

}
