(function ($) {

    $.fn.laytable = function (ops){
        var el = $(this),
            settings = {
        	   el : el,
        	   moveEl : '.pay',
        	   endCallback : function(){}
           };
        $.extend(settings, ops);
        $.data(layDray, 'trDray', settings);
        var moveElem = settings.moveEl;
        el.on('mousedown', moveElem, function(e){
            if(e.button == 2){return false;}
            layDray.layStart(e, this);
        });
        el.on('mouseenter', moveElem, function (e){
            var n = $('a', el).index(this);
            if (n == layDray.dcoln) { return false; }
            layDray.dcolt = n; 
        });
        el.on('mouseleave', moveElem, function (e){
        	layDray.dcolt = null;
            $(this).removeClass('thover');
        });
    };
    var layDray = {
        shdivDelay : null,
        laySet: {},
        layStart: function (e, obj) {
            document.onselectstart = function (e) { return false; };
            var meData = $.data(this, 'trDray'),
                elem = meData.el[0];
            var ob = $(obj);
                this.laySet = $(elem).offset();
                this.laySet.right = this.laySet.left + $(elem).width();
                this.laySet.bottom = this.laySet.top + $(elem).height() + 200;
                //当前对象
                this.dcol = ob;
                //记录此项在列表中的索引
                this.dcoln = $('a', elem).index(ob);
                this.colCopy = document.createElement("div");
                this.colCopy.className = "colCopy";
                this.colCopy.innerHTML = $(ob).attr("data-name");

                $(this.colCopy).css({ position: 'absolute', float: 'left', display: 'none', textAlign: 'center' });
                $('body').append(this.colCopy);
            $(document).mousemove(function (e) { layDray.layMove(e); }).mouseup(function (e) { layDray.layEnd(e); });
        },
        layMove: function (e) {
            if (this.colCopy) {
                $(this.dcol).addClass('thMove').removeClass('thOver');
                if (e.pageX > this.laySet.right || e.pageX < this.laySet.left || e.pageY > this.laySet.bottom || e.pageY < this.laySet.top) {
                    $('body').css('cursor', 'pointer');
                }
                else {
                    $('body').css('cursor', 'move');
                    $(this.colCopy).css({ top: e.pageY + 10, left: e.pageX + 20, display: 'block' });
                }
            }
        },
        layEnd: function (e) {
            var meData = $.data(this, 'trDray'),
                elem = meData.el[0],
                endCallback = meData.endCallback;
            if (this.colCopy) {
                $(this.colCopy).remove();
                if (this.dcolt !== null && this.dcoln !== null) {
                    if (this.dcoln > this.dcolt) {
                        $('a:eq(' + this.dcolt + ')', elem).before(this.dcol);
                    }
                    else if (this.dcoln < this.dcolt) {
                        $('a:eq(' + this.dcolt + ')', elem).after(this.dcol);
                    }
                }
                this.dcol = null;
                this.laySet = null;
                this.dcoln = null;
                this.dcolt = null;
                this.colCopy = null;
                $('.thMove', elem).removeClass('thMove');
                $('body').css('cursor', 'default');
            }
            endCallback && endCallback();
            $(document).unbind();
            document.onselectstart = function (e) { return true; };
            return layDray.preventEvent(e);
        },
         getPageCoord:function(element){
            var coord = {x: 0, y: 0};
            while (element){
                coord.x += element.offsetLeft;
                coord.y += element.offsetTop;
                element = element.offsetParent;
            }
            return coord;
        },
        getOffset:function(obj, evt){
            if(!+[1,]) {
                var objset = $(obj).offset();
                var evtset = {
                    offsetX:evt.pageX || evt.screenX,
                    offsetY:evt.pageY || evt.screenY
                };
                var offset ={
                    offsetX: evtset.offsetX - objset.left,
                    offsetY: evtset.offsetY - objset.top
                };
                return offset;
            }
            var target = evt.target;
            if (target.offsetLeft == undefined){
                target = target.parentNode;
            }
            var pageCoord = layDray.getPageCoord(target);
            var eventCoord ={
                x: window.pageXOffset + evt.clientX,
                y: window.pageYOffset + evt.clientY
            };
            var offset ={
                offsetX: eventCoord.x - pageCoord.x,
                offsetY: eventCoord.y - pageCoord.y
            };
            return offset;
        },
        preventEvent: function (e) {
            if (e && e.stopPropagation) { e.stopPropagation(); }
            if (e && e.preventDefault) { e.preventDefault(); }
            return false;
        }
    };
})(jQuery);