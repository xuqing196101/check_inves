package synchro.util;

import java.io.File;

import org.apache.commons.lang.StringUtils;
/**
 * 上传文件path混淆
 * @author LWL
 *
 */
public class FileEncryption {
	private static String SEPARATOR = ""; 
	private static String XSEPARATOR = ""; 
/**
 * 混淆路劲
 * @param path  路劲
 * @return
 */
  static{
	  String os = System.getProperty("os.name").toLowerCase();
	  if(os!=null&&!"".equals(os)){
		  if(os.indexOf("windows")>=0){
			  SEPARATOR="\\\\";
			  XSEPARATOR="\\";
		  }else{
			  SEPARATOR="/";
			  XSEPARATOR="/";
		  }
		}
  }
  public static void main(String[] args) {
	  String decrypted = getDecrypted("file:upload/attachments/tmp/WU_FILE_1/123.png_tmp");
	  System.out.println(decrypted);
}
  public static String setEncryption(String path){
    if(path!=null&&!"".equals(path)){
      String[] spts = path.split(SEPARATOR);
        for(int i=0;i<spts.length;i++){
          if(spts[i]!=null&&!"".equals(spts[i])){
            if("web".equals(spts[i])){
              spts[i]="file";
            }
            if("attach".equals(spts[i])){
              spts[i]="upload";
            }
            if("uploads".equals(spts[i])){
              spts[i]="attachments";
            }
          }
        }
      StringBuffer buffer=new StringBuffer(StringUtils.join(spts, XSEPARATOR));
      if(buffer.length()>0){
    	  buffer=buffer.delete(0, 1);
      }
      buffer.replace(buffer.indexOf(XSEPARATOR), buffer.indexOf(XSEPARATOR)+1, ":");
      return buffer.toString();
    }else{
      return "";
    }
  }
/**
 * 解除混淆
 * @param path 路劲
 * @return
 */
  public static String getDecrypted(String path){
    if(path!=null&&!"".equals(path)){
      path=path.replaceAll(":", SEPARATOR);
      String[] spts = path.split(SEPARATOR);
      for(int i=0;i<spts.length;i++){
        if(spts[i]!=null&&!"".equals(spts[i])){
          if("file".equals(spts[i])){
            spts[i]="web";
          }
          if("upload".equals(spts[i])){
            spts[i]="attach";
          }
          if("attachments".equals(spts[i])){
            spts[i]="uploads";
          }
        }
      }
      return XSEPARATOR+StringUtils.join(spts, XSEPARATOR);
    }else{
      return "";
    }
  }
}
