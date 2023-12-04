<#-- @ftlvariable name="addNoticeForm" type="ru.itmo.wp.form.NoticeCredentials" -->
<#import "/spring.ftl" as spring>
<#import "commons.ftlh" as c>

<@c.page>
    <div class="addNotice form-box">
        <div class="header">Notice</div>
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value="enter"/>

                <div class="field">
                    <div class="name">
                        <label for="content">Notice</label>
                    </div>
                    <div class="value">
                        <textarea id="content" name="content" value="${addNoticeForm.content!}"></textarea>

                    </div>

                    <@c.error "addNoticeForm.content"/>
                </div>

                <div class="button-field">
                    <input type="submit" value="Add">
                </div>
            </form>
        </div>
    </div>
</@c.page>
