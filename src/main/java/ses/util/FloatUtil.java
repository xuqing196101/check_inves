package ses.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import bss.model.ppms.SupplyMark;

public class FloatUtil {
	 //默认除法运算精度  
    private static final int DEF_DIV_SCALE = 4;  
    //这个类不能实例化  
    private FloatUtil(){  
    	
    }  
    /** 
     * 提供精确的加法运算。 
     * @param v1 被加数 
     * @param v2 加数 
     * @return 两个参数的和 
     */  
    public static double add(double v1,double v2){  
        BigDecimal b1 = new BigDecimal(Double.toString(v1));  
        BigDecimal b2 = new BigDecimal(Double.toString(v2));  
        return b1.add(b2).doubleValue();  
    }  
    /** 
     * 提供精确的减法运算。 
     * @param v1 被减数 
     * @param v2 减数 
     * @return 两个参数的差 
     */  
    public static double sub(double v1,double v2){  
        BigDecimal b1 = new BigDecimal(Double.toString(v1));  
        BigDecimal b2 = new BigDecimal(Double.toString(v2));  
        return b1.subtract(b2).doubleValue();  
    }  
    /** 
     * 提供精确的乘法运算。 
     * @param v1 被乘数 
     * @param v2 乘数 
     * @return 两个参数的积 
     */  
    public static double mul(double v1,double v2){  
        BigDecimal b1 = new BigDecimal(Double.toString(v1));  
        BigDecimal b2 = new BigDecimal(Double.toString(v2));  
        return b1.multiply(b2).doubleValue();  
    }  
  
    /** 
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 
     * 小数点以后10位，以后的数字四舍五入。 
     * @param v1 被除数 
     * @param v2 除数 
     * @return 两个参数的商 
     */  
    public static double div(double v1,double v2){  
        return div(v1,v2,DEF_DIV_SCALE);  
    }  
  
    /** 
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 
     * 定精度，以后的数字四舍五入。 
     * @param v1 被除数 
     * @param v2 除数 
     * @param scale 表示表示需要精确到小数点以后几位。 
     * @return 两个参数的商 
     */  
    public static double div(double v1,double v2,int scale){  
        if(scale<0){  
            throw new IllegalArgumentException(  
                "The scale must be a positive integer or zero");  
        }  
        BigDecimal b1 = new BigDecimal(Double.toString(v1));  
        BigDecimal b2 = new BigDecimal(Double.toString(v2));  
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();  
    }  
  
    /** 
     * 提供精确的小数位四舍五入处理。 
     * @param v 需要四舍五入的数字 
     * @param scale 小数点后保留几位 
     * @return 四舍五入后的结果 
     */  
    public static double round(double v,int scale){  
  
        if(scale<0){  
            throw new IllegalArgumentException(  
                "The scale must be a positive integer or zero");  
        }  
        BigDecimal b = new BigDecimal(Double.toString(v));  
        BigDecimal one = new BigDecimal("1");  
        return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();  
    }  
    
    public static boolean equal(double v1,double v2){  
        BigDecimal b1 = new BigDecimal(Double.toString(v1)).setScale(DEF_DIV_SCALE);  
        BigDecimal b2 = new BigDecimal(Double.toString(v2)).setScale(DEF_DIV_SCALE);  
        return b1.equals(b2);  
    }  
    public static List<SupplyMark> setSort(List<SupplyMark> list){
    	double maxscore = 15;
    	double score =0;
    	int unit =1;
    	double unitsore = 2;
    	for(int i=0;i<list.size();i++){
    		if(i==0){
    			list.get(i).setScore(maxscore);
    		}else {
				if(new Double(list.get(i).getPrarm()).compareTo(new Double(list.get(i-1).getPrarm())) ==0){
					list.get(i).setScore(list.get(i-1).getScore());
				}else {
					score = sub(list.get(i-1).getScore(), mul(unitsore, unit));
					list.get(i).setScore(score);
					//unit++;
				}
			}
    	}
    	return list;
    }
    
    @SuppressWarnings("unchecked")
	public static void main(String[] args) {
    	
    }
    
}
/*@SuppressWarnings("rawtypes")
class SortByParam implements Comparator {
	public int compare(Object o1, Object o2) {
		SupplyMark s1 = (SupplyMark) o1;
		SupplyMark s2 = (SupplyMark) o2;
		//升序排列
		if (new Double(s1.getPrarm()).compareTo(new Double(s2.getPrarm())) >0){
			return 1;
		}else if (new Double(s1.getPrarm()).compareTo(new Double(s2.getPrarm())) <0) {
			return -1;
		}
		//降序排列
		if (new Double(s1.getPrarm()).compareTo(new Double(s2.getPrarm())) >0){
			return -1;
		}else if (new Double(s1.getPrarm()).compareTo(new Double(s2.getPrarm())) <0) {
			return 1;
		}
		return 0;
	}
}*/