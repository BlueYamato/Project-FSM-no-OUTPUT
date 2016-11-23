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
public class Tester {
    public static void main(String[] args) {
        int[] req = {1,1,1,0,0,1,1,0};
        int[] rec = {0,0,1,1,1,1,0,1,0,1,1,0};
        Comparator comparator = new Comparator(req, rec);
        Corrector cor = new Corrector();
        comparator.compareTo();
        comparator.createDataBitReq();
        comparator.createDataBitRec();
        comparator.syndromeError();
        cor.correct(comparator);
    }
}
