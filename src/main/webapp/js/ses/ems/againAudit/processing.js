// 获取地址栏参数
function getUrlParam(name) {
  var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
  var r = window.location.search.substr(1).match(reg);
  if (r!=null) return unescape(r[2]); return null;
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
  
  $('#selected_content .select_item').each(function () {
    ids.push($(this).val());
  });
  
  if (ids.length <= 0) {
    layer.msg('请至少选择一名专家', {
      offset: '100px'
    });
  } else {
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: list_url,
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
            if (batchName_obj.val() === '') {
              layer.msg('请填写批次名称', {
                offset: '100px'
              });
              return false;
            } else if (batchNumber_obj.val() === '') {
              layer.msg('请填写批次编号', {
                offset: '100px'
              });
              return false;
            } else {
              $.ajax({
                type: 'POST',
                dataType: 'json',
                url: batch_url,
                data: {
                  ids: ids.join(','),
                  batchName: batchName_obj.val(),
                  batchNumber: batchNumber_obj.val()
                },
                success: function (data) {
                  layer.msg(data.message, {
                    offset: '100px'
                  });
                  batchName_obj.val('');
                  batchNumber_obj.val('');
                  $('#list_content').listConstructor({
                    url: list_url
                  });
                  $('#selected_content').listConstructor_t({
                    url: temporary_init_url
                  });
                  select_ids = [];
                  unselect_ids = [];
                  layer.close(index);
                },
                error: function (data) {
                  layer.msg(data.message, {
                    offset: '100px'
                  });
                }
              });
            }
          },
          btn2: function() {
            batchName_obj.val('');
            batchNumber_obj.val('');
            layer.close(index);
          }
        });
      }
    });
  }
}

// 初始化专家列表和批次列表
function init_list(list_url, newGroup_url) {
  $('#list_content').listConstructor({
    url: list_url,
    newGroup_url: newGroup_url,
    data: {
      batchId: getUrlParam('batchId'),
      status: '14'
    },
    data_new: {
      batchId: getUrlParam('batchId')
    }
  });
}

// 创建新分组
function add_batch() {
  if (select_ids.length > 0) {
    var ids = select_ids.join(',');
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: add_url,
      data:{
        batchId: batch_id,
        ids: ids
      },
      success: function () {
        init_list(list_url, newGroup_url);
      }
    });
    
    select_ids = [];
  } else {
    layer.msg('请选择专家', {
      offset: '100px'
    });
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
      url: del_url,
      data: {
        ids: str_group_ids
      },
      success: function (data) {
        if (data.status) {
          init_list(list_url, newGroup_url);
        } else {
          layer.msg(data.message, {
            offset: '100px'
          });
        }
      }
    });
  } else {
    layer.msg('请选择专家', {
      offset: '100px'
    });
  }
}

// 添加至已有分组
function show_hasGroud() {
  if (select_ids.length > 0) {
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: getGroup_url,
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
              url: addGroup_url,
              data: {
                groupId: select_groupId,
                ids: ids
              },
              success: function (data) {
                layer.msg(data.message, {
                  offset: '100px'
                });
                select_ids = [];
                select_groupId = '';
                init_list(list_url, newGroup_url);
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
    layer.msg('请选择专家', {
      offset: '100px'
    });
  }
}

// 批次分组完成校验
function finish_groupBatch() {
  $.ajax({
    type: 'POST',
    dataType: 'json',
    url: finish_url,
    data: {
      batchId: getUrlParam('batchId')
    },
    success: function (data) {
      layer.msg(data.message, {
        offset: '100px'
      });
      window.history.back();
    },
    error: function (data) {
      layer.msg(data.message, {
        offset: '100px'
      });
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
      url: del_url,
      data: {
        ids: str_group_ids
      },
      success: function (data) {
        init_list(list_url, newGroup_url);
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
        layer.msg('请确认两次密码一致！', {
          offset: '100px'
        });
        password2.val('').focus();
        return false;
      } else {
        $.ajax({
          type: 'POST',
          dataType: 'json',
          url: setPwd_url,
          data: {
            groupId: getUrlParam('groupId'),
            password: password.val(),
            password2: password2.val()
          },
          success: function (data) {
            if (data.status) {
              layer.msg(data.message, {
                offset: '100px',
                time: 1000
              }, function() {
                password.val('');
                password2.val('');
                layer.close(index);
                location.reload();
              });
            } else {
              layer.msg(data.message, {
                offset: '100px'
              });
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
      offset: '100px',
      time: 1000
    });
  } else {
    $('#list_content tr').each(function (index) {
      var relName = Trim($(this).find('input[name=relName]').val(), 'g');
      var orgName = Trim($(this).find('input[name=orgName]').val(), 'g');
      var duties = Trim($(this).find('input[name=duties]').val(), 'g');
      
      if (relName === '') {
        layer.msg('专家姓名不能为空', {
          offset: '100px',
          time: 1000
        });
        empty_sum = 1;
        return false;
      } else if (relName === '') {
        layer.msg('专家姓名不能为空', {
          offset: '100px',
          time: 1000
        });
        empty_sum = 1;
        return false;
      } else if (orgName === '') {
        layer.msg('单位不能为空', {
          offset: '100px',
          time: 1000
        });
        empty_sum = 1;
        return false;
      } else if (duties === '') {
        layer.msg('职务不能为空', {
          offset: '100px',
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
          url: add_url,
          data: {
            userName: loginName,
            password: password,
            list: list
          },
          success: function (data) {
            layer.msg(data.message, {
              offset: '100px',
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
    url: usernameOnly_url,
    data: {
      loginName: loginname.val()
    },
    success: function (data) {
      if (!data.status) {
        loginname.val('');
        layer.msg(data.message, {
          offset: '100px'
        });
      }
    }
  });
}

// 专家批次复审
function expert_auditBatch(url, expertId) {
  var win = window.open();
  
  $.ajax({
    type: 'POST',
    dataType: 'json',
    url: audit_url,
    data: {
      expertId: expertId
    },
    success: function (data) {
      if (data.status) {
        win.location = url + "/expertAudit/basicInfo.html?expertId="+expertId+"&sign=2";
      } else {
        layer.msg(data.message, {
          offset: '100px'
        });
      }
    }
  });
}

//  创建复审批次列表反选操作
function againAudit_reverseSelection(id) {
  var select_num = 0;
  
  if (typeof(id) == 'string') {
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
      $('#' + id).siblings('thead').find('[name=checkAll]').prop('checked', true);
    } else {
      $('#' + id).siblings('thead').find('[name=checkAll]').prop('checked', false);
    }
  }
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

// 自动分组
function auto_group() {
  var count = $('#autoGroup_num').val();
  $.ajax({
    type: 'POST',
    dataType: 'json',
    url: autoGroup_url,
    data:{
      batchId: batch_id,
      count: count
    },
    success: function (data) {
      layer.msg(data.message, {
        offset: '100px'
      });
      init_list(list_url, newGroup_url);
    }
  });
}

//  跳转到批次分组
function jump_batchGroup() {
  $.ajax({
    type: 'POST',
    dataType: 'json',
    url: list_url,
    data:{
      batchId: getUrlParam('batchId'),
      status: '14'
    },
    success: function (data) {
      if (data.status) {
        window.location.href = jump_auditBatch_url;
      } else {
        layer.msg(data.message, {
          offset: '100px'
        });
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
function import_history() {
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
        url: history_url,
        data: {},
        success: function (data) {
          var list_content = data.object;
          var str = '';
          
          for (var i in list_content) {
            if (list_content[i] != null) {
              str += '<tr>'
                +'<td class="tc break-all"><input type="hidden" value="'+ list_content[i].relName +', '+ list_content[i].orgName +', '+ list_content[i].duties +'"><input type="checkbox" class="select_item"></td>'
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
      if ($('#history_content input[type=checkbox]:checked').length <= 2 && $('#history_content input[type=checkbox]:checked').length > 0) {
        var val = [];
        var empty_tr = 0;
        
        $('#history_content input[type=checkbox]:checked').each(function (index) {
          val.push($(this).siblings('input[type=hidden]').val().split(','));
        });
        
        if ($('#history_content input[type=checkbox]:checked').length == 1) {
          $('#list_content tr').each(function (index) {
            if ($(this).find('input[name=relName]').val() == '' && $(this).find('input[name=orgName]').val() == '' && $(this).find('input[name=duties]').val() == '') {
              $(this).find('input[name=relName]').val(val[0][0]);
              $(this).find('input[name=orgName]').val(val[0][1]);
              $(this).find('input[name=duties]').val(val[0][2]);
              return false;
            } else {
              empty_tr++;
            }
            
            if (empty_tr == $('#list_content tr').length) {
              $('#list_content tr').eq(0).find('input[name=relName]').val(val[0][0]);
              $('#list_content tr').eq(0).find('input[name=orgName]').val(val[0][1]);
              $('#list_content tr').eq(0).find('input[name=duties]').val(val[0][2]);
            }
          });
        } else {
          $('#list_content tr').each(function (index) {
            $(this).find('input[name=relName]').val(val[index][0]);
            $(this).find('input[name=orgName]').val(val[index][1]);
            $(this).find('input[name=duties]').val(val[index][2]);
          });
        }
        
        layer.close(index);
      } else if ($('#history_content input[type=checkbox]:checked').length <= 0) {
        layer.msg('最至少选择一个历史人员', {
          offset: '100px'
        });
      } else {
        layer.msg('最多选择两个历史人员', {
          offset: '100px'
        });
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
    url: temporary_url,
    data: {
      ids: select_ids.join(',')
    },
    success: function (data) {
      // 重新初始化已选分组数据
      $('#selected_content').listConstructor_t({
        url: temporary_init_url
      });
      
      // 重新加载未选数据
      $('#list_content').listConstructor({
        url: list_url
      });
      $('.unselected_checkAll').prop('checked', false);
      select_ids = [];
      unselect_ids = [];
      
      $('#selected_tab li').eq(1).find('a').tab('show');  // 打开已选分组标签
      select_total();  // 统计专家人数总数
      layer.msg(data.message, {
        offset: '100px'
      });
    }
  });
}

// 移除已选分组
function remove_selected() {
  $.ajax({
    type: 'POST',
    dataType: 'json',
    url: remove_temporary_url,
    data: {
      ids: unselect_ids.join(',')
    },
    success: function (data) {
      // 重新初始化已选分组数据
      $('#selected_content').listConstructor_t({
        url: temporary_init_url
      });
      $('.selected_checkAll').prop('checked', false);
      select_ids = [];
      unselect_ids = [];
      
      // 重新加载未选数据
      $('#list_content').listConstructor({
        url: list_url
      });
      
      select_total();  // 统计专家人数总数
      layer.msg(data.message, {
        offset: '100px'
      });
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