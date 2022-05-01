package su.svn.readXML.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public enum IO {
    Util;

    public String readFromInputStream(java.io.InputStream inputStream) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }
}
