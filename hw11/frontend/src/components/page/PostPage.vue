<template>
    <div>
        <Post class="middlePost" :post="post" :commentsNumber="commentsNumber" :key="commentsNumber"/>
        <template v-if="user">
            <div class="form">
                <div class="body">
            <form @submit.prevent="onWriteComment">
        <div class="field">
            <div class="name">
                <label for="text">Write your comment:</label>
            </div>
            <div class="value">
                <textarea id="text" name="text" v-model="text"></textarea>
            </div>
        </div>
            <div class="error">{{ error }}</div>
            <div class="button-field">
                <input type="submit" value="Write">
            </div>
            </form>
                </div>
            </div>

        </template>

        <Comment v-for="comment in comments" :comment="comment" :user="comment.user.login" :key="comment.id"/>
    </div>
</template>

<script>
    import Post from "./Post.vue";
    import Comment from "./Comment.vue";
    import axios from "axios";

    export default {
        name: "PostPage",
        data: function () {
            return {
                text: "",
                error: "",
                comments: [],
                commentsNumber: 0
            }
        },
        components: {
            Post, Comment
        },
        props: ["post", "user"],
        methods: {
            onWriteComment: function () {
                this.error = "";
                this.$root.$emit("onWriteComment", this.text, this.post);
                if (this.error === "") {
                    this.text = "";
                }

            }
        },
        beforeCreate() {
            this.$root.$on("onWriteCommentValidationError", error => this.error = error);
            this.$root.$on("onWriteCommentFinished", () => axios.get("/api/1/posts/comments", {params: {postId: this.post["id"]}}).then(response => {
                this.comments = response.data;
                this.commentsNumber = response.data.length;
            }))
        },
        created() {
            axios.get("/api/1/posts/comments", {params: {postId: this.post["id"]}}).then(response => {
                this.comments = response.data;
                this.commentsNumber = response.data.length;
            });

        }
    }
</script>

<style scoped>

</style>
