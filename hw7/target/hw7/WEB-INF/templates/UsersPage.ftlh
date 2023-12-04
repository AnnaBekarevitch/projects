<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <#if user??>
                 <#if user.isAdmin()>
                     <th>Admin</th>
                 </#if>
                </#if>
            </tr>
            </thead>
            <tbody>
            <template>
                <tr>
                    <td class="user-id"></td>
                    <td class="user_login"></td>
                    <#if user??>
                 <#if user.isAdmin()>
                    <td class="user-status">
                        <button type="button"></button>
                    </td>
                 </#if>
                    </#if>
                </tr>
            </template>
            <tr class="noData">
                <td colspan="3">
                    No data
                </td>
            </tr>
            </tbody>
        </table>
    </div>

    <script>
        $(function () {
            const $noData = $(".users .noData");
            $noData.find("td").text("Loading...");

            $.ajax({
                dataType: "json",
                data: {
                    action: "findAll"
                },
                success: function(response) {
                    response["users"].forEach(function(user) {
                        $noData.hide();
                        const $tr = $($(".users template").prop("content")).clone();
                        $tr.find(".user-id").text(user["id"]);
                        $tr.find(".user_login").text(user["login"]);
                        $tr.find(".user-status button").text(user["admin"] == true ? "disable" : "enable");
                        $tr.find("tr").attr("data-userId", user["id"]);
                        $tr.find("button").attr("data-userId", user["id"]);

                        $(".users tbody").append($tr);

                    });
                    $(".users tr").click(function() {
                        $.ajax({
                            dataType: "json",
                            data: {
                                action: "findUser",
                                userId: $(this).attr("data-userId")
                            },
                            success: function (response) {
                                notify(response["user"].creationTime);
                            },
                            type: "POST"
                        })
                    });

                    $(".users button").click(function() {
                        const button = $(this);
                        const $error = $(this).find(".error");
                        $.ajax({
                            dataType: "json",
                            data: {
                                action: "changeStatus",
                                userId: $(this).attr("data-userId"),
                            },
                            success: function (response) {

                                if (response["error"]) {
                                    $error.text(response["error"]);
                                } else if (response["redirect"]) {
                                    location.href = response["redirect"];
                                } else {
                                    if (button.text() === "disable") {
                                        button.text("enable");
                                    } else {
                                        button.text("disable");
                                    }
                                }
                            },
                            type: "POST"
                        })
                    });
                },
                type: "POST"
            });
        })
    </script>
</@c.page>
