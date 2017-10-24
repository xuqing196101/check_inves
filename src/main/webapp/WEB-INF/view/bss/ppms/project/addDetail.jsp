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
            elem: '#tb_id' //指定列表容器
            ,done: function(page, next) { //到达临界点（默认滚动触发），触发下一页
              var lis = [];
              //以jQuery的Ajax请求为例，请求下一页数据
              $.ajax({
                url: "${pageContext.request.contextPath}/project/viewPlanDetail.do?taskId=${taskId}&page=" + page + "&detailId=" + detailId + "&projectId=" + projectId,
                type: "get",
                dataType: "json",
                success: function(res) {
                  /* var ch=$("#tb_id").children();
                  if(ch.length>1){
                    for(var i=0;i<ch.length;i++){
                       var tds=$(ch[i]).children()[0];
                       if($(tds).children()[0]==obj){
                         check($(tds).children()[0])
                         break;
                       }
                    }
                   
                  } */
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
                      if(objectStauts){
							 //查看父节点是否被选中
		                      $("input[name='chkItem_" + item.parentId + "']").each(function() {
		                        flag = $(this).prop("checked");
		                        if(flag == true) {
		                          checkDoc.push(item.id);
		                        }
		                      });
						}else{
							flag=false;
						}
                     

                      html += "<td class='text-center'>";
                      html += "<input type='hidden' name='pId_" + item.parentId + "' value='" + item.parentId + "'>";
                      if (flag == true) {
                    	  if(item.seq=='一'){
                    		  html +="<input type='checkbox' checked='checked' value='"+item.id+"' name='chkItem_"+item.id+"' onclick='check(this,1)' alt=''>";
                    	  }else{
                    		  html +="<input type='checkbox' checked='checked' value='"+item.id+"' name='chkItem_"+item.id+"' onclick='check(this,2)' alt=''>";
                    	  }
								
						} else {
						  if(item.seq=='一'){
								html +="<input type='checkbox' value='"+item.id+"' name='chkItem_"+item.id+"' onclick='check(this,1)' alt=''>";
						  }else{
							    html +="<input type='checkbox' value='"+item.id+"' name='chkItem_"+item.id+"' onclick='check(this,2)' alt=''>";
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
                  next(lis.join(''), page < res.pages);
                  
                  var fixed_header = '';
                  $('#tb_id').siblings('thead').find('th').each(function () {
                    fixed_header += '<th style="width: '+ $(this).outerWidth(true) +'px">'+ $(this).html() +'</th>';
                  });
                  $('#fixed_header').css({
                    position: 'absolute',
                    top: 0,
                    left: 0,
                    width: $('#tb_id').parents('table').outerWidth()
                  });
                  $('#fixed_header').html('<table class="table table-hover table-border mb0">'+ fixed_header +'</table>');
                  chage_fixedHeader();
                  $(window).scroll(function () {
                    chage_fixedHeader();
                  });
                  
                  /* 锁定表头 */
                  // var fixed_table = $('#table');
                  // fixed_table.bootstrapTable('destroy').bootstrapTable({
                  //   fixedColumns: true,
                  //   onPostBody: function () {
                  //     console.log($('.fixed-table-body').height());
                  //     $('.fixed-table-body').scroll(function () {
                  //       console.log('aaa');
                  //       var scroll_top = $('.fixed-table-body').scrollTop();
                  //       console.log(scroll_top);
                  //     });
                  //   }
                  // });
                  /* 锁定表头 */
                },
              });
            }
          });
        });
        
      });
      
      function chage_fixedHeader() {
        if ($(window).scrollTop() >= $('#table').offset().top) {
          $('#fixed_header').css({
            position: 'fixed',
            left: 'auto'
          });
        } else {
          $('#fixed_header').css({
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
      <div class="col-md-12 col-sm-12 col-xs-12 p0 over_auto pr mt20" id="content">
        <table id="table" class="table table-hover table-bordered">
          <thead>
            <tr>
              <th width="50">选择</th>
              <th width="60">序号</th>
              <th width="80">需求部门</th>
              <th width="100">物资类别及名称</th>
              <th width="100">规格型号</th>
              <th width="80">质量技术标准(技术参数)</th>
              <th width="80">计量单位</th>
              <th width="80">采购数量</th>
              <th width="100">交货期限</th>
              <th width="80">采购方式</th>
              <th width="100">供应商名称</th>
              <!-- <th class="info freetax">是否申请<br/>办理免税</th>
              <th class="info goodsuse">物资用途<br/>（进口）</th>
              <th class="info useunit">使用单位<br/>（进口）</th> -->
              <th>备注</th>
            </tr>
          </thead>
          <tbody id="tb_id">
          </tbody>
        </table>
        <div id="fixed_header"></div>
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