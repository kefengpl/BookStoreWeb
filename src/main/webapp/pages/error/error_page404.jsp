<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>尚硅谷会员注册页面</title>
	<!-- 静态包含 base标签，css样式，jQuery文件 -->
	<%@include file="/pages/common/head.jsp"%>
<style type="text/css">
	h1 {
		text-align: center;
		margin-top: 200px;
	}
	
	h1 a {
		color:red;
	}
</style>
</head>
<body>
		<div id="header">
				<img class="logo_img" alt="" src="static/img/logo.gif" >
				<!-- 为了便于维护，欢迎部分全部使用静态包含 -->
				<%@ include file="/pages/common/login_success_menu.jsp"%>
		</div>
		
		<div id="main">
		
			<h1>祝贺您！页面不存在或者已经被删除了！ <a href="index.jsp">转到主页</a></h1>
	
		</div>

		<!-- 公共部分的页脚 -->
		<%@ include file="/pages/common/footer.jsp"%>
</body>
</html>