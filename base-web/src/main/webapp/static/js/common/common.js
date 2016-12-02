var globalObj = globalObj || {};
  Date.prototype.format = function(fmt)  {
	  var o = { 
	    "M+" : this.getMonth()+1,                 //月份 
	    "d+" : this.getDate(),                    //日 
	    "h+" : this.getHours(),                   //小时 
	    "m+" : this.getMinutes(),                 //分 
	    "s+" : this.getSeconds(),                 //秒 
	    "q+" : Math.floor((this.getMonth()+3)/3), //季度 
	    "S"  : this.getMilliseconds()             //毫秒 
	  }; 
	  if(/(y+)/.test(fmt)) 
	    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
	  for(var k in o) 
	    if(new RegExp("("+ k +")").test(fmt)) 
	  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length))); 
	  return fmt; 
};

  globalObj = $.extend(globalObj, {
	gId : function(d){
		return document.getElementById(d); 
	},
	commons : function(){
		var doc = $(document),
		    curUrl = window.location.href;
		doc.on("click", "[data-action]", function(){
			var elem = this,
              me = $(this), 
              meAction = me.attr("data-action"), 
              meEven = globalObj[meAction];
			  meEven && meEven.call(elem);
			  return false;
		});
	   var bdy = $('body'),
	       theader = $('.header'),
	       topNav = $('.top-page-nav'),
	       fot = $('.footer'),
	       pbody = $('.page-body');
	   var theaderH = theader.length && theader.outerHeight() + 20 || 0,
	       topNavH = topNav.length && topNav.outerHeight() || 0,
	       fotH = fot.length && fot.outerHeight(true) || 0;
	   if(!pbody.hasClass('cur-minh')){
		   pbody.css('height', bdy.outerHeight() - theaderH - topNavH -fotH);   
	   }
	   //设置APP左侧栏当前样式
	   $('.appleftnav').find('a').each(function(){
		   var me = $(this);
		  if(curUrl.indexOf(me.attr('href')) > -1){
			  me.parents('li').addClass('active');
		  } 
	   });
	   doc.on('keyup blur', ':text, :password', function(){
		  var me = $(this),
		      meVal = me.val();
		      meVal && me.css('color', '#666') || me.css('color', '#dfe1e8');
	   });
	  //头部模块当前样式
	  $('li[data-pageword]' ,$('.top-nav')).each(function(){
		  var me = $(this),
		      ky = me.attr('data-pageword');
		  if(ky.indexOf(',') > -1){
			  var kyArr = ky.split(',');
			  for(var i = 0, len = kyArr.length; i < len; i++){
				   if(curUrl.indexOf(kyArr[i]) > -1){
					  me.addClass('active');
				   }
			   }
		  }else{
			  if(curUrl.indexOf(ky) > -1){
				  me.addClass('active');
			   }
		  }
	   });
	   /*
		 * 绑定日期插件
		 */
		doc.on("click", ".inp-date", function(){
			  var me = $(this),
			      meFmt = me.attr("data-fmt"),
			      param = {};
			  param.dateFmt = meFmt || 'yyyy-MM-dd';
			  param.onpicked = function(){
					me.trigger('change');
				};
			  WdatePicker(param);
			  return false;
		  });
		  doc.on("click", "[data-datetarid]", function(){
			  var param = {},
			      tarel = $(this).attr("data-datetarid"),
			      elFmt = $("#" + tarel).attr("data-fmt");
			  param.el = globalObj.gId(tarel);
			  param.dateFmt = elFmt || 'yyyy-MM-dd';
			  param.onpicked = function(){
				  $("#" + tarel).trigger('change');
				};
			  WdatePicker(param);
			  return false;
		  });
		  doc.on('click', function(){
			  if($(".jqTransformSelectWrapper").length){
				  $(".jqTransformSelectWrapper > ul").hide();  
			  }
		  });
		 doc.on('click', '.btn-go-back', function(){
			 window.history.go(-1);
			 return false;
		 });
	},
    msgbox : {
    	show : function(msg, type, timeout, opts){
    		ZENG.msgbox.show(msg, type, timeout, opts);
    	},
    	hide : function(){
    		ZENG.msgbox.hide(500);
    	}
    },
    regType : {
    	emailReg : /^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
    	emptyReg : /[\w\W]+/,
        nReg : /^\d+$/,
        fReg : /^\d+(\.\d+)?$/,
        f32Reg : /^\d{0,3}(\.\d{0,2})?$/,
        phoneReg : /^13[0-9]{9}$|14[0-9]{9}|15[0-9]{9}$|17[0-9]{9}$|18[0-9]{9}$/,
        httpurlReg : /((http|https):\/\/([\w\-]+\.)+[\w\-]+(\/[\w\u4e00-\u9fa5\-\.\/?\@\%\!\&=\+\~\:\#\;\,]*)?)/
    },
    errorDes : {
    		email: {
    	        empty: "请输入邮箱",
    	        isExist : "该邮箱已存在，请重新填写",
    	        character: "邮箱名称格式不对,请重新填写"
    	    },
    	    password: {
    	        empty: "请输入密码",
    	        length: "密码要求6~20位之间",
    	        character: "密码须由数字/字母/符号组成"
    	    },
    	    confirmpassword: {
    	        empty: "请输入确认密码",
    	        error: "两次输入密码不一致"
    	    },
    	    vcode: {
    	        empty: "请输入验证码",
    	        character: "验证码错误，请重新输入"
    	    },
    	    loginbutton: {
    	        loading: "登录中...",
    	        unloading: "登录"
    	    },
    	    registerbutton: {
    	        loading: "注册中...",
    	        unloading: "注册"
    	    } 
    	},
      ajax : function(opts){
    	var df = $.Deferred(),
    	    curSet = {
	    		 type : "POST",
	    		 async : true,
	    		 url : "",
	    		 data : {},
	    		 dataType : 'json'
    	};
    	$.extend(curSet, opts);
        $.ajax({
            type: curSet.type,
            url: curSet.url,
            headers: {},
            data: curSet.data,
            dataType: curSet.dataType,
            success: function(data) {
            	if(data.status ==='SUCCESS' || data.status ==='success'){
            		df.resolve(data);
            	}else{
            		df.reject(data);
            	}
            },
            error: function(xhr, textStatus) {
//              if (xhr.status == 401){
//              }else if(xhr.status == 501){
//              }else{
//               		var d = JSON.parse(xhr.responseText);
//               		df.reject(d);
//              }
            	console.info("textStatus:"+ textStatus);
            	df.reject({'status':'ERROR','result':'请求超时，请稍候再试'});
            }
        });
        return df;
    },
    pageForm : function(pageCon, pageForm){
  	  var pagesize = $("input[name='pageSize']", $(pageForm)).val(),
  	      currpage = $("input[name='currPage']", $(pageForm)).val(),
  	      pagecount = $("input[name='pageCount']", $(pageForm)).val();
  	 $(pageCon).myPagination({
				cssStyle:"black",
				pageSize:parseInt(pagesize) || 1,
				currPage:parseInt(currpage) || 0,
		        pageCount:parseInt(pagecount) || 0,
		        pageNumber:5,
		        panel:{
		        	first:"&nbsp;",
		        	last:"&nbsp;",
		        	prev:"&nbsp;",
		        	next:"&nbsp;"
		        },
		       ajax : {
		    	     onClick : function(page){
		    		    $("input[name='pageNo']", $(pageForm)).val(page);
		    		   $(pageForm).submit();
		    	   }
		       }
		    });
   },
   pageAjaxJson : function(pageCon, url, pdata, callback){
  	 var xhrPage = $(pageCon).myPagination({
				cssStyle:"black",
		        panel:{
		        	first:"&nbsp;",
		        	last:"&nbsp;",
		        	prev:"&nbsp;",
		        	next:"&nbsp;"
		        },
		       ajax : {
		    	     on : true,
		    	     url : url,
		    	     type : "post",
		    	     dataType : "json",
		    	     pageCountId : "totalPageCount",
		    	     param : pdata,
		    	     ajaxStart: function() {
		    	    	 globalObj.msgbox.show("数据拉取中...", 6, 300000);
		             },
		    	     onClick : function(page){
		    	   },
		    	   callback : callback
		       }
		    });
  	    return xhrPage;
      },
      /**
   	 * 当前时间添加月份,如果当前月份时间为月底,则添加后时间日期为那个月的最后一天 如：1月31日添加1月后时间为2月28日
   	 * @param {Object} dateStr 转换的日期字符 'yyyy-MM-dd'或者'yyyy/MM/dd' 只支持年月日格式
   	 * @param {Object} period 添加的月份个数
   	 * @param {Object} format 格式化日期 例如 'yyyy-MM-dd' 不传默认返回 'yyyy-MM-dd'
   	 * @return {String} dateStr 
   	 */
      monthAdd: function(dateStr, period, format){
  		if (!dateStr || !period) {
  			return new Date().format('yyyy-MM-dd');
  		}else {
  			var date = globalObj.newDate(dateStr.substring(0, 4)+'-'+dateStr.substring(5, 7)+'-'+dateStr.substring(8, 10));
  			var year = date.getFullYear(), 
  				month = date.getMonth()+1, 
  				day = date.getDate();
  			//判断是否当月最后一天
  			var isLastDay = (day == globalObj.monthDays[month]);
  			//判断是否超过12月
  			period = parseInt(period);
  			if (month + period > 12) {
  				year += parseInt((month + period)/ 12);
  				month = (month + period) % 12 == 0 ? 12 : (month + period) % 12;
  			} else {
  				month = month + period;
  			}
  			//判断是否是二月份
  			if (month == 2) {
  				//指定的年份是否能被4整除且不能被100整除，或能被100整除且能被400整除
  				if ((year % 4 == 0 && year % 100 != 0) || (year % 100 == 0 && year % 400 == 0)) {
  					if (isLastDay) {
  						day = 29;
  					}
  				} else {
  					if (isLastDay) {
  						day = 28;
  					}
  				}
  			} else {
  				if (isLastDay) {//获取月份最大天数
  					day = globalObj.monthDays[month];
  				}
  			}
  			var returnStr = '';
  			if(!format){
  				returnStr = globalObj.newDate(year+'-'+month+'-'+day).format('yyyy-MM-dd');
  			}else{
  				returnStr = globalObj.newDate(year+'-'+month+'-'+day).format(format);
  			}
  			return returnStr;
  		}

     }, 
 	/**
 	 * 带参数的日期格式化 (IE和firefox 下new Date()带参数不可用,需要调用此方法)
 	 * @param dateStr 日期格式'yyyy-MM-dd' 或 yyyyMMdd
 	 * @param hourStr 时间格式 H:MM
 	 */	 
 	newDate : function(dateStr, hourStr){
 		var str = [];
 		if(dateStr.length == 8 && dateStr.indexOf('-') == -1){
 			str[0] = dateStr.substring(0, 4);
 			str[1] = dateStr.substring(4, 6);
 			str[2] = dateStr.substring(6, 8);
 		}else{
 			str = dateStr.split('-');
 		}
 		var date = new Date(); 
 		date.setUTCFullYear(str[0], str[1] - 1, str[2]); 
 		if(hourStr){
 			var hourArr = hourStr.split(':');
 			date.setUTCHours(hourArr[0],hourArr[1], 0, 0); 
 		}else{
 			date.setUTCHours(0, 0, 0, 0); 
 		}
 		return date; 
 	},
 	//月份对应的天数
 	monthDays: {
 		1 : 31, 3 : 31, 4 : 30, 5 : 31, 6 : 30, 7 : 31, 8 : 31, 9 : 30, 10 : 31, 11 : 30, 12 : 31
 	},
 	phoneCodeUtil : {
		   sendCodeAndDown : function(btn, second , btnVal, mobileNo, url, pdata, susCallBack){
			   this.timeDown(btn, second, btnVal);
			   ajaxUtil.ajax(url, pdata, function(data){
				   susCallBack && susCallBack(data, btn, btnVal);
			   });
		   },
		   downTimeout : null,
		   /**
		    * 倒计时函数
		    * 
		    * @param btn
		    *    获取验证码按钮对应的DOM节点
		    * @param second
		    *    倒计时的秒数
		    * @param btnVal
		    *    显示在获取验证码按钮上文字
		    */
		   timeDown : function(btn, second, btnVal){
			   	if (second > 0) {
			   		if ($(btn).get(0).tagName == 'INPUT') {
			   			btn.value = second + '秒';
			   		} else {
			   			$(btn).text(second + "秒");
			   		}
			   		this.downTimeout = setTimeout(function() {
			   			globalObj.phoneCodeUtil.timeDown(btn, --second, btnVal);
			   		}, 1000);
			   	} else {
			   		if ($(btn).get(0).tagName == 'INPUT') {
			   			btn.value = btnVal;
			   		} else {
			   			$(btn).text(btnVal);
			   		}
			   	}
		   }
   }
});

$(function(){
	globalObj.commons();
});