package graph;

public class Path {
    private int dis;
    private String path;

    Path(int dis, String path) {
        this.dis = dis;
        this.path = path;
    }

    public int getDis() {
        return dis;
    }

    void setDis(int dis) {
        this.dis = dis;
    }

    public String getPath() {
        return path;
    }

    void setPath(String path) {
        this.path = path;
    }
}
