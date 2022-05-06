package Skills;

import com.csds.marineradrift.Tree;
import com.csds.marineradrift.Node;
import Inventory.Inventory;
import Inventory.Item;

public class SkillTree extends Tree<Skill> {
	private Inventory inventory;
	public SkillTree(Inventory inventory) {
		super();
		this.inventory = inventory;
	}
	public Node<Skill> get(String s){
		return get(getRoot(), s);
	}
	public Node<Skill> get(Node<Skill> n, String s) {
		if (s.equals(n.get().getName()))
			return n;
		if (n.numChildren() < 1)
			return null;
		for (int i = 0; i < n.numChildren(); i++) {
			if(get(n.getChild(i), s) != null)
					return get(n.getChild(i), s);
		}
		return null;
	}
	public void unlock(String n) {
		Skill s = get(n).get();
		for(Item i: s.getRequirements()) {
			inventory.remove(i);
		}
		s.unlock();
	}
	
}
