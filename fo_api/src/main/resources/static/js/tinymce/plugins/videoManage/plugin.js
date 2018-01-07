
/**
 * Example plugin that adds a toolbar button and menu item.
 */

tinymce.PluginManager.add('videoManage', function(editor, url) {

    editor.addButton('videoManage', {
        tooltip:"视频",
        icon: "media",
        onclick: function() {
            sohuHD.videoManage(function(type,data){
                if(data){
                    var _player = '<div class="_sohutv_player mce-object mceNonEditable" contenteditable="false"><p style="padding: 100px;"></p><div class="_sohutv_player_param" style="display: none;">'+JSON.stringify(data)+'</div></div>';
                    editor.insertContent(_player);
                }
            });
        }
    });
});
