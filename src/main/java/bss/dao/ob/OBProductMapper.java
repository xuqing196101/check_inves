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
	
	
	/**
	 * 
	 * Description: 修改产品发布状态
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月23日 
	 * @param  @param id 
	 * @return void 
	 * @exception
	 */
	void fab(String id);
	
	/**
	 * 
	 * Description: 撤回发布
	 * 
	 * @author  zhang shubin
	 * @version  2017年4月1日 
	 * @param  @param id 
	 * @return void 
	 * @exception
	 */
	void chfab(String id);
	
	/**
	 * 
	 * Description: 查询所有的末节点
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月29日 
	 * @param  @param name
	 * @param  @return 
	 * @return List<OBProduct> 
	 * @exception
	 */
	List<OBProduct> selectAllAmallPointsId(@Param("name")String name);
	
	/**
	 * 
	 * Description: 根据目录获取采购机构
	 * 
	 * @author  zhang shubin
	 * @version  2017年4月2日 
	 * @param  @param smallPointsId
	 * @param  @return 
	 * @return String 
	 * @exception
	 */
	String selOrgByCategory(@Param("smallPointsId") String smallPointsId,@Param("id") String id);
    
}