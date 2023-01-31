use std::collections::HashMap;
use actix_web::{get, delete, error, web, App, HttpServer, Responder, Result};
use serde::{Deserialize, Serialize};
use std::sync::Mutex;

#[derive(Serialize, Clone)]
struct StoryDto {
    story_id: String,
    user_id: String,
}

#[get("/story/{id}")]
async fn fetch_story_by_id(path: web::Path<String>, data: web::Data<AppStateWithCounter>) -> Result<impl Responder>    {
    let story_id = path.into_inner();
    let stories = data.stories.lock().unwrap();

    stories.get(&story_id)
        .map(|story| story.clone())
        .map(|story| web::Json(story))
        .ok_or_else(|| error::ErrorNotFound(format!("Unable to find story with id {}", story_id)))
}

#[derive(Deserialize)]
struct SearchInfo {
    user_id: String,
}

#[get("/story")]
async fn all_stories(data: web::Data<AppStateWithCounter>) -> Result<impl Responder> {
    let stories = data.stories.lock().unwrap();
    let found: Vec<StoryDto> = stories.values()
        .map(|story| story.clone())
        .collect();

    let response = web::Json(found);

    Ok(response)
}

#[get("/story")]
async fn search_for_stories(search_info: Option<web::Query<SearchInfo>>, data: web::Data<AppStateWithCounter>) -> Result<impl Responder> {
    if search_info.is_some() {
        let foo = search_info.unwrap();
        let stories = data.stories.lock().unwrap();
        let found: Vec<StoryDto> = stories.values()
            .filter(|story| story.user_id.eq(&foo.user_id))
            .map(|story| story.clone())
            .collect();

        let response = web::Json(found);

        Ok(response)
    } else {
        let stories = data.stories.lock().unwrap();
        let found: Vec<StoryDto> = stories.values()
            .map(|story| story.clone())
            .collect();

        let response = web::Json(found);

        Ok(response)
    }
}

#[delete("/story/{id}")]
async fn delete_story_by_id(data: web::Data<AppStateWithCounter>, path: web::Path<String>) -> Result<impl Responder>    {
    let story_id = path.into_inner();
    let mut stories = data.stories.lock().unwrap();
    stories.remove(&story_id)
        .map(|story| web::Json(story))
        .ok_or_else(|| error::ErrorNotFound(format!("Unable to delete story with id {}", story_id)))
}

struct AppStateWithCounter {
    stories: Mutex<HashMap<String, StoryDto>>, // <- Mutex is necessary to mutate safely across threads
}

#[actix_web::main]
async fn main() -> std::io::Result<()> {
    std::env::set_var("RUST_LOG", "debug");

    let stories = HashMap::from([
        ("1".to_string(), StoryDto{ story_id: "1".to_string(), user_id: "foo".to_string()}),
        ("2".to_string(), StoryDto{ story_id: "2".to_string(), user_id: "bar".to_string()})
    ]);
    let app_state: web::Data<AppStateWithCounter> = web::Data::new(AppStateWithCounter {
        stories: Mutex::new(stories)
    });
    HttpServer::new(move || {
        App::new()
            .app_data(app_state.clone())
            .service(fetch_story_by_id)
            .service(search_for_stories)
            .service(all_stories)
    })
        .bind(("127.0.0.1", 8080))?
        .run()
        .await
}
