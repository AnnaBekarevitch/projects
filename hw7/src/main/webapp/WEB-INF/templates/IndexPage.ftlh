<#import "commons.ftlh" as c>

<@c.page>

    <ul class="articles">
        <li class="no-data"></li>

        <template>
    <div>
    <article>
        <div class="title"></div>
        <div class="information">By <span class="author"></span>, <span class="creation-time"></span></div>
        <div class="body"></div>

            <ul class="attachment">
                <li>Announcement of <a href="#">Codeforces Round #510 (Div. 1)</a></li>
                <li>Announcement of <a href="#">Codeforces Round #510 (Div. 2)</a></li>
            </ul>
            <div class="footer">
                <div class="left">
                    <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
                    <span class="positive-score">+173</span>
                    <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
                </div>
                <div class="right">
                    <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                    2 days ago
                    <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
                    <a href="#">68</a>
                </div>
            </div>
                </article>
</div>

        </template>
    </ul>

    <script>
        $(function () {
            const $articles = $(".articles");
            $articles.find(".no-data").text("Loading articles...");
            $.ajax({
                dataType: "json",
                data: {
                    action: "findAllShowing"
                },
                success: function (response) {
                    $articles.find(".no-data").remove()
                    response.articles.forEach(function (article) {
                        const $tpl = $($articles.find("template").prop("content")).clone();
                        $tpl.find(".title").text(article.title);
                        $tpl.find(".body").text(article.text);
                        $tpl.find(".author").text(article.userLogin);
                        $tpl.find(".creation-time").text(article.creationTime.toString());
                        $articles.append($tpl);
                    });
                },
                type: "POST"
            });
        })
    </script>
</@c.page>