window.notify = function (message) {
    $.notify(message, {
        position: "right bottom",
        className: "success"
    });
}

window.ajax = (args, $error) => $.ajax({
    type: "POST",
    url: "",
    dataType: "json",
    data: args,
    success: function (response) {
        if (response["error"]) {
            $error.text(response["error"]);
        } else {
            location.href = response["redirect"];
        }
    }
});