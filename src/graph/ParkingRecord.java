package graph;

public class ParkingRecord {
    private String number;
    private String time;
    private int type;// 0: in ; 1: out;

    public ParkingRecord(int type, String number, String time) {
        this.number = number;
        this.time = time;
        this.type = type;
    }

    public String getNumber() {

        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
