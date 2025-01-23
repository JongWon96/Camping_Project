/**
 *  회원처리 관련 자바스크립트 함수
 */
function go_next() {
    // 라디오 버튼 그룹 선택
    const agreeRadios = document.getElementsByName("okon1");

    // 선택된 라디오 버튼 확인
    let isAgreed = false;
    for (let radio of agreeRadios) {
        if (radio.checked && radio.value === "동의함") {
            isAgreed = true;
            break;
        }
    }

    // 동의 여부에 따라 처리
    if (isAgreed) {
        // 동의함 선택 시 폼 제출
        document.getElementById("contract").action = "join";
        document.getElementById("contract").submit();
    } else {
        // 동의하지 않음 선택 시 알림
        alert("약관에 동의하셔야 가입할 수 있습니다.");
    }
}

/*
**  id중복 확인 화면 출력 요청
*/
function idcheck() {
	// id입력값 입력 확인
	if($("#memberId").val() == "") {
		alert("아이디를 입력해 주세요!");
		$("#memberId").focus();
		return false;
	}
	
	// id중복확인 창 오픈
	var url = "id_check_form?memberId=" + $("#memberId").val();
	window.open(url, "_blank_", "toolbar=no, menubar=no, scrollbars=no, " +
			"resizable=yes, width=350, height=200");
}

/*
**  전화번호 입력시 autoHyphen
*/
function autoHyphen(input) {
    // 숫자만 남기고 모든 문자 제거
    var phone = input.value.replace(/[^\d]/g, "");

    // 전화번호 형식에 맞게 하이푼 추가
    if (phone.length >= 4 && phone.length <= 7) {
        input.value = phone.slice(0, 3) + "-" + phone.slice(3);
    } else if (phone.length >= 8) {
        input.value = phone.slice(0, 3) + "-" + phone.slice(3, 7) + "-" + phone.slice(7);
    }
}

/*
 * 회원 가입시, 필수 입력 항목 확인
 */
function go_save() {
	if ($("#memberId").val() == "") {
		alert("아이디를 입력해 주세요!");
		$("#memberId").focus();
		return false;
	} else if($("#memberId").val() != $("#rememberId").val()) {
		alert("아이디 중복 체크를 해주세요!");
		$("#memberId").focus();
		return false;
	} else if ($("#password").val() == "") {
		alert("비밀번호를 입력해 주세요!");
		$("#password").focus();
		return false;
	} else if($("#password").val() != $("#pwdCheck").val()) {
		alert("비밀번호가 일치하지 않습니다!");
		$("#password").focus();
		return false;
	} else if ($("#name").val() == "") {
		alert("이름을 입력해 주세요!");
		return false;
	} else if ($("#phone").val() == "") {
		alert("전화번호를 입력해 주세요!");
		return false;
	} else if ($("#gender").val() == "") {
	alert("성별을 입력해 주세요!");
		return false;	
	} else if ($("#age").val() == "") {
	alert("나이를 입력해 주세요!");
		return false;
	} else if ($("#email").val() == "") {
	alert("이메일을 입력해 주세요!");
		return false;
	} else if ($("#sample6_address").val() == "") {
	alert("주소를 입력해 주세요!");
		return false;
	} else if ($("#sample6_detailAddress").val() == "") {
	alert("상세주소를 입력해 주세요!");
		return false;
	} else {
		// 회원 가입 요청
		$("#join").attr("action", "join").submit();
	}
}

/*
**  아이디 찾기 화면 요청
*/
function find_id_form() {
	var url = "find_id_form";
	
	window.open(url, "_blank_", "toolbar=no, menubar=no, scrollbars=no, " +
			"resizable=yes, width=550, height=450");
}

/*
**  비밀번호 찾기 화면 요청
*/
function find_pwd_form() {
	var url = "find_pwd_form";
	
	window.open(url, "_blank_", "toolbar=no, menubar=no, scrollbars=no, " +
			"resizable=yes, width=550, height=450");
}

/*
**  아이디 찾기 요청
*/
function fc_findMemberId() {
	if ($("#name").val() == "") {
		alert("이름을 입력해 주세요.");
		$("#name").focus();
		return false;
	} else if ($("#phone").val() == "") {
		alert("전화번호를 입력해 주세요.");
		$("#phone").focus();
		return false;
	} else {
		var form = $("#findMemberId");
		form.action = "find_id";  // 컨트롤러 요청 URL
		form.submit();  // 컨트롤러로 전송
	}
}

/*
**  비밀번호 찾기 요청
*/
function fc_findPassword() {
	if ($("#memberId").val() == "") {
		alert("아이디를 입력해 주세요.");
		$("#memberId").focus();
		return false;
	} else if ($("#name").val() == "") {
		alert("이름을 입력해 주세요.");
		$("#name").focus();
		return false;
	} else if ($("#phone").val() == "") {
		alert("전화번호를 입력해 주세요.");
		$("#phone").focus();
		return false;
	} else {
		var form = $("#findPassword");
		form.action = "find_pwd";  // 컨트롤러 요청 URL
		form.submit();  // 컨트롤러로 전송
	}
}

/*
**  비밀번호 변경
*/
function changePassword() {
	console.log("changePassword()...")
	if($("#password").val() == "") {
		alert("비밀번호를 입력해 주세요.");
		$("#password").focus();
		return false;
	} else if($("#password").val() != $("#pwdCheck").val()) {
		alert("비밀번호가 맞지 않습니다. 다시 입력해 주세요");
		$("#pwdCheck").focus();
		return false;		
	} else {
		$("#pwd_form").action = "change_pwd";
		$("#pwd_form").submit();
	}
}