<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<!DOCTYPE HTML>
<html>
  <head>
     <title>待办事项</title>
     <script src="${pageContext.request.contextPath}/public/backend/js/jquery.min.js"></script>
<link href="${pageContext.request.contextPath}/public/backend/images/favicon.ico"  rel="shortcut icon" type="image/x-icon" />
<link href="${pageContext.request.contextPath}/public/backend/css/bootstrap.min.css" media="screen" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/public/backend/css/common.css" media="screen" rel="stylesheet" type="text/css">  
<link href="${pageContext.request.contextPath}/public/backend/css/unify.css" media="screen" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/public/backend/css/global.css" media="screen" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/public/backend/css/btn.css" media="screen" rel="stylesheet" type="text/css"> 
<link href="${pageContext.request.contextPath}/public/accordion/SpryAccordion.css" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/public/accordion/SpryAccordion.js"></script>
<script src="${pageContext.request.contextPath}/public/webuploadFT/layui/layui.js"></script>
    <script type="text/javascript">
    $(function(){
  	  /* laypage({
  		    cont: $("#supplierTodos"), //容器。值支持id名、原生dom对象，jquery对象,
  		    pages: "${supplierTodos.pages}", //总页数
  		    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
  		    skip: true, //是否开启跳页
  		    total: "${supplierTodos.total}",
  		    startRow: "${supplierTodos.startRow}",
  		    endRow: "${supplierTodos.endRow}",
  		    groups: "${supplierTodos.pages}">=3?3:"${supplierTodos.pages}", //连续显示分页数
  		    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
  		    	return "${supplierTodos.pageNum}";
  		    }(), 
  		    jump: function(e, first){ //触发分页后的回调
  		        if(!first){ //一定要加此判断，否则初始时会无限刷新
  		      		window.location.href="${pageContext.request.contextPath}/todo/todos.html?supplierPage="+e.curr+"&type=supplier";
  		        }
  		    }
  		}); */
  		
  		$(function() { 
  			pageHtml("#tbody_id0","0","#agentslist0","供应商待办");
  			pageHtml("#tbody_id1","1","#agentslist1","专家待办");
  			pageHtml("#tbody_id2","2","#agentslist2","项目待办");
  	      });
  		function pageHtml(id,type,pid,texts){
  			layui.use('flow', function(){
  	  	    	var $ = layui.jquery;
  	  	        var flow = layui.flow;
  	  	        flow.load({
  	  			    elem: id,
  	  			    mb:50,
  	  			    done: function(page, next){
  	  			      var lis = [];
  	  			      $.ajax({
  	  			      url: "${pageContext.request.contextPath}/todo/supplier.do?page=" + page+"&type="+type,
  	  		          type: "get",
  	  		          dataType: "json",
  	  		          success: function(res) {
  	  		               layui.each(res.data, function(index, item){
  	  		            	  var timeJson=item.createdAt;
  	  		            	  var time=timeJson.time;
                              var str= new Date(time);
  	  			               var html="<tr onclick='view(\"${pageContext.request.contextPath}/"+item.url+"\");'>";
  	  			                   html+="<td>"+(parseInt((index+1))+(res.pageNum-1)*(res.pageSize))+"</td>";
  	  			                   html+="<td>"+item.name+"</td>";
  	  			                   html+="<td class='tc'>"+item.senderName+"</td>";
  	  			                   html+="<td class='tc'>"+str.toLocaleString()+"</td>";
  	  			                   html+="</tr>";
  	  			            	 lis.push(html);
  	  			              }); 
  	  		               if(page==1){
  	  		            	   var totals=0;
  	  		            	   if(res){
  	  		            		totals=res.total;
  	  		            	   }
  	  		            	   $(pid).prev().html(texts+"("+totals+")");
  	  		               }
  	  			           next(lis.join(''), page < res.pages);
  	  		          },
  	  			      });
  	  			    }
  	  			  });
  	  	      });
  		}
  		
  		/* 
  	    laypage({
		    cont: $("#expertTodos"), //容器。值支持id名、原生dom对象，jquery对象,
		    pages: "${expertTodos.pages}", //总页数
		    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		    skip: true, //是否开启跳页
		    total: "${expertTodos.total}",
		    startRow: "${expertTodos.startRow}",
		    endRow: "${expertTodos.endRow}",
		    groups: "${expertTodos.pages}">=3?3:"${expertTodos.pages}", //连续显示分页数
		    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
		    	return "${expertTodos.pageNum}";
		    }(), 
		    jump: function(e, first){ //触发分页后的回调
		        if(!first){ //一定要加此判断，否则初始时会无限刷新
		      		window.location.href="${pageContext.request.contextPath}/todo/todos.html?expertPage="+e.curr+"&type=expert";
		        }
		    }
		});
  		 laypage({
 		    cont: $("#projectTodos"), //容器。值支持id名、原生dom对象，jquery对象,
 		    pages: "${projectTodos.pages}", //总页数
 		    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
 		    skip: true, //是否开启跳页
 		    total: "${projectTodos.total}",
 		    startRow: "${projectTodos.startRow}",
 		    endRow: "${projectTodos.endRow}",
 		    groups: "${projectTodos.pages}">=3?3:"${projectTodos.pages}", //连续显示分页数
 		    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
 		    	return "${projectTodos.pageNum}";
 		    }(), 
 		    jump: function(e, first){ //触发分页后的回调
 		        if(!first){ //一定要加此判断，否则初始时会无限刷新
 		      		window.location.href="${pageContext.request.contextPath}/todo/todos.html?projectPage="+e.curr+"&type=project";
 		        }
 		    }
 		});
  	    $(document).keyup(function(event) {
  			if (event.keyCode == 13) {
  				query();
  			}
  		}); */
    });
     

      function view(url) {
        $("#a").attr("href", url);
        var el = document.getElementById('a');
        el.click(); //触发打开事件
      }
    </script>

    <body >
      <!-- 查看项目-->
      <div id="con_one_2" class="dnonev">
        <div id="Accordion2" class="Accordion" >
        <div  class="AccordionPanel AccordionPanelOpen">
              <div class="AccordionPanelTab" onclick="viewHidden(this,'supplierTodos')">
              </div>
              <div id="agentslist0" class="w100p h300 over_auto p0">
                <a id="a" href="#" target="_parent"></a>
                <table  class="hand table table-striped table-bordered">
                	<thead>
                    <tr>
                      <th class="info w50">序号</th>
                      <th class="info" width="45%">标题</th>
                      <th class="info">发送人</th>
                      <th class="info" width="25%">创建时间</th>
                    </tr>
                    </thead>
                    <tbody id="tbody_id0">
                    <%-- <c:forEach items="${supplierTodos.list}" var="agents" varStatus="s">
                      <tr class="cursor" onclick="view('${pageContext.request.contextPath}/${ agents.url}');">
                        <td class="tc w50">${(s.index+1)+(supplierTodos.pageNum-1)*(supplierTodos.pageSize)}</td>
                        <td class="tl pl20" title="${agents.name }">
                          <c:choose>
                            <c:when test="${fn:length(agents.name) > 20}">
                              ${fn:substring(agents.name, 0, 20)}......
                            </c:when>
                            <c:otherwise>
                              ${agents.name }
                            </c:otherwise>
                          </c:choose>
                        </td>
                        <td class="tc w100">${agents.senderName}</td>
                        <td class="tc w150">
                          <fmt:formatDate value="${agents.createdAt}" pattern="YYY-MM-dd HH:mm:ss" />
                        </td>
                      </tr>
                    </c:forEach> --%>
                    </tbody>
                </table>
                <!-- <div id="supplierTodos" align="right"></div> -->
              </div>
         </div>
         <div  class="AccordionPanel">
              <div class="AccordionPanelTab" onclick="viewHidden(this,'expertTodos')">
              </div>
              <div id="agentslist1" class="w100p h300 over_auto p0" style="display: none;">
                <table  class="hand table table-striped table-bordered">
                	<thead>
                    <tr>
                      <th class="info w50">序号</th>
                      <th class="info"  width="45%">标题</th>
                      <th class="info">发送人</th>
                      <th class="info"  width="25%">创建时间</th>
                    </tr>
                    </thead>
                    <tbody id="tbody_id1">
                    <%-- <c:forEach items="${expertTodos.list}" var="agents" varStatus="s">
                      <tr class="cursor" onclick="view('${pageContext.request.contextPath}/${ agents.url}');">
                        <td class="tc w50">${(s.index+1)+(expertTodos.pageNum-1)*(expertTodos.pageSize)}</td>
                        <td class="tl pl20" title="${agents.name }">
                          <c:choose>
                            <c:when test="${fn:length(agents.name) > 20}">
                              ${fn:substring(agents.name, 0, 20)}......
                            </c:when>
                            <c:otherwise>
                              ${agents.name }
                            </c:otherwise>
                          </c:choose>
                        </td>
                        <td class="tc w100">${agents.senderName}</td>
                        <td class="tc w150">
                          <fmt:formatDate value="${agents.createdAt}" pattern="YYY-MM-dd HH:mm:ss" />
                        </td>
                      </tr>
                    </c:forEach> --%>
                    </tbody>
                </table>
                <!-- <div id="expertTodos" align="right"></div> -->
              </div>
         </div>
        <div  class="AccordionPanel">
              <div class="AccordionPanelTab" onclick="viewHidden(this,'projectTodos')">
                                  项目待办(${projectTodos.total})
              </div>
              <div id="agentslist2" class="w100p h300 over_auto p0" style="display: none; ">
                <table  class="hand table table-striped table-bordered">
                	<thead>
                    <tr>
                      <th class="info w50">序号</th>
                      <th class="info" width="45%">标题</th>
                      <th class="info">发送人</th>
                      <th class="info" width="25%">创建时间</th>
                    </tr>
                    </thead>
                    <tbody id="tbody_id2">
                    <%-- <c:forEach items="${projectTodos.list}" var="agents" varStatus="s">
                      <tr class="cursor" onclick="view('${pageContext.request.contextPath}/${ agents.url}');">
                        <td class="tc w50">${(s.index+1)+(projectTodos.pageNum-1)*(projectTodos.pageSize)}</td>
                        <td class="tl pl20" title="${agents.name }">
                          <c:choose>
                            <c:when test="${fn:length(agents.name) > 20}">
                              ${fn:substring(agents.name, 0, 20)}......
                            </c:when>
                            <c:otherwise>
                              ${agents.name }
                            </c:otherwise>
                          </c:choose>
                        </td>
                        <td class="tc w100">${agents.senderName}</td>
                        <td class="tc w150">
                          <fmt:formatDate value="${agents.createdAt}" pattern="YYY-MM-dd HH:mm:ss" />
                        </td>
                      </tr>
                    </c:forEach> --%>
                    </tbody>
                </table>
               <!--  <div id="projectTodos" align="right"></div> -->
              </div>
         </div>
        <%-- 
          <c:forEach items="${listTodos }" var="agentslist" varStatus="s">
            <div class="AccordionPanel">
              <div class="AccordionPanelTab">
                <c:if test="${agentslist[0].undoType==1}">
                                                  供应商待办
                </c:if>
                <c:if test="${agentslist[0].undoType==2}">
                                                     专家待办
                </c:if>
                 <c:if test="${agentslist[0].undoType==3}">
                                                     项目待办
                </c:if>
                (${agentslist.size()})
              </div>
              <div class=""  id="agentslist${s.index}" style="width: 100%;height:300px; overflow:auto; ">
                <a id="a" href="#" target="_parent"></a>
                <table  class="hand table table-striped table-bordered">
                    <tr>
                      <th class="info w50">序号</th>
                      <th class="info">标题</th>
                      <th class="info">发送人</th>
                      <th class="info">创建时间</th>
                    </tr>
                    <c:forEach items="${agentslist }" var="agents" varStatus="s">
                      <tr class="cursor" onclick="view('${pageContext.request.contextPath}/${ agents.url}');">
                        <td class="tc w50">${s.index+1}</td>
                        <td class="tl pl20" title="${agents.name }">
                          <c:choose>
                            <c:when test="${fn:length(agents.name) > 20}">
                              ${fn:substring(agents.name, 0, 20)}......
                            </c:when>
                            <c:otherwise>
                              ${agents.name }
                            </c:otherwise>
                          </c:choose>
                        </td>
                        <td class="tc w100">${agents.senderName}</td>
                        <td class="tc w150">
                          <fmt:formatDate value="${agents.createdAt}" pattern="YYY-MM-dd HH:mm:ss" />
                        </td>
                      </tr>
                    </c:forEach>
                </table>
              </div>
            </div>
          </c:forEach> --%>
        </div>
      </div>
      <script type="text/javascript">
      var acc=$("#Accordion2").children();
        if("${type}"!=""){
        	if("${type}"=="expert"){
        		commNum(1);
        	}
        	if("${type}"=="project"){
        		commNum(2);
        	}
        	if("${type}"=="supplier"){
        		commNum(0);
        	}
        }
        function viewHidden(obj,name){
        		if(name=="projectTodos"){
        			commObj(obj,2);
        		}else if(name=="expertTodos"){
        			commObj(obj,1);
        		}else if(name=="supplierTodos"){
        			commObj(obj,0);
        		}
        }
        function commObj(obj,num){
        	for(var i=0;i<acc.length;i++){
				if(i==num){
					$(obj).parent().attr("class","AccordionPanel AccordionPanelOpen");
					$(obj).next()[0].style.display='';
    				$(obj).next()[0].style.height="300px";
				}else{
					$(acc[i]).attr("class","AccordionPanel AccordionPanelClosed");
    				$(acc[i]).children()[1].style.display='none';
    				$(acc[i]).children()[1].style.height="0px";
				}
			}
        }
        function commNum(index){
        	for(var i=0;i<acc.length;i++){
    			if(i==index){
    				$(acc[i]).attr("class","AccordionPanel AccordionPanelOpen");
    				$(acc[i]).children()[1].style.display='';
    				$(acc[i]).children()[1].style.height="300px";
    			}else{
    				$(acc[i]).attr("class","AccordionPanel AccordionPanelClosed");
    				$(acc[i]).children()[1].style.display='none';
    				$(acc[i]).children()[1].style.height="0px";
    			}
    		}
        }
        
        $("#agentslist0").scroll(function(){
        	scrollPage(0);
        })
        $("#agentslist1").scroll(function(){
        	scrollPage(1);
        })
        $("#agentslist2").scroll(function(){
        	scrollPage(2);
        })
        function scrollPage(index){
        	var height=parseFloat($("#agentslist"+index).height());
        	var scrollTop=parseFloat($("#agentslist"+index).scrollTop());
        	var scrollHeight=parseFloat(document.getElementById("agentslist"+index).scrollHeight)-1;
        	if(document.getElementById("tbody_id"+index).lastChild.lastChild!=null){
        		document.getElementById("tbody_id"+index).lastChild.lastChild.text="";
        	} 
        	if((height+scrollTop)>=scrollHeight-10){
        	   var div=document.getElementById("tbody_id"+index).lastChild.lastChild;
        	   if(div!=null){
        		   div.click();
        	   }
        	  
        	}
        	
        }
      </script>

    </body>

</html>