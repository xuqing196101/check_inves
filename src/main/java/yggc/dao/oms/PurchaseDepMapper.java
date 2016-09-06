package yggc.dao.oms;

import yggc.model.oms.PurchaseDep;

public interface PurchaseDepMapper {
    int deleteByPrimaryKey(String id);

    int insert(PurchaseDep record);

    int insertSelective(PurchaseDep record);

    PurchaseDep selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PurchaseDep record);

    int updateByPrimaryKey(PurchaseDep record);
}