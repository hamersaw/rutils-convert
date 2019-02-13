package com.bushpath.rutils_convert;

import com.bushpath.rutils.reader.ThreadedCsvReader;

import java.io.FileOutputStream;
import java.io.DataOutputStream;
import java.io.BufferedOutputStream;

public class Csv2Binary {
    public static void main(String[] args) {
        try {
            // parse arguments
            if (args.length != 2) {
                System.out.println("Usage: convert Csv2Binary"
                    + " <input-filename> <output-filename>");
                System.exit(1);
            }

            // open reader and writer
            ThreadedCsvReader in = new ThreadedCsvReader(args[0], 4);
            BufferedOutputStream bufferedOut =
                new BufferedOutputStream(new FileOutputStream(args[1]));
            DataOutputStream out = new DataOutputStream(bufferedOut);

            // write header
            String[] header = in.getHeader();
            out.writeInt(header.length);
            for (String feature : header) {
                out.writeUTF(feature);
            }

            // iterate over records
            float[] record = null;
            while ((record = in.next()) != null) {
                // write record
                for (float value : record) {
                    out.writeFloat(value);
                }
            }

            // close reader and writer
            in.close();
            out.close();
            bufferedOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
