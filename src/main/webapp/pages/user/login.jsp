<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>尚硅谷会员登录页面</title>
	<!-- 静态包含 base标签，css样式，jQuery文件 -->
	<%@include file="/pages/common/head.jsp"%>
	<script type="text/javascript">
		$(function() {

		});
	</script>

</head>
<body>
		<div id="login_header">
			<img class="logo_img" alt="" src="static/img/log44" >
		</div>
		
			<div class="login_banner">
			
				<div id="l_content">
					<span class="login_word">欢迎登录</span>
				</div>
				
				<div id="content">
					<div class="login_form">
						<div class="login_box">
							<div class="tit">
								<h1>尚硅谷会员</h1>
								<a href="pages/user/regist.jsp">立即注册</a>
							</div>
							<div class="msg_cont">
								<b></b>
								<span class="errorMsg"><%=request.getAttribute("errorMsg") == null ? "请输入用户名和密码" : request.getAttribute("errorMsg")%></span>
							</div>
							<div class="form">
								<form action="login" method="post">
									<label>用户名称：</label>
									<input class="itxt" type="text" placeholder="请输入用户名" autocomplete="off" tabindex="1" name="username"
										   value="<%=request.getParameter("username") == null ? "" : request.getParameter("username") %>" />
									<br />
									<br />
									<label>用户密码：</label>
									<input class="itxt" type="password" placeholder="请输入密码" autocomplete="off" tabindex="1" name="password"
									       value="<%=request.getParameter("password") == null ? "" : request.getParameter("password")%>"/>
									<br />
									<br />
									<input type="submit" value="登录" id="sub_btn" />
								</form>
							</div>
							
						</div>
					</div>
				</div>
			</div>
		<!-- 公共部分的页脚 -->
		<%@ include file="/pages/common/footer.jsp"%>
</body>
</html>