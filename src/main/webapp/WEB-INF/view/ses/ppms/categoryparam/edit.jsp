<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'edit.jsp' starting page</title>
    
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
 
    /**新增/删除资质输入框*/
  function addAttach(){
		html="<input class='mt10' type='text'  name='productName'/><a class='ml10 btn ' onclick='deleteattach(this)'>X</a><br/>";
		$("#addinput").append(html);
	}
	function addAtt(){
		html="<input class='mt10' type='text'  name='saleName'/><a class='ml10 btn' onclick='deleteattach(this)'>X</a><br/>";
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
        obj = document.getElementsByName("name");
        
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
		$("#form").submit();
	} 
</script>
  </head>
  
  <body>
  <div class="wrapper">
	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a><li><a href="#">产品参数管理</a><li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container">
   <!-- <div class="col-md-3">
	</div> -->
	<div class=" mt10 col-md-9">
                     <form id="form" action="<%=basePath%>categoryparam/edit.do" method="post" >
                      <input type="hidden" name="categoryId" value="${category.id}"/>
                     <input type="hidden" id="sss" name="names" value="" />
                     <input type="hidden" id="bbb" name="values" value=" "/>
                     <input type="hidden" id="ddd" name="products" value=""/>
                     <input type="hidden" id="ccc" name="sales" value=""/>
                     <input type="hidden" id="eee" name="kinds" value=""/>
                     <table id="result" class="table table-bordered table-condensedb mt15">
                    <c:forEach var="cate" items="${caList}">
                     <tr><td>参数名称：<input value="${cate.name }" name="name"/></td>
                         <td>参数类型：
                         <select name="valueType">
                         	<option value="${cate.valueType }" selected="selected">${cate.valueType }</option>
                         	<option value="字符型">字符型</option>
                         	<option value="数字型">数字型</option>
                            <option value="日期">日期</option>
                         </select>
                         </td>
                     </tr>
                     </c:forEach> 
                     <tr><td >是否公开</td>
					 <td>
					 <span class="ml30"><input type="radio" value="0" name="ispublish" <c:if test="${category.isPublish eq 0}">checked</c:if>/>是</span>
					 <span class="ml60"><input type="radio" value="1" name="ispublish" <c:if test="${category.isPublish eq 1}">checked</c:if> />否</span>
					 </td></tr>
				
					 <tr><td >产品类型</td>
					 <td>
					 <span class="ml30"><input type="checkbox" value="E73923CC68A44E2981D5EA6077580372" name="type" id="box"/>生产型</span>
					 <span class="ml30"><input type="checkbox" value="18A966C6FF17462AA0C015549F9EAD79" name="type" id="box"/>销售型</span>
					 </td></tr>
					 
					 <tr><td>验证规范</td><td>
					 <textarea name="acceptRange">${category.acceptRange }</textarea></td></tr>
					 
					<tr><td>生产型资质</td>
					 <td><div id="addinput"><input  type="text" value="" name="productName" class="mt10"/>
					 <input  type="button" value="添加" onclick="addAttach()" class=""/><br/></div>
					 </td></tr>
					

					 <tr><td>销售型资质</td>
					 <td><div id="addnews"><input  type="text" name="saleName" value="" class="mt10"/>
					 <input  type="button" value="添加" onclick="addAtt()" class=""/><br/></div>
					 </td></tr>
					
				     <tr><td colspan="2"  >
					<button type="button" class="btn mr30" onclick="renew()" name="" >提交</button>
					 <input type="button"class="btn" value="返回" onclick="location.href='javascript:history.go(-1);'"/></td>
					 </tr> 
    </table>
    </form>
    </div>
  </body>
</html>
