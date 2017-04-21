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

      //进入实施页面
      var flag = true;

      function start() {
        var id = [];
        $('input[name="chkItem"]:checked').each(function() {
          id.push($(this).val());
        });
        var status = $("input[name='chkItem']:checked").parents("tr").find("td").eq(6).find("input").val();
        status = $.trim(status);
        var currPage = ${info.pageNum};
        if(id.length == 1) {
          /* if(status == "YLX_DFB") {
             $.ajax({
              url: "${pageContext.request.contextPath}/project/viewPackages.html",
              data: "id=" + id,
              type: "post",
              dataType: "json",
              success: function(result) {
                if(result == 0) {
                  layer.open({
                    type: 2, //page层
                    area: ['800px', '500px'],
                    title: '请上传项目批文',
                    shade: 0.01, //遮罩透明度
                    moveType: 1, //拖拽风格，0是默认，1是传统拖动
                    shift: 1, //0-6的动画形式，-1不开启
                    shadeClose: true,
                    content: '${pageContext.request.contextPath}/project/startProject.html?id=' + id,
                  });
                } else if(result == 1) {
                  layer.alert("项目中有明细尚未分包,请进修改页面进行分包", {
                    offset: ['30%', '40%'],
                  });
                  $(".layui-layer-shade").remove();
                }
              }
            }); 
          }
           */
          /* if(status == "YJLX") {
             layer.open({
                    type: 2, //page层
                    area: ['800px', '500px'],
                    title: '请上传项目批文',
                    shade: 0.01, //遮罩透明度
                    moveType: 1, //拖拽风格，0是默认，1是传统拖动
                    shift: 1, //0-6的动画形式，-1不开启
                    shadeClose: true,
                    content: '${pageContext.request.contextPath}/project/start.html?id=' + id,
                  });
          }else */ 
          if(status == "YJFB") {
            layer.alert("项目已废标", {
              offset: ['30%', '40%'],
            });
          }else if(status == "YQX") {
            layer.alert("项目已取消", {
              offset: ['30%', '40%'],
            });
          } else {
            window.location.href = "${pageContext.request.contextPath}/project/excute.html?id=" + id + "&page=" + currPage;
          }
        } else if(id.length > 1) {
          layer.alert("只能选择一个", {
            offset: ['222px', '390px'],
            shade: 0.01,
          });
        } else {
          layer.alert("请选择需要启动的项目", {
            offset: ['222px', '390px'],
            shade: 0.01,
          });
        }
      }

      //项目分包
      function subPackage() {
        var status = "";
        var count = 0;
        var chkItem = document.getElementsByName("chkItem");
        var str = "";
        for(var i = 0; i < chkItem.length; i++) {
          if(chkItem[i].checked == true) {
            count++;
          }
        }
        if(count > 1) {
          layer.alert("只能选择一项", {
            offset: ['222px', '390px'],
          });
          $(".layui-layer-shade").remove();
          return;
        } else if(count == 0) {
          layer.alert("请先选择一项", {
            offset: ['222px', '390px'],
          });
          $(".layui-layer-shade").remove();
          return;
        } else {
          for(var i = 0; i < chkItem.length; i++) {
            if(chkItem[i].checked == true) {
              str = chkItem[i].value;
              status = $(chkItem[i]).prev().val();
            }
          }
          if(status == 3) {
            window.location.href = "${pageContext.request.contextPath}/project/subPackage.html?id=" + str;
          } else if(status == 1) {
            layer.alert("项目在实施中，不可进行分包操作，请重新选择", {
              offset: ['222px', '390px'],
            });
            $(".layui-layer-shade").remove();
            return;
          } else if(status == 2) {
            layer.alert("项目已完成，不可进行分包操作，请重新选择", {
              offset: ['222px', '390px'],
            });
            $(".layui-layer-shade").remove();
            return;
          }
        }
      }

      //修改项目信息
      function edit() {
        var id = [];
        $('input[name="chkItem"]:checked').each(function() {
          id.push($(this).val());
        });
        var status = $("input[name='chkItem']:checked").parents("tr").find("td").eq(6).find("input").val();
        status = $.trim(status);
        if(id.length == 1) {
          window.location.href = '${pageContext.request.contextPath}/project/edit.html?id=' + id;
          /* if(status == "YLX_DFB" || status == "YFB_DSS") {
            
          }else{
            layer.alert("实施中的项目不能修改", {
              offset: ['222px', '730px'],
              shade: 0.01,
            });
          } */
        } else if(id.length > 1) {
          layer.alert("只能选择一个", {
            offset: ['222px', '730px'],
            shade: 0.01,
          });
        } else {
          layer.alert("请选择需要修改的任务", {
            offset: ['222px', '730px'],
            shade: 0.01,
          });
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
          <li>
            <a href="javascript:void(0)">首页</a>
          </li>
          <li>
            <a href="javascript:void(0)">保障作业系统</a>
          </li>
          <li>
            <a href="javascript:void(0)">采购项目管理</a>
          </li>
          <li class="active">
            <a href="javascript:void(0)">项目实施</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    <!-- 录入采购计划开始-->
    <div class="container">
      <div class="headline-v2">
        <h2>实施列表</h2>
      </div>
      <!-- 项目戳开始 -->
      <h2 class="search_detail">
    <form action="${pageContext.request.contextPath}/project/list.html" id="form1" method="post" class="mb0">
      <ul class="demand_list">
      <li>
        <label class="fl">项目名称： </label>
        <span>
          <input type="hidden" name="page" id="page">
          <input type="text" name="name" id="proName" value="${projects.name }" /> 
        </span>
      </li>
      <li>
        <label class="fl">项目编号：</label> 
        <span>
          <input type="text" name="projectNumber" id="projectNumber" value="${projects.projectNumber }" /> 
        </span>
      </li>
      <li>
        <label class="fl">状态：</label>
            <span class="">
              <select name="status" id="status">
                <option selected="selected" value="">请选择</option>
                <c:forEach items="${status}" var="status" >
                  <option  value="${status.id}" <c:if test="${status.id eq projects.status}">selected="selected"</c:if>>${status.name}</option>
                </c:forEach>
              </select>
            </span>
      </li>
      <button class="btn fl mt1" type="submit">查询</button>
        <button type="reset" class="btn fl mt1" onclick="clearSearch();">重置</button>
    </ul>
    <div class="clear"></div>
    </form>
    </h2>
      <c:if test="${admin!=1 }">
        <div class="col-md-12 pl20 mt10">
          <button class="btn btn-windows apply" onclick="start();" type="button">实施</button>
        </div>
      </c:if>

      <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
          <thead>
            <tr class="info">
              <th class="w30">
                <input type="checkbox" id="checkAll" onclick="selectAll()" />
              </th>
              <th class="w50">序号</th>
              <th>项目名称</th>
              <th>项目编号</th>
              <th>采购方式</th>
              <th>创建时间</th>
              <th>项目状态</th>
              <th>项目负责人</th>
            </tr>
          </thead>
          <tbody id="tbody_id">
            <c:forEach items="${info.list}" var="obj" varStatus="vs">
              <tr class="pointer">
                <td class="tc w30">
                  <input type="checkbox" value="${obj.id }" name="chkItem" onclick="check()">
                </td>
                <td class="tc w50">${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>
                <td class="tl pl20" onclick="view('${obj.id}')">${obj.name}</td>
                <td class="tl pl20" onclick="view('${obj.id}')">${obj.projectNumber}</td>
                <td class="tc " onclick="view('${obj.id}')">
                  <c:forEach items="${kind}" var="kind">
                    <c:if test="${kind.id == obj.purchaseType}">${kind.name}</c:if>
                  </c:forEach>
                </td>
                <td class="tl pl20" onclick="view('${obj.id}')">
                  <fmt:formatDate type='date' value='${obj.createAt}' pattern=" yyyy-MM-dd HH:mm:ss " />
                </td>
                <td class="tc">
                  <c:forEach items="${status}" var="status">
                    <c:if test="${status.id == obj.status}">${status.name}
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