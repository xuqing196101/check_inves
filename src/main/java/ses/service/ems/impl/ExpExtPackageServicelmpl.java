package ses.service.ems.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import bss.dao.ppms.PackageMapper;
import bss.model.ppms.Packages;
import ses.dao.ems.ExpExtPackageMapper;
import ses.dao.sms.SupplierExtPackageMapper;
import ses.model.ems.ExpExtPackage;
import ses.model.sms.SupplierExtPackage;
import ses.service.ems.ExpExtPackageService;
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
public class ExpExtPackageServicelmpl implements ExpExtPackageService  {
    @Autowired
    private PackageMapper mapper;
    @Autowired
    private ExpExtPackageMapper extPackageMapper;

    /**
     * 
     *〈简述〉获取集合
     *〈详细描述〉
     * @author Wang Wenshuai
     */
    public List<ExpExtPackage> list(ExpExtPackage sExtPackage,String page){
        //==0不分页
        if(Integer.valueOf(page)!=0){
            PageHelper.startPage(Integer.valueOf(page), PropUtil.getIntegerProperty("pageSize"));
        }
        List<ExpExtPackage> list = extPackageMapper.list(sExtPackage);
        //如果为空插入数据后重新查询
        if (list==null || list.size() == 0){
            HashMap<String, Object> map=new HashMap<String, Object>();
            map.put("projectId",sExtPackage.getProjectId());
            List<Packages> listPackages = mapper.findPackageById(map);
            List<ExpExtPackage> listExt=new ArrayList<ExpExtPackage>();
            for (Packages packages : listPackages) {
                ExpExtPackage extPackage=new ExpExtPackage();
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
    public void update(ExpExtPackage sExtPackage) {
        extPackageMapper.updateByPrimaryKeySelective(sExtPackage);
    }

    /**
     * 
     *〈简述〉插入
     *〈详细描述〉
     * @author Wang Wenshuai
     */
    @Override
    public void insert(ExpExtPackage sExtPackage) {
        extPackageMapper.insertSelective(sExtPackage);
        
    }
    
    /**
     * 
     *〈简述〉
     *〈详细描述〉
     * @author Wang Wenshuai
     */
    @Override
    public List<ExpExtPackage> extractsList(ExpExtPackage record){
        return extPackageMapper.extractsList(record);
    }

    /**
     * 
     *〈简述〉 getbyid
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param id id
     * @return 对象
     */
    @Override
    public ExpExtPackage getById(String id) {
        
        return extPackageMapper.selectByPrimaryKey(id);
        
    }
    

}
