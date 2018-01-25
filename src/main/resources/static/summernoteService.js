var summernoteService = {
    summernote: function (elementId, msgId) {
        $(elementId).wrap('<div></div>');
        $(  '<div class="sendPanel">' +
            ' <div class="text-right">' +
            '  <button class="btn btn-danger" onclick="summernoteService.close(\'' + elementId + '\')"><i class="fa fa-times"></i> Discard</button>' +
            '  <button class="btn btn-primary" onclick="summernoteService.edit(\'' + elementId + '\',' + msgId + ')""><i class="fa fa-send"></i> Send</button>' +
            ' </div>\n' +
            '</div>').appendTo($(elementId).parent());


        $(elementId).summernote({
            placeholder: 'Write your message here...',
            height: 300
        });
    },
    
    close: function (elementId) {
        $(elementId).summernote('destroy');
        $(elementId).parent().find('div.sendPanel').remove();
        $(elementId).unwrap();
    },

    edit: function (elementId, msgId) {
        var code = $(elementId).summernote('code');
        $.ajax({
            url: "/message/" + msgId,
            type: 'PUT',
            data: {message: code},
            success: function (result) {
                window.location = result;
            }
        })
    }
};