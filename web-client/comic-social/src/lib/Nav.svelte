<script lang="ts">
    import {link} from "svelte-navigator";
    import Authenticated from "./user/Authenticated.svelte";
    import {emptyUser, userService} from './user/UserService';

    function logout(): boolean {
        userService.user = emptyUser();
        return false;
    }
</script>

<nav class="navbar navbar-expand-lg bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="/" use:link>Comic Social</a>
    </div>
    <Authenticated>
        <span slot="authenticated">
            <div class="collapse navbar-collapse" id="navbarNav">
              <ul class="navbar-nav">
                <li class="nav-item">
                  <button class="btn btn-link nav-link" aria-current="page" on:click={logout} use:link>Logout</button>
                </li>
              </ul>
            </div>
        </span>
    </Authenticated>
</nav>
