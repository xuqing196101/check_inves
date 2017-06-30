package bss.dao.ppms;

import java.util.HashMap;
import java.util.List;

import bss.model.ppms.Packages;
import bss.model.ppms.SupplierCheckPass;

public interface SupplierCheckPassMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param id
     */
    int deleteByPrimaryKey(String id);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(SupplierCheckPass record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplierCheckPass record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierCheckPass selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierCheckPass record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierCheckPass record);
    
    /**
     * 
     *〈简述〉获取包id和包name
     *〈详细描述〉
     * @author Wang Wenshuai.
     * @param projectId 项目id
     * @return 包集合
     */
    List<Packages> getPackageName(String projectId);
    
    /**
     * 
     *〈简述〉获取项目包下所有信息 
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param projectId 项目id
     * @return 包集合
     */
    List<Packages> listPackage(String projectId);
    
    /**
     * 
     *〈简述〉获取供应商名称+ 包名
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param checkPass 对象
     * @return 包集合
     */
    List<SupplierCheckPass> listSupplierCheckPass(SupplierCheckPass checkPass);
    
    /**
     * 
     *〈简述〉根据包id获取包下为发送通知的供应商和中标未中标的供应商
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param checkPass 对象
     * @return 包集合
     */
    List<SupplierCheckPass> listCheckPass(SupplierCheckPass checkPass);
    /**
     * @author Ma Mingwei
     * @description 根据包id获取包下为发送通知的供应商和中标未中标的供应商---为了不改上面原作者方法，新增一个
     * @param checkPass
     * @return 包集合
     */
    List<SupplierCheckPass> listCheckPassBD(SupplierCheckPass checkPass);
    
    /**
     *〈简述〉 查询每包是否都选择了中标供应商
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param prijectId 项目id
     * @return 集合
     */
    String[] selectWonBid(String projectId);
    
    List<SupplierCheckPass> getByCheck(String packageId);
    
    List<SupplierCheckPass> getByContractId(String contractId);
    List<SupplierCheckPass> selectPackageIdWonBid(String packageId);
    List<SupplierCheckPass> listsupplier(HashMap<String, Object> map);
    List<SupplierCheckPass> listCheckPassOrderRanking(String packId);
}