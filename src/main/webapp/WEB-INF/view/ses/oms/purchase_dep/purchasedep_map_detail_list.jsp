<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../../../common.jsp"%>
<script type="text/javascript">
	  	  $(function(){
		  laypage({
			    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
			    pages: "${list.pages}", //总页数
			    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			    skip: true, //是否开启跳页
			    total: "${list.total}",
			    startRow: "${list.startRow}",
			    endRow: "${list.endRow}",
			    groups: "${list.pages}">=5?5:"${list.pages}", //连续显示分页数
			    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
			        return "${list.pageNum}";
			    }(), 
			    jump: function(e, first){ //触发分页后的回调
			        if(!first){ //一定要加此判断，否则初始时会无限刷新
			             $("#page").val(e.curr);
                   $("#form1").submit();
			        }
			    }
			});
	  });
	  function fanhui(){
	  	window.location.href="${pageContext.request.contextPath}/purchaseManage/purchaseDepMapList.html";
	  }
function chongzhi(){
	$("#name").val('');
	$("#quaStartDate").val('');
	$("#quaEdndate").val('');
	$("option")[0].selected = true;
}

</script>
</head>
  <body>
  	
  	<div class="margin-top-10 breadcrumbs ">
	      <div class="container">
			  <ul class="breadcrumb margin-left-0">
				  <li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a></li>
				  <li><a href="javascript:void(0);">支撑系统</a></li>
				  <li><a href="javascript:void(0);">机构管理</a></li>
				  <li class="active"><a href="javascript:void(0);" onclick="jumppage('${pageContext.request.contextPath}/purchaseManage/purchaseDepMapList.html')">采购机构查询管理</a></li>
			  </ul>
			<div class="clear"></div>
		  </div>
	   </div>
	   <div class="container">
		   <div class="headline-v2">
		     <h2>采购机构信息</h2>
		   </div>  
		   <h2 class="search_detail">
  				<form id="form1" action="${pageContext.request.contextPath}/purchaseManage/purchaseDepdetailList.html" method="post" class="mb0">
		       <input type="hidden" name="page" id="page">
		       <input type="hidden" name="parentName" value="${ parentName }">
		      <ul class="demand_list">
                       <li>
                        <label class="fl">采购机构名称：</label><span><input id="name" name="name" value="${purchaseDep.name }" type="text"></span>
                      </li>
                       <li>
                        <label class="fl">资质起止日期：</label>
                        <input id="quaStartDate" name="quaStartDate" class="Wdate w150" type="text"  value='<fmt:formatDate value="${purchaseDep.quaStartDate }" pattern="YYYY-MM-dd"/>'
                        onFocus="var endDate=$dp.$('endDate');WdatePicker({onpicked:function(){quaStartDate.focus();},maxDate:'#F{$dp.$D(\'quaStartDate\')}'})"/>
                        <span class="f14">至</span>
                        <input id="quaEdndate" name="quaEdndate" value='<fmt:formatDate value="${purchaseDep.quaEdndate }" pattern="YYYY-MM-dd"/>' class="Wdate w150" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'quaEdndate\')}'})"/>
                      </li>
                      <!-- <li>
                        <label class="fl">上级监管部门：</label>
                        <span class="fl">
                          <select name="" class="w100">
                                    <option selected="selected" value=''>-请选择-</option>
                                    <option  value="生产型">部门1</option>
                                    <option  value="销售型">部门2</option>
                                    <option  value="军区采购">军区采购</option>
                          </select>
                        </span>
                      </li> -->
                  </ul>
                        <input class="btn fl mt1" onclick="submit()" type="button" value="查询">
                        <input class="btn fl mt1" onclick="chongzhi()" type="button" value="重置"> 
                   <div class="clear"></div>
		     </form>
		     </h2>
		     <div class="col-md-12 pl20 mt10" style="margin-top: 20px">
		          <input class="btn btn-windows back" value="返回" type="button" onclick="fanhui();">
		     </div>
		      <div class="content table_box">
                 <table id="tb1" class="table table-bordered table-condensed table-hover table-striped">
		      <thead>
				<tr>
					<th class="info w50">序号</th>
					<th class="info" width="18%">采购机构名称</th>
					<th class="info" width="13%">采购资质编号</th>
					<th class="info w80">等级</th>
					<th class="info" width="15%">地址</th>
					<th class="info" width="12%">采购资质开始日期</th>
					<th class="info" width="12%">采购资质截止日期</th>
					<!-- <th class="info">上级监管部门</th> -->
				</tr>
			  </thead>
			  <tbody>
				 <c:forEach items="${list.list }" var="list" varStatus="vs">
					<tr>
						<td class="tc w50">${vs.index+1 }</td>
						<td><a href="${pageContext.request.contextPath}/purchaseManage/purchaseDepMapShow.html?orgId=${list.orgId}&parentName=${parentName}">${list.name }</a></td>
						<td>${list.quaCode }</td>
						<td>
							<c:forEach items="${kind}" var="status">
							  <c:if test="${status.id eq list.levelDep}">${status.name}</c:if>
							</c:forEach>
						</td>
						<td>${list.address }</td>
						<td><fmt:formatDate value="${list.quaStartDate }" pattern="yyyy-MM-dd" /></td>
						<td><fmt:formatDate value="${list.quaEdndate }" pattern="yyyy-MM-dd" /></td>
						<!-- <td>军区采购</td> -->
					</tr>
				</c:forEach> 
			  </tbody>
		 </table>
		 <div id="pagediv" align="right"></div>
     </div>
     </div>
     
  </body>
</html>
