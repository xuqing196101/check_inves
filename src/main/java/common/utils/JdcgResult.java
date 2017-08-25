package common.utils;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
* @Title: JdcgResult.java 
* @Package common.utils 
* @Description: 自定义响应结构
* @author SongDong   
* @date 2017年2月24日 下午5:00:17 
* @version 2017年2月24日
 */
public class JdcgResult implements Serializable {

	private static final long serialVersionUID = 1L;
    private String count;
    private String sum;
	
	// 定义jackson对象
	private static final ObjectMapper MAPPER = new ObjectMapper();

	// 响应业务状态
	private Integer status;

	// 响应消息
	private String msg;

	// 响应中的数据
	private Object data;

	public static JdcgResult build(Integer status, String msg, Object data) {
		return new JdcgResult(status, msg, data);
	}

	public static JdcgResult ok(Object data) {
		return new JdcgResult(data);
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getSum() {
		return sum;
	}

	public void setSum(String sum) {
		this.sum = sum;
	}

	public static JdcgResult ok() {
		return new JdcgResult(null);
	}
	
	public JdcgResult() {

	}

	public static JdcgResult build(Integer status, String msg) {
		return new JdcgResult(status, msg, null);
	}

	public JdcgResult(Integer status, String msg, Object data) {
		this.status = status;
		this.msg = msg;
		this.data = data;
	}

	public JdcgResult(Object data) {
		this.status = 200;
		this.msg = "OK";
		this.data = data;
	}

	/*public JdcgResult(Integer status){
		this.status = status;
	}*/
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * 
	* @Title: formatToPojo 
	* @Description: 将json结果集转化为JdcgResult对象
	* @author SongDong   
	* @date 2017年2月24日 下午5:00:03 
	* @param @param jsonData
	* @param @param clazz
	* @param @return
	* @return JdcgResult
	* @throws 
	* @version 2017年2月24日
	 */
	public static JdcgResult formatToPojo(String jsonData, Class<?> clazz) {
		try {
			if (clazz == null) {
				return MAPPER.readValue(jsonData, JdcgResult.class);
			}
			JsonNode jsonNode = MAPPER.readTree(jsonData);
			JsonNode data = jsonNode.get("data");
			Object obj = null;
			if (clazz != null) {
				if (data.isObject()) {
					obj = MAPPER.readValue(data.traverse(), clazz);
				} else if (data.isTextual()) {
					obj = MAPPER.readValue(data.asText(), clazz);
				}
			}
			return build(jsonNode.get("status").intValue(), jsonNode.get("msg")
					.asText(), obj);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	* @Title: format 
	* @Description: 没有object对象的转化
	* @author SongDong   
	* @date 2017年2月24日 下午4:59:54 
	* @param @param json
	* @param @return
	* @return JdcgResult
	* @throws 
	* @version 2017年2月24日
	 */
	public static JdcgResult format(String json) {
		try {
			return MAPPER.readValue(json, JdcgResult.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	* @Title: formatToList 
	* @Description: Object是集合转化
	* @author SongDong   
	* @date 2017年2月24日 下午4:59:42 
	* @param @param jsonData
	* @param @param clazz
	* @param @return
	* @return JdcgResult
	* @throws 
	* @version 2017年2月24日
	 */
	public static JdcgResult formatToList(String jsonData, Class<?> clazz) {
		try {
			JsonNode jsonNode = MAPPER.readTree(jsonData);
			JsonNode data = jsonNode.get("data");
			Object obj = null;
			if (data.isArray() && data.size() > 0) {
				obj = MAPPER.readValue(data.traverse(), MAPPER.getTypeFactory()
						.constructCollectionType(List.class, clazz));
			}
			return build(jsonNode.get("status").intValue(), jsonNode.get("msg")
					.asText(), obj);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String toString() {
		return "JdcgResult [count=" + count + ", sum=" + sum + ", status=" + status + ", msg=" + msg + ", data=" + data
				+ "]";
	}

}
