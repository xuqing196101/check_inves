package ses.service.sms.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import bss.dao.ppms.PackageMapper;
import bss.model.ppms.Packages;
import ses.dao.sms.SupplierExtPackageMapper;
import ses.model.sms.SupplierExtPackage;
import ses.service.sms.SupplierExtPackageServicel;
import ses.util.PropUtil;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述> 根据包抽取
 * <详细描述>
 * @author   Wang Wenshuai
 * @version  
 * @since
 * @see
 */

@Service
public class SupplierExtPackageServicelmpl implements SupplierExtPackageServicel  {
    @Autowired
    private PackageMapper mapper;
    @Autowired
    private SupplierExtPackageMapper extPackageMapper;


    /**
     * 
     *〈简述〉获取集合
     *〈详细描述〉
     * @author Wang Wenshuai
     */
    public List<SupplierExtPackage> list(SupplierExtPackage sExtPackage,String page){
        //==0不分页
        if(Integer.valueOf(page)!=0){
            PageHelper.startPage(Integer.valueOf(page), PropUtil.getIntegerProperty("pageSize"));
        }
        List<SupplierExtPackage> list = extPackageMapper.list(sExtPackage);
        //如果为空插入数据后重新查询
        if (list==null || list.size() == 0){
            HashMap<String, Object> map=new HashMap<String, Object>();
            map.put("projectId",sExtPackage.getProjectId());
            List<Packages> listPackages = mapper.findPackageById(map);
            List<SupplierExtPackage> listExt=new ArrayList<SupplierExtPackage>();
            for (Packages packages : listPackages) {
                SupplierExtPackage extPackage=new SupplierExtPackage();
                extPackage.setPackageId(packages.getId());
                extPackage.setProjectId(sExtPackage.getProjectId());
                listExt.add(extPackage);
            }
            //插入
            if(listExt.size()!=0){
                extPackageMapper.insertList(listExt);
            }
            PageHelper.startPage(Integer.valueOf(page), PropUtil.getIntegerProperty("pageSize"));
            list = extPackageMapper.list(sExtPackage);
        }

        return list;
    }
    
    /**
     * 
     *〈简述〉修改
     *〈详细描述〉
     * @author Wang Wenshuai
     */
    public void update(SupplierExtPackage sExtPackage) {
        extPackageMapper.updateByPrimaryKeySelective(sExtPackage);
    }

    /**
     * 
     *〈简述〉插入
     *〈详细描述〉
     * @author Wang Wenshuai
     */
    @Override
    public void insert(SupplierExtPackage sExtPackage) {
        extPackageMapper.insertSelective(sExtPackage);
        
    }
    
    /**
     * 
     *〈简述〉
     *〈详细描述〉
     * @author Wang Wenshuai
     */
    @Override
    public List<SupplierExtPackage> extractsList(SupplierExtPackage record){
        return extPackageMapper.extractsList(record);
    }
    

}
