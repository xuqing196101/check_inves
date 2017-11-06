package ses.model.ems;

import java.io.Serializable;

/**
 *
 * Description: 专家查询VO
 *
 * @author Easong
 * @version 2017/11/6
 * @param 
 * @since JDK1.7
 */
public class ExpertVO extends Expert implements Serializable {

    private static final long serialVersionUID = -769693824093997623L;

    private String batchDetailsNumber;

    public String getBatchDetailsNumber() {
        return batchDetailsNumber;
    }

    public void setBatchDetailsNumber(String batchDetailsNumber) {
        this.batchDetailsNumber = batchDetailsNumber;
    }
}