package com.radar.flightTrack;

public class Pagination {

    private int count;
    private int total;
    private int limit;
    private int offset;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getLimit(){
        return limit;
    }

    public void setLimit(int limit){
        this.limit=limit;
    }

    public int getOffset(){
        return offset;
    }

    public void setOffset(int offset){
        this.offset=offset;
    }

}
