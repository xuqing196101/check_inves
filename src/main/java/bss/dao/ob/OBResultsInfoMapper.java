package bss.dao.ob;

import bss.model.ob.OBResultsInfo;
import bss.model.ob.OBResultsInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OBResultsInfoMapper {
    int countByExample(OBResultsInfoExample example);

    int deleteByExample(OBResultsInfoExample example);

    int deleteByPrimaryKey(String id);

    int insert(OBResultsInfo record);

    int insertSelective(OBResultsInfo record);

    List<OBResultsInfo> selectByExample(OBResultsInfoExample example);

    OBResultsInfo selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") OBResultsInfo record, @Param("example") OBResultsInfoExample example);

    int updateByExample(@Param("record") OBResultsInfo record, @Param("example") OBResultsInfoExample example);

    int updateByPrimaryKeySelective(OBResultsInfo record);

    int updateByPrimaryKey(OBResultsInfo record);
    /***
     * 根据竞价id 获取 数据
     * @author YangHongLiang
     */
    List<OBResultsInfo> selectByProjectId(String projectId);
}