package app.dao.app;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import app.model.AppInfo;

/**
 * 
 * Description: App版本管理
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
public interface AppInfoMapper {

    /**
     * 
     * Description: 列表查询App版本信息
     * 
     * @author zhang shubin
     * @data 2017年6月16日
     * @param 
     * @return
     */
    List<AppInfo> list (AppInfo appInfo);

    /**
     * 
     * Description: 根据版本号查询
     * 
     * @author zhang shubin
     * @data 2017年6月16日
     * @param 
     * @return
     */
    AppInfo selectByVersion(@Param("version")String version);
    
    /**
     * 
     * Description: 版本回退
     * 
     * @author zhang shubin
     * @data 2017年6月16日
     * @param 
     * @return
     */
    Integer fallbackByVersion();
    
    /**
     * 
     * Description: 查询上传文件的路径
     * 
     * @author zhang shubin
     * @data 2017年6月22日
     * @param 
     * @return
     */
    List<String> selectPathByBusinessId(@Param("businessId")String businessId);
    
    /**
     * 
     * Description: 查询文件存放id
     * 
     * @author zhang shubin
     * @data 2017年6月22日
     * @param 
     * @return
     */
    List<String> selectFileIdByBusinessId(@Param("businessId")String businessId);
    /**
     * 
     * Description: 新增
     * 
     * @author zhang shubin
     * @data 2017年6月22日
     * @param 
     * @return
     */
    void add(AppInfo appInfo);
}
