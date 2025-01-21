/**
 * 예약 처리 관련 자바스크립트
 */

function reservation_check() {
    // event.preventDefault(); // 폼 제출 방지

    // 입력한 날짜 가져오기    
    var checkin = document.getElementById("checkin").value;
    var checkout = document.getElementById("checkout").value;
    var bottom = document.getElementById("bottom").value;
    var people = document.getElementById("people").value;

    var systemDate = new Date();
    var systemDateString = systemDate.toISOString().split('T')[0];

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
        alert("인원수가 너무 많습니다");
        $("#people").focus();
        return false;
    } else if (people < 1) {
        alert("인원수를 확인해 주세요");
        $("#people").focus();
        return false;
    } else {
        var checkinDate = new Date(checkin);
        var checkoutDate = new Date(checkout);
        var systemDateObj = new Date(systemDateString);

        if (checkinDate < systemDateObj || checkoutDate < checkinDate || checkoutDate < systemDateObj) {
            alert('날짜를 확인해주세요');
            return false;
        } else {
            // `checkin`과 `checkout` 값을 숨겨진 input에 설정
            document.getElementById("checkinValue").value = checkin;
            document.getElementById("checkoutValue").value = checkout;

            // 예약 결과 페이지로 이동
            $("#reservationform").submit();
        }
    }
}

// 날짜가 변경되었을 때 폼의 값 업데이트
function updateFormValues() {
    var checkin = document.getElementById("checkin").value;
    var checkout = document.getElementById("checkout").value;

    document.getElementById("checkinValue").value = checkin;
    document.getElementById("checkoutValue").value = checkout;
}

// 페이지 로드 시 남은 방 개수를 업데이트
$(document).ready(function() {
    updateRemainingRooms();
});

// 날짜 선택 시 남은 방 개수 업데이트
$('#checkin, #checkout').on('change', function() {
    updateRemainingRooms();
});

function updateRemainingRooms() {
var checkinDate = $('#checkin').val();
var checkoutDate = $('#checkout').val();

if (checkinDate && checkoutDate) {
$.ajax({
url: '/checkRemainingRooms',  // 남은 방 개수를 계산하는 서버 엔드포인트
method: 'GET',
data: {
campingId: $('input[name="campingId"]').val(),
room: $('input[name="room"]').val(),
checkin: checkinDate,
checkout: checkoutDate
},
success: function(response) {
$('#remainingRoomCount').text(response.remainingRooms); // 서버로부터 받은 남은 방 개수 업데이트
},
error: function() {
                    alert('남은 방 개수를 가져오는 데 실패했습니다.');
                    alert('서버와의 연결에 문제가 있습니다. 다시 시도해주세요.');
}
});
}
    
}
