# Barkr API - A Simple REST API

Implementation of a simple REST (technically "REST-like") API, using different languages/frameworks. The primary goals for this exercise are:

* stand up a simple REST API with a small handful of operations/endpoints
* create accompanying test cases to ensure the API handles requests as expected

The APIs will be standalone, that is, not wired up to any particular database. Any data will just be stored locally. Future exercises will look at e.g. persistence, caching, etc.

## The API

Consider an API we will call Barkr, which is a canine social networks. Users (Barkrs) can post Barks (posts), which contain an identifier, timestamp, title, and some text. Users can retrieve Barks using the identifier.

`POST /barks` - post a new Bark

Input: a JSON snippet containing

```json
{
	"title": "some title string",
	"content": "some content string"
}
```

Output: a JSON snippet containing

```json
{
	"id": "some-unique-identifier-string",
	"creation_ts": "some timestamp representing when the Bark was created",
	"title": "some title string",
	"content": "some content string"
}
```

`GET /barks/{bark_id}` - fetch a previously-stored Bark. Return HTTP 404 not found if a Bark doesn't exist with that ID

Output: a JSON snippet containing

```json
{
	"id": "some-unique-identifier-string",
	"timestamp": "some timestamp representing when the Bark was created",
	"title": "some title string",
	"content": "some content string"
}
```

## The Implementations

### Java/Spring Boot

Spring Boot is a very opinionated framework for creating Java applications. It is especially suited for standing up Java API services very quickly (for some definition of "quickly").

The Java/Spring Boot implementation is located in the `java/` subdirectory.

### Python/Flask

Flask is a very lightweight web framework for building Python microservices.

The Python/Flask implementation is located in the `python/` subdirectory.
