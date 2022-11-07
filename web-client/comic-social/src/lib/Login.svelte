<script type="typescript">
    import {userService} from './UserService';

    let username = '';
    $: foo = username + ' other';

    function login(): boolean {
        return !!userService.login(username);
    }

</script>

<div class="card">
    <div class="card-body">
        <form>
            <div class="mb-3">
                <label for="usernameInput" class="form-label">Username</label>
                <input type="text" class="form-control" id="usernameInput" bind:value="{username}">
            </div>
            <button type="button" class="btn btn-primary" on:click="{login}">Login</button>
        </form>
    </div>
</div>
