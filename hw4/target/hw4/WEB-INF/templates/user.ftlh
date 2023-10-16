<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#-- @ftlvariable name="user_id" type="java.lang.Long" -->
<#-- @ftlvariable name="logged_user_id" type="java.lang.Long" -->
<#import "./common.ftlh" as c>
<#import "./posts.ftlh" as postsPage>

<@c.page>
    <#if handle?has_content>
        <#assign index = c.findIndexBy(users, "handle", handle)!/>
    <#elseif user_id?has_content>
        <#assign index = c.findIndexBy(users, "id", user_id)!/>
    </#if>

    <div class="user-profile"
         style="border: 1px solid var(--border-color);
                border-radius: var(--border-radius);
                overflow: hidden;">
    <div class="info" style="float: left;">
    <span style="font-size: 1.5rem;
                    padding: 0 0.5rem;">
            </span>
    <#if index?has_content>
        <#assign currentUser = users[index]/>
        <#if currentUser?has_content>
            <span style="font-size: 2rem;"> <@c.userlink user=currentUser/> </span>

            <ul>
                <li>id: ${currentUser.id}</li>
                <li>handle: ${currentUser.handle}</li>
                <li>name: ${currentUser.name}</li>
                <li>number of posts: ${currentUser.numberOfPosts}</li>
                
            </ul>
            </div>
            <img src="img/photo.jpg" alt="profile photo"
                 style="float: right;">
            </div>
            <#assign prevUser = nextUser(users,"handle",currentUser.handle,-1)!/>
            <#assign nextUser = nextUser(users,"handle",currentUser.handle,1)!/>
            <@printLink linkName=prevUser char="⟵"></@printLink>
            <@printLink linkName=nextUser char="⟶"></@printLink>
        <#else>
            No such user
        </#if>
    <#else>
        No such user
    </#if>

</@c.page>

<#function nextUser items key value step>
    <#list items as item>
        <#if item[key] == value>
            <#return items[item_index+step]/>
        </#if>
    </#list>
</#function>

<#macro printLink linkName char>
    <#if linkName?has_content>
        <a href="/user?handle=${linkName["handle"]}" class="nextlink"
           style="color: blue;"> ${char} </a>
    <#else>
        <span class="nextlink" style="color: lightgray;">  ${char} </span>
    </#if>
</#macro>