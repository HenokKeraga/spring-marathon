package com.example.bookservice;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "spring.datasource.url=jdbc:tc:mysql:8.0:///")
class BookServiceApplicationTests {
    @Autowired
    WebTestClient webTestClient;

    @Test
    void contextLoads() {
        var book = new Book(null, "the spring book");

        webTestClient.
                post()
                .uri("/books")
                .bodyValue(book)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Book.class).value(actualValue -> {

                            Assertions.assertThat(actualValue.id()).isNotNull();
                            Assertions.assertThat(actualValue.title()).isEqualTo(book.title());
                        }

                );

    }

}
