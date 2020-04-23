$(document).ready(function() {
    getAllSubscribers();

    $('#subscriberTable').on('click-cell.bs.table', function (field, value, row, $el) {
        fillSubscriber($el);
    });
});

function updateSubscriber(){
    var var1 = document.getElementById("id").value;
       $.ajax({
            type:"PUT",
            url:'/subscriber/'+var1,
            data:{
                id:document.getElementById("id").value,
                name:document.getElementById("name").value,
                description:document.getElementById("description").value,
                active:document.getElementById("active").checked,
                chatId:document.getElementById("chatId").value
            },
            success:function(responsedata){
               location.reload();
            }
        })
}

function getSubscriber(){
    var var1 = document.getElementById("id").value;
    $.ajax({
                type:"GET",
                url:'/subscriber/' + var1,
                dataType: "json",
                success:function(responsedata){
                    fillSubscriber(responsedata);
                }
            })
}

function fillSubscriber(elem){
    document.getElementById("id").value = elem.id;
    document.getElementById("name").value = elem.name;
    document.getElementById("description").value = elem.description;
    document.getElementById("chatId").value = elem.chatId;
    document.getElementById("active").checked=elem.active;
}

function getAllSubscribers(){
    $.ajax({
        type:"GET",
        url:'/subscriber/all',
        dataType: "json",
        success:function(responsedata){
            $('#subscriberTable').bootstrapTable({data: responsedata})
            $('#subscriberTable').bootstrapTable('hideLoading');
        }
    })
}