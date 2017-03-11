package bss.dao.ob;

import bss.model.ob.OBProductInfo;
import bss.model.ob.OBProductInfoExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface OBProductInfoMapper {
    int countByExample(OBProductInfoExample example);

    int deleteByExample(OBProductInfoExample example);

    int deleteByPrimaryKey(String id);

    int insert(OBProductInfo record);

    int insertSelective(OBProductInfo record);

    List<OBProductInfo> selectByExample(OBProductInfoExample example);

    OBProductInfo selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") OBProductInfo record, @Param("example") OBProductInfoExample example);

    int updateByExample(@Param("record") OBProductInfo record, @Param("example") OBProductInfoExample example);

    int updateByPrimaryKeySelective(OBProductInfo record);

    int updateByPrimaryKey(OBProductInfo record);
    /**
     * 获取 产品相关数据
     * @author YangHongLiang
     */
    List<OBProductInfo> selectByCreaterId(Map<String, Object> map);
    /**
     * 根据竞价id 获取数据
     */
    List<OBProductInfo> selectByProjectId(String id);
    
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