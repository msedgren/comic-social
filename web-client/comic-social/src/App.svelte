<script lang="ts">
  import Home from './routes/Home.svelte';
  import PageAlert from './lib/notification/PageAlert.svelte';
  import StorySelector from './routes/StorySelector.svelte';
  import {Router, Route} from "svelte-navigator";
  import Authenticated from "./lib/user/Authenticated.svelte";
  import Nav from "./lib/Nav.svelte";


</script>

<Nav></Nav>
<main class="container px-4">
  <div class="container">
    <Router>
      <PageAlert></PageAlert>
    </Router>
    <Authenticated>
      <span slot="authenticated">
        <Router>
          <Route path="/"><Home /></Route>
          <Route path="/story-selector"><StorySelector /></Route>
        </Router>
      </span>
      <span slot="unauthenticated">
        <Home />
      </span>
    </Authenticated>
  </div>
</main>
