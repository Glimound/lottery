package com.glimound.lottery.domain.activity.service.partake;

import com.glimound.lottery.common.Constants;
import com.glimound.lottery.common.Result;
import com.glimound.lottery.domain.activity.model.req.PartakeReq;
import com.glimound.lottery.domain.activity.model.res.PartakeRes;
import com.glimound.lottery.domain.activity.model.res.StockRes;
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

        // 通过redis查询库存数量
        Integer stockCount = activityRepository.getActivityStockByRedis(req.getActivityId());
        if (stockCount == null) {
            // 若redis返回null，说明活动已过期，返回error
            return new PartakeRes(Constants.ResponseCode.ACTIVITY_NON_EXIST.getCode(), Constants.ResponseCode.ACTIVITY_NON_EXIST.getInfo());
        }
        if (stockCount <= 0) {
            // 库存已耗尽
            return new PartakeRes(Constants.ResponseCode.OUT_OF_STOCK.getCode(), Constants.ResponseCode.OUT_OF_STOCK.getInfo());
        }

        // 若库存数量充足，尝试使用redis减扣库存（若减扣失败会自动补偿）
        StockRes deductActivityRes = this.deductActivityStockByRedis(req.getUId(), req.getActivityId());
        if (!Constants.ResponseCode.SUCCESS.getCode().equals(deductActivityRes.getCode())) {
            return new PartakeRes(deductActivityRes.getCode(), deductActivityRes.getInfo());
        }

        // 查询是否存在未执行抽奖领取活动单，若获取成功则补回库存
        UserTakeActivityVO userTakeActivityVO = this.getNoConsumedTakeActivityOrder(req.getActivityId(), req.getUId());
        if (userTakeActivityVO != null) {
            this.recoverActivityStockByRedis(req.getActivityId());
            return new PartakeRes(Constants.ResponseCode.NOT_CONSUMED_TAKE.getCode(), Constants.ResponseCode.NOT_CONSUMED_TAKE.getInfo(),
                    userTakeActivityVO.getStrategyId(), userTakeActivityVO.getTakeId());
        }

        // 查询活动单
        ActivityBillVO activityBillVO = this.getActivityBill(req);

        // 活动信息校验，若不通过则补回库存
        Result checkResult = this.checkActivityBill(req, activityBillVO);
        if (!Constants.ResponseCode.SUCCESS.getCode().equals(checkResult.getCode())) {
            this.recoverActivityStockByRedis(req.getActivityId());
            return new PartakeRes(checkResult.getCode(), checkResult.getInfo());
        }

        // 插入领取活动信息
        Long takeId = idGeneratorMap.get(Constants.Ids.SnowFlake).nextId();
        Result grabResult = this.grabActivity(req, activityBillVO, takeId);
        if (!Constants.ResponseCode.SUCCESS.getCode().equals(grabResult.getCode())) {
            this.recoverActivityStockByRedis(req.getActivityId());
            return new PartakeRes(grabResult.getCode(), grabResult.getInfo());
        }

        // 封装结果
        return new PartakeRes(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo(),
                activityBillVO.getStrategyId(), takeId,  deductActivityRes.getStockSurplusCount());
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
     * 活动信息校验处理，校验状态、日期、个人参与次数
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

    /**
     * 扣减活动库存，通过Redis
     *
     * @param uId        用户ID
     * @param activityId 活动号
     * @return 扣减结果
     */
    protected abstract StockRes deductActivityStockByRedis(String uId, Long activityId);

    /**
     * 恢复活动库存，通过Redis
     */
    protected abstract void recoverActivityStockByRedis(Long activityId);

}
