<template>
    <div>
        <Post :post="post" :comments="comments" :users="users" :key="post.id"/>
        <template v-if="userId">
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

        <Comment v-for="comment in Object.values(this.comments).filter(comment => comment.postId === post.id).sort((a, b) => b.id - a.id)" :users="users" :comment="comment" :key="comment.id"/>
    </div>
</template>

<script>
    import Post from "./Post.vue";
    import Comment from "./Comment.vue";

    export default {
        name: "PostPage",
        data: function () {
            return {
                text: "",
                error: ""
            }
        },
        components: {
            Post, Comment
        },
        props: ["post", "comments", "users", "userId"],
        methods: {
            onWriteComment: function () {
                this.error = "";
                this.$root.$emit("onWriteComment", this.text);
                if (this.error === "") {
                    this.text = "";
                }

            }
        },
        beforeCreate() {
            this.$root.$on("onWriteCommentValidationError", error => this.error = error);
        }
    }
</script>

<style scoped>

</style>
