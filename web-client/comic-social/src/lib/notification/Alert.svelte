<script type="typescript">
    import type {Note} from './NotificationService';
    import {NoteType} from './NotificationService';
    import {createEventDispatcher} from 'svelte';

    export let note: Note;
    $: typeClass = note?.notificationType === NoteType.Success ? 'alert-primary' : 'alert-danger';

    const dispatch = createEventDispatcher<{close: Note}>();

    function closeAlert() {
        dispatch('close', note);
    }
</script>

<div class="alert {typeClass} alert-dismissible fade show" role="alert">
    <button type="button" class="btn btn-close btn-sm" data-bs-dismiss="alert" aria-label="Close" on:click="{closeAlert}"></button>
    {note.message}
</div>
