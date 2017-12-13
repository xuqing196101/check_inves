// 获取地址栏参数
function getUrlParam(name) {
  var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
  var r = window.location.search.substr(1).match(reg);
  if (r!=null) return unescape(r[2]); return null;
}

// 去除空格
function Trim(str, is_global) {
  var result;
  result = str.replace(/(^\s+)|(\s+$)/g,"");
  if(is_global.toLowerCase()=="g") {
    result = result.replace(/\s/g,"");
  }
  return result;
}

//  全选操作
function checkAll(el, className) {
  var temp_list = [];
  
  if ($(el).is(':checked')) {
    if (typeof(className) != 'undefined') {
      $(className).find('.select_item').each(function () {
        $(this).prop('checked', true);
        temp_list.push($(this).val());
      });
    } else {
      $('.againAudit_table').find('.select_item').each(function () {
        $(this).prop('checked', true);
        temp_list.push($(this).val());
      });
    }
    
    for (var i in temp_list) {
      for (var ii in select_ids) {
        if (temp_list[i] === select_ids[ii]) {
          temp_list.splice(i, 1);
        }
      }
    }
    
    for (var iii in temp_list) {
      select_ids.push(temp_list[iii]);
    }
  } else {
    if (typeof(className) != 'undefined') {
      $(className).find('.select_item').each(function () {
        $(this).prop('checked', false);
        for (var i in select_ids) {
          if ($(this).val() === select_ids[i]) {
            select_ids.splice(i, 1);
          }
        }
      });
    } else {
      $('.againAudit_table').find('.select_item').each(function () {
        $(this).prop('checked', false);
        for (var i in select_ids) {
          if ($(this).val() === select_ids[i]) {
            select_ids.splice(i, 1);
          }
        }
      });
    }
  }
}

//  创建复审批次列表全选操作
function againAudit_checkAll(el, id) {
  var temp_list = [];
  var checkbox = $('#'+ id +' .select_item');
  
  if ($(el).is(':checked')) {
    checkbox.each(function () {
      if (!$(this).parents('tr').hasClass('hide')) {
        $(this).prop('checked', true);
        temp_list.push($(this).val());
      }
    });
    
    if (id == 'list_content') {
      for (var i in temp_list) {
        for (var ii in select_ids) {
          if (temp_list[i] === select_ids[ii]) {
            temp_list.splice(i, 1);
          }
        }
      }
      
      for (var iii in temp_list) {
        select_ids.push(temp_list[iii]);
      }
    } else {
      for (var i in temp_list) {
        for (var ii in unselect_ids) {
          if (temp_list[i] === unselect_ids[ii]) {
            temp_list.splice(i, 1);
          }
        }
      }
      
      for (var iii in temp_list) {
        unselect_ids.push(temp_list[iii]);
      }
    }
  } else {
    checkbox.each(function () {
      $(this).prop('checked', false);
      if (id == 'list_content') {
        for (var i in select_ids) {
          if ($(this).val() === select_ids[i]) {
            select_ids.splice(i, 1);
          }
        }
      } else {
        for (var i in unselect_ids) {
          if ($(this).val() === unselect_ids[i]) {
            unselect_ids.splice(i, 1);
          }
        }
      }
    });
  }
}

// 创建复审批次
function create_review_batches() {
  var ids = [];  // 将获得到的id数组转换成字符串（为后台处理）
  var batchName_obj = $('[name=batchName]');  // 批次名称
  var batchNumber_obj = $('[name=batchNumber]');  // 批次编号
  
  // 将已选项添加到最终数组
  $('#selected_content .select_item').each(function () {
    ids.push($(this).val());
  });
  
  // 如果最终数组中无值则弹出提示信息，否则进行提交操作
  if (ids.length <= 0) {
    layer.msg('请至少选择一名专家');
  } else {
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: root_url + '/expertAgainAudit/againAuditList.do',
      data: {
        batchIds: ids
      },
      success: function (data) {
        var index = layer.open({
          title: ['创建专家复审批次'],
          shade: 0.3, //遮罩透明度
          type : 1,
          area : ['360px', '186px'], //宽高
          content : $('#create_review_batches'),
          btn: ['创建复审批次', '取消'],
          yes: function() {
            // 验证批次名称和批次编号是否为空
            if (batchName_obj.val() === '') {
              layer.msg('请填写批次名称');
              return false;
            } else if (batchNumber_obj.val() === '') {
              layer.msg('请填写批次编号');
              return false;
            } else {
              $.ajax({
                type: 'POST',
                dataType: 'json',
                url: root_url + '/expertAgainAudit/createBatch.do',
                data: {
                  ids: ids.join(','),
                  batchName: batchName_obj.val(),
                  batchNumber: batchNumber_obj.val()
                },
                success: function (data) {
                  layer.msg(data.message);
                  batchName_obj.val('');  // 清空批次名称
                  batchNumber_obj.val('');  // 清空批次编号
                  $('#list_content').listConstructor();  // 重新加载未选数据
                  $('#selected_content').listConstructor_t();  // 重新初始化已选分组数据
                  select_ids = [];  // 清空已选数组
                  unselect_ids = [];  // 清空未选数组
                  layer.close(index);  // 关闭弹出框
                },
                error: function (data) {
                  layer.msg(data.message);
                }
              });
            }
          },
          btn2: function() { // 取消操作
            batchName_obj.val('');
            batchNumber_obj.val('');
            layer.close(index);
          }
        });
      }
    });
  }
}

// 创建新分组
function add_batch() {
  if (select_ids.length > 0) {
    var ids = select_ids.join(',');
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: root_url + '/expertAgainAudit/expertGrouping.do',
      data:{
        batchId: batch_id,
        ids: ids
      },
      success: function () {
        $('#list_content').listConstructor();
      }
    });
    
    select_ids = [];
  } else {
    layer.msg('请选择专家');
  }
}

// 删除操作
function del_group(el) {
  var group_ids = [];  // 分组id集合
  var str_group_ids = '';
  $(el).parents('.group_batch_list').find('.select_item').each(function () {
    if ($(this).is(':checked')) {
      group_ids.push($(this).val());
    }
  });
  
  if (group_ids.length > 0) {
    str_group_ids = group_ids.join(',');
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: root_url + '/expertAgainAudit/delExpertGroupDetails.do',
      data: {
        ids: str_group_ids
      },
      success: function (data) {
        if (data.status) {
          $('#list_content').listConstructor();
        } else {
          layer.msg(data.message);
        }
      }
    });
  } else {
    layer.msg('请选择专家');
  }
}

// 添加至已有分组
function show_hasGroud() {
  if (select_ids.length > 0) {
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: root_url + '/expertAgainAudit/getGroups.do',
      data:{
        batchId: getUrlParam('batchId')
      },
      success: function (data) {
        var list_content = data.object;
        var str = '';
        var ids = select_ids.join(',');
        for (var i in list_content) {
          if (i === '0') {
            str += '<tr>'
                  +'  <td class="w50 text-center"><input name="groupId" type="radio" checked="checked" value="'+ list_content[i].groupId +'" class="select_item"></td>'
                  +'  <td class="text-center">'+ list_content[i].groupName +'</td>'
                  +'</tr>';
          } else {
            str += '<tr>'
                  +'  <td class="w50 text-center"><input name="groupId" type="radio" value="'+ list_content[i].groupId +'" class="select_item"></td>'
                  +'  <td class="text-center">'+ list_content[i].groupName +'</td>'
                  +'</tr>';
          }
        }
        
        str = '<div class="p20"><table class="table table-bordered table-hover table-striped m0">'
            +'   <thead><tr><th>选择</th><th>审核组</th></tr></thead>'
            +'   <tbody>'+ str +'</tbody>'
            +'</table></div>';
        
        $('#group_list').html(str);
        
        var index = layer.open({
          title: ['添加至已有分组'],
          shade: 0.3, //遮罩透明度
          type : 1,
          area : ['300px'], //宽高
          content : $('#group_list'),
          btn: ['确定', '取消'],
          yes: function() {
            $('#group_list').find('.select_item').each(function () {
              if ($(this).is(':checked')) {
                select_groupId = $(this).val();
                return false;
              }
            });
            
            $.ajax({
              type: 'POST',
              dataType: 'json',
              url: root_url + '/expertAgainAudit/expertAddGroup.do',
              data: {
                groupId: select_groupId,
                ids: ids
              },
              success: function (data) {
                layer.msg(data.message);
                select_ids = [];
                select_groupId = '';
                $('#list_content').listConstructor();
                layer.close(index);
              }
            });
          },
          btn2: function() {
            select_groupId = '';
            layer.close(index);
          }
        });
      }
    });
  } else {
    layer.msg('请选择专家');
  }
}

// 批次分组完成校验
function finish_groupBatch() {
  $.ajax({
    type: 'POST',
    dataType: 'json',
    url: root_url + '/expertAgainAudit/checkComplete.do',
    data: {
      batchId: getUrlParam('batchId')
    },
    success: function (data) {
      layer.msg(data.message);
      window.history.back();
    },
    error: function (data) {
      layer.msg(data.message);
    }
  });
}

// 批次分组取消操作
function cancel_groupBatch() {
  var group_ids = [];
  var str_group_ids = '';
  $('.groupBatch_table .select_item').each(function () {
    group_ids.push($(this).val());
  });
  
  if (group_ids.length > 0) {
    str_group_ids = group_ids.join(',');
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: root_url + '/expertAgainAudit/deleteExpertReviewTeam.do',
      data: {
        ids: str_group_ids
      },
      success: function (data) {
        $('#list_content').listConstructor();
      }
    });
  }
  
  history.back();
}

// 添加审核组成员
function add_members() {
  var index = parseInt($('#list_content tr').length);
  list[index] = {
    'groupId': getUrlParam('groupId'),
    'loginName': '',
    'relName': '',
    'orgName': '',
    'duties': '',
    'passWord': ''
  };
  $('#list_content').append('<tr>'
    +'<td class="text-center"><input name="id" type="checkbox" value="none" class="select_item"></td>'
    +'<td class="text-center"><input type="text" name="loginName" class="form-control w100p border0 m0" onblur="checkOnly(this)" placeholder="由6-20个字母或数字组成"></td>'
    +'<td class="text-center"><input type="text" name="relName" class="form-control w100p border0 m0"></td>'
    +'<td class="text-center"><input type="text" name="orgName" class="form-control w100p border0 m0"></td>'
    +'<td class="text-center"><input type="text" name="duties" class="form-control w100p border0 m0"></td>'
    +'<td class="text-center"></td>'
  +'</tr>');
}

// 删除审核组成员
function del_members() {
  $('#list_content tr').each(function (index) {
    if ($(this).find('.select_item').is(':checked')) {
      list.splice(index, 1);
      $(this).remove();
    }
  });
}

// 设置密码
function set_password() {
  var password = $('input[name=password]');  // 新密码
  var password2 = $('input[name=password2]');  // 确认新密码
  
  var index = layer.open({
    title: ['设置新密码'],
    shade: 0.3, //遮罩透明度
    type : 1,
    area : ['300px'], //宽高
    content : $('#modal_setPwd'),
    btn: ['确定', '取消'],
    yes: function() {
      if (password.val() != password2.val()) {
        layer.msg('请确认两次密码一致！');
        password2.val('').focus();
        return false;
      } else {
        $.ajax({
          type: 'POST',
          dataType: 'json',
          url: root_url + '/expertAgainAudit/setUpPassword.do',
          data: {
            groupId: getUrlParam('groupId'),
            password: password.val(),
            password2: password2.val()
          },
          success: function (data) {
            if (data.status) {
              layer.msg(data.message, {
                time: 1000
              }, function() {
                password.val('');
                password2.val('');
                layer.close(index);
                location.reload();
              });
            } else {
              layer.msg(data.message);
              return false;
            }
          }
        });
      }
    },
    btn2: function() {
      password.val('');
      password2.val('');
      layer.close(index);
    }
  });
}

// 结束审核组成员配置
function save_editMembers() {
  var empty_sum = 0;
  var loginName = Trim($('input[name=loginName]').val(), 'g');
  var password = Trim($('input[name=password]').val(), 'g');
  
  if (loginName === '') {
    layer.msg('用户名不能为空', {
      time: 1000
    });
  } else {
    $('#list_content tr').each(function (index) {
      var relName = Trim($(this).find('input[name=relName]').val(), 'g');
      var orgName = Trim($(this).find('input[name=orgName]').val(), 'g');
      var duties = Trim($(this).find('input[name=duties]').val(), 'g');
      
      if (relName === '') {
        layer.msg('专家姓名不能为空', {
          time: 1000
        });
        empty_sum = 1;
        return false;
      } else if (relName === '') {
        layer.msg('专家姓名不能为空', {
          time: 1000
        });
        empty_sum = 1;
        return false;
      } else if (orgName === '') {
        layer.msg('单位不能为空', {
          time: 1000
        });
        empty_sum = 1;
        return false;
      } else if (duties === '') {
        layer.msg('职务不能为空', {
          time: 1000
        });
        empty_sum = 1;
        return false;
      }
      list[index] = {'indexNum': '', 'groupId': '', 'relName': '', 'orgName': '', 'duties': ''};
      list[index].indexNum = index;
      list[index].groupId = getUrlParam('groupId');
      list[index].relName = relName;
      list[index].orgName = orgName;
      list[index].duties = duties;
    });
    
    if (empty_sum === 0) {
      layer.confirm('您确定要保存么？', {
        btn: ['确定', '取消']
      }, function () {
        $.ajax({
          type: 'POST',
          dataType: 'json',
          url: root_url + '/expertAgainAudit/addExpertReviewTeam.do',
          data: {
            userName: loginName,
            password: password,
            list: list
          },
          success: function (data) {
            layer.msg(data.message, {
              time: 1000
            }, function () {
              location.reload();
            });
          }
        });
      });
    }
  }
}

// 检查用户名唯一性
function checkOnly(el) {
  var loginname = $('[name=loginName]');
  
  $.ajax({
    type: 'POST',
    dataType: 'json',
    url: root_url + '/expertAgainAudit/checkLoginName.do',
    data: {
      loginName: loginname.val()
    },
    success: function (data) {
      if (!data.status) {
        loginname.val('');
        layer.msg(data.message);
      }
    }
  });
}

// 专家批次复审
function expert_auditBatch(expertId) {
  var win = window.open();
  $.ajax({
    type: 'POST',
    dataType: 'json',
    url: root_url + '/expertAgainAudit/checkGroupStatus.do',
    data: {
      expertId: expertId
    },
    success: function (data) {
      if (data.status) {
        win.location = root_url + "/expertAudit/basicInfo.html?expertId="+ expertId +"&sign=2";
      } else {
        layer.msg(data.message);
      }
    },
    error: function (data) {
      win.close();
      layer.msg(data.status + '错误');
    }
  });
}

//  创建复审批次列表反选操作
function againAudit_reverseSelection(id) {
  var select_num = 0;  // 选中总数
  
  // 防止传参错误
  $('#' + id + ' .select_item').each(function () {
    if (id == 'list_content') {
      if (!$(this).parents('tr').hasClass('hide')) {
        if (!$(this).is(':checked')) {
          $(this).prop('checked', true);
          select_ids.push($(this).val());
          select_num++;
        } else {
          for (var i in select_ids) {
            if (select_ids[i] == $(this).val()) {
              select_ids.splice(i, 1);
              break;
            }
          }
          $(this).prop('checked', false);
        }
      }
    } else {
      if (!$(this).parents('tr').hasClass('hide')) {
        if (!$(this).is(':checked')) {
          $(this).prop('checked', true);
          unselect_ids.push($(this).val());
          select_num++;
        } else {
          for (var i in unselect_ids) {
            if (unselect_ids[i] == $(this).val()) {
              unselect_ids.splice(i, 1);
              break;
            }
          }
          $(this).prop('checked', false);
        }
      }
    }
  });
  
  if (select_num === $('#' + id + ' .select_item').length) {
    if (id == 'list_content') {
      $('.unselected_checkAll').prop('checked', true);
    } else {
      $('.selected_checkAll').prop('checked', true);
    }
  } else {
    if (id == 'list_content') {
      $('.unselected_checkAll').prop('checked', false);
    } else {
      $('.selected_checkAll').prop('checked', false);
    }
  }
}

// 自动分组
function auto_group() {
  var count = $('#autoGroup_num').val();
  $.ajax({
    type: 'POST',
    dataType: 'json',
    url: root_url + '/expertAgainAudit/automaticGrouping.do',
    data:{
      batchId: batch_id,
      count: count
    },
    success: function (data) {
      layer.msg(data.message);
      $('#list_content').listConstructor();
    }
  });
}

//  跳转到批次分组
function jump_batchGroup() {
  $.ajax({
    type: 'POST',
    dataType: 'json',
    url: root_url + '/expertAgainAudit/findBatchDetails.do',
    data:{
      batchId: getUrlParam('batchId'),
      status: '14'
    },
    success: function (data) {
      if (data.status) {
        window.location.href = root_url + '/expertAgainAudit/groupBatch.html?batchId=' + getUrlParam('batchId');
      } else {
        layer.msg(data.message);
      }
    }
  });
}

// 显示/隐藏批次列表
function toggle_list(el) {
  var show_ob = $(el).parents('.gbl_tit').siblings('.gbl_content');
  if (show_ob.hasClass('hide')) {
    show_ob.removeClass('hide');
    $(el).removeClass('shrink');
    $(el).addClass('spread');
  } else {
    show_ob.addClass('hide');
    $(el).addClass('shrink');
    $(el).removeClass('spread');
  }
}

// 导入历史人员
function import_history(el) {
  var index = layer.open({
    title: ['导入历史人员'],
    shade: 0.3, //遮罩透明度
    type : 1,
    area : ['700px', '400px'], //宽高
    content : $('#import_history'),
    btn: ['确定', '返回'],
    success: function () {
      var index_load = layer.load();
      $.ajax({
        type: 'POST',
        dataType: 'json',
        url: root_url + '/expertAgainAudit/selectReviewTeamAll.do',
        data: {},
        success: function (data) {
          var list_content = data.object;
          var str = '';
          
          for (var i in list_content) {
            if (list_content[i] != null) {
              str += '<tr>'
                +'<td class="tc break-all"><input type="hidden" value="'+ list_content[i].relName +', '+ list_content[i].orgName +', '+ list_content[i].duties +'"><input type="radio" name="history" class="select_item"></td>'
                +'<td class="tc break-all">'+ (parseInt(i) + 1) +'</td>'
                +'<td class="tc break-all">'+ list_content[i].relName +'</td>'
                +'<td class="tc break-all">'+ list_content[i].orgName +'</td>'
                +'<td class="tc break-all">'+ list_content[i].duties +'</td>'
              +'</tr>';
            }
          }
          
          $('#history_content').html(str);
          layer.close(index_load);
        }
      });
    },
    yes: function() {
      if ($('#history_content input[type=radio]:checked').length > 0) {
        var val = [];
        var has_num = 0;
        
        val = $('#history_content input[type=radio]:checked').siblings('input[type=hidden]').val().split(',');
        
        $('#list_content tr').each(function (index) {
          if (Trim($(this).find('td').eq(0).find('input').val(), 'g') == Trim(val[0], 'g') && Trim($(this).find('td').eq(1).find('input').val(), 'g') == Trim(val[1], 'g') && Trim($(this).find('td').eq(2).find('input').val(), 'g') == Trim(val[2], 'g')) {
            has_num = 1;
          }
        });
        
        if (has_num == 0) {
          $(el).parents('tr').find('input[name=relName]').val(Trim(val[0], 'g'));
          $(el).parents('tr').find('input[name=orgName]').val(Trim(val[1], 'g'));
          $(el).parents('tr').find('input[name=duties]').val(Trim(val[2], 'g'));
          
          layer.close(index);
        } else {
          layer.msg('您选择的人员已存在，请重新选择');
        }
      }
    },
    btn2: function() {
      layer.close(index);
    }
  });
}

// 添加到已选分组
function addto_selected() {
  $.ajax({
    type: 'POST',
    dataType: 'json',
    url: root_url + '/expertAgainAudit/addBatchTemporary.do',
    data: {
      ids: select_ids.join(',')
    },
    success: function (data) {
      $('#list_content').listConstructor();  // 重新加载未选数据
      $('#selected_content').listConstructor_t();  // 重新初始化已选分组数据
      $('.unselected_checkAll').prop('checked', false);  // 清除多选选中状态
      select_ids = [];  // 清空已选数组
      unselect_ids = [];  // 清空未选数组
      $('#selected_tab li').eq(1).find('a').tab('show');  // 打开已选分组标签
      select_total();  // 统计专家人数总数
      layer.msg(data.message);
    }
  });
}

// 移除已选分组
function remove_selected() {
  $.ajax({
    type: 'POST',
    dataType: 'json',
    url: root_url + '/expertAgainAudit/deleteBatchTemporary.do',
    data: {
      ids: unselect_ids.join(',')
    },
    success: function (data) {
      $('#list_content').listConstructor();  // 重新加载未选数据
      $('#selected_content').listConstructor_t();  // 重新初始化已选分组数据
      $('.selected_checkAll').prop('checked', false);  // 清除多选选中状态
      select_ids = [];  // 清空已选数组
      unselect_ids = [];  // 清空未选数组
      select_total();  // 统计专家人数总数
      layer.msg(data.message);
    }
  });
}

// 专家总和统计
function select_total() {
  $('#select_expertTotal').html($('#selected_content tr').length);
  $('#unselect_expertTotal').html(parseInt($('#list_content tr').length) - parseInt($('#list_content tr.hide').length));
}

// 未选序号排序
function unselected_sort() {
  var sort = 1;
  $('#list_content tr').each(function () {
    if (!$(this).hasClass('hide')) {
      $(this).find('td').eq(1).html(sort);
      sort++;
    }
  });
}

// 生效
function takeEffect() {
  $.ajax({
    type: 'POST',
    dataType: 'json',
    url: root_url + '/expertAgainAudit/takeEffect.do',
    data: {
      batchId: getUrlParam('batchId')
    },
    success: function (data) {
      $('#table_content').listConstructor({
        data: {
          batchId: getUrlParam('batchId')
        }
      });
      layer.msg(data.message);
    }
  });
}

// 重新复审
function reexamination(expertId) {
  $.ajax({
    type: 'POST',
    dataType: 'json',
    url: root_url + '/expertAgainAudit/againReview.do',
    data: {
      id: expertId
    },
    success: function (data) {
      $('#table_content').listConstructor({
        data: {
          batchId: getUrlParam('batchId')
        }
      });
      layer.msg(data.message);
    }
  });
}

// 取消重新复审
function cancel_reexamination(expertId) {
  $.ajax({
    type: 'POST',
    dataType: 'json',
    url: root_url + '/expertAgainAudit/cancelReview.do',
    data: {
      id: expertId
    },
    success: function (data) {
      $('#table_content').listConstructor({
        data: {
          batchId: getUrlParam('batchId')
        }
      });
      layer.msg(data.message);
    }
  });
}

// 跳转批次审核
function jump_auditBatch() {
  window.location.href = root_url + '/expertAgainAudit/auditBatch.html?batchId=' + getUrlParam('batchId');
}

// 下载
function downloadTable(id) {
  var state = $("#" + id + "").parent("tr").find("td").eq(10).text();
  state = Trim(state, 'g');
  if(state == "预复审结束") {
    $.ajax({
      url: root_url + "/expertAudit/findExpertInfo.do",
      data: {
        id: id
      },
      type: "post",
      success: function(data) {
        if(data.isReviewEnd != 1){
          $("input[name='tableType']").val('2');
          $("input[name='expertId']").val(id);
          $("#form_id").attr("action", root_url + "/expertAudit/download.html");
          $("#form_id").submit();
        } else {
          layer.msg("该专家已复审结束，请刷新页面 !");
        }
      }
    });
  } else {
    layer.msg("请选择预复审结束的专家 !");
  }
}

// 下载复审统计表
function downloadReviewTable() {
  $("input[name='batchId']").val(getUrlParam('batchId'));
  $("#form_expertReview").attr("action", root_url + "/expertAgainAudit/downloadExpertReview.html");
  $("#form_expertReview").submit();
}

// 复审结束（审核专家操作）
function reviewEnd(expertId) {
  $.ajax({
    url: root_url + "/expertAgainAudit/reviewEnd.do",
    data: {
      expertId: expertId
    },
    success: function(data) {
      if(data.status == 200) {
        layer.msg("操作成功");
        $('#table_content').listConstructor();
      }
    },
    error: function() {
      layer.msg("操作失败");
    }
  });
}

// 批次详情页查看
function viewDetails(expertId) {
  window.open(root_url + "/expertAudit/basicInfo.html?expertId="+ expertId +"&sign=2&isCheck=yes");
}

// 配置审核组成员
function jump_members(groupId) {
  $.ajax({
    type: 'POST',
    dataType: 'json',
    url: root_url + '/expertAgainAudit/findExpertReviewTeam.do',
    data: {
      groupId: groupId
    },
    success: function (data) {
      window.location.href = root_url + '/expertAgainAudit/editMembers.html?groupId='+ groupId +'';
    }
  });
}