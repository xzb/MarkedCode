/*==================================================================
 * Readme: 1. Read information from text and store in TreeMap. The value of vertex is key.
 *         2. The pseudocode of Dijkstra from book is performed.
 *==================================================================*/

class Vertex
{
	public String key;			//the Vertex value

	public LinkedList<String>	adj;	//Adjacency list
	public boolean  known;
	public int 	 dist;            
	public Vertex   path;

	public LinkedList<Integer> weight;	//weight to adjacent vertex

	public Vertex() {
		adj= new LinkedList<String>();
		weight= new LinkedList<Integer>();
	}
}


public class Dijkstra {

	private TreeMap<String,Vertex> verMap;
	private Integer con;						//use to print the connections

	
	/*************************
	 * Constructor
	 * **********************/
	public Dijkstra() {
		verMap = new TreeMap<String,Vertex>( );
		verMap = readTex(verMap);
	}


	/*************************
	 * The Dijkstra algorithm is performed here. 
	 * **********************/
	public void shPath (String a, String b) {

		Vertex s=null,v=null;

		for (Vertex ver:verMap.values())		//traverse all the vertex
		{			
			ver.dist = Integer.MAX_VALUE;
			ver.known = false;
			ver.path = null;

			if (ver.key.equals(a)) {
				s= ver;					//start vertex
				s.dist=0;
				v= ver;					//current vertex; smallest unknown distance vertex;
			}			
		}

		boolean known=false;
		int dist=Integer.MAX_VALUE;

		while( !known )
		{
			v.known = true;
			if(v.key.equals(b)) break;			//found the destination.

			Iterator<Integer> weight=v.weight.iterator();

			for (String wk:v.adj)				//for each Vertex w adjacent to v
			{
				Vertex w= verMap.get(wk);		//get the adjacent vertex

				if( !w.known )
				{
					int cvw = weight.next();	//cost of edge from v to w;
					if( v.dist + cvw < w.dist )
					{
						// Update w
						w.dist = v.dist + cvw;	//decrease( w.dist to v.dist + cvw );
						w.path = v;
					}
				}
			}

			known=true;
			dist=Integer.MAX_VALUE;
			for (Vertex ver:verMap.values()) {
				known= known && ver.known;

				if (!ver.known && ver.dist<dist)		//find the smallest unknown vertex, traverse is consuming.
					v=ver;
			}
		} 

		
		/* Print out the information */
		con=0;
		String route= printPath(v);
		System.out.println("\nBy price:");
		System.out.println("  Price: "+"\t"+v.dist);
		System.out.println("  Connections: "+"\t"+con);
		System.out.println("  Route: "+"\t"+route);

	}


	
	/*************************
	 * Read from airports.txt and store the vertexes in TreeMap.
	 * **********************/
	private TreeMap<String,Vertex> readTex(TreeMap<String,Vertex> verMap) {
		String str="";

		try{
			FileReader fr = new FileReader("airports.txt");
			BufferedReader bfr= new BufferedReader(fr);

			while((str = bfr.readLine())!=null){

				String[] parts = str.split(" ");

				Vertex v= new Vertex();
				v.key=parts[0];

				for (int i=1,flag=0;i<parts.length;i++) {
					if (!parts[i].equals(""))
						if (flag==0)
						{v.adj.add(parts[i]); flag=1;}
						else
						{v.weight.add(Integer.parseInt(parts[i])); flag=0;}	//change String to Integer
				}

				verMap.put(v.key,v);

				//System.out.println(parts[1].equals(""));
			}
			//System.out.println(verMap.get("ATL").adj.toString());
			bfr.close();
		}catch (IOException e) {
			e.printStackTrace();
		} 

		return verMap;
	}


	/*************************
	 * Return shortest path.
	 * **********************/
	private String printPath( Vertex v)
	{
		if( v.path != null ) {
			con++;
			return printPath( v.path) +" -> "+ v.key;
		}
		else {
			con--;
			return v.key;
		}
	}


	public static void main(String[] args) {

		Dijkstra D=new Dijkstra();

		/*System.out.println("Please choose two airports from below:");
		System.out.println(D.verMap.keySet().toString());*/

		Scanner sc = new Scanner(System.in);

		while(true) {

			System.out.print("Enter departure airport:");
			String a = sc.next();
			System.out.print("Enter departure airport:");
			String b = sc.next(); 

			D.shPath(a,b);


			System.out.println("\nCheck another route (Y/N)?");
			if(sc.next().equals("Y")) ;
			else break; 

		}

	}

}
