(function($) {
  $.fn.listConstructor = function(options) {
    var list_content = [];  // 初始化数据
    
    //默认参数
    var defaults = {
      type: 'POST',
      dataType: 'json',
      url: root_url + '/expertAgainAudit/findBatchDetails.do',
      data: {
        batchId: $('[name=batchId]').val()
      },
      success: function (data) {
        list_content = data.object;  // 储存所需数据到变量
        var userType = data.userType;
        
        if (typeof(list_content) != null && typeof(list_content) != 'null' && typeof(list_content) != 'undefined') {
          $('#head_tit').html(list_content.batchName);
          
          // 按钮判断
          if (userType === '4') {
            $('.search_detail').removeClass('hide');
            $('#back_btn').removeClass('hide');
            
            // 初始化搜索
            if (is_init === 0) {
              var str = '';
              // 判断是否禁用页面功能
              if (list_content.batchStatus == '1') {
                $('[name=orgName]').prop('disabled', true);
                $('[name=expertsFrom]').prop('disabled', true);
                $('[name=expertsTypeId]').prop('disabled', true);
                $('[name=expertsTypeId]').prop('disabled', true);
                $('[name=groupId]').prop('disabled', true);
                $('[name=status]').prop('disabled', true);
              } else {
                for (var orgName_i in list_content.allOrg) {
                  str += '<option value="'+ list_content.allOrg[orgName_i].shortName +'">'+ list_content.allOrg[orgName_i].shortName +'</option>';
                }
                $('[name=orgName]').html('<option value="">请选择</option>' + str);
                str = '';
                for (var expertsFrom_i in list_content.lyTypeList) {
                  str += '<option value="'+ list_content.lyTypeList[expertsFrom_i].id +'">'+ list_content.lyTypeList[expertsFrom_i].name +'</option>';
                }
                $('[name=expertsFrom]').html('<option value="">全部</option>' + str);
                str = '';
                for (var expertsTypeId_i in list_content.expTypeList) {
                  str += '<option value="'+ list_content.expTypeList[expertsTypeId_i].id +'">'+ list_content.expTypeList[expertsTypeId_i].name +'</option>';
                }
                $('[name=expertsTypeId]').html(str);
                $('[name=expertsTypeId]').select2({
                  placeholder: '请选择'
                });
                str = '';
                for (var groupId_i in list_content.groupList) {
                  str += '<option value="'+ list_content.groupList[groupId_i].groupId +'">'+ list_content.groupList[groupId_i].groupName +'</option>';
                }
                $('[name=groupId]').html('<option value="">全部</option>' + str);
              }
            }
            is_init ++;
            
            $('#pic_checkword_picker').css({
              verticalAlign: 'middle',
              marginRight: 3,
              marginBottom: 2
            });
            
            $('#pic_checkword_picker .webuploader-pick').css({
              paddingTop: 4,
              paddingBottom: 4
            });
  
            if ($('.pic_upload').siblings('button').length <= 0) {
              $('.pic_upload').before('<button type="button" class="btn btn-windows group canDisable" onclick="jump_batchGroup()">批次分组</button>'
                +'<button type="button" class="btn btn-windows config canDisable" onclick="jump_auditBatch()">审核配置</button>'
                +'<button type="button" class="btn btn-windows reset" onclick="javascript: location.reload()">刷新</button>'
                +'<button type="button" class="btn btn-windows input" onclick="downloadReviewTable()">下载复审统计表</button>'
              );
              $('.pic_upload').after('<button type="button" class="btn btn-windows passed canDisable mr0 ml5" onclick="takeEffect()">生效</button>');
            }
            
            // 判断是否禁用页面功能
            if (list_content.batchStatus == '1') {
              $('#table_content').html('<table class="table table-bordered table-condensed table-hover table-striped break-all againAudit_table fixedTable">'
                +'<thead>'
                +'  <tr>'
                +'    <th class="w30"><input type="checkbox" onclick="checkAll(this)" name="checkAll" disabled></th>'
                +'    <th class="w130">专家编号</th>'
                +'    <th class="w100">采购机构</th>'
                +'    <th class="w80">专家姓名</th>'
                +'    <th class="w50">性别</th>'
                +'    <th class="w50">专家类型</th>'
                +'    <th class="w80">专家类别</th>'
                +'    <th>工作单位</th>'
                +'    <th class="w80">专业职称<br>(职务)</th>'
                +'    <th class="w60">审核组</th>'
                +'    <th class="w110">审核状态</th>'
                +'    <th class="w50">审核结论</th>'
                +'    <th class="w100">操作</th>'
                +'  </tr>'
                +'</thead>'
                +'<tbody id="list_content"></tbody>'
              +'</table>');
            } else {
              $('#table_content').html('<table class="table table-bordered table-condensed table-hover table-striped break-all againAudit_table fixedTable">'
                +'<thead>'
                +'  <tr>'
                +'    <th class="w30"><input type="checkbox" onclick="checkAll(this)" name="checkAll"></th>'
                +'    <th class="w130">专家编号</th>'
                +'    <th class="w100">采购机构</th>'
                +'    <th class="w80">专家姓名</th>'
                +'    <th class="w50">性别</th>'
                +'    <th class="w50">专家类型</th>'
                +'    <th class="w80">专家类别</th>'
                +'    <th>工作单位</th>'
                +'    <th class="w80">专业职称<br>(职务)</th>'
                +'    <th class="w60">审核组</th>'
                +'    <th class="w110">审核状态</th>'
                +'    <th class="w50">审核结论</th>'
                +'    <th class="w100">操作</th>'
                +'  </tr>'
                +'</thead>'
                +'<tbody id="list_content"></tbody>'
              +'</table>');
            }
            
            if (typeof(list_content) != 'undefined') {
              $('#list_content').html('');
              for (var i in list_content.list) {
                var btn = '';
                
                // 判断复审专家输出
                if (list_content.list[i].status === '4' || list_content.list[i].status === '11' || list_content.list[i].status === '14') {
                  list_content.list[i].auditor = '';
                  list_content.list[i].auditAt = '';
                }
                
                // 判断状态输出
                if (list_content.list[i].status === '-3') {
                  list_content.list[i].status = '复审合格';
                } else if (list_content.list[i].status === '-2' && list_content.list[i].isReviewEnd != '1') {
                  list_content.list[i].status = '<span class="green">预复审结束</span>';
                  btn = '<button type="button" class="btn canDisable" onclick="downloadTable(\''+ list_content.list[i].expertId +'\')">下载复审表</button>';
                } else if (list_content.list[i].status === '-2' && typeof(list_content.list[i].reviewStatus) == 'undefined') {
                  list_content.list[i].status = '<span class="red">复审结束</span>';
                  btn = '<button type="button" class="btn canDisable" onclick="reexamination(\''+ list_content.list[i].expertId +'\')">重新复审</button>';
                } else if (list_content.list[i].status === '-1') {
                  list_content.list[i].status = '暂存';
                } else if (list_content.list[i].status === '0') {
                  list_content.list[i].status = '待初审';
                } else if (list_content.list[i].status === '1') {
                  list_content.list[i].status = '初审合格';
                } else if (list_content.list[i].status === '2') {
                  list_content.list[i].status = '初审不合格';
                } else if (list_content.list[i].status === '3') {
                  list_content.list[i].status = '初审退回修改';
                } else if (list_content.list[i].status === '4') {
                  if (list_content.list[i].status === '4' && list_content.list[i].auditTemporary === '2') {
                    list_content.list[i].status = '复审中';
                  } else {
                    list_content.list[i].status = '复审已分配';
                  }
                } else if (list_content.list[i].status === '5') {
                  list_content.list[i].status = '复审不合格';
                } else if (list_content.list[i].status === '6') {
                  list_content.list[i].status = '待复查';
                } else if (list_content.list[i].status === '7') {
                  list_content.list[i].status = '复查合格';
                } else if (list_content.list[i].status === '8') {
                  list_content.list[i].status = '复查不合格';
                } else if (list_content.list[i].status === '9') {
                  list_content.list[i].status = '初审退回再审核';
                } else if (list_content.list[i].status === '10') {
                  list_content.list[i].status = '复审退回修改';
                } else if (list_content.list[i].status === '11') {
                  list_content.list[i].status = '待分配';
                } else if (list_content.list[i].status === '12') {
                  list_content.list[i].status = '处罚中';
                } else if (list_content.list[i].status === '13') {
                  list_content.list[i].status = '无产品专家';
                } else if (list_content.list[i].status === '14') {
                  list_content.list[i].status = '复审待分组专家';
                } else if (list_content.list[i].status === '15') {
                  list_content.list[i].status = '预初审合格';
                } else if (list_content.list[i].status === '16') {
                  list_content.list[i].status = '预初审不合格';
                } else if (list_content.list[i].status === '17') {
                  list_content.list[i].status = '资料不全';
                } else if (list_content.list[i].status === '100' ) {
                  list_content.list[i].status = '重新复审';
                }
                
                // 判断是否为重新复审状态
                if (typeof(list_content.list[i].reviewStatus) != 'undefined' && list_content.list[i].status == '-2') {
                  list_content.list[i].status = '重新复审';
                  btn = '<button type="button" class="btn canDisable" onclick="cancel_reexamination(\''+ list_content.list[i].expertId +'\')">取消重新复审</button>';
                }
                
                if (typeof(list_content.list[i].batchDetailsNumber) === 'undefined') {
                  list_content.list[i].batchDetailsNumber = '';
                }
                if (typeof(list_content.list[i].orgName) === 'undefined') {
                  list_content.list[i].orgName = '';
                }
                if (typeof(list_content.list[i].realName) === 'undefined') {
                  list_content.list[i].realName = '';
                }
                if (typeof(list_content.list[i].gender) === 'undefined') {
                  list_content.list[i].gender = '';
                }
                if (typeof(list_content.list[i].workUnit) === 'undefined') {
                  list_content.list[i].workUnit = '';
                }
                if (typeof(list_content.list[i].professTechTitles) === 'undefined' || list_content.list[i].professTechTitles == '') {
                  if (typeof(list_content.list[i].atDuty) === 'undefined') {
                    list_content.list[i].professTechTitles = '';
                  } else {
                    list_content.list[i].professTechTitles = list_content.list[i].atDuty;
                  }
                }
                if (typeof(list_content.list[i].groupName) === 'undefined') {
                  list_content.list[i].groupName = '';
                }
                if (typeof(list_content.list[i].auditor) === 'undefined') {
                  list_content.list[i].auditor = '';
                }
                if (typeof(list_content.list[i].status) == 'undefined') {
                  list_content.list[i].status = '';
                }
                if (typeof(list_content.list[i].auditAt) === 'undefined') {
                  list_content.list[i].auditAt = '';
                }
                if (typeof(list_content.list[i].expertsTypeId) === 'undefined') {
                  list_content.list[i].expertsTypeId = '';
                }
                if (typeof(list_content.list[i].expertsFrom) === 'undefined') {
                  list_content.list[i].expertsFrom = '';
                }
                if (typeof(list_content.list[i].expertStatus) === 'undefined') {
                  list_content.list[i].expertStatus = '';
                } else if (list_content.list[i].expertStatus === '-3') {
                  list_content.list[i].expertStatus = '复审合格';
                } else if (list_content.list[i].expertStatus === '5') {
                  list_content.list[i].expertStatus = '复审不合格';
                } else if (list_content.list[i].expertStatus === '10') {
                  list_content.list[i].expertStatus = '复审退回修改';
                }
                
                // 判断是否禁用页面功能
                if (list_content.batchStatus == '1') {
                  $('#list_content').append('<tr><input id="'+ list_content.list[i].expertId +'" type="hidden">'
                	+'<td class="text-center"><input type="checkbox" value="'+ list_content.list[i].expertId +'" class="select_item" disabled></td>'
                    +'<td class="text-center break-all">'+ list_content.list[i].batchDetailsNumber +'</td>'
                    +'<td class="text-center break-all">'+ list_content.list[i].orgName +'</td>'
                    +'<td class="text-center break-all" onclick="viewDetails(\''+ list_content.list[i].expertId +'\');"><a href="javascript:void(0)">'+ list_content.list[i].realName +'</a></td>'
                    +'<td class="text-center break-all">'+ list_content.list[i].gender +'</td>'
                    +'<td class="text-center break-all">'+ list_content.list[i].expertsFrom +'</td>'
                    +'<td class="text-center break-all">'+ list_content.list[i].expertsTypeId +'</td>'
                    +'<td class="text-center break-all">'+ list_content.list[i].workUnit +'</td>'
                    +'<td class="text-center break-all">'+ list_content.list[i].professTechTitles +'</td>'
                    +'<td class="text-center break-all">'+ list_content.list[i].groupName +'</td>'
                    +'<td class="text-center break-all">'+ list_content.list[i].status +'</td>'
                    +'<td class="text-center break-all">'+ list_content.list[i].expertStatus +'</td>'
                    +'<td class="text-center break-all">'+ btn +'</td>'
                  +'</tr>');
                } else {
                  $('#list_content').append('<tr><input id="'+ list_content.list[i].expertId +'" type="hidden">'
                	+'<td class="text-center"><input type="checkbox" value="'+ list_content.list[i].expertId +'" class="select_item"></td>'
                    +'<td class="text-center break-all">'+ list_content.list[i].batchDetailsNumber +'</td>'
                    +'<td class="text-center break-all">'+ list_content.list[i].orgName +'</td>'
                    +'<td class="text-center break-all" onclick="viewDetails(\''+ list_content.list[i].expertId +'\');"><a href="javascript:void(0)">'+ list_content.list[i].realName +'</a></td>'
                    +'<td class="text-center break-all">'+ list_content.list[i].gender +'</td>'
                    +'<td class="text-center break-all">'+ list_content.list[i].expertsFrom +'</td>'
                    +'<td class="text-center break-all">'+ list_content.list[i].expertsTypeId +'</td>'
                    +'<td class="text-center break-all">'+ list_content.list[i].workUnit +'</td>'
                    +'<td class="text-center break-all">'+ list_content.list[i].professTechTitles +'</td>'
                    +'<td class="text-center break-all">'+ list_content.list[i].groupName +'</td>'
                    +'<td class="text-center break-all">'+ list_content.list[i].status +'</td>'
                    +'<td class="text-center break-all">'+ list_content.list[i].expertStatus +'</td>'
                    +'<td class="text-center break-all">'+ btn +'</td>'
                  +'</tr>');
                }
              }
            }
            
            // 判断是否禁用页面功能
            if (list_content.batchStatus == '1') {
              $('.canDisable').prop('disabled', true);
            }
            
            $('.fixedTable').m_fixedTable({
              fixedNumber: 0
            });
          } else if (userType === '6') {
            $('#btn_group').html('<button type="button" class="btn btn-windows reset" onclick="javascript: location.reload()">刷新</button>');
            
            $('#table_content').html('<table class="table table-bordered table-condensed table-hover table-striped break-all againAudit_table fixedTable">'
              +'<thead>'
              +'  <tr>'
              +'    <th class="w100">专家编号</th>'
              +'    <th class="w100">采购机构</th>'
              +'    <th class="w80">专家姓名</th>'
              +'    <th class="w50">性别</th>'
              +'    <th>工作单位</th>'
              +'    <th class="w120">专业职称(职务)</th>'
              +'    <th class="w120">专家类型</th>'
              +'    <th class="w80">专家类别</th>'
              +'    <th class="w60">审核组</th>'
              +'    <th class="w110">审核状态</th>'
              +'    <th>审核结论</th>'
              +'    <th class="w100">操作</th>'
              +'  </tr>'
              +'</thead>'
              +'<tbody id="list_content"></tbody>'
            +'</table>');
            
            if (typeof(list_content) != 'undefined') {
              $('#list_content').html('');
              for (var i in list_content.list.list) {
                var btn = '';
                
                // 判断复审专家输出
                if (list_content.list.list[i].status === '4' || list_content.list.list[i].status === '11' || list_content.list.list[i].status === '14') {
                  list_content.list.list[i].auditor = '';
                  list_content.list.list[i].auditAt = '';
                }
                
                // 判断状态输出
                if (list_content.list.list[i].status === '-3') {
                  list_content.list[i].status = '复审合格';
                } else if (list_content.list.list[i].status === '-2'&& list_content.list.list[i].isReviewEnd != '1') {
                  list_content.list.list[i].status = '<span class="green">预复审结束</span>';
                  if(list_content.list.list[i].isDownload == '1'){
                  	btn = '<button type="button" class="btn w100p m0 canDisable" onclick="reviewEnd(\''+ list_content.list.list[i].expertId +'\');">复审结束</button>';
                  }
                }else if (list_content.list.list[i].status === '-2' && list_content.list.list[i].isReviewEnd == '1') {
                    list_content.list.list[i].status = '<span class="red">复审结束</span>';
                }else if (list_content.list.list[i].status === '-1') {
                  list_content.list.list[i].status = '暂存';
                } else if (list_content.list.list[i].status === '0') {
                  list_content.list.list[i].status = '待初审';
                } else if (list_content.list.list[i].status === '1') {
                  list_content.list.list[i].status = '初审合格';
                } else if (list_content.list.list[i].status === '2') {
                  list_content.list.list[i].status = '初审不合格';
                } else if (list_content.list.list[i].status === '3') {
                  list_content.list.list[i].status = '初审退回修改';
                } else if (list_content.list.list[i].status === '4') {
                  if (list_content.list.list[i].status === '4' && list_content.list.list[i].auditTemporary === '2') {
                    list_content.list.list[i].status = '复审中';
                  } else {
                    list_content.list.list[i].status = '复审已分配';
                  }
                } else if (list_content.list.list[i].status === '5') {
                  list_content.list.list[i].status = '复审不合格';
                } else if (list_content.list.list[i].status === '6') {
                  list_content.list.list[i].status = '待复查';
                } else if (list_content.list.list[i].status === '7') {
                  list_content.list.list[i].status = '复查合格';
                } else if (list_content.list.list[i].status === '8') {
                  list_content.list.list[i].status = '复查不合格';
                } else if (list_content.list.list[i].status === '9') {
                  list_content.list.list[i].status = '初审退回再审核';
                } else if (list_content.list.list[i].status === '10') {
                  list_content.list.list[i].status = '复审退回修改';
                } else if (list_content.list.list[i].status === '11') {
                  list_content.list.list[i].status = '待分配';
                } else if (list_content.list.list[i].status === '12') {
                  list_content.list.list[i].status = '处罚中';
                } else if (list_content.list.list[i].status === '13') {
                  list_content.list.list[i].status = '无产品专家';
                } else if (list_content.list.list[i].status === '14') {
                  list_content.list.list[i].status = '复审待分组专家';
                } else if (list_content.list.list[i].status === '15') {
                  list_content.list.list[i].status = '预初审合格';
                } else if (list_content.list.list[i].status === '16') {
                  list_content.list.list[i].status = '预初审不合格';
                } else if (list_content.list.list[i].status === '17') {
                  list_content.list.list[i].status = '资料不全';
                }
                
                if (typeof(list_content.list.list[i].batchDetailsNumber) === 'undefined') {
                  list_content.list.list[i].batchDetailsNumber = '';
                }
                if (typeof(list_content.list.list[i].orgName) === 'undefined') {
                  list_content.list.list[i].orgName = '';
                }
                if (typeof(list_content.list.list[i].realName) === 'undefined') {
                  list_content.list.list[i].realName = '';
                }
                if (typeof(list_content.list.list[i].gender) === 'undefined') {
                  list_content.list.list[i].gender = '';
                }
                if (typeof(list_content.list.list[i].workUnit) === 'undefined') {
                  list_content.list.list[i].workUnit = '';
                }
                if (typeof(list_content.list.list[i].professTechTitles) === 'undefined' || list_content.list.list[i].professTechTitles == '') {
                  if (typeof(list_content.list.list[i].atDuty) === 'undefined') {
                    list_content.list.list[i].professTechTitles = '';
                  } else {
                    list_content.list.list[i].professTechTitles = list_content.list.list[i].atDuty;
                  }
                }
                if (typeof(list_content.list.list[i].updateTime) === 'undefined') {
                  list_content.list.list[i].updateTime = '';
                }
                if (typeof(list_content.list.list[i].groupName) === 'undefined') {
                  list_content.list.list[i].groupName = '';
                }
                if (typeof(list_content.list.list[i].auditor) === 'undefined') {
                  list_content.list.list[i].auditor = '';
                }
                if (typeof(list_content.list.list[i].status) == 'undefined') {
                  list_content.list.list[i].status = '';
                }
                if (typeof(list_content.list.list[i].auditAt) === 'undefined') {
                  list_content.list.list[i].auditAt = '';
                }
                if (typeof(list_content.list.list[i].expertStatus) === 'undefined') {
                  list_content.list.list[i].expertStatus = '';
                } else if (list_content.list.list[i].expertStatus === '-3') {
                  list_content.list.list[i].expertStatus = '复审合格';
                } else if (list_content.list.list[i].expertStatus === '5') {
                  list_content.list.list[i].expertStatus = '复审不合格';
                } else if (list_content.list.list[i].expertStatus === '10') {
                  list_content.list.list[i].expertStatus = '复审退回修改';
                }
                
                if (list_content.list.list[i].status != "公示中" && list_content.list.list[i].status != "复审合格" && list_content.list.list[i].status != "<span class=\"red\">复审结束</span>" && list_content.list.list[i].status != "复审不合格" && list_content.list.list[i].status != "复审退回修改" && list_content.list.list[i].status != "复查合格" && list_content.list.list[i].status != "复查未合格") {
                  btn = '<button type="button" class="btn w100p mr0 mb5 canDisable" onclick="expert_auditBatch(\''+ list_content.list.list[i].expertId +'\')">复审</button>' + btn;
                }
                
                $('#list_content').append('<tr>'
                  +'<input type="hidden" name="expertId" value="'+ list_content.list.list[i].expertId +'">'
                  +'<td class="text-center break-all">'+ list_content.list.list[i].batchDetailsNumber +'</td>'
                  +'<td class="text-center break-all">'+ list_content.list.list[i].orgName +'</td>'
                  +'<td class="text-center break-all">'+ list_content.list.list[i].realName +'</td>'
                  +'<td class="text-center break-all">'+ list_content.list.list[i].gender +'</td>'
                  +'<td class="text-center break-all">'+ list_content.list.list[i].workUnit +'</td>'
                  +'<td class="text-center break-all">'+ list_content.list.list[i].professTechTitles +'</td>'
                  +'<td class="text-center break-all">'+ list_content.list.list[i].expertsFrom +'</td>'
                  +'<td class="text-center break-all">'+ list_content.list.list[i].expertsTypeId +'</td>'
                  +'<td class="text-center break-all">'+ list_content.list.list[i].groupName +'</td>'
                  +'<td class="text-center break-all">'+ list_content.list.list[i].status +'</td>'
                  +'<td class="text-center break-all">'+ list_content.list.list[i].expertStatus +'</td>'
                  +'<td class="text-center break-all">'+ btn +'</td>'
                +'</tr>');
              }
            }
            
            // 判断是否禁用页面功能
            if (list_content.batchStatus == '1') {
              $('.canDisable').prop('disabled', true);
            }
            
            $('.fixedTable').m_fixedTable({
              fixedNumber: 0
            });
            
            var position = '';
            $('#list_content tr').each(function () {
              if (getUrlParam('expertId') == $(this).find('input[name="expertId"]').val()) {
                position = $(this).offset().top - parseInt($('.mfixed-header').height());
              }
            });
            
            $('html, body').animate({
              scrollTop: position
            });
          }
        } else {
          $('#list_content').html('');
        }
        
        // 勾选翻页之前选中的项
        var allchecked = 0;
        for (var i in select_ids) {
          $('.select_item').each(function () {
            if ($(this).val() === select_ids[i]) {
              allchecked++;
              $(this).prop('checked', true);
              return false;
            }
          });
          if (allchecked === $('.select_item').length) {
            $('[name=checkAll]').prop('checked', true);
          } else {
            $('[name=checkAll]').prop('checked', false);
          }
        }
        
        // 绑定列表框点击事件，获取选中id集合
        var select_checkbox = $('.againAudit_table').find('.select_item');
        if (select_checkbox.length > 0) {
          select_checkbox.bind('click', function () {
            var this_val = $(this).val().toString();
            
            if ($(this).is(':checked')) {
              select_ids.push(this_val);
            } else {
              for (var i in select_ids) {
                if (select_ids[i] == this_val) {
                  select_ids.splice(i, 1);
                  break;
                }
              }
            }
            
            var sum = 0;
            $('.againAudit_table').find('.select_item').each(function () {
              if ($(this).is(':checked')) {
                sum++;
              }
            });
            
            if (sum === $('.againAudit_table').find('.select_item').length) {
              $('[name=checkAll]').prop('checked', true);
            } else {
              $('[name=checkAll]').prop('checked', false);
            }
          });
        }
        
        index_load(false);
      },
      error: function (data) {
        layer.msg(data.message);
      }
    };

    //覆盖默认参数
    var opts = $.extend(defaults, options);
    var el = this;

    var start = function() {
      el.each(function() {
        $.ajax({
          type: opts.type,
          dataType: opts.dataType,
          url: opts.url,
          data: opts.data,
          success: opts.success
        });
      });
    }

    return start();
  }
})(jQuery);