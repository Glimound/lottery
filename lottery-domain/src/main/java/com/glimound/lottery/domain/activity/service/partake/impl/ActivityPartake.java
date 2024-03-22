package com.glimound.lottery.domain.activity.service.partake.impl;

import com.glimound.db.router.strategy.IDBRouterStrategy;
import com.glimound.lottery.common.Constants;
import com.glimound.lottery.common.Result;
import com.glimound.lottery.domain.activity.model.req.PartakeReq;
import com.glimound.lottery.domain.activity.model.vo.ActivityBillVO;
import com.glimound.lottery.domain.activity.repository.IUserTakeActivityRepository;
import com.glimound.lottery.domain.activity.service.partake.BaseActivityPartake;
import com.glimound.lottery.domain.support.ids.IIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 活动参与功能实现
 * @author Glimound
 */
@Service
@Slf4j
public class ActivityPartake extends BaseActivityPartake {

    @Resource
    private IUserTakeActivityRepository userTakeActivityRepository;
    @Resource
    private Map<Constants.Ids, IIdGenerator> idGeneratorMap;
    @Resource
    private TransactionTemplate transactionTemplate;
    @Resource
    private IDBRouterStrategy dbRouter;

    @Override
    protected Result checkActivityBill(PartakeReq partake, ActivityBillVO bill) {
        // 校验活动状态
        if (!Constants.ActivityState.DOING.getCode().equals(bill.getState())) {
            log.warn("活动当前状态非可用 state：{}", bill.getState());
            return Result.buildResult(Constants.ResponseCode.UNKNOWN_ERROR, "活动当前状态非可用");
        }

        // 校验活动日期
        if (bill.getBeginDateTime().after(partake.getPartakeDate()) || bill.getEndDateTime().before(partake.getPartakeDate())) {
            log.warn("活动时间范围非可用 beginDateTime：{} endDateTime：{}", bill.getBeginDateTime(), bill.getEndDateTime());
            return Result.buildResult(Constants.ResponseCode.UNKNOWN_ERROR, "活动时间范围非可用");
        }

        // 校验活动库存
        if (bill.getStockSurplusCount() <= 0) {
            log.warn("活动剩余库存非可用 stockSurplusCount：{}", bill.getStockSurplusCount());
            return Result.buildResult(Constants.ResponseCode.UNKNOWN_ERROR, "活动剩余库存非可用");
        }

        // 校验个人库存 - 个人活动剩余可领取次数
        if (bill.getUserTakeLeftCount() != null) {
            if (bill.getUserTakeLeftCount() <= 0) {
                log.warn("个人领取次数非可用 userTakeLeftCount{}:", bill.getUserTakeLeftCount());
                return Result.buildErrorResult("个人领取次数非可用");
            }
        }

        return Result.buildSuccessResult();
    }

    @Override
    protected Result deductActivityStock(PartakeReq req) {
        int count = activityRepository.deductActivityStockById(req.getActivityId());
        if (0 == count) {
            log.error("扣减活动库存失败 activityId：{}", req.getActivityId());
            return Result.buildResult(Constants.ResponseCode.NO_UPDATE);
        }
        return Result.buildSuccessResult();
    }

    @Override
    protected Result grabActivity(PartakeReq partake, ActivityBillVO bill) {
        try {
            // 此处使用编程式事务而非@Transaction注解，主要是因为声明式事务在开启时（比doRouter早）便会获取数据源，
            // 导致默认数据源被缓存至ThreadLocal中，使得后续执行时会直接获取该默认数据源，而非动态数据源
            dbRouter.doRouter(partake.getUId());
            return transactionTemplate.execute(status -> {
                try {
                    // 扣减个人已参与次数
                    int updateCount = userTakeActivityRepository.deductLeftCount(bill.getActivityId(), bill.getActivityName(),
                            bill.getTakeCount(), bill.getUserTakeLeftCount(), partake.getUId(), partake.getPartakeDate());
                    if (updateCount == 0) {
                        status.setRollbackOnly();
                        log.error("领取活动，扣减个人已参与次数失败 activityId：{} uId：{}", partake.getActivityId(), partake.getUId());
                        return Result.buildResult(Constants.ResponseCode.NO_UPDATE);
                    }

                    // 插入领取活动信息
                    Long takeId = idGeneratorMap.get(Constants.Ids.SnowFlake).nextId();
                    userTakeActivityRepository.takeActivity(bill.getActivityId(), bill.getActivityName(), bill.getTakeCount(),
                            bill.getUserTakeLeftCount(), partake.getUId(), partake.getPartakeDate(), takeId);
                } catch (DuplicateKeyException e) {
                    status.setRollbackOnly();
                    log.error("领取活动，唯一索引冲突 activityId：{} uId：{}", partake.getActivityId(), partake.getUId(), e);
                    return Result.buildResult(Constants.ResponseCode.INDEX_DUPLICATE);
                }
                return Result.buildSuccessResult();
            });
        } finally {
            dbRouter.clear();
        }
    }
}
