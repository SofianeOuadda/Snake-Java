package org.snakeInc.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.snakeInc.server.model.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import org.springframework.test.context.ActiveProfiles;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ScoreIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void get_score_test() {
        ResponseEntity<Score[]> response = testRestTemplate.getForEntity("/api/v1/scores", Score[].class);

        assertEquals(response.getStatusCode(), org.springframework.http.HttpStatusCode.valueOf(200));
        Score[] body = response.getBody();

        assertEquals(2, body.length);
        assertEquals("python", body[0].getSnake());
        assertEquals(125, body[0].getScore());
    }

    @Test
    void createScore_test() {
        String jsonRequest = """
                {
                   "snake": "anaconda",
                   "score": 15
                }
                """;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(jsonRequest, headers);

        ResponseEntity<Score> postResponse = testRestTemplate.postForEntity("/api/v1/scores", request, Score.class);
        assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());
        Score createdScore = postResponse.getBody();
        assert createdScore != null;
        assertNotNull(createdScore.getId());
        assertEquals("anaconda", createdScore.getSnake());
        assertEquals(15, createdScore.getScore());

        ResponseEntity<Score[]> getResponse = testRestTemplate.getForEntity("/api/v1/scores", Score[].class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        Score[] allScores = getResponse.getBody();
        assertNotNull(allScores);
        // Maintenant, on doit avoir 2 enregistrements : celui inséré par data.sql et le nouveau
        assertEquals(2, allScores.length);
    }
}
