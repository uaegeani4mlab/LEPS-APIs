/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.leps.LinkingService.model.DAO;

import eu.leps.LinkingService.model.DMO.EidDMO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author nikos
 */
public interface EidRepository extends JpaRepository<EidDMO,Long>{
    
}
