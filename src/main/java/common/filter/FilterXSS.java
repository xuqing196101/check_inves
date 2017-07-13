package common.filter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
public class FilterXSS implements Filter {
    // XSS处理Map
    private static Map<String, String> xssMap = new LinkedHashMap<String, String>();
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain fc)
            throws IOException, ServletException {
        // 强制类型转换 HttpServletRequest
        HttpServletRequest httpReq = (HttpServletRequest)req;
        //获取请求方法 
        String headValue = httpReq.getServletPath();
        //忽略上传方法
        if(headValue !=null & !headValue.contains("file") & !headValue.contains("article") & !headValue.contains("open_bidding") & !headValue.contains("purchaser") & !headValue.contains("templet")& !headValue.contains("collect")& !headValue.contains("noticeDocument")){
        // 构造HttpRequestWrapper对象处理XSS
        HttpRequestWrapper httpReqWarp = new HttpRequestWrapper(httpReq,xssMap);
        fc.doFilter(httpReqWarp, res);
        }else{
        HttpRequestWrapper httpReqWarp = new HttpRequestWrapper(httpReq);
        fc.doFilter(httpReqWarp, res);
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub
        // 含有脚本： script
        //xssMap.put("[s|S][c|C][r|R][i|C][p|P][t|T]", "");
        // 含有脚本 javascript
        //xssMap.put("[\\\"\\\'][\\s]*[j|J][a|A][v|V][a|A][s|S][c|C][r|R][i|I][p|P][t|T]:(.*)[\\\"\\\']", "\"\"");
        // 含有函数： eval
       // xssMap.put("[e|E][v|V][a|A][l|L]\\((.*)\\)", "");
        // 含有符号 <
        xssMap.put("<", "＜");
        // 含有符号 >
        xssMap.put(">", "＞");
        // 含有符号 (
        xssMap.put("\\(", "（");
        // 含有符号 )
        xssMap.put("\\)", "）");
        // 含有符号 '
        xssMap.put("'", "＇");
        // 含有符号 "
        xssMap.put("\"", "＂");
        xssMap.put("--", "－－");
        xssMap.put("[s|S][c|C][r|R][i|C][p|P][t|T]", "");
        /*xssMap.put(";", "；");
        /* xssMap.put("&","＆");*/
        /*xssMap.put("%","％");*/
    }
    /**
     * 
     * Description:内部类 实现替换转换特殊符号
     * 
     * @author YangHongLiang
     * @version 2017-6-1
     * @since JDK1.7
     */
    private final class HttpRequestWrapper extends HttpServletRequestWrapper {
        private Map<String, String> xssMap;
        public HttpRequestWrapper(HttpServletRequest request) {
            super(request);
        }
        public HttpRequestWrapper(HttpServletRequest request,Map<String, String> xssMap) {
            super(request);
            this.xssMap = xssMap;
        }
        @Override
        public String[] getParameterValues(String parameter) {
            String[] values = super.getParameterValues(parameter);
            if (values == null) {
                return null;
            }
            int count = values.length;
            // 遍历每一个参数，检查是否含有
            String[] encodedValues = new String[count];
            for (int i = 0; i < count; i++) {
                encodedValues[i] = cleanXSS(values[i]);
            }
            return encodedValues;
        }
        @Override
        public String getParameter(String parameter) {
            String value = super.getParameter(parameter);
            if (value == null) {
                return null;
            }
            return cleanXSS(value);
        }
        public String getHeader(String name) {
            String value = super.getHeader(name);
            if (value == null)
                return null;
            return cleanXSS(value);
        }
		private String cleanXSS(String value) {
			if(xssMap !=null){
				Set<String> keySet = xssMap.keySet();
				for(String key : keySet){
			        String v = xssMap.get(key);
	                value = value.replaceAll(key,v);
	            }
			}
            return value;
        }
    }
}
