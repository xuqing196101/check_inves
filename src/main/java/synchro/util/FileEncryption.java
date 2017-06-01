package synchro.util;

import java.io.File;

import org.apache.commons.lang.StringUtils;
/**
 * 上传文件path混淆
 * @author LWL
 *
 */
public class FileEncryption {
/**
 * 混淆路劲
 * @param path  路劲
 * @return
 */
  public static String setEncryption(String path){
    if(path!=null&&!"".equals(path)){
      String[] spts = path.split("'"+File.separator+"'");
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
      StringBuffer buffer=new StringBuffer(StringUtils.join(spts, File.separator));
      if(buffer.length()>0){
    	  buffer=buffer.delete(0, 1);
      }
      buffer.replace(buffer.indexOf(File.separator), buffer.indexOf(File.separator)+1, ":");
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
      path=path.replaceAll(":", "'"+File.separator+"'");
      String[] spts = path.split("'"+File.separator+"'");
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
      return File.separator+StringUtils.join(spts, File.separator);
    }else{
      return "";
    }
  }
}
