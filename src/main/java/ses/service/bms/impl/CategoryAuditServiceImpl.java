package ses.service.bms.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.formbean.ResponseBean;
import ses.model.bms.Category;
import ses.model.bms.CategoryTree;
import ses.model.bms.DictionaryData;
import ses.service.bms.CategoryAuditService;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;
import ses.util.StringUtil;
import common.constant.StaticVariables;

/**
 * 
 * 版权：(C) 版权所有 <简述> 参数审核service实现 <详细描述>
 * 
 * @author myc
 * @version
 * @since
 * @see
 */
@Service
public class CategoryAuditServiceImpl implements CategoryAuditService {

	/** 根目录 */
	private static final String ROOT_PID = "0";

	/** 字典类型-品目根节点 */
	private static final String KIND_TYPE = "6";

	/** 物资类编码 */
	private static final String GOODS_CODE = "GOODS";

	/** 数据字典service */
	@Autowired
	private DictionaryDataServiceI directionService;

	/** 品目service */
	@Autowired
	private CategoryService categoryService;

	/**
	 * 
	 * @see ses.service.bms.CategoryAuditService#initTree(String treeId)
	 */
	@Override
	public List<CategoryTree> initTree(String treeId) {

		List<CategoryTree> list = new ArrayList<CategoryTree>();
		if (StringUtils.isNotBlank(treeId)) {
			List<Category> treeList = categoryService.findTreeByStatus(treeId,
					StaticVariables.CATEGORY_SUBMIT_STATUS);
			for (Category cate : treeList) {
				CategoryTree tree = new CategoryTree();
				tree.setId(cate.getId());
				tree.setName(cate.getName());
				tree.setpId(treeId);
				List<Category> cList = categoryService.findTreeByPid(cate
						.getId());
				if (cList != null && cList.size() > 0) {
					tree.setIsParent("true");
				} else {
					tree.setIsParent("false");
				}
				tree.setClassify(cate.getClassify() + "");
				tree.setPubStatus(cate.getIsPublish());
				tree.setStatus(cate.getParamStatus());
				list.add(tree);
			}
		} else {
			loadRoot(list);
		}

		return list;
	}

	/**
	 * 
	 * @see ses.service.bms.CategoryAuditService#audit(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public ResponseBean audit(String id, String status, String advise) {

		ResponseBean rb = new ResponseBean();
		if (!StringUtils.isNotBlank(status)) {
			rb.setResult(false);
			rb.setErrorMsg("审核状态不能为空");
			return rb;
		}

		Integer auditStatus = Integer.parseInt(status);
		if (auditStatus == StaticVariables.CATEGORY_ASSIGNED_STATUS) {
			if (!StringUtils.isNotBlank(advise)) {
				rb.setResult(false);
				rb.setErrorMsg("审核意见不能为空");
				return rb;
			}
		}

		if (!StringUtil.validateStrByLength(advise, 200)) {
			rb.setResult(false);
			rb.setErrorMsg("审核意见不能超过200个字");
			return rb;
		}

		if (StringUtils.isNotBlank(id)) {
			Category category = categoryService.selectByPrimaryKey(id);
			HashMap<String, Object> map=new HashMap<String, Object>();
			map.put("id",id);
			List<Category> treeList = categoryService
					.findCategoryByParentNode(map);
			List<String> list=new ArrayList<String>();
			if (auditStatus == StaticVariables.CATEGORY_ASSIGNED_STATUS) {
				category.setParamStatus(auditStatus);
				category.setAuditDate(new Date());
				category.setAuditPersonId("");
				category.setAuditAdvise(advise);
				categoryService
						.updateByPrimaryKeySelective(category);
				rb.setResult(true);
				rb.setObj(category);
				List<Category> categoryStatus = categoryService.findTreeByStatus(category.getParentId(), 2);
				if(categoryStatus==null||categoryStatus.size()==0){
					Category categoryP = categoryService.selectByPrimaryKey(category.getParentId());
					categoryP.setParamStatus(auditStatus);
					categoryService
					.updateByPrimaryKeySelective(categoryP);
					List<Category> categoryStatuTwo = categoryService.findTreeByStatus(categoryP.getParentId(), 2);
					if(categoryStatuTwo==null||categoryStatuTwo.size()==0){
						Category categoryJ = categoryService.selectByPrimaryKey(categoryP.getParentId());
						categoryJ.setParamStatus(auditStatus);
						categoryService
						.updateByPrimaryKeySelective(categoryJ);
						List<Category> categoryStatuThree = categoryService.findTreeByStatus(categoryJ.getParentId(), 2);
						if(categoryStatuTwo==null||categoryStatuTwo.size()==0){
							Category categoryK = categoryService.selectByPrimaryKey(categoryJ.getParentId());
							categoryK.setParamStatus(auditStatus);
							categoryService
							.updateByPrimaryKeySelective(categoryK);
						}
					}
				}
				
					/*
				for (int i = 0; i < treeList.size(); i++) {
					Category categorys = treeList.get(i);
					HashMap<String, Object> mapId=new HashMap<String, Object>();
					mapId.put("id", categorys.getParentId());
					List<Category> categoryChildren = categoryService.findCategoryByChildren(mapId);
					for(int j=0;j<categoryChildren.size();j++){
						Category categoryChil = categoryChildren.get(j);
						List<Category> cList = categoryService.findTreeByPid(categoryChil.getId());
                        if (cList != null &&cList.size()!=0) {
						}else{
							if(!category.getId().equals(categoryChil.getId())){
								categoryChil.setParamStatus(auditStatus);
								categoryService.updateByPrimaryKeySelective(categorys);
							}
							
						} 
					}
					if (!categorys.getId().equals(category.getId())) {
						List<Category> cList = categoryService.findTreeByPid(categorys.getId());
						if (cList != null &&cList.size()!=0) {
							
						}else{
							categorys.setParamStatus(auditStatus);
							categoryService.updateByPrimaryKeySelective(categorys);
						} 
					}
				}*/
				
			} else {
				if (category.getParamStatus() >= StaticVariables.CATEGORY_AUDIT_STATUS) {
					rb.setResult(false);
					rb.setErrorMsg(category.getName()
							+ StaticVariables.CATEGORY_AUDIT_MSG
							+ StaticVariables.OPERA_SUBMIT_MSG);
					return rb;

				} else if (category.getParamStatus() == StaticVariables.CATEGORY_ASSIGNED_STATUS) {
					rb.setResult(false);
					rb.setErrorMsg(category.getName()
							+ StaticVariables.CATEGORY_REJECT_MSG);
					return rb;
				} else {
					for (int i = 0; i < treeList.size(); i++) {
						Category categorys = treeList.get(i);
						if (categorys.getId().equals(category.getId())) {
							category.setParamStatus(auditStatus);
							category.setAuditDate(new Date());
							category.setAuditPersonId("");
							category.setAuditAdvise(advise);
							categoryService
									.updateByPrimaryKeySelective(category);
							rb.setResult(true);
							rb.setObj(category);
						} else {
							categorys.setParamStatus(auditStatus);
							categoryService
									.updateByPrimaryKeySelective(categorys);
						}
					}
				}

			}

		}

		return rb;
	}

	/**
	 * 
	 * 〈简述〉 加载根节点 〈详细描述〉
	 * 
	 * @author myc
	 * @param list
	 *            {@link 对象list}
	 */
	private void loadRoot(List<CategoryTree> list) {

		List<DictionaryData> treeRootList = directionService
				.findByKind(KIND_TYPE);

		for (DictionaryData data : treeRootList) {

			CategoryTree tree = new CategoryTree();
			tree.setId(data.getId());
			tree.setName(data.getName());
			tree.setpId(ROOT_PID);
			tree.setIsParent("true");
			if (data.getCode().equals(GOODS_CODE)) {
				tree.setClassify(GOODS_CODE);
			} else {
				tree.setClassify(null);
			}
			list.add(tree);
		}
	}

	@Override
	public List<CategoryTree> initTreeStatus(String treeId) {

		List<CategoryTree> list = new ArrayList<CategoryTree>();
		List<DictionaryData> treeRootList = directionService
				.findByKind(KIND_TYPE);
		for (DictionaryData data : treeRootList) {
			CategoryTree tree = new CategoryTree();
			tree.setId(data.getId());
			tree.setName(data.getName());
			tree.setpId(ROOT_PID);
			tree.setIsParent("true");
			if (data.getCode().equals(GOODS_CODE)) {
				tree.setClassify(GOODS_CODE);
			} else {
				tree.setClassify(null);
			}
			list.add(tree);
		}
		List<Category> treeList = categoryService
				.findCategoryByStatusAll(StaticVariables.CATEGORY_SUBMIT_STATUS);
		for (Category cate : treeList) {
			CategoryTree tree = new CategoryTree();
			tree.setId(cate.getId());
			tree.setName(cate.getName());
			tree.setpId(cate.getParentId());
			List<Category> cList = categoryService.findTreeByPid(cate.getId());
			if (cList != null && cList.size() > 0) {
				tree.setIsParent("true");
			} else {
				tree.setIsParent("false");
			}
			tree.setClassify(cate.getClassify() + "");
			tree.setPubStatus(cate.getIsPublish());
			tree.setStatus(cate.getParamStatus());
			tree.setAuditAdvise(cate.getAuditAdvise());
			tree.setAuditDate(cate.getAuditDate());
			list.add(tree);
		}
		/*
		 * for (DictionaryData data : treeRootList) { CategoryTree tree=new
		 * CategoryTree(); tree.setId(data.getId());
		 * tree.setName(data.getName()); tree.setpId(ROOT_PID);
		 * tree.setIsParent("true"); if (data.getCode().equals(GOODS_CODE)){
		 * tree.setClassify(GOODS_CODE); }else { tree.setClassify(null); }
		 * list.add(tree);
		 * 
		 * HashMap<String, Object> map=new HashMap<String, Object>();
		 * map.put("parentId", data.getId()); map.put("status",
		 * StaticVariables.CATEGORY_SUBMIT_STATUS); List<Category>
		 * findCategoryByChidNode = categoryService.findCategoryByChidNode(map);
		 * 
		 * Set<String> set=new HashSet<String>(); for (Category cate :
		 * findCategoryByChidNode){ set.add(cate.getParentId()); CategoryTree
		 * trees=new CategoryTree(); trees.setId(cate.getId());
		 * trees.setName(cate.getName()); trees.setpId(cate.getParentId());
		 * List<Category> cList = categoryService.findTreeByPid(cate.getId());
		 * if (cList != null && cList.size() > 0){ trees.setIsParent("true"); }
		 * else { trees.setIsParent("false"); }
		 * trees.setClassify(cate.getClassify()+"");
		 * trees.setPubStatus(cate.getIsPublish());
		 * trees.setStatus(cate.getParamStatus());
		 * trees.setAuditAdvise(cate.getAuditAdvise());
		 * trees.setAuditDate(cate.getAuditDate()); list.add(trees); }
		 * 
		 * Iterator it=set.iterator(); while(it.hasNext()){ HashMap<String,
		 * Object> maps=new HashMap<String, Object>(); maps.put("id",
		 * it.next()); List<Category> findCategoryByParentNode =
		 * categoryService.findCategoryByParentNode(maps); for(Category cate :
		 * findCategoryByParentNode){ CategoryTree trees=new CategoryTree();
		 * trees.setId(cate.getId()); trees.setName(cate.getName());
		 * trees.setpId(cate.getParentId()); List<Category> cList =
		 * categoryService.findTreeByPid(cate.getId()); if (cList != null &&
		 * cList.size() > 0){ trees.setIsParent("true"); } else {
		 * trees.setIsParent("false"); }
		 * trees.setClassify(cate.getClassify()+"");
		 * trees.setPubStatus(cate.getIsPublish());
		 * trees.setStatus(cate.getParamStatus()); list.add(trees); } } }
		 */
		return list;
	}

}
