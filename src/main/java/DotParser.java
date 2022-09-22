import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.nio.dot.DOTImporter;

import java.io.FileReader;
import java.io.IOException;

public class DotParser {
    private static final String DOT_FILE = "./test1.dot";

    public static void main(String[] args) throws IOException {
        parseFromDot();
    }

    public static void parseFromDot() throws IOException {
        FileReader fr = new FileReader(DOT_FILE);
        DOTImporter<TaskNode, DefaultWeightedEdge> importer = new DOTImporter<>();
        Graph<TaskNode, DefaultWeightedEdge> g = new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class);

        System.out.println(g);

        importer.setVertexWithAttributesFactory(TaskNode::new);
        importer.importGraph(g, fr);
        System.out.println(g);

        fr.close();
    }
}
