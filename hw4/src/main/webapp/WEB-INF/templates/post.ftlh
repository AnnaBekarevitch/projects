<#import "./common.ftlh" as c >

<@c.page "Post">
    <#assign nowPost=c.findBy(posts, "id", post_id)>
    <@c.post nowPost users false> </@c.post>

    <@c.blogfooter></@c.blogfooter>
</@c.page>
