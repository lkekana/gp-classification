package GP;

// CSV file sample:
/*
 * Open,High,Low,Close,Adj Close,Output
-1.104550546,-1.103639854,-1.103311839,-1.100806056,-1.460681595,0
-1.098880837,-1.094481018,-1.097054617,-1.090832857,-1.404481685,1
-1.089937536,-1.095261261,-1.094458908,-1.098988201,-1.446811772,0
-1.097685041,-1.102451407,-1.09677864,-1.099555067,-1.431253596,0
-1.098284603,-1.100281155,-1.095300347,-1.100175385,-1.41510777,0
 */

public class DataPoint {
    double open;
    double high;
    double low;
    double close;
    double adjClose;
    public int output; // class

    public DataPoint(double open, double high, double low, double close, double adjClose, int output) {
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.adjClose = adjClose;
        this.output = output;
    }

    public double[] getData() {
        return new double[]{open, high, low, close, adjClose};
    }

    public String toString() {
        return "DataPoint{" +
                "open=" + open +
                ", high=" + high +
                ", low=" + low +
                ", close=" + close +
                ", adjClose=" + adjClose +
                ", output=" + output +
                '}';
    }

}