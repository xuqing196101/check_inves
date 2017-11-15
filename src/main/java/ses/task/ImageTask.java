package ses.task;

import bss.util.FileUtil;
import bss.util.PropUtil;
import common.constant.StaticVariables;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * 
 * @Title: ImageTask
 * @Description:  每天定时把供应商，专家的图片导进来
 * @author Li Xiaoxiao
 * @date  2017年6月6日,下午6:40:09
 *
 */

@Component("ImageTask")
public class ImageTask {

	/**
	 * 
	* @Title: imageHandler
	* @Description: 供应商专家的图片，每天只能外网导出
	* author: Li Xiaoxiao
	* @param      
	* @return void     
	* @throws
	 */
	public void imageHandler(){
		//图片只能外网导出
		if ("1".equals(StaticVariables.ipAddressType)) {
			Date date=new Date();
			SimpleDateFormat sdf=new  SimpleDateFormat("yyyyMMdd");
			Calendar cale = Calendar.getInstance();
			cale.setTime(date);
			cale.add(Calendar.DAY_OF_MONTH, -1);
			String src = sdf.format(cale.getTime());//昨天的文件夹名字
			// 供应商图片上传目录：/web/attach/uploads/supplier
			String supplierPath=PropUtil.getProperty("file.base.path")+PropUtil.getProperty("file.supplier.system.path")+"/"+src;//供应商所有图片
			// 数据同步导出目录: /web/sync/export
			String synchExport=PropUtil.getProperty("file.sync.base")+PropUtil.getProperty("file.sync.export")+"/"+src;
			FileUtil.copyFolder(supplierPath, synchExport);
		}
	}
	
	/**
	 *
	 * Description: 供应商专家的图片，每天只能内网导入
	 *
	 * @author Easong
	 * @version 2017/10/19
	 * @param 
	 * @since JDK1.7
	 */
	public void imageImportHandler(){
		//图片只能内网导入
		if ("0".equals(StaticVariables.ipAddressType)) {
			Date date=new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Calendar cale = Calendar.getInstance();
			cale.setTime(date);
			cale.add(Calendar.DAY_OF_MONTH, -1);
			String src = sdf.format(cale.getTime());//昨天的文件夹名字
            // 数据同步导入目录: /web/sync/import
			String supplier = PropUtil.getProperty("file.sync.base") + PropUtil.getProperty("file.sync.import")+"/"+src;//供应商图片
            // 供应商图片上传目录：/web/attach/uploads/supplier
			String supplierPath = PropUtil.getProperty("file.base.path") + PropUtil.getProperty("file.supplier.system.path")+"/"+src;//供应商专路径
			FileUtil.copyFolder(supplier, supplierPath);
		}
	}
}
