import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.In;

public class GeoLocator {

    public ST<Long, Location> symTab = new ST<Long, Location>();

    GeoLocator(String filename) {
        In in = new In(filename);
        String line = in.readLine();

        while(line != null){ 
            TokenFinder tf = new TokenFinder(line);
            String nextToken = tf.nextToken();

            String[] locArr = new String[8];

            for(int i = 0; i < 8; i++) {
                locArr[i] = nextToken;
                nextToken = tf.nextToken();
            }

            Location loc = new Location(locArr[2],locArr[3],locArr[4],locArr[5],
                                        Double.parseDouble(locArr[6]),Double.parseDouble(locArr[7]));
            long ip = IPConv.noip(locArr[0]);
            this.symTab.put(ip, loc);

            line = in.readLine();
        }
    }

    Location location(String q) {
        long ip = IPConv.noip(q);
        long key = this.symTab.floor(ip);
        return this.symTab.get(key);
    } 
}
