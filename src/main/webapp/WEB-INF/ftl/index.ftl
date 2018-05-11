<html>
<body>
<@shiro.user>
欢迎[<@shiro.principal/>]登录，<a href="${base}/logout">退出</a>
</@shiro.user>
<br/>

<@shiro.authenticated>
用户[<@shiro.principal/>]已身份验证通过
</@shiro.authenticated>

<br/>
<@shiro.hasRole name="admin">
用户[<@shiro.principal/>]拥有角色admin<br/>
</@shiro.hasRole>

<br/>

<@shiro.hasAnyRoles name="admin,user,member">
用户[<@shiro.principal/>]拥有角色admin或user或member<br/>
</@shiro.hasAnyRoles>

<br/>

<@shiro.hasPermission name="user:add1">
用户[<@shiro.principal/>]拥有user:add权限
</@shiro.hasPermission>
</body>
</html>