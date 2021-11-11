<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>
<@c.page>
    <div>
        <@l.logout />
    </div>
<#-- только после авторизации, пользователь сможет увидеть остальную часть страницы ∨∨∨-->
    <div>
        <form method="post">                       <#-- форма ввода данных в db-->
            <input type="text" name="text" placeholder="Введите сообщение"/>
            <input type="text" name="tag" placeholder="Тэг"/>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/> <#-- любой запрос на сервер не сопровождающийся этим
                                                                         токеном будет игнорироваться сервером-->
            <button type="submit">Добавить</button>
        </form>
    </div>
    <div>Список сообщений</div>
    <form method="get" action="/base">           <#-- форма фильтрации данных в db-->
        <input type="text" name="filter" value="${filter}"/>
        <button type="submit">Найти</button>
    </form>
    <#list messages as message>                 <#--списки в freemarker выглядят вот так ∨∨∨-->
        <div>
            <b>${message.id}</b>
            <span>${message.text}</span>
            <i>${message.tag}</i>
            <strong>${message.authorName}</strong>
        </div>
    <#else>
        No messages
    </#list>                <#--списки в freemarker выглядят вот так ^^^-->
</@c.page>