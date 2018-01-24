var summernoteService = {
    summernote: function (elementId) {
        $(elementId).wrap('<div></div>');
        $(  '<div class="sendPanel">' +
            ' <div class="text-right">' +
            '  <button class="btn btn-danger" onclick="summernoteService.close(\'' + elementId + '\')"><i class="fa fa-times"></i> Discard</button>' +
            '  <button class="btn btn-primary" onclick="postThread()"><i class="fa fa-send"></i> Send</button>' +
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
    }

/*function: sendReply() {
    var code = $('div#editor').summernote('code');
        var title = 'RE: '+[[${thread.title}]];
        $.post(url, {title: title, content: code})
            .done(function (data) {
                window.location.href = [[@{~/thread/{threadId}(threadId=${thread.id})}]] + '?page=' + data;
            });
    }*/
};