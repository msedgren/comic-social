<script lang="ts">
    import {userService, emptyUser} from './UserService';
    import { onDestroy } from 'svelte';

    let user = emptyUser();

    const unsub = userService.subscribe(u => user = u);

    onDestroy(unsub);

</script>

{#if user.username}
    <slot name="authenticated">
    </slot>
{:else}
    <slot name="unauthenticated">
    </slot>
{/if}
