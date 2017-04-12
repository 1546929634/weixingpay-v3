<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title> 测试请求</title>
</head>
<body>
	<form action="" method="post">
		<input id="pay" type="submit" value="提交" />
	</form>
</body>
</html>
<script type="text/javascript">
	var basePath = "localhost:8080";
	$("#pay").click(function(){
		$.ajax({
			url:""
		});
	});
</script>