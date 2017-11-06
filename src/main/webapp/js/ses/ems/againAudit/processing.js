// 创建复审批次
function create_review_batches() {
  var ids = final_ids.join(',');  // 将获得到的id数组转换成字符串（为后台处理）
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
        // var str = '';
        // var list_content = data.object;
        // for (var i in list_content.expertList) {
        //   if (typeof(list_content.expertList[i].relName) === 'undefined') {
        //     list_content.expertList[i].relName = '';
        //   }
        //   if (typeof(list_content.expertList[i].professTechTitles) === 'undefined') {
        //     list_content.expertList[i].professTechTitles = '';
        //   }
        //   if (typeof(list_content.expertList[i].updateTime) === 'undefined') {
        //     list_content.expertList[i].updateTime = '';
        //   }
        //   str += '<tr>'
        //     +'<td class="text-center">'+ (parseInt(i) + 1) +'</td>'
        //     +'<td class="text-center">'+ list_content.expertList[i].relName +'</td>'
        //     +'<td class="text-center">'+ list_content.expertList[i].professTechTitles +'</td>'
        //     +'<td class="text-center">'+ list_content.expertList[i].updateTime +'</td>'
        //   +'</tr>';
        // }
        // $('#crb_content').html(str);
        var index = layer.open({
          title: ['创建专家复审批次'],
          shade: 0.3, //遮罩透明度
          type : 1,
          area : ['320px', '186px'], //宽高
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
                  $('#selected_content').html('');
                  final_ids = [];
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
      } else if (password.val().length < 6) {
        layer.msg('请输入大于6位的密码！', {
          offset: '100px'
        });
        password.val('').focus();
        password2.val('');
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

// 设置新添加用户密码
// function set_newPassword() {
//   var list_index = [];
//   var empty_sum = 0;
//   var password = $('input[name=password]');  // 新密码
//   var password2 = $('input[name=password2]');  // 确认新密码
//   
//   var index = layer.open({
//     title: ['设置新密码'],
//     shade: 0.3, //遮罩透明度
//     type : 1,
//     area : ['300px'], //宽高
//     content : $('#modal_setPwd'),
//     btn: ['确定', '取消'],
//     yes: function() {
//       if (password.val() != password2.val()) {
//         layer.msg('请确认两次密码一致！', {
//           offset: '100px'
//         });
//         password2.val('').focus();
//         return false;
//       } else if (password.val().length < 6) {
//         layer.msg('请输入大于6位的密码！', {
//           offset: '100px'
//         });
//         password.val('').focus();
//         password2.val('');
//         return false;
//       } else {
//         for (var i in list_index) {
//           list[list_index[i]].passWord = password.val();
//           $('#list_content .select_item').each(function (index) {
//             if (list_index[i] === index) {
//               $(this).prop('checked', false);
//             }
//           });
//           $('#list_content tr').eq(list_index[i]).find('td').eq(5).html('已设置密码');
//         }
//         layer.msg('操作成功', {
//           offset: '100px'
//         });
//         password.val('');
//         password2.val('');
//         layer.close(index);
//       }
//     },
//     btn2: function() {
//       password.val('');
//       password2.val('');
//       layer.close(index);
//     }
//   });
// }

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
      list[index] = {'groupId': '', 'relName': '', 'orgName': '', 'duties': ''};
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
  
  // var is_only = 0;
  // var loginname = $('#list_content [name=loginName]');
  // if ($(el).val() != '') {
  //   loginname.each(function (index) {
  //     var _this = $(this);
  //     var _index = index;
  //     loginname.each(function (index) {
  //       if (_index != index) {
  //         if ($(this).val() != '') {
  //           if ($(this).val() === _this.val()) {
  //             $(this).val('');
  //             is_only = 1;
  //             return false;
  //           }
  //         }
  //       }
  //     });
  //   });
  //   if (is_only === 1) {
  //     layer.msg('用户名不能重复', {
  //       offset: '100px'
  //     });
  //   } else {
  //     $.ajax({
  //       type: 'POST',
  //       dataType: 'json',
  //       url: usernameOnly_url,
  //       data: {
  //         loginName: $(el).val()
  //       },
  //       success: function (data) {
  //         if (data.status) {
  //           $(el).removeAttr('style');
  //           $(el).prop('placeholder', '请输入用户名');
  //         } else {
  //           if (data.message != '用户名不能为空') {
  //             $(el).val('');
  //             layer.msg(data.message, {
  //               offset: '100px'
  //             });
  //           }
  //         }
  //       }
  //     });
  //   }
  // }
}

// 专家批次复审
function expert_auditBatch(url, expertId) {
  var batchId = getUrlParam('batchId')
  // if (select_ids.length > 1) {
  //   layer.msg('不能多选，请选择一项', {
  //     offset: '100px'
  //   });
  // } else if (select_ids.length === 0) {
  //   layer.msg('请至少选择一项', {
  //     offset: '100px'
  //   });
  // } else {
    // var ids = select_ids.toString();
    // var state = $("#" + ids + "").parent("tr").find("td").eq(10).text(); //.trim();
    // state = trim(state);
    // if( state =="公示中"  ||state == "复审合格"||state == "专家复审结束"  || state == "复审不合格"|| state == "复审退回修改" || state == "复查合格" || state == "复查未合格"){
    // 	 layer.msg('只能选择未复审完成的专家', {
    // 	      offset: '100px'
    // 	    });
    // 	 return;
    // }
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: audit_url,
      data: {
        expertId: expertId
      },
      success: function (data) {
        if (data.status) {
          window.location.href=url+"/expertAudit/basicInfo.html?expertId="+expertId+"&sign=2"+"&batchId=" + batchId;
        } else {
          layer.msg(data.message, {
            offset: '100px'
          });
        }
      }
    });
  // }
}

//  全选操作
// function checkAll(el) {
//   var temp_list = [];
//   
//   if ($(el).is(':checked')) {
//     $(el).parents('table').find('.select_item').each(function () {
//       $(this).prop('checked', true);
//       temp_list.push($(this).val());
//     });
//     
//     for (var i in temp_list) {
//       for (var ii in select_ids) {
//         if (temp_list[i] === select_ids[ii]) {
//           temp_list.splice(i, 1);
//         }
//       }
//     }
//     
//     for (var iii in temp_list) {
//       select_ids.push(temp_list[iii]);
//     }
//   } else {
//     $(el).parents('table').find('.select_item').each(function () {
//       $(this).prop('checked', false);
//       for (var i in select_ids) {
//         if ($(this).val() === select_ids[i]) {
//           select_ids.splice(i, 1);
//         }
//       }
//     });
//   }
// }

//  全选操作
function checkAll(el, className) {
  var temp_list = [];
  
  if ($(el).is(':checked')) {
    $(className).find('.select_item').each(function () {
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
    $(className).find('.select_item').each(function () {
      $(this).prop('checked', false);
      for (var i in select_ids) {
        if ($(this).val() === select_ids[i]) {
          select_ids.splice(i, 1);
        }
      }
    });
  }
}

//  创建复审批次列表全选操作
function againAudit_checkAll() {
  var temp_list = [];
  
  if ($('.againAudit_table [name=checkAll]').is(':checked')) {
    $('.againAudit_table [name=checkAll]').parents('table').find('.select_item').each(function () {
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
    $('.againAudit_table [name=checkAll]').parents('table').find('.select_item').each(function () {
      $(this).prop('checked', false);
      for (var i in select_ids) {
        if ($(this).val() === select_ids[i]) {
          select_ids.splice(i, 1);
        }
      }
    });
  }
}

//  创建复审批次列表反选操作
function againAudit_reverseSelection(table_name) {
  // var push_list = [];
  // var remove_list = [];
  var select_num = 0;
  
  if (typeof(table_name) == 'string') {
    $(table_name).find('.select_item').each(function () {
      if (!$(this).is(':checked')) {
        // push_list.push($(this).val());
        $(this).prop('checked', true);
        select_num++;
      } else {
        // remove_list.push($(this).val());
        $(this).prop('checked', false);
      }
    });
    
    // for (var i in remove_list) {
    //   for (var ii in select_ids) {
    //     if (select_ids[ii] === remove_list[i]) {
    //       select_ids.splice(ii, 1);
    //     }
    //   }
    // }
    // 
    // for (var iii in push_list) {
    //   var hassame = 0;
    //   for (var iiii in select_ids) {
    //     if (select_ids[iiii] === push_list[iii]) {
    //       hassame = 1;
    //     }
    //   }
    //   if (hassame === 0) {
    //     select_ids.push(push_list[iii]);
    //   }
    // }
    
    if (select_num === $(table_name).find('.select_item').length) {
      $(table_name).find('[name=checkAll]').prop('checked', true);
    } else {
      $(table_name).find('[name=checkAll]').prop('checked', false);
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
  if ($('#list_content .select_item:checked').length > 0) {
    var str = '';
    $('#list_content tr').each(function () {
      var has_num = 0;
      if (!$(this).hasClass('hide')) {
        if ($(this).find('.select_item').is(':checked')) {
          for (var i in final_ids) {
            if ($(this).find('.select_item').val() == final_ids[i]) {
              has_num = 1;
              break;
            }
          }
          if (has_num == 0) {
            final_ids.push($(this).find('.select_item').val());
          }
          str += '<tr>'+ $(this).html() +'</tr>';
          $(this).addClass('hide');
          $(this).find('.select_item').prop('checked', false);
        }
      }
    });
    
    if ($('#list_content tr.hide').length == $('#list_content tr').length) {
      $('#list_content').siblings('thead').find('[name=checkAll]').prop('checked', false);
    }
    
    $('#selected_content').append(str);
    $('#selected_tab li').eq(1).find('a').tab('show');
    
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
        $(this).parents('tbody').find('.select_item').each(function () {
          if ($(this).is(':checked')) {
            sum++;
          }
        });
        
        var checkAll_class = $(this).parents('tbody').siblings('thead').find('[name=checkAll]').attr('class');
        if (sum === $(this).parents('tbody').find('.select_item').length) {
          $('.' + checkAll_class).prop('checked', true);
        } else {
          $('.' + checkAll_class).prop('checked', false);
        }
      });
    }
    
    unselect_total();  // 统计未选专家
    select_total();  // 统计已选专家
  } else {
    layer.msg('至少选择一条数据', {
      offset: '100px'
    });
  }
}

// 移除已选分组
function remove_selected() {
  if ($('#selected_content .select_item:checked').length > 0) {
    var remove_item = [];
    $('#selected_content tr').each(function () {
      if ($(this).find('.select_item').is(':checked')) {
        remove_item.push($(this).find('.select_item').val());
        $(this).remove();
      }
    });
    
    for (var i in remove_item) {
      $('#list_content .select_item').each(function (index) {
        if ($(this).val() == remove_item[i]) {
          $(this).parents('tr').removeClass('hide');
        }
      });
      
      for (var ii in final_ids) {
        if (remove_item[i] == final_ids[ii]) {
          final_ids.splice(ii, 1);
          break;
        }
      }
    }
    
    if ($('#selected_content tr').length <= 0) {
      $('#selected_content').siblings('thead').find('[name=checkAll]').prop('checked', false);
    }
    
    unselect_total();  // 统计未选专家
    select_total();  // 统计已选专家
  } else {
    layer.msg('至少选择一条数据', {
      offset: '100px'
    });
  }
}

// 专家总和统计
function unselect_total() {
  var total = 0;
  $('#list_content tr').each(function () {
    if (!$(this).hasClass('hide')) {
      total++;
    }
  });
  $('#unselect_expertTotal').html(total);
}

// 专家总和统计
function select_total() {
  var total = $('#selected_content tr').length;
  $('#select_expertTotal').html(total);
}

// 暂存初始化
function temporary_init() {
  var temporary_content = [];
  var str = '';
  
  $.ajax({
    type: 'POST',
    dataType: 'json',
    url: temporary_init_url,
    data: {},
    success: function (data) {
      temporary_content = data;
      for (var i in temporary_content) {
        str += '<tr>'
            +'  <td class="text-center"><input name="id" type="checkbox" value="91d91266b5e14e1d87be71b42154bda0" class="select_item"></td>'
            +'  <td class="text-center">1</td>'
            +'  <td>CG08</td>'
            +'  <td>1027ZJ5</td>'
            +'  <td class="text-center">男</td>'
            +'  <td>工程技术、服务技术</td>'
            +'  <td class="text-center">军队</td>'
            +'  <td>FSFSD</td>'
            +'  <td>4324324</td>'
            +'  <td class="text-center">2017-11-03</td>'
        +'</tr>';
      }
      
      $('#selected_content').html(str);
      
      // 处理未选人员
      $('#list_content tr').each(function () {
        for (var ii in temporary_content) {
          if ($(this).find('input[type="checkbox"]').val() == temporary_content[ii]) {
            $(this).addClass('hide');
            break;
          }
        }
      });
      
      unselect_total();  // 统计未选专家
      select_total();  // 统计已选专家
    }
  });
}

// 暂存操作
function againAudit_temporary() {
  var ids = [];  // 专家id
  $('#selected_content tr').each(function () {
    ids.push($(this).find('input[type="checkbox"]').val());
  });
  
  $.ajax({
    type: 'POST',
    dataType: 'json',
    url: temporary_url,
    data: {
      ids: ids.join(',')
    },
    success: function (data) {
      layer.msg(data.message, {
        offset: '100px'
      });
    }
  });
}