<template>

    <article>
        <div class="title"> <a href="#" @click.prevent="onOpenPost('PostPage', post.id)"> {{ post.id }} {{ post.title }} </a></div>
        <div class="information">By  two days ago </div>
        <div class="body">{{ post.text }}</div>

    <ul class="attachment">
        <li>Announcement of <a href="#">Codeforces Round #510 (Div. 1)</a></li>
        <li>Announcement of <a href="#">Codeforces Round #510 (Div. 2)</a></li>
    </ul>

    <div class="footer">
        <div class="right">
            <img src="@/assets/img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
            {{post.creationTime.substring(0, post.creationTime.indexOf('T'))}}
            <img src="@/assets/img/comments_16x16.png" title="Comments" alt="Comments"/>
            {{commentsNumber}}
        </div>
    </div>
    </article>

</template>


<script>
    import axios from "axios";

    export default {
        name: "Post",
        props: ["post"],
        data: function () {
            return {
                commentsNumber: 0
            }
        },
        methods: {
            onOpenPost: function (page, postId) {
                this.$root.$emit("onOpenPost", page, postId)
            }

        },
        created() {
            axios.get("/api/1/posts/comments_count", {params: {postId: this.post["id"]}}).then(response => {
                this.commentsNumber = response.data;
            });
        }
    }
</script>

<style scoped>
    section {
        margin-bottom: 1rem;
    }
</style>
