/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Itsuka Kotori
 */
public class Corrector {
    public void correct(Comparator comp){
        int[] dataBitRec = comp.getDataBitRec();
        ArrayList<Integer> syndrome = comp.getErrorIndex();
        for(int i = syndrome.size() - 1; i >= 0; i--){
            int dataIndex = toDecimal(syndrome.get(i));
            if(dataBitRec[12 - dataIndex] == 0){
                comp.setDataBitRec(12-dataIndex, 1);
            }
            else{
                comp.setDataBitRec(12-dataIndex, 0);
            }
        }
        System.out.println(Arrays.toString(comp.getDataBitRec()));
    }
    
    private int toDecimal(int x){
        int result = 0;
        int pow = 0;
        while(x!=0){
            int y = x%10;
             result+= y*(int)Math.pow(2, pow);
             pow++;
             x = x/10;
        }
        return result;
    }
}
