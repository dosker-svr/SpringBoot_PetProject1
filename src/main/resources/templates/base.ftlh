<#-- перестали использовать Mustache, т.к. дублируем код, а в Freemarker(шаблонизатор) можно работать с макросами -->
<#import "parts/common.ftlh" as c>
<#--<#import "parts/login.ftlh" as l>-->
<@c.page>
<#-- только после авторизации, пользователь сможет увидеть остальную часть страницы ∨∨∨-->
<#-- <div>
        <@l.logout />
        <span><a href="/user">Users List</a></span>
    </div>-->
    <div class="form-row">
        <div class="form-group col-md-6">
            <form method="get" action="/base" class="form-inline">           <#-- форма фильтрации данных в db-->
                <input type="text" name="filter" value="${filter?ifExists}" placeholder="Search by filter"/>
                <button type="submit" class="btn btn-primary ml-2 mb-2">Search</button>
            </form>
        </div>
    </div>
    <a class="btn btn-primary mb-1" data-bs-toggle="collapse" href="#collapseMessage" role="button"
       aria-expanded="false" aria-controls="collapseMessage">
        Add new Message
    </a>
    <div class="collapse" id="collapseMessage">
        <div class="form-group col-sm-7">
            <form method="post" enctype="multipart/form-data">                       <#-- форма ввода данных в db-->
                <div class="form-group">
                    <input type="text" class="form-control" name="text" placeholder="Введите сообщение"/>
                </div>
                <div class="form-group">
                    <input type="text" class="form-control" name="tag" placeholder="Тэг">
                </div>
                <div class="form-group">
                    <div class="custom-file">
                        <input type="file" name="file" id="customFile">
                        <label class="custom-file-label" for="customFile">Choose File</label>
                    </div>
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/> <#-- любой запрос на сервер не сопровождающийся
                                                                             этим токеном будет игнорироваться сервером.
                                                                             токен нужен только для post запросов-->
                <div class="form-group">
                    <button type="submit" class="btn btn-primary">Добавить</button> <#-- mt-1 -->
                </div>
            </form>
        </div>
    </div>
    <div>Список сообщений</div>

    <div class="row row-cols-3 row-cols-md-3 g-10">
        <#list messages as message>                 <#--цикл обхода списков во freemarker -->
            <div class="card my-3">
                <#--<b>${message.id}</b>-->
                <#if message.filename??>
                    <img src="/img/${message.filename}" class="card-img-top"/>
                </#if>
                <div class="m-2">
                    <span>${message.text}</span>
                    <i>${message.tag}</i>
                </div>
                <div class="card-footer text-muted">
                    ${message.authorName}
                </div>

            </div>
        <#else>
            No messages
        </#list>
    </div>

</@c.page>