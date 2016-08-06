package rna.gol.resources

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import rna.gol.dto.Cell
import rna.gol.services.GameOfLifeService

@RestController
@RequestMapping("/gol")
@Slf4j //@Slf4j generate a log var
class GameOfLifeController {

    @Autowired
    GameOfLifeService gameOfLifeService

    @RequestMapping(value = "/init", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    def initGame(@RequestBody Cell cell) {
        log.debug("Init a grid with $cell.row rows and $cell.col column")
        gameOfLifeService.initGrid(cell.row, cell.col)
    }

    @RequestMapping(method = RequestMethod.GET)
    def getGrid() {
        return gameOfLifeService.getCells()
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    def nextRound() {
        gameOfLifeService.nextRound()
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    def addCell(@RequestBody Cell cell) {
        gameOfLifeService.addCell(cell.row, cell.col)
    }
}
