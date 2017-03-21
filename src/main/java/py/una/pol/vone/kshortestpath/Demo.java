package py.una.pol.vone.kshortestpath;

import java.util.Arrays;
import java.util.List;

public class Demo {

    public static void main(String[] args) {
        demo1();
    }

    private static void demo1() {
        //    1    4
        //  /  \  / \
        // 0    3    6
        //  \  / \  /
        //   2    5

        DirectedGraphNode a = new DirectedGraphNode(0); 
        DirectedGraphNode b = new DirectedGraphNode(1); 
        DirectedGraphNode c = new DirectedGraphNode(2); 
        DirectedGraphNode d = new DirectedGraphNode(3); 
        /*DirectedGraphNode e = new DirectedGraphNode(4); 
        DirectedGraphNode f = new DirectedGraphNode(5); 
        DirectedGraphNode g = new DirectedGraphNode(6); */

        // The edges above the line 0 - 6 have weight of 1.0.
        // The edges below the line 0 - 6 have weight of 2.0
        DirectedGraphWeightFunction weightFunction = 
                new DirectedGraphWeightFunction();

        a.addChild(b); weightFunction.put(a, b, 2);
        a.addChild(c); weightFunction.put(a, c, 1);
        b.addChild(d); weightFunction.put(b, d, 3);
        b.addChild(a); weightFunction.put(b, a, 2);

        c.addChild(a); weightFunction.put(c, a, 1);
        c.addChild(d); weightFunction.put(c, d, 2);
        d.addChild(b); weightFunction.put(d, b, 3);
        d.addChild(c); weightFunction.put(d, c, 2);

        List<Path> paths = new DefaultKShortestPathFinder()
                .findShortestPaths(a, d, weightFunction, 3);

        for (Path path : paths) {
            System.out.println(Arrays.toString(path.getNodeList().toArray()));
        }
    }
}