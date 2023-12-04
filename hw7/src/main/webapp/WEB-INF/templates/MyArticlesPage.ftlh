

<#-- @ftlvariable name="articles" type="ru.itmo.wp.model.domain.Article[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="my-articles datatable">
        <div class="caption">Articles</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Title</th>
                <th>Show/Hide</th>
            </tr>
            </thead>
            <tbody>
            <#list articles as currentArticle>
                <tr>

                    <td class="article-id"> ${currentArticle.getId()} </td>
                    <td class="article-title"> ${currentArticle.getTitle()} </td>
                    <td class="article-hidden">
                        <button data-article-id=${currentArticle.getId()} data-user-id=${user.getId()}>${currentArticle.getStringHidden()}</button>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>

    <script>
        $(".my-articles button").click(function () {
            const button = $(this);
            const $error = $(this).find(".error");
            $.ajax({
                type: "POST",
                url: "",
                dataType: "json",
                data: {
                    action: "changeHidden",
                    articleId: $(this).attr("data-article-id"),
                    userId: $(this).attr("data-user-id")
                },
                success: function (response) {
                    if (response["error"]) {
                        $error.text(response["error"]);
                    } else if (response["redirect"]) {
                        location.href = response["redirect"];
                    } else {
                        if (button.text() === "Hide") {
                            button.text("Show");
                        } else if (button.text() === "Show") {
                            button.text("Hide");
                        }
                        notify("Article status changed");
                    }
                }
            });
        })
    </script>
</@c.page>