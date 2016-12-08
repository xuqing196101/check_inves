<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE>
<html>
  <head>
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

        $.ajax({
          type: 'post',
          url: "${pageContext.request.contextPath}/purchaseManage/getProvinceList.do?",
          data: {
            pid: 1
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
          }
        });

      });

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
            $.each(data, function(idx, item) {
              $("#city").append("<option value='-1'>请选择</option>");
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
          success: function(data) {
            console.dir(data);
            truealert(data.message, data.success == false ? 5 : 1);
          }
        });

      }

      function stashBasic() {
        validateAll();
        return;
        $("#formID").submit();
      }

      function stashOffice() {
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
            layer.closeAll();
            parent.location.href = "${pageContext.request.contextPath}/purchaseManage/purchaseUnitList.do";
          }
        });
      }
      //添加场所
      function dynamicaddTwo() {
        var typeName = $("#typeName").val();
        showiframe("添加场所", 1000, 600, "${pageContext.request.contextPath}/purchaseManage/addPosition.do?typeName=" + typeName, "-4");
      }
      //添加采购管理部门
      function dynamicaddThree() {
        var typeName = $("#typeName").val();
        showiframe("添加采购管理部门", 1000, 600, "${pageContext.request.contextPath}/purchaseManage/addPurchaseOrg.do?typeName=" + typeName, "-4");
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
          "<td>" + num2 + "</td>" +
          "<td><input name='purchaseUnitName'/></td>" +
          "<td><input name='purchaseUnitDuty'/></td>" +
          "<td><a href=\'javascript:void(0);\' onclick=\'delOrgTr(this)\'>删除</a></td>" +
          "</tr>");
        num2++;
      }

      function delOrgTr(obj) {
        var tr = obj.parentNode.parentNode;
        tr.parentNode.removeChild(tr);
        var num = $("#tab-orgnization tbody tr").length;
        var trs = $("#tab-orgnization tbody tr");
        console.dir(trs.find("td:eq(0)"));
        for(i = 0; i < num; i++) {
          trs.find("td:eq(0)").each(function(i) {
            $(this).text(i + 1);
          });
        }
        num2--;
      }

      function delPositionTr(obj) {
        var tr = obj.parentNode.parentNode;
        tr.parentNode.removeChild(tr);
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
              hide: 100
            },
            container: value.element,
            template: "<div class=\"popover\"><div class=\"arrow\"></div> <div class=\"popover-inner\"><div class=\"popover-content\"><p></p></div></div></div>"
          });
          _popover.data("bs.popover").options.content = value.message;
          return _popover.popover("show");
        });
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
          <form action="${pageContext.request.contextPath}/purchaseManage/savePurchaseDep.html" method="post" id="formID">
            <div class="tab-content padding-top-20">
              <div class="tab-pane fade active in" id="tab-1">
                <h2 class="count_flow"><i>1</i>基本信息</h2>
                <input class="hide" name="orgnization.id" type="hidden" value="${purchaseDep.orgId}">
                <input class="hide" name="id" type="hidden" value="${purchaseDep.id }">
                <ul class="ul_list">
                  <li class="col-md-3 col-sm-6 col-xs-12 pl15"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">采购机构名称</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="orgnization.name" type="text">
                      <span class="add-on">i</span>
                      <div class="b f18 ml10 red hand">${name_msg}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">采购机构简称</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="orgnization.shortName" type="text" value="${purchaseDep.shortName }"> <span class="add-on">i</span>
                      <div class="b f18 ml10 red hand">${name_msg}</div>
                    </div>
                  </li>

                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">采购机构单位级别</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="levelDep" type="text" value="${purchaseDep.levelDep }"> <span class="add-on">i</span>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">行政隶属单位</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="subordinateOrgName" value="${purchaseDep.subordinateOrgName }" type="text">
                      <span class="add-on">i</span>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">采购业务范围</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="businessRange" value="${purchaseDep.businessRange }" type="text">
                      <span class="add-on">i</span>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">采购机构地址</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="orgnization.address" type="text" value="${purchaseDep.address }"> <span class="add-on">i</span>
                      <div class="b f18 ml10 red hand">${name_msg}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">邮编</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="orgnization.postCode" value="${ purchaseDep.postCode}" type="text"> <span class="add-on">i</span>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">传真号</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="fax" type="text" value="${ purchaseDep.fax}"> <span class="add-on">i</span>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">省</span>
                    <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
                      <select name="provinceId" id="province" onchange="loadCities(this.value);">
                      </select> <input type="hidden" name="orgnization.provinceId" id="pid" value="${purchaseDep.provinceId }">
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">市</span>
                    <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
                      <select name="cityId" id="city" onchange="loadTown(this.value);">
                      </select> <input type="hidden" name="orgnization.cityId" id="cid" value="${purchaseDep.cityId }">
                    </div>
                  </li>

                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">值班室电话</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="dutyRoomPhone" type="text" value="${ purchaseDep.dutyRoomPhone}"> <span class="add-on">i</span>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">是否具有审核供应商</span>
                    <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
                      <select name="isAuditSupplier">
                        <option value="">-请选择-</option>
                        <option value="1">是</option>
                        <option value="0">否</option>
                      </select>
                    </div>
                  </li>
                </ul>
                
                <!--  class="panel panel-default" -->
                <h2 class="count_flow"><i>2</i>资质信息</h2>
                <ul class="ul_list">
                  <li class="col-md-3 col-sm-6 col-xs-12 pl15">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">采购资质等级</span>
                    <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
                      <select name="quaLevel">
                        <option value="">-请选择-</option>
                        <option value="1">一级</option>
                        <option value="2">二级</option>
                        <option value="3">三级</option>
                        <option value="4">四级</option>
                        <option value="5">五级</option>
                        <option value="6">六级</option>
                        <option value="7">七级</option>
                        <option value="8">八级</option>
                        <option value="9">九级</option>
                      </select>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">采购资质范围</span>
                    <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
                      <select name="quaRange">
                        <option value="">-请选择-</option>
                        <option value="1">综合</option>
                        <option value="1">物资</option>
                        <option value="1">工程</option>
                        <option value="1">服务</option>
                      </select>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i
                                                class="red">＊</i>采购资质开始日期</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="Wdate w230" type="text" readonly="readonly" onClick="WdatePicker()" name="quaStartDate" value="<fmt:formatDate value=" ${purchaseDep.quaStartDate} " pattern="yyyy-MM-dd " />" />
                    </div>
                  </li>
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class=""><i
                                                class="red">＊</i>采购资质截止日期</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="Wdate w230" type="text" readonly="readonly" onClick="WdatePicker()" name="quaEdndate" value="<fmt:formatDate value=" ${purchaseDep.quaEdndate} " pattern="yyyy-MM-dd " />" />
                    </div>
                  </li>
                  <%-- <li>
                                        <div>
                                            <input id="d12" type="text"/>
                                            <img onclick="WdatePicker({el:'d12'})" src="${pageContext.request.contextPath}/public/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle">
                                        </div>
                                    </li> --%>
                                    
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">采购资质编号</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="quaCode" type="text" value="${purchaseDep.quaCode}"> <span class="add-on">i</span>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">＊</i>采购资格证书图片</span>
                    <div class="uploader orange m0">
                      <up:upload id="cert_up_id" businessId="${purchaseDep.id}" sysKey="2" auto="true" typeId="${PURCHASE_QUA_CERT_ID }" />
                      <up:show showId="cert_up_id" businessId="${purchaseDep.id}" sysKey="2" typeId="${PURCHASE_QUA_CERT_ID }" />
                    </div>
                  </li>
                </ul>
                
                
                <h2 class="count_flow"><i>3</i>个人信息</h2>
                <ul class="ul_list">
                  <li class="col-md-3 col-sm-6 col-xs-12 pl15"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">单位主要领导姓名及电话</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="leaderTelephone" type="text">
                      <span class="add-on">i</span>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">军官编制人数</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="officerCountnum" type="text">
                      <span class="add-on">i</span>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">军官现有人数</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="officerNowCounts" type="text">
                      <span class="add-on">i</span>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">士兵现有人数</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="soldierNowCounts" type="text">
                      <span class="add-on">i</span>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">士兵编制人数</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="soldierNum" type="text">
                      <span class="add-on">i</span>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">职工编制人数</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="staffNum" type="text"> <span class="add-on">i</span>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">职工现有人数</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="staffNowCounts" type="text">
                      <span class="add-on">i</span>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">具备采购资格人员数量</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="purchasersCount" type="text">
                      <span class="add-on">i</span>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">初级采购师人数</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="juniorPurCount" type="text">
                      <span class="add-on">i</span>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">高级采购师人数</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="seniorPurCount" type="text">
                      <span class="add-on">i</span>
                    </div>
                  </li>
                </ul>
                
                
                <h2 class="count_flow"><i>3</i>甲方信息</h2>
                <ul class="ul_list">
                  <li class="col-md-3 col-sm-6 col-xs-12 pl15"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">单位名称</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="depName" type="text"> <span class="add-on">i</span>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">法定代表人</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="legal" type="text"> <span class="add-on">i</span>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">委托代理人</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="agent" type="text"> <span class="add-on">i</span>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">联系人</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="contact" type="text"> <span class="add-on">i</span>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">联系电话</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="contactTelephone" type="text">
                      <span class="add-on">i</span>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">通讯地址</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="contactAddress" type="text">
                      <span class="add-on">i</span>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">邮政编码</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="unitPostCode" type="text">
                      <span class="add-on">i</span>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">付款单位</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="payDep" type="text"> <span class="add-on">i</span>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">开户银行</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="bank" type="text"> <span class="add-on">i</span>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">银行账号</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="bankAccount" type="text">
                      <span class="add-on">i</span>
                    </div>
                  </li>
                </ul>
                <div class="clear"></div>
                <!-- <div class="mt40 tc mb50">
                                        <input type="button"
                                            class="btn  padding-right-20 btn_back margin-5"
                                            onclick="updateBasic();" value="确认" /> <input type="button"
                                            class="btn  padding-right-20 btn_back margin-5"
                                            onclick="stashBasic();" value="暂存" /> <input type="button"
                                            class="btn  padding-right-20 btn_back margin-5"
                                            onclick="history.go(-1)" value="取消" />
                                    </div> -->
              </div>


              <!-- 财务信息 -->
              <div class="tab-pane fade height-450" id="tab-2">
                <div class="headline-v2">
                  <h2>部门信息</h2>
                </div>
                <div class="col-md-12 pl20 mt10">
                  <button type="button" class="btn btn-windows add" id="dynamicAdd" onclick="addOrg();">添加</button>
                </div>
                <div class="content table_box">
                  <table class="table table-bordered table-condensed table-hover table-striped" id="tab-orgnization">
                    <thead>
                      <tr>
                        <th class="info f13">序号</th>
                        <th class="info f13">部门名称</th>
                        <th class="info f13">主要职责</th>
                        <th class="info f13">操作</th>
                      </tr>
                    </thead>
                    <tbody>
                    </tbody>
                  </table>
                </div>
                <div class="clear"></div>
              </div>

              <!-- 股东信息 -->
              <div class="tab-pane fade height-200" id="tab-3">
                <h2 class="count_flow"><i>1</i>基本信息</h2>
                <ul class="ul_list">
                  <li class="col-md-3 col-sm-6 col-xs-12 pl15"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">办公场地总面积</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="officeArea" type="text" value="${purchaseDep.officeArea}"> <span class="add-on">i</span>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">办公司数量</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="officeCount" type="text">
                      <span class="add-on">i</span>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">会议室数量</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="mettingRoomCount" type="text">
                      <span class="add-on">i</span>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">招标室数量</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="inviteRoomCount" type="text">
                      <span class="add-on">i</span>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">评标室数量</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="bidRoomCount" type="text">
                      <span class="add-on">i</span>
                    </div>
                  </li>
                </ul>
                
                
                <h2 class="count_flow"><i>2</i>添加场所</h2>
                <ul class="ul_list">
                  <div class="col-md-12 pl20 mt10">
                    <!--  onclick= addOffice()-->
                    <button class="btn btn-windows add" type="button" id="dynamicAdd" onclick="dynamicaddTwo();">添加场所</button>
                  </div>
                  <div class="content table_box">
                    <table class="table table-bordered table-condensed table-hover table-striped" id="tab-position">
                      <thead>
                        <tr>
                          <th class="info f13">序号</th>
                          <th class="info f13">类型</th>
                          <th class="info f13">编号</th>
                          <th class="info f13">位置</th>
                          <th class="info f13">面积</th>
                          <th class="info f13">接入方式</th>
                          <th class="info f13">容纳人员数量</th>
                          <th class="info f13">是否介入网络</th>
                          <th class="info f13">是否具备监控系统</th>
                          <th class="info f13">操作</th>
                        </tr>
                      </thead>
                      <tbody>
                      </tbody>
                    </table>
                    <div class="clear"></div>
                </ul>

                </div>

                <!-- <div class="mt40 tc mb50">
                                        <input type="button" class="btn  padding-right-20 btn_back margin-5" onclick="updatePosition();" value="确认"/>
                                        <input type="button" class="btn  padding-right-20 btn_back margin-5" onclick="stashPosition();" value="暂存"/> 
                                        <input type="button" class="btn  padding-right-20 btn_back margin-5" onclick="history.go(-1)" value="取消"/>
                                    </div> -->
                <!--  -->
                
                
                <div class="tab-pane fade height-200" id="tab-4">
                  <div class="headline-v2">
                    <h2>关联管理部门</h2>
                  </div>
                  <div class="col-md-12 pl20 mt10">
                    <button type="button" class="btn btn-windows add" id="dynamicAdd" onclick="dynamicaddThree();">关联</button>
                  </div>
                  <div class="content table_box">
                    <table class="table table-bordered table-condensed table-hover table-striped" id="tab">
                      <thead>
                        <tr>
                          <th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
                          <th class="info w50">序号</th>
                          <th class="info f13">机构名称</th>
                          <th class="hide f13">机构id</th>
                          <th class="info f13">操作</th>
                        </tr>
                      </thead>
                      <tbody>

                      </tbody>
                    </table>
                  </div>

                </div>
              </div>

              <div class="mt40 tc mb50">
                <input type="button" class="btn btn-windows save" onclick="update();" value="保存" />
                <!-- <input type="button" class="btn" onclick="stash();"  value="暂存" />  -->
                <input type="button" class="btn btn-windows cancel" onclick="history.go(-1)" value="取消" />
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