<#-- @ftlvariable name="post" type="ru.itmo.wp.domain.Post" -->
<#import "commons.ftlh" as c>

<@c.page>
    <#if post??>
    <@c.post post=post/>
    <#if user??>
        <form method="post" action="">
            <input type="hidden" name="action" value="writeComment"/>
            <div class="field">
                <div class="name">
                    <label for="text">Comment</label>
                </div>
                <div class="value">
                    <textarea id="text" name="text">${comment.text!}</textarea>
                </div>
                <@c.error "comment.text"/>
            </div>
            <div class="button-field">
                <input type="submit" value="Write">
            </div>
        </form>
    </#if>
        <#if post.comments?? && post.comments?size!=0>
            <#list post.comments as comment>
                <@c.comment comment=comment/>
            </#list>
        <#else>
        </#if>
   <#else>
    No such post
    </#if>

</@c.page>
