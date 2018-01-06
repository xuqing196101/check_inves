<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
 
  <script type="text/javascript">
  
  /*分页  */
  $(function(){
	  laypage({
		    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
		    pages: "${info.pages}", //总页数
		    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		    total: "${info.total}",
		    startRow: "${info.startRow}",
		    endRow: "${info.endRow}",
		    skip: true, //是否开启跳页
		    groups: "${info.pages}">=3?3:"${info.pages}", //连续显示分页数
		    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
//			        var page = location.search.match(/page=(\d+)/);
//			        return page ? page[1] : 1;
				return "${info.pageNum}";
		    }(), 
		    jump: function(e, first){ //触发分页后的回调
		         if(!first){ //一定要加此判断，否则初始时会无限刷新
		        	 $("#page").val(e.curr);
		        	 $("#form1").submit();
		        	  
		        // window.location.href = "${pageContext.request.contextPath}/adjust/list.html?page="+e.curr;
		        }  
		    }
		});
  });
  
  
	/** 全选全不选 */
	function selectAll(){
		 var checklist = document.getElementsByName ("chkItem");
		 var checkAll = document.getElementById("checkAll");
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
	
	/** 单选 */
	function check(){
		 var count=0;
		 var checklist = document.getElementsByName ("chkItem");
		 var checkAll = document.getElementById("checkAll");
		 for(var i=0;i<checklist.length;i++){
			   if(checklist[i].checked == false){
				   checkAll.checked = false;
				   break;
			   }
			   for(var j=0;j<checklist.length;j++){
					 if(checklist[j].checked == true){
						   checkAll.checked = true;
						   count++;
					   }
				 }
		   }
	}
    
	function down(){
	  	var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){   
			
			window.location.href="${pageContext.request.contextPath}/set/excel.html?id="+id;
 	  	}else if(id.length>1){
			layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
		}else{
			layer.alert("请选中一条",{offset: ['222px', '390px'], shade:0.01});
		}  
		 
	}
	function looks(){
  	var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		var taskNature = $("input[name='chkItem']:checked").parents("tr").find("td").eq(6).find("input").val();
		taskNature = $.trim(taskNature);
		if(taskNature == "1"){
		  layer.msg("预研任务不能修改！");
		}else{
		  if(id.length==1){
	  var status = $("input[name='chkItem']:checked").parents("tr").find("td").eq(5).text();
	  if($.trim(status)!="已取消"){
		  window.location.href="${pageContext.request.contextPath}/adjust/all.html?id="+id;
	  }else{
		  layer.msg("已取消任务不能调整");
	  }
      }else if(id.length>1){
      layer.msg("只能选择一个");
    }else{
      layer.msg("请选中一条");
    }
		}
		
	}
	function show(id){
		window.location.href="${pageContext.request.contextPath}/adjust/all.html?id="+id;
	}	
 	
	//取消采购任务
	function cancel(){
		var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		});
		var status = $("input[name='chkItem']:checked").parents("tr").find("td").eq(5).find("input").val();
		status = $.trim(status);
		
		if (id.length == 0){
			layer.msg("请选择需要取消的任务记录");
			return ;
		}
		if(status == '2'){
		  layer.msg("任务已取消");
		  return;
		}
		layer.confirm('您确定要取消？', {
			  btn: ['确定','取消'] 
			}, function(){
				ajaxCancel(id.toString());
			}
		 );
	}
	
	//ajax取消
	function ajaxCancel(ids){
		$.ajax({
			type: "POST",  
            url: "${pageContext.request.contextPath}/adjust/cancel.do",  
            data: {'ids':ids},  
            success:function(msg){
   		     if (msg == 'ok'){
   		    	 layer.msg("取消成功");
   		    	 window.location.href='${pageContext.request.contextPath}/adjust/list.do';
   		    	/*  updateStatus(); */
   		     }
   		   /*   if (msg == 'failed'){
   		    	 layer.msg("记录中存在已取消状态的任务");
   		     } */
            }
		 });
	}
	
	
	//更新状态
	function updateStatus(){
		$('input[name="chkItem"]:checked').each(function(){ 
			$(this).parents("tr").find("td").eq(5).text("已取消");
		});
	}
	
	/** 查看任务 */
    function viewd(id) {
      window.location.href = "${pageContext.request.contextPath}/task/view.html?id=" + id;
    }
	
	function clearSearch() {
        $("#name").attr("value", "");
        $("#documentNumber").attr("value", "");
        //还原select下拉列表只需要这一句
        $("#status option:selected").removeAttr("selected");
      }
  </script>
  </head>
  
  <body>
<!--面包屑导航开始-->
 <div class="margin-top-10 breadcrumbs ">
      <div class="container">
          <ul class="breadcrumb margin-left-0">
              <li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a></li>
              <li><a href="javascript:void(0)">保障作业系统</a></li>
              <li><a href="javascript:void(0)">采购项目管理</a></li>
              <li class="active"><a href="javascript:jumppage('${pageContext.request.contextPath}/adjust/list.html');">采购任务调整</a></li>
          </ul>
		<div class="clear"></div>
	  </div>
   </div>
<!-- 录入采购计划开始-->
 <div class="container">
   <div class="headline-v2 fl">
      <h2>采购任务列表</h2>
   </div> 
   
   
    <h2 class="search_detail">
    <form id="form1" action="${pageContext.request.contextPath}/adjust/list.html" method="post" class="mb0">
    <input type="hidden" name="page" id="page">
		<div class="m_row_5">
    <div class="row">
      <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3">
        <div class="row">
          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">采购任务名称：</div>
          <div class="col-xs-8 f0 lh0">
						<input type="text" name="name" id="name" value="${task.name}" class="w100p h32 f14 mb0">
          </div>
        </div>
      </div>
      
      <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3">
        <div class="row">
          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">采购任务文号：</div>
          <div class="col-xs-8 f0 lh0">
						<input type="text" name="documentNumber" id="documentNumber" value="${task.documentNumber }" class="w100p h32 f14 mb0">
          </div>
        </div>
      </div>
      
      <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3">
        <div class="row">
          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">状态：</div>
          <div class="col-xs-8 f0 lh0">
						<select name="status" id="status" class="w100p h32 f14">
							<option selected="selected" value="">请选择</option>
							<option value="0" <c:if test="${'0'==task.status}">selected="selected"</c:if>>未受领</option>
							<option value="1" <c:if test="${'1'==task.status}">selected="selected"</c:if>>已受领</option>
							<option value="2" <c:if test="${'2'==task.status}">selected="selected"</c:if>>已取消</option>
						</select>
          </div>
        </div>
      </div>
      
      <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3">
        <div class="row">
          <div class="col-xs-12 f0">
						<button class="btn mb0 h32" type="submit">查询</button>
			      <button class="btn mb0 mr0 h32" type="reset" onclick="clearSearch()">重置</button>
					</div>
        </div>
      </div>
    </div>
    </div>
    </form>
  </h2>
  
   <div class="col-md-12 pl20 mt10">
   		<c:if test="${auth == 'show'}">
			<button class="btn padding-left-10 padding-right-10 btn_back" onclick="looks()">调整采购任务</button>
			<button class="btn padding-left-10 padding-right-10 btn_back" onclick="cancel()">取消采购任务</button>
 		</c:if>
   </div>
   <div class="content table_box">
         <table class="table table-bordered table-condensed table-hover table-striped">
          <thead>
            <tr class="info">
              <th class="w30"><input type="checkbox" id="checkAll" onclick="selectAll()"></th>
              <th class="w50">序号</th>
              <th width="25%">采购任务名称</th>
              <th width="20%">采购管理部门</th>
              <th width="16%">采购任务文号</th>
              <th width="10%">状态</th>
              <th width="10%">任务性质</th>
              <th>下达时间</th>
            </tr>
          </thead>
          <c:forEach items="${info.list}" var="obj" varStatus="vs">
            <tr class="pointer">
              <td class="tc w30"><input type="checkbox" value="${obj.id }" name="chkItem" onclick="check()"></td>
              <td class="tc w50">${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>
              <td title="${obj.name}">
                <a href="javascript:void(0)" onclick="viewd('${obj.id}');">
	                <c:if test="${fn:length (obj.name) > 20}">${fn:substring(obj.name,0,19)}...</c:if>
	                <c:if test="${fn:length(obj.name) <= 20}">${obj.name}</c:if>
                </a>
              </td>
              <td>
                <a href="javascript:void(0)" onclick="viewd('${obj.id}');">
                  <c:forEach items="${list2}" var="list" varStatus="vs">
                    <c:if test="${obj.orgId eq list.id}">${list.shortName}</c:if>
                  </c:forEach>
                </a>
              </td>
              <td>
                <a href="javascript:void(0)" onclick="viewd('${obj.id}');">${obj.documentNumber}</a>
              </td>
              <td class="tc">
                <c:if test="${'0'==obj.status}">
                 		未受领
                </c:if>
                <c:if test="${'1'==obj.status}">
                  			已受领
                </c:if>
                <c:if test="${'2'==obj.status}">
                 		 已取消 
                </c:if>
                <input type="hidden" value="${obj.status}"/>
              </td>
              <td class="tc">
                <c:if test="${'1'==obj.taskNature}">
                  <span class="label rounded-2x label-orange">预研任务</span>
                </c:if>
                <c:if test="${'0'==obj.taskNature}">
                  <span class="label rounded-2x label-u">正常任务</span>
                </c:if>
                <input type="hidden" value="${obj.taskNature}"/>
              </td>
              <td class="tc">
                <fmt:formatDate value="${obj.giveTime }" pattern="yyyy-MM-dd"/>
              </td>
            </tr>
          </c:forEach>
        </table>
      <div id="pagediv" align="right"></div>
   </div>
 </div>
</body>
</html>
