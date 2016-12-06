package ses.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class WordUtil {
	   
	/**
	* @Desc：生成word文件
	* @Author：Ws
	* @Date： 2016年7月13日9:46:44
	* @param dataMap word中需要展示的动态数据，用map集合来保存
	* @param templateName word模板名称，例如：test.ftl
	* @param fileName 生成的文件名称，例如：test.doc
	*/
    public static String createWord(Map<String, Object> dataMap,String templateName,String fileName ,HttpServletRequest request){
        //存放路径
	    //String url = request.getSession().getServletContext().getRealPath("/WEB-INF/upload_file/");
	    //String url="";
	    //String filePath = FileUtil.getPath();
	    //暂存路径
	    String filePath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload_file/");
	    try {
	        //创建配置实例 
	    	Configuration configuration = new Configuration();
	    	
	    	//设置编码
	        configuration.setDefaultEncoding("UTF-8");
	            
	        //ftl模板文件统一放至 ses.document.template 包下面
	        TemplateLoader templateLoader=new ClassTemplateLoader(WordUtil.class,"/ses/document/template/"); 
	            
	        //设置Configuration的模板
	        configuration.setTemplateLoader(templateLoader);
	            
	        //获取模板 
	        Template template = configuration.getTemplate(templateName);
	            
	        //封装文件名
	        fileName = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase() + "_" + fileName;
	           
	        //输出文件
	        File outFile = new File(filePath+File.separator+fileName);
	            
	        //如果输出目标文件夹不存在，则创建
	        if (!outFile.getParentFile().exists()){
	            outFile.getParentFile().mkdirs();
	        }
	            
	        //将模板和数据模型合并生成文件 
	        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"UTF-8"));
	        template.process(dataMap, out);
	            
	        //关闭流
	        out.flush();
	        out.close();
	        	
	        //FileUtils.copyFile(outFile, new File(url+"/"+fileName));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		return fileName;
	}
}
