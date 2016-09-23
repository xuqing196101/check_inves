package bss.dao.pms;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import bss.model.pms.UpdateFiled;

public interface UpdateFiledMapper {
    int deleteByPrimaryKey(String id);

    int insert(UpdateFiled record);

    int insertSelective(UpdateFiled record);

    UpdateFiled selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UpdateFiled record);

    int updateByPrimaryKey(UpdateFiled record);
    
    List<UpdateFiled> getAll();
    /**
     * 
    * @Title: update
    * @Description: 根据字段名修改 
    * author: Li Xiaoxiao 
    * @param @param ipdateFiled     
    * @return void     
    * @throws
     */
    void update(@Param("isUpdate")Integer isUpdate,@Param("list")List<String> list);
}