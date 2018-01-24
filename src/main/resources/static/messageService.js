var messageService = {
    remove: function (id) {
        $.ajax({
            url: "/message/"+id,
            type: 'DELETE',
            success: function (result) {
                window.location = result;
            }
        })
    },
};