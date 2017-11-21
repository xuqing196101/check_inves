package bss.util.comet;

import java.util.List;
import java.util.Map;

/**
 * 消息实体
 * @author 李万林
 *
 */
public class Comet {
  private String userId;
  private String msgCount;
  private String msgData;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getMsgCount() {
    return msgCount;
  }

  public void setMsgCount(String msgCount) {
    this.msgCount = msgCount;
  }

  public String getMsgData() {
    return msgData;
  }

  public void setMsgData(String msgData) {
    this.msgData = msgData;
  }

}
