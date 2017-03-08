package bss.service.ob;

import java.util.List;
import bss.model.ob.OBProject;

/***
 * 竞价信息管理 接口
 * @author YangHangLiang
 *
 */
public interface OBProjectServer {
    /***
     * 分页显示竞价信息
     */
	List<OBProject> list(OBProject op);
}
