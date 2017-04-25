package ses.controller.sys.sms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import ses.service.bms.CategoryParameterService;
import ses.service.sms.impl.SMSProductLibService;
import ses.util.DictionaryDataUtil;
import synchro.util.Constant;

import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
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
	 * 
	 * @Title: addProductLibInfo
	 * @Description: 供应商后台录入产品信息
	 * @author Easong
	 * @param @return 设定文件
	 * @return JdcgResult 返回类型
	 * @throws
	 */
	@RequestMapping("/addProductLibInfo")
	@ResponseBody
	public JdcgResult addProductLibInfo(@CurrentUser User user,
			SMSProductVO smsProductVO, Integer flag) {
		if (user == null) {
			return JdcgResult.build(500, "请先登录");
		}
		return smsProductLibService.addProductLibInfo(smsProductVO, flag, user);
	}

	/**
	 * 
	 * @Title: deleteProductLibInfo
	 * @Description: 供应商后台删除暂存的产品信息
	 * @author Easong
	 * @param @return 设定文件
	 * @return JdcgResult 返回类型
	 * @throws
	 */
	@RequestMapping("/deleteProductLibInfo")
	@ResponseBody
	public JdcgResult deleteProductLibInfo(@RequestParam(value = "idss[]") String[] idss) {
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
	@RequestMapping("/updateSignalProductInfo")
	@ResponseBody
	public JdcgResult updateSignalProductInfo(@CurrentUser User user,
			SMSProductVO smsProductVO) {
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
		// 定义招标采购系统key文件上传key
		Integer sysKey = common.constant.Constant.TENDER_SYS_KEY;
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
	 * @Description: 供应商审核查询信息
	 * @author Easong
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping("/findAllWaitCheck")
	public String findAllWaitCheck(Model model, Integer page,
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

		map.put("page", page);
		List<SMSProductBasic> list = smsProductLibService.findAllWaitCheck(map);
		PageInfo<SMSProductBasic> info = new PageInfo<SMSProductBasic>(list);
		model.addAttribute("info", info);
		// 查询信息回显
		model.addAttribute("name", name);
		model.addAttribute("status", status);
		return "ses/sms/supplier_product_lib/check/list";
	}

	/**
	 * 
	 * @Title: checkProductInfo
	 * @Description: 供应商审核产品信息
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

	/*
	 * @RequestMapping("/test") public String test(String aaa){
	 * System.out.println(""); return null; }
	 */
}
