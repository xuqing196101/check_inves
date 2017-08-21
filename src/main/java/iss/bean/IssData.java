package iss.bean;

import iss.model.ps.Article;
import iss.service.ps.ArticleService;
import ses.model.ems.Expert;
import ses.model.ems.ExpertPublicity;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierPublicity;
import ses.service.ems.ExpertAuditService;
import ses.service.ems.ExpertService;
import ses.service.sms.SupplierAuditService;
import ses.service.sms.SupplierLevelService;
import ses.service.sms.SupplierService;
import synchro.util.SpringBeanUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 *@Title:IssData
 *@Description:首页信息类,在自定义jstl标签中使用
 *@author tianzhiqiang
 *@date 2017-3-30 15:50:22
 */

public class IssData {

	// 专家名录
	public static List<Expert> getExpertList() {
		Expert expert=new Expert();
		ExpertService expertService=SpringBeanUtil.getBean(ExpertService.class);
		 //只显示公开的
		//expert.setIsPublish(1);
        Map<String, Object> expertMap = new HashMap<>();
        int statusArray[] = {4,6,7,8};
        expertMap.put("size", statusArray.length);
        expertMap.put("statusArray", statusArray);
        expertMap.put("flag", 1);
        List<Expert> expertList = expertService.selectIndexExpert(1, expertMap);
        return expertList;
	}
	
	//供应商名录
	public static List<Supplier> getSupplierList() {
		SupplierService suppService=SpringBeanUtil.getBean(SupplierService.class);
        Map<String, Object> sMap = new HashMap<String, Object>();

        //只显示公开的
        //sMap.put("IS_PUBLISH", 1);
        int statusArray[] = {1,4,5,6,7,8};
        sMap.put("size", statusArray.length);
        sMap.put("statusArray", statusArray);
        List<Supplier> supplierList = suppService.query(sMap);
        return supplierList;
	}
	
	//测试方法
	 public static String sayHello(String name) {  
	      return "Hello " + name;  
	 }
	 
	 //临时复制过来，解决首页公告信息不能显示
	 public static void topNews(Map<String, Object> indexMapper){
		 ArticleService articleService=SpringBeanUtil.getBean(ArticleService.class);
			Map<String, Object> map = new HashMap<String, Object>();
			map.clear();
			String[] idArray = {"3","24"};
			map.put("idArray", idArray);
			
			
			List<Article> xinxicaiwuziList = articleService.selectAllByParId(map);
//			xinxicaiwuziList.addAll(article3List);
//			xinxicaiwuziList.addAll(article24List);
			indexMapper.put("xinxicaiwuziList", xinxicaiwuziList);
//			List<Article> xinxicaigongchengList = new ArrayList<Article>();
			map.clear();
			String[] idArray1 = {"8","29"};
			map.put("idArray", idArray1);
			List<Article> xinxicaigongchengList = articleService.selectAllByParId(map);
//			xinxicaigongchengList.addAll(article8List);
//			xinxicaigongchengList.addAll(article29List);
			indexMapper.put("xinxicaigongchengList", xinxicaigongchengList);
//			List<Article> xinxicaifuwuList = new ArrayList<Article>();
			map.clear();
			String[] idArray2 = {"13","34"};
			map.put("idArray", idArray2);
			List<Article> xinxicaifuwuList = articleService.selectAllByParId(map);
//			xinxicaifuwuList.addAll(article13List);
//			xinxicaifuwuList.addAll(article34List);
			indexMapper.put("xinxicaifuwuList", xinxicaifuwuList);
//			List<Article> xinxicaijinkouList = new ArrayList<Article>();
			map.clear();
			String[] idArray3 = {"18","39"};
			map.put("idArray", idArray3);
			List<Article> xinxicaijinkouList = articleService.selectAllByParId(map);
//			xinxicaijinkouList.addAll(article18List);
//			xinxicaijinkouList.addAll(article39List);
			indexMapper.put("xinxicaijinkouList", xinxicaijinkouList);
//			List<Article> xinxizhongwuziList = new ArrayList<Article>();
			map.clear();
			String[] idArray4 = {"46","67"};
			map.put("idArray", idArray4);
			List<Article> xinxizhongwuziList = articleService.selectAllByParId(map);
//			xinxizhongwuziList.addAll(article46List);
//			xinxizhongwuziList.addAll(article67List);
			indexMapper.put("xinxizhongwuziList", xinxizhongwuziList);
//			List<Article> xinxizhonggongchengList = new ArrayList<Article>();
			map.clear();
			String[] idArray5 = {"51","72"};
			map.put("idArray", idArray5);
			List<Article> xinxizhonggongchengList = articleService.selectAllByParId(map);
//			xinxizhonggongchengList.addAll(article51List);
//			xinxizhonggongchengList.addAll(article72List);
			indexMapper.put("xinxizhonggongchengList", xinxizhonggongchengList);
//			List<Article> xinxizhongfuwuList = new ArrayList<Article>();
			map.clear();
			String[] idArray6 = {"56","77"};
			map.put("idArray", idArray6);
			List<Article> xinxizhongfuwuList = articleService.selectAllByParId(map);
//			xinxizhongfuwuList.addAll(article56List);
//			xinxizhongfuwuList.addAll(article77List);
			indexMapper.put("xinxizhongfuwuList", xinxizhongfuwuList);
//			List<Article> xinxizhongjinkouList = new ArrayList<Article>();
			map.clear();
			String[] idArray7 = {"61","82"};
			map.put("idArray", idArray7);
			List<Article> xinxizhongjinkouList = articleService.selectAllByParId(map);
//			xinxizhongjinkouList.addAll(article61List);
//			xinxizhongjinkouList.addAll(article82List);
			indexMapper.put("xinxizhongjinkouList", xinxizhongjinkouList);
//			List<Article> xinxidanwuziList = new ArrayList<Article>();
			map.clear();
			String[] idArray8 = {"89","94"};
			map.put("idArray", idArray8);
			List<Article> xinxidanwuziList = articleService.selectAllByArticleType(map);
//			xinxidanwuziList.addAll(article89List);
//			xinxidanwuziList.addAll(article94List);
			indexMapper.put("xinxidanwuziList", xinxidanwuziList);
//			List<Article> xinxidangongchengList = new ArrayList<Article>();
			map.clear();
			String[] idArray9 = {"90","95"};
			map.put("idArray", idArray9);
			List<Article> xinxidangongchengList = articleService.selectAllByArticleType(map);
//			xinxidangongchengList.addAll(article90List);
//			xinxidangongchengList.addAll(article95List);
			indexMapper.put("xinxidangongchengList", xinxidangongchengList);
//			List<Article> xinxidanfuwuList = new ArrayList<Article>();
			map.clear();
			String[] idArray10 = {"91","96"};
			map.put("idArray", idArray10);
			List<Article> xinxidanfuwuList = articleService.selectAllByArticleType(map);
//			xinxidanfuwuList.addAll(article91List);
//			xinxidanfuwuList.addAll(article96List);
			indexMapper.put("xinxidanfuwuList", xinxidanfuwuList);
//			List<Article> xinxidanjinkouList = new ArrayList<Article>();
			map.clear();
			String[] idArray11 = {"92","97"};
			map.put("idArray", idArray11);
			List<Article> xinxidanjinkouList = articleService.selectAllByArticleType(map);
//			xinxidanjinkouList.addAll(article92List);
//			xinxidanjinkouList.addAll(article97List);
			indexMapper.put("xinxidanjinkouList", xinxidanjinkouList);
			map.clear();
			map.put("typeId","103");
			List<Article> article103List = articleService.selectArticleByArticleType(map);
			indexMapper.put("select103List", article103List);
			map.clear();
			map.put("typeId","104");
			List<Article> article104List = articleService.selectArticleByArticleType(map);
			indexMapper.put("select104List", article104List);
			map.clear();
			map.put("typeId","105");
			List<Article> article105List = articleService.selectArticleByArticleType(map);
			indexMapper.put("select105List", article105List);
			map.clear();
			map.put("typeId","112");
			List<Article> article112List = articleService.selectArticleByArticleType(map);
			indexMapper.put("select112List", article112List);
			map.clear();
			map.put("typeId","107");
			List<Article> article107List = articleService.selectArticleByArticleType(map);
			indexMapper.put("select107List", article107List);
			map.clear();
			map.put("typeId","108");
			List<Article> article108List = articleService.selectArticleByArticleType(map);
			indexMapper.put("select108List", article108List);
			map.clear();
			map.put("typeId","109");
			List<Article> article109List = articleService.selectArticleByArticleType(map);
			indexMapper.put("select109List", article109List);
			map.clear();
	    map.put("typeId","115");
	    List<Article> article115List = articleService.selectArticleByArticleType(map);
	    indexMapper.put("article115List", article115List);
	    map.clear();
	    map.put("typeId","116");
	    List<Article> article116List = articleService.selectArticleByArticleType(map);
	    indexMapper.put("article116List", article116List);
	    map.clear();
	    map.put("typeId","117");
	    List<Article> article117List = articleService.selectArticleByArticleType(map);
	    indexMapper.put("article117List", article117List);
		}
	 
	 // 供应商诚信记录
	 public static Map<String, Object> getSupplierCreditRecord() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", 1);
	 	SupplierLevelService levelService = SpringBeanUtil.getBean(SupplierLevelService.class);
	 	return levelService.findSupplierCreditIndex(map);
	}
	
	// 查询供应商公示列表
	public static List<SupplierPublicity> getPublicitySupplier(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", 1);
		SupplierAuditService supplierAuditService = SpringBeanUtil.getBean(SupplierAuditService.class);
		return supplierAuditService.selectSupByPublictyList(map);
	}
	 
	// 查询专家公示列表 
	public static List<ExpertPublicity> getPublicityExpert(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", 1);
		ExpertAuditService expertAuditService = SpringBeanUtil.getBean(ExpertAuditService.class);
		return expertAuditService.selectExpByPublictyList(map);
	}

}
