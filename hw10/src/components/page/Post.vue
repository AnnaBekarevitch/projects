<template>

    <article>
        <div class="title"> <a href="#" @click.prevent="onOpenPost('PostPage', post.id)"> {{ post.id }} {{ post.title }} </a></div>
        <div class="information">By {{ users[post.userId].login }} two days ago </div>
        <div class="body">{{ post.text }}</div>

    <ul class="attachment">
        <li>Announcement of <a href="#">Codeforces Round #510 (Div. 1)</a></li>
        <li>Announcement of <a href="#">Codeforces Round #510 (Div. 2)</a></li>
    </ul>

    <div class="footer">
        <div class="left">
            <img src="@/assets/img/voteup.png/" title="Vote Up" alt="Vote Up"/>
            <span class="positive-score">+173</span>
            <img src="@/assets/img/votedown.png" title="Vote Down" alt="Vote Down"/>
        </div>
        <div class="right">
            <img src="@/assets/img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
            24.11.2023
            <img src="@/assets/img/comments_16x16.png" title="Comments" alt="Comments"/>
            <a href="#">{{ Object.values(this.comments).filter(comment => comment.postId === post.id).length }}</a>
        </div>
    </div>
    </article>

</template>


<script>
    export default {
        name: "Post",
        props: ["post", "comments", "users"],
        methods: {
            onOpenPost: function (page, postId) {
                this.$root.$emit("onOpenPost", page, postId)
            }
        }
    }
</script>

<style scoped>
    section {
        margin-bottom: 1rem;
    }
</style>
