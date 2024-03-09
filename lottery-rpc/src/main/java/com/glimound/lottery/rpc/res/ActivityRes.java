package com.glimound.lottery.rpc.res;

import com.glimound.lottery.common.Result;
import com.glimound.lottery.rpc.dto.ActivityDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Glimound
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ActivityRes implements Serializable {
    private Result result;
    private ActivityDto activity;
}
