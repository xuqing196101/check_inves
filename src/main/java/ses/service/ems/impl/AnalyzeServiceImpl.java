package ses.service.ems.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.bms.UserMapper;
import ses.dao.ems.ExpertMapper;
import ses.dao.sms.SupplierMapper;
import ses.model.bms.Analyze;
import ses.model.bms.AnalyzeItem;
import ses.service.ems.AnalyzeService;
import ses.util.DictionaryDataUtil;
import bss.util.PropUtil;
import common.constant.Constant;
import common.dao.AttUploadAnalyzeMapper;
import common.dao.FileUploadMapper;
import common.dao.LoginAnalyzeMapper;
import common.dao.LoginLogMapper;
import common.dao.RegisterAnalyzeMapper;
import common.model.AttUploadAnalyze;
import common.model.LoginAnalyze;
import common.model.RegisterAnalyze;
import common.utils.DateUtils;

/**
 * 
 * @ClassName: AnalyzeServiceImpl
 * @Description: 统计分析Service实现类
 * @author Easong
 * @date 2017年5月3日 下午3:14:23
 * 
 */
@Service
public class AnalyzeServiceImpl implements AnalyzeService {

	// 注入文件上传Mapper
	@Autowired
	private FileUploadMapper fileUploadMapper;

	// 注入供应商Mapper
	@Autowired
	private SupplierMapper supplierMapper;

	// 注入专家Mapper
	@Autowired
	private ExpertMapper expertMapper;

	// 注入用户Mapper
	@Autowired
	private UserMapper userMapper;

	// 注入登录日志Mapper
	@Autowired
	private LoginLogMapper loginLogMapper;

	// 注入登录统计Mapper
	@Autowired
	private LoginAnalyzeMapper loginAnalyzeMapper;
	// 注入注册统计Mapper
	@Autowired
	private RegisterAnalyzeMapper registerAnalyzeMapper;
	// 注入文件上传统计Mapper
	@Autowired
	private AttUploadAnalyzeMapper attUploadAnalyzeMapper;

	// 类型
	private static final String EXPERT_NAME = "专家";
	private static final String SUPPLIER_NAME = "供应商";
	private static final String TENDER_NAME = "后台用户";

	// 登录统计数据字典
	private static final String ANALYZE_LOGIN = "ANALYZE_LOGIN";
	// 注册统计数据字典
	private static final String ANALYZE_REGISTER = "ANALYZE_REGISTER";
	// 附件上传统计数据字典
	private static final String ANALYZE_ATTACHMENT_UPLOAD = "ANALYZE_ATTACHMENT_UPLOAD";

	// 按天统计
	private static final String ANALYZE_OF_DAY = "DAY";
	// 按周统计
	private static final String ANALYZE_OF_WEEK = "WEEK";
	// 按月统计
	private static final String ANALYZE_OF_MONTH = "MONTH";

	// 登录统计
	private static final String C_LOGIN = "C_LOGIN";
	// 注册统计
	private static final String C_REGISTER = "C_REGISTER";
	// 图片上传统计
	private static final String C_ATT_UPLOAD = "C_ATT_UPLOAD";

	/**
	 * 
	 * @Title: getFileCountByEmp
	 * @Description: 文件上传统计
	 * @author Easong
	 * @param @return 设定文件
	 * @throws
	 */
	@Override
	public AnalyzeItem getFileCountByEmp() {
		Map<String, Object> map = new HashMap<String, Object>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// 当前日期
		Date date = new Date();
		// 获取后台管理上传文件表
		AnalyzeItem analyzeItem = new AnalyzeItem();
		List<Analyze> analyzes = analyzeItem.getAnalyzes();

		// 获取供应商上传文件表
		String tableName = Constant.fileSystem.get(Constant.EXPERT_SYS_KEY);
		map.put("createdAt", dateFormat.format(date));
		map.put("tableName", tableName);
		Long expertFileCount = fileUploadMapper.getFileCountByEmp(map);
		// 封装数据
		setAnalyzeData(analyzes, SUPPLIER_NAME, expertFileCount);

		// 获取专家上传文件表
		tableName = Constant.fileSystem.get(Constant.SUPPLIER_SYS_KEY);
		map.put("tableName", tableName);
		Long supplierFileCount = fileUploadMapper.getFileCountByEmp(map);
		// 封装数据
		setAnalyzeData(analyzes, EXPERT_NAME, expertFileCount);

		tableName = Constant.fileSystem.get(Constant.TENDER_SYS_KEY);
		map.put("tableName", tableName);
		Long tenderFileCount = fileUploadMapper.getFileCountByEmp(map);
		// 封装数据
		setAnalyzeData(analyzes, TENDER_NAME, tenderFileCount);

		// 汇总
		analyzeItem.setSubtext(tenderFileCount + expertFileCount
				+ supplierFileCount);
		return analyzeItem;
	}

	/**
	 * 
	 * @Title: setAnalyzeData
	 * @Description: 封装回显数据
	 * @author Easong
	 * @param @param analyzes
	 * @param @param TypeName
	 * @param @param count 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void setAnalyzeData(List<Analyze> analyzes, String TypeName,
			Long count) {
		Analyze analyze = new Analyze();
		analyze.setName(TypeName);
		analyze.setValue(count);
		analyzes.add(analyze);
	}

	/**
	 * 
	 * @Title: getRegisterCountByEmp
	 * @Description: 用户注册统计
	 * @author Easong
	 * @param @return 设定文件
	 * @throws
	 */
	@Override
	public AnalyzeItem getRegisterCountByEmp() {
		Map<String, Object> map = new HashMap<String, Object>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// 当前日期
		Date date = new Date();
		// 登录供应商
		map.put("createdAt", dateFormat.format(date));
		// 创建统计对象
		AnalyzeItem analyzeItem = new AnalyzeItem();
		List<Analyze> analyzes = analyzeItem.getAnalyzes();
		
		// 注册供应商
		Long registerSupplierCountByEmp = supplierMapper
				.getRegisterSupplierCountByEmp(map);
		setAnalyzeData(analyzes, SUPPLIER_NAME, registerSupplierCountByEmp);
		// 注册专家
		Long registerExpertCountByEmp = expertMapper
				.getRegisterExpertCountByEmp(map);
		setAnalyzeData(analyzes, EXPERT_NAME, registerExpertCountByEmp);

		// 注册后台管理
		Long registerUserCountByEmp = userMapper.getRegisterTenderCountByEmp(map);
		// 相减
		Long registerTenderCountByEmp = registerUserCountByEmp
				- registerSupplierCountByEmp - registerExpertCountByEmp;
		setAnalyzeData(analyzes, TENDER_NAME, registerTenderCountByEmp);
		// 设置注册总数
		analyzeItem.setSubtext(registerSupplierCountByEmp
				+ registerExpertCountByEmp + registerTenderCountByEmp);
		return analyzeItem;
	}

	/**
	 * 
	 * @Title: getLoginCountByEmp
	 * @Description: 统计用户日登录量
	 * @author Easong
	 * @param @return 设定文件
	 * @throws
	 */
	@Override
	public AnalyzeItem getLoginCountByEmp() {
		// 封装查询map
		Map<String, Object> map = new HashMap<String, Object>();
		// 创建统计对象
		AnalyzeItem analyzeItem = new AnalyzeItem();
		List<Analyze> analyzes = analyzeItem.getAnalyzes();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// 登录供应商
		// 当前日期
		map.put("loginTime", dateFormat.format(new Date()));
		// 用户类型 1：后台 2：供应商 3：专家
		map.put("loginType", 2);
		Long supllierLoginCount = loginLogMapper.getLoginCountByEmp(map);
		if (supllierLoginCount == null) {
			supllierLoginCount = 0l;
		}
		setAnalyzeData(analyzes, SUPPLIER_NAME, supllierLoginCount);

		// 注册专家
		map.put("loginType", 3);
		Long expertLoginCount = loginLogMapper.getLoginCountByEmp(map);
		if (expertLoginCount == null) {
			expertLoginCount = 0l;
		}
		setAnalyzeData(analyzes, EXPERT_NAME, expertLoginCount);

		// 注册后台管理
		map.put("loginType", 1);
		Long tenderLoginCount = loginLogMapper.getLoginCountByEmp(map);
		if (tenderLoginCount == null) {
			tenderLoginCount = 0l;
		}
		setAnalyzeData(analyzes, TENDER_NAME, tenderLoginCount);
		// 设置登录总数
		analyzeItem.setSubtext(supllierLoginCount + expertLoginCount
				+ tenderLoginCount);
		return analyzeItem;
	}

	/**
	 * 
	 * @Title: taskAnalyzeLogin
	 * @Description: 定时执行任务，登录日志表 新增数据转到统计表
	 * @author Easong
	 * @param 设定文件
	 * @throws
	 */
	@Override
	public void taskAnalyzeLogin() {
		Map<String, Object> map = new HashMap<String, Object>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// 登录供应商
		// 当前日期
		Date date = new Date();
		Date subDayDate = DateUtils.subDayDate(date, 1);
		map.put("createdAt", dateFormat.format(subDayDate));
		// 用户类型 1：专家 2：供应商 3：后台管理员
		map.put("loginType", 1);
		// 注册专家
		Long expertLoginCount = loginLogMapper.getLoginCountByEmp(map);
		
		map.put("loginType", 2);
		Long supplierLoginCount = loginLogMapper.getLoginCountByEmp(map);
		
		// 注册后台管理
		map.put("loginType", 3);
		Long backgroundLoginCount = loginLogMapper.getLoginCountByEmp(map);
		
		// 获取字典数据
		String typeId = DictionaryDataUtil.getId(ANALYZE_LOGIN);
		LoginAnalyze loginAnalyze = new LoginAnalyze();
		LoginAnalyze loginAnalyzeData = (LoginAnalyze) analyzeData(
				loginAnalyze, subDayDate, dateFormat, typeId);
		if (loginAnalyzeData != null) {
			if(expertLoginCount != null){
				// 存储专家登录数
				loginAnalyzeData.setType(1);
				loginAnalyzeData.setLoginNum(expertLoginCount.intValue());
				loginAnalyzeMapper.insertSelective(loginAnalyzeData);
			}
			
			if(supplierLoginCount != null){
				// 存储供应商登录数
				loginAnalyzeData.setType(2);
				loginAnalyzeData.setLoginNum(supplierLoginCount.intValue());
				loginAnalyzeMapper.insertSelective(loginAnalyzeData);
			}
			
			if(backgroundLoginCount != null){
				// 存储后台登录数
				loginAnalyzeData.setType(3);
				loginAnalyzeData.setLoginNum(backgroundLoginCount.intValue());
				loginAnalyzeMapper.insertSelective(loginAnalyzeData);
			}
		}

	}

	/**
	 * 
	 * @Title: loginAnalyzeData
	 * @Description: 封装统计表数据
	 * @author Easong
	 * @param @param object
	 * @param @param date
	 * @param @param dateFormat
	 * @param @param typeId
	 * @param @param type
	 * @param @return 设定文件
	 * @return Object 返回类型
	 * @throws
	 */
	private Object analyzeData(Object object, Date date,
			DateFormat dateFormat, String typeId) {
		String year = DateUtils.getYear(date);
		// 天 如：20170511
		Integer day = DateUtils.getDayOfYear(date, dateFormat);

		// 计算当前时间是第几周 如：201723
		int i = DateUtils.getWeekOfYear();
		Integer week = Integer.parseInt(year + i);

		// 计算当前时间是第几月 如：201706
		DateFormat dateFormatForMonth = DateUtils.getDateFormat("yyyy-MM");
		Integer month = DateUtils.getDayOfYear(date, dateFormatForMonth);
		// 封装参数
		// 登录统计
		if (object instanceof LoginAnalyze) {
			LoginAnalyze loginAnalyze = (LoginAnalyze) object;
			loginAnalyze.setCreatedAt(date);
			loginAnalyze.setTypeId(typeId);
			loginAnalyze.setIndexDay(day);
			loginAnalyze.setIndexWeek(week);
			loginAnalyze.setIndexMonth(month);
			return loginAnalyze;
		}

		// 注册统计
		if (object instanceof RegisterAnalyze) {
			RegisterAnalyze registerAnalyze = (RegisterAnalyze) object;
			registerAnalyze.setCreatedAt(date);
			registerAnalyze.setTypeId(typeId);
			registerAnalyze.setIndexDay(day);
			registerAnalyze.setIndexWeek(week);
			registerAnalyze.setIndexMonth(month);
			return registerAnalyze;
		}

		// 图片上传统计
		if (object instanceof AttUploadAnalyze) {
			AttUploadAnalyze attUploadAnalyze = (AttUploadAnalyze) object;
			attUploadAnalyze.setCreatedAt(date);
			attUploadAnalyze.setTypeId(typeId);
			attUploadAnalyze.setIndexDay(day);
			attUploadAnalyze.setIndexWeek(week);
			attUploadAnalyze.setIndexMonth(month);
			return attUploadAnalyze;
		}
		return null;
	}

	/**
	 * 
	 * @Title: taskAnalyzeRegister
	 * @Description: 定时执行任务，注册， 新增数据转到统计表
	 * @author Easong
	 * @param 1：专家 2：供应商 3：后台管理员
	 * @throws
	 */
	@Override
	public void taskAnalyzeRegister() {
		Map<String, Object> map = new HashMap<String, Object>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// 当前日期
		Date date = new Date();
		Date subDayDate = DateUtils.subDayDate(date, 1);
		// 登录供应商
		map.put("createdAt", dateFormat.format(subDayDate));
		// 查询专家注册的数量
		Long registerExpertCountByEmp = expertMapper.getRegisterExpertCountByEmp(map);
		
		// 查询供应商注册的数量
		Long registerSupplierCountByEmp = supplierMapper.getRegisterSupplierCountByEmp(map);
		
		// 查询后台管理员注册的数量
		Long registerUserCountByEmp = userMapper.getRegisterTenderCountByEmp(map);
		// 所有注册用户-专家注册-注册后台管理
		Long registerTenderCountByEmp = registerUserCountByEmp- registerSupplierCountByEmp - registerExpertCountByEmp;
		
		if(registerTenderCountByEmp < 0){
			registerTenderCountByEmp = 0l;
		}
		
		// 获取字典数据
		String typeId = DictionaryDataUtil.getId(ANALYZE_REGISTER);
		// 创建登录统计对象
		RegisterAnalyze registerAnalyze = new RegisterAnalyze();
		RegisterAnalyze registerAnalyzeData = (RegisterAnalyze) analyzeData(
				registerAnalyze, subDayDate, dateFormat, typeId);
		// 向统计表中插入数据
		if (registerAnalyzeData != null) {
			if(registerExpertCountByEmp != null){
				// 存储专家登录数
				registerAnalyzeData.setType(1);
				registerAnalyzeData.setRegisterNum(registerExpertCountByEmp.intValue());
				registerAnalyzeMapper.insertSelective(registerAnalyzeData);
			}
			
			if(registerSupplierCountByEmp != null){
				// 存储供应商登录数
				registerAnalyzeData.setType(2);
				registerAnalyzeData.setRegisterNum(registerSupplierCountByEmp.intValue());
				registerAnalyzeMapper.insertSelective(registerAnalyzeData);
			}
			
			if(registerTenderCountByEmp != null){
				// 存储后台登录数
				registerAnalyzeData.setType(3);
				registerAnalyzeData.setRegisterNum(registerTenderCountByEmp.intValue());
				registerAnalyzeMapper.insertSelective(registerAnalyzeData);
			}
		}
		
	}

	/**
	 * 
	 * @Title: taskAnalyzeAttUpload
	 * @Description: 定时执行任务，文件上传表 新增数据转到统计表
	 * @author Easong
	 * @param 1：专家 2：供应商 3：后台管理员
	 * @throws
	 */
	@Override
	public void taskAnalyzeAttUpload() {
		Map<String, Object> map = new HashMap<String, Object>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// 当前日期
		Date date = new Date();
		Date subDayDate = DateUtils.subDayDate(date, 1);
		// 获取专家上传文件表
		String tableName = Constant.fileSystem.get(Constant.EXPERT_SYS_KEY);
		map.put("createdAt", dateFormat.format(subDayDate));
		map.put("tableName", tableName);
		Long expertFileCount = fileUploadMapper.getFileCountByEmp(map);
		
		// 获取供应商上传文件表
		tableName = Constant.fileSystem.get(Constant.SUPPLIER_SYS_KEY);
		map.put("tableName", tableName);
		Long supplierFileCount = fileUploadMapper.getFileCountByEmp(map);
		// 获取后台管理上传文件表

		tableName = Constant.fileSystem.get(Constant.TENDER_SYS_KEY);
		map.put("tableName", tableName);
		Long backgroundFileCount = fileUploadMapper.getFileCountByEmp(map);
		
		// 获取字典数据
		String typeId = DictionaryDataUtil.getId(ANALYZE_ATTACHMENT_UPLOAD);
		AttUploadAnalyze attUploadAnalyze = new AttUploadAnalyze();
		AttUploadAnalyze attUploadAnalyzeData = (AttUploadAnalyze) analyzeData(attUploadAnalyze, subDayDate, dateFormat, typeId);
		// 向统计表中插入数据
		if (attUploadAnalyzeData != null) {
			if(expertFileCount != null){
				// 存储专家登录数
				attUploadAnalyzeData.setType(1);
				attUploadAnalyzeData.setUploadNum(expertFileCount.intValue());
				attUploadAnalyzeMapper.insertSelective(attUploadAnalyzeData);
			}
			
			if(supplierFileCount != null){
				// 存储供应商登录数
				attUploadAnalyzeData.setType(2);
				attUploadAnalyzeData.setUploadNum(supplierFileCount.intValue());
				attUploadAnalyzeMapper.insertSelective(attUploadAnalyzeData);
			}
			
			if(backgroundFileCount != null){
				// 存储后台登录数
				attUploadAnalyzeData.setType(3);
				attUploadAnalyzeData.setUploadNum(backgroundFileCount.intValue());
				attUploadAnalyzeMapper.insertSelective(attUploadAnalyzeData);
			}
		}
	}

	/**
	 * 
	* @Title: analyzeLoginCount 
	* @Description: 登录统计
	* @author Easong
	* @param @param analyzeType
	* analyzeTypeByCate:三种统计：登录、注册、图片上传
	* @param @return    设定文件 
	* @throws
	 */
	@Override
	public List<Analyze> analyzeLoginCount(String analyzeType, Integer analyzeTypeIntegerStart, Integer analyzeTypeIntegerEnd
			,String analyzeTypeByCate) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(analyzeTypeIntegerStart != null && analyzeTypeIntegerEnd != null){
			
		}else{
			DateFormat dateFormatDay = DateUtils.getDateFormat("yyyy-MM-dd");
			Date date = new Date();
			// 获取年
			String year = DateUtils.getYear(date);
			// 定义统计结果集合
			List<Analyze> list = null;
			// DAY
			if(ANALYZE_OF_DAY.equals(analyzeType)){
				// 按日统计
				// 获取当前日 20170512
				analyzeTypeIntegerEnd = DateUtils.getDayOfYear(date, dateFormatDay);
				// 获取前30天的日期 20160412
				analyzeTypeIntegerStart = DateUtils.getDayOfYear(DateUtils.subDayDate(date, 30), dateFormatDay);
				// 查询
				map.put("analyzeTypeIntegerStart", analyzeTypeIntegerStart);
				map.put("analyzeTypeIntegerEnd", analyzeTypeIntegerEnd);
				// 判断类型 三种统计：登录(C_LOGIN)、注册(C_REGISTER)、图片上传(C_ATT_UPLOAD)
				// 按月登录统计
				if("C_LOGIN".equals(analyzeTypeByCate)){
					list = loginAnalyzeMapper.analyzeLoginCountByDay(map);
				}
				// 按月注册统计
				if("C_REGISTER".equals(analyzeTypeByCate)){
					list = registerAnalyzeMapper.analyzeRegisterCountByDay(map);
				}
				// 按月上传图片统计
				if("C_ATT_UPLOAD".equals(analyzeTypeByCate)){
					list = attUploadAnalyzeMapper.analyzeAttUploadCountByDay(map);
				}
				getAnalyzeData(list);
				return list;
			}
			// WEEK
			if(ANALYZE_OF_WEEK.equals(analyzeType)){
				// 按周统计
				// 获取当前是第几周 201720
				int weekOfYear = DateUtils.getWeekOfYear();
				if(weekOfYear - 7 < 0){
					// 结束周数
					analyzeTypeIntegerStart = analyzeTypeIntegerEnd = Integer.parseInt(year + weekOfYear);
				}else{
					// 开始周数
					analyzeTypeIntegerEnd = Integer.parseInt(year + weekOfYear);
					// 计算七周的数据
					analyzeTypeIntegerStart = Integer.parseInt(year + (weekOfYear - 7));
				}
				// 查询
				map.put("analyzeTypeIntegerStart", analyzeTypeIntegerStart);
				map.put("analyzeTypeIntegerEnd", analyzeTypeIntegerEnd);
				// 按周登录统计
				if("C_LOGIN".equals(analyzeTypeByCate)){
					list = loginAnalyzeMapper.analyzeLoginCountByWeek(map);
				}
				// 按周注册统计
				if("C_REGISTER".equals(analyzeTypeByCate)){
					list = registerAnalyzeMapper.analyzeRegisterCountByWeek(map);
				}
				// 按周上传图片统计
				if("C_ATT_UPLOAD".equals(analyzeTypeByCate)){
					list = attUploadAnalyzeMapper.analyzeAttUploadCountByWeek(map);
				}
				getAnalyzeData(list);
				return list;
			}
			// MONTH
			if(ANALYZE_OF_MONTH.equals(analyzeType)){
				// 按月统计
				// 得到当前月
				DateFormat dateFormatForMonth = DateUtils.getDateFormat("yyyy-MM");
				Integer month = DateUtils.getDayOfYear(date, dateFormatForMonth);
				// 获取开始月 201701
				analyzeTypeIntegerStart = Integer.parseInt(year + 01);
				// 获取结束月
				analyzeTypeIntegerEnd = month;
				// 查询
				map.put("analyzeTypeIntegerStart", analyzeTypeIntegerStart);
				map.put("analyzeTypeIntegerEnd", analyzeTypeIntegerEnd);
				// 登录统计
				if(C_LOGIN.equals(analyzeTypeByCate)){
					list = loginAnalyzeMapper.analyzeLoginCountByMonth(map);
				}
				// 注册统计
				if(C_REGISTER.equals(analyzeTypeByCate)){
					list = registerAnalyzeMapper.analyzeRegisterCountByMonth(map);
				}
				// 用户上传图片统计
				if(C_ATT_UPLOAD.equals(analyzeTypeByCate)){
					list = attUploadAnalyzeMapper.analyzeAttUploadCountByMonth(map);
				}
				getAnalyzeData(list);
				return list;
			}
			
		}
		return null;
	}

	/**
	 *
	* @Title: getAnalyzeData 
	* @Description: 区分统计类别
	* @author Easong
	* @param @param list    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	private void getAnalyzeData(List<Analyze> list) {
		if(list != null && list.size() > 0){
			for (Analyze analyze : list) {
				if("1".equals(analyze.getGroup())){
					analyze.setGroup(EXPERT_NAME);
				}
				if("2".equals(analyze.getGroup())){
					analyze.setGroup(SUPPLIER_NAME);
				}
				if("3".equals(analyze.getGroup())){
					analyze.setGroup(TENDER_NAME);
				}
			}
		}
	}

	/**
	 * 
	* @Title: vertifyIpAddressType 
	* @Description: 判断内外网
	* @author Easong
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public String vertifyIpAddressType(){
		String ipAddressType= PropUtil.getProperty("ipAddressType");
		// 1外网  // 0内网
		return ipAddressType;
	}
}
