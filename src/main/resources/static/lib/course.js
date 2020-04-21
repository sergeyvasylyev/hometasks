$(document).ready(function() {
    getAllCourses();
});

function getAllCourses(){
    $.ajax({
        type:"GET",
        url:'/course/all',
        dataType: "json",
        success:function(responsedata){
            $('#courseTable').bootstrapTable({data: responsedata})
            $('#courseTable').bootstrapTable('hideLoading');
        }
    })
}

function updateCourses(){
    $.ajax({
        type:"POST",
        url:'/course/update',
        dataType: "json",
        success:function(responsedata){
            location.reload();
//            $('#courseTable').bootstrapTable({data: responsedata})
//            $('#courseTable').bootstrapTable('hideLoading');
        }
    })
}