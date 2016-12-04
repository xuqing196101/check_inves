package bss.service.ppms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.ppms.SupplierCheckPassMapper;
import bss.model.ppms.Packages;
import bss.model.ppms.SupplierCheckPass;
import bss.service.ppms.SupplierCheckPassService;

@Service
public class SupplierCheckPassServiceImpl implements SupplierCheckPassService {

    @Autowired
    SupplierCheckPassMapper checkPassMapper;

    /**
     * 
     *〈简述〉获取包id和包name
     *〈详细描述〉
     * @author Wang Wenshuai.
     * @param projectId 项目id
     * @return 包集合
     */
    @Override
    public List<Packages> getPackageName(String projectId){
        return checkPassMapper.getPackageName(projectId);
    }

    /**
     * 
     *〈简述〉获取项目包下所有信息 
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param projectId 项目id
     * @return 包集合
     */
    @Override
    public List<Packages> listPackage(String projectId){

        return checkPassMapper.listPackage(projectId);

    }

    /**
     * 
     *〈简述〉根据包id获取包下未发送通知的供应商和中标未中标的供应商
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param checkPass 对象
     * @return 包集合
     */
    @Override
    public List<SupplierCheckPass> listSupplierCheckPass(SupplierCheckPass checkPass){
        return checkPassMapper.listSupplierCheckPass(checkPass);
    }

    /**
     * 
     *〈简述〉修改
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param checkPass 对象
     * @return 包集合
     */
    @Override
    public void update(SupplierCheckPass checkPass) {
        checkPassMapper.updateByPrimaryKeySelective(checkPass);
    }

    /**
     * 
     *〈简述〉修改中标状态
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param id
     */
    @Override
    public void updateBid(String[] ids) {
        SupplierCheckPass record = null;
        for (String sid : ids) {
            record = new SupplierCheckPass();
            record.setId(sid);
            record.setIsWonBid((short) 1);
            checkPassMapper.updateByPrimaryKeySelective(record);
        }
    }

}
