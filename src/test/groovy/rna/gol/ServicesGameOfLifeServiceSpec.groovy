package rna.gol

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import rna.gol.config.ApplicationConfig
import rna.gol.services.GameOfLifeService
import spock.lang.Specification
import spock.lang.Stepwise

/**
 * Test class for @GameOfLifeService
 */
// This annotation is use to keep tests execution order.
@Stepwise
@ContextConfiguration(classes = ApplicationConfig)
class ServicesGameOfLifeServiceSpec extends Specification {

    @Autowired
    GameOfLifeService gameOfLifeService

    def "test if neighbour count is right"(){
        setup:"init the grid and put 3 cells"
        gameOfLifeService.initGrid(5, 5)
        gameOfLifeService.addCell(2, 1)
        gameOfLifeService.addCell(2, 2)
        gameOfLifeService.addCell(2, 3)

        when:
        def result = gameOfLifeService.countNeighbours(2, 2)

        then:
        result == 2

        when:
        result = gameOfLifeService.countNeighbours(2, 1)

        then:
        result == 1

        when:
        result = gameOfLifeService.countNeighbours(2, 3)

        then:
        result == 1
    }

    def "Test if neighbour count is right with dead cell"() {
        //thank to @Stepwise previous state are still in memory

        when:"test an empty cell"
        result = gameOfLifeService.countNeighbours(0, 0)

        then:
        result == 0

        when:"test an empty cell near alive"
        result = gameOfLifeService.countNeighbours(1, 2)

        then:
        result == 3
    }

    def "Simple test "() {
        setup:"init the grid and put 3 cells to match a simple case"
        gameOfLifeService.initGrid(5, 5)
        gameOfLifeService.addCell(2, 1)
        gameOfLifeService.addCell(2, 2)
        gameOfLifeService.addCell(2, 3)

        when:
        gameOfLifeService.nextRound()

        then:
        gameOfLifeService.cells.size() == 3
        gameOfLifeService.cells.sort() == ["1/2", "2/2", "3/2"].sort()
    }
}
