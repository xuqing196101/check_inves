package bss.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
/***
 * 验证信息
 * @author YanghongLiang
 *
 */
public class CheckUtil {
    /**
     * 检查 list 是否 有空
     * @return
     */
	public static boolean isList(List<String> list){
		 if(list !=null && list.size()>0){
		for(String item:list){
			if(item==""||item==null){
				return true;
			}
		}
		 }else{
			 return false;
		 }
		return false;
	}
	/**
	 * 检查 数组 是否有空
	 * @return
	 */
	public static boolean isArrayString(String [] arrString){
		if(arrString !=null &&arrString.length>0){
			for(String item:arrString){
				if(item==""|| item==null){
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 组合字符串
	 */
	public static String combinationInteger(Integer [] intArray){
		String returnStr="";
		if(intArray!=null){
			for(int i=0;intArray.length>i;i++){
				returnStr+=intArray[i]+":";
			}
			returnStr=returnStr.substring(0, returnStr.length()-1);
		}
		return returnStr;
	}
}
