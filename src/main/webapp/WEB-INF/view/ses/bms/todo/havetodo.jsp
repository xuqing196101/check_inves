<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE HTML>
<html>

  <head>

  

    <title>已办事项</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplieragents.css" type="text/css">
<script src="${pageContext.request.contextPath}/public/laypage-v1.3/laypage/laypage.js"></script>
<script src="${pageContext.request.contextPath}/public/backend/js/jquery.min.js"></script>
<link href="${pageContext.request.contextPath}/public/backend/images/favicon.ico"  rel="shortcut icon" type="image/x-icon" />
<link href="${pageContext.request.contextPath}/public/backend/css/bootstrap.min.css" media="screen" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/public/backend/css/common.css" media="screen" rel="stylesheet" type="text/css">  
<link href="${pageContext.request.contextPath}/public/backend/css/unify.css" media="screen" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/public/backend/css/global.css" media="screen" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/public/backend/css/btn.css" media="screen" rel="stylesheet" type="text/css"> 
  
  </head>
  <script type="text/javascript">
    $(function() {
      laypage({
        cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
        pages: "${list.pages}", //总页数
        skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
        skip: true, //是否开启跳页
        total: "${list.total}",
        startRow: "${list.startRow}",
        endRow: "${list.endRow}",
        groups: "${list.pages}" >= 5 ? 5 : "${list.pages}", //连续显示分页数
        curr: function() { //通过url获取当前页，也可以同上（pages）方式获取

          return "${list.pageNum}";
        }(),
        jump: function(e, first) { //触发分页后的回调
          if(!first) { //一定要加此判断，否则初始时会无限刷新
            $("#pages").val(e.curr);
            $("form:first").submit();
          }
        }
      });
      var ut = "${todos.undoType}";
      $("#undoType").find("option[value='" + ut + "']").attr("selected", true);

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

/*     function view(url) {
      $("#a").attr("href", url);
      var el = document.getElementById('a');
      el.click(); //触发打开事件

    } */

    function del() {
      var ids = [];
      $('input[name="chkItem"]:checked').each(function() {
        ids.push($(this).val());
      });
      if(ids.length > 0) {
        layer.confirm('您确定要删除吗?', {
          title: '提示',
          offset: ['222px', '360px'],
          shade: 0.01
        }, function(index) {
          layer.close(index);
          window.location.href = "${pageContext.request.contextPath}/StationMessage/deleteSoftSMIsDelete.do?ids=" + ids;
        });
      } else {
        layer.alert("请选择要删除的用户", {
          offset: ['222px', '390px'],
          shade: 0.01
        });
      }
    }

    function resetQuery() {
      $("#form2").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
      $("#undoType").find("option[value='']").attr("selected", true);
    }
  </script>

  <body>

    <div id="con_one_2" class="dnonev">
      <div id="Accordion1" class="Accordion" tabindex="1">
        <div class="AccordionPanel">
          <div class="search_detail mt10 mb10 ml0">
            <form action="${pageContext.request.contextPath}/todo/havetodo.do" method="post" id="form2" class="mb0" target="">
              <input id="pages" name="pages" type="hidden">
              <ul class="demand_list">
                <li class="fl"><label class="fl">标题：</label><span><input
                                                                    type="text" id="topic" class="w200" name="name" value="${todos.name}" /></span></li>
                <li class="fl"><label class="fl">待办类型：</label>
                  <span>
                  <select class="w200 " id="undoType" name="undoType">
                    <option value="">请选择</option>
                    <option value="1">供应商待办</option>
                    <option value="2">专家待办</option>
                    <option value="3">项目待办</option>
                  </select>
                  </span>
                </li>
                <input type="submit" value="查询" class="btn" />
                <input type="button" onclick="resetQuery();" class="btn" value="重置" />
              </ul>
              <div class="clear"></div>
            </form>
          </div>
          <table class="table table-striped table-bordered table-hover">
            <thead>
              <tr>
                <th class="info w50">序号</th>
                <th class="info">标题</th>
                <th class="info w100">待办类型</th>
                <th class="info w100">发送人</th>
                <th class="info w150">创建时间</th> 
              </tr>
            </thead>
            <c:forEach items="${list.list}" var="agents" varStatus="vs">
              <tr class="cursor" >
                <td class="tc">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
                <td class="tl pl20">
                  <c:choose>
                    <c:when test="${fn:length(agents.name) > 20}">
                     ${fn:substring(agents.name, 0, 20)}......
                    </c:when>
                    <c:otherwise>
                     ${agents.name }
                    </c:otherwise>
                  </c:choose>
                </td>
                <td class="tc">
                  <c:if test="${agents.undoType==1}">
                                                         供应商待办
                  </c:if>
                  <c:if test="${agents.undoType==2}">
                                                         专家待办
                  </c:if>
                </td>
                <td class="tc">${agents.senderName}</td>
                <td class="tc">
                  <fmt:formatDate value="${agents.createdAt}" pattern="YYY-MM-dd HH:mm:ss" />
                </td>
              </tr>
            </c:forEach>
          </table>
          <div id="pagediv" align="right"></div>
        </div>

      </div>
    </div>

  </body>

</html>