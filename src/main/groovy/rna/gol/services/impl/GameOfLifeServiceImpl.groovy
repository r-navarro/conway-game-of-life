package rna.gol.services.impl

import groovy.util.logging.Slf4j
import org.springframework.stereotype.Service
import rna.gol.dto.Cell
import rna.gol.services.GameOfLifeService

@Service
@Slf4j
class GameOfLifeServiceImpl implements GameOfLifeService {

    int rowNumber
    int columnNumber

    /**
     * This Set will manage unique coordinates for alive's cell
     */
    Set<String> cells = []

    @Override
    void initGrid(int rows, int cols) {
        rowNumber = rows
        columnNumber = cols
        cells.clear()
    }

    @Override
    void addCell(int row, int col) {
        if (checkRowAndCol(row, col)) {
            cells << "$row/$col".toString()
            log.debug("cell added at $row / $col")
        }
    }

    @Override
    void nextRound() {
        log.debug("next round")
        Set<String> keyToRemove = []
        Set<String> keyToAdd = []

        // Groovy function times allow to loop over an integer
        rowNumber.times { row ->
            columnNumber.times { col ->
                String key = "$row/$col"
                def neighbours = countNeighbours(row, col)
                if (willDie(key, neighbours)) {
                    keyToRemove << key
                } else if (willPop(key, neighbours)) {
                    keyToAdd << key
                }
            }
        }

        // Groovy each closure is looping way
        // it is the default var name in closure
        keyToRemove.each {
            cells.remove(it)
        }

        keyToAdd.each {
            cells << it
        }

        log.debug("round end: ${keyToRemove.size()} removed ${keyToAdd.size()} added")
    }

    /**
     * Check if coordinate are good
     * @param row
     * @param col
     * @return
     */
    private boolean checkRowAndCol(int row, int col) {
        if (row >= 0 && col >= 0) {
            if (row <= rowNumber && col <= columnNumber) {
                return true
            }
        }
        return false
    }

    int countNeighbours(int rowIndex, int colIndex) {
        def neighbours = 0

        //(Integer..Integer) is a groovy range
        (-1..1).each {
            def row = rowIndex + it
            (-1..1).each {
                def col = colIndex + it
                String key = "$row/$col"
                if (row != rowIndex || col != colIndex) {
                    if (cells.contains(key)) {
                        neighbours++
                    }
                }
            }
        }
        log.debug("$rowIndex:$colIndex neighbours: $neighbours")
        return neighbours
    }

    private boolean willDie(String key, int neighbours) {
        if (cells.contains(key)) {
            if (neighbours < 2 || neighbours > 3) {
                return true
            }
        }
        return false
    }

    private boolean willPop(String key, int neighbours) {
        if (!cells.contains(key)) {
            if (neighbours == 3) {
                return true
            }
        }
        return false
    }
}
