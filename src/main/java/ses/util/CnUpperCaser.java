package ses.util;
/**
 * 版权：(C) 版权所有 
 * <简述>金额转换为大写工具类
 * <详细描述>
 * @author   Song Biaowei
 * @version  
 * @since
 * @see
 */
public class CnUpperCaser {
    /**
     * 单位
     */
    private static final String UNIT = "万千佰拾亿千佰拾万千佰拾元角分";
    /**
     * 大写数字
     */
    private static final String DIGIT = "零壹贰叁肆伍陆柒捌玖";
    /**
     * 无限大
     */
    private static final double MAX_VALUE = 9999999999999.99D;
    /**
     * 定义常量100
     */
    private static final int NUMBER_HUNDRED = 100;
    /**
     *〈简述〉主方法
     *〈详细描述〉
     * @author Song Biaowei
     * @param v 参数
     * @return String
     */
    public static String getCnString(double v) {
        if (v < 0 || v > MAX_VALUE){
            return "参数非法!";
        }
        long l = Math.round(v * NUMBER_HUNDRED);
        if (l == 0){
            return "零元整";
        }
        String strValue = l + "";
        // i用来控制数
        int i = 0;
        // j用来控制单位
        int j = UNIT.length() - strValue.length();
        String rs = "";
        boolean isZero = false;
        for (; i < strValue.length(); i++, j++) {
            char ch = strValue.charAt(i);
            if (ch == '0') {
                isZero = true;
                if (UNIT.charAt(j) == '亿' || UNIT.charAt(j) == '万' || UNIT.charAt(j) == '元') {
                    rs = rs + UNIT.charAt(j);
                    isZero = false;
                }
            } else {
                if (isZero) {
                    rs = rs + "零";
                    isZero = false;
                }
                rs = rs + DIGIT.charAt(ch - '0') + UNIT.charAt(j);
            }
        }
        if (!rs.endsWith("分")) {
            rs = rs + "整";
        }
        rs = rs.replaceAll("亿万", "亿");
        return rs;
    }
    /**
     *〈简述〉main方法测试
     *〈详细描述〉
     * @author Song Biaowei
     * @param args 参数
     */
    public static void main(String[] args) {
        System.out.println(CnUpperCaser.getCnString(1));
    }
}