package ses.model.oms.util;

import com.github.pagehelper.PageInfo;

public class CommUtils {
	public static String getTranslation(PageInfo page,String url){
		url = url + "?pageNum=";
		int pageCount=getpageCount(page.getPageSize(),Integer.parseInt(String.valueOf(page.getTotal()))); //总的页数
		StringBuffer sb = new StringBuffer();
			if(CommonConstant.INT_10 >= pageCount){
				for(int i=1;i<=pageCount;i++){
					if(i!=Integer.parseInt(String.valueOf(page.getTotal()))){
						if(i == page.getPageNum()){
							sb.append("<a href=\"javascript:void(0);\" onclick=\"pagesub('"+i+"','"+2+"')\" class=\"sel\">"+i+"</a>");
						}else{
							sb.append("<a href=\"javascript:void(0);\" onclick=\"pagesub('"+i+"','"+2+"')\" >"+i+"</a>");
						}
					}
			}
		}else{
			if(page.getPageNum() > 2){
				sb.append("<a href=\"javascript:void(0);\" onclick=\"pagesub('"+1+"','"+2+"')\">"+CommonConstant.INT_1+"</a>"+"<a>...</a>");
			}
			if(page.getPageNum() + 10 < pageCount){	
				for(int i=page.getPageNum();i < page.getPageNum() + CommonConstant.INT_10;i++){
					if(i == page.getPageNum()){
						sb.append("<a href=\"javascript:void(0);\" onclick=\"pagesub('"+i+"','"+2+"')\" class=\"sel\">"+i+"</a>");
					}else{
						sb.append("<a href=\"javascript:void(0);\" onclick=\"pagesub('"+i+"','"+2+"')\" >"+i+"</a>");
					}
			      }
				sb.append("<a>...</a>");
					
			}else{
				int startpagge = pageCount - 10;  
				for(int i=startpagge;i <= pageCount;i++){
					if(i <= Integer.parseInt(String.valueOf(page.getTotal()))-1){
						if(i == page.getPageNum()){
							sb.append("<a href=\"javascript:void(0);\" onclick=\"pagesub('"+i+"','"+2+"')\" class=\"sel\">"+i+"</a>");
						}else{
							sb.append("<a href=\"javascript:void(0);\" onclick=\"pagesub('"+i+"','"+2+"')\" >"+i+"</a>");
						}
					}
			   }
			}
		}
		String st = "<a class=\"pre\" href=\"javascript:void(0);\" onclick=\"pagesub('"+(page.getPageNum()-1)+"','"+2+"')\"> &nbsp;</a>"+sb.toString()+"<a class=\"next\" href=\"javascript:void(0);\" onclick=\"pagesub('"+(page.getPageNum()+1)+"','"+2+"')\"> &nbsp;</a>";
		if(page.getPageNum()==1&&page.getPageNum()!=pageCount){
			st="<a class=\"pre\" href=\"javascript:void(0);\" onclick=\"pagesub('"+(page.getPageNum()-1)+"','"+2+"')\" disablesd> &nbsp;</a>"+sb.toString()+"<a class=\"next\" href=\"javascript:void(0);\" onclick=\"pagesub('"+(page.getPageNum()+1)+"','"+2+"')\" > &nbsp;</a>";
		}
        if(page.getPageNum()!=1&&page.getPageNum()==pageCount){
        	st=  "<a class=\"pre\" href=\"javascript:void(0);\" onclick=\"pagesub('"+(page.getPageNum()-1)+"','"+2+"')\"> &nbsp;</a>"+sb.toString()+"<a class=\"next\" href=\"javascript:void(0);\" onclick=\"pagesub('"+(page.getPageNum()+1)+"','"+2+"')\" disabled> &nbsp;</a>";
		}
        if((page.getPageNum()==1&&page.getPageNum()==pageCount)||pageCount==0){
        	st="";
		}
        //name=\"pageNum\"
        //设置页数  2016-09-20
        st = st + "<span>共"+pageCount+"页"+page.getTotal()+"条，去第</span><input type=\"text\" name=\"pageNum\" id=\"pageNums\" value="+page.getPageNum()+" ><span>页</span><a href=\"javascript:void(0);\" onclick=\"pagesub('"+0+"','"+1+"')\"  class=\"subm\">确定</a>";
        //st = st + "<span>共"+pageCount+"页"+page.getTotal()+"条，去第</span><input type=\"text\" name=\"pageNum\" id=\"pageNums\" value=\"0\" ><span>页</span><a href=\"javascript:void(0);\" onclick=\"pagesub('"+0+"','"+1+"')\"  class=\"subm\">确定</a>";
    	String sc = "</br><script type=\"text/javascript\">"+
				"function pagesub(url,type){"+
				"var pageNum = $(\"#pageNums\").val();"+
				"if(pageNum == null && pageNum == \"\"){"+
				"pageNum = 1;"+
				"}"+
				"if(type == '2')"+
				"{$(\"#pageNums\").val(url);}"+
				"document.forms[0].submit();"+
			//	"location.href = url + pageNum; "+
				"}"+
				"</script>";	
        return st + sc;
	}
	public static int getpageCount(int pageSize,int rowCount){
		int pageCount=0;
		if(rowCount%pageSize==0){
			pageCount=rowCount/pageSize;
		}else{
			pageCount=rowCount/pageSize+1;
		}
		return pageCount;
	}
}
