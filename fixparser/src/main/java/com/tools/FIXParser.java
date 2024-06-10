package com.tools;

public class FIXParser {

    public FIXMessage parse(byte[] msg) {

        char startOfHeading = '\u0001'; // SOH character is used as a delimiter to separate different tags within a FIX message
        char equals = '='; // Equals character is used as a delimiter to separate tag and its value within a FIX message

        FIXMessage fixMessage = new FIXMessage();

        int start = 0;
        int length = msg.length;

        while (start < length) {
            // Iterate through till we first find '=' and subsequently 'SOH'

            int equalsIndex = -1;

            int end = start;

            // Find the '=' character
            while (end < length && msg[end] != equals) {
                end++;
            }
            equalsIndex = end;

            // Find the '\u0001' character
            while (end < length && msg[end] != startOfHeading) {
                end++;
            }

            // Extract the tag and its value
            if (end < length) {

                String tag = new String(msg, start, equalsIndex - start);
                String tagValue = new String(msg, equalsIndex + 1, end - equalsIndex - 1);

                fixMessage.addTag(tag, tagValue);
            }

            // Continue to next tag (if any)
            start = end + 1;
        }

        return fixMessage;

    }

}
