package trydemo.Algorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class TreeDML {

	public static void main(String[] args) {
		
		 ArrayList<Integer> intList=new ArrayList<Integer>();  
         ArrayList<Node> treeList=new ArrayList<Node>();  
         
         Tree tree = new Tree();
         tree.insert(3123);
         tree.insert(123);
         tree.insert(123123);
         tree.insert(1234);
         tree.insert(89);
         
         Node root = tree.root;
         //把节点放到队列中
         Queue<Node> queue = new LinkedList<Node>();
         queue.add(root);
         //打印点
         Node last = root;
         Node nlast  =null;
         while(queue.size()>0){
        	 Node node  =  queue.poll();
//        	 if()
        	 if(node.leftChild!=null){
        		 //把右边的节点放入队列
        		 queue.add(node.leftChild);
        		 nlast = node.leftChild;
        	 }
        	 if(node.rightChild!=null){
        		 //把左边的节点放入队列
        		 queue.add(node.rightChild);
        		 nlast = node.rightChild;
        	 }
        	System.out.print(node.value+"       ");
        	 
        	if(node == last){
        		//换行
        		System.out.println();
        		last = nlast;
        	}
        	 
        	 
         }
         
	} 
	
//	public void 
	
	
}
