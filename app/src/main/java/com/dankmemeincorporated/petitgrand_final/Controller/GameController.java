package com.dankmemeincorporated.petitgrand_final.Controller;

//import com.dankmemeincorporated.petitgrand_final.LC<Integer>;
//import com.dankmemeincorporated.petitgrand_final.Maillon;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GameController {
    private int turn;
    private LinkedList<Integer> stackA;
    private LinkedList<Integer> stackB;
    private LinkedList<Integer> stackMid;
    private LinkedList<Integer> stackRev;

    public GameController(){
        turn = 1;

        distribute();

    }

    public void distribute(){
        for(int i = 0; i<30;i++){
            stackA.add(getFreeCard());
            stackB.add(getFreeCard());
        }
        stackMid.add(getFreeCard());
    }

    private int cards[]={9,9,9,9,9,9,9};
    public int getFreeCard(){
        boolean done = false;
        while(!done) {
            int nb = ((int) Math.random() % 7);
            if (cards[nb] > 0) {
                cards[nb]--;
                done=true;//c'est par principe ^^
                return nb+1;
            }
        }
        return 0;
    }


    public void bet(int bt){
        LinkedList<Integer> stack = (turn==1)? stackA : stackB;

        if(
                (bt==1&&stack.getFirst().intValue()>stackMid.getFirst().intValue())||
                (bt==0&&stack.getFirst().intValue()==stackMid.getFirst().intValue())||
                (bt==-1&&stack.getFirst().intValue()<stackMid.getFirst().intValue())){
            continuer();//succes
        }else{
            rempiler();//echec
        }
    }

    public void rempiler(){
        if(turn==1) {
//            stackRev.getLast().setNext(stackA.getFirst());
//            stackA.setHead(stackRev.getFirst());
//            turn=2;
//            stackRev.setHead(new Maillon());
//            stackRev.setTail(new Maillon());
            stackA.addAll(stackRev);
            stackRev.clear();
        }
        if(turn==2) {
//            stackRev.getLast().setNext(stackB.getFirst());
//            stackB.setHead(stackRev.getFirst());
//            turn=1;
//            stackRev.setHead(new Maillon());
//            stackRev.setTail(new Maillon());
            stackB.addAll(stackRev);
            stackRev.clear();
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
//        stackMid.getLast().setNext(stackRev.getFirst());
//        stackMid.setTail(stackRev.getLast());
//        stackRev.setHead(new Maillon(0));
//        stackRev.setTail(new Maillon(0));
        stackMid.addAll(stackRev);
        stackRev.clear();
    }

    public void win(int who){
        System.out.println("Joueur "+who+" a gagné !");
    }

    public int getLeft(){return stackA.getFirst().intValue();}
    public int getRight(){return stackB.getFirst().intValue();}
    public int getMid(){
        if(stackRev.isEmpty()/*.getFirst().intValue()==0*/){
            return stackMid.getLast().intValue();
        }
        return stackRev.getLast().intValue();
    }

    public int switchTurn(){
        if(turn==1){
            turn=2;
        }else{
            turn=1;
        }
        return turn;
    }

}
//TODO afficher le stackrev.getlast() ou stackmid.getlast si l'autre est vide