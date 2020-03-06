package RegisterAllocation;

import cs132.util.SourcePos;

public class Node {
    SourcePos sourcePos;
    RelatedNodes relatednodes;
    Sets sets = new Sets();

    Node(SourcePos linepos){
        sourcePos = linepos;
        relatednodes = new RelatedNodes();
    }
//    public boolean equals(Node node){
//        if(sourcePos.toString().equals(node.sourcePos.toString()))
//            return true;
//        else
//            return false;
//    }
//    public boolean equals(SourcePos sp){
//        if(sourcePos.toString().equals(sp.toString()))
//            return true;
//        else
//            return false;
//    }

    public void addPre(Node pre) {
        this.relatednodes.addPre(pre);
    }

    public void addSucc(Node succ) {
        this.relatednodes.addSucc(succ);
    }

    public boolean isSuccEmpty() {
        return this.relatednodes.succNodes == null || this.relatednodes.succNodes.isEmpty();
    }
}

