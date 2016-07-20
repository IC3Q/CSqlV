package com.csqlv.utils;

public class UserMessages {
    public static void printHelp() {
        StringBuilder sb = new StringBuilder();
        sb.append("Usage: \tjava -jar CSqlV.jar --query=\"yourQuery\"\n");
        sb.append("notes:\n" +
                "\t* Query should be in quotes,\n" +
                "\t* filepath should be in escaped quotes\n" +
                "\t* mysql-like syntax\n" +
                "\t* if COUNT(something) is provided result will have only number of rows which satisfied provided condition\n" +
                "\t* Example of query: \"SELECT a, b FROM \\\"./filename\\\" WHERE a LIKE %s% ORDER BY b DESC LIMIT 5\n" +
                "\t* ");
        System.out.println(sb.toString());
    }

    public static void printNothingProvided() {
        System.out.println("type java -jar CSqlV.jar --help to get usage.");
    }
}
