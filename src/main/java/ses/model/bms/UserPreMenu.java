package ses.model.bms;

/**
 * Description: 用户-菜单关联实体
 *
 * @author Ye MaoLin
 * @version 2016-9-18
 * @since JDK1.7
 */
public class UserPreMenu {

	/** 用户 */
	private User user;
	
	/** 菜单 */
	private PreMenu preMenu;
	
	/**
	 * 用户个性化权限类型0：增加 1：减去
	 */
	private Integer kind;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public PreMenu getPreMenu() {
		return preMenu;
	}

	public void setPreMenu(PreMenu preMenu) {
		this.preMenu = preMenu;
	}

  public Integer getKind() {
    return kind;
  }

  public void setKind(Integer kind) {
    this.kind = kind;
  }
	
}
