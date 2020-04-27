$(document).ready(function() {
    getAllCourses();

    $('#courseTable').on('click-cell.bs.table', function (field, value, row, $el) {
            fillCourse($el);
    });
});

function fillCourse(elem){
    document.getElementById("id").value = elem.id;
    document.getElementById("name").value = elem.name;
    getCourse();
}

function getAllCourses(){
    $.ajax({
        type:"GET",
        url:'/course/all',
        dataType: "json",
        success:function(responsedata){
            $('#courseTable').bootstrapTable({data: responsedata})
            $('#courseTable').bootstrapTable('hideLoading');
        }
    })
}

function updateCourses(){
    $.ajax({
        type:"POST",
        url:'/course/update',
        dataType: "json",
        success:function(responsedata){
            location.reload();
//            $('#courseTable').bootstrapTable({data: responsedata})
//            $('#courseTable').bootstrapTable('hideLoading');
        }
    })
}

//-----------------------
function getCourse(){
    var var1 = document.getElementById("id").value;
    $.ajax({
                type:"GET",
                url:'/course/' + var1,
                dataType: "json",
                success:function(responsedata){
                    document.getElementById("id").value = responsedata.id;
                    document.getElementById("name").value = responsedata.name;
                    document.getElementById("descriptionHeading").value = responsedata.descriptionHeading;
                    document.getElementById("alternateLink").value = responsedata.alternateLink;
                    document.getElementById("enrollmentCode").value = responsedata.enrollmentCode;
                    document.getElementById("sourceOfFunds").value = responsedata.sourceOfFunds;
                    document.getElementById("courseState").value = responsedata.courseState;
                    document.getElementById("room").value = responsedata.room;
                    document.getElementById("section").value = responsedata.section;
                }
            })
}

function updateCourse(){
    var var1 = document.getElementById("id").value;
    $.ajax({
                type:"PUT",
                url:'/course/' + var1,
                dataType: "json",
                data:{
                    id:document.getElementById("id").value,
                    name:document.getElementById("name").value,
                    descriptionHeading:document.getElementById("descriptionHeading").value,
                    alternateLink:document.getElementById("alternateLink").value,
                    enrollmentCode:document.getElementById("enrollmentCode").value,
                    sourceOfFunds:document.getElementById("sourceOfFunds").value,
                    courseState:document.getElementById("courseState").value,
                    room:document.getElementById("room").value,
                    section:document.getElementById("section").value
                },
                success:function(responsedata){
                    location.reload();
                }
            })
}

function deleteCourse(){
    var var1 = document.getElementById("id").value;
    $.ajax({
                type:"DELETE",
                url:'/course/' + var1,
                dataType: "json",
                success:function(responsedata){
                    location.reload();
                }
            })
}