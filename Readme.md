# BrainChain
BrainChain is a reactive knowledge-sharing backend inspired by platforms like Quora.
It enables users to ask questions, search content, explore topics through tags, and interact using likes/dislikes — all built with Spring WebFlux, Reactive MongoDB, and Kafka event streaming for scalability.

Designed to be lightweight, real-time, and extensible, BranChain can power Q&A platforms, discussion forums, or knowledge bases that require high performance and reactive APIs.

## 🚀 Features
- 🔎 Questions

    - Create, fetch, and delete questions.

    - Full-text search across titles & content.

    - Pagination: offset-based + cursor-based.

    - Tag-based filtering to group related questions.

    - View count tracking (via Kafka event streaming).

- 🏷 Tags

    - Auto-extraction and normalization of tags.

    - Track tag usage frequency for trending insights.

    - Fetch questions grouped by a specific tag.

- 👍 Likes & Dislikes (Planned/Basic)

    - Like/Dislike on Questions, Answers, or Tags.

    - Track engagement per entity.

    - Future-ready for votes-based ranking system.

- 💬 Answers & Comments (Upcoming)

    - Answer questions with rich content.

    - Comment threads for discussions.

    - Reactive pipelines for real-time updates.

- 📈 Analytics & Trends (Future Roadmap)

    - Trending tags & popular questions.

    - Kafka consumers for event-driven analytics.

    - Elasticsearch for advanced search & aggregation.

- ⚡ Tech Highlights

    - Reactive APIs (Spring WebFlux).

    - Reactive MongoDB for non-blocking persistence.

    - Kafka integration for event-driven architecture.

    - Scalable design ready for microservices expansion.
## Table of Contents
- Project Overview
- Tech Stack
- Features
- Architecture Overview
- Data Model (High-level)
- API Reference
    - Questions
- Pagination
- Setup and Prerequisites
    - Java and Build Tools
    - MongoDB
    - Kafka
    - Elasticsearch (optional)
- Running the Application
- Environment Variables
- Development Tips
- Roadmap

---

## Project Overview
This project provides a scalable, reactive backend for a Q&A application with:
- Non-blocking I/O for high throughput.
- MongoDB for storage.
- Kafka for event streaming (e.g., view events).
- Optional Elasticsearch for search capabilities.

---

## Tech Stack
- Java 17
- Spring Boot (WebFlux)
- Spring Data MongoDB (Reactive)
- Reactor (Flux/Mono)
- Apache Kafka
- Optional: Elasticsearch

---

## Features
- Questions:
    - Create question with title, content, and tags (comma/space-separated tags string).
    - Retrieve a question by ID.
    - List all questions.
    - Delete a question by ID.
    - Search questions by title/content with offset pagination.
    - Cursor-based listing by creation time.
    - Paginated, count-inclusive listing response structure.
    - Filter questions by tag.
- Tags:
    - Store and query questions by tag name.
- Events:
    - Emits view count events to Kafka when a question is fetched by ID.
- Extensible for:
    - Answers
    - Likes
    - Trending tags
    - Elasticsearch indexing

---

## Architecture Overview
- Controller layer: Defines REST endpoints.
- Service layer: Business logic, mapping, and integration with messaging.
- Repository layer: Reactive MongoDB repositories.
- Event Publisher: Kafka producers for domain events (e.g., view count).
- DTO/Adapter: Converters between persistence models and API responses.

---

## Data Model (High-level)
- Question:
    - id: String
    - title: String
    - content: String
    - tags: List<String> (normalized tag names)
    - createdAt: LocalDateTime
    - updatedAt: LocalDateTime

- Tag:
    - tagName: String (unique)
    - usageCount: int

Note: A question can hold a list of tags as strings; tag statistics can be managed separately.

---

## API Reference

Base Path: /api

### Questions

- Create Question
    - Method: POST
    - Path: /api/questions
    - Body: QuestionRequestDTO
        - title: string
        - content: string
        - tag: string (comma/space-separated tags; stored as list internally)
    - Response: QuestionResponseDTO (Mono)
    - Notes: On success, returns the created question.

- Get Question by ID
    - Method: GET
    - Path: /api/questions/{id}
    - Response: QuestionResponseDTO (Mono)
    - Side-effect: Publishes a view-count event to Kafka.

- Get All Questions
    - Method: GET
    - Path: /api/questions
    - Response: Flux<QuestionResponseDTO>

- Delete Question by ID
    - Method: DELETE
    - Path: /api/questions/{id}
    - Response: Mono<Void>

- Search Questions (offset pagination)
    - Method: GET
    - Path: /api/questions/search
    - Query Params:
        - query: string (search term; title or content contains)
        - page: int (default 0)
        - size: int (default 10)
    - Response: Flux<QuestionResponseDTO>

- Cursor-based Query (createdAt-based)
    - Method: GET
    - Path: /api/questions/questions
    - Query Params:
        - Cursor: string (ISO-8601 timestamp; optional)
        - size: int (page size)
    - Response: Flux<QuestionResponseDTO>
    - Notes:
        - If cursor is invalid/missing, returns the top by createdAt ascending.
        - If valid, returns items created after the cursor.

- Paginated Query with Total Count
    - Method: GET
    - Path: /api/questions/pagination
    - Query Params:
        - searchTerm: string
        - page: int
        - size: int
    - Response: Mono<PaginationRespnseDTO<Question>>
        - data: List<Question>
        - total: long
        - page: int
        - size: int

- Get Questions by Tag
    - Method: GET
    - Path: /api/questions/tag/{tag}
    - Query Params:
        - page: int (default 0)
        - size: int (default 10)
    - Response: Flux<QuestionResponseDTO>
    - Notes: Filters questions by tag name.

---

## Pagination
- Offset-based: Uses page and size.
- Cursor-based: Uses an ISO-8601 timestamp Cursor parameter to fetch items created after the cursor time.

---

## Setup and Prerequisites

### 1) Java and Build Tools
- Java 17
- Maven or Gradle (matching the project’s chosen build tool)

Verify:
- java -version
- mvn -v or gradle -v

### 2) MongoDB (Reactive)
You can run MongoDB locally or via Docker.

- Docker:
    - docker run -d --name mongo -p 27017:27017 mongo:6
- Connection string example:
    - mongodb://localhost:27017/quora

Ensure application properties point to your MongoDB instance.

### 3) Apache Kafka
Kafka is used to publish domain events such as question view counts.

- Quick start with Docker Compose:
    - docker compose example (single-broker):
        - Use a compose file with zookeeper and kafka (e.g., confluentinc/cp-kafka)
    - Or run directly:
        - Zookeeper: docker run -d --name zookeeper -p 2181:2181 zookeeper
        - Kafka: docker run -d --name kafka -p 9092:9092 --env KAFKA_ZOOKEEPER_CONNECT=host.docker.internal:2181 --env KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 wurstmeister/kafka

- Create topic (if required by your setup):
    - docker exec -it kafka kafka-topics.sh --create --topic view-count-events --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1

- How it’s used:
    - When a question is fetched by ID, a view-count event is emitted to Kafka with the entity id, type ("question"), and timestamp. A separate consumer (not shown here) can aggregate views or persist metrics.

### 4) Elasticsearch (Optional)
Elasticsearch can be used for advanced search and analytics. The integration can be enabled by indexing Question documents into an ES index.

- Docker:
    - docker run -d --name es -p 9200:9200 -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:8.13.4
- Kibana (optional):
    - docker run -d --name kibana -p 5601:5601 --link es:elasticsearch docker.elastic.co/kibana/kibana:8.13.4

- How it’s used (optional path):
    - Mirror Question creates/updates to an Elasticsearch index (e.g., questions) to power full-text search and aggregations.
    - Keep MongoDB as source of truth; ES as a read-optimized projection.

---

## Running the Application

1) Configure environment variables (see next section) for MongoDB and Kafka.
2) Build:
    - With Maven: mvn clean package
3) Run:
    - With Maven: mvn spring-boot:run
    - Or run the packaged jar: java -jar target/quora-backend.jar

Application should start on the configured server port (commonly 8080).

---

## Environment Variables
Set these in your environment or application properties (application.yml/properties):

- MongoDB:
    - spring.data.mongodb.uri=mongodb://localhost:27017/quora

- Kafka:
    - spring.kafka.bootstrap-servers=localhost:9092
    - app.kafka.topics.view-count=view-count-events

- Server (optional):
    - server.port=8080

- Elasticsearch (optional):
    - spring.elasticsearch.uris=http://localhost:9200

Note: Property names may vary based on your configuration approach.

---

## Development Tips
- Reactive patterns:
    - Prefer non-blocking I/O. Do not call blocking operations on reactive threads.
- Validation:
    - Validate input on create/update requests (title/content/tag).
- Tag management:
    - Normalize tag strings to lowercase and trim spaces before storing.
- Observability:
    - Add logs and metrics around Kafka publishing and search operations.
- Testing:
    - Use StepVerifier for reactive tests.
    - Consider Testcontainers for MongoDB/Kafka during integration tests.

---

## Roadmap
- Answers API
- Likes/Votes API
- Tag analytics: trending, related tags
- Full Elasticsearch-based search capability
- Caching layer for hot endpoints
- Rate limiting on critical routes

---
## 🤝 Contributing

- Fork the repo

- Create a feature branch (git checkout -b feature/my-feature)

- Commit changes (git commit -m 'feat: add something')

- Push to branch (git push origin feature/my-feature)

- Open a PR 🎉

## 📜 License

- This project is licensed under the MIT License.