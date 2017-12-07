package ses.controller.sys;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.constants.SupplierConstants;
import ses.dao.bms.CategoryMapper;
import ses.dao.sms.SupplierItemMapper;
import ses.dao.sms.SupplierMapper;
import ses.dao.sms.SupplierTypeRelateMapper;
import ses.model.bms.Area;
import ses.model.bms.Category;
import ses.model.bms.DictionaryData;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierItem;
import ses.model.sms.SupplierMatEng;
import ses.model.sms.TestSupplier;
import ses.service.bms.AreaServiceI;
import ses.service.sms.SupplierMatEngService;
import ses.util.Constant;
import ses.util.DictionaryDataUtil;
import bss.util.ExcelUtils;

/**
 * @Description: 测试类
 * @author yggc
 * @date 2017-12-4 下午2:20:25
 */
@Controller
@RequestMapping("/my_test/supplier/")
public class TestSupplierController {
	
//	@Autowired
//	private SupplierService supplierService;
	@Autowired
	private SupplierMapper supplierMapper;
	@Autowired
	private SupplierItemMapper supplierItemMapper;
	@Autowired
	private SupplierTypeRelateMapper supplierTypeRelateMapper;
//	@Autowired
//	private SupplierAuditMapper supplierAuditMapper;
	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private SupplierMatEngService supplierMatEngService;
	@Autowired
	private AreaServiceI areaService;
	
    @RequestMapping("/exportExcel")
    public void exportExcel(HttpServletResponse httpServletResponse, Supplier supplier) {
        ExcelUtils excelUtils = new ExcelUtils(httpServletResponse, "供应商信息", "sheet1", 3000);
        // 设置冻结行
        excelUtils.setFreezePane(true);
        excelUtils.setFreezePane(new Integer[]{0, 1, 0, 1});
        // 设置序号列
        excelUtils.setOrder(true);
        //ExcelUtils excelUtils = new ExcelUtils("./test.xls", "sheet1");
        supplier.setSupplierTypeIds("PROJECT");
        List<TestSupplier> dataList = getSupplierData(supplier);
        String titleColumn[] = {"orderNum", "supplierName", "businessNature", "address", 
        		"armyBusinessName", "armyBuinessTelephone", "armyBuinessMobile",
        		"isHavingConAchi", "businessScope",
                "supplierType", "firstNode", "secondNode", "supplierItemIds",
                "statusString", "returnCount", "lastReturnDate", "instorageAt",
                "orgName", "auditor", "depPhone"};
        String titleName[] = {"序号", "供应商名称", "企业性质", "住所地址", 
        		"军队联系人", "联系手机", "联系固话", 
        		"是否有国家或军队保密工程业绩", "承揽业务范围",
                "供应商类型", "产品目录大类", "产品目录中类", "产品类别",
                "状态", "退回修改次数", "最近一次退回修改时间", "入库时间",
                "采购机构", "审核人", "联系电话"};
        int titleSize[] = {5, 40, 10, 35, 20, 13, 13, 10, 30, 20, 30, 70, 70, 22, 10, 22, 22, 22, 15, 15};
        excelUtils.wirteExcel(titleColumn, titleName, titleSize, dataList);
    }
    
    private List<TestSupplier> getSupplierData(Supplier supplier) {
    	List<TestSupplier> resultList = new ArrayList<TestSupplier>();
        // 封装供应商类型查询条件
        String supplierTypeIds = supplier.getSupplierTypeIds();
        if(StringUtils.isNotEmpty(supplierTypeIds)){
            List<String> listSupplierTypeIds = Arrays.asList(supplierTypeIds.split(","));
            supplier.setItemType(listSupplierTypeIds);
        }
        // 封装品目查询条件
        String queryCategory = supplier.getQueryCategory();
        if(StringUtils.isNotEmpty(queryCategory)){
            List<String> strings = Arrays.asList(queryCategory.split(","));
            supplier.setQueryCategorys(strings);
        }
        List<Supplier> suppliers = null;
        // 供应商状态区分状态
        Integer supplierStatus = supplier.getStatus();
        if(supplierStatus != null){
            switch (supplierStatus) {
                case 100:// 审核中
                    supplier.setAuditTemporary(1);
                    supplier.setStatus(null);
                    break;
                case 200:// 复核中
                    supplier.setAuditTemporary(2);
                    supplier.setStatus(null);
                    break;
                case 300:// 考察中
                    supplier.setAuditTemporary(3);
                    supplier.setStatus(null);
                    break;
                default:
                    supplier.setAuditTemporary(0);
                    break;
            }
            if(supplierStatus == 400){// 无产品供应商
                supplier.setStatus(null);
                supplier.setAuditTemporary(null);
                suppliers = supplierMapper.selectSupplierListByNoCate(supplier);
            }else{
                suppliers = supplierMapper.querySupplierList(supplier);
            }
        }else{
            suppliers = supplierMapper.querySupplierList(supplier);
        }
        if (suppliers != null && !suppliers.isEmpty()) {
            Map<String, Object> param = new HashMap<>();
            // 定义数据封装对象
            StringBuffer supTypeSB = new StringBuffer(); // 供应商类型
            StringBuffer supCategrySB = new StringBuffer(); // 供应商品目（小类）-分类型
            StringBuffer supCategrySB2 = new StringBuffer(); // // 供应商品目（小类）
            StringBuffer areasSb = new StringBuffer(); // 供应商地址
            String product = Constant.SUPPLIER_PRODUCT_NAME;
            String sales = Constant.SUPPLIER_SALES_NAME;
            String project = Constant.SUPPLIER_PROJECT_NAME;
            String server = Constant.SUPPLIER_SERVICE_NAME;
            //List<SupplierAudit> supNoPassType = null;
            List<String> supplierTypes = null;
            List<String> list = null;
            Area area = null;
            for (Supplier sup : suppliers) {
            	StringBuffer firstNodeSb = new  StringBuffer();
                StringBuffer secondNodeSb = new  StringBuffer();
                StringBuffer thirdNodeSb = new  StringBuffer();
                StringBuffer fourthNodeSb = new  StringBuffer();
                // 企业性质
                if (sup.getBusinessNature() != null) {
                    DictionaryData dict = DictionaryDataUtil.findById(sup.getBusinessNature());
                    if (dict != null) {
                        sup.setBusinessNature(dict.getName());
                    }
                }
                // 供应商类型和产品类别
                // 根据供应商ID查询供应商类型
                supplierTypes = supplierTypeRelateMapper.findTypeBySupplierId(sup.getId());
                // 查询供应商审核不通过类型
                param.put("supplierId", sup.getId());
                param.put("auditType", ses.util.Constant.SUPPLIER_CATE_INFO_ITEM_FLAG);
                /*supNoPassType = supplierAuditMapper.selectBySupIdAndType(param);
                list = new ArrayList<>();
                if (supNoPassType != null && !supNoPassType.isEmpty()) {
                    for (SupplierAudit supAudit : supNoPassType) {
                        list.add(supAudit.getType());
                    }
                    // 排除审核不通过的类型
                    supplierTypes.removeAll(list);
                }*/
                param.clear();

                if (supplierTypes != null && !supplierTypes.isEmpty()) {
                    for (String supType : supplierTypes) {
                    	param.put("supplierId", sup.getId());
                    	param.put("type", supType);
                        // 物资生产
                        if (Constant.SUPPLIER_PRODUCT.equals(supType)) {
                            supTypeSB.append(product).append("、");
                            supCategrySB.append(product).append("：");
                            firstNodeSb.append(product).append("：");
                            secondNodeSb.append(product).append("：");
                        }
                        // 物资销售
                        if (Constant.SUPPLIER_SALES.equals(supType)) {
                            supTypeSB.append(sales).append("、");
                            supCategrySB.append(sales).append("：");
                            firstNodeSb.append(sales).append("：");
                            secondNodeSb.append(sales).append("：");
                        }
                        // 工程
                        if (Constant.SUPPLIER_PROJECT.equals(supType)) {
                            supTypeSB.append(project).append("、");
                            supCategrySB.append(project).append("：");
                            firstNodeSb.append(project).append("：");
                            secondNodeSb.append(project).append("：");
                        }
                        // 服务
                        if (Constant.SUPPLIER_SERVICE.equals(supType)) {
                            supTypeSB.append(server).append("、");
                            supCategrySB.append(server).append("：");
                            firstNodeSb.append(server).append("：");
                            secondNodeSb.append(server).append("：");
                        }
                        // 查询该类型下通过的产品类别
                        if (Constant.SUPPLIER_SALES.equals(supType)) {
                            param.put("items_sales_page", ses.util.Constant.ITEMS_SALES_PAGE);
                        } else {
                            param.put("items_product_page", ses.util.Constant.ITEMS_PRODUCT_PAGE);
                        }
                        // 查询该某类型下的所通过的子节点
                        list = new ArrayList<>();
                        //List<SupplierItem> supplierItems = supplierItemMapper.selectPassItemByCond(param);
                        List<SupplierItem> supplierItems = supplierItemMapper.findByMap(param);
                        StringBuffer firstNode = new  StringBuffer();
                        StringBuffer secondNode = new  StringBuffer();
                        StringBuffer thirdNode = new  StringBuffer();
                        StringBuffer fourthNode = new  StringBuffer();
                        if (supplierItems != null && !supplierItems.isEmpty()) {
                            for (SupplierItem supplierItem : supplierItems) {
                            	if(supplierItem.getNodeLevel() != null && supplierItem.getNodeLevel() == 3){
                            		list.add(supplierItem.getCategoryId());
                            	}
                            	List < Category > parentNodeList = categoryMapper.selectPListById(supplierItem.getCategoryId());
                        		if(parentNodeList != null){
                        			if(parentNodeList.size() > 0){
                        				String indexOf = parentNodeList.get(0).getName() + "、";
                        				if(firstNode.indexOf(indexOf) == -1){
                        					firstNode.append(parentNodeList.get(0).getName()).append("、");
                        				}
                        			}
                        			if(parentNodeList.size() > 1){
                        				String indexOf = parentNodeList.get(1).getName() + "、";
                        				if(secondNode.indexOf(indexOf) == -1){
                        					secondNode.append(parentNodeList.get(1).getName()).append("、");
                        				}
                        			}
                        			if(parentNodeList.size() > 2){
                        				String indexOf = parentNodeList.get(2).getName() + "、";
                        				if(thirdNode.indexOf(indexOf) == -1){
                        					thirdNode.append(parentNodeList.get(2).getName()).append("、");
                        				}
                        			}
                        			if(parentNodeList.size() > 3){
                        				String indexOf = parentNodeList.get(3).getName() + "、";
                        				if(fourthNode.indexOf(indexOf) == -1){
                        					fourthNode.append(parentNodeList.get(3).getName()).append("、");
                        				}
                        			}
                        		}else{
                        			firstNode.append("无产品").append("、");
                    				secondNode.append("无产品").append("、");
                    				thirdNode.append("无产品").append("、");
                    				fourthNode.append("无产品").append("、");
                        		}
                            }
                        }else{
                        	firstNode.append("无产品").append("、");
            				secondNode.append("无产品").append("、");
            				thirdNode.append("无产品").append("、");
            				fourthNode.append("无产品").append("、");
                        }
                        if(firstNode.indexOf("、") != -1){
                        	firstNodeSb.append(firstNode.substring(0, firstNode.lastIndexOf("、"))).append(" ");
                        }
                        if(secondNode.indexOf("、") != -1){
                        	secondNodeSb.append(secondNode.substring(0, secondNode.lastIndexOf("、"))).append(" ");
                        }
                        if(thirdNode.indexOf("、") != -1){
                        	thirdNodeSb.append(thirdNode.substring(0, thirdNode.lastIndexOf("、"))).append(" ");
                        }
                        if(fourthNode.indexOf("、") != -1){
                        	fourthNodeSb.append(fourthNode.substring(0, fourthNode.lastIndexOf("、"))).append(" ");
                        }
                        // 查询节点名称
                        param.clear();
                        //System.out.println(supplier.getId() + ":" + list.size()+"==========================");
                        List<String> newList = new ArrayList<>();
                        int i = 0;
                        int j = 999;
                        List<String> strings = null;
                        List<String> cateNames = null;
                        if (!list.isEmpty()) {
                        	strings = new ArrayList<>();
                        	if(list.size() > 1000){
                        		while(true){
                            		if(list.size() > 1000){
                            			newList = list.subList(i, j);
                            			param.put("categorys", newList);
                            			cateNames = categoryMapper.selectMoreCategoryByIds(param);
                            			strings.addAll(cateNames);
                            			list.removeAll(newList);
                            		}else{
                            			param.put("categorys", list);
                            			cateNames = categoryMapper.selectMoreCategoryByIds(param);
                            			strings.addAll(cateNames);
                                		break;
                                	}
                            	}
                        	} else {
                    			param.put("categorys", list);
                    			strings = categoryMapper.selectMoreCategoryByIds(param);
                    		}
                        	if (strings != null && !strings.isEmpty()) {
                                for (String cateName : strings) {
                                    supCategrySB.append(cateName).append("、");
                                }
                            }
                        } else {
                            supCategrySB.append("无产品").append("、");
                        }
                        if(!supCategrySB.toString().isEmpty()){
                        	if(supCategrySB.indexOf("、") != -1){
                        		String substring = supCategrySB.substring(0, supCategrySB.lastIndexOf("、"));
                                supCategrySB2.append(substring + " ");
                        	}
                            supCategrySB.delete(0, supCategrySB.length());
                        }
                    }
                    if (supTypeSB.length() > 0) {
                        // 设置供应商类型
                        sup.setSupplierType(supTypeSB.substring(0, supTypeSB.lastIndexOf("、")));
                    } else {
                        sup.setSupplierType(null);
                    }
                    sup.setSupplierItemIds(supCategrySB2.toString());
                }

                // 地址信息
                // 注册详细地址信息
                String contactAddress = sup.getContactAddress();
                // 通过address获取省级加市级地址
                area = sup.getArea();
                if (area != null) {
                    sup.setAddress(areasSb.append(area.getName()).append(" ").append(sup.getAreaName())
                            .append(" ").append(contactAddress == null ? "" : contactAddress).toString());
                }
                // 状态
                Integer auditTemporary = sup.getAuditTemporary();
                if(auditTemporary != null && auditTemporary != 0){
                    sup.setStatusString(SupplierConstants.STATUSMAP_AUDITTEMPORARY.get(auditTemporary));
                } else {
                    sup.setStatusString(SupplierConstants.STATUSMAP.get(sup.getStatus()));
                }

                // 清空数据封装
                supTypeSB.delete(0, supTypeSB.length());
                supCategrySB2.delete(0, supCategrySB2.length());
                areasSb.delete(0, areasSb.length());
                
                TestSupplier testSupplier = new TestSupplier();
                testSupplier.setSupplierName(sup.getSupplierName());
                testSupplier.setStatusString(sup.getStatusString());
                testSupplier.setSupplierItemIds(sup.getSupplierItemIds());
                testSupplier.setAddress(sup.getAddress());
                testSupplier.setBusinessNature(sup.getBusinessNature());
                testSupplier.setSupplierType(sup.getSupplierType());
                testSupplier.setArmyBusinessName(sup.getArmyBusinessName());
                testSupplier.setArmyBuinessTelephone(sup.getArmyBuinessTelephone());
                testSupplier.setArmyBuinessMobile(sup.getArmyBuinessMobile());
                testSupplier.setInstorageAt(sup.getInstorageAt());
                testSupplier.setReturnCount(sup.getReturnCount());
                testSupplier.setLastReturnDate(sup.getLastReturnDate());
                testSupplier.setOrgName(sup.getOrgName());
                testSupplier.setDepPhone(sup.getDepPhone());
                testSupplier.setAuditor(sup.getAuditor());
                
                if(supplierTypeIds != null && supplierTypeIds.contains("PROJECT")){
                	SupplierMatEng matEng = supplierMatEngService.getMatEngBasicBySupplierId(sup.getId());
                	if(matEng != null){
                		if("1".equals(matEng.getIsHavingConAchi())){
                			testSupplier.setIsHavingConAchi("是");
                		}else{
                			testSupplier.setIsHavingConAchi("否");
                		}
                		String businessScope = matEng.getBusinessScope();
                		String businessScopeName = "";
                		if(businessScope != null){
                			String[] areaIdAry = businessScope.split(",");
                			if(areaIdAry != null){
                				for(String areaId : areaIdAry){
                					Area areaById = areaService.listById(areaId);
                					if(areaById != null){
                						businessScopeName += areaById.getName() + ",";
                					}
                    			}
                			}
                		}
                		if(businessScopeName.contains(",")){
                			businessScopeName = businessScopeName.substring(0, businessScopeName.lastIndexOf(","));
                		}
                		testSupplier.setBusinessScope(businessScopeName);
                	}
                }
                
                testSupplier.setFirstNode(firstNodeSb.toString());
                testSupplier.setSecondNode(secondNodeSb.toString());
                testSupplier.setThirdNode(thirdNodeSb.toString());
                testSupplier.setFourthNode(fourthNodeSb.toString());
                resultList.add(testSupplier);
            }
        }
        return resultList;
    }
    
}
