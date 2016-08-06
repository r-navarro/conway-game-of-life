package rna.gol

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ContextConfiguration
import rna.gol.config.ApplicationConfig
import rna.gol.dto.Cell
import spock.lang.Specification
import spock.lang.Stepwise

@ContextConfiguration(classes = ApplicationConfig)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Stepwise
class GameOfLifeControllerSpec extends Specification {

    @Autowired
    TestRestTemplate restTemplate;

    def "Init game and add a cell"() {
        setup:
        def initSize = new Cell(row: 5, col: 5)
        def cell = new Cell(row: 4, col: 4)

        when:
        restTemplate.postForObject("/gol/init", new HttpEntity(initSize), String)
        restTemplate.postForObject("/gol", new HttpEntity(cell), String)

        and:
        ResponseEntity<Set<String>> response = restTemplate.getForEntity("/gol", Set)

        then:
        response.statusCode == HttpStatus.OK
        response.body.size() == 1
        response.body[0] == "4/4"
    }

    def "Run a round and check"() {
        when:
        restTemplate.put("/gol", null)

        and:
        ResponseEntity<Set<String>> response = restTemplate.getForEntity("/gol", Set)

        then:
        response.statusCode == HttpStatus.OK
        response.body.size() == 0
    }
}
