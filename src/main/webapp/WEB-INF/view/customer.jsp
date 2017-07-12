<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<table>
    <tr>
        <th>客户名称</th>
        <th>联系人</th>
        <th>电话号码</th>
        <th>邮箱地址</th>
        <th>操作</th>
    </tr>

    <c:forEach var="customer" items="${customList}">
    <tr>
        <td>${customer.name}</td>
        <td>${customer.contact}</td>
        <td>${customer.telephone}</td>
        <td>${customer.email}</td>
        <td>
            <a href="<%=basePath%>/customer_edit?id=${customer.id}">编辑</a>
            <a href="<%=basePath%>/customer_delete?id=${customer.id}">删除</a>
        </td>
    </tr>
    </c:forEach>
</table>
</body>
</html>
