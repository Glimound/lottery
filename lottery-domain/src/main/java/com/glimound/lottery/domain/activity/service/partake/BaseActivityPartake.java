package com.glimound.lottery.domain.activity.service.partake;

import com.glimound.lottery.common.Constants;
import com.glimound.lottery.common.Result;
import com.glimound.lottery.domain.activity.model.req.PartakeReq;
import com.glimound.lottery.domain.activity.model.res.PartakeRes;
import com.glimound.lottery.domain.activity.model.vo.ActivityBillVO;
import com.glimound.lottery.domain.activity.model.vo.UserTakeActivityVO;
import com.glimound.lottery.domain.support.ids.IIdGenerator;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 活动领取模板抽象类
 * @author Glimound
 */
public abstract class BaseActivityPartake extends ActivityPartakeSupport implements IActivityPartake {

    @Resource
    private Map<Constants.Ids, IIdGenerator> idGeneratorMap;

    @Override
    public PartakeRes doPartake(PartakeReq req) {

        // 查询是否存在未执行抽奖领取活动单【user_take_activity 存在 state = 0，领取了但抽奖过程失败的，可以直接返回领取结果继续抽奖】
        UserTakeActivityVO userTakeActivityVO = this.getNoConsumedTakeActivityOrder(req.getActivityId(), req.getUId());
        if (null != userTakeActivityVO) {
            return new PartakeRes(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo(),
                    userTakeActivityVO.getStrategyId(), userTakeActivityVO.getTakeId());
        }

        // 查询活动账单
        ActivityBillVO activityBillVO = super.getActivityBill(req);

        // 活动信息校验处理【活动库存、状态、日期、个人参与次数】
        Result checkResult = this.checkActivityBill(req, activityBillVO);
        if (!Constants.ResponseCode.SUCCESS.getCode().equals(checkResult.getCode())) {
            return new PartakeRes(checkResult.getCode(), checkResult.getInfo());
        }

        // 扣减活动库存【目前为直接对配置库中的 lottery.activity 直接操作表扣减库存，后续优化为Redis扣减】
        Result deductActivityResult = this.deductActivityStock(req);
        if (!Constants.ResponseCode.SUCCESS.getCode().equals(deductActivityResult.getCode())) {
            return new PartakeRes(deductActivityResult.getCode(), deductActivityResult.getInfo());
        }

        // 插入领取活动信息【个人用户把活动信息写入到用户表】
        Long takeId = idGeneratorMap.get(Constants.Ids.SnowFlake).nextId();
        Result grabResult = this.grabActivity(req, activityBillVO, takeId);
        if (!Constants.ResponseCode.SUCCESS.getCode().equals(grabResult.getCode())) {
            return new PartakeRes(grabResult.getCode(), grabResult.getInfo());
        }

        // 封装结果
        return new PartakeRes(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo(),
                activityBillVO.getStrategyId(), takeId);
    }

    /**
     * 查询是否存在未执行抽奖领取活动单【user_take_activity 存在 state = 0，领取了但抽奖过程失败的，可以直接返回领取结果继续抽奖】
     *
     * @param activityId 活动ID
     * @param uId        用户ID
     * @return 领取单
     */
    protected abstract UserTakeActivityVO getNoConsumedTakeActivityOrder(Long activityId, String uId);

    /**
     * 活动信息校验处理，把活动库存、状态、日期、个人参与次数
     *
     * @param partake 参与活动请求
     * @param bill    活动账单
     * @return 校验结果
     */
    protected abstract Result checkActivityBill(PartakeReq partake, ActivityBillVO bill);

    /**
     * 扣减活动库存
     *
     * @param req 参与活动请求
     * @return 扣减结果
     */
    protected abstract Result deductActivityStock(PartakeReq req);

    /**
     * 领取活动
     *
     * @param partake 参与活动请求
     * @param bill    活动账单
     * @param takeId  领取活动ID
     * @return 领取结果
     */
    protected abstract Result grabActivity(PartakeReq partake, ActivityBillVO bill, Long takeId);
}
