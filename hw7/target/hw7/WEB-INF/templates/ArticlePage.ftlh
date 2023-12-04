<#import "commons.ftlh" as c>

<@c.page>
    <div class="article form-box">
        <div class="header">Article</div>
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value="create"/>
                <div class="field">
                    <div class="name">
                        <label for="title">Title</label>
                    </div>
                    <div class="value">
                        <input id="title" name="title" value="${title!}"/>
                    </div>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="text">Tell us your story:</label>
                    </div>
                    <div class="value">
                    <textarea id="text" name="text" rows="5" cols="33" value="${text!}"></textarea>

                    </div>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="text">Show</label>
                    </div>
                    <div class="value">
                        <input type="radio" checked='checked' name="storeSquirrel" class="custom-control-input" id="storeSquirrel" value=0>
                    </div>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="text">Hide</label>
                    </div>
                    <div class="value">
                        <input type="radio" checked='checked' name="storeSquirrel" class="custom-control-input" id="storeSquirrel" value=1>
                    </div>
                </div>
                <div class="button-field">
                    <input type="submit" value="submit">
                </div>
            </form>
        </div>
    </div>
    <script>
        $(function () {
            $(".article form").submit(function () {
                const hidden = Boolean($(this).find("input[name='storeSquirrel']:checked").val());

                const title = $(this).find("input[name='title']").val();
                const text = $(this).find("textarea[name='text']").val();
                const $error = $(this).find(".error");
                ajax({
                    action: "create",
                    title: title,
                    text: text,
                    hidden: hidden
                }, $error);
                return false;
            });
        })
    </script>
</@c.page>




