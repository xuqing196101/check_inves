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
          cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
          pages: "${info.pages}", //总页数
          skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
          skip: true, //是否开启跳页
          total: "${info.total}",
          startRow: "${info.startRow}",
          endRow: "${info.endRow}",
          groups: "${info.pages}" >= 3 ? 3 : "${info.pages}", //连续显示分页数
          curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
            return "${info.pageNum}";
          }(),
          jump: function(e, first) { //触发分页后的回调
            if(!first) { //一定要加此判断，否则初始时会无限刷新
              $("#page").val(e.curr);
              $("#form1").submit();
            }
          }
        });
      });

      //查看明细
      function view(id,type) {
        window.location.href = "${pageContext.request.contextPath}/project/excute.html?id=" + id + "&type=" + type;
      }




      //重置
      function clearSearch() {
        $("#proName").attr("value", "");
        $("#projectNumber").attr("value", "");
        $("#materialsType").attr("value", "");
        $("#sectorOfDemand").attr("value", "");
        $("#prIntroduce").attr("value", "");
        $("#status option:selected").removeAttr("selected");
        $("#purchaseType option:selected").removeAttr("selected");
        $("#purchaseDepId option:selected").removeAttr("selected");
      }
    </script>
  </head>

  <body>
    <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
          <li>
            <a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
          </li>
          <li>
            <a href="javascript:void(0)">保障作业系统</a>
          </li>
          <li>
            <a href="javascript:void(0)">采购项目管理</a>
          </li>
          <li class="active">
            <a href="javascript:jumppage('${pageContext.request.contextPath}/project/projectByAll.html')">全部采购项目</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    <!-- 录入采购计划开始-->
    <div class="container">
      <div class="headline-v2">
        <h2>采购项目列表</h2>
      </div>
      <!-- 项目戳开始 -->
      <div class="search_detail">
      <form action="${pageContext.request.contextPath}/project/projectByAll.html" id="form1" method="post" class="mb0">
      <div class="m_row_5">
    	<div class="row">
    		<div class="col-md-3 col-sm-4 col-xs-6 mb10">
    			<div class="row">
    				<div class="col-xs-4 h32 lh32 tr text-nowrapEl">项目名称：</div>
    				<div class="col-xs-8 f0 lh0">
    					<input type="hidden" name="page" id="page">
          		<input type="text" name="name" id="proName" value="${projects.name}" class="w100p h32 mb0">
    				</div>
    			</div>
    		</div>
    		
    		<div class="col-md-3 col-sm-4 col-xs-6 mb10">
    			<div class="row">
    				<div class="col-xs-4 h32 lh32 tr text-nowrapEl">项目编号：</div>
    				<div class="col-xs-8 f0 lh0">
          		<input type="text" name="projectNumber" id="projectNumber" value="${projects.projectNumber}" class="w100p h32 mb0">
    				</div>
    			</div>
    		</div>
    		
    		<div class="col-md-3 col-sm-4 col-xs-6 mb10">
    			<div class="row">
    				<div class="col-xs-4 h32 lh32 tr text-nowrapEl">状态：</div>
    				<div class="col-xs-8 f0 lh0">
    					<select name="status" id="status" class="w100p h32">
                <option selected="selected" value="">请选择</option>
                <c:forEach items="${status}" var="status" >
                  <option  value="${status.id}" <c:if test="${status.id eq projects.status}">selected="selected"</c:if>>${status.name}</option>
                </c:forEach>
              </select>
    				</div>
    			</div>
    		</div>
    		
    		<div class="col-md-3 col-sm-4 col-xs-6 mb10">
    			<div class="row">
    				<div class="col-xs-4 h32 lh32 tr text-nowrapEl">产品名称：</div>
    				<div class="col-xs-8 f0 lh0">
    					<input type="text" name="materialsType" id="materialsType" value="${projects.materialsType}" class="w100p h32 mb0">
    				</div>
    			</div>
    		</div>
    		
    		<div class="col-md-3 col-sm-4 col-xs-6 mb10">
    			<div class="row">
    				<div class="col-xs-4 h32 lh32 tr text-nowrapEl">需求部门：</div>
    				<div class="col-xs-8 f0 lh0">
    					<input type="text" name="sectorOfDemand" id="sectorOfDemand" value="${projects.sectorOfDemand}" class="w100p h32 mb0">
    				</div>
    			</div>
    		</div>
    		
    		<div class="col-md-3 col-sm-4 col-xs-6 mb10">
    			<div class="row">
    				<div class="col-xs-4 h32 lh32 tr text-nowrapEl">任务文号：</div>
    				<div class="col-xs-8 f0 lh0">
    					<input type="text" name="prIntroduce" id="prIntroduce" value="${projects.prIntroduce}" class="w100p h32 mb0">
    				</div>
    			</div>
    		</div>
    		
    		<div class="col-md-3 col-sm-4 col-xs-6 mb10">
    			<div class="row">
    				<div class="col-xs-4 h32 lh32 tr text-nowrapEl">采购方式：</div>
    				<div class="col-xs-8 f0 lh0">
    					<select name="purchaseType" id="purchaseType" class="w100p h32">
                <option selected="selected" value="">请选择</option>
                <c:forEach items="${kind}" var="type">
                  <option  value="${type.id}" <c:if test="${type.id eq projects.purchaseType}">selected="selected"</c:if>>${type.name}</option>
                </c:forEach>
              </select>
    				</div>
    			</div>
    		</div>
    		
    		<c:if test="${typeName eq '4'}">
    		<div class="col-md-3 col-sm-4 col-xs-6 mb10">
    			<div class="row">
    				<div class="col-xs-4 h32 lh32 tr text-nowrapEl">采购机构：</div>
    				<div class="col-xs-8 f0 lh0">
    					<select name="purchaseDepId" id="purchaseDepId" class="w100p h32">
                <option selected="selected" value="">请选择</option>
                <c:forEach items="${orgByPosition}" var="org" >
                  <option  value="${org.id}" <c:if test="${org.id eq projects.purchaseDepId}">selected="selected"</c:if>>${org.shortName}</option>
                </c:forEach>
              </select>
    				</div>
    			</div>
    		</div>
    		</c:if>
    	</div>
      </div>
      
	    <div class="clear tc">
	    	<button class="btn mb0" type="submit">查询</button>
	      <button type="reset" class="btn mr0 mb0" onclick="clearSearch();">重置</button>
	    </div>
    <div class="clear"></div>
    </form>
    </div>
      <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
          <thead>
            <tr class="info">
              <th class="w50">序号</th>
              <th width="25%">项目名称</th>
              <th width="15%">项目编号</th>
              <th width="10%">采购方式</th>
              <th width="17%">创建时间</th>
              <th width="10%">项目状态</th>
              <th width="10%">采购机构</th>
              <th>项目负责人</th>
            </tr>
          </thead>
          <tbody id="tbody_id">
            <c:forEach items="${info.list}" var="obj" varStatus="vs">
              <tr class="pointer">
                <td class="tc w50">${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>
                <td class="tl" onclick="view('${obj.id}','1')">${obj.name}</td>
                <td class="tl" onclick="view('${obj.id}','1')">${obj.projectNumber}</td>
                <td class="tc" onclick="view('${obj.id}','1')">
                  <c:forEach items="${kind}" var="kind">
                    <c:if test="${kind.id eq obj.purchaseType}">${kind.name}</c:if>
                  </c:forEach>
                </td>
                <td class="tc" onclick="view('${obj.id}','1')">
                  <fmt:formatDate type='date' value='${obj.createAt}' pattern=" yyyy-MM-dd HH:mm:ss"/>
                </td>
                <td class="tl">
                  <c:forEach items="${status}" var="status">
                    <c:if test="${status.id eq obj.status}">${status.name}
                    <input type="hidden" value="${status.code}"/>
                    </c:if>
                  </c:forEach>
                </td>
                <td class="tl" onclick="view('${obj.id}','1')">
                	<c:forEach items="${orgByPosition}" var="org">
                    <c:if test="${org.id eq obj.purchaseDepId}">${org.shortName}</c:if>
                  </c:forEach>
                </td>
                <td class="tl" onclick="view('${obj.id}','1')">${obj.principal}</td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
      <div id="pagediv" align="right"></div>
    </div>
  </body>

</html>