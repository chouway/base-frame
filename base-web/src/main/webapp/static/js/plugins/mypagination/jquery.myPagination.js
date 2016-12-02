/*!
 * myPagination Jquery Pagination Plug-in Library v5.1
 * 
 * http://linapex.blog.163.com/
 *
 * Date: 2012/11/17 14:57
 */
;(function($) {
    function init(param, obj) {
        function getParam() {
            var a = "page=" + opts.currPage;
            if (debug("data type:" + typeof opts.ajax.param), opts.ajax.param) if (typeof opts.ajax.param == "object") {
                var b = "&";
                for (var c in opts.ajax.param) debug("key\uff1a" + c + ",value\uff1a" + opts.ajax.param[c]),
                b += c + "=" + opts.ajax.param[c] + "&";
                b = b.length == 1 ? "": b.substring(0, b.length - 1),
                a += b
            } else typeof opts.ajax.param == "string" && (a += "&" + opts.ajax.param);
            return a
        }
        function checkInputPage(a) {
            var b;
            1 > a && (b = "\u8f93\u5165\u503c\u4e0d\u80fd\u5c0f\u4e8e1");
            var c = /^[0-9]{1,8}$/;
            return c.exec(a) || (b = "\u8bf7\u8f93\u5165\u6b63\u786e\u7684\u6570\u5b57"),
            a > opts.pageCount && (b = "\u8f93\u5165\u503c\u4e0d\u80fd\u5927\u4e8e\u603b\u9875\u6570"),
            b ? (alert(b), !1) : !0
        }
        function getPanelTipInfo() {
            var str = "";
            if (opts.panel.tipInfo_on) {
                var input = "<input type='text' value='" + opts.currPage + "' >",
                info = opts.panel.tipInfo;
                info = info.replace("{currText}", input),
                info = info.replace("{sumPage}", opts.pageCount),
                info = $(info),
                input = info.children(":text:first");
                var css = opts.panel.tipInfo_css;
                for (var temp in css) {
                    var val = eval("css." + temp);
                    input.css(temp, val)
                }
                str = info.html()
            }
            return str
        }
        function onRequest() {
            debug(opts.id),
            debug("ajax\u8bf7\u6c42\u53c2\u6570\u5217\u8868:"),
            debug(getParam()),
            opts.ajax.on ? (opts.ajax.ajaxStart(), $.ajax({
                url: opts.ajax.url,
                type: opts.ajax.type,
                data: getParam(),
                contentType: "application/x-www-form-urlencoded;utf-8",
                async: !0,
                cache: !1,
                timeout: 6e4,
                error: function() {
                    alert("\u8bbf\u95ee\u670d\u52a1\u5668\u8d85\u65f6\uff0c\u8bf7\u91cd\u8bd5\uff0c\u8c22\u8c22\uff01")
                },
                success: function(a) {
                    responseHandle(a),
                    createPageBar()
                }
            })) : createPageBar()
        }
        function responseHandle(data) {
            var pageCountId = opts.ajax.pageCountId,
            resultPageCount = 1;
            switch (opts.ajax.dataType) {
            case "json":
                try {
                    data = eval("(" + data + ")")
                } catch(err) {} finally {
                    resultPageCount = eval("data.result." + pageCountId)
                }
                break;
            case "xml":
                resultPageCount = $(data).find(pageCountId).text();
                break;
            default:
                resultPageCount = $(data).find(":hidden[id='" + pageCountId + "']").val()
            }
            debug(opts.id),
            debug("\u8fd4\u56de\u603b\u9875\u6570:" + resultPageCount),
            opts.pageCount = resultPageCount,
            opts.ajax.callback(data)
        }
        function createPageBar() {
            var a = opts.panel.links;
            opts.currPage = opts.currPage > opts.pageCount ? opts.pageCount: opts.currPage;
            var b = opts.currPage,
            c = parseInt(opts.pageCount),
            d = parseInt(opts.pageNumber / 2),
            e = opts.pageNumber,
            f = "";
            //if(c == 0) {obj.html('') return;}
            opts.panel.first_on && (f = "<a href='" + a + "' title='1' class='pre-to-begin'>" + opts.panel.first + "</a>"),
            opts.panel.prev_on && (f += b == 1 ? '<a class="disabled pre" title="' + opts.panel.prev + '">' + opts.panel.prev + " </a>": "<a class='pre' href='" + a + "' title='" + (b - 1) + "'>" + opts.panel.prev + " </a>");
            var g = lastPage = 1;
            /*for (g = b - d > 0 ? g = b - d: 1, g + e > c ? (lastPage = c + 1, g = lastPage - e) : lastPage = g + e, 0 >= g && (g = 1), g; lastPage > g; g++)
            	f += g == b ? '<span class="current" title="' + g + '">' + g + "</span>": "<a href='" + a + "' title='" + g + "'>" + g + "</a>";*/
            for (var i = (parseInt(b) - 2) > 0 ? ((((parseInt(c) - parseInt(b) + 1) < 3) ? parseInt(c) - 2 : parseInt(b))) : 1; i <= ((parseInt(b) < 3 && parseInt(c) >= 3) ? 3 : ((parseInt(b) +2) <= parseInt(c) ? (parseInt(b) +2) : parseInt(c))); i++){
  			  if (i == parseInt(b)){
                  f +="<a href='" + a + "' class='current' title='" + i + "' paged='" + i + "'>" + i + "</a>";
                 }else{
                  f += "<a href='" + a + "' title='" + i + "' paged='" + i + "'>" + i + "</a>";
                 }
  			}
  			if ((parseInt(b) + 3) <= parseInt(c) && parseInt(c) > 4){
  				if((parseInt(b) + 3) < parseInt(c) || parseInt(c) <= 5) {
              		f += "<span class='spec'>...</span>";
  				}
                  f += "<a href='" + a + "'  title='" + c + "' paged='" + c + "'>" + c + "</a>";
              }
            opts.panel.next_on && (f += b == c ? '<a class="disabled next" title="' + opts.panel.next + '">' + opts.panel.next + " </a>": "<a class='next' href='" + a + "' title='" + (b + 1) + "'>" + opts.panel.next + " </a>"),
            opts.panel.last_on && (f += "<a class='next-to-end' href='" + a + "' title='" + c + "'>" + opts.panel.last + "</a>"),
            f += getPanelTipInfo(),
            debug(opts.id),
            debug("\u6700\u7ec8\u751f\u6210\u83dc\u5355\uff1a"),
            debug(f),
            obj.html(f),
            obj.children(":text").keypress(function(a) {
                var b = a.which;
                if (b == 13) {
                    var c = $(this).val();
                    checkInputPage(c) && (obj.children("a").unbind("click"), obj.children("a").each(function() {
                        $(this).click(function() {
                            return ! 1
                        })
                    }), opts.currPage = c, onRequest())
                }
            }),
            obj.children("a").each(function() {
                $(this).click(function() {
                    var a = parseInt(this.title);
                    return a = a > 0 ? a: 1,
                    obj.children("a").unbind("click"),
                    obj.children("a").each(function() {
                        $(this).click(function() {
                            return ! 1
                        })
                    }),
                    opts.currPage = a,
                    opts.ajax.onClick(a),
                    onRequest(),
                    $(this).focus(),
                    !1
                })
            })
        }
        function debug(a) {
            opts.debug && $.fn.debug(a)
        }
        var defaults = {
            currPage: 1,
            pageCount: 10,
            pageNumber: 5,
            cssStyle: "badoo",
            debug: !1,
            ajax: {
                on: !1,
                type: "POST",
                pageCountId: "pageCount",
                url: "jsonTest.php",
                dataType: "json",
                param: !1,
                onClick: function() {
                    return ! 1
                },
                ajaxStart: function() {
                    return ! 1
                },
                callback: function() {
                    return ! 1
                }
            },
            panel: {
                first: "\u9996\u9875",
                last: "\u5c3e\u9875",
                next: "\u4e0b\u4e00\u9875",
                prev: "\u4e0a\u4e00\u9875",
                first_on: !0,
                last_on: !0,
                next_on: !0,
                prev_on: !0,
                links: "#",
                tipInfo_on: !1,
                tipInfo: "<span>&nbsp;&nbsp;\u8df3{currText}/{sumPage}\u9875</span>",
                tipInfo_css: {
                    width: "22px"
                }
            }
        },
        opts = $.extend(!0, defaults, param);
        opts.id = obj.attr("id"),
        obj.addClass(opts.cssStyle),
        onRequest();
        var method = {};
        return method.getPage = function() {
            return this.currPage
        },
        method.onReload = function() {
            debug("reload()"),
            onRequest()
        },
        method.onLoad = function(a) {
            a && a instanceof Object && (debug(a), opts.currPage = 1, opts.ajax.param = a.param, onRequest())
        },
        method.jumpPage = function(a) {
            debug("jumpPage()"),
            a = 1 > a ? 1 : a,
            a = a > opts.pageCount ? opts.pageCount: a,
            opts.currPage = a,
            onRequest()
        },
        method
    }
    $.fn.myPagination = function(a) {
        return init(a, $(this))
    },
    $.fn.debug = function(a) {
        window.console && window.console.log && console.log(a)
    }
})(jQuery)