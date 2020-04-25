$(document).ready(function() {
    getJobHistory();
});

function getJobHistory(){
    $.ajax({
        type:"GET",
        url:'/settings/jobHistory',
        dataType: "json",
        success:function(responsedata){
            $('#jobHistoryTable').bootstrapTable({data: responsedata})
            $('#jobHistoryTable').bootstrapTable('hideLoading');
        }
    })
}

function forceJob(){
    $.ajax({
                type:"GET",
                url:'/settings/force',
                dataType: "json",
                success:function(responsedata){
                    //force success
                }
            })
}