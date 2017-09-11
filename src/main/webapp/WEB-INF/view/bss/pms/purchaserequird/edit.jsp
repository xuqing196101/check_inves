<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <%@ include file="/WEB-INF/view/common/webupload.jsp"%>

    <script type="text/javascript" src="${pageContext.request.contextPath}/public/lock_table/moment.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/ajaxfileupload.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/lock_table/pikaday.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/lock_table/ZeroClipboard.js"></script>

    <link href="${pageContext.request.contextPath}/public/lock_table/handsontable.min.css" media="screen" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/public/lock_table/pikaday.css" media="screen" rel="stylesheet" type="text/css">

    <script type="text/javascript">
      $(function() {
          delId = [];
        });

      function referenceNO() {
        var referenceNO = $("#referenceNo").val();
        if(referenceNO == '') {
          return fasle;
        }
        $.ajax({
          url: '${pageContext.request.contextPath}/purchaser/selectUniqueReferenceNO.do',
          data: {
            "referenceNO": referenceNO
          },
          success: function(data) {
            if(data.data > 1) {
              $("#referenceNo").val("");
              layer.msg("采购需求文号已存在");
            }
          }
        });
      }

      var flag = true;

      //数量
      function sum2(obj) { 
        var bool = adds(obj);
        if(!bool) {
          layer.alert("请先填写序号", {
            shade: 0.01
          });
        } else {
          //数量
          var purchaseCount = $(obj).val() - 0; 
          //价钱
          var price2 = $(obj).parent().next().children(":last").prev();
          var price = $(price2).val() - 0;
          //单价乘以数量获得总价（万元）
          var sum = purchaseCount * price / 10000;
          var budget = $(obj).parent().next().next().children(":last").prev();
          sum = sum.toFixed(2);
          //将总价赋给预算金额
          $(budget).val(sum);
          var id = $(obj).next().val(); //parentId
          aa(id);
        }
      }

      function sum1(obj) {
        var bool = adds(obj);
        if(!bool) {
          layer.alert("请先填写序号", {
            shade: 0.01
          });
        } else {
          var purchaseCount = parseFloat($(obj).val()==""?0:$(obj).val()) - 0; //价钱
          var price2 = parseFloat($(obj).parent().prev().children(":last").prev().val()==""?0:$(obj).parent().prev().children(":last").prev().val()) - 0; //数量
          var sum = purchaseCount * price2 / 10000;
          sum = sum.toFixed(2);
          $(obj).parent().next().children(":last").prev().val(sum);
          var id = $(obj).next().val(); //parentId
          aa(id);
        }
      }

      function adds(obj) {
        var bool = true;
        var seq = $(obj).parent().parent().find("td:eq(1)").children(":eq(1)").val();
        if($.trim(seq) == "") {
          bool = false;
        }
        return bool;
      }


      function aa(id) { // id是指当前的父级parentid
        var budget = 0;
        $("#table tr").each(function() {
          var cid = $(this).find("td:eq(9)").children(":last").val(); //parentId
          var same = $(this).find("td:eq(9)").children(":last").prev().val() - 0; //价格
          if(id == cid) {
            budget = budget + same; //查出所有的子节点的值
          }
        });
        budget = budget.toFixed(2);

        $("#table tr").each(function() {
          var pid = $(this).find("td:eq(9)").children(":first").val(); //上级id

          if(id == pid) {
            $(this).find("td:eq(9)").children(":first").next().val(budget);
            var spid = $(this).find("td:eq(9)").children(":last").val();
            calc(spid);
          }
        });
        /* var did = $("table tr:eq(1)").find("td:eq(9)").children(":first").val();
        var total = 0;
        $("#table tr").each(function() {
          var cid = $(this).find("td:eq(9)").children(":last").val();
          var same = $(this).find("td:eq(9)").children(":last").prev().val() - 0;
          if(did == cid) {
            total = total + same;
          }
        });
        $("table tr:eq(1)").find("td:eq(9)").children(":first").next().val(total); */
      }

      function calc(id) {
        var bud = 0;
        $("#table tr").each(function() {
          var pid = $(this).find("td:eq(8)").children(":last").val();
          if(id == pid) {
            var currBud = $(this).find("td:eq(8)").children(":first").next().val() - 0;
            bud = parseFloat(bud) + parseFloat(currBud);
            bud = bud.toFixed(2);

            var spid = $(this).find("td:eq(8)").children(":last").val();
            aa(spid);
          }
        });

      }

      function ssl(obj) {
        if($(obj).val() != "DYLY") {
          $(obj).parent().next().children(":last").val("");
        }
        var next = $(obj).parent().parent().nextAll();
        var parent_id = $($(obj).parent().parent().children()[1]).children(":last").val();
        for(var i = 0; i < next.length; i++) {
          if(next[i].tagName == "TR") {
            if(parent_id == $($(next[i]).children()[1]).children(":last").val()) {
              break;
            }
            $($(next[i]).children()[11]).children(":last").val($(obj).val());
          }
        }
      }

      function changeType(obj) {
        var purchaseType = $(obj).find("option:selected").text(); //选中的文本
        if($.trim(purchaseType) == "单一来源") {
          $(obj).parent().next().find("input").removeAttr("readonly");
        } else {
          $(obj).parent().next().find("input").val("");
          $(obj).parent().next().find("input").attr("readonly", "readonly");
        }
        var next = $(obj).parent().parent().nextAll();
        var id = $($(obj).parent().parent().children()[1]).children(":first").val();
        for(var i = 0; i < next.length; i++) {
          if(id == $($(next[i]).children()[1]).children(":last").val()) {
            var aa = $($(next[i]).children()[11]).children(":last").val($(obj).val());
            changeType(aa);
          }
        }
      }

      function supplierReadOnly(obj) {
        var dyly = "${DYLY}";
        if($(obj).parent().prev().find("select").val() == dyly) {
          $(obj).removeAttr("readonly");
        } else {
          $(obj).val("");
          $(obj).attr("readonly", "readonly");
        }
      }

      function dyly() {
        var dyly = "${DYLY}";
        var bool = true;
        $("#detailZeroRow tr").each(function(i) {
          if($(this).find("td:eq(8)").children(":first").next().val() != "") {
            var type = $(this).find("td:eq(11)").children(":last").val();
            if($.trim(type) == dyly) { //单一来源
              var supp = $(this).find("td:eq(12)").children(":last").val();
              if($.trim(supp) == '') {
                bool = false;
                return bool;
              }

            }
          }
        });
        return bool;

      }
      function trimNull(i){
    	  var trimFlog=false;
    	  if($.trim($("input[name='list[" + i + "].goodsName']").val()) == "") {
              layer.alert("需求明细中物资类别及物资名称不能为空");
              trimFlog=true;
            } else if($.trim($("input[name='list[" + i + "].qualitStand']").val()) == "") {
              layer.alert("需求明细中质量技术标准不能为空");
              trimFlog=true;
            } else if($.trim($("input[name='list[" + i + "].item']").val()) == "") {
              layer.alert("需求明细中计量单位不能为空");
              trimFlog=true;
            } else if($.trim($("input[name='list[" + i + "].purchaseCount']").val()) == "") {
              layer.alert("需求明细中采购数量不能为空");
              trimFlog=true;
            } else if($.trim($("input[name='list[" + i + "].price']").val()) == "") {
              layer.alert("需求明细中单价不能为空");
              trimFlog=true;
            }else if($.trim($("select[name='list[" + i + "].purchaseType']").val()) == "") {
                layer.alert("需求明细中采购方式不能为空");
                trimFlog=true;
              }
    	  return trimFlog;
      }
      var flgNumber=false;
      function submit() {
    	  if(flgNumber) {
           layer.alert("节点填写错误");
           return false;
        }
    	  if($("#detailZeroRow tr").length < 2) {
              layer.alert("请添加需求明细！");
              return false;
            }else{
            	var tableTr=$("#detailZeroRow tr");
            	for(var i = 1; i < tableTr.length; i++) {
            		 if(typeof($(tableTr[i]).attr("attr"))=="undefined"){//获取子节点
            			  if(trimNull(i)){
            				  return false;
            				  break;
            			  }
            		 }
            	}
         }
        var refNo = $("#referenceNo").val();
        var name = $("#jhmc").val();
        var uniqueId = $("#uniqueId").val();
        var mobile = $("#rec_mobile").val();
        var flag = true;
        if($.trim(name) == "") {
          layer.alert("需求名称不允许为空");
          return false;
        }
        if($.trim(mobile) == "") {
            layer.alert("录入人手机号不允许为空");
            return false;
        }
        var dy = dyly();
        if(!dy) {
          layer.alert("单一来源必须填写供应商");
          return false;
        }
        var flgs = false;
        $("#table tr").each(function(i) {
          var price = $($(this).find("td:eq(8)").children()[1]).val();
          if($.trim(price) != "") {
            if($(this).find("td:eq(11)").children(":last").val() == "") {
              flgs = true;
            }
          }
        });
        if(flgs) {
          layer.alert("子节点采购方式不能为空");
          return false;
        }
        var no = $("#jhbh").val();
        var type = $("#wtype").val();
        var mobile = $("#rec_mobile").val();
        var uniqueId = $("#uniqueId").val();
        $("input[name='planName']").val(name);
        $("input[name='planNo']").val(no);
        $("input[name='referenceNo']").val(refNo);
        $("input[name='planType']").val(type);
        $("input[name='mobile']").val(mobile);
        var jsonStr = [];
        $("#table tr").each(function(i) { //遍历Table的所有Row
          if(i > 0) { //&&i<=$("#listSize").val()
            var id = $(this).find("td:eq(1)").children(":first").val();
            var parentId = $(this).find("td:eq(1)").children(":last").val();
            var seq = $(this).find("td:eq(1)").children(":first").next().val();
            var department = $.trim($(this).find("td:eq(2)").text());
            var goodsName = $(this).find("td:eq(3)").children(":last").children(":first").next().val();
            var stand = $(this).find("td:eq(4)").children(":last").val();
            if(stand.length > 250) {
              flag = false;
              layer.alert("第" + i + "行规格型号不能超过250字");
              return false;
            }
            var qualitStand = $(this).find("td:eq(5)").children(":last").val();
            if(qualitStand.length > 1000) {
              flag = false;
              layer.alert("第" + i + "行质量技术标准不能超过1000字");
              return false;
            }
            var item = $(this).find("td:eq(6)").children(":last").val();
            var purchaseCount = $(this).find("td:eq(7)").children(":first").next().val();
            var price = $(this).find("td:eq(8)").children(":first").next().val();
            var budget = $(this).find("td:eq(9)").children(":first").next().val();
            var deliverDate = $(this).find("td:eq(10)").children(":last").val();
            var purchaseTypes = $(this).find("td:eq(11)").children(":last").val();
            var supplier = $(this).find("td:eq(12)").children(":last").val();
            var isFreeTax = $(this).find("td:eq(13)").children(":last").val();
            var goodsUse = $(this).find("td:eq(14)").children(":last").val();
            var useUnit = $(this).find("td:eq(15)").children(":last").val();
            var memo = $(this).find("td:eq(16)").children(":last").val();
            var json = {
              "seq": seq,
              "id": id,
              "parentId": parentId,
              "department": department,
              "goodsName": goodsName,
              "stand": stand,
              "qualitStand": qualitStand,
              "item": item,
              "purchaseCount": purchaseCount,
              "price": price,
              "budget": budget,
              "deliverDate": deliverDate,
              "purchaseType": purchaseTypes,
              "supplier": supplier,
              "isFreeTax": isFreeTax,
              "goodsUse": goodsUse,
              "useUnit": useUnit,
              "memo": memo,
              "isMaster": i
            };

            jsonStr.push(json);

          }
        });
        if(!flag) {
          return;
        }
        $.ajax({
          type: "POST",
          url: "${pageContext.request.contextPath}/purchaser/editdetail.do",
          data: {
            "prList": JSON.stringify(jsonStr),
            "planType": type,
            "planNo": no,
            "planName": name,
            "recorderMobile": mobile,
            "referenceNo": refNo,
            "unqueId": uniqueId,
            "enterPort": $("#enterPort").val()
          },
          success: function(message) {
            window.location.href = "${pageContext.request.contextPath}/purchaser/list.do";
          },
          error: function(message) {}
        });
      }

      function gtype(obj) {
        var vals = $(obj).val();
        var goods = "${goods}";
        $("#import").attr("checked", false);
        $("input[name='enterPort']").val(0);
        $("td[name='userNone']").attr("style", "display:none");
        $("th[name='userNone']").attr("style", "display:none");
        if(vals == goods) {
          $("#dnone").show();
          $("#dnone").next().attr("class", "col-md-3 col-sm-6 col-xs-12");
        } else {
          $("#dnone").hide();
          $("#dnone").next().attr("class", "col-md-3 col-sm-6 col-xs-12 mt25 ml5");
        }
      }

      function imports(obj) {
        var bool = $(obj).is(':checked');
        if(bool == true) {
          $("td[name='userNone']").attr("style", "");
          $("th[name='userNone']").attr("style", "");
          $("input[name='enterPort']").val(1);
        } else {
          $("td[name='userNone']").attr("style", "display:none");
          $("th[name='userNone']").attr("style", "display:none");
          $("input[name='enterPort']").val(0);
        }
      }
      //重写下载方法，只下载最近上传的一个文件
      function download(id, key, zipFileName, fileName) {
        location.href = "${pageContext.request.contextPath}/file/downloadOneFile.html?id=" + id + "&key=" + key + "&zipFileName=" + encodeURI(encodeURI(zipFileName)) + "&fileName=" + encodeURI(encodeURI(fileName));
      }
      //下载模板
      function down() {
        window.location.href = "${pageContext.request.contextPath}/purchaser/download.do";
      }
      //鼠标移动显示全部内容
      var index;
      //查看编制说明
      function chakan() {
        index = layer.open({
          type: 1, //page层
          area: ['600px', '500px'],
          title: '编制说明',
          closeBtn: 1,
          shade: 0.01, //遮罩透明度
          moveType: 1, //拖拽风格，0是默认，1是传统拖动
          shift: 1, //0-6的动画形式，-1不开启
          offset: ['80px', '400px'],
          content: $('#organization'),
        });
      }

      function closeLayer() {
        layer.close(index);
      }
      //查看产品分类目录
      var datas;
      var treeObj;

      $(function() {
        var setting = {
          async: {
            autoParam: ["id"],
            enable: true,
            url: "${pageContext.request.contextPath}/purchaser/createtree.do",
            dataType: "json",
            type: "post",
          },
          callback: {
            onClick: zTreeOnClick, //点击节点触发的事件
          },
          data: {
            simpleData: {
              enable: true,
              idKey: "id",
              pIdKey: "pId",
              rootPId: 0,
            }
          },
        };
        //控制树的显示和隐藏
        var expertsTypeId = $("#expertsTypeId").val();
        if(expertsTypeId == 1 || expertsTypeId == "1") {
          treeObj = $.fn.zTree.init($("#ztree"), setting, datas);
          $("#ztree").show();
        } else {
          treeObj = $.fn.zTree.init($("#ztree"), setting, datas);
          $("#ztree").hide();
        }

      });

      function typeShow() {
        $("#ztree").show();
        layer.open({
          type: 1,
          title: '信息',
          skin: 'layui-layer-rim',
          shadeClose: true,
          offset: ['20%', '20%'],
          area: ['45%', '70%'],
          content: $("#catalogue")
        });
        $(".layui-layer-shade").remove();
      }
      var treeid = null;
      /*树点击事件*/
      function zTreeOnClick(event, treeId, treeNode) {
        treeid = treeNode.id;
      }

      function typehide() {
        layer.closeAll();
      }

      function same(obj, parentId) {
        $(obj).parent().parent().find("td:eq(1)").children(":last").val(parentId);
        $(obj).parent().parent().find("td:eq(7)").children(":last").val(parentId);
        $(obj).parent().parent().find("td:eq(8)").children(":last").val(parentId);
        $(obj).parent().parent().find("td:eq(9)").children(":last").val(parentId);
      }

      function news(obj) {
        var s = $("#count").val();
        s++;
        $("#count").val(s);
        var trs = $(obj).parent().parent();
        $(trs).after("<tr><td class='tc'><input style='border: 0px;' type='text' name='list[" + s + "].id' />" +
          "<input style='border: 0px;' type='text' name='list[" + s + "].seq' /><input style='border: 0px;' value='" + id + "' type='hidden' name='list[" + s + "].parentId' /></td>" +
          "<td class='tc p0'> <input  style='border: 0px;'  type='text' name='list[" + s + "].department' /> </td>" +
          "<td class='tc p0'> <input  style='border: 0px;' type='text' name='list[" + s + "].goodsName' /> </td>" +
          "<td class='tc p0'> <input  style='border: 0px;' type='text' name='list[" + s + "].stand' /> </td>" +
          "<td class='tc p0'> <input  style='border: 0px;' type='text' name='list[" + s + "].qualitStand' /> </td>" +
          "<td class='tc p0'> <input style='border: 0px;' type='text' name='list[" + s + "].item' /> </td>" +
          "<td class='tc p0'> <input style='border: 0px;' type='text' name='list[" + s + "].purchaseCount' /> </td>" +
          "<td class='tc p0'> <input style='border: 0px;' type='text' name='list[" + s + "].price' /> </td>" +
          "<td class='tc p0'> <input style='border: 0px;' type='text' name='list[" + s + "].budget' /> </td>" +
          "<td class='tc p0'> <input style='border: 0px;' type='text' name='list[" + s + "].deliverDate' /> </td>" +
          "<td class='tc p0'> <input style='border: 0px;' type='text' name='list[" + s + "].purchaseType' /> </td>" +
          "<td class='tc p0'> <input style='border: 0px;' type='text' name='list[" + s + "].supplier' /> </td>" +
          "<td class='tc p0'> <input style='border: 0px;' type='text' name='list[" + s + "].isFreeTax' /> </td>" +
          "<td class='tc p0'> <input style='border: 0px;' type='text' name='list[" + s + "].goodsUse' /> </td>" +
          "<td class='tc p0'> <input style='border: 0px;' type='text' name='list[" + s + "].useUnit' /> </td>" +
          "<td class='tc p0'> <input style='border: 0px;' type='text' name='list[" + s + "].memo' /> </td>" +
          "<td class='tc p0'><input class='add' name='dyadds' type='button' onclick='aadd(this)' value='添加子节点'>" +
          "<input class='btn btn-windows add' name='delt' type='button' onclick='same(this)' value='添加同级节点'>" +
          " <input class='btn btn-windows add' name='delt' type='button' onclick='news(this)' value='新加任务'></td>" +
          +"<tr/>");

      }
      //导入
      function uploadExcel() {
        index = layer.open({
          type: 1, //page层
          area: ['400px', '300px'],
          title: '导入采购需求',
          closeBtn: 1,
          shade: 0.01, //遮罩透明度
          moveType: 1, //拖拽风格，0是默认，1是传统拖动
          shift: 1, //0-6的动画形式，-1不开启
          offset: ['80px', '400px'],
          content: $('#file_div'),
        });
      }

      function fileup() {
        var detailRow = document.getElementsByName("detailRow");
        var index = detailRow.length;
        $.ajaxFileUpload({
          url: "${pageContext.request.contextPath}/purchaser/upload.do",
          secureuri: false,
          fileElementId: 'fileName',
          dataType: 'json',
          success: function(data) {
            var bool = true;
            var chars = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];
            if(data == "0") {
              layer.alert("非法文件不能导入", {
                shade: 0.01
              });
            }
            if(data == "1") {
              layer.alert("文件格式错误", {
                shade: 0.01
              });

            }
            if(data == "2") {
              layer.alert("excel编写错误，请重写编写", {
                offset: ['222px', '390px'],
                shade: 0.01
              });
            }
            if(data == "3") {
              layer.alert("节点错误，请重写编写", {
                offset: ['222px', '390px'],
                shade: 0.01
              });
            }
            if(data == "4") {
              layer.alert("父级节点不能填写数量，采购单价，请重写编写", {
                offset: ['222px', '390px'],
                shade: 0.01
              });
            }

            if(data.indexOf("行") != -1) {
              bool = false;
            }

            if(bool != true) {
              layer.alert(data, {
                offset: ['222px', '390px'],
                shade: 0.01
              });
            } else {
              layer.alert("上传成功", {
                offset: ['222px', '390px'],
                shade: 0.01
              });
              $("#detailZeroRow").empty();
              var count = 1;
              $.ajax({
                type: "POST",
                url: "${pageContext.request.contextPath}/templet/uploaddetail.html",
                data: {
                  prList: JSON.stringify(data),
                  index: index,
                  type: "edit"
                },
                success: function(result) {
                  $("#detailZeroRow").append(result);
                  init_web_upload();
                  var bool = $("input[name='import']").is(':checked');
                  if(bool == true) {
                    $("td[name='userNone']").attr("style", "");
                    $("th[name='userNone']").attr("style", "");
                  } else {
                    $("td[name='userNone']").attr("style", "display:none");
                    $("th[name='userNone']").attr("style", "display:none");
                  }

                },
                error: function(message) {}
              });
              layer.close(index);
            }
          }

        });

      }

      //动态添加
      var flgg=false;
      var indexCount=0;
      function aadd() {
    	  if(flgNumber) {
              layer.alert("节点填写错误");
              return false;
          }
        if(!flgg){
            var detailRow = document.getElementsByName("detailRow");
            var index = detailRow.length;
            indexCount=index;
            flgg=true;
        }else{
            indexCount++;
        }
        $.ajax({
          url: "${pageContext.request.contextPath}/templet/detail.html",
          type: "post",
          data: {
            "index": indexCount,
            "type": "edit"
          },
          success: function(data) {
            $("#detailZeroRow").append(data);
            init_web_upload(); //加载附件上传按钮
            var bool = $("input[name='import']").is(':checked');
            if(bool == true) {
              $("td[name='userNone']").attr("style", "");
              $("th[name='userNone']").attr("style", "");
            } else {
              $("td[name='userNone']").attr("style", "display:none");
              $("th[name='userNone']").attr("style", "display:none");
            }

          }
        });
      }
      function numberTwo(prevVal,val){
    	  var twoBool=false;
    	  var number=["一","二","三","四","五","六","七","八","九","十"];
    	  for(var i=0;i<number.length;i++){
    		  if(number[i]==prevVal){
    			   if(val==number[i+1]){
    				   twoBool=true;
    				   break;
    			   }
    		  }
    	  }
    	  return twoBool;
      }
      function numberSix(prevVal,val){
    	  var twoBool=false;
    	  var number=["a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"];
    	  for(var i=0;i<number.length;i++){
    		  if(number[i]==prevVal){
    			   if(val==number[i+1]){
    				   twoBool=true;
    				   break;
    			   }
    		  }
    	  }
    	  return twoBool;
      }
      function parentAttr(tr){
    	  $(tr).attr("attr","true");
    	  var tr7=$($(tr).children()[7]).children(":first").next();
    	  var tr8=$($(tr).children()[8]).children(":first").next();
    	  var tr3=$($(tr).children()[3]).children(":first").children(":last");
    	  /* var tr9=$($(tr).children()[9]).children(":first").next(); */
    	  $(tr7).attr("readonly", "readonly");
    	  $(tr3).removeAttr("onkeyup");
    	  $(tr7).removeAttr("onblur");
    	  $(tr7).removeAttr("onkeyup");
    	  $(tr7).val("");
    	  $(tr8).attr("readonly", "readonly");
    	  $(tr8).removeAttr("onblur");
    	  $(tr8).removeAttr("onkeyup");
    	  $(tr8).val("");
    	  /* $(tr9).attr("readonly", "readonly");
    	  $(tr9).val(""); */
      }
      //获取序号
      function getSeq(obj) {
    	  flgNumber=false;
        //当前节点的ID
        var id = $("table tr:eq(1)").find("td:eq(1)").children(":first").val();
        //当前节点的序号
        var val = $(obj).parent().parent().find("td:eq(1)").children(":first").next().val();
        //当前节点上一个的序号
        var prev = $(obj).parent().parent().prev().find("td:eq(1)").children(":first").next().val();

        val = $.trim(val);
        if(val) {
          //判断是否有括号，如果有代表是二级，四级，六级节点
          var plural = ifBracket(val);
          if(plural) {
            //等于true说明是第二级
            var second = conChniese(val);
            if(second) {
              var list = $(obj).parent().parent().prevAll();
              outer:
              for(var i = 0; i < list.length; i++) {
                var aa = $(list[i].children[1]).children(":first").next().val();
                var conPrevs = chniese(aa);
                if(conPrevs) {
                  var parentId = $(list[i].children[1]).children(":first").val();
                  parentAttr(list[i]);
                  same(obj, parentId);
                  var val=$(obj).val().substring(1,$(obj).val().length-1);
                  if(val!="一"){
	                  var prevVal="";
	                  inter:
	                  for(var j = 0; j < list.length; j++){
	                	  if($(obj).next().val()==$(list[j].children[1]).children(":last").val()){
	                		  prevVal=$(list[j].children[1]).children(":last").prev().val();
	                		  break inter;
	                	  }
	                  }
	                  prevVal=prevVal.substring(1,prevVal.length-1);
	                  if(!numberTwo(prevVal,val)){
	                	  layer.msg("序号填写错误");
	                	  flgNumber=true;
	                	  return false;
	                  }
                   }else{
                  	 var ffg=true;
                	      inter:
	                      for(var j = 0; j < list.length; j++){
	                    	  if($(obj).next().val()==$(list[j].children[1]).children(":last").val()){
	                    		  if($(obj).val()==$(list[j].children[1]).children(":first").next().val()){
	                    			  ffg=false;
	                    			  break inter;
	                    		  }
	                    		  
	                    	  }
	                      }
                    if(!ffg){
                      layer.msg("序号填写错误");
	                    	  flgNumber=true;
	                    	  return false;
                    }
                 }
                  break outer;
                }
              }
            } else {
              //等于true说明是第四级
              var fourth = conNum(val);
              if(fourth) {
                //校验上面节点是否有第三级
                var ifThird = ifThirdNode(obj);
                if(ifThird) {
                  var list = $(obj).parent().parent().prevAll();
                  outer:
                  for(var i = 0; i < list.length; i++) {
                    var aa = $(list[i].children[1]).children(":first").next().val();
                    var conPrevs = nums(aa);
                    if(conPrevs) {
                      var parentId = $(list[i].children[1]).children(":first").val();
                      parentAttr(list[i]);
                      same(obj, parentId);
                      var val=$(obj).val().substring(1,$(obj).val().length-1);
                      if(parseInt(val)!=1){
	                      var prevVal="";
	                      inter:
	                      for(var j = 0; j < list.length; j++){
	                    	  if($(obj).next().val()==$(list[j].children[1]).children(":last").val()){
	                    		  prevVal=$(list[j].children[1]).children(":last").prev().val();
	                    		  break inter;
	                    	  }
	                      }
	                      prevVal=prevVal.substring(1,prevVal.length-1);
	                      if(parseInt(val)!=parseInt(prevVal)+1){
	                    	  layer.msg("序号填写错误");
	                    	  flgNumber=true;
	                    	  return false;
	                      }
                     }else{
                    	 var ffg=true;
                    	    inter:
   	                      for(var j = 0; j < list.length; j++){
   	                    	  if($(obj).next().val()==$(list[j].children[1]).children(":last").val()){
   	                    		  if($(obj).val()==$(list[j].children[1]).children(":first").next().val()){
   	                    			  ffg=false;
   	                    			  break inter;
   	                    		  }
   	                    		  
   	                    	  }
   	                      }
                        if(!ffg){
                          layer.msg("序号填写错误");
  	                    	  flgNumber=true;
  	                    	  return false;
                        }
                     }
                      break outer;
                    }
                  }
                } else {
                	layer.msg("序号填写错误");
                  flgNumber=true;
                }
              } else {
                var ifFifth = ifFifthNode(obj);
                if(ifFifth) {
                  var list = $(obj).parent().parent().prevAll();
                  outer:
                  for(var i = 0; i < list.length; i++) {
                    var aa = $(list[i].children[1]).children(":first").next().val();
                    var conPrevs = eng(aa);
                    if(conPrevs) {
                      var parentId = $(list[i].children[1]).children(":first").val();
                      parentAttr(list[i]);
                      same(obj, parentId);
                      var val=$(obj).val().substring(1,$(obj).val().length-1);
                      if(val!="a"){
	                      var prevVal="";
	                      inter:
	                      for(var j = 0; j < list.length; j++){
	                    	  if($(obj).next().val()==$(list[j].children[1]).children(":last").val()){
	                    		  prevVal=$(list[j].children[1]).children(":last").prev().val();
	                    		  break inter;
	                    	  }
	                      }
	                      prevVal=prevVal.substring(1,prevVal.length-1);
	                      if(!numberSix(prevVal,val)){
	                    	  layer.msg("序号填写错误");
	                    	  flgNumber=true;
	                    	  return false;
	                      }
                     }else{
                    	 var ffg=true;
                    	    inter:
   	                      for(var j = 0; j < list.length; j++){
   	                    	  if($(obj).next().val()==$(list[j].children[1]).children(":last").val()){
   	                    		  if($(obj).val()==$(list[j].children[1]).children(":first").next().val()){
   	                    			  ffg=false;
   	                    			  break inter;
   	                    		  }
   	                    		  
   	                    	  }
   	                      }
                        if(!ffg){
                          layer.msg("序号填写错误");
  	                    	  flgNumber=true;
  	                    	  return false;
                        }
                     }
                      break outer;
                    }
                  }
                } else {
                	layer.msg("序号填写错误");
                  flgNumber=true;
                }
              }
            }
          } else {
            //判断是否是数字，是就是三级节点
            var third = nums(val);
            if(third) {
              var list = $(obj).parent().parent().prevAll();
              outer:
              for(var i = 0; i < list.length; i++) {
                var aa = $(list[i].children[1]).children(":first").next().val();
                var conPrevs = conChniese(aa);
                if(conPrevs) {
                  var parentId = $(list[i].children[1]).children(":first").val();
                  parentAttr(list[i]);
                  same(obj, parentId);
                  var val=$(obj).val();
                  if(parseInt(val)!=1){
                      var prevVal="";
                      inter:
                      for(var j = 0; j < list.length; j++){
                    	  if($(obj).next().val()==$(list[j].children[1]).children(":last").val()){
                    		  prevVal=$(list[j].children[1]).children(":last").prev().val();
                    		  break inter;
                    	  }
                      }
                      if(parseInt(val)!=parseInt(prevVal)+1){
                    	  layer.msg("序号填写错误");
                    	  flgNumber=true;
                    	  return false;
                      }
                 }else{
                	 var ffg=true;
                	      inter:
	                      for(var j = 0; j < list.length; j++){
	                    	  if($(obj).next().val()==$(list[j].children[1]).children(":last").val()){
	                    		  if($(obj).val()==$(list[j].children[1]).children(":first").next().val()){
	                    			  ffg=false;
	                    			  break inter;
	                    		  }
	                    		  
	                    	  }
	                      }
                    if(!ffg){
                      layer.msg("序号填写错误");
	                    	  flgNumber=true;
	                    	  return false;
                    }
                 }
                  break outer;
                }
              }
            } else {
              //先校验这个五级节点上面是否有四级节点
              var ifFour = ifFourthNode(obj);
              if(ifFour) {
                //判断是否是字母，是就是五级节点
                var fifth = eng(val);
                if(fifth) {
                  //获取父节点
                  var list = $(obj).parent().parent().prevAll();
                  outer:
                  for(var i = 0; i < list.length; i++) {
                    var aa = $(list[i].children[1]).children(":first").next().val();
                    var conPrevs = conNum(aa);
                    if(conPrevs) {
                      var parentId = $(list[i].children[1]).children(":first").val();
                      parentAttr(list[i]);
                      same(obj, parentId);
                      if(val!="a"){
	                      var prevVal="";
	                      inter:
	                      for(var j = 0; j < list.length; j++){
	                    	  if($(obj).next().val()==$(list[j].children[1]).children(":last").val()){
	                    		  prevVal=$(list[j].children[1]).children(":last").prev().val();
	                    		  break inter;
	                    	  }
	                      }
	                      if(!numberSix(prevVal,val)){
	                    	  layer.msg("序号填写错误");
	                    	  flgNumber=true;
	                    	  return false;
	                      }
                     }else{
                    	 var ffg=true;
                    	    inter:
   	                      for(var j = 0; j < list.length; j++){
   	                    	  if($(obj).next().val()==$(list[j].children[1]).children(":last").val()){
   	                    		  if($(obj).val()==$(list[j].children[1]).children(":first").next().val()){
   	                    			  ffg=false;
   	                    			  break inter;
   	                    		  }
   	                    		  
   	                    	  }
   	                      }
                        if(!ffg){
                          layer.msg("序号填写错误");
  	                    	  flgNumber=true;
  	                    	  return false;
                        }
                     }
                      break outer;
                    }
                  }
                } else {
                	layer.msg("序号填写错误");
              	  flgNumber=true;
                }
              } else {
            	  layer.msg("序号填写错误");
            	  flgNumber=true;
              }
            }
          }
        } else {
        	layer.msg("序号填写错误");
      	  flgNumber=true;
        }
      }

      function ifBracket(obj) {
        var bool = false;
        if(obj.indexOf("（") != -1) {
          bool = true;
        }
        return bool;
      }

      function ifThirdNode(obj) {
        var bool = false;
        var list = $(obj).parent().parent().prevAll();
        for(var i = 0; i < list.length; i++) {
          var aa = $(list[i].children[1]).children(":first").next().val();
          var conPrevs = nums(aa);
          if(conPrevs) {
            bool = true;
            break;
          }
        }
        return bool;
      }

      function ifFourthNode(obj) {
        var bool = false;
        var list = $(obj).parent().parent().prevAll();
        for(var i = 0; i < list.length; i++) {
          var aa = $(list[i].children[1]).children(":first").next().val();
          var conPrevs = conNum(aa);
          if(conPrevs) {
            bool = true;
            break;
          }
        }
        return bool;
      }

      function ifFifthNode(obj) {
        var bool = false;
        var list = $(obj).parent().parent().prevAll();
        for(var i = 0; i < list.length; i++) {
          var aa = $(list[i].children[1]).children(":first").next().val();
          var conPrevs = eng(aa);
          if(conPrevs) {
            bool = true;
            break;
          }
        }
        return bool;
      }

      //判断是否是中文
      function chniese(val) {

        var bool = true;
        var reg = /^[\u4e00-\u9fa5]+$/;
        if(!reg.test(val) && !val.indexOf("（") != -1) {
          bool = false;
        }
        return bool;
      }
      //判断是否包含是中文
      function conChniese(val) {
        var bool = true;
        var reg = /^.*[\u4e00-\u9fa5]+.*$/;
        if(reg.test(val) && val.indexOf("（") != -1) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      //判断是否是数字
      function nums(val) {
        var bool = true;
        var reg = /^\d{1,}$/;
        if(reg.test(val) && !val.indexOf("（") != -1) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      //是包含数字
      function conNum(val) {
        var bool = true;
        var reg = /^.*\d+.*$/;
        if(reg.test(val) && val.indexOf("（") != -1) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      //是否是英文
      function eng(val) {
        var bool = false;
        var chars = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'];
        for(var i = 0; i < chars.length; i++) {
          if(chars[i] == val) {
            bool = true;
          }
        }
        return bool;
      }

      //是否是英文
      function conEng(val) {
        var bool = false;
        var chars = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'];
        for(var i = 0; i < chars.length; i++) {
          if(val.match(chars[i]) && val.indexOf("（") != -1) {
            bool = true;
          }
        }
        return bool;
      }

      //获取距离当前元素最近的上级元素
      function getPreviousElement(methodName) {
        var parentId;
        $("#table tr").each(function(i) { //遍历Table的所有Row
          var seq = $('#seq' + i).val();
          if(eval(methodName + "(\"" + seq + "\")")) {
            parentId = $("#id" + i).val();
          }
        })
        return parentId;
      }
      //校验供应商名称
      function checkSupplierName(obj) {
        var name = $(obj).val();
        if(name != null) {
          $.ajax({
            type: "POST",
            async: false,
            dataType: "text",
            data: {
              "name": name
            },
            url: "${pageContext.request.contextPath }/purchaser/checkSupplierName.do",
            success: function(data) {
              if(data == 'true') {
                $(obj).val("");
                layer.alert("库中没有此供应商，请重新输入");
              }
            }
          });
        }
      }
      function deleteRowRequired(obj,status){
    	  flgNumber=false;
    	  var id=[],seq=[];
    	  var trAll=$("#detailZeroRow tr");
    	  if(trAll.length<=2){
    		  layer.alert("至少保留两行！", {
    	        offset: ['222px', '390px'],
    	        shade: 0.01
    	     });
    	  }else{
    		  var tr = $(obj).parent().parent();
    		  var trId=$(tr).children(":first").next().children(":first").val();
    		  var trPid=$(tr).children(":first").next().children(":last").val();
    		  if(typeof($(tr).attr("attr"))!="undefined"){//父节点删除
    			  var trNextAll=tr.nextAll();
    			  var nextFlg=false;
    			  for(var i=0;i<trNextAll.length;i++){
    				  var tdNextId=$(trNextAll[i]).children(":first").next().children(":last").val();
    			      if(trId==tdNextId){
    			    	  nextFlg=true;
    			    	  break;
    			      }
    			  }
    			  if(nextFlg){//父节点下面有子节点的不允许删
    				  layer.alert("不能删除父节点！", {
			    	        offset: ['222px', '390px'],
			    	        shade: 0.01
			    	   });
    			  }
    		  }else{//删除子节点
    			  var trNextPid=$(tr).next().children(":first").next().children(":last").val();
    			  var trPrevPid=$(tr).prev().children(":first").next().children(":last").val();
    			  if(trPid==trNextPid||trPid==trPrevPid){//说明字节点不是只有一个，删除当前节点，改变后面节点的序号
    				  var trNextAll=tr.nextAll();
        			for(var i=0;i<trNextAll.length;i++){
        				var tdNextId=$(trNextAll[i]).children(":first").next().children(":last").val();
    					  if(trPid==tdNextId){
    						  var nextNumber=$(trNextAll[i]).children(":first").next().children(":first").next();
    						  if(nextNumber.val().indexOf("（")>=0){//说明2,4,6级
    							  var second = conChniese($(nextNumber).val());//2级
    							  if(second){
    								  var vals=$(nextNumber).val().substring(1,$(nextNumber).val().length-1);
    								  var number=["一","二","三","四","五","六","七","八","九","十"];
    								  num:
    								  for(var j=0;j<number.length;j++){
        							   if(vals==number[j]){
        								    id.push($(trNextAll[i]).children(":first").next().children(":first").val());
        								    seq.push("（"+number[j-1]+"）");
        							      $(nextNumber).val("（"+number[j-1]+"）");
          							    break num;
        							   }
    								  }
    							  }
    							  var fourth = conNum($(nextNumber).val());//4级
    							  if(fourth){
    								  var vals=$(nextNumber).val().substring(1,$(nextNumber).val().length-1);
    								  id.push($(trNextAll[i]).children(":first").next().children(":first").val());
  								    seq.push("（"+(parseInt(vals)-1)+"）");
    								  $(nextNumber).val("（"+(parseInt(vals)-1)+"）");//重新赋值序号
    							  }
    							  var ifFifth = ifFifthNode($(nextNumber).val());//6级
    							  if(ifFifth){
    								  var vals=$(nextNumber).val().substring(1,$(nextNumber).val().length-1);
    								  var number=["a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"];
    								  num:  
    								  for(var j=0;j<number.length;j++){
      							    	if(vals==number[j]){
      							    		id.push($(trNextAll[i]).children(":first").next().children(":first").val());
      	  								  seq.push("（"+number[j-1]+"）");
      							    		$(nextNumber).val("（"+number[j-1]+"）");
      							    		break num;
      							    	}
      							    }
    							  }
    						  }else{//1,3,5级
    							  var third = nums($(nextNumber).val());//3级
    						    if(third){
    						    	id.push($(trNextAll[i]).children(":first").next().children(":first").val());
	  								  seq.push(parseInt($(nextNumber).val())-1);
    						    	$(nextNumber).val(parseInt($(nextNumber).val())-1)//重新赋值序号
    						    }
    							  var fifth = eng($(nextNumber).val());//5级
    							  if(fifth){
    								  var number=["a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"];
    							    num:
    								  for(var j=0;j<number.length;j++){
    							    	if($(nextNumber).val()==number[j]){
    							    		id.push($(trNextAll[i]).children(":first").next().children(":first").val());
    		  								seq.push(number[j-1]);
    							    		$(nextNumber).val(number[j-1]);
    							    		break num;
    							    	}
    							    }
    							  }
    						  }
    					  }
    				  }
        			if(status=="old"){
        				ajaxDeleteRequired(trId,id,seq);
        			}
        			var price;
        			if(trPid!=trPrevPid){
        				price=$($(tr).prev().children()[8]).children(":first").next();
        			}else{
        				price=$($(tr).next().children()[8]).children(":first").next();
        			}
        			var trNextAllNum=tr.nextAll();
	  				  if(trNextAllNum.length>0){
		    				  for(var i=0;i<trNextAllNum.length;i++){
		    					  $($(trNextAllNum[i]).children()[0]).text(parseInt($($(trNextAllNum[i]).children()[0]).text())-1);
		    				  }
		    				  indexCount=parseInt($($(trNextAllNum[trNextAllNum.length-1]).children()[0]).text())-1;
	  				  }else{
	  					  indexCount=parseInt($($(tr).children()[0]).text())-2;
	  				  }
    				  $(tr).remove();
    				  sum1(price);
    			  }else{//删除当前节点，把父节点的父节点的readOnly=false,并且删除tr上的attr=“true”
    				    $(tr).prev().removeAttr("attr");
    		    	  var tr7=$($(tr).prev().children()[7]).children(":first").next();
    		    	  var tr8=$($(tr).prev().children()[8]).children(":first").next();
    		    	  var tr3=$($(tr).prev().children()[3]).children(":first").children(":last");
    		    	 /*  var tr9=$($(tr).prev().children()[9]).children(":first").next(); */
    		    	  $(tr7).removeAttr("readonly");
    		    	  $(tr3).attr("onkeyup","listName(this)");
    		    	  $(tr3).after($("#materialName"))
    		    	  $(tr7).attr("onblur","sum2(this)");
    		    	  $(tr7).attr("onkeyup","checkNum(this,1)");
    		    	  $(tr7).val("");
    		    	  $(tr8).removeAttr("readonly");
    		    	  $(tr8).attr("onblur","sum1(this)");
    		    	  $(tr8).attr("onkeyup","checkNum(this,2)");
    		    	  $(tr8).val("");
    		    	  /* $(tr9).removeAttr("readonly");
    		    	  $(tr9).val(""); */
    		    	  if(status=="old"){
    		    		  ajaxDeleteRequired(trId,null,null);
          			}
    		    	  var trNextAllNum=tr.nextAll();
	    				  if(trNextAllNum.length>0){
		    				  for(var i=0;i<trNextAllNum.length;i++){
		    					  $($(trNextAllNum[i]).children()[0]).text(parseInt($($(trNextAllNum[i]).children()[0]).text())-1);
		    				  }
		    				  indexCount=parseInt($($(trNextAllNum[trNextAllNum.length-1]).children()[0]).text())-1;
	    				  }else{
	    					  indexCount=parseInt($($(tr).children()[0]).text())-2;
	    				  }
    				  
    		    	  $(tr).remove();
    		    	  sum1(tr8);
    			  }
    			  
    		  }
    	  }
      }
      function ajaxDeleteRequired(id,ids,seqs){
    	  if(ids!=null){
    		  ids=ids.join(",");
    		  seqs=seqs.join(",");
    	  }
    	  $.ajax({
              type: "POST",
              url: "${pageContext.request.contextPath}/purchaser/deleteRequired.do",
              data: {
                "id": id,
                "ids":ids,
                "seqs":seqs
              },
              success: function(data) {
            	   if(data=="ok"){
            		   layer.msg("节点删除成功");
            	   }
              },
            }); 
      }
      function deleteRow(obj) {//删除导入的，或者新添加的
    	  deleteRowRequired(obj,"new");
      }

      function delRowIndex(obj) { //删除原有的数据
    	  deleteRowRequired(obj,"old");
        /* var detailRow = document.getElementsByName("detailRow");
        var index = detailRow.length;

        var input = $(obj).prev().val();
        delId.push(input);
        if(index < 3) {
          layer.alert("至少保留两行！", {
            offset: ['222px', '390px'],
            shade: 0.01
          });
        } else {
          var tr = $(obj).parent().parent();
          var nextEle = $(obj).parent().parent().next().children();
          var val = $(tr).find("td:eq(8)").children(":first").next().val();
          if($.trim(val) != "") {
            var input = $(obj).prev().val();
            if(typeof(input) != "undefined") {
            }
            $(obj).parent().parent().remove();
          } else if(nextEle.length < 1) {
            var input = $(obj).prev().val();
            if(typeof(input) != "undefined") {
            }
            $(obj).parent().parent().remove();
          } else {
            layer.alert("只能删除末级节点", {
              offset: ['222px', '390px'],
              shade: 0.01
            });
          }
          $("#detailZeroRow tr").each(function(index) {
            $(this).find("td:eq(0)").text(index + 1);
          });
        }
        var cid = $(obj).parent().prev().prev().prev().prev().prev().prev().prev().prev().prev().prev().children(":last");
        sum2(cid); */
      }
      //检索名字
      function listName(obj) {
        var name = $(obj).val();
        if(name == "" || name == null) {
          $("#materialName").html("");
          $("#materialName").addClass("dnone");
          return;
        }
        $.ajax({
          type: "POST",
          dataType: "json",
          url: "${pageContext.request.contextPath }/purchaser/listName.do?name=" + name,
          success: function(data) {
            if(data.length > 0) {
              var html = "";
              for(var i = 0; i < data.length; i++) {
            	  var name="";
            	  var title="";
            	  if(data[i].name.split("@").length>1){
            		  name=data[i].name.split("@")[0];
            		  title=data[i].name.split("@")[1];
            	  }else{
            		  name=data[i].name.split("@")[0];
            	  }
                html += "<div style='width:178px;height:20px;' class='pointer' onmouseover='changeColor(this)' onclick='getValue(this)' title='"+title+"'>" + name + "</div>";
              }
              $("#materialName").html(html);
              $("#materialName").removeClass("dnone");
              $(obj).after($("#materialName"));
            } else {
              $("#materialName").html("");
              $("#materialName").addClass("dnone");
            }
          }
        });
      }
      
      function selectNode(obj,index){
        //获取节点
        var node = $(obj).val();
        //判断是几级节点
        var bool = ifNode(node);
        var parentId = $(obj).next().val();
        if(bool){
          if(bool == 1){
            if(parentId != '1'){
              layer.msg("节点错误");
              return;
            }
          } else if (bool == 2) {
            //获取上级节点
            var list = $(obj).parent().parent().prevAll();
            for(var i = 0; i < list.length; i++){
              var aa = $(list[i].children[1]).children(":first").val();
              if(aa == parentId){
                var bb = $(list[i].children[1]).children(":last").val();
                if(bb != '1'){
                  layer.msg("节点错误");
                  return;
                }
              }
            }
          } else if (bool == 3) {
            //获取上级节点
            var list = $(obj).parent().parent().prevAll();
            for(var i = 0; i < list.length; i++){
              var aa = $(list[i].children[1]).children(":first").val();
              if(aa == parentId){
                var bb = $(list[i].children[1]).children(":first").next().val();
                var cc = conChniese(bb);
                if(!cc){
                  layer.msg("节点错误");
                  return;
                }
              }
            }
          } else if (bool == 4) {
            //获取上级节点
            var list = $(obj).parent().parent().prevAll();
            for(var i = 0; i < list.length; i++){
              var aa = $(list[i].children[1]).children(":first").val();
              if(aa == parentId){
                var bb = $(list[i].children[1]).children(":first").next().val();
                var cc = nums(bb);
                if(!cc){
                  layer.msg("节点错误");
                  return;
                }
              }
            }
          } else if (bool == 5) {
            //获取上级节点
            var list = $(obj).parent().parent().prevAll();
            for(var i = 0; i < list.length; i++){
              var aa = $(list[i].children[1]).children(":first").val();
              if(aa == parentId){
                var bb = $(list[i].children[1]).children(":first").next().val();
                var cc = conNum(bb);
                if(!cc){
                  layer.msg("节点错误");
                  return;
                }
              }
            }
          } else {
            //获取上级节点
            var list = $(obj).parent().parent().prevAll();
            for(var i = 0; i < list.length; i++){
              var aa = $(list[i].children[1]).children(":first").val();
              if(aa == parentId){
                var bb = $(list[i].children[1]).children(":first").next().val();
                var cc = eng(bb);
                if(!cc){
                  layer.msg("节点错误");
                  return;
                }
              }
            }
          }
        } else {
          layer("节点错误");
        }
      }
      
      function ifNode(obj){
        var node = null;
        //一级节点
        var one = chniese(obj);
        //二级节点
        var two = conChniese(obj);
        //三级节点
        var three = nums(obj);
        //四级节点
        var four = conNum(obj);
        //五级节点
        var five = eng(obj);
        //六级节点
        var six = conEng(obj);
        if (one) {
          node = 1;
        } else if (two) {
          node = 2;
        } else if (three) {
          node = 3;
        } else if (four) {
          node = 4;
        } else if (five) {
          node = 5;
        } else {
          node = 6;
        }
        return node;
      }
      
      //改变颜色
      function changeColor(obj) {
        $(obj).css("background-color", "#eee");
      }

      //获取值
      function getValue(obj) {
        $(obj).parent().parent().children(":first").next().val($(obj).html());
        $(obj).parent().addClass("dnone");
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
            <a href="javascript:void(0);">保障作业系统</a>
          </li>
          <li>
            <a href="javascript:void(0);">采购计划管理</a>
          </li>
          <li class="active">
            <a href="javascript:jumppage('${pageContext.request.contextPath}/purchaser/list.html');">采购需求编报</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    <div class="container container_box" id="container">
      <input type="hidden" value="${uniqueId}" id="uniqueId">
      <div>
        <h2 class="count_flow">
          <i>1</i>需求主信息
        </h2>
        <ul class="ul_list">
          <li class="col-md-3 col-sm-6 col-xs-12 pl15"><span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="star_red">*</div>需求名称</span>
            <div class="input-append input_group col-sm-12 col-xs-12 p0">
              <input type="text" class="input_group" id="jhmc" value="${list[0].planName}"> <span class="add-on">i</span>
            </div>
          </li>
          <li class="col-md-3 col-sm-6 col-xs-12 hide"><span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">需求编号</span>
            <div class="input-append input_group col-sm-12 col-xs-12 p0">
              <input type="text" class="input_group" id="jhbh" value="${list[0].planNo}"> <span class="add-on">i</span>
            </div>
          </li>

          <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">需求文号</span>
            <div class="input-append input_group col-sm-12 col-xs-12 p0">
              <input type="text" class="input_group" id="referenceNo" name="referenceNo" value="${list[0].referenceNo}" onblur="referenceNO()"> <span class="add-on">i</span>
            </div>
          </li>

          <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>类别</span>
            <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
              <select name="planType" id="wtype" onchange="gtype(this)">
                <c:forEach items="${types}" var="tp">
                  <c:if test="${tp.id==list[0].planType }">
                    <option value="${tp.id}" selected="selected">${tp.name }</option>
                  </c:if>
                  <c:if test="${tp.id!=list[0].planType }">
                    <option value="${tp.id}">${tp.name }</option>
                  </c:if>
                </c:forEach>
              </select>
            </div>
          </li>

          <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="star_red">*</div>录入人手机号</span>
            <div class="input-append input_group col-sm-12 col-xs-12 p0">
              <input type="text" class="input_group" id="rec_mobile" name="mobile" value="${list[0].recorderMobile }"> <span class="add-on">i</span>
            </div>
          </li>
          <c:if test="${list[0].planType == goods}">
            <li class="col-md-3 col-sm-6 col-xs-12 mt25 ml5" id="dnone">
              <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
                <input type="checkbox" id="import" name="" value="进口" <c:if test="${list[0].enterPort==1}">checked="checked"</c:if> onchange="imports(this)" />进口
              </div>
            </li>
          </c:if>
          <c:if test="${list[0].planType!= goods}">
            <li class="col-md-3 col-sm-6 col-xs-12 mt25 ml5 hide mt25 ml5" id="dnone">
              <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
                <input type="checkbox" id="import" name="" value="进口" onchange="imports(this)" />进口
              </div>
            </li>
          </c:if>
          <li class="col-md-3 col-sm-6 col-xs-12 mt25 ml5"><span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">需求附件</span> <u:upload id="detail" multiple="true" buttonName="上传附件" businessId="${fileId}" sysKey="2" typeId="${detailId}" auto="true" />
            <u:show showId="detailshow" businessId="${fileId}" sysKey="2" typeId="${detailId}" />
          </li>
        </ul>
      </div>

      <h2 class="count_flow">
      <i>2</i>需求明细
    </h2>
      <div class="content mt0 require_ul_list">
        <div class="col-md-12 p115 mt10">
          <button class="btn btn-windows add" onclick="aadd()">添加</button>
          <button class="btn btn-windows input" onclick="down()">下载模板</button>
          <button class="btn btn-windows input" onclick="uploadExcel();">导入</button>
          <button class="btn padding-left-10 padding-right-10 btn_back" onclick="typeShow()">查看产品分类目录</button>
          <button class="btn padding-left-10 padding-right-10 btn_back" onclick="chakan()">查看编制说明</button>
        </div>
        <div class="content " id="content">
          <input type="hidden" name="enterPort" id="enterPort" value="${list[0].enterPort}" /> <input type="hidden" id="oneNodeId" value=""> <input type="hidden" id="twoNodeId" value="">
          <input type="hidden" id="threeNodeId" value=""> <input type="hidden" id="fourNodeId" value=""> <input type="hidden" id="fiveNodeId" value=""> <input type="hidden" id="sixNodeId" value=""> <input type="hidden" id="oneNodeId" value="">
          <table id="table" class="table table-bordered table-condensed lockout">
            <thead>
              <tr id="scroll_top">
                <th class="info w30">行号</th>
                <th class="info w50">序号</th>
                <th class="info department">需求部门</th>
                <th class="info goodsname">物资类别<br/>及名称
                </th>
                <th class="info stand">规格型号</th>
                <th class="info qualitstand">质量技术标准<br/>（技术参数）
                </th>
                <th class="info item">计量<br>单位
                </th>
                <th class="info purchasecount">采购<br>数量
                </th>
                <th class="info price">单价<br/>（元）
                </th>
                <th class="info budget">预算金额<br/>（万元）
                </th>
                <th class="info deliverdate">交货期限</th>
                <th class="info purchasetype">采购方式</th>
                <th class="info purchasename">供应商名称</th>
                <th class="info freetax" name="userNone" <c:if test="${list[0].enterPort==0}">style="display:none;"</c:if>>是否申请<br/>办理免税
                </th>

                <th name="userNone" class="info" <c:if test="${list[0].enterPort==0}">style="display:none;"</c:if>>物资用途<br/>（仅进口）
                </th>
                <th name="userNone" class="info" <c:if test="${list[0].enterPort==0}">style="display:none;"</c:if>>使用单位<br/>（仅进口）
                </th>

                <!--    <th class="info">物资用途<br/>（仅进口）</th>
              <th class="info">使用单位<br/>（仅进口）</th> -->
                <th class="info memo">备注</th>
                <th class="extrafile">附件</th>
                <th class="w100">操作</th>
              </tr>
            </thead>
            <form id="edit_form" action="${pageContext.request.contextPath}/purchaser/update.html" method="post">
              <input type="hidden" id="listSize" value="${listSize }" />
              <tbody id="detailZeroRow">
                <c:forEach items="${list }" var="obj" varStatus="vs">
                  <tr style="cursor: pointer;" name="detailRow"  <c:if test="${obj.price=='' || obj.price==null}">attr="true"</c:if> >
                    <td class="tc">
                      ${vs.index+1 }
                    </td>
                    <td class="tc">
                      <input type="hidden" id="id${vs.index}" name="list[${vs.index }].id" value="${obj.id }">
                      <input type="text" class="w70" id="seq${vs.index}" name="list[${vs.index }].seq" value="${obj.seq}" onblur="selectNode(this,'${vs.index}')">
                      <input type="hidden" id="parentId${vs.index}" name="list[${vs.index }].parentId" value="${obj.parentId }"> 
                    </td>
                    <td>
                      <div class="department">${obj.department}</div>
                    </td>
                    <td>
                      <div class="goodsname">
                        <input type="hidden" name="ss" value="${obj.id }">
                        <input type="text" name="list[${vs.index }].goodsName" class="target" onkeyup="listName(this)" value="${obj.goodsName}">
                      </div>
                    </td>
                    <td><input type="hidden" name="ss" value="${obj.id }">
                      <input type="text" name="list[${vs.index }].stand" value="${obj.stand}" class="stand">
                    </td>
                    <td>
                      <input type="hidden" name="ss" value="${obj.id }">
                      <input type="text" name="list[${vs.index }].qualitStand" value="${obj.qualitStand}" class="qualitstand">
                    </td>
                    <td><input type="hidden" name="ss" value="${obj.id }">
                      <input type="text" name="list[${vs.index }].item"  value="${obj.item}" class="item">
                    </td>
                    <td>
                      <input type="hidden" name="ss" value="${obj.id }">
                      <input maxlength="11" class="purchasecount"  <c:if test="${obj.price!=''&&obj.price!=null}">onblur="sum2(this);"</c:if> <c:if test="${obj.price==''||obj.price==null}">readonly="readonly"</c:if> type="text" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" name="list[${vs.index }].purchaseCount" value="${obj.purchaseCount}" />
                      <input type="hidden" name="ss" value="${obj.parentId }">
                    </td>
                    <td class="tl w80">
                      <input type="hidden" name="ss" value="${obj.id }">
                      <input maxlength="11" class="price" name="list[${vs.index }].price" <c:if test="${obj.price==''||obj.price==null}">readonly="readonly"</c:if> <c:if test="${obj.price!=''&&obj.price!=null}">onblur="sum1(this);"</c:if> value="${obj.price}" type="text" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" />
                      <input type="hidden" name="ss" value="${obj.parentId }">
                    </td>
                    <td><input type="hidden" name="ss" value="${obj.id }">
                      <input maxlength="11" id="budget" name="list[${vs.index }].budget" type="text" readonly="readonly" value="${obj.budget}" class="budget" />
                      <input type="hidden" name="ss" value="${obj.parentId }">
                    </td>
                    <td class="tc">
                      <input type="hidden" name="ss" value="${obj.id }">
                      <textarea name="list[${vs.index }].deliverDate" class="target deliverdate">${obj.deliverDate}</textarea>
                    </td>

                    <td>
                      <input type="hidden" name="ss" value="${obj.id}"/>
                      <select name="list[${vs.index }].purchaseType" <c:if test="${obj.price==null}"> onchange="changeType(this);" </c:if>
                        <c:if test="${obj.price!=null}"> onchange="ssl(this);" </c:if>
                        class="purchasetype" id="select">
                        <option value="">请选择</option>
                        <c:forEach items="${kind}" var="kind">
                          <option value="${kind.id}" <c:if test="${kind.id == obj.purchaseType}">selected="selected" </c:if>> ${kind.name}
                          </option>
                        </c:forEach>
                      </select>
                    </td>
                    <td>
                      <input type="hidden" name="ss" value="${obj.id }">
                      <textarea name="list[${vs.index }].supplier" onmouseover="supplierReadOnly(this)" class="target purchasename">${obj.supplier}</textarea>
                    </td>
                    <td name="userNone" <c:if test="${list[0].enterPort==0}"> style="display:none;" </c:if>><input type="text" name="list[${vs.index }].isFreeTax" value="${obj.isFreeTax}" class="freetax"></td>

                    <td name="userNone" <c:if test="${list[0].enterPort==0}"> style="display:none;" </c:if>><input type="text" name="list[${vs.index }].goodsUse" value="${obj.goodsUse}"></td>
                    <td name="userNone" <c:if test="${list[0].enterPort==0}"> style="display:none;" </c:if>><input type="text" name="list[${vs.index }].userUnit" value="${obj.useUnit}"></td>

                    <td>
                      <div class="memo">
                        <input type="hidden" name="ss" value="${obj.id }">
                        <textarea name="list[${vs.index }].memo" class="target purchasename">${obj.memo}</textarea>
                      </div>
                    </td>
                    <td>
                      <c:if test="${obj.purchaseCount!=null}">
                        <div class="extrafile">
                          <u:upload id="up_${vs.index}" multiple="true" businessId="${obj.id}" buttonName="上传文件" sysKey="2" typeId="${detailId}" auto="true" />
                          <u:show showId="show_${vs.index}" businessId="${obj.id}" sysKey="2" typeId="${detailId}" />
                        </div>
                      </c:if> <input type="hidden" class="ptype" name="ptype" value="${obj.purchaseType}" /></td>
                    <td class="tc w100"><input type="hidden" value="${obj.id}" /> <button type="button" class="btn" onclick="delRowIndex(this);">删除</button></td>

                  </tr>

                </c:forEach>
              </tbody>
            </form>

          </table>
        </div>
        <div class="col-md-12  mt10 col-sm-12 col-xs-12 tc">
          <input type="hidden" name="planName" />
          <input type="hidden" name="planNo" />
          <input type="hidden" id="planNo" value="${planNo }" />
          <input type="hidden" name="planType" />
          <input type="hidden" name="mobile" />
          <input type="hidden" name="referenceNo" />
          <input type="hidden" name="delobjId" />
          <input class="btn btn-windows git" type="button" onclick="submit()" value="保存"><input class="btn btn-windows back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
        </div>

      </div>
    </div>
    <div id="organization" class="dnone">
      <p align="center">编制说明
        <p style="margin-left: 20px;">1、请严格按照序号顺序为：一、（一）、1（1）、a、（a）的顺序填写序号，括号为中文括号</p>

        <p style="margin-left: 20px;">2、任务明细最多为六级,请勿多于六级</p>

        <p style="margin-left: 20px;">3、请勿空行填写</p>

        <p style="margin-left: 20px;">4、需求单位名称不能为空</p>

        <p style="margin-left: 20px;">5、请按表式填写需求明细。用户可以编辑行，但不能增加或删除列。</p>

        <p style="margin-left: 20px;">6、最子级请严格按照填写说明填写，父级菜单请将序号与金额填写正确(金额=所有子项金额/10000)</p>

        <p style="margin-left: 20px;">7、采购方式填写选项包括：公开招标、邀请招标、竞争性谈判、询价、单一来源。</p>

        <p style="margin-left: 20px;">8、选择单一来源采购方式的，必须填写供应商名称；选择其他采购方式的不填。</p>

        <p style="margin-left: 20px;">9、规格型号和质量技术标准内容分别不得超过250、1000字。超过此范围的，请以附件形式另报。并在Excel中对应的值写“另附”，详见Excel模板。</p>

        <p style="margin-left: 20px;">10、采购数量、单价和预算金额必须为数字格式。其中单价单位为“元”，预算金额单位为“万元”。</p>
        <button class="btn padding-left-10 padding-right-10 btn_back" style="margin-left: 230px;" onclick="closeLayer()">确定</button>

    </div>

    <input type="hidden" id="count" value="0">
    <div id="catalogue" class="dnone">
      <div id="ztree" class="ztree"></div>
    </div>
    <div class=" clear margin-top-30" id="file_div" style="display: none;">
      <div class="col-md-12 col-sm-12 col-xs-12">

        <p class="red" style="font-size: 16px;">注：请选择无标签水印的文件！</p>

        <input type="file" id="fileName" class="input_group" name="file">
      </div>
      <div class="col-md-12 col-sm-12 col-xs-12 mt20 tc">
        <input type="button" class="btn input" onclick="fileup()" value="导入" />
      </div>
    </div>
    <div id="materialName" class="dnone" style="width:178px;max-height:400px;overflow:scroll;border:1px solid grey;">

    </div>
  </body>

</html>
