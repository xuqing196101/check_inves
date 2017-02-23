<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.6.1.min.js"></script>
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
            //如果状态不是1  则添加状态 1
            //setTimeout("addPack()", 300);
          }
        })
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

      //包下面全选明细功能
      function selectAllDetail(number) {
        var info = document.getElementsByName("info" + number);
        var selectAll = document.getElementById("selectAll" + number);
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

      //勾选明细
      function selectedBox(ele) {
        var projectId = $("#projectId").val();
        var flag = $(ele).prop("checked");
        var id = $(ele).val();
        $.ajax({
          url: "${pageContext.request.contextPath }/project/checkProjectDeail.do?id=" + id + "&projectId=" + projectId,
          async: false, //请求是否异步，默认为异步
          type: "post",
          dataType: "json",
          success: function(result) {
            for(var i = 0; i < result.length; i++) {
              $("input[name='info']").each(function() {
                var v1 = result[i].id;
                var v2 = $(this).val();
                if(v1 == v2) {
                  $(this).prop("checked", flag);
                }
              });
            }
          }
        });
        var count = 0;
        var len = 0;
        var info = document.getElementsByName("info");
        var selectAll = document.getElementById("selectAll");
        for(var i = 0; i < info.length; i++) {
          if(info[i].checked == true) {
            count++;
          }
          len++;
        }
        if(count == len) {
          selectAll.checked = true;
        } else {
          selectAll.checked = false;
        }
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
        if($.trim(name) == "" || name == null) {
          layer.alert("包名不能为空", {
            offset: ['30%', '40%']
          });
          $(".layui-layer-shade").remove();
          return;
        } else {
          var packageId = $(obj).next().next().next().val();
          $.ajax({
            url: "${pageContext.request.contextPath }/project/editPackName.do?name=" + name + "&id=" + packageId,
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
          layer.alert("请选择明细", {
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
            url: "${pageContext.request.contextPath }/project/deleteDetailById.do?id=" + packageId + "&dId=" + id,
            success: function(data) {
              layer.msg('删除成功', {
                offset: ['45%', '50%']
              });
              window.location.href = "${pageContext.request.contextPath }/project/subPackage.do?id=" + projectId;
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
            url: "${pageContext.request.contextPath }/project/addPack.do?id=" + ids + "&projectId=" + projectId + "&num=" + num,
            success: function(data) {
              var datas = eval("(" + data + ")");
              if(datas == false) {
                layer.alert("一个包只能有一个供应商");
              } else {
                layer.msg('添加成功', {
                  offset: ['40%', '45%']
                });
                window.location.href = "${pageContext.request.contextPath }/project/subPackage.do?id=" + projectId + "&num=" + num;
              }
            }
          });
        }
      }

      //返回
      function back() {
        window.location.href = "${pageContext.request.contextPath }/project/listProject.html";
      }

      //包下勾选明细
      function selectedPackage(ele, number) {
        /*var ids = "";
        var pInfo = document.getElementsByName("info" + number);
        for(var i = 0; i < pInfo.length; i++) {
          if(pInfo[i].checked) {
            ids += pInfo[i].value + ',';
          }
        }**/
        var projectId = $("#projectId").val();
        var flag = $(ele).prop("checked");
        var id = $(ele).val();
        $.ajax({
          url: "${pageContext.request.contextPath }/project/checkProjectDeail.do?id=" + id + "&projectId=" + projectId,
          type: "post",
          async: false, //请求是否异步，默认为异步
          dataType: "json",
          success: function(result) {
            for(var i = 0; i < result.length; i++) {
              $("input[name='info" + number + "']").each(function() {
                var v1 = result[i].id;
                var v2 = $(this).val();
                if(v1 == v2) {
                  $(this).prop("checked", flag);
                }
              });
            }
          }
        });
        var count = 0;
        var len = 0;
        var info = document.getElementsByName("info" + number);
        var selectAll = document.getElementById("selectAll" + number);
        for(var i = 0; i < info.length; i++) {
          if(info[i].checked == true) {
            count++;
          }
          len++;
        }
        if(count == len) {
          selectAll.checked = true;
        } else {
          selectAll.checked = false;
        }
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
        var projectId = $("#projectId").val();
        var flag = $(ele).prop("checked");
        var id = $(ele).val();
        $.ajax({
          url: "${pageContext.request.contextPath }/project/checkProjectDeail.do?id=" + id + "&projectId=" + projectId,
          async: false, //请求是否异步，默认为异步
          type: "post",
          dataType: "json",
          success: function(result) {
            for(var i = 0; i < result.length; i++) {
              $("input[name='infoAdd']").each(function() {
                var v1 = result[i].id;
                var v2 = $(this).val();
                if(v1 == v2) {
                  $(this).prop("checked", flag);
                }
              });
            }
          }
        });
        var count = 0;
        var len = 0;
        var info = document.getElementsByName("infoAdd");
        var selectAll = document.getElementById("selectAddAll");
        for(var i = 0; i < info.length; i++) {
          if(info[i].checked == true) {
            count++;
          }
          len++;
        }
        if(count == len) {
          selectAll.checked = true;
        } else {
          selectAll.checked = false;
        }
      }

      var packId = "";
      //添加明细
      function addDetail(obj) {
        var info = document.getElementsByName("info");
        if(info.length == 0) {
          layer.alert("项目中已无明细可以添加", {
            offset: ['30%', '40%']
          });
          $(".layui-layer-shade").remove();
          return;
        } else {
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
            url: "${pageContext.request.contextPath }/project/addDetailById.do?id=" + id + "&projectId=" + projectId + "&packageId=" + packId,
            success: function(data) {
              layer.msg('添加成功', {
                offset: ['40%', '45%']
              });
              window.location.href = "${pageContext.request.contextPath }/project/subPackage.do?id=" + projectId;
            }
          });
        }
      }

      //上一步
      function backs(id) {
        window.location.href = "${pageContext.request.contextPath }/project/edit.do?id=" + id;
      }

      //下一步
      function next() {
        var projectId = $("#projectId").val();
        $.ajax({
          type: "POST",
          dataType: "json",
          url: "${pageContext.request.contextPath }/project/judgeNext.do?projectId=" + projectId,
          success: function(data) {
            if(data == 0) {
              layer.alert("项目还有明细未分包，请先分包", {
                offset: ['20%', '40%']
              });
              $(".layui-layer-shade").remove();
            } else if(data == 1) {
              layer.open({
                type: 2, //page层
                area: ['800px', '500px'],
                title: '请上传项目批文',
                shade: 0.01, //遮罩透明度
                moveType: 1, //拖拽风格，0是默认，1是传统拖动
                shift: 1, //0-6的动画形式，-1不开启
                shadeClose: true,
                content: '${pageContext.request.contextPath}/project/startProject.html?id=' + projectId,
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
    </script>
    
    
  </head>

  <body>
    <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
          <li>
            <a href="#">首页</a>
          </li>
          <li>
            <a href="#">保障作业</a>
          </li>
          <li>
            <a href="#">采购项目管理</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <div class="container">
      <h2 class="tc dangan_file">项目名称：${project.name}</h2>
      <input type="hidden" id="projectId" value="${project.id }" />
      <div class="headline-v2">
        <h2>明细列表</h2>
      </div>

      <!-- 按钮开始-->
      <div class="col-md-12 col-sm-12 co-xs-12 mb5 p0 mt10">
        <button class="btn btn-windows add" type="button" onclick="addPack()" id="addPack">添加分包</button>
        <input class="btn btn-windows back" value="返回" type="button" onclick="back()">
        <span class="fr mt10">项目编号：${project.projectNumber}</span>
      </div>

      <c:if test="${!empty list}">
        <div class="col-md-12 col-sm-12 col-xs-12 p0 over_scroll" id="content">
          <table id="table" class="table table-bordered table-condensed"  style="width: 1600px; color: #000000; font-size: medium;">
            <thead>
              <tr class="space_nowrap">
                <th class="choose"><input type="checkbox" id="selectAll" onclick="selectAll()"></th>
                <th class="seq">序号</th>
                <th class="department">需求部门</th>
                <th class="w80">物资名称</th>
                <th class="w80">规格型号</th>
                <th class="w80">质量技术<br>标准</th>
                <th class="w80">计量<br>单位</th>
                <th class="w80">采购<br>数量</th>
                <!-- <th class="price">单价<br>（元）</th>
                <th class="budget">预算金额<br>（万元）</th> -->
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
                <td><div class="choose"><input type="checkbox" value="${obj.id }" name="info" onclick="selectedBox(this)"></div></td>
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
               <%--  <td>
                  <div class="price">${obj.price}</div>
                </td>
                <td>
                  <div class="budget">${obj.budget}</div>
                </td> --%>
                <td>
                  <div class="deliverdate">${obj.deliverDate}</div>
                </td>
                <td>
                  <div class="purchasetype">
                    <c:choose>
                      <c:when test="${obj.detailStatus==0 }">
                      </c:when>
                      <c:otherwise>
                        <c:forEach items="${kind}" var="kind">
                          <c:if test="${kind.id == obj.purchaseType}">${kind.name}</c:if>
                        </c:forEach>
                      </c:otherwise>
                    </c:choose>
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
        <div class="col-md-12 col-sm-12 col-xs-12 p0 over_scroll" id="contents">
          <div class="col-md-6 col-sm-6 col-xs-12 p0">
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

          <table class="table table-bordered table-condensed" id="show${p.index }">
            <thead>
              <tr class="space_nowrap">
                <th class="choose">选择</th>
                <th class="seq">序号</th>
                <th class="department">需求部门</th>
                <th class="goodsname">物资名称</th>
                <th class="stand">规格型号</th>
                <th class="qualitstand">质量技术<br>标准</th>
                <th class="item">计量<br>单位</th>
                <th class="purchasecounts">采购<br>数量</th>
                <%--<th class="price">单价（元）</th>
                <th class="budget">预算金额（万元）</th>
                --%>
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
            <c:forEach items="${pack.projectDetails}" var="obj">
              <tr>
                <td><div class="choose"><input type="checkbox" name="info${p.index }" value="${obj.id }" onclick="selectedPackage(this,${p.index})" /></div></td>
                <td><div class="seq">${obj.serialNumber }</div></td>
                <td>
                  <%--<c:if test="${orgnization.id == obj.department}"> 
                           ${orgnization.name}
                       </c:if>--%>
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
                  <div class="item">${obj.item}</div>
                </td>
                <td>
                  <div class="purchasecount">${obj.purchaseCount}</div>
                </td>
                <%--<td>
                  <div class="price">${obj.price}</div>
                 </td>
                <td><div class="budget">${obj.budget}</div></td>
                --%>
                <td>
                  <div class="deliverdate">${obj.deliverDate}</div>
                </td>
                <td>
                  <div class="purchasetype">
                    <c:choose>
                      <c:when test="${obj.detailStatus==0 }">
                      </c:when>
                      <c:otherwise>
                        <c:forEach items="${kind}" var="kind">
                          <c:if test="${kind.id == obj.purchaseType}">${kind.name}</c:if>
                        </c:forEach>
                      </c:otherwise>
                    </c:choose>
                  </div>
                </td>
                <td>
                  <div class="purchasename">${obj.supplier}</div>
                </td>
                <c:if test="${pack.isImport==1 }">
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
      </c:forEach>
    </div>

    <!-- 按钮 -->
    <div class="col-md-12 col-sm-12 col-xs-12 mt10 tc">
      <input type="hidden" id="num" value="${num}" />
      <c:if test="${num eq 1}">
        <button class="btn" type="button" onclick="JavaScript:history.go(-1)">上一步</button>
      </c:if>
      <c:if test="${num eq 0}">
        <button class="btn" type="button" onclick="backs('${project.id}')">上一步</button>
      </c:if>
      <%--  --%>

      <button class="btn" type="button" onclick="next();">下一步</button>
    </div>

    <c:if test="${!empty list}">
      <div class="content over_scroll dnone" id="oddDetail">
        <table id="table" class="table table-bordered table-condensed table-hover table-striped">
          <thead>
            <tr class="info">
              <th><div class="choose"><input type="checkbox" id="selectAddAll" onclick="selectAddAll()"></div></th>
              <th class="seq">序号</th>
              <th class="department">需求部门</th>
              <th class="goodsname">物资名称</th>
              <th class="stand">规格型号</th>
              <th class="qualitstand">质量技术<br>标准</th>
              <th class="item">计量<br>单位</th>
              <th class="purchasecount">采购<br>数量</th>
              <!-- <th class="price">单价<br>（元）</th>
              <th class="budget">预算金额<br>（万元）</th> -->
              <th class="deliverdate">交货期限</th>
              <th class="purchasetype">采购方式</th>
              <th class="purchasename">供应商名称</th>
              <c:if test="${project.isImport==1 }">
                <th class="freetax">是否申请<br>办理免税</th>
                <th class="goodsuse">物资用途<br>（进口）</th>
                <th class="goodsuse">使用单位<br>（进口）</th>
              </c:if>
            </tr>
          </thead>
          <c:forEach items="${list}" var="obj">
            <tr>
              <td><div class="choose"><input type="checkbox" value="${obj.id }" name="infoAdd" onclick="selectedAddBox(this)"></div></td>
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
                <div class="item">${obj.item}</div>
              </td>
              <td>
                <div class="purchasecount">${obj.purchaseCount}</div>
              </td>
              <%-- <td>
                <div class="price">${obj.price}</div>
              </td>
              <td>
                <div class="budget">${obj.budget}</div>
              </td> --%>
              <td>
                <div class="deliverdate">${obj.deliverDate}</div>
              </td>
              <td>
                <div class="purchasetype">
                  <c:choose>
                    <c:when test="${obj.detailStatus==0 }">
                    </c:when>
                    <c:otherwise>
                      <c:forEach items="${kind}" var="kind">
                        <c:if test="${kind.id == obj.purchaseType}">${kind.name}</c:if>
                      </c:forEach>
                    </c:otherwise>
                  </c:choose>
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
        <!-- 按钮 -->
        <div class="col-md-12 col-sm-12 col-xs-12 mt10 tc">
          <button class="btn btn-windows save" type="button" onclick="sureAdd()">确定</button>
          <input class="btn btn-windows back" value="取消" type="button" onclick="cancel()" />
        </div>
      </div>
    </c:if>
  </body>

</html>