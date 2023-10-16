<#import "./common.ftlh" as c >

<@c.page "Posts">
    <#list posts as nowPost>
            <#if nowPost.getUser_id() == user_id>
                <section>
            <@c.post nowPost users false> </@c.post>

                    <@c.blogfooter></@c.blogfooter>
        </section>

            </#if>
    </#list>

</@c.page>