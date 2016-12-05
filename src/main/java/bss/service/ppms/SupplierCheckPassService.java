package bss.service.ppms;

import java.util.List;

import bss.model.ppms.Packages;
import bss.model.ppms.SupplierCheckPass;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述> 中标供应商
 * <详细描述>
 * @author   Wang Wenshuai
 * @version  
 * @since
 * @see
 */
public interface SupplierCheckPassService {
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
     *〈简述〉根据包id获取包下为发送通知的供应商和中标未中标的供应商
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param checkPass 对象
     * @return 包集合
     */
    List<SupplierCheckPass> listSupplierCheckPass(SupplierCheckPass checkPass);

    /**
     * 
     *〈简述>修改
     *〈详细描述〉
     * @author Wang Wenshuai
     */
    void update(SupplierCheckPass checkPass);
    
    /**
     * 
     *〈简述〉修改中标状态
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param id
     */
    void updateBid(String[] id);
    
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
     *〈简述〉 查询每包是否都选择了中标供应商
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param prijectId 项目id
     * @return 集合
     */
    String[] selectWonBid(String prijectId);
}
