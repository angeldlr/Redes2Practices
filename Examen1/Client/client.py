#!/usr/bin/python3           # This is client.py file
from  bs_functions import BombSweeper
import socket
import os
import json
import numpy as np


def getBoardFromServer(h,p,level):
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM) 
    host = h                         
    port = p
    s.connect((host, port))
    data = level+"\n"                               
    s.sendall(data.encode('utf-8'))
    msg = s.recv(4096)
    s.close()
    board = np.array(json.loads(msg.decode('utf-8')))
    return board
def startGame(bs):
    playing = True
    while playing:
        os.system("clear")
        print(bs.boardGame)
        bs.shwBoard()
        print("Type one of the following options:")
        play = int(input("(Type 1 to open a box),(Type 2 to mark a bomb),(Type 3 put a question mark),(Type 4 to remove a mark):"))
        selRow,selCol = input("Write the number of your selected box[row colum]:").split()
    
        if play == 1:
            if not bs.unlockBox(int(selRow),int(selCol)):
                print("Oh looks bad, you die :'v")
                resetGame()
        elif play==2:
            bs.markBomb(int(selRow),int(selCol))
        elif play ==3:
            bs.markQuestion(int(selRow),int(selCol))
        elif play ==4: 
            bs.removeMark(int(selRow),int(selCol))
        else:
            print("Please, type a valid option(1,2,3,4) try again")
            
        if bs.markedBombs == bs.bombs:
            print("Awesome!!! you have been discovered all the bombs :D")
            resetGame()
        
            
def resetGame():
    res = int(input("Do you want to play again?[1=yes,0=no]:"))
    if res==1:
        menuGame()
    else:
        print("Thanks for playing, see u :)")
        exit(0)
def menuGame():
    opt = 0
    while opt != 4:
        os.system("clear")
        print("Welcome to bomb sweeper")
        print("Choose your favourite difficulty leverl:")
        print("1. Easy.")
        print("2. Medium.")
        print("3. Hard.")
        print("4. Finish game.")
        opt = int(input("Please enter the number of your selection:"))
        if opt == 1:
            bs = BombSweeper(9,9,10,getBoardFromServer("127.0.0.1",7000,"1"))
        elif opt == 2:
            bs = BombSweeper(16,16,40,getBoardFromServer("127.0.0.1",7000,"2"))
        elif opt == 3:
            bs = BombSweeper(16,30,99,getBoardFromServer("127.0.0.1",7000,"3"))
        else:
            break;
        startGame(bs)        
def main():
    menuGame()

main()
