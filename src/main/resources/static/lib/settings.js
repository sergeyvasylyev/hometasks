function forceJob(){
    $.ajax({
                type:"GET",
                url:'/settings/force',
                dataType: "json",
                success:function(responsedata){

                }
            })
}