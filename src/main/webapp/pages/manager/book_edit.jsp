<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>编辑图书</title>
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

			<!-- 静态包含 base标签，css样式，jQuery文件 -->
			<%@include file="/pages/common/head.jsp"%>
			<%@include file="/pages/common/manager_menu.jsp"%>

			<form action="manager/book" method="post">
				<table class="table" style="height: 150px" >
					<tr>
						<td>名称</td>
						<td>价格</td>
						<td>作者</td>
						<td>销量</td>
						<td>库存</td>
						<td colspan="2">操作</td>
					</tr>		
					<tr>
						<!-- 注意：由于命名原因 book_name 应该是 name，导致无法注入 JavaBean
						 param.pageType == "modify"用于区分你是想添加图书还是修改既有图书的参数-->
						<td><input style="width: auto; background: none;" class="pn_input" name="name" type="text" value="${param.pageType == "modify" ? requestScope.modifyItem.name : "时间简史"}"/></td>
						<td><input style="width: auto; background: none;"  class="pn_input" name="price" type="text" value="${param.pageType == "modify" ? requestScope.modifyItem.price : "30.00"}"/></td>
						<td><input style="width: auto; background: none;" class="pn_input" name="author" type="text" value="${param.pageType == "modify" ? requestScope.modifyItem.author : "霍金"}"/></td>
						<td><input style="width: auto; background: none;" class="pn_input" name="sales" type="text" value="${param.pageType == "modify" ? requestScope.modifyItem.sales : "200"}"/></td>
						<td><input style="width: auto; background: none;" class="pn_input" name="stock" type="text" value="${param.pageType == "modify" ? requestScope.modifyItem.stock : "300"}"/></td>
						<input type="hidden" name="action" value="${param.pageType == "modify" ? "update" : "add"}">
						<!-- 注意：缺乏 id 会导致数据库无法更新 -->
						<input type="hidden" name="id" value="${param.pageType == "modify" ? requestScope.modifyItem.id : null}">
						<input type="hidden" name="pageNo" value="${param.pageNo}">
						<td><input type="submit" value="${param.pageType == "modify" ? "修改" : "添加"}" class="join_cart"/></td>
					</tr>	
				</table>
			</form>


		<!-- 公共部分的页脚 -->
		<%@ include file="/pages/common/footer.jsp"%>
</body>
</html>