<%@ page session="false" %>
<%
    HttpSession session=request.getSession(false);
    if(session==null)
    {response.sendRedirect("index.jsp");}
    else
    {String email=(String)session.getAttribute("email");}
%>

<html>
    <body>
        <style>
            body {
              background-image: url('img/mainn.png');
              background-size: 100% 100%;}
        </style>
        <form name="data" method="POST" action="addRecord">
            Website: <input type="text" name="website">
            Username: <input type="text" name="username">
            Password: <input type="text" name="password">
            <button type="submit">Add</button>
        </form>
    </body>
</html>