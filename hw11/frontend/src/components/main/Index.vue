<template>
    <div>
        <Post v-for="post in viewPosts" :post="post" :commentsNumber="comments.filter(comment => comment.post.id === post.id).length" :users="users" :key="post.id"/>
    </div>
</template>

<script>
    import Post from "../page/Post.vue";

    export default {
        name: "Index",
        data: function () {
            return {
                comments: []
            }
        },
        components: {
            Post,
        },
        props: ["posts", "users"],
        computed: {
            viewPosts: function () {
                return Object.values(this.posts).sort((a, b) => b.id - a.id);
            }
        }
    }
</script>

<style scoped>

</style>
