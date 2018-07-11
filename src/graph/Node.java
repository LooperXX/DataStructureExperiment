package graph;

import java.util.*;

public class Node {
    public Node son;
    public Node brother;
    public String name;
    public int value;



    public List<String> getPath() {
        List<String> res = new ArrayList<String>();
        Stack<Node> stack = new Stack<>();
        Node cur = this;
        while(!stack.empty() || cur != null){
            if(cur != null){
                res.add(cur.name);
                if(cur.brother != null){
                    stack.push(cur.brother);
                }
                cur = cur.son;
            }else
                cur = stack.pop();
        }
        return res;
    }

    public void insert(Node node) {
        if (this.son == null) {
            this.son = node;
        } else {
            Node cur = this.son;
            while (cur.brother != null) {
                cur = cur.brother;
            }
            cur.brother = node;
        }
    }

    public Node(String name, int value) {
        this.name = name;
        this.value = value;
    }
}