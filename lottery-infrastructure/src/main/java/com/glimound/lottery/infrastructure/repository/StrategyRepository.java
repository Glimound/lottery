package com.glimound.lottery.infrastructure.repository;

import com.glimound.lottery.domain.strategy.model.aggregate.StrategyRich;
import com.glimound.lottery.domain.strategy.model.vo.AwardBriefVO;
import com.glimound.lottery.domain.strategy.model.vo.StrategyBriefVO;
import com.glimound.lottery.domain.strategy.model.vo.StrategyDetailBriefVO;
import com.glimound.lottery.domain.strategy.repository.IStrategyRepository;
import com.glimound.lottery.infrastructure.dao.IAwardDao;
import com.glimound.lottery.infrastructure.dao.IStrategyDao;
import com.glimound.lottery.infrastructure.dao.IStrategyDetailDao;
import com.glimound.lottery.infrastructure.po.Award;
import com.glimound.lottery.infrastructure.po.Strategy;
import com.glimound.lottery.infrastructure.po.StrategyDetail;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Glimound
 */
@Repository
public class StrategyRepository implements IStrategyRepository {

    @Resource
    private IStrategyDao strategyDao;

    @Resource
    private IStrategyDetailDao strategyDetailDao;

    @Resource
    private IAwardDao awardDao;

    @Override
    public StrategyRich getStrategyRichById(Long strategyId) {
        // 此处使用多次查询组装，而非join
        Strategy strategy = strategyDao.getStrategyById(strategyId);
        List<StrategyDetail> strategyDetailList = strategyDetailDao.listStrategyDetailById(strategyId);

        StrategyBriefVO strategyBriefVO = new StrategyBriefVO();
        BeanUtils.copyProperties(strategy, strategyBriefVO);

        List<StrategyDetailBriefVO> strategyDetailBriefVOList = new ArrayList<>();
        for (StrategyDetail strategyDetail : strategyDetailList) {
            StrategyDetailBriefVO strategyDetailBriefVO = new StrategyDetailBriefVO();
            BeanUtils.copyProperties(strategyDetail, strategyDetailBriefVO);
            strategyDetailBriefVOList.add(strategyDetailBriefVO);
        }

        return new StrategyRich(strategyId, strategyBriefVO, strategyDetailBriefVOList);
    }

    @Override
    public AwardBriefVO getAwardById(Long awardId) {
        Award award = awardDao.getAwardById(awardId);
        AwardBriefVO awardBriefVO = new AwardBriefVO();
        BeanUtils.copyProperties(award, awardBriefVO);
        return awardBriefVO;
    }

    @Override
    public List<Long> listNoStockStrategyAwardById(Long strategyId) {
        return strategyDetailDao.listNoStockStrategyAwardById(strategyId);
    }

    @Override
    public boolean deductStockById(Long strategyId, Long awardId) {
        StrategyDetail req = new StrategyDetail();
        req.setStrategyId(strategyId);
        req.setAwardId(awardId);
        int count = strategyDetailDao.deductStockById(req);
        return count == 1;
    }
}
