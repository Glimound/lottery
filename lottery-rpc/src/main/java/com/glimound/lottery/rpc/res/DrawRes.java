package com.glimound.lottery.rpc.res;

import com.glimound.lottery.common.Result;
import com.glimound.lottery.rpc.dto.AwardDTO;

import java.io.Serializable;

public class DrawRes extends Result implements Serializable {

    private AwardDTO awardDTO;

    public DrawRes(String code, String info) {
        super(code, info);
    }

    public AwardDTO getAwardDTO() {
        return awardDTO;
    }

    public void setAwardDTO(AwardDTO awardDTO) {
        this.awardDTO = awardDTO;
    }
}
