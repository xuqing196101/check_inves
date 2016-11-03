<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>专家诚信列表</title>
<!-- Meta -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<script src="<%=basePath%>public/layer/layer.js"></script>
<script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>
<script type="text/javascript">
   
	function cancel(){
		 var index=parent.layer.getFrameIndex(window.name);
		    parent.layer.close(index);
	}
   	//修改
   	function edit(){
  	  var count = 0;
  	  var ids = document.getElementsByName("check");
   
       for(i=0;i<ids.length;i++) {
     		 if(document.getElementsByName("check")[i].checked){
     		 var id = document.getElementsByName("check")[i].value;
     		var value = id.split(",");
     		 count++;
      }
    }   
    		if(count>1){
    			layer.alert("只能选择一条记录",{offset: ['222px', '390px'],shade:0.01});
    		}else if(count<1){
    			layer.alert("请选择一条记录",{offset: ['222px', '390px'],shade:0.01});
    		}else if(count==1){
    			window.location.href="<%=basePath%>expert/toEditBasicInfo.html?id="+value[0];
       	}
    }
	function submit1(){
   		var name = $("#name").val();
		if(!name){
			layer.tips("请填写名称", "#name");
			return ;
		}
		var id=[]; 
		$('input[name="isStatus"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==0){
			layer.tips("请选择状态", "#kind");
			return ;
		}
		
		var creater = $("#creater").val();
		if(!creater){
			layer.tips("请填写分值", "#creater");
			return ;
		}
		
		var index=parent.layer.getFrameIndex(window.name);
		$.ajax({
			url:"<%=basePath %>credible/update.html",
			data:$("#form1").serialize(),
			type:"post",
			success:function(){
				parent.location.reload();
			},
			error:function(){
				layer.msg("更新失败",{offset: ['222px', '390px']});
			}
		});
   	}
	/* $(document).ready(function() { 
		 $("#creater").bind("keypress", function(event) {  
	    var event= event || window.event;  
	    var getValue = $(this).val();  
	    //控制第一个不能输入小数点"."  
	    if (getValue.length == 0 && event.which == 46) {  
	        event.preventDefault();  
	        return;  
	    }  
	    //控制只能输入一个小数点"."  
	    if (getValue.indexOf('.') != -1 && event.which == 46) {  
	        event.preventDefault();  
	        return;  
	    }  
	    //控制只能输入的值  
	    if (event.which && (event.which < 48 || event.which > 57) && event.which != 8 && event.which != 46) {  
	        event.preventDefault();  
	         return;  
	        }  
	    });  
	    //失去焦点是触发  
	    $("#creater").bind("blur", function(event) {  
	    var value = $(this).val(), reg = /\.$/;  
	    if (reg.test(value)) {  
	    value = value.replace(reg, "");  
	    $(this).val(value);  
	    }  
	    });  
	});  */
	function textre(t) {
	    t.value = t.value.replace(/[^(\-)0-9]/g,'').replace(/(^|\D)\.+/g,"$1").replace(/^(\-?\d*\.?\d*).*$/,"$1").replace(/^(\-?(\d\.?){1,4}).*$/,"$1");
	}
</script>
</head>
<body>
 <div id="openWindow">
	<form action="<%=basePath %>credible/update.html" method="post" id="form1">
	<input type="hidden" name="id" value="${expertCredible.id }">
     <table class="table table-bordered table-condensed">
     <thead>
      <tr>
        <th>诚信内容:</th><td><input type="text"  maxlength="255" value="${expertCredible.badBehavior }" name="badBehavior" id="name"></td>
        <th>状态:</th><td><input type="radio" <c:if test="${expertCredible.isStatus == 1 }">checked</c:if>  name="isStatus" value="1" >启用&nbsp;<input type="radio" name="isStatus" <c:if test="${expertCredible.isStatus == 2 }">checked</c:if>  id="kind" value="2" >停用</td>
        <th>分值:</th><td><input name="score" value="${expertCredible.score}" onkeyup="textre(this);" onblur="textre(this);" onkeypress="textre(this);"  maxlength="5" id="creater" type="text"></td>
      </tr>
     </thead>
    </table>
    <input type="button" onclick="submit1();"  value="修改"class="btn btn-windows add"/>
    <input type="button"  value="取消" onclick="cancel();"  class="btn btn-windows cancel"/>
  </form>
</div>
</body>
</html>
