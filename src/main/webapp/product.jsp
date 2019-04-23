<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="utils.CafeeDBUtils"%>
<%@page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.4.0.min.js"
	integrity="sha256-BJeo0qm959uMBGb65z40ejJYGSgR7REI4+CW1fNKwOg="
	crossorigin="anonymous"></script>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
	integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
	integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
	crossorigin="anonymous"></script>
<meta charset="UTF-8">
<title>Insert title here</title>
<%

String sql = "select * from product order by name";
ArrayList<HashMap<String, String>> products =  CafeeDBUtils.getInstance()
.executeQuery(Thread.currentThread().getStackTrace(), sql);;

%>
</head>
<body>
	<main role="main">

	<div class="container mt-5">
		<!-- Example row of columns -->
		<div class="row">
			
			<%for(HashMap<String, String> map:products){ %>
			<div class="col-md-4 ">
				<div class="card" style="width: 18rem;">
					<img src="<%=map.get("image_url") %>" class="card-img-top" alt="<%=map.get("name") %>">
					<div class="card-body">
						<h5 class="card-title"><%=map.get("name") %></h5>
						<p class="card-text">Some quick example text to build on the
							card title and make up the bulk of the card's content.</p>
						<a href="#" class="btn btn-primary">Go somewhere</a>
					</div>
				</div>
				</div>
				<%} %>
			
		</div>
	</div>
	</main>

</body>
</html>