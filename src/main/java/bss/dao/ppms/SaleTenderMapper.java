package bss.dao.ppms;

import java.util.List;

import bss.model.ppms.SaleTender;

public interface SaleTenderMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param id
     */
    int deleteByPrimaryKey(String id);

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

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SaleTender selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SaleTender record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SaleTender record);
    
    
    List<SaleTender> list(SaleTender record);
}