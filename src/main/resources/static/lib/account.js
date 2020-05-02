$(document).ready(function() {
    //getAccount();
    getAllAccounts();
    document.getElementById("google-authorization-link").hidden = true;

    $('#accountTable').on('click-cell.bs.table', function (field, value, row, $el) {
            fillAccount($el);
    });
});

//function getAccount(){
//    $.ajax({
//            contentType: 'application/json',
//            type:"GET",
//            url:'/account/get',
//            dataType: 'json',
//            success:function(responsedata){
//                console.log(responsedata);
//                document.getElementById("account-name").value = responsedata.name;
//                document.getElementById("is-default").checked = responsedata.isDefault;
//               //location.reload();
//            }
//    })
//}

//function saveSettings(){
//    var accountData = {
//    name:document.getElementById("name").value,
//    isDefault:document.getElementById("isDefault").checked
//    };
//       $.ajax({
//            contentType: 'application/json',
//            type:"PUT",
//            url:'/account',
//            data:JSON.stringify(accountData),
//            dataType: 'json',
//            success:function(responsedata){
//               //location.reload();
//            }
//        })
//}

function updateAccount(){
    var accountData = {
        name:document.getElementById("name").value,
        isDefault:document.getElementById("isDefault").checked
        };
       $.ajax({
            contentType: 'application/json',
            type:'POST',
            url:'/account',
            data:JSON.stringify(accountData),
            dataType: 'json',
            success:function(responsedata){
               location.reload();
            }
        })
}

function getAllAccounts(){
    $.ajax({
        type:"GET",
        url:'/account/all',
        dataType: "json",
        success:function(responsedata){
            $('#accountTable').bootstrapTable({data: responsedata})
            $('#accountTable').bootstrapTable('hideLoading');
        }
    })
}

function fillAccount(elem){
    document.getElementById("name").value = elem.name;
    document.getElementById("isDefault").checked = elem.isDefault;
}

//==============================================
function authorize(){
    //getGoogleAuth();
    $.ajax({
            type:"GET",
            url:'/google/setup',
            data:{name:document.getElementById("name").value},
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

//=========================
function getSettings(){
//    $.ajax({
//            type:"GET",
//            url:'/settings',
//            data:{name:document.getElementById("name").value},
//            success:function(responsedata){
//                console.log(responsedata);
//            }
//    })
     var name = document.getElementById("name").value;
        if (name) {
            window.location = '/settings?name=' + name;
        }
}