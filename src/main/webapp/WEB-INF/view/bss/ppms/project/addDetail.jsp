<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script src="${pageContext.request.contextPath}/public/webuploadFT/layui/layui.js"></script>
    <script type="text/javascript">
      var objectStauts=false;
      var obj = "";
      var detailId = "";
      var projectId = "${id}";
      
      $(function() {
        layui.use('flow', function() {
          var flow = layui.flow;
          flow.load({
            elem: '#tb_id', //指定列表容器
            done: function(page, next) { //到达临界点（默认滚动触发），触发下一页
              var lis = [];
              var flow_load = layer.load(2);
              //以jQuery的Ajax请求为例，请求下一页数据
              $.ajax({
                url: "${pageContext.request.contextPath}/project/viewPlanDetail.do?taskId=${taskId}&page=" + page + "&detailId=" + detailId + "&projectId=" + projectId,
                type: "get",
                dataType: "json",
                success: function(res) {
                  detailId = res.detailId;
                  var checkDoc = []; //保存被选中的节点
                  layui.each(res.data, function(index, item) {
                    var code = "";
                    if(item.oneAdvice == "DYLY") {
                      code = item.supplier;
                    }
                    if(item.purchaseCount == 0) {
                      item.purchaseCount = "";
                    }
                    var html = "<tr>";
                    /* if (item.projectStatus == 1) {
				              //如果已被引用
				              html +="<td><input type='text'><input type='checkbox' disabled='disabled' title='该明细已被引用'></td>";
				              html +="<td><div class='seq'>" + item.seq + "</div></td>";
				              html +="<td><div class='department'>" + item.department + "</div></td>";
				              html +="<td><div class='goodsname'>" + item.goodsName + "</div></td>";
				              html +="<td><div class='stand'>" + item.stand + "</div></td>";
				              html +="<td><div class='qualitStand'>" +item.qualitStand + "</div></td>";
				              html +="<td><div class='item'>" + item.item + "</div></td>";
				              html +="<td><div class='purchaseCount'>" + item.purchaseCount + "</div></td>";
				              html +="<td><div class='deliverDate'>" + item.deliverDate + "</div></td>";
				              html +="<td><div class='purchaseType tc'>" + item.purchaseType + "</div></td>";
				              html +="<td><div class='purchasename'>" + code + "</div></td>";
				              html +="<td><div class='memo'>"+item.memo+"</div><input type='hidden' id='planType' value='"+item.planType+"' /></td>";
				              html +="</tr>";
				              lis.push(html);
				            } */
                    //如果未被引用
                    if(item.projectStatus != 1) {
                      var flag = false;
                      
                      for(var int = 0; int < checkDoc.length; int++) {
                        if(item.parentId == checkDoc[int]) {
                          flag = true;
                          checkDoc.push(item.id);
                        }
                      }
                      if(objectStauts) {
                        //查看父节点是否被选中
	                      $("input[name='chkItem_" + item.parentId + "']").each(function() {
	                        flag = $(this).prop("checked");
	                        if(flag == true) {
	                          checkDoc.push(item.id);
	                        }
	                      });
          						} else {
          							flag = false;
          						}

                      html += "<td class='text-center'>";
                      html += "<input type='hidden' name='pId_" + item.parentId + "' value='" + item.parentId + "'>";
                      if (flag == true) {
                    	  if(item.seq=='一') {
                    		  html += "<input type='checkbox' checked='checked' value='"+item.id+"' name='chkItem_"+item.id+"' onclick='check(this,1)' alt=''>";
                    	  } else {
                    		  html += "<input type='checkbox' checked='checked' value='"+item.id+"' name='chkItem_"+item.id+"' onclick='check(this,2)' alt=''>";
                    	  }
          						} else {
          						  if(item.seq=='一') {
          								html += "<input type='checkbox' value='"+item.id+"' name='chkItem_"+item.id+"' onclick='check(this,1)' alt=''>";
          						  }else{
          							  html += "<input type='checkbox' value='"+item.id+"' name='chkItem_"+item.id+"' onclick='check(this,2)' alt=''>";
          						  }
          						}

                      html += "</td>";
                      html += "<td class='text-center'><div class='seq'>" + item.seq + "</div></td>";
                      html += "<td class='text-center'><div class='department'>" + item.department + "</div></td>";
                      html += "<td><div class='goodsname'>" + item.goodsName + "</div></td>";
                      html += "<td><div class='stand'>" + item.stand + "</div></td>";
                      html += "<td class='text-center'><div class='qualitStand'>" + item.qualitStand + "</div></td>";
                      html += "<td class='text-center'><div class='item'>" + item.item + "</div></td>";
                      html += "<td class='text-center'><div class='purchaseCount'>" + item.purchaseCount + "</div></td>";
                      html += "<td><div class='deliverDate'>" + item.deliverDate + "</div></td>";
                      html += "<td class='text-center'><div class='purchaseType tc'>" + item.purchaseType + "</div></td>";
                      html += "<td><div class='purchasename'>" + code + "</div></td>";
                      html += "<td><div class='memo'>" + item.memo + "</div><input type='hidden' id='planType' value='" + item.planType + "' /></td>";
                      html += "</tr>";
                      lis.push(html);
                    }
                    /* var html = "<tr class='pointer'><td><input type='checkbox' value='"+item.id+"' name='chkItem' onclick='check(this)'  alt=''></td><td><div class='seq'>" + item.seq + "</div></td><td><div class='department'>" +
                      item.department + "</div></td><td><div class='goodsname'>" + item.goodsName + "</div></td><td><div class='stand'>" + item.stand + "</div></td><td><div class='qualitStand'>" +item.qualitStand + "</div></td><td><div class='item'>" + 
                      item.item + "</div></td><td><div class='purchaseCount'>" + item.purchaseCount + "</div></td><td><div class='deliverDate'>" + item.deliverDate + "</div></td><td><div class='purchaseType tc'>" + item.purchaseType + 
                      "</div></td><td><div class='purchasename'>" + code + "</div></td><td><div class='memo'>"+item.memo+"</div><input type='hidden' id='planType' value='"+item.planType+"' /></td></tr>"; */
                  });
                  
                  // 关闭loading
                  layer.close(flow_load);
                  
                  next(lis.join(''), page < res.pages);
                  
                  build_fixedHead();
                }
              });
            }
          });
        });
        
      });
      
      $(window).resize(function () {
        build_fixedHead();
      });
      
      function build_fixedHead() {
        var fixed_header = '';  // 定义保存表头html变量
        var fixed_header2 = '';  // 定义保存表头前两列html变量
        var fixed_column = '';  // 定义保存冻结列html变量
        
        // 获取表头html并添加每个单元格宽度（与内容表格保持一致）
        $('#tb_id').siblings('thead').find('th').each(function (index) {
          fixed_header += '<th style="width: '+ $(this).outerWidth(true) +'px">'+ $(this).html() +'</th>';
          if (index <= 1) {
            fixed_header2 += '<th style="width: '+ $(this).outerWidth(true) +'px; height: '+ $(this).outerHeight(true) +'px">'+ $(this).html() +'</th>';
          }
        });
        
        // 添加前两列表头
        $('#fixed_header2').html('<table class="table table-bordered mb0"><thead>'+ fixed_header2 +'</thead></table>');
        
        // 为空则是第一次初始化，从第一个tr开始保存，不为空则从上次最后一个开始保存
        if ($('#fixed_column tbody').html() != '') {
          var last_index = parseInt($('#fixed_column tr').length) - 1;
          $('#tb_id tr').each(function (index) {
            if (index > last_index) {
              fixed_column += '<tr>'
                +'<td class="text-center" style="width: '+ $(this).find('td').eq(0).outerWidth(true) +'px; height: '+ $(this).find('td').eq(0).outerHeight(true) +'px">'
                + $(this).find('td').eq(0).html()
                +'</td>'
                +'<td class="text-center" style="width: '+ $(this).find('td').eq(1).outerWidth(true) +'px; height: '+ $(this).find('td').eq(1).outerHeight(true) +'px">'
                + $(this).find('td').eq(1).html()
              +'</td></tr>';
            }
          });
        } else {
          $('#tb_id tr').each(function (index) {
            fixed_column += '<tr>'
              +'<td class="text-center" style="width: '+ $(this).find('td').eq(0).outerWidth(true) +'px; height: '+ $(this).find('td').eq(0).outerHeight(true) +'px">'
              + $(this).find('td').eq(0).html()
              +'</td>'
              +'<td class="text-center" style="width: '+ $(this).find('td').eq(1).outerWidth(true) +'px; height: '+ $(this).find('td').eq(1).outerHeight(true) +'px">'
              + $(this).find('td').eq(1).html()
            +'</td></tr>';
          });
        }
        
        // 定义表头样式
        $('#fixed_header').css({
          position: 'absolute',
          top: 0,
          left: 0,
          width: $('#content').width(),
          zIndex: 1
        });
        // 定义冻结列样式
        $('#fixed_column').css({
          position: 'absolute',
          top: $('#tb_id').siblings('thead').outerHeight(),
          left: 0,
          width: parseInt($('#tb_id').find('td').eq(0).outerWidth()) + parseInt($('#tb_id').find('td').eq(1).outerWidth()) + 1,
          backgroundColor: '#FFF'
        });
        
        // 填充内容到容器
        $('#fixed_header').html('<table class="table table-hover table-bordered mb0"><thead>'+ fixed_header +'</thead></table>');
        $('#fixed_column tbody').append(fixed_column);
        
        $('#fixed_header table').css({
          width: $('#tb_id').parents('table').outerWidth()
        });
        
        // 给左侧冻结列添加hover样式
        $('#tb_id tr').hover(function () {
          var index = $(this).index();
          $('#fixed_column tr').removeClass('hover');
          $('#fixed_column tr').eq(index).addClass('hover');
        }, function () {
          $('#tb_id tr').removeClass('hover');
          $('#fixed_column tr').removeClass('hover');
        });
        // 给右侧内容区添加hover样式
        $('#fixed_column tr').hover(function () {
          var index = $(this).index();
          $('#tb_id tr').removeClass('hover');
          $('#tb_id tr').eq(index).addClass('hover');
        }, function () {
          $('#tb_id tr').removeClass('hover');
          $('#fixed_column tr').removeClass('hover');
        });
        
        $('#content').bind('scroll', function () {
          var scroll_left = $('#content').scrollLeft();
          $('#fixed_header table').css({
            marginLeft: -(scroll_left)
          });
        });
        
        // 判断表头是否开始跟随
        chage_fixedHeader();
        $(window).scroll(function () {
          chage_fixedHeader();
        });
      }
      
      function chage_fixedHeader() {
        if ($(window).scrollTop() >= $('#table').offset().top) {
          $('#fixed_header').css({
            position: 'fixed',
            left: 'auto'
          });
          $('#fixed_header2').css({
            position: 'fixed',
            left: 'auto'
          });
        } else {
          $('#fixed_header').css({
            position: 'absolute',
            left: 0
          });
          $('#fixed_header2').css({
            position: 'absolute',
            left: 0
          });
        }
      }

      function check(ele,seq) {
        obj = ele;
        var flag = $(ele).prop("checked");
        var id = $(ele).val();
        var pId = $(ele).prev().val();
        if(flag == true) {
        	if(seq==1){
        		objectStauts=true;
        	}
          //递归选中父节点
          checkedParent(pId);
          //递归选中子节点
          checkedChild(id);
        } else {
        	if(seq==1){
        		objectStauts=false;
        	}
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
            $(this).prop("checked", false);
            var pId_v = $(this).prev().val();
            noCheckedParent(pId_v);
          });
        }
      }

      //递归取消子节点选中
      function noCheckedChild(id) {
        //所有子节点取消选中
        $("input[name='pId_" + id + "']").each(function() {
          $(this).next().prop("checked", false);
          var currId = $(this).next().val();
          noCheckedChild(currId);
        });
      }

      //递归选中父节点
      function checkedParent(pId) {
        $("input[name='chkItem_" + pId + "']").each(function() {
          $(this).prop("checked", true);
          var pId_v = $(this).prev().val();
          checkedParent(pId_v);
        });
      }

      //递归选中子节点
      function checkedChild(id) {
        $("input[name='pId_" + id + "']").each(function() {
          $(this).next().prop("checked", true);
          var currId = $(this).next().val();
          checkedChild(currId);
        });
      }

      function check1(ele) {
        obj = ele;
        var flag = $(ele).prop("checked");
        var id = $(ele).val();
        $.ajax({
          url: "${pageContext.request.contextPath}/project/checkDeail.html",
          data: {
            "id": id,
            "flag": flag
          },
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
        $('input[name^="chkItem"]:checked').each(function() {
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
                  $('input[name^="chkItem"]:not(:checked)').each(function() {
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
                  var index = layer.load(1, {
					  shade: [0.1,'#fff'] //0.1透明度的白色背景
					});
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
      /* function test(obj) {
    	  if(obj.checked){
    		   objectStauts=true;
    			alert("teue");
    		}else{
    		   objectStauts=false;
    			alert("false");
    		}
    	}  */
    </script>
    
    <style>
      .table-hover>tbody>tr.hover {
        background-color: #f5f5f5;
      }
      .layui-flow-more {
        display: none;
      }
    </style>
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
      <div class="col-md-12 col-sm-12 col-xs-12 p0 pr mt20">
        <div class="over_auto" id="content">
          <table id="table" class="table table-bordered table-hover mb0" style="width: 1600px;">
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
          <div id="fixed_header" class="over_hideen w100p"></div>
        </div>
        <div id="fixed_header2" style="position: absolute; top: 0; left: 0; z-index: 2; background-color: #FFF;"></div>
        <table class="table table-hover table-bordered mb0" id="fixed_column"><tbody></tbody></table>
      </div>
      <div class="col-md-12 tc col-sm-12 col-xs-12 mt20">
        <button class="btn btn-windows save" type="button" onclick="save()">确定选择</button>
        <button class="btn btn-windows back" type="button" onclick="javascript:history.go(-1);">返回</button>
      </div>
    </div>
    <form id="save_form_id" action="${pageContext.request.contextPath}/project/save.html" method="post">
      <input id="detail_id" name="checkIds" type="hidden" />
      <input id="planTypes" name="planType" type="hidden" />
      <input name="name" type="hidden" value="${name}" />
      <input name="projectNumber" value="${projectNumber}" type="hidden" />
      <input name="id" type="hidden" id="projectId" value="${id}" />
      <input name="taskId" type="hidden" value="${taskId}" />
      <!-- <input id="uncheckId" name="uncheckId" type="hidden" /> -->
      <input name="orgId" type="hidden" value="${orgId}" />
    </form>
  </body>

</html>