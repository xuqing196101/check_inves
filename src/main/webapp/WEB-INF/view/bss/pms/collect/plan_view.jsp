<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
  <head>
    <%@ include file="/WEB-INF/view/common.jsp" %>
    <%@ include file="/WEB-INF/view/common/webupload.jsp"%>
<script type="text/javascript">
  /** 全选全不选 */
  function selectAll(){
     var checklist = document.getElementsByName ("chkItem");
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
  
    function view(no){
      
      
      window.location.href="${pageContext.request.contextPath}/purchaser/queryByNo.html?planNo="+no;
    }
    
    function edit(){
      var id=[]; 
    $('input[name="chkItem"]:checked').each(function(){ 
      id.push($(this).val());
    }); 
    if(id.length==1){
      
      window.location.href="${pageContext.request.contextPath}/purchaser/queryByNo.html?planNo="+no;
    }else if(id.length>1){
      layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
    }else{
      layer.alert("请选择需要修改的版块",{offset: ['222px', '390px'], shade:0.01});
    }
    }
    
    function del(){
      var id =[]; 
    $('input[name="chkItem"]:checked').each(function(){ 
      id.push($(this).val()); 
    }); 
    if(id.length>0){
      layer.confirm('您确定要删除吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
        layer.close(index);
        window.location.href="${pageContext.request.contextPath}/park/delete.html?id="+id;
      });
    }else{
      layer.alert("请选择要删除的版块",{offset: ['222px', '390px'], shade:0.01});
    }
    }
    var index;
    function add(){
      
      index=layer.open({
        type: 1, //page层
        area: ['300px', '200px'],
        title: '',
        closeBtn: 1,
        shade:0.01, //遮罩透明度
        moveType: 1, //拖拽风格，0是默认，1是传统拖动
        shift: 1, //0-6的动画形式，-1不开启
        offset: ['80px', '600px'],
        content: $('#content'),
      });
      
   
    }
    
  //鼠标移动显示全部内容
  function out(content){
  if(content.length>10){
  layer.msg(content, {
      icon:6,
      shade:false,
      area: ['600px'],
      time : 1000    //默认消息框不关闭
    });//去掉msg图标
  }else{
    layer.closeAll();//关闭消息框
  }
}
  
  function closeLayer(){
    var val=$("input[name='goods']:checked").val();
    
    window.location.href="${pageContext.request.contextPath}/purchaser/add.html?type=" + val;
    layer.close(index);
  }
</script>
<%-- <script src="${pageContext.request.contextPath}/public/backend/js/lock_table_head.js" ></script> --%>


</head>

<body>
  <!--面包屑导航开始-->
  <div class="margin-top-10 breadcrumbs ">
    <div class="container" id="container">
      <ul class="breadcrumb margin-left-0">
        <li><a href="javascript:void(0);"> 首页</a></li>
        <li><a href="javascript:void(0);">保障作业系统</a></li>
        <li><a href="javascript:void(0);">采购计划管理</a></li>
        <!-- <li class="active"><a href="javascript:void(0);">采购需求管理重新</a></li> -->
      </ul>
      <div class="clear"></div>
    </div>
  </div>
  <div class="container">
    <div class="headline-v2 fl">
      <h2>计划明细</h2>
    </div>
    <div class="container clear margin-top-30"  id="content" >
      <form action="${pageContext.request.contextPath}/purchaser/update.html" method="post">
       <table id="table" class="table table-bordered table-condensed lockout">
          <thead>
            <tr>
              <th class="info seq">序号</th>
              <th class="info department">需求部门</th>
              <th class="info goodsname">物资类别<br>及名称</th>
              <th class="info stand">规格型号</th>
              <th class="info qualitstand">质量技术标准<br>（技术参数）</th>
              <th class="info item">计量</br>单位</th>
              <th class="info purchasecount">采购</br>数量</th>
              <th class="info price">单价<br>（元）</th>
              <th class="info budget">预算金额<br>（万元）</th>
              <th class="info deliverdate">交货<br>期限</th>
              <th class="info purchasetype">采购方式</th>
              <th class="info organization">采购机构</th>
              <th class="info purchasename">供应商名称</th>
              <th class="info freetax">是否申请<br>办理免税</th>
            <!--   <th class="info w150">物资用途<br>（仅进口）</th>
              <th class="info w150">使用单位<br>（仅进口）</th> -->
              <th class="info memo">备注</th>
              <th class="info extrafile">附件</th>
            </tr>
          </thead>

          <c:forEach items="${list }" var="obj" varStatus="vs">
            <tr>
              <td><div class="seq">${obj.seq }</div> 
              </td>
              <td><div class="department">
                <c:if test="${obj.purchaseCount==null }">
              ${obj.department}
              </c:if>
              </div></td >
              <%-- <td class="tl pl20">
                   <c:forEach items="${requires }" var="re" >
                    <c:if test="${obj.department==re.id }"> ${re.name }</c:if>
                  </c:forEach> 
          
              </td> --%>
              <%-- <td>${obj.goodsName }</td> --%>
              <td title="${obj.goodsName}">
               <div class="goodsname">
                  <c:if test="${fn:length (obj.goodsName) > 8}">${fn:substring(obj.goodsName,0,7)}...</c:if>
                  <c:if test="${fn:length(obj.goodsName) <= 8}">${obj.goodsName}</c:if>
               </div>
              </td >
              <%-- <td class="tc"> ${obj.stand }</td> --%>
              <td title="${obj.stand}">
                <div class="stand">
                  <c:if test="${fn:length (obj.stand) > 8}">${fn:substring(obj.stand,0,7)}...</c:if>
                  <c:if test="${fn:length(obj.stand) <= 8}">${obj.stand}</c:if>
                </div>
              </td >
              <%-- <td class="tc"> ${obj.qualitStand }</td> --%>
              <td title="${obj.qualitStand}">
                <div class="qualitstand">
                  <c:if test="${fn:length (obj.qualitStand) > 8}">${fn:substring(obj.qualitStand,0,7)}...</c:if>
                  <c:if test="${fn:length(obj.qualitStand) <= 8}">${obj.qualitStand}</c:if>
                </div>
              </td >
              <%-- <td class="tc"> ${obj.item }</td> --%>
              <td title="${obj.item}">
                <div class="item">
                 <c:if test="${fn:length (obj.item) > 8}">${fn:substring(obj.item,0,7)}...</c:if>
                 <c:if test="${fn:length(obj.item) <= 8}">${obj.item}</c:if>
                </div>
              </td >
              <td>
               <div class="purchasecount">
                 ${obj.purchaseCount }
               </div>
              </td>
              <td>
               <div class="price"> <fmt:formatNumber type="number"   pattern="#,##0.00" value="${obj.price }" /></div>
              </td>
              <td>
               <div class="budget"><fmt:formatNumber type="number"   pattern="#,##0.00" value="${obj.budget}" /></div>
              </td>
              <td>
               <div class="deliverdate">${obj.deliverDate }</div>
              </td>
              <td class="tc">
                <div class="purchasetype">
                <c:if test="${obj.purchaseCount!=null }">
                 <c:forEach items="${kind}" var="kind" >
                 <c:if test="${kind.id == obj.purchaseType}">${kind.name}</c:if>
                 </c:forEach>
                 </c:if>
                </div>
              </td>
              <td class="tl">
                <div class="organization">
                 <c:if test="${obj.purchaseCount!=null }">
                  <c:forEach items="${org}" var="og" >
                   <c:if test="${og.orgId == obj.organization}">${og.name}</c:if>
                  </c:forEach>
                  </c:if>
                </div>
              </td>  
              <td title="${obj.supplier}" class="tl">
               <div class="purchasename">
                <c:if test="${fn:length (obj.supplier) > 8}">${fn:substring(obj.supplier,0,7)}...</c:if>
                <c:if test="${fn:length(obj.supplier) <= 8}">${obj.supplier}</c:if>
               </div>
              </td >
              <%-- <td class="tc">${obj.isFreeTax }</td> --%>
              <td title="${obj.isFreeTax}" class="tc">
               <div class="freetax">
                <c:if test="${fn:length (obj.isFreeTax) > 8}">${fn:substring(obj.isFreeTax,0,7)}...</c:if>
                <c:if test="${fn:length(obj.isFreeTax) <= 8}">${obj.isFreeTax}</c:if>
               </div>
              </td >
              <%-- <td class="tc">${obj.goodsUse }</td> --%>
           <%--    <td title="${obj.goodsUse}" class="tl pl20">
              <c:if test="${fn:length (obj.goodsUse) > 8}">${fn:substring(obj.goodsUse,0,7)}...</c:if>
              <c:if test="${fn:length(obj.goodsUse) <= 8}">${obj.goodsUse}</c:if>
              </td > 
              <td class="tl pl20">${obj.useUnit }</td> --%>
              <td title="${obj.memo}">
                <div class="memo">
                 <c:if test="${fn:length (obj.memo) > 8}">${fn:substring(obj.memo,0,7)}...</c:if>
                 <c:if test="${fn:length(obj.memo) <= 8}">${obj.memo}</c:if>
                </div>
              </td > 
           
              <td class="p0">
					<%--   <div class="extrafile">
							  <u:upload id="pUp${vs.index}" businessId="${obj.id}" buttonName="上传文件" sysKey="2" typeId="${typeId}" auto="true" />
							  <u:show showId="pShow${vs.index}"  businessId="${obj.id}" sysKey="2" typeId="${typeId}" />
					  </div> --%>	
			  </td>
						 
            </tr>

          </c:forEach>
        </table>
        
        <div class="col-md-12 col-xs-12 col-sm-12 tc mt20">
         <input class="btn btn-windows back" value="返回" type="button"
                    onclick="location.href='javascript:history.go(-1);'">
      </form>
    </div>
  </div>
</body>
</html>
