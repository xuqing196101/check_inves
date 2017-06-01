<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>标书管理</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script type="text/javascript">
  function OpenFile(fileId) {
    var obj = document.getElementById("TANGER_OCX");
    obj.Menubar = true;
    obj.Caption = "( 双击可放大 ! )";
    if(fileId != 0){
      obj.BeginOpenFromURL("${pageContext.request.contextPath}/supplierProject/loadFile.html?fileId="+fileId, true, false, 'word.document');// 异步加载, 服务器文件路径
    }
  }
  
  function queryVersion(){
    var obj = document.getElementById("TANGER_OCX");
    var v = obj.GetProductVerString();
    obj.ShowTipMessage("当前ntko版本",v);
  }
  
  function inputTemplete(){
    var obj = document.getElementById("TANGER_OCX");
  }
  
  function saveFile(){
    var obj = document.getElementById("TANGER_OCX");
  }
  
  function closeFile(){
    var obj = document.getElementById("TANGER_OCX");
    obj.close();
  }
   
  //标记
  function mark(e, target, kind, id){
    var projectId = $("#projectId").val();
    var obj = document.getElementById("TANGER_OCX");
    //obj.ShowTipMessage("提示","绑定指标时请把光标停在选中内容的起始处");
    if(typeof(obj.ActiveDocument) == "undefined"){
      obj.ShowTipMessage("提示","文档加载失败或者未加载文档");
      return;
    }
    //获取当前页码
    var page = obj.ActiveDocument.Application.Selection.information(1);
    if(confirm("确定【"+target+"】指标的绑定内容从第"+page+"页开始吗？")){
      obj.ActiveDocument.BookMarks.Add(target);
      var idType="";
      if(kind == 'first'){
        //初审项id
        idType = "firstAuditId";
      }
      if(kind == 'second'){
        //初审项id
        idType = "smId";
      }
      $.ajax({  
               type: "POST",  
               url: "${pageContext.request.contextPath}/supplierProject/saveBindingIndex.html?projectId="+projectId+"&page="+page+"&"+idType+"="+id,  
               dataType: 'json',  
               success:function(result){
                   var html = "<div class='shanchu light_icon'><a href='javascript:void(0)' onclick='delMark(this,"+'"'+target+'"'+","+'"'+kind+'",'+'"'+id+'"'+");'>删除</a></div>";
          html+= "<div class='dinwei light_icon'><a href='javascript:void(0)' onclick='searchMark(this,"+'"'+target+'"'+","+'"'+kind+'",'+'"'+id+'"'+");'>定位</a></div>";
          $(e).parent().after(html);
          $(e).parent().remove();
          obj.ShowTipMessage("提示","【"+target+"】"+"指标内容绑定成功，请在刷新或者关闭页面前点击下方的保存");
                },
                error: function(result){
                    layer.msg("操作失败",{offset: '222px'});
                }
            });
    }
  }  
  
  //获取标记内容并且定位
  function searchMark(e,target,kind,id){
    var obj = document.getElementById("TANGER_OCX");
    //判断标记是否存在
    if(obj.ActiveDocument.Bookmarks.Exists(target)){
      //定位到书签内容
      obj.ActiveDocument.Bookmarks.Item(target).Select();
    }else{
      obj.ShowTipMessage("提示","定位失败，该指标未被绑定或绑定未保存,请重新绑定！",true);
      var html = "<div class='bdzb light_icon'><a href='javascript:void(0)' onclick='mark(this,"+'"'+target+'"'+","+'"'+kind+'",'+'"'+id+'"'+");'>绑定指标</a></div>";
      $(e).parent().prev().remove();
      $(e).parent().after(html);
      $(e).parent().remove();
    }
  }
  
  //删除标记
  function delMark(e,target, kind, id){
    var projectId = $("#projectId").val();
    var obj = document.getElementById("TANGER_OCX");
    //判断标记是否存在
    if(obj.ActiveDocument.Bookmarks.Exists(target)){
      var idType="";
      if(kind == 'first'){
        //初审项id
        idType = "firstAuditId";
      }
      if(kind == 'second'){
        //初审项id
        idType = "smId";
      }
      $.ajax({  
               type: "POST",  
               url: "${pageContext.request.contextPath}/supplierProject/deletedBindingIndex.html?projectId="+projectId+"&"+idType+"="+id,  
               dataType: 'json',  
               success:function(result){
                   obj.ActiveDocument.BookMarks.Item(target).Delete();
          obj.ShowTipMessage("提示","删除绑定成功！",false);
          var html = "<div class='bdzb light_icon'><a href='javascript:void(0)' onclick='mark(this,"+'"'+target+'"'+","+'"'+kind+'",'+'"'+id+'"'+");'>绑定指标</a></div>";
          $(e).parent().next().remove();
          $(e).parent().after(html);
          $(e).parent().remove();
                },
                error: function(result){
                    layer.msg("操作失败",{offset: '222px'});
                }
            });
      
    }else{
      obj.ShowTipMessage("提示","删除失败，该指标未被绑定或绑定未保存,请重新绑定！",true);
      var html = "<div class='bdzb light_icon'><a href='javascript:void(0)' onclick='mark(this,"+'"'+target+'"'+","+'"'+kind+'",'+'"'+id+'"'+");'>绑定指标</a></div>";
      $(e).parent().next().remove();
      $(e).parent().after(html);
      $(e).parent().remove();
    }
  }
  
  function bd(projectId){
    var obj = document.getElementById("TANGER_OCX");
    $.ajax({  
               type: "POST",  
               url: "${pageContext.request.contextPath}/supplierProject/isExistFile.html?projectId="+projectId,  
               dataType: 'json',  
               success:function(result){
                   if(result.isExist == 0){
                     obj.ShowTipMessage("提示","请先保存标书到服务器",true);
                     return;
                   }
                   if(result.isExist == 1){
                     window.location.href = "${pageContext.request.contextPath}/supplierProject/toBindingIndex.html?projectId="+projectId;
                   }
                },
                error: function(result){
                    layer.msg("操作失败",{offset: '222px'});
                }
            });
  }
  
  function tishi(v){
    var obj = document.getElementById("TANGER_OCX");
    obj.ShowTipMessage("提示",v,true);
  }
  
  //保存数据
  function savefirst(){
    var projectId = $("#projectId").val();
    var pagNum = $("#pagNum").val();//分包数量
    var subData1 = "";//初审项值
    var subData2 = "";//详细评审项值
      var count = 0;
      var msg = "";//提示信息
    for (var i = 1; i < pagNum+1; i++) {
      var num = 0;
      //取初审项的值
      var indexTr0 = 0;
      $("#tbody_1_"+i).find("tr").each(function(){
        var tdArr = $(this).children();
        var pfaId = tdArr.eq(0).find("input").val();//初审项与包关联id
        var v = tdArr.eq(3).find("select").val();
          var pId = tdArr.eq(5).find("input").val();//包id
        if(indexTr0 == 0 && i == 1){
            subData1 += "[";
          }
          if(indexTr0 > 0 || i > 1){
            subData1 += ",[";
          }
          subData1 += projectId;
          subData1 += "_";
          subData1 += pfaId;
          subData1 += "_";
          subData1 += pId;
          subData1 += "_";
          subData1 += v;
          subData1 += "]";
          indexTr0 += 1;
      });
      //取详细评审项的值
      var indexTr = 0;
        $("#tbody_2_"+i).find("tr").each(function(){
          var tdArr = $(this).children();
          var v;//填写的值
          var smId = tdArr.eq(1).find("input").val();//评审项与模型关联id
          var mType = tdArr.eq(0).find("input").val();//模型类型
          var pName = tdArr.eq(5).find("input").val();//包名
          var pId = tdArr.eq(6).find("input").val();//包id
          var smName = tdArr.eq(2).find("input").val();//评审项名称
          if(mType == 0){
            v = tdArr.eq(4).find("select").val();
          }else{
            v = tdArr.eq(4).find("input").val();
            if(v == undefined || v == '' || v == null){
              if(num == 0){
                msg += "【";
                msg += pName;
                msg += "】中【";
                msg += smName;
                msg +="】";
              }else{
                msg += "、【";
                msg +=smName;
                msg +="】";
              }
              v = "null";//未填写项为空值
              count +=1;
              num +=1;
            }
          }
          if(indexTr == 0 && i == 1){
            subData2 += "[";
          }
          if(indexTr > 0 || i > 1){
            subData2 += ",[";
          }
          subData2 += projectId;
          subData2 += "_";
          subData2 += smId;
          subData2 += "_";
          subData2 += pId;
          subData2 += "_";
          subData2 += v;
          subData2 += "]";
          indexTr += 1;
        });
      }
      if(count > 0){
        layer.confirm(msg+'项未填写指标值，该项得分视为0分！', {title:'提示',shade:0.01}, function(index){
        layer.close(index);
        $.ajax({  
                 type: "POST",  
                 url: "${pageContext.request.contextPath}/supplierProject/saveIndex.html?data1="+subData1+"&data2="+subData2,  
                 dataType: 'json',  
                 success:function(result){
                      layer.msg(result.msg,{offset: '300px'});
                      $("#saveFirstPage").addClass("dnone");
                      $("#nextStep").removeClass("dnone");
                  },
                  error: function(result){
                      layer.msg("操作失败",{offset: '300px'});
                  }
              });
      });
      }else{
        $.ajax({  
                 type: "POST",  
                 url: "${pageContext.request.contextPath}/supplierProject/saveIndex.html?data1="+subData1+"&data2="+subData2,  
                 dataType: 'json',  
                 success:function(result){
                      layer.msg(result.msg,{offset: '300px'});
                       $("#saveFirstPage").addClass("dnone");
                      $("#nextStep").removeClass("dnone");
                  },
                  error: function(result){
                      layer.msg("操作失败",{offset: '300px'});
                  }
              });
      }
  }
  
  //绑定指标页面
  function nextStep(){
    $("#secodPage").removeClass("dnone");
    $("#firstPage").addClass("dnone");
    var fileId = $("#fileId").val();
    OpenFile(fileId);
  }
  //填写指标页面
  function prevStep(){
    $("#firstPage").removeClass("dnone");
    $("#secodPage").addClass("dnone");
  }
  
  //保存绑定文件 kind=0时代表暂存，下次进来还是该环节
  function saveSecond(kind){
    var projectId = $("#projectId").val();
    var supplierName = $("#supplierName").val();
    var obj = document.getElementById("TANGER_OCX");
    if(kind == '0'){
      if(confirm("您确定暂存下次继续绑定吗？")){
        //参数说明
        //1.url  2.后台接收的文件的变量  3.可选参数(为空)    4.文件名    5.form表单的ID
        var s = obj.SaveToURL("${pageContext.request.contextPath}/supplierProject/saveBidFile.html?projectId="+projectId+"&kind="+kind, "ntko", "", supplierName+"_投标文件.doc", "MyFile");
        obj.ShowTipMessage("提示","投标文件已暂存");
      }
    }
    if(kind == '1'){
      if(confirm("保存后将不能修改！")){
        //参数说明
        //1.url  2.后台接收的文件的变量  3.可选参数(为空)    4.文件名    5.form表单的ID
        var s = obj.SaveToURL("${pageContext.request.contextPath}/supplierProject/saveBidFile.html?projectId="+projectId+"&kind="+kind, "ntko", "", supplierName+"_投标文件.doc", "MyFile");
        obj.ShowTipMessage("提示","投标文件已上传至服务器");
        //跳转下一个环节
        window.location.href = "${pageContext.request.contextPath}/mulQuo/list.html?projectId="+projectId;
      }
    }
  }
</script>

</head>

<body >
  <div class="margin-top-10 breadcrumbs ">
      <div class="container">
       <ul class="breadcrumb margin-left-0">
       <li><a href="javascript:void(0);"> 首页</a></li><li><a href="javascript:void(0);">我的项目</a></li><li><a href="javascript:void(0);">标书管理</a></li>
       </ul>
    <div class="clear"></div>
    </div>
    </div>
    <div class="container clear mt20">
      <input type="hidden" id="projectId" value="${project.id}">
      <input type="hidden" id="pagNum" value="${packages.size()}">
       <div class="list-unstyled padding-10 breadcrumbs-v3">
           <span>
          <a href="${pageContext.request.contextPath}/mulQuo/openBid.html?projectId=${project.id}" class="img-v1">开标一览表</a>
          <span class="green_link">→</span>
      </span>
      <span>
          <a href="${pageContext.request.contextPath}/mulQuo/priceBuild.html?projectId=${project.id}" class="img-v1">价格构成表</a>
          <span class="green_link">→</span>
      </span>
      <span>
          <a href="${pageContext.request.contextPath}/mulQuo/priceView.html?projectId=${project.id}" class="img-v1">明细表</a><!--货物材料、部件、工具价格明细表  -->
          <span class="green_link">→</span>
      </span>
        <span>
          <c:if test="${std.bidFinish == 0}">
          <a href="${pageContext.request.contextPath}/supplierProject/bidDocument.html?projectId=${project.id}" class="img-v2 orange_link">编制标书</a>
          <span class="green_link">→</span>
          </c:if>
          <c:if test="${std.bidFinish != 0}">
          <a href="${pageContext.request.contextPath}/supplierProject/bidDocument.html?projectId=${project.id}" class="img-v1">编制标书</a>
          <span class="">→</span>
          </c:if>
      </span>
      <span>
        <c:if test="${std.bidFinish == 1}">
          <a href="${pageContext.request.contextPath}/supplierProject/toBindingIndex.html?projectId=${project.id}" class="img-v2 orange_link">绑定指标</a>
          <span class="green_link">→</span>
        </c:if>
        <c:if test="${std.bidFinish == 2 || std.bidFinish == 3 || std.bidFinish == 4}">
          <a href="${pageContext.request.contextPath}/supplierProject/toBindingIndex.html?projectId=${project.id}" class="img-v1">绑定指标</a>
          <span class="">→</span>
        </c:if>
        <c:if test="${std.bidFinish == 0}">
          <a href="javascript:void(0);" onclick="tishi('请先编制保存标书到服务器');" class="img-v3">绑定指标</a>
          <span class="">→</span>
        </c:if>
      </span>
      <span>
        <c:if test="${std.bidFinish == 2 }">
          <a href="${pageContext.request.contextPath}/mulQuo/list.html?projectId=${project.id}"  class="img-v2 orange_link">填写报价</a>
          <span class="green_link">→</span>
        </c:if>
        <c:if test="${std.bidFinish == 0}">
          <a href="javascript:void(0);" onclick="tishi('请先编制保存标书到服务器');" class="img-v3">填写报价</a>
          <span class="">→</span>
        </c:if>
        <c:if test="${std.bidFinish == 1}">
          <a href="javascript:void(0);" onclick="tishi('请先绑定指标');" class="img-v3">填写报价</a>
          <span class="">→</span>
        </c:if>
        <c:if test="${std.bidFinish == 3 || std.bidFinish == 4}">
          <a href="${pageContext.request.contextPath}/mulQuo/list.html?projectId=${project.id}" class="img-v1">填写报价</a>
          <span class="">→</span>
        </c:if>
      </span>
        <span>
          <c:if test="${std.bidFinish == 3 }">
            <a href="${pageContext.request.contextPath}/supplierProject/result.html?projectId=${project.id}" class="img-v2  orange_link">完成</a>
          </c:if>
          <c:if test="${std.bidFinish == 0}">
          <a href="javascript:void(0);" onclick="tishi('请先编制保存标书到服务器');" class="img-v3">完成</a>
        </c:if>
        <c:if test="${std.bidFinish == 1}">
          <a href="javascript:void(0);" onclick="tishi('请先绑定指标');" class="img-v3">完成</a>
        </c:if>
        <c:if test="${std.bidFinish == 2}">
          <a href="javascript:void(0);" onclick="tishi('请先填写报价');" class="img-v3">完成</a>
        </c:if>
        <c:if test="${std.bidFinish == 4}">
          <a href="${pageContext.request.contextPath}/supplierProject/result.html?projectId=${project.id}"  class="img-v1">完成</a>
        </c:if>
      </span>
       </div>
    </div>
    <!-- 指标填写 -->
    <div class="container container_box" id="firstPage">
      <div class="row magazine-page">
        <div class="col-md-12 tab-v2 job-content">
        <div class="padding-top-10">
        <ul class="nav nav-tabs bgdd">
          <c:forEach items="${packages }"  var="obj" varStatus="vs" >
         <c:if test="${vs.index==0 }">
           <li class="active">
             <a aria-expanded="true" href="#tab-${vs.index+1 }" data-toggle="tab" title="${obj.name }" >
               <c:choose>
                 <c:when test="${fn:length(obj.name)>3}">${fn:substring(obj.name, 0, 3)}...</c:when>
                 <c:otherwise>${obj.name}</c:otherwise>
               </c:choose>
             </a>
           </li>
         </c:if>
         <c:if test="${vs.index>0 }">
           <li class="">
             <a aria-expanded="true" href="#tab-${vs.index+1 }" data-toggle="tab" title="${obj.name }" >
               <c:choose>
                 <c:when test="${fn:length(obj.name)>3}">${fn:substring(obj.name, 0, 3)}...</c:when>
                 <c:otherwise>${obj.name}</c:otherwise>
               </c:choose>
             </a>
           </li>
          </c:if>
      </c:forEach>
        </ul>
       <div class="tab-content padding-top-20">
       <c:forEach items="${packages }"  var="pas" varStatus="vs">
           <c:if test="${vs.index == 0 }">
            <div class="tab-pane fade active in" id="tab-${vs.index+1 }">
           </c:if>
           <c:if test="${vs.index > 0 }">
            <div class="tab-pane fade" id="tab-${vs.index+1 }">
           </c:if>
          <h2 class="count_flow"><i>1</i>初审项</h2>
          <ul class="ul_list">
          <table class="table table-bordered table-condensed mt5">
                <thead>
                  <tr>
                    <th class="info w50">序号</th>
                    <th class="info">名称</th>
                    <th class="info">类型</th>
                    <th class="info">是否满足</th>
                    <th class="dnone"></th>
                    <th class="dnone"></th>
                  </tr>
                </thead>
                <tbody id="tbody_1_${vs.index+1}">
                <c:set var="count" value="0"> </c:set>
             <c:forEach items="${packageFirstAudits }"  var="pfa" varStatus="vf" >
               <c:if test="${pfa.packageId == pas.id }">
                 <c:set var="count" value="${count+1}"></c:set>
                      <tr >
                        <td class="tc w50">${count}<input type="hidden" value="${pfa.firstAuditId }" /></td>
                        <td class="tc">${pfa.firstAuditName}</td>
                        <td class="tc">${pfa.firstAuditKind}</td>
                        <td class="tc">
                          <span class="red mr10">*</span>
                          <select name="" class="w150">
                            <option value="0" <c:if test="${pfa.is_pass == 0}">selected</c:if>>不满足</option>
                            <option value="1" <c:if test="${pfa.is_pass == 1}">selected</c:if>>满足</option>
                          </select>
                        </td>
                        <td class="dnone"><input type="hidden" value="${pas.name }" /></td>
                          <td class="dnone"><input type="hidden" value="${pas.id }" /></td>
                      </tr>
               </c:if>
             </c:forEach>
             </tbody>
              </table>
            </ul>
            <h2 class="count_flow"><i>2</i>评分细则</h2>
          <ul class="ul_list">
            <table class="table table-bordered table-condensed mt5">
                <thead>
                  <tr>
                    <th class="dnone"></th>
                    <th class="info w50">序号</th>
                    <th class="info">名称</th>
                    <th class="info">类型</th>
                    <th class="info">指标值</th>
                    <th class="dnone"></th>
                    <th class="dnone"></th>
                  </tr>
                </thead>
                <tbody id="tbody_2_${vs.index+1}">
                <c:set var="count2" value="0"> </c:set>
             <c:forEach items="${scoreModels }"  var="sm" varStatus="vss" >
               <c:if test="${sm.packageId == pas.id }">
               <c:set var="count2" value="${count2+1}"></c:set>
                    <tr >
                      <td class="dnone"><input type="hidden" value="${sm.typeName }" /></td>
                      <td class="tc w50">${count2}<input type="hidden" value="${sm.id }" /></td>
                      <td class="tc">${sm.markTerm.name}<input type="hidden" value="${sm.markTerm.name}" /></td>
                      <td class="tc">
                        <c:if test="${sm.markTerm.typeName==0}">商务</c:if>
                        <c:if test="${sm.markTerm.typeName==1}">技术</c:if>
                      </td>
                      <td class="tc">
                        <c:choose>
                        <c:when test="${sm.typeName == '0'}">
                          <select name="" class="w150">
                            <option value="0" <c:if test="${sm.value == 0}">selected</c:if>>不满足</option>
                            <option value="1" <c:if test="${sm.value == 1}">selected</c:if>>满足</option>
                          </select>
                        </c:when>
                        <c:otherwise>
                          <input maxlength="30" value="${sm.value}" onkeyup="this.value=(this.value.match(/\d+(\.\d{0,2})?/)||[''])[0]"/>
                        </c:otherwise>
                        </c:choose>
                      </td>
                      <td class="dnone"><input type="hidden" value="${pas.name }" /></td>
                      <td class="dnone"><input type="hidden" value="${pas.id }" /></td>
                    </tr>
                    </c:if>
             </c:forEach>
             </tbody>
              </table>
          </ul>
        </div>
        </c:forEach>
        </div>
      <div class="mt40 tc mb50">
       <c:if test="${'no' eq saveFirst}">
       <button class="btn padding-left-20 padding-right-20 btn_back margin-5" id="saveFirstPage" onclick="savefirst();">保存</button>
        <button class="btn padding-left-20 padding-right-20 btn_back margin-5 dnone" id="nextStep" onclick="nextStep();">下一步</button>
       </c:if>
       <c:if test="${'ok' eq saveFirst}">
       <button class="btn padding-left-20 padding-right-20 btn_back margin-5" id="nextStep" onclick="nextStep();">下一步</button>
       </c:if>
     <!-- <button class="btn padding-left-20 padding-right-20 btn_back margin-5">返回</button> -->
    </div>
    </div>
    </div>
    </div>
    </div>
    
    <!-- 指标绑定 -->
    <div class="container content container_box dnone" id="secodPage">
        <div class="padding-top-10">
       <div class="row">
          <!-- Begin Content -->
          <div class="col-md-2 col-sm-3 col-xs-12">
             <div class="tag-box tag-box-v3">  
         <div class="light_main">
          <div class="light_list">
         初审项      
        </div>
          <ul class="light_box"> 
            <c:if test="${std.bidFinish == 2 || std.bidFinish == 3 || std.bidFinish == 4}">
              <c:forEach items="${firstAudits}" var="fa">
                <li>
                  <span class="light_desc">
                    <c:if test="${fa.name.length()>4 }">
                      <a href="javascript:void(0);" title="${fa.name}">${fn:substring(fa.name,0,4)}...</a>
                    </c:if>
                    <c:if test="${fa.name.length()<=4 }">
                      <a href="javascript:void(0);" title="${fa.name}">${fa.name}</a>
                    </c:if>
                  </span>
                  <div class='dinwei light_icon'>
                    <a href='javascript:void(0)' onclick="searchMark(this,'${fa.name}','first','${fa.id}');">定位</a>
                  </div>
              </li>
              </c:forEach>
            </c:if>
            <c:if test="${std.bidFinish == 1}">
              <c:forEach items="${firstAudits}" var="fa">
                <li>
                  <span class="light_desc">
                    <c:if test="${fa.name.length()>4 }">
                      <a href="javascript:void(0);" title="${fa.name}">${fn:substring(fa.name,0,4)}...</a>
                    </c:if>
                    <c:if test="${fa.name.length()<=4 }">
                      <a href="javascript:void(0);" title="${fa.name}">${fa.name}</a>
                    </c:if>
                  </span>
                  <c:if test="${fa.page != null}">
                    <div class='shanchu light_icon'>
                      <a href='javascript:void(0)' onclick="delMark(this,'${fa.name}','first','${fa.id}');">删除</a>
                    </div>
                    <div class='dinwei light_icon'>
                      <a href='javascript:void(0)' onclick="searchMark(this,'${fa.name}','first','${fa.id}');">定位</a>
                    </div>
                  </c:if>
                  <c:if test="${fa.page == null}">
                    <div class='bdzb light_icon'>
                      <a href='javascript:void(0)' onclick="mark(this,'${fa.name}','first','${fa.id}');">绑定指标</a>
                    </div>
                  </c:if>
              </li>
              </c:forEach>
              </c:if>
        </ul>
           </div>
         <div class="light_main">
          <div class="light_list">
         评分细则
        </div>
          <ul class="light_box">
            <c:if test="${std.bidFinish == 2 || std.bidFinish == 3 || std.bidFinish == 4}">
              <c:forEach items="${scoreModels}" var="sm">
                <li>
                  <span class="light_desc">
                    <c:if test="${sm.markTerm.name.length()>4 }">
                      <a href="javascript:void(0);" title="${sm.markTerm.name}">${fn:substring(sm.markTerm.name,0,4)}...</a>
                    </c:if>
                    <c:if test="${sm.markTerm.name.length()<=4 }">
                      <a href="javascript:void(0);" title="${sm.markTerm.name}">${sm.markTerm.name}</a>
                    </c:if>
                  </span>
                  <div class='dinwei light_icon'>
                    <a href='javascript:void(0)' onclick="searchMark(this,'${sm.markTerm.name}','second','${sm.id}');">定位</a>
                  </div>
              </li>
              </c:forEach>
            </c:if>
            <c:if test="${std.bidFinish == 1}">
              <c:forEach items="${scoreModels}" var="sm">
                <li>
                  <span class="light_desc">
                    <c:if test="${sm.markTerm.name.length()>4 }">
                      <a href="javascript:void(0);" title="${sm.markTerm.name}">${fn:substring(sm.markTerm.name,0,4)}...</a>
                    </c:if>
                    <c:if test="${sm.markTerm.name.length()<=4 }">
                      <a href="javascript:void(0);" title="${sm.markTerm.name}">${sm.markTerm.name}</a>
                    </c:if>
                  </span>
                  <c:if test="${sm.page != null}">
                    <div class='shanchu light_icon'>
                      <a href='javascript:void(0)' onclick="delMark(this,'${sm.markTerm.name}','second','${sm.id}');">删除</a>
                    </div>
                    <div class='dinwei light_icon'>
                      <a href='javascript:void(0)' onclick="searchMark(this,'${sm.markTerm.name}','second','${sm.id}');">定位</a>
                    </div>
                  </c:if>
                  <c:if test="${sm.page == null}">
                    <div class='bdzb light_icon'>
                      <a href='javascript:void(0)' onclick="mark(this,'${sm.markTerm.name}','second','${sm.id}');">绑定指标</a>
                    </div>
                  </c:if>
              </li>
              </c:forEach>
              </c:if> 
        </ul>
           </div>
          </div>   
      </div>
      <div class="tag-box tag-box-v4 col-md-10 col-sm-9 col-xs-12" id="show_content_div">
      <form id="MyFile" method="post"  enctype="multipart/form-data">
        <c:if test="${std.bidFinish == 1}">
           <!-- 按钮 -->
              <div class="mt10 mb10">
                 <!-- <input type="button" class="btn btn-windows cancel" onclick="delMark()" value="删除标记"></input>
                 <input type="button" class="btn btn-windows cancel" onclick="searchMark()" value="查看标记"></input>
                 <input type="button" class="btn btn-windows cancel" onclick="mark()" value="标记"></input> -->
                 <!-- <input type="button" class="btn btn-windows cancel" onclick="closeFile()" value="关闭当前文档"></input> -->
                 <!-- <input type="button" class="btn btn-windows " onclick="queryVersion()" value="版本查询"></input> -->
                 <!-- <input type="button" class="btn btn-windows save" onclick="saveFile();" value="保存绑定操作"></input> -->
            </div>
        </c:if>
        <input type="hidden" id="status" value="${status }">
         <input type="hidden" id="fileId" value="${fileId }">
         <input type="hidden" id="supplierName" value="${supplier.supplierName }">
        <script type="text/javascript" src="${pageContext.request.contextPath}/public/ntko/ntkoofficecontrol.js"></script>
      </form>
      </div>    
     </div>
     <div class="mt20 tc ww100 tc">
     <button class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="prevStep();">上一步</button>
     <c:if test="${std.bidFinish == 1}">
       <button class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="saveSecond('0');">暂存</button>
       <button class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="saveSecond('1');">保存</button>
     </c:if>
     <!-- <button class="btn padding-left-20 padding-right-20 btn_back margin-5">返回</button> -->
    </div>
  </div>
</body>
</html>
