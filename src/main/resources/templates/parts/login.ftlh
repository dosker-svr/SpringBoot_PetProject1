<#macro login path> <#-- макрос под названием 'login' с аргументом 'path'-->
    <form action="${path}" method="post"> <#-- как раз тут используем этот аргумент, который потом укажем тут:
                                             <#import "parts/login.ftlh" as l>
                                             <@l.login "/login" />                  -->
        <div><label> User Name : <input type="text" name="username"/> </label></div>
        <div><label> Password: <input type="password" name="password"/> </label></div>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <div><input type="submit" value="Sign In"/></div>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}" /> <#-- любой запрос на сервер не сопровождающийся этим
        токеном будет игнорироваться сервером-->
        <input type="submit" value="Sign Out"/>
    </form>
</#macro>