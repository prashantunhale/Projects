package com.tools;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FIXParserTest {

    @Test
    public void testEmptyStringParse() {
        FIXParser fixParser = new FIXParser();

        byte[] msg = "".getBytes();

        FIXMessage fixMessage = fixParser.parse(msg);

        assertNull(fixMessage.getTagValue("8"));
        assertEquals("FIXMessage{tags={}}", fixMessage.toString());
    }

    @Test
    public void testNonFIXParse() {
        FIXParser fixParser = new FIXParser();

        byte[] msg = "Hello World".getBytes();

        FIXMessage fixMessage = fixParser.parse(msg);

        assertNull(fixMessage.getTagValue("8"));
        assertEquals("FIXMessage{tags={}}", fixMessage.toString());
    }

    @Test
    public void testIncompleteFIXParse() {
        FIXParser fixParser = new FIXParser();

        byte[] msg = "8=FIX.4.2".getBytes(); //Missing SOH '\u0001'

        FIXMessage fixMessage = fixParser.parse(msg);

        assertNull(fixMessage.getTagValue("8"));
        assertEquals("FIXMessage{tags={}}", fixMessage.toString());
    }

    @Test
    public void testTruncatedFIXParse() {
        FIXParser fixParser = new FIXParser();

        byte[] msg = "8=FIX.4.2\u00019=".getBytes(); //Missing SOH '\u0001'

        FIXMessage fixMessage = fixParser.parse(msg);

        assertEquals("FIX.4.2", fixMessage.getTagValue("8"));
        assertEquals("FIXMessage{tags={8=FIX.4.2}}", fixMessage.toString());
    }

    @Test
    public void testInvalidSeparatorFIXParse() {
        FIXParser fixParser = new FIXParser();

        byte[] msg = "8:FIX.4.2\u00019=".getBytes(); //Missing SOH '\u0001'

        FIXMessage fixMessage = fixParser.parse(msg);

        assertNull(fixMessage.getTagValue("8"));
        assertEquals("FIXMessage{tags={}}", fixMessage.toString());
    }

    @Test
    public void testInvalidStartFIXParse() {
        FIXParser fixParser = new FIXParser();

        byte[] msg = "\u00018".getBytes(); //Missing SOH '\u0001'

        FIXMessage fixMessage = fixParser.parse(msg);

        assertNull(fixMessage.getTagValue("8"));
        assertEquals("FIXMessage{tags={}}", fixMessage.toString());
    }


    @Test
    public void testNewSingleOrderParse() {
        FIXParser fixParser = new FIXParser();

        // Reference: https://www.fixsim.com/sample-fix-messages
        byte[] msg = "8=FIX.4.2\u00019=163\u000135=D\u000134=972\u000149=TESTBUY3\u000152=20190206-16:25:10.403\u000156=TESTSELL3\u000111=141636850670842269979\u000121=2\u000138=100\u000140=1\u000154=1\u000155=AAPL\u000160=20190206-16:25:08.968\u0001207=TO\u00016000=TEST1234\u000110=106\u0001"
                .getBytes();

        FIXMessage fixMessage = fixParser.parse(msg);

        assertEquals("FIX.4.2", fixMessage.getTagValue("8"));
        assertEquals("163", fixMessage.getTagValue("9"));
        assertEquals("D", fixMessage.getTagValue("35"));
        assertEquals("972", fixMessage.getTagValue("34"));
        assertEquals("TESTBUY3", fixMessage.getTagValue("49"));
        assertEquals("20190206-16:25:10.403", fixMessage.getTagValue("52"));
        assertEquals("TESTSELL3", fixMessage.getTagValue("56"));
        assertEquals("141636850670842269979", fixMessage.getTagValue("11"));
        assertEquals("2", fixMessage.getTagValue("21"));
        assertEquals("100", fixMessage.getTagValue("38"));
        assertEquals("1", fixMessage.getTagValue("40"));
        assertEquals("1", fixMessage.getTagValue("54"));
        assertEquals("AAPL", fixMessage.getTagValue("55"));
        assertEquals("20190206-16:25:08.968", fixMessage.getTagValue("60"));
        assertEquals("TO", fixMessage.getTagValue("207"));
        assertEquals("TEST1234", fixMessage.getTagValue("6000"));
        assertEquals("106", fixMessage.getTagValue("10"));

        assertEquals(
                "FIXMessage{tags={11=141636850670842269979, 55=AAPL, 34=972, 56=TESTSELL3, 35=D, 6000=TEST1234, 49=TESTBUY3, 38=100, 8=FIX.4.2, 9=163, 207=TO, 60=20190206-16:25:08.968, 40=1, 52=20190206-16:25:10.403, 21=2, 54=1, 10=106}}",
                fixMessage.toString());
    }

    @Test
    public void testExecutionReportParse() {
        FIXParser fixParser = new FIXParser();

        // Reference: https://www.fixsim.com/sample-fix-messages
        byte[] msg = "8=FIX.4.2\u00019=271\u000135=8\u000134=974\u000149=TESTSELL3\u000152=20190206-16:26:09.059\u000156=TESTBUY3\u00016=174.51\u000111=141636850670842269979\u000114=100.0000000000\u000117=3636850671684357979\u000120=0\u000121=2\u000131=174.51\u000132=100.0000000000\u000137=1005448\u000138=100\u000139=2\u000140=1\u000154=1\u000155=AAPL\u000160=20190206-16:26:08.435\u0001150=2\u0001151=0.0000000000\u000110=194\u0001"
                .getBytes();

        FIXMessage fixMessage = fixParser.parse(msg);

        assertEquals("FIX.4.2", fixMessage.getTagValue("8"));
        assertEquals("271", fixMessage.getTagValue("9"));
        assertEquals("8", fixMessage.getTagValue("35"));
        assertEquals("974", fixMessage.getTagValue("34"));
        assertEquals("TESTSELL3", fixMessage.getTagValue("49"));
        assertEquals("20190206-16:26:09.059", fixMessage.getTagValue("52"));
        assertEquals("TESTBUY3", fixMessage.getTagValue("56"));
        assertEquals("174.51", fixMessage.getTagValue("6"));
        assertEquals("141636850670842269979", fixMessage.getTagValue("11"));
        assertEquals("100.0000000000", fixMessage.getTagValue("14"));
        assertEquals("3636850671684357979", fixMessage.getTagValue("17"));
        assertEquals("0", fixMessage.getTagValue("20"));
        assertEquals("2", fixMessage.getTagValue("21"));
        assertEquals("174.51", fixMessage.getTagValue("31"));
        assertEquals("100.0000000000", fixMessage.getTagValue("32"));
        assertEquals("1005448", fixMessage.getTagValue("37"));
        assertEquals("100", fixMessage.getTagValue("38"));
        assertEquals("2", fixMessage.getTagValue("39"));
        assertEquals("1", fixMessage.getTagValue("40"));
        assertEquals("1", fixMessage.getTagValue("54"));
        assertEquals("AAPL", fixMessage.getTagValue("55"));
        assertEquals("20190206-16:26:08.435", fixMessage.getTagValue("60"));
        assertEquals("2", fixMessage.getTagValue("150"));
        assertEquals("0.0000000000", fixMessage.getTagValue("151"));
        assertEquals("194", fixMessage.getTagValue("10"));

        assertEquals(
                "FIXMessage{tags={49=TESTSELL3, 150=2, 151=0.0000000000, 52=20190206-16:26:09.059, 31=174.51, 32=100.0000000000, 54=1, 10=194, 11=141636850670842269979, 55=AAPL, 34=974, 56=TESTBUY3, 35=8, 14=100.0000000000, 37=1005448, 38=100, 17=3636850671684357979, 39=2, 6=174.51, 8=FIX.4.2, 9=271, 60=20190206-16:26:08.435, 40=1, 20=0, 21=2}}",
                fixMessage.toString());
    }

    @Test
    public void testLogOnParse() {
        FIXParser fixParser = new FIXParser();

        // Reference: https://www.fixsim.com/sample-fix-messages
        byte[] msg = "8=FIX.4.2\u00019=74\u000135=A\u000134=978\u000149=TESTSELL3\u000152=20190206-16:29:19.208\u000156=TESTBUY3\u000198=0\u0001108=60\u000110=137\u0001"
                .getBytes();

        FIXMessage fixMessage = fixParser.parse(msg);

        assertEquals("FIX.4.2", fixMessage.getTagValue("8"));
        assertEquals("74", fixMessage.getTagValue("9"));
        assertEquals("A", fixMessage.getTagValue("35"));
        assertEquals("978", fixMessage.getTagValue("34"));
        assertEquals("TESTSELL3", fixMessage.getTagValue("49"));
        assertEquals("20190206-16:29:19.208", fixMessage.getTagValue("52"));
        assertEquals("TESTBUY3", fixMessage.getTagValue("56"));
        assertEquals("0", fixMessage.getTagValue("98"));
        assertEquals("60", fixMessage.getTagValue("108"));
        assertEquals("137", fixMessage.getTagValue("10"));

        assertEquals(
                "FIXMessage{tags={34=978, 56=TESTBUY3, 35=A, 49=TESTSELL3, 8=FIX.4.2, 9=74, 108=60, 52=20190206-16:29:19.208, 98=0, 10=137}}",
                fixMessage.toString());
    }

    @Test
    public void testLogOutParse() {
        FIXParser fixParser = new FIXParser();

        // Reference: https://www.fixsim.com/sample-fix-messages
        byte[] msg = "8=FIX.4.2\u00019=62\u000135=5\u000134=977\u000149=TESTSELL3\u000152=20190206-16:28:51.518\u000156=TESTBUY3\u000110=092\u0001"
                .getBytes();

        FIXMessage fixMessage = fixParser.parse(msg);

        assertEquals("FIX.4.2", fixMessage.getTagValue("8"));
        assertEquals("62", fixMessage.getTagValue("9"));
        assertEquals("5", fixMessage.getTagValue("35"));
        assertEquals("977", fixMessage.getTagValue("34"));
        assertEquals("TESTSELL3", fixMessage.getTagValue("49"));
        assertEquals("20190206-16:28:51.518", fixMessage.getTagValue("52"));
        assertEquals("TESTBUY3", fixMessage.getTagValue("56"));
        assertEquals("092", fixMessage.getTagValue("10"));

        assertEquals(
                "FIXMessage{tags={34=977, 56=TESTBUY3, 35=5, 49=TESTSELL3, 8=FIX.4.2, 9=62, 52=20190206-16:28:51.518, 10=092}}",
                fixMessage.toString());

    }

}
