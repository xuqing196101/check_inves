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


      function clearSearch() {
        $("#relName").attr("value", "");
        //还原select下拉列表只需要这一句
        $("#expertsFrom option:selected").removeAttr("selected");
        $("#status option:selected").removeAttr("selected");
        /* $("#expertsTypeId option:selected").removeAttr("selected"); */
        $("#mobile").attr("value", "");
        $("#graduateSchool").val("");
        $("#idCardNumber").val("");
        $("#expertType").val("");
        $("#expertTypeIds").val("");
        $("#categoryIds").val("");
        $("#category").val("");
        $("#orgName option:selected").removeAttr("selected");
        $("#address option:selected").removeAttr("selected");
        $("#formSearch").submit();
      }
      //查看信息
      function view(id) {
        window.location.href = "${pageContext.request.contextPath}/expertQuery/view.html?expertId=" + id;
      }



    </script>
    <script type="text/javascript">
    function showExpertType() {
        var setting = {
          check: {
            enable: true,
            chkboxType: {
              "Y": "",
              "N": ""
            }
          },
          view: {
            dblClickExpand: false
          },
          data: {
            simpleData: {
              enable: true,
              idKey: "id",
              pIdKey: "parentId"
            }
          },
          callback: {
            beforeClick: beforeClick,
            onCheck: onCheck
          }
        };

        $.ajax({
          type: "GET",
          async: false,
          url: "${pageContext.request.contextPath}/expert/experType.do",
          dataType: "json",
          success: function(zNodes) {
            for(var i = 0; i < zNodes.length; i++) {
              if(zNodes[i].isParent) {
              } else {
                //zNodes[i].icon = "${ctxStatic}/images/532.ico";//设置图标  
              }
            }
            tree = $.fn.zTree.init($("#treeExpertType"), setting, zNodes);
            tree.expandAll(true); //全部展开
          }
        });
        var cityObj = $("#expertType");
        var cityOffset = $("#expertType").offset();
        $("#expertTypeContent").css({
          left: cityOffset.left + "px",
          top: cityOffset.top + cityObj.outerHeight() + "px"
        }).slideDown("fast");
        $("body").bind("mousedown", onBodyDownexpertType);
      }

      function hideexpertType() {
        $("#expertTypeContent").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDownexpertType);

      }

      function onBodyDownexpertType(event) {
        if(!(event.target.id == "menuBtn" || $(event.target).parents("#expertTypeContent").length > 0)) {
          hideexpertType();
        }
      }
    
      function beforeClick(treeId, treeNode) {
          var zTree = $.fn.zTree.getZTreeObj("treeExpertType");
          zTree.checkNode(treeNode, !treeNode.checked, null, true);
          return false;
        }

        function onCheck(e, treeId, treeNode) {
          var zTree = $.fn.zTree.getZTreeObj("treeExpertType"),
            nodes = zTree.getCheckedNodes(true),
            v = "";
          var rid = "";
          for(var i = 0, l = nodes.length; i < l; i++) {
            v += nodes[i].name + ",";
            rid += nodes[i].id + ",";
          }
          if(v.length > 0) v = v.substring(0, v.length - 1);
          if(rid.length > 0) rid = rid.substring(0, rid.length - 1);
          var cityObj = $("#expertType");
          cityObj.attr("value", v);
          $("#expertTypeIds").val(rid);
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
    	$("#formSearch").attr("action", "${pageContext.request.contextPath}/expertQuery/exportExcel.html?flag=1");
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
            <a href="javascript:jumppage('${pageContext.request.contextPath}/expert/findAllExpert.html')">全部专家查询</a>
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
        <h2>专家列表</h2>
      </div>
      <h2 class="search_detail">
      <form action="${pageContext.request.contextPath}/expert/findAllExpert.html"  method="post" id="formSearch"  class="mb0"> 
        <input type="hidden" name="page" id="page">
        <input type="hidden" name="flag" value="0">
        <ul class="demand_list">
        <li>
          <label class="fl">专家姓名：</label><span><input type="text" id="relName" name="relName" value="${expert.relName}" class="w220"></span>
        </li>
        <li>
          <label class="fl">手机号：</label><span><input type="text" id="mobile" name="mobile" value="${expert.mobile }" class="w220"></span>
        </li>
        <li>
          <label class="fl">专家类型：</label>
          <span class="fl">
            <select  name="expertsFrom" id="expertsFrom" class="w220">
              <option selected="selected" value="">全部</option>
              <c:forEach items="${lyTypeList }" var="ly" varStatus="vs"> 
                <option <c:if test="${expert.expertsFrom eq ly.id }">selected="selected"</c:if> value="${ly.id}">${ly.name}</option>
              </c:forEach>
            </select>          
          </span>
        </li>
        <li>
          <label class="fl">专家状态：</label>
          <span class="fl">
            <select name="status" id="status" class="w220">
               <option selected="selected" value=''>全部</option>
               <option <c:if test="${expert.status eq '-1' }">selected</c:if> value="-1">暂存</option>
               <option <c:if test="${expert.status eq 'temporary' }">selected</c:if> value="temporary">临时</option>
               <option <c:if test="${expert.status eq '0' and expert.auditTemporary == 0}">selected</c:if> value="0">待初审</option>
               <option <c:if test="${expert.status eq '9' }">selected</c:if> value="9">退回再初审</option>
               <option <c:if test="${expert.status eq '0' and expert.auditTemporary == 1}">selected</c:if> value="firstInstance">初审中</option>
               <option <c:if test="${expert.status eq 'endOfTrial' }">selected</c:if> value="endOfTrial">预初审结束</option>
               <option <c:if test="${expert.status eq '1' }">selected</c:if> value="1">初审合格(待复审)</option>
               <option <c:if test="${expert.status eq '2' }">selected</c:if> value="2">初审不合格</option>
               <option <c:if test="${expert.status eq '3' }">selected</c:if> value="3">初审退回修改</option>
               
               
               <option <c:if test="${expert.status eq '11' }">selected</c:if> value="11">待分配</option>
               <option <c:if test="${expert.status eq '14' }">selected</c:if> value="14">复审待分组专家</option>
               <option <c:if test="${expert.status eq '4' and expert.auditTemporary == 0}">selected</c:if> value="4">复审已分配</option>
               <option <c:if test="${expert.status eq '4' and expert.auditTemporary == 2}">selected</c:if> value="review">复审中</option>
               <option <c:if test="${expert.status eq '-2' }">selected</c:if> value="-2">预复审结束</option>
               <option <c:if test="${expert.status eq '5' }">selected</c:if> value="5">复审不合格</option>
               <option <c:if test="${expert.status eq '10' }">selected</c:if> value="10">复审退回修改</option>
               <option <c:if test="${expert.status eq '-3' }">selected</c:if> value="-3">公示中</option>
               
               
               <option <c:if test="${expert.status eq '6' and expert.auditTemporary == 0}">selected</c:if> value="6">入库(待复查 )</option>
               <option <c:if test="${expert.status eq '6' and expert.auditTemporary == 3}">selected</c:if> value="reviewLook">复查中</option>
               <option <c:if test="${expert.status eq '19' and expert.auditTemporary == 19}">selected</c:if> value="19">预复查结束</option>
               <option <c:if test="${expert.status eq '7' }">selected</c:if> value="7">复查合格</option>
               <option <c:if test="${expert.status eq '8' }">selected</c:if> value="8">复查不合格</option>
               
               
               <option <c:if test="${expert.status eq '17' }">selected</c:if> value="17">资料不全</option>
               <option <c:if test="${expert.status eq '18' }">selected</c:if> value="18">异议处理</option>
               <option <c:if test="${expert.status eq '12' }">selected</c:if> value="12">处罚中</option>
               <option <c:if test="${expert.status eq '13' }">selected</c:if> value="13">无产品专家</option>
             </select>
          </span>
       </li>
        <li>
          <label class="fl">身份证号：</label><span><input class="w220" type="text" id="idCardNumber" name="idCardNumber" value="${expert.idCardNumber }"></span>
        </li>
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
       <%--  <li>
          <label class="fl">毕业院校：</label><span><input class="w220"type="text" id="graduateSchool" name="graduateSchool" value="${expert.graduateSchool }"></span>
        </li> --%>
        
        <%-- <li>
          <label class="fl">专家类别：</label>
          <span class="fl">
            <select name="expertsTypeId" id="expertsTypeId" class="w220">
              <option selected="selected"  value=''>全部</option>
              <c:forEach items="${expTypeList}" var="exp">
                <option <c:if test="${expert.expertsTypeId == exp.id}">selected</c:if> value="${exp.id}">${exp.name}</option>
              </c:forEach>          
            </select>
          </span>
        </li> --%>
        
        <li>
          <label class="fl">专家类别：</label><span><input  class="w220" id="expertType" class="span2 mt5" type="text" name="expertType"  readonly value="${expertType}" onclick="showExpertType();" />
          <input   type="hidden" name="expertTypeIds"  id="expertTypeIds" value="${expertTypeIds}" /></span>
        </li>
        <li>
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
        <input class="btn mt1" onclick="exportExcel();" value="导出" type="reset">
      </div>
      <div class="clear"></div>
    </form>
   </h2>
      <!-- 表格开始-->

      <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
          <thead>
            <tr>
              <!-- <th class="info w50">选择</th> -->
              <th class="info w40">序号</th>
              <th class="info w150">采购机构</th>
              <th class="info w120">姓名</th>
              <!-- <th class="info">用户名</th> -->
              <th class="info w40">性别</th>
              <th class="info w150">专业职称（职务）</th>
              <!-- <th class="info">身份证号</th> -->
              <th class="info w50">类型</th>
              <th class="info w80">类别</th>
              <!-- <th class="info">毕业院校及专业</th> -->
              <th class="info w90">注册日期</th>
              <th class="info w100">最新提交日期</th>
              <th class="info w100">最新审核日期</th>
              <!-- <th class="info">手机</th>
              <th class="info">地区</th> -->
              <th class="info w100">状态</th>
            </tr>
          </thead>
          <c:forEach items="${result.list }" var="e" varStatus="vs">
            <tr class="pointer">
              <%-- <td class="tc w30"><input type="radio" name="check" id="checked" alt="" value="${e.id }"></td> --%>
              <td class="tc" class="tc w50">${(vs.index+1)+(result.pageNum-1)*(result.pageSize)}</td>
              <td class="">${e.orgName }</td>
              <td>
                <c:choose>
                  <c:when test="${e.status eq '4' and e.isProvisional eq '1'}">
                    <a href="javascript:jumppage('${pageContext.request.contextPath}/expertQuery/temporaryExpert.html?expertId=${e.id}')">${e.relName}</a>
                  </c:when>
                  <c:otherwise>
                    <a href="javascript:jumppage('${pageContext.request.contextPath}/expertQuery/view.html?expertId=${e.id}&sign=1&status=${e.status}')">${e.relName}</a>
                  </c:otherwise>
                </c:choose>
              </td>
              <%-- <td class="tl pl20" >${e.loginName}</td> --%>
              <td class="tc">${e.gender}</td>
              <td class="tc">
              	<c:choose>
              		<c:when test="${e.professTechTitles !=null and e.professTechTitles ne ''}">
              			${e.professTechTitles}
              		</c:when>
              		<c:otherwise>
              			 ${e.atDuty}
              		</c:otherwise>
              	</c:choose>
              </td>
              <%-- <td class="tc">${e.idCardNumber}</td> --%>
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
              <%-- <td class="tc">${e.mobile }</td>
              <td class="tc">${e.address }</td> --%>
              <td class="tc" id="${e.id}">
                <c:if test="${e.status eq '-3'}">
                  <span class="label rounded-2x label-dark">公示中</span>
                </c:if>
                <c:if test="${e.status eq '0' and e.auditTemporary == 1}">
                  <span class="label rounded-2x label-dark">初审中</span>
                </c:if>
                <c:if test="${e.status eq '4' and e.auditTemporary == 2 and e.isProvisional eq '0'}">
                  <span class="label rounded-2x label-dark">复审中</span>
                </c:if>
                <c:if test="${e.status eq '6' and e.auditTemporary == 3}">
                  <span class="label rounded-2x label-dark">复查中</span>
                </c:if>
                <c:if test="${e.status eq '-2'}">
                  <span class="label rounded-2x label-u">预复审结束</span>
                </c:if>
                <c:if test="${e.isProvisional eq '1' and e.status eq '4'}">
                  <span class="label rounded-2x label-dark">临时</span>
                </c:if>
                <c:if test="${e.status eq '-1' and e.isSubmit eq '0'}">
                  <span class="label rounded-2x label-dark">暂存</span>
                </c:if>
                <c:if test="${e.status eq '0' and e.auditTemporary == 0}">
                  <span class="label rounded-2x label-dark">待初审</span>
                </c:if>
                <c:if test="${e.status eq '1' }">
                  <span class="label rounded-2x label-u">初审合格(待复审)</span>
                </c:if>
                <c:if test="${e.status eq '2' }">
                  <span class="label rounded-2x label-dark">初审不合格</span>
                </c:if>
                <c:if test="${e.status eq '3' }">
                  <span class="label rounded-2x label-dark">初审退回修改</span>
                </c:if>
                <c:if test="${e.status eq '4' and e.isProvisional eq '0' and e.auditTemporary != 2}">
                  <span class="label rounded-2x label-u">复审已分配</span>
                </c:if>
                <c:if test="${e.status eq '5' }">
                  <span class="label rounded-2x label-dark">复审不合格</span>
                </c:if>
                <c:if test="${e.status eq '6' and e.auditTemporary == 0}">
                  <span class="label rounded-2x label-u">入库(待复查 )</span>
                </c:if>
                <c:if test="${e.status eq '7' }">
                  <span class="label rounded-2x label-u">复查合格</span>
                </c:if>
                <c:if test="${e.status eq '8' }">
                  <span class="label rounded-2x label-dark">复查不合格</span>
                </c:if>
                <c:if test="${e.status eq '9' }">
                  <span class="label rounded-2x label-dark">退回再初审</span>
                </c:if>
                <c:if test="${e.status eq '10' }">
                  <span class="label rounded-2x label-dark">复审退回修改</span>
                </c:if>
                <c:if test="${e.status eq '11' }">
                  <span class="label rounded-2x label-dark">待分配</span>
                </c:if>
                <c:if test="${e.status eq '12' }">
                  <span class="label rounded-2x label-dark">处罚中</span>
                </c:if>
                <c:if test="${e.status eq '13' }">
                  <span class="label rounded-2x label-dark">无产品专家</span>
                </c:if>
                <c:if test="${e.status eq '14' }">
                  <span class="label rounded-2x label-dark">复审待分组专家</span>
                </c:if>
                <c:if test="${e.status eq '15' or e.status eq '16'}">
                  <span class="label rounded-2x label-u">预初审结束</span>
                </c:if>
                <c:if test="${e.status eq '17' }">
                  <span class="label rounded-2x label-dark">资料不全</span>
                </c:if>
                <c:if test="${e.status eq '18' }">
                  <span class="label rounded-2x label-dark">异议处理</span>
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