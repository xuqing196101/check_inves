package bss.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
	/****
	 * 验证 身份证 * 身份证15位编码规则：dddddd yymmdd xx p dddddd：6位地区编码 yymmdd:
	 * 出生年(两位年)月日，如：910215 xx: 顺序编码，系统产生，无法确定 p: 性别，奇数为男，偶数为女
	 * 
	 * 身份证18位编码规则：dddddd yyyymmdd xxx y dddddd：6位地区编码 yyyymmdd:
	 * 出生年(四位年)月日，如：19910215 xxx：顺序编码，系统产生，无法确定，奇数为男，偶数为女 y: 校验码，该位数值可通过前17位计算获得
	 * 
	 * 前17位号码加权因子为 Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ]
	 * 验证位 Y = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ]
	 * 如果验证码恰好是10，为了保证身份证是十八位，那么第十八位将用X来代替 校验位计算公式：Y_P = mod( ∑(Ai×Wi),11 )
	 * i为身份证号码1...17 位; Y_P为校验码Y所在校验码数组位置
	 */
	public static String validateIdCard(String card){
		String returnStr="";
		 //15位和18位身份证号码的正则表达式
		String regIdCard="^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$";
		// 编译正则表达式
	    Pattern pattern = Pattern.compile(regIdCard); 
	    Matcher matcher = pattern.matcher(card);
	    // 字符串是否与正则表达式相匹配
	    boolean rs = matcher.matches();
		//如果通过该验证，说明身份证格式正确，但准确性还需计算
		 if(rs){
		  if(card.length()==18){
		   Integer idCardWi[]={ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 }; //将前17位加权因子保存在数组里
		   Integer idCardY[]={  1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 }; //这是除以11后，可能产生的11位余数、验证码，也保存成数组
		   int idCardWiSum=0; //用来保存前17位各自乖以加权因子后的总和
		   for(int i=0;i<17;i++){
		    idCardWiSum+=Integer.valueOf(card.substring(i,i+1))*idCardWi[i];
		   }

		   int idCardMod=idCardWiSum%11;//计算出校验码所在数组的位置
		   String idCardLast=card.substring(17);//得到最后一位身份证号码

		   //如果等于2，则说明校验码是10，身份证号码最后一位应该是X
		   if(idCardMod==2){
		    if(idCardLast=="X"||idCardLast=="x"){
		    	returnStr= "success";
		    }else{
		    	returnStr= "身份证号码错误！";
		    }
		   }else{
		    //用计算出的验证码与最后一位身份证号码匹配，如果一致，说明通过，否则是无效的身份证号码
		    if(idCardLast.equals(idCardY[idCardMod]+"")){
		    	returnStr= "success";
		    }else{
		    	returnStr= "身份证号码错误！";
		    }
		   }
		  }else{
			  //15位
			  returnStr= "success";
		   }
		  }else{
			  returnStr= "身份证格式不正确!";
		  }
		return returnStr;
	}
}
