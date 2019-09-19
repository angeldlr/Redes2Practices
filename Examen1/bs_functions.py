#!/usr/bin/python3
import json
import numpy as np
import sys
sys.setrecursionlimit(100500)
class BombSweeper:
    displayBoardGame = []
    markedBombs = 0
    rows = 0
    cols = 0
    bombs = 0
    boardGame = []
    def __init__(self, rows,cols,bombs,boardGame):
        self.rows = rows
        self.cols = cols
        self.bombs = bombs
        self.boardGame = boardGame
        self.setNeighborBombs()
    def markBomb(self,selRow,selCol):
        self.displayBoardGame[selRow][selCol] = ord('P')
        if self.boardGame[selRow][selCol]==-1:
            self.markedBombs+=1
    def markQuestion(self,selRow,selCol):
        self.displayBoardGame[selRow][selCol] = ord('?')
    def removeMark(self,selRow,selCol):
        if self.displayBoardGame[selRow][selCol] in (ord('P'),ord('?')): 
            self.displayBoardGame[selRow][selCol] = ord('-')
        if self.boardGame[selRow][selCol]==ord('P'):
            self.markedBombs-=1
    def setDisplayBoard(self):
        auxRow = [ord('-') for i in range(0,self.cols)]
        self.displayBoardGame = np.array([auxRow for i in range(0,self.rows)])
        
    def shwBoard(self):
        colHeads = ["Col"+str(i) for i in range(0,self.cols)]
        print("     "+"    ".join(str(x) for x in colHeads))
        i=0
        for j in self.displayBoardGame:
            if i < 10:
                print("Row"+str(i)+"    "+"    ".join(chr(x) if x==ord('-') or x==ord('P') or x==ord('?') else str(x) for x in j ))
            else:
                print("Row"+str(i)+"   "+"    ".join(chr(x) if x==ord('-') or x==ord('P') or x==ord('?') else str(x) for x in j ))
            i+=1
    def unlockBox(self,selRow,selCol):
        if self.displayBoardGame[selRow][selCol] in (ord('P'),ord('?')):
            print("Marked box, type again :v")
            return True
        self.displayBoardGame[selRow][selCol] = self.boardGame[selRow][selCol]
        if self.boardGame[selRow][selCol]==-1:
            return False
        if self.boardGame[selRow][selCol] == 0:
            if selRow > 0:
                if (self.boardGame[selRow - 1][selCol]!=-1) and (self.displayBoardGame[selRow - 1][selCol] != 0):
                    self.unlockBox(selRow - 1,selCol)
                else :
                    self.displayBoardGame[selRow - 1][selCol] = self.boardGame[selRow - 1][selCol]
            if selRow < self.rows - 1:
                if (self.boardGame[selRow + 1][selCol]!=-1) and (self.displayBoardGame[selRow + 1][selCol] != 0):
                    self.unlockBox(selRow + 1,selCol)
                else :
                    self.displayBoardGame[selRow + 1][selCol] = self.boardGame[selRow + 1][selCol]
            if selCol > 0:
                if (self.boardGame[selRow][selCol - 1]!=-1) and (self.displayBoardGame[selRow][selCol - 1] != 0):
                    self.unlockBox(selRow,selCol - 1)
                else :
                    self.displayBoardGame[selRow][selCol - 1] = self.boardGame[selRow][selCol - 1]
            if selCol < self.cols - 1:
                if (self.boardGame[selRow][selCol + 1]!=-1) and (self.displayBoardGame[selRow][selCol + 1] != 0):
                    self.unlockBox(selRow,selCol + 1)
                else :
                    self.displayBoardGame[selRow][selCol + 1] = self.boardGame[selRow][selCol + 1]
        return True

    def setNeighborBombs(self):
        newBoard = self.boardGame
        for i in range(0,self.rows):
            for j in range(0,self.cols):
                    # calcular el numero de vecinos de la celula que se esta vicitando
                    n = 0
                    if i > 0 and j > 0 and self.boardGame[i - 1][j - 1]==-1:
                        n += 1
                    if j > 0 and self.boardGame[i][j - 1]==-1:
                        n += 1
                    if i < self.rows - 1 and j > 0 and self.boardGame[i + 1][j - 1]==-1:
                        n += 1
                    if i > 0 and self.boardGame[i - 1][j]==-1:
                        n += 1
                    if i < self.rows - 1 and self.boardGame[i + 1][j]==-1:
                        n += 1
                    if i > 0 and j < self.cols - 1 and self.boardGame[i - 1][j + 1]==-1:
                        n += 1
                    if j < self.cols - 1 and self.boardGame[i][j + 1]==-1:
                        n += 1
                    if i < self.rows - 1 and j < self.cols - 1 and self.boardGame[i + 1][j + 1]==-1:
                        n += 1
                    if not self.boardGame[i][j]:
                        newBoard[i][j] = n
                    else :
                        newBoard[i][j] = -1
        self.boardGame = newBoard
        self.setDisplayBoard()


#Easy game
#bs = BombSweeper(9,9,10,getBoardFromServer("127.0.0.1",7000,"1"))
#Medium game
#bs = BombSweeper(16,16,40,getBoardFromServer("127.0.0.1",7000,"2"))
#Hard game
#bs = BombSweeper(16,30,99,getBoardFromServer("127.0.0.1",7000,"3"))
#bs.shwBoard()


