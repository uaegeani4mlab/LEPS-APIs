/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.leps.LinkingService.model.TO;

import java.util.List;
import java.util.Optional;
import org.springframework.util.StringUtils;

/**
 *
 * @author nikos
 */
public class LinkedSetTO {

    private Optional<String> sessionId;
    private List<EidTO> linked;
    private Long id;

    public LinkedSetTO(String sessionId, List<EidTO> linked, Long id) {
        if (StringUtils.isEmpty(sessionId)) {
            this.sessionId = Optional.empty();
        } else {
            this.sessionId = Optional.of(sessionId);
        }
        this.linked = linked;
        this.id=id;
    }
    
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Optional<String> getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = Optional.of(sessionId);
    }

    public List<EidTO> getLinked() {
        return linked;
    }

    public void setLinked(List<EidTO> linked) {
        this.linked = linked;
    }

}
