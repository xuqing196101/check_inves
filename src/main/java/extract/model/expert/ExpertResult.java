package extract.model.expert;

/**
 * 
 * Description: 数据回传专家信息封装
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
public class ExpertResult {

    private String expertId;

    // 售领采购文件开始时间
    private String feedBackTime;

    // 1 确认参加 2 拒绝参加 0 待定(默认值)
    private short join;

    public String getExpertId() {
        return expertId;
    }

    public void setExpertId(String expertId) {
        this.expertId = expertId;
    }

    public short getJoin() {
        return join;
    }

    public void setJoin(short join) {
        this.join = join;
    }

    public String getFeedBackTime() {
        return feedBackTime;
    }

    public void setFeedBackTime(String feedBackTime) {
        this.feedBackTime = feedBackTime;
    }
}
