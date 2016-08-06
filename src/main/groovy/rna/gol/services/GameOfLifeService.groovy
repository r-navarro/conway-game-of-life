package rna.gol.services

interface GameOfLifeService {

    /**
     * Init a new grid
     * @param rows the number of rows
     * @param cols the number of columns
     */
    void initGrid(int rows, int cols)

    /**
     * Add a cell to the grid.
     * @param row
     * @param col
     */
    void addCell(int row, int col)

    /**
     * compute the new round
     */
    void nextRound()

    /**
     * Get the coordinates of the alive cells.
     * @return
     */
    Set<String> getCells()

    /**
     * Get the number of neighbour of a cell.
     * @param rowIndex
     * @param colIndex
     * @return
     */
    int countNeighbours(int row, int col)
}