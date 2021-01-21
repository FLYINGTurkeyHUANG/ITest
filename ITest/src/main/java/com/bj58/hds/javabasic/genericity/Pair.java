package com.bj58.hds.javabasic.genericity;

/**
 * java所实现的泛型可以称之为伪泛型，实际上并没有生成一个新的类型，所有的同一泛型类都将被擦除为其上级类型，此处则会擦除为Pair<Compareble>,未指明上级类型的则会擦除为Object。
 * c++中的泛型是真泛型，类型参数不同就会生成不同的类，这样容易出现类型膨胀的问题。
 * */
public class Pair<T extends Comparable> {
    T first;
    T second;
    public Pair(){}
    public Pair(T first,T second){
        this.first = first;
        this.second = second;
    }
    public<T> T getLow(){
        return (T) (first.compareTo(second)==-1?first:second);
    }
}
