                                          Simple FIX Parser
                                          -----------------

Highlights:
-----------
1. 100% Code Coverage of FIX Parser
2. No external libraries (except JUnit and JaCoCo for Code Coverage)
3. Benchmarking

Code Coverage:
--------------
<img width="1433" alt="Screenshot 2024-06-10 at 11 48 40 PM" src="https://github.com/prashantunhale/Projects/assets/20407916/48888d18-5d29-4451-91b4-aef54ee0c2ff">

Benchmarking:
-------------

Performed 10 rounds of parsing below FIX Message for 1M iterations:

8=FIX.4.29=16335=D34=97249=TESTBUY352=20190206-16:25:10.40356=TESTSELL311=14163685067084226997921=238=10040=154=155=AAPL60=20190206-16:25:08.968207=TO6000=TEST123410=106

Average Time Taken: 1045.2 ms

| Attempt  | Time (ms) |
| -------- | --------- |
|     1    |     1077  |
|     2    |     1028  |
|     3    |     1003  |
|     4    |     1109  |
|     5    |     1039  |
|     6    |     1039  |
|     7    |     1087  |
|     8    |     1025  |
|     9    |     1010  |
|    10    |     1035  |
