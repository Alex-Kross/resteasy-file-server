<%--
  Created by IntelliJ IDEA.
  User: KarpukAY
  Date: 23.03.2023
  Time: 16:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>JAX-RS Upload Form</h1>

    <form action="http://localhost:8080/restyeasy/rws/file-system/upload/" method="post" enctype="multipart/form-data">
<%--    <form action="rws/file-system/upload" method="post" enctype="multipart/form-data">--%>

        <p>
            Select a file : <input type="file" name="uploadedFile" size="50" />
        </p>

        <input type="submit" value="Upload It" />
    </form>
</body>
</html>
