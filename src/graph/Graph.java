package graph;

import java.io.*;
import java.util.*;

public class Graph {
    private static String filePath = "E:\\workspace\\IDEA_workspace\\ScenicSpotManagementSystemv1\\graphInit.txt";
    public static int num_sceneicSpot;
    private static Map<String, ArrayList<Path>> edges = new HashMap<>();
    private static Map<String, SceneicSpot> scene = new HashMap<>();
    private static Map<String, SceneicSpot> sceneRecover = new HashMap<>();
    private static Map<String, Path> pathRecover = new HashMap<>();
    public static ArrayList<String> name = new ArrayList<>();
    private static String[] sortName;
    private static final int MAX = 32767;
    private static String[] tspPath;
    private static int SPathDis = MAX;

    public static Map<String, SceneicSpot> getSceneRecover() {
        return sceneRecover;
    }

    public static Map<String, Path> getPathRecover() {
        return pathRecover;
    }

    public static String[] getTspPath() {
        return tspPath;
    }

    public static String[] getSortName() {
        return sortName;
    }

    public static int getSPathDis() {
        return SPathDis;
    }

    public static Map<String, ArrayList<Path>> getEdges() {
        return edges;
    }

    protected static int getNum_sceneicSpot() {
        return num_sceneicSpot;
    }

    protected static void setNum_sceneicSpot(int num_sceneicSpot) {
        Graph.num_sceneicSpot = num_sceneicSpot;
    }

    public static Map<String, SceneicSpot> getScene() {
        return scene;
    }

    protected static void setScene(Map<String, SceneicSpot> scene) {
        Graph.scene = scene;
    }

    public static ArrayList<String> getName() {
        return name;
    }

    protected static void setName(ArrayList<String> name) {
        Graph.name = name;
    }

    protected static String getFilePath() {
        return filePath;
    }

    protected static void setFilePath(String filePath) {
        Graph.filePath = filePath;
    }

    static void initGraph(){
        File file = new File(filePath);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"))) {
            String tempString = null;
             //一次读入一行,直到读入null为文件结束
            num_sceneicSpot = Integer.parseInt(reader.readLine());
            while ((tempString = reader.readLine()) != null) {
                if(tempString.equals("Description")){
                    break;
                }
                String[] res = tempString.split(",");
                // 检查景点是否已存在
                for (int i = 0; i < 2; i++){
                    if(!scene.containsKey(res[i])){
                        scene.put(res[i],new SceneicSpot());
                    }
                }
                scene.get(res[0]).getLinked().put(res[1],new Edge(Integer.parseInt(res[2])));
                scene.get(res[1]).getLinked().put(res[0],new Edge(Integer.parseInt(res[2])));
                if(edges.containsKey(res[0]))
                    edges.get(res[0]).add(new Path(Integer.parseInt(res[2]),res[1]));
                else{
                    ArrayList<Path> path = new ArrayList<>();
                    path.add(new Path(Integer.parseInt(res[2]),res[1]));
                    edges.put(res[0],path);
                }
            }
            int index = 0;
            while ((tempString = reader.readLine()) != null && index != 12) {
                String[] res = tempString.split(",");
                scene.get(res[0]).setDescription(res[1]);
                scene.get(res[0]).setWelcome_degree(Integer.parseInt(res[2]));
                scene.get(res[0]).setHas_restArea((Integer.parseInt(res[3]) == 1));
                scene.get(res[0]).setHas_toilet((Integer.parseInt(res[4]) == 1));
                index++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printName(String name){
        if(name.length()<3){
            System.out.print(name + "\t\t");
        }else{
            System.out.print(name + '\t');
        }
    }

    static void showGraph() {
        System.out.print("\t\t");
        for (String key : scene.keySet()) {
            printName(key);
        }
        System.out.println();
        for (String key : scene.keySet()) {
            printName(key);
            Map cur = scene.get(key).getLinked();
            for (String aName : name) {
                if (key.equals(aName)) {
                    System.out.print(0 + "\t\t");
                } else if (cur.containsKey(aName)) {
                    System.out.print(((Edge) cur.get(aName)).getDistance() + "\t\t");
                } else {
                    System.out.print(MAX + "\t");
                }
            }
            System.out.println();
        }
    }

    static void initName() {
        System.out.print("\t\t");
        int i = 0;
        tspPath = new String[num_sceneicSpot + 1];
        for (String key : scene.keySet()) {
            printName(key);
            name.add(key);
            tspPath[i++] = key;
        }
        tspPath[i] = tspPath[0];
        System.out.println();
    }

    static void initSPath() {
        // 初始化每个节点所维护的最短路径Map,放入其所有的邻接节点以及不可达节点
        for(int i = 0; i < name.size(); i++){
            for(int j = 0; j < name.size(); j++)
                if(j != i){
                    scene.get(name.get(i)).getPath().put(name.get(j),new Path(MAX,name.get(i) + "->" + name.get(j)));
                    if(scene.get(name.get(i)).getLinked().containsKey(name.get(j)))
                        scene.get(name.get(i)).getPath().put(name.get(j),new Path(scene.get(name.get(i)).getLinked().get(name.get(j)).getDistance(),name.get(i) + "->" + name.get(j)));
                }else{
                    scene.get(name.get(i)).getPath().put(name.get(j),new Path(0,name.get(i) + "->" + name.get(j)));
                }
        }

    }

    static void computeSPath() {
        //循环遍历
        for (String node : name) {
            Map<String, Edge> linkedMap = scene.get(node).getLinked();
            // 从start节点的每一个邻接节点开始
            for (String link : linkedMap.keySet()) {
                String curPath = node + "->" + link;
                int curDis = linkedMap.get(link).getDistance();
                solveSPath(node, link, curPath, curDis);
            }
        }
    }

    private static void solveSPath(String start, String linkedNode, String curPath, int curDis) {
        Map<String, Edge> linkedMap = scene.get(linkedNode).getLinked();
        for(String newNode : linkedMap.keySet()){
            if(!newNode.equals(start)){
                int temp = curDis + linkedMap.get(newNode).getDistance();
                if(scene.get(start).getPath().get(newNode).getDis() > temp) {
                    scene.get(start).getPath().get(newNode).setDis(temp);
                    scene.get(start).getPath().get(newNode).setPath(curPath + "->" + newNode);
                    solveSPath(start,newNode,curPath + "->" + newNode,temp);
                }
            }
        }
    }

    static void showAllPath() {
        for(String start : name){
            for(String end : name){
                showPath(start,end);
            }
        }
    }

    static void showPath(String start, String end) {
        System.out.println(scene.get(start).getPath().get(end).getPath() + ":" + scene.get(start).getPath().get(end).getDis());
    }

    public static String servletSPath(String start, String end){
        return scene.get(start).getPath().get(end).getPath() + "," + scene.get(start).getPath().get(end).getDis();
    }

    public static void addScenicSpot(String newNode, String[] linked, Edge[] linkedEdge){
        ArrayList<Path> path = new ArrayList<>();
        for(int i = 0; i < linked.length; i++){
            path.add(new Path(linkedEdge[i].getDistance(),linked[i]));
        }
        edges.put(newNode,path);
        name.add(newNode);
        scene.put(newNode,new SceneicSpot());
        num_sceneicSpot++;
        scene.get(newNode).getPath().put(newNode,new Path(0,newNode + "->" + newNode));
        int index = 0;
        // 加入到各自的邻接表中,并初始化新节点以及邻接节点的最短路径表
        for (Edge edge : linkedEdge) {
            scene.get(newNode).getLinked().put(linked[index],edge);
            scene.get(linked[index]).getLinked().put(newNode,new Edge(edge.getDistance()));
            scene.get(newNode).getPath().put(linked[index],new Path(edge.getDistance(),newNode + "->" + linked[index]));
            scene.get(linked[index]).getPath().put(newNode, new Path(edge.getDistance(),linked[index] + "->" + newNode));
            index++;
        }

        // 增加到其他节点的最短路径表中(并在新节点的最短路径表中加入其他节点）
        for(String cur : scene.keySet()){
            if(scene.get(cur).getPath().size() != num_sceneicSpot && !cur.equals(newNode)){
                scene.get(cur).getPath().put(newNode,new Path(MAX, cur + "->" + newNode));
                scene.get(newNode).getPath().put(cur,new Path(MAX, newNode + "->" + cur));
            }
        }
        // 更新最短路径
        initSPath();
        computeSPath();

    }

    public static void deleteScenicSpot(String deleteNode) {
        num_sceneicSpot--;
        // 删除edges
        for(String s : edges.keySet()){
            if(s.equals(deleteNode)){
                edges.remove(deleteNode);
                break;
            }else{
                for(int i = 0; i < edges.get(s).size(); i++){
                    if(edges.get(s).get(i).getPath().equals(deleteNode)){
                        edges.get(s).remove(i);
                        break;
                    }
                }
            }
        }
        // 备份景点
        sceneRecover.put(deleteNode,scene.get(deleteNode));

        // 删除景点
        name.remove(deleteNode);
        scene.remove(deleteNode);
        // 删除邻接与最短路径并重置各节点途中包括本节点的路径
        for(SceneicSpot sc : scene.values()){
            sc.getLinked().remove(deleteNode);
            sc.getPath().remove(deleteNode);
            for(String end : sc.getPath().keySet()){
                if(sc.getPath().get(end).getPath().contains(deleteNode)){
                    sc.getPath().put(end,new Path(MAX,(sc.getPath().get(end).getPath().split("->"))[0] + "->" + end));
                }
            }
        }
        // FIXME: |...-_-...|
        initSPath();
        computeSPath();
    }

    public static void recoverScenicSpot(String node){
        // 还原edges并纠正scene的linked
        ArrayList<Path> rec = new ArrayList<>();
        SceneicSpot recoverNode = sceneRecover.get(node);
        sceneRecover.remove(node);
        for(String s : recoverNode.getLinked().keySet()){
            if(sceneRecover.containsKey(s)){
                recoverNode.getLinked().remove(s);
            }else{
                int disRecover = recoverNode.getLinked().get(s).getDistance();
                rec.add(new Path(disRecover,s));
                scene.get(s).getLinked().put(node,new Edge(disRecover));
            }
        }
        edges.put(node,rec);
        // 纠正path
        for(String s : sceneRecover.keySet()){
            if(recoverNode.getPath().containsKey(s) && !s.equals(node))
                recoverNode.getPath().remove(s);
        }
        for(String s : recoverNode.getPath().keySet()){
            for(String del : sceneRecover.keySet()){
                if(recoverNode.getPath().get(s).getPath().contains(del)){
                    recoverNode.getPath().put(s,new Path(MAX, recoverNode.getPath().get(s).getPath().split("->")[0] + "->" + s));
                }
            }
        }
        for(String s : scene.keySet()){
            scene.get(s).getPath().put(node,new Path(MAX,s + "->" + node));
        }
        num_sceneicSpot++;
        name.add(node);
        scene.put(node,recoverNode);
        initSPath();
        computeSPath();
    }

    static void outGuideMap(String start) {
        String[] guide = new String[num_sceneicSpot];
        guide[0] = start;
        int index = 1;
        for(String s : name){
            if(!s.equals(start)){
                guide[index++] = s;
            }
        }
        int[][] map = new int[num_sceneicSpot][num_sceneicSpot];
        for(int i = 0; i < num_sceneicSpot; i++){
            map[i][i] = 0;
            for(int j = 0; j < num_sceneicSpot; j++){
                if(j == i){
                    continue;
                }
                if(scene.get(guide[i]).getLinked().containsKey(guide[j])){
                    map[i][j] = scene.get(guide[i]).getLinked().get(guide[j]).getDistance();
                }else{
                    //map[i][j] = scene.get(guide[i]).getPath().get(guide[j]).getDis();
                    map[i][j] = MAX;
                }
            }
        }
        prim(map,guide);
    }

    public static void addEdge(String[] newEdge) {
        int dis = Integer.parseInt(newEdge[2]);
        if(!scene.get(newEdge[0]).getLinked().containsKey(newEdge[1])){
            scene.get(newEdge[0]).getLinked().put(newEdge[1],new Edge(dis));
            scene.get(newEdge[1]).getLinked().put(newEdge[0],new Edge(dis));
            if(scene.get(newEdge[0]).getPath().get(newEdge[1]).getDis() > dis){
                scene.get(newEdge[0]).getPath().get(newEdge[1]).setDis(dis);
                scene.get(newEdge[0]).getPath().get(newEdge[1]).setPath(newEdge[0] + "->" + newEdge[1]);
                scene.get(newEdge[1]).getPath().get(newEdge[0]).setDis(dis);
                scene.get(newEdge[1]).getPath().get(newEdge[0]).setPath(newEdge[1] + "->" + newEdge[0]);
            }
            initSPath();
            computeSPath();
            showGraph();
            showAllPath();
            if(edges.containsKey(newEdge[0]) || edges.containsKey(newEdge[1])){
                if(edges.containsKey(newEdge[0])){
                    edges.get(newEdge[0]).add(new Path(dis,newEdge[1]));
                }else{
                    edges.get(newEdge[1]).add(new Path(dis,newEdge[0]));
                }
            }else{
                ArrayList<Path> p = new ArrayList<>();
                p.add(new Path(dis,newEdge[1]));
                edges.put(newEdge[0],p);
            }
        }else{
            if(scene.get(newEdge[0]).getPath().get(newEdge[1]).getDis() < dis){
                System.out.println("[Wrong]您的添加有误:<");
            }else{
                scene.get(newEdge[0]).getLinked().put(newEdge[1],new Edge(dis));
                scene.get(newEdge[1]).getLinked().put(newEdge[0],new Edge(dis));
                scene.get(newEdge[0]).getPath().get(newEdge[1]).setDis(dis);
                scene.get(newEdge[0]).getPath().get(newEdge[1]).setPath(newEdge[0] + "->" + newEdge[1]);
                scene.get(newEdge[1]).getPath().get(newEdge[0]).setDis(dis);
                scene.get(newEdge[1]).getPath().get(newEdge[0]).setPath(newEdge[1] + "->" + newEdge[0]);
                initSPath();
                computeSPath();
                showGraph();
                showAllPath();
                if(edges.containsKey(newEdge[0])){
                    for(Path p : edges.get(newEdge[0])){
                        if(p.getPath().equals(newEdge[1])){
                            p.setDis(dis);
                        }
                    }
                }else {
                    for(Path p : edges.get(newEdge[1])){
                        if(p.getPath().equals(newEdge[0])){
                            p.setDis(dis);
                        }
                    }
                }
            }
        }
    }

    public static void deleteEdge(String[] newEdge) {
        pathRecover.put(newEdge[0],new Path(scene.get(newEdge[0]).getLinked().get(newEdge[1]).getDistance(),newEdge[1]));
        // 更新edges
        if(edges.containsKey(newEdge[0])){
            for(Path p : edges.get(newEdge[0])){
                if(p.getPath().equals(newEdge[1])){
                    edges.get(newEdge[0]).remove(p);
                    break;
                }
            }
            if(edges.get(newEdge[0]).size() == 0)
                edges.remove(newEdge[0]);
        }else{
            for(Path p : edges.get(newEdge[1])){
                if(p.getPath().equals(newEdge[0])){
                    edges.get(newEdge[1]).remove(p);
                    break;
                }
            }
            if(edges.get(newEdge[1]).size() == 0)
                edges.remove(newEdge[1]);
        }

        scene.get(newEdge[0]).getLinked().remove(newEdge[1]);
        scene.get(newEdge[1]).getLinked().remove(newEdge[0]);
        String path1 = newEdge[0] + "->" + newEdge[1];
        String path2 = newEdge[1] + "->" + newEdge[0];
        for(SceneicSpot s : scene.values()){
            for(Path p : s.getPath().values()){
                if(p.getPath().contains(path1) || p.getPath().contains(path2)){
                    p.setDis(MAX);
                    String[] prePath = p.getPath().split("->");
                    p.setPath(prePath[0] + "->" + prePath[prePath.length-1]);
                }
            }
        }
        initSPath();
        computeSPath();
        showGraph();
        showAllPath();
    }

    public static void recoverEdge(String[] edge){
        Path path;
        if(pathRecover.containsKey(edge[0])){
            path = pathRecover.get(edge[0]);
            pathRecover.remove(edge[0]);
        }else{
            path = pathRecover.get(edge[1]);
            pathRecover.remove(edge[1]);
        }
        scene.get(edge[0]).getLinked().put(edge[1],new Edge(path.getDis()));
        scene.get(edge[1]).getLinked().put(edge[0],new Edge(path.getDis()));
        if(edges.containsKey(edge[0]) || edges.containsKey(edge[1])){
            if(edges.containsKey(edge[0])){
                edges.get(edge[0]).add(new Path(path.getDis(),edge[1]));
            }else{
                edges.get(edge[1]).add(new Path(path.getDis(),edge[0]));
            }
        }else{
            ArrayList<Path> p = new ArrayList<>();
            p.add(new Path(path.getDis(),edge[1]));
            edges.put(edge[0],p);
        }
        initSPath();
        computeSPath();
    }

    public static Map<String,String> searchScenicSpot(String keyword){
        if(keyword.equals("")){
            return null;
        }
        Map<String,String> resMap = new HashMap<>();
        for(String s : scene.keySet()){
            if(s.contains(keyword) || scene.get(s).getDescription().contains(keyword)){
                SceneicSpot cur = scene.get(s);
                System.out.println(s + ": " + cur.getDescription());
                System.out.println("\t受欢迎度：" + cur.getWelcome_degree() + "\n\t是否有休息区: " + (cur.isHas_restArea() ? "有": "没有") + "\n\t是否有厕所: " + (cur.isHas_toilet() ? "有": "没有"));
                resMap.put(keyword,s + "," + cur.getDescription() + "," + cur.getWelcome_degree() + "," + (cur.isHas_restArea() ? "有": "没有") + "," + (cur.isHas_toilet() ? "有": "没有"));
                return resMap;
            }
        }
        System.out.println("[Wrong]您输入的关键字查询失败:<");
        return null;
    }

    private static int partitionByEdge(int low, int high){
        String temp = sortName[low];
        while (low < high) {
            while (low < high && scene.get(sortName[high]).getLinked().size() <= scene.get(temp).getLinked().size()){
                if(scene.get(sortName[high]).getLinked().size() == scene.get(temp).getLinked().size()){
                    if(scene.get(sortName[high]).getWelcome_degree() <= scene.get(temp).getWelcome_degree())
                        high--;
                    else
                        break;

                }else{
                    high--;
                }
            }

            sortName[low] = sortName[high];
            while (low < high && scene.get(sortName[low]).getLinked().size() >= scene.get(temp).getLinked().size()){
                if(scene.get(sortName[low]).getLinked().size() == scene.get(temp).getLinked().size()){
                    if(scene.get(sortName[low]).getWelcome_degree() >= scene.get(temp).getWelcome_degree())
                        low++;
                    else
                        break;
                }else{
                    low++;
                }
            }

            sortName[high] = sortName[low];
        }
        sortName[low] = temp;
        return low;
    }

    private static void sortScenicSpotByEdge(int low, int high){
        if (low < high) {
            int result = partitionByEdge(low, high);
            sortScenicSpotByEdge(low, result - 1);
            sortScenicSpotByEdge(result + 1, high);
        }
    }

    private static void sortScenicSpotByWd(int low, int high){
        if (low < high) {
            int result = partitionByWd(low, high);
            sortScenicSpotByWd(low, result - 1);
            sortScenicSpotByWd(result + 1, high);
        }
    }

    private static int partitionByWd(int low, int high){
        String temp = sortName[low];
        while (low < high) {
            while (low < high && scene.get(sortName[high]).getWelcome_degree() <= scene.get(temp).getWelcome_degree()){
                if (scene.get(sortName[high]).getWelcome_degree() == scene.get(temp).getWelcome_degree()){
                    if(scene.get(sortName[high]).getLinked().size() <= scene.get(temp).getLinked().size())
                        high--;
                    else
                        break;
                }else{
                    high--;
                }
            }

            sortName[low] = sortName[high];
            while (low < high && scene.get(sortName[low]).getWelcome_degree() >= scene.get(temp).getWelcome_degree()){
                if (scene.get(sortName[low]).getWelcome_degree() == scene.get(temp).getWelcome_degree()){
                    if(scene.get(sortName[low]).getLinked().size() >= scene.get(temp).getLinked().size())
                        low++;
                    else
                        break;
                }else{
                    low++;
                }
            }
            sortName[high] = sortName[low];
        }
        sortName[low] = temp;
        return low;
    }

    public static void sortScenicSpotBy(String rule){
        switch (rule){
            case "岔路数":
                sortName = new String[num_sceneicSpot];
                name.toArray(sortName);
                sortScenicSpotByEdge(0,num_sceneicSpot - 1);
                System.out.println("景区名 | 岔路数");
                for(String res : sortName){
                    System.out.println(res + " : " + scene.get(res).getLinked().size());
                }
                break;
            case "受欢迎度":
                sortName = new String[num_sceneicSpot];
                name.toArray(sortName);
                sortScenicSpotByWd(0,num_sceneicSpot - 1);
                System.out.println("景区名 | 受欢迎度");
                for(String res : sortName){
                    System.out.println(res + " : " + scene.get(res).getWelcome_degree());
                }
                break;
            default:
                System.out.println("[Wrong]您的输入有误:<");
                break;
        }
    }

    static void prim(int[][] graph, String[] guide) {
        int n = num_sceneicSpot;
        Node root = new Node(guide[0]);
        Map<String, Node> tree = new HashMap<>();
        tree.put(guide[0],root);
        for(int i = 0; i < num_sceneicSpot - 1; i++){
            tree.put(guide[i+1],new Node(guide[i+1]));
        }
        int[] cost = new int[n];
        int[] save = new int[n];//存取前驱结点
        int i, j, min, index, sum = 0;
        for (i = 1; i < n; i++) {
            cost[i] = graph[0][i];
            save[i] = 0;
        }
        for (i = 1; i < n; i++) {
            min = MAX;
            index = 0;
            for (j = 1; j < n; j++) {
                if (cost[j] != 0 && cost[j] < min) {
                    min = cost[j];
                    index = j;
                }
            }
            if (index == 0) return;
            cost[index] = 0;
            //sum += min;
            tree.get(guide[save[index]]).insert(tree.get(guide[index]));
            //System.out.println(guide[save[index]] + "->" + guide[index] + "," + min);
            for (j = 1; j < n; j++) {
                if (cost[j] != 0 && cost[j] > graph[index][j]) {
                    cost[j] = graph[index][j];
                    save[j] = index;
                }
            }
        }
        //System.out.println("sum:" + sum);
        List<String> res = root.getPath();
        System.out.println(res.toString());
    }

    public static void tsp(String start){
        boolean[] visited = new boolean[num_sceneicSpot];
        ArrayList<String> pathList = new ArrayList<>();
        for(int i = 0; i < num_sceneicSpot; i++){
            visited[i] = false;
        }
        pathList.add(start);
        SPathDis = MAX;
        for(String end : scene.get(start).getLinked().keySet()){
            tspDFS(name.indexOf(start),name.indexOf(end),visited, pathList);
        }
        for(int i = 0; i < num_sceneicSpot; i++){
            System.out.print(tspPath[i] + "->");
        }
        System.out.println(tspPath[num_sceneicSpot]);
        System.out.println("距离: " + SPathDis);
    }

    private static void tspDFS(int cur, int end, boolean[] visited, ArrayList<String> pathList) {
        visited[cur] = true;
        if (cur == end && pathList.size() == num_sceneicSpot)
        {
            String path = pathList.toString();
            String[] road = path.substring(1,path.length()-1).replace(" ","").split(",");
            int dis = 0;
            for(int i = 0; i < num_sceneicSpot - 1; i++){
                dis += scene.get(road[i]).getLinked().get(road[i+1]).getDistance();
            }
            dis += scene.get(road[0]).getLinked().get(road[num_sceneicSpot-1]).getDistance();
            if(dis < SPathDis){
                for(int i = 0; i < num_sceneicSpot; i++){
                    tspPath[i] =  road[i];
                }
                tspPath[num_sceneicSpot] = road[0];
                SPathDis = dis;
            }

        }
        for (String s : scene.get(name.get(cur)).getLinked().keySet())
        {
            if (!visited[name.indexOf(s)])
            {
                pathList.add(name.get(name.indexOf(s)));
                tspDFS(name.indexOf(s), end, visited, pathList);
                pathList.remove(s);
            }
        }
        visited[cur] = false;
    }
}
