/**
 * 
 */
package bss.dao.ppms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ses.model.ems.ExpExtCondition;
import ses.model.ems.ProjectExtract;
import bss.model.ppms.Packages;
import bss.model.ppms.SupplierCheckPass;

/**
 * @Title:PackageMapper
 * @Description: 
 * @author ZhaoBo
 * @date 2016-10-9下午2:01:43
 */
public interface PackageMapper {
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
     *〈简述〉根据条件查询
     *〈详细描述〉
     * @author YangHongliang
     * @param map
     * @return
     */
    List<Packages> findByID(Map<String,Object> map);
    
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


    List<Packages> selectByPrimaryKey(HashMap<String,Object> map);

    /**
     * @Title: findPackageByCondition
     * @author Song Biaowei
     * @date 2016-10-27 下午1:33:11  
     * @Description: 根据项目Id查找包名  
     * @param @param str
     * @param @return      
     * @return List<Packages>
     */
    List<Packages> findPackageByCondition(String str);


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
     * @Title: findPackageAndBidMethodById
     * @author: Tian Kunfeng
     * @date: 2016-11-7 上午10:50:39
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
     *〈简述〉根据包返回抽取中标供应商
     *〈详细描述〉
     * @author Wang Wenshuai
     * @return
     */
    List<Packages> listSupplierCheckPass(String projectId);
    
    List<Packages> supplierCheckPa(String projectId);
    
    /**
     *〈简述〉根据条件查询
     *〈详细描述〉
     * @author Ye MaoLin
     * @param map
     * @return
     */
    List<Packages> find(Packages packages);
    
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
     *〈简述〉根据包获取抽取信息
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param projectId
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
     *〈简述〉供应商抽取信息
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param projectId
     * @return
     */
    List<Packages> listSupplierExtract(String projectId);

    /**
     *〈简述〉按照创建时间排序
     *〈详细描述〉
     * @author Ye MaoLin
     * @param map
     * @return
     */
    List<Packages> selectPackageOrderByCreated(HashMap<String, Object> map);
}
