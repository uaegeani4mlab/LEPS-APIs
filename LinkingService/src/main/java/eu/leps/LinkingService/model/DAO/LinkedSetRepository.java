/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.leps.LinkingService.model.DAO;

import eu.leps.LinkingService.model.DMO.LinkedSetDMO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author nikos
 */
public interface LinkedSetRepository extends JpaRepository<LinkedSetDMO,Long>{
    

    @Query("SELECT s FROM LinkedSetDMO s JOIN s.eids l where l.eid = :eid  ")
    public List<LinkedSetDMO> selectIfContainsEid(@Param("eid") String eid);

}
