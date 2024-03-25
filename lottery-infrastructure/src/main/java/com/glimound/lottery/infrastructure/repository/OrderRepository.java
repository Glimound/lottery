package com.glimound.lottery.infrastructure.repository;

import com.glimound.lottery.domain.award.repository.IOrderRepository;
import com.glimound.lottery.infrastructure.dao.IUserStrategyExportDao;
import com.glimound.lottery.infrastructure.po.UserStrategyExport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author Glimound
 */
@Repository
public class OrderRepository implements IOrderRepository {

    @Resource
    private IUserStrategyExportDao userStrategyExportDao;

    @Override
    public void updateAwardGrantState(String uId, Long orderId, Long awardId, Integer grantState) {
        UserStrategyExport userStrategyExport = new UserStrategyExport();
        userStrategyExport.setUId(uId);
        userStrategyExport.setOrderId(orderId);
        userStrategyExport.setAwardId(awardId);
        userStrategyExport.setGrantState(grantState);
        userStrategyExportDao.updateAwardGrantState(userStrategyExport);
    }
}
