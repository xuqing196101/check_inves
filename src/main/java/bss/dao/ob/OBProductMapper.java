package bss.dao.ob;

import bss.model.ob.OBProduct;
import bss.model.ob.OBProductExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OBProductMapper {
    int countByExample(OBProductExample example);

    void deleteByPrimaryKey(@Param("id")String id);

    int insert(OBProduct record);

    int insertSelective(OBProduct record);

    List<OBProduct> selectByExample(OBProduct example);

    OBProduct selectByPrimaryKey(String id);
    
    OBProduct selectSignalByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") OBProduct record, @Param("example") OBProductExample example);

    int updateByExample(@Param("record") OBProduct record, @Param("example") OBProductExample example);

    int updateByPrimaryKeySelective(OBProduct record);

    int updateByPrimaryKey(OBProduct record);
    /**
     * 获取全部 可以用的产品相关信息
     * @author YangHongLiang
     * */
    List<OBProduct> selectList();
    
    /**
	 * 
	 * Description: 验证产品代码唯一
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月15日 
	 * @param  @param code
	 * @param  @return 
	 * @return int 
	 * @exception
	 */
	int yzProductCode(@Param("code") String code,@Param("id") String id);
	
	/**
	 * 
	 * Description: 验证产品名称唯一
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月15日 
	 * @param  @param name
	 * @param  @return 
	 * @return int 
	 * @exception
	 */
	int yzProductName(@Param("name") String name,@Param("id") String id);
	
	/**
	 * 
	 * Description: 验证采购机构是否存在
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月16日 
	 * @param  @param shortName
	 * @param  @return 
	 * @return int 
	 * @exception
	 */
	int yzorg(String shortName);
	/**
	 * 根据id集合 获取相关的 产品 信息
	 * @author YangHongLiang
	 * @param ids
	 * @return
	 */
	List<OBProduct> selectInId(@Param("list")List<String> ids);
    
}