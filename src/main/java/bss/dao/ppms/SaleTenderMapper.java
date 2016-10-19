package bss.dao.ppms;

import bss.model.ppms.SaleTender;

public interface SaleTenderMapper {
    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(SaleTender record);

    /**
     *
     * @param record
     */
    int insertSelective(SaleTender record);
}