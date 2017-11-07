<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
  	<%@ include file="/WEB-INF/view/common.jsp"%>
    <script type="text/javascript">
        $(function() {
			    laypage({
			      cont : $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
			      pages : "${list.pages}", //总页数
			      skin : '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			      skip : true, //是否开启跳页
			      total : "${list.total}",
			      startRow : "${list.startRow}",
			      endRow : "${list.endRow}",
			      groups : "${list.pages}" >= 3 ? 3 : "${info.pages}", //连续显示分页数
			      curr : function() { //通过url获取当前页，也可以同上（pages）方式获取
			        return "${list.pageNum}";
			      }(),
			      jump : function(e, first) { //触发分页后的回调
			        if (!first) { //一定要加此判断，否则初始时会无限刷新
			         $("#page").val(e.curr);
			         var name = $("#name").val();
			         var projectNumber = $("#projectNumber").val();
			         $("#sname").val(name);
			         $("#sprojectNumber").val(projectNumber);
			         $("#form1").submit();
			        }
			      }
			    });
			  });
      

      function checkInfo(ele) {
        var flag = $(ele).prop("checked");
        //父节点
        var pId = $(ele).prev().val();
        //当前对应的节点ID
        var zId = $(ele).next().val();
        if(flag){
          //勾选子节点
          checkedChild(zId);
        } else {
          //取消勾选
          noCheckedChild(pId);
        }
      }
      
      function checkedChild(id) {
        $("input[name='pId_" + id + "']").each(function() {
          $(this).next().prop("checked", true);
          var currId = $(this).next().next().val();
          checkedChild(currId);
        });
      }
      
      function noCheckedChild(id) {
        //所有子节点取消选中
        $("input[name='pId_" + id + "']").each(function() {
          $(this).next().prop("checked", false);
          var currId = $(this).next().next().val();
          noCheckedChild(currId);
        });
      }

      //移除
      function remove() {
        var ids = [];
        var pId = [];
        $('input[name="chkItems"]:checked').each(function() {
          ids.push($(this).val());
          pId.push($(this).prev().val());
        });
        if(ids.length > 0) {
          var name = $("#name").val();
          var projectNumber = $("#projectNumber").val();
          window.location.href = "${pageContext.request.contextPath}/project/delete.html?ids=" + ids + "&id=${id}" + "&name=" + name + "&projectNumber=" + projectNumber + "&pId=" + pId;
        } else {
          layer.msg("请选择移除的信息", {
            offset: ['180px', '200px'],
            shade: 0.01
          });
        }
      }

      // 返回
      function back(){
        var id = $("#id").val();
        var ix = "${ix}";
        if(ix){
        	window.history.go(-1);
        } else {
        	window.location.href = "${pageContext.request.contextPath}/project/deleted.html?id="+id;
        }
      }
      
      //获取采购明细
      function chooce(taskId, id, name, projectNumber) {
        var name = $("#name").val();
        var projectNumber = $("#projectNumber").val();
        var orgId = $("#orgId").val();
        window.location.href = "${pageContext.request.contextPath}/project/addDetails.html?taskId=" + taskId + "&id=" + id + "&name=" + name + "&projectNumber=" + projectNumber+"&orgId="+orgId;
      }

      
       function verify() {
      	var bool = true;
        var projectNumber = $("#projectNumber").val();
        $.ajax({
          url: "${pageContext.request.contextPath}/project/verify.html",
          type: "post",
          data: {"projectNumber" : projectNumber},
          dataType: "text",
          async: false,
          success: function(data) {
          		if(data == "false"){
          			$("#sps").html("项目编号已存在").css('color', 'red');
          			bool = false;
          		} else {
          			$("#sps").html("");
          		}
          },
        });
        return bool;
      }

      function nextStep() {
        var name = $("#name").val();
        var projectNumber = $("#projectNumber").val();
        var chkItems = $("input[name='chkItems']").val();
        var ids = [];
        $('input[name="chkItems"]').each(function() {
          ids.push($(this).val());
        });
        chkItems = $.trim(chkItems);
        name = $.trim(name);
        projectNumber = $.trim(projectNumber);
        if(name == "") {
          layer.tips("项目名称不允许为空", "#name");
          $("#name").focus();
        } else if(projectNumber == "") {
          layer.tips("项目编号不允许为空", "#projectNumber");
          $("#projectNumber").focus();
        }else if(!chkItems){
          layer.alert("请选择明细");
        } else {
        	var bool = verify();
        	if(bool){
        		var nameTr=document.getElementsByName("attr");
        	   var checkName=[];
        	   for(var i=0;i<nameTr.length;i++){
	        	     checkName.push($(nameTr[i]).children()[9]);
        	   }
        	   for(var i=0;i<checkName.length;i++){
        	      if(i != 0){
        	        if($(checkName[i]).text()!=$(checkName[i-1]).text()){
        	           layer.msg("采购方式不一样");
        	           return false;
        	        }
        	      }
        	   }
        		layer.open({
	             type: 2, //page层
	             area: ['800px', '500px'],
	             title: '请上传项目批文',
	             shade: 0.01, //遮罩透明度
	             moveType: 1, //拖拽风格，0是默认，1是传统拖动
	             shift: 1, //0-6的动画形式，-1不开启
	             shadeClose: true,
	             content: '${pageContext.request.contextPath}/project/nextStep.html?id=${id}'+ '&name=' + name + '&projectNumber=' + projectNumber+'&checkId='+ids,
	          });
        	} else {
        		$("#sps").html("项目编号已存在").css('color', 'red');
        		$("#projectNumber").focus();
        	}
        }
      }
      
      function hold(){
      	var id = $("#id").val();
      	var name = $("#name").val();
        var projectNumber = $("#projectNumber").val();
        var chkItems = $("input[name='chkItems']").val();
        chkItems = $.trim(chkItems);
        name = $.trim(name);
        projectNumber = $.trim(projectNumber);
        if(name == "") {
          layer.tips("项目名称不允许为空", "#name");
          $("#name").focus();
        } else if(projectNumber == "") {
          layer.tips("项目编号不允许为空", "#projectNumber");
          $("#projectNumber").focus();
        }else if(!chkItems){
          layer.alert("请选择明细");
        } else {
        	$.ajax({
	          url: "${pageContext.request.contextPath}/project/hold.html",
	          type: "post",
	          data: {"id" : id},
	          dataType: "text",
	          async: false,
	          success: function(data) {
	          		if(data == "ok"){
	          			layer.msg("暂存成功");
	          		} else {
	          			layer.msg("暂存失败");
	          		}
	          },
	        });
        }
      }
      
      //重置
      function resetResult(){
    	  $("#planName").val("");
    	  $("#orgName").val("");
    	  $("#documentNumber").val("");
      }
      
      //查询
      function query(){
        var name = $("#name").val();
        var projectNumber = $("#projectNumber").val();
        $("#sname").val(name);
        $("#sprojectNumber").val(projectNumber);
        $("#form1").submit();
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
            <a href="javascript:void(0)">采购项目管理</a>
          </li>
          <li>
            <a href="javascript:jumppage('${pageContext.request.contextPath}/project/listProject.html')">立项管理</a>
          </li>
          <li class="active">
            <a href="javascript:void(0)">新建采购项目</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    <div class="container container_box">
        <div>
          <h2 class="count_flow"><i>1</i>添加信息</h2>
          <ul class="ul_list">
            <li class="col-md-3 col-sm-6 col-xs-12 pl15">
              <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="star_red">*</div>项目名称</span>
              <div class="input-append input_group col-sm-12 col-xs-12 p0">
                <input type="text" id="name" class="input_group" name="name" value="${name}" />
                <input type="hidden" id="orgId" class="input_group" name="orgId" value="${orgId}" />
                <span class="add-on">i</span>
                <div class="cue">${ERR_name}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12 pl15">
              <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="star_red">*</div>项目编号</span>
              <div class="input-append input_group col-sm-12 col-xs-12 p0">
                <input id="projectNumber" type="text" class="input_group" name="projectNumber" onblur="verify(this);" value="${projectNumber}" />
                <span class="add-on">i</span>
                <div class="cue" id="sps">${ERR_projectNumber}</div>
              </div>
            </li>
          </ul>
        </div>
        <div class="padding-top-10 clear">
          <h2 class="count_flow"><i>2</i>选择采购明细</h2>
          <!-- 项目戳开始 -->
				<h2 class="search_detail ml0">
				  <form action="${pageContext.request.contextPath}/project/add.html" id="form1" method="post" class="mb0">
						<ul class="demand_list">
					  	<li>
					    	<label class="fl">采购任务名称：</label>
					    	<input type="hidden" name="page" id="page">
								<span><input type="text" name="planName" id="planName" value="${planName}" /></span>
								<input type="hidden" id="id" class="input_group" name="id" value="${id}" />
								<input type="hidden" name="ix" value="${ix}" />
					  	</li>
			        <li>
			          <label class="fl">采购管理部门：</label>
			          <span><input type="text" name="orgName" id="orgName" value="${orgName}"/></span>
			        </li>
			        <li>
			          <label class="fl">采购任务文号：</label>
			          <span><input type="text" name="documentNumber" id="documentNumber" value="${documentNumber }"/></span>
			        </li>
						</ul>
						<input type="hidden" id="sname" name="name"/>
						<input type="hidden" id="sprojectNumber" name="projectNumber"/>
					  <button class="btn fl mt1" type="button" onclick="query()">查询</button>
					  <button class="btn fl" type="button" onclick="resetResult()">重置</button>
						<div class="clear"></div>
						</form>
				</h2>
          <div class="ul_list">
            <div class="content table_box pl0">
              <table class="table table-bordered table-condensed table-hover">
                <thead>
                  <tr class="info">
                    <th class="w50">序号</th>
                    <th>采购任务名称</th>
                    <th>采购管理部门</th>
                    <th>采购任务文号</th>
                    <th>下达时间</th>
                    <th>
                      <div class="star_red">*</div>操作
                    </th>
                  </tr>
                </thead>
                <tbody id="task_id">
                  <c:forEach items="${list.list}" var="obj" varStatus="vs">
                    <tr class="pointer">
                      <td class="tc w50">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
                      <td class="pl20">${obj.name}</td>
                      <td class="pl20">${obj.orgId}</td>
                      <td class="pl20">${obj.documentNumber}</td>
                      <td class="tc">
                        <fmt:formatDate value="${obj.giveTime }" />
                      </td>
                      <td class="tc w30">
                        <button type="button" class="btn" onclick="chooce('${obj.id }','${id}','${name}','${projectNumber}')">选择</button>
                      </td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
              <div id="pagediv" align="right"></div>
            </div>
  
            <div id="hide_detail">
              <c:if test="${lists ne null }">
              <div id="remove" class="col-md-12 col-sm-12 co-xs-12 mb5 p0 mt10">
                <button class="btn" type="button" onclick="remove()">移除</button>
              </div>
              <div class="col-md-12 col-sm-12 col-xs-12 p0 over_auto" id="content">
                <table id="table" class="table table-bordered table-condensed lockout">
                  <thead>
                    <tr class="space_nowrap">
                      <th class="choose">操作</th>
                      <th class="info seq">序号</th>
                      <th class="info department">需求部门</th>
                      <th class="info goodsname">物资类别<br/>及名称</th>
                      <th class="info stand">规格型号</th>
                      <th class="info qualitstand">质量技术标准<br/>(技术参数)</th>
                      <th class="info item">计量<br/>单位</th>
                      <th class="info purchasecount">采购<br/>数量</th>
                      <th class="info deliverdate">交货<br/>期限</th>
                      <th class="info purchasetype">采购方式</th>
                      <th class="info purchasename">供应商名称</th>
                      <!-- <th class="info freetax">是否申请<br/>办理免税</th>
                      <th class="info goodsuse">物资用途<br/>（进口）</th>
                      <th class="info useunit">使用单位<br/>（进口）</th> -->
                      <th class="memo">备注</th>
                    </tr>
                  </thead>
                  <c:forEach items="${lists}" var="obj" varStatus="vs">
                    <tr class="pointer" <c:if test="${obj.price ne null && obj.purchaseCount ne null}">name="attr"</c:if>>
                      <td>
                       <div class="choose">
                        <input type="hidden" name="pId_${obj.parentId}" value="${obj.parentId}"/>
                        <input type="checkbox" value="${obj.id}" name="chkItems" onclick="checkInfo(this)" alt="">
                        <input type="hidden" name="id_${obj.requiredId}" value="${obj.requiredId}">
                       </div>
                      </td>
                      <td class="seq"> ${obj.serialNumber}
                      </td>
                      <td class="tl">
                         <c:if test="${obj.price eq null}">
		                    <div class="department">${obj.department}</div>
		                    </c:if>
                      </td>
                      <td class="tl">
                         <div class="goodsname">${obj.goodsName}</div>
                      </td>
                      <td class="tl"> <div class="stand">${obj.stand}</div>
                      </td>
                      <td class="tl"><div class="qualitstand">${obj.qualitStand}</div>
                      </td>
                      <td class="tc"><div class="item">${obj.item}</div>
                      </td>
                      <td class="tc"><div class="purchasecount">${obj.purchaseCount}</div>
                      </td>
                      <td class="tl"><div class="deliverdate">${obj.deliverDate}</div>
                      </td>
                      <td class="tc">
                       <div class=purchasetype>
                         <c:choose>
                            <c:when test="${obj.detailStatus==0 }">
                            </c:when>
                            <c:otherwise>
                              <c:forEach items="${kind}" var="kind">
			                    <c:if test="${kind.id eq obj.purchaseType}">${kind.name}</c:if>
			                  </c:forEach>
                            </c:otherwise>
                         </c:choose>
                        </div>
                      </td>
                      <td class="tl"><div class="purchasename">${obj.supplier}</div>
                      </td>
                      <%-- <td class="tc"><div class="freetax">${obj.isFreeTax}</div>
                      </td>
                      <td class="tl"><div class="goodsuse">${obj.goodsUse}</div>
                      </td>
                      <td class="tl"><div class="useunit">${obj.useUnit}</div>
                      </td> --%>
                      <td class="tl"><div class="memo">${obj.memo}</div>
                      </td>
                    </tr>
                  </c:forEach>
                </table>
              </div>
              </c:if>
            </div>
          </div>
        </div>
        <div class="col-md-12 col-xs-12 col-sm-12 tc mt20">
        	<c:if test="${ix eq null}">
        		<button class="btn" onclick="hold()" type="button">暂存</button>
        	</c:if>
          <button class="btn" onclick="nextStep()" type="button">确定</button>
          <button class="btn btn-windows back" onclick="back()" type="button">返回</button>
        </div>
    </div>

  </body>

</html>