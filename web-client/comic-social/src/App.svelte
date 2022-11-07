<script lang="ts">
  import Home from './routes/Home.svelte';
  import { Router, Route, link } from "svelte-routing";
  export let url = "";
</script>

<nav class="navbar navbar-expand-lg bg-light">
  <div class="container-fluid">
    <a class="navbar-brand" href="/" use:link>Comic Socal</a>
  </div>
</nav>
<main class="container px-4">
  <div class="container">
    <Router url="{url}">
      <Route path="/"><Home /></Route>
    </Router>
  </div>
</main>
