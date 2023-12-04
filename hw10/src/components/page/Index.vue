<template>
    <div>
        <Post v-for="post in viewPosts" :post="post" :comments="comments" :users="users" :key="post.id"/>
    </div>
</template>

<script>
import Post from "./Post.vue";

export default {
    name: "Index",
    components: {
        Post
    },
    props: ["posts", "comments", "users"],
    computed: {
        viewPosts: function () {
            return Object.values(this.posts).sort((a, b) => b.id - a.id);
        }
    }
}
</script>

<style scoped>

</style>
