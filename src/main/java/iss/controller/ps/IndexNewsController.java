
package iss.controller.ps;

import bss.model.ob.OBProduct;
import bss.model.ob.OBSupplier;
import bss.service.ob.OBProductService;
import bss.service.ob.OBSupplierService;
import com.github.pagehelper.PageInfo;
import common.constant.Constant;
import common.constant.OnlineBidding;
import common.model.UploadFile;
import common.service.UploadService;
import common.utils.CommonStringUtil;
import common.utils.JdcgResult;
import common.utils.RequestTool;
import common.utils.UploadUtil;
import gui.ava.html.image.generator.HtmlImageGenerator;
import iss.model.hl.ServiceHotline;
import iss.model.ps.Article;
import iss.model.ps.ArticleAttachments;
import iss.model.ps.ArticleType;
import iss.model.ps.DownloadUser;
import iss.service.hl.ServiceHotlineService;
import iss.service.ps.ArticleAttachmentsService;
import iss.service.ps.ArticleService;
import iss.service.ps.ArticleTypeService;
import iss.service.ps.DownloadUserService;
import iss.service.ps.IndexNewsService;
import iss.service.ps.SearchService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ses.controller.sys.sms.BaseSupplierController;
import ses.model.bms.Category;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.ems.Expert;
import ses.model.ems.ExpertBlackList;
import ses.model.ems.ExpertBlackListVO;
import ses.model.ems.ExpertPublicity;
import ses.model.oms.PurchaseDep;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierBlacklist;
import ses.model.sms.SupplierBlacklistVO;
import ses.model.sms.SupplierPublicity;
import ses.model.sms.SupplierTypeTree;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.ems.ExpertAuditService;
import ses.service.ems.ExpertBlackListService;
import ses.service.ems.ExpertCategoryService;
import ses.service.ems.ExpertService;
import ses.service.oms.PurChaseDepOrgService;
import ses.service.sms.SupplierAuditService;
import ses.service.sms.SupplierBlacklistService;
import ses.service.sms.SupplierItemService;
import ses.service.sms.SupplierService;
import ses.service.sms.SupplierTypeService;
import ses.util.DictionaryDataUtil;
import ses.util.FtpUtil;
import ses.util.PropUtil;
import ses.util.PropertiesUtil;
import synchro.util.SpringBeanUtil;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/*
 *@Title:IndexNewsController
 *@Description:首页信息控制类
 *@author QuJie
 *@date 2016-8-29上午8:50:22
 */
@Controller
@Scope("prototype")
@RequestMapping("/index")
public class IndexNewsController extends BaseSupplierController{
	
	@Autowired
	private IndexNewsService indexNewsService;
	
	@Autowired
	private ArticleTypeService articleTypeService;
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private ArticleAttachmentsService articleAttachmentsService;
	
	@Autowired
	private DownloadUserService downloadUserService;
	
	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;
	
	@Autowired
	private UploadService uploadService;	
	
	@Autowired
	private SearchService searchService;
	
	@Autowired
	private SupplierBlacklistService supplierBlacklistService;
	@Autowired
	private ExpertBlackListService expertBlackListService;
	
    /**竞价产品Service**/
    @Autowired
    private OBProductService oBProductService;
    
    /**竞价供应商Service**/
    @Autowired
    private OBSupplierService oBSupplierService;
    
    /**采购目录管理接口Service**/
    @Autowired
    private CategoryService categoryService;
    
    /** 服务热线 **/
    @Autowired
	private ServiceHotlineService serviceHotlineService;
    
    // 注入专家审核Service
    @Autowired
    private ExpertAuditService expertAuditService;
    
    // 注入供应商审核Service
    @Autowired
    private SupplierAuditService supplierAuditService;

    // 注入供应商产品类别
    @Autowired
    private SupplierItemService supplierItemService;

    @Autowired
    private ExpertService expertService;

    @Autowired
    private ExpertCategoryService expertCategoryService;

    // 注入供应商类型Service
    @Autowired
    private SupplierTypeService supplierTypeService;

	// 注入采购机构Service
	@Autowired
	private PurChaseDepOrgService purChaseDepOrgService;


	/**
	 *
	* @Title: sign
	* @author Peng Zhongjun
	* @date 2016-11-10 上午8:50:09
	* @Description: 跳转登录页面
	* @param @return
	* @return String
	 */
	@RequestMapping("/sign")
	public String sign(){
		return "iss/ps/index/sign";
	}

	public void topNews(Map<String, Object> indexMapper){
		Map<String, Object> map = new HashMap<String, Object>();
		map.clear();
		String[] idArray = {"3","24"};
		map.put("idArray", idArray);
		List<Article> xinxicaiwuziList = articleService.selectAllByParId(map);
//		xinxicaiwuziList.addAll(article3List);
//		xinxicaiwuziList.addAll(article24List);
		indexMapper.put("xinxicaiwuziList", xinxicaiwuziList);
//		List<Article> xinxicaigongchengList = new ArrayList<Article>();
		map.clear();
		String[] idArray1 = {"8","29"};
		map.put("idArray", idArray1);
		List<Article> xinxicaigongchengList = articleService.selectAllByParId(map);
//		xinxicaigongchengList.addAll(article8List);
//		xinxicaigongchengList.addAll(article29List);
		indexMapper.put("xinxicaigongchengList", xinxicaigongchengList);
//		List<Article> xinxicaifuwuList = new ArrayList<Article>();
		map.clear();
		String[] idArray2 = {"13","34"};
		map.put("idArray", idArray2);
		List<Article> xinxicaifuwuList = articleService.selectAllByParId(map);
//		xinxicaifuwuList.addAll(article13List);
//		xinxicaifuwuList.addAll(article34List);
		indexMapper.put("xinxicaifuwuList", xinxicaifuwuList);
//		List<Article> xinxicaijinkouList = new ArrayList<Article>();
		map.clear();
		String[] idArray3 = {"18","39"};
		map.put("idArray", idArray3);
		List<Article> xinxicaijinkouList = articleService.selectAllByParId(map);
//		xinxicaijinkouList.addAll(article18List);
//		xinxicaijinkouList.addAll(article39List);
		indexMapper.put("xinxicaijinkouList", xinxicaijinkouList);
//		List<Article> xinxizhongwuziList = new ArrayList<Article>();
		map.clear();
		String[] idArray4 = {"46","67"};
		map.put("idArray", idArray4);
		List<Article> xinxizhongwuziList = articleService.selectAllByParId(map);
//		xinxizhongwuziList.addAll(article46List);
//		xinxizhongwuziList.addAll(article67List);
		indexMapper.put("xinxizhongwuziList", xinxizhongwuziList);
//		List<Article> xinxizhonggongchengList = new ArrayList<Article>();
		map.clear();
		String[] idArray5 = {"51","72"};
		map.put("idArray", idArray5);
		List<Article> xinxizhonggongchengList = articleService.selectAllByParId(map);
//		xinxizhonggongchengList.addAll(article51List);
//		xinxizhonggongchengList.addAll(article72List);
		indexMapper.put("xinxizhonggongchengList", xinxizhonggongchengList);
//		List<Article> xinxizhongfuwuList = new ArrayList<Article>();
		map.clear();
		String[] idArray6 = {"56","77"};
		map.put("idArray", idArray6);
		List<Article> xinxizhongfuwuList = articleService.selectAllByParId(map);
//		xinxizhongfuwuList.addAll(article56List);
//		xinxizhongfuwuList.addAll(article77List);
		indexMapper.put("xinxizhongfuwuList", xinxizhongfuwuList);
//		List<Article> xinxizhongjinkouList = new ArrayList<Article>();
		map.clear();
		String[] idArray7 = {"61","82"};
		map.put("idArray", idArray7);
		List<Article> xinxizhongjinkouList = articleService.selectAllByParId(map);
//		xinxizhongjinkouList.addAll(article61List);
//		xinxizhongjinkouList.addAll(article82List);
		indexMapper.put("xinxizhongjinkouList", xinxizhongjinkouList);
//		List<Article> xinxidanwuziList = new ArrayList<Article>();
		map.clear();
		String[] idArray8 = {"89","94"};
		map.put("idArray", idArray8);
		List<Article> xinxidanwuziList = articleService.selectAllByArticleType(map);
//		xinxidanwuziList.addAll(article89List);
//		xinxidanwuziList.addAll(article94List);
		indexMapper.put("xinxidanwuziList", xinxidanwuziList);
//		List<Article> xinxidangongchengList = new ArrayList<Article>();
		map.clear();
		String[] idArray9 = {"90","95"};
		map.put("idArray", idArray9);
		List<Article> xinxidangongchengList = articleService.selectAllByArticleType(map);
//		xinxidangongchengList.addAll(article90List);
//		xinxidangongchengList.addAll(article95List);
		indexMapper.put("xinxidangongchengList", xinxidangongchengList);
//		List<Article> xinxidanfuwuList = new ArrayList<Article>();
		map.clear();
		String[] idArray10 = {"91","96"};
		map.put("idArray", idArray10);
		List<Article> xinxidanfuwuList = articleService.selectAllByArticleType(map);
//		xinxidanfuwuList.addAll(article91List);
//		xinxidanfuwuList.addAll(article96List);
		indexMapper.put("xinxidanfuwuList", xinxidanfuwuList);
//		List<Article> xinxidanjinkouList = new ArrayList<Article>();
		map.clear();
		String[] idArray11 = {"92","97"};
		map.put("idArray", idArray11);
		List<Article> xinxidanjinkouList = articleService.selectAllByArticleType(map);
//		xinxidanjinkouList.addAll(article92List);
//		xinxidanjinkouList.addAll(article97List);
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
    //供应商处罚公告
    //String[] idArray116 = {"116","117"};
    map.put("typeId","114");
    List<Article> article116List = articleService.selectArticleByArticleType(map);
    indexMapper.put("article116List", article116List);
    /*map.clear();
    map.put("typeId","117");
    List<Article> article117List = articleService.selectArticleByArticleType(map);
    indexMapper.put("article117List", article117List);*/
 // 供应商黑名单列表
    List<SupplierBlacklist> supplierBlackList = supplierBlacklistService.getIndexSupplierBlacklist();
    // 专家黑名单列表
    List<ExpertBlackList> expertBlackList = expertBlackListService.getIndexExpertBlackList();
    
    indexMapper.put("supplierBlackList", supplierBlackList);
    indexMapper.put("expertBlackList", expertBlackList);
	}
	
	/**
	 * 
	* @Title: selectIndexNews
	* @author QuJie 
	* @date 2016-8-29 上午8:50:02  
	* @Description: 查询全部首页信息 
	* @param @return      
	* @return List<Article>
	 */
	@RequestMapping("/selectIndexNews")
	public String selectIndexNews(Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> indexMapper = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		String[] idArray110 = {"110","111"};
		map.put("idArray", idArray110);
		List<Article> article110List = articleService.selectJob(map);
		indexMapper.put("select110List", article110List);
		map.clear();
		map.put("parId","3");
//		List<ArticleType> articleTypeList = articleTypeService.selectAllArticleTypeForSolr();
		List<Article> article3List = articleService.selectArticleByParId(map);
		indexMapper.put("select3List", article3List);
		map.clear();
		map.put("parId","8");
		List<Article> article8List = articleService.selectArticleByParId(map);
		indexMapper.put("select8List", article8List);
		map.clear();
		map.put("parId","13");
		List<Article> article13List = articleService.selectArticleByParId(map);
		indexMapper.put("select13List", article13List);
		map.clear();
		map.put("parId","18");
		List<Article> article18List = articleService.selectArticleByParId(map);
		indexMapper.put("select18List", article18List);
		map.clear();
		map.put("articleTypeId", "1");
		map.put("secondArticelTypeId", "2");
		List<Article> cjList = articleService.selectAllByTab(map);
		indexMapper.put("cjList", cjList);
		map.clear();
		map.put("parId","24");
		List<Article> article24List = articleService.selectArticleByParId(map);
		indexMapper.put("select24List", article24List);
		map.clear();
		map.put("parId","29");
		List<Article> article29List = articleService.selectArticleByParId(map);
		indexMapper.put("select29List", article29List);
		map.clear();
		map.put("parId","34");
		List<Article> article34List = articleService.selectArticleByParId(map);
		indexMapper.put("select34List", article34List);
		map.clear();
		map.put("parId","39");
		List<Article> article39List = articleService.selectArticleByParId(map);
		indexMapper.put("select39List", article39List);
		map.clear();
//		String idAr1[] = {"24","29","34","39"};
//		map.put("idArray", idAr1);
		map.put("articleTypeId", "1");
		map.put("secondArticelTypeId", "23");
		List<Article> cbList = articleService.selectAllByTab(map);
		indexMapper.put("cbList", cbList);
		map.clear();
		map.put("parId","46");
		List<Article> article46List = articleService.selectArticleByParId(map);
		indexMapper.put("select46List", article46List);
		map.clear();
		map.put("parId","51");
		List<Article> article51List = articleService.selectArticleByParId(map);
		indexMapper.put("select51List", article51List);
		map.clear();
		map.put("parId","56");
		List<Article> article56List = articleService.selectArticleByParId(map);
		indexMapper.put("select56List", article56List);
		map.clear();
		map.put("parId","61");
		List<Article> article61List = articleService.selectArticleByParId(map);
		indexMapper.put("select61List", article61List);
		map.clear();
//		String idAr2[] = {"46","51","56","61"};
//		map.put("idArray", idAr2);
		map.put("articleTypeId", "44");
		map.put("secondArticelTypeId", "45");
		List<Article> czList = articleService.selectAllByTab(map);
		indexMapper.put("czList", czList);
		map.clear();
		map.put("parId","67");
		List<Article> article67List = articleService.selectArticleByParId(map);
		indexMapper.put("select67List", article67List);
		map.clear();
		map.put("parId","72");
		List<Article> article72List = articleService.selectArticleByParId(map);
		indexMapper.put("select72List", article72List);
		map.clear();
		map.put("parId","77");
		List<Article> article77List = articleService.selectArticleByParId(map);
		indexMapper.put("select77List", article77List);
		map.clear();
		map.put("parId","82");
		List<Article> article82List = articleService.selectArticleByParId(map);
		indexMapper.put("select82List", article82List);
		map.clear();
//		String idAr3[] = {"67","72","77","82"};
//		map.put("idArray", idAr3);
		map.put("articleTypeId", "44");
		map.put("secondArticelTypeId", "66");
		List<Article> bzList = articleService.selectAllByTab(map);
		indexMapper.put("bzList", bzList);
		map.clear();
		map.put("typeId","89");
		List<Article> article89List = articleService.selectArticleByArticleType(map);
		indexMapper.put("select89List", article89List);
		map.clear();
		map.put("typeId","90");
		List<Article> article90List = articleService.selectArticleByArticleType(map);
		indexMapper.put("select90List", article90List);
		map.clear();
		map.put("typeId","91");
		List<Article> article91List = articleService.selectArticleByArticleType(map);
		indexMapper.put("select91List", article91List);
		map.clear();
		map.put("typeId","92");
		List<Article> article92List = articleService.selectArticleByArticleType(map);
		indexMapper.put("select92List", article92List);
		map.clear();
//		String idAr4[] = {"89","90","91","92"};
//		map.put("idArray", idAr4);
		map.put("articleTypeId", "87");
		map.put("secondArticelTypeId", "88");
		List<Article> jdList = articleService.selectAllByDanTab(map);
		indexMapper.put("jdList", jdList);
		map.clear();
		map.put("typeId","94");
		List<Article> article94List = articleService.selectArticleByArticleType(map);
		indexMapper.put("select94List", article94List);
		map.clear();
		map.put("typeId","95");
		List<Article> article95List = articleService.selectArticleByArticleType(map);
		indexMapper.put("select95List", article95List);
		map.clear();
		map.put("typeId","96");
		List<Article> article96List = articleService.selectArticleByArticleType(map);
		indexMapper.put("select96List", article96List);
		map.clear();
		map.put("typeId","97");
		List<Article> article97List = articleService.selectArticleByArticleType(map);
		indexMapper.put("select97List", article97List);
		map.clear();
//		String idAr5[] = {"94","95","96","97"};
//		map.put("idArray", idAr5);
		map.put("articleTypeId", "87");
		map.put("secondArticelTypeId", "93");
		List<Article> bdList = articleService.selectAllByDanTab(map);
		indexMapper.put("bdList", bdList);
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
		map.put("typeId","112");
		List<Article> article112List = articleService.selectArticleByArticleType(map);
		indexMapper.put("select112List", article112List);
		map.clear();
		map.put("typeId","113");
		List<Article> article113List = articleService.selectAllByArticleTypeId(map);
		indexMapper.put("select113List", article113List);
//		List<Article> xinxicaiwuziList = new ArrayList<Article>();
		map.clear();
    map.put("typeId","115");
    List<Article> article115List = articleService.selectArticleByArticleType(map);
    indexMapper.put("article115List", article115List);
    //首页供应商处罚公告
    map.clear();
    /*String[] idArray116 = {"116","117"};
	map.put("idArray", idArray116);*/
	map.put("typeId","114");
    List<Article> article116List = articleService.selectArticleByArticleType(map);
    indexMapper.put("article116List", article116List);
    /*map.clear();
    map.put("typeId","117");
    List<Article> article117List = articleService.selectArticleByArticleType(map);
    indexMapper.put("article117List", article117List);*/
		map.clear();
		String[] idArray = {"3","24"};
		map.put("idArray", idArray);
		List<Article> xinxicaiwuziList = articleService.selectAllByParId(map);
//		xinxicaiwuziList.addAll(article3List);
//		xinxicaiwuziList.addAll(article24List);
		indexMapper.put("xinxicaiwuziList", xinxicaiwuziList);
//		List<Article> xinxicaigongchengList = new ArrayList<Article>();
		map.clear();
		String[] idArray1 = {"8","29"};
		map.put("idArray", idArray1);
		List<Article> xinxicaigongchengList = articleService.selectAllByParId(map);
//		xinxicaigongchengList.addAll(article8List);
//		xinxicaigongchengList.addAll(article29List);
		indexMapper.put("xinxicaigongchengList", xinxicaigongchengList);
//		List<Article> xinxicaifuwuList = new ArrayList<Article>();
		map.clear();
		String[] idArray2 = {"13","34"};
		map.put("idArray", idArray2);
		List<Article> xinxicaifuwuList = articleService.selectAllByParId(map);
//		xinxicaifuwuList.addAll(article13List);
//		xinxicaifuwuList.addAll(article34List);
		indexMapper.put("xinxicaifuwuList", xinxicaifuwuList);
//		List<Article> xinxicaijinkouList = new ArrayList<Article>();
		map.clear();
		String[] idArray3 = {"18","39"};
		map.put("idArray", idArray3);
		List<Article> xinxicaijinkouList = articleService.selectAllByParId(map);
//		xinxicaijinkouList.addAll(article18List);
//		xinxicaijinkouList.addAll(article39List);
		indexMapper.put("xinxicaijinkouList", xinxicaijinkouList);
//		List<Article> xinxizhongwuziList = new ArrayList<Article>();
		map.clear();
		String[] idArray4 = {"46","67"};
		map.put("idArray", idArray4);
		List<Article> xinxizhongwuziList = articleService.selectAllByParId(map);
//		xinxizhongwuziList.addAll(article46List);
//		xinxizhongwuziList.addAll(article67List);
		indexMapper.put("xinxizhongwuziList", xinxizhongwuziList);
//		List<Article> xinxizhonggongchengList = new ArrayList<Article>();
		map.clear();
		String[] idArray5 = {"51","72"};
		map.put("idArray", idArray5);
		List<Article> xinxizhonggongchengList = articleService.selectAllByParId(map);
//		xinxizhonggongchengList.addAll(article51List);
//		xinxizhonggongchengList.addAll(article72List);
		indexMapper.put("xinxizhonggongchengList", xinxizhonggongchengList);
//		List<Article> xinxizhongfuwuList = new ArrayList<Article>();
		map.clear();
		String[] idArray6 = {"56","77"};
		map.put("idArray", idArray6);
		List<Article> xinxizhongfuwuList = articleService.selectAllByParId(map);
//		xinxizhongfuwuList.addAll(article56List);
//		xinxizhongfuwuList.addAll(article77List);
		indexMapper.put("xinxizhongfuwuList", xinxizhongfuwuList);
//		List<Article> xinxizhongjinkouList = new ArrayList<Article>();
		map.clear();
		String[] idArray7 = {"61","82"};
		map.put("idArray", idArray7);
		List<Article> xinxizhongjinkouList = articleService.selectAllByParId(map);
//		xinxizhongjinkouList.addAll(article61List);
//		xinxizhongjinkouList.addAll(article82List);
		indexMapper.put("xinxizhongjinkouList", xinxizhongjinkouList);
//		List<Article> xinxidanwuziList = new ArrayList<Article>();
		map.clear();
		String[] idArray8 = {"89","94"};
		map.put("idArray", idArray8);
		List<Article> xinxidanwuziList = articleService.selectAllByArticleType(map);
//		xinxidanwuziList.addAll(article89List);
//		xinxidanwuziList.addAll(article94List);
		indexMapper.put("xinxidanwuziList", xinxidanwuziList);
//		List<Article> xinxidangongchengList = new ArrayList<Article>();
		map.clear();
		String[] idArray9 = {"90","95"};
		map.put("idArray", idArray9);
		List<Article> xinxidangongchengList = articleService.selectAllByArticleType(map);
//		xinxidangongchengList.addAll(article90List);
//		xinxidangongchengList.addAll(article95List);
		indexMapper.put("xinxidangongchengList", xinxidangongchengList);
//		List<Article> xinxidanfuwuList = new ArrayList<Article>();
		map.clear();
		String[] idArray10 = {"91","96"};
		map.put("idArray", idArray10);
		List<Article> xinxidanfuwuList = articleService.selectAllByArticleType(map);
//		xinxidanfuwuList.addAll(article91List);
//		xinxidanfuwuList.addAll(article96List);
		indexMapper.put("xinxidanfuwuList", xinxidanfuwuList);
//		List<Article> xinxidanjinkouList = new ArrayList<Article>();
		map.clear();
		String[] idArray11 = {"92","97"};
		map.put("idArray", idArray11);
		List<Article> xinxidanjinkouList = articleService.selectAllByArticleType(map);
//		xinxidanjinkouList.addAll(article92List);
//		xinxidanjinkouList.addAll(article97List);
		indexMapper.put("xinxidanjinkouList", xinxidanjinkouList);
//		List<Article> faguiList = new ArrayList<Article>();
		map.clear();
		String[] idArray12 = {"107","108"};
		map.put("idArray", idArray12);
		List<Article> faguiList = articleService.selectAllByArticleType(map);
//		faguiList.addAll(article107List);
//		faguiList.addAll(article108List);
		indexMapper.put("faguiList", faguiList);
		map.clear();
		List<Article> picList = articleService.selectPics();
		List<Article> indexPics = null;
		if(picList.size()>0){
			indexPics = new ArrayList<Article>();
			for(Article article : picList){
				DictionaryData dd=new DictionaryData();
				dd.setCode("POST_ATTACHMENT");
				List<DictionaryData> lists = dictionaryDataServiceI.find(dd);
				String sysKey = Constant.TENDER_SYS_KEY.toString();
				String attachTypeId=null;
				if(lists.size()>0){
					attachTypeId = lists.get(0).getId();
				}
				List<UploadFile> uploadList = uploadService.getFilesOther(article.getId(), attachTypeId, sysKey);
				if(uploadList.size()>0){
					article.setUploadId(uploadList.get(0).getId());
					indexPics.add(article);
				}
			}
		}
		if(indexPics!=null){
			int num = indexPics.size();
			model.addAttribute("nums", Integer.toString(num));
		}else{
			model.addAttribute("nums", "0");
		}
		indexMapper.put("picList", indexPics);
		if(indexPics!=null){
			model.addAttribute("picSize", indexPics.size());
		}else{
			model.addAttribute("picSize", 0);
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
		Date nowdate = new Date();
		String time = sdf.format(nowdate);
		Calendar date = Calendar.getInstance();
		date.setTime(nowdate);
		date.set(Calendar.DATE, date.get(Calendar.DATE)-1);
		Date qianDate = null;
		String qiantime = "";
		try {
			qianDate = sdf.parse(sdf.format(date.getTime()));
			qiantime = sdfDate.format(qianDate);
			qiantime = qiantime+" 23:59:59";
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> timerMap = new HashMap<String, Object>();
		timerMap.put("nowTime", time);
		timerMap.put("qianDate", qiantime);
		timerMap.put("id", "3");
		BigDecimal articlejcw = articleService.selectByTimer(timerMap);
		model.addAttribute("articlejcw",articlejcw);
		timerMap.clear();
		timerMap.put("nowTime", time);
		timerMap.put("qianDate", qiantime);
		timerMap.put("articleTypeId", "1");
		timerMap.put("secondArticleTypeId", "2");
		BigDecimal jcall = articleService.selectAllByTimer(timerMap);
		model.addAttribute("jcall",jcall);
		timerMap.clear();
		timerMap.put("nowTime", time);
		timerMap.put("qianDate", qiantime);
		timerMap.put("articleTypeId", "1");
		timerMap.put("secondArticleTypeId", "23");
		BigDecimal bcall = articleService.selectAllByTimer(timerMap);
		model.addAttribute("bcall",bcall);
		timerMap.clear();
		timerMap.put("nowTime", time);
		timerMap.put("qianDate", qiantime);
		timerMap.put("articleTypeId", "44");
		timerMap.put("secondArticleTypeId", "45");
		BigDecimal jzall = articleService.selectAllByTimer(timerMap);
		model.addAttribute("jzall",jzall);
		timerMap.clear();
		timerMap.put("nowTime", time);
		timerMap.put("qianDate", qiantime);
		timerMap.put("articleTypeId", "44");
		timerMap.put("secondArticleTypeId", "66");
		BigDecimal bzall = articleService.selectAllByTimer(timerMap);
		model.addAttribute("bzall",bzall);
		timerMap.clear();
		timerMap.put("nowTime", time);
		timerMap.put("qianDate", qiantime);
		timerMap.put("articleTypeId", "87");
		timerMap.put("secondArticleTypeId", "88");
		BigDecimal jdall = articleService.selectAllByTimer(timerMap);
		model.addAttribute("jdall",jdall);
		timerMap.clear();
		timerMap.put("nowTime", time);
		timerMap.put("qianDate", qiantime);
		timerMap.put("articleTypeId", "87");
		timerMap.put("secondArticleTypeId", "93");
		BigDecimal bdall = articleService.selectAllByTimer(timerMap);
		model.addAttribute("bdall",bdall);
		timerMap.clear();
		timerMap.put("nowTime", time);
		timerMap.put("qianDate", qiantime);
		timerMap.put("id", "8");
		BigDecimal articlejcg = articleService.selectByTimer(timerMap);
		model.addAttribute("articlejcg",articlejcg);
		timerMap.clear();
		timerMap.put("nowTime", time);
		timerMap.put("qianDate", qiantime);
		timerMap.put("id", "13");
		BigDecimal articlejcf = articleService.selectByTimer(timerMap);
		model.addAttribute("articlejcf",articlejcf);
		timerMap.clear();
		timerMap.put("nowTime", time);
		timerMap.put("qianDate", qiantime);
		timerMap.put("id", "18");
		BigDecimal articlejcj = articleService.selectByTimer(timerMap);
		model.addAttribute("articlejcj",articlejcj);
		timerMap.clear();
		timerMap.put("nowTime", time);
		timerMap.put("qianDate", qiantime);
		timerMap.put("id", "24");
		BigDecimal articlebcw = articleService.selectByTimer(timerMap);
		model.addAttribute("articlebcw",articlebcw);
		timerMap.clear();
		timerMap.put("nowTime", time);
		timerMap.put("qianDate", qiantime);
		timerMap.put("id", "29");
		BigDecimal articlebcg = articleService.selectByTimer(timerMap);
		model.addAttribute("articlebcg",articlebcg);
		timerMap.clear();
		timerMap.put("nowTime", time);
		timerMap.put("qianDate", qiantime);
		timerMap.put("id", "34");
		BigDecimal articlebcf = articleService.selectByTimer(timerMap);
		model.addAttribute("articlebcf",articlebcf);
		timerMap.clear();
		timerMap.put("nowTime", time);
		timerMap.put("qianDate", qiantime);
		timerMap.put("id", "39");
		BigDecimal articlebcj = articleService.selectByTimer(timerMap);
		model.addAttribute("articlebcj",articlebcj);
		timerMap.clear();
		timerMap.put("nowTime", time);
		timerMap.put("qianDate", qiantime);
		timerMap.put("id", "46");
		BigDecimal articlejzw = articleService.selectByTimer(timerMap);
		model.addAttribute("articlejzw",articlejzw);
		timerMap.clear();
		timerMap.put("nowTime", time);
		timerMap.put("qianDate", qiantime);
		timerMap.put("id", "51");
		BigDecimal articlejzg = articleService.selectByTimer(timerMap);
		model.addAttribute("articlejzg",articlejzg);
		timerMap.clear();
		timerMap.put("nowTime", time);
		timerMap.put("qianDate", qiantime);
		timerMap.put("id", "56");
		BigDecimal articlejzf = articleService.selectByTimer(timerMap);
		model.addAttribute("articlejzf",articlejzf);
		timerMap.clear();
		timerMap.put("nowTime", time);
		timerMap.put("qianDate", qiantime);
		timerMap.put("id", "61");
		BigDecimal articlejzj = articleService.selectByTimer(timerMap);
		model.addAttribute("articlejzj",articlejzj);
		timerMap.clear();
		timerMap.put("nowTime", time);
		timerMap.put("qianDate", qiantime);
		timerMap.put("id", "67");
		BigDecimal articlebzw = articleService.selectByTimer(timerMap);
		model.addAttribute("articlebzw",articlebzw);
		timerMap.clear();
		timerMap.put("nowTime", time);
		timerMap.put("qianDate", qiantime);
		timerMap.put("id", "72");
		BigDecimal articlebzg = articleService.selectByTimer(timerMap);
		model.addAttribute("articlebzg",articlebzg);
		timerMap.clear();
		timerMap.put("nowTime", time);
		timerMap.put("qianDate", qiantime);
		timerMap.put("id", "77");
		BigDecimal articlebzf = articleService.selectByTimer(timerMap);
		model.addAttribute("articlebzf",articlebzf);
		timerMap.clear();
		timerMap.put("nowTime", time);
		timerMap.put("qianDate", qiantime);
		timerMap.put("id", "82");
		BigDecimal articlebzj = articleService.selectByTimer(timerMap);
		model.addAttribute("articlebzj",articlebzj);
		timerMap.clear();
		timerMap.put("nowTime", time);
		timerMap.put("qianDate", qiantime);
		timerMap.put("id", "89");
		BigDecimal articlejdw = articleService.selectDanByTimer(timerMap);
		model.addAttribute("articlejdw",articlejdw);
		timerMap.clear();
		timerMap.put("nowTime", time);
		timerMap.put("qianDate", qiantime);
		timerMap.put("id", "90");
		BigDecimal articlejdg = articleService.selectDanByTimer(timerMap);
		model.addAttribute("articlejdg",articlejdg);
		timerMap.clear();
		timerMap.put("nowTime", time);
		timerMap.put("qianDate", qiantime);
		timerMap.put("id", "91");
		BigDecimal articlejdf = articleService.selectDanByTimer(timerMap);
		model.addAttribute("articlejdf",articlejdf);
		timerMap.clear();
		timerMap.put("nowTime", time);
		timerMap.put("qianDate", qiantime);
		timerMap.put("id", "92");
		BigDecimal articlejdj = articleService.selectDanByTimer(timerMap);
		model.addAttribute("articlejdj",articlejdj);
		timerMap.clear();
		timerMap.put("nowTime", time);
		timerMap.put("qianDate", qiantime);
		timerMap.put("id", "94");
		BigDecimal articlebdw = articleService.selectDanByTimer(timerMap);
		model.addAttribute("articlebdw",articlebdw);
		timerMap.clear();
		timerMap.put("nowTime", time);
		timerMap.put("qianDate", qiantime);
		timerMap.put("id", "95");
		BigDecimal articlebdg = articleService.selectDanByTimer(timerMap);
		model.addAttribute("articlebdg",articlebdg);
		timerMap.clear();
		timerMap.put("nowTime", time);
		timerMap.put("qianDate", qiantime);
		timerMap.put("id", "96");
		BigDecimal articlebdf = articleService.selectDanByTimer(timerMap);
		model.addAttribute("articlebdf",articlebdf);
		timerMap.clear();
		timerMap.put("nowTime", time);
		timerMap.put("qianDate", qiantime);
		timerMap.put("id", "97");
		BigDecimal articlebdj = articleService.selectDanByTimer(timerMap);
		model.addAttribute("articlebdj",articlebdj);
		timerMap.clear();
		timerMap.put("nowTime", time);
		timerMap.put("qianDate", qiantime);
		timerMap.put("id", "110");
		BigDecimal articlebjob = articleService.selectDanByTimer(timerMap);
		model.addAttribute("articlebjob",articlebjob);
		BigDecimal articleTouSu = getcount("tousu");
		BigDecimal articleChuFa = getcount("chufa");
		BigDecimal articleZytz = getcount("zhongYaoTongZhi");
		BigDecimal articleCgfg = getcount("caiGouFaGui");
		model.addAttribute("articleTouSu",articleTouSu);
		model.addAttribute("articleChuFa",articleChuFa);
		model.addAttribute("articleZytz",articleZytz);
		model.addAttribute("articleCgfg",articleCgfg);
//		for(int i=0;i<articleTypeList.size();i++){
//			List<Article> indexNewsList = null;
//			if(articleTypeList.get(i).getName().equals("工作动态")){
//				indexNewsList = indexNewsService.selectNewsForJob(articleTypeList.get(i).getId());
//			}else{
//				indexNewsList = indexNewsService.selectNews(articleTypeList.get(i).getId());
//			}
////			if(!indexNewsList.isEmpty()){
//				indexMapper.put("select"+articleTypeList.get(i).getId()+"List", indexNewsList);
////			}else{
////				List<Article> indexNews = new ArrayList<Article>();
////				Article article = new Article();
////				article.setArticleType(articleTypeList.get(i));
////				indexNews.add(article);
////				indexMapper.put("select"+articleTypeList.get(i).getId()+"List", indexNews);
////			}
//		}
		
		// 供应商黑名单列表
	    List<SupplierBlacklist> supplierBlackList = supplierBlacklistService.getIndexSupplierBlacklist();
	    // 专家黑名单列表
	    List<ExpertBlackList> expertBlackList = expertBlackListService.getIndexExpertBlackList();
	    
	    indexMapper.put("supplierBlackList", supplierBlackList);
	    indexMapper.put("expertBlackList", expertBlackList);
		
		model.addAttribute("key", Constant.TENDER_SYS_KEY);
		model.addAttribute("indexMapper", indexMapper);
//		model.addAttribute("isIndex", "true");
		
		//设置是否弹窗
		model.addAttribute("isPopup", true);
		
		return "iss/ps/index/index";
	};
	
	/**
	 * 供应商黑名单列表
	 * @param request
	 * @param model
	 * @param supplierBlacklist
	 * @param page
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "supplierBlackList")
	public String supplierBlackList(HttpServletRequest request, Model model, 
			SupplierBlacklist supplierBlacklist, 
			String supplierTypeIds, 
			String supplierTypeNames, 
			Integer page) throws UnsupportedEncodingException {
		String supplierName = supplierBlacklist.getSupplierName();
		if(supplierName != null){
			supplierName = URLDecoder.decode(supplierName, "UTF-8");
		}
		if(supplierTypeNames != null){
			supplierTypeNames = URLDecoder.decode(supplierTypeNames, "UTF-8");
		}
		supplierBlacklist.setSupplierName(supplierName);
		List<SupplierBlacklistVO> listSupplierBlacklists = supplierBlacklistService.findSupplierBlacklist(supplierBlacklist, supplierTypeIds, page == null ? 1 : page);
		model.addAttribute("page", new PageInfo<SupplierBlacklistVO>(listSupplierBlacklists));
		model.addAttribute("supplierName", supplierName);
		model.addAttribute("supplierTypeIds", supplierTypeIds);
		model.addAttribute("supplierTypeNames", supplierTypeNames);
		if (supplierBlacklist.getStartTime() != null) {
			model.addAttribute("startTime", new SimpleDateFormat("yyyy-MM-dd").format(supplierBlacklist.getStartTime()));
		}
		if (supplierBlacklist.getEndTime() != null) {
			model.addAttribute("endTime", new SimpleDateFormat("yyyy-MM-dd").format(supplierBlacklist.getEndTime()));
		}
		Map<String, Object> indexMapper = new HashMap<String, Object>();
		topNews(indexMapper);
		model.addAttribute("indexMapper", indexMapper);
		return "iss/ps/index/supplierBlackList";
	}
	
	/**
	 * 专家黑名单列表
	 * @param request
	 * @param model
	 * @param page
	 * @param expert
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/expertBlackList")
	public String expertBlackList(HttpServletRequest request,Model model,
			String expertTypeId,
			ExpertBlackList expert,
			Integer page) throws UnsupportedEncodingException{
		String relName = expert.getRelName();
		if(relName != null){
			relName = URLDecoder.decode(relName, "UTF-8");
		}
		expert.setRelName(relName);
		List<ExpertBlackListVO> expertList = expertBlackListService.findExpertBlackList(expert,expertTypeId,page==null?1:page);
		request.setAttribute("page", new PageInfo<ExpertBlackListVO>(expertList));
		model.addAttribute("expertList", expertList);
		//回显
		request.setAttribute("relName", relName);
		request.setAttribute("expertTypeId", expertTypeId);
		// 查询数据字典中的专家类别数据
        List<DictionaryData> expertTypeList1 = DictionaryDataUtil.find(6);
        List<DictionaryData> expertTypeList2 = DictionaryDataUtil.find(19);
        List<DictionaryData> expertTypeList = new ArrayList<DictionaryData>();
        expertTypeList.addAll(expertTypeList1);
        expertTypeList.addAll(expertTypeList2);
        for(DictionaryData dd : expertTypeList){
        	if(dd.getKind() == 6){
        		dd.setName(dd.getName()+"技术");
        	}
        }
        Map<String, Object> indexMapper = new HashMap<String, Object>();
		topNews(indexMapper);
		model.addAttribute("indexMapper", indexMapper);
        request.setAttribute("expertTypeList", expertTypeList);
		return "iss/ps/index/expertBlackList";
	}
	
	private BigDecimal getcount(String str) {
	  // 获取当前年份、月份、日期  
    Calendar cale = null;  
    cale = Calendar.getInstance(); 
    // 获取当月第一天和最后一天  
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
    String firstday, lastday;  
    // 获取前月的第一天  
    cale = Calendar.getInstance();  
    cale.add(Calendar.MONTH, 0);  
    cale.set(Calendar.DAY_OF_MONTH, 1);  
    firstday = format.format(cale.getTime());  
    // 获取前月的最后一天  
    cale = Calendar.getInstance();  
    cale.add(Calendar.MONTH, 1);  
    cale.set(Calendar.DAY_OF_MONTH, 0);  
    lastday = format.format(cale.getTime());
    if ("tousu".equals(str)) {
      HashMap<String, String> timerMap = new HashMap<String, String>();
      timerMap.put("firstday", firstday);
      timerMap.put("lastday", lastday);
      timerMap.put("id", "112");
      BigDecimal touSuNum = articleService.selectByTypeIdTimer(timerMap);
      return touSuNum;
    } else if ("chufa".equals(str)) {
      HashMap<String, String> timerMap = new HashMap<String, String>();
      timerMap.put("firstday", firstday);
      timerMap.put("lastday", lastday);
      timerMap.put("id", "113");
      BigDecimal chuFaNum = articleService.selectByTypeIdTimer(timerMap);
      return chuFaNum;
    } else if ("zhongYaoTongZhi".equals(str)) {
      HashMap<String, String> timerMap = new HashMap<String, String>();
      timerMap.put("firstday", firstday);
      timerMap.put("lastday", lastday);
      timerMap.put("id", "109");
      BigDecimal zhongYaoTongZhiNum = articleService.selectByTypeIdTimer(timerMap);
      return zhongYaoTongZhiNum;
    } else if ("caiGouFaGui".equals(str)) {
      HashMap<String, String> timerMap = new HashMap<String, String>();
      timerMap.put("firstday", firstday);
      timerMap.put("lastday", lastday);
      timerMap.put("id", "106");
      BigDecimal caiGouFaGuiNum = articleService.selectByTypeIdTimer(timerMap);
      return caiGouFaGuiNum;
    } else {
      return new BigDecimal(0);
    }
  }

  /**
	 * 
	* @Title: selectIndexNewsByTypeId
	* @author QuJie 
	* @date 2016-8-31 下午1:22:28  
	* @Description: 根据指定的typeid来获取数据 
	* @param @param id
	* @param @param model
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/selectIndexNewsByParId")
	public String selectIndexNewsByParId(Model model,Integer page,String id,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String title = RequestTool.getParam(request, "title", "");
		String tab=RequestTool.getParam(request, "tab", "");
		String productType=RequestTool.getParam(request, "productType", "");
	
		String categoryName=RequestTool.getParam(request, "productTypeName", "");
		
		String lastArticleTypeName=RequestTool.getParam(request, "lastArticleTypeName", "");
		
		String publishStartDate=RequestTool.getParam(request, "publishStartDate", "");
		
		String publishEndDate=RequestTool.getParam(request, "publishEndDate", "");
		
		
		if(page==null){
			page=1;
		}
		map.put("parId",id);
		map.put("page", page);
		map.put("title", title);
		//传入参数通过产品目录查询文章
		map.put("productType", productType);
		//采购方式
		map.put("lastArticleTypeName", lastArticleTypeName);
		//起止时间
		map.put("publishStartDate", publishStartDate);
		map.put("publishEndDate", publishEndDate);

//		List<ArticleType> articleTypeList = articleTypeService.selectAllArticleTypeForSolr();
		List<Article> articleList = null;
		List<Article> twoArticleList = articleService.selectArticleByParIdTwo(map);
		if(twoArticleList.size()>0){
			articleList = twoArticleList;
		}
		Map<String, Object> indexMapper = new HashMap<String, Object>();
		topNews(indexMapper);
		model.addAttribute("indexMapper", indexMapper);
		model.addAttribute("id", id);
		model.addAttribute("list", new PageInfo<Article>(articleList));
		model.addAttribute("indexList", articleList);
		
		model.addAttribute("title", title);
		model.addAttribute("productType", productType);
		model.addAttribute("tab", tab);
		model.addAttribute("productTypeName", categoryName);
		model.addAttribute("categoryNames", categoryName);
		model.addAttribute("categoryIds", productType);
		model.addAttribute("lastArticleTypeName", lastArticleTypeName);
		model.addAttribute("publishStartDate", publishStartDate);
		model.addAttribute("publishEndDate", publishEndDate);
		
		return "iss/ps/index/parindex_two";
	}
	
	/**
	 * 
	* @Title: selectsumByParId
	* @author QuJie 
	* @date 2016-8-31 下午1:22:28  
	* @Description: 根据指定的typeid来获取数据 
	* @param @param id
	* @param @param model
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/selectsumByParId")
	public String selectsumByParId(Model model,Integer page,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String[] idArray = new String[2];
		idArray[0] = "107";
		idArray[1] = "108";
		if(page==null){
			page=1;
		}
		map.put("idArray",idArray);
		map.put("title",RequestTool.getParam(request, "title", ""));
		map.put("page", page);
		
//		List<ArticleType> articleTypeList = articleTypeService.selectAllArticleTypeForSolr();
		List<Article> articleList = null;
		List<Article> twoArticleList = articleService.selectsumByParId(map);
		if(twoArticleList.size()>0){
			articleList = twoArticleList;
		}
		Map<String, Object> indexMapper = new HashMap<String, Object>();
		topNews(indexMapper);
		model.addAttribute("list", new PageInfo<Article>(articleList));
		model.addAttribute("indexList", articleList);
		model.addAttribute("title", RequestTool.getParam(request, "title", ""));
		model.addAttribute("indexMapper", indexMapper);
		return "iss/ps/index/sumParId_two";
	}
	
	/**
	 * 
	* @Title: selectsumByParId
	* @author QuJie 
	* @date 2016-8-31 下午1:22:28  
	* @Description: 根据指定的typeid来获取数据 
	* @param @param id
	* @param @param model
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/selectsumBynews")
	public String selectsumBynews(Model model,Integer page,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String id = request.getParameter("id");
		String twoid = request.getParameter("twoid");
		String title = RequestTool.getParam(request, "title", "");
	
		String tab=RequestTool.getParam(request, "tab", "");
	
		String productType=RequestTool.getParam(request, "productType", "");
		
		String categoryName=RequestTool.getParam(request, "productTypeName", "");
		
		String lastArticleTypeName=RequestTool.getParam(request, "lastArticleTypeName", "");
	
		String publishStartDate=RequestTool.getParam(request, "publishStartDate", "");
	
		String publishEndDate=RequestTool.getParam(request, "publishEndDate", "");
		
		if(page==null){
			page=1;
		}
		map.put("id",id);
		map.put("twoid", twoid);
		map.put("page", page);
		map.put("title", title.trim());
//		List<ArticleType> articleTypeList = articleTypeService.selectAllArticleTypeForSolr();
		List<Article> articleList = null;
		//传入参数通过产品目录查询文章
		map.put("productType", productType);
		//采购方式
		map.put("lastArticleTypeName", lastArticleTypeName);
		//起止时间
		map.put("publishStartDate", publishStartDate);
		map.put("publishEndDate", publishEndDate);
		
		List<Article> twoArticleList = articleService.selectsumBynews(map);
		//List<Article> twoArticleList = articleService.findArtByCategory(map);
		if(twoArticleList.size()>0){
			articleList = twoArticleList;
		}
		Map<String, Object> indexMapper = new HashMap<String, Object>();
		topNews(indexMapper);
		model.addAttribute("indexMapper", indexMapper);
		model.addAttribute("id", id);
		model.addAttribute("twoid", twoid);
		model.addAttribute("list", new PageInfo<Article>(articleList));
		model.addAttribute("indexList", articleList);
		
		model.addAttribute("title", title.trim());
		model.addAttribute("productType", productType);
		model.addAttribute("tab", tab);
		model.addAttribute("productTypeName", categoryName);
		model.addAttribute("categoryNames", categoryName);
		model.addAttribute("categoryIds", productType);
		model.addAttribute("lastArticleTypeName", lastArticleTypeName);
		model.addAttribute("publishStartDate", publishStartDate);
		model.addAttribute("publishEndDate", publishEndDate);
		
		return "iss/ps/index/sumBynews_two";
	}
	
	/**
	 * 
	* @Title: selectsumBydanNews
	* @author QuJie 
	* @date 2016-8-31 下午1:22:28  
	* @Description: 根据指定的typeid来获取数据 
	* @param @param id
	* @param @param model
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/selectsumBydanNews")
	public String selectsumBydanNews(Model model,Integer page,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
//		String id = request.getParameter("id");
//		String twoid = request.getParameter("twoid");
//		String title = request.getParameter("title");
		String id = RequestTool.getParam(request, "id","0");
		String twoid =  RequestTool.getParam(request, "twoid","0");
		String title = RequestTool.getParam(request, "title","");
		
		if(page==null){
			page=1;
		}
		map.put("id",id);
		map.put("twoid", twoid);
		map.put("page", page);
		map.put("title", title);
//		List<ArticleType> articleTypeList = articleTypeService.selectAllArticleTypeForSolr();
		List<Article> articleList = null;
		List<Article> twoArticleList = articleService.selectsumBydanNews(map);
		if(twoArticleList.size()>0){
			articleList = twoArticleList;
		}
		Map<String, Object> indexMapper = new HashMap<String, Object>();
		topNews(indexMapper);
		model.addAttribute("id", id);
		model.addAttribute("twoid", twoid);
		model.addAttribute("list", new PageInfo<Article>(articleList));
		model.addAttribute("indexList", articleList);
		model.addAttribute("indexMapper", indexMapper);
		model.addAttribute("title", title);
		return "iss/ps/index/sumBydanNews_two";
	}
	
	/**
	 * 
	* @Title: selectIndexNewsByTypeId
	* @author QuJie 
	* @date 2016-8-31 下午1:22:28  
	* @Description: 根据指定的typeid来获取数据 
	* @param @param id
	* @param @param model
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/selectIndexNewsByTypeId")
	public String selectIndexNewsByTypeId(Model model,Integer page,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> countMap = new HashMap<String, Object>();
//		Map<String,Object> indexMapper = new HashMap<String, Object>();
//		List<ArticleType> articleTypeList = articleTypeService.selectAllArticleTypeForSolr();
//		for(int i=0;i<26;i++){
//			List<Article> indexNewsList = indexNewsService.selectNews(articleTypeList.get(i).getId());
//			if(!indexNewsList.isEmpty()){
//				indexMapper.put("select"+articleTypeList.get(i).getId()+"List", indexNewsList);
//			}else{
//				List<Article> indexNews = new ArrayList<Article>();
//				Article article = new Article();
//				article.setArticleType(articleTypeList.get(i));
//				indexNews.add(article);
//				indexMapper.put("select"+articleTypeList.get(i).getId()+"List", indexNews);
//			}
//		}
		PropertiesUtil config = new PropertiesUtil("config.properties");
		String pageSize = config.getString("pageSize");
		if(page==null){
			page=1;
		}
		String articleTypeId = request.getParameter("id");
		//String title = request.getParameter("title");
		String title = RequestTool.getParam(request, "title","");
		map.put("articleTypeId", articleTypeId);
		map.put("page", page);
		map.put("pageSize", pageSize);
		map.put("title", title);
		countMap.put("articleTypeId", articleTypeId);
		countMap.put("title", title);
		List<Article> indexNewsList = indexNewsService.selectNewsByArticleTypeId(map);
		ArticleType articleTypeOne = articleTypeService.selectTypeByPrimaryKey(articleTypeId);
		Integer pages = indexNewsService.selectCount(countMap);
		Integer startRow = 0;
		Integer endRow = 0;
		if(indexNewsList!=null && !indexNewsList.isEmpty()){
			if(indexNewsList.size()>0){
				startRow = (page-1)*Integer.parseInt(pageSize)+1;
			}
			if(indexNewsList.size()>0){
				endRow = startRow+(indexNewsList.size()-1);
			}
		}
		Map<String, Object> indexMapper = new HashMap<String, Object>();
		topNews(indexMapper);
		model.addAttribute("total", pages);
		model.addAttribute("startRow", startRow);
		model.addAttribute("endRow", endRow);
		model.addAttribute("pages", Math.ceil((double)pages/Integer.parseInt(pageSize)));
		model.addAttribute("indexList", indexNewsList);
		String articleTypeName = null;
		if(articleTypeOne != null){
			articleTypeName = articleTypeOne.getName();
		}
		model.addAttribute("typeName", articleTypeName);
		model.addAttribute("articleTypeId", articleTypeId);
		model.addAttribute("title", title);
		model.addAttribute("indexMapper", indexMapper);
		return "iss/ps/index/index_two";
	}
	
	/**
	 * 
	 * Description: 供应商处罚公告
	 * 
	 * @author zhang shubin
	 * @data 2017年6月13日
	 * @param 
	 * @return
	 */
	@RequestMapping("/supplierPunishment")
	public String supplierPunishment(Model model,Integer page,HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> countMap = new HashMap<String, Object>();
		PropertiesUtil config = new PropertiesUtil("config.properties");
		String pageSize = config.getString("pageSize");
		if(page==null){
			page=1;
		}
		String title = RequestTool.getParam(request, "title","");
		/*String idArray[] = {"116","117"};
		map.put("idArray",idArray);*/
		map.put("typeId","114");
		map.put("page", page);
		map.put("pageSize", pageSize);
		map.put("title", title);
		countMap.put("typeId","114");
		countMap.put("title", title);
		List<Article> indexNewsList = indexNewsService.selectSupplierAllNews(map);
		Integer pages = indexNewsService.selectSupplierCount(countMap);
		Integer startRow = 0;
		Integer endRow = 0;
		if(indexNewsList!=null){
			if(indexNewsList.size()>0){
				startRow = (page-1)*Integer.parseInt(pageSize)+1;
			}
			if(indexNewsList.size()>0){
				endRow = startRow+(indexNewsList.size()-1);
			}
		}
		Map<String, Object> indexMapper = new HashMap<String, Object>();
		topNews(indexMapper);
		model.addAttribute("total", pages);
		model.addAttribute("startRow", startRow);
		model.addAttribute("endRow", endRow);
		model.addAttribute("pages", Math.ceil((double)pages/Integer.parseInt(pageSize)));
		model.addAttribute("indexList", indexNewsList);
		model.addAttribute("typeName", "供应商处罚公告");
		model.addAttribute("title", title);
		model.addAttribute("indexMapper", indexMapper);
		return "iss/ps/index/index_two";
	}
	
	public static void markByText(String logoText, String srcImgPath,String targerPath) {
		markByText(logoText, srcImgPath, targerPath, null);
	}
	
//	private static int interval = 20;
	
	public static void markByText(String logoText,String srcImgPath,String targetPath,Integer degree){
		//主图片路径
		InputStream is = null;
		FileOutputStream os = null;
		try {
			Image srcImg = ImageIO.read(new File(srcImgPath));
			BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),
					srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
			
			//得到画笔对象
			Graphics2D g = buffImg.createGraphics();
			
			//设置对线段的锯齿状边缘处理
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			
			g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg.getHeight(null), Image.SCALE_SMOOTH),0,0,null);
			if(null!=degree){
				//设置水印旋转
				g.rotate(Math.toRadians(degree),(double) buffImg.getWidth()/2,(double) buffImg.getHeight()/2);
			}
			
			ImageIcon imgIcon = new ImageIcon(logoText);
			
			Image img = imgIcon.getImage();
//			//设置颜色
//			g.setColor(Color.red);
//			
//			//设置 Font
//			g.setFont(new Font("宋体",Font.BOLD,30));
			
			float alpha = 0.5f;
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));
			g.drawImage(img,200,10,null);
			//第一参数->设置内容，后面两个参数->文字在图片上的坐标位置(x,y)
//			for(int height=0;height<buffImg.getHeight();height = height+40){
//				for(int weight=130;weight<buffImg.getWidth();weight=weight+200){
//					g.drawString(logoText, weight-30, height);
//				}
//			}
			
//			g.drawString(logoText, 0,0);
//			g.drawString(logoText, 300, 20);
//			g.drawString(logoText, 500, 20);
			
			g.dispose();
			
			os = new FileOutputStream(targetPath);
			
			//生成图片
			ImageIO.write(buffImg, "jpg", os);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(null!=is)
					is.close();
				if(null!=os)
					os.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
//	private static double degree = 0f;
//	private static int interval = 0;
//	private static float alpha = 0.5f;
//	public static void setImageMarkOptions(float alphat,int degreet,int intervalt){
//		if(alpha!=0.0f){
//			alpha = alphat;
//		}
//		if(degree!=0f){
//			degree = degreet;
//		}
//		if(interval!=0f){
//			interval = intervalt;
//		}
//	}
	
	@RequestMapping("/downloadDetailsImage")
	public void downloadDetailsImage(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("id");
		String filePath = PropUtil.getProperty("file.noticePic.base") + "/glistening";
		//String filePath = request.getSession().getServletContext().getRealPath("/")+"/glistening";
		String targerPath2 = filePath+"/"+id+".jpg";
		InputStream fis = null;
		try {
			      File file = new File(targerPath2);
            response.setContentType("image/*");
            fis = new BufferedInputStream(new FileInputStream(file));  
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            byte[] b = new byte[fis.available()];
            fis.read(b);
            toClient.write(b);
            toClient.flush();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(fis!=null)
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	/**
	 * 
	* @Title: selectArticleNewsById
	* @author QuJie 
	* @date 2016-8-31 下午5:11:48  
	* @Description: 详情页信息 
	* @param @param article
	* @param @param model
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/selectArticleNewsById")
	public String selectArticleNewsById(Article article,Model model,HttpServletRequest request) throws Exception{
	  String ipAddressType = PropUtil.getProperty("ipAddressType");
	 // Article articleDetail = articleService.selectArticleById(article.getId());
	  //修改已取消发布和删除文章还能显示问题
	  String id=article.getId();
	  Article articleDetail = articleService.selectReleaseById(article.getId());
	  if(null==articleDetail)return "";
		Integer showCount = articleDetail.getShowCount();
		articleDetail.setShowCount(showCount+1);
		articleService.update(articleDetail);
		if ("0".equals(ipAddressType)) {
      //内网
		  System.out.println("内网环境");
    }
		
		if ("1".equals(ipAddressType)) {
      //外网
		  /*String filePath = request.getSession().getServletContext().getRealPath("/")+"/zanpic";
		  String glisteningPath = request.getSession().getServletContext().getRealPath("/")+"/glistening";*/
		  String filePath = PropUtil.getProperty("file.noticePic.base")+ File.separator + "zanpic";
      String glisteningPath = PropUtil.getProperty("file.noticePic.base")+ File.separator + "glistening";
		  /*String filePathFile = filePath+"/"+article.getId()+".png";*/
  		File glisteningFile = new File(glisteningPath+"/"+articleDetail.getId()+".jpg");
  		UploadUtil.createDir(filePath);
      UploadUtil.createDir(glisteningPath);
  		String proWaterPath = request.getSession().getServletContext().getRealPath("/")+"/proWatermark/shuiyin.png";
		  File stagingFile = new File(filePath);
		  File glisFile = new File(glisteningPath);
		  
		  //判读图片是否存在
		  if(glisteningFile.exists()){
          
      }else {
          /*  		if(stagingFile.exists()){
    			stagingFile.delete();
    		}
    		if(glisFile.exists()){
    			glisFile.delete();
    		}
    		if(stagingFile.exists()&&stagingFile.isDirectory()){
    			File[] files = stagingFile.listFiles();
    			for(int i=0;i<files.length;i++){
    				if(files[i].isFile()){
    					File file = new File(files[i].getAbsolutePath());
    					if(file.exists()&&file.isFile()){
    						file.delete();
    					}
    				}
    			}
    		}
    		if(stagingFile.exists()&&stagingFile.isDirectory()){
    			stagingFile.delete();
    		}
    		if(glisFile.exists()&&glisFile.isDirectory()){
    			File[] files = glisFile.listFiles();
    			for(int i=0;i<files.length;i++){
    				if(files[i].isFile()){
    					File file = new File(files[i].getAbsolutePath());
    					if(file.exists()&&file.isFile()){
    						file.delete();
    					}
    				}
    			}
    		}
    		if(glisFile.exists()&&glisFile.isDirectory()){
    			glisFile.delete();
    		}*/
          if(!stagingFile.exists()){
            stagingFile.mkdir();
          }
          if(!glisFile.exists()){
            glisFile.mkdir();
          }
          HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
          StringBuffer divStyle = new StringBuffer();
          divStyle.append("<div class='article_content' style='font-size: 14px; line-height: 35px; padding: 20px; width:900px'>");
          
          String content = articleDetail.getContent();
          
          if (StringUtils.isNotBlank(content)){
            content = content.replaceAll(CommonStringUtil.getAppendString("&nbsp;", 30), "");
            content = content.replaceAll(":=\"\"", "=\"\"");
          }
          
          divStyle.append(content);
          divStyle.append("</div>");
          String htmlstr = divStyle.toString();
          imageGenerator.loadHtml(htmlstr);
          imageGenerator.getBufferedImage();
          imageGenerator.saveAsImage(filePath+"/"+articleDetail.getId()+".png");
          String zancunPicPath = filePath+"/"+articleDetail.getId()+".png";
          
          String srcImgPath = zancunPicPath; 
  //		String logoText = "军队采购网";  
          String iconPath = proWaterPath;
          String targerPath2 = glisteningPath+"/"+articleDetail.getId()+".jpg";
          
          // 给图片添加水印
  //		markByText(logoText, srcImgPath, targerPath);
          
          //给图片添加水印，水印旋转-45
          markByText(iconPath, srcImgPath,targerPath2,0);
      }
		  
//		Map<String,Object> indexMapper = new HashMap<String, Object>();
//		List<ArticleType> articleTypeList = articleTypeService.selectAllArticleTypeForSolr();
//		for(int i=0;i<26;i++){
//			List<Article> indexNewsList = indexNewsService.selectNews(articleTypeList.get(i).getId());
//			if(!indexNewsList.isEmpty()){
//				indexMapper.put("select"+articleTypeList.get(i).getId()+"List", indexNewsList);
//			}else{
//				List<Article> indexNews = new ArrayList<Article>();
//				Article articless = new Article();
//				articless.setArticleType(articleTypeList.get(i));
//				indexNews.add(articless);
//				indexMapper.put("select"+articleTypeList.get(i).getId()+"List", indexNews);
//			}
//		}
		  System.out.println("外网环境");
		}
	  Map<String, Object> indexMapper = new HashMap<String, Object>();
	  topNews(indexMapper);
	  model.addAttribute("indexMapper", indexMapper);
    
		model.addAttribute("articleId", article.getId());
		DictionaryData da=new DictionaryData();
		da.setCode("GGWJ");
		List<DictionaryData> dlists = dictionaryDataServiceI.find(da);
		request.getSession().setAttribute("articleSysKey", Constant.TENDER_SYS_KEY);
		if(dlists.size()>0){
			model.addAttribute("artiAttachTypeId", dlists.get(0).getId());
		}
		
		List<UploadFile> uploadList = uploadService.getFilesOther(article.getId(), dlists.get(0).getId(), Constant.TENDER_SYS_KEY.toString());
//		List<ArticleAttachments> articleAttaList = articleAttachmentsService.selectAllArticleAttachments(articleDetail.getId());
//		articleDetail.setArticleAttachments(articleAttaList);
		model.addAttribute("fileSize", uploadList.size());
		model.addAttribute("articleDetail", articleDetail);
		model.addAttribute("ipAddressType", ipAddressType);
		return "iss/ps/index/index_details";
	}
	
	
	/**
	 * 
	* @Title: solrSearch 
	* @Description: 全文检索方法
	* @author SongDong
	* @param @param model
	* @param @param request
	* @param @param page
	* @param @return
	* @param @throws Exception    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping("/solrSearch")
	public String solrSearch(Model model,HttpServletRequest request,Integer page) throws Exception{
		// 获取搜索关键字，解决get请求中文乱码
		String conditionStr = request.getParameter("condition");
		String condition = null;
		if(conditionStr != null){
			condition = new String(request.getParameter("condition").getBytes("ISO8859-1"), "UTF-8");
		}
		if(page == null){
			page = 1;
		}
		Map<String, Object> map = searchService.search(condition, page);
		// 回显搜索关键字
		model.addAttribute("oldCondition", condition);
		// 全部搜索内容
		model.addAttribute("indexList", map.get("indexList"));
		
		// 总页数
		model.addAttribute("pages", map.get("pages"));
		// 总记录数
		model.addAttribute("total", map.get("total"));
		model.addAttribute("startRow", map.get("startRow"));
		model.addAttribute("endRow", map.get("endRow"));
		return "iss/ps/index/index_alltwo";
		
	}
	
	/**
	 * 
	* @Title: clearIndex 
	* @Description: 清空索引库
	* @author Easong
	* @param @return    设定文件 
	* @return JdcgResult    返回类型 
	* @throws
	 */
	@RequestMapping("/clearIndex")
	@ResponseBody
	public JdcgResult clearIndex(){
		return searchService.clearIndex();
	}
	
	/**
	 * 
	* @Title: deleteSignalIndex 
	* @Description: 根据条件清除索引库
	* @author Easong
	* @param @param request
	* @param @return    设定文件 
	* @return JdcgResult    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/deleteSignalIndex", method = RequestMethod.POST)
	@ResponseBody
	public JdcgResult deleteSignalIndex(HttpServletRequest request){
		String id = request.getParameter("id");
		if(id == null){
			return JdcgResult.ok("您要清除的索引不存在！");
		}
		String[] ids = id.split(",");
		return searchService.deleteSignalIndex(ids);
	}
	
	/**
	 * 
	* @Title: addSignalIndex 
	* @Description: 将指定的标题添加到索引库
	* @author Easong
	* @param @param request
	* @param @return    设定文件 
	* @return JdcgResult    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/addSignalIndex", method = RequestMethod.POST)
	@ResponseBody
	public JdcgResult addSignalIndex(HttpServletRequest request){
		String id = request.getParameter("id");
		if(id == null){
			return JdcgResult.ok("您选择的标题不存在！");
		}
		String[] ids = id.split(",");
		return searchService.addSignalIndex(ids);
	}
	
	
	/**
	 * 
	* @Title: solrSearch
	* @author QuJie 
	* @date 2016-9-6 上午9:56:32  
	* @Description: 全文索引查询 
	* @param @param model
	* @param @param request
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/solrSearch1")
	public String solrSearch1(Model model,HttpServletRequest request,Integer page, String condition) throws Exception{
		if(page==null){
			page=1;
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("title", condition);
		map.put("page", page);
		List<Article> articleList = indexNewsService.selectAllByName(map);
		PageInfo<Article> info = new PageInfo<Article>(articleList);
		model.addAttribute("indexList", articleList);
		model.addAttribute("oldCondition", condition);
		model.addAttribute("info", info);
		return "iss/ps/index/index_alltwo";
	}
	
	/**
	 * 
	* @Title: downloadArticleAtta
	* @author QuJie 
	* @date 2016-9-8 上午9:05:53  
	* @Description: 详情页下载附件 
	* @param @throws Exception      
	* @return void
	 */
	@RequestMapping("/downloadArticleAtta")
	public void downloadArticleAtta(HttpServletRequest request,ArticleAttachments articleAttachments,HttpServletResponse response) throws Exception{
		ArticleAttachments articleAtta = articleAttachmentsService.selectArticleAttaById(articleAttachments.getId());
//		String filePath = articleAtta.getAttachmentPath();
//		File file = new File(filePath);
//		if(file == null || !file.exists()){
//			return;
//		}
		Article article = articleService.selectArticleById(articleAtta.getArticle().getId());
		String floadername = PropUtil.getProperty("file.upload.path.articlenews");
		String path = (request.getSession().getServletContext().getRealPath("/") + PropUtil.getProperty("file.stashPath") + "/").replace("\\", "/");
		String fileName = articleAtta.getFileName();
		FtpUtil.startDownFile(path, floadername, fileName);
		FtpUtil.closeFtp();
		if (fileName != null && !"".equals(fileName)) {
			super.download(request, response, fileName);
		}

		super.removeStash(request, fileName);
//		String fileName = (articleAtta.getFileName().split("_"))[1];
//		response.reset();
//		response.setContentType(articleAtta.getContentType()+"; charset=utf-8");
//		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
//		OutputStream out = response.getOutputStream();
//		out.write(FileUtils.readFileToByteArray(file));
//		out.flush();
		DownloadUser downloadUser = new DownloadUser();
		downloadUser.setCreatedAt(new Date());
		downloadUser.setArticle(article);
		downloadUser.setIsDeleted(0);
		downloadUser.setUpdatedAt(new Date());
		User creater = (User) request.getSession().getAttribute("loginUser");
		downloadUser.setUserName(creater.getLoginName());
//		downloadUser.setUser("1231231");//死数据
		downloadUserService.addDownloadUser(downloadUser);
		article.setDownloadCount(article.getDownloadCount()+1);
		articleService.update(article);
//		if(out !=  null){
//			out.close();
//		}
	}
	
	/**
	 * 后台索引库管理
	* @Title: indexImportUI 
	* @Description: 
	* @author Easong
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping("/indexImportUI")
	public String indexImportUI(Model model, Integer page, HttpServletRequest request){
		if(page == null){
			page = 1;
		}
		// 文章标题名称
		String name = (String) request.getParameter("name");
		if (name != null) {
			name = name.trim();
		}
		// 是否建立索引
		String isIndexStr = (String) request.getParameter("isIndex");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("isIndex", isIndexStr);
		map.put("page", page);
		List<Article> list = articleService.selectAllArticleByCondition(map);
		// 分页
		PageInfo<Article> info = new PageInfo<Article>(list);
		model.addAttribute("info", info);
		// 查询条件回显
		model.addAttribute("name", name);
		model.addAttribute("isIndex", isIndexStr);
		return "iss/ps/sorl_index/solr_index";
	}
	
	// 将所有文章导入索引库方法
	@RequestMapping("importAll")
	@ResponseBody
	public JdcgResult importAll(){
		return searchService.importAll();
	}
	
	@RequestMapping("/showPic")
	public void showPic(HttpServletRequest request,HttpServletResponse response){
		String path = request.getParameter("path");
		InputStream fis = null;
		File file = new File(path);
        response.setContentType("image/*");
        try {
			fis = new BufferedInputStream(new FileInputStream(file));
			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
	        byte[] b = new byte[fis.available()];
	        fis.read(b);
	        toClient.write(b);
	        toClient.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
            if (fis != null) {
                try {
                   fis.close();
                } catch (IOException e) {
                e.printStackTrace();
            }   
              }
        }  
       
	}
	
	@RequestMapping("/selectAllByTabs")
	public String selectAllByTabs(Model model,HttpServletRequest request,Integer page)throws Exception{
//		String[] idArray = new String[4];
		String articleTypeId = request.getParameter("articleTypeId");
		String secondArticleTypeId = request.getParameter("secondArticleTypeId");
		String title = RequestTool.getParam(request, "title", "");
		
		String tab=RequestTool.getParam(request, "tab", "");
	
		String productType=RequestTool.getParam(request, "productType", "");
	
		String categoryName=RequestTool.getParam(request, "productTypeName", "");
		
		String lastArticleTypeName=RequestTool.getParam(request, "lastArticleTypeName", "");
	
		String publishStartDate=RequestTool.getParam(request, "publishStartDate", "");
	
		String publishEndDate=RequestTool.getParam(request, "publishEndDate", "");
	
		if(page==null){
			page=1;
		}
		
//		String id1 = request.getParameter("id");
//		String id2 = request.getParameter("id2");
//		String id3 = request.getParameter("id3");
//		String id4 = request.getParameter("id4");
//		idArray[0] = id1;
//		idArray[1] = id2;
//		idArray[2] = id3;
//		idArray[3] = id4;
		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("idArray", idArray);
		map.put("page", page);
		map.put("title", title);
		map.put("articleTypeId", articleTypeId);
		map.put("secondArticleTypeId", secondArticleTypeId);
		
		//传入参数通过产品目录查询文章
		map.put("productType", productType);
		//采购方式
		map.put("lastArticleTypeName", lastArticleTypeName);
		//起止时间
		map.put("publishStartDate", publishStartDate);
		map.put("publishEndDate", publishEndDate);
				
		//List<Article> twoArticleList = articleService.selectsumBynews(map);
		
		//xxxxx
		
		List<Article> indexList = articleService.selectAllByTabs(map);
		model.addAttribute("indexList", indexList);
		model.addAttribute("list", new PageInfo<Article>(indexList));
		model.addAttribute("articleTypeId", articleTypeId);
		model.addAttribute("secondArticleTypeId", secondArticleTypeId);
		
		Map<String, Object> indexMapper = new HashMap<String, Object>();
		topNews(indexMapper);
		model.addAttribute("indexMapper", indexMapper);
		
		model.addAttribute("title", title);
		model.addAttribute("productType", productType);
		model.addAttribute("tab", tab);
		model.addAttribute("productTypeName", categoryName);
		model.addAttribute("categoryNames", categoryName);
		model.addAttribute("categoryIds", productType);
		model.addAttribute("lastArticleTypeName", lastArticleTypeName);
		model.addAttribute("publishStartDate", publishStartDate);
		model.addAttribute("publishEndDate", publishEndDate);
		model.addAttribute("pt", tab);
		
//		model.addAttribute("id2", id2);
//		model.addAttribute("id3", id3);
//		model.addAttribute("id4", id4);
//		model.addAttribute("id", id1);
//		model.addAttribute("title", title);
		return "iss/ps/index/sumBytabs_two";
	}
	
	@RequestMapping("/selectAllByDanTabs")
	public String selectAllByDanTabs(Model model,HttpServletRequest request,Integer page){
		String[] idArray = new String[4];
		String title = request.getParameter("title");
		if(page==null){
			page=1;
		}
		String id1 = request.getParameter("id");
		String id2 = request.getParameter("id2");
		String id3 = request.getParameter("id3");
		String id4 = request.getParameter("id4");
		idArray[0] = id1;
		idArray[1] = id2;
		idArray[2] = id3;
		idArray[3] = id4;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("idArray", idArray);
		map.put("page", page);
		map.put("title", title);
		List<Article> indexList = articleService.selectAllByDanTabs(map);
		model.addAttribute("indexList", indexList);
		model.addAttribute("list", new PageInfo<Article>(indexList));
		model.addAttribute("id2", id2);
		model.addAttribute("id3", id3);
		model.addAttribute("id4", id4);
		model.addAttribute("id", id1);
		model.addAttribute("title", title);
		return "iss/ps/index/sumByDantabs_two";
	}
	
	@RequestMapping("/selectIndexChuFaNewsByTypeId")
  public String selectIndexChuFaNewsByTypeId(Model model,Integer page,HttpServletRequest request) throws Exception{
    Map<String, Object> map = new HashMap<String, Object>();
    Map<String, Object> countMap = new HashMap<String, Object>();
    PropertiesUtil config = new PropertiesUtil("config.properties");
    String pageSize = config.getString("pageSize");
    if(page==null){
      page=1;
    }
    String articleTypeId = request.getParameter("id");
    String title = RequestTool.getParam(request, "title","");
    map.put("articleTypeId", articleTypeId);
    map.put("page", page);
    map.put("pageSize", pageSize);
    map.put("title", title);
    countMap.put("articleTypeId", articleTypeId);
    countMap.put("title", title);
    List<Article> indexNewsList = indexNewsService.selectIndexChuFaNewsByTypeId(map);
    ArticleType articleTypeOne = articleTypeService.selectTypeByPrimaryKey(articleTypeId);
    Integer pages = indexNewsService.selectChufaCount(countMap);
    Integer startRow = 0;
    Integer endRow = 0;
    if(indexNewsList!=null){
      if(indexNewsList.size()>0){
        startRow = (page-1)*Integer.parseInt(pageSize)+1;
      }
      if(indexNewsList.size()>0){
        endRow = startRow+(indexNewsList.size()-1);
      }
    }
    Map<String, Object> indexMapper = new HashMap<String, Object>();
    topNews(indexMapper);
    model.addAttribute("total", pages);
    model.addAttribute("startRow", startRow);
    model.addAttribute("endRow", endRow);
    model.addAttribute("pages", Math.ceil((double)pages/Integer.parseInt(pageSize)));
    model.addAttribute("indexList", indexNewsList);
    model.addAttribute("typeName", articleTypeOne.getName());
    model.addAttribute("articleTypeId", articleTypeId);
    model.addAttribute("title", title);
    model.addAttribute("indexMapper", indexMapper);
    return "iss/ps/index/index_chufa_two";
  }
	
	/**
	 * 
	* @Title: selectsumByDirectory
	* @author tianzhiqiang 
	* @date 2017-4-1 下午1:22:28  
	* @Description: 专家名录、供应商名录更多页面 
	* @param @param id
	* @param @param model
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/selectsumByDirectory")
	public String selectsumByDirectory(Model model,Integer page,HttpServletRequest request) throws Exception{
		String act=request.getParameter("act");
		//供应商名录
		if("0".equals(act)){
			SupplierService suppService=SpringBeanUtil.getBean(SupplierService.class);
	        Map<String, Object> sMap = new HashMap<String, Object>();
	        //只显示公开的
	        //sMap.put("IS_PUBLISH", 1);
	        //处理查询参数
			String supplierName=RequestTool.getParam(request,"supplierName","");
			if(!"".equals(supplierName)){
				sMap.put("supplierName", supplierName);
				model.addAttribute("supplierName", supplierName );
			}
			String status=RequestTool.getParam(request,"status","");
			if(!"".equals(status)){
				String [] statusArray= status.split(",");
                sMap.put("size", statusArray.length);
				sMap.put("statusArray", statusArray);
				model.addAttribute("status", status );
			}else{
				int statusArray[] = {1,4,5,6,7,8};
                sMap.put("size", statusArray.length);
				sMap.put("statusArray", statusArray);
			}
			
	        List<Supplier> list = suppService.query(page == null ? 1 : page,sMap);
	        model.addAttribute("list",  new PageInfo<Supplier>(list));
	        return "iss/ps/index/sumByPubSupplier";
		}
		else {//专家名录 ：1
			Expert expert=new Expert();
			Map<String, Object> expertMap = new HashMap<>();
			//处理查询参数
			String relName=RequestTool.getParam(request,"relName","");
			if(!"".equals(relName)){
				expert.setRelName(relName);
				expertMap.put("relName", relName);
				model.addAttribute("relName", relName );
			}
			String status=RequestTool.getParam(request,"status","");
			if(!"".equals(status)){
				String [] statusArray= status.split(","); 
				expertMap.put("statusArray", statusArray);
				expertMap.put("size", statusArray.length);
				expert.setStatus(status);
				model.addAttribute("status", status );
			}else {
                int statusArray[] = {4,6,7,8};
                expertMap.put("size", statusArray.length);
                expertMap.put("statusArray", statusArray);
            }
			
			ExpertService expertService=SpringBeanUtil.getBean(ExpertService.class);
            //只显示公开的
			//expert.setIsPublish(1);
			//expertMap.put("isPublish", 1);
			expertMap.put("flag", 1);

			//分页
	        List<Expert> list = expertService.selectIndexExpert(page == null ? 1 : page, expertMap);
	        model.addAttribute("list",  new PageInfo<Expert>(list));
	        return "iss/ps/index/sumByPubExpert";
		}
	}
	
	/**
	 * 
	 * Description: 首页定型产品列表查询
	 * 
	 * @author zhang shubin
	 * @data 2017年6月15日
	 * @param 
	 * @return
	 */
    @RequestMapping("/index_productList")
    public String headlist(HttpServletRequest request,Model model, @RequestParam(defaultValue="1")Integer page) {
        OBProduct example = new OBProduct();
        String name = request.getParameter("name") == null ? "" : request.getParameter("name");
        String code = request.getParameter("code") == null ? "" : request.getParameter("code");
        String smallPointsId = request.getParameter(OnlineBidding.SMALL_POINTS_ID) == null ? "" : request.getParameter(OnlineBidding.SMALL_POINTS_ID);
        example.setName(name);
        example.setCode(code);
        example.setSmallPointsId(smallPointsId);
        List<OBProduct> list = oBProductService.selectPublishProduct(example, page);
        if(list != null){
            for (OBProduct oBProduct : list) {
                String id = oBProduct.getSmallPointsId();
                if(id != null){
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("id", id);
                    List<Category> clist = categoryService.findCategoryByParentNode(map);
                    StringBuilder sb = new StringBuilder();
                    for (Category category : clist) {
                        if(!oBProduct.getSmallPoints().getName().equals(category.getName())){
                            sb.append(category.getName());
                            sb.append("/");
                        }
                    }
                    if(oBProduct.getSmallPoints() != null){
                        sb.append(oBProduct.getSmallPoints().getName());
                        oBProduct.setPointsName(sb.toString());
                    }
                }
            }
        }
        Category cl = categoryService.findById(smallPointsId);
        if(cl != null){
            model.addAttribute("catName", cl.getName());
        }
        PageInfo<OBProduct> info = new PageInfo<>(list);
        List<OBSupplier> numlist = oBSupplierService.selectSupplierNum();
        for (OBSupplier ob : numlist) {
            if(null != ob && null == ob.getnCount()){
                ob.setnCount(0);
            }
        }
        Map<String, Object> indexMapper = new HashMap<String, Object>();
        topNews(indexMapper);
        model.addAttribute("indexMapper", indexMapper);
        model.addAttribute("info", info);
        model.addAttribute("product", example);
        model.addAttribute("numlist", numlist);
        return "bss/ob/finalize_DesignProduct/index_list";
    }

    /**
     * 
     * Description: 首页服务热线
     * 
     * @author zhang shubin
     * @data 2017年6月15日
     * @param 
     * @return
     */
    @RequestMapping("/index_hotLineList")
    public String index_list(@RequestParam(defaultValue="1")Integer page, Model model,String servicecontent){
        ServiceHotline serviceHotline = new ServiceHotline();
        if(servicecontent != null){
            serviceHotline.setServicecontent(servicecontent);
        }
        List<ServiceHotline> list = serviceHotlineService.selectAll(serviceHotline,page);
        PageInfo<ServiceHotline> info = new PageInfo<>(list);
        Map<String, Object> indexMapper = new HashMap<String, Object>();
        topNews(indexMapper);
        model.addAttribute("indexMapper", indexMapper);
        model.addAttribute("info", info);
        model.addAttribute("serviceHotline", serviceHotline);
        return "iss/hl/index_list";
    }

    /**
     * 
     * Description: 加载首页导航栏公告信息
     * 
     * @author zhang shubin
     * @data 2017年6月14日
     * @param 
     * @return
     */
    @RequestMapping("/indexHeadInfo")
    public void indexHeadInfo(Model model){
        Map<String, Object> indexMapper = new HashMap<String, Object>();
        topNews(indexMapper);
        model.addAttribute("indexMapper", indexMapper);
    }

    /**
     * 
     * Description:加载首页导航栏专家公示列表
     * 
     * @author Easong
     * @version 2017年6月28日
     * @param page
     * @return
     */
    @RequestMapping("/indexExpPublicity")
    public String indexExpPublicity(Model model){
        // 查询数据字典中的专家类别数据
        // 专家基类型
        List < DictionaryData > basicTypeList = DictionaryDataUtil.find(6);
        for(DictionaryData data: basicTypeList) {
            data.setName(data.getName() + "技术");
        }
        List < DictionaryData > economyTypeList = DictionaryDataUtil.find(19);
        basicTypeList.addAll(economyTypeList);
        model.addAttribute("expTypeList", basicTypeList);
        //全部机构
        List<PurchaseDep>  orgDepList = purChaseDepOrgService.findAllOrg();
        model.addAttribute("orgDepList", orgDepList);
    	return "iss/ps/index/index_expPublicity";
    }
    
    /**
     * 
     * Description:加载首页导航栏供应商公示列表
     * 
     * @author Easong
     * @version 2017年6月28日
     * @param page
     * @return
     */
    @RequestMapping("/indexSupPublicity")
    public String indexSupPublicity(Model model){
        // 类型
		List<SupplierTypeTree> listSupplierTypes = supplierTypeService.findSupplierType(null);
		for(SupplierTypeTree t : listSupplierTypes){
			if("物资".equals(t.getName())){
                listSupplierTypes.remove(t);
				break;
			}
		}
        // 查询企业性质
        List<DictionaryData> businessNatureList = DictionaryDataUtil.find(32);
		// 获取所有采购机构
		//全部机构
		List<PurchaseDep>  orgDepList = purChaseDepOrgService.findAllOrg();
		model.addAttribute("orgDepList", orgDepList);
        model.addAttribute("businessNatureList",businessNatureList);
        model.addAttribute("listSupplierTypes",listSupplierTypes);
        return "iss/ps/index/index_supPublicity";
    }
    
    /**
     * 
     * Description:加载首页导航栏供应商公示列表数据
     * 
     * @author Easong
     * @version 2017年6月28日
     * @param page
     * @param supplierPublicity
     * @return
     */
    @RequestMapping("/indexSupPublicityAjax")
    @ResponseBody
    public JdcgResult indexSupPublicityAjax(@RequestParam(value="page", defaultValue = "1") int page, SupplierPublicity supplierPublicity){
    	Map<String, Object> map = new HashMap<>();
    	map.put("page", page);
    	map.put("supplierPublicity", supplierPublicity);
    	List<SupplierPublicity> list = supplierAuditService.selectSupByPublictyList(map);
    	PageInfo<SupplierPublicity> info = new PageInfo<>(list);
    	return JdcgResult.ok(info);
    }
    /**
     * 
     * Description:加载首页导航栏专家公示列表数据
     * 
     * @author Easong
     * @version 2017年6月28日
     * @param page
     * @param expertPublicity
     * @return
     */
    @RequestMapping("/indexExpPublicityAjax")
    @ResponseBody
    public JdcgResult indexExpPublicityAjax(@RequestParam(value="page", defaultValue = "1") int page, ExpertPublicity expertPublicity){
    	Map<String, Object> map = new HashMap<>();
    	map.put("page", page);
    	map.put("expertPublicity", expertPublicity);
    	List<ExpertPublicity> list = expertAuditService.selectExpByPublictyList(map);
    	PageInfo<ExpertPublicity> info = new PageInfo<>(list);
    	return JdcgResult.ok(info);
    }

	/**
	 *
	 * Description:专家小类到根节点查询页面
	 *
	 * @author Easong
	 * @version 2017/7/6
	 * @param
	 * @since JDK1.7
	 */
	@RequestMapping("/indexExpPublicityItem")
    public String indexExpPublicityItem(Model model, String query_id_of_cate, Expert expert, String sign){
		//初审复审标识（1初审，3复查，2复审）
		model.addAttribute("sign", sign);

		expert = expertService.selectByPrimaryKey(query_id_of_cate);

		List <DictionaryData> allCategoryList = new ArrayList <> ();

		// 查询审核通过的专家类型
        List<String> stringList = expertCategoryService.selectCateByExpertId(query_id_of_cate);

        // 获取专家类别
		List < String > allTypeId = new ArrayList <> ();
		if(expert.getExpertsTypeId() !=null && !"".equals(expert.getExpertsTypeId())){
			for(String id: expert.getExpertsTypeId().split(",")) {
			    if(stringList != null && stringList.contains(id)){
                    allTypeId.add(id);
                }
			}
		}

		a: for(int i = 0; i < allTypeId.size(); i++) {
			DictionaryData dictionaryData = dictionaryDataServiceI.getDictionaryData(allTypeId.get(i));
			allCategoryList.add(dictionaryData);
		}
		model.addAttribute("allCategoryList", allCategoryList);

		model.addAttribute("expertId", query_id_of_cate);

		//查询品目类型id
		String matCodeId=DictionaryDataUtil.getId("GOODS");
		String engCodeId=DictionaryDataUtil.getId("PROJECT");
		String serCodeId=DictionaryDataUtil.getId("SERVICE");
		String engInfoId=DictionaryDataUtil.getId("ENG_INFO_ID");

		String goodsServerId=DictionaryDataUtil.getId("GOODS_SERVER");
		String goodsProjectId=DictionaryDataUtil.getId("GOODS_PROJECT");

		model.addAttribute("matCodeId", matCodeId);
		model.addAttribute("engCodeId", engCodeId);
		model.addAttribute("serCodeId", serCodeId);
		model.addAttribute("engInfoId", engInfoId);

		model.addAttribute("goodsServerId", goodsServerId);
		model.addAttribute("goodsProjectId", goodsProjectId);

		return "iss/ps/index/index_expPublicity_item";
	}

	/**
	 *
	 * Description:专家小类到根节点查询列表
	 *
	 * @author Easong
	 * @version 2017/7/6
	 * @param
	 * @since JDK1.7
	 */
    @RequestMapping("/indexExpPublicityItemAjax")
    @ResponseBody
    public JdcgResult indexExpPublicityItemAjax(){
    	return JdcgResult.ok();
	}

	/**
	 *
	 * Description:供应商产品类别到根节点查询页面
	 *
	 * @author Easong
	 * @version 2017/7/6
	 * @param
	 * @since JDK1.7
	 */
	@RequestMapping("/indexSupPublicityItem")
    public String indexSupPublicityItem(Model model, String query_id_of_cate, String supplierStatus){
        model.addAttribute("supplierId", query_id_of_cate);
        model.addAttribute("supplierStatus", supplierStatus);
        //封装 目录分类 分别显示相关的数据
        if(StringUtils.isNotBlank(query_id_of_cate)){
            // 封装查询数据
			// 定义两个集合
			Map<String, Object> map = new HashedMap();
			map.put("supplierId", query_id_of_cate);
            Set<String> set = supplierItemService.findPassSupplierTypeBySupplierId(map);
            model.addAttribute("supplierTypes", StringUtils.join(set,","));
        }
		return "iss/ps/index/index_supPublicity_item";
	}

	/**
	 *
	 * Description:供应商产品类别到根节点查询列表
	 *
	 * @author Easong
	 * @version 2017/7/6
	 * @param
	 * @since JDK1.7
	 */
    @RequestMapping("/indexSupPublicityItemAjax")
    @ResponseBody
    public JdcgResult indexSupPublicityItemAjax(String supplierId){
    	return supplierItemService.selectRegSupCateOfLastNode(supplierId);
	}
}
