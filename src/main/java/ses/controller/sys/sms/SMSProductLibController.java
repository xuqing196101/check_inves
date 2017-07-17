package ses.controller.sys.sms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.CategoryParameter;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.sms.SMSProductBasic;
import ses.model.sms.SMSProductCheckRecord;
import ses.model.sms.SMSProductInfo;
import ses.model.sms.SMSProductQueryVO;
import ses.model.sms.SMSProductVO;
import ses.model.sms.Supplier;
import ses.service.bms.CategoryParameterService;
import ses.service.sms.SMSProductLibService;
import ses.util.DictionaryDataUtil;
import synchro.util.Constant;

import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
import common.annotation.SystemControllerLog;
import common.annotation.SystemServiceLog;
import common.utils.JdcgResult;

/**
 * 
 * @ClassName: ProductLibController
 * @Description: 产品库管理Controller
 * @author Easong
 * @date 2017年4月10日 下午5:42:32
 * 
 */
@Controller
@RequestMapping("/product_lib")
public class SMSProductLibController {

	@Autowired
	private SMSProductLibService smsProductLibService;

	@Autowired
	private CategoryParameterService categoryParameterService;
	
	// 定义用户类型
	private final static String USER_TYPE_SUPPLIER= "供应商";

	/**
	 * 
	 * @Title: findAllProductLibBasicInfo
	 * @Description: 查询全部商品的基本信息
	 * @author Easong
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping("/findAllProductLibBasicInfo")
	public String findAllProductLibBasicInfo(@CurrentUser User user,
			Model model, Integer page, SMSProductQueryVO smsProductQueryVO) {
		// 用户未登录
		if (user == null) {
			return "ses/sms/supplier_product_lib/list";
		}
		if (page == null) {
			page = 1;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取查询名称
		String name = smsProductQueryVO.getName();
		map.put("name", name);
		// 获取查询的状态
		Integer status = smsProductQueryVO.getStatus();
		map.put("status", status);
		map.put("page", page);
		map.put("userId", user.getTypeId());
		List<SMSProductBasic> list = smsProductLibService
				.findAllProductLibBasicInfo(map);
		PageInfo<SMSProductBasic> info = new PageInfo<SMSProductBasic>(list);
		model.addAttribute("info", info);
		// 查询信息回显
		model.addAttribute("name", name);
		model.addAttribute("status", status);
		return "ses/sms/supplier_product_lib/list";
	}

	/**
	 * 
	 * @Title: findAllProductLibBasicInfo
	 * @Description: 查询单个商品的全部信息
	 * @author Easong
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping("/findSignalProductInfo")
	public String findSignalProductInfo(Model model, String id, String flag) {
		Map<String, Object> map = smsProductLibService
				.findSignalProductInfo(id);
		// 产品基本信息
		SMSProductBasic smsProductBasic = (SMSProductBasic) map
				.get("smsProductBasic");
		// 产品描述信息
		SMSProductInfo smsProductInfo = (SMSProductInfo) map
				.get("smsProdcutInfo");
		// 产品审核信息
		SMSProductCheckRecord productCheckRecord = (SMSProductCheckRecord) map
				.get("productCheckRecord");
		model.addAttribute("smsProductBasic", smsProductBasic);
		model.addAttribute("smsProductInfo", smsProductInfo);
		model.addAttribute("productCheckRecord", productCheckRecord);
		// 文件上传项
		fileUploadItem(model);
		if ("check".equals(flag)) {
			// 产品审核页面
			return "ses/sms/supplier_product_lib/check/productCheck";
		}
		// 产品信息页面
		return "ses/sms/supplier_product_lib/product";
	}

	/**
	 * 
	 * @Title: getParametersByItemId
	 * @Description: 根据产品末节点类型获取产品参数
	 * @author Easong
	 * @param @param itemId
	 * @param @return 设定文件
	 * @return List<CategoryParameter> 返回类型
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/getParametersByItemId")
	public List<CategoryParameter> getParametersByItemId(String cateId) {
		return categoryParameterService.getParamsByCateId(cateId);
	}

	/**
	 * 
	 * @Title: addSMSProductUI
	 * @Description: 供应商后台录入产品信息页面回显
	 * @author Easong
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping("/addSMSProductUI")
	public String addSMSProductUI(Model model) {
		fileUploadItem(model);
		return "ses/sms/supplier_product_lib/add";
	}

	/**
	 * @throws Exception 
	 * 
	 * @Title: addProductLibInfo
	 * @Description: 供应商后台录入产品信息
	 * @author Easong
	 * @param @return 设定文件
	 * @return JdcgResult 返回类型
	 * @throws
	 */
	@SystemControllerLog(description= USER_TYPE_SUPPLIER)
	@RequestMapping("/addProductLibInfo")
	@ResponseBody
	public JdcgResult addProductLibInfo(@CurrentUser User user,
			SMSProductVO smsProductVO, Integer flag) throws Exception {
		if (user == null) {
			return JdcgResult.build(500, "请先登录");
		}
		return smsProductLibService.addProductLibInfo(smsProductVO, flag, user);
	}

	/**
	 * @throws Exception 
	 * 
	 * @Title: deleteProductLibInfo
	 * @Description: 供应商后台删除暂存的产品信息
	 * @author Easong
	 * @param @return 设定文件
	 * @return JdcgResult 返回类型
	 * @throws
	 */
	@SystemControllerLog(description=USER_TYPE_SUPPLIER)
	@RequestMapping("/deleteProductLibInfo")
	@ResponseBody
	public JdcgResult deleteProductLibInfo(
			@RequestParam(value = "idss[]") String[] idss) throws Exception {
		return smsProductLibService.deleteProductLibInfo(idss);
	}

	/**
	 * 
	 * @Title: editSignalProductInfoUI
	 * @Description: 供应商后台修改产品信息页面回显
	 * @author Easong
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping("/editSignalProductInfoUI")
	public String editSignalProductInfoUI(Model model, String id) {
		// 查询产品信息
		Map<String, Object> map = smsProductLibService
				.findSignalProductInfo(id);
		SMSProductBasic smsProductBasic = (SMSProductBasic) map
				.get("smsProductBasic");
		SMSProductInfo smsProductInfo = (SMSProductInfo) map
				.get("smsProdcutInfo");
		model.addAttribute("smsProductBasic", smsProductBasic);
		model.addAttribute("smsProductInfo", smsProductInfo);
		// 文件上传项
		fileUploadItem(model);

		return "ses/sms/supplier_product_lib/edit";
	}

	/**
	 * @throws Exception 
	 * 
	 * @Title: updateSignalProductInfo
	 * @Description: 供应商后台修改产品信息
	 * @author Easong
	 * @param @param user
	 * @param @param smsProductVO
	 * @param @param flag
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@SystemControllerLog(description= USER_TYPE_SUPPLIER)
	@RequestMapping("/updateSignalProductInfo")
	@ResponseBody
	public JdcgResult updateSignalProductInfo(@CurrentUser User user,
			SMSProductVO smsProductVO) throws Exception {
		return smsProductLibService.updateSignalProductInfo(smsProductVO);
	}

	/**
	 * 
	 * @Title: productParamterUI
	 * @Description: 加载参数页面
	 * @author Easong
	 * @param @param categoryId
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping("/productParamterUI")
	public String productParamterUI(String categoryId, Model model) {
		List<CategoryParameter> categoryParamlist = categoryParameterService
				.getParamsByCateId(categoryId);
		fileUploadItem(model);
		model.addAttribute("categoryParamlist", categoryParamlist);
		return "ses/sms/supplier_product_lib/productParamter";
	}

	/**
	 * 
	 * @Title: fileUploadItem
	 * @Description: 获取文件上传配置
	 * @author Easong
	 * @param @param model 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void fileUploadItem(Model model) {
		// 供应商系统key文件上传key
		Integer sysKey = common.constant.Constant.SUPPLIER_SYS_KEY;
		// 定义文件上传类型
		DictionaryData dictionaryData = DictionaryDataUtil
				.get(Constant.PRODUCT_LIB_FILE);
		if (dictionaryData != null) {
			model.addAttribute("typeId", dictionaryData.getId());
		}
		model.addAttribute("sysKey", sysKey);
	}

	/**
	 * 
	 * @Title: findAllWaitCheck
	 * @Description: 供应商审核列表
	 * @author Easong
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping("/findAllWaitCheck")
	public String findAllWaitCheck(@CurrentUser User user,Model model, Integer page,
			SMSProductQueryVO smsProductQueryVO) {
		//2失效  0 可执行
		int authType=2;
		if(user !=null){
			//资源服务中心
			if("4".equals(user.getTypeName())){
				authType=0;
				checkList(model, page, smsProductQueryVO);
			}
		}
		model.addAttribute("authType", authType);
		return "ses/sms/supplier_product_lib/check/checklist";
	}
	
	/**
	 * 
	 * @Title: findAllCheckProduct
	 * @Description: 供应商审核查询信息
	 * @author Easong
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping("/findAllCheckProduct")
	public String findAllCheckProduct(Model model, Integer page,
			SMSProductQueryVO smsProductQueryVO) {
		checkList(model, page, smsProductQueryVO);
		return "ses/sms/supplier_product_lib/check/searchlist";
	}

	/**
	 * 
	* @Title: checkList 
	* @Description: 抽取审核列表
	* @author Easong
	* @param @param model
	* @param @param page
	* @param @param smsProductQueryVO    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	private void checkList(Model model, Integer page,
			SMSProductQueryVO smsProductQueryVO) {
		if (page == null) {
			page = 1;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		// 获取查询名称
		String name = smsProductQueryVO.getName();
		map.put("name", name);
		// 获取查询的状态
		Integer status = smsProductQueryVO.getStatus();
		map.put("status", status);
		String createrId = smsProductQueryVO.getCreaterId();
		// 获取供应商ID 根据需求修改全部人 可以查询
		map.put("createrId", createrId);

		map.put("page", page);
		List<SMSProductBasic> list = smsProductLibService.findAllWaitCheck(map);
		PageInfo<SMSProductBasic> info = new PageInfo<SMSProductBasic>(list);
		model.addAttribute("info", info);
		// 查询信息回显
		model.addAttribute("name", name);
		model.addAttribute("status", status);
		model.addAttribute("createrId", createrId);
	}
	

	/**
	 * 
	 * @Title: checkProductInfo
	 * @Description: 监管部门审核产品信息
	 * @author Easong
	 * @param @return 设定文件
	 * @return JdcgResult 返回类型
	 * @throws
	 */
	@RequestMapping("checkProductInfo")
	@ResponseBody
	public JdcgResult checkProductInfo(@CurrentUser User user,
			SMSProductCheckRecord productCheckRecord) {
		return smsProductLibService.checkProductInfo(user, productCheckRecord);
	}

	/**
	 * 
	 * @Title: vartifyUniqueSKU
	 * @Description: SKU唯一校验
	 * @author Easong
	 * @param @param sku
	 * @param @return 设定文件
	 * @return JdcgResult 返回类型
	 * @throws
	 */
	@RequestMapping("/vartifyUniqueSKU")
	@ResponseBody
	public JdcgResult vartifyUniqueSKU(String sku, String pid) {
		return smsProductLibService.vertifyUniqueSKU(sku, pid);
	}
	
	
	/**
	 * 
	* @Title: findAllSupplier 
	* @Description: 查询所有供应商
	* @author Easong
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping("/findAllSupplier")
	@ResponseBody
	public List<Supplier> findAllSupplier(){
		return smsProductLibService.findAllSupplier();
	}

	@RequestMapping("/test")
	public String test(String aaa) {
		return null;
	}
	
	/**
	 * 
	 * Description: 提交
	 * 
	 * @author zhang shubin
	 * @data 2017年7月13日
	 * @param 
	 * @return
	 */
	@RequestMapping("/submit")
	public String submit(String id){
		smsProductLibService.updateStatusById(id);
		return "redirect:findAllProductLibBasicInfo.html";
	}
}
