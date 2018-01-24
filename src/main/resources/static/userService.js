var userService = {
    remove: function (id) {
        $.ajax({
            url: "/user/"+id,
            type: 'DELETE',
            success: function (result) {
                window.location = result;
            }
        })
    },

    setRole: function (id, role) {
        $.ajax({
            url: "/user/"+id+'/roles',
            type: 'PUT',
            data: {role: role},
            success: function (result) {
                window.location = result;
            }
        })
    }
};