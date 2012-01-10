package graph;

public class GraphTests {
	
	
//	public void testAdjListMethods(){
//		//Gerichteter Graph
//		Graph gerichtet = AdjList.valueOf("v1>v2:8;v2>v3:7;v1>v1:4");
//		//Ungerichteter Graph
//		Graph ungerichtet = AdjList.valueOf("v1-v2:8;v2-v3:7;v1-v1:4");
//		
//		
//		//toString--------------------------------------
//		String gerichtetResult = "v1 --> v2(8.0); v1(4.0); \n"+
//								 "v3 --> \n" +
//								 "v2 --> v3(7.0); \n";
//		String ungerichtetResult = "v1 --> v2(8.0); v1(4.0); v1(4.0); \n" +
//								   "v3 --> v2(7.0); \n" +
//								   "v2 --> v1(8.0); v3(7.0); \n";
//		assertEquals(gerichtetResult, gerichtet.toString());
//		assertEquals(ungerichtetResult, ungerichtet.toString());
//		//----------------------------------------------
//		
//
//		//allNodes--------------------------------------
//		Set<String> nodeSet = new HashSet<String>();
//		nodeSet.add("v1");
//		nodeSet.add("v3");
//		nodeSet.add("v2");
//		
//		assertEquals(nodeSet, gerichtet.allNodes());
//		assertEquals(nodeSet, ungerichtet.allNodes());
//		//----------------------------------------------
//		
//		
//		//noOfNodes-------------------------------------
//		assertEquals(3, gerichtet.noOfNodes());
//		assertEquals(3, ungerichtet.noOfNodes());
//		//----------------------------------------------
//		
//		//neighbors ??
//	}
//	
//	
//	public void testAdjMatrixMethods(){
//		//Gerichteter Graph
//		Graph gerichtet = AdjMatrix.valueOf("v1>v2:8;v2>v3:7;v1>v1:4");
//		//Ungerichteter Graph
//		Graph ungerichtet = AdjMatrix.valueOf("v1-v2:8;v2-v3:7;v1-v1:4");
//		
//		
//		//toString--------------------------------------
//		String gerichtetResult = "v1|4.0 | 8.0 | Infinity | \n" +
//								 "v2|Infinity | Infinity | 7.0 | \n" +
//								 "v3|Infinity | Infinity | Infinity | \n";
//		String ungerichtetResult = "v1|4.0 | 8.0 | Infinity | \n" +
//		 						   "v2|8.0 | Infinity | 7.0 | \n" +
//		 						   "v3|Infinity | 7.0 | Infinity | \n";
//		assertEquals(gerichtetResult, gerichtet.toString());
//		assertEquals(ungerichtetResult, ungerichtet.toString());
//		//----------------------------------------------
//		
//		
//		//allNodes--------------------------------------
//		Set<String> nodeSet = new HashSet<String>();
//		nodeSet.add("v1");
//		nodeSet.add("v3");
//		nodeSet.add("v2");
//		
//		assertEquals(nodeSet, gerichtet.allNodes());
//		assertEquals(nodeSet, ungerichtet.allNodes());
//		//----------------------------------------------
//		
//		
//		//noOfNodes-------------------------------------
//		assertEquals(3, gerichtet.noOfNodes());
//		assertEquals(3, ungerichtet.noOfNodes());
//		//----------------------------------------------
//	}
//	
//	
//	public void testNAG(){
//		//Gerichteter Graph
//		Graph gerichtet = AdjMatrix.valueOf("WrongGraph");
//		//Ungerichteter Graph
//		Graph ungerichtet = AdjMatrix.valueOf("AnotherWrongGraph");
//		
//		
//		//toString--------------------------------------
//		String nag = "NAG";
//		
//		assertEquals(nag, gerichtet.toString());
//		assertEquals(nag, ungerichtet.toString());
//		//----------------------------------------------
//		
//		
//		//allNodes--------------------------------------
//		assertEquals(null, gerichtet.allNodes());
//		assertEquals(null, ungerichtet.allNodes());
//		//----------------------------------------------
//		
//		
//		//noOfNodes-------------------------------------
//		assertEquals(-1, gerichtet.noOfNodes());
//		assertEquals(-1, ungerichtet.noOfNodes());
//		//----------------------------------------------
//		
//	}
//	
//	public void testBfs(){
//		Graph g = Graphs.adjMatrix("a-b:8;c>b:2;b>d:1;c-e:1;d-e:1");
//		Map<String,Integer> result = new HashMap<String,Integer>();
//		result.put("a", 0);
//		result.put("b", 1);
//		result.put("c", 4);
//		result.put("d", 2);
//		result.put("e", 3);
//		assertEquals(result,g.bfs("a"));
//		
//	}
	
	public static void dijkstraTest(){
		String s = "a>e:10;a>c:3;c>e:7;c>d:4;d>e:1;b>e:3;d>b:6";
		Graph g = Graphs.adjMatrix(s);
		System.out.println(g.dijkstra("a", "e"));
		System.out.println("Dijkstra: "+ g.getZugriffe());
	}
	
	public static void fiFoTest(){
		String s = "a>e:10;a!c:3;c>e:7;c!d:4;d>e:1;b!e:3;d>b:6";
		Graph g = Graphs.adjMatrix(s);
		System.out.println(g.dijkstraFiFo("a", "b"));
		System.out.println("FiFo: " + g.getZugriffe());
	}
	
	public static void floydWarshallTest(){
		String s = "a>e:10;a!c:3;c>e:7;c!d:4;d>e:1;b!e:3;d>b:6";
		String negativeZyklen = "a>b:2;a>c:1;c>d:-3;d>e:1;e>c:1;e>b:1";
		Graph g = Graphs.adjMatrix(negativeZyklen);
		Graph[] a = g.floydWarshall();
		System.out.println(a[1]);
		System.out.println(a[0]);
		System.out.println(g.getZugriffe());
		
	}
	
	public static void residualGraphTest(){
		String s = "q>x1:1;q>x2:1;q>x3:1;q>x4:1;q>x5:1;y1>s:1;y2>s:1;y3>s:1;y4>s:1;y5>s:1;x1>y1:1;x1>y2:1;x1>y3:1;x1>y4:1;x2>y1:1;x2>y2:1;x2>y3:1;x2>y4:1;x3>y2:1;x3>y5:1;x4>y2:1;x4>y5:1;x5>y2:1;x5>y5:1";
		Graph g = Graphs.adjMatrix(s);
		Pair<Graph, Double> result = g.residualGraph("q", "s");
		System.out.println("Maximale Matching per Residualgraph:\n");
		System.out.println(result.toString());
	}
	
	public static void edgesReverseTest(){
		String s = "a>b:1;a>c:4;b>c:3;b>d:1;c>d:5";
		Graph g = Graphs.adjList(s);
		
		System.out.println("AdjList: ");
		System.out.println("A " + g.edgesReverse("a"));
		System.out.println("B " + g.edgesReverse("b"));
		System.out.println("C " + g.edgesReverse("c"));
		System.out.println("D " + g.edgesReverse("d"));
		//System.out.println("X " + g.edgesReverse("x"));
		
		g = Graphs.adjMatrix(s);
		
		System.out.println("AdjMatrix:");
		System.out.println("A " + g.edgesReverse("a"));
		System.out.println("B " + g.edgesReverse("b"));
		System.out.println("C " + g.edgesReverse("c"));
		System.out.println("D " + g.edgesReverse("d"));
		//System.out.println("X " + g.edgesReverse("x"));
		
		
	}
	
	public static void fordFulkersonTest(){
		//String s = "q>x1:1;q>x2:1;q>x3:1;q>x4:1;x1>y1:1;x1>y2:1;x1>y4:1;x2>y1:1;x3>y3:1;x4>y4:1;y1>s:1;y2>s:1;y3>s:1;y4>s:1";
		//String s = "q>v1:4;q>v2:2;v1>v3:2;v1>v4:3;v2>v4:3;v3>s:5;v4>s:1";
		String s = "q>w1:1;q>w2:1;q>w3:1;q>w4:1;w1>j1:1;w1>j2:1;w1>j3:1;w1>j4:1;w2>j1:1;w2>j2:1;w2>j3:1;w3>j1:1;w3>j2:1;w4>j1:1;j2>s:1;j3>s:1;j4>s:1;j1>s:1";
		Graph g = Graphs.adjMatrix(s);
		System.out.println(g.fordFulkerson("q", "s"));
		System.out.println("Zugriffe: " + g.getZugriffe());
	}
	
	public static void bfsTest(){
		String s = "x1>x2:1";
		Graph g = Graphs.adjList(s);
		System.out.println(g.bfs("x1"));
		
	}
	
	public static void eckenGradTest(){
		String s = "x1>x3:1;x1>x2:1;x2!x3:1;x3>x2:1";
		Graph g1 = Graphs.adjMatrix(s);
		Graph g2 = Graphs.adjList(s);
		
		System.out.println("x1-Eingangsgrade:");
		System.out.println(g1.eingangsgrad("x1"));
		System.out.println(g2.eingangsgrad("x1"));
		
		System.out.println("x1-Ausgangsgrade:");
		System.out.println(g1.ausgangsgrad("x1"));
		System.out.println(g2.ausgangsgrad("x1"));
		
		System.out.println("x2-Eingangsgrade:");
		System.out.println(g1.eingangsgrad("x2"));
		System.out.println(g2.eingangsgrad("x2"));
		
		System.out.println("x2-Ausgangsgrade:");
		System.out.println(g1.ausgangsgrad("x2"));
		System.out.println(g2.ausgangsgrad("x2"));
		
		System.out.println("x3-Eingangsgrade:");
		System.out.println(g1.eingangsgrad("x3"));
		System.out.println(g2.eingangsgrad("x3"));
		
		System.out.println("x3-Ausgangsgrade:");
		System.out.println(g1.ausgangsgrad("x3"));
		System.out.println(g2.ausgangsgrad("x3"));
		
		

	}
	
	
	public static void deleteEdgetest(){
		String s = "x1>x3:1;x1>x2:1;x2!x3:1;x3>x2:1";
		AdjList g = (AdjList)AdjList.valueOf(s);
		System.out.println(g);
		g.deleteEdge("x1", "x3");
		System.out.println(g);
		

	}
	
	public static void fleuryTest(){
		String s = "a!b:1;a!c:1;b!c:1";
		String s1 = "a!f:1;b!f:1;a!c:1;a!d:1;a!b:1;b!c:1;b!d:1;d!e:1;d!c:1;c!e:1";
		String s2 = "a!b:1";
		Graph g1 = Graphs.adjList(s2);
		System.out.println(g1.fleury("c"));
	}
	
	public static void schnittkanteTest(){
		String s = "b!c:1;c>a:1";
		String s1 = "d!a:1;d!e:1;d!c:1;e!c:1";
		String s2 = "a!d:1;c!d:1;d!e:1;c!e:1";
		Graph g = Graphs.adjList(s2);
		System.out.println(g.istSchnittkante("e", "d"));
	}
	
	
	
	
	public static void main(String args[]){
 
		
		//residualGraphTest();
		
		//dijkstraTest();
		
		//floydWarshallTest();
		
		//fiFoTest();
		
		//edgesReverseTest();
		
		//fordFulkersonTest();
		
		//bfsTest();
		
		//eckenGradTest();
		
		//deleteEdgetest();
		
		//schnittkanteTest();
		
		fleuryTest();
		
		
		
		
		
		
	}
	
}

