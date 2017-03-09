package bss.service.ob;

import bss.model.ob.OBProduct;

/***
 * 竞价信息相关的产品 接口
 * @author YangHangLiang
 *
 */
public interface OBProductInfoServer {
     /**
      * 保存竞价信息相关的产品
      * */
	 void saveOBProductInfo(OBProduct product);
}
