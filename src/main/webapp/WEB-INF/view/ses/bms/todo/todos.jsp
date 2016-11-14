<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>待办事项</title>
<script type="text/javascript">
  $(function(){
	  laypage({
		    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
		    pages: "${listStationMessage.pages}", //总页数
		    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		    skip: true, //是否开启跳页
		    groups: "${listStationMessage.pages}">=3?3:"${listStationMessage.pages}", //连续显示分页数
		    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
// 		        var page = location.search.match(/page=(\d+)/);
		        return "${listStationMessage.pageNum}";
		    }(), 
		    jump: function(e, first){ //触发分页后的回调
		        if(!first){ //一定要加此判断，否则初始时会无限刷新
		        	$("#pages").val(e.curr);
		        	$("form:first").submit();
		        }
		    }
		});
  });
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
  	function view(url){
  	  $("#a").attr("href",url);
      var el=document.getElementById('a');
    el.click();//触发打开事件
  	}

    function del(){
    	var ids =[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
		}); 
		if(ids.length>0){
			layer.confirm('您确定要删除吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
				layer.close(index);
				window.location.href="${pageContext.request.contextPath}/StationMessage/deleteSoftSMIsDelete.do?ids="+ids;
			});
		}else{
			layer.alert("请选择要删除的用户",{offset: ['222px', '390px'], shade:0.01});
		}
    }
 
  </script>
<body>

                                <!-- 查看项目-->
                                <div id="con_one_2" class="dnonev">
                                    <div id="Accordion2" class="Accordion" tabindex="0">
                                        <c:forEach items="${listTodos }" var="agentslist"
                                            varStatus="s">
                                            <div class="AccordionPanel">
                                                <div class="AccordionPanelTab">
                                                    <c:if test="${agentslist[0].undoType==1}">
                                                                                                                                                                  供应商待办
                                                    </c:if>
                                                    <c:if test="${agentslist[0].undoType==2}">
                                                                                                                                                               专家待办
                                                    </c:if>
                                                    (${agentslist.size()})

                                                </div>
                                                <div class="" style="width: 100%;height:300px; overflow:auto; ">
                                                 <a id="a" href="#" target="_parent"></a>
                                                    <table class=" table table-striped table-bordered table-hover" >
                                                        <thead>
                                                            <tr>
                                                                <th class="info">序号</th>
                                                                <th class="info">标题</th>
                                                                <th class="info">发送人</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <c:forEach items="${agentslist }" var="agents"
                                                                varStatus="s">
                                                                <tr class="cursor"
                                                                    onclick="view('${pageContext.request.contextPath}/${ agents.url}');"  >
                                                                    <td class="tc">${s.index+1}</td>
                                                                    <td class="tc">${agents.name}</td>
                                                                    <td class="tc">${agents.senderName}</td>
                                                                </tr>
                                                            </c:forEach>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                            <script type="text/javascript">
                                var Accordion2 = new Spry.Widget.Accordion(
                                        "Accordion2");
                            </script>
                 
</body>
</html>
