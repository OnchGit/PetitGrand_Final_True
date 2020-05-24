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
    private LinkedList<Integer> stackA=new LinkedList<Integer>();
    private LinkedList<Integer> stackB=new LinkedList<Integer>();
    private LinkedList<Integer> stackMid=new LinkedList<Integer>();
    private LinkedList<Integer> stackRev=new LinkedList<Integer>();

    public GameController(){
        turn = 1;
        cheatmode=false;
        winner=0;

        distribute();

        System.out.println(stackA);
        System.out.println(stackB);
        System.out.println(turn);

    }
    public void cheat(){//en mode cheat le joueur peut voir la carte actuelle et pas simplement la dernière qu'il a joué.
        cheatmode=true;
    }

    public void distribute(){
        for(int i = 0; i<30;i++){
            stackA.add(getFreeCard());
            stackB.add(getFreeCard());
        }
        stackMid.add(getFreeCard());
    }

    private static int cards[]={9,9,9,9,9,9,9};
    public int getFreeCard(){
//        System.out.println("Bonjour!");
//        boolean done = false;
        Random rand = new Random();
//        while(!done) {
//            System.out.println("Boucle !");
//
//            int nb = rand.nextInt(6);
//            if (cards[nb] > 0) {
//                cards[nb]--;
//                done=true;//c'est par principe ^^
//                return nb+1;
//            }
//        }
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


    public void bet(int bt){
        LinkedList<Integer> stack;
        int compare=stackMid.getLast().intValue();
//        if(stackRev.isEmpty()){//j'avais compris qu'on comparait à la dernière jouée
//            compare=stackMid.getLast().intValue();
//        }else{
//            compare=stackRev.getLast().intValue();
//        }
        if(turn==1){
            if(
                    (bt==1&&stackA.getFirst().intValue()>compare)||
                            (bt==0&&stackA.getFirst().intValue()==compare)||
                            (bt==-1&&stackA.getFirst().intValue()<compare)){
                continuer();//succes
            }else{
                System.out.println("J1 rempile !");
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
            System.out.println("Rempilage !");
            System.out.println("tour : "+turn);
//            stackRev.getLast().setNext(stackA.getFirst());
//            stackA.setHead(stackRev.getFirst());
//            turn=2;
//            stackRev.setHead(new Maillon());
//            stackRev.setTail(new Maillon());
            stackA.addAll(stackRev);
            stackRev.clear();
            turn=(turn%2)+1;
            System.out.println("maintenant tour : "+turn);
//            switchTurn();
//            turn=2;
        }else /*if(turn==2) */{
//            stackRev.getLast().setNext(stackB.getFirst());
//            stackB.setHead(stackRev.getFirst());
//            turn=1;
//            stackRev.setHead(new Maillon());
//            stackRev.setTail(new Maillon());
            stackB.addAll(stackRev);
            stackRev.clear();
            turn=(turn%2)+1;
//            switchTurn();
        }
    }
    public void continuer(){
        if(turn==1) {
            stackRev.add(stackA.getFirst().intValue());
            stackA.removeFirst();
//            stackRev.getLast().setNext(stackA.getFirst());
//            stackRev.setTail(stackRev.getLast().getNext());
//            if(stackA.getFirst().getNext()!=null){
//                stackA.setHead(stackA.getFirst().getNext());
//                stackRev.getLast().setNext(null);
//            }else{
            if(stackA.isEmpty())
                win(1);
//            }
        }
        if(turn==2) {
//            stackRev.getLast().setNext(stackB.getFirst());
//            stackRev.setTail(stackRev.getLast().getNext());
//            if(stackA.getFirst().getNext()!=null) {
//                stackB.setHead(stackB.getFirst().getNext());
//                stackRev.getLast().setNext(null);
//            }else{
            stackRev.add(stackB.getFirst().intValue());
            stackB.removeFirst();
            if(stackB.isEmpty()){
                win(2);
            }
        }
    }

    public void stop(){
//        System.out.println("stop");
//        stackMid.getLast().setNext(stackRev.getFirst());
//        stackMid.setTail(stackRev.getLast());
//        stackRev.setHead(new Maillon(0));
//        stackRev.setTail(new Maillon(0));
        stackMid.addAll(stackRev);
        stackRev.clear();
        turn=turn%2+1;
//        switchTurn();
    }

    public void win(int who){
        System.out.println("Joueur "+who+" a gagné !");
        winner=who;
    }

    public int getLeft(){
        if(cheatmode) {
            if(stackA.isEmpty()){
                return 0;
            }else {
                return stackA.getFirst().intValue();
            }
        }else{
            if(stackRev.isEmpty()){
                return 0;
            }else{
                return stackRev.getLast().intValue();
            }
        }
    }
    public int getRight(){
        if(cheatmode){
            if(stackB.isEmpty()){
                return 0;
            }else {
                return stackB.getFirst().intValue();
            }
        }else{
            if(stackRev.isEmpty()){
                return 0;
            }else{
                return stackRev.getLast().intValue();
            }
        }

    }
    public int getMid(){
//        if(stackRev.isEmpty()/*.getFirst().intValue()==0*/){
        return stackMid.getLast().intValue();
//        }
//        return stackRev.getLast().intValue();
    }


    public int switchTurn(){
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

    public void restart() {
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
