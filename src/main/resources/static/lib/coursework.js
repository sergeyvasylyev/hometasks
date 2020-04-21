$(document).ready(function() {
    getAllCourseworks();
});

function getAllCourseworks(){
    $.ajax({
        type:"GET",
        url:'/coursework/all',
        dataType: "json",
        success:function(responsedata){
            $('#courseworkTable').bootstrapTable({data: responsedata})
            $('#courseworkTable').bootstrapTable('hideLoading');
        }
    })
}