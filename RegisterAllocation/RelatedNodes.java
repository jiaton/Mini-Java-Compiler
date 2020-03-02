package RegisterAllocation;

import java.util.HashMap;

public class RelatedNodes {
    HashMap<String,Node> preNodes;  //<sourcePos.toString,node>
    HashMap<String,Node> succNodes;
    RelatedNodes(){
        preNodes = new HashMap<>();
        succNodes = new HashMap<>();
    }
    public void addPre(Node pre){
        preNodes.put(pre.sourcePos.toString(),pre);
    }
    public void addSucc(Node succ){
        succNodes.put(succ.sourcePos.toString(),succ);
    }
}
