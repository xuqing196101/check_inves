<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
  <script type="text/javascript">
    //新增一个评审项
    function addItem(kindId){
    	$("#itemKind").val(kindId);
    	layer.open({
            type: 1,
            title: '添加评审项信息',
            area: ['500px', '300px'],
            closeBtn: 1,
            shade:0.01, //遮罩透明度
            moveType: 1, //拖拽风格，0是默认，1是传统拖动
            shift: 1, //0-6的动画形式，-1不开启
            offset: '110px',
            shadeClose: false,
            content: $("#openDiv"),
          });
    }
    
    //修改评审项
    function editItem(id){
        layer.open({
            type: 2,
            title: '修改评审项信息',
            area: ['500px', '300px'],
            closeBtn: 1,
            shade:0.01, //遮罩透明度
            moveType: 1, //拖拽风格，0是默认，1是传统拖动
            shift: 1, //0-6的动画形式，-1不开启
            offset: '110px',
            shadeClose: false,
            content: '${pageContext.request.contextPath}/auditTemplat/editItem.html?id='+id
          });
    }
    
    //删除评审项 
    function delItem(id){
    	layer.confirm('您确定要删除吗?', {title:'提示',offset: '222px',shade:0.01}, function(index){
	    	$.ajax({   
	            type: "POST",  
	            url: "${pageContext.request.contextPath}/auditTemplat/delItem.html?id="+id,        
	            dataType:'json',
	            success:function(result){
	                if(!result.success){
	                    layer.msg(result.msg,{offset: ['150px']});
	                }else{
	                	var templatKind = $("#templetKind").val();
	                    var templatId = $("#templetId").val();
	                    window.location.href = '${pageContext.request.contextPath}/auditTemplat/editTemplat.html?templetKind='+templatKind+'&templetId='+templatId;
	                    layer.msg(result.msg,{offset: ['150px']});
	                    layer.close(index);
	                }
	            },
	            error: function(result){
	                layer.msg("删除失败",{offset: ['150px']});
	            }
	       });       	
        });
    }
    
    //关闭弹窗
    function cancel(){
        layer.closeAll();
    }
    
    //返回模板列表
    function goBack(){
    	window.location.href = '${pageContext.request.contextPath}/auditTemplat/list.html';
    }
    
    //保存评审项
    function saveItem(){
    	$.ajax({   
            type: "POST",  
            url: "${pageContext.request.contextPath}/auditTemplat/saveItem.html",        
            data : $('#form2').serializeArray(),
            dataType:'json',
            success:function(result){
                if(!result.success){
                    layer.msg(result.msg,{offset: ['150px']});
                }else{
                	/* 不刷新加载
                	var itemKind = $("#itemKind").val();
                	var trobj = $("#"+itemKind);
                    var tdarr = trobj.children();
                    var rowspans = parseInt(tdarr.eq(0).find("input").val())+parseInt(1);
                    tdarr.eq(0).attr("rowspan",rowspans);//设置tr的rowspan
                    tdarr.eq(0).find("input").val(rowspans);
                    var data = $('#form2').serializeArray();
                    var tempName = data[2].value;
                    var tempContent = data[4].value;
                    var trhtml = "<tr><td class='w260'>"+tempName;
                    	trhtml += "<img src='${pageContext.request.contextPath}/public/backend/images/light_icon.png'>";
                    	trhtml += "<img src='${pageContext.request.contextPath}/public/backend/images/sc.png'>";
                    	trhtml += "</td>";
                    	trhtml += "<td>"+tempContent+"</td></tr>";
                    	trobj.after(trhtml); */
                    var templetKind = $("#templetKind").val();
                    var templetId = $("#templetId").val();
                    window.location.href = '${pageContext.request.contextPath}/auditTemplat/editTemplat.html?templetKind='+templetKind+'&templetId='+templetId;
                    layer.closeAll();
                    layer.msg(result.msg,{offset: ['150px']});
                }
            },
            error: function(result){
                layer.msg("添加失败",{offset: ['150px']});
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
                <li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')" target="_blank">首页</a></li>
                <li><a href="javascript:void(0)">支撑系统</a></li>
                <li><a href="javascript:void(0)">后台管理</a></li>
                <li class="active"><a href="javascript:jumppage('${pageContext.request.contextPath}/auditTemplat/list.html')">评审模版管理</a></li>
            </ul>
            <div class="clear"></div>
        </div>
    </div>
    <div class="container">
        <div class="headline-v2">
            <h2>模板编辑</h2>
        </div>
       <div class="content table_box">
           <input type="hidden" id="templetKind" value="${templetKind}">
           <table class="table table-bordered table-condensed table-hover">
               <thead>
                  <tr>
                     <th class="info" colspan="2">评审名称</th>
                     <th class="info">评审内容</th>
                  </tr>
               </thead>
               <c:forEach items="${dds}" var="d" varStatus="vs">
                  <!-- 如果没有评审项 ，显示空td-->
                  <c:if test="${d.code == 'COMPLIANCE' && items1.size() == 0}">
                    <tr id="${d.id}">
                       <td rowspan="2" class="w150">
                           <input type="hidden" value="2">
                           <span class="fl">${d.name}</span>
                           <a class="addItem item_size" onclick="addItem('${d.id}');" ></a>
                       </td>
                    </tr>
                    <tr>
                        
                    </tr>
                  </c:if>
                  <c:if test="${d.code == 'QUALIFICATION' && items2.size() == 0}">
                    <tr id="${d.id}">
                       <td rowspan="2" class="w150">
                           <input type="hidden" value="2">
                           <span class="fl">${d.name}</span>
                           <a class="addItem item_size" onclick="addItem('${d.id}');" ></a>
                       </td>
                    </tr>
                    <tr>
                        
                    </tr>
                  </c:if>
                  <!-- 如果有评审项 ，加载符合性评审项-->
                  <c:if test="${d.code == 'COMPLIANCE' && items1.size() > 0}">
                    <tr id="${d.id}">
                       <td rowspan="${items1.size() + 1}" class="w150">
                           <input type="hidden" value="${items1.size() + 1}">
                           <span class="fl">${d.name}</span>
                           <a class="addItem item_size" onclick="addItem('${d.id}');" ></a>
                       </td>
                    </tr>
                    <c:forEach items="${items1}" var="i" varStatus="iv">
                    <tr>
                        <td class="w400">
                            <c:if test="${i.kind == d.id}">
                                 <span class="fl">${i.name}</span>
	                             <a href="javascript:void(0);" title="编辑" onclick="editItem('${i.id}');" class="item_size editItem"></a>
	                             <a href="javascript:void(0);" title="删除" onclick="delItem('${i.id}')" class="item_size deleteItem"></a>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${i.kind == d.id}">
	                           ${i.content}
	                        </c:if>
                        </td>
                    </tr>  
                    </c:forEach>
                   </c:if>
                   <!-- 如果有评审项 ，加载资格性评审项-->
                   <c:if test="${d.code == 'QUALIFICATION' && items2.size() > 0}">
                    <tr id="${d.id}">
                       <td rowspan="${items2.size() + 1}" class="w150">
                           <input type="hidden" value="${items2.size() + 1}">
                           <span class="fl">${d.name}</span>
                           <a class="addItem item_size" onclick="addItem('${d.id}');" ></a>
                       </td>
                    </tr>
                    <c:forEach items="${items2}" var="i" varStatus="iv">
                    <tr>
                        <td class="w400">
                            <c:if test="${i.kind == d.id}">
                                 <span class="fl">${i.name}</span>
                                 <a href="javascript:void(0);" title="编辑" onclick="editItem('${i.id}');" class="item_size editItem"></a>
                                 <a href="javascript:void(0);" title="删除" onclick="delItem('${i.id}')" class="item_size deleteItem"></a>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${i.kind == d.id}">
                               ${i.content}
                            </c:if>
                        </td>
                    </tr>  
                    </c:forEach>
                   </c:if>
                </c:forEach>
           </table>
       </div>
       <div class="mt40 tc mb50">
           <!-- <button class="btn btn-windows save" onclick="saveItem();">保存</button> -->
           <button class="btn btn-windows back" onclick="goBack();">返回</button>
       </div>
    </div>
    <div id="openDiv" class="dnone layui-layer-wrap" >
      <form id="form2" method="post" >
        <div class="drop_window">
              <input type="hidden" name="templatId" id="templetId" value="${templetId}">
              <input type="hidden" name="kind" id="itemKind" > 
              <ul class="list-unstyled">
                  <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6 pl15">
                    <label class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>评审名称</label>
	                <span class="col-md-12 col-sm-12 col-xs-12 p0 input_group input-append">
	                   <input name="name" id="itemName" maxlength="30" type="text">
	                </span>
                  </li>
                  <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
                    <label class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>序号</label>
	                <div class="col-md-12 col-sm-12 col-xs-12 p0 input_group input-append">
	                   <input  name="position" id="itemPosition" maxlength="10" onkeyup="value=value.replace(/[^\d]/g,'')" type="text">
	                </div>
                 </li>
                 <li class="col-md-12 col-sm-12 col-xs-12 mb20">
                   <label class="col-md-12 pl20 col-xs-12 padding-left-5"><div class="star_red">*</div>评审内容</label>
                   <span class="col-md-12 col-sm-12 col-xs-12 p0">
                    <textarea class="w100p h80" id="itemContent" name="content" maxlength="200" title="" placeholder=""></textarea>
                   </span>
                 </li>
              </ul>
              <div class="mt10 tc col-md-12 col-sm-12 col-xs-12 ">
                <input class="btn btn-windows save"  onclick="saveItem();" value="保存" type="button"> 
                <input class="btn btn-windows back"  onclick="cancel();" value="取消" type="button"> 
              </div>
            </div>
         </form>
      </div>
</body>
</html>