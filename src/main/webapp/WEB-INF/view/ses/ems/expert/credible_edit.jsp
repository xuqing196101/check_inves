<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:include page="/WEB-INF/view/common.jsp"></jsp:include>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>专家诚信修改</title>
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
    			window.location.href="${pageContext.request.contextPath}/expert/toEditBasicInfo.html?id="+value[0];
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
			url:"${pageContext.request.contextPath}/credible/update.html",
			data:$("#form1").serialize(),
			type:"post",
			success:function(){
				parent.location.reload();
			},
			error:function(){
				layer.msg("参数错误，更新失败",{offset: ['102px', '390px']});
			}
		});
   	}
	  //控制只能输入数字
	   window.onload=function(){
	       var txt = document.getElementById("creater");
	       txt.onkeypress = function(evt){
	           var evt = window.event||evt;
	           var keynum = evt.keyCode||evt.which;
	           var num = String.fromCharCode(keynum);
	           if(keynum==8 || keynum==13)
	           {
	               return true;
	           }
	           if(this.value==="")
	           {
	               return /[-1-9]/.test(num);
	           }
	           else{
	               return /[0-9]/.test(num);
	           }
	       }
	       txt.onblur = function(evt){
	           var evt = window.event||evt;
	           var keynum = evt.keyCode||evt.which;
	           var num = String.fromCharCode(keynum);
	           if(keynum==8 || keynum==13)
	           {
	               return true;
	           }
	           if(this.value==="")
	           {
	               return /[-1-9]/.test(num);
	           }
	           else{
	        	   
	               return /[0-9]/.test(num);
	           }
	       }
	   }
</script>
</head>
<body>
 <div id="openWindow">
	<form action="${pageContext.request.contextPath}/credible/update.html" method="post" id="form1">
	<input type="hidden" name="id" value="${expertCredible.id }">
	<ul class="list-unstyled">
                <li class="mt10 col-md-12 p0">
                  <label class="col-md-12 pl20">诚信内容</label>
                  <span class="col-md-12">
                    <input type="text"  maxlength="255" value="${expertCredible.badBehavior }" name="badBehavior" id="name">
                  </span>
                </li>
                <li class="mt10 col-md-12 p0">
                  <label class="col-md-12 pl20">状态</label>
                  <span class="col-md-12">
                  <input type="radio" <c:if test="${expertCredible.isStatus == 1 }">checked</c:if>  name="isStatus" value="1" >启用&nbsp;<input type="radio" name="isStatus" <c:if test="${expertCredible.isStatus == 2 }">checked</c:if>  id="kind" value="2" >停用
                  </span>
                </li>
                <li class="mt10 col-md-12 p0">
                  <label class="col-md-12 pl20">分值</label>
                  <span class="col-md-12">
                   <input name="score"  style="ime-mode:disabled" onpaste="return false" value="${expertCredible.score}"   maxlength="5" id="creater" type="text">
                  </span>
                </li>
                <div class="clear"></div>
               </ul>
    <input type="button" onclick="submit1();"  value="修改"class="btn btn-windows add"/>
    <input type="button"  value="取消" onclick="cancel();"  class="btn btn-windows cancel"/>
  </form>
</div>
</body>
</html>
