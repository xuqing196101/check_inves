package app.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import app.model.AppInfo;


/**
 * 
 * Description: App版本管理Service
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
public interface AppInfoService {

    /**
     * 
     * Description: 列表查询App版本信息
     * 
     * @author zhang shubin
     * @data 2017年6月16日
     * @param 
     * @return
     */
    List<AppInfo> list (AppInfo appInfo, Integer page);

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
    Integer fallbackByVersion(String type);
    
    /**
     * 
     * Description: 查询上传文件的路径
     * 
     * @author zhang shubin
     * @data 2017年6月22日
     * @param 
     * @return
     */
    String selectPathByBusinessId(@Param("businessId")String businessId);
    
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
    
    /**
     * 
     * Description: 查询文件存放id
     * 
     * @author zhang shubin
     * @data 2017年6月22日
     * @param 
     * @return
     */
    String selectFileIdByBusinessId(@Param("businessId")String businessId);

}
