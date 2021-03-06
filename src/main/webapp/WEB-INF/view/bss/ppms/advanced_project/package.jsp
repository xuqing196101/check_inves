<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp"%>
		<script type="text/javascript">
		  var clickState = 0;
      $(function() {
        var sure = document.getElementsByName("sure");
        for(var i = 0; i < sure.length; i++) {
          $(sure[i]).hide();
        }
        $("#addPack").click(function() {
          if(clickState == 1) {
            //如果状态为1就什么都不做
          } else {
             if(clickCortisone != 2) {
              clickState = 1;
            }
          }
        });
        
        //获取查看或操作权限
          var isOperate = $('#isOperate', window.parent.document).val();
          if(isOperate == 0) {
            //只具有查看权限，隐藏操作按钮
        $(":button").each(function(){ 
          $(this).hide();
              }); 
      } 
      });

      //全选方法
      function selectAll() {
        var info = document.getElementsByName("info");
        var selectAll = document.getElementById("selectAll");
        if(selectAll.checked) {
          for(var i = 0; i < info.length; i++) {
            info[i].checked = true;
          }
        } else {
          for(var i = 0; i < info.length; i++) {
            info[i].checked = false;
          }
        }
      }
      
      
      //包下勾选明细
      function selectedPackage(ele) {
        var flag = $(ele).prop("checked");
        var id = $(ele).next().val();
        var pId = $(ele).prev().val();
        if(flag) {
          //递归选中父节点
          checkedParent(pId);
          //递归选中子节点
          checkedChild(id);
        } else {
          //递归取消父节点选中
          noCheckedParent(pId);
          //递归取消子节点选中
          noCheckedChild(id);
        }
      }

      //勾选明细
      function selectedBox(ele) {
        var flag = $(ele).prop("checked");
        var id = $(ele).next().val();
        var pId = $(ele).prev().val();
        if(flag) {
          //递归选中父节点
          checkedParent(pId);
          //递归选中子节点
          checkedChild(id);
        } else {
          //递归取消父节点选中
          noCheckedParent(pId);
          //递归取消子节点选中
          noCheckedChild(id);
        }
      }
      
      //递归取消父节点选中
      function noCheckedParent(pId) {
        //判断子节点是否全部没有选中
        var isChecked = 0;
        $("input[name='pId_" + pId + "']").each(function() {
          var v = $(this).val();
          if($(this).next().prop("checked") == true) {
            isChecked = 1;
          }
        });
        if(isChecked == 0) {
          $("input[name='chkItem_" + pId + "']").each(function() {
            $(this).prev().prop("checked", false);
            var pId_v = $(this).prev().prev().val();
            noCheckedParent(pId_v);
          });
        }
      }
      
      //递归取消子节点选中
      function noCheckedChild(id) {
        //所有子节点取消选中
        $("input[name='pId_" + id + "']").each(function() {
          $(this).next().prop("checked", false);
          var currId = $(this).next().next().val();
          noCheckedChild(currId);
        });
      }

      //递归选中父节点
      function checkedParent(pId) {
        $("input[name='chkItem_" + pId + "']").each(function() {
          $(this).prev().prop("checked", true);
          var pId_v = $(this).prev().prev().val();
          checkedParent(pId_v);
        });
      }

      //递归选中子节点
      function checkedChild(id) {
        $("input[name='pId_" + id + "']").each(function() {
          $(this).next().prop("checked", true);
          var currId = $(this).next().next().val();
          checkedChild(currId);
        });
      }

      //修改包名
      function edit(obj) {
        var name = $(obj).parent().prev().find($("span[name='packageName']")).html();
        var packageId = $(obj).next().next().next().val();
        var content = "<input type='text' name='pack' value='" + name + "'/>";
        $(obj).parent().prev().find($("span[name='packageName']")).html(content);
        $(obj).parent().prev().find($("span[name='packageName']")).find($("input[name='pack']")).focus();
        $(obj).next().show();
        $(obj).hide();
      }

      //确定按钮
      function sure(obj) {
        var projectId = $("#projectId").val();
        var name = $(obj).parent().prev().find($("span[name='packageName']")).find($("input[name='pack']")).val();
        var packageId = $(obj).next().next().next().val();
        $.ajax({
          url: "${pageContext.request.contextPath }/advancedProject/editPackName.do?name=" + name + "&id=" + packageId,
          type: "post",
          success: function(data) {
            layer.msg('修改成功', {
              offset: ['45%', '50%']
            });
            $(obj).parent().prev().find($("span[name='packageName']")).html(name);
            $(obj).hide();
            $(obj).prev().show();
          }
        });
      }

      //删除明细
      function deleteDetail(obj, number) {
        var projectId = $("#projectId").val();
        var packageId = $(obj).next().val();
        var count = 0;
        var id = "";
        var info = document.getElementsByName("info" + number);
        for(var i = 0; i < info.length; i++) {
          if(info[i].checked == true) {
            count++;
          }
        }
        if(count == 0) {
          layer.alert("请选择一项", {
            offset: ['30%', '40%']
          });
          $(".layui-layer-shade").remove();
          return;
        }
        for(var i = 0; i < info.length; i++) {
          if(info[i].checked) {
            id += info[i].value + ',';
          }
        }
        layer.confirm('您确定要删除吗?', {
          title: '提示',
          offset: ['45%', '40%'],
          shade: 0.01
        }, function(index) {
          layer.close(index);
          $.ajax({
            type: "POST",
            url: "${pageContext.request.contextPath }/advancedProject/deleteDetailById.do?id=" + packageId + "&dId=" + id,
            success: function(data) {
              layer.msg('删除成功');
              window.location.href = "${pageContext.request.contextPath }/advancedProject/subPackage.do?projectId=" + projectId;
            }
          });
        });
      }

      var clickCortisone = 2;
      //添加分包
      function addPack() {
        var projectId = $("#projectId").val();
        var num = $("#num").val();
        var count = 0;
        var ids = "";
        var info = document.getElementsByName("info");
        for(var i = 0; i < info.length; i++) {
          if(info[i].checked == true) {
            count++;
          }
        }
        if(info.length > 0) {
          if(count == 0) {
            layer.alert("请选择明细", {
              offset: ['20%', '40%']
            });
            $(".layui-layer-shade").remove();
            return;
          }
        }
        for(var i = 0; i < info.length; i++) {
          if(info[i].checked) {
            ids += info[i].value + ',';
          }
        }
        if(info.length == 0) {
          layer.alert("项目中已无明细可以用于分包", {
            offset: ['30%', '40%']
          });
          $(".layui-layer-shade").remove();
          return;
        }
        if(clickState != 1) {
          clickState = 1;
          clickCortisone = 1;
          $.ajax({
            type: "POST",
            url: "${pageContext.request.contextPath }/advancedProject/addPack.do?id=" + ids + "&projectId=" + projectId,
            success: function(data) {
              clickState = 1;
              layer.msg('添加成功', {
                offset: ['40%', '45%']
              });
              window.location.href = "${pageContext.request.contextPath }/advancedProject/subPackage.do?projectId=" + projectId+"&num="+num;
            }
          });
        }
      }

      //返回
      function back() {
        window.location.href = "${pageContext.request.contextPath }/advancedProject/list.html";
      }


      //添加功能的明细全选
      function selectAddAll() {
        var info = document.getElementsByName("infoAdd");
        var selectAll = document.getElementById("selectAddAll");
        if(selectAll.checked) {
          for(var i = 0; i < info.length; i++) {
            info[i].checked = true;
          }
        } else {
          for(var i = 0; i < info.length; i++) {
            info[i].checked = false;
          }
        }
      }

      function selectedAddBox(ele) {
        var flag = $(ele).prop("checked");
        var id = $(ele).next().val();
        var pId = $(ele).prev().val();
        if(flag) {
          //递归选中父节点
          checkedParent(pId);
          //递归选中子节点
          checkedChild(id);
        } else {
          //递归取消父节点选中
          noCheckedParent(pId);
          //递归取消子节点选中
          noCheckedChild(id);
        }
      }

      var packId = "";
      //添加明细
      function addDetail(obj) {
        var info = document.getElementsByName("info");
        if(info.length==0){
          layer.alert("项目中已无明细可以添加", {
            offset: ['30%', '40%']
          });
          $(".layui-layer-shade").remove();
          return;
        }else{
	        layer.open({
	          type: 1,
	          title: '明细信息',
	          skin: 'layui-layer-rim',
	          shadeClose: true,
	          area: ['90%', '90%'],
	          offset: ['5%', '5%'],
	          content: $("#oddDetail")
	        });
	        $(".layui-layer-shade").remove();
	        packId = $(obj).next().next().val();
	      }
      }

      //取消
      function cancel() {
        layer.closeAll();
      }

      //包中添加明细功能
      function sureAdd() {
        var projectId = $("#projectId").val();
        var count = 0;
        var id = "";
        var info = document.getElementsByName("infoAdd");
        for(var i = 0; i < info.length; i++) {
          if(info[i].checked == true) {
            count++;
          }
        }
        if(count == 0) {
          layer.alert("请选择明细", {
            offset: ['20%', '40%']
          });
          $(".layui-layer-shade").remove();
          return;
        }
        for(var i = 0; i < info.length; i++) {
          if(info[i].checked) {
            id += info[i].value + ',';
          }
        }
        if(clickState != 1) {
           clickState = 1;
          clickCortisone = 1;
          $.ajax({
            type: "POST",
            url: "${pageContext.request.contextPath }/advancedProject/addDetailById.do?id=" + id + "&projectId=" + projectId + "&packageId=" + packId,
            success: function(data) {
              clickState = 1;
              layer.msg("添加成功");
              window.location.href = "${pageContext.request.contextPath }/advancedProject/subPackage.do?projectId=" + projectId;
            }
          });
        }
      }
      
      //上一步
      function backs(id){
        window.location.href = "${pageContext.request.contextPath }/advancedProject/edit.do?id=" + id;
      }
      
      //下一步
     function next(){
        var projectId = $("#projectId").val();
        $.ajax({
          type: "POST",
          dataType:"json",
          url: "${pageContext.request.contextPath }/advancedProject/judgeNext.do?projectId=" + projectId,
          success: function(data) {
            if(data==0){
               layer.alert("项目还有明细未分包，请先分包", {
                    offset: ['20%', '40%']
                  });
                  $(".layui-layer-shade").remove();
            }else if(data==1){
               layer.open({
                      type : 2, //page层
                      area : [ '800px', '500px' ],
                      title : '请上传项目批文',
                      shade : 0.01, //遮罩透明度
                      moveType : 1, //拖拽风格，0是默认，1是传统拖动
                      shift : 1, //0-6的动画形式，-1不开启
                      shadeClose : true,
                      content : '${pageContext.request.contextPath}/advancedProject/startProject.html?id=' + projectId,
                 });
            }
          }
        });
      }
      
      
      //隐藏包的信息
      function ycDiv(obj, index) {
        if($(obj).hasClass("shrink") && !$(obj).hasClass("spread")) {
          $(obj).removeClass("shrink");
          $(obj).addClass("spread");
        } else {
          if($(obj).hasClass("spread") && !$(obj).hasClass("shrink")) {
            $(obj).removeClass("spread");
            $(obj).addClass("shrink");
          }
        }
        if($("#handle" + index).hasClass("hide")) {
          $("#handle" + index).removeClass("hide");
        } else {
          $("#handle" + index).addClass("hide");
        }
        if($("#show" + index).hasClass("hide")) {
          $("#show" + index).removeClass("hide");
        } else {
          $("#show" + index).addClass("hide");
        }
      }
      
      
      //合并实施
      function merge(){
        var id = [];
        var projectId = $("#projectId").val();
        $('input[name="pName"]:checked').each(function() {
          id.push($(this).val());
        }); 
        if(id.length > 1){
          layer.confirm('选择合并实施的项目，只能编辑一套招标文件并同时开标', {
             title: '提示',
             shade: 0.01
           },
           function(index) {
             layer.close(index);
               $.ajax({
                 url: "${pageContext.request.contextPath}/advancedProject/merge.html?id=" + id,
                 data: {
                    "projectId" : projectId
                 },
                 type: "post",
                 dateType: "json",
                 success: function(result) {
                    if(result == "ok"){
                      window.location.href = "${pageContext.request.contextPath}/advancedProject/subPackage.html?projectId=" + projectId;
                    }
                   },
                 error: function() {
                   layer.msg("失败");
                 }
               });
          });
        } else if (id.length == 1){
          layer.msg("请选择多个");
        } else {
          layer.msg("请选择需要合并的包");
        }
      }
      
      //独立实施
      function independent(){
        var id = [];
        var projectId = $("#projectId").val();
        $('input[name="pName"]:checked').each(function() {
          id.push($(this).val());
        });
        if(id.length == 1){
          layer.confirm('确定选择独立实施？', {
             title: '提示',
             shade: 0.01
           },
           function(index) {
             layer.close(index);
               $.ajax({
                 url: "${pageContext.request.contextPath}/advancedProject/merge.html?id=" + id,
                 data: {
                    "projectId" : projectId
                 },
                 type: "post",
                 dateType: "json",
                 success: function(result) {
                    if(result == "ok"){
                      window.location.href = "${pageContext.request.contextPath}/advancedProject/subPackage.html?projectId=" + projectId;
                    } else {
                      layer.msg("失败");
                    }
                   },
                 error: function() {
                   layer.msg("失败");
                 }
               });
          });
        } else if (id.length > 1){
          layer.msg("只能选择一个");
        } else {
          layer.msg("请选择");
        }
      }
      
      function goback(){
        window.location.href = "${pageContext.request.contextPath }/advancedProject/findByPackage.html";
      }
		</script>
	</head>

	<body>
	    <div class="margin-top-10 breadcrumbs ">
	      <div class="container">
	        <ul class="breadcrumb margin-left-0">
	          <li>
	            <a href="javascript:void(0);">首页</a>
	          </li>
	          <li>
	            <a href="javascript:void(0);">保障作业</a>
	          </li>
	          <li>
	            <a href="javascript:void(0);">采购项目管理</a>
	          </li>
	        </ul>
	        <div class="clear"></div>
	      </div>
      </div> 
			<!-- 按钮开始-->
			<div class="container">
			<h2 class="tc dangan_file">项目名称：${project.name}</h2>
      <input type="hidden" id="projectId" value="${project.id}" />
      <input type="hidden" id="flowDefineId" value="${flowDefineId}"/>
      <div class="headline-v2">
        <h2>明细列表</h2>
      </div>
      <!-- 按钮开始-->
      <span class="star_red">(注)未合并实施的包，每个包将作为单独的项目分别实施</span>
			<div class="col-md-12 col-sm-12 co-xs-12 mb5 p0 mt10">
        <button class="btn btn-windows add" type="button" onclick="addPack()" id="addPack">添加分包</button>
      </div>

      <c:if test="${!empty list}">
        <div class="col-md-12 col-sm-12 col-xs-12 p0 over_auto" id="content">
          <table class="table table-bordered table-condensed lockout">
            <thead>
              <tr class="space_nowrap">
                <th class="choose"><input type="checkbox" id="selectAll" onclick="selectAll()"></th>
                <th class="seq">序号</th>
                <th class="department">需求部门</th>
                <th class="goodsname">产品名称</th>
                <th class="stand">规格型号</th>
                <th class="qualitstand">质量技术<br>标准</th>
                <th class="item">计量<br>单位</th>
                <th class="purchasecount">采购<br>数量</th>
                <th class="deliverdate">交货<br>期限</th>
                <th class="purchasetype">采购方式</th>
                <th class="purchasename">供应商名称</th>
                <c:if test="${project.isImport==1 }">
                  <th class="freetax">是否申请<br>办理免税</th>
                  <th class="goodsuse">物资用途<br>（进口）</th>
                  <th class="useunit">使用单位<br>（进口）</th>
                </c:if>
              </tr>
            </thead>
						<c:forEach items="${list}" var="obj">
              <tr style="cursor: pointer;">
                <td class="tc choose">
                  <input type="hidden" name="pId_${obj.parentId}" value="${obj.parentId}" />
                  <input type="checkbox" value="${obj.id}" name="info" onclick="selectedBox(this)">
                  <input type="hidden" name="chkItem_${obj.requiredId}" value="${obj.requiredId}" />
                </td>
                <td><div class="seq">${obj.serialNumber }</div></td>
                <td>
                  <div class="department">${obj.department }</div>
                </td>
                <td>
                  <div class="goodsname">${obj.goodsName}</div>
                </td>
                <td>
                  <div class="stand">${obj.stand}</div>
                </td>
                <td>
                  <div class="qualitstand">${obj.qualitStand}</div>
                </td>
                <td>
                  <div class="tc item">${obj.item}</div>
                </td>
                <td>
                  <div class="tc purchasecount">${obj.purchaseCount}</div>
                </td>
                <td>
                  <div class="deliverdate">${obj.deliverDate}</div>
                </td>
                <td>
                  <div class="purchasetype">
                        <c:forEach items="${kind}" var="kind">
                          <c:if test="${kind.id == obj.purchaseType}">${kind.name}</c:if>
                        </c:forEach>
                  </div>
                </td>
                <td>
                  <div class="purchasename">${obj.supplier}</div>
                </td>
                <c:if test="${project.isImport==1 }">
                  <td>
                    <div class="freetax">${obj.isFreeTax}</div>
                  </td>
                  <td>
                    <div class="goodsuse">${obj.goodsUse}</div>
                  </td>
                  <td>
                    <div class="useunit">${obj.useUnit}</div>
                  </td>
                </c:if>
              </tr>
            </c:forEach>
					</table>
				  </div>
			</c:if>

			<c:forEach items="${packageList }" var="pack" varStatus="p">
        <div class="col-md-12 col-sm-12 col-xs-12 p0 over_auto" id="contents">
          <div class="col-md-6 col-sm-6 col-xs-12 p0">
            <input type="checkbox" name="pName" value="${pack.id}"/>
            <span onclick="ycDiv(this,${p.index})" class="count_flow spread hand"></span>
            <span class="f16 b">包名：</span>
            <span class="f14 blue" name="packageName">${pack.name }</span>
          </div>
          <div class="col-md-6 col-sm-6 col-xs-12 tr p0 mb5" id="handle${p.index }">
            <input class="btn btn-windows edit" type="button" onclick="edit(this)" value="修改包名" />
            <input class="btn" name="sure" type="button" onclick="sure(this)" value="确定" />
            <input class="btn btn-windows add" type="button" onclick="addDetail(this)" value="添加" />
            <input class="btn btn-windows delete" type="button" onclick="deleteDetail(this,${p.index})" value="删除" />
            <input type="hidden" value="${pack.id }" />
          </div>
          <table class="table table-bordered table-condensed lockout" id="show${p.index }">
            <thead>
              <tr class="space_nowrap">
                <th class="choose">选择</th>
                <th class="seq">序号</th>
                <th class="department">需求部门</th>
                <th class="goodsname">产品名称</th>
                <th class="stand">规格型号</th>
                <th class="qualitstand">质量技术<br>标准</th>
                <th class="item">计量<br>单位</th>
                <th class="purchasecounts">采购<br>数量</th>
                <th class="deliverdate">交货<br>期限</th>
                <th class="purchasetype">采购方式<br>建议</th>
                <th class="purchasename">供应商名称</th>
                <c:if test="${pack.isImport==1 }">
                  <th class="freetax">是否申请<br>办理免税</th>
                  <th class="goodsuse">物资用途<br>（进口）</th>
                  <th class="useunit">使用单位<br>（进口）</th>
                </c:if>
              </tr>
            </thead>
						<c:forEach items="${pack.advancedDetails}" var="obj">
							<tr>
								<td class="choose">
								  <input type="hidden" name="pId_${obj.parentId}${p.index}" value="${obj.parentId}${p.index}" />
								  <input type="checkbox" name="info${p.index}" value="${obj.id}" onclick="selectedPackage(this)" />
								  <input type="hidden" name="chkItem_${obj.requiredId}${p.index}" value="${obj.requiredId}${p.index}" />
								</td>
								<td><div class="seq">${obj.serialNumber }</div></td>
								<td><div class="department">${obj.department}</div></td>
								<td><div class="goodsname">${obj.goodsName}</div></td>
								<td><div class="stand">${obj.stand}</div></td>
								<td><div class="qualitstand">${obj.qualitStand}</div></td>
								<td  class="tc"><div class="item">${obj.item}</div></td>
								<td  class="tc"><div class="purchasecount">${obj.purchaseCount}</div></td>
								<td><div class="deliverdate">${obj.deliverDate}</div></td>
								<td  class="">
								 <div class="purchasetype">
									<c:forEach items="${kind}" var="kind">
										<c:if test="${kind.id == obj.purchaseType}">${kind.name}</c:if>
									</c:forEach>
								 </div>
								</td>
								<td><div class="purchasename">${obj.supplier}</div></td>
								<c:if test="${pack.isImport==1 }">
									<td><div class="freetax">${obj.isFreeTax}</div></td>
									<td><div class="goodsuse">${obj.goodsUse}</div></td>
									<td><div class="useunit">${obj.useUnit}</div></td>
								</c:if>
							</tr>
						</c:forEach>
					</table>
					</div>
				</c:forEach>
		<div class="col-md-12 col-sm-12 col-xs-12 mt10 tc">
      <button class="btn" type="button" onclick="merge();">合并实施</button>
      <button class="btn" type="button" onclick="independent();">独立实施</button>
      <button class="btn" type="button" onclick="goback();">返回</button>
    </div>
		<c:if test="${!empty list}">
      <div class="content over_auto dnone" id="oddDetail">
        <table id="table" class="table table-bordered table-condensed table-hover table-striped lockout">
          <thead>
            <tr>
              <th class="choose"><input type="checkbox" id="selectAddAll" onclick="selectAddAll()"></th>
              <th class="seq">序号</th>
              <th class="department">需求部门</th>
              <th class="goodsname">产品名称</th>
              <th class="stand">规格型号</th>
              <th class="qualitstand">质量技术标准</th>
              <th class="item">计量<br>单位</th>
              <th class="purchasecounts">采购<br>数量</th>
              <th class="price">单价<br>（元）</th>
              <th class="budget">预算金额<br>（万元）</th>
              <th class="deliverdate">交货期限</th>
              <th class="purchasetype">采购方式</th>
              <th class="purchasename">供应商名称</th>
              <c:if test="${project.isImport==1 }">
                <th class="freetax">是否申请<br>办理免税</th>
                <th class="goodsuse">物资用途<br>（进口）</th>
                <th class="useunit">使用单位<br>（进口）</th>
              </c:if>
            </tr>
          </thead>
          <c:forEach items="${list}" var="obj">
            <tr class="tc">
              <td class="choose tc">
                <input type="hidden" name="pId_${obj.parentId}add" value="${obj.parentId}add" />
                <input type="checkbox" value="${obj.id}" name="infoAdd" onclick="selectedAddBox(this)">
                <input type="hidden" name="chkItem_${obj.requiredId}add" value="${obj.requiredId}add" />
              </td>
              <td class="seq">${obj.serialNumber }</td>
              <td><div class="department">${obj.department }</div></td>
              <td><div class="goodsname">${obj.goodsName}</div></td>
              <td><div class="stand">${obj.stand}</div></td>
              <td><div class="qualitstand">${obj.qualitStand}</div></td>
              <td><div class="item">${obj.item}</div></td>
              <td><div class="purchasecount">${obj.purchaseCount}</div></td>
              <td><div class="price">${obj.price}</div></td>
              <td><div class="budget">${obj.budget}</div></td>
              <td><div class="deliverdate">${obj.deliverDate}</div></td>
              <td>
                <div class="purchasetype">
                 <c:forEach items="${kind}" var="kind">
                  <c:if test="${kind.id == obj.purchaseType}">${kind.name}</c:if>
                 </c:forEach>
                </div>
              </td>
              <td><div class="purchasename">${obj.supplier}</div></td>
              <c:if test="${project.isImport==1 }">
                <td><div class="freetax">${obj.isFreeTax}</div></td>
                <td><div class="goodsuse">${obj.goodsUse}</div></td>
                <td><div class="useunit">${obj.useUnit}</div></td>
              </c:if>
            </tr>
          </c:forEach>
        </table>
        <!-- 按钮 -->
        <div class="col-md-12 col-sm-12 col-xs-12 mt10 tc">
          <button class="btn btn-windows save" type="button" onclick="sureAdd()">确定</button>
          <input class="btn btn-windows back" value="取消" type="button" onclick="cancel()"/>
        </div>
      </div>

    </c:if> 
		</div>
	</body>

</html>