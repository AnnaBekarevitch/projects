<template>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th>Name</th>
            </tr>
            </thead>
            <User v-for="user in users" :user="user" :key="user.id"/>

        </table>
    </div>
</template>

<script>
    import User from "./User.vue";

    export default {
        name: "Users",
        components: {
            User
        },
        props: ["users"]
    }
</script>

<style scoped>

</style>
