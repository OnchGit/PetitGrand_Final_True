package com.dankmemeincorporated.petitgrand_final;

public class Maillon {

    private int value;
    private Maillon next;

    public Maillon(int i) {
        this.setValue(i);
        this.setNext(null);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Maillon getNext() {
        return next;
    }

    public void setNext(Maillon next) {
        this.next = next;
    }

    public Maillon(){
        value = 0;
        next= null;
    }



}
