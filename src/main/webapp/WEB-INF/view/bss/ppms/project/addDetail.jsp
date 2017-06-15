<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script src="${pageContext.request.contextPath}/public/webuploadFT/layui/layui.js"></script>
    <script type="text/javascript">
      var obj="";
      $(function() {
          layui.use('flow', function() {
            var flow = layui.flow;
            flow.load({
              elem: '#tb_id' //指定列表容器
              ,done: function(page, next) { //到达临界点（默认滚动触发），触发下一页
                var lis = [];
                //以jQuery的Ajax请求为例，请求下一页数据
                $.ajax({
                  url: "${pageContext.request.contextPath}/project/viewPlanDetail.do?taskId=${taskId}&page=" + page,
                  type: "get",
                  dataType: "json",
                  success: function(res) {
                   var ch=$("#tb_id").children();
                   if(ch.length>1){
                     for(var i=0;i<ch.length;i++){
                        var tds=$(ch[i]).children()[0];
                        if($(tds).children()[0]==obj){
                          check($(tds).children()[0])
                          break;
                        }
                     }
                   
                   }
                    layui.each(res.data, function(index, item) {
                      var code = "";
                      if(item.oneAdvice == "DYLY") {
                        code = item.supplier;
                      }
                      if(item.purchaseCount == 0) {
                        item.purchaseCount = "";
                      }
                      var html = "<tr class='pointer'><td><input type='checkbox' value='"+item.id+"' name='chkItem' onclick='check(this)'  alt=''></td><td><div class='seq'>" + item.seq + "</div></td><td><div class='department'>" +
                        item.department + "</div></td><td><div class='goodsname'>" + item.goodsName + "</div></td><td><div class='stand'>" + item.stand + "</div></td><td><div class='qualitStand'>" +item.qualitStand + "</div></td><td><div class='item'>" + 
                        item.item + "</div></td><td><div class='purchaseCount'>" + item.purchaseCount + "</div></td><td><div class='deliverDate'>" + item.deliverDate + "</div></td><td><div class='purchaseType tc'>" + item.purchaseType + 
                        "</div></td><td><div class='purchasename'>" + code + "</div></td><td><div class='memo'>"+item.memo+"</div><input type='hidden' id='planType' value='"+item.planType+"' /></td></tr>";
                      lis.push(html);
                    });
                    next(lis.join(''), page < res.pages);
                  },
                });
              }
            });
          });
        });
        
        
        function check(ele) {
          obj=ele;
          var flag = $(ele).prop("checked");
          var id = $(ele).val();
          $.ajax({
            url: "${pageContext.request.contextPath}/project/checkDeail.html",
            data: {"id" : id, "flag" : flag},
            type: "post",
            dataType: "json",
            success: function(result) {
              for(var i = 0; i < result.length; i++) {
                $("input[name='chkItem']").each(function() {
                  var v1 = result[i].id;
                  var v2 = $(this).val();
                  if(v2 == v1) {
                    $(this).prop("checked", flag);
                  }
                });
              }
  
            },
  
            error: function() {
              layer.msg("失败", {
                offset: ['222px', '390px']
              });
            }
          });

      }
      
      function save() {
        var checkIds = [];
        $('input[name="chkItem"]:checked').each(function() {
          checkIds.push($(this).val());
        });
        if(checkIds.length < 1) {
          layer.alert("请勾选明细", "#tb_id");
        } else {
          $.ajax({
            url: "${pageContext.request.contextPath}/project/purchaseType.html",
            data: "id=" + checkIds,
            type: "post",
            dataType: "json",
            success: function(result) {
              if(result == "1") {
                if(checkIds.length > 0) {
                  var checked;
                  var unCheckedBoxs = [];
                  $('input[name="chkItem"]:not(:checked)').each(function() {
                    unCheckedBoxs.push($(this).val());
                  });
                  if(unCheckedBoxs < 1) {
                    checked = 0;
                  } else {
                    checked = 1;
                  }
                  var planType = $("#planType").val();
                  $("#planTypes").val(planType);
                  $("#detail_id").val(checkIds);
                  $("#save_form_id").submit();
                }
              } else {
                layer.alert("采购方式不相同", {
                  shade: 0.01
                });

              }
            },
            error: function() {
              layer.msg("失败", {
                offset: ['222px', '390px']
              });
            }
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
            <a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
          </li>
          <li>
            <a href="javascript:void(0)">保障作业</a>
          </li>
          <li>
            <a href="javascript:void(0)">项目管理</a>
          </li>
          <li>
            <a href="javascript:jumppage('${pageContext.request.contextPath}/project/listProject.html')">立项管理</a>
          </li>
          <li class="active">
            <a href="javascript:void(0)">新建采购项目</a>
          </li>
          <li class="active">
            <a href="javascript:void(0)">选择明细</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <!-- 录入采购计划开始-->
    <div class="container">

      <!-- 项目戳开始 -->
      <div class="col-md-12 col-sm-12 col-xs-12 p0 over_auto mt20" id="content">
        <table id="table" class="table table-bordered table-condensed lockout">
          <thead>
            <tr class="space_nowrap">
              <th class="choose">选择</th>
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
          <tbody id="tb_id">
          </tbody>
        </table>

      </div>
      <div class="col-md-12 tc col-sm-12 col-xs-12 mt20">
        <button class="btn btn-windows save" type="button" onclick="save()">确定</button>
        <button class="btn btn-windows back" type="button" onclick="javascript:history.go(-1);">返回</button>
      </div>
    </div>
    <form id="save_form_id" action="${pageContext.request.contextPath}/project/save.html" method="post">
      <input id="detail_id" name="checkIds" type="hidden" />
      <input id="planTypes" name="planType" type="hidden" />
      <input name="name" type="hidden" value="${name}" />
      <input name="projectNumber" value="${projectNumber}" type="hidden" />
      <input name="id" type="hidden" value="${id}" />
      <input name="taskId" type="hidden" value="${taskId}" />
      <!-- <input id="uncheckId" name="uncheckId" type="hidden" /> -->
      <input name="orgId" type="hidden" value="${orgId}" />
    </form>
  </body>

</html>