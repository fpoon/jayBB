var userService = {
    remove: function (id) {
        $.ajax({
            url: "/user/"+id,
            type: 'DELETE',
            success: function (result) {
                window.location = result;
            }
        })
    }
};