package ses.common;/**
 * Created by Easong on 2017/11/23.
 */

import java.util.Map;

/**
 * @author Easong
 * @create 2017-11-23 14:25
 * 短信发送信息封装抽象类
 **/
public abstract class AbstractMessageCommon {

    /**
     * Description: 准备数据 短信信息封装
     * example：Map<"状态", "多个手机号之间半角逗号分隔">
     *
     * @author Easong
     * @version 2017/11/23
     * @param 
     * @since JDK1.7
     */
    public abstract Map<Integer, StringBuffer> packageMessageInfo(Object obj, Map<Integer, StringBuffer> currStatusAndMobile);


    /**
     * Description: 发送信息
     *
     * @param [currSupStatusAndMobile]
     * @author Easong
     * @version 2017/11/23
     * @since JDK1.7
     */
    public abstract void beginSendMessage(Map<Integer, StringBuffer> currStatusAndMobile);
}
