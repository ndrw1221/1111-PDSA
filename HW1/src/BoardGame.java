import java.util.Arrays;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


class BoardGame {
    private final int[] board;
    private final int height;
    private final int width;
    private final WeightedQuickUnionUF cc;
    private final int[] emptyEdges;


    public BoardGame(int h, int w) { // create a board of size h*w
        board = new int[h * w];
        height = h;
        width = w;

        cc = new WeightedQuickUnionUF(h * w);
        emptyEdges = new int[h * w];
    }


    public void putStone(int[] x, int[] y, char stoneType) { // put stones of the specified type on the board according to the coordinate
        int stone = stoneType == 'O' ? 1 : 2; // 'O' -> 1, 'X' -> 2

        int ee, sameStoneType, eeSum;

        for (int i = 0; i < x.length; i++) {
            int index = x[i] * width + y[i];
            board[index] = stone;

            ee = 0; // number of empty edges
            sameStoneType = 0; // number of stone with same stone type
            eeSum = 0;

            if (index - width < 0 || board[index - width] == 0) {
                ee++;
            } else {
                if (board[index - width] == stone) {
                    eeSum += emptyEdges[cc.find(index - width)];
                    cc.union(index, index - width);
                    sameStoneType++;
                } else {
                    emptyEdges[cc.find(index - width)]--;
                }
            }

            if (index + width >= height * width || board[index + width] == 0) {
                ee++;
            } else {
                if (board[index + width] == stone) {
                    if (cc.find(index) != cc.find(index + width)) {
                        eeSum += emptyEdges[cc.find(index + width)];
                        cc.union(index, index + width);
                    }
                    sameStoneType++;
                } else {
                    emptyEdges[cc.find(index + width)]--;
                }
            }

            if (index % width == 0 || board[index - 1] == 0) {
                ee++;
            } else {
                if (board[index - 1] == stone) {
                    if (cc.find(index) != cc.find(index - 1)) {
                        eeSum += emptyEdges[cc.find(index - 1)];
                        cc.union(index, index - 1);
                    }
                    sameStoneType++;
                } else {
                    emptyEdges[cc.find(index - 1)]--;
                }
            }

            if (index % width == width - 1 || board[index + 1] == 0) {
                ee++;
            } else {
                if (board[index + 1] == stone) {
                    if (cc.find(index) != cc.find(index + 1)) {
                        eeSum += emptyEdges[cc.find(index + 1)];
                        cc.union(index, index + 1);
                    }
                    sameStoneType++;
                } else {
                    emptyEdges[cc.find(index + 1)]--;
                }
            }

            emptyEdges[cc.find(index)] = eeSum - sameStoneType + ee;

        }
    }


    public boolean surrounded(int x, int y) { // Answer if the stone and its connected stones are surrounded by another type of stones
        return emptyEdges[cc.find(x * width + y)] == 0;
    }


    public char getStoneType(int x, int y) { // Get the type of the stone at (x,y)
        return board[x * width + y] == 1 ? 'O' : 'X';
    }


    public int countConnectedRegions() { // Get the number of connected regions in the board, including both types of the stones
        int noStoneCount = 0;
        for (int stone : board) {
            if (stone == 0)
                noStoneCount++;
        }
        return cc.count() - noStoneCount;
    }

    public static void main (String[] arg) {

    }
}