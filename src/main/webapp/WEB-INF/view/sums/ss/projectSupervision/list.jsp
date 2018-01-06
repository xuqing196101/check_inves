<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script src="${pageContext.request.contextPath}/public/easyui/jquery.easyui.min.js"></script>
    <link href="${pageContext.request.contextPath}/public/easyui/themes/icon.css" media="screen" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/public/easyui/themes/default/easyui.css" media="screen" rel="stylesheet" type="text/css">
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

      /* $(function() {
      	$("input[name='linkman']").each(function(){
      		if($(this).val() == '1'){
      			$(this).prev().css("color","gray");
      		}
      	});
      }); */

      //重置
      function clearSearch() {
        $("#proName").attr("value", "");
        $("#projectNumber").attr("value", "");
        $("#status option:selected").removeAttr("selected");
        $("#purchaseType option:selected").removeAttr("selected");
      }
      
      
      
      function view(id){
        jumppage("${pageContext.request.contextPath}/projectSupervision/view.html?id="+id);
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
            <a href="javascript:void(0)">业务监管系统</a>
          </li>
          <li>
            <a href="javascript:void(0)">采购业务监督</a>
          </li>
          <li class="active">
            <a href="javascript:jumppage('${pageContext.request.contextPath}/projectSupervision/list.html')">采购项目监督</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    <!-- 录入采购计划开始-->
    <div class="container">
      <div class="headline-v2">
        <h2>项目监督列表</h2>
      </div>
      <!-- 项目戳开始 -->
      <h2 class="search_detail">
    <form action="${pageContext.request.contextPath}/projectSupervision/list.html" id="form1" method="post" class="mb0">
    <div class="m_row_5">
    <div class="row">
      <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
        <div class="row">
          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">项目名称：</div>
          <div class="col-xs-8 f0 lh0">
            <input type="hidden" name="page" id="page">
            <input type="text" name="name" id="proName" value="${project.name }" class="w100p h32 f14 mb0">
          </div>
        </div>
      </div>
      
      <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
        <div class="row">
          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">项目编号：</div>
          <div class="col-xs-8 f0 lh0">
            <input type="text" name="projectNumber" id="projectNumber" value="${project.projectNumber }" class="w100p h32 f14 mb0">
          </div>
        </div>
      </div>
      
      <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
        <div class="row">
          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">采购方式：</div>
          <div class="col-xs-8 f0 lh0">
            <select name="purchaseType" id="purchaseType" class="w100p h32 f14">
              <option selected="selected" value="">请选择</option>
              <c:forEach items="${kind}" var="kinds" >
                <option  value="${kinds.id}" <c:if test="${kinds.id eq project.purchaseType}">selected="selected"</c:if>>${kinds.name}</option>
              </c:forEach>
            </select>
          </div>
        </div>
      </div>
      
      <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
        <div class="row">
          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">状态：</div>
          <div class="col-xs-8 f0 lh0 m_select2">
            <select name="status" id="status" class="w100p h32 f14">
              <option selected="selected" value="">请选择</option>
              <c:forEach items="${status}" var="status" >
                <option  value="${status.id}" <c:if test="${status.id eq project.status}">selected="selected"</c:if>>${status.name}</option>
              </c:forEach>
            </select>
          </div>
        </div>
      </div>
    </div>
    </div>
    
    <div class="tc">
      <button class="btn mb0" type="submit">查询</button>
      <button type="reset" class="btn mb0 mr0" onclick="clearSearch();">重置</button>
    </div>
    </form>
    </h2>

      <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
          <thead>
            <tr class="info">
              
              <th class="w50">序号</th>
              <th width="25%">项目名称</th>
              <th width="18%">项目编号</th>
              <th width="15%">采购机构名称</th>
              <th width="10%">采购方式</th>
              <th width="15%">创建时间</th>
              <th>创建人</th>
              <!-- <th>项目状态</th> -->
            </tr>
          </thead>
          <tbody id="tbody_id">
            <c:forEach items="${info.list}" var="obj" varStatus="vs">
              <tr class="pointer <c:if test="${obj.status eq yzz}">red</c:if>">
                
                <td class="tc w50">${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>
                <td class="tl">
                  <a href="javascript:void(0)" <c:if test="${obj.status eq yzz}">class="red"</c:if> onclick="view('${obj.id}');">${obj.name}</a>
                  <input type="hidden" name="linkman" value="${obj.linkman}"/>
                </td>
                <td class="tl">${obj.projectNumber}</td>
                <td class="tl">${obj.purchaseDepId}</td>
                <td class="tc">
                  <c:forEach items="${kind}" var="kind">
                    <c:if test="${kind.id eq obj.purchaseType}">${kind.name}</c:if>
                  </c:forEach>
                </td>
                <td class="tc">
                  <fmt:formatDate type='date' value='${obj.createAt}' pattern=" yyyy-MM-dd HH:mm:ss " />
                </td>
                <td class="tl">${obj.appointMan}</td>
                <%-- <td>
                  <c:forEach items="${status}" var="status">
                    <c:if test="${status.id == obj.status}">${status.name}
                    <input type="hidden" value="${status.code}"/>
                    </c:if>
                  </c:forEach>
                </td> --%>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
      <div id="pagediv" align="right"></div>
    </div>
  </body>

</html>