<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <%@ include file="/WEB-INF/view/common/webupload.jsp"%>
  </head>

  <script type="text/javascript">
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

    function show(id,report) {
      var status = "0";
      window.location.href = "${pageContext.request.contextPath}/pqinfo/view.html?id=" + id + "&status=" + status+ "&report=" + report;
    }

    function edit() {
      var id = [];
      $('input[name="chkItem"]:checked').each(function() {
        id.push($(this).val());
      });
      if(id.length == 1) {
        window.location.href = "${pageContext.request.contextPath}/pqinfo/edit.html?id=" + id;
      } else if(id.length > 1) {
        layer.alert("只能选择一个", {
          shade: 0.01
        });
      } else {
        layer.alert("请选择需要修改的质检报告", {
          shade: 0.01
        });
      }
    }

    function del() {
      var ids = [];
      $('input[name="chkItem"]:checked').each(function() {
        ids.push($(this).val());
      });
      if(ids.length > 0) {
        layer.confirm('您确定要删除吗?', {
          title: '提示',
          shade: 0.01
        }, function(index) {
          layer.close(index);
          window.location.href = "${pageContext.request.contextPath}/pqinfo/delete.html?ids=" + ids;
        });
      } else {
        layer.alert("请选择要删除的质检报告", {
          shade: 0.01
        });
      }
    }

    function add() {
      window.location.href = "${pageContext.request.contextPath}/pqinfo/add.html";
    }


    function openUpload(id, report) {
      if(report == "0") {
        layer.msg("没有上传质检报告");
      } else {
        var a = "2";
        openViewDIv(id, report, a, null, null);
      }
    }

    function clearSearch() {
      $("#contractName").val("");
      $("#contractCode").val("");
      $("#searchType option:selected").removeAttr("selected");
      $("#searchConclusion option:selected").removeAttr("selected");
    }
  </script>

  <body>
    <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
          <li>
            <a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
          </li>
          <li>
            <a href="javascript:void(0)">保障作业</a>
          </li>
          <li>
            <a href="javascript:void(0)">产品质量管理</a>
          </li>
          <li class="active">
            <a href="javascript:jumppage('${pageContext.request.contextPath}/pqinfo/getAll.html')">产品质量结果登记 </a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    <div class="container">
      <div class="headline-v2">
        <h2>质量结果登记</h2>
      </div>

      <!-- 查询 -->

      <div class="search_detail">
        <form id="form1" action="${pageContext.request.contextPath}/pqinfo/getAll.html" method="post" class="mb0">
          <input type="hidden" name="page" id="page">
          <ul class="demand_list">
            <li class="fl">
              <label class="fl">合同名称： </label>
              <span><input type="text" id="contractName" name="contract.name" value="${pqInfo.contract.name}" class="mb0"/></span>
            </li>
            <li class="fl">
              <label class="fl">合同编号：</label>
              <span><input type="text" id="contractCode" name="contract.code" value="${pqInfo.contract.code}" class="mb0"/></span>
            </li>
            <li class="fl"><label class="fl">验收类型：</label>
              <span>
                  <select id="searchType" name="type" class="w100">
                    <option value="" selected="selected">请选择</option>
                    <option value="0" <c:if test="${'0' eq pqInfo.type}">selected="selected"</c:if>>首件检验</option>
                    <option value="1" <c:if test="${'1' eq pqInfo.type}">selected="selected"</c:if>>生产验收</option>
                    <option value="2" <c:if test="${'2' eq pqInfo.type}">selected="selected"</c:if>>出厂验收</option>
                    <option value="3" <c:if test="${'3' eq pqInfo.type}">selected="selected"</c:if>>到货验收</option>
                  </select>
                </span>
            </li>
            <li class="fl mr15"><label class="fl">质检结论：</label>
              <span>
                  <select id="searchConclusion" name="conclusion" class="w80">
                    <option value="" selected="selected">请选择</option>
                    <option value="0" <c:if test="${'0' eq pqInfo.conclusion}">selected="selected"</c:if>>合格</option>
                    <option value="1" <c:if test="${'1' eq pqInfo.conclusion}">selected="selected"</c:if>>不合格</option>
                  </select>
                </span>
            </li>
          </ul>
          <div class="col-md-12 clear tc mt10">
			      <button class="btn" type="submit">查询</button>
			      <button class="btn" type="button" onclick="clearSearch()">重置</button>
		      </div>
		      <div class="clear"></div>
        </form>
      </div>

      <!-- 表格开始-->

      <div class="col-md-12 pl20 mt10">
        <button class="btn btn-windows add" type="button" onclick="add();">登记</button>
        <button class="btn btn-windows edit" type="button" onclick="edit()">修改</button>
        <button class="btn btn-windows delete" type="button" onclick="del();">删除</button>
      </div>
      <div class="content table_box">
        <table class="table table-bordered table-condensed">
          <thead>
            <tr>
              <th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
              <th class="info w50">序号</th>
              <th class="info" width="25%">合同名称</th>
              <th class="info" width="20%">合同编号</th>
              <th class="info" width="20%">供应商名称</th>
              <th class="info" width="10%">验收类型</th>
              <th class="info" width="8%">质检结论</th>
              <th class="info">查看</th>
            </tr>
          </thead>
          <c:forEach items="${info.list}" var="obj" varStatus="vs">
            <tr>

              <td class="tc"><input onclick="check()" type="checkbox" name="chkItem" value="${obj.id}" /></td>

              <td class="tc pointer" onclick="show('${obj.id}','${obj.report}')">${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>

              <td class="tl pointer" onclick="show('${obj.id}','${obj.report}')">${obj.contract.name}</td>

              <td class="tl pointer" onclick="show('${obj.id}','${obj.report}')">${obj.contract.code}</td>

              <td class="tl pointer" onclick="show('${obj.id}','${obj.report}')">${obj.contract.supplier.supplierName}</td>

              <td class="tc pointer" onclick="show('${obj.id}','${obj.report}')">
                <c:if test="${'0' eq obj.type}">首件检验</c:if>
                <c:if test="${'1' eq obj.type}">生产验收</c:if>
                <c:if test="${'2' eq obj.type}">出厂验收</c:if>
                <c:if test="${'3' eq obj.type}">到货验收</c:if>
              </td>

              <td class="tc pointer" onclick="show('${obj.id}','${obj.report}')">
                <c:if test="${'0' eq obj.conclusion}">合格</c:if>
                <c:if test="${'1' eq obj.conclusion}">不合格</c:if>
              </td>

              <td class="tc pointer">
                <button type="button" onclick="openUpload('${obj.id}','${obj.report}')" class="btn">质检报告</button>
              </td>

            </tr>
          </c:forEach>
        </table>
      </div>
      <div id="pagediv" align="right"></div>
    </div>
  </body>

</html>