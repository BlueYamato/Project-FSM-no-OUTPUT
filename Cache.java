/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Itsuka Kotori
 */
public abstract class Cache {
    
    protected int[] requestBit= new int[8];
    protected int[] receiveBit = new int[12];
    
    public Cache(int[] req, int[] rec){
        this.requestBit = req;
        this.receiveBit = rec;
    }
    
    @Override
    public String toString(){
        String hasil ="";
        for(int i = 0; i<this.requestBit.length;i++){
            hasil+=requestBit[i];
        }
        return hasil;
    }
}
