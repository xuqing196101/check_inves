package ses.service.ems.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.util.NewBeanInstanceStrategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.constant.Constant;
import common.dao.FileUploadMapper;
import common.dao.LoginLogMapper;
import ses.dao.bms.UserMapper;
import ses.dao.ems.ExpertMapper;
import ses.dao.sms.SupplierMapper;
import ses.model.bms.Analyze;
import ses.model.bms.AnalyzeItem;
import ses.service.ems.AnalyzeService;

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

	// 类型
	private static final String TENDER_NAME = "后台用户";
	private static final String SUPPLIER_NAME = "供应商";
	private static final String EXPERT_NAME = "专家";

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
		// 获取后台管理上传文件表
		AnalyzeItem analyzeItem = new AnalyzeItem();
		List<Analyze> analyzes = analyzeItem.getAnalyzes();

		// 获取供应商上传文件表
		String tableName = Constant.fileSystem.get(Constant.EXPERT_SYS_KEY);
		Long expertFileCount = fileUploadMapper.getFileCountByEmp(tableName);
		// 封装数据
		setAnalyzeData(analyzes, SUPPLIER_NAME, expertFileCount);

		// 获取专家上传文件表
		tableName = Constant.fileSystem.get(Constant.SUPPLIER_SYS_KEY);
		Long supplierFileCount = fileUploadMapper.getFileCountByEmp(tableName);
		// 封装数据
		setAnalyzeData(analyzes, EXPERT_NAME, expertFileCount);

		tableName = Constant.fileSystem.get(Constant.TENDER_SYS_KEY);
		Long tenderFileCount = fileUploadMapper.getFileCountByEmp(tableName);
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

		// 创建统计对象
		AnalyzeItem analyzeItem = new AnalyzeItem();
		List<Analyze> analyzes = analyzeItem.getAnalyzes();

		// 注册供应商
		Long registerSupplierCountByEmp = supplierMapper
				.getRegisterSupplierCountByEmp();
		setAnalyzeData(analyzes, SUPPLIER_NAME, registerSupplierCountByEmp);
		// 注册专家
		Long registerExpertCountByEmp = expertMapper
				.getRegisterExpertCountByEmp();
		setAnalyzeData(analyzes, EXPERT_NAME, registerExpertCountByEmp);

		// 注册后台管理
		Long registerUserCountByEmp = userMapper.getRegisterTenderCountByEmp();
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
		if(supllierLoginCount == null){
			supllierLoginCount = 0l;
		}
		setAnalyzeData(analyzes, SUPPLIER_NAME, supllierLoginCount);
		
		// 注册专家
		map.put("loginType", 3);
		Long expertLoginCount = loginLogMapper.getLoginCountByEmp(map);
		if(expertLoginCount == null){
			expertLoginCount = 0l;
		}
		setAnalyzeData(analyzes, EXPERT_NAME, expertLoginCount);

		// 注册后台管理
		map.put("loginType", 1);
		Long tenderLoginCount = loginLogMapper.getLoginCountByEmp(map);
		if(tenderLoginCount == null){
			tenderLoginCount = 0l;
		}
		setAnalyzeData(analyzes, TENDER_NAME, tenderLoginCount);
		// 设置登录总数
		analyzeItem.setSubtext(supllierLoginCount + expertLoginCount
				+ tenderLoginCount);
		return analyzeItem;
	}

}
