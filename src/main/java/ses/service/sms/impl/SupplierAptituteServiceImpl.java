package ses.service.sms.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierAptituteMapper;
import ses.model.sms.SupplierAptitute;
import ses.model.sms.SupplierCateTree;
import ses.model.sms.SupplierItem;
import ses.model.sms.SupplierMatEng;
import ses.service.sms.SupplierAptituteService;
import ses.service.sms.SupplierItemService;
import ses.service.sms.SupplierMatEngService;
import ses.util.DictionaryDataUtil;

@Service(value = "supplierAptituteService")
public class SupplierAptituteServiceImpl implements SupplierAptituteService {
	
	@Autowired
	private SupplierAptituteMapper supplierAptituteMapper;
	
	@Autowired
	private SupplierMatEngService supplierMatEngService;
	
	@Autowired
	private SupplierItemService supplierItemService;
	
	@Override
	public void saveOrUpdateAptitute(SupplierAptitute supplierAptitute) {
//		String id = supplierAptitute.getId();
//		if (id != null && !"".equals(id)) {
//			supplierAptituteMapper.updateByPrimaryKeySelective(supplierAptitute);
//		} else {
			supplierAptituteMapper.insertSelective(supplierAptitute);
//		}

	}

	@Override
	public void deleteAptitute(String aptituteIds) {
	    if(!StringUtils.isEmpty(aptituteIds)){
            for (String id : aptituteIds.split(",")) {
                supplierAptituteMapper.deleteById(id);
            }
        }
	}

	@Override
	public SupplierAptitute queryById(String id) {
		SupplierAptitute aptitute = supplierAptituteMapper.selectByPrimaryKey(id);
		return aptitute;
	}

	@Override
	public List<SupplierAptitute> queryByAptitute(String projectId) {
		return supplierAptituteMapper.findAptituteByMatEngId(projectId);
	}

	@Override
	public List<SupplierAptitute> queryByCodeAndType(String certType,String matEngId,String code, String type) {
		return supplierAptituteMapper.quertByCodeAndName(certType,matEngId, code, type);
	}

	@Override
	public List<String> getPorType(String typeId, String matEngId, String code) {
		return supplierAptituteMapper.quertProType(typeId, matEngId, code);
	}

	@Override
	public int selectByCertCode(String certCode) {
		return supplierAptituteMapper.selectByCertCode(certCode);
	}

	@Override
	public Map<String, Object> getEngAptitute(String suppId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		SupplierMatEng matEng = supplierMatEngService.getMatEng(suppId);
		String firstCateId = DictionaryDataUtil.getId("PROJECT");
		List < SupplierItem > listSupplierItems = supplierItemService.getCategoryOther(suppId, firstCateId, "PROJECT");
		List < SupplierCateTree > allTreeList = new ArrayList < SupplierCateTree > ();
		String modifiedCertCodes = "";
		for(SupplierItem item: listSupplierItems) {
			String categoryId = item.getCategoryId();
			SupplierCateTree cateTree = supplierItemService.getTreeListByCategoryId(categoryId, item);
			//后台判断证书编号是否有更新，若有将更新的证书编号放进数组，前台更新显示样式
			/*if(StringUtils.isNotEmpty(item.getCertCode())){
				int selectByCertCode = selectByCertCode(item.getCertCode());
				if(selectByCertCode == 0){
					if(StringUtils.isEmpty(modifiedCertCodes)){
						modifiedCertCodes = item.getCertCode();
					}else{
						modifiedCertCodes = modifiedCertCodes+"-"+item.getCertCode();
					}
				}
			}*/

			if(cateTree != null && cateTree.getRootNode() != null) {
				cateTree.setItemsId(item.getId());
				cateTree.setDiyLevel(item.getLevel());
				//每次都是最新
				if(matEng.getListSupplierAptitutes() != null && !matEng.getListSupplierAptitutes().isEmpty()){
					for (SupplierAptitute apt: matEng.getListSupplierAptitutes()){
						//proName="三大部分"  diyLevel="6B0CC322A1BF489898A3EF51DE9AA6AD"
						//qualificationType="4D96D5A8CAF4E7E2E050007F0100A66F"
						//System.out.println(apt.getCertCode()+","+apt.getCertType()+"==="+cateTree.getCertCode()+","+cateTree.getQualificationType());
						if(apt.getCertCode().equals(cateTree.getCertCode()) && apt.getCertType().equals(cateTree.getQualificationType())){
							cateTree.setProName(apt.getProfessType());
							cateTree.setLevel(DictionaryDataUtil.findById(apt.getAptituteLevel()));
							cateTree.setFileId(apt.getId());
							break;
						}else{
							cateTree.setProName(null);
							cateTree.setLevel(null);
							cateTree.setFileId(null);
						}
					}
				}
				/*if(cateTree!=null && cateTree.getCertCode() != null && cateTree.getQualificationType() != null && cateTree.getProName() != null) {
					List<SupplierAptitute> certEng = queryByCodeAndType(null, matEng.getId(), cateTree.getCertCode(), cateTree.getProName());
					if(certEng != null && certEng.size() > 0) {
						cateTree.setFileId(certEng.get(0).getId());
					}
				}*/
				allTreeList.add(cateTree);
			}
		}
		resultMap.put("allTreeList", allTreeList);
		resultMap.put("modifiedCertCodes", modifiedCertCodes);
		return resultMap;
	}
	
}
