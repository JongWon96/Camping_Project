/**
 * 
 */
/*
** 상품 이름으로 검색 요청
*/
function go_search() {
	
	$("#camping_form").attr("action", "admin_camping_list").submit();
}

/*
** 전체 상품 보기 버튼 처리
*/
function go_total() {
	$("#key").val("");
	$("#camping_form").attr("action", "admin_camping_list").submit();
}

/*
** 상품등록 페이지 표시
*/
function go_wrt() {
	$("#camping_form").attr("action", "admin_camping_write_form").submit();
}

/*
** 상품등록 페이지 표시
*/
function go_mov() {
	$("#camping_form").attr("action", "admin_camping_list").submit();
}

/*
** 순익 필드(price3) 입력
*/
/*
function go_ab() {
	var price1 = $("#price1").val().replace(/,/g, '');  // replace() -> 콤마(,)를 빈문자열로 대치
	var price2 = $("#price2").val().replace(/,/g, '');
	var result = price2 - price1;
	
	$("#price3").val(result);
}
*/

/*
** 숫자 3자리 마다 콤마 찍기
*/
function NumFormat(t) {
	num = t.value;
    // 숫자 이외의 문자 제거
    num = num.replace(/\D/g, '');
    // 숫자 3자리 마다 콤마 추가
    len = num.length - 3;
    while (len > 0) {
        num = num.substr(0, len) + "," + num.substr(len);
        len -= 3;
    }
   
    t.value = num;
   
    return t;
}

/*
** 상품 등록 요청
*/
function go_save() {
	
	if ($("#category").val() == "") {
		alert("켐핑 종류를 입력하세요.");
		$("#category").focus();
		return false;
	} else if ($("#facltnm").val() == "") {
		alert("켐핑장명을 입력하세요.");
		$("#facltnm").focus();
		return false;
	} else if ($("#addr1").val() == "") {
		alert("켐핑장 주소를 입력하세요.");
		$("#addr1").focus();
		return false;
	} else if ($("#roomcount").val() == "") {
		alert("켐핑장 예약가능 방 개수를 입력하세요.");
		$("#roomcount").focus();
		return false;
	} else if ($("#zipcode").val() == "") {
		alert("zip주소를 입력하세요.");
		$("#zipcode").focus();
		return false;
	} else if ($("#featurenm").val() == "") {
		alert("켐핑설명을 입력하세요.");
		$("#featurenm").focus();
		return false;
	} else {
		var facltnm = $("#facltnm");
		var addr1 = $("#addr1");
		var roomcount = $("#roomcount");
		/*
		price1.val(remove_comma(price1));
		price2.val(remove_comma(price2));
		price3.val(remove_comma(price3));
		*/
		var theform = $("#write_form");
		theform.attr("enctype", "multipart/form-data");
		theform.attr("action", "admin_camping_write");
		theform.submit();
	}
}

/*
**  숫자 데이터에서 콤마 제거
*/
function remove_comma(data) {
	
	return data.val().replace(/,/g, '');
} 

/*
** 상품 상세보기 요청
*/
function go_detail(id) {
	
	alert("id", id);
	$("#camping_form").attr("action", "admin_camping_detail?id=" + id).submit();
	
}

function go_mod(id) {
	$("#detail_form").attr("action", "admin_camping_update_form?id="+id).submit();
}

function go_mod_save() {
	if ($("#category").val() == "") {
		alert("켐핑 종류를 입력하세요.");
		$("#category").focus();
		return false;
	} else if ($("#facltnm").val() == "") {
		alert("켐핑장명을 입력하세요.");
		$("#facltnm").focus();
		return false;
	} else if ($("#addr1").val() == "") {
		alert("켐핑장 주소를 입력하세요.");
		$("#addr1").focus();
		return false;
	} else if ($("#roomcount").val() == "") {
		alert("켐핑장 예약가능 방 개수를 입력하세요.");
		$("#roomcount").focus();
		return false;
	} else if ($("#zipcode").val() == "") {
		alert("zip주소를 입력하세요.");
		$("#zipcode").focus();
		return false;
	} else if ($("#featurenm").val() == "") {
		alert("켐핑설명을 입력하세요.");
		$("#featurenm").focus();
		return false;
	}  else {
		var facltnm = $("#facltnm");
		var addr1 = $("#addr1");
		var roomcount = $("#roomcount");
		/*
		price1.val(remove_comma(price1));
		price2.val(remove_comma(price2));
		price3.val(remove_comma(price3));
		*/
		var theform = $("#update_form");
		theform.attr("enctype", "multipart/form-data");
		theform.attr("action", "admin_camping_update");
		theform.submit();
	}
}










