<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp" %>
    <script type="text/javascript">
      $(function() {
        laypage({
          cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
          pages: "${result.pages}", //总页数
          skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
          skip: true, //是否开启跳页
          total: "${result.total}",
          startRow: "${result.startRow}",
          endRow: "${result.endRow}",
          groups: "${result.pages}" >= 5 ? 5 : "${result.pages}", //连续显示分页数
          curr: function() { //合格url获取当前页，也可以同上（pages）方式获取
            //var page = location.search.match(/page=(\d+)/);
            //return page ? page[1] : 1;
            return "${result.pageNum}";
          }(),
          jump: function(e, first) { //触发分页后的回调
            if(!first) { //一定要加此判断，否则初始时会无限刷新
              $("#page").val(e.curr);
              $("#formSearch").submit();
            }
          }
        });
      });
    </script>
    <script type="text/javascript">
      function clearSearch() {
        $("#relName").attr("value", "");
        $("#status option:selected").removeAttr("selected");
        $("#mobile").attr("value", "");
        $("#idCardNumber").attr("value", "");
        $("#orgName").attr("value", "");
        $("#categoryIds").val("");
        $("#category").val("");
        $("#expertsFrom option:selected").removeAttr("selected");
        $("#expertsTypeId option:selected").removeAttr("selected");
        $("#orgName option:selected").removeAttr("selected");
        $("#address option:selected").removeAttr("selected");
        $("#formSearch").submit();
      }
      
     //查看列表
      function checkMap(){
        window.location.href = "${pageContext.request.contextPath}/expertQuery/expertStorageMap.html";
      }
    </script>
    <!-- 品目 -->
    <script type="text/javascript">
      var key;
      function showCategory() {
        var zTreeObj;
        var zNodes;
        var setting = {
          async: {
            autoParam: ["id"],
            enable: true,
            url: "${pageContext.request.contextPath}/expertQuery/createtree.do",
            otherParam: {
              categoryIds: "${categoryIds}",
            },
            dataType: "json",
            type: "post",
          },
          check: {
            enable: true,
            chkboxType: {
              "Y": "s",
              "N": "s"
            }
          },
          callback: {
            beforeClick: beforeClickCategory,
            onCheck: onCheckCategory
          },
          data: {
            simpleData: {
              enable: true,
              idKey: "id",
              pIdKey: "parentId"
            }
          },
          view: {
            fontCss: getFontCss
          }
        };
        zTreeObj = $.fn.zTree.init($("#treeRole"), setting, zNodes);
        key = $("#key");
        key.bind("focus", focusKey)
          .bind("blur", blurKey)
          .bind("propertychange", searchNode)
          .bind("input", searchNode);

        var cityObj = $("#category");
        var cityOffset = $("#category").offset();
        $("#roleContent").css({
          left: cityOffset.left + "px",
          top: cityOffset.top + cityObj.outerHeight() + "px"
        }).slideDown("fast");
        $("body").bind("mousedown", onBodyDownOrg);
      }

      function focusKey(e) {
        if(key.hasClass("empty")) {
          key.removeClass("empty");
        }
      }

      function blurKey(e) {
        if(key.get(0).value === "") {
          key.addClass("empty");
        }
      }
      var lastValue = "",
        nodeList = [],
        fontCss = {};

      function clickRadio(e) {
        lastValue = "";
        searchNode(e);
      }

      function searchNode(e) {
        var zTree = $.fn.zTree.getZTreeObj("treeRole");
        var value = $.trim(key.get(0).value);
        var keyType = "name";
        if(key.hasClass("empty")) {
          value = "";
        }
        if(lastValue === value) return;
        lastValue = value;
        if(value === "") return;
        updateNodes(false);
        nodeList = zTree.getNodesByParamFuzzy(keyType, value);
        updateNodes(true);
      }

      function updateNodes(highlight) {
        var zTree = $.fn.zTree.getZTreeObj("treeRole");
        for(var i = 0, l = nodeList.length; i < l; i++) {
          nodeList[i].highlight = highlight;
          zTree.updateNode(nodeList[i]);
        }
      }

      function getFontCss(treeId, treeNode) {
        return(!!treeNode.highlight) ? {
          color: "#A60000",
          "font-weight": "bold"
        } : {
          color: "#333",
          "font-weight": "normal"
        };
      }

      function filter(node) {
        return !node.isParent && node.isFirstNode;
      }

      function beforeClickCategory(treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeRole");
        zTree.checkNode(treeNode, !treeNode.checked, null, true);
        return false;
      }

      function onCheckCategory(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeRole"),
          nodes = zTree.getCheckedNodes(true),
          v = "";
        var rid = "";
        for(var i = 0, l = nodes.length; i < l; i++) {
          v += nodes[i].name + ",";
          rid += nodes[i].id + ",";
        }
        if(v.length > 0) v = v.substring(0, v.length - 1);
        if(rid.length > 0) rid = rid.substring(0, rid.length - 1);
        var cityObj = $("#category");
        cityObj.attr("value", v);
        $("#categoryIds").val(rid);
      }

      function onBodyDownOrg(event) {
        if(!(event.target.id == "menuBtn" || event.target.id == "roleSel" || event.target.id == "roleContent" || $(event.target).parents("#roleContent").length > 0)) {
          hideRole();
        }
      }

      function hideRole() {
        $("#roleContent").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDownOrg);

      }
    </script>
    <script type="text/javascript">
    function exportExcel(){
    	$("#formSearch").attr("action", "${pageContext.request.contextPath}/expertQuery/exportExcel.html?flag=2");
        $("#formSearch").submit();
        //还原地址
        $("#formSearch").attr("action", "${pageContext.request.contextPath}/expert/findAllExpert.html");
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
            <a href="javascript:void(0)">支撑环境</a>
          </li>
          <li>
            <a href="javascript:void(0)">专家管理</a>
          </li>
          <li>
            <a href="javascript:jumppage('${pageContext.request.contextPath}/expertQuery/list.html')">专家入库查询</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    <div id="roleContent" class="roleContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
      <!-- <input type="text" id="key" value="" class="empty" /><br/> -->
      <ul id="treeRole" class="ztree" style="margin-top:0;"></ul>
    </div>
    <div id="expertTypeContent" class="expertTypeContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
      <ul id="treeExpertType" class="ztree" style="margin-top:0;"></ul>
    </div>
    
    <!-- 我的订单页面开始-->
    <div class="container">
      <div class="headline-v2">
        <h2>入库专家列表</h2>
      </div>
      <h2 class="search_detail">  
      <form action="${pageContext.request.contextPath}/expertQuery/list.html"  method="post" id="formSearch"  class="mb0"> 
         <input type="hidden" name="page" id="page">
         <input type="hidden" name="addressName" value="${addressName}">
         <input type="hidden" name="flag" value="${flag}">
        <ul class="demand_list">
        <li>
          <label class="fl">专家姓名：</label><span><input class="w220" type="text" id="relName" name="relName" value="${expert.relName }"></span>
        </li>
        <li>
          <label class="fl">身份证号：</label><span><input class="w220" type="text" id="idCardNumber" name="idCardNumber" value="${expert.idCardNumber }"></span>
        </li>
        <li>
          <label class="fl">手机号：</label><span><input class="w220" type="text" id="mobile" name="mobile" value="${expert.mobile }"></span>
        </li>
        <li>
          <label class="fl">专家类型：</label>
          <span class="fl">
            <select  name="expertsFrom" id="expertsFrom" class="w220">
              <option selected="selected" value="">全部</option>
              <c:forEach items="${expertFromList }" var="from" varStatus="vs"> 
                <option <c:if test="${expert.expertsFrom eq from.id }">selected="selected"</c:if> value="${from.id}">${from.name}</option>
              </c:forEach>
            </select>          
          </span>
        </li>
        <li>
        <label class="fl">专家状态：</label>
        <span class="fl">
          <select name="status" id="status" class="w220">
             <option selected="selected" value=''>全部</option>
             <option <c:if test="${expert.status =='6' }">selected</c:if> value="6">入库(待复查)</option>
             <option <c:if test="${expert.status eq 'reviewLook'}">selected</c:if> value="reviewLook">复查中</option>
             <option <c:if test="${expert.status =='19' }">selected</c:if> value="19">预复查结束</option>
             <option <c:if test="${expert.status =='7' }">selected</c:if> value="7">复查合格</option>
             <option <c:if test="${expert.status =='13' }">selected</c:if> value="13">无产品专家</option>
             <option <c:if test="${expert.status =='17' }">selected</c:if> value="17">资料不全</option>
           </select>
        </span>
       </li>
       <%-- <li>
          <label class="fl">采购机构：</label><span><input class="w220" type="text" id="orgName" name="orgName" value="${expert.orgName }"></span>
        </li> --%>
        <li>
         <label class="fl">采购机构：</label>
         <select name="orgName" id="orgName" class="w220">
           <option value=''>全部</option>
           <c:forEach items="${allOrg}" var="org">
           <c:if test="${org.isAuditSupplier == 1}">
             <option value="${org.shortName}" <c:if test="${expert.orgName eq org.shortName}">selected</c:if>>${org.shortName}</option>
           </c:if>
           </c:forEach>
         </select>
       </li>
        
        <!-- 专家类别查询 -->
        <li>
          <label class="fl">专家类别：</label>
          <span class="fl">
            <select name="expertsTypeId" id="expertsTypeId" class="w220">
              <option selected="selected"  value=''>全部</option>
              <c:forEach items="${expTypeList}" var="exp">
                <option <c:if test="${expert.expertsTypeId == exp.id}">selected</c:if> value="${exp.id}">${exp.name}</option>
              </c:forEach>          
            </select>
          </span>
        </li>
        <li>
         <label class="fl">地区：</label>
         <select name="address" id="address" class="w220">
           <option value=''>全部</option>
           <c:forEach items="${privnce}" var="list">
             <option <c:if test="${expert.address eq list.id }">selected</c:if> value="${list.id }">${list.name }</option>
           </c:forEach>
         </select>
        </li>
        <li>
          <label class="fl">品目：</label><span><input id="category" type="text" name="categoryNames" value="${categoryNames}" readonly onclick="showCategory();" class="w220"/>
          <input type="hidden" name="categoryIds"  id="categoryIds" value="${categoryIds}" /></span>
        </li>
      </ul>
      <div class="col-md-12 clear tc mt10">
        <input class="btn mt1"  value="查询" type="submit">
       <input class="btn mt1" onclick="clearSearch();" value="重置" type="reset">
       <c:if test="${flag == 1 }">
	       <input class="btn mt1" onclick="checkMap();" value="返回" type="reset">
       </c:if>
       <c:if test="${flag != 1 }">
	       <input class="btn mt1" onclick="checkMap();" value="切换到地图" type="reset">
       </c:if>
       <input class="btn mt1" onclick="exportExcel();" value="导出" type="reset">
     </div>
     <div class="clear"></div>
    </form>
   </h2>
      <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
          <thead>
            <tr>
              <th class="info w50">序号</th>
              <th class="info">采购机构</th>
              <th class="info">专家姓名</th>
              <!-- <th class="info">身份证号</th> -->
              <th class="info w40">性别</th>
              <th class="info w130">专业职称（职务）</th>
              <th class="info">类型</th>
              <th class="info">类别</th>
              <!-- <th class="info">毕业院校及专业</th> -->
              <th class="info w90">注册日期</th>
              <th class="info w100">最新提交日期</th>
              <th class="info w100">最新审核日期</th>
              <th class="info">手机</th>
              <th class="info">地区</th>
              <th class="info">专家状态</th>
            </tr>
          </thead>
          <c:forEach items="${result.list }" var="e" varStatus="vs">
            <tr class="pointer">
              <td class="tc w50" class="tc w50">${(vs.index+1)+(result.pageNum-1)*(result.pageSize)}</td>
              <td class="tl">${e.orgName}</td>
              <td class="tl">
                <a href="javascript:jumppage('${pageContext.request.contextPath}/expertQuery/view.html?expertId=${e.id}&sign=2')">${e.relName}</a>
              </td>
              <%-- <td class="tc">${e.idCardNumber}</td> --%>
              <td class="tc">${e.gender}</td>
              <td class="tc">${e.atDuty}</td>
              <td class="tc">${e.expertsFrom }</td>
              <td class="hand" title="${e.expertsTypeId}">
                <c:if test="${fn:length (e.expertsTypeId) > 4}">${fn:substring(e.expertsTypeId,0,4)}...</c:if>
                <c:if test="${fn:length (e.expertsTypeId) <= 4}">${e.expertsTypeId}</c:if>
              </td>
              <%-- <td class="tl">${e.graduateSchool }</td> --%>
              <td class="tc">
                <fmt:formatDate value="${e.createdAt }" pattern="yyyy-MM-dd" />
              </td>
              <td class="tc">
                <fmt:formatDate value="${e.submitAt }" pattern="yyyy-MM-dd" />
              </td>
              <td class="tc">
                <fmt:formatDate value="${e.auditAt }" pattern="yyyy-MM-dd" />
              </td>
              <td class="tc">${e.mobile }</td>
              <td class="tc">${e.address }</td>
              <td class="tc" id="${e.id}">
                <c:if test="${e.status eq '6' and e.auditTemporary != 3}">
                  <span class="label rounded-2x label-u">入库(待复查)</span>
                </c:if>
                <c:if test="${e.status eq '6' and e.auditTemporary == 3}">
                  <span class="label rounded-2x label-u">复查中</span>
                </c:if>
                <c:if test="${e.status eq '19' }">
                  <span class="label rounded-2x label-u">预复查结束</span>
                </c:if>
                <c:if test="${e.status eq '7' }">
                  <span class="label rounded-2x label-u">复查合格</span>
                </c:if>
                <c:if test="${e.status eq '13' }">
                  <span class="label rounded-2x label-u">无产品专家</span>
                </c:if>
                <c:if test="${e.status eq '17' }">
                  <span class="label rounded-2x label-u">资料不全</span>
                </c:if>
              </td>
            </tr>
          </c:forEach>
        </table>
        <div id="pagediv" align="right"></div>
      </div>
    </div>

  </body>

</html>