package ses.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

import bss.util.FileUtil;
import bss.util.PropUtil;
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
	* @Description: 供应商专家的图片，每天定时弄到内网
	* author: Li Xiaoxiao 
	* @param      
	* @return void     
	* @throws
	 */
	public void imageHandler(){
		Date date=new Date();
		SimpleDateFormat sdf=new  SimpleDateFormat("yyyyMMdd");
		Calendar cale = Calendar.getInstance();
		cale.setTime(date);
		cale.add(Calendar.DAY_OF_MONTH, -1);
		String src = sdf.format(cale.getTime());//昨天的文件夹名字
		String supplierPath=PropUtil.getProperty("file.base.path")+PropUtil.getProperty("file.supplier.system.path")+"/"+src;//供应商所有图片
		String synchExport=PropUtil.getProperty("file.sync.base")+PropUtil.getProperty("file.sync.export")+"/"+src;
		FileUtil.copyFolder(supplierPath, synchExport);
		
	}
	
	public void imageImportHandler(){
		Date date=new Date();
		SimpleDateFormat sdf=new  SimpleDateFormat("yyyyMMdd");
		Calendar cale = Calendar.getInstance();
		cale.setTime(date);
		cale.add(Calendar.DAY_OF_MONTH, -1);
		String src = sdf.format(cale.getTime());//昨天的文件夹名字
		String supplier=PropUtil.getProperty("file.sync.base")+PropUtil.getProperty("file.sync.import")+"/"+src;//供应商图片
		String supplierPath=PropUtil.getProperty("file.base.path")+PropUtil.getProperty("file.supplier.system.path")+"/"+src;//供应商专路径
		FileUtil.copyFolder(supplier, supplierPath);
		
	}
	
	
}
