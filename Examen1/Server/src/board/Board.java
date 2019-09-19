package board;

import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import org.json.*;

public class Board {
	int level;
	int rows;
	int cols;
	int bombs;
	int[][] board;
	String jsonBoard;
	private static Random random = new Random(new Date().getTime());

	public Board(int lvl) {
		this.level = lvl;
		switch (level) {
		case 1:
			bombs = 10;
			rows = 9;
			cols = 9;
			break;
		case 2:
			bombs = 40;
			rows = 16;
			cols = 16;
			break;
		case 3:
			bombs = 99;
			rows = 16;
			cols = 30;
			break;
		}
		board = new int[rows][cols];
		fullFillBoard();
		putBombsInBoard();
	}
	public void fullFillBoard() {
		int i =0,j=0;
		for(i=0;i>this.rows;i++)
			for (j = 0; j < this.cols; j++)
				board[i][j]=0;
	}
	public void putBombsInBoard() {
		int m = bombs;
        int xx, yy;
        while (m != 0) {
            do {
                xx = (int) (random.nextDouble() * this.rows);
                yy = (int) (random.nextDouble() * this.cols);
            } while (board[xx][yy] == -1);
            board[xx][yy] = -1;
            m--;
        }
	}
	public String getJSONBoard() {
		try {
			JSONArray bJSON = new JSONArray(Arrays.deepToString(board));
			return bJSON.toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
