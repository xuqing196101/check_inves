<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script type="text/javascript">
      /** 分页  */
      $(function() {
        laypage({
          cont: $("#pageDiv"), //容器。值支持id名、原生dom对象，jquery对象,
          pages: "${info.pages}", //总页数
          skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
          skip: true, //是否开启跳页
          total: "${info.total}",
          startRow: "${info.startRow}",
          endRow: "${info.endRow}",
          groups: "${info.pages}" >= 5 ? 5 : "${info.pages}", //连续显示分页数
          curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
            var page = location.search.match(/page=(\d+)/);
            if(page == null) {
              page = {};
              page[0] = "${info.pageNum}";
              page[1] = "${info.pageNum}";
            }
            return page ? page[1] : 1;
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

      /** 受领任务 */
      function start() {
        var id = [];
        $('input[name="chkItem"]:checked').each(function() {
          id.push($(this).val());
        });
        var status = $("input[name='chkItem']:checked").parents("tr").find("td").eq(5).text();
        status = $.trim(status);
        var taskNature = $("input[name='chkItem']:checked").parents("tr").find("td").eq(6).text();
        taskNature = $.trim(taskNature);
        if(id.length == 1) {
          if(status == "已受领"){
            layer.alert("任务已经受领", {shade : 0.01});
          }else if(status == "已取消"){
            layer.alert("已取消的任务不能受领，请重新选择", {shade : 0.01});
          } else {
            if(taskNature == "正常任务"){
              $.ajax({
                url: "${pageContext.request.contextPath}/task/comparison.html",
                data: "id=" + id,
                type: "post",
                success: function(result) {
                  if(result == "ok") {
                    layer.confirm('您是否要引用预研生成正式项目?',{
                      shade: 0.01,
                      btn: ['是', '否'],
                    },function() {
                      window.location.href = "${pageContext.request.contextPath}/task/quote.html?taskId="+id;
                    },function() {
                      accept(id);
                    });
                  } else {
                    accept(id);
                  }
                }
              });
            } else {
            	layer.confirm('您确定要受领吗?', {
			          title: '提示',
			          shade: 0.01
			        },function(index) {
			          layer.close(index);
			          $.ajax({
			            url: "${pageContext.request.contextPath}/task/startTask.do",
			            data: "id=" + id,
			            type: "post",
			            dateType: "json",
			            success: function() {
			              layer.open({
			            		type: 2, //page层
			                area: ['800px', '500px'],
			                title: '请上传项目批文',
			                shade: 0.01, //遮罩透明度
			                moveType: 1, //拖拽风格，0是默认，1是传统拖动
			                shift: 1, //0-6的动画形式，-1不开启
			                shadeClose: true,
			                content: '${pageContext.request.contextPath}/advancedProject/startProject.html?id=' + id,
			              });
			            },
			            error: function() {
			              layer.msg("受领失败");
			            }
			          });
			         });
            	
            }
          }
        }else if(id.length>1){
          layer.alert("只能选择一项任务", {
            shade : 0.01
          });
        }else {
          layer.alert("请选择要受领的任务", {
            shade : 0.01
          });
        }
      }
      
      function accept(id){
        layer.confirm('您确定要受领吗?', {
          title: '提示',
          shade: 0.01
        },function(index) {
          layer.close(index);
          $.ajax({
            url: "${pageContext.request.contextPath}/task/startTask.do",
            data: "id=" + id,
            type: "post",
            dateType: "json",
            success: function() {
              layer.msg("受领成功");
              window.setTimeout(function() {location.reload();}, 1000);
            },
            error: function() {
              layer.msg("受领失败");
            }
          });
         });
      }


      /** 查看任务 */
      function viewd(id) {
        window.location.href = "${pageContext.request.contextPath}/task/view.html?id=" + id;
      }

      /** 重置任务 */
      function clearSearch() {
        $("#name").attr("value", "");
        $("#documentNumber").attr("value", "");
        $("#purchaseId").attr("value", "");
        //还原select下拉列表只需要这一句
        $("#status option:selected").removeAttr("selected");
        $("#taskNature option:selected").removeAttr("selected");
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
            <a href="javascript:void(0)">采购任务管理</a>
          </li>
          <li class="active">
            <a href="javascript:jumppage('${pageContext.request.contextPath}/task/list.html')">采购任务受领</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    <!-- 录入采购计划开始-->
    <div class="container">
      <div class="headline-v2">
        <h2>任务列表</h2>
      </div>
      <!-- 项目戳开始 -->
      <h2 class="search_detail">
        <form id="form1" action="${pageContext.request.contextPath}/task/list.html" method="post" class="mb0">
        <input type="hidden" name="page" id="page">
        <div class="m_row_5">
        <div class="row">
          <div class="col-xs-2 col-sm-4 col-md-4 col-lg-3 mb10">
            <div class="row">
              <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">采购任务名称：</div>
              <div class="col-xs-8 f0 lh0">
                <input type="text" name="name" id="name" value="${task.name}" class="w100p h32 f14 mb0">
              </div>
            </div>
          </div>
          
          <div class="col-xs-2 col-sm-4 col-md-4 col-lg-3 mb10">
            <div class="row">
              <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">采购任务文号：</div>
              <div class="col-xs-8 f0 lh0">
                <input type="text" name="documentNumber" id="documentNumber" value="${task.documentNumber }" class="w100p h32 f14 mb0">
              </div>
            </div>
          </div>
          
          <div class="col-xs-2 col-sm-4 col-md-4 col-lg-3 mb10">
            <div class="row">
              <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">状态：</div>
              <div class="col-xs-8 f0 lh0">
                <select  name="status" id="status" class="w100p h32 f14">
                  <option selected="selected" value="">请选择</option>
                  <option value="0" <c:if test="${'0' eq task.status}">selected="selected"</c:if>>未受领</option>
                  <option value="1" <c:if test="${'1' eq task.status}">selected="selected"</c:if>>已受领</option>
                  <option value="2" <c:if test="${'2' eq task.status}">selected="selected"</c:if>>已取消</option>
                </select>
              </div>
            </div>
          </div>
          
          <div class="col-xs-2 col-sm-4 col-md-4 col-lg-3 mb10">
            <div class="row">
              <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">任务性质：</div>
              <div class="col-xs-8 f0 lh0">
                <select  name="taskNature" id="taskNature" class="w100p h32 f14">
                  <option selected="selected" value="">请选择</option>
                  <option value="1" <c:if test="${'1' eq task.taskNature}">selected="selected"</c:if>>预研任务</option>
                  <option value="0" <c:if test="${'0' eq task.taskNature}">selected="selected"</c:if>>正常任务</option>
                </select>
              </div>
            </div>
          </div>
        </div>
        </div>
        
        <div class="tc">
          <button class="btn mb0" type="submit">查询</button>
          <button class="btn mb0 mr0" type="reset" onclick="clearSearch()">重置</button>
        </div>
        </form>
      </h2>
      <c:if test="${admin!=1 }">
        <div class="col-md-12 pl20 mt10">
        	<c:if test="${auth eq 'show'}">
	          <button class="btn btn-windows git" onclick="start()">受领</button>
	        </c:if>
        </div>
      </c:if>
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
            <c:if test="${orgId eq obj.purchaseId}"></c:if>
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
                <c:if test="${'0' eq obj.status}"> 未受领</c:if>
                <c:if test="${'1' eq obj.status}">已受领</c:if>
                <c:if test="${'2' eq obj.status}">已取消</c:if>
              </td>
              <td class="tc">
                <c:if test="${'1' eq obj.taskNature}">
                  <span class="label rounded-2x label-orange">预研任务</span>
                </c:if>
                <c:if test="${'0' eq obj.taskNature}">
                  <span class="label rounded-2x label-u">正常任务</span>
                </c:if>
              </td>
              <td class="tc"><fmt:formatDate value="${obj.giveTime }" /></td>
            </tr>
          </c:forEach>
        </table>
      </div>
      <div id="pageDiv" align="right"></div>
    </div>
  </body>

</html>