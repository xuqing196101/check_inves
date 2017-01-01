/**
 * 
 */
package ses.service.sms.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.dao.sms.SupplierAgentsMapper;
import ses.dao.sms.SupplierConditionMapper;
import ses.dao.sms.SupplierExtPackageMapper;
import ses.dao.sms.SupplierExtRelateMapper;
import ses.dao.sms.SupplierExtractsMapper;
import ses.dao.sms.SupplierMapper;
import ses.model.ems.ExtConType;
import ses.model.ems.ProjectExtract;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierConType;
import ses.model.sms.SupplierCondition;
import ses.model.sms.SupplierExtPackage;
import ses.model.sms.SupplierExtRelate;
import ses.service.sms.SupplierExtRelateService;

/**
 * @Description:供应商抽取关联
 *	 
 * @author Wang Wenshuai
 * @date 2016年9月20日下午4:17:22
 * @since  JDK 1.7
 */
@Service
public class SupplierExtRelateServiceImpl implements SupplierExtRelateService {
    @Autowired
    SupplierExtRelateMapper supplierExtRelateMapper;
    @Autowired
    SupplierConditionMapper conditionMapper;
    @Autowired
    SupplierMapper supplierMapper;
    @Autowired
    SupplierExtractsMapper supplierExtractsMapper;
    @Autowired
    private SupplierExtPackageMapper extPackageMapper;
    /**
     * @Description:insert
     *
     * @author Wang Wenshuai
     * @version 2016年9月28日 下午4:12:09  
     * @param       
     * @return void
     */
    @Override
    public String insert(String cId,String userid,String[] projectId,String conditionId) {
        //获取查询条件
        List<SupplierCondition> list = conditionMapper.list(new SupplierCondition(cId, ""));
        if(list!=null&&list.size()!=0){
            SupplierCondition show=list.get(0);
            //给供应商set查询条件
            Supplier supplier=new Supplier();
            supplier.setAddress(show.getAddress());
            //复制对象
            List<SupplierConType> conTypeCopy=new ArrayList<SupplierConType>();
            for(SupplierConType ct : show.getConTypes()) {
                SupplierConType dw = new SupplierConType();
                BeanUtils.copyProperties(ct, dw, new String[] {"serialVersionUID"});
                conTypeCopy.add(dw);
            }

            for (SupplierConType contype : conTypeCopy) {
                List<SupplierExtRelate> listRelate=new ArrayList<SupplierExtRelate>();
                show.getConTypes().clear();
                show.getConTypes().add(contype);
                //查询供应商集合
                if(contype.getSupplierTypeId() != null &&  !"".equals(contype.getSupplierTypeId())){
                    contype.setArraySupplierTypeId(contype.getSupplierTypeId().split("\\,"));
                }
                List<Supplier> selectAllExpert = supplierMapper.listExtractionExpert(show);//getAllSupplier(null);
                //循环吧查询出的专家集合insert到专家记录表和专家关联的表中
                SupplierExtRelate supplierExtRelate=null;
                String[] conId = conditionId.split(",");
                for (int i = 0; i < conId.length; i++ ) {
                    for (Supplier supplier2 : selectAllExpert) {
                        Map<String, String> map=new HashMap<String, String>();
                        map.put("supplierId", supplier2.getId());
                        map.put("projectId",show.getProjectId());
                        if(supplierExtRelateMapper.getSupplierId(map)==0){
                            supplierExtRelate = new SupplierExtRelate();
                            //供应商id
                            supplierExtRelate.setSupplierId(supplier2.getId());
                            //项目id
                            supplierExtRelate.setProjectId(projectId[i]);
                            //条件表id
                            supplierExtRelate.setSupplierConditionId(conId[i]);
                            supplierExtRelate.setIsDeleted((short)0);
                            supplierExtRelate.setOperatingType((short)0);
                            supplierExtRelate.setConTypeId(contype.getId());
                            //添加到集合
                            listRelate.add(supplierExtRelate);
                        }
                    }
                }
                //插入表中
                if(listRelate.size()!=0){
                    supplierExtRelateMapper.insertList(listRelate);
                }

            }

        }
        return "";
    }
    /**
     * @Description:集合展示
     *
     * @author Wang Wenshuai
     * @version 2016年9月28日 下午6:07:39  
     * @param @param projectExtract      
     * @return void
     */
    @Override
    public List<SupplierExtRelate> list(SupplierExtRelate projectExtract,String page) {
        if(page!=null&&!"".equals(page))
            PageHelper.startPage(Integer.valueOf(page), 10);
        return supplierExtRelateMapper.list(projectExtract);

    }
    /**
     * @Description:修改操作状态
     *
     * @author Wang Wenshuai
     * @version 2016年9月28日 下午8:02:39  
     * @param @param projectExtract      
     * @return void
     */
    @Override
    public void update(SupplierExtRelate projectExtract) {

        supplierExtRelateMapper.updateByPrimaryKeySelective(projectExtract);

    }

    /**
     * @Description:获取单个对象
     *
     * @author Wang Wenshuai
     * @version 2016年9月28日 下午8:02:39  
     * @param @param projectExtract      
     * @return void
     */
    @Override
    public SupplierExtRelate getSupplierExtRelate(String id) {
        return supplierExtRelateMapper.selectByPrimaryKey(id);
    }

    /**
     * @Description:删除重复记录
     *
     * @author Wang Wenshuai
     * @version 2016年9月28日 下午6:09:52  
     * @param @param extract
     * @param @return      
     * @return List<ProjectExtract>
     */
    @Override
    public void deleteData(Map map){
        supplierExtRelateMapper.deleteData(map);
    }

    /**
     * @Description:当抽取数量满足时修改还未抽取的专家状态为1
     *
     * @author Wang Wenshuai
     * @version 2016年9月28日 下午6:09:52  
     * @param @param extract
     * @param @return      
     * @return List<ProjectExtract>
     */
    @Override
    public void updateStatusCount(String type,String conTypeId){
        Map<String, String> map=new HashMap<String, String>();
        map.put("type", type);
        map.put("conTypeId", conTypeId);
        supplierExtRelateMapper.updateStatusCount(map);
    }
    
    /**
     * 删除未抽取的抽取信息
     * @see ses.service.sms.SupplierExtRelateService#del(java.lang.String)
     */
    @Override
    public void del(String id) {
        supplierExtRelateMapper.del(id);
    }
    
    /**
     * 抽取完成后删除信息
     */
    @Override
    public void delPe(String id) {
      // TODO Auto-generated method stub
      supplierExtRelateMapper.delPe(id);
    }
}


