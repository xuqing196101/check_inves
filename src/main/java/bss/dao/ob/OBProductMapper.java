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
    
}