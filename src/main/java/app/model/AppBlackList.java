package app.model;

/**
 * 
 * Description: 黑名单实体
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
public class AppBlackList {
    
    //名称
    private String name;
    
    //处罚类型
    private String punishType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPunishType() {
        return punishType;
    }

    public void setPunishType(String punishType) {
        this.punishType = punishType;
    }
}
