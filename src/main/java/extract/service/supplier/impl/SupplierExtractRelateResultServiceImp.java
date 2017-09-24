/**
 * 
 */
package extract.service.supplier.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierExtPackageMapper;
import ses.dao.sms.SupplierExtRelateMapper;
import ses.dao.sms.SupplierMapper;
import ses.util.PropUtil;
import ses.util.UUIDUtils;

import com.github.pagehelper.PageHelper;

import extract.dao.supplier.SupplierExtractConditionMapper;
import extract.dao.supplier.SupplierExtractRecordMapper;
import extract.dao.supplier.SupplierExtractRelateResultMapper;
import extract.model.supplier.SupplierExtractResult;
import extract.service.supplier.SupplierExtractRelateResultService;

/**
 * @Description:供应商抽取关联
 *	 
 * @author Wang Wenshuai
 * @date 2016年9月20日下午4:17:22
 * @since  JDK 1.7
 */
@Service
public class SupplierExtractRelateResultServiceImp implements SupplierExtractRelateResultService {
  @Autowired
  SupplierExtractRelateResultMapper supplierExtRelateMapper;
  @Autowired
  SupplierExtractConditionMapper conditionMapper;
  @Autowired
  SupplierMapper supplierMapper;
  @Autowired
  SupplierExtractRecordMapper supplierExtractsMapper;
  @Autowired
  private SupplierExtPackageMapper extPackageMapper;
  
  @Autowired
  private SupplierExtRelateMapper extRelateMapper;
  /**
   * @Description:insert
   *
   * @author Wang Wenshuai
   * @version 2016年9月28日 下午4:12:09  
   * @param       
   * @return void
   */
  
  /*@Override
   * public String insert(String cId,String userid,String[] projectId,String conditionId) {
    //获取查询条件
    List<SupplierCondition> list = conditionMapper.list(new SupplierCondition(cId, ""));
    if(list!=null&&list.size()!=0){
      SupplierCondition show=list.get(0);
      //循环出地址
      if(show.getAddressId() != null && !"".equals(show.getAddressId())){
        if(show.getAddressId().contains(",")){
          String[] split = show.getAddressId().split(",");  
          show.setAddressSplit(split);
          show.setAddressId(null);
        }
      }
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
        if(show.getExpertsTypeId() != null &&  !"".equals(show.getExpertsTypeId())){
          contype.setArraySupplierTypeId(show.getExpertsTypeId().split("\\,"));
        }
        List<Suppliere> selectAllExpert = supplierMapper.listExtractionExpert(show);//getAllSupplier(null);
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
          Collections.shuffle(listRelate);
          supplierExtRelateMapper.insertList(listRelate);
        }

      }

    }
    return "";
  }*/
  /**
   * @Description:集合展示
   *
   * @author Wang Wenshuai
   * @version 2016年9月28日 下午6:07:39  
   * @param @param projectExtract      
   * @return void
   */
  @Override
  public List<SupplierExtractResult> list(SupplierExtractResult projectExtract,String page) {
    if(page!=null&&!"".equals(page))
      PageHelper.startPage(Integer.valueOf(page), PropUtil.getIntegerProperty("pageSize"));
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
  public void update(SupplierExtractResult projectExtract) {
   /* if(projectExtract != null && projectExtract.getPackageId() != null && projectExtract.getPackageId().length !=0 ){
      for (String packageId : projectExtract.getPackageId()) {
        if (!"".equals(packageId)){
          SupplierExtractResult pe = supplierExtRelateMapper.selectByPrimaryKey(projectExtract.getId());
          if(pe != null){
            if(packageId != pe.getProjectId()){
              SupplierExtractResult extract = new SupplierExtractResult();
              extract.setProjectId(packageId);
              extract.setSupplierId(pe.getSupplier().getId());
              List<SupplierExtractResult> list = supplierExtRelateMapper.list(extract);
              if(list != null && list.size() != 0){
                list.get(0).setOperatingType(projectExtract.getOperatingType());
                list.get(0).setReviewType(pe.getReviewType());
                if(projectExtract.getOperatingType() == 3){
                  list.get(0).setReviewType(null);
                  list.get(0).setReason(projectExtract.getReason());
                }
                supplierExtRelateMapper.updateByPrimaryKeySelective(list.get(0));
              }else{
                SupplierExtractResult pext = new SupplierExtractResult();
                pext.setSupplierId(pe.getSupplier().getId());
                pext.setProjectId(packageId);

                pext.setOperatingType(projectExtract.getOperatingType());
                pext.setCreatedAt(new Date());
                pext.setReviewType(pe.getReviewType());
                if(projectExtract.getOperatingType() == 3){
                  pext.setReviewType(null);
                  pext.setReason(projectExtract.getReason());
                }
                supplierExtRelateMapper.insertSelective(pext);
              }


            }

          }
        }
      }

    }
    supplierExtRelateMapper.updateByPrimaryKeySelective(projectExtract);*/

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
  public SupplierExtractResult getSupplierExtRelate(String id) {
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
  public void del(String conditionId,String projectId,List<String> expertTypeIds,List<String> saveExpertTypeIds) {
   /* List<SupplierExtractResult> list = selectSupplierType(conditionId);
    for (SupplierExtractResult supplierExtRelate : list) {
      boolean containsAll = expertTypeIds.containsAll(castList(supplierExtRelate.getSupplierTypeId(),saveExpertTypeIds));
      if(containsAll){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("projectId", projectId);
        map.put("supplierId",supplierExtRelate.getSupplierId());
        supplierExtRelateMapper.del(map);
      }
    }*/
  }

  /**
   * 
   *〈简述〉转换集合
   *〈详细描述〉
   * @author Wang Wenshuai
   * @param type
   * @return
   */
  private List<String>  castList(String type,List<String> saveExpertTypeIds){
    List<String> list = null;
    if(type != null && !"".equals(type)){
      list = new ArrayList<String>();
      String[]  str = type.split(",");
      for (String ids : saveExpertTypeIds) {
        for (String ty : str) {
          if(ids.equals(ty)){
            list.add(ty);  
          }
        }
      }
    }
    return list;

  }

  /**
   * 抽取完成后删除信息
   */
  @Override
  public void delPe(String id) {
    // TODO Auto-generated method stub
    supplierExtRelateMapper.delPe(id);
  }

  /**
   * 供应商类型
   * @see ses.service.sms.SupplierExtRelateService#selectSupplierType(java.lang.String)
   */
  @Override
  public List<SupplierExtractResult> selectSupplierType(String conditionId) {
    return supplierExtRelateMapper.selectSupplierType(conditionId);
  }
  
  /**
   * 存储结果
   */
	@Override
	public void saveResult(SupplierExtractResult supplierExtRelate,String projectType) {
		
		ArrayList<SupplierExtractResult> arrayList = new ArrayList<>();
		String[] packageIds = supplierExtRelate.getPackageIds();
		for (String packageId : packageIds) {
			supplierExtRelate.setId(UUIDUtils.getUUID32());
			supplierExtRelate.setPackageId(packageId);
			arrayList.add(supplierExtRelate);
		}
		
		switch (projectType) {
		case "advPro":
			extRelateMapper.insertAdv(arrayList);
			break;
		case "relPro":
			extRelateMapper.insertRel(arrayList);
			break;

		default:
			supplierExtRelateMapper.insertSelective(supplierExtRelate);
			break;
		}
	}
  @Override
  public String insert(String cId, String userId, String[] projectId,
      String conditionId) {
    return null;
  }
	
	/**
	 * 删除待定
	 */
	
	
}


