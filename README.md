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
the data layer using R2DBC.

Once SpringBoot 3 is released, an attempt will be made to run the application using native compilation via GRAAL.

#### User Interface

Work will be started on this next to leverage the APIs. Svelte will be the primary framework that is used.

#### Other APIs

Rust will be used for the next API. My guess is that this would be around CRUD operations of images.

#### Notes

1. No work has been completed to hookup up authorization and authentication.


