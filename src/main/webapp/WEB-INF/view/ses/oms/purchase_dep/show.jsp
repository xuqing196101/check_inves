<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

  <head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<%@ include file="/WEB-INF/view/common/webupload.jsp" %>
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

      function dynamicaddThree() {
        var typeName = $("#typeName").val();
        showiframe("添加机构", 1000, 600, "${pageContext.request.contextPath}/purchaseManage/addPurchaseOrg.do?typeName=" + typeName, "-4");
      }

      function showiframe(titles, width, height, url, top) {
        if(top == null || top == "underfined") {
          top = 120;
        }
        layer.open({
          type: 2,
          title: [titles, "background-color:#83b0f3;color:#fff;font-size:16px;text-align:center;"],
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
        //$(obj).parent.remove();//删除当前行   
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
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li><a href="javascript:void(0);"> 首页</a>
					</li>
					<li><a href="javascript:void(0);">支撑系统</a>
					</li>
					<li><a href="javascript:void(0);">后台管理</a>
					</li>
					<li class="active"><a href="javascript:void(0);">采购机构管理</a>
					</li>
				</ul>
			</div>
		</div>
    <div class="container mt20">
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
              <a aria-expanded="false" href="#tab-4" data-toggle="tab" class="f18">关联采购管理部门信息</a>
            </li>
          </ul>
          <form action="${pageContext.request.contextPath}/purchaseManage/updatePurchaseDep.do" method="post" id="formID">
            <input type="hidden" value="${purchaseDep.typeName }" name="orgnization.typeName" />
            <div class="tab-content padding-top-20">
              <div class="tab-pane fade active in" id="tab-1">
                <h2 class="count_flow jbxx">基本信息</h2>
                <input class="hide" name="orgnization.id" type="hidden" value="${purchaseDep.orgId }">
                <input class="hide" name="id" type="hidden" value="${purchaseDep.id }">
                <table class="table table-bordered">
                  <tbody>
                  
                    <tr>
                      <td class="bggrey" width="13%" >采购机构名称：</td>
                      <td width="20%">${purchaseDep.name }</td>
                      <td class="bggrey" width="13%">采购机构简称：</td>
                      <td width="20%">${purchaseDep.shortName }</td>
                      <td class="bggrey" width="14%">采购机构单位级别：</td>
                      <td width="20%">
                        <c:forEach items="${unitLevelList}" var="unitLevel">
                           <c:if test="${unitLevel.id == purchaseDep.levelDep}">
                             ${unitLevel.name}
                           </c:if>
                        </c:forEach>
                      </td>
                    </tr>
                    
                    <tr>
                      <td class="bggrey">行政隶属单位：</td>
                      <td>${purchaseDep.subordinateOrgName }</td>
                      <td class="bggrey">采购业务范围：</td>
                      <td>${purchaseDep.businessRange }</td>
                      <td class="bggrey">采购机构地址：</td>
                      <td>${purchaseDep.address }</td>
                    </tr>
                    
                    <tr>
                      <td class="bggrey">邮编：</td>
                      <td>${purchaseDep.postCode }</td>
                      <td class="bggrey">省：</td>
                      <td>${area.name }</td>
                      <td class="bggrey">市：</td>
                      <td>${area1.name }</td>
                    </tr>
                    
                    <tr>
                      <td class="bggrey" width="13%">值班室电话：</td>
                      <td width="20%">${purchaseDep.dutyRoomPhone }</td>
                      <td class="bggrey" width="13%">传真号：</td>
                      <td width="20%">${purchaseDep.fax }</td>
                      <td class="bggrey" width="14%">是否具有审核供应商：</td>
                      <td width="20%">
                        <c:if test="${'1' eq purchaseDep.isAuditSupplier}">是 </c:if>
                        <c:if test="${'0' eq purchaseDep.isAuditSupplier}">否 </c:if>
                      </td>
                    </tr>
                    
                  </tbody>
                </table>
                <!--  class="panel panel-default" -->
                <h2 class="count_flow jbxx">资质信息</h2>
                <table class="table table-bordered">
                  <tbody>
                  
                    <tr>
                      <td class="bggrey" width="13%">采购资质等级：</td>
                      <td  width="20%">
                        <c:if test="${'1' eq purchaseDep.quaLevel}">一级 </c:if>
                        <c:if test="${'2' eq purchaseDep.quaLevel}">二级 </c:if>
                        <c:if test="${'3' eq purchaseDep.quaLevel}">三级 </c:if>
                        <c:if test="${'4' eq purchaseDep.quaLevel}">四级</c:if>
                        <c:if test="${'5' eq purchaseDep.quaLevel}">五级 </c:if>
                        <c:if test="${'6' eq purchaseDep.quaLevel}">六级</c:if>
                        <c:if test="${'7' eq purchaseDep.quaLevel}">七级</c:if>
                        <c:if test="${'8' eq purchaseDep.quaLevel}">八级 </c:if>
                        <c:if test="${'9' eq purchaseDep.quaLevel}">九级</c:if>
                      </td>
                      <td class="bggrey "  width="13%">采购资质范围：</td>
                      <td  width="20%">
                        <c:if test="${'1' eq purchaseDep.quaRange}">综合 </c:if>
                        <c:if test="${'2' eq purchaseDep.quaRange}">物资 </c:if>
                        <c:if test="${'3' eq purchaseDep.quaRange}">工程</c:if>
                        <c:if test="${'4' eq purchaseDep.quaRange}">服务</c:if>
                      </td>
                      <td class="bggrey"  width="14%">采购资质编号：</td>
                      <td  width="20%">${purchaseDep.quaCode }</td>
                    </tr>
                    
                    <tr>
                      <td class="bggrey ">采购资质开始日期：</td>
                      <td>
                        <fmt:formatDate value="${purchaseDep.quaStartDate}" pattern="yyyy-MM-dd" />
                      </td>
                      <td class="bggrey">采购资质截止日期：</td>
                      <td>
                        <fmt:formatDate value="${purchaseDep.quaEdndate}" pattern="yyyy-MM-dd" />
                      </td>
                      <td class="bggrey ">采购资格证书图片：</td>
                      <td>
                        <u:show delete="false" showId="cert_show_id" businessId="${purchaseDep.id}" typeId="${purchaseTypeId}" sysKey="2" />
                      </td>
                    </tr>
                    
                  </tbody>
                </table>
                <h2 class="count_flow jbxx">个人信息</h2>
                <table class="table table-bordered">
                  <tbody>
                  
                    <tr>
                      <td class="bggrey"  width="20%">单位主要领导姓名：</td>
                      <td width="30%">${purchaseDep.leaderTelephone}</td>
                      <td class="bggrey" width="20%">军官编制人数：</td>
                      <td width="30%">${purchaseDep.officerCountnum}</td>
                    </tr>
                    <tr>
                      <td class="bggrey ">军官现有人数：</td>
                      <td>${purchaseDep.officerNowCounts}</td>
                      <td class="bggrey">士兵现有人数：</td>
                      <td>${purchaseDep.soldierNowCounts}</td>
                    </tr>
                    
                    <tr>
                      <td class="bggrey ">士兵编制人数：</td>
                      <td>${purchaseDep.soldierNum}</td>
                      <td class="bggrey ">职工编制人数：</td>
                      <td>${purchaseDep.staffNum}</td>
                    </tr>
                    
                    <tr>
                      <td class="bggrey">职工现有人数：</td>
                      <td>${purchaseDep.staffNowCounts}</td>
                      <td class="bggrey ">具备采购资格人员数量：</td>
                      <td>${purchaseDep.purchasersCount}</td>
                    </tr>
                    
                    <tr>
                      <td class="bggrey ">初级采购师人数：</td>
                      <td>${purchaseDep.juniorPurCount}</td>
                      <td class="bggrey">高级采购师人数：</td>
                      <td>${purchaseDep.seniorPurCount}</td>
                    </tr>
                    
                  </tbody>
                </table>
                <h2 class="count_flow jbxx">甲方信息</h2>
                <table class="table table-bordered">
                  <tbody>
                    <tr>
                      <td class="bggrey" width="20%">单位名称：</td>
                      <td width="30%">${purchaseDep.depName}</td>
                      <td class="bggrey" width="20%">法定代表人：</td>
                      <td width="30%">${purchaseDep.legal}</td>
                    </tr>
                    <tr>
                      <td class="bggrey ">委托代理人：</td>
                      <td>${purchaseDep.agent}</td>
                      <td class="bggrey">联系人：</td>
                      <td>${purchaseDep.contact}</td>
                    </tr>
                    
                    <tr>
                      <td class="bggrey ">联系电话：</td>
                      <td>${purchaseDep.contactTelephone}</td>
                      <td class="bggrey ">通讯地址：</td>
                      <td>${purchaseDep.contactAddress}</td>
                    </tr>
                    
                    <tr>
                      <td class="bggrey">邮政编码：</td>
                      <td>${purchaseDep.unitPostCode}</td>
                      <td class="bggrey ">付款单位：</td>
                      <td>${purchaseDep.payDep}</td>
                    </tr>
                    
                    <tr>
                      <td class="bggrey ">开户银行：</td>
                      <td>${purchaseDep.bank}</td>
                      <td class="bggrey ">银行账号：</td>
                      <td>${purchaseDep.bankAccount}</td>
                    </tr>
                    
                  </tbody>
                </table>
              </div>

              <!-- 财务信息 -->
              <div class="tab-pane fade height-450" id="tab-2">
                <div class="headline-v2">
                  <h2>部门信息</h2>
                </div>
                <div class="content table_box">
                  <table class="table table-bordered table-condensed table-hover table-striped" id="tab-orgnization">
                    <thead>
                      <tr>
                        <th class="info f13">序号</th>
                        <th class="info f13">部门名称</th>
                        <th class="info f13">主要职责</th>
                      </tr>
                    </thead>
                    <tbody>
                           <c:forEach items="${orgInfos}" var="obj" varStatus="vs">
                          <tr style="cursor: pointer;">
                              <td class="tc w50">${(vs.index+1)}</td>
                              <td class="tc">${obj.purchaseUnitName}</td>
                              <td class="tc">${obj.purchaseUnitDuty}</td>
                             </tr>
                        </c:forEach>
                    </tbody>
                  </table>
                </div>
                <div class="clear"></div>
              </div>

              <!-- 股东信息 -->
              <div class="tab-pane fade height-200" id="tab-3">
                <h2 class="count_flow jbxx">基本信息</h2>
                <table class="table table-bordered">
                  <tbody>
                  
                    <tr>
                      <td class="bggrey">办公场地总面积：</td>
                      <td>${purchaseDep.officeArea}</td>
                      <td class="bggrey ">办公司数量：</td>
                      <td>${purchaseDep.officeCount}</td>
                      <td class="bggrey ">会议室数量：</td>
                      <td>${purchaseDep.mettingRoomCount}</td>
                    </tr>
                    
                    <tr>
                      <td class="bggrey">招标室数量：</td>
                      <td>${purchaseDep.inviteRoomCount}</td>
                      <td class="bggrey ">评标室数量：</td>
                      <td>${purchaseDep.bidRoomCount}</td>
                      <td class="bggrey ">是否接入网络：</td>
                      <td>
                        <c:if test="${purchaseDep.accessNetwork eq '0' }">是</c:if>
                        <c:if test="${purchaseDep.accessNetwork eq '1' }">否</c:if>
                      </td>
                    </tr>
                    <tr>
                      <td class="bggrey">接入方式：</td>
                      <td>${purchaseDep.accessWay}</td>
                      <td class="bggrey ">是否具备视频监控系统：</td>
                      <td colspan="3">
                        <c:if test="${purchaseDep.videoSurveillance eq '0' }">是</c:if>
                        <c:if test="${purchaseDep.videoSurveillance eq '1' }">否</c:if>
                      </td>
                    </tr>
                  </tbody>
                </table>
                <h2 class="count_flow jbxx">场所信息</h2>
                <div class="content table_box">
                  <table class="table table-bordered table-condensed table-hover table-striped" id="tab-position">
                    <thead>
                    
                      <tr>
                        <th class="info f13">序号</th>
                        <th class="info f13">类型</th>
                        <th class="info f13">编号</th>
                        <th class="info f13">位置</th>
                        <th class="info f13">面积</th>
                        <th class="info f13">容纳人员数量</th>
                      </tr>
                      
                    </thead>
                    <tbody>
                        <c:forEach items="${locales}" var="obj" varStatus="vs">
                              <tr style="cursor: pointer;">
                              <td class="tc w50">${(vs.index+1)}</td>
                              <td class="tc">
                              <c:if test="${'1' eq obj.siteType}"> 办公室</c:if>
                              <c:if test="${'2' eq obj.siteType}"> 会议室</c:if>
                              <c:if test="${'3' eq obj.siteType}"> 招标室</c:if>
                              <c:if test="${'4' eq obj.siteType}"> 评标室</c:if>
                             </td>
                              <td class="tc">${obj.siteNumber}</td>
                              <td class="tc">${obj.location}</td>
                              <td class="tc">${obj.area}</td>
                              <td class="tc">${obj.crewSize}</td>
                             </tr>
                          </c:forEach>
                    </tbody>
                  </table>
                </div>
              </div>
              <div class="tab-pane fade height-200" id="tab-4">
                <div class="headline-v2">
                  <h2>采购管理部门信息</h2>
                </div>
                <div class="content table_box">
                  <table class="table table-bordered table-condensed table-hover table-striped" id="tab">
                    <thead>
                      <tr>
                        <th class="info w50">序号</th>
                        <th class="info f13">机构名称</th>
                      </tr>
                    </thead>
                    <tbody>
                        <c:if test="${lists != null}">
                        <c:forEach items="${lists}" var="obj" varStatus="vs">
                             <tr style="cursor: pointer;">
                              <td class="tc w50">${(vs.index+1)}</td>
                              <td class="tc w50">${obj.name}</td>
                             </tr>
                          </c:forEach>
                          </c:if>
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
            <div class="mt20 tc col-sm-12 col-xs-12 col-md-12">
                <input type="button" class="btn btn-windows back" onclick="history.go(-1)" value="返回" />
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