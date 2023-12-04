<#import "commons.ftlh" as c>

<@c.page>
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
            <#if users?size==0>
                <tr class="noData">
                    <td colspan="3">
                        No data
                    </td>
                </tr>
            <#else>

                <#list users as user>
                    <tr>

                        <td class="id">${user.id}</td>
                        <td class="login">${user.login}</td>
                        <td class="creationTime">${user.creationTime}</td>

                        <td class="button-field">
                            <#if currentUser??>
                                <#if currentUser.id != user.id>
                                    <form method="post" action="">
                                        <input type="hidden" name="action" value=${user.getDisabledStatus()}>
                                        <input type="hidden" name="id" value=${user.getId()}>
                                        <input type="submit" value=${user.getDisabledStatus()}>
                                    </form>
                                </#if>
                            </#if>
                        </td>
                    </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>
</@c.page>
