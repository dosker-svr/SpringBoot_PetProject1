<#macro login path isRegisterForm> <#-- макрос под названием 'login' с аргументом 'path', 'isRegisterForm'-->
    <form action="${path}" method="post"> <#-- как раз тут используем этот аргумент, который потом укажем тут:
                                             <#import "parts/login.ftlh" as l>
                                             <@l.login "/login" />                  -->
        <div class="form-group row">
            <label class="col-sm-1 col-form-label mb-1">User Name:</label>
            <div class="col-sm-4">
                <input type="text" name="username" class="form-control" placeholder="User Name"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-1 col-form-label  mb-1">Password:</label>
            <div class="col-sm-4">
                <input type="password" name="password" class="form-control" placeholder="Password"/> <#-- type="password" - возможно "text"-->
            </div>
        </div>
        <#if isRegisterForm>
        <div class="form-group row">
            <label class="col-sm-1 col-form-label  mb-1">Email:</label>
            <div class="col-sm-4">
                <input type="email" name="email" class="form-control" placeholder="some@some.com"/> <#-- type="email" - возможно "text"-->
            </div>
        </div>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <#if !isRegisterForm> <#-- показывает эту ссыль только если это не форма регистрации -->
            <a href="/registration">Registration for new User</a>
        </#if>
        <button class="btn btn-primary" type="submit">
            <#if isRegisterForm>
                Create
                <#else>
                Sign In
            </#if>
        </button>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}" /> <#-- любой запрос на сервер не сопровождающийся этим
        токеном будет игнорироваться сервером-->
        <button class="btn btn-primary" type="submit">Sign Out</button><#--<input type="submit" value="Sign Out"/>-->
    </form>
</#macro>