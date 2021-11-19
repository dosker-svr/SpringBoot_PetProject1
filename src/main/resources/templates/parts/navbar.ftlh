<#include "security.ftlh" >
<#import "login.ftlh" as l>
<nav class="navbar navbar-expand-lg navbar-light bg-light"> <#-- панель навигации меняется в зависимости от ширины экрана (navbar-expand-lg)
        (navbar-light bg-light) - цветовая схема панели -->
    <div class="container-fluid">
        <a class="navbar-brand" href="/">Web Application</a> <#-- описываем название проекта -->

        <#-- тут описываем кнопку меню (появляющуюся на экранах меньше чем -lg): -->
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <#-- далее описываем наше меню :  -->
        <div class="collapse navbar-collapse" id="navbarSupportedContent"> <#-- указываем что меню схлопываемое -->
            <ul class="navbar-nav me-auto mb-2 mb-lg-0"> <#-- основа меню - упорядоченный список. меню -автоматически генерируемое -->
                <li class="nav-item">
                    <a class="nav-link" href="/">Home</a> <#-- навигационная ссылка -->
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/base">Messages</a>
                </li>
                <#if isAdmin>
                <li class="nav-item">
                    <a class="nav-link" href="/user">List Users</a>
                </li>
                </#if>
                <#if user??> <#-- user?? - в 'security.ftlh' если user авторизован -->
                    <li class="nav-item">
                        <a class="nav-link" href="/user/profile">Profile</a>
                    </li>
                </#if>
            </ul>
            <div class="navbar-text me-3" >${name}</div> <#--  -->
            <@l.logout />
        </div>
    </div>
</nav>