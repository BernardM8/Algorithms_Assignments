public class Digraph {
    private final int V;
    private final Bag<Integer>[] adj;
    //private final Bag<Integer>[] adjFriend;

    //create empty graph with V verticies
    public Digraph() {
        this.V = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        //adjFriend = (Bag<Integer>[]) new Bag[V];
    }


    //create empty graph with V verticies
    public Digraph(int V)
    {
        this.V = V;
        adj = (Bag<Integer>[]) new Bag[V];
        //adjFriend = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<Integer>();
    }

    public void addEdge(int v, int w)
    {
        adj[v].add(w);
    }

    public Iterable<Integer> adj(int v)
    {  return adj[v];  }


}
