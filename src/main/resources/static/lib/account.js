$(document).ready(function() {
    getAccount();
    document.getElementById("google-authorization-link").hidden = true;

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
    //getGoogleAuth();
    $.ajax({
            type:"GET",
            url:'/google/setup',
            data:{name:document.getElementById("account-name").value},
            success:function(responsedata){
                document.getElementById("google-authorization-link").hidden = false;
                document.getElementById("google-authorization-link").href = responsedata;
            }
    })
}

function getGoogleAuth(){
$.ajax({
            type:"GET",
//            headers: {
//                    'Access-Control-Allow-Origin': '*',
//                    'Access-Control-Allow-Methods': 'GET, POST, OPTIONS',
//                    'Access-Control-Allow-Headers': 'DNT,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Range',
//                    'Access-Control-Expose-Headers': 'Content-Length,Content-Range'
//                },
            url:'https://accounts.google.com/o/oauth2/auth?access_type=offline&client_id=1687532898-j712qggo8ultk4ljddn0ork5psfr2dpp.apps.googleusercontent.com&redirect_uri=http://localhost:8080/google/auth&response_type=code&scope=https://www.googleapis.com/auth/classroom.courses.readonly%20https://www.googleapis.com/auth/classroom.coursework.me.readonly%20https://www.googleapis.com/auth/classroom.topics.readonly&state=s.vasylyev@gmail.com',
            success:function(responsedata){
            }
    })
}