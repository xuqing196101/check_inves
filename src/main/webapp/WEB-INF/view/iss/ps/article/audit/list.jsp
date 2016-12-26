<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <link href="${pageContext.request.contextPath }/public/select2/css/select2.css" rel="stylesheet" />
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath }/public/select2/js/select2.js"></script>
    <script type="text/javascript">
      $(function() {
        laypage({
          cont: $("#pagediv"),
          pages: "${list.pages}",
          skin: '#2c9fA6',
          skip: true,
          total: "${list.total}",
          startRow: "${list.startRow}",
          endRow: "${list.endRow}",
          groups: "${list.pages}" >= 5 ? 5 : "${list.pages}",
          curr: function() {
            var page = location.search.match(/page=(\d+)/);
            return page ? page[1] : 1;
          }(),
          jump: function(e, first) { 
            if(!first) {
              location.href = '${ pageContext.request.contextPath }/article/auditlist.html?status=1&page=' + e.curr;
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

      function view(id) {
        window.location.href = "${pageContext.request.contextPath }/article/auditInfo.html?id=" + id;
      }

      function audit() {
        var id = [];
        $('input[name="chkItem"]:checked').each(function() {
          id.push($(this).val());
        });
        var audit = $("input[name='chkItem']:checked").parents("tr").find("td").eq(6).text();
        if(id.length == 1) {
          if($.trim(audit) == "待发布") {
            window.location.href = "${pageContext.request.contextPath }/article/auditInfo.html?id=" + id;
          } else {
            layer.alert("请选择待发布信息", {
              offset: ['180px', '200px'],
              shade: 0.01,
            });

          }
        } else if(id.length > 1) {
          layer.alert("只能选择一个", {
            offset: ['222px', '390px'],
            shade: 0.01
          });
        } else {
          layer.alert("请选择需要审核的信息", {
            offset: ['222px', '390px'],
            shade: 0.01
          });
        }
      }

      $(function() {
        $.ajax({
          contentType: "application/json;charset=UTF-8",
          url: "${pageContext.request.contextPath }/article/aritcleTypeParentId.do?parentId=0",
          type: "POST",
          dataType: "json",
          success: function(articleTypes) {
            if(articleTypes) {
              $("#articleTypes").append("<option></option>");
              $.each(articleTypes, function(i, articleType) {
                if(articleType.name != null && articleType.name != '') {
                  $("#articleTypes").append("<option value=" + articleType.id + ">" + articleType.name + "</option>");
                }
              });
            }
            $("#articleTypes").select2();
            $("#articleTypes").select2("val", "${article.articleType.id }");
          }
        });
      })

      function search() {
        var kname = $("#kname").val();
        var parkId = $("#parkId  option:selected").val();
        location.href = "${ pageContext.request.contextPath }/article/serch.html?kname=" + kname + "&articlestatus=1";

      }

      function resetQuery() {
        $("#form1").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
        $("#articleTypes").select2("val", "");
      }

      $(function() {
        $("#articleTypes").select2("val", "${article.articleType.id}");
        $("#range").val("${articlesRange}");
        $("#status").val("${articlesStatus}");
      })

      function edit() {
        var id = [];
        $('input[name="chkItem"]:checked').each(function() {
          id.push($(this).val());
        });
        if(id.length == 1) {
          window.location.href = "${pageContext.request.contextPath }/article/auditEdit.html?id=" + id;
        } else if(id.length > 1) {
          layer.alert("只能选择一个", {
            offset: ['222px', '390px'],
            shade: 0.01
          });
        } else {
          layer.alert("请选择需要修改的信息", {
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
            <a href="${ pageContext.request.contextPath }/article/getAll.html">信息管理</a>
          </li>
          <li>
            <a href="javascript:void(0)">审核信息管理</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <div class="container">
      <div class="headline-v2">
        <h2>审核信息列表</h2>
      </div>

      <div class="search_detail">
        <form id="form1" action="${pageContext.request.contextPath }/article/auditlist.html?status=1" method="post" class="mb0">
          <ul class="demand_list">
            <li>
              <label class="fl">信息标题：</label>
              <span>
          <input type="text" id="name" name="name" value="${articleName }"/>
        </span>
            </li>
            <li>
              <label class="fl">信息栏目：</label>
              <span class="fl mt5">
        <div class="w200">
          <select id="articleTypes" name="articleType.id" class="w200" >
            </select>
          </div>
            </span>
            </li>
            <li>
              <label class="fl">发布范围：</label>
              <span>
              <select id ="range" name="range" class="w100"  >
                <option></option>
                <option value="0">内网</option>
                <option value="1">外网</option>
                <option value="2">内网/外网</option>
               </select>
           </span>
            </li>
            <button type="submit" class="btn">查询</button>
            <button type="button" class="btn" onclick="resetQuery()">重置</button>
          </ul>
          <div class="clear"></div>
        </form>
      </div>

      <input type="hidden" id="depid" name="depid">

      <div class="col-md-12 pl20 mt10">
        <button class="btn btn-windows check" type="button" onclick="audit()">发布</button>
        <button class="btn btn-windows edit" type="button" onclick="edit()">编辑</button>
      </div>

      <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
          <thead>
            <tr>
              <th class="info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
              <th class="info">序号</th>
              <th class="info">信息标题</th>
              <th class="info">发布范围</th>
              <th class="info">发布时间</th>
              <th class="info">信息栏目</th>
              <th class="info">发布状态</th>
              <th class="info">发布依据</th>
            </tr>
          </thead>
          <c:forEach items="${list.list}" var="article" varStatus="vs">
            <tr class="pointer">
              <td class="tc"><input onclick="check()" type="checkbox" name="chkItem" value="${article.id }" /></td>
              <td class="tc" onclick="view('${article.id }')">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
              <c:if test="${fn:length(article.name)>30}">
                <td onclick="view('${article.id }')" onmouseover="titleMouseOver('${article.name}',this)" onmouseout="titleMouseOut()">${fn:substring(article.name,0,30)}...</td>
              </c:if>
              <c:if test="${fn:length(article.name)<=30}">
                <td onclick="view('${article.id }')">${article.name }</td>
              </c:if>
              <td class="tc" onclick="view('${article.id }')">
                <c:if test="${article.range=='0' }">
                  内网
                </c:if>
                <c:if test="${article.range=='1' }">
                  外网
                </c:if>
                <c:if test="${article.range=='2' }">
                  内网/外网
                </c:if>
              </td>
              <td class="tc" onclick="view('${article.id }')">
                <fmt:formatDate value='${article.publishedAt }' pattern="yyyy年MM月dd日   HH:mm:ss" />
              </td>
              <td class="tc" onclick="view('${article.id }')">${article.articleType.name }</td>
              <td class="tc">
                <c:if test="${article.status=='1' }">
                  <input type="hidden" name="status" value="${article.status }">待发布
                </c:if>
                <c:if test="${article.status=='2' }">
                  <input type="hidden" name="status" value="${article.status }">发布
                </c:if>
                <c:if test="${article.status=='3' }">
                  <input type="hidden" name="status" value="${article.status }">退回
                </c:if>
                <c:if test="${article.status=='4' }">
                  <input type="hidden" name="status" value="${article.status }">撤回
                </c:if>
              </td>
              <td>
                <u:show showId="artice_secret_show" delete="false" businessId="${article.id}" sysKey="${secretSysKey}" typeId="${secretTypeId }" />
              </td>
            </tr>
          </c:forEach>
        </table>
      </div>
      <div id="pagediv" align="right"></div>
    </div>
  </body>

</html>