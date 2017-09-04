// 创建复审批次
function create_review_batches() {
  var ids = select_ids.join(',');  // 将获得到的id数组转换成字符串（为后台处理）
  var batchName_obj = $('[name=batchName]');  // 批次名称
  var batchNumber_obj = $('[name=batchNumber]');  // 批次编号
  
  if (ids === '') {
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
        var str = '';
        var list_content = data.object.list;
        for (var i in list_content) {
          if (typeof(list_content[i].relName) === 'undefined') {
            list_content[i].relName = '';
          }
          if (typeof(list_content[i].professTechTitles) === 'undefined') {
            list_content[i].professTechTitles = '';
          }
          if (typeof(list_content[i].updateTime) === 'undefined') {
            list_content[i].updateTime = '';
          }
          str += '<tr>'
            +'<td class="text-center">'+ (parseInt(i) + 1) +'</td>'
            +'<td class="text-center">'+ list_content[i].relName +'</td>'
            +'<td class="text-center">'+ list_content[i].professTechTitles +'</td>'
            +'<td class="text-center">'+ list_content[i].updateTime +'</td>'
          +'</tr>';
        }
        $('#crb_content').html(str);
        var index = layer.open({
          title: ['创建专家复审批次'],
          shade: 0.3, //遮罩透明度
          type : 1,
          area : ['700px', '400px'], //宽高
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
                  ids: ids,
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
        init_list(list_url, newGroup_url);
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

// 获取地址栏参数
function getUrlParam(name) {
  var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
  var r = window.location.search.substr(1).match(reg);
  if (r!=null) return unescape(r[2]); return null;
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
  $('#list_content').append('<tr>'
    +'<td class="text-center"><input name="id" type="checkbox" value="none" class="select_item"></td>'
    +'<td class="text-center"><input type="text" name="loginName" class="form-control w100p border0 m0" onblur="checkOnly(this)"></td>'
    +'<td class="text-center"><input type="text" name="relName" class="form-control w100p border0 m0"></td>'
    +'<td class="text-center"><input type="text" name="orgName" class="form-control w100p border0 m0"></td>'
    +'<td class="text-center"><input type="text" name="duties" class="form-control w100p border0 m0"></td>'
    +'<td class="text-center"></td>'
  +'</tr>');
  
  // 弹窗方法
  // function reset() {
  //   loginName.val('');
  //   relName.val('');
  //   orgName.val('');
  //   duties.val('');
  //   $('#list_content').listConstructor({
  //     url: list_url,
  //     data: {
  //       groupId: getUrlParam('groupId')
  //     }
  //   });
  //   $('input[name=loginName]').unbind();
  //   $('input[name=loginName]').removeAttr('style');
  //   $('input[name=loginName]').prop('placeholder', '请输入用户名');
  // }
  
  // var index = layer.open({
  //   title: ['添加审核组成员'],
  //   shade: 0.3, //遮罩透明度
  //   type : 1,
  //   area : ['400px'], //宽高
  //   content : $('#modal_addMembers'),
  //   btn: ['确定', '取消'],
  //   yes: function() {
  //     if (loginName.val() === '') {
  //       layer.msg('请填写用户名', {
  //         offset: '100px'
  //       });
  //       return false;
  //     } else if (is_only === 0) {
  //       layer.msg('此用户名已注册，请换一个', {
  //         offset: '100px'
  //       });
  //       return false;
  //     } else if (relName.val() === '') {
  //       layer.msg('请填写专家姓名', {
  //         offset: '100px'
  //       });
  //       return false;
  //     } else if (orgName.val() === '') {
  //       layer.msg('请填写单位', {
  //         offset: '100px'
  //       });
  //       return false;
  //     } else if (duties.val() === '') {
  //       layer.msg('请填写职务', {
  //         offset: '100px'
  //       });
  //       return false;
  //     } else {
  //       $.ajax({
  //         type: 'POST',
  //         dataType: 'json',
  //         url: add_url,
  //         data: {
  //           groupId: getUrlParam('groupId'),
  //           loginName: loginName.val(),
  //           relName: relName.val(),
  //           orgName: orgName.val(),
  //           duties: duties.val()
  //         },
  //         success: function (data) {
  //           layer.msg(data.message, {
  //             offset: '100px'
  //           });
  //           reset();
  //           layer.close(index);
  //         },
  //         error: function (data) {
  //           layer.msg(data.message, {
  //             offset: '100px'
  //           });
  //         }
  //       });
  //     }
  //   },
  //   btn2: function() {
  //     reset();
  //     layer.close(index);
  //   }
  // });
  
  // layer.ready(function () {
  //   $('input[name=loginName]').bind('blur', function () {
  //     var _this = $(this);
  //     
  //     if (loginName != '') {
  //       $.ajax({
  //         type: 'POST',
  //         dataType: 'json',
  //         url: usernameOnly_url,
  //         data: {
  //           loginName: _this.val()
  //         },
  //         success: function (data) {
  //           if (data.status) {
  //             _this.removeAttr('style');
  //             _this.prop('placeholder', '请输入用户名');
  //             is_only = 1;
  //           } else {
  //             if (data.message != '用户名不能为空') {
  //               _this.val('');
  //               _this.css({
  //                 borderColor: '#FF0000'
  //               });
  //               _this.prop('placeholder', data.message);
  //             }
  //             is_only = 0;
  //           }
  //         },
  //         error: function (data) {
  //           layer.msg(data.message, {
  //             offset: '100px'
  //           });
  //         }
  //       });
  //     }
  //   });
  // });
}

// 删除审核组成员
function del_members() {
  var ids = select_ids.join(',');
  $.ajax({
    type: 'POST',
    dataType: 'json',
    url: del_url,
    data: {
      ids: ids
    },
    success: function (data) {
      layer.msg(data.message, {
        offset: '100px'
      });
      $('#list_content').listConstructor({
        url: list_url,
        data: {
          groupId: getUrlParam('groupId')
        }
      });
    },
    error: function (data) {
      layer.msg(data.message, {
        offset: '100px'
      });
    }
  });
}

// 设置密码
function set_password() {
  var ids = select_ids.join(',');
  var password = $('input[name=password]');  // 新密码
  var password2 = $('input[name=password2]');  // 确认新密码
  
  if (ids === '') {
    layer.msg('请至少选择一名专家', {
      offset: '100px'
    });
  } else {
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
              ids: ids,
              password: password.val(),
              password2: password2.val()
            },
            success: function (data) {
              if (data.status) {
                layer.msg(data.message, {
                  offset: '100px'
                });
                for (var i in select_ids) {
                  $('#list_content .select_item').each(function () {
                    if (select_ids[i] === $(this).val()) {
                      $(this).prop('checked', false);
                      return false;
                    }
                  });
                }
                select_ids = [];
                password.val('');
                password2.val('');
                layer.close(index);
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
}

// 结束审核组成员配置
function save_editMembers() {
  var list = [];  // 储存列表
  
  $('#list_content tr').each(function (index) {
    if ($(this).find('input[name=loginName]').val() === '') {
      $(this).find('input[name=loginName]').blur();
      layer.msg('用户名不能为空', {
        offset: '100px',
        time: 1000
      });
      return false;
    } else if ($(this).find('input[name=relName]').val() === '') {
      $(this).find('input[name=relName]').blur();
      layer.msg('专家姓名不能为空', {
        offset: '100px',
        time: 1000
      });
      return false;
    } else if ($(this).find('input[name=orgName]').val() === '') {
      $(this).find('input[name=orgName]').blur();
      layer.msg('单位不能为空', {
        offset: '100px',
        time: 1000
      });
      return false;
    } else if ($(this).find('input[name=duties]').val() === '') {
      $(this).find('input[name=duties]').blur();
      layer.msg('职务不能为空', {
        offset: '100px',
        time: 1000
      });
      return false;
    }
    
    list[index] = {'groupId': getUrlParam('groupId'), 'loginName': $(this).find('input[name=loginName]').val(), 'relName': $(this).find('input[name=relName]').val(), 'orgName': $(this).find('input[name=orgName]').val(), 'duties': $(this).find('input[name=duties]').val()};
  });
  
  $.ajax({
    type: 'POST',
    dataType: 'json',
    url: add_url,
    data: {
      list: list
    },
    success: function (data) {
      layer.msg(data.message, {
        offset: '100px'
      });
      layer.close(index);
    },
    error: function (data) {
      layer.msg(data.message, {
        offset: '100px'
      });
    }
  });
  
  // 弹窗方法
  // $.ajax({
  //   type: 'POST',
  //   dataType: 'json',
  //   url: save_url,
  //   data: {
  //     groupId: getUrlParam('groupId')
  //   },
  //   success: function (data) {
  //     layer.msg(data.message, {
  //       offset: '100px'
  //     });
  //   }
  // });
}

// 检查用户名唯一性
function checkOnly(el) {
  if ($(el) != '') {
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: usernameOnly_url,
      data: {
        loginName: $(el).val()
      },
      success: function (data) {
        if (data.status) {
          $(el).removeAttr('style');
          $(el).prop('placeholder', '请输入用户名');
          is_only = 1;
        } else {
          if (data.message != '用户名不能为空') {
            $(el).val('');
            layer.msg(data.message, {
              offset: '100px'
            });
          }
          is_only = 0;
        }
      },
      error: function (data) {
        layer.msg(data.message, {
          offset: '100px'
        });
      }
    });
  }
}

// 专家批次审核
function expert_auditBatch(url) {
  if (select_ids.length > 1) {
    layer.msg('不能多选，请选择一项', {
      offset: '100px'
    });
  } else if (select_ids.length === 0) {
    layer.msg('请至少选择一项', {
      offset: '100px'
    });
  } else {
    var ids = select_ids.toString();
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: audit_url,
      data: {
        expertId: ids
      },
      success: function (data) {
        if (data.status) {
          window.location.href=url+"/expertAudit/basicInfo.html?expertId="+ids+"&sign=2";
        } else {
          layer.msg(data.message, {
            offset: '100px'
          });
        }
      }
    });
  }
}

//  全选操作
function checkAll(el) {
  var temp_list = [];
  
  if ($(el).is(':checked')) {
    $(el).parents('table').find('.select_item').each(function () {
      $(this).prop('checked', true);
      temp_list.push($(this).val());
    });
    
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
    $(el).parents('table').find('.select_item').each(function () {
      $(this).prop('checked', false);
      for (var i in select_ids) {
        if ($(this).val() === select_ids[i]) {
          select_ids.splice(i, 1);
        }
      }
    });
  }
}