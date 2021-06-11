package com.example.secondprogram;

public class Rate {
    public String str;
    public String val;
    Rate(String str, String val){
        this.str=str;
        this.val=val;
    }
    public void setCname(String Cname){
        this.str=Cname;
    }
    public void setCval(String Cval){
        this.val=Cval;
    }

    public String getCname() {
        return str;
    }

    public String getCval() {
        return val;
    }
}
