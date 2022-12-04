# comic-social
A social web application for creating comics from everyday life.

Mostly though, it serves as a project to experiment with different technologies.

### goals

1. Produce a working application. The details are a bit light as primary objective is to play.
2. Play with a few different technologies...


### technologies

A list of the already created components and planned components is given below.

#### User API

This was created using reactive components from Spring. Following from this, the REST API was written using WebFlux (Project Reactor with Mono/Flux) and
the data layer using R2DBC. Native compilation was added via Graal.

#### User Interface

Svelte was used as the UI framework.

#### Other APIs

Rust will be used for the next API. My guess is that this would be around CRUD operations of images or stories.

#### Notes

1. No work has been completed to hookup up authorization and authentication. Hooking up with an OAuth provider... is a bit outside of the scope of this project.


### Thoughts on Project

[December 4, 2022](project-notes/04-12-2022.md)
