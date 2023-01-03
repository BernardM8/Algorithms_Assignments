import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UnionFindTest {

	@Test
	void testQuickFindCount() {
		QuickFind uf = new QuickFind(5);
		
		uf.union(0, 1);
		uf.union(1, 4);
		uf.union(2, 3);
		
		int actual = uf.getConnectedComponentCount();
		int expected = 2;
		
		assertEquals(expected, actual);
		
	}
	
	@Test
	void testQuickFindIsConnected() {
		QuickFind uf = new QuickFind(5);
		
		uf.union(0, 1);
		uf.union(1, 4);
		uf.union(2, 3);
		
		boolean actual = uf.isConnected(2,0);
		boolean expected = false;
		
		assertEquals(expected, actual);
		
	}
	
	@Test
	void testQuickFindFind() {
		QuickFind uf = new QuickFind(5);
		
		uf.union(0, 1);
		uf.union(1, 4);
		uf.union(2, 3);
		
		int actual = uf.find(2);
		int expected = 3;
		
		assertEquals(expected, actual);
		
	}

}
