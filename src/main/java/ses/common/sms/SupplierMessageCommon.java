package ses.common.sms;/**
 * Created by Easong on 2017/11/23.
 */

import common.utils.SMSUtil;
import org.apache.commons.lang3.StringUtils;
import ses.common.AbstractMessageCommon;
import ses.constants.SupplierConstants;
import ses.model.sms.Supplier;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 供应商短信接口
 *
 * @author Easong
 * @create 2017-11-23 13:44
 **/
public class SupplierMessageCommon extends AbstractMessageCommon implements Serializable{

    private static final long serialVersionUID = -3041229505805491877L;

    /**
     * Description: 准备数据 短信信息封装
     * example：Map<"状态", "多个手机号之间半角逗号分隔">
     *
     * @param [supplier]
     * @author Easong
     * @version 2017/11/23
     * @since JDK1.7
     */
    @Override
    public Map<Integer, StringBuffer> packageMessageInfo(Object obj, Map<Integer, StringBuffer> currSupStatusAndMobile) {
        if(currSupStatusAndMobile == null){
            currSupStatusAndMobile = new HashMap<>();
        }
        // 开始封装短信发送Map 格式：状态+电话号码集
        if (obj instanceof Supplier) {
            Supplier supplier = (Supplier) obj;
            if (StringUtils.isNotEmpty(supplier.getMobile())) {
                if (currSupStatusAndMobile.get(supplier.getStatus()) != null) {
                    // 在基础上插入
                    currSupStatusAndMobile.put(supplier.getStatus(), currSupStatusAndMobile.get(supplier.getStatus()).append(supplier.getMobile()).append(","));
                } else {
                    // 第一次插入
                    currSupStatusAndMobile.put(supplier.getStatus(), new StringBuffer(supplier.getMobile()).append(","));
                }
            }
        }
        return currSupStatusAndMobile;
    }

    /**
     * Description: 发送信息
     *
     * @param [currSupStatusAndMobile]
     * @author Easong
     * @version 2017/11/23
     * @since JDK1.7
     */
    @Override
    public void beginSendMessage(Map<Integer, StringBuffer> currStatusAndMobile) {
        if (currStatusAndMobile != null) {
            // 短信通知
            for (Map.Entry<Integer, StringBuffer> entry : currStatusAndMobile.entrySet()) {
                //SMSUtil.sendMsg(entry.getValue().toString(), "【军队采购网】审核通知：您好，您的信息" + SupplierConstants.STATUSMAP_SMS.get(entry.getKey()) + "。");
            }
        }
    }
}
