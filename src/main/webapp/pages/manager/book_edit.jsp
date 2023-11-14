<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>编辑图书</title>
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
	
	input {
		text-align: center;
	}
</style>
</head>
<body>
		<div id="header">
			<img class="logo_img" alt="" src="../../static/img/logo.gif" >
			<span class="wel_word">编辑图书</span>
			<%@include file="/pages/common/manager_menu.jsp"%>
		</div>
		
		<div id="main">
			<form action="manager/book" method="post">
				<table>
					<tr>
						<td>名称</td>
						<td>价格</td>
						<td>作者</td>
						<td>销量</td>
						<td>库存</td>
						<td colspan="2">操作</td>
					</tr>		
					<tr>
						<!-- 注意：由于命名原因 book_name 应该是 name，导致无法注入 JavaBean -->
						<td><input name="name" type="text" value="${param.pageType == "modify" ? requestScope.modifyItem.name : "时间简史"}"/></td>
						<td><input name="price" type="text" value="${param.pageType == "modify" ? requestScope.modifyItem.price : "30.00"}"/></td>
						<td><input name="author" type="text" value="${param.pageType == "modify" ? requestScope.modifyItem.author : "霍金"}"/></td>
						<td><input name="sales" type="text" value="${param.pageType == "modify" ? requestScope.modifyItem.sales : "200"}"/></td>
						<td><input name="stock" type="text" value="${param.pageType == "modify" ? requestScope.modifyItem.stock : "300"}"/></td>
						<input type="hidden" name="action" value="${param.pageType == "modify" ? "update" : "add"}">
						<!-- 注意：缺乏 id 会导致数据库无法更新 -->
						<input type="hidden" name="id" value="${param.pageType == "modify" ? requestScope.modifyItem.id : null}">
						<input type="hidden" name="pageNo" value="${param.pageNo}">
						<td><input type="submit" value="${param.pageType == "modify" ? "修改" : "添加"}"/></td>
					</tr>	
				</table>
			</form>
			
	
		</div>

		<!-- 公共部分的页脚 -->
		<%@ include file="/pages/common/footer.jsp"%>
</body>
</html>