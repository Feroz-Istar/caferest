<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="assets/css/jalendar.min.css">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">

<script src="https://code.jquery.com/jquery-3.4.1.min.js"
	integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
	crossorigin="anonymous"></script>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
	integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
	crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
	integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
	integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
	crossorigin="anonymous"></script>
	<script src="assets/js/jalendar.min.js"></script>
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
      rel="stylesheet">
</head>
<body style="background:rgb(232, 232, 232)">

	<main role="main" class="mt-5">
	<div class="container">
		<div class="form-group">
			<div class="jalendar-input input-group">
				<input type="text" readonly id="result" />
				<div id="yourId4Input" class="jalendar"></div>
				
				  <div class="added-event" data-type="task" data-date="18-06-2019" data-title="Hazal ve Bora Nikah Töreni"></div>
				  <div class="input-group-append">
    <button class="btn btn-outline-secondary" type="button" id="button-addon2"><i class="material-icons">
event_note
</i></button>
  </div>
			</div>
		</div>

	</div>
	</main>

	<script type="text/javascript">
		$(document).ready(function() {
			/* 	$('#yourId4').jalendar({
			 color: '#37C4A7',
			 type: 'range',
			 done: function() {
			 alert($('#yourId4 input.data1').val() + ' / ' + $('#yourId4 input.data2').val())
			 }
			 }); */

			var abc =$('#yourId4Input').jalendar({
			    color: '#fff',
			    color2: '#57c8bf', // Unlimited

				type : 'range',
				dayWithZero : true,
				monthWithZero:true,
				selectingBeforeToday: true,
				dateType: "dd-mm-yyyy",
			    titleColor: '#666',
			    weekColor: '#EA5C49',
			    todayColor: '#EA5C49',
			    done: function() {
			    	$('#result').val($('#yourId4Input input.data1').val().replace(',',' -'));

			    	
			        alert( $('#yourId4Input input.data1').val() );
			    }

			});
			 $('#button-addon2').click(function (){
				 console.log('cc')
				 setTimeout(function(){ $('#result').trigger('click')}, 100);

				 /* $('#result').trigger('click');		
				 $('.jalendar-input').trigger('click');	 */
			 });

		});
	</script>
</body>
</html>