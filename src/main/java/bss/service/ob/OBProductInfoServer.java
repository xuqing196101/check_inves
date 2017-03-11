package bss.service.ob;

import bss.model.ob.OBProduct;

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
}
