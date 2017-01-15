 <%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
 
  <tr name="detailRow">
				 						<td class="tc  p0">
											<input type="hidden" name="list[${vs.index }].id"  value="${id}">
										
											<input type="text" name="list[${vs.index }].seq" value="${objs.seq}" class="m0">
											<input type="hidden" name="list[${vs.index }].parentId"  value="">
										</td>
										<td class=" p0" >
										
									<%-- 	<input type="text" name="list[${vs.index }].department"   value="${obj.department}"> --%>
											<input type="hidden" name="list[${vs.index }].department" value="${orgId }" >
								  			<input type="text"  readonly="readonly" value="${orgName}" >
											
										
										</td>
										<td class=" p0">
											<input type="text" name="list[${vs.index }].goodsName" onkeyup="listName(this)" onblur="lossValue()" value="" />
										</td>
										<td class="tc  p0"><input type="text" name="list[${vs.index }].stand" value="${objs.stand}"></td>
										<td class="tc  p0"><input type="text" name="list[${vs.index }].qualitStand" value="" class=""></td>
										<td class="tc  p0"><input type="text" name="list[${vs.index }].item" value="" ></td>
										<td class="tc p0" name="purchaseQuantity"><input type="text" name="list[${vs.index }].purchaseCount" onkeyup="checkNum(this,1)" value="" ></td>
										<td class="tc  p0" name="unitPrice"><input type="text" name="list[${vs.index }].price" onkeyup="checkNum(this,2)" value="" ></td>
										<td class="tc  p0"><input type="text" name="list[${vs.index }].budget"   value=" " ></td>
										<td class=" p0"><input type="text" name="list[${vs.index }].deliverDate" value=" " ></td>
										<td class="p0">
											<select name="list[${vs.index }].purchaseType" class="pt" onchange="changeType(this)" >
												<option value="">请选择</option>
												<c:forEach items="${list2 }" var="objd">
														<option value="${objd.id }"> ${objd.name }</option>
													 
												</c:forEach>
											</select>
										</td>
										<td class="tc  p0"><input type="text" name="list[${vs.index }].supplier"  class="m0"></td>
										<td class="tc  p0"><input type="text" name="list[${vs.index }].isFreeTax" class="m0"></td>
						 	<td class="tc  p0"><input type="text" name="list[${vs.index }].goodsUse" class="m0"></td>
							 <td class="tc p0"><input type="text" name="list[${vs.index }].useUnit" class="m0"></td>
						 <td class="tc  p0"><input type="text" name="list[${vs.index }].memo" value=" " class="m0" ></td>
 		</tr>