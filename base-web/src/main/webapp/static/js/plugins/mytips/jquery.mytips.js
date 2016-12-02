;(function($){
	var settings = {
			msg : '',
			onlyShow : !1,
			position : 'mytipstop',
			plusOffset : 0,
			timeout : 2*1000	
	};
	$.fn.mytips = function(options){
		if(typeof options === 'string') {
			settings.msg = options;
		} else if(Object.prototype.toString.call(options) === '[object Object]') {
			$.extend(settings, options);
		}
		var me = this,
		    elem = $(me),
		    objId,
		    objCon;
		if(!elem.attr("id")){
			  elem.attr("id", 'mytipsid' + parseInt(Math.random()*1000+1));
			}
		objId = elem.attr("id");
		if(settings.onlyShow){$('.mytips').hide();}
		if($("#" + objId + "Con").length < 1){
			   var conHtml = '<div class="mytips jquery-mytips '+ settings.position +'" id="' + objId + 'Con">'
			                 +'<span class="Validform_checktip"></span>'
			                 +'<span class="jquery-mytips-span">'
			                 +'<s class="jquery-mytips-s1">&#9670;</s>'
			                 +'<s class="jquery-mytips-s2">&#9670;</s>'
			                 +'</span></div>';
			     $("body").append(conHtml);
			}
		objCon = $("#" + objId + "Con");
		var elOffset = elem.offset(),
		    left = elOffset.left,
		    top = elOffset.top;
		objCon.find('.Validform_checktip').html(settings.msg);
		settings.position === 'mytipstop' ? (objCon.css({left : left, top : top-45}).show().animate({top : top-35},200)) : (objCon.css({left : left + elem.outerWidth() + parseInt(settings.plusOffset) + 10, top : top}).fadeIn());
		if(settings.timeout !== 0){
			if(objCon.data('clearTime')){
				  clearTimeout(objCon.data('clearTime'));
		       }
			objCon.data('clearTime',setTimeout(function(){
				  objCon.stop(true, true).fadeOut();
				}, settings.timeout));
		}
		return objCon;
	}; 
	$.fn.mytipsClose = function(){
		$('.mytips').hide();
	};
})(jQuery);