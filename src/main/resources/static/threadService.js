var threadService = {
    remove: function (id) {
        $.ajax({
            url: "/thread/"+id,
            type: 'DELETE',
            success: function (result) {
                window.location = result;
            }
        })
    }
};