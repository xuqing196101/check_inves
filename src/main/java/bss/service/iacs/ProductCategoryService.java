package bss.service.iacs;

import java.util.List;

import bss.model.iacs.ProductCategory;

/**
 * 
 * @Title: ProductCategoryService
 * @Description: 产品目录业务接口 
 * @author Li Xiaoxiao
 * @date  2016年10月25日,下午2:09:07
 *
 */
public interface ProductCategoryService {

	/**
	 * 
	* @Title: add
	* @Description: 添加实体 
	* author: Li Xiaoxiao 
	* @param @param productCategory     
	* @return void     
	* @throws
	 */
	void add(ProductCategory productCategory);
	
	/**
	 * 
	* @Title: queryById
	* @Description: 根据 id查询
	* author: Li Xiaoxiao 
	* @param @param cid
	* @param @return     
	* @return ProductCategory     
	* @throws
	 */
	ProductCategory queryById(String id);
	/**
	 * 
	* @Title: update
	* @Description: 修改产品目录 
	* author: Li Xiaoxiao 
	* @param @param ProductCategory     
	* @return void     
	* @throws
	 */
	void update(ProductCategory ProductCategory);
	
	/**
	 * 
	* @Title: queryAll
	* @Description: 查询所有的目录结构 
	* author: Li Xiaoxiao 
	* @param @return     
	* @return List<ProductCategory>     
	* @throws
	 */
	List<ProductCategory> queryAll();
	
}
