<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<script type="text/javascript">
  /*分页  */
  $(function() {
    laypage({
      cont : $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
      pages : "${list.pages}", //总页数
      skin : '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
      skip : true, //是否开启跳页
      total : "${list.total}",
      startRow : "${list.startRow}",
      endRow : "${list.endRow}",
      groups : "${list.pages}" >= 3 ? 3 : "${list.pages}", //连续显示分页数
      curr : function() { //通过url获取当前页，也可以同上（pages）方式获取
        return "${list.pageNum}";
      }(),
      jump : function(e, first) { //触发分页后的回调
        if (!first) { //一定要加此判断，否则初始时会无限刷新
          $("#page").val(e.curr);
          $("#form1").submit();
        }
      }
    });
    var confirmFile = "${confirmFile}";
    $("#status").find("option[value='"+confirmFile+"']").attr("selected",true);
    
  });

  /** 全选全不选 */
  function selectAll() {
	var checklist = document.getElementsByName("chkItem");
	var checkAll = document.getElementById("checkAll");
	if (checkAll.checked) {
	  for ( var i = 0; i < checklist.length; i++) {
		checklist[i].checked = true;
	  }
	} else {
	  for ( var j = 0; j < checklist.length; j++) {
		checklist[j].checked = false;
	  }
	}
  }

  /** 单选 */
  function check() {
    var count = 0;
	var checklist = document.getElementsByName("chkItem");
	var checkAll = document.getElementById("checkAll");
	for ( var i = 0; i < checklist.length; i++) {
	  if (checklist[i].checked == false) {
		checkAll.checked = false;
		break;
	  }
	  for ( var j = 0; j < checklist.length; j++) {
		if (checklist[j].checked == true) {
		  checkAll.checked = true;
		  count++;
		}
	  }
    }
  }

  //查看明细
  function view(id) {
    window.location.href = "${pageContext.request.contextPath}/advancedProject/view.html?id=" + id;
  }

  //审核
  function audit() {
	     var id=[]; 
	     $('input[name="chkItem"]:checked').each(function(){ 
	       id.push($(this).val());
	     }); 
	     if(id.length==1){
	    	  window.location.href = "${pageContext.request.contextPath}/open_bidding/bidFile.html?id=" + id + "&process=1";   
	     }else if(id.length>1){
	       layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
	     }else{
	       layer.alert("请选择项目",{offset: ['222px', '390px'], shade:0.01});
	     }
	  
	  
	
	  
  }
  //重置
  function clearSearch() {
	$("#proName").attr("value", "");
	$("#projectNumber").attr("value", "");
	$("#status option:selected").removeAttr("selected");
  }
</script>
</head>

<body>
  <!--面包屑导航开始-->
  <div class="margin-top-10 breadcrumbs ">
    <div class="container">
	  <ul class="breadcrumb margin-left-0">
	    <li><a href="javascript:void(0)">首页</a></li>
		<li><a href="javascript:void(0)">保障作业系统</a></li>
		<li><a href="javascript:void(0)">采购项目管理</a></li>
		<li class="active"><a href="javascript:void(0)">采购文件审核</a></li>
	  </ul>
	  <div class="clear"></div>
    </div>
  </div>
  <!-- 录入采购计划开始-->
  <div class="container">
    <div class="headline-v2">
	  <h2>采购文件审核</h2>
	</div>
	<!-- 项目戳开始 -->
    <h2 class="search_detail">
	  <form action="${pageContext.request.contextPath}/Auditbidding/list.html" id="form1" method="post" class="mb0">
	    <ul class="demand_list">
		  <li>
		    <label class="fl">项目名称： </label>
		    <span>
		      <input type="hidden" name="page" id="page">
		      <input type="text" name="name" id="proName" value="${project.name }" /> 
		    </span>
		  </li>
		  <li>
		    <label class="fl">项目编号：</label> 
		    <span>
		      <input type="text" name="projectNumber" id="projectNumber" value="${project.projectNumber }" /> 
		    </span>
		  </li>
		   <li>
        <label class="fl">审核状态：</label> 
        <span>
        <select class="w178" id="status" name="confirmFile" >
            <option value="">全部</option>
            <option value="1">待审核</option>
            <option value="2">审核未通过</option>
            <option value="3">审核通过</option>
            <option value="4">修改报备</option>
        </select>
        </span>
      </li>
		</ul>
		  <button class="btn fl mt1" type="submit">查询</button>
	      <button type="reset" class="btn fl mt1" onclick="clearSearch();">重置</button>
		<div class="clear"></div>
	  </form>
    </h2>
	<div class="col-md-12 pl20 mt10">
	  <button class="btn btn-windows apply" onclick="audit();" type="button">审核</button>
	</div>
	<div class="content table_box">
      <table class="table table-bordered table-condensed table-hover table-striped">
	    <thead>
		  <tr>
		    <th class="info w30">
		      <input type="checkbox" id="checkAll" onclick="selectAll()" alt="" />
		    </th>
			<th class="info w50">序号</th>
			<th class="info">项目名称</th>
			<th class="info">项目编号</th>
			<th class="info">采购方式</th>
			<!-- <th class="info">项目创建时间</th> -->
			<th class="info">提交时间</th>
			<th class="info">项目负责人</th>
			<th class="info">审核状态</th>
		  </tr>
		</thead>
		<tbody id="tbody_id">
		  <c:forEach items="${list.list}" var="obj" varStatus="vs">
		    <tr style="cursor: pointer;">
			  <td class="tc w30">
			    <input type="checkbox" value="${obj.id }" name="chkItem" onclick="check()" alt="">
			  </td>
			  <td class="tc w50">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
			  <td class="tl w320" title="${obj.name}">
			  	 <c:if test="${fn:length(obj.name)>20}">
			  	 ${fn:substring(obj.name,0,20)}...
			  	 </c:if>
			  	<c:if test="${fn:length(obj.name)<=20}">
			  	 ${obj.name}
			  	 </c:if>
			  </td>
			  <td class="tl w120">${obj.projectNumber}</td>
			  <td class="tc">
			    <%-- <a href="javascript:void(0)" onclick="view('${obj.id}');"> --%>
				  <c:forEach items="${kind}" var="kind" >
                    <c:if test="${kind.id == obj.purchaseType}">${kind.name}</c:if>
                  </c:forEach>
				<!-- </a> -->
			  </td>
			  <%-- <td class="tl pl20" ><fmt:formatDate type='date' value='${obj.createAt}'  pattern=" yyyy-MM-dd HH:mm:ss "/></td> --%>
			  <td class="tl w180" ><fmt:formatDate type='date' value='${obj.approvalTime}'  pattern=" yyyy-MM-dd HH:mm:ss "/></td>
			  <td class="tc" >${obj.projectContractor}</td>
			   <td class="tc">
			   <c:if test="${obj.confirmFile == 1 }">待审核</c:if>
			   <c:if test="${obj.confirmFile == 3 }">审核通过</c:if>
			   <c:if test="${obj.confirmFile == 2 }">退回重报</c:if>
			   <c:if test="${obj.confirmFile == 4 }">修改报备</c:if>
			   </td>
			</tr>
		  </c:forEach>
		</tbody>
	  </table>
	</div>
	<div id="pagediv" align="right"></div>
  </div>
</body>
</html>
