/**
 * 
 */
package bss.service.ppms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bss.model.ppms.Packages;

/**
 * @Title:PackageService
 * @Description: 
 * @author ZhaoBo
 * @date 2016-10-9下午2:14:16
 */
public interface PackageService {
    /**
     * 
     * @Title: insertSelective
     * @author ZhaoBo
     * @date 2016-10-9 下午2:13:01  
     * @Description: 新增分包 
     * @param @param pack
     * @param @return      
     * @return int
     */
    int insertSelective(Packages pack);

    /**
     *〈简述〉专家评审页面模糊查询
     *〈详细描述〉
     * @author WangHuijie
     * @param map
     * @return
     */
    List<Packages> selectPackageById(HashMap<String,Object> map);
    
    /**
     * 
     * @Title: updateByPrimaryKeySelective
     * @author ZhaoBo
     * @date 2016-10-9 下午2:13:37  
     * @Description: 更新分包 
     * @param @param pack
     * @param @return      
     * @return int
     */
    int updateByPrimaryKeySelective(Packages pack);

    /**
     * 
     * @Title: findPackageById
     * @author ZhaoBo
     * @date 2016-10-9 下午2:25:15  
     * @Description: 根据项目Id查找包名 
     * @param @param map
     * @param @return      
     * @return List<Package>
     */
    List<Packages> findPackageById(HashMap<String,Object> map);

    /**
     * 
     * @Title: deleteByPrimaryKey
     * @author ZhaoBo
     * @date 2016-10-18 下午3:04:26  
     * @Description: 根据Id删除 
     * @param @param id
     * @param @return      
     * @return int
     */
    int deleteByPrimaryKey(String id);

    /**
     * 
     * @Title: selectAllByIsWon
     * @author ZhaoBo
     * @date 2016-10-18 下午3:04:26  
     * @Description: 按项目条件查询 
     * @param @param id
     * @param @return      
     * @return int
     */
    List<Packages> selectAllByIsWon(Map<String, Object> map);
    /**
     * 
     * @Title: findPackageById
     * @author: Tian Kunfeng
     * @date: 2016-11-1 上午10:59:17
     * @Description: TODO
     * @param: @param map
     * @param: @return
     * @return: List<Packages>
     */
    List<Packages> findPackageAndBidMethodById(HashMap<String,Object> map);

    /**
     * 
     *〈简述〉根据包返回抽取专家
     *〈详细描述〉
     * @author Wang Wenshuai
     * @return
     */
    List<Packages> listResultExpert(String projectId);
    
    /**
     * 
     *〈简述〉根据包返回抽取+临时专家
     *〈详细描述〉
     * @author Wang Wenshuai
     * @return
     */
    List<Packages> listResultAllExpert(String projectId);

    /**
     * 
     *〈简述〉根据包返回抽取供应商
     *〈详细描述〉
     * @author Wang Wenshuai
     * @return
     */
    List<Packages> listResultSupplier(String projectId);

    /**
     * 
     *〈简述〉根据包返回中标供应商
     *〈详细描述〉
     * @author Wang Wenshuai
     * @return
     */
    List<Packages> listSupplierCheckPass(String projectId);
    
    List<Packages> supplierCheckPa(String projectId);

    /**
     *〈简述〉分页查询包
     *〈详细描述〉
     * @author Ye MaoLin
     * @param map
     * @param i
     * @return
     */
    List<Packages> findPackageByPage(Packages packages, int i);
    
    /**
     * 
     *〈简述〉根据id 项目id查询包
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param packages
     * @return
     */
    List<Packages> find(Packages packages);
    
    
    /**
     * 〈简述〉根据map 项目id查询包
     *  @author YangHongliang
     * @param map
     * @return
     */
     List<Packages> findByID(Map<String,Object> map);
    /**
     * 
    * @Title: insertPackage
    * @author ZhaoBo
    * @date 2016-12-26 下午8:22:15  
    * @Description: UUID新增包 
    * @param @param pack
    * @param @return      
    * @return int
     */
    int insertPackage(Packages pack);
    
    /**
     * 
     *〈简述〉根据包返回专家抽取信息
     *〈详细描述〉
     * @author Wang Wenshuai
     * @return
     */
    List<Packages> listProjectExtract(String projectId);
    
    /**
     * 
     *〈简述〉根据包返回抽取记录
     *〈详细描述〉
     * @author Wang Wenshuai
     * @return
     */
    List<Packages> listExpExtCondition(String projectId);
    
    Packages selectByPrimaryKeyId(String id);
    
    /**
     * 
     *〈简述〉根据包返回供应商抽取信息
     *〈详细描述〉
     * @author Wang Wenshuai
     * @return
     */
    List<Packages> listExtRelate(String projectId);
    List<Packages> selectByProjectKey(HashMap<String,Object> map);
    
    /**
     * 
    * @Title: merge
    * @author FengTian 
    * @date 2017-5-27 下午2:35:15  
    * @Description: 合并 分包
    * @param @param projectId
    * @param @param id
    * @param @return      
    * @return String
     */
    String merge(String projectId, String id);

    /**
     *〈简述〉
     *〈详细描述〉查询包
     * @author Ye MaoLin
     * @param map
     * @param i
     * @return
     */
    List<Packages> findPackage(HashMap<String, Object> map, int i);
    
    /**
     * 获取有资格性符合性检查的包
     * @param map
     * @param i
     * @return
     */
    List<Packages> selectByPackageFirstAudit(HashMap<String, Object> map);


    /**
     * 
    * @Title: savePackage
    * @author FengTian 
    * @date 2017-10-17 上午10:10:30  
    * @Description: TODO 
    * @param @param ids
    * @param @param projectId
    * @param @return      
    * @return Boolean
     */
    Boolean savePackage(String ids, String projectId);

}
