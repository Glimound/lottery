package com.glimound.lottery.domain.strategy.service.draw;

import com.glimound.lottery.common.Constants;
import com.glimound.lottery.domain.strategy.model.aggregate.StrategyRich;
import com.glimound.lottery.domain.strategy.model.req.DrawReq;
import com.glimound.lottery.domain.strategy.model.res.DrawRes;
import com.glimound.lottery.domain.strategy.model.vo.AwardRateInfo;
import com.glimound.lottery.domain.strategy.model.vo.DrawAwardInfo;
import com.glimound.lottery.domain.strategy.service.algorithm.IDrawAlgorithm;
import com.glimound.lottery.infrastructure.po.Award;
import com.glimound.lottery.infrastructure.po.Strategy;
import com.glimound.lottery.infrastructure.po.StrategyDetail;
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
        Strategy strategy = strategyRich.getStrategy();

        // 2. 校验和初始化数据
        List<StrategyDetail> strategyDetailList = strategyRich.getStrategyDetailList();
        this.checkAndInitRateData(strategy.getStrategyId(), strategy.getStrategyMode(), strategyDetailList);

        // 3. 获取不在抽奖范围内的列表，包括：奖品库存为空、风控策略、临时调整等
        List<Long> excludeAwardIds = this.getExcludeAwardIds(req.getStrategyId());

        // 4. 获取抽奖策略并执行抽奖
        IDrawAlgorithm drawAlgorithm = drawAlgorithmGroup.get(strategy.getStrategyMode());
        Long awardId = this.drawAlgorithm(strategy.getStrategyId(), drawAlgorithm, excludeAwardIds);

        // 5. 包装中奖结果
        return this.buildDrawResult(req.getUId(), strategyRich.getStrategyId(), awardId);
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
    public void checkAndInitRateData(Long strategyId, Integer strategyMode, List<StrategyDetail> strategyDetailList) {
        IDrawAlgorithm drawAlgorithm = drawAlgorithmGroup.get(strategyMode);

        // 若非单项概率算法，则无需初始化rateTuple，仅需初始化awardRateInfoMap即可
        if (!Constants.StrategyMode.SINGLE.getCode().equals(strategyMode)) {
            List<AwardRateInfo> awardRateInfoList = new ArrayList<>(strategyDetailList.size());
            for (StrategyDetail strategyDetail : strategyDetailList) {
                awardRateInfoList.add(new AwardRateInfo(strategyDetail.getAwardId(), strategyDetail.getAwardRate()));
            }
            drawAlgorithm.initAwardRateInfoMap(strategyId, awardRateInfoList);
            return;
        }

        // 若rateTuple已初始化过，则return
        if (drawAlgorithm.isExistRateTuple(strategyId)) {
            return;
        }

        List<AwardRateInfo> awardRateInfoList = new ArrayList<>(strategyDetailList.size());
        for (StrategyDetail strategyDetail : strategyDetailList) {
            awardRateInfoList.add(new AwardRateInfo(strategyDetail.getAwardId(), strategyDetail.getAwardRate()));
        }

        drawAlgorithm.initRateTuple(strategyId, awardRateInfoList);
    }

    /**
     * 包装抽奖结果
     *
     * @param uId        用户ID
     * @param strategyId 策略ID
     * @param awardId    奖品ID，null 情况：并发抽奖情况下，库存临界值1 -> 0，会有用户中奖结果为 null
     * @return 中奖结果
     */
    private DrawRes buildDrawResult(String uId, Long strategyId, Long awardId) {
        if (awardId == null) {
            log.info("执行策略抽奖完成【未中奖】，用户：{} 策略ID：{}", uId, strategyId);
            return new DrawRes(uId, strategyId, Constants.DrawState.FAIL.getCode());
        }

        Award award = super.getAwardById(awardId);
        DrawAwardInfo drawAwardInfo = new DrawAwardInfo(award.getAwardId(), award.getAwardName());
        log.info("执行策略抽奖完成【已中奖】，用户：{} 策略ID：{} 奖品ID：{} 奖品名称：{}", uId, strategyId, awardId, award.getAwardName());

        return new DrawRes(uId, strategyId, Constants.DrawState.SUCCESS.getCode(), drawAwardInfo);
    }
}
