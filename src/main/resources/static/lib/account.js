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
                document.getElementById("is-default").checked = responsedata.isDefault;
               //location.reload();
            }
    })
}

function saveSettings(){
    var accountData = {
    name:document.getElementById("account-name").value,
    isDefault:document.getElementById("is-default").checked
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

function authorize(){
    $.ajax({
            type:"GET",
            url:'/google/setup',
            data:{name:document.getElementById("account-name").value},
            success:function(responsedata){
            }
    })
}