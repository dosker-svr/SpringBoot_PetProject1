<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>
    <div class="mb-1">Add new User</div>
${message?ifExists}  <#--т.е. если у нас есть поле 'message'(для model в любом из Controller на "/registration"), то мы его показываем-->
<@l.login "/registration" true/>
</@c.page>