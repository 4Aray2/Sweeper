package Sweeper;

class Bomb {
    private Matrix bombMap;
    private int totalBombs;

    Bomb(int totalBombs) {
        this.totalBombs = totalBombs;
        fixBombCount();
    }

    void start() {
        bombMap = new Matrix(Box.ZERO);
        for (int i = 0; i < totalBombs; i++)
            placeBomb();
    }

    Box get(Coord coord) {
        return bombMap.get(coord);
    }

    private void fixBombCount() {
        int maxBobms = (Ranges.getSize().x - 1) *
                       (Ranges.getSize().y - 1);
        if (totalBombs > maxBobms)
            totalBombs = maxBobms;
    }

    private void placeBomb() {
        while (true) {
            Coord coord = Ranges.getRandomCoord();
            if (Box.BOMB == bombMap.get(coord))
                continue;
            bombMap.set(coord, Box.BOMB);
            incrementNumbersAroundBomb(coord);
            break;
        }
    }

    private void incrementNumbersAroundBomb(Coord coord) {
        for (Coord around : Ranges.getCoordsAround(coord))
            if (Box.BOMB != bombMap.get(around)) {
                bombMap.set(around, bombMap.get(around).getNextNumgerBox());
            }
    }

    int getTotalBombs() {
        return totalBombs;
    }
}