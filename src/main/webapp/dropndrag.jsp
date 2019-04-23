<%@page import="apppojo.Product"%>
<%@page import="com.google.gson.GsonBuilder"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="utils.CafeeDBUtils"%>
<%@page import="java.util.*"%>
<%@page import="java.lang.reflect.Type"%>
<%@page import="com.google.gson.reflect.TypeToken"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<style>
.ui-draggable-dragging{
z-index:2;
background: yellow !important;
}
</style>
</head>
<body>

<%
String unassigned_product_sql = "select * from product where category_id is null order by name";

String category_sql = "select categories.id,categories.name,'['||string_agg('{ \"id\":'||p.id|| ', \"name\":\"' ||p.name|| '\"}',',')||']' as products from categories left join product as p on p.category_id = categories.id group by categories.id,categories.name order by categories.name";
ArrayList<HashMap<String, String>> categories =  CafeeDBUtils.getInstance()
.executeQuery(Thread.currentThread().getStackTrace(), category_sql);
ArrayList<HashMap<String, String>> products_list =  CafeeDBUtils.getInstance()
.executeQuery(Thread.currentThread().getStackTrace(), unassigned_product_sql);

%>
	<main role="main">

	<div class="container">
		<!-- Example row of columns -->
		<div class="row">
			<div class="col-md-6">
				<ul class="list-group" id="gallery">
					<%for(HashMap<String, String> map:products_list){ %>
				
					<li class="list-group-item product_item draggable" data-productid="<%=map.get("id")%>"><%=map.get("name")%></li>
					<%} %>
				</ul>
			</div>
			<div class="col-md-6" style="    max-height: 500px;
    overflow: auto;">
			<%for(HashMap<String, String> map: categories){
			
			%>
			<ul class="list-group" id="gallery">
					<li class="list-group-item product_item droppable" data-id ="<%=map.get("id")%>"> <div><%=map.get("name")%></div>
						<ul class="list-group " id="gallery">
							
							<%try{
								Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
								Type type = new TypeToken<List<Product>>() {

								}.getType();
								System.out.println(map.get("products"));
								if(map.get("products")!= null && !map.get("products").toString().equalsIgnoreCase("null")){
								List<Product> products = gson.fromJson(map.get("products").toString(), type);
								for(Product product:products){
							
							 %>
							<li class="list-group-item product_item" data-productid="<%=product.getId()%>"><%=product.getName()%></li>
						<% }}}catch(Exception e){
								e.printStackTrace();
							}%>
						
						</ul>

					</li>
					
				</ul>
				<%} %>
			
			
			</div>
		</div>
	</div>
	</main>

	<script>
		$(function() {
			
			 $( "#gallery" ).sortable({
			      revert: true
			    });
			$(".draggable").draggable({
			      connectToSortable: "#gallery",

			      cancel: "a.ui-icon", // clicking an icon won't initiate dragging
			      revert: "invalid", // when not dropped, the item will revert back to its initial position
			      containment: "document",
			      cursor: "move"
			    });
			$( ".droppable" ).droppable({
			      accept: "#gallery > li",

			      drop: function( event, ui ) {
			    	  var product_id=$(ui.draggable).data('productid');
			    	  var category_id=$(this).data('id');
			      
			        $.post( "rest/product/assign?product_id="+product_id+"&category_id="+category_id, function( data ) {
			        	location.reload();

			        	});
			        
			        
			      }
			    });
		});
	</script>
</body>
</html>