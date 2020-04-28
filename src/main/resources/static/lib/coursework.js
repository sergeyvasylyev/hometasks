$(document).ready(function() {
    getAllCourseworks();

    $('#courseworkTable').on('click-cell.bs.table', function (field, value, row, $el) {
                fillCourse($el);
        });

});

function fillCourse(elem){
    document.getElementById("id").value = elem.id;
    document.getElementById("title").value = elem.name;
    getCoursework();
}

function getAllCourseworks(){
    $.ajax({
        type:"GET",
        url:'/coursework/all',
        dataType: "json",
        success:function(responsedata){
            $('#courseworkTable').bootstrapTable({data: responsedata})
            $('#courseworkTable').bootstrapTable('hideLoading');
        }
    })
}

//-----------------------
function getCoursework(){
    var var1 = document.getElementById("id").value;
    $.ajax({
                type:"GET",
                url:'/coursework/' + var1,
                dataType: "json",
                success:function(responsedata){
                    document.getElementById("id").value = responsedata.id;
                    document.getElementById("dueDate").value = responsedata.dueDate;
                    document.getElementById("description").value = responsedata.description;
                    document.getElementById("alternateLink").value = responsedata.alternateLink;
                    document.getElementById("title").value = responsedata.title;
                    document.getElementById("courseId").value = responsedata.course.id;
                    document.getElementById("creationTime").value = responsedata.creationTime;
                    document.getElementById("maxPoints").value = responsedata.maxPoints;
                    document.getElementById("state").value = responsedata.state;
                    document.getElementById("updateTime").value = responsedata.updateTime;
                    document.getElementById("workType").value = responsedata.workType;
                    document.getElementById("topicId").value = responsedata.topicId;
                }
            })
}

function updateCoursework(){
    var var1 = document.getElementById("id").value;
    $.ajax({
                type:"PUT",
                url:'/coursework/' + var1,
                dataType: "json",
                data:{
                    id:document.getElementById("id").value,
                    dueDate:document.getElementById("dueDate").value,
                    description:document.getElementById("description").value,
                    alternateLink:document.getElementById("alternateLink").value,
                    title:document.getElementById("title").value,
                    courseId:document.getElementById("courseId").value,
                    creationTime:document.getElementById("creationTime").value,
                    maxPoints:document.getElementById("maxPoints").value,
                    state:document.getElementById("state").value,
                    updateTime:document.getElementById("updateTime").value,
                    workType:document.getElementById("workType").value,
                    topicId:document.getElementById("topicId").value
                },
                success:function(responsedata){
                    location.reload();
                }
            })
}

function deleteCoursework(){
    var var1 = document.getElementById("id").value;
    $.ajax({
                type:"DELETE",
                url:'/coursework/' + var1,
                dataType: "json",
                success:function(responsedata){
                    location.reload();
                }
            })
}