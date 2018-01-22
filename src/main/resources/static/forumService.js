var forumService = {
    remove: function (id) {
        $.ajax({
            url: "/forum/"+id,
            type: 'DELETE',
            success: function (result) {
                window.location = '/';
            }
        })
    }
};