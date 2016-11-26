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
public class Comparator extends Cache implements ALUCompareTo {

    private final int[] tempReq = new int[8];
    private final int[] tempRec = new int[8];
    private final int[] checkBitReq = new int[4];
    private final int[] checkBitRec = new int[4];
    private int[] dataBitReq = new int[12];
    private int[] dataBitRec = new int[12];
    private ArrayList<Integer> errorIndex = new ArrayList<>();
    private int trappedNumberCheckbit = 0;
    private int trappedNumberDatabit = 0;
    private String errorStatusCheckBit = "No Error";
    private String errorStatusDataBit = "No Error";
    private boolean checkBitError, dataBitError;

    public Comparator(int[] req, int[] rec) {
        super(req, rec);
    }

    private void getReqCheckBit() {
        int c1 = ((((this.requestBit[7] ^ this.requestBit[6]) ^ this.requestBit[4]) ^ this.requestBit[3]) ^ this.requestBit[1]);
        int c2 = ((((this.requestBit[7] ^ this.requestBit[5]) ^ this.requestBit[4]) ^ this.requestBit[2]) ^ this.requestBit[1]);
        int c4 = (((this.requestBit[6] ^ this.requestBit[5]) ^ this.requestBit[4]) ^ this.requestBit[0]);
        int c8 = (((this.requestBit[3] ^ this.requestBit[2]) ^ this.requestBit[1]) ^ this.requestBit[0]);
        this.checkBitReq[0] = c1;
        this.checkBitReq[1] = c2;
        this.checkBitReq[2] = c4;
        this.checkBitReq[3] = c8;
    }

    private void getRecBit() {
        this.tempRec[0] = this.receiveBit[11];
        this.tempRec[1] = this.receiveBit[10];
        this.tempRec[2] = this.receiveBit[9];
        this.tempRec[3] = this.receiveBit[8];
        this.tempRec[4] = this.receiveBit[6];
        this.tempRec[5] = this.receiveBit[5];
        this.tempRec[6] = this.receiveBit[4];
        this.tempRec[7] = this.receiveBit[2];
    }

    private void getRecCheckBit() {
        this.checkBitRec[0] = this.receiveBit[7];
        this.checkBitRec[1] = this.receiveBit[3];
        this.checkBitRec[2] = this.receiveBit[1];
        this.checkBitRec[3] = this.receiveBit[0];
    }

    public void createDataBitReq() {
        this.dataBitReq[11] = this.checkBitReq[0];
        this.dataBitReq[10] = this.checkBitReq[1];
        this.dataBitReq[9] = this.requestBit[7];
        this.dataBitReq[8] = this.checkBitReq[2];
        this.dataBitReq[7] = this.requestBit[6];
        this.dataBitReq[6] = this.requestBit[5];
        this.dataBitReq[5] = this.requestBit[4];
        this.dataBitReq[4] = this.checkBitReq[3];
        this.dataBitReq[3] = this.requestBit[3];
        this.dataBitReq[2] = this.requestBit[2];
        this.dataBitReq[1] = this.requestBit[1];
        this.dataBitReq[0] = this.requestBit[0];
        System.out.println(Arrays.toString(dataBitReq));
    }

    public void createDataBitRec() {
        this.dataBitRec[11] = this.checkBitRec[0];
        this.dataBitRec[10] = this.checkBitRec[1];
        this.dataBitRec[9] = this.tempRec[0];
        this.dataBitRec[8] = this.checkBitRec[2];
        this.dataBitRec[7] = this.tempRec[1];
        this.dataBitRec[6] = this.tempRec[2];
        this.dataBitRec[5] = this.tempRec[3];
        this.dataBitRec[4] = this.checkBitRec[3];
        this.dataBitRec[3] = this.tempRec[4];
        this.dataBitRec[2] = this.tempRec[5];
        this.dataBitRec[1] = this.tempRec[6];
        this.dataBitRec[0] = this.tempRec[7];
        System.out.println(Arrays.toString(dataBitRec));
    }

    public ArrayList<Integer> syndromeError() {
        for (int i = 0; i < dataBitReq.length; i++) {
            if (dataBitRec[i] != dataBitReq[i]) {
                errorIndex.add(12 - i);
            }
        }
        System.out.println(errorIndex.toString());
        return errorIndex;
    }

    @Override
    public void compareTo() {
        this.getRecBit();
        this.getRecCheckBit();
        this.getReqCheckBit();
//        System.out.println(Arrays.toString(this.checkBitRec));
//        System.out.println(Arrays.toString(this.checkBitReq));
//        System.out.println(Arrays.toString(this.requestBit));
//        System.out.println(Arrays.toString(this.tempRec));
        for (int i = 0; i < checkBitReq.length; i++) {
            if (this.checkBitRec[i] != this.checkBitReq[i]) {
                trappedNumberCheckbit += 1;
                this.checkBitError = true;
                this.errorStatusCheckBit = "CheckBit Error";
                System.out.println(this.errorStatusCheckBit);
            }
        }
        int j = requestBit.length - 1;
        for (int i = 0; i < tempReq.length; i++) {
            if (this.tempRec[i] != this.requestBit[j]) {
                trappedNumberDatabit += 1;
                this.errorStatusDataBit = "DataBit Error";
                this.dataBitError = true;
                System.out.println(this.errorStatusDataBit);
            }
            j--;
        }
        this.createDataBitRec();
        this.createDataBitReq();
    }
    

    public int[] getDataBitReq() {
        return this.dataBitReq;
    }

    public int[] getDataBitRec() {
        return this.dataBitRec;
    }

    public void setDataBitReq(int index, int x) {
        this.dataBitReq[index] = x;
    }

    public void setDataBitRec(int index, int x) {
        this.dataBitRec[index] = x;
    }

    public ArrayList<Integer> getErrorIndex() {
        return this.errorIndex;
    }
    
    public int getDistance(){
        return this.trappedNumberCheckbit + this.trappedNumberDatabit;
    }
    
    public String getErrorMessages() {
        if (this.checkBitError) {
            return this.errorStatusCheckBit;
        } 
        else if (this.dataBitError) {
            return this.errorStatusDataBit;
        }
        else if(this.dataBitError && this.checkBitError){
            return this.errorStatusCheckBit + ", " + this.errorStatusDataBit;
        }
        else{
            return "No Error";
        }
    }
}
