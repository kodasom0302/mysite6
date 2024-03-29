<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link href="${pageContext.request.contextPath}/assets/css/mysite.css"
	rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/assets/css/guestbook.css"
	rel="stylesheet" type="text/css">

<!-- Axios 라이브러리 포함 -->
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>

<style>
	/* 모달창 배경 회색 부분 */
	.modal{
		width: 100%;	/* 가로 전체 */
		height: 100%;	/* 세로 전체 */
		display: none;	/* 시작 시 숨김 처리 */
		position: fixed;	/* 배경 바뀌어도 화면에 고정 */
		left: 0;	/* 제일 왼쪽에서 시작 */
		top:0 ;	/* 제일 위쪽에서 시작 */
		z-index: 999;	/* 제일 위에 - 클수록 올라감 */
		overflow: auto;	/* 내용 많으면 스크롤 생김 - 창 넘치면 어떡할 거냐 */
		background-color: rgba(0, 0, 0, 0.4);	/* 배경이 검정색에 반투명 */
	}
	
	/* 모달창 내용 흰색 부분 */
	.modal .modal-content{
		width: 600px;
		margin: 100px auto; /* 상하 100px, 좌우 가운데 */
		padding: 0px 20px 20px 20px;	/* 안쪽으로 얼마나 넣을 건지 - 여백 */
		background-color: white;	/* 배경 하얀색 */
		border: 1px solid #888888;	/* 테두리 모양, 색 */
	}
	
	/* 닫기 버튼 */
	.modal .modal-content .closeBtn{
		text-align: right;
		color: #aaaaaa;
		font-size: 28px;
		font-weight: bold;
		cursor: pointer;
	}
</style>

</head>

<body>
	<div id="wrap">

		<!-- header -->
		<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
		<!-- header -->

		<div id="container" class="clearfix">
			<div id="aside">
				<h2>방명록</h2>
				<ul>
					<li>일반방명록</li>
					<li>ajax방명록</li>
				</ul>
			</div>
			<!-- //aside -->

			<div id="content">

				<div id="content-head" class="clearfix">
					<h3>일반방명록</h3>
					<div id="location">
						<ul>
							<li>홈</li>
							<li>방명록</li>
							<li class="last">일반방명록</li>
						</ul>
					</div>
				</div>
				<!-- //content-head -->

				<div id="guestbook">
					<form id="guestForm" action="${pageContext.request.contextPath}/guest/write" method="get">
						<table id="guestAdd">
							<colgroup>
								<col style="width: 70px;">
								<col>
								<col style="width: 70px;">
								<col>
							</colgroup>
							<tbody>
								<tr>
									<th><label class="form-text" for="input-uname">이름</label>
									</td>
									<td><input id="input-uname" type="text" name="name"></td>
									<th><label class="form-text" for="input-pass">패스워드</label>
									</td>
									<td><input id="input-pass" type="password" name="password"></td>
								</tr>
								<tr>
									<td colspan="4"><textarea name="content" cols="72"
											rows="5"></textarea></td>
								</tr>
								<tr class="button-area">
									<td colspan="4" class="text-center"><button type="submit">등록</button></td>
								</tr>
							</tbody>

						</table>
						<!-- //guestWrite -->
						
						<input type="hidden" name="action" value="add">

					</form>
					
					<!-- 모달 창 컨텐츠 -->
               <div id="myModal" class="modal">
                  <div id="guestbook" class="modal-content">
                     <div class="closeBtn">×</div>
                     <div class="m-header">
                        패스워드를 입력하세요
                     </div>
                     <div class="m-body">
                        <input class="m-pw" type="password" name="password" value=""><br>
                        <input class="m-no" type="text" name="no" value="">
                     </div>
                     <div class="m-footer">
                        <button class="btnDelete" type="button">삭제</button>
                     </div>
                  </div>
               </div>
					
					<div id="gListArea"></div>

				</div>
				<!-- //guestbook -->

			</div>
			<!-- //content  -->
		</div>
		<!-- //container  -->

		<!-- //footer -->
		<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
		<!-- //footer -->
	</div>
	<!-- //wrap -->

</body>

<script>
document.addEventListener("DOMContentLoaded", function(){
	
	//돔트리가 생성 완료 되었을 때
	//리스트 요청	- 데이터만 받을 거야
	getListAndRender();
	
		//등록 버튼 클릭했을 때(데이터만 받을 거야)
		let guestForm=document.querySelector("#guestForm");
		guestForm.addEventListener("submit", addAndRender);
		
		//모달창 호출 버튼을 클릭했을 때
		let gListArea=document.querySelector("#gListArea");
		gListArea.addEventListener("click", callModal);
		
		//모달창 닫기 버튼 (X) 클릭했을 때
		let closeBtn=document.querySelector(".closeBtn");
		closeBtn.addEventListener("click", closeModal);
		
		//모달창에 삭제 버튼을 클릭했을 때 - 진짜 삭제
		let btnDelete=document.querySelector(".btnDelete");
		btnDelete.addEventListener("click", deleteAndRemove);
		
});//document.addEventListener

	///////////////함수들///////////////////
	
	//리스트 가져오고 그리기
	function getListAndRender(){
		//리스트 요청 - 데이터만 받을 거야
		axios({
				method: 'get', // put, post, delete
				url: '${pageContext.request.contextPath}/api/guestbooks',
				headers: {"Content-Type" : "application/json; charset=utf-8"}, //전송타입
				responseType: 'json' //수신타입
			})
			.then(function (response) {
				
				//리스트 자리에 글을 추가한다
				for(let i=0; i<response.data.length; i++){
					let guestVo=response.data[i];
					render(guestVo, "down");	//1개의 글을 render()에게 전달 -> render() 리스트 위치에 그린다
				}
			})
			.catch(function (error) {
				console.log(error);
			});
	}//getListAndRender
	
	//글 저장하고 그리기
	function addAndRender(event) {
		event.preventDefault();
		console.log("글쓰기 버튼 클릭");
		
		//폼의 데이터 수집
		let name=document.querySelector('[name="name"]').value;
		let pw=document.querySelector('[name="password"]').value;
		let content=document.querySelector('[name="content"]').value;
		
		let guestVo={
				name: name,
				password: pw,
				content: content
		};
		
		console.log(guestVo);
		
		//서버로 데이터 전송
		axios({
			method: 'post', // put, post, delete
			url: '${pageContext.request.contextPath}/api/guestbooks',
			headers: {"Content-Type" : "application/json; charset=utf-8"}, //전송타입
			params: guestVo, //get방식 파라미터로 값이 전달
			//data: guestbookVo, //put, post, delete 방식 자동으로 JSON으로 변환 전달
			responseType: 'json' //수신타입
		})
		.then(function (response) {
			console.log(response); //수신데이타
			console.log(response.data);
			
			let guestVo=response.data
			
			//그리기
			render(guestVo, "up");
			
			//비우기
			guestForm.reset();
		})
		.catch(function (error) {
			console.log(error);
		});
	}//addAndRender
	
	//방명록 글 그리기
	function render(guestVo, dir){
		console.log("render()");
		console.log(guestVo);
		
		let gListArea=document.querySelector("#gListArea");
		
		let str='';
		str+='<table id="t-'+guestVo.no+'" class="guestRead">';
		str+='	<colgroup>';
		str+='		<col style="width: 10%;">';
		str+='		<col style="width: 40%;">';
		str+='		<col style="width: 40%;">';
		str+='		<col style="width: 10%;">';
		str+='	</colgroup>';
		str+='	<tr>';
		str+='		<td>'+ guestVo.no +'</td>';
		str+='		<td>'+ guestVo.name +'</td>';
		str+='		<td>'+ guestVo.regDate +'</td>';
		str+='		<td><button class="btnModal" type="button" data-no="'+guestVo.no+'">[삭제]</button></td>';
		str+='	</tr>';
		str+='	<tr>';
		str+='		<td colspan=4 class="text-left">'+ guestVo.content +'</td>';
		str+='	</tr>';
		str+='</table>';
		
		if(dir=="down"){
			gListArea.insertAdjacentHTML("beforeend", str);
		} else if(dir=="up"){
			gListArea.insertAdjacentHTML("afterbegin", str);
		} else{
			console.log("방향 체크");
		}
		
	}//render
	
	//모달창 보이기
	function callModal(event){
			
			if(event.target.tagName=="BUTTON"){
				
				console.log("모달창 보이기");
				
				let modal=document.querySelector("#myModal");
				//modal의 화면을 블록으로 변경 - 창 뜨게 바꾼다
				modal.style.display="block";
				
				
				//hidden의 value -> no 값 입력
				let noTag=document.querySelector('[name="no"]');
				noTag.value=event.target.dataset.no;
				
				//패스워드 창 비우기
				let tag=document.querySelector('.m-pw');
				tag.value="";
			}
	}//callModal
	
	//모달창 숨기기
	function closeModal() {
		let modal=document.querySelector("#myModal");
		modal.style.display="none";
	}//closeModal
	
	//삭제
	function deleteAndRemove(){
		console.log("삭제");
		
		//데이터 모으고
		let no=document.querySelector(".m-no").value;
		let pw=document.querySelector(".m-pw").value;
		
		let guestVo={
				password: pw
		};
		
		//서버로 전송
		//url / api / guestbooks / delete - axios 사용
		//post
		axios({
			method: 'delete', // put, post, delete
			url: '${pageContext.request.contextPath}/api/guestbooks/'+no,
			headers: {"Content-Type" : "application/json; charset=utf-8"}, //전송타입
			params: guestVo, //파라미터로 값이 전달
			//data: guestbookVo, //put, post, delete 방식 자동으로 JSON으로 변환 전달
			responseType: 'json' //수신타입
		})
		.then(function (response) {
			console.log(response); //수신데이타 - 전체
			console.log(response.data);	//수신데이타 - 우리가 보낸 거
			
			if(response.data==1) {
				//찾아서 삭제
				let tagId="#t-"+no;
				console.log(tagId);
				
				let removeTable=document.querySelector(tagId);
				console.log(removeTable);
				
				removeTable.remove();
			}
			
			//let tags=document.querySelectorAll("#gListArea table tr");
			//console.log(tags);
		})
		.catch(function (error) {
			console.log(error);
		});
	}//deleteAndRemove
</script>
</html>