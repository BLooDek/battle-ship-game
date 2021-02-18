// Aircraft Carrier is 5 cells, Battleship is
// 4 cells, Submarine is 3 cells, Cruiser is also
// 3 cells, and Destroyer is 2 cells.
public enum Ships {
    Aircraft(5, "Aircraft Carrier"),
    Battleship(4, "Battleship"),
    Submarine(3, "Submarine"),
    Cruiser(3, "Cruiser"),
    Destroyer(2, "Destroyer");

    int enuLen;
    String enuName;

    Ships(int enuLen, String enuName) {
        this.enuLen = enuLen;
        this.enuName = enuName;
    }

}
