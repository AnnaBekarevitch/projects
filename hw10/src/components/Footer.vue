<template>
    <footer>
        <a href="/">Codeforces</a> &copy; 2022 by Mike Mirzayanov
        Number of users {{userQuantity}}, of posts {{postQuantity}}
    </footer>
</template>

<script>
export default {
    name: "Footer",
    props: ["userQuantity", "postQuantity"],
}

</script>



<style scoped>

</style>