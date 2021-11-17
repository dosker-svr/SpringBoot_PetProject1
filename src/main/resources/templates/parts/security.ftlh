<#-- простой хэлпер с описанием необходимых переменных -->

<#-- позволяет определять несколько переменных прямо в шаблонах : -->
<#-- проверяем существует ли в контексте необходимый объект -->
<#-- SPRING_SECURITY помещает в контекст freemarker специальный объект, позволяющий использовать контекст SPRING_SECURITY-->
<#-- если 'known' определен в контексте (??), значит мы можем работать с сессией пользователя -->
<#assign
    known = Session.SPRING_SECURITY_CONTEXT??
>

<#if known> <#-- если сессия существ., то : -->
    <#-- сначала определяем набор необходимых данных -->
    <#-- 'Session.SPRING_SECURITY_CONTEXT.authentication.principal' - будет содержать user из db (User.java) -->
    <#assign
        user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
        name = user.getUsername()
        isAdmin = user.isAdmin()
    >
<#else> <#-- иначе отображаем заглушку : -->
    <#assign
        name = "unknown"
        isAdmin = false
    >
</#if>