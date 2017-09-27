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
        var str = '';
        var str_tr = '';
        var groupStatus = ['（待配置）','（配置中）'];
        var groupStatus_str = '';
        list_content = data.object;  // 储存所需数据到变量
        
        if (typeof(list_content) != 'undefined') {
          $('#group_batch_box').html('');
          if (list_content.length === 0) {
            layer.msg('请先进行批次分组！', {
              time: 0,
              btn: ['好'],
              yes: function(index){
                layer.close(index);
                history.back();
              }
            });
          } else {
            for (var i in list_content) {
              for (var ii in list_content[i].expertList) {
                // 判断状态输出
                if (list_content[i].expertList[ii].status === '-3') {
                  list_content[i].expertList[ii].status = '公示中';
                } else if (list_content[i].expertList[ii].status === '-2' && list_content[i].expertList[ii].isReviewEnd == '1') {
                  list_content[i].expertList[ii].status = '复审结束';
                } else if (list_content[i].expertList[ii].status === '-2') {
                  list_content[i].expertList[ii].status = '预复审结束';
                } else if (list_content[i].expertList[ii].status === '-1') {
                  list_content[i].expertList[ii].status = '暂存';
                } else if (list_content[i].expertList[ii].status === '0') {
                  list_content[i].expertList[ii].status = '待初审';
                } else if (list_content[i].expertList[ii].status === '4') {
                  if (list_content[i].expertList[ii].status === '4' && list_content[i].expertList[ii].auditTemporary === '4') {
                    list_content[i].expertList[ii].status = '复审中';
                  } else {
                    list_content[i].expertList[ii].status = '待复审';
                  }
                } else if (list_content[i].expertList[ii].status === '5') {
                  list_content[i].expertList[ii].status = '复审不合格';
                } else if (list_content[i].expertList[ii].status === '6') {
                  list_content[i].expertList[ii].status = '待复查';
                } else if (list_content[i].expertList[ii].status === '7') {
                  list_content[i].expertList[ii].status = '复查合格';
                } else if (list_content[i].expertList[ii].status === '8') {
                  list_content[i].expertList[ii].status = '复查不合格';
                } else if (list_content[i].expertList[ii].status === '10') {
                  list_content[i].expertList[ii].status = '复审退回修改';
                } else if (list_content[i].expertList[ii].status === '11') {
                  list_content[i].expertList[ii].status = '待分配';
                } else if (list_content[i].expertList[ii].status === '12') {
                  list_content[i].expertList[ii].status = '处罚中';
                } else if (list_content[i].expertList[ii].status === '13') {
                  list_content[i].expertList[ii].status = '无产品专家';
                } else if (list_content[i].expertList[ii].status === '14') {
                  list_content[i].expertList[ii].status = '复审待分组专家';
                }
                
                if (typeof(list_content[i].expertList[ii].batchDetailsNumber) === 'undefined') {
                  list_content[i].expertList[ii].batchDetailsNumber = '';
                }
                if (typeof(list_content[i].expertList[ii].orgName) === 'undefined') {
                  list_content[i].expertList[ii].orgName = '';
                }
                if (typeof(list_content[i].expertList[ii].realName) === 'undefined') {
                  list_content[i].expertList[ii].realName = '';
                }
                if (typeof(list_content[i].expertList[ii].gender) === 'undefined') {
                  list_content[i].expertList[ii].gender = '';
                }
                if (typeof(list_content[i].expertList[ii].workUnit) === 'undefined') {
                  list_content[i].expertList[ii].workUnit = '';
                }
                if (typeof(list_content[i].expertList[ii].professTechTitles) === 'undefined') {
                  list_content[i].expertList[ii].professTechTitles = '';
                }
                if (typeof(list_content[i].expertList[ii].updateTime) === 'undefined') {
                  list_content[i].expertList[ii].updateTime = '';
                }
                if (typeof(list_content[i].expertList[ii].status) === 'undefined') {
                  list_content[i].expertList[ii].status = '';
                }
                if (typeof(list_content[i].expertList[ii].updateTime) === 'undefined') {
                  list_content[i].expertList[ii].updateTime = '';
                }
                
                str_tr += '<tr>'
                  +'<td class="text-center">'+ list_content[i].expertList[ii].batchDetailsNumber +'</td>'
                  +'<td class="text-center">'+ list_content[i].expertList[ii].orgName +'</td>'
                  +'<td class="text-center">'+ list_content[i].expertList[ii].realName +'</td>'
                  +'<td class="text-center">'+ list_content[i].expertList[ii].gender +'</td>'
                  +'<td class="text-center">'+ list_content[i].expertList[ii].workUnit +'</td>'
                  +'<td class="text-center">'+ list_content[i].expertList[ii].professTechTitles +'</td>'
                  +'<td class="text-center">'+ list_content[i].expertList[ii].updateTime +'</td>'
                  +'<td class="text-center">'+ list_content[i].expertList[ii].status +'</td>'
                  +'<td class="text-center">'+ list_content[i].expertList[ii].updateTime +'</td>'
                +'</tr>';
              }
              if (list_content[i].groupStatus === '1') {
                groupStatus_str = groupStatus[0];
              } else if (list_content[i].groupStatus === '2') {
                groupStatus_str = groupStatus[1];
              } else if (list_content[i].groupStatus === '3') {
                groupStatus_str = '（审核组成员：' + list_content[i].team.replace(/,/g, '、') + '）';
              } else {
                groupStatus_str = '';
              }
              str += '<div class="group_batch_list">'
                    +'<div class="gbl_tit"><span class="gbl_icon">'+ (parseInt(i) + 1) +'</span><span>'+ list_content[i].name + groupStatus_str +'</span></div>'
                    +'<div class="mt10 mb10">'
                    +'  <button type="button" class="btn btn-windows config" onclick="jump_members(\''+ list_content[i].id +'\')">配置审核组成员</button>'
                    +'</div>'
                    +'<table class="table table-bordered table-condensed table-hover table-striped groupBatch_table">'
                    +'  <thead>'
                    +'    <tr>'
                    +'      <th class="info w100">批次编号</th>'
                    +'      <th class="info">采购机构</th>'
                    +'      <th class="info">专家姓名</th>'
                    +'      <th class="info">性别</th>'
                    +'      <th class="info">工作单位</th>'
                    +'      <th class="info">专业职称</th>'
                    +'      <th class="info">初审时间</th>'
                    +'      <th class="info">审核状态</th>'
                    +'      <th class="info">复审时间</th>'
                    +'    </tr>'
                    +'  </thead>'
                    +'  <tbody>'+ str_tr +'</tbody>'
                    +'</table>'
              +'</div>';
              $('#group_batch_box').append(str);
              str = str_tr = '';
            }
          }
        }
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
        pages: list_content.pages, //总页数
        skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
        skip: true, //是否开启跳页
        total: list_content.total,
        startRow: list_content.startRow,
        endRow: list_content.endRow,
        groups: list_content.pages >= 3 ? 3 : list_content.pages, //连续显示分页数
        curr: function() { //合格url获取当前页，也可以同上（pages）方式获取
          return list_content.pageNum;
        }(),
        jump: function(e, first) { //触发分页后的回调
          if(!first) { //一定要加此判断，否则初始时会无限刷新
            $("#pageNum").val(e.curr);
            opts.data.pageNum = e.curr;
            opts.data.status = '14';
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