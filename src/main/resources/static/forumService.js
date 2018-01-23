var forumService = {
    remove: function (id) {
        $.ajax({
            url: "/forum/"+id,
            type: 'DELETE',
            success: function (result) {
                window.location = '/';
            }
        })
    },

    create: function (title, parentId) {
        $.ajax({
            url: "/forum/",
            type: 'POST',
            data: {title: title, parentId: parentId},
            success: function (result) {
                window.location = result;
            }
        })
    },
    
    addMod: function (forumId, userId) {
        $.ajax({
            url: "/forum/" + forumId + "/moderators",
            type: 'POST',
            data: {userId: userId},
            success: function (result) {
                window.location = result;
            }
        })
    },

    removeMod: function (forumId, userId) {
        $.ajax({
            url: "/forum/" + forumId + "/moderators/" + userId,
            type: 'DELETE',
            success: function (result) {
                window.location = result;
            }
        })
    },
};