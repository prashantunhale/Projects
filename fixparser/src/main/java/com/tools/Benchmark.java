package com.tools;

public class Benchmark {
    public static void main(String[] args) {
        
        String inputFIXMessage = "8=FIX.4.2\u00019=163\u000135=D\u000134=972\u000149=TESTBUY3\u000152=20190206-16:25:10.403\u000156=TESTSELL3\u000111=141636850670842269979\u000121=2\u000138=100\u000140=1\u000154=1\u000155=AAPL\u000160=20190206-16:25:08.968\u0001207=TO\u00016000=TEST1234\u000110=106\u0001";
        int iterations = 1000000;

        byte[] msg = inputFIXMessage.getBytes();

        FIXParser fixParser = new FIXParser();

        long startTime = System.nanoTime();

        for(int i = 0 ; i < iterations; i++){
            fixParser.parse(msg);
        }

        long endTime = System.nanoTime();

        long duration = (endTime - startTime)/1000000;

        FIXMessage fixMessage = fixParser.parse(msg);

        System.out.println("Output: " + fixMessage);
        System.out.println("For " + iterations + " time taken: " + duration + "ms");

    }
}