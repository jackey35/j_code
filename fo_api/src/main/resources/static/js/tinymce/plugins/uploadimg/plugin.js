/**
 * plugin.js
 *
 * Released under LGPL License.
 * Copyright (c) 1999-2015 Ephox Corp. All rights reserved
 *
 * License: http://www.tinymce.com/license
 * Contributing: http://www.tinymce.com/contributing
 */

/*jshint unused:false */
/*global tinymce:true */

/**
 * Example plugin that adds a toolbar button and menu item.
 */
tinymce.PluginManager.add('uploadimg', function(editor, url) {

    editor.addButton('uploadimg', {

        tooltip:"上传图片",
        icon: "image",
        onclick: function(ed) {

           var $dom = '<div id="_editor_fileupload_wrap" style="width: 180px;height: 60px;overflow: hidden;"><div href="javascript:void(0)" id="_editor_fileupload"></div></div>';
           var win = editor.windowManager.open({
                title: '插入图片',
                html: $dom,
                width: 180,
                height: 60,
                buttons: [{
                    text: 'Cancel',
                    onclick: 'close'
                }]
            });

            setTimeout(function(){
               var _flashUpload = new SWFUpload({
                    file_post_name: 'file',
                    flash_url: '//tv.sohu.com/upload/jq_plugin/swfupload/swfupload.swf',
                    upload_url: '//001.img.pu.sohu.com.cn/sdfs/photoUpload.do?',
                    post_params: {
                        'key': "c2hvd19waWNfa2V5",
                        'channelCode': 0,//渠道码：UGC统一填写3、 PGC统一填写8
                        'picType': 1 //0-允许上传gif图片（默认），1-不允许上传gif图片
                    },
                    file_size_limit: '2042',
                    file_types: '*.jpg;*.png;*.bmp;',
                    file_types_description: 'Image Files',
                    file_upload_limit :999,
                    file_queue_limit :10,
                    // Button settings
                    button_placeholder_id: '_editor_fileupload',
                    button_cursor : SWFUpload.CURSOR.HAND,
                    button_window_mode : SWFUpload.WINDOW_MODE.TRANSPARENT,
                    button_text : '',
                    button_image_url : "//css.tv.itc.cn/wemedia/eshop/images/uploadimg.png",
                    button_width: '180',
                    button_height: '60',
                    //event Handlers
                    swfupload_loaded_handler : function(){
                        _flashUpload.movieElement.style.width = "180px";
                        _flashUpload.movieElement.style.height = "60px";
                    },
                    file_dialog_complete_handler: function () {
                        try {
                            this.startUpload();
                        }
                        catch (ex) {
                            this.debug(ex);
                        }
                    },
                    file_queue_error_handler: function (file, errorCode) {
                        if (errorCode == -110) {
                            alert('上传图片不能超过2M');
                        }
                    },
                    upload_error_handler: function (file, errorCode, message) {
                        alert([errorCode, message]);
                    },
                    upload_success_handler: function (file, serverData) {
                        var data = JSON.parse(serverData);
                        var _img = '//001.img.pu.sohu.com.cn/'+data.message;
                        editor.insertContent('<img src="' + _img +'">');
                        editor.nodeChanged();


                    },
                    upload_complete_handler :function uploadComplete(file) {
                        try {
                            if (this.getStats().files_queued === 0) {
                                document.getElementById(this.customSettings.cancelButtonId).disabled = true;
                            } else {
                                this.startUpload();
                            }
                        } catch (ex) {
                            this.debug(ex);
                        }
                    },
                   queue_complete_handler : function () {
                       // win.close();

                   }
                });


            },200);





        }
    });
});
