$(document).ready(function() {
    getSettings();
});

function getSettings(){
$.ajax({
                type:"GET",
                url:'/settings/getDefault',
                dataType: "json",
                success:function(responsedata){
                    document.getElementById("account-name").value = responsedata.name;
                    document.getElementById("is-default").checked = responsedata.isDefault;
                    responsedata.appSettings.forEach(fillPageData);
                }
            })
}

function fillPageData(item, index){
    document.getElementById(item.settingType).value = item.settingData;
}

function saveSettings(){
var settingsList = ["GOOGLE_APP_NAME",
    "GOOGLE_APP_CREDENTIALS",
    "GOOGLE_SHEETS_SPREADSHEET_ID",
    "TELEGRAM_BOT_NAME",
    "TELEGRAM_BOT_USERNAME",
    "TELEGRAM_BOT_TOKEN",
    "JOB_GET_COURSES_STATUS",
    "JOB_GET_COURSES_HISTORY_STATUS",
    "GOOGLE_SHEETS_USE_STATUS"];

var appSettingToAdd = [];
    settingsList.forEach((element) => {
        appSettingToAdd.push({
                settingType:element,
                settingData:document.getElementById(element).value
            });
    });

var accountData = { name:document.getElementById("account-name").value,
                    isDefault:document.getElementById("is-default").checked,
                    appSettings:appSettingToAdd
                  };
       $.ajax({
            contentType: 'application/json',
            type:"PUT",
            url:'/settings',
            data:JSON.stringify(accountData),
            dataType: 'json',
            success:function(responsedata){
               //location.reload();
            }
        })
}
