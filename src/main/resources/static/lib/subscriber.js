$(document).ready(function() {
    getAllSubscribers();
    $('#subscriberTable').on('click-cell.bs.table', function (field, value, row, $el) {
        fillSubscriber($el);
    });

    getAllAccounts();
    $('#subscriberAccountsTable').bootstrapTable({
        formatNoMatches: function () {
            return '';
        }
    });
    $('#subscriberAccountsTable').bootstrapTable('hideLoading');

});

function updateSubscriber(){
    var var1 = document.getElementById("id").value;
    var requestType = 'POST';
    var requestUrl = '/subscriber';
    if (var1){
        requestType = 'PUT';
        requestUrl += '/'+var1
    };

    var accountToAdd = [];
    $("tr.item").each(function() {
        accountToAdd.push({
                name:$(this).find("input.form-control").val()
            });
    });
    var accountsData = {
        id:document.getElementById("id").value,
        name:document.getElementById("name").value,
        description:document.getElementById("description").value,
        active:document.getElementById("active").checked,
        chatId:document.getElementById("chatId").value,
        accounts:accountToAdd
    };
       $.ajax({
            contentType: 'application/json',
            type:requestType,
            url:requestUrl,
            data:JSON.stringify(accountsData),
            dataType: 'json',
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
    document.getElementById("active").checked = elem.active;

    $("#subscriberAccountsTable > tbody").empty();
    elem.accounts.forEach(function(item){
        fillSubscriberAccountTable(item.name);
    });
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

function fillSubscriberTable(){
    var subscribersData =$('#subscriberTable').bootstrapTable('getData');
    console.log(subscribersData);
    console.log(subscribersData.length);
}

//==================================
//subscriber accounts
function addAccountItem(item, index){
      $('#account').append('<option value="'+item.name+'">'+item.name+'</option>');
      $("#account").val(item.name);
      $("#account").selectpicker("refresh");
}

function getAllAccounts(){
    $.ajax({
        type:"GET",
        url:'/account/all',
        dataType: "json",
        success:function(responsedata){
            responsedata.forEach(addAccountItem);
        }
    })
}

function addAccount(){
    var selectedAcc = $('.selectpicker').val();
    fillSubscriberAccountTable(selectedAcc);
}

function deleteAccount(item){
    var p=item.parentNode.parentNode;
        p.parentNode.removeChild(p);
}

function fillSubscriberAccountTable(item){
        var newRow = $('<tr class="item">');
        var cols = '<td><input type="text" class="form-control" value="' + item + '" readonly/></td>';
        cols += '<td><button type="submit" class="btn btn-secondary" onclick="deleteAccount(this)">-</button></td>';
        newRow.append(cols);
        $('#subscriberAccountsTable').append(newRow);
        $('#subscriberAccountsTable').bootstrapTable('refresh');
}