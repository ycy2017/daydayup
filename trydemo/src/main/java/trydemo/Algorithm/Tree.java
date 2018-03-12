package trydemo.Algorithm;

/**
 * 二叉树
 * 右节点比左节点大
 * @author Mathsys
 *
 */
public class Tree {

	 Node root = null;

//	Tree(int value) {
//		root = new Node(value);
//		root.leftChild = null;
//		root.rightChild = null;
//	}

	public Node findKey(int value) {
		return root;
	} // 查找

	/**
	 *  插入
	 * @param value
	 * @return
	 */
	public String insert(int value) {
		//头一次,加载根节点
		if( root==null ){
			root = new Node(value);
			return "insert ok";
		}
		//从root开始遍历
		Node currentNode = root;
//		System.out.println(value);
		while(true){
			if(value >= currentNode.value){
				if(currentNode.rightChild==null){
					currentNode.rightChild = new Node(value);
					return "insert ok";
				}else{
					currentNode = currentNode.rightChild;
				}
			}else {
				if(currentNode.leftChild==null){
					currentNode.leftChild = new Node(value);
					return "insert ok";
				}else{
					currentNode = currentNode.leftChild;
				}
			}
		}
		
	} 

	public void inOrderTraverse() {
	} // 中序遍历递归操作

	public void inOrderByStack() {
	} // 中序遍历非递归操作

	public void preOrderTraverse() {
	} // 前序遍历

	public void preOrderByStack() {
	} // 前序遍历非递归操作

	public void postOrderTraverse() {
	} // 后序遍历

	public void postOrderByStack() {
	} // 后序遍历非递归操作

	public int getMinValue() {
		return 0;
	} // 得到最小(大)值

	public boolean delete(int value) {
		return false;
	} // 删除

}

class Node {
	int value;
	Node leftChild;
	Node rightChild;

	Node(int value) {
		this.value = value;
	}

	public void display() {
		System.out.print(this.value + "\t");
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.valueOf(value);
	}
}