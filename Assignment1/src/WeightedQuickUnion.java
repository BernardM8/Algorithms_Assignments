import java.util.List;

public class WeightedQuickUnion extends QuickUnion{
	
	private int[] size;
	private int height= 0;

	public WeightedQuickUnion(int n) {
		super(n);
		size = new int[n];
		for (int i = 0; i < id.length; i++) {
			size[i] = 1;
		}
	}



	//implementation to connect arrays
	public void connectWithUnion(List<int[]> input){
		for (int row = 0; row < input.size(); row++) {
			for (int col = 0; col < input.get(row).length; col++) {
				//check vertical connections
				if (row+1<input.size() && input.get(row)[col]==1 && input.get(row+1)[col]==1) {
					int p = col + row * input.get(row).length;
					int q = col + input.get(row).length * (row+1); // value to change
					union(p, q);    //make vertical connection
				}
				//check horizontal connections
				if(col+1<input.get(row).length && input.get(row)[col]==1 && input.get(row)[col+1]==1){
					int p2= col+row*input.get(row).length;
					int q2 = col+1+row*input.get(row).length; // value to change
					union(p2,q2);    //make horizontal connection
				}
			}
			this.height++;
		}
	}




	@Override
	public void union(int p, int q) {
		int pRoot = root(p);
		int qRoot = root(q);
		
		if(pRoot == qRoot)
			return;
		
		if(size[pRoot] < size[qRoot]) {
			id[pRoot] = qRoot;
			size[qRoot] += size[pRoot];
		}else {
			id[qRoot] = pRoot;
			size[pRoot] += size[qRoot];
		}
		count--;
	}


	//implement path compression find
	public String pathCompFind() {
		int row = id.length / height;
		for (int i = id.length-row; i < id.length; i++) {
			if (id[i]<row  && id[i]>=0){  //if a number in the last row matches the first row
				return "Allows water to drain";
			}
		}
		return "Don’t allow water to drain";
	}



	public void printArrays(){
		//print size array
		System.out.print("size[] = [" );
		for (int i = 0; i < size.length; i++) {
			System.out.print(size[i] + ",");
		}
		System.out.println("]" );
		//print id array
		System.out.print("id[] = [" );
		for (int i = 0; i < id.length; i++) {
			System.out.print(id[i] + ",");
		}
		System.out.println("]" );
	}



}
