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
      /** 全选全不选 */
      function selectAll() {
        var checklist = document.getElementsByName("check");
        var checkAll = document.getElementById("allId");
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

      //修改
      function edit() {
        var count = 0;
        var ids = document.getElementsByName("check");

        for(i = 0; i < ids.length; i++) {
          if(document.getElementsByName("check")[i].checked) {
            var id = document.getElementsByName("check")[i].value;
            var value = id.split(",");
            count++;
          }
        }
        if(count > 1) {
          layer.alert("只能选择一条记录", {
            offset: ['222px', '390px'],
            shade: 0.01
          });
        } else if(count < 1) {
          layer.alert("请选择一条记录", {
            offset: ['222px', '390px'],
            shade: 0.01
          });
        } else if(count == 1) {
          window.location.href = "${pageContext.request.contextPath}/expert/toEditBasicInfo.html?id=" + value[0];
        }
      }
      //删除
      function dell() {
        var count = 0;
        var ids = document.getElementsByName("check");
        var id2 = "";
        var num = 0;
        for(i = 0; i < ids.length; i++) {
          if(document.getElementsByName("check")[i].checked) {
            id2 += document.getElementsByName("check")[i].value + ",";
            num++;
          }
          //id.push(document.getElementsByName("check")[i].value);
          count++;
        }
        var id = id2.substring(0, id2.length - 1);
        if(num > 0) {
          layer.confirm('您确定要删除吗?', {
            title: '提示',
            offset: ['222px', '360px'],
            shade: 0.01
          }, function(index) {
            layer.close(index);
            $.ajax({
              url: "${pageContext.request.contextPath}/expert/deleteAll.html",
              data: {
                "ids": id
              },
              type: "post",
              success: function() {
                layer.msg('删除成功', {
                  offset: ['222px', '390px']
                });
                window.setTimeout(function() {
                  window.location.reload();
                  for(var i = 0; i < info.length; i++) {
                    info[i].checked = false;
                  }
                }, 1000);
              },
              error: function() {
                layer.msg("删除失败", {
                  offset: ['222px', '390px']
                });
              }
            });
          });
        } else {
          layer.alert("请选择一条记录", {
            offset: ['222px', '390px'],
            shade: 0.01
          });
        }

      }

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
        $("#orgName option:selected").removeAttr("selected");
        $("#formSearch").submit();
      }
      //查看信息
      function view(id) {
        window.location.href = "${pageContext.request.contextPath}/expertQuery/view.html?expertId=" + id;
      }

      function shenhe() {
        var count = 0;
        var ids = document.getElementsByName("check");

        for(i = 0; i < ids.length; i++) {
          if(document.getElementsByName("check")[i].checked) {
            var id = document.getElementsByName("check")[i].value;
            var value = id.split(",");
            count++;
          }
        }
        if(count > 1) {
          layer.alert("只能选择一条记录", {
            offset: ['222px', '390px'],
            shade: 0.01
          });
        } else if(count < 1) {
          layer.alert("请选择一条记录", {
            offset: ['222px', '390px'],
            shade: 0.01
          });
        } else if(count == 1) {
          if(value[1] == 0) {
            window.location.href = "${pageContext.request.contextPath}/expert/toShenHe.do?id=" + value[0];
          } else {
            layer.alert("请选择未审核的", {
              offset: ['222px', '390px'],
              shade: 0.01
            });
          }

        }
      }
      //诚信登记
      function creadible() {
        var id = $(":radio:checked").val();
        if(id == null) {
          layer.msg("请选择专家！", {
            offset: '100px'
          });
        } else {
          var state = $("#" + id + "").parents("tr").find("td").eq(9).text().trim();
          if(state == "复审合格" || state == "复审不合格" || state == "待复查" || state == "复查合格" || state == "复查不合格") {
            index = layer.open({
              type: 2, //page层
              area: ['700px', '440px'],
              title: '诚信登记',
              shade: 0.01, //遮罩透明度
              moveType: 1, //拖拽风格，0是默认，1是传统拖动
              shift: 1, //0-6的动画形式，-1不开启
              offset: ['5px', '300px'],
              shadeClose: true,
              content: "${pageContext.request.contextPath}/credible/findAll.html?id=" + id,
              //数组第二项即吸附元素选择器或者DOM $('#openWindow')
            });
          } else {
            layer.msg("请选择入库的专家！", {
              offset: '100px'
            });
          }

        }
      }

      /**重置密码*/
      /* function resetPwd(){
        var id = $(":radio:checked").val();
        if(id !=null){
          $.ajax({
            url: "${pageContext.request.contextPath}/user/setPassword.do",
            data: {"typeId":id},
            type: "post",
            dataType: "json",
            success: function(data){
              if("sccuess" == data){
                layer.alert("重置成功！默认密码：123456",{offset: '100px'});
                   }else{
                     layer.msg("重置失败！",{offset: '100px'});
                   }
                 }
              });
          }else{
            layer.msg("请选择专家！",{offset: '100px'});
          }
      } */

      //禁用F12键及右键
      function click(e) {
        if(document.layers) {
          if(e.which == 3) {
            oncontextmenu = 'return false';
          }
        }
      }
      if(document.layers) {
        document.captureEvents(Event.MOUSEDOWN);
      }
      document.onmousedown = click;
      document.oncontextmenu = new Function("return false;");
      document.onkeydown = document.onkeyup = document.onkeypress = function() {
        if(window.event.keyCode == 123) {
          window.event.returnValue = false;
          return(false);
        }
      };
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
	    <input type="text" id="key" value="" class="empty" /><br/>
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
          <label class="fl">审核状态：</label>
          <span class="fl">
            <select name="status" id="status" class="w220">
               <option selected="selected" value=''>全部</option>
               <option <c:if test="${expert.status eq 'temporary' }">selected</c:if> value="temporary">临时</option>
               <option <c:if test="${expert.status eq '-3' }">selected</c:if> value="-3">公示中</option>
               <option <c:if test="${expert.status eq '0' and expert.auditTemporary == 1}">selected</c:if> value="firstInstance">初审中</option>
               <option <c:if test="${expert.status eq '4' and expert.auditTemporary == 2}">selected</c:if> value="review">复审中</option>
               <option <c:if test="${expert.status eq '6' and expert.auditTemporary == 3}">selected</c:if> value="reviewLook">复查中</option>
               <option <c:if test="${expert.status eq '-2' }">selected</c:if> value="-2">预复审合格</option>
               <option <c:if test="${expert.status eq '-1' }">selected</c:if> value="-1">暂存</option>
               <option <c:if test="${expert.status eq '0' and expert.auditTemporary == 0}">selected</c:if> value="0">待初审</option>
               <option <c:if test="${expert.status eq '1' }">selected</c:if> value="1">初审合格(待复审)</option>
               <option <c:if test="${expert.status eq '2' }">selected</c:if> value="2">初审不合格</option>
               <option <c:if test="${expert.status eq '3' }">selected</c:if> value="3">初审退回修改</option>
               <option <c:if test="${expert.status eq '4' and expert.auditTemporary == 0}">selected</c:if> value="4">复审已分配</option>
               <option <c:if test="${expert.status eq '5' }">selected</c:if> value="5">复审不合格</option>
               <option <c:if test="${expert.status eq '6' and expert.auditTemporary == 0}">selected</c:if> value="6">入库(待复查 )</option>
               <option <c:if test="${expert.status eq '7' }">selected</c:if> value="7">复查合格</option>
               <option <c:if test="${expert.status eq '8' }">selected</c:if> value="8">复查不合格</option>
               <option <c:if test="${expert.status eq '9' }">selected</c:if> value="9">初审退回再审核</option>
               <option <c:if test="${expert.status eq '10' }">selected</c:if> value="10">复审退回修改</option>
               <option <c:if test="${expert.status eq '11' }">selected</c:if> value="11">待分配</option>
               <option <c:if test="${expert.status eq '12' }">selected</c:if> value="12">处罚中</option>
               <option <c:if test="${expert.status eq '13' }">selected</c:if> value="13">无产品专家</option>
               <option <c:if test="${expert.status eq '14' }">selected</c:if> value="14">复审待分组专家</option>
               <option <c:if test="${expert.status eq '15' }">selected</c:if> value="15">预初审合格</option>
               <option <c:if test="${expert.status eq '16' }">selected</c:if> value="16">预初审不合格</option>
               <option <c:if test="${expert.status eq '17' }">selected</c:if> value="17">资料不全</option>
               <option <c:if test="${expert.status eq '18' }">selected</c:if> value="18">异议处理</option>
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
      </ul>
      <div class="col-md-12 clear tc mt10">
        <input class="btn mt1"  value="查询" type="submit">
        <input class="btn mt1" onclick="clearSearch();" value="重置" type="reset">
      </div>
      <div class="clear"></div>
    </form>
   </h2>
      <!-- 表格开始-->
      <div class="col-md-12 pl20 mt10">
        <%-- <button class="btn btn-windows edit" type="button" onclick="edit();">修改</button>
        <button class="btn btn-windows delete" type="button" onclick="dell();">删除</button>
        <button class="btn btn-windows check" type="button" onclick="shenhe();">审核</button>--%>
        <!-- <button class="btn btn-windows git" type="button" onclick="creadible();">诚信登记</button> -->
        <!-- <button class="btn btn-windows edit" type="button" onclick="resetPwd()">重置密码</button> -->
      </div>

      <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
          <thead>
            <tr>
              <!-- <th class="info w50">选择</th> -->
              <th class="info w50">序号</th>
              <th class="info">专家姓名</th>
              <!-- <th class="info">用户名</th> -->
              <th class="info">身份证号</th>
              <th class="info">性别</th>
              <th class="info">类别</th>
              <!-- <th class="info">毕业院校及专业</th> -->
              <th class="info w90">注册日期</th>
              <th class="info w90">提交日期</th>
              <th class="info w90">审核日期</th>
              <th class="info">手机</th>
              <!-- <th class="info">积分</th> -->
              <th class="info">专家类型</th>
              <th class="info">采购机构</th>
              <th class="info">专家状态</th>
            </tr>
          </thead>
          <c:forEach items="${result.list }" var="e" varStatus="vs">
            <tr class="pointer">
              <%-- <td class="tc w30"><input type="radio" name="check" id="checked" alt="" value="${e.id }"></td> --%>
              <td class="tc w50" class="tc w50">${(vs.index+1)+(result.pageNum-1)*(result.pageSize)}</td>
              <td>
                <c:choose>
                  <c:when test="${e.status eq '4' and e.isProvisional eq '1'}">
                    <a href="javascript:jumppage('${pageContext.request.contextPath}/expertQuery/temporaryExpert.html?expertId=${e.id}')">${e.relName}</a>
                  </c:when>
                  <c:otherwise>
                    <a href="javascript:jumppage('${pageContext.request.contextPath}/expertQuery/view.html?expertId=${e.id}&sign=1')">${e.relName}</a>
                  </c:otherwise>
                </c:choose>
              </td>
              <td class="tc">${e.idCardNumber}</td>
              <%-- <td class="tl pl20" >${e.loginName}</td> --%>
              <td class="tc w50">${e.gender}</td>
              <td class="tl">${e.expertsTypeId}</td>
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
              <%-- <td class="tc"  class="tc">${e.honestyScore }</td> --%>
              <td class="tc">${e.expertsFrom }</td>
              <td class="tc">${e.orgName }</td>
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
                  <span class="label rounded-2x label-u">预复审合格</span>
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
                  <span class="label rounded-2x label-dark">退回修改</span>
                </c:if>
                <c:if test="${e.status eq '4' and e.isProvisional eq '0' and e.auditTemporary == 0}">
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
                  <span class="label rounded-2x label-dark">初审退回再审核</span>
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
                <c:if test="${e.status eq '15' }">
                  <span class="label rounded-2x label-u">预初审合格</span>
                </c:if>
                <c:if test="${e.status eq '16' }">
                  <span class="label rounded-2x label-dark">预初审不合格</span>
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