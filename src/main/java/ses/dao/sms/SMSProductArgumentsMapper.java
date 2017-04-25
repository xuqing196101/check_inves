package ses.dao.sms;

import java.util.List;

import ses.model.sms.SMSProductArguments;
/**
 * 
* @ClassName: SMSProductArgumentsMapper 
* @Description: 产品库管理产品参数Mapper
* @author Easong
* @date 2017年4月21日 上午10:58:13 
*
 */
public interface SMSProductArgumentsMapper {
	int deleteByPrimaryKey(String id);

	int insert(SMSProductArguments record);

	int insertSelective(SMSProductArguments record);

	SMSProductArguments selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(SMSProductArguments record);

	int updateByPrimaryKey(SMSProductArguments record);

	/**
	 * 
	 * @Title: addProductArgumentsBatch
	 * @Description: 批量插入产品参数信息
	 * @author Easong
	 * @param @param arguments
	 * @param @return 设定文件
	 * @return int 返回类型
	 * @throws
	 */
	int addProductArgumentsBatch(List<SMSProductArguments> list);

	/**
	 * 
	 * @Title: updateProductArgumentsBatch
	 * @Description: 修改商品参数信息
	 * @author Easong
	 * @param @param record
	 * @param @return 设定文件
	 * @return int 返回类型
	 * @throws
	 */
	int updateProductArgumentsBatch(SMSProductArguments record);
	
	/**
	 * 
	* @Title: selectByArgumentId 
	* @Description: 根据ARGUMENTS_ID查询所对应的参数
	* @author Easong
	* @param @return    设定文件 
	* @return List<SMSProductArguments>    返回类型 
	* @throws
	 */
	List<SMSProductArguments> selectByArgumentId(String argumentId);
	
	/**
	 * 
	* @Title: deleteByArgumentId 
	* @Description: 根据ARGUMENTS_ID删除所对应的参数
	* @author Easong
	* @param @param argumentId
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	int deleteByArgumentId(String argumentId);
}