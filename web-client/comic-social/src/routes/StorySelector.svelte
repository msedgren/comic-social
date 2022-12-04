<script lang="ts">
    import {emptyUser, userService} from '../lib/user/UserService';
    import { onDestroy } from 'svelte';

    let user = emptyUser();

    const unsub = userService.subscribe(u => user = u);

    onDestroy(unsub);
</script>

<h3 class="my-3">Story Selector</h3>

<div class="row">
    Hello {user.username}, start by creating a new story or selecting an existing story.
</div>
