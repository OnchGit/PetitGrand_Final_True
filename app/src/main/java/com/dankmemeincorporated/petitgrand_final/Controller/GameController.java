package com.dankmemeincorporated.petitgrand_final.Controller;

//import com.dankmemeincorporated.petitgrand_final.LC<Integer>;
//import com.dankmemeincorporated.petitgrand_final.Maillon;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class GameController {
    private int turn;
    private boolean cheatmode;
    private int winner;
    private LinkedList<Integer> stackA=new LinkedList<Integer>();//pile du J1
    private LinkedList<Integer> stackB=new LinkedList<Integer>();//pile du J2
    private LinkedList<Integer> stackMid=new LinkedList<Integer>();//pile centrale
    private LinkedList<Integer> stackRev=new LinkedList<Integer>();//pile temporaire des paris réussis du joueur actuel

    private int stock1;//pour retenir la dernière carte lors d'un pari raté
    private int stock2;

    public GameController(){
        turn = 1;
        cheatmode=false;
        winner=0;
        stock1=stock2=0;

        distribute();

        System.out.println(stackA);
        System.out.println(stackB);
        System.out.println(turn);

    }
    public void cheat(){//en mode cheat le joueur peut voir la carte actuelle et pas simplement la dernière qu'il a joué/sur laquelle il a parié.
        cheatmode=true;
    }

    public void distribute(){//distribue les cartes
        for(int i = 0; i<30;i++){
            stackA.add(getFreeCard());
            stackB.add(getFreeCard());
        }
        stackMid.add(getFreeCard());
    }

    private static int cards[]={9,9,9,9,9,9,9};
    public int getFreeCard(){//pioche une carte en s'assurant qu'il en reste en stock

        Random rand = new Random();
        int nb=0;
        do{
            for (int c :cards){
                System.out.println(c);
            }
            System.out.println("stop");

            nb = rand.nextInt(7);
        }while(cards[nb] == 0);
        cards[nb]--;
        return nb+1;
    }


    public void bet(int bt){//effectue les tests liés au pari
        int compare=stackMid.getLast().intValue();
        if(turn==1){
            if(
                    (bt==1&&stackA.getFirst().intValue()>compare)||
                            (bt==0&&stackA.getFirst().intValue()==compare)||
                            (bt==-1&&stackA.getFirst().intValue()<compare)){
                continuer();//succes
            }else{
                rempiler();//echec
            }
        }else{
            if(
                    (bt==1&&stackB.getFirst().intValue()>compare)||
                            (bt==0&&stackB.getFirst().intValue()==compare)||
                            (bt==-1&&stackB.getFirst().intValue()<compare)){
                continuer();//succes
            }else{
                rempiler();//echec
            }
        }


    }

    public void rempiler(){//le joueur n'a pas réussi son pari il récupère les cartes jouées
        if(turn==1) {
            stackA.addAll(stackRev);
            stackRev.clear();
            turn=(turn%2)+1;
            stock1=stackA.getFirst().intValue();//stoque le dernier élément afin de l'afficher au joueur puis le renvoit à la fin de la pile
            stackA.add(stackA.getFirst().intValue());
            stackA.removeFirst();;
        }else /*if(turn==2) */{
            stackB.addAll(stackRev);
            stackRev.clear();
            turn=(turn%2)+1;
            stock2=stackB.getFirst().intValue();//stoque le dernier élément afin de l'afficher au joueur puis le renvoit à la fin de la pile
            stackB.add(stackB.getFirst().intValue());
            stackB.removeFirst();
        }
    }


    public void continuer(){//pari réussi on stock la carte dans la pile temporaire et on passe à la suivante
        if(turn==1) {
            stackRev.add(stackA.getFirst().intValue());
            stackA.removeFirst();
            if(stackA.isEmpty())
                win(1);
        }
        if(turn==2) {
            stackRev.add(stackB.getFirst().intValue());
            stackB.removeFirst();
            if(stackB.isEmpty()){
                win(2);
            }
        }
    }

    public void stop(){//le joueur passe son tour et met ses cartes (pari réussi) sur la pile centrale
        stackMid.addAll(stackRev);
        stackRev.clear();
        turn=turn%2+1;
    }

    public void win(int who){//quelqu'un a gagné: bravo quelqu'un!
        System.out.println("Joueur "+who+" a gagné !");
        winner=who;
    }

    public int getLeft() {//joueur 1 ... que j'ai mis à droite
        if (cheatmode) {//en cheatmode ono voit toujours la carte suivante
            if (stackA.isEmpty()) {//sauf si la pile est vide
                return 0;
            } else {
                return stackA.getFirst().intValue();
            }
        } else {//en mode normal
            if (turn == 2) {//si ce n'est pas son tour
                if (stock1 != 0) {//mais qu'il vient de perdre un pari
                    int temp = stock1;
                    stock1 = 0;
                    return temp;//on lui affiche la carte sur laquelle son pari a échoué
                }else{
                    return 0;
                }
            } else {//si c'est son tour
                if (stackRev.isEmpty()) {
                    return 0;
                } else {//on lui affiche la dèrnière carte sur laquelle il a réussi son pari
                    return stackRev.getLast().intValue();
                }
            }
        }
    }
    public int getRight(){//joueur 2 ... qui lui est à gauche; moi aussi ça me fait mal au crâne
        if(cheatmode){
            if(stackB.isEmpty()){
                return 0;
            }else {
                return stackB.getFirst().intValue();
            }
        }else{
            if(turn==1){
                if(stock2!=0){
                    int temp=stock2;
                    stock2=0;
                    return temp;
                } else {
                    return 0;
                }
            } else {
                if (stackRev.isEmpty()) {
                    return 0;
                } else {
                    return stackRev.getLast().intValue();
                }
            }

        }

    }
    public int getMid(){//la carte au sommet de la pile centrale, enfin celle visible par les joueurs
        return stackMid.getLast().intValue();
    }


    public int switchTurn(){//on ne s'en sert pas
        if(turn==1){
            turn=2;
        }else{
            turn=1;
        }
        return turn;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public boolean isCheatmode() {
        return cheatmode;
    }

    public void setCheatmode(boolean cheatmode) {
        this.cheatmode = cheatmode;
    }

    public int getWinner() {
        return winner;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }

    public void restart() {//redistribue les cartes et donne la main au joueur perdant
        switchTurn();
        winner=0;
        stackB.clear();
        stackA.clear();
        stackRev.clear();
        stackMid.clear();
        cards[0]=9;
        cards[1]=9;
        cards[2]=9;
        cards[3]=9;
        cards[4]=9;
        cards[5]=9;
        cards[6]=9;
        distribute();
    }
}
