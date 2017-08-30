<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp" %>

    <script type="text/javascript">
      var id = "${id}";
      /*分页  */
      $(function() {
        laypage({
          cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
          pages: "${info.pages}", //总页数
          skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
          skip: true, //是否开启跳页
          groups: "${info.pages}" >= 3 ? 3 : "${info.pages}", //连续显示分页数
          curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
            //              var page = location.search.match(/page=(\d+)/);
            //              return page ? page[1] : 1;
            return "${info.pageNum}";
          }(),
          jump: function(e, first) { //触发分页后的回调
            if(!first) { //一定要加此判断，否则初始时会无限刷新
              //  $("#page").val(e.curr);
              // $("#form1").submit();

              location.href = '${pageContext.request.contextPath}/purchaser/list.do?page=' + e.curr;
            }
          }
        });
      });

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
      
      $(function() {
        //移到右边
        $('#add').click(function() {
          //先判断是否有选中
          if(!$("#select1 option").is(":selected")) {
            alert("请选择需要移动的选项")
          }
          //获取选中的选项，删除并追加给对方
          else {
            $('#select1 option:selected').appendTo('#select2');
          }
        });

        //移到左边
        $('#remove').click(function() {
          //先判断是否有选中
          if(!$("#select2 option").is(":selected")) {
            alert("请选择需要移动的选项")
          } else {
            $('#select2 option:selected').appendTo('#select1');
          }
        });

        //全部移到右边
        $('#add_all').click(function() {
          //获取全部的选项,删除并追加给对方
          $('#select1 option').appendTo('#select2');
        });

        //全部移到左边
        $('#remove_all').click(function() {
          $('#select2 option').appendTo('#select1');
        });

        //双击选项
        $('#select1').dblclick(function() { //绑定双击事件
          //获取全部的选项,删除并追加给对方
          $("option:selected", this).appendTo('#select2'); //追加给对方
        });

        //双击选项
        $('#select2').dblclick(function() {
          $("option:selected", this).appendTo('#select1');
        });

      });
      //保存
      function save() {
        var ap = $("#userList tr:last td:first input:last").val();
        var tp = Number($(".tempPersonIndex:first").val());
        if(isNaN(ap) && isNaN(tp)) {
          layer.msg("请添加审核人员");
        } else {
          cleanErr();
          var index = Number($(".tempPersonIndex:first").val());
          var auditNature = $("#audit_nature").val();
          var turns = $("#auditRound").val();
          if(isNaN(index)) {
        	  $("#austa").val(auditNature);
        	  $.ajax({
        		  url:"${pageContext.request.contextPath}/set/clearSession.html",
        		  type: "POST",
        		  success:function(){
            $("#set_form").submit();
        			  
        		  }
        	  });
          } else {
            $.ajax({
              url: "${pageContext.request.contextPath}/set/judgeAddUser.do?index=" + index + "&auditNature=" + auditNature + "&turns=" + turns,
              type: "POST",
              dataType: "json",
              data: $("#set_form").serialize(),
              success: function(msg) {
                if(msg.isErr == 'error') {
                  var size = msg.length;
                  $("#auditNatureErr").text(msg.auditNatureErr);
                  for(var i = index; i < eval(index + size); i++) {
                    var name = "name" + i;
                    var phone = "phone" + i;
                    var unitName = "unitName" + i;
                    var duty = "duty" + i;
                    $("#name" + i).text(msg[name]);
                    $("#phone" + i).text(msg[phone]);
                    $("#duty" + i).text(msg[duty]);
                    $("#unitName" + i).text(msg[unitName]);
                  }
                } else {
                  $("#set_form").submit();
                }
              }
            });
          }
        }

      }
      //添加专家
      function beforeExperts() {
        var ap = $("#userList tr:last td:first input:last").val();
        var tp = Number($(".tempPersonIndex:first").val());
        experts();
        if(isNaN(ap) && isNaN(tp)) {
          //experts();
        } else {
          /* cleanErr();
          layer.alert("只能有一个审核人员", {
            offset: ['30%', '40%']
          }); */
          /*  var index = Number($(".tempPersonIndex:first").val());
            var auditNature = $("#audit_nature").val();
            var turns=$("#auditRound").val();
            if(index!=0){     
                $.ajax({
                  url:"${pageContext.request.contextPath}/set/judgeAddUser.do?index="+index+"&auditNature="+auditNature+"&turns="+turns,
                type:"POST",
                dataType:"json",
                  data:$("#set_form").serialize(),
                  success:function(msg){
                    if(msg.isErr=='error'){
                      var size= msg.length;
                      $("#auditNatureErr").text(msg.auditNatureErr);
                      for (var i = index; i < eval(index+size); i++) {
                        var name = "name"+i;
                        var phone = "phone"+i;
                        var unitName ="unitName"+i;
                        var duty = "duty"+i;
                        $("#name"+i).text(msg[name]);
                        $("#phone"+i).text(msg[phone]);
                        $("#duty"+i).text(msg[duty]);
                        $("#unitName"+i).text(msg[unitName]);
                      }
                    }else{
                      experts();
                    }
                  }
                });     
            }else{
              experts();
            }*/
        }
      }

      var nature;
      //    var turns;
      function experts() {
        nature = $("#audit_nature").val();
        //      turns=$("#audit_turn").val();
        var type = "${type }";
        var tp = 0;
        nature = $.trim(nature);
        if(nature == null || nature == '') {
          layer.alert("请填写审核人员性质", {
            offset: ['30%', '40%']
          });
          tp = 1;
        }
        //      if(turns==null || turns== ''){
        //       layer.alert("请填写审核轮次", {
        //        offset: ['30%', '40%']
        //      });  
        //       tp = 1;
        //      }
        if(tp != 1) {
          layer.open({
            type: 2, //page层
            area: ['80%', '80%'],
            title: '专家库',
            closeBtn: 1,
            shade: 0.01, //遮罩透明度
            moveType: 1, //拖拽风格，0是默认，1是传统拖动
            shift: 1, //0-6的动画形式，-1不开启
            offset: ['0px', '10%'],
            content: "${pageContext.request.contextPath}/set/expert.html?type=" +type+ "&backAttr=" + "${backAttr}" + "&backid=" + "${backid}",
          });
        }
      }

      function beforeUsers() {
        var ap = $("#userList tr:last td:first input:last").val();
        var tp = Number($(".tempPersonIndex:first").val());
        users();
        if(isNaN(ap) && isNaN(tp)) {
          //users();
        } else {
          /*cleanErr();
           layer.alert("只能有一个审核人员", {
            offset: ['30%', '40%']
          }); */
          //        var index = Number($(".tempPersonIndex:first").val());
          //      var auditNature = $("#audit_nature").val();
          //      var turns=$("#auditRound").val();
          //      if(index!=0){     
          //          $.ajax({
          //            url:"${pageContext.request.contextPath}/set/judgeAddUser.do?index="+index+"&auditNature="+auditNature+"&turns="+turns,
          //          type:"POST",
          //          dataType:"json",
          //            data:$("#set_form").serialize(),
          //            success:function(msg){
          //              if(msg.isErr=='error'){
          //                var size= msg.length;
          //                $("#auditNatureErr").text(msg.auditNatureErr);
          //                for (var i = index; i < eval(index+size); i++) {
          //                  var name = "name"+i;
          //                  var phone = "phone"+i;
          //                  var unitName ="unitName"+i;
          //                  var duty = "duty"+i;
          //                  $("#name"+i).text(msg[name]);
          //                  $("#phone"+i).text(msg[phone]);
          //                  $("#duty"+i).text(msg[duty]);
          //                  $("#unitName"+i).text(msg[unitName]);
          //                }
          //              }else{
          //                users();
          //              }
          //            }
          //          });     
          //      }else{
          //        users();
          //      }
        }
      }

      function users() {
        nature = $("#audit_nature").val();
        //       turns=$("#audit_turn").val();
        var tp = 0;
        var type = "${type }";
        nature = $.trim(nature);
        if(nature == null || nature == '') {
          layer.alert("请填写审核人员性质", {
            offset: ['30%', '40%']
          });
          tp = 1;
        }
        //         if(turns==null || turns == '' ){
        //           layer.alert("请填写审核轮次", {
        //            offset: ['30%', '40%']
        //          });  
        //           tp = 0;
        //         }
        if(tp != 1) {
          layer.open({
            type: 2, //page层
            area: ['80%', '80%'],
            title: '用户库',
            closeBtn: 1,
            shade: 0.01, //遮罩透明度
            shift: 1, //0-6的动画形式，-1不开启
            offset: ['0px', '10%'],
            content: "${pageContext.request.contextPath}/set/user.html?type=" + type+"&backAttr="+"${backAttr}" +"&backid="+"${backid}",
          });

        }
      }

      //    var index;//添加临时审核人员
      function tempbefore() {
        debugger;
        var tabhtml = "";
        var index = Number($("#userList tr:last td:first input:last").val());
        var ind = Number(index) + 1;
        var first = Number($(".tempPersonIndex:last").val());
        var isTable = 0;
        var i = 0;
        if(isNaN(index)) {
          index = 1;
          isTable = 1;
          ind = Number(index) + 1;
        }
        if(isNaN(first)){
          first = ind;
        }
        if(first != null && first != "") {
          i = eval(ind - first);
        }
        tabhtml += '<tr class="tc pointer tempPersonList">';
        tabhtml += '<td class="w30"><input type="checkbox" name="chkItem" alt="" value=""><input type="hidden" class="tempPersonIndex" value="' + ind + '"></td>';
        tabhtml += '<td><input class="m0 border0 w100p" name="auditPersons[' + i + '].name" type="text" value=""><div class="clear red names" id="name' + ind + '"></div></td>';
        tabhtml += '<td><input class="m0 border0 w100p" name="auditPersons[' + i + '].mobile" type="text" maxlength="18" onkeyup="this.value=this.value.replace(/[^0-9]/g,' + "''" + ')" value=""><div class="clear red phones" id="phone' + ind + '"></div></td>';
        tabhtml += '<td><input class="m0 border0 w100p" name="auditPersons[' + i + '].duty" type="text" value=""><div class="clear red duties" id="duty' + ind + '"></div></td>';
        tabhtml += '<td><input class="m0 border0 w100p" name="auditPersons[' + i + '].unitName" type="text" value=""><div class="clear red unitNames" id="unitName' + ind + '"></div></td>';
        tabhtml += '</tr>';
        if(isTable == 0) {
          $("#userList tbody").append(tabhtml);
        } else {
          $("#userList").append(tabhtml);
        }
      }

      function saveTemp() {
        cleanErr();
        var index = Number($(".tempPersonIndex:first").val());
        var auditNature = $("#audit_nature").val();
        var turns = $("#auditRound").val();
        if(isNaN(index)) {
          window.location.reload();
        } else {
          $.ajax({
            url: "${pageContext.request.contextPath}/set/judgeAddUser.do?index=" + index + "&auditNature=" + auditNature + "&turns=" + turns,
            type: "POST",
            dataType: "json",
            data: $("#set_form").serialize(),
            success: function(msg) {
              if(msg.isErr == 'error') {
                var size = msg.length;
                $("#auditNatureErr").text(msg.auditNatureErr);
                for(var i = index; i < eval(index + size); i++) {
                  var name = "name" + i;
                  var phone = "phone" + i;
                  var unitName = "unitName" + i;
                  var duty = "duty" + i;
                  $("#name" + i).text(msg[name]);
                  $("#phone" + i).text(msg[phone]);
                  $("#duty" + i).text(msg[duty]);
                  $("#unitName" + i).text(msg[unitName]);
                }
              } else {
                window.location.reload();
              }
            }
          });
        }
      }

      function cancel() {
        layer.close(index);
      }

      function qd() {
        nature = $("#audit_nature").val();
        //       turns=$("#audit_turn").val();
        $("#auditStaff_1").val(nature);
        $("#auditRound_1").val(turns);
        nature = $("#audit_nature").val();
        //         turns=$("#audit_turn").val();
        var type = "${type}";
        var tp = 0;
        if(nature == null || nature == '') {
          layer.alert("请填写审核人员性质", {
            offset: ['30%', '40%']
          });
          tp = 1;
        }
        if(tp != 1) {

          $.ajax({
            url: "${pageContext.request.contextPath}/set/judgeAddUser.do",
            type: "POST",
            dataType: "json",
            data: $("#collect_form").serialize(),
            success: function(data) {

              if(data.status != null && data.status != '' && data.status == 1) {
                layer.msg('添加成功', {
                  offset: ['40%', '45%']
                });
                var el = document.createElement("a");
                document.body.appendChild(el);
                el.href = "${pageContext.request.contextPath}/set/list.html?staff=" + data.staff + "&&id=" + $("#collId").val() + "&&type=" + type; //url 是你得到的连接
                //el.target = '_parent'; //指定在新窗口打开
                el.click();
                document.body.removeChild(el);
                //            window.location.reload();
              } else {
                var error = eval(data);
                if(error.name) {
                  $("#userName").html(error.name);
                } else {
                  $("#userName").html("");
                }
                if(error.phone) {
                  $("#userPhone").html(error.phone);
                } else {
                  $("#userPhone").html("");
                }
                if(error.unitName) {
                  $("#userUnitName").html(error.unitName);
                } else {
                  $("#userUnitName").html("");
                }
                if(error.auditStaff) {
                  $("#userAuditStaff").html(error.auditStaff);
                } else {
                  $("#userAuditStaff").html("");
                }
              }
            }
          });
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
      //删除
      function delet() {
        var id = [];
        $('input[name="chkItem"]:checked').each(function() {
          id.push($(this).val());
          $(this).parent().parent().remove();
        });
        $("#del_id").val(id);
        var val = $("#del_id").val();
        if(id.length>0) {
          $.ajax({
            url: "${pageContext.request.contextPath}/set/delete.html",
            type: "post",
            data: $("#del_form").serialize(),
            success: function() {
              layer.msg('删除成功', {
                offset: ['40%', '45%']
              });
              $('input[name="chkItem"]:checked').each(function() {
                $(this).parent().parent().remove();
              });
            }
          });
        }else{
        	layer.msg('请选择一条记录删除', {offset: ['40%', '45%']});
        }

      }

      function cleanErr() {
        $(".names").empty();
        $(".phones").empty();
        $(".duties").empty();
        $(".unitNames").empty();
        $("#auditNatureErr").empty();
      }
      
       function checkLen(){    
    	    if($("#audit_nature").val().length>=20){   //长度可自定义
    	        layer.msg("审核人员性质长度过长")
    	    }else{
    	        //未超出……
    	    }
    	}; 
    	
    	function goBack(){
    		$.ajax({
                url: "${pageContext.request.contextPath}/set/goBack.html",
                type: "post",
                success: function(data) {
                	if(data=="ok"){
                	location.href='javascript:history.go(-1);'
                	}
                }
              });
    		}
    	
    </script>
  </head>

  <body>
    <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
          <li>
            <a href="javascript:void(0);"> 首页</a>
          </li>
          <li>
            <a href="javascript:void(0);">保障作业系统</a>
          </li>
          <li>
            <a href="javascript:void(0);">采购计划子系统</a>
          </li>
          <li class="active">
            <a href="javascript:void(0);">计划汇总审核</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    <!-- 录入采购计划开始-->
    <div class="container">
      <div class="container container_box">

        <div>
          <h2 class="count_flow"><i>1</i>审核设置</h2>

          <ul class="ul_list">
            <li class="col-md-3 col-sm-6 col-xs-12 pl15">
              <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="star_red">*</div>审核人员性质</span>
              <div class="input-append input_group col-sm-12 col-md-12 col-xs-12 p0">
                <input id="audit_nature" type="text" class="input_group" name="name" value="${staff }" maxlength="10" placeholder="最大长度10个字符" onkeyup="checkLen();" />
                <span class="add-on">i</span>
                <div class="cue" id="auditNatureErr"></div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12 pl15">
              <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="star_red">*</div>审核轮次</span>
              <div class="input-append input_group col-sm-12 col-xs-12 p0">
                <input id="audit_turn" type="text" class="input_group" name="projectNumber" value="${auditRound }" readonly="readonly" />
                <span class="add-on">i</span>
              </div>
            </li>
          </ul>
        </div>

        <div>
          <h2 class="count_flow"><i>2</i><font color=red>*</font>审核人员列表</h2>
          <!--<h2 class="list_title">审核人员列表</h2> -->
          <div class="ul_list">

            <div class="col-md-12 col-sm-12 col-xs-12 pl20 mt10">
              <button class="btn btn-windows add" onclick="beforeExperts()">专家库添加</button>
              <button class="btn btn-windows add" onclick="beforeUsers()">用户库添加</button>
              <button class="btn btn-windows add" onclick="tempbefore()">添加临时人员</button>
              <!-- <button class="btn btn-windows add" onclick="saveTemp()">保存临时人员</button> -->
              <button class="btn btn-windows delete" onclick="delet()">删除</button>
            </div>
            <div class="content table_box">
              <form id="set_form" action="${pageContext.request.contextPath}/set/update.html" method="post">
              <input type="hidden" name="backAttr" id="backAttr" value="${backAttr }">
              <input type="hidden" name="backid" id="backid" value="${backid}">
                <table class="table table-bordered table-condensed " id="userList">
                  <thead>
                    <tr class="info">
                      <th class="w30"><input type="checkbox" id="checkAll" onclick="selectAll()" alt=""></th>
                      <th>姓名</th>
                      <th>手机号</th>
                      <th>职务</th>
                      <th>单位名称</th>
                    </tr>
                  </thead>
                  <%-- <c:forEach items="${info.list}" var="obj" varStatus="vs" >
                    <tr class="tc pointer" id="person_set">
                      <td class="w30"><input type="checkbox" value="${obj.id }" onclick="check()" name="chkItem" alt=""><input type="hidden" class="positions" value="${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}"></td>

                         <td>
            <c:forEach items="${kind}" var="kind">
            <c:if test="${kind.id == obj.auditRound}">${kind.name}</c:if>
          </c:forEach>
        </td>
                      <td class="tc w120">${obj.name }</td>
                      <td class="tc w120">${obj.mobile }</td>
                      <td class="tl pl20" width="30%">${obj.duty }</td>
                      <td class="tl pl20">${obj.unitName }</td>
                        <td>${obj.auditStaff }</td>
                    </tr>
                  </c:forEach> --%>
                  <c:forEach items="${expInfo.list}" var="obj" varStatus="vs" >
                    <tr class="tc pointer" id="person_set1">
                      <td class="w30"><input type="checkbox" value="${obj.id }" onclick="check()" name="chkItem" alt=""><input type="hidden" class="positions" value="${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}"></td>
                      <td class="tc w120">${obj.name }</td>
                      <td class="tc w120">${obj.mobile }</td>
                      <td class="tl pl20" width="30%">${obj.duty }</td>
                      <td class="tl pl20">${obj.unitName }</td>
                    </tr>
                  </c:forEach>
                  <c:forEach items="${aupInfo.list}" var="obj" varStatus="vs" >
                    <tr class="tc pointer" id="person_set2">
                      <td class="w30"><input type="checkbox" value="${obj.id }" onclick="check()" name="chkItem" alt=""><input type="hidden" class="positions" value="${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}"></td>
                      <td class="tc w120">${obj.name }</td>
                      <td class="tc w120">${obj.mobile }</td>
                      <td class="tl pl20" width="30%">${obj.duty }</td>
                      <td class="tl pl20">${obj.unitName }</td>
                    </tr>
                  </c:forEach>
                  <input type="hidden" name="collectId" id="collectId" value="${id }"/>
                  <input type="hidden" name="type" value="${type}"/>
                  <input type = "hidden" id = "austa" name = "austa" value="${staff }"/>
                </table>
              </form>
            </div>
            <!--  <div id="pagediv" align="right"></div> -->
          </div>
        </div>
        <div class="mt20 tc col-md-12 col-xs-12 col-sm-12">
          <button class="btn btn-windows git" onclick="save()">保存</button>
          <input class="btn btn-windows back" value="返回" type="button" onclick="goBack()">
        </div>

        <div id="content" class="dnone layui-layer-wrap mt20">
          <form id="collect_form" action="">
            <input type="hidden" id="auditRound" value="${type }" name="auditRound">
            <input type="hidden" name="type" id="type" value="3">
            <input type="hidden" name="id" value="123123123">
            <div class="drop_window">
              <ul class="list-unstyled">
                <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6 pl15">
                  <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">姓名</span>
                  <div class="col-md-12 col-xs-12 col-sm-12 input-append input_group p0">
                    <input id="citySel" class="title" name="name" type="text" />
                    <div class="cue" id="userName"></div>
                  </div>
                </li>
                <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
                  <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">电话</span>
                  <div class="col-md-12 col-xs-12 col-sm-12 input-append input_group p0">
                    <input id="citySel" class="title" name="mobile" type="text" />
                    <div class="cue" id="userPhone"></div>
                  </div>
                </li>
                <li class="col-md-12 col-xs-12 col-sm-12">
                  <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">单位名称</span>
                  <div class="col-md-12 col-xs-12 col-sm-12 input-append input_group p0">
                    <input class="title" name="unitName" type="text">
                    <div class="cue" id="userUnitName"></div>
                  </div>
                </li>
                <input type="hidden" name="auditStaff" value="" id="auditStaff_1">
                <!--         <input type="hidden" name="auditRound" value="" id="auditRound_1" > -->
                <input type="hidden" id="collId" name="collectId" value="${id }">
                <div class="clear"></div>
              </ul>
              <div class="tc mt10 col-md-12 col-xs-12 col-sm-12">
                <button class="btn btn-windows save" id="save" type="button" onclick="qd()">添加</button>
                <button class="btn btn-windows cancel" onclick="cancel()" type="button">取消</button>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>

    <form id="del_form" action="" method="post">
      <input type="hidden" name="id" id="del_id">
    </form>

  </body>

</html>