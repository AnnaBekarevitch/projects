<#import "commons.ftlh" as c>

<@c.page>
    <#if user??>
                <div class="users datatable">
                <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th>Created</th>
            </tr>
            </thead>
            <tbody>
                    <tr>
                            <td class="id">${user.id}</td>
                        <td class="login">${user.login}</td>
                        <td class="creationTime">${user.creationTime}</td>
                    </tr>
            </tbody>
        </table>
                </div>
            <#else>
                <div> No such user </div>
            </#if>

</@c.page>
