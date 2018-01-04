package ses.service.sms.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import ses.dao.bms.CategoryMapper;
import ses.dao.bms.CategoryQuaMapper;
import ses.dao.sms.SupplierAptituteMapper;
import ses.dao.sms.SupplierAptituteRecyMapper;
import ses.dao.sms.SupplierMatEngMapper;
import ses.model.bms.Category;
import ses.model.bms.CategoryQua;
import ses.model.sms.SupplierAptitute;
import ses.model.sms.SupplierAptituteRecy;
import ses.model.sms.SupplierItem;
import ses.model.sms.SupplierMatEng;
import ses.service.sms.SupplierAptituteService;
import ses.service.sms.SupplierItemService;
import ses.service.sms.SupplierService;

@Service(value = "supplierAptituteService")
public class SupplierAptituteServiceImpl implements SupplierAptituteService {
	
	@Autowired
	private SupplierAptituteMapper supplierAptituteMapper;
	@Autowired
	private CategoryQuaMapper categoryQuaMapper;
	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private SupplierItemService supplierItemService;
	@Autowired
	private SupplierMatEngMapper supplierMatEngMapper;
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private SupplierAptituteRecyMapper supplierAptituteRecyMapper;
	
	@Override
	public int saveOrUpdateAptitute(SupplierAptitute supplierAptitute) {
//		String id = supplierAptitute.getId();
//		if (id != null && !"".equals(id)) {
//			supplierAptituteMapper.updateByPrimaryKeySelective(supplierAptitute);
//		} else {
			return supplierAptituteMapper.insertSelective(supplierAptitute);
//		}

	}

	@Override
	public SupplierAptitute queryById(String id) {
		SupplierAptitute aptitute = supplierAptituteMapper.selectByPrimaryKey(id);
		return aptitute;
	}

	@Override
	public List<SupplierAptitute> queryByMatEngId(String matEngId) {
		return supplierAptituteMapper.findAptituteByMatEngId(matEngId);
	}

	@Override
	public List<SupplierAptitute> queryByCodeAndType(String certType,String matEngId,String code, String type) {
		return supplierAptituteMapper.quertByCodeAndName(certType, matEngId, code, type);
	}

	@Override
	public List<String> getProType(String typeId, String matEngId, String code) {
		return supplierAptituteMapper.quertProType(typeId, matEngId, code);
	}

	@Override
	public int selectByCertCode(String certCode) {
		return supplierAptituteMapper.selectByCertCode(certCode);
	}

	@Override
	public boolean deleteAptituteByIds(String ids) {
		boolean isSuccess = false;
		try {
			if (StringUtils.isNotBlank(ids)) {
				String[] idArray = ids.split(",");
				int delCount = 0;
				int hasCount = 0;
				String supplierId = null;
				String supplierSt = null;
				for (int i = 0; i < idArray.length; i++) {
					String id = idArray[i];
					if (StringUtils.isNotBlank(id)) {
						SupplierAptitute aptitute = supplierAptituteMapper.selectByPrimaryKey(id);
						if (aptitute != null) {
							int key = supplierAptituteMapper.deleteByPrimaryKey(id);
							if (key == 1) {
								delCount++;
								// 关联删除所选产品类别
								if(supplierId == null){
									String matEngId = aptitute.getMatEngId();
									SupplierMatEng supplierMatEng = supplierMatEngMapper.selectByPrimaryKey(matEngId);
									if(supplierMatEng != null){
										supplierId = supplierMatEng.getSupplierId();
									}
								}
								if(supplierSt == null){
									supplierSt = supplierService.getStatusById(supplierId);
								}
								String recyAptId = null;
								if("2".equals(supplierSt)){
									recyAptId = id;
								}
								String quaId = aptitute.getCertType();
								CategoryQua paramCq = new CategoryQua();
								paramCq.setQuaId(quaId);
								paramCq.setQuaType(4);
								List<CategoryQua> listCq = categoryQuaMapper.selectCategoryQuaList(paramCq);
								if(listCq != null && listCq.size() > 0){
									for(CategoryQua cq : listCq){
										String categoryId = cq.getCategoryId();
										Category category = categoryMapper.findById(categoryId);
										int count = supplierItemService.countBySupplierIdCategoryId(supplierId, categoryId, "PROJECT");
										if(category != null && count > 0){
											SupplierItem supplierItem = new SupplierItem();
											supplierItem.setSupplierId(supplierId);
											supplierItem.setCategoryId(categoryId);
											supplierItem.setSupplierTypeRelateId("PROJECT");
											if((category.getCode()+"").startsWith("B02")
											|| (category.getCode()+"").startsWith("B03")){
												// 工程勘察/工程设计
												supplierItemService.deleteItems(supplierItem, true, recyAptId);
											}else{
												supplierItemService.deleteItems(supplierItem, false, recyAptId);
											}
										}
									}
								}
								// 将删除的记录保存至回收站
								if("2".equals(supplierSt)){
									// 回收工程资质信息
									SupplierAptituteRecy supplierAptituteRecy = new SupplierAptituteRecy();
									BeanUtils.copyProperties(aptitute, supplierAptituteRecy);
									supplierAptituteRecyMapper.insertSelective(supplierAptituteRecy);
								}
							}
							hasCount++;
						}
					}
				}
				if (delCount == hasCount) {
					isSuccess = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return isSuccess;
	}
	
}
