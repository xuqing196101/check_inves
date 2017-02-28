<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
  	<style type="text/css">
  		.table th, .table td { 
		text-align: center;
		vertical-align: middle!important;
	}
  	</style>
    <link href="${pageContext.request.contextPath }/public/select2/css/select2.css" rel="stylesheet" />
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <%@ include file="/WEB-INF/view/common/webupload.jsp"%>
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath }/public/select2/js/select2.js"></script>
    <script type="text/javascript">
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

      function audit() {
        var id = [];
        var status = "";
        var data = "";
        $('input[name="chkItem"]:checked').each(function() {
          id.push($(this).val());
          data = id[0].split(",");
          status=$(this).parent().next().text();
        });
        if(id.length == 1) {
        	<!-- 清除首页缓存 -->
   			layer.confirm('确认要清空首页缓存吗？', {
   				btn : [ '是', '否' ]
   			//按钮
   			}, function() {
   				$.ajax({
   				    url: "${pageContext.request.contextPath}/cacheManage/clearStringCache.do?cacheKey="+data[0]+"&&cacheType="+data[1],
   				    type: "POST",
   				    dataType: "json",
   				    success: function(data) {
   				    	// 成功后提示
   				    	layer.confirm(data.data,{
							btn:['确定']
						},function(){
							location.reload();
							}
						)
   				    }
   				});
   			}, function() {
   				layer.close();
   			});
        } else if(id.length > 1) {
          layer.alert("只能选择一个", {
            offset: ['222px', '390px'],
            shade: 0.01
          });
        } else {
          layer.alert("请选择需要删除缓存的信息", {
            offset: ['222px', '390px'],
            shade: 0.01
          });
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
            <a href="javascript:void(0)"> 首页</a>
          </li>
          <li>
            <a href="javascript:void(0)">信息服务</a>
          </li>
          <li>
            <a href="javascript:void(0)">缓存管理</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <div class="container">
      <div class="headline-v2">
        <h2>缓存列表</h2>
      </div>


      <input type="hidden" id="depid" name="depid">

      <div class="col-md-12 pl20 mt10">
        <button class="btn btn-windows edit" type="button" onclick="audit()">清空缓存</button>
      </div>

      <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
          <thead>
            <tr>
              <th class="info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
              <th class="tnone"></th>
              <th class="info">序号</th>
              <th class="info">缓存名称</th>
              <th class="info">缓存类型</th>
            </tr>
          </thead>
          <c:forEach items="${cacheMap}" var="cacheMap" varStatus="vs">
           	<tr>
		        <td class="tc w30"><input onclick="check()" type="checkbox" name="chkItem" value="${cacheMap.key},${cacheMap.value}" /></td>
           		<td class="tl pl20">${vs.index + 1}</td>
	          	<td class="tl pl20" >${cacheMap.key}</td>
	          	<td class="tl pl20" >${cacheMap.value}</td>
           	</tr>
          </c:forEach>
        </table>
      </div>
      <div align="right"><font>总计 ${cacheMap.size()} 条</font></div>
    </div>
  </body>

</html>