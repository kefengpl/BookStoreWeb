<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>后台管理</title>
	<!-- 静态包含 base标签，css样式，jQuery文件 -->
	<%@include file="/pages/common/head.jsp"%>
<style type="text/css">
	h1 {
		text-align: center;
		margin-top: 200px;
	}
</style>
</head>
<body>
    <%@include file="/pages/common/manager_menu.jsp"%>
	<table class="index-table" style="height: 300px;margin-top: 10px;">
		<tr><td><h1 style="color: black; margin: auto">欢迎管理员进入后台管理系统</h1></td></tr>
	</table>
	<!-- 公共部分的页脚 -->
	<%@ include file="/pages/common/footer.jsp"%>
</body>
</html>