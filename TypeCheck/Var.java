package TypeCheck;

import syntaxtree.NodeToken;

public class Var {
    public String jid;
    public String vid;
    public NodeToken type;
    public int value;
    public String envid;
    static int intoffset=0;
    public Var(String id,String newvid,NodeToken mtype,int mvalue,String eid){
        jid = id;
        vid = newvid;
        type = mtype;
        value = mvalue;
        envid = eid;
    }
}
