/**
 * 예약 처리 관련 자바스크립트
 */

function reservation_check() {
    // 이벤트 기본 동작 방지
    // event.preventDefault(); // 폼 제출 방지

    // 입력한 값 가져오기    
    var checkin = document.getElementById("checkin").value;
    var checkout = document.getElementById("checkout").value;
    var bottom = document.getElementById("bottom").value;
    var people = document.getElementById("people").value;
  

    var systemDate = new Date();
    var systemDateString = systemDate.toISOString().split('T')[0];  // 현재 날짜

    // 유효성 검사
    if (checkin === "") {
        alert("체크인 날짜를 입력해 주세요.");
        $("#checkin").focus();
        return false;
    } else if (checkout === "") {
        alert("체크아웃 날짜를 입력해 주세요.");
        $("#checkout").focus();
        return false;
    } else if (bottom === "") {
        alert("바닥재를 선택해 주세요.");
        $("#bottom").focus();
        return false;
    } else if (people === "") {
        alert("인원수를 입력해 주세요.");
        $("#people").focus();
        return false;
    } else if (people > 10) {
        alert("인원수가 너무 많습니다.");
        $("#people").focus();
        return false;
    } else if (people < 1) {
        alert("인원수를 확인해 주세요.");
        $("#people").focus();
        return false;
    } else {
        // 날짜 비교
        var checkinDate = new Date(checkin);
        var checkoutDate = new Date(checkout);
        var systemDateObj = new Date(systemDateString);

        if (checkinDate < systemDateObj || checkoutDate < checkinDate || checkoutDate < systemDateObj) {
            alert('날짜를 확인해주세요');
            return false;
        } else {
            // 숨겨진 input에 체크인/체크아웃 값 설정
            document.getElementById("checkinValue").value = checkin;
            document.getElementById("checkoutValue").value = checkout;

            // 예약 폼 제출
 
        }
    }
}

// 날짜가 변경될 때 폼의 값 업데이트
function updateFormValues() {
    var checkin = document.getElementById("checkin").value;
    var checkout = document.getElementById("checkout").value;

    document.getElementById("checkinValue").value = checkin;
    document.getElementById("checkoutValue").value = checkout;
}

function updateRemainingRooms() {
    var checkinDate = $('#checkin').val();
    var checkoutDate = $('#checkout').val();
    var campingid = document.getElementById("campingid").value;
    var room = document.getElementById("room").value;

    console.log("checkinDate: ", checkinDate);  // checkinDate 값 로그
    console.log("checkoutDate: ", checkoutDate);  // checkoutDate 값 로그
    console.log("campingId: ", campingid);  // campingId 값 로그
    console.log("room: ", room);  // room 값 로그

    if (checkinDate && checkoutDate) {
        $.ajax({
            url: '/checkRemainingRooms',  // 서버 엔드포인트
            method: 'GET',
            data: {
                checkin: checkinDate,
                checkout: checkoutDate,
                campingId: campingid,
                room: room
            },
            success: function(response) {
                $('#remainingRoomCount').text(response.remainingRooms); // 서버로부터 받은 남은 방 개수 업데이트
            },
            error: function() {
                alert('남은 방 개수를 가져s오는 데 실패했습니다.');
            },
            dataType: "json"
        });
    }
}


// 페이지 로드 시 남은 방 개수를 업데이트
$(document).ready(function() {
    updateRemainingRooms();
});

// 날짜 변경 시 남은 방 개수 업데이트
$('#checkin, #checkout').on('change', function() {
    updateRemainingRooms();
});
