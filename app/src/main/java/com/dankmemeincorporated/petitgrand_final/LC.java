package com.dankmemeincorporated.petitgrand_final;

public class LC {
    private Maillon head;

    public Maillon getTail() {
        return tail;
    }

    public void setTail(Maillon tail) {
        this.tail = tail;
    }

    private Maillon tail;

    public Maillon getHead() {
        return head;
    }

    public void setHead(Maillon head) {
        this.head = head;
    }

    public LC add(int i){
        Maillon h = this.head;
        if(h.getValue()==0){
            h.setValue(i);
        }else{
            tail.setNext(new Maillon(i));
            this.setTail(getTail().getNext());
//            while(h.getNext()!=null){
//                h=h.getNext();
//            }
//            Maillon n = new Maillon();
//            n.setValue(i);
//            h.setNext(n);
        }
        tail=h;
        return this;
    }

//    public Maillon getLast(){
//        Maillon h = this.head;
//        while(h.getNext()!=null){
//            h=h.getNext();
//        }
//        return h;
//    }

}
