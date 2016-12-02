jQuery.extend({
	handleError : function(s, xhr, status, e) {
		// If a local callback was specified, fire it
		if (s.error) {
			s.error.call(s.context || s, xhr, status, e);
		}
		// Fire the global callback
		if (s.global) {
			(s.context ? jQuery(s.context) : jQuery.event).trigger("ajaxError", [ xhr, s, e ]);
		}
	},
	createUploadIframe : function(id, uri) {
		var frameId = 'jUploadFrame' + id;
		var io = null;
		if (window.ActiveXObject) {
			if (jQuery.browser.version == "9.0" || jQuery.browser.version == "10.0" || jQuery.browser.version == "11.0") {
				io = document.createElement('iframe');
				io.id = frameId;
				io.name = frameId;
			} else if (jQuery.browser.version == "6.0" || jQuery.browser.version == "7.0" || jQuery.browser.version == "8.0") {
				io = document.createElement('<iframe id="' + frameId + '" name="' + frameId + '" />');
				if (typeof uri == 'boolean') {
					io.src = 'javascript:false';
				} else if (typeof uri == 'string') {
					io.src = uri;
				}
			}
		} else {
			io = document.createElement('iframe');
			io.id = frameId;
			io.name = frameId;
		}
		io.style.position = 'absolute';
		io.style.top = '-1000px';
		io.style.left = '-1000px';

		document.body.appendChild(io);

		return io;
	},
	createUploadForm : function(id, url, fileElementId, data) {
		// create form
		var formId = 'jUploadForm' + id;
		var fileId = 'jUploadFile' + id;
		var form = jQuery('<form action="' + url + '" method="POST" name="' + formId + '" id="' + formId + '" enctype="multipart/form-data"></form>');
		if (data) {
			for ( var i in data) {
				jQuery('<input type="hidden" name="' + i + '" value="' + data[i] + '" />').appendTo(form);
			}
		}

		var oldElement = jQuery('#jQueryFormFile' + fileElementId);
		var newElement = jQuery(oldElement).clone();
		jQuery(oldElement).attr('id', fileId);
		jQuery(oldElement).before(newElement);
		jQuery(oldElement).appendTo(form);

		// set attributes
		jQuery(form).css({
			'position': 'absolute',
			'top': '-1200px',
			'left': '-1200px'
		});
		jQuery(form).appendTo('body');
		return form;
	},
	ajaxFileUpload : function(s) {
		// TODO introduce global settings, allowing the client to modify them
		// for all requests, not only timeout
		s = jQuery.extend({}, jQuery.ajaxSettings, s);
		var id = new Date().getTime();
		var form = jQuery.createUploadForm(id, s.url, s.fileElementId, s.data);
		var io = jQuery.createUploadIframe(id, s.secureuri);
		var frameId = 'jUploadFrame' + id;
		var formId = 'jUploadForm' + id;
		try {
			var form = $('#' + formId);
			$(form).attr('action', s.url);
			$(form).attr('method', 'POST');
			$(form).attr('target', frameId);
			if (form.encoding) {
				form.encoding = 'multipart/form-data;charset=UTF-8';
			} else {
				form.enctype = 'multipart/form-data;charset=UTF-8';
			}
			$(form).ajaxSubmit({
				url : s.url,
				method : "POST",
				contentType : "text/html",
				dataType : "html",
				success : function(data) {
					var json = eval("(" + data + ")");
                    var u;
					if ("success" === json.status || "SUCCESS" === json.status) {
						// 从服务器返回的json中取出message中的数据
						$("#" + s.fileElementId).attr("data-status" , json.status);
						//判断替换
						if($("#jQueryFormFile" + s.fileElementId).attr("data-flag") === '0'){
							$("#jQueryFormFile" + s.fileElementId).attr("data-flag", "1");
							 u = {"uflag" : "0", "fid" : s.fileElementId, "frameid" : frameId, "formid" : formId};
							$.extend(true, json, u);
						}else{
							 u = {"uflag" : "1", "fid" : s.fileElementId, "frameid" : frameId, "formid" : formId};
							 $.extend(true, json, u);
						}
						// 清除
						if(s.isRemove){
						  $("#" + frameId).remove();
						  $("#" + formId).remove();
						}
						// 回调
						if (typeof s.callback == 'function') {
							s.callback.call(this, json);
						}
					} else {
						payPop(json.result);
						globalObj.msgbox.hide();
					}
				},
			    error: function(xhr, textStatus) {
			    	globalObj.msgbox.hide();
	                if (xhr.status == 401){
	                	payPop('无访问权限.');
	                }else if(xhr.status == 501){
	                	
	                }else if(xhr.status == 404){
	                	payPop('请求地址不存在.');
	                }else{
	                	payPop('通讯失败.');
	                }
	            }
			});

		  } catch (e) {
		    jQuery.handleError(s, xml, null, e);
		 }
	}
});
;(function(w, $, undefined){
	//默认配置
	var settings = {
			 app : App["ctx"],
			 url : App["ctx"] + '/file/upload',
			 allowFile : ["xls", "xlsx", "doc", "docx", "pdf", "txt", "swf", "wmv", "gif", "png", "jpg", "jpeg", "bmp", "rar", "zip"],
			 limitSize : 1024,
			 type : 'P1',
			 isRemove : true,
			 postData : {},
			 beforeCallback : function(el, fileKey){
				 
			 },
			 callback : function(data){
		    	 
		     }
		};
	$.fn.myAjaxFileUpload = function(config) {
		return this.each(function(){
			var curSettings = {};
			$.extend(curSettings, settings);
			if(Object.prototype.toString.call(config) == '[object Object]') {
				$.extend(curSettings, config);
			}
			new ajaxFileUpload(this, curSettings);
		});
	};
	function ajaxFileUpload(elem, config){
		this.elem = elem;
		this.config = config;
		this.init();
	}
	ajaxFileUpload.prototype = {
	    elem : null,
	    fileId : '',
	    fileKey : '',
	    config : null,
		init : function(){
			this.initElem();
			this.initEvent();
		},
		initElem : function(){
			 this.fileId = $(this.elem).attr('id');
			 this.fileKey = $(this.elem).attr('data-id');
		},
		initEvent : function(){
			var me = this;
			$("body").on("change", "#" + me.fileId + "", function(){
				 var _this = $(this),
				     _thisKey = me.fileKey,
				     fileType = _this.val().substring(_this.val().lastIndexOf(".") + 1);
				  if (!me.isInArray(fileType, me.config.allowFile)) {  
					  _this.outerHTML += '';     
					  _this.val('');
					  payPop("只支持后缀名" + me.config.allowFile.join('，') + " 文件上传！");
			          return false;
			      }
				  if($.type(me.config.beforeCallback) === 'function'){
					  me.config.beforeCallback(_this, _thisKey);
				  }
				  globalObj.msgbox.show("上传中...", 6, 300000);
				  var defData = {
						    allowFile : me.config.allowFile.join(','),//限制后缀
							limitSize : me.config.limitSize || 1024,// 文件大写限制
							type : me.config.type || 'P1',//上传文件地址
				  };
				  $.extend(true, defData, me.config.postData);
				$.ajaxFileUpload({
					app : me.config.app,
					url : me.config.url,// 用于文件上传的服务器端请求地址
					secureuri : false,// 一般设置为false
					fileElementId : me.fileKey,// 文件上传空间的id属性
					type : "POST",
					image : "0",
					isRemove : me.config.isRemove,
					data : defData,
					dataType : 'json',// 返回值类型 一般设置为json
					callback : me.config.callback
				});
				return false;
		  });
		},
		isInArray : function(needle, haystack){
			var type = typeof needle;  
		    if(type == 'string' || type =='number') {  
		        for(var i in haystack) {  
		            if(haystack[i] == needle.toLocaleLowerCase()) {  
		                return true;  
		            }  
		        }  
		    }  
		    return false;
		} 
	};
})(window, jQuery);
