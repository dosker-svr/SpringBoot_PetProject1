<#-- перестали использовать Mustache, т.к. дублируем код, а в Freemarker(шаблонизатор) можно работать с макросами -->
<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>
<@c.page>
<#-- только после авторизации, пользователь сможет увидеть остальную часть страницы ∨∨∨-->
    <div>
        <@l.logout />
        <span><a href="/user">Users List</a></span>
    </div>
    <div>
        <form method="post" enctype="multipart/form-data">                       <#-- форма ввода данных в db-->
            <input type="text" name="text" placeholder="Введите сообщение" />
            <input type="text" name="tag" placeholder="Тэг">
            <input type="file" name="file">
            <input type="hidden" name="_csrf" value="${_csrf.token}" /> <#-- любой запрос на сервер не сопровождающийся
                                                                         этим токеном будет игнорироваться сервером.
                                                                         токен нужен только для post запросов-->
            <button type="submit">Добавить</button>
        </form>
    </div>
    <div>Список сообщений</div>
    <form method="get" action="/base">           <#-- форма фильтрации данных в db-->
        <input type="text" name="filter" value="${filter?ifExists}" />
        <button type="submit">Найти</button>
    </form>
    <#list messages as message>                 <#--цикл обхода списков во freemarker -->
        <div>
            <b>${message.id}</b>
            <span>${message.text}</span>
            <i>${message.tag}</i>
            <strong>${message.authorName}</strong>
            <div>
                <#if message.filename??>
                    <img src="/img/${message.filename}" />
                </#if>
            </div>
        </div>
    <#else>
        No messages
    </#list>
</@c.page>