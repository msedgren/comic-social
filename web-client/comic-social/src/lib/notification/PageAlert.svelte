<script type="typescript">
    import { onDestroy } from 'svelte';
    import { useMatch } from "svelte-navigator";
    import type {Note} from './NotificationService';
    import {notificationService} from './NotificationService';
    import Alert from './Alert.svelte';


    let notes: ReadonlyArray<Note> = [];

    const useMatchUnsub = useMatch('/**').subscribe(() => {
        notificationService.clear();
    });

    const noteUnsub = notificationService.subscribe(given => {
        notes = given;
    });

    function removeAlert(note: Note): void {
        notificationService.remove(note);
    }

    onDestroy(() => {
        useMatchUnsub();
        noteUnsub();
    });
</script>

<style lang="scss">
    .page-alert-section {
      z-index: 10000;
      max-width: 500px;
    }

</style>

<div class="position-absolute top-0 end-0 page-alert-section mt-3 mx-2">
    {#each notes as note}
        <Alert note="{note}" on:close="{() => removeAlert(note)}"></Alert>
    {/each}
</div>

