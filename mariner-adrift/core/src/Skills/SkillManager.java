package Skills;

import Inventory.Inventory;
import Inventory.Item;

public class SkillManager {
	public static SkillTree skilltree;
	private static Inventory inventory;
	public SkillManager(Inventory inventory) {
		this.inventory = inventory;
	}
	static {
		skilltree = new SkillTree(inventory);
		Skill s1 = new Skill("Base", "The Beginning", new Item[]{new Item(0, 1)}, null);
		Skill s2 = new Skill("Wood 1", "The Beginning", new Item[]{new Item(0, 5)}, s1);
		Skill s3 = new Skill("Stone 1", "The Beginning", new Item[]{new Item(1, 5)}, s1);
		Skill s4 = new Skill("Fiber 1", "The Beginning", new Item[]{new Item(2, 5)}, s1);
		Skill s5 = new Skill("Wood 2", "The Beginning", new Item[]{new Item(0, 15)}, s2);
		Skill s6 = new Skill("Stone 2", "The Beginning", new Item[]{new Item(1, 15)}, s3);
		Skill s7 = new Skill("Fiber 2", "The Beginning", new Item[]{new Item(2, 15)}, s4);
		skilltree.add(s1);
		skilltree.add(s2, skilltree.get(s1));
		skilltree.add(s3, skilltree.get(s1));
		skilltree.add(s4, skilltree.get(s1));
		skilltree.add(s5, skilltree.get(s2));
		skilltree.add(s6, skilltree.get(s3));
		skilltree.add(s7, skilltree.get(s4));
		
	}
}
