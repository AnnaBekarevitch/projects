<template>
    <section>
        <div class="header">
            <a href="#" @click.prevent="onOpenPost('PostPage', post.id)"> {{ post.title }} </a>

        </div>
        <div class="body">
            {{ post.text }}
        </div>
        <div class="footer">
            <a href="#" @click.prevent="onOpenPost('PostPage', post.id)"> View all</a>
        </div>
    </section>
</template>

<script>
export default {
    name: "SidebarPost",
    props: ["post"],
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
