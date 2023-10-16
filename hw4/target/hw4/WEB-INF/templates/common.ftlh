<#-- @ftlvariable name="post" type="ru.itmo.web.hw4.model.Post" -->
<#macro page underHeader="">
    <link href="css/blog.css" rel="stylesheet">
    <!DOCTYPE html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <title>Codeforces</title>
        <link rel="stylesheet" href="/css/normalize.css">
        <link rel="stylesheet" href="/css/style.css">
        <link rel="icon" href="/favicon.ico">
    </head>
    <body>
    <@header underHeader/>
    <div class="middle">
        <@sidebar/>
        <main>
            <#nested/>
        </main>
    </div>
    <@footer/>
    </body>
    </html>
</#macro>

<#macro header underHeader="">
    <link href="css/blog.css" rel="stylesheet">
    <header>
        <a href="#" class="logo"><img alt="Codeforces" title="Codeforces"
                                      src="/img/codeforces-logo-with-telegram.png"></a>
        <div class="languages">
            <a href="#"><img alt="In English" title="In English"
                             src="/img/gb.png"></a>
            <a href="#"><img alt="In Russian" title="In Russian"
                             src="/img/ru.png"></a>
        </div>
        <div class="enter-or-register-box">
            <#if user??>
                <@userlink user=user/>
                |
                <a href="/logout">Logout</a>
            <#else>
                <a href="/enter">Enter</a>
                |
                <a href="/register">Register</a>
            </#if>
        </div>
        <nav>
            <#assign items=[
            {"href": "/index", "name": "Home"},
            {"href": "/misc/help", "name": "Help"},
            {"href": "/users","name":"Users"},
            {"href": "/contests", "name": "Contests"}
            ]/>
            <ul>
                <#list items as item>
                    <li>
                        <#if item.name == underHeader>
                            <u style="text-decoration-color: #3B5998; text-decoration-thickness: 0.2rem"><a
                                        href="${item.href}">${item.name}</a></u>
                        <#else >
                            <a href="${item.href}">${item.name}</a>

                        </#if>
                    </li>
                </#list>
            </ul>
        </nav>
    </header>
</#macro>

<#macro sidebar>
    <link href="css/blog.css" rel="stylesheet">
    <aside>

        <link href="css/blog.css" rel="stylesheet">
        <aside>

            <#list posts as nowPost>
                <section>
                    <div class="header">Post# ${nowPost.getId()} </div>
                    <@post nowPost users true> </@post>
                    <div class="footer">
                        <a href="/post?post_id=${nowPost.getId()}">View all</a>
                    </div>
                </section>
            </#list>



        </aside>

        </aside>
</#macro>

<#macro footer>
    <footer>
        2010-2023 &copy; by Mike Mirzayanov
    </footer>
</#macro>

<#macro userlink user>
<#-- @ftlvariable name="user" type="ru.itmo.web.hw4.model.User" -->
    <#if user.logged>
        <a href="/user?handle=${user.handle}">${user.name}</a>
    <#else >
        <a href="/user?handle=${user.handle}" class="mylinkblock" style=
        <#if user.color == "RED">"color: red"
        <#elseif user.color == "GREEN">"color: green"
        <#elseif user.color == "BLUE">"color: blue" </#if>>${user.name}</a>
    </#if>
</#macro>

<#macro post post users length>
<#-- @ftlvariable name="post" type="ru.itmo.web.hw4.model.Post" -->
    <link href="css/blog.css" rel="stylesheet">
    <article>
        <#assign user=findBy(users, "id", post.user_id)>
        <#assign name=user.name>
        <div class="title">${post.title}</div>
        <div class="information">By <@userlink user=user></@userlink>, 10 days ago, translation</div>
        <div class="body">
            <#if length>
            ${post.text?truncate(350,"...")}
            <#else>
                ${post.text}
            </#if>

        </div>
        <ul class="attachment">
            <li>Announcement of <a href="#">Codeforces Round #510 (Div. 1)</a></li>
            <li>Announcement of <a href="#">Codeforces Round #510 (Div. 2)</a></li>
        </ul>
    </article>

</#macro>

<#macro blogfooter>
<div class="footer">
    <div class="left">
        <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
        <span class="positive-score">+200</span>
        <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
    </div>
    <div class="right">
        <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
        10 days ago
        <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
        <a href="#">100</a>
    </div>
</div>
</#macro>
<#macro table users>
    <link href="css/table.css" rel="stylesheet">
    <div class="datatable">
        <div class="caption">Users</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>User</th>
                <th>Handle</th>
            </tr>
            </thead>
            <tbody>
            <#list users as u>
                <tr>
                    <td>${u.id}</td>
                    <td>
                        <@userlink user=u></@userlink>
                    </td>
                    <td>${u.handle}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</#macro>


<#function findIndexBy items key value>
    <#list items as item>
        <#if item[key] == value>
            <#return item_index/>
        </#if>
    </#list>
</#function>


<#function findBy items key value>
    <#assign index = findIndexBy(items, key, value)>
    <#if index?has_content>
        <#return items[index]/>
    </#if>
</#function>