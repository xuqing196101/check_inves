package ses.service.sms;

import java.io.IOException;
import java.util.List;

import bss.model.pqims.PqInfo;
import ses.model.sms.ProductCon;


public interface ProductConService {
	
	/**
	 * 1.获取所有模板对象
	 */
	List<ProductCon> getAll(Integer pageNum);
	
	/**
	 * Description: 查询列表，分页
	 * 
	 * @author Li ChenHao
	 * @version 2017-03-01
	 * @param ProductCon
	 * @param pageNum
	 * @return List<ProductCon>
	 */
	public List<ProductCon> findProductCon(ProductCon ProductCon, int page);
	
	/**
	 * Description: 保存产品
	 * 
	 * @author Li ChenHao
	 * @version 2017-03-01
	 * @param ProductCon
	 */
	public void saveOrUpdateProductCon(ProductCon ProductCon);
	
	/**
	 * Description: 根据id获得产品信息
	 * 
	 * @author Li ChenHao
	 * @version 2017-03-01
	 * @param ProductCon
	 */
	public ProductCon getProductCon(String id);
	
	/**
	 * Description: 根据id删除产品
	 * 
	 * @author Li ChenHao
	 * @version 2017-03-01
	 * @param ProductCon
	 */
	public void deleteProductCon(String id);
	
	
}
