package bss.dao.ob;

import bss.model.ob.OBProductInfo;
import bss.model.ob.OBProductInfoExample;
import bss.model.ob.OBResultsInfo;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface OBProductInfoMapper {
    int countByExample(OBProductInfoExample example);

    int deleteByExample(OBProductInfoExample example);

    int deleteByPrimaryKey(String id);

    int insert(OBProductInfo record);

    int insertSelective(OBProductInfo record);
    /**
     * 根据id 获区 数据数量
     * @param id
     * @return
     */
    Integer countById(@Param("id")String id);
    List<OBProductInfo> selectByExample(OBProductInfoExample example);

    OBProductInfo selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") OBProductInfo record, @Param("example") OBProductInfoExample example);

    int updateByExample(@Param("record") OBProductInfo record, @Param("example") OBProductInfoExample example);

    int updateByPrimaryKeySelective(OBProductInfo record);

    int updateByPrimaryKey(OBProductInfo record);
    /**
     * 根据竞价id 获取数据
     */
    List<OBProductInfo> selectByProjectId(String id);
    /**
     * 获取产品 名称
     * @author Yanghongliang
     * @param projectId
     * @return
     */
    List<OBProductInfo> getProductName(@Param("id")String projectId);
    
    /**
     * 
     * Description: 查询产品数量总和
     * 
     * @author  zhang shubin
     * @version  2017年3月11日 
     * @param  @param id
     * @param  @return 
     * @return int 
     * @exception
     */
    int selectCount(String id);
}