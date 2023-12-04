<template>
    <div class="middle">
        <Sidebar :posts="viewPosts"/>
        <main>
            <Index :posts="posts" :users="users" :comments="comments" v-if="page === 'Index'"/>
            <Enter v-if="page === 'Enter'"/>
            <Register v-if="page === 'Register'"/>
            <WritePost v-if="page === 'WritePost'"/>
            <EditPost v-if="page === 'EditPost'"/>
            <Users :users="users" v-if="page === 'Users'"/>
            <PostPage :users="users" :post="posts[postId]" :comments="comments" :userId="userId" v-if="page === 'PostPage'"/>
        </main>

    </div>
</template>

<script>
import Sidebar from "./sidebar/Sidebar";
import Index from "./page/Index";
import Enter from "./page/Enter";
import WritePost from "./page/WritePost";
import EditPost from "./page/EditPost";
import Register from "./page/Register";
import Users from "./page/Users";
import PostPage from "./page/PostPage";

export default {
    name: "Middle",
    data: function () {
        return {
            page: "Index"
        }
    },
    components: {
        WritePost,
        Enter,
        Register,
        Index,
        Sidebar,
        EditPost,
        Users,
        PostPage
    },
    props: ["posts", "users", "postId", "userId"],
    computed: {
        viewPosts: function () {
            return Object.values(this.posts).sort((a, b) => b.id - a.id).slice(0, 2);
        }
    }, beforeCreate() {
        this.$root.$on("onChangePage", (page) => this.page = page)
        this.$root.$on("onOpenPost", (page, postId) => (this.page = page, this.$root.$data.postId = postId))
    }
}
</script>

<style scoped>

</style>
