/**
 * 时间格式化
 * 
 * @param fmt
 * @returns
 */
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
 
 var App = App || {}, dateUtil = {
 	/**
 	 * 当前时间添加月份,如果当前月份时间为月底,则添加后时间日期为那个月的最后一天 如：1月31日添加1月后时间为2月28日
 	 * @param {Object} dateStr 转换的日期字符 'yyyy-MM-dd'或者'yyyy/MM/dd' 只支持年月日格式
 	 * @param {Object} period 添加的月份个数
 	 * @param {Object} format 格式化日期 例如 'yyyy-MM-dd' 不传默认返回 'yyyy-MM-dd'
 	 * @return {String} dateStr 
 	 */
    monthAdd: function(dateStr,period,format){
		if (dateStr == null || dateStr == '') {
			return '';
		} else if(period == null || dateStr == ''){
			return '';
		}
		else {
			var date = dateUtil.newDate(dateStr.substring(0, 4)+'-'+dateStr.substring(5, 7)+'-'+dateStr.substring(8, 10));
			var year = date.getFullYear(), 
				month = date.getMonth()+1, 
				day = date.getDate();
			//判断是否当月最后一天
			var isLastDay = (day == dateUtil.monthDays[month]);
			//判断是否超过12月
			period = parseInt(period);
			if (month + period > 12) {
				year += parseInt((month+period)/12);
				if(((month + period) % 12 == 0) && ((month+period)/12) >= 2){
					year = year - 1;
				}
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
					day = dateUtil.monthDays[month];
				}
			}
			var returnStr = '';
			if(format == undefined || format == null || format== ''){
				returnStr = dateUtil.newDate(year+'-'+month+'-'+day).format('yyyy-MM-dd');
			}else{
				returnStr = dateUtil.newDate(year+'-'+month+'-'+day).format(format);
			}
			return returnStr;
		}

   },
	/**
	 * 日期加减年月日
	 * @param {Object} date
     * @param {Object} period 
     * @param {Object} type 'y'年 'm'月 'd'日
	 */
	dateAdd : function(date,period,type){
		switch(type){
			case 'y' :
				date.setFullYear(date.getFullYear()+period);
			case 'm' :
				date.setMonth(date.getMonth()+period);
				return date;
			case 'd' :
				date.setDate(date.getDate()+period);
				return date;
		}
	},
	/**
	 * 判断日期大小,如果大于等于返回false,小于返回true
 	 * @param {Object} startdate 开始时间
     * @param {Object} enddate 结束时间
	 */
	dateCompare : function (startdate, enddate) {
			if(startdate == enddate ){
				return false;
			}
			var starttime = new Date(dateUtil.joinDateStr(startdate,'-'));
			var starttimes = starttime.getTime();

			var arrs = enddate.split("-");
			var lktime = new Date(dateUtil.joinDateStr(enddate,'-'));;
			var lktimes = lktime.getTime();

			if (starttimes > lktimes) {
				return false;
			} else
				return true;
		}, 
	/**
	 * 带参数的日期格式化 (IE和firefox 下new Date()带参数不可用,需要调用此方法)
	 * @param dateStr 日期格式'yyyy-MM-dd' 或 yyyyMMdd
	 * @param hourStr 时间格式 H:MM
	 */	 
	newDate : function(dateStr,hourStr){
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
		if(hourStr != undefined && hourStr!=null){
			var hourArr = hourStr.split(':');
			date.setUTCHours(hourArr[0],hourArr[1], 0, 0); 
		}else{
			date.setUTCHours(0, 0, 0, 0); 
		}
		return date; 
	},
	//月份对应的天数
	monthDays: {
		1:31,3:31,4:30,5:31,6:30,7:31,8:31,9:30,10:31,11:30,12:31
	},
	/**
	 * 八位数日期格式化 yyyyMMdd 
     * @param {Object} dateStr
     * @param {Object} regex 分隔符 如'-' '/'
	 */
	joinDateStr : function(dateStr,regex){
		if(dateStr.length > 8){
			return dateStr.substring(0, 4)+regex+dateStr.substring(5, 7)+regex+dateStr.substring(8, 10);
		}else{
			return dateStr.substring(0, 4)+regex+dateStr.substring(4,6)+regex+dateStr.substring(6,8);
		}
	},
  	 //返回相差 X 天
  	 dateCompare2Num : function (startdate, enddate) {
  			var starttime = new Date(dateUtil.joinDateStr(startdate,'-'));
  			var starttimes = starttime.getTime();

  			var lktime = new Date(dateUtil.joinDateStr(enddate,'-'));;
  			var lktimes = lktime.getTime();
  			
  			var compare = lktimes - starttimes;
  			return compare/86400000;//24*60*60*1000 = 144*6*100000
  		},
  	     //相差x.x小时 or  x 小时
  	 timeCompare2Num :function(starttime,endtime){
	  		 var hourAndMin_start = starttime.split(":");
	  		 if(hourAndMin_start[0].indexOf("0")==0){
	  			hourAndMin_start[0] = parseInt(hourAndMin_start[0].substring(1,2));
	  		}else{
	  			hourAndMin_start[0]= parseInt(hourAndMin_start[0]);
	  		}
	  		 if(hourAndMin_start[1].indexOf("0")==0){
	  			 hourAndMin_start[1] = parseInt(hourAndMin_start[1].substring(1,2));
	  		 }else{
	  			 hourAndMin_start[1]= parseInt(hourAndMin_start[1]);
	  		 }
	  		 
	  		 var hourAndMin_end = endtime.split(":");
	  		if(hourAndMin_end[0].indexOf("0")==0){
	  			hourAndMin_end[0] = parseInt(hourAndMin_end[0].substring(1,2));
	  		}else{
	  			hourAndMin_end[0]= parseInt(hourAndMin_end[0]);
	  		}
	  		 if(hourAndMin_end[1].indexOf("0")==0){
	  			hourAndMin_end[1] = parseInt(hourAndMin_end[1].substring(1,2));
	  		 }else{
	  			hourAndMin_end[1]= parseInt(hourAndMin_end[1]);
	  		 }
	  		 
	  		 var res_comp = hourAndMin_end[0] - hourAndMin_start[0] + (hourAndMin_end[1] - hourAndMin_start[1] )/60;
	  		res_comp = res_comp.toFixed(1);
	  		if(res_comp.indexOf(".0")!=-1){
	  			res_comp = res_comp.substring(0,res_comp.length-2);
	  			res_comp = parseInt(res_comp);
	  		}
	  		return res_comp;
	  	 },
	  	 /**
	  	  * 格式化时间格式为标准 HH:mm
 		  * @param {Object} hour HHmm
	  	  */
	  	 formatHour:function(hour){
	  	 	var len = hour.length;
	  	 	return hour.substring(0,len-2) +':'+ hour.substring(len-2,len);
	  	 }
 };