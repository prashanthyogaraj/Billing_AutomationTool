<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<link href='landing.css' rel='stylesheet'>

		<form method="POST" action="Billing">
			<div class="box">
			<h1 style="color:#3348A7">Billing Automation Tool</h1>
			<table class="tab"  align="center">
			<tr> <td>Prepared By:</td> <td><input type="text" id ="uploader" name="uploader" required/></td> </tr>
			<tr> <td>Company name:</td> <td><input type="text" id ="company" name="company" required/></td> </tr>
			<tr> <td>PDF File Path:</td> <td><input type="text" id ="path" name="path" required/></td> </tr>
			<tr> <td>Cutting Length Cost:</td><td><input type="text" id ="cl_Al" name="cl_Al" placeholder="Al" required/> <input type="text" id ="cl_ms" name="cl_ms" placeholder="MS" required/> <input type="text" id ="cl_ss" name="cl_ss" placeholder="SS" required/></td> 
			</tr>
		 	<tr> <td>Piercing Point Cost:</td><td><input type="text" id ="pp_Al" name="pp_Al" placeholder="Al" required/> <input type="text" id ="pp_ms" name="pp_ms" placeholder="MS" required/> <input type="text" id ="pp_ss" name="pp_ss" placeholder="SS" required/></td>		 					
			<tr><td><input type="Submit" value="Submit"></td>
			</table>
			</div>	  
		</form>
	<script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>
	<script type="text/javascript">

</script>
</body>
</html>