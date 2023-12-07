
<template>
    <div class="users datatable">
        <div class="caption">Users</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th>Name</th>
            </tr>
            </thead>
            <UserItem v-for="user in users" :user="user" :key="user.id"/>
        </table>
    </div>
</template>

<script>
    import UserItem from "./UserItem.vue";
    export default {
        name: "Users",
        components: {UserItem},
        props: ["users"]
    }
</script>

<style scoped>
</style>