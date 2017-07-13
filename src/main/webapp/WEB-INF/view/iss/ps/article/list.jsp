<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <link href="${pageContext.request.contextPath }/public/select2/css/select2.css" rel="stylesheet" />
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script type="text/javascript">
      $(function() {
    	var saveNews="${saveNews}";
    	if(saveNews=='1'){
    		layer.msg("保存成功", {
                offset: '222px',
                shade: 0.01
              });
    	}
    	if(saveNews=='0'){
    		layer.msg("提交成功", {
                offset: '222px',
                shade: 0.01
              });
    	}
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
              var articleTypeId = "${article.articleType.id}";
              var range = $("#range").val();
              var status = $("#status").val();
              var name = "${articleName}";
              window.location.href = "${pageContext.request.contextPath }/article/serch.html?page=" + e.curr + "&articleTypeId=" + articleTypeId + "&range=" + range + "&status=" + status + "&name=" + name;
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
      	var status = $("#status").val();
      	var curpage = "${list.pageNum}";
      	var articleTypeId = $("#articleTypes").val();
      	var range = $("#range").val();
      	var title = $("#name").val();
        window.location.href = "${ pageContext.request.contextPath }/article/view.html?id=" + id +"&status=" + status +"&curpage=" +curpage +"&range=" +range +"&articleTypeId=" +articleTypeId + "&title=" +title;
      }

      function add() {
	  	window.location.href = "${ pageContext.request.contextPath }/article/add.html";
      }

      function find() {
        var id = [];
        $('input[name="chkItem"]:checked').each(function() {
          id.push($(this).val());
        });
        if(id.length == 1) {

          window.location.href = "${ pageContext.request.contextPath }/article/view.html?id=" + id;
        } else if(id.length > 1) {
          layer.alert("只能选择一个", {
            offset: ['222px', '390px'],
            shade: 0.01
          });
        } else {
          layer.alert("请选择需要查看的信息", {
            offset: ['222px', '390px'],
            shade: 0.01
          });
        }
      }

      function edit() {
        var id = [];
        $('input[name="chkItem"]:checked').each(function() {
          id.push($(this).val());
        });
        if(id.length == 1) {
          window.location.href = "${ pageContext.request.contextPath }/article/edit.html?id=" + id;
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

      function del() {
        var ids = [];
        var status = [];
        var flag = true;
        $('input[name="chkItem"]:checked').each(function() {
          ids.push($(this).val());
          status.push($(this).parent().next().text());
        });
        if(ids.length > 0) {
        for(var i=0;i<status.length;i++){
        	if(status[i]=='1'||status=='2' ||status=='4'){
        		flag=false;
        	}
        }
        if(flag){
          layer.confirm('您确定要删除吗?', {
            title: '提示',
            offset: ['222px', '360px'],
            shade: 0.01
          }, function(index) {
            layer.close(index);
            window.location.href = "${ pageContext.request.contextPath }/article/delete.html?ids=" + ids;
          });
        }else{
        	layer.alert("只可删除暂存或者退回的信息", {
                offset: ['180px', '200px'],
                shade: 0.01,
              });
        }
        } else {
          layer.alert("请选择要删除的信息", {
            offset: ['222px', '390px'],
            shade: 0.01
          });
        }
      }

      function audit() {
        window.location.href = "${pageContext.request.contextPath }/article/auditlist.html?status=1";
      }

      function sub() {
        var ids = [];
        var status=[];
        var flag=true;
        $('input[name="chkItem"]:checked').each(function() {
          ids.push($(this).val());
          status.push($(this).parent().next().text());
        });
        if(ids.length > 0) {
       	  for(var i=0;i<status.length;i++){
       		  if(status[i]=='1' || status[i]=='2' || status[i]=='4'){
       			  flag=false;
       		  }
       	  }
       	  if(flag){
          layer.confirm('您确定要提交吗?', {
            title: '提示',
            offset: ['222px', '360px'],
            shade: 0.01
          }, function(index) {
            layer.close(index);
            window.location.href = "${ pageContext.request.contextPath }/article/sumbit.html?ids=" + ids;
          });
       	  }else{
       		layer.alert("请选择暂存或者退回的信息提交", {
                offset: ['222px', '390px'],
                shade: 0.01
              }); 
       	  }
        } else {
          layer.alert("请选择要提交的信息", {
            offset: ['222px', '390px'],
            shade: 0.01
          });
        }
      }

      function resetQuery() {
        $("#form1").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
        $("#articleTypes").select2("val", "");
        $("#status").val("");
      }

      $(function() {
        $.ajax({
          contentType: "application/json;charset=UTF-8",
          url: "${pageContext.request.contextPath }/article/aritcleTypeParentId.do?parentId=0",
          type: "POST",
          dataType: "json",
          success: function(articleTypes) {
            if(articleTypes) {
              //$("#articleTypes").append("<option></option>");
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

      $(function() {
        $("#articleTypes").select2("val", "${article.articleType.id}");
        /* $("#range").val("${articlesRange}");
        $("#status").val("${articlesStatus}"); */
      })

      //发布
      function apply() {
        var applys = [];
        var ids = [];
        $('input[name="chkItem"]:checked').each(function() {
          ids.push($(this).val());
        });

        $("input[name='chkItem']:checked").each(function() {
          applys.push($.trim($(this).parents("tr").find("td").eq(6).text()));
        });

        if(ids.length > 0) {

          if($.inArray("待发布", applys) > -1) {
            layer.alert("请选择需要发布的信息", {
              offset: ['222px', '390px'],
              shade: 0.01
            });
          } else if($.inArray("待提交", applys) > -1) {
            layer.alert("请选择需要发布的信息", {
              offset: ['222px', '390px'],
              shade: 0.01
            });
          } else if($.inArray("退回", applys) > -1) {
            layer.alert("请选择需要发布的信息", {
              offset: ['222px', '390px'],
              shade: 0.01
            });
          } else if($.inArray("发布", applys) > -1) {
            layer.alert("请选择需要发布的信息", {
              offset: ['222px', '390px'],
              shade: 0.01
            });
          } else {
            layer.confirm('您确定要发布吗?', {
              title: '提示',
              offset: ['222px', '360px'],
              shade: 0.01
            }, function(index) {
              layer.close(index);
              window.location.href = "${pageContext.request.contextPath }/article/apply.html?ids=" + ids;
            });
          }

        } else {
          layer.alert("请选择要提交的信息", {
            offset: ['222px', '390px'],
            shade: 0.01
          });
        }
      }

      //撤回
      function withdraw() {
        var applys = [];
        var ids = [];
        $('input[name="chkItem"]:checked').each(function() {
          ids.push($(this).val());
        });

        $("input[name='chkItem']:checked").each(function() {
          applys.push($.trim($(this).parents("tr").find("td").eq(6).text()));
        });

        if(ids.length > 0) {
          /* if($.inArray("待发布", applys) > -1) {
            layer.alert("请选择需要撤回的信息", {
              offset: ['222px', '390px'],
              shade: 0.01
            });
          } else if($.inArray("待提交", applys) > -1) {
            layer.alert("请选择需要撤回的信息", {
              offset: ['222px', '390px'],
              shade: 0.01
            });
          } else if($.inArray("退回", applys) > -1) {
            layer.alert("请选择需要撤回的信息", {
              offset: ['222px', '390px'],
              shade: 0.01
            });
          } else if($.inArray("撤回", applys) > -1) {
            layer.alert("请选择需要撤回的信息", {
              offset: ['222px', '390px'],
              shade: 0.01
            });
          } else { */
          layer.confirm('您确定要撤回吗?', {
            title: '提示',
            offset: ['222px', '360px'],
            shade: 0.01
          }, function(index) {
            layer.close(index);
            window.location.href = "${pageContext.request.contextPath }/article/withdraw.html?ids=" + ids;
          });
          /* } */

        } else {
          layer.alert("请选择要撤回的信息", {
            offset: '222px',
            shade: 0.01
          });
        }
      }

      function editor() {
        var id = [];
        var status = "";
        $('input[name="chkItem"]:checked').each(function() {
          id.push($(this).val());
          status=$(this).parent().next().text();
        });
        if(id.length == 1) {
          if(status=='2' || status=='1'||status=='4') {
            layer.alert("只可修改暂存或者退回的信息", {
              offset: ['180px', '200px'],
              shade: 0.01,
            });
          } else {
            window.location.href = "${pageContext.request.contextPath }/article/edit.html?id=" + id;
          }

        } else if(id.length > 1) {
          layer.alert("只能选择一个", {
            offset: ['222px', '390px'],
            shade: 0.01
          });
        } else {
          layer.alert("请选择需要编辑的信息", {
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
          <li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a></li>
          <li><a>信息服务</a></li>
          <li><a>门户管理</a></li>
          <li>
            <a href="javascript:jumppage('${pageContext.request.contextPath}/article/getAll.html')">信息管理</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <div class="container">
      <div class="headline-v2">
        <h2>信息发布</h2>
      </div>

      <div class="search_detail">
        <form id="form1" action="${pageContext.request.contextPath }/article/serch.html" method="post" class="mb0">
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
          <select id="articleTypes" name="articleTypeId" class="w200" >
            	<option value="">全部</option>
            </select>
          </div>
            </span>
            </li>
            <li>
              <label class="fl">发布范围：</label>
              <span>
              <select id ="range" name="range" class="w100"  >
                <option value=""  <c:if test="${articlesRange == ''}">selected</c:if>>全部</option>
                <option value="0" <c:if test="${articlesRange == '0'}">selected</c:if>>内网</option>
                <%-- <option value="1" <c:if test="${articlesRange == '1'}">selected</c:if>>外网</option> --%>
                <option value="2" <c:if test="${articlesRange == '2'}">selected</c:if>>内外网</option>
               </select>
           </span>
            </li>
            <li>
              <label class="fl w100">状态：</label>
              <span>
              <select id ="status" name="status" class="w100">
                <option value="" <c:if test="${articlesStatus == ''}">selected</c:if>>全部</option>
                <option value="0" <c:if test="${articlesStatus == '0'}">selected</c:if>>暂存</option>
                <option value="1" <c:if test="${articlesStatus == '1'}">selected</c:if>>已提交</option>
                <option value="2" <c:if test="${articlesStatus == '2'}">selected</c:if>>已发布</option>
                <option value="3" <c:if test="${articlesStatus == '3'}">selected</c:if>>已退回</option>
                <option value="4" <c:if test="${articlesStatus == '4'}">selected</c:if>>已取消发布</option>
               </select>
           </span>
            </li>
          </ul>
          <div class="col-md-12 col-sm-12 col-xs-12 tc mt5">
            <button type="submit" class="btn">查询</button>
            <button type="button" class="btn" onclick="resetQuery()">重置</button>
          </div>
          <div class="clear"></div>
        </form>
      </div>

      <input type="hidden" id="depid" name="depid">

      <div class="col-md-12 pl20 mt10">
        <button class="btn btn-windows add" type="button" onclick="add()">新增</button>
        <button class="btn btn-windows edit" type="button" onclick="editor()">修改</button>
        <button class="btn btn-windows delete" type="button" onclick="del()">删除</button>
        <button class="btn btn-windows git" type="button" onclick="sub()">提交</button>
        <%-- <button class="btn btn-windows apply" type="button" onclick="apply()">发布</button> --%>
        <%--<button class="btn btn-windows withdraw" type="button" onclick="withdraw()">撤回</button>
      --%></div>

      <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
          <thead>
            <tr>
              <th class="info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
              <th class="tnone"></th>
              <th class="info w50">序号</th>
              <th class="info" width="35%">信息标题</th>
              <th class="info" width="8%">发布范围</th>
              <th class="info" width="17%">提交时间</th>
              <th class="info" width="15%">信息栏目</th>
              <th class="info" width="10%">状态</th>
              <th class="info">浏览量</th>
            </tr>
          </thead>
          <c:forEach items="${list.list}" var="article" varStatus="vs">
            <tr class="pointer">
              <td class="tc w30"><input onclick="check()" type="checkbox" name="chkItem" value="${article.id }" /></td>
              <td class="tnone">${article.status}</td>
              <td class="tc w50">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>

              <c:if test="${fn:length(article.name)>30}">
                <td onclick="view('${article.id }')" <%-- onmouseover="titleMouseOver('${article.name}',this)" onmouseout="titleMouseOut()" --%> title="${article.name}">${fn:substring(article.name,0,30)}...</td>
              </c:if>
              <c:if test="${fn:length(article.name)<=30}">
                <td onclick="view('${article.id }')" title="${article.name}">${article.name }</td>
              </c:if>

              <td class="tl" onclick="view('${article.id }')">
                <c:if test="${article.range=='0' }">
               		   内网
                </c:if>
                <c:if test="${article.range=='2' }">
             		     内外网
                </c:if>
              </td>
              <td class="tc" onclick="view('${article.id }')">
                <fmt:formatDate value='${article.createdAt}' pattern="yyyy-MM-dd  HH:mm:ss" />
              </td>
              <td class="tl" onclick="view('${article.id }')">${article.articleType.name }</td>
              <td class="tl">
                <c:if test="${article.status=='0' }">
                  <input type="hidden" name="status" value="${article.status }">暂存
                </c:if>
                <c:if test="${article.status=='1' }">
                  <input type="hidden" name="status" value="${article.status }">已提交
                </c:if>
                <c:if test="${article.status=='2' }">
                  <input type="hidden" name="status" value="${article.status }">已发布
                </c:if>
                <c:if test="${article.status=='3' }">
                  <input type="hidden" name="status" value="${article.status }">已退回
                </c:if>
                <c:if test="${article.status=='4' }">
                  <input type="hidden" name="status" value="${article.status }">已取消发布
                </c:if>
              </td>
              <td class="tc">${article.showCount }</td>
            </tr>
          </c:forEach>
        </table>
      </div>
      <div id="pagediv" align="right"></div>
    </div>
    </div>
  </body>

</html>