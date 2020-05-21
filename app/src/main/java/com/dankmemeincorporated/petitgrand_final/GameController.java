package com.dankmemeincorporated.petitgrand_final;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    private int turn;
    private LC stackA;
    private LC stackB;
    private LC stackMid;
    private LC stackRev;

    public GameController(){
        turn = 1;

        distribute();

    }

    public void distribute(){
        for(int i = 0; i<31;i++){
            stackA.add(getFreeCard());
            stackB.add(getFreeCard());
        }
        stackMid.add(getFreeCard());
    }

    private int cards[]={8,8,8,8,8,8,8};
    public int getFreeCard(){
        boolean done = false;
        while(!done) {
            int nb = ((int) Math.random() % 7);
            if (cards[nb] > 0) {
                cards[nb]--;
                return nb+1;
            }
        }
        return 0;
    }


    public void bet(int bt){
        LC stack = (turn==1)? stackA : stackB;

        if(
                (bt==1&&stack.getHead().getValue()>stackMid.getHead().getValue())||
                (bt==0&&stack.getHead().getValue()==stackMid.getHead().getValue())||
                (bt==-1&&stack.getHead().getValue()<stackMid.getHead().getValue())){
            continuer();
        }else{
            rempiler();
        }
    }

    public void rempiler(){
        if(turn==1) {
            stackRev.getTail().setNext(stackA.getHead());
            stackA.setHead(stackRev.getHead());
            turn=2;
            stackRev.setHead(new Maillon());
            stackRev.setTail(new Maillon());
        }
        if(turn==2) {
            stackRev.getTail().setNext(stackB.getHead());
            stackB.setHead(stackRev.getHead());
            turn=1;
            stackRev.setHead(new Maillon());
            stackRev.setTail(new Maillon());
        }
    }
    public void continuer(){
        if(turn==1) {
            stackRev.getTail().setNext(stackA.getHead());
            stackRev.setTail(stackRev.getTail().getNext());
            if(stackA.getHead().getNext()!=null){
                stackA.setHead(stackA.getHead().getNext());
                stackRev.getTail().setNext(null);
            }else{
                win(1);
            }
        }
        if(turn==2) {
            stackRev.getTail().setNext(stackB.getHead());
            stackRev.setTail(stackRev.getTail().getNext());
            if(stackA.getHead().getNext()!=null) {
                stackB.setHead(stackB.getHead().getNext());
                stackRev.getTail().setNext(null);
            }else{
                win(2);
            }
        }
    }

    public void stop(){
        stackMid.getTail().setNext(stackRev.getHead());
        stackMid.setTail(stackRev.getTail());
        stackRev.setHead(new Maillon(0));
        stackRev.setTail(new Maillon(0));
    }

    public void win(int who){
        System.out.println("Joueur "+who+" a gagné !");
    }

    public int getLeft(){return stackA.getHead().getValue();}
    public int getRight(){return stackB.getHead().getValue();}
    public int getMid(){
        if(stackRev.getHead().getValue()==0){
            return stackMid.getTail().getValue();
        }
        return stackRev.getTail().getValue();
    }

}
//TODO afficher le stackrev.getlast() ou stackmid.getlast si l'autre est vide