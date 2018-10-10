/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.leps.LinkingService.model.pojo;

import eu.leps.LinkingService.model.TO.EidTO;
import eu.leps.LinkingService.pojo.enums.ResponseCodes;
import java.util.List;

/**
 *
 * @author nikos
 */
public class LinkingResponse {

    private ResponseCodes code;
    private List<EidTO> result;
    private String uuid;
    private String error;

    public LinkingResponse(ResponseCodes code, List<EidTO> result, String uuid, String error) {
        this.code = code;
        this.result = result;
        this.error = error;
        this.uuid=uuid;
    }

    public ResponseCodes getCode() {
        return code;
    }

    public void setCode(ResponseCodes code) {
        this.code = code;
    }

    public List<EidTO> getResult() {
        return result;
    }

    public void setResult(List<EidTO> result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
