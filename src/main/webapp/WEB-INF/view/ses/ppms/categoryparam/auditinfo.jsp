<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'auditinfo.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
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
       var name = "${caAptitude.productName}";
       var saleName = "${caAptitude.saleName}";
       var names = name.split(",");
       var saleNames=saleName.split(",");
       var html = "";
       var html1="";
       for ( var i = 0; i < names.length-1; i++) {
    
       html = html+"<input class='mt10' type='text' value='"+names[i]+"' name='productName'/><a class='ml10 btn' onclick='deleteattach(this)'>X</a><br/>";
	}
		$("#addinput").append(html);
       for ( var i = 0; i < saleNames[i].length-1; i++) {
		html1 = html1+"<input class='mt10' type='text' value='"+saleNames[i]+"' name='saleName'/><a class='ml10 btn' onclick='deleteattach(this)'>X</a><br/>";
	}
	$("#addnews").append(html1);
   }); 
 
    
	
	$(function(){
	    var name  = "${cateParam.name}";
	  
	    var value = "${cateParam.valueType}";
	    var names = name.split(",");
	  
	    var values = value.split(",");
	     var html = "";
	     for ( var i = 0 ; i< names.length-1; i++){
				html = html +"<tr><td>参数名称：<input readonly='readonly' type='text' value='"+names[i]+"'/></td><td>"
				+"<select readonly='readonly' name='valueType'>"
				+"<option value='' selected='selected'>"+values[i]+"</option>"
				+"<option value='字符型'>字符型</option>"
				+"<option value='数字型'>数字型</option>"
				+"<option value='日期'>日期</option><select/></td></tr>";
	     }
	  
	      $("#result").prepend(html);
	});
	$(function(){
       var name = "${caAptitude.productName}";
       var saleName = "${caAptitude.saleName}";
       var names = name.split(",");
       var saleNames=saleName.split(",");
       var html = "";
       var html1="";
       for ( var i = 0; i < names.length-1; i++) {
    
       html = html+"<input class='mt10' type='text' value='"+names[i]+"' name='productName'/><a class='ml10 btn' onclick='deleteattach(this)'>X</a><br/>";
	}
		$("#addinput").append(html);
       for ( var i = 0; i < saleNames[i].length-1; i++) {
		html1 = html1+"<input class='mt10' type='text' value='"+saleNames[i]+"' name='saleName'/><a class='ml10 btn' onclick='deleteattach(this)'>X</a><br/>";
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
	
	function sto(val){
	    $("#storage").val(1);
	   $("#form").submit();
	}
	function publish(obj){
	   $("#storage").val(2);
	   $("#form").submit();
	}
	function validite(obj){
	   $("#srorage").val(3);
	   $("#form").submit();
	}
</script>
  </head>
  
  <body>
   <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">产品参数管理</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container">
	<div class="headline-v2 clear">
	   <h2>审核</h2>
	  </div>
	          <div class="tag-box ml100 col-md-6">
                     <form id="form" action="<%=basePath%>categoryparam/audit_param.html" method="post" >
                      <input type="hidden" name="categoryId" value="${category.id}"/>
                     <input type="hidden" id="storage" name="paramstatus" value=""/>
                     <input type="hidden" id="sss" name="names" value="" />
                     <input type="hidden" id="bbb" name="values" value=" "/>
                     <input type="hidden" id="ddd" name="products" value=""/>
                     <input type="hidden" id="ccc" name="sales" value=""/>
                     <input type="hidden" id="eee" name="kinds" value=""/>
                     <table id="result" class="table table-bordered table-condensedb mt15">
                     <tr><td >是否公开</td>
					 <td>
					 <span class="ml30"><input readonly="readonly" type="radio" value="0" name="ispublish" <c:if test="${category.isPublish eq 0}">checked</c:if>/>是</span>
					 <span class="ml60"><input readonly="readonly" type="radio" value="1" name="ispublish" <c:if test="${category.isPublish eq 1}">checked</c:if> />否</span>
					 </td></tr>
					 <tr><td>公示范围</td>
					 <td>
					 <span class="ml30"><input type="checkbox" value="0" name="range"/>内网</span>
					 <span class="ml60"><input type="checkbox" value="1" name="range"/>外网</span>
					 </td>
					 </tr>
					 <tr><td>验证规范</td><td>
					 <textarea name="acceptRange">${category.acceptRange }</textarea></td></tr>
					 <tr><td>生产型资质</td>
					 <td><div id="addinput"></div>
					 </td></tr>
					 <tr><td>销售型资质</td>
					 <td><div id="addnews"></div>
					 </td></tr>
				     <!-- <tr><td colspan="2" class="" >
					</td>
					 </tr>  -->
					
    </table>
    				<input type="button" class="btn mr30" onclick="sto('${category.id}')"  value="暂存"/>
					<input type="button" class="btn mr30" onclick="publish('${category.id}')" value="公示"/>
					<input type="button" class="btn mr30" onclick="validite('${category.id}')"  value="生效"/>
    </form>
    </div>
  </body>
</html>
