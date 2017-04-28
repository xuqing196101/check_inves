package bss.service.ob;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import bss.model.ob.OBProduct;
import bss.model.ob.OBProductInfo;

/***
 * 竞价信息相关的产品 接口
 * 
 * @author YangHangLiang
 * 
 */
public interface OBProductInfoServer {
	/**
	 * 保存竞价信息相关的产品
	 * */
	void saveOBProductInfo(OBProduct product);

	/**
	 * 
	 * Description: 查询产品数量总和
	 * 
	 * @author zhang shubin
	 * @version 2017年3月11日
	 * @param @param id
	 * @param @return
	 * @return int
	 * @exception
	 */
	int selectCount(String id);
	/**
	 * 根据竞价 id 获取 产品 集合
	 * @param projectId
	 * @return
	 */
	List<OBProductInfo> selectByProjectId(String projectId);
	/**
	 * 根据竞价 id 获取 产品 名称
	 * @param projectId
	 * @return
	 */
	List<OBProductInfo> getProductName(String projectId);

}
