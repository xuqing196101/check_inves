package bss.util;

public class NumberUtils {
	protected NumberUtils() {

    }

    protected static final String[] UNITS = { "", "十", "百", "千",
                                                "万", "十", "百", "千",
                                                "亿", "十", "百", "千",
                                             };

    protected static final String[] NUMS = {  "零","一", "二", "三", "四", "五",
                                                  "六", "七", "八", "九",
                                            };

    /**
     *  数字转换成中文汉字
     *  @param value  转换的数字
     *  @return 返回数字转后的汉字字符串
     */
    public static String translate(int value) {
        //转译结果
        String result = "";

        for (int i = String.valueOf(value).length() - 1; i >= 0; i--) {
            int r = (int) (value / Math.pow(10, i));
            result += NUMS[r % 10] + UNITS[i];
        }

        result = result.replaceAll("零[十, 百, 千]", "零");
        result = result.replaceAll("零+", "零");

        result = result.replaceAll("零([万, 亿])", "$1");

        result = result.replaceAll("亿万", "亿");   //亿万位拼接时发生的特殊情况

        if (result.startsWith("一十"))
            result = result.substring(1);

        if(result.endsWith("零"))
            result = result.substring(0, result.length() - 1);

        return result;
    }
}
