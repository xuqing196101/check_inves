package app.model;

/**
 *
 * Description： app首页
 *
 * @author  zhaobowen
 * @version
 * @since JDK1.7
 * @date 2017年6月1日 下午2:10:46 
 *
 */
public class AppImg {
    //请求状态码 
    private boolean status;

    //返回错误信息
    private String msg;

    //返回集合
    private AppData data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public AppData getData() {
        return data;
    }

    public void setData(AppData data) {
        this.data = data;
    }


}
