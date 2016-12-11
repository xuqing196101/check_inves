package ses.util;

/**
 * 版权：(C) 版权所有 2011-2016
 * <简述>
 * 用来判断IP地址是内网还是外网
 * <详细描述>
 * @author   WangHuijie
 * @version  1.0.0
 * @since   2016-12-8 20:34:22
 * @see
 */
public class IpAddressUtil {

    /**
     *〈简述〉
     * static方法判断IP是外网还是内网
     *〈详细描述〉
     * @author WangHuijie
     * @param ipAddress String类型的IP地址
     * @return  返回0代表是内网,1代表是外网
     */
    public static int validateIpAddress (String ipAddress) {
        /** 0代表内网用户,1代表外网用户*/
        int type = 0;
        /**
         * 10.0.0.0 - 10.255.255.255 （A类内网地址）
         * 172.16.0.0 - 172.31.255.255 （B类内网地址）
         * 192.168.0.0 - 192.168.255.255 （C类内网地址）
         */
        String[] ips = ipAddress.split("\\.");
        // 如果不是这三种则为外网
        if (!"10".equals(ips[0]) && !"172".equals(ips[0]) && !"192".equals(ips[0])) {
            type = 1;
        } else {
            // 如果是172开头,则第二位必须是16-31之间
            if ("172".equals(ips[0])) {
                if (Integer.parseInt(ips[1]) < 16 || Integer.parseInt(ips[1]) > 31) {
                    type = 1;
                }
            }
            // 如果是172开头,则第二位必须是168之间
            if ("192".equals(ips[0]) && !"168".equals(ips[1])) {
                type = 1;
            }
        }
        // 判断是不是本地IP
        if ("127.0.0.1".equals(ipAddress)) {
            type = 0;
        }
        return type;
    }
}
