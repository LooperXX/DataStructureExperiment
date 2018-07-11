package graph;

public class Edge {
    private int distance;
    private int time;

    public Edge(int distance){
        this.distance = distance;
    }

    // Getter and Setter
    int getTime() {
        return time;
    }

    void setTime(int time) {
        this.time = time;
    }

    int getDistance() {
        return distance;
    }

    void setDistance(int distance) {
        this.distance = distance;
    }
}
