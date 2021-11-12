<#import "parts/common.ftlh" as c>

<@c.page>
    List of Users
    <table>
        <thead>
        <tr>
            <th>Name</th>
            <th>Role</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <#list users as user>
            <tr>
                <td>${user.username}</td> <#-- ячейки с данными пользователя -->
                <td><#list user.roles as role>${role}<#sep>, </#list></td> <#-- <#sep>,  - это ', ' после каждого эл-та списка -->
                <td><a href="/user/${user.id}">edit</a></td>
            </tr>
        </#list>
        </tbody>
    </table>
</@c.page>