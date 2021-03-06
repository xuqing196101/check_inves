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

      /** 全选全不选 */
      function selectAll() {
        var checklist = document.getElementsByName("chkItem");
        var checkAll = document.getElementById("checkAll");
        if(checkAll.checked) {
          for(var i = 0; i < checklist.length; i++) {
            checklist[i].checked = true;
          }
        } else {
          for(var j = 0; j < checklist.length; j++) {
            checklist[j].checked = false;
          }
        }
      }

      /** 单选 */
      function check() {
        var count = 0;
        var checklist = document.getElementsByName("chkItem");
        var checkAll = document.getElementById("checkAll");
        for(var i = 0; i < checklist.length; i++) {
          if(checklist[i].checked == false) {
            checkAll.checked = false;
            break;
          }
          for(var j = 0; j < checklist.length; j++) {
            if(checklist[j].checked == true) {
              checkAll.checked = true;
              count++;
            }
          }
        }
      }

      //查看明细
      function view(id) {
        window.location.href = "${pageContext.request.contextPath}/project/view.html?id=" + id;
      }


      //修改项目信息
      function edit() {
        var typeName = $("#orgn").val();
        if(typeName == "1"){
           var id = [];
	        $('input[name="chkItem"]:checked').each(function() {
	          id.push($(this).val());
	        });
	        var status = $("input[name='chkItem']:checked").parents("tr").find("td").eq(6).find("input").val();
	        status = $.trim(status);
	        var name = $("input[name='chkItem']:checked").parents("tr").find("td").eq(2).text();
	        name = $.trim(name);
	        var projectNumber = $("input[name='chkItem']:checked").parents("tr").find("td").eq(3).text();
	        projectNumber = $.trim(projectNumber);
	        if(id.length == 1) {
	        	if(status == "XMZC"){
	        		window.location.href = '${pageContext.request.contextPath}/project/add.html?id=' + id + '&name=' + name + '&projectNumber=' + projectNumber + '&ix=1';
	        	} else {
	        		layer.msg("只有暂存的项目可以修改!");
	        	}
          } else if(id.length > 1) {
	          layer.alert("只能选择一个", {
	            offset: ['222px', '730px'],
	            shade: 0.01,
	          });
	        } else {
	          layer.alert("请选择需要修改的项目", {
	            offset: ['222px', '730px'],
	            shade: 0.01,
	          });
	        }
        }else{
          layer.msg("只有当前采购机构的才能修改!");
        }
       
      }

      //重置
      function clearSearch() {
        $("#proName").attr("value", "");
        $("#projectNumber").attr("value", "");
        $("#status option:selected").removeAttr("selected");
      }
      
      
      function typeName(){
        var typeName = $("#orgn").val();
        if(typeName == "1"){
           window.location.href = "${pageContext.request.contextPath}/project/add.html";
        }else{
          layer.msg("只有当前采购机构的才能立项!");
        }
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
            <a href="javascript:jumppage('${pageContext.request.contextPath}/project/listProject.html')">立项管理</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    <!-- 录入采购计划开始-->
    <div class="container">
      <div class="headline-v2">
        <h2>立项列表</h2>
      </div>
      <!-- 项目戳开始 -->
      <h2 class="search_detail">
      <form action="${pageContext.request.contextPath}/project/listProject.html" id="form1" method="post" class="mb0">
      <div class="m_row_5">
      <div class="row">
        <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3">
          <div class="row">
            <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">项目名称：</div>
            <div class="col-xs-8 f0 lh0">
              <input type="hidden" name="page" id="page">
              <input type="text" name="name" id="proName" value="${projects.name }" class="w100p h32 f14 mb0">
            </div>
          </div>
        </div>
        
        <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3">
          <div class="row">
            <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">项目编号：</div>
            <div class="col-xs-8 f0 lh0">
              <input type="text" name="projectNumber" id="projectNumber" value="${projects.projectNumber }" class="w100p h32 f14 mb0">
            </div>
          </div>
        </div>
        
        <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3">
          <div class="row">
            <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">状态：</div>
            <div class="col-xs-8 f0 lh0">
              <select name="status" id="status" class="w100p h32 f14">
                <option selected="selected" value="">请选择</option>
                <c:forEach items="${status}" var="status" >
                  <option  value="${status.id}" <c:if test="${status.id eq projects.status}">selected="selected"</c:if>>${status.name}</option>
                </c:forEach>
              </select>
            </div>
          </div>
        </div>
        
        <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3">
          <div class="row">
            <div class="col-xs-12 f0">
              <button class="btn mb0 h32" type="submit">查询</button>
              <button type="reset" class="btn mb0 mr0 h32" onclick="clearSearch();">重置</button>
            </div>
          </div>
        </div>
      </div>
      </div>
    </form>
    </h2>
        <div class="col-md-12 pl20 mt10">
          <c:if test="${typeName eq '1'}">
	          <button class="btn btn-windows add" onclick="typeName()">立项</button>
	          <button class="btn btn-windows edit" onclick="edit();">修改</button>
          </c:if>
          <input type="hidden" id="orgn" value="${typeName}"/>
        </div>

      <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
          <thead>
            <tr class="info">
              <th class="w30">
                <input type="checkbox" id="checkAll" onclick="selectAll()" />
              </th>
              <th class="w50">序号</th>
              <th>项目名称</th>
              <th class="w180">项目编号</th>
              <th class="w100">采购方式</th>
              <th class="w120">创建时间</th>
              <th class="w180">项目状态</th>
              <th class="w90">项目负责人</th>
            </tr>
          </thead>
          <tbody id="tbody_id">
            <c:forEach items="${info.list}" var="obj" varStatus="vs">
              <tr class="pointer">
                <td class="tc w30">
                  <input type="checkbox" value="${obj.id }" name="chkItem" onclick="check()">
                </td>
                <td class="tc w50">${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>
                <td class="tl" onclick="view('${obj.id}')">${obj.name}</td>
                <td class="tc" onclick="view('${obj.id}')">${obj.projectNumber}</td>
                <td class="tc " onclick="view('${obj.id}')">
                  <c:forEach items="${kind}" var="kind">
                    <c:if test="${kind.id eq obj.purchaseType}">${kind.name}</c:if>
                  </c:forEach>
                </td>
                <td class="tc" onclick="view('${obj.id}')">
                  <fmt:formatDate type='date' value='${obj.createAt}' pattern=" yyyy-MM-dd HH:mm:ss " />
                </td>
                <td class="tc">
                  <c:forEach items="${status}" var="status">
                    <c:if test="${status.id eq obj.status}">${status.name}
                    <input type="hidden" value="${status.code}"/>
                    </c:if>
                  </c:forEach>
                </td>
                <td class="tc" onclick="view('${obj.id}')">${obj.projectContractor}</td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
      <div id="pagediv" align="right"></div>
    </div>
  </body>

</html>