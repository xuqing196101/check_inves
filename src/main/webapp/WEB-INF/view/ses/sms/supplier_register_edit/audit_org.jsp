<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
  <head>
<%@ include file="/reg_head.jsp"%>
    <style type="text/css">
      form div.invalid {
        width: 200px;
        margin-left: 10px;
        color: Red;
      }
    </style>
    <script type="text/javascript">
      var tab = 1;
      $(function() {
        $("#page_ul_id").find("li").click(function() {
          var id = $(this).attr("id");
          tab = id;
          var page = "tab-" + id.charAt(id.length - 1);

          $("input[name='defaultPage']").val(page);
        });
        var defaultPage = "${defaultPage}";
        if(defaultPage) {
          var num = defaultPage.charAt(defaultPage.length - 1);
          $("#page_ul_id").find("li").each(function(index) {
            if(index == num - 1) {
              $(this).attr("class", "active");
            } else {
              $(this).removeAttr("class");
            }
          });
          $(".tab-pane").each(function() {
            var id = $(this).attr("id");
            if(id == defaultPage) {
              $(this).attr("class", "tab-pane fade height-200 active in");
            } else {
              $(this).attr("class", "tab-pane fade height-200");
            }
          });
        }
      });
    </script>
    <script type="text/javascript">
      var num1 = 1;
      var num2 = 1;
      Array.prototype.indexOf = function(val) {
        for(var i = 0; i < this.length; i++) {
          if(this[i] == val) return i;
        }
        return -1;
      };
      Array.prototype.remove = function(val) {
        var index = this.indexOf(val);
        if(index > -1) {
          this.splice(index, 1);
        }
      };
      var array = [];
      var deltr = function(index, name) {
        //var _len = $("#tab tr").length;
        //console.dir(index);
        //console.dir(index.id);
        //console.dir($("tr[id='" + index.id + "']"));
        var deldata = index + "," + name;
        array.remove(deldata);
        $("tr[id='" + index + "']").remove(); //删除当前行   
        var num = $("#tab tbody tr").length;
        var trs = $("#tab tbody tr");
        for(i = 0; i < num; i++) {
          trs.find("td:eq(1)").each(function(i) {
            $(this).text(i + 1);
          });
        }
      };
      $(document).ready(function() {
        var proviceId = $("#pid").val();
        //console.dir(proviceId);

        $.ajax({
          type: 'post',
          url: "${pageContext.request.contextPath}/purchaseManage/getProvinceList.do?",
          data: {
            pid: 0
          },
          success: function(data) {
            $("#city").append("<option value='-1'>请选择</option>");
            $("#province").append("<option value='-1'>请选择</option>");
            $.each(data, function(idx, item) {
              if(item.id == proviceId) {

                var html = "<option value='" + item.id + "' selected>" + item.name +
                  "</option>";
                $("#province").append(html);
                loadCities(proviceId);
              } else {
                var html = "<option value='" + item.id + "'>" + item.name +
                  "</option>";
                $("#province").append(html);
              }
            });
            if(proviceId != null && proviceId != "" && proviceId != undefined) {
              //loadCities(proviceId);
            }
            /*  var optionHTML="<select name=\"province\" onchange=\"loadCities(this.value)\">";
             var optionHTML="";
              optionHTML+="<option value=\""+"-1"+"\">"+"清选择"+"</option>"; 
              for(var i=0;i<data.length;i++){
               // console.dir(data[i].id);
                optionHTML+="<option value=\""+data[i].id+"\">"+data[i].name+"</option>"; 
              }
              optionHTML+="</select>";
              $("#province").html(optionHTML);//将数据填充到省份的下拉列表中
              console.dir(optionHTML); */
          }
        });

      });
      
       
      function selectAlls(){
       var checklist = document.getElementsByName ("selectedItems");
       var checkAll = document.getElementById("checkAlls");
         if(checkAll.checked){
           for(var i=0;i<checklist.length;i++)
           {
              checklist[i].checked = true;
           } 
         }else{
          for(var j=0;j<checklist.length;j++)
          {
             checklist[j].checked = false;
          }
        }
      }
      
      function selectAll(){
       var checklist = document.getElementsByName ("checkboxs");
       var checkAll = document.getElementById("checkAll");
         if(checkAll.checked){
           for(var i=0;i<checklist.length;i++)
           {
              checklist[i].checked = true;
           } 
         }else{
          for(var j=0;j<checklist.length;j++)
          {
             checklist[j].checked = false;
          }
        }
      }
      
        function check() {
          var count = 0;
          var checklist = document.getElementsByName("selectedItems");
          var checkAll = document.getElementById("checkAlls");
          for ( var i = 0; i < checklist.length; i++) {
            if (checklist[i].checked == false) {
            checkAll.checked = false;
            break;
            }
            for ( var j = 0; j < checklist.length; j++) {
            if (checklist[j].checked == true) {
              checkAll.checked = true;
              count++;
              }
            }
          }
        }
        
        
        function checkAdd() {
          var count = 0;
          var checklist = document.getElementsByName("selectedItem");
          var checkAll = document.getElementById("checkAlls");
          for ( var i = 0; i < checklist.length; i++) {
            if (checklist[i].checked == false) {
            checkAll.checked = false;
            break;
            }
            for ( var j = 0; j < checklist.length; j++) {
            if (checklist[j].checked == true) {
              checkAll.checked = true;
              count++;
              }
            }
          }
        }
        
        function checks() {
          var count = 0;
          var checklist = document.getElementsByName("checkboxs");
          var checkAll = document.getElementById("checkAll");
          for ( var i = 0; i < checklist.length; i++) {
            if (checklist[i].checked == false) {
            checkAll.checked = false;
            break;
            }
            for ( var j = 0; j < checklist.length; j++) {
            if (checklist[j].checked == true) {
              checkAll.checked = true;
              count++;
              }
            }
          }
        }

      function loadCities(pid) {
        $("#pid").val(pid);
        var cityId = $("#cid").val();
        $("#city").empty();
        $.ajax({
          type: 'post',
          url: "${pageContext.request.contextPath}/purchaseManage/getProvinceList.do?",
          data: {
            pid: pid
          },
          success: function(data) {
           $("#city").append("<option value='-1'>请选择</option>");
            $.each(data, function(idx, item) {
             
              if(item.id == cityId) {
                var html = "<option value='" + item.id + "' selected>" + item.name +
                  "</option>";
                $("#city").append(html);
              } else {
                var html = "<option value='" + item.id + "'>" + item.name +
                  "</option>";
                $("#city").append(html);
              }

            });
            /* var optionHTML="";
             optionHTML+="<option value=\""+"-1"+"\">"+"清选择"+"</option>"; 
             for(var i=0;i<data.length;i++){
              // console.dir(data[i].id);
               optionHTML+="<option value=\""+data[i].id+"\">"+data[i].name+"</option>"; 
             }
             optionHTML+="</select>";
             $("#city").html(optionHTML);//将数据填充到省份的下拉列表中
             //console.dir(optionHTML); */
          }
        });
      }

      function loadTown(pid) {
        $("#cid").val(pid);
      }

      function update() {
        $.ajax({
          type: 'post',
          url: "${pageContext.request.contextPath}/purchaseManage/updatePurchaseDepAjxa.do?",
          data: $('#formID').serialize(),
          //data: {'pid':pid,$("#formID").serialize()},
          success: function(data) {
            console.dir(data);
            truealert(data.message, data.success == false ? 5 : 1);
          }
        });

      }

      function stashBasic() {
        //var s = validteBasic().form();
        validateAll();
        return;
        $("#formID").submit();
      }

      function stashOffice() {
        //var s2 = validateOffice().form();
        validateAll();
        return;
        $("#formID").submit();
      }

      function stashPosition() {
        //var s2 = validatePosition().form();
        validateAll();
        return;
        $("#formID").submit();
      }

      function stash() {
        if(tab = 1) {
          validteBasic();
        } else if(tab = 2) {
          validatePosition();
        } else {
          validateOffice();
        }
        /* if(validateAll().form()){
            console.dir("ok");
        }else{
            console.dir("no");
        } */
        return false;
        $("#formID").submit();
      }

      function truealert(text, iconindex) {
        layer.open({
          content: text,
          icon: iconindex,
          shade: [0.3, '#000'],
          offset: 300 + "px",
          yes: function(index) {
            //do something
            //parent.location.reload();
            layer.closeAll();
            //parent.layer.close(index); //执行关闭
            parent.location.href = "${pageContext.request.contextPath}/purchaseManage/purchaseUnitList.do";
          }
        });
      }

      function pageOnload() {
        /* var proviceId = $("#pid").val();
        console.dir(proviceId);
        var cityId = $("#cid").val();
        var isAudit = $("#cid").val();
        $("#province").val('A4CCB12438AD4E49AADE355B3B02910C');
        $("#province").get(0).selectedIndex=proviceId;
        $("#province option[value ='"+proviceId+"']").attr("selected", true);//val(2);
        $("#city").val(cityId); */
        //$("#provinceId").val(proviceId);
        //$("div.panel-collapse").addClass("in");

      }
      //添加场所
      function dynamicaddTwo() {
        var typeName = $("#typeName").val();
        showiframe("添加场所", 1000, 600, "${pageContext.request.contextPath}/purchaseManage/addPosition.do?typeName=" + typeName, "-4");
      }
      //添加采购管理部门
      function dynamicaddThree() {
      var typeName = $("input[name='typeName']").val();
       layer.open({
          type: 2, //page层
          area : [ '600px', '550px' ],
          title: '添加采购管理部门',
          shade:0.01, //遮罩透明度
          moveType: 1, //拖拽风格，0是默认，1是传统拖动
          shift: 1, //0-6的动画形式，-1不开启
          offset: ['90px', '630px'],
          shadeClose: true,
          content:"${pageContext.request.contextPath}/purchaseManage/addPurchaseOrg.do?typeName="+typeName,
         });
      }

      function showiframe(titles, width, height, url, top) {
        if(top == null || top == "underfined") {
          top = 120;
        }
        layer.open({
          type: 2,
          title: [titles, "background-color:#eee;color:#fff;font-size:16px;text-align:center;"],
          maxmin: true,
          shade: [0.3, '#000'],
          offset: top + "px",
          shadeClose: false, //点击遮罩关闭层 
          area: [width + "px", height + "px"],
          content: url
        });
      }

      function addOffice() {
        $.ajax({
          type: 'post',
          url: "${pageContext.request.contextPath}/purchaseManage/addOffice.do?",
          data: {
            num: num1
          },
          success: function(data) {
            console.dir(data);
            $("#tab-position").append(data.message);
            num1++;
          }
        });

      }

      function addOffice1() {
        countAdd();
        $("#tab-position").append("<tr id=" + num1 + " align='center'>" +
          "<td>" + num1 + "</td>" +
          "<td><select class='purchaseRoomTypeName' id=purchaseRoomTypeName" + num1 + " name='purchaseRoomTypeName'> <option value=''>请选择</option><option value='0'>办公室</option><option value='1'>会议室</option><option value='2'>招标室</option><option value='3'>评标室</option></select></td>"
          //+"<td><input type='text' name='desc"+_len+"' id='name"+_len+"' value='"+_len+"' /></td>"
          +
          "<td><input id=purchaseRoomCode" + num1 + " name='purchaseRoomCode' style='width:100px;'/></td>" +
          "<td><input name='purchaseRoomLocation' style='width:100px;'/></td>" +
          "<td><input name='purchaseRoomArea' style='width:100px;'/></td>" +
          "<td><input name='purchaseRoomNetConnectStyle' style='width:100px;'/></td>" +
          "<td><input name='purchaseRoomCapacity' style='width:100px;'/></td>" +
          "<td><select name='purchaseRoomIsNetConnect'> <option value='-1'>请选择</option><option value='0'>是</option><option value='1'>否</option></select></td>" +
          "<td><select name='purchaseRoomHasVideoSys'> <option value='-1'>请选择</option><option value='0'>是</option><option value='1'>否</option></select></td>" +
          "<td><a href=\'#\' onclick=\'delPositionTr(this)\'>删除</a></td>" +
          "</tr>");
        num++;
      }
      //统计
      var bg_office = 0;
      var hy_office = 0;
      var zb_office = 0;
      var pb_office = 0;
      var area = 0;

      function countAdd() {
        var num = $("#tab-position tbody tr").length;
        var trs = $("#tab-position tbody tr");

        var t = $("select.purchaseRoomTypeName");
        for(j = 0; j < t.length; j++) {
          if(t[j].value != undefined && t[j].value == "0") {
            bg_office++;
          } else if(t[j].value == "1") {
            hy_office++;
          } else if(t[j].value == "2") {
            zb_office++;
          } else if(t[j].value == "3") {
            pb_office++;
          }
        }
        for(i = 0; i < num; i++) {
          trs.find("td:eq(4)").each(function(i) {
            area = Number($(this).text()) + Number(area);
          });
        }
        console.dir(bg_office);
        console.dir(area);
      }

      function addOrg() {
        $("#tab-orgnization").append("<tr id=" + num2 + " align='center'>" +
          "<td class='tc'><input type='checkbox' name='chkItem' /> </td>" +
          "<td>" + num2 + "</td>" +
          "<td><input name='purchaseUnitName'/></td>" +
          "<td><input name='purchaseUnitDuty'/></td>" +
          "</tr>");
        num2++;
      }

      function delOrgTr() {
        var id = [];
        var orgId = $("#orgId").val();
        $("input[name='checkboxs']:checked").each(function(){ 
          id.push($(this).val());
        }); 
        
        var ids = [];
        $("input[name='chkItem']:checked").each(function(){ 
          ids.push($(this).val());
        }); 
        if(ids.length > 0){
            $("input[name='chkItem']:checked").parents("tr").remove();
        }
        
        if(id.length > 0){
            $("input[name='checkboxs']:checked").parents("tr").remove();
            window.location.href = "${pageContext.request.contextPath}/purchaseManage/delTr.do?id="+id+"&orgId="+orgId;
        }else{
          layer.msg("请选择需要删除的部门");
        }
      }

      function delPositionTr(obj) {
        var tr = obj.parentNode.parentNode;
        tr.parentNode.removeChild(tr);
        //$(obj).parent.remove();//删除当前行   
        var num = $("#tab-position tbody tr").length;
        var trs = $("#tab-position tbody tr");
        console.dir(trs.find("td:eq(0)"));
        for(i = 0; i < num; i++) {
          trs.find("td:eq(0)").each(function(i) {
            $(this).text(i + 1);
          });
        }
        num1--;
      }
      //validate
      function validteBasic() {
        return $("#formID").validate({
          ignore: [],
          focusInvalid: false, //当为false时，验证无效时，没有焦点响应  
          onkeyup: false,
          rules: {
            levelDep: {
              required: true
            }
          },
          messages: {
            levelDep: {
              required: "必填项 !"
            }
          },
          showErrors: function(errorMap, errorList) {
            $.each(this.successList, function(index, value) {
              return $(value).popover("hide");
            });
            return $.each(errorList, function(index, value) {
              var _popover;
              _popover = $(value.element).popover({
                trigger: "manual",
                placement: "right",
                content: value.message,
                template: "<div class=\"popover\"><div class=\"arrow\"></div> <div class=\"popover-inner\"><div class=\"popover-content\"><p></p></div></div></div>"
              });
              _popover.data("bs.popover").options.content = value.message;
              return _popover.popover("show");
            });
          }
        });
      }
      //
      function validatePosition() {
        return $("#formID").validate({
          ignore: [],
          focusInvalid: false, //当为false时，验证无效时，没有焦点响应  
          onkeyup: false,
          rules: {
            purchaseRoomTypeName: {
              required: true
            },
            purchaseRoomCode: {
              required: true
            }
          },
          messages: {
            purchaseRoomTypeName: {
              required: "必填项 !"
            },
            purchaseRoomCode: {
              required: "必填项 !"
            }
          },
          showErrors: function(errorMap, errorList) {
            $.each(this.successList, function(index, value) {
              return $(value).popover("hide");
            });
            return $.each(errorList, function(index, value) {
              var _popover;
              _popover = $(value.element).popover({
                trigger: "manual",
                placement: "right",
                content: value.message,
                template: "<div class=\"popover\"><div class=\"arrow\"></div> <div class=\"popover-inner\"><div class=\"popover-content\"><p></p></div></div></div>"
              });
              _popover.data("bs.popover").options.content = value.message;
              return _popover.popover("show");
            });
          }
        });
      }

      function validateOffice() {
        return $("#formID").validate({
          ignore: [],
          focusInvalid: false, //当为false时，验证无效时，没有焦点响应  
          onkeyup: false,
          rules: {
            purchaseUnitName: {
              required: true
            }
          },
          messages: {
            purchaseUnitName: {
              required: "必填项 !"
            }
          },
          showErrors: function(errorMap, errorList) {
            $.each(this.successList, function(index, value) {
              return $(value).popover("hide");
            });
            return $.each(errorList, function(index, value) {
              console.dir(value);
              var _popover;
              _popover = $(value.element).popover({
                trigger: "manual",
                placement: "right",
                content: value.message,
                delay: {
                  show: 5000,
                  hide: 100
                },
                container: value.element,
                template: "<div class=\"popover\"><div class=\"arrow\"></div> <div class=\"popover-inner\"><div class=\"popover-content\"><p></p></div></div></div>"
              });
              _popover.data("bs.popover").options.content = value.message;
              return _popover.popover("show");
            });
          }
        });
      }

      function validateAll() {
        return $("#formID").validate({
          ignore: ":hidden",
          ignore: "",
          focusInvalid: false, //当为false时，验证无效时，没有焦点响应  
          onkeyup: false,
          wrapper: "div",
          rules: {
            levelDep: {
              required: true
            },
            purchaseUnitName: {
              required: true
            },
            purchaseRoomTypeName: {
              required: true
            },
            purchaseRoomCode: {
              required: true
            }
          },
          messages: {
            levelDep: {
              required: "必填项 !"
            },
            purchaseUnitName: {
              required: "必填项 !"
            },
            purchaseRoomTypeName: {
              required: "必填项 !"
            },
            purchaseRoomCode: {
              required: "必填项 !"
            }
          },
          errorPlacement: function(error, element) {
            error.appendTo(element.parent("div").next("td"));
          },
          showErrors: function(errorMap, errorList) {
            for(var a = 0; a < errorList.length; a++) {
            }
            this.defaultShowErrors();
          }
        });
      }

      function a(errorList) {
        return $.each(errorList, function(index, value) {
          var _popover;
          _popover = $(value.element).popover({
            trigger: "manual",
            placement: "right",
            content: value.message,
            delay: {
              show: 5000,
              hide: 100,
            },
            container: value.element,
            template: "<div class=\"popover\"><div class=\"arrow\"></div> <div class=\"popover-inner\"><div class=\"popover-content\"><p></p></div></div></div>"
          });
          _popover.data("bs.popover").options.content = value.message;
          return _popover.popover("show");
        });
      }
      
      
  function save(){
    var id = [];
    $("input[name='selectedItem']").each(function(){ 
          id.push($(this).val());
          }); 
     $("#ids").val(id);
        $("#formID").submit();
  }
  
   
  function deleteds(){
    var id = [];
    var orgId = $("#orgId").val();
    $("input[name='selectedItems']:checked").each(function(){ 
      id.push($(this).val());
    }); 
    
    var ids = [];
    $("input[name='selectedItem']:checked").each(function(){ 
      ids.push($(this).val());
    }); 
    
    if(ids.length >0){
      $("input[name='selectedItem']:checked").parents("tr").remove();
    }
    
    if(id.length > 0){
        window.location.href = "${pageContext.request.contextPath}/purchaseManage/deleteds.do?id="+id+"&orgId="+orgId;
    }
  } 
    </script>
  </head>

  <body>
    <div class="container">
      <div class="tab-content">
        <div class="tab-v2">
          <ul class="nav nav-tabs bgwhite">
            <li id="li_id_1" class="active">
              <a aria-expanded="true" href="#tab-1" data-toggle="tab" class="f18">详细信息</a>
            </li>
            <li id="li_id_2" class="">
              <a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18">部门信息</a>
            </li>
            <li id="li_id_3" class="">
              <a aria-expanded="false" href="#tab-3" data-toggle="tab" class="f18">场所信息</a>
            </li>
            <li id="li_id_3" class="">
              <a aria-expanded="false" href="#tab-4" data-toggle="tab" class="f18">管理部门信息</a>
            </li>
          </ul>
          
          
          <form action="${pageContext.request.contextPath}/purchaseManage/updatePurchaseDepI.do" method="post" id="formID">
            <input type="hidden" value="${purchaseDep.typeName }" name="orgnization.typeName" />
            <div class="tab-content padding-top-20">
              <div class="tab-pane fade active in" id="tab-1">
                <h2 class="count_flow"><i>1</i>基本信息</h2>
                <input class="hide" name="id" type="hidden" value="${purchaseDep.id }">
                <input class="hide" name="orgnization.id" id="orgId" type="hidden" value="${purchaseDep.orgId }">
                <input class="hide" name="ids" id="ids" type="hidden" >
                <ul class="ul_list">
                  <li class="col-md-3 col-sm-6 col-xs-12 pl15"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="star_red">*</i>采购机构名称</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="name" type="text" value="${purchaseDep.name }"> <span class="add-on">i</span>
                     <div class="cue">${ERR_name}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="star_red">*</i>采购机构简称</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="shortName" type="text" value="${purchaseDep.shortName }"> <span class="add-on">i</span>
                      <div class="cue">${ERR_shortName}</div>
                    </div>
                  </li>

                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="star_red">*</i>采购机构单位级别</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="levelDep" type="text" value="${purchaseDep.levelDep }"> <span class="add-on">i</span>
                       <div class="cue">${ERR_levelDep}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="star_red">*</i>行政隶属单位</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="subordinateOrgName" value="${purchaseDep.subordinateOrgName }" type="text">
                      <span class="add-on">i</span>
                      <div class="cue">${ERR_subordinateOrgName}</div>
                    </div>
                  </li>
                  
                   <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="star_red">*</i>省</span>
                    <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
                      <select name="provinceId" id="province" onchange="loadCities(this.value);">
                      </select> <input type="hidden" name="orgnization.provinceId" id="pid" value="${purchaseDep.provinceId }">
                      <div class="cue">${ERR_provinceId}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="star_red">*</i>市</span>
                    <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
                      <select name="cityId" id="city" onchange="loadTown(this.value);">
                      </select> <input type="hidden" name="orgnization.cityId" id="cid" value="${purchaseDep.cityId }">
                      <div class="cue">${ERR_provinceId}</div>
                    </div>
                  </li>
                  
                   <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="star_red">*</i>采购机构地址</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="address" type="text" value="${purchaseDep.address }"> <span class="add-on">i</span>
                      <div class="cue">${ERR_address}</div>
                    </div>
                  </li>
                  
                   <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="star_red">*</i>邮编</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="postCode" value="${ purchaseDep.postCode}" onkeyup="this.value=this.value.replace(/\D/g,'')" type="text"> <span class="add-on">i</span>
                       <div class="cue">${ERR_postCode}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="star_red">*</i>传真号</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="fax" type="text" value="${purchaseDep.fax}"> <span class="add-on">i</span>
                      <div class="cue">${ERR_fax}</div>
                    </div>
                  </li>
                  
                 

                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="star_red">*</i>值班室电话</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="dutyRoomPhone" type="text" onkeyup="this.value=this.value.replace(/\D/g,'')" value="${ purchaseDep.dutyRoomPhone}"> <span class="add-on">i</span>
                      <div class="cue">${ERR_dutyRoomPhone}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="star_red">*</i>是否具有审核供应商</span>
                    <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
                      <select name="isAuditSupplier">
                        <option  value="" <c:if test="${null eq purchaseDep.isAuditSupplier}">selected="selected" </c:if>>请选择</option>
                        <option value="1" <c:if test="${'1' eq purchaseDep.isAuditSupplier}">selected="selected" </c:if>>是</option>
                        <option value="0" <c:if test="${'0' eq purchaseDep.isAuditSupplier}">selected="selected" </c:if>>否</option>
                      </select>
                      <div class="cue">${ERR_isAuditSupplier}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-12 col-sm-12 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="star_red">*</i>采购业务范围</span>
                    <div class="col-md-12 col-sm-12 col-xs-12 p0">
                      <textarea class="col-md-12 col-sm-12 col-xs-12" name="businessRange" style="height:130px" title="不超过800个字">${purchaseDep.businessRange }</textarea>
                       <div class="cue">${ERR_businessRange}</div>
                    </div>
                  </li>
                </ul>
                
                
                <!--  class="panel panel-default" -->
                <h2 class="count_flow"><i>2</i>资质信息</h2>
                <ul class="ul_list">
                  <li class="col-md-3 col-sm-6 col-xs-12 pl15">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="star_red">*</i>采购资质等级</span>
                    <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
                      <select name="quaLevel">
                        <option  value="" selected="selected">请选择</option>
                        <option value="1" <c:if test="${'1' eq purchaseDep.quaLevel}">selected="selected" </c:if>>一级</option>
                        <option value="2" <c:if test="${'2' eq purchaseDep.quaLevel}">selected="selected" </c:if>>二级</option>
                        <option value="3" <c:if test="${'3' eq purchaseDep.quaLevel}">selected="selected" </c:if>>三级</option>
                        <option value="4" <c:if test="${'4' eq purchaseDep.quaLevel}">selected="selected" </c:if>>四级</option>
                         <option value="5" <c:if test="${'5' eq purchaseDep.quaLevel}">selected="selected" </c:if>>五级</option>
                        <option value="6" <c:if test="${'6' eq purchaseDep.quaLevel}">selected="selected" </c:if>>六级</option>
                         <option value="7" <c:if test="${'7' eq purchaseDep.quaLevel}">selected="selected" </c:if>>七级</option>
                        <option value="8" <c:if test="${'8' eq purchaseDep.quaLevel}">selected="selected" </c:if>>八级</option>
                        <option value="9" <c:if test="${'9' eq purchaseDep.quaLevel}">selected="selected" </c:if>>九级</option>
                      </select>
                       <div class="cue">${ERR_quaLevel}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="star_red">*</i>采购资质范围</span>
                    <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
                      <select name="quaRange">
                         <option  value="" <c:if test="${null eq purchaseDep.quaRange}">selected="selected" </c:if>>请选择</option>
                        <option value="1" <c:if test="${'1' eq purchaseDep.quaRange}">selected="selected" </c:if>>综合</option>
                        <option value="2" <c:if test="${'2' eq purchaseDep.quaRange}">selected="selected" </c:if>>物资</option>
                        <option value="3" <c:if test="${'3' eq purchaseDep.quaRange}">selected="selected" </c:if>>工程</option>
                        <option value="4" <c:if test="${'4' eq purchaseDep.quaRange}">selected="selected" </c:if>>服务</option>
                      </select>
                      <div class="cue">${ERR_quaRange}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="star_red">*</i>采购资质开始日期</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="Wdate w230" type="text" readonly="readonly" onClick="WdatePicker()" name="quaStartDate" value="<fmt:formatDate type='date' value='${purchaseDep.quaStartDate }' dateStyle="default" pattern="yyyy-MM-dd"/>" />
                      <div class="cue">${ERR_quaStartDate}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class=""><i class="star_red">*</i>采购资质截止日期</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="Wdate w230" type="text" readonly="readonly" onClick="WdatePicker()" name="quaEdndate" value="<fmt:formatDate type='date' value='${purchaseDep.quaEdndate }' dateStyle="default" pattern="yyyy-MM-dd"/>" />
                      <div class="cue">${ERR_quaEdndate}</div>
                    </div>
                  </li>
                                    
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="star_red">*</i>采购资质编号</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="quaCode" type="text" value="${purchaseDep.quaCode}"> <span class="add-on">i</span>
                       <div class="cue">${ERR_quaCode}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="star_red">*</i>采购资格证书图片</span>
                    <div class="uploader orange m0">
                      <up:upload id="cert_up_id"  multiple="true" businessId="${purchaseDep.id}" sysKey="2" auto="true" typeId="${PURCHASE_QUA_CERT_ID }" />
                      <up:show showId="cert_up_id" businessId="${purchaseDep.id}" sysKey="2" typeId="${PURCHASE_QUA_CERT_ID }" />
                    </div>
         <%--            <div class="cue"><br>${ERR_msg}</div> --%>
                  </li>
                </ul>
                
                
                 <h2 class="count_flow"><i>3</i>个人信息</h2>
                <ul class="ul_list">
                  <li class="col-md-3 col-sm-6 col-xs-12 pl15"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="star_red">*</i>单位主要领导姓名及电话</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="leaderTelephone" value="${purchaseDep.leaderTelephone}" type="text">
                      <span class="add-on">i</span>
         <%--              <div class="cue">${ERR_leaderTelephone}</div> --%>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="star_red">*</i>军官编制人数</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="officerCountnum" value="${purchaseDep.officerCountnum}" onkeyup="this.value=this.value.replace(/\D/g,'')" type="text">
                      <span class="add-on">i</span>
                      <div class="cue">${ERR_officerCountnum}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="star_red">*</i>军官现有人数</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="officerNowCounts" value="${purchaseDep.officerNowCounts}" onkeyup="this.value=this.value.replace(/\D/g,'')" type="text">
                      <span class="add-on">i</span>
                   <%--    <div class="cue">${ERR_officerNowCounts}</div> --%>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="star_red">*</i>士兵现有人数</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="soldierNowCounts" value="${purchaseDep.soldierNowCounts}" onkeyup="this.value=this.value.replace(/\D/g,'')" type="text">
                      <span class="add-on">i</span>
                      <div class="cue">${ERR_soldierNowCounts}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="star_red">*</i>士兵编制人数</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="soldierNum" value="${purchaseDep.soldierNum}" onkeyup="this.value=this.value.replace(/\D/g,'')" type="text">
                      <span class="add-on">i</span>
                      <div class="cue">${ERR_soldierNum}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="star_red">*</i>职工编制人数</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="staffNum" value="${purchaseDep.staffNum}" onkeyup="this.value=this.value.replace(/\D/g,'')" type="text"> <span class="add-on">i</span>
                      <div class="cue">${ERR_staffNum}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="star_red">*</i>职工现有人数</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="staffNowCounts" value="${purchaseDep.staffNowCounts}" onkeyup="this.value=this.value.replace(/\D/g,'')" type="text">
                      <span class="add-on">i</span>
                      <div class="cue">${ERR_staffNowCounts}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="star_red">*</i>具备采购资格人员数量</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="purchasersCount" value="${purchaseDep.purchasersCount}" onkeyup="this.value=this.value.replace(/\D/g,'')" type="text">
                      <span class="add-on">i</span>
                      <div class="cue">${ERR_purchasersCount}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="star_red">*</i>初级采购师人数</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="juniorPurCount" value="${purchaseDep.juniorPurCount}" onkeyup="this.value=this.value.replace(/\D/g,'')" type="text">
                      <span class="add-on">i</span>
                      <div class="cue">${ERR_juniorPurCount}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="star_red">*</i>高级采购师人数</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="seniorPurCount" value="${purchaseDep.seniorPurCount}" onkeyup="this.value=this.value.replace(/\D/g,'')" type="text">
                      <span class="add-on">i</span>
                      <div class="cue">${ERR_seniorPurCount}</div>
                    </div>
                  </li>
                </ul>
                
                
                 <h2 class="count_flow"><i>3</i>甲方信息</h2>
                <ul class="ul_list">
                  <li class="col-md-3 col-sm-6 col-xs-12 pl15"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="star_red">*</i>单位名称</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="depName" value="${purchaseDep.depName}" type="text"> <span class="add-on">i</span>
                      <div class="cue">${ERR_depName}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="star_red">*</i>法定代表人</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="legal" value="${purchaseDep.legal}" type="text"> <span class="add-on">i</span>
                      <div class="cue">${ERR_legal}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="star_red">*</i>委托代理人</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="agent" value="${purchaseDep.agent}" type="text"> <span class="add-on">i</span>
                      <div class="cue">${ERR_agent}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="star_red">*</i>联系人</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="contact" value="${purchaseDep.contact}" type="text"> <span class="add-on">i</span>
                      <div class="cue">${ERR_contact}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="star_red">*</i>联系电话</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="contactTelephone" value="${purchaseDep.contactTelephone}" onkeyup="this.value=this.value.replace(/\D/g,'')" type="text">
                      <span class="add-on">i</span>
                      <div class="cue">${ERR_contactTelephone}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="star_red">*</i>通讯地址</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="contactAddress" value="${purchaseDep.contactAddress}" type="text">
                      <span class="add-on">i</span>
                      <div class="cue">${ERR_contactAddress}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="star_red">*</i>邮政编码</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="unitPostCode" value="${purchaseDep.unitPostCode}" onkeyup="this.value=this.value.replace(/\D/g,'')" type="text">
                      <span class="add-on">i</span>
                      <div class="cue">${ERR_unitPostCode}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="star_red">*</i>付款单位</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="payDep" value="${purchaseDep.payDep}" type="text"> <span class="add-on">i</span>
                      <div class="cue">${ERR_payDep}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="star_red">*</i>开户银行</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="bank" value="${purchaseDep.bank}" type="text"> <span class="add-on">i</span>
                      <div class="cue">${ERR_bank}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="star_red">*</i>银行账号</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="bankAccount" onkeyup="this.value=this.value.replace(/\D/g,'')" value="${purchaseDep.bankAccount}" type="text">
                      <span class="add-on">i</span>
                      <div class="cue">${ERR_bankAccount}</div>
                    </div>
                  </li>
                </ul>
                <div class="clear"></div>
              </div>
 
 
              </div>

              <div class="mt40 tc mb50">
                <input type="button"  class="btn btn-windows save"  value="关闭" />
              </div>
          </form>
          </div>
        </div>
      </div>

      <form target="_blank" id="download_form_id" action="${pageContext.request.contextPath}/supplier/download.html" method="post">
        <input type="hidden" name="fileName" />
      </form>

  </body>

</html>