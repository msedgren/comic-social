<script type="typescript">
    import type {User} from './user/UserService';
    import {userService} from './user/UserService';
    import {notificationService, NoteType} from './notification/NotificationService';
    import {navigate} from "svelte-navigator";

    let form: HTMLFormElement;
    let input: HTMLInputElement;
    let wasValidated = false;
    $: inputValidationMessage = input && input.validationMessage;

    let username = '';

    function login(): Promise<boolean> {
        wasValidated = true;

        const validPromise: Promise<void> =
           form.checkValidity() ? Promise.resolve() : Promise.reject('Unable to login, please correct errors to proceed');

        return validPromise
            .then(() => userService.login(username))
            .then( _completeLogin, _addLoginFailure);
    }

    function clearInvalidUserValidation(): void {
        input.setCustomValidity('');
        input = input;
    }

    function _completeLogin(user: User): boolean {
        navigate('/story-selector');
        userService.user = user;
        return true;
    }

    function _addLoginFailure(err: any): boolean {
        if(err.status === 404) {
            input.setCustomValidity('User not found');
            input = input;
        }
        notificationService.add({
            notificationType: NoteType.Error,
            message: `Failed to login with username.`,
        });
        return false;
    }

</script>

<div class="card">
    <div class="card-body">
        <form bind:this={form} on:submit|preventDefault={login} class:was-validated={wasValidated}>
            <div class="mb-3">
                <label for="usernameInput" class="form-label">Username</label>
                <input type="text" required class="form-control" id="usernameInput" bind:this={input} bind:value="{username}" on:change={clearInvalidUserValidation}>
                {#if inputValidationMessage}
                    <p class="invalid-feedback">{inputValidationMessage}</p>
                {/if}
            </div>
            <button type="button" class="btn btn-primary" on:click="{login}">Login</button>
        </form>
    </div>
</div>
