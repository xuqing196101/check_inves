<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>


<tr>
    <td class="tc" style="border: 1px solid #ddd;"><input type="checkbox" value="${id}" /></td>
    <td class="tc">
        <input type="text" required class="w200 border0 address_zip_code" name="addressList[${ind }].code" value="" onblur='tempSave()' />
        <input type='hidden' name='addressList[${ind }].id' value='${id}'>
    </td>
    <td class="tc" style="border: 1px solid #ddd;">
        <div class="col-md-5 col-xs-5 col-sm-5 mr5 p0 ml20">
            <select id="root_area_select_id_" class="w100p" onchange="loadChildren(this)" name="addressList[${ind }].provinceId" <c:if test="${fn:contains(audit,'address_'.concat(addr.id))}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('address_${addr.id }')"</c:if>>
                <option value="">请选择</option>
                <c:forEach items="${privnce }" var="prin">
                    <option value="${prin.id }" onchange='tempSave()'>${prin.name }</option>
                </c:forEach>
            </select>
        </div>
        <div class="col-md-5 col-xs-5 col-sm-5 mr5 p0">
            <select id="children_area_select_id_" class="w100p" name="addressList[${ind }].address" <c:if test="${fn:contains(audit,'address_'.concat(addr.id))}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('address_${addr.id }')"</c:if>>
                <c:forEach items="${addr.areaList }" var="city">
                    <option value="${city.id }" onchange='tempSave()'>${city.name }</option>
                </c:forEach>
            </select>
        </div>
    </td>
    <td class="tc" style="border: 1px solid #ddd;">
        <input type="text" class="w200 border0" name="addressList[${ind }].detailAddress" onblur='tempSave()' maxlength="50" value="" >

    </td>
    <td class="tc" style="border: 1px solid #ddd;">
        <div class="w200 fl">
            <u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="house_up_${ind}" multiple="true" businessId="${id}" sysKey="${sysKey}" typeId="${typeId}" auto="true" />
            <u:show showId="house_show_${ind}" businessId="${id}" sysKey="${sysKey}" typeId="${typeId}" />
        </div>
    </td>
</tr>




<%--<li class="col-md-2 col-sm-6 col-xs-12 pl10">
    <span class="col-md-12 col-xs-12 col-sm-12  padding-left-5"><i class="red">*</i> 生产或经营地址邮编</span>
    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
        <input type="text" name="addressList[${ind }].code" value="" onblur='tempSave()' />
        <input type='hidden' name='addressList[${ind }].id' value='${id}' onblur='tempSave()'>
        <span class="add-on cur_point">i</span>
        <div class="cue"></div>
    </div>
</li>

<li class="col-md-3 col-sm-6 col-xs-12">
    <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 生产或经营地址（填写所有地址）</span>
    <div class="col-md-12 col-xs-12 col-sm-12 select_common p0">
        <div class="col-md-5 col-xs-5 col-sm-5 mr5 p0">
            <select id="root_area_select_id" onchange="loadChildren(this)" name="addressList[${ind}].provinceId" >
                <option value="">请选择</option>
                <c:forEach items="${privnce }" var="prin">
                    <option value="${prin.id }" onchange='tempSave()'>${prin.name }</option>
                </c:forEach>
            </select>
        </div>
        <div class="col-md-5 col-xs-5 col-sm-5 mr5 p0">
            <select id="children_area_select_id" name="addressList[${ind}].address" >
                <c:forEach items="${addr.areaList }" var="city">
                    <option value="${city.id }" onchange='tempSave()'>${city.name }</option>
                </c:forEach>
            </select>
        </div>
        <div class="cue"> </div>
    </div>
</li>

<li class="col-md-2 col-sm-6 col-xs-12">
    <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 生产或经营详细地址</span>
    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
        <input type="text" name="addressList[${ind}].detailAddress" onblur='tempSave()' maxlength="50" value="" >
        <span class="add-on cur_point">i</span>
        <div class="cue"></div>
    </div>
</li>
<li class="col-md-3 col-sm-6 col-xs-12">
    <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5" ><i class="red">*</i> 房产证明或租赁协议 </span>
    <div class="input-append h30 input_group col-sm-12 col-xs-12 col-md-12 p0">
        <u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="house_up_${ind}" multiple="true" maxcount="3" businessId="${id}" sysKey="${sysKey}" typeId="${typeId}" auto="true" />
        <u:show showId="house_show_${ind}" businessId="${id}" sysKey="${sysKey}" typeId="${typeId}" />
        <div class="cue"></div>
    </div>
</li>
<li class="col-md-2 col-sm-6 col-xs-12">
    <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5 white">操作</span>
    <div class="col-md-12 col-xs-12 col-sm-12 p0 mb25 h30">
        <input type="button" onclick="increaseAddress(this)" class="btn list_btn" value="十" />
        <input type="button" onclick="delAddress(this)" class="btn list_btn" value="一" />
        <input type="hidden" value="${id}" />
    </div>
</li>--%>








