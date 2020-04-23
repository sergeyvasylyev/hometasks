$(document).ready(function() {
    getAccount();
});

function getAccount(){
    $.ajax({
            contentType: 'application/json',
            type:"GET",
            url:'/account/get',
            dataType: 'json',
            success:function(responsedata){
                console.log(responsedata);
                document.getElementById("account-name").value = responsedata.name;
               //location.reload();
            }
    })
}

function saveSettings(){
    var accountData = {
    name:document.getElementById("account-name").value,
    };
       $.ajax({
            contentType: 'application/json',
            type:"PUT",
            url:'/account',
            data:JSON.stringify(accountData),
            dataType: 'json',
            success:function(responsedata){
               //location.reload();
            }
        })
}