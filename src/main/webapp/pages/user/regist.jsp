<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<!-- 添加 base 标签之意义在于：防止请求转发后相对路径失效，工程路径对应于 webapp 文件夹 -->

		<title>尚硅谷会员注册页面</title>
		<!-- 静态包含 base标签，css样式，jQuery文件 -->
		<%@include file="/pages/common/head.jsp"%>
		<script type="text/javascript">
			$(function() {
				// 给验证码图片绑定单击事件
				$("#code_img").click(function () {
					// 在事件响应的 function 函数中，
					// this 是当前正在响应的dom对象
					// src属性表示验证码 img 标签的图片路径，它可读，可写
					// 但是在浏览器有缓存的情况下，似乎不能点击就切换验证码了
					// 加上一个时间戳，即可保证验证码每过1s就能够跳过缓存进行切换
					// 注意：如果你在 1s 之内连续点击验证码图片，由于时间戳没变化，还是会进入浏览器缓存，无法更改验证码图片
					this.src = "verifycode.jpg?nocache=" + new Date();
				});
				// 给按钮绑定单击事件
				$("#sub_btn").click(function () {
					var username = $("#username").val();
					var password = $("#password").val();
					var repwd = $("#repwd").val();
					var email = $("#email").val();
					var varify_code = $("#code").val();
					var patt = /^\w{5,12}$/;
					if (!patt.test(username)) {
						// 只有input有val，其余是text
						$("span.errorMsg").text("用户名不符合规范");
						return false;
					}
					if (!patt.test(password)) {
						$("span.errorMsg").text("密码不符合规范");
						return false;
					}
					if (password !== repwd) {
						$("span.errorMsg").text("两次密码不一致");
						return false;
					}
					var email_patt = /^[a-z\d]+(\.[a-z\d]+)*@([\da-z](-[\da-z])?)+(\.{1,2}[a-z]+)+$/;
					if (!email_patt.test(email)) {
						$("span.errorMsg").text("邮箱格式不正确");
						return false;
					}
					/* 注意没有输入验证码的判定方法和规则即可 */
					// 去掉验证码前后空格
					varify_code = $.trim(varify_code);
					if (varify_code == null || varify_code == "") {
						$("span.errorMsg").text("没有输入验证码");
						return false;
					}

					//只要合法了，就把error内容去掉
					$("span.errorMsg").text("");

				});
			});
		</script>


	<style type="text/css">
		.login_form{
			height:420px;
			margin-top: 25px;
		}

	</style>
	</head>
	<body>
		<div id="login_header">
			<!--<img class="logo_img" alt="" src="../../static/img/logo.gif45" >-->
		</div>

			<div class="login_banner">

				<div id="l_content">
					<span class="login_word">欢迎注册</span>
				</div>

				<div id="content">
					<div class="login_form">
						<div class="login_box">
							<div class="tit">
								<h1>注册会员</h1>
								<!-- 代码优化：使用 EL 表达式可以很好地处理 空值不显示 null 的情况 -->
								<span class="errorMsg"> ${requestScope.errorMsg} </span>
							</div>
							<div class="form">
								<form action="user" method="post">
									<!-- 细节问题：注意区分请求参数 和 域对象
									 一个是 getAttribute 而请求参数是 getParameter -->
									<label>用户名称：</label>
									<input class="itxt" type="text" placeholder="请输入用户名"
										   autocomplete="off" tabindex="1" name="username" id="username"
										   value="${param.username}" />
									<br />
									<br />
									<label>用户密码：</label>
									<input class="itxt" type="password" placeholder="请输入密码"
										   autocomplete="off" tabindex="1" name="password" id="password"
										   value="${param.password}" />
									<br />
									<br />
									<label>确认密码：</label>
									<input class="itxt" type="password" placeholder="确认密码"
										   autocomplete="off" tabindex="1" name="repwd" id="repwd"
										   value="${param.password}" />
									<br />
									<br />
									<label>电子邮件：</label>
									<input class="itxt" type="text" placeholder="请输入邮箱地址"
										   autocomplete="off" tabindex="1" name="email" id="email"
										   value="${param.email}" />
									<br />
									<br />
									<label>验证码：</label>
									<input class="itxt" type="text" style="width: 80px;" name="code" id="code"
										   value="${param.code}" />
									<!-- 注意：在首次请求这个页面的情况下，这个验证码会自动储存到 SESSION 域中 -->
									<img alt="" src="verifycode.jpg" id="code_img" style="margin-right: 40px; width: 110px; height: 30px">
									<br />
									<br />
									<input type="submit" value="注册" id="sub_btn" />
									<!-- 传递隐藏参数给Servlet，用于POST分发给 login 或者 regist 模块处理 -->
									<input type="hidden" name="action" value="regist">
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