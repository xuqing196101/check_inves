package ses.service.sms.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.review.SupplierCateAuditMapper;
import ses.model.bms.Category;
import ses.model.bms.DictionaryData;
import ses.model.sms.SupplierAudit;
import ses.model.sms.SupplierCateTree;
import ses.model.sms.SupplierItem;
import ses.model.sms.review.SupplierCateAudit;
import ses.model.sms.review.SupplierCateAuditExample;
import ses.model.sms.review.SupplierCateAuditExample.Criteria;
import ses.service.bms.CategoryService;
import ses.service.sms.SupplierAuditService;
import ses.service.sms.SupplierCateAuditService;
import ses.service.sms.SupplierItemService;
import ses.service.sms.SupplierTypeRelateService;
import ses.util.DictionaryDataUtil;
import ses.util.WfUtil;

@Service("supplierCateAuditService")
public class SupplierCateAuditServiceImpl implements SupplierCateAuditService {
	
	@Autowired
	private SupplierCateAuditMapper supplierCateAuditMapper;
	@Autowired
	private SupplierItemService supplierItemService;
	@Autowired
	private SupplierAuditService supplierAuditService;
	@Autowired
	private SupplierTypeRelateService supplierTypeRelateService;
	@Autowired
	private CategoryService categoryService;

	@Override
	public int countBySupplierId(String supplierId) {
		SupplierCateAuditExample example = new SupplierCateAuditExample();
		example.createCriteria().andSupplierIdEqualTo(supplierId).andIsDeletedEqualTo(0);
		return supplierCateAuditMapper.countByExample(example);
	}

	@Override
	public List<SupplierCateAudit> getBySupplierId(String supplierId) {
		SupplierCateAuditExample example = new SupplierCateAuditExample();
		example.setOrderByClause("position asc");
		example.createCriteria().andSupplierIdEqualTo(supplierId).andIsDeletedEqualTo(0);
		return supplierCateAuditMapper.selectByExample(example);
	}

	@Override
	public int addBySupplierId(String supplierId) {
		int result = 0;
		// 审核通过的品目
		List<String> typeList = supplierTypeRelateService.findTypeBySupplierId(supplierId);
		if(typeList != null && typeList.size() > 0){
			int m = 0;
			for(String supplierType : typeList){
				SupplierAudit supplierAuditType = new SupplierAudit();
				supplierAuditType.setSupplierId(supplierId);
				supplierAuditType.setAuditType("supplierType_page");
				DictionaryData dd = DictionaryDataUtil.get(supplierType);
				if(dd != null){
					supplierAuditType.setAuditField(dd.getId());
				}
				int countAuditType = supplierAuditService.countAuditRecords(supplierAuditType, new Integer[]{0,2});
				if(countAuditType == 0){
					m++;
					List<SupplierItem> itemList = supplierItemService.getItemList(supplierId, supplierType, null, null);
					if(itemList != null && itemList.size() > 0){
						int firstN = 0;
						int secondN = 0;
						int thirdN = 0;
						int fourthN = 0;
						for(SupplierItem item : itemList){
							if(item.getNodeLevel() != null && item.getNodeLevel() == 3){
								SupplierAudit supplierAuditItem = new SupplierAudit();
								supplierAuditItem.setSupplierId(supplierId);
								String auditType = "items_product_page";
								if("SALES".equals(supplierType)){
									auditType = "items_sales_page";
								}
								supplierAuditItem.setAuditType(auditType);
								supplierAuditItem.setAuditField(item.getCategoryId());
								int countAuditItem = supplierAuditService.countAuditRecords(supplierAuditItem, new Integer[]{0,2});
								if(countAuditItem == 0){
									
									SupplierCateTree cateTree = supplierItemService.getSupplierCateTree(item);
									
									SupplierCateAuditExample example = new SupplierCateAuditExample();
									Criteria criteria = example.createCriteria();
									criteria
									.andSupplierIdEqualTo(supplierId)
									.andSupplierTypeIdEqualTo(item.getSupplierTypeRelateId());
									
									SupplierCateAudit supplierCateAudit = null;
									int countCate = 0;
									
									if(StringUtils.isNotBlank(cateTree.getRootNode())){
										example.clear();
										criteria = example.createCriteria();
										criteria
										.andSupplierIdEqualTo(supplierId)
										.andSupplierTypeIdEqualTo(item.getSupplierTypeRelateId());
										criteria.andCategoryIdEqualTo(cateTree.getRootNodeID());
										countCate = supplierCateAuditMapper.countByExample(example);
										if(countCate == 0){
											supplierCateAudit = new SupplierCateAudit();
											supplierCateAudit.setId(WfUtil.createUUID());
											supplierCateAudit.setSupplierId(supplierId);
											supplierCateAudit.setSupplierTypeId(item.getSupplierTypeRelateId());
											supplierCateAudit.setCategoryId(cateTree.getRootNodeID());
											supplierCateAudit.setCategoryName(cateTree.getRootNode());
											supplierCateAudit.setSn(toChinese(m));
											supplierCateAudit.setPosition(result+1);
											result += supplierCateAuditMapper.insertSelective(supplierCateAudit);
										}
									}
									
									if(StringUtils.isNotBlank(cateTree.getFirstNode())){
										example.clear();
										criteria = example.createCriteria();
										criteria
										.andSupplierIdEqualTo(supplierId)
										.andSupplierTypeIdEqualTo(item.getSupplierTypeRelateId());
										criteria.andCategoryIdEqualTo(cateTree.getFirstNodeID());
										countCate = supplierCateAuditMapper.countByExample(example);
										if(countCate == 0){
											firstN++;
											secondN = 0;
											thirdN = 0;
											fourthN = 0;
											supplierCateAudit = new SupplierCateAudit();
											supplierCateAudit.setId(WfUtil.createUUID());
											supplierCateAudit.setSupplierId(supplierId);
											supplierCateAudit.setSupplierTypeId(item.getSupplierTypeRelateId());
											supplierCateAudit.setCategoryId(cateTree.getFirstNodeID());
											supplierCateAudit.setCategoryName(cateTree.getFirstNode());
											supplierCateAudit.setSn("（"+toChinese(firstN)+"）");
											supplierCateAudit.setPosition(result+1);
											result += supplierCateAuditMapper.insertSelective(supplierCateAudit);
										}
									}
									
									if(StringUtils.isNotBlank(cateTree.getSecondNode())){
										example.clear();
										criteria = example.createCriteria();
										criteria
										.andSupplierIdEqualTo(supplierId)
										.andSupplierTypeIdEqualTo(item.getSupplierTypeRelateId());
										criteria.andCategoryIdEqualTo(cateTree.getSecondNodeID());
										countCate = supplierCateAuditMapper.countByExample(example);
										if(countCate == 0){
											secondN++;
											thirdN = 0;
											fourthN = 0;
											supplierCateAudit = new SupplierCateAudit();
											supplierCateAudit.setId(WfUtil.createUUID());
											supplierCateAudit.setSupplierId(supplierId);
											supplierCateAudit.setSupplierTypeId(item.getSupplierTypeRelateId());
											supplierCateAudit.setCategoryId(cateTree.getSecondNodeID());
											supplierCateAudit.setCategoryName(cateTree.getSecondNode());
											supplierCateAudit.setSn(secondN+"");
											supplierCateAudit.setPosition(result+1);
											result += supplierCateAuditMapper.insertSelective(supplierCateAudit);
										}
									}
									
									if(StringUtils.isNotBlank(cateTree.getThirdNode())){
										example.clear();
										criteria = example.createCriteria();
										criteria
										.andSupplierIdEqualTo(supplierId)
										.andSupplierTypeIdEqualTo(item.getSupplierTypeRelateId());
										criteria.andCategoryIdEqualTo(cateTree.getThirdNodeID());
										countCate = supplierCateAuditMapper.countByExample(example);
										if(countCate == 0){
											thirdN++;
											fourthN = 0;
											supplierCateAudit = new SupplierCateAudit();
											supplierCateAudit.setId(WfUtil.createUUID());
											supplierCateAudit.setSupplierId(supplierId);
											supplierCateAudit.setSupplierTypeId(item.getSupplierTypeRelateId());
											supplierCateAudit.setCategoryId(cateTree.getThirdNodeID());
											supplierCateAudit.setCategoryName(cateTree.getThirdNode());
											supplierCateAudit.setSn("（"+thirdN+"）");
											supplierCateAudit.setPosition(result+1);
											result += supplierCateAuditMapper.insertSelective(supplierCateAudit);
										}
									}
									
									if(StringUtils.isNotBlank(cateTree.getFourthNode())){
										example.clear();
										criteria = example.createCriteria();
										criteria
										.andSupplierIdEqualTo(supplierId)
										.andSupplierTypeIdEqualTo(item.getSupplierTypeRelateId());
										criteria.andCategoryIdEqualTo(cateTree.getFourthNodeID());
										countCate = supplierCateAuditMapper.countByExample(example);
										if(countCate == 0){
											fourthN++;
											supplierCateAudit = new SupplierCateAudit();
											supplierCateAudit.setId(WfUtil.createUUID());
											supplierCateAudit.setSupplierId(supplierId);
											supplierCateAudit.setSupplierTypeId(item.getSupplierTypeRelateId());
											supplierCateAudit.setCategoryId(cateTree.getFourthNodeID());
											supplierCateAudit.setCategoryName(cateTree.getFourthNode());
											supplierCateAudit.setSn(toEnglish(fourthN));
											supplierCateAudit.setPosition(result+1);
											result += supplierCateAuditMapper.insertSelective(supplierCateAudit);
										}
									}
									
								}
							}
						}
					}
				}
			}
		}
		return result;
	}
	
	@Override
	public int updateById(String id, Integer isSupplied, String suggest) {
		int result = 0;
		SupplierCateAudit supplierCateAudit = supplierCateAuditMapper.selectByPrimaryKey(id);
		if(supplierCateAudit != null){
			SupplierCateAudit record = new SupplierCateAudit();
			record.setId(id);
			record.setIsSupplied(isSupplied);
			record.setSuggest(suggest);
			result = supplierCateAuditMapper.updateByPrimaryKeySelective(record);
			// 更新子节点
			String categoryId = supplierCateAudit.getCategoryId();
			List<Category> clist = categoryService.getCListById(categoryId);
			if(clist != null && clist.size() > 0){
				List<String> cid = new ArrayList<>();
				for(Category c : clist){
					cid.add(c.getId());
				}
				SupplierCateAuditExample example = new SupplierCateAuditExample();
				example.createCriteria().andCategoryIdIn(cid);
				record = new SupplierCateAudit();
				record.setIsSupplied(isSupplied);
				record.setSuggest(suggest);
				result = supplierCateAuditMapper.updateByExampleSelective(record, example);
			}
		}
		return result;
	}
	
	/**
	 * 
	 * Description: 数字转化字母
	 * 
	 * @author zhang shubin
	 * @data 2017年8月29日
	 * @param 
	 * @return
	 */
	public String toEnglish(Integer num){
    	if(num > 0){
	    	String a[] = {"","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
	    	if(num <= 26){
	    		return a[num];
	    	}else if(num % 26 == 0){
	    		return a[num/26 - 1] + "z";
	    	}else if(num % 26 <= 26){
	    		return a[num/26] + a[num%26];
	    	}else {
	    		return "";
	    	}
    	}else{
    		return "";
    	}
    }
	
	/**
     * 
     * Description: 数字转换中文
     * 
     * @author zhang shubin
     * @data 2017年8月23日
     * @param 
     * @return
     */
	public String toChinese(Integer num) {
		String[] units = { "", "十", "百", "千", "万", "十万", "百万", "千万", "亿", "十亿",
				"百亿", "千亿", "万亿" };
		char[] numArray = { '零', '一', '二', '三', '四', '五', '六', '七', '八', '九' };
		char[] val = String.valueOf(num).toCharArray();
		int len = val.length;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			String m = val[i] + "";
			int n = Integer.valueOf(m);
			boolean isZero = n == 0;
			String unit = units[(len - 1) - i];
			if (isZero) {
				if ('0' == val[i - 1]) {
					continue;
				} else {
					sb.append(numArray[n]);
				}
			} else {
				sb.append(numArray[n]);
				sb.append(unit);
			}
		}
		return sb.toString();
	}

}
