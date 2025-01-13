/**
 *  예약 처리 관련 자바스크립트
 */

 
function revervation_check() {
	event.preventDefault(); // 폼 제출 방지

	// 입력한 날짜 가져오기
	const checkInDate = document.getElementById('checkin').value;
	const checkOutDate= document.getElementById('checkout').value;
            
	const checkIn = new Date(checkInDate);
	const checkOut = new Date(checkOutDate);

	// 시스템 날짜 가져오기
	const currentDate = new Date();
            
	if ($("#checkin").val() == null) {
		alert("체크인 날짜를 입력해 주세요.");
		$("#checkin").focus();
		return false;
	} else if ($("#checkout").val() == null) { 
		alert("체크아웃 날짜를 입력해 주세요.");
		$("#checkout").focus();
		return false;
	} else if ($("#bottom").val() == null) { 
		alert("바닥재를 선택해 주세요.");
		$("#bottom").focus();
		return false;
	} else if ($("#people").val() == null) { 
		alert("인원수를 입력해 주세요.");
		$("#people").focus();
		return false;
	} else if ($("#people").val() > 10 ) { 
		alert("인원수가 너무 많습니다");
		$("#bottom").focus();
		return false;
	} else if ($("#people").val() < 1 ) { 
		alert("인원수를 확인해 주세요");
		$("#bottom").focus();
		return false;
	} else if (checkIn < currentDate || checkOut < checkIn || checkOut < currentDate) {  
		alert('날짜를 확인해주세요'); // 과거 날짜, 채크아웃이 채크인보다 빠를 경우 경고창
		return false;
	} else {
		// 예약 결과 페이지로 이동
		$("#reservationform").attr("action", "reservation_process").submit();
		//window.location.href = 'https://example.com'; // 특정 URL로 이동
	}
            
}
	
 
 
 