<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
<title>专家诚信列表</title>
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
    		         area: ['500px', '300px'],
    		          title: '修改',
    		          shade:0.01, //遮罩透明度
    		          moveType: 1, //拖拽风格，0是默认，1是传统拖动
    		          shift: 1, //0-6的动画形式，-1不开启
    		          offset: ['110px', '30%'],
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
	          area: ['500px', '300px'],
	          title: '新增',
	          shade:0.01, //遮罩透明度
	          moveType: 1, //拖拽风格，0是默认，1是传统拖动
	          shift: 1, //0-6的动画形式，-1不开启
	          offset: ['80px', '30%'],
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
</script>
</head>
<body>
  <div class="wrapper">
<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
          <ul class="breadcrumb margin-left-0">
              <li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a></li>
              <li><a href="javascript:void(0)">支撑环境</a></li>
              <li><a href="javascript:void(0)">专家管理</a></li>
              <li><a href="javascript:jumppage('${pageContext.request.contextPath}/credible/list.html')">专家诚信管理</a></li>
          </ul>
		<div class="clear"></div>
	  </div>
   </div>
<!-- 我的订单页面开始-->
  
    <div class="container">
   <div class="headline-v2">
     <h2>专家诚信列表</h2>
   </div>   
    <div class="search_detail">
  
   <form action="${pageContext.request.contextPath}/credible/list.html"  id="form1"  method="post"   class="mb0"> 
  <input type="hidden" name="page" id="page">
  <input type="hidden" name="flag" value="0">
                   <ul class="demand_list">
                   <li>
                    <label>诚信内容：</label><span><input type="text" id="relName" name="badBehavior" value="${expertCredible.badBehavior }"></span>
                       </li>
                       <li>
                       <span class="fl">
						 <label class="fl">状态：</label>
						   <select  name="isStatus" id="expertsFrom" class="w178">
						    <option selected="selected" value=''>-请选择-</option>
						   	<option <c:if test="${expertCredible.isStatus ==1}">selected</c:if> value="1">启用</option>
						   	<option <c:if test="${expertCredible.isStatus ==2 }">selected</c:if> value="2">停用</option>
						      </select>
						   </span>
					   </li>
					</ul>
                          <input class="btn fl mt1"  value="查询" type="submit">
                          <input class="btn fl  mt1" id="button" onclick="clearSearch();" value="重置" type="reset">
                 	<div class="clear"></div>
                  </form>
                  </div>
                  </div>
                  </div>
               
<!-- 表格开始-->
   <div class="container">
   <div class="col-md-12 col-sm-12 col-xs-12 pl20 mt10">
    <!-- <button class="btn btn-windows add" type="submit">新增</button>
	<button class="btn btn-windows edit" type="submit">修改</button>
	<button class="btn btn-windows delete" type="submit">删除</button> -->
	<button class="btn btn-windows add" type="button" onclick="openWindow();">新增</button>
	<button class="btn btn-windows edit" type="button" onclick="edit();">修改</button>
	<button class="btn btn-windows delete" type="button" onclick="dell();">删除</button>
	</div>
   
   <div class="content table_box ">
        <table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
		  <th class="info w30"><input type="checkbox" onclick="selectAll();"  id="allId" alt=""></th>
		  <th class="info w50">序号</th>
		  <th class="info" width="40%">诚信内容</th>
		  <th class="info" width="10%">状态</th>
		  <th class="info" width="15%">创建时间</th>
		  <th class="info" width="15%">修改时间</th>
		  <th class="info">分值</th>
		</tr>
		</thead>
		<c:forEach items="${result.list }" var="e" varStatus="vs">
		<tr style="cursor: pointer; ">
		  <td class="tc w30"><input type="checkbox" name="check" id="checked" alt="" value="${e.id }"></td>
		  <td  class="tc w50">${(vs.index+1)+(result.pageNum-1)*(result.pageSize)}</td>
		  <td class="tl" title="${e.badBehavior}">
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
    
 <div id="openWindow"   class="dnone layui-layer-wrap" >
    <div class="drop_window">
	<form action="${pageContext.request.contextPath}/credible/save.html" method="post" id="form2">
	 <ul class="list-unstyled">
                <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
                   <label class="col-md-12 pl20 col-xs-12"><div class="red star_red">*</div>诚信内容</label>
                  <span class="col-md-12 col-xs-12">
                    <input type="text" class="title col-md-12"  maxlength="255" name="badBehavior" id="name">
                  </span>
                </li>
                <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
                  <label class="col-md-12 pl20 col-xs-12"><div class="red star_red">*</div>状态</label>
                  <span class="col-md-12 col-xs-12">
                   <input type="radio"  name="isStatus" value="1" >启用&nbsp;<input type="radio" name="isStatus" id="kind" value="2" >停用
                  </span>
                </li>
                <li class="mt10 col-md-12 p0">
                  <label class="col-md-12 pl20 col-xs-12"><div class="red star_red">*</div>分值</label>
                   <span class="col-md-12 col-xs-12">
                   <input name="score" class="title col-md-12"   maxlength="5" id="creater" type="text">
                  </span>
                </li>
                <div class="clear"></div>
               </ul>
               <div class="tc mt10 col-md-12 col-xs-12">
				    <input type="button"  value="添加" onclick="submit1();" class="btn btn-windows add"/>
				    <input type="button"  value="取消" onclick="cancel();"  class="btn btn-windows cancel"/>
			     </div>
  </form>
</div>
</div>
</body>
</html>
