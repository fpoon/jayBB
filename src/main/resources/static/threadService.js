var threadService = {
    remove: function (id) {
        $.ajax({
            url: "/thread/"+id,
            type: 'DELETE',
            success: function (result) {
                window.location = result;
            }
        })
    },

    stick: function (id) {
        $.ajax({
            url: "/thread/"+id+'/stick',
            type: 'POST',
            success: function (result) {
                window.location = result;
            }
        })
    }
};