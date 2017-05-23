<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML PUBLIC>
<html>
  <head>
<%@ include file="/WEB-INF/view/common.jsp"%>
    <title>产品参数修改</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
<script type="text/javascript">
	/**类型默认选中*/
   $(function(){
       var obj ="${category.kind}";
       var v2= obj.split(',');
       for ( var i = 0; i < v2.length; i++) {
       $("input[name='type']").each(function(){
       if($(this).val()==v2[i])
           $(this).attr("checked",true);
       });
	} 
   });
    $(function(){
       var name = "${productname}";
       var saleName = "${salename}";
       var names = name.split(",");
       var saleNames=saleName.split(",");
       var html = "";
       var html1="";
       for ( var i = 0; i < names.length-1; i++) {
    
       html = html+"<input class='mt10' type='text' value='"+names[i]+"' name='productName'/><br/>";
	}
		$("#addinput").append(html);
       for ( var i = 0; i < saleNames.length-1; i++) {
		html1 = html1+"<input class='mt10' type='text' value='"+saleNames[i]+"' name='saleName'/><br/>";
	}
	$("#addnews").append(html1);
   }); 
 
    /**新增/删除资质输入框*/
  function addAttach(){
		html="<input class='mt10' type='text'  name='productName'/><br/>";
		$("#addinput").append(html);
	}
	function addAtt(){
		html="<input class='mt10' type='text'  name='saleName'/><br/>";
		$("#addnews").append(html);
	}
	function deleteattach(obj){
		$(obj).prev().remove();
		$(obj).next().remove();
		$(obj).remove();
	} 
	/**更新参数内容*/
	function renew(){
	    var name="";
    	var value="";
    	var str="";
    	var sts="";
    	var type="";
    	/**根据name获取各项数据的值*/
        obj = document.getElementsByName("paramname");
        
        abj = document.getElementsByName("valueType");
      
		cbj=document.getElementsByName("productName");
		
		dbj=document.getElementsByName("saleName");
	
		ebj=document.getElementsByName("type");
		
        for ( var i = 0; i < obj.length; i++) {
			name+=$(obj[i]).val()+",";
		}
        for ( var j = 0; j < abj.length; j++) {
			value+=$(abj[j]).val()+",";
		}
		for ( var k= 0; k < cbj.length; k++) {
			str+=$(cbj[k]).val()+",";
		}
        for ( var n = 0; n < dbj.length; n++) {
			sts+=$(dbj[n]).val()+",";
		}
		for ( var m = 0; m < ebj.length; m++) {
			type+=$(ebj[m]).val()+",";
		}
		$("#sss").val(name);
		$("#bbb").val(value);
		$("#ddd").val(str);
		$("#ccc").val(sts);
		$("#eee").val(type);
		 $.ajax({
		    	cache:true,
		    	dataType:"json",
		    	type:"post",
		    	data:$("#form").serialize(),
		    	url :"${pageContext.request.contextPath}/categoryparam/edit.do",
		    	success:callback
		    })
	}
	 function callback(allListNews){
	      $("#td_input").html(allListNews.name);
	      $("#td_select").html(allListNews.value);
	      $("#span_input").html(allListNews.ispublish);
	      $("#span_td").html(allListNews.kind);
	      $("#td_textarea").html(allListNews.acceptrange);
	      $("#div_input").html(allListNews.product);
	      $("#add_input").html(allListNews.sale);
	      } 
	$(function(){
	    var name  = "${name}";
	    var value = "${value}";
	    var names = name.split(",");
	    var values = value.split(",");
	     var html = "";
	     for ( var i = 0 ; i< names.length-1; i++){
				html = html +"<tr><td class='info'>参数名称：<input type='text' name='paramname' value='"+names[i]+"'/><div id='td_input' ></div</td><td>参数类型："
				+"<select  name='valueType'>"
				+"<option value='' selected='selected'>"+values[i]+"</option>"
				+"<option>--请选择--</option>"
				+"<option value='字符型'>字符型</option>"
				+"<option value='数字型'>数字型</option>"
				+"<option value='日期型'>日期型</option><select/><div id='td_select'></div></td></tr>";
	     }
	  
	      $("#result").prepend(html);
	});
</script>
  </head>
  
  <body>
  
	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0);"> 首页</a></li><li><a href="javascript:void(0);">产品参数管理</a></li><li><a href="javascript:void(0);">修改</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container">
   <!-- <div class="col-md-3">
	</div> -->
	<div class="headline-v2 clear">
	   <h2>修改</h2>
	  </div>
	<div class="  tag-box ml100 col-md-9">
                     <form id="form" action="${pageContext.request.contextPath}/categoryparam/edit.do" method="post" >
                      <input type="hidden" name="id" value="${category.id}"/>
                     <input type="hidden" id="sss" name="names" value="" />
                     <input type="hidden" id="bbb" name="values" value=" "/>
                     <input type="hidden" id="ddd" name="products" value=""/>
                     <input type="hidden" id="ccc" name="sales" value=""/>
                     <input type="hidden" id="eee" name="kinds" value=""/>
                     <table id="result" class="table table-bordered table-condensedb mt15">
                  
                    <!--  <tr><td>参数名称：<input value="" name="name"/></td>
                         <td>参数类型：
                         <select name="valueType">
                         	<option value="" selected="selected"></option>
                         	<option value="字符型">字符型</option>
                         	<option value="数字型">数字型</option>
                            <option value="日期">日期</option>
                         </select>
                         </td>
                     </tr> -->
                    
                     <tr><td class="info" >是否公开</td>
					 <td>
					 <span class="ml30"><input type="radio" value="0" name="ispublish" class="mt0" <c:if test="${category.isPublish eq 0}">checked</c:if>/>是</span>
					 <span class="ml60"><input type="radio" value="1" name="ispublish" class="mt0" <c:if test="${category.isPublish eq 1}">checked</c:if> />否</span>
                      <div id="span_input"></div>
					 </td></tr>
				
					 <tr><td class="info">产品类型</td>
					 <td>
					 <span class="ml30"><input type="checkbox" value="E73923CC68A44E2981D5EA6077580372" name="type" id="box" class="mt0"/>生产型</span>
					 <span class="ml30"><input type="checkbox" value="18A966C6FF17462AA0C015549F9EAD79" name="type" id="box" class="mt0"/>销售型</span>
					<div class="span_td"></div>
					 </td></tr>
					 
					 <tr><td class="info">验证规范</td><td>
					 <textarea name="acceptRange" class="col-md-8 h100">${category.acceptRange }</textarea><div id="td_textarea"></div></td></tr>
					 
					<tr><td class="info">生产型资质</td>
					 <td><div id="addinput"></div></div><div id="div_input"></div>
					 </td></tr>
					
					 <tr><td class="info">销售型资质</td>
					 <td><div id="addnews"></div></div><div id="add_input"></div>
					 </td></tr>
					 </table>
				     <tr><td colspan="2" class="" >
					 <button type="button" class="btn mr30" onclick="renew()" name="" >更新</button>
					 <input type="button"class="btn" value="返回" onclick="location.href='javascript:history.go(-1);'"/></td>
					 </tr> 
    
    </form>
    </div>
    </div>
  </body>
</html>
