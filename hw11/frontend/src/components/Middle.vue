<template>
    <div class="middle">
        <Sidebar class="middlePost" :posts="viewPosts"/>

        <main>
            <Index class="middlePost" :users="users" :posts="posts" v-if="page === 'Index'"/>
                <PostPage  :post="post" :user="user" v-if="page === 'PostPage'"/>

            <Users :users="users" v-if="page === 'Users'"/>
            <Enter v-if="page === 'Enter'"/>
            <Register v-if="page === 'Register'"/>
            <WritePost :user="user" v-if="page === 'WritePost'"/>

        </main>
    </div>
</template>

<script>
import Sidebar from "./sidebar/Sidebar";
import Index from "./main/Index";
import Enter from "./main/Enter";
import Users from "./page/Users";
import Register from "./main/Register";
import WritePost from "./page/WritePost";
import PostPage from "./page/PostPage";

export default {
    name: "Middle",
    data: function () {
        return {
            page: "Index",
            post: null
        }
    },
    components: {
        Register,
        Enter,
        Index,
        Sidebar,
        WritePost,
        Users,
        PostPage
    },
    props: ["posts", "users", "user"],
    computed: {
        viewPosts: function () {
            return Object.values(this.posts).sort((a, b) => b.id - a.id).slice(0, 2);
        }
    }, beforeCreate() {
        this.$root.$on("onChangePage", (page) => this.page = page)
        this.$root.$on("onOpenPost", (page, postId) =>

            (this.page = page, this.post = Object.values(this.posts).filter(post => post.id === postId).at(0)))


    }
}
</script>

<style scoped>

</style>
