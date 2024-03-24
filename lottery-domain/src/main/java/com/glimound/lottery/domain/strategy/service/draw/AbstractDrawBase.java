package com.glimound.lottery.domain.strategy.service.draw;

import com.glimound.lottery.common.Constants;
import com.glimound.lottery.domain.strategy.model.aggregate.StrategyRich;
import com.glimound.lottery.domain.strategy.model.req.DrawReq;
import com.glimound.lottery.domain.strategy.model.res.DrawRes;
import com.glimound.lottery.domain.strategy.model.vo.*;
import com.glimound.lottery.domain.strategy.service.algorithm.IDrawAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Glimound
 */
@Slf4j
public abstract class AbstractDrawBase extends DrawStrategySupport implements IDrawExec {

    @Override
    public DrawRes doDrawExec(DrawReq req) {

        // 1. 获取抽奖策略
        StrategyRich strategyRich = super.getStrategyRichById(req.getStrategyId());
        StrategyBriefVO strategy = strategyRich.getStrategy();

        // 2. 校验和初始化数据
        List<StrategyDetailBriefVO> strategyDetailList = strategyRich.getStrategyDetailList();
        this.checkAndInitRateData(strategy.getStrategyId(), strategy.getStrategyMode(), strategyDetailList);

        // 3. 获取不在抽奖范围内的列表，包括：奖品库存为空、风控策略、临时调整等
        List<Long> excludeAwardIds = this.getExcludeAwardIds(req.getStrategyId());

        // 4. 获取抽奖策略并执行抽奖
        IDrawAlgorithm drawAlgorithm = drawAlgorithmGroup.get(strategy.getStrategyMode());
        Long awardId = this.drawAlgorithm(strategy.getStrategyId(), drawAlgorithm, excludeAwardIds);

        // 5. 包装中奖结果
        return this.buildDrawResult(req.getUId(), strategyRich.getStrategyId(), awardId, strategy);
    }

    /**
     * 获取不在抽奖范围内的列表，包括：奖品库存为空、风控策略、临时调整等，这类数据含有业务逻辑，故需要由具体的实现方决定
     *
     * @param strategyId 策略ID
     * @return 排除的奖品ID集合
     */
    protected abstract List<Long> getExcludeAwardIds(Long strategyId);

    /**
     * 执行抽奖算法，也由具体的实现方决定
     *
     * @param strategyId      策略ID
     * @param drawAlgorithm   抽奖算法
     * @param excludeAwardIds 排除的抽奖ID集合
     * @return 中奖奖品ID
     */
    protected abstract Long drawAlgorithm(Long strategyId, IDrawAlgorithm drawAlgorithm, List<Long> excludeAwardIds);

    /**
     * 根据
     * @param strategyId 抽奖策略ID
     * @param strategyMode 抽奖策略模式
     * @param strategyDetailList 抽奖策略详情
     */
    public void checkAndInitRateData(Long strategyId, Integer strategyMode, List<StrategyDetailBriefVO> strategyDetailList) {
        IDrawAlgorithm drawAlgorithm = drawAlgorithmGroup.get(strategyMode);

        // 初始化AwardRateInfoMap
        List<AwardRateVO> awardRateInfoList = new ArrayList<>(strategyDetailList.size());
        for (StrategyDetailBriefVO strategyDetail : strategyDetailList) {
            awardRateInfoList.add(new AwardRateVO(strategyDetail.getAwardId(), strategyDetail.getAwardRate()));
        }
        drawAlgorithm.initAwardRateInfoMap(strategyId, awardRateInfoList);

        drawAlgorithm.initializeIfAbsent(strategyId, awardRateInfoList);
    }

    /**
     * 包装抽奖结果
     *
     * @param uId        用户ID
     * @param strategyId 策略ID
     * @param awardId    奖品ID，null 情况：并发抽奖情况下，库存临界值1 -> 0，会有用户中奖结果为 null
     * @return 中奖结果
     */
    private DrawRes buildDrawResult(String uId, Long strategyId, Long awardId, StrategyBriefVO strategy) {
        if (awardId == null) {
            log.info("执行策略抽奖完成【未中奖】，用户：{} 策略ID：{}", uId, strategyId);
            return new DrawRes(uId, strategyId, Constants.DrawState.FAIL.getCode());
        }

        AwardBriefVO award = super.getAwardById(awardId);
        DrawAwardVO drawAwardInfo = new DrawAwardVO(uId, award.getAwardId(), award.getAwardName(), award.getAwardType(),
                award.getAwardContent(), strategy.getStrategyMode(), strategy.getGrantType(), strategy.getGrantDate());
        log.info("执行策略抽奖完成【已中奖】，用户：{} 策略ID：{} 奖品ID：{} 奖品名称：{}", uId, strategyId, awardId, award.getAwardName());

        return new DrawRes(uId, strategyId, Constants.DrawState.SUCCESS.getCode(), drawAwardInfo);
    }
}
