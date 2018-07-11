package graph;

import java.util.HashMap;
import java.util.Map;

public class SceneicSpot {
    private Map<String, Edge> linked = new HashMap<>();// 邻接
    private Map<String, Path> path = new HashMap<>();// 最短路径
    private String description;
    private int welcome_degree;
    private boolean has_restArea;
    private boolean has_toilet;

    SceneicSpot(){ }

    SceneicSpot(String description, int welcome_degree, boolean has_restArea, boolean has_toilet){
        this.description = description;
        this.welcome_degree = welcome_degree;
        this.has_restArea = has_restArea;
        this.has_toilet = has_toilet;
    }

    // Getter and Setter
    public Map<String, Path> getPath() {
        return path;
    }

    public void setPath(Map<String, Path> path) {
        this.path = path;
    }

    public Map<String, Edge> getLinked() {
        return linked;
    }

    public void setLinked(Map<String, Edge> linked) {
        this.linked = linked;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getWelcome_degree() {
        return welcome_degree;
    }

    public void setWelcome_degree(int welcome_degree) {
        this.welcome_degree = welcome_degree;
    }

    public boolean isHas_restArea() {
        return has_restArea;
    }

    public void setHas_restArea(boolean has_restArea) {
        this.has_restArea = has_restArea;
    }

    public boolean isHas_toilet() {
        return has_toilet;
    }

    public void setHas_toilet(boolean has_toilet) {
        this.has_toilet = has_toilet;
    }
}
