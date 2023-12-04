<#-- @ftlvariable name="targetUserLogin" type="java.lang.String" -->
<#-- @ftlvariable name="user" type="ru.itmo.wp.model.domain.User" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->
<#-- @ftlvariable name="talks" type="ru.itmo.wp.model.domain.Talk[]" -->
<#import "commons.ftlh" as c>

<@c.page>





    <div class="form-box">
        <div class="header">Talk</div>
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value="submit"/>




                <input type="hidden" name="action" value="submit"/>
                <div class="field">
                    <div class="name">
                        <label for="targetUserLogin">Target user login</label>
                    </div>

                    <td>
                        <select id="targetUserLogin" name="targetUserLogin">
                            <#list users as targetUser>
                                <option value="${targetUser.getLogin()}"> ${targetUser.getLogin()}</option>
                            </#list>
                        </select>
                    </td>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="text">Message</label>
                    </div>
                    <div class="value">
                        <input id="text" name="text" value="${text!}"/>
                    </div>
                </div>
                <#if error??>
                    <div class="error">
                        ${error!}
                    </div>
                </#if>
                <div class="button-field">
                    <input type="submit" value="Submit">
                </div>
            </form>
        </div>
    </div>

    <#list talks as currentTalk>
        <section>
            <@c.talk currentTalk></@c.talk>
        </section>
    </#list>

</@c.page>
