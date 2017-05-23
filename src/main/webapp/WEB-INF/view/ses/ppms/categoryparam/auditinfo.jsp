<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML PUBLIC>
<html>  
<head>  
<%@ include file="../../../common.jsp"%>
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
       var html1 ="";
       for ( var i = 0; i < names.length-1; i++) {
       html = html+"<input class='mt10' readonly='readonly' type='text' value='"+names[i]+"' name='productName'/><br/>";
	     }
		$("#addinput").append(html);
       for ( var i = 0; i < saleNames[i].length-1; i++) {
		html1 = html1+"<input class='mt10' readonly='readonly' type='text' value='"+saleNames[i]+"' name='saleName'/><br/>";
	     }
	    $("#addnews").append(html1);
   }); 
 
    
	
	$(function(){
	    var name  = "${name}";
	    var value = "${value}";
	    var names = name.split(",");
	    var values = value.split(",");
	     var html = "";
	     for ( var i = 0 ; i< names.length-1; i++){
				html = html +"<tr><td>参数名称：<input readonly='readonly' type='text' value='"+names[i]+"'/></td><td >参数类型："
				+"<select  name='valueType'>"
				+"<option value='' selected='selected' readonly='readonly'>"+values[i]+"</option>"
				+"<option value='字符型'>字符型</option>"
				+"<option value='数字型'>数字型</option>"
				+"<option value='日期'>日期</option><select/></td></tr>";
	     }
	      $("#result").prepend(html);
	});
	
	function sto(){
	    $("#storage").val(1);
	   $("#form").submit();
	}
	function publish(){
		var index;
		  index =  layer.open({
		    shift: 1, //0-6的动画形式，-1不开启
		    moveType: 1, //拖拽风格，0是默认，1是传统拖动
		    title: ['请选择公示范围','border-bottom:1px solid #e5e5e5'],
		    shade:0.01, //遮罩透明度
			type : 1,
			skin : 'layui-layer-rim', //加上边框
			area : [ '200px', '200px' ], //宽高
			content : $('#tr_range'),
			offset: ['150px', '500px']
		  });
	} 
	
    function sure(){
    	var range ="";
	    obj = document.getElementsByName("range")
	    for(var i=0; i<obj.length;i++){
	    	range+=$(obj[i]).val()+",";
	    }
	   $("#fff").val(range);
	   $("#storage").val(2);
	   $("#form").submit(); 
    }	
	function validite(){
	   $("#storage").val(3);
	   $("#form").submit();
	}
</script>
  </head>
  
  <body>
   <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0);"> 首页</a></li><li><a href="javascript:void(0);">产品参数管理</a></li><li><a href="javascript:void(0);">审核</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container">
	<div class="headline-v2 clear">
	   <h2>审核</h2>
	       </div>
	          <div class="tag-box ml100 col-md-9">

                     <form id="form" action="${pageContext.request.contextPath}/categoryparam/audit_param.html" method="post" >
                      <input type="hidden" name="id" value="${category.id}"/>
                     <input type="hidden" id="storage" name="storage" value=""/>
                     <input type="hidden" id="fff" name="ranges" value=""/>
                     <table id="result" class="table table-bordered table-condensedb mt15">
                     <tr><td >验证规范：</td><td>
					 <textarea name="acceptRange" readonly="readonly">${category.acceptRange }</textarea></td></tr>
                     <tr><td >是否公开：</td>
					 <td>
					 <span class="ml30"><input class="mt0 ml10" readonly="readonly" type="radio" value="0" name="ispublish" <c:if test="${category.isPublish eq 0}">checked</c:if>/>是</span>
					 <span class="ml60"><input class="mt0" readonly="readonly" type="radio" value="1" name="ispublish" <c:if test="${category.isPublish eq 1}">checked</c:if> />否</span>
					 </td ></tr>
					 <tr><td >生产型资质：</td>
					 <td><div id="addinput"></div>
					 </td></tr>
					 <tr><td >销售型资质：</td>
					 <td><div id="addnews"></div>
					 </td></tr>
               </table>
               <div id="tr_range" class="dnone ml30 mt20"><td>
					 <span><input type="checkbox" value="内网" name="range" class="mt0"/>内网</span>
					 <span class="ml30"><input type="checkbox" value="外网" name="range" class="mt0"/>外网</span>
					 </td>
					 <input type="button" value="确定"   class="mt50 btn" onclick="sure()"/>
					 <input type="button" value="取消"  class="mt50 ml30 btn" onclick="unsure()"/>
					 </div>
					 <div class="tc">
    				<input type="button" class="btn mr30" onclick="sto()"  value="暂存"/>
					<input type="button" class="btn mr30" onclick="publish()" value="公示"/>
					<input type="button" class="btn mr30" onclick="validite()"  value="生效"/>
					<input type="button"class="btn btn-windows back" value="返回" onclick="javascript:history.go(-1);"/>
					</div>
    </form>
    </div>
  </body>
</html>
