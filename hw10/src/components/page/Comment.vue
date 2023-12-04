<template>
    <article>
        <div class="information">By {{users[comment.userId].login}}, 1 day ago</div>
        <div class="body">{{comment.text}}</div>
    </article>
</template>

<script>
    export default {
        name: "Comment",
        props: ["comment", "users"]
    }
</script>