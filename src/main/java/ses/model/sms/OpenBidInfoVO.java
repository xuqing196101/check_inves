package ses.model.sms;

import java.util.List;

/**
 * 
 * Description:封装参数
 * 
 * @author YangHongLiang
 * @version 2017-5-27
 * @since JDK1.7
 */
public class OpenBidInfoVO {
  //获取开标一览表
    private List<OpenBidInfo> openBidInfoList;

    public List<OpenBidInfo> getOpenBidInfoList() {
      return openBidInfoList;
    }

    public void setOpenBidInfoList(List<OpenBidInfo> openBidInfoList) {
      this.openBidInfoList = openBidInfoList;
    }
    
}
