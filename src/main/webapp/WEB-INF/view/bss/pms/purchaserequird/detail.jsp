 <%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
 
  <tr name="detailRow">
  		<td class="tc">${index+1}</td>
  
				 						<td class="tc  p0">
											<input type="hidden" name="list[${index }].id"  value="">
										
											<input type="text" name="list[${index }].seq" value="" class="m0">
											<input type="hidden" name="list[${index }].parentId"  value="">
										</td>
										<td class=" p0" >
										
									<%-- 	<input type="text" name="list[${index }].department"   value="${obj.department}"> --%>
											<input type="hidden" name="list[${index }].department" value="${orgId }" >
								  			<input type="text"  readonly="readonly" value="${orgName}" >
											
										
										</td>
										<td class=" p0">
											<input type="text" name="list[${index }].goodsName" onkeyup="listName(this)" onblur="lossValue()" value="" />
										</td>
										<td class="tc  p0"><input type="text" name="list[${index }].stand" value="${objs.stand}"></td>
										<td class="tc  p0"><input type="text" name="list[${index }].qualitStand" value="" class=""></td>
										<td class="tc  p0"><input type="text" name="list[${index }].item" value="" ></td>
										<td class="tc p0" ><input type="text" name="list[${index }].purchaseCount" onkeyup="checkNum(this,1)" value="" ></td>
										<td class="tc  p0" ><input type="text" name="list[${index }].price" onkeyup="checkNum(this,2)" value="" ></td>
										<td class="tc  p0"><input type="text" name="list[${index }].budget"   value=" " ></td>
										<td class=" p0"><input type="text" name="list[${index }].deliverDate" value=" " ></td>
										<td class="p0">
											<select name="list[${index }].purchaseType" class="pt" onchange="changeType(this)" >
												<option value="">请选择</option>
												<c:forEach items="${list2 }" var="objd">
														<option value="${objd.id }"> ${objd.name }</option>
													 
												</c:forEach>
											</select>
										</td>
										<td class="tc  p0"><input type="text" name="list[${index }].supplier"  class="m0"></td>
										<td class="tc  p0"><input type="text" name="list[${index }].isFreeTax" class="m0"></td>
									 	<td class="tc  p0" name='userNone'><input type="text" name="list[${index }].goodsUse" class="m0"></td>
										 <td class="tc p0"  name='userNone' ><input type="text" name="list[${index }].useUnit" class="m0"></td>
									     <td class="tc  p0"><input type="text" name="list[${index }].memo" value=" " class="m0" ></td>
									 	<td class="tc p0"><button type="button" class="btn" onclick="delRowIndex(this)">删除</button></td>
 		</tr>