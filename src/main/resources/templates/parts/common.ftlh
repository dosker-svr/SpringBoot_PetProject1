<#-- сами макросы - #, а вызов их - @ -->
<#macro page>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="/static/style.css">
</head>
<body>
<#nested> <#-- секция в которой содержится вложенный код -->
</body>
</html>
</#macro>
<#--  -->