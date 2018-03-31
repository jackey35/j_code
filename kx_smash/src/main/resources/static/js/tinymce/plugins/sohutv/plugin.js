/**
 * plugin.js
 *
 * Released under LGPL License.
 * Copyright (c) 1999-2015 Ephox Corp. All rights reserved
 *
 * License: http://www.tinymce.com/license
 * Contributing: http://www.tinymce.com/contributing
 */

/*jshint maxlen:255 */
/*eslint max-len:0 */
/*global tinymce:true */

/**
 * plugin.js
 *
 * Released under LGPL License.
 * Copyright (c) 1999-2015 Ephox Corp. All rights reserved
 *
 * License: http://www.tinymce.com/license
 * Contributing: http://www.tinymce.com/contributing
 */

/*global tinymce:true */

tinymce.PluginManager.add('sohutv', function(editor) {


	function sanitize(html) {
		if (editor.settings.media_filter_html === false) {
			return html;
		}

		var writer = new tinymce.html.Writer(), blocked;

		new tinymce.html.SaxParser({
			validate: false,
			allow_conditional_comments: false,
			special: 'script',

			comment: function(text) {
				writer.comment(text);
			},

			cdata: function(text) {
				writer.cdata(text);
			},

			text: function(text, raw) {
				writer.text(text, raw);
			},

			start: function(name, attrs, empty) {
				blocked = true;

				if (name == 'script' || name == 'noscript') {
					return;
				}
				for (var i = 0; i < attrs.length; i++) {
					if (attrs[i].name.indexOf('on') === 0) {
						return;
					}

					if (attrs[i].name == 'style') {
						attrs[i].value = editor.dom.serializeStyle(editor.dom.parseStyle(attrs[i].value), name);
					}
				}

				writer.start(name, attrs, empty);
				blocked = false;
			},

			end: function(name) {
				if (blocked) {
					return;
				}

				writer.end(name);
			}
		}).parse(html);
		return writer.getContent();
	}



	function showDialog() {
		var data = {}, selection = editor.selection, dom = editor.dom, selectedElm;
		var win, vid, width,  height, autoplay, plid;





		selectedElm = selection.getNode();
		_thisEle = dom.getParent(selectedElm, '.mce-object');
		data.vid = _thisEle ? dom.getAttrib(_thisEle, 'vid') : '';
		data.bid = _thisEle ? dom.getAttrib(_thisEle, 'bid') : '';
		data.width = _thisEle ? dom.getAttrib(_thisEle, 'width') : '';
		data.height = _thisEle ? dom.getAttrib(_thisEle, 'height') : '';


		win = editor.windowManager.open({
			title: 'Insert link',
			data: data,
			body: [
				{
					name: 'vid',
					type: 'textbox',
					label: 'vid(vrs)',
					autofocus: true,
					onchange: function(){
						data.vid = this.value();
					},
					onkeyup: function(){
						data.vid = this.value();
					}
				},
				{
					name: 'bid',
					type: 'textbox',
					label: 'bid(ugc)',
					autofocus: true,
					onchange: function(){
						data.bid = this.value();
					},
					onkeyup: function(){
						data.bid = this.value();
					}
				},
				{
					name: 'width',
					type: 'textbox',
					label: '宽(px)',
					onchange: function(){
						data.width = this.value();
					},
					onkeyup: function(){
						data.width = this.value();
					}
				},
				{
					name: 'height',
					type: 'textbox',
					label: '高（px）',
					onchange: function(){
						data.height = this.value();
					},
					onkeyup: function(){
						data.height = this.value();
					}
				},
				{
					name: 'rate',
					type: 'textbox',
					label: 'h5比例(16:9)',
					onchange: function(){
						data.rate = this.value();
					},
					onkeyup: function(){
						data.rate = this.value();
					}
				}
			],

			onSubmit: function(e) {
				var _name = "sohuplayer"+ Math.ceil(Math.random()*100);
				var _w =data.width;
				var _h =data.height;

				var _param = {
					autoplay:false,
					getHTML :1
				};

				if(data.vid){
					_param['vid']= data.vid;
				}else{
					_param['bid'] =data.bid;
				}

				if(data.rate){
					var rates = data.rate.split(":");

					_param["width"] = "100%";
					_param["height"] = "100%"

					var dom = ['<div class="mce-object" id="',_name,'"></div><script type="text/javascript">',
						,'document.getElementById("',_name,'").style.height=document.body.clientWidth*',rates[1],'/',rates[0] ,'+"px";'
						,'document.getElementById("',_name,'").innerHTML = showVrsPlayer('+JSON.stringify(_param)+');'
						,'</script>'
					].join('');
				}else{
					_param["width"] = _w;
					_param["height"] = _h;

					var dom = ['<div class="mce-object" id="',_name,'" style="width:',_w,'px;height:',_h,'px;"></div><script type="text/javascript">',
						,'document.getElementById("',_name,'").innerHTML = showVrsPlayer('+JSON.stringify(_param)+');'
						,'</script>'
					].join('');
				}


				editor.insertContent(dom);
			}
		});
	}


	editor.addButton('sohutv', {
		tooltip: 'Insert/edit video',
		icon: 'media',
		onclick: showDialog,
		stateSelector: ['img[data-mce-object]', 'span[data-mce-object]']
	});


});

