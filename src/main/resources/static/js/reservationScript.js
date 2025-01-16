/**
 *  예약 처리 관련 자바스크립트
 */

 
function reservation_check() {
	//event.preventDefault(); // 폼 제출 방지

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
	} else if (people > 10 ) { 
		alert("인원수가 너무 많습니다");
		$("#people").focus();
		return false;
	} else if (people < 1 ) { 
		alert("인원수를 확인해 주세요");
		$("#people").focus();
		return false;
	} else {
		var checkinDate = new Date(checkin);
		var checkoutDate = new Date(checkout);
		var systemDateObj = new Date(systemDateString);
		
		if (checkinDate < systemDateObj || checkoutDate < checkinDate || checkoutDate < systemDateObj) {
			alert('날짜를 확인해주세요'); // 과거 날짜, 채크아웃이 채크인보다 빠를 경우 경고창
			return false;
		} else {
			// 예약 결과 페이지로 이동
			$("#reservationform").attr("action", "/reservation_process").submit();
			//window.location.href = 'https://example.com'; // 특정 URL로 이동	
		}
            
	}
}	
 
function AddLike() {
	const campingId = document.getElementById('camping-info').getAttribute('data-id');
	
	
	
}
 

 
 