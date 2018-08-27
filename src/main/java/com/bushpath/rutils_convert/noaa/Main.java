package com.bushpath.rutils_convert.noaa;

import com.bushpath.rutils.reader.NoaaReader;
import com.bushpath.rutils.reader.NoaaRecord;
import com.bushpath.rutils.reader.Reader;
import com.bushpath.rutils.util.Geohash;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {
            // parse arguments
            if (args.length != 2) {
                System.out.println("Usage: convert-noaa"
                    + " <comma-separated-features> <filename>");
                System.exit(1);
            }

            String[] features = args[0].split(",");

            // open reader
            Reader<NoaaRecord> in = new NoaaReader(args[1], 4);

            // iterate over objects
            NoaaRecord record = null;
            int recordCount = 0;
            while ((record = in.next()) != null) {
                // add geohash, startTime, endTime
                StringBuilder stringBuilder = new StringBuilder(
                    Geohash.encode(record.getLatitude(), record.getLongitude(), 8)
                    + "," + record.getStartTime()
                    + "," + record.getEndTime());

                // add features
                for (int i=0; i<features.length; i++) {
                    stringBuilder.append("," + record.getAttribute(features[i]));
                }

                System.out.println(stringBuilder.toString());
                recordCount += 1;
            }

            // close reader
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
