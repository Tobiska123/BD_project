package Model;

import java.util.List;

public class Pair<T, R> {
    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public R getSecond() {
        return second;
    }

    public void setSecond(R second) {
        this.second = second;
    }

    private T first;
    private R second;

    public Pair(T first, R second){
        this.first = first;
        this.second = second;
    }


}
