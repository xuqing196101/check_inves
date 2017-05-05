package ses.service.sms.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.bms.CategoryParameterMapper;
import ses.dao.sms.SMSProductArgumentsMapper;
import ses.dao.sms.SMSProductBasicMapper;
import ses.dao.sms.SMSProductCheckRecordMapper;
import ses.dao.sms.SMSProductInfoMapper;
import ses.dao.sms.SupplierMapper;
import ses.model.bms.User;
import ses.model.sms.SMSProductArguments;
import ses.model.sms.SMSProductBasic;
import ses.model.sms.SMSProductCheckRecord;
import ses.model.sms.SMSProductInfo;
import ses.model.sms.SMSProductVO;
import ses.model.sms.Supplier;
import ses.service.sms.SMSProductLibService;
import ses.util.PropertiesUtil;
import ses.util.SMSProductLibConstant;
import ses.util.UUIDUtils;
import synchro.service.SynchRecordService;
import synchro.util.FileUtils;
import synchro.util.OperAttachment;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;

import common.annotation.SystemServiceLog;
import common.constant.Constant;
import common.dao.FileUploadMapper;
import common.model.UploadFile;
import common.service.UploadService;
import common.utils.JdcgResult;

/**
 * 
 * @ClassName: SMSProductLibServiceImpl
 * @Description: 产品库管理接口的实现类
 * @author Easong
 * @date 2017年4月18日 下午6:06:50
 * 
 */
@Service
public class SMSProductLibServiceImpl implements SMSProductLibService {

	// 注入商品基本信息
	@Autowired
	private SMSProductBasicMapper smsProductBasicMapper;

	// 注入商品描述信息
	@Autowired
	private SMSProductInfoMapper smsProductInfoMapper;

	// 注入商品参数
	@Autowired
	private SMSProductArgumentsMapper smsProductArgumentsMapper;

	// 注入商品审核记录
	@Autowired
	private SMSProductCheckRecordMapper smsProductCheckRecordMapper;

	// 文件存储
	@Autowired
	private FileUploadMapper fileDao;

	// 产品参数管理
	@Autowired
	private CategoryParameterMapper cateParamterMapper;

	@Autowired
	private SupplierMapper supplierMapper;
	
	// 定义图片表名称
	private String tableName = Constant.SUPPLIER_SYS_VALUE;
	
	/**文件**/
	@Autowired
    private UploadService uploadService;
	 /**
     * 同步service
     */
    @Autowired
    private SynchRecordService recordService;
	/**
	 * 
	 * @Title: addProductLibInfo
	 * @Description: 供应商后台录入产品信息
	 * @author Easong
	 * @param @param smsProductVO
	 * @param @return 设定文件
	 * @throws
	 */
	@Override
	public JdcgResult addProductLibInfo(SMSProductVO smsProductVO,
			Integer flag, User user) throws Exception{
		/** -----用户未输入信息 **/
		if (smsProductVO == null) {
			return JdcgResult.build(500, "请填写表单信息");
		}
		// 获取商品的基本信息
		SMSProductBasic productBasic = smsProductVO.getProductBasic();
		// 获取商品的描述信息
		SMSProductInfo smsProductInfo = smsProductVO.getSmsProductInfo();
		// 获取商品的参数信息
		List<SMSProductArguments> arguments = smsProductVO
				.getProductArguments();
		// 商品基本信息主键id
		String id = UUIDUtils.getUUID32();
		// 生成产品参数ID
		String paramId = UUIDUtils.getUUID32();

		/****** 用户点击保存时，不做表单校验 ******/
		if (flag != null && flag == SMSProductLibConstant.PRODUCT_LIB_SAVE_FLAG) {
			if (productBasic != null) {
				// 设置商品id
				productBasic.setId(id);
				// 设置产品的状态 1：上架 0:下架
				productBasic
						.setProductStatus(SMSProductLibConstant.PRODUCT_LIB_ITEM_STATUS_PUTAWAY);
				// 设置产品发布状态0：暂存 1：待审核 2：审核不通过 3：审核通过
				productBasic
						.setStatus(SMSProductLibConstant.PRODUCT_LIB_ITEM_STATUS_TEMP_SAVE);
				// 设置产品是否删除
				productBasic
						.setIsDeleted(SMSProductLibConstant.PRODUCT_LIB_ITEM_NOT_DELETE);
				// 校验图片是否已上传
				vertifyMajorPictureUpload(productBasic, tableName);
				
				// 设置创建人ID
				productBasic.setCreaterId(user.getTypeId());
				// 设置创建时间和修改时间
				productBasic.setUpdatedAt(new Date());
				productBasic.setCreatedAt(new Date());
				smsProductBasicMapper.insert(productBasic);
				// 保存商品基本信息
			}
			if (smsProductInfo != null) {
				// 设置产品参数ID
				smsProductInfo.setArgumentsId(paramId);
				// 设置商品基本信息ID
				smsProductInfo.setProudctBasicId(id);
				// 校验子图是否上传
				vertifySubPictureUpload(smsProductInfo);
				// 设置创建时间和修改时间
				smsProductInfo.setUpdatedAt(new Date());
				smsProductInfo.setCreatedAt(new Date());
				// 保存商品描述信息
				smsProductInfoMapper.insertSelective(smsProductInfo);
			}
			// 保存商品参数信息
			if (arguments != null) {
				for (SMSProductArguments argument : arguments) {
					// 如果参数是图片且不是必填写的则如果没上传则置为空
					if (SMSProductLibConstant.PRODUCT_LIB_ARGU_TYPE.equals(argument.getParameterType())) {
						// 校验参数图片是否上传
						vertifyArguPictureUpload(argument);
					}
					argument.setArgumentsId(paramId);
					// 设置创建时间和修改时间
					argument.setUpdatedAt(new Date());
					argument.setCreatedAt(new Date());
					smsProductArgumentsMapper.insertSelective(argument);
				}
			}
		}

		/****** 用户点击提交按钮时触发 ******/
		if (flag != null
				&& flag == SMSProductLibConstant.PRODUCT_LIB_SUBMIT_FLAG) {

			/** -----用户输入信息----表单数据校验 **/

			JdcgResult result = verifyProductBasicInfo(productBasic);
			if (result != null) {
				// 校验失败
				return result;
			}
			/** ----校验完毕 保存---- **/
			// 设置商品id
			productBasic.setId(id);
			// 设置产品的状态 1：上架 0:下架
			productBasic
					.setProductStatus(SMSProductLibConstant.PRODUCT_LIB_ITEM_STATUS_PUTAWAY);
			// 设置产品发布状态 1：待审核 2：审核不通过 3：审核通过
			productBasic
					.setStatus(SMSProductLibConstant.PRODUCT_LIB_ITEM_STATUS_WAIT_CHECK);
			// 设置产品是否删除
			productBasic
					.setIsDeleted(SMSProductLibConstant.PRODUCT_LIB_ITEM_NOT_DELETE);

			vertifyMajorPictureUpload(productBasic, tableName);

			// 设置创建人ID
			productBasic.setCreaterId(user.getTypeId());
			// 设置创建时间和修改时间
			productBasic.setUpdatedAt(new Date());
			productBasic.setCreatedAt(new Date());

			if (smsProductInfo != null && productBasic != null) {
				JdcgResult resultInfo = verifyProductIntroduceInfo(smsProductInfo);
				if (resultInfo != null) {
					// 校验失败
					return resultInfo;
				}
				vertifySubPictureUpload(smsProductInfo);
				// 设置产品参数ID
				smsProductInfo.setArgumentsId(paramId);
				// 设置历史价格
				smsProductInfo.setHistoryPrice(productBasic.getPrice());
				// 设置商品基本信息ID
				smsProductInfo.setProudctBasicId(id);
				// 设置创建时间和修改时间
				smsProductInfo.setUpdatedAt(new Date());
				smsProductInfo.setCreatedAt(new Date());
			}

			// 判断该项是否是必填信息
			if (arguments != null && arguments.size() > 0
					&& smsProductInfo != null && productBasic != null) {
				// 1填写，0可以不填 required自定义字段，只做表单填写校验
				for (SMSProductArguments argument : arguments) {
					// 如果参数是图片且不是必填写的则如果没上传则置为空
					if (SMSProductLibConstant.PRODUCT_LIB_ARGU_TYPE.equals(argument.getParameterType())) {
						vertifyArguPictureUpload(argument);
					}
					if (StringUtils.isEmpty(argument.getParameterValue())
							&& argument.getRequired() == SMSProductLibConstant.PRODUCT_LIB_ARGU_REQUIRED) {
						return JdcgResult.build(500, "参数:" + argument.getParamName()
								+ "不能为空");
					}
					argument.setArgumentsId(paramId);
					// 设置创建时间和修改时间
					argument.setUpdatedAt(new Date());
					argument.setCreatedAt(new Date());
				}
			}

			// 将以上信息保存
			// 保存商品基本信息
			if (productBasic != null) {
				smsProductBasicMapper.insertSelective(productBasic);
			}
			// 保存商品描述信息
			if (smsProductInfo != null) {
				smsProductInfoMapper.insertSelective(smsProductInfo);
			}
			// 保存商品参数信息
			if (arguments != null) {
				for (SMSProductArguments argu : arguments) {
					smsProductArgumentsMapper.insertSelective(argu);
				}
			}

		}
		return JdcgResult.ok();
	}

	/**
	 * 
	* @Title: vertifyArguPictureUpload 
	* @Description: 校验参数图片是否上传
	* @author Easong
	* @param @param argument    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	private void vertifyArguPictureUpload(SMSProductArguments argument) {
		List<UploadFile> parameterValuePictureType = fileDao
				.findBybusinessId(argument.getParameterValue(),
						tableName);
		// 子图未上传
		if (parameterValuePictureType != null
				&& parameterValuePictureType.size() == 0) {
			argument.setParameterValue(null);
		}
	}

	/**
	 * 
	* @Title: vertifySubPictureUpload 
	* @Description: 校验子图是否上传
	* @author Easong
	* @param @param smsProductInfo    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	private void vertifySubPictureUpload(SMSProductInfo smsProductInfo) {
		List<UploadFile> pictureSub = fileDao.findBybusinessId(
				smsProductInfo.getPictureSub(), tableName);
		// 子图未上传
		if (pictureSub != null && pictureSub.size() == 0) {
			smsProductInfo.setPictureSub(null);
		}
	}

	/**
	 * 
	* @Title: vertifyMajorPictureUpload 
	* @Description: 校验主图是否上传
	* @author Easong
	* @param @param productBasic
	* @param @param tableName    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	private void vertifyMajorPictureUpload(SMSProductBasic productBasic,
			String tableName) {
		List<UploadFile> pictureMajor = fileDao.findBybusinessId(
				productBasic.getPictureMajor(), tableName);
		// 主图未上传
		if (pictureMajor != null && pictureMajor.size() == 0) {
			productBasic.setPictureMajor(null);
		}
	}

	/**
	 * 
	 * @Title: findAllProductLibBasicInfo
	 * @Description: 查询商品的基本信息
	 * @author Easong
	 * @param @return 设定文件
	 * @throws
	 */
	@Override
	public List<SMSProductBasic> findAllProductLibBasicInfo(
			Map<String, Object> map) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage((Integer) (map.get("page")),
				Integer.parseInt(config.getString("pageSize")));
		return smsProductBasicMapper.findAllProductLibBasicInfo(map);
	}

	/**
	 * 
	 * @Title: findSignalProductInfo
	 * @Description: 查询单个商品的全部信息
	 * @author Easong
	 * @param @param id
	 * @param @return 设定文件
	 * @throws
	 */
	@Override
	public Map<String, Object> findSignalProductInfo(String id) {
		String[] flag = null;
		// 商品id
		String pid = null;
		// 产品审核状态
		String pStatus = null;
		if (id != null && id.indexOf(",") != -1) {
			flag = id.split(",");
			pid = flag[0];
			pStatus = flag[1];
		} else {
			pid = id;
		}

		// 查询商品的基本信息，带出商品的类目
		SMSProductBasic smsProductBasic = smsProductBasicMapper
				.selectByPrimaryKey(pid);
		// 查询商品的参数信息带出商品的参数和商品参数对应的参数信息
		SMSProductInfo smsProdcutInfo = smsProductInfoMapper
				.findSMSProdcutInfo(pid);
		Map<String, Object> map = new HashMap<String, Object>();
		if (smsProductBasic != null) {
			// 封装商品的基本信息
			// 修改时主图片上传bussiness_id
			if(smsProductBasic.getPictureMajor() == null){
				smsProductBasic.setPictureMajor(UUIDUtils.getUUID32());
			}
			map.put("smsProductBasic", smsProductBasic);
		}

		if (smsProdcutInfo != null) {
			// 修改时子图片上传bussiness_id
			if(smsProdcutInfo.getPictureSub() == null){
				smsProdcutInfo.setPictureSub(UUIDUtils.getUUID32());
			}
			// 获取参数信息
			List<SMSProductArguments> arguments = smsProdcutInfo.getSmsProductArguments();
			if(arguments != null && arguments.size() > 0){
				for (SMSProductArguments smsProductArguments : arguments) {
					if(SMSProductLibConstant.PRODUCT_LIB_ARGU_TYPE.equals(smsProductArguments.getParameterType())
							&& smsProductArguments.getParameterValue() == null){
						// 修改时参数值为空并且是附件，设置bussiness_id
						smsProductArguments.setParameterValue(UUIDUtils.getUUID32());
					}
				}
			}
			
			// 封装商品的参数信息
			map.put("smsProdcutInfo", smsProdcutInfo);
		}

		// 查询商品审核信息 只查询审核未通过和通过的
		if (pStatus != null) {
			if (SMSProductLibConstant.PRODUCT_LIB_ITEM_STATUS_NOT_THROUGH_CHECK
					.equals(Integer.parseInt(pStatus))
					|| SMSProductLibConstant.PRODUCT_LIB_ITEM_STATUS_THROUGH_CHECK
							.equals(Integer.parseInt(pStatus))) {
				SMSProductCheckRecord productCheckRecord = smsProductCheckRecordMapper
						.selectByProductBasicId(pid);
				map.put("productCheckRecord", productCheckRecord);
			}
		}
		return map;
	}

	/**
	 * 
	 * @Title: updateSignalProductInfo
	 * @Description: 供应商后台修改产品信息
	 * @author Easong
	 * @param @param smsProductVO
	 * @param @return 设定文件
	 * @throws
	 */
	@Override
	public JdcgResult updateSignalProductInfo(SMSProductVO smsProductVO) throws Exception{

		/****************** 获取商品的基本信息 ******************/
		SMSProductBasic productBasic = smsProductVO.getProductBasic();
		// 校验商品基本信息表单
		JdcgResult result = verifyProductBasicInfo(productBasic);
		if (result != null) {
			// 校验失败
			return result;
		}
		// 查询该商品是否失效
		SMSProductBasic existsmsProductBasic = smsProductBasicMapper
				.selectByPrimaryKey(productBasic.getId());
		if (existsmsProductBasic == null) {
			return JdcgResult.build(500, "该商品已失效");
		}

		/****************** 获取商品的描述信息 ******************/
		SMSProductInfo smsProductInfo = smsProductVO.getSmsProductInfo();
		if (smsProductInfo != null && productBasic != null) {
			JdcgResult resultInfo = verifyProductIntroduceInfo(smsProductInfo);
			if (resultInfo != null) {
				// 校验失败
				return resultInfo;
			}
			// 修改时间
			smsProductInfo.setUpdatedAt(new Date());
		}

		// 修改
		// 保存商品基本信息
		if (productBasic != null) {
			// 判断主图片是否上传
			vertifyMajorPictureUpload(productBasic, tableName);
			// 暂存状态-待审核状态
			productBasic.setStatus(SMSProductLibConstant.PRODUCT_LIB_ITEM_STATUS_WAIT_CHECK);
			smsProductBasicMapper.updateByPrimaryKeySelective(productBasic);
		}
		// 保存商品描述信息
		if (smsProductInfo != null) {
			// 判断子图片是否上传
			vertifySubPictureUpload(smsProductInfo);
			smsProductInfoMapper.updateByPrimaryKeySelective(smsProductInfo);
		}

		/****************** 获取商品的参数信息 ******************/
		// 判断该品目下有没有参数
		/*List<CategoryParameter> list = cateParamterMapper
				.getParamsByCateId(productBasic.getCategoryId());*/
		// 查询用户第一次有没有存储新的参数
		// List<SMSProductArguments> selectByArgumentId =
		// smsProductArgumentsMapper.selectByArgumentId(smsProductInfo.getArgumentsId());
		// 用户修改参数信息时，先删除后增加
		List<SMSProductArguments> arguments = smsProductVO
				.getProductArguments();
		// 判断如果用户第一次录入没有参数，修改后出现参数
		if(arguments == null){
			// 删除原先的参数
			smsProductArgumentsMapper.deleteByArgumentId(smsProductInfo.getArgumentsId());
		}
		if(arguments != null && arguments.size() > 0){
			// 参数是否重新加载
			SMSProductArguments selectByPrimaryKey = smsProductArgumentsMapper.selectByPrimaryKey(arguments.get(0).getId());
			// 只做修改
			if(selectByPrimaryKey != null){
				// 1填写，0可以不填 required自定义字段，只做表单填写校验
				for (SMSProductArguments argument : arguments) {
					if (StringUtils.isEmpty(argument.getParameterValue())
							&& argument.getRequired() == SMSProductLibConstant.PRODUCT_LIB_ARGU_REQUIRED) {
						return JdcgResult.build(500, "参数:" + argument.getParamName()
								+ "不能为空");
					}
					// 判断有没有上传图片
					if (SMSProductLibConstant.PRODUCT_LIB_ARGU_TYPE.equals(argument.getParameterType())) {
						vertifyArguPictureUpload(argument);
					}
					// 修改时间
					argument.setUpdatedAt(new Date());
					// 保存商品参数信息
					smsProductArgumentsMapper.updateByPrimaryKeySelective(argument);
				}
			// 目录更换
			}else{
				// 删除原先的参数
				smsProductArgumentsMapper.deleteByArgumentId(smsProductInfo.getArgumentsId());
				// 1填写，0可以不填 required自定义字段，只做表单填写校验
				for (SMSProductArguments argument : arguments) {
					// 如果参数是图片且不是必填写的则如果没上传则置为空
					if (SMSProductLibConstant.PRODUCT_LIB_ARGU_TYPE.equals(argument.getParameterType())) {
						vertifyArguPictureUpload(argument);
					}
					if (StringUtils.isEmpty(argument.getParameterValue())
							&& argument.getRequired() == SMSProductLibConstant.PRODUCT_LIB_ARGU_REQUIRED) {
						return JdcgResult.build(500, "参数:" + argument.getParamName()
								+ "不能为空");
					}
					argument.setArgumentsId(smsProductInfo.getArgumentsId());
					// 设置创建时间和修改时间
					argument.setUpdatedAt(new Date());
					argument.setCreatedAt(new Date());
					// 保存商品参数信息
					smsProductArgumentsMapper.insertSelective(argument);
				}
			}
			
		}
		/*// 判断该项是否是必填信息
		if (arguments != null && arguments.size() > 0 && smsProductInfo != null
				&& productBasic != null) {
			// 1填写，0可以不填 required自定义字段，只做表单填写校验
			for (SMSProductArguments argument : arguments) {
				if (StringUtils.isEmpty(argument.getParameterValue())
						&& argument.getRequired() == SMSProductLibConstant.PRODUCT_LIB_ARGU_REQUIRED) {
					return JdcgResult.build(500, "参数:" + argument.getParamName()
							+ "不能为空");
				}
				// 修改时间
				argument.setUpdatedAt(new Date());
			}
		}
		// 保存商品参数信息
		if (arguments != null) {
			for (SMSProductArguments argu : arguments) {
				smsProductArgumentsMapper.updateByPrimaryKeySelective(argu);
			}
		}*/

		// 修改成功
		return JdcgResult.ok();
	}

	/**
	 * 
	 * @Title: verifyProductBasicInfo
	 * @Description: 校验商品基本信息
	 * @author Easong
	 * @param @param smsProductVO 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public JdcgResult verifyProductBasicInfo(SMSProductBasic productBasic) {
		if (productBasic != null) {
			/********* 表单校验 *************/
			// 类别
			if (StringUtils.isEmpty(productBasic.getCategoryId())) {
				return JdcgResult.build(500, "请选择类别");
			}
			// 名称
			if (StringUtils.isEmpty(productBasic.getName())) {
				return JdcgResult.build(500, "请填写商品名称");
			}
			// 价格
			if (productBasic.getPrice() == null || "".equals(productBasic.getPrice())) {
				return JdcgResult.build(500, "请填写商品价格");
			}
			// 品牌
			if (StringUtils.isEmpty(productBasic.getBrand())) {
				return JdcgResult.build(500, "请填写商品品牌");
			}
			// 型号
			if (StringUtils.isEmpty(productBasic.getTypeNum())) {
				return JdcgResult.build(500, "请填写商品型号");
			}
			// 库存
			if (productBasic.getStore() == null || "".equals(productBasic.getStore())) {
				return JdcgResult.build(500, "请填写商品库存");
			}
			// 判断用户输入的SKU是否唯一
			if (StringUtils.isEmpty(productBasic.getSku())) {
				return JdcgResult.build(500, "请填写SKU");
			}
			// 查询SKU是否已存在
			JdcgResult jdcgResult = vertifyUniqueSKU(productBasic.getSku(), productBasic.getId());
			if("500".equals(jdcgResult.getData())){
				return JdcgResult.build(500, "SKU已存在，请重新输入");
			}
		}
		return null;
	}

	/**
	 * 
	 * @Title: verifyProductBasicInfo
	 * @Description: 校验商品描述信息
	 * @author Easong
	 * @param @param smsProductVO 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public JdcgResult verifyProductIntroduceInfo(SMSProductInfo smsProductInfo) {
		// 商品描述
		if (StringUtils.isEmpty(smsProductInfo.getIntroduce())) {
			return JdcgResult.build(500, "请填写商品介绍");
		}
		return null;
	}

	/**
	 * 
	 * @Title: deleteProductLibInfo
	 * @Description: 删除产品
	 * @author Easong
	 * @param @param ids
	 * @param @return 设定文件
	 * @throws
	 */
	@Override
	public JdcgResult deleteProductLibInfo(String ids[]) throws Exception{
		
		if (ids != null) {
			for (int i = 0; i < ids.length; i++) {
				// 先查询该商品是否存在
				SMSProductBasic smsProductBasic = smsProductBasicMapper
						.selectByPrimaryKey(ids[i]);
				if (smsProductBasic == null) {
					JdcgResult.build(500, "该商品已失效");
				}
				// 删除标识 IsDeleted 1：删除
				smsProductBasic
						.setIsDeleted(SMSProductLibConstant.PRODUCT_LIB_ITEM_DELETE);
				smsProductBasicMapper
						.updateByPrimaryKeySelective(smsProductBasic);
			}
		}
		return JdcgResult.ok();
	}

	/**
	 * 
	 * @Title: findAllWaitCheck
	 * @Description: 供应商审核查询信息
	 * @author Easong
	 * @param @param map
	 * @param @return 设定文件
	 * @throws
	 */
	@Override
	public List<SMSProductBasic> findAllWaitCheck(Map<String, Object> map) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage((Integer) (map.get("page")),
				Integer.parseInt(config.getString("pageSize")));
		return smsProductBasicMapper.findAllWaitCheck(map);
	}

	/**
	 * 
	 * @Title: checkProductInfo
	 * @Description: 供应商审核产品信息---单个审核和批量审核
	 * @author Easong
	 * @param @param user
	 * @param @param smsProductCheckRecord
	 * @param @return 设定文件
	 * @throws
	 */
	@Override
	public JdcgResult checkProductInfo(User user,
			SMSProductCheckRecord smsProductCheckRecord) {
		if (smsProductCheckRecord != null
				&& StringUtils.isNotEmpty(smsProductCheckRecord
						.getProductBasicIds())) {
			// 用户点击审核状态
			Integer pStatus = null;
			// 修改商品状态
			if (SMSProductLibConstant.PRODUCT_LIB_ITEM_STATUS_THROUGH_CHECK == smsProductCheckRecord
					.getFlag()) {
				// 通过审核
				pStatus = SMSProductLibConstant.PRODUCT_LIB_ITEM_STATUS_THROUGH_CHECK;
			}
			if (SMSProductLibConstant.PRODUCT_LIB_ITEM_STATUS_NOT_THROUGH_CHECK == smsProductCheckRecord
					.getFlag()) {
				// 未通过审核
				pStatus = SMSProductLibConstant.PRODUCT_LIB_ITEM_STATUS_NOT_THROUGH_CHECK;
			}
			// 切分数组
			String[] pids = smsProductCheckRecord.getProductBasicIds().split(
					",");
			for (String pid : pids) {
				// 查询该商品是否存在
				SMSProductBasic smsProductBasic = smsProductBasicMapper
						.selectByPrimaryKey(pid);
				if (smsProductBasic == null) {
					return JdcgResult.build(500, "该商品已失效");
				}
				// 设置审核记录 产品id字段
				smsProductCheckRecord.setProductBasicId(pid);
				// 修改产品审核状态
				smsProductBasic.setStatus(pStatus);
				updateProductCheckStatus(user, smsProductCheckRecord,
						smsProductBasic);
			}
		}
		// 成功信息返回
		return JdcgResult.ok();
	}

	/**
	 * 
	 * @Title: updateProductCheckStatus
	 * @Description: 保存产品审核记录信息
	 * @author Easong
	 * @param @param user
	 * @param @param smsProductCheckRecord
	 * @param @param smsProductBasic 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void updateProductCheckStatus(User user,
			SMSProductCheckRecord smsProductCheckRecord,
			SMSProductBasic smsProductBasic) {
		// 修改审核状态
		smsProductBasicMapper.updateByPrimaryKeySelective(smsProductBasic);
		// 审核前判断该商品是否是再次审核，如果是再次审核，删除已经审核的信息，添加信息审核信息
		SMSProductCheckRecord productCheckRecord = smsProductCheckRecordMapper.selectByProductBasicId(smsProductBasic.getId());
		if(productCheckRecord != null){
			// 说明是再次审核，删除之前的审核意见，加入新的审核意见
			productCheckRecord.setIsDeleted(SMSProductLibConstant.PRODUCT_LIB_ITEM_DELETE);
			smsProductCheckRecordMapper.updateByPrimaryKeySelective(productCheckRecord);
		}
		// 设置是否删除
		smsProductCheckRecord
				.setIsDeleted(SMSProductLibConstant.PRODUCT_LIB_ITEM_NOT_DELETE);
		// 设置审核人
		smsProductCheckRecord.setCreatedId(user.getId());
		// 设置审核时间
		smsProductCheckRecord.setCreatedAt(new Date());
		// 设置审核更新时间
		smsProductCheckRecord.setUpdatedAt(new Date());
		// 保存审核信息
		smsProductCheckRecordMapper.insertSelective(smsProductCheckRecord);
	}
	
    /**
     * 实现 产品库录入的未审核的数据   外网  导出 
     */
	@Override
	public boolean exportAddProjectData(String start, String end, Date synchDate) {
		boolean boo=false;
		List<SMSProductBasic>basic= smsProductBasicMapper.selectByCreatedAt(start,end);
		// 定义 文件收集
		 List<UploadFile> uploadList=new ArrayList<>();
		 if(basic!=null && basic.size()>0){
		 for(SMSProductBasic ba: basic){
			 // 获取产品 详情 
			 SMSProductInfo info= smsProductInfoMapper.selectByBasicId(ba.getId());
			 ba.setInfo(info);
			 if(ba.getPictureMajor()!=null){
			  //根据主图 id 获取图片
			 List<UploadFile> major= uploadService.findBybusinessId(ba.getPictureMajor(), Constant.SUPPLIER_SYS_KEY);
			 uploadList.addAll(major);
			 }
			 if(info!=null){
				 if(info.getPictureSub()!=null){
				 //根据 info 子图 id 获取图片
				 List<UploadFile> sub= uploadService.findBybusinessId(info.getPictureSub(), Constant.SUPPLIER_SYS_KEY);
				 uploadList.addAll(sub);
				 }
				// 获取 产品 参数 详情
				 List<SMSProductArguments> arguments=smsProductArgumentsMapper.selectByArgumentId(info.getArgumentsId());
				 if(arguments!=null && arguments.size()>0){
			    for (SMSProductArguments smsProductArguments : arguments) {
			    	if(smsProductArguments.getParameterType().equals("附件")){
			    		 if(smsProductArguments.getParameterValue()!=null){
			    	//根据 info 子图 id 获取图片
					 List<UploadFile> value= uploadService.findBybusinessId(smsProductArguments.getParameterValue(), Constant.SUPPLIER_SYS_KEY);
					 uploadList.addAll(value);
			    		 }
			    	}
				}
			    ba.setArguments(arguments);
			    }
			 }
		 }
		 FileUtils.writeFile(FileUtils.getExporttFile(FileUtils.C_SYNCH_OUTER_PRODUCT_LIBRARY, 12),JSON.toJSONString(basic));
		 }
		 //同步附件
	        if (uploadList != null && uploadList.size() > 0){
	            FileUtils.writeFile(FileUtils.getExporttFile(FileUtils.C_SYNCH_OUTER_FILE_PRODUCT_LIBRARY, 12),JSON.toJSONString(uploadList));
	            String basePath = FileUtils.attachExportPath(13);
	            if (StringUtils.isNotBlank(basePath)){
	                OperAttachment.writeFile(basePath, uploadList);
	                recordService.synchBidding(synchDate, new Integer(uploadList.size()).toString(), synchro.util.Constant.DATA_TYPE_ATTACH_CODE, synchro.util.Constant.OPER_TYPE_EXPORT, synchro.util.Constant.CREATED_COMMIT_ATTACH);
	            }
	        }
	        recordService.synchBidding(synchDate, new Integer(basic.size()).toString(), synchro.util.Constant.SYNCH_PRODUCT_LIBRARY, synchro.util.Constant.OPER_TYPE_EXPORT, synchro.util.Constant.OUTER_PRODUCT_LIBRARY_COMMIT);
		boo=true;
		return boo;
	}
	
    /***
     * 实现 管理员 产品库审核的 相关数据 内网
     */
	@Override
	public boolean exportCheckProjectData(String start, String end,
			Date synchDate) {
	    boolean boo=false;
	    List<SMSProductCheckRecord> check=smsProductCheckRecordMapper.selectByCreatedAt(start, end);
	    if(check!=null && check.size()>0){
	    	for (SMSProductCheckRecord smsProductCheckRecord : check) {
	    		SMSProductBasic basic= smsProductBasicMapper.getByPrimaryKey(smsProductCheckRecord.getProductBasicId());
	    		smsProductCheckRecord.setBasic(basic);
			}
	      FileUtils.writeFile(FileUtils.getExporttFile(FileUtils.C_SYNCH_INNER_PRODUCT_LIBRARY, 11),JSON.toJSONString(check));
	    }
	    recordService.synchBidding(synchDate, new Integer(check.size()).toString(), synchro.util.Constant.SYNCH_PRODUCT_LIBRARY, synchro.util.Constant.OPER_TYPE_EXPORT, synchro.util.Constant.OUTER_PRODUCT_LIBRARY_COMMIT);
	    boo=true;
		return boo;
	}
	
    /**
     * 实现  导入产品库录入的未审核的数据
     */
	@Override
	public boolean importAddProjectData(File file) {
		boolean boo=false;
		 List<SMSProductBasic> list = FileUtils.getBeans(file, SMSProductBasic.class); 
		 if(list!=null && list.size()>0){
			 for (SMSProductBasic smsProductBasic : list) {
				int basicCount=smsProductBasicMapper.countById(smsProductBasic.getId());
				if(basicCount==0){
					smsProductBasicMapper.insertSelective(smsProductBasic);
					SMSProductInfo info=smsProductBasic.getInfo();
					if(info!=null){
						int infoCount=smsProductInfoMapper.countById(info.getId());
						if(infoCount==0){
							smsProductInfoMapper.insertSelective(info);
						}
					}
					List<SMSProductArguments> arguments=smsProductBasic.getArguments();
					if(arguments!=null && arguments.size()>0){
						for (SMSProductArguments smsProductArguments : arguments) {
							int argumentsCount=smsProductArgumentsMapper.countById(smsProductArguments.getId());
							if(argumentsCount==0){
								smsProductArgumentsMapper.insertBySelective(smsProductArguments);
							}
						}
					}
					
				}
			}
			  recordService.synchBidding(new Date(), list.size()+"", synchro.util.Constant.SYNCH_PRODUCT_LIBRARY, synchro.util.Constant.OPER_TYPE_IMPORT, synchro.util.Constant.OUTER_PRODUCT_LIBRARY_IMPORT);
		 }
		return boo;
	}
	
    /***
     * 实现导入    管理员 产品库审核的 相关数据
     */
	@Override
	public boolean importCheckProjectData(File file) {
		boolean boo=false;
		 List<SMSProductCheckRecord> list = FileUtils.getBeans(file, SMSProductCheckRecord.class); 
		 if(list!=null && list.size()>0){
			 for (SMSProductCheckRecord smsProductCheckRecord : list) {
				 int count=smsProductCheckRecordMapper.countById(smsProductCheckRecord.getId());
				if(count==0){
					smsProductCheckRecordMapper.insertBySelective(smsProductCheckRecord);
				}else{
					smsProductCheckRecordMapper.updateByPrimaryKeySelective(smsProductCheckRecord);
				}
				SMSProductBasic basic=smsProductCheckRecord.getBasic();
				if(basic!=null){
					smsProductBasicMapper.updateByPrimaryKeySelective(basic);
				}
			}
		 recordService.synchBidding(new Date(), list.size()+"", synchro.util.Constant.SYNCH_PRODUCT_LIBRARY, synchro.util.Constant.OPER_TYPE_IMPORT, synchro.util.Constant.OUTER_PRODUCT_LIBRARY_IMPORT);
		 }
		return boo;
	}

	
	/**
	 * 
	* @Title: vartifyUniqueSKU 
	* @Description: SKU唯一校验
	* @author Easong
	* @param @param sku
	* @param @return    设定文件 
	* @throws
	 */
	@Override
	public JdcgResult vertifyUniqueSKU(String sku, String pid) {
		String uniqueSKU = smsProductBasicMapper.vertifyUniqueSKU(sku);
		// 查询该商品
		SMSProductBasic smsProductBasic = smsProductBasicMapper.selectByPrimaryKey(pid);
		if(uniqueSKU != null && smsProductBasic == null){
			// SKU已存在
			return JdcgResult.ok(500);
		}
		// 暂存状态的判断
		if(uniqueSKU != null && smsProductBasic != null && smsProductBasic.getSku() == null){
			// SKU已存在
			return JdcgResult.ok(500);
		}
		// 不存在
		return JdcgResult.ok(200);
	}

	
	/**
	 * 
	* @Title: findAllSupplier 
	* @Description: 查询所有供应商
	* @author Easong
	* @param @return    设定文件 
	* @throws
	 */
	@Override
	public List<Supplier> findAllSupplier() {
		return supplierMapper.findQualifiedSupplier();
	}

}
