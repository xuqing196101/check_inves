package ses.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
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
    public static String createWord(Object dataMap,String templateName,String fileName ,HttpServletRequest request){
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
	        Template template1 = configuration.getTemplate(templateName, "UTF-8");
	        /****************************************文件保护不可修改*********************************************************************************************/
	        String pathLinShi = request.getSession().getServletContext().getRealPath("/WEB-INF/classes/ses/document/template/");
	        
	        String context = template1.toString();
	        
	        if (context.indexOf("<w:doNotTrackMoves/>") >= 0) {
				context = context.replace("<w:doNotTrackMoves/>", "<w:doNotTrackMoves/><w:documentProtection w:edit='readOnly' w:formatting='1' w:enforcement='1' w:cryptProviderType='rsaAES' w:cryptAlgorithmClass='hash' w:cryptAlgorithmType='typeAny' w:cryptAlgorithmSid='14' w:cryptSpinCount='100000' w:hash='gGLyhzLRCE/TOwg/5KQId6H8skrr3fZ0B07ndW890k7gMAt+SDnT+wqbA7Un2mrgGhRf1VnkTGi2eEQw8bHsuw==' w:salt='YTL2T3pOPNkRfbvKGPv71Q=='/>");
		    }
	        context = context.replace("<w:documentProtection w:enforcement=\"0\" />", "<w:documentProtection w:edit='readOnly' w:formatting='1' w:enforcement='1' w:cryptProviderType='rsaAES' w:cryptAlgorithmClass='hash' w:cryptAlgorithmType='typeAny' w:cryptAlgorithmSid='14' w:cryptSpinCount='100000' w:hash='gGLyhzLRCE/TOwg/5KQId6H8skrr3fZ0B07ndW890k7gMAt+SDnT+wqbA7Un2mrgGhRf1VnkTGi2eEQw8bHsuw==' w:salt='YTL2T3pOPNkRfbvKGPv71Q=='/>");
	        if (context.indexOf("<w:body>") >= 0) {
	        	context = context.replace("<w:body>", "<w:body><w:documentProtection w:edit='readOnly' w:formatting='1' w:enforcement='1' w:cryptProviderType='rsaAES' w:cryptAlgorithmClass='hash' w:cryptAlgorithmType='typeAny' w:cryptAlgorithmSid='14' w:cryptSpinCount='100000' w:hash='gGLyhzLRCE/TOwg/5KQId6H8skrr3fZ0B07ndW890k7gMAt+SDnT+wqbA7Un2mrgGhRf1VnkTGi2eEQw8bHsuw==' w:salt='YTL2T3pOPNkRfbvKGPv71Q=='/>");
	        } else if (context.indexOf("<w:WordDocument>") >= 0) {
		    	context = context.replace("<w:WordDocument>", "<w:WordDocument><w:DocumentProtection>ReadOnly</w:DocumentProtection><w:UnprotectPassword>88A0AC12</w:UnprotectPassword><w:StyleLock/><w:StyleLockEnforced/>");
		    } else if (context.indexOf("<w:documentProtection w:enforcement=\"0\" />") >= 0){
		    	context = context.replace("<w:documentProtection w:enforcement=\"0\" />", "<w:documentProtection w:edit='readOnly' w:formatting='1' w:enforcement='1' w:cryptProviderType='rsaAES' w:cryptAlgorithmClass='hash' w:cryptAlgorithmType='typeAny' w:cryptAlgorithmSid='14' w:cryptSpinCount='100000' w:hash='gGLyhzLRCE/TOwg/5KQId6H8skrr3fZ0B07ndW890k7gMAt+SDnT+wqbA7Un2mrgGhRf1VnkTGi2eEQw8bHsuw==' w:salt='YTL2T3pOPNkRfbvKGPv71Q=='/>");
		    }
	        
	        String fileNam = UUID.randomUUID().toString() + ".ftl";
	        
	        File file = new File(pathLinShi + File.separator + fileNam);
	        
	        
	        /* 创建写入对象 */
	        FileWriter fileWriter = new FileWriter(file);
	        /* 创建缓冲区 */
	        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"utf-8"));
	        /* 写入字符串 */
	        writer.write(context);
	        
	        /* 关掉对象 */
	        writer.flush();
	        writer.close();
	        fileWriter.flush();
	        fileWriter.close();
	        
	        
	        Template template = configuration.getTemplate(fileNam, "UTF-8");
	        
	        /*************************************************************************************************************************************/	        
	        
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
	        outFile.setWritable(false);
	        file.delete();
	        	
	        //FileUtils.copyFile(outFile, new File(url+"/"+fileName));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
		return fileName;
	}
}
