package rna.gol.dto

class Cell implements Serializable{

    int row
    int col

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        Cell cell = (Cell) o

        if (col != cell.col) return false
        if (row != cell.row) return false

        return true
    }

    int hashCode() {
        int result
        result = row
        result = 31 * result + col
        return result
    }
}
