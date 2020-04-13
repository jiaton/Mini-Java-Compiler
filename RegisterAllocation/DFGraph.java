package RegisterAllocation;

import cs132.util.SourcePos;

import java.util.*;

public class DFGraph {
	public LinkedHashMap<String, Node> nodes;    //<sourcePos.toString, node>

	public Node getNode(String pos) {
		return nodes.get(pos);
	}

	public HashMap<String, Node> getPre(String pos) {
		return nodes.get(pos).relatednodes.preNodes;
	}

	public HashMap<String, Node> getSucc(String pos) {
		return nodes.get(pos).relatednodes.succNodes;
	}

	public void initial(SourcePos sourcePos) {
		nodes = new LinkedHashMap<>();
		nodes.put(sourcePos.toString(), new Node(sourcePos));
	}

	public void setSucc(SourcePos sourcePos, HashSet<SourcePos> list) {
		Node tnode = getNode(sourcePos.toString());
		for (SourcePos succPos : list) {
			Node succnode;
			if (nodes.containsKey(succPos.toString()))
				succnode = nodes.get(succPos.toString());
			else {
				succnode = new Node(succPos);
				nodes.put(succPos.toString(), succnode);
			}
			succnode.addPre(tnode);
			tnode.addSucc(succnode);
		}
	}

	public void addNode(Node n) {
		nodes.put(n.sourcePos.toString(), n);
	}

	public void print() {
		for (Map.Entry<String, Node> entry : nodes.entrySet()) {
			System.out.print("(");
			for (Map.Entry<String, Node> preentry : entry.getValue().relatednodes.preNodes.entrySet()) {
				System.out.print(preentry.getKey() + ", ");
			}
			System.out.print(") " + entry.getKey() + " (");
			for (Map.Entry<String, Node> succentry : entry.getValue().relatednodes.succNodes.entrySet()) {
				System.out.print(succentry.getKey() + ", ");
			}
			System.out.println(")");

		}
	}
}
