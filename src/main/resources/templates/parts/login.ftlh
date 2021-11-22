<#macro login path isRegisterForm> <#-- макрос под названием 'login' с аргументом 'path', 'isRegisterForm'-->
    <form action="${path}" method="post"> <#-- как раз тут используем этот аргумент, который потом укажем тут:
                                             <#import "parts/login.ftlh" as l>
                                             <@l.login "/login" />                  -->
        <div class="form-group row">
            <label class="col-sm-1 col-form-label mb-1">User Name:</label>
            <div class="col-sm-4">
                <input type="text"
                       name="username"
                       value="<#if user??>${user.username}</#if>"
                       class="form-control ${(usernameError??)?string('is-invalid', '')}"
                       placeholder="User Name"/>
                <#if usernameError??>
                    <div class="invalid-feedback">
                        ${usernameError}
                    </div>
                </#if>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-1 col-form-label  mb-1">Password:</label>
            <div class="col-sm-4">
                <input type="password"
                       name="password"
                       class="form-control ${(passwordError??)?string('is-invalid', '')}"
                       placeholder="Password"/>
                <#if passwordError??>
                    <div class="invalid-feedback">
                        ${passwordError}
                    </div>
                </#if>
            </div>
        </div>
        <#if isRegisterForm>
            <div class="form-group row">
                <label class="col-sm-1 col-form-label  mb-1">Password:</label>
                <div class="col-sm-4">
                    <input type="password"
                           name="password2"
                           class="form-control ${(password2Error??)?string('is-invalid', '')}"
                           placeholder="Retype password"/>
                    <#if password2Error??>
                        <div class="invalid-feedback">
                            ${password2Error}
                        </div>
                    </#if>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-1 col-form-label  mb-1">Email:</label>
                <div class="col-sm-4">
                    <input type="email"
                           name="email"
                           value="<#if user??>${user.email}</#if>"
                           class="form-control ${(emailError??)?string('is-invalid', '')}"
                           placeholder="some@some.com"/>
                    <#if emailError??>
                        <div class="invalid-feedback">
                            ${emailError}
                        </div>
                    </#if>
                </div>
            </div>
            <#-- добавляем каптчу: -->
            <div class="col-sm-4">
                <div class="g-recaptcha" data-sitekey="6LfC804dAAAAADtzwVW0ZctxfV1QQNz5XZ_nLTDO"></div>
                <#if captchaError??>
                    <div class="alert alert-danger" role="alert">
                        ${captchaError}
                    </div>
                </#if>
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