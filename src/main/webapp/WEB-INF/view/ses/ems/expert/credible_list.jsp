<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<script src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}/public/laypage-v1.3/laypage/laypage.js"></script>
<script type="text/javascript">
   $(function(){
		  laypage({
			    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
			    pages: "${result.pages}", //总页数
			    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			    skip: true, //是否开启跳页
			    total: "${result.total}",
			    startRow: "${result.startRow}",
			    endRow: "${result.endRow}",
			    groups: "${result.pages}">=5?5:"${result.pages}", //连续显示分页数
			    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
// 			        var page = location.search.match(/page=(\d+)/);
// 			        return page ? page[1] : 1;
					return "${result.pageNum}";
			    }(), 
			    jump: function(e, first){ //触发分页后的回调
			        if(!first){ //一定要加此判断，否则初始时会无限刷新
			        	$("#page").val(e.curr);
			        	$("#form1").submit();
			        }
			    }
			});
		  
	  });
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
   }
   /** 全选全不选 */
	function selectAll(){
		 var checklist = document.getElementsByName ("check");
		 var checkAll = document.getElementById("allId");
		   if(checkAll.checked){
			   for(var i=0;i<checklist.length;i++)
			   {
			      checklist[i].checked = true;
			   } 
			 }else{
			  for(var j=0;j<checklist.length;j++)
			  {
			     checklist[j].checked = false;
			  }
		 	}
		}
 
   	//修改
   	function edit(){
  	  var count = 0;
  	  var ids = document.getElementsByName("check");
   
       for(i=0;i<ids.length;i++) {
     		 if(document.getElementsByName("check")[i].checked){
     		 var id = document.getElementsByName("check")[i].value;
     		 count++;
      }
    }   
    		if(count>1){
    			layer.alert("只能选择一条记录",{offset: ['222px', '390px'],shade:0.01});
    		}else if(count<1){
    			layer.alert("请选择一条记录",{offset: ['222px', '390px'],shade:0.01});
    		}else if(count==1){
    			index = layer.open({
    		          type: 2, //page层
    		          area: ['700px', '300px'],
    		          title: '修改',
    		          shade:0.01, //遮罩透明度
    		          moveType: 1, //拖拽风格，0是默认，1是传统拖动
    		          shift: 1, //0-6的动画形式，-1不开启
    		          offset: ['120px', '250px'],
    		          shadeClose: true,
    		          content:"${pageContext.request.contextPath}/credible/toUpdate.html?id="+id
    		        		  //数组第二项即吸附元素选择器或者DOM $('#openWindow')
    			 });
       	}
    }
   	//删除
   	function dell(){
   	 var count = 0;
 	  var ids = document.getElementsByName("check");
 	 var id2="";
 	 var num =0;
      for(i=0;i<ids.length;i++) {
    		 if(document.getElementsByName("check")[i].checked){
	    		  id2 += document.getElementsByName("check")[i].value+",";
	    		  num++;
    		  }
    		 //id.push(document.getElementsByName("check")[i].value);
       		 count++;
     }
   	var id = id2.substring(0,id2.length-1);
   		if(num>0){
   			layer.confirm('您确定要删除吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
   	 			layer.close(index);
   	 			$.ajax({
   	 				url:"${pageContext.request.contextPath}/credible/deleteAll.html",
   	 				data:{"ids":id},
   	 				type:"post",
   	 	       		success:function(){
   	 	       			layer.msg('删除成功',{offset: ['222px', '390px']});
   	 		       		window.setTimeout(function(){
   	 		       			window.location.reload();
   	 		       				for(var i = 0;i<info.length;i++){
   	 						info[i].checked = false;
   	 						}
   	 		       		}, 1000);
   	 	       		},
   	 	       		error: function(){
   	 					layer.msg("删除失败",{offset: ['222px', '390px']});
   	 				}
   	 	       	});
   	 		});
   		}else{
   			layer.alert("请选择一条记录",{offset: ['222px', '390px'],shade:0.01});
      	}
   		
   	}
   	//清空搜索条件
   	function clearSearch(){
   		$("#relName").attr("value","");
   	    //还原select下拉列表只需要这一句
	   	$("#expertsFrom option:selected").removeAttr("selected");
   	}
   	var index;
   	function cancel(){
 	   layer.close(index);
 	}
   	function openWindow(){
		index = layer.open({
	          type: 1, //page层
	          area: ['700px', '300px'],
	          title: '新增',
	          shade:0.01, //遮罩透明度
	          moveType: 1, //拖拽风格，0是默认，1是传统拖动
	          shift: 1, //0-6的动画形式，-1不开启
	          offset: ['220px', '250px'],
	          shadeClose: true,
	          content:$('#openWindow') //数组第二项即吸附元素选择器或者DOM $('#openWindow')
		 });
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
		$.ajax({
			url:"${pageContext.request.contextPath}/credible/save.html",
			data:$("#form2").serialize(),
			type:"post",
			success:function(){
				layer.msg("新增成功！",{offset: ['222px', '390px']});
				window.setTimeout(function(){
		       			window.location.reload();
		       		}, 1000);
			},
			error:function(){
				layer.msg("参数错误，保存失败",{offset: ['222px', '390px']});
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
	/*  function textre(t) {
	    t.value = t.value.replace(/[^(\-)0-9]/g,'').replace(/(^|\D)\.+/g,"$1").replace(/^(\-?\d*\.?\d*).*$/,"$1").replace(/^(\-?(\d\.?){1,4}).*$/,"$1");
	}  */
	
	
</script>
</head>
<body>
  <div class="wrapper">
<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">业务管理</a></li><li><a href="#">专家诚信列表</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
<!-- 我的订单页面开始-->
  
    <div class="container">
   <div class="headline-v2">
     <h2>专家诚信列表</h2>
   </div>   
     <h2 class="search_detail">
  
   <form action="${pageContext.request.contextPath}/credible/list.html"  id="form1"  method="post"   class="mb0"> 
  <input type="hidden" name="page" id="page">
  <input type="hidden" name="flag" value="0">
                    <table>
                    <tr>
                    <td>
                    <span >关键字：</span><input type="text" id="relName" name="badBehavior" value="${expertCredible.badBehavior }">
                    </td>
                    <td>
						 <span>状态：</span>
						   <select  name="isStatus" id="expertsFrom" class="w178">
						    <option selected="selected" value=''>-请选择-</option>
						   	<option <c:if test="${expertCredible.isStatus ==1}">selected</c:if> value="1">启用</option>
						   	<option <c:if test="${expertCredible.isStatus ==2 }">selected</c:if> value="2">停用</option>
						   </select>
					</td>
					<td>
                          <input class="btn btn-windows "  value="搜索" type="submit">
                          <input class="btn btn-windows" id="button" onclick="clearSearch();" value="重置" type="reset">
                     </td>
                        </tr>
                        </table>
                  </form>
                  </h2>
                  </div>
                  </div>  
<!-- 表格开始-->
   <div class="container">
   <div class="col-md-12 pl20 mt10">
    <!-- <button class="btn btn-windows add" type="submit">新增</button>
	<button class="btn btn-windows edit" type="submit">修改</button>
	<button class="btn btn-windows delete" type="submit">删除</button> -->
	<button class="btn btn-windows add" type="button" onclick="openWindow();">新增</button>
	<button class="btn btn-windows edit" type="button" onclick="edit();">修改</button>
	<button class="btn btn-windows delete" type="button" onclick="dell();">删除</button>
	</div>
   
   <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover">
		<thead>
		<tr>
		  <th class="info w30"><input type="checkbox" onclick="selectAll();"  id="allId" alt=""></th>
		  <th class="info w50">序号</th>
		  <th class="info">诚信内容</th>
		  <th class="info">状态</th>
		  <th class="info">创建时间</th>
		  <th class="info">修改时间</th>
		  <th class="info">分值</th>
		</tr>
		</thead>
		<c:forEach items="${result.list }" var="e" varStatus="vs">
		<tr style="cursor: pointer; ">
		  <td class="tc w30"><input type="checkbox" name="check" id="checked" alt="" value="${e.id }"></td>
		  <td  class="tc w50">${(vs.index+1)+(result.pageNum-1)*(result.pageSize)}</td>
		  <td class="tc" title="${e.badBehavior}">
		     <c:if test="${fn:length(e.badBehavior)>5}">${fn:substring(e.badBehavior, 0, 5)}...</c:if> 
		     <c:if test="${fn:length(e.badBehavior)<6}">${e.badBehavior}</c:if>
		  </td>
		  <c:if test="${e.isStatus == 1 }">
		 	<td  class="tc"><span class="label rounded-2x label-u">启用</span></td>
		 </c:if>
		 <c:if test="${e.isStatus == 2 }">
		 	<td  class="tc"><span class="label rounded-2x label-dark">停用</span></td>
		 </c:if>
		 <td  class="tc"><fmt:formatDate type='date' value='${e.createAt }' dateStyle="default" pattern="yyyy-MM-dd"/></td>
		 <td  class="tc"><fmt:formatDate type='date' value='${e.updateAt }' dateStyle="default" pattern="yyyy-MM-dd"/></td>
		 <td  class="tc">${e.score }</td>
		</tr>
		</c:forEach>
        </table>
        <div id="pagediv" align="right"></div>
     </div>
   </div>
    
 </div>
 <div id="openWindow"  style="display: none;">
	<form action="${pageContext.request.contextPath}/credible/save.html" method="post" id="form2">
     <table class="table table-bordered table-condensed">
     <thead>
      <tr>
        <th>诚信内容:</th><td><input type="text"  maxlength="255" name="badBehavior" id="name"></td>
        <th>状态:</th><td><input type="radio"  name="isStatus" value="1" >启用&nbsp;<input type="radio" name="isStatus" id="kind" value="2" >停用</td>
        <th>分值:</th><td><input name="score"  maxlength="10" id="creater" type="text"></td>
      </tr>
     </thead>
    </table>
    <input type="button"  value="添加" onclick="submit1();" class="btn btn-windows add"/>
    <input type="button"  value="取消" onclick="cancel();"  class="btn btn-windows cancel"/>
  </form>
</div>
</body>
</html>
