package RegisterAllocation;

import cs132.util.SourcePos;

import java.util.HashMap;
import java.util.HashSet;

public class Sets {
    public HashSet<String> inSet;
    public HashSet<String> outSet;
    public HashSet<String> useSet;
    public HashSet<String> defSet;
    public HashSet<String> active;
    Sets(){
        inSet = new HashSet<>();
        outSet = new HashSet<>();
        useSet = new HashSet<>();
        defSet = new HashSet<>();
        active = new HashSet<>();
    }
    public void addIn(String e){
        inSet.add(e);
    }
    public void addOut(String e){
        outSet.add(e);
    }
    public void addUse(String e){
        useSet.add(e);
    }
    public void addDef(String e){
        defSet.add(e);
    }
    public void printActive(){
        for(String str : active)
            System.out.println("active: "+str);
    }
    public void setActive(){
        active.clear();
        active = new HashSet<>();
        for(String str : inSet){
            if(!active.contains(str)){
                active.add(str);
            }
        }
        for(String str : defSet){
            if(!active.contains(str)){
                active.add(str);
            }
        }
    }
    public void removeIn(String s){
        HashSet<String> removeSet = new HashSet<>();
        for(String string : inSet){
            if(string.equals(s)){
                removeSet.add(string);
            }
        }
        for(String sp : removeSet){
            inSet.remove(sp);
        }
    }
    public void removeOut(String s){
        HashSet<String> removeSet = new HashSet<>();
        for(String string : outSet){
            if(string.equals(s)){
                removeSet.add(string);
            }
        }
        for(String sp : removeSet){
            outSet.remove(sp);
        }
    }
    public void removeDef(String s){
        HashSet<String> removeSet = new HashSet<>();
        for(String string : defSet){
            if(string.equals(s)){
                removeSet.add(string);
            }
        }
        for(String sp : removeSet){
            defSet.remove(sp);
        }
    }
    public void removeUse(String s){
        HashSet<String> removeSet = new HashSet<>();
        for(String string : useSet){
            if(string.equals(s)){
                removeSet.add(string);
            }
        }
        for(String sp : removeSet){
            useSet.remove(sp);
        }
    }


}
