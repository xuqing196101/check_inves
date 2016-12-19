<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

  <head>
    <base href="${pageContext.request.contextPath}/">

    <title>确定中标供应商</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->

  </head>
  <script type="text/javascript">
    /** 全选全不选 */
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

    /** 单选 */
    function check(){
         var count=0;
         var checklist = document.getElementsByName ("chkItem");
         var checkAll = document.getElementById("checkAll");
         for(var i=0;i<checklist.length;i++){
               if(checklist[i].checked == false){
                   checkAll.checked = false;
                   break;
               }
               for(var j=0;j<checklist.length;j++){
                     if(checklist[j].checked == true){
                           checkAll.checked = true;
                           count++;
                       }
                 }
           }
    }


      function save() {
        var id = [];
        $('input[name="chkItem"]:checked').each(function() {
          id.push($(this).val());
        });
        if(id.length >= 1) {
        	 layer.confirm('确定后将不可修改,是否确定', {title:'提示',offset: ['100px','300px'],shade:0.01}, function(index){
        		   var json='${supplierCheckPassJosn}';
        		   layer.close(index);
                   $.ajax({
                         type: "post",
                         url:"${pageContext.request.contextPath}/winningSupplier/comparison.do",
                         data:"checkPassId="+id+"&&jsonCheckPass="+json,
                         dataType:"json",
                         success: function(data) {
//                           alert(data);
                           if(data=="SCCUESS"){
                               window.location.href = '${pageContext.request.contextPath}/winningSupplier/selectSupplier.do?projectId=${projectId}&&flowDefineId=${flowDefineId}';
                           }else{
                               var iframeWin;
                               var type=0;
                                   layer.open({
                                         type: 2,
                                         title: '上传',
                                         shadeClose: false,
                                         shade: 0.01,
                                         area: ['367px', '180px'], //宽高
                                         content: '${pageContext.request.contextPath}/winningSupplier/upload.html?packageId=${packageId}&&flowDefineId=${flowDefineId}&&projectId=${projectId}&&checkPassId='+id,
                                         success: function(layero, index){
                                             iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                                           },
                                         btn: ['保存', '关闭'] 
                                             ,yes: function(){
                                                iframeWin.upload();
                                                type=1;
                                             }
                                             ,btn2: function(){
                                            	 delFileAjax();
                                             },
                                          end:function(){
                                        	  if(type!=1){
                                        		  delFileAjax();
                                        	  }
                                          }
                                       });
                                 }
                             }     
                     });
             });
      
        } else {
          layer.alert("请选择供应商", {
            offset: ['100px', '390px'],
            shade: 0.01
          });
        }
      }
      
      /**ajax 删除文件 **/
      function delFileAjax(){
    	  $.ajax({
              type: "POST",
              dataType : "json",
              url:'${pageContext.request.contextPath}/winningSupplier/deleFile.do?packageId=${packageId}',
              success: function(data) {
                  var map =data;
                  alert(map);
                  if(map=="SCCUESS"){
                     window.location.href = '${pageContext.request.contextPath}/winningSupplier/selectSupplier.do?projectId=${projectId}&&flowDefineId=${flowDefineId}';
                  }else{
                    layer.msg("请上传");
                  }
                  
              }
          });
      }
      
  </script>

  <body>
    <div class="col-md-12 p0">
      <ul class="flow_step">
        <li class="active">
          <a href="${pageContext.request.contextPath}/winningSupplier/selectSupplier.html?projectId=${projectId}">01、确认中标供应商</a>
          <i></i>
        </li>
        <li>
          <a href="javascript:void(0);" class="dhx_move_denied">02、中标通知书</a>
          <i></i>
        </li>
        <li>
          <a href="javascript:void(0);">03、未中标通知书</a>
        </li>
      </ul>
    </div>
      <div class="headline-v2">
        <h2>确认中标供应商</h2>
      </div>
      <c:if test="${view != 1 }">
        <div class="col-md-12 pl20 mt10">
          <button class="btn btn-windows add" onclick="save();" type="button">确定</button>
        </div>
      </c:if>
      <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
          <thead>
            <tr>
                 <th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
              <th class="info">供应商名称</th>
              <th class="info">参加时间</th>
              <th class="info">总报价（万元）</th>
              <th class="info">总得分</th>
              <th class="info">排名</th>
              <c:if test="${view == 1 }">
              <th class="info">中标状态</th>
              <th class="info">中标金额（万元）</th>
              <th class="info">占比（%）</th>
              </c:if>
            </tr>
          </thead>
          <c:forEach items="${supplierCheckPass}" var="checkpass" varStatus="vs">
            <tr>
              <td class="tc opinter"><input onclick="check();" type="checkbox" name="chkItem" value="${checkpass.id}" /></td>
              <td class="tc opinter" onclick="">${checkpass.supplier.supplierName}</td>
              <td class="tc opinter" onclick="">
                <fmt:formatDate value='${checkpass.joinTime}' pattern="yyyy-MM-dd " />
              </td>
              <td class="tc opinter" onclick="">${checkpass.totalPrice}</td>
              <td class="tc opinter" onclick="">${checkpass.totalScore}</td>
              <td class="tc opinter" onclick="">${(vs.index+1)}</td>
               <c:if test="${view == 1 }">
               <c:if test="${checkpass.isWonBid == 0}">
                <td class="tc opinter" >未中标</td>
               </c:if>
               <c:if test="${checkpass.isWonBid == 1}">
                <td class="tc opinter" >已中标</td>
               </c:if>
                <td class="tc opinter">${checkpass.wonPrice }</td>
                <td class="tc opinter">${checkpass.priceRatio}</td>
              </c:if>
            </tr>
          </c:forEach>
        </table>
           <div class="col-md-12 tc">
              <button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
            </div>
      </div>
  </body>

</html>