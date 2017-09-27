(function($) {
  $.fn.listConstructor = function(options) {
    var list_content = [];  // 初始化数据
    
    //默认参数
    var defaults = {
      type: 'POST',
      dataType: 'json',
      url: '',
      data: {},
      success: function (data) {
        list_content = data.object;  // 储存所需数据到变量
        var userType = data.userType;
        
        $('#head_tit').html(list_content.batchName);
        // 按钮判断
        if (userType === '4') {
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
            $('.pic_upload').before('<button type="button" class="btn btn-windows group" onclick="jump_batchGroup()">批次分组</button>'
              +'<button type="button" class="btn btn-windows config" onclick="jump_auditBatch()">审核配置</button>'
              +'<button type="button" class="btn btn-windows apply" onclick="reviewConfirm()">批准</button>'
            );
          }
            
          $('#table_content').html('<table class="table table-bordered table-condensed table-hover table-striped break-all againAudit_table">'
            +'<thead>'
            +'  <tr>'
            +'    <th class="info w30"><input type="checkbox" onclick="selectAll();" id="checkAll"></th>'
            +'    <th class="info w130">专家编号</th>'
            +'    <th class="info w100">采购机构</th>'
            +'    <th class="info w100">专家姓名</th>'
            +'    <th class="info w50">性别</th>'
            +'    <th class="info w80">专家类型</th>'
            +'    <th class="info w80">专家类别</th>'
            +'    <th class="info">工作单位</th>'
            +'    <th class="info w120">专业职称(职务)</th>'
            +'    <th class="info w60">审核组</th>'
            +'    <th class="info w80">审核状态</th>'
            +'    <th class="info w100">操作</th>'
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
                list_content.list.list[i].status = '公示中';
              } else if (list_content.list.list[i].status === '-2' && list_content.list.list[i].isReviewEnd != '1') {
                list_content.list.list[i].status = '预复审结束';
                btn = '<button type="button" class="btn" onclick="downloadTable(\''+ list_content.list.list[i].expertId +'\')">下载复审表</button>';
              } else if (list_content.list.list[i].status === '-2' && list_content.list.list[i].isReviewEnd == '1') {
            	  list_content.list.list[i].status = '复审结束';
              } else if (list_content.list.list[i].status === '-1') {
                list_content.list.list[i].status = '暂存';
              } else if (list_content.list.list[i].status === '1') {
                list_content.list.list[i].status = '初审合格';
              } else if (list_content.list.list[i].status === '0') {
                list_content.list.list[i].status = '待初审';
              } else if (list_content.list.list[i].status === '4') {
                if (list_content.list.list[i].status === '4' && list_content.list.list[i].auditTemporary === '4') {
                  list_content.list.list[i].status = '复审中';
                } else {
                  list_content.list.list[i].status = '待复审';
                }
              } else if (list_content.list.list[i].status === '5') {
                list_content.list.list[i].status = '复审不合格';
              } else if (list_content.list.list[i].status === '6') {
                list_content.list.list[i].status = '待复查';
              } else if (list_content.list.list[i].status === '7') {
                list_content.list.list[i].status = '复查合格';
              } else if (list_content.list.list[i].status === '8') {
                list_content.list.list[i].status = '复查不合格';
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
              if (typeof(list_content.list.list[i].professTechTitles) === 'undefined') {
                list_content.list.list[i].professTechTitles = '';
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
              if (typeof(list_content.list.list[i].expertsTypeId) === 'undefined') {
                list_content.list.list[i].expertsTypeId = '';
              }
              if (typeof(list_content.list.list[i].expertsFrom) === 'undefined') {
                list_content.list.list[i].expertsFrom = '';
              }
              
              $('#list_content').append('<tr><input id="'+ list_content.list.list[i].expertId +'" type="hidden">'
            	+'<td class="text-center"><input name="chkItem" type="checkbox" onclick="check();" value="'+ list_content.list.list[i].expertId +'" class="select_item"></td>'
                +'<td class="text-center">'+ list_content.list.list[i].batchDetailsNumber +'</td>'
                +'<td class="text-center">'+ list_content.list.list[i].orgName +'</td>'
                +'<td class="text-center">'+ list_content.list.list[i].realName +'</td>'
                +'<td class="text-center">'+ list_content.list.list[i].gender +'</td>'
                +'<td class="text-center">'+ list_content.list.list[i].expertsTypeId +'</td>'
                +'<td class="text-center">'+ list_content.list.list[i].expertsFrom +'</td>'
                +'<td class="text-center">'+ list_content.list.list[i].workUnit +'</td>'
                +'<td class="text-center">'+ list_content.list.list[i].professTechTitles +'</td>'
                +'<td class="text-center">'+ list_content.list.list[i].groupName +'</td>'
                +'<td class="text-center">'+ list_content.list.list[i].status +'</td>'
                +'<td class="text-center">'+ btn +'</td>'
              +'</tr>');
            }
          }
        } else if (userType === '6') {
          // $('#btn_group').html('<button type="button" class="btn btn-windows git" onclick="expert_auditBatch(\''+ root_url +'\')">审核</button>'
          //   +'<button type="button" onclick="downloadTable(2)" class="btn btn-windows input">下载复审表</button>');
          
          $('#btn_group').html('<button type="button" class="btn btn-windows git" onclick="expert_auditBatch(\''+ root_url +'\')">审核</button>');
          
          $('#table_content').html('<table class="table table-bordered table-condensed table-hover table-striped break-all againAudit_table">'
            +'<thead>'
            +'  <tr>'
            +'    <th class="info w30"></th>'
            +'    <th class="info w100">专家编号</th>'
            +'    <th class="info w100">采购机构</th>'
            +'    <th class="info w80">专家姓名</th>'
            +'    <th class="info w50">性别</th>'
            +'    <th class="info w120">专家类型</th>'
            +'    <th class="info w80">专家类别</th>'
            +'    <th class="info">工作单位</th>'
            +'    <th class="info w80">专业职称(职务)</th>'
            +'    <th class="info w60">审核组</th>'
            +'    <th class="info w90">审核状态</th>'
            +'    <th class="info w100">操作</th>'
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
                list_content.list.list[i].status = '公示中';
              } else if (list_content.list.list[i].status === '-2'&& list_content.list.list[i].isReviewEnd != 1) {
                list_content.list.list[i].status = '预复审结束';
                if(list_content.list.list[i].isDownload == 1 && list_content.list.list[i].isReviewEnd != 1){
                	btn = '<button type="button" class="btn" onclick="reviewEnd(\''+ list_content.list.list[i].expertId +'\');">复审结束</button>';
                }
              }else if (list_content.list.list[i].status === '-2' && list_content.list.list[i].isReviewEnd == 1) {
                  list_content.list.list[i].status = '复审结束';
              }else if (list_content.list.list[i].status === '-1') {
                list_content.list.list[i].status = '暂存';
              } else if (list_content.list.list[i].status === '1') {
                list_content.list.list[i].status = '初审合格';
              } else if (list_content.list.list[i].status === '0') {
                list_content.list.list[i].status = '待初审';
              } else if (list_content.list.list[i].status === '4') {
                if (list_content.list.list[i].status === '4' && list_content.list.list[i].auditTemporary === '4') {
                  list_content.list.list[i].status = '复审中';
                } else {
                  list_content.list.list[i].status = '待复审';
                }
              } else if (list_content.list.list[i].status === '5') {
                list_content.list.list[i].status = '复审不合格';
              } else if (list_content.list.list[i].status === '6') {
                list_content.list.list[i].status = '待复查';
              } else if (list_content.list.list[i].status === '7') {
                list_content.list.list[i].status = '复查合格';
              } else if (list_content.list.list[i].status === '8') {
                list_content.list.list[i].status = '复查不合格';
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
              if (typeof(list_content.list.list[i].professTechTitles) === 'undefined') {
                list_content.list.list[i].professTechTitles = '';
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
              
              $('#list_content').append('<tr><input id="'+ list_content.list.list[i].expertId +'" type="hidden">'
                +'<td class="text-center"><input name="id" type="checkbox" value="'+ list_content.list.list[i].expertId +'" class="select_item"></td>'
                +'<td class="text-center">'+ list_content.list.list[i].batchDetailsNumber +'</td>'
                +'<td class="text-center">'+ list_content.list.list[i].orgName +'</td>'
                +'<td class="text-center">'+ list_content.list.list[i].realName +'</td>'
                +'<td class="text-center">'+ list_content.list.list[i].gender +'</td>'
                +'<td class="text-center">'+ list_content.list.list[i].expertsTypeId +'</td>'
                +'<td class="text-center">'+ list_content.list.list[i].expertsFrom +'</td>'
                +'<td class="text-center">'+ list_content.list.list[i].workUnit +'</td>'
                +'<td class="text-center">'+ list_content.list.list[i].professTechTitles +'</td>'
                +'<td class="text-center">'+ list_content.list.list[i].groupName +'</td>'
                +'<td class="text-center">'+ list_content.list.list[i].status +'</td>'
                +'<td class="text-center">'+ btn +'</td>'
              +'</tr>');
            }
          }
        }
        
        // 勾选翻页之前选中的项
        for (var i in select_ids) {
          $('.select_item').each(function () {
            if ($(this).val() === select_ids[i]) {
              $(this).prop('checked', true);
              return false;
            }
          });
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
          });
        }
        
        // 构造分页
        laypageConstructor();
      },
      error: function (data) {
        layer.msg(data.message, {
          offset: '100px'
        });
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
  
    // 分页
    function laypageConstructor() {
      laypage({
        cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
        pages: list_content.list.pages, //总页数
        skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
        skip: true, //是否开启跳页
        total: list_content.list.total,
        startRow: list_content.list.startRow,
        endRow: list_content.list.endRow,
        groups: list_content.list.pages >= 3 ? 3 : list_content.list.pages, //连续显示分页数
        curr: function() { //合格url获取当前页，也可以同上（pages）方式获取
          return list_content.list.pageNum;
        }(),
        jump: function(e, first) { //触发分页后的回调
          if(!first) { //一定要加此判断，否则初始时会无限刷新
            $("#pageNum").val(e.curr);
            opts.data.pageNum = e.curr;
            opts.data.batchId = getUrlParam('batchId');
            $.ajax({
              type: opts.type,
              dataType: opts.dataType,
              url: opts.url,
              data: opts.data,
              success: opts.success
            });
          }
        }
      });
    }

    return start();
  }
})(jQuery);