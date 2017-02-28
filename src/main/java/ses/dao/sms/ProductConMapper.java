package ses.dao.sms;

import ses.model.sms.ProductCon;

public interface ProductConMapper {
	
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
    int insert(ProductCon record);
    /**
    *
    * @param record
    */
   int insertSelective(ProductCon record);
   /**
    * 根据主键获取一条数据库记录
    *
    * @param id
    */
   ProductCon selectByPrimaryKey(String id);
   /**
   *
   * @param record
   */
  int updateByPrimaryKeySelective(ProductCon record);
  /**
   * 根据主键来更新数据库记录
   *
   * @param record
   */
  int updateByPrimaryKey(ProductCon record);

}
