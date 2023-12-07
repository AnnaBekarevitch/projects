<template>
    <article>
        <div class="information">By {{user}}, 1 day ago</div>
        <div class="body">{{comment.text}}</div>
    </article>
</template>

<script>
    export default {
        name: "Comment",
        props: ["comment", "user"]
    }
</script>