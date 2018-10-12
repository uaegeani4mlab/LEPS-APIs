/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.leps.LinkingService.model.service.impl;

import eu.leps.LinkingService.model.DAO.LinkedSetRepository;
import eu.leps.LinkingService.model.DMO.EidDMO;
import eu.leps.LinkingService.model.DMO.LinkedSetDMO;
import eu.leps.LinkingService.model.TO.LinkedSetTO;
import eu.leps.LinkingService.model.service.LinksService;
import eu.leps.LinkingService.model.wrappers.WrapDMO;
import eu.leps.LinkingService.model.wrappers.WrapTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author nikos
 */
@Service
public class LinksServiceImpl implements LinksService {

    private LinkedSetRepository linksRepo;

    @Autowired
    public LinksServiceImpl(LinkedSetRepository linksRepo) {
        this.linksRepo = linksRepo;
    }

    @Override
    @Transactional
    public void addEidToLinkById(String eid, String source, Long linkId) {
        Optional<LinkedSetDMO> link = linksRepo.findById(linkId);
        if (link.isPresent()) {
            List<String> eids = link.get().getEids().stream().map(eidDmo -> {return eidDmo.getEid();}).collect(Collectors.toList());
            if (!eids.contains(eid)) {
                EidDMO newEid = new EidDMO(link.get().getId(), eid, source);
                link.get().getEids().add(newEid);
                linksRepo.save(link.get());
            }

        } else {
            throw new IndexOutOfBoundsException("Not result for given linkID");
        }
    }

    @Override
    @Transactional
    //TODO
    public void addEidToLinkByObj(String eid, String source, LinkedSetTO set) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Transactional
    public LinkedSetTO findByEid(String eid) throws IndexOutOfBoundsException {
        List<LinkedSetDMO> links = linksRepo.selectIfContainsEid(eid);
        if (links != null && links.size() > 0) {
            if (links.size() == 1) {
                return WrapTO.wrapLinkSet(links.get(0));
            }
            throw new IndexOutOfBoundsException("More than 1 matching eids found!!");
        }
        LinkedSetTO empty = new LinkedSetTO(null, new ArrayList(), null);
        return empty;
    }

    @Override
    @Transactional
    public Long saveLinkSet(LinkedSetTO link) {
        LinkedSetDMO set = WrapDMO.wrapLinkSet(link);
        linksRepo.save(set);
        return set.getId();
    }

    @Override
    @Transactional
    public LinkedSetTO findById(Long id) {
        Optional<LinkedSetDMO> set = linksRepo.findById(id);
        if(set.isPresent()){
            return WrapTO.wrapLinkSet(set.get());
        }else{
            return new LinkedSetTO(null, new ArrayList(), null);
        }
    }

}
