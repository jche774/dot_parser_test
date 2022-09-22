import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;
import org.jgrapht.nio.dot.DOTImporter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;

public class DotParser {
    private static final String DOT_FILE = "test.dot";
    private static final String OUTPUT_FILE_NATIVE = "output1.dot";
    private static final String OUTPUT_FILE_HARDCODE = "output2.dot";

    private static String graphId;

    public static void main(String[] args) throws IOException {
        DotParser parser = new DotParser();
        Graph<TaskNode, TaskDependency> g = parser.parseFromDot(DOT_FILE);

        parser.parseToDot(g, parser.getGraphId(), OUTPUT_FILE_NATIVE);
        parser.parseToDot2(g, parser.getGraphId(), OUTPUT_FILE_HARDCODE);
    }

    public Graph<TaskNode, TaskDependency> parseFromDot(String dotFile) throws IOException {
        FileReader fr = new FileReader(dotFile);
        DOTImporter<TaskNode, TaskDependency> importer = new DOTImporter<>();
        Graph<TaskNode, TaskDependency> g = new DefaultDirectedGraph<>(TaskDependency.class);


        importer.addGraphAttributeConsumer((s, attribute) -> {
            if (s.equals("ID")) graphId = attribute.getValue();
        });

        importer.setVertexWithAttributesFactory(TaskNode::new);
        importer.setEdgeWithAttributesFactory(TaskDependency::new);
        importer.importGraph(g, fr);

        /*TODO It's ugly but this is the only way I can come up with to retrieve source, target and the weight of the
            edge at the same time*/
        g.edgeSet().forEach(e -> e.setSource(g.getEdgeSource(e)));
        g.edgeSet().forEach(e -> e.setTarget(g.getEdgeTarget(e)));

        fr.close();
        return g;
    }

    /**
     * <p> A native way to parser back to .dot which could be used for exporting the solution later on after some modification.
     * <p> Contains a few glitches(e.g. a keyword 'strict' is always shown at the beginning of the file), so another approach {@link #parseToDot2 parseToDot2()} could be useful
     * <p> Apparently the edge class of the graph will need to be changed
     */
    public void parseToDot(Graph<TaskNode, TaskDependency> g, String graphId, String outputFile) throws IOException {
        File f = new File(outputFile);
        if (!f.exists()) f.createNewFile();
        FileWriter fw = new FileWriter(f);
        DOTExporter<TaskNode, TaskDependency> exporter = new DOTExporter<>(TaskNode::getName);

        //TODO Minor issue that the weights exported is quoted, which is different from the output shown, but I don't think we will get penalized by that since it's still in dot format
        exporter.setEdgeAttributeProvider(taskDependency ->
                Collections.singletonMap("Weight", DefaultAttribute.createAttribute(taskDependency.getDuration()))
        );
        exporter.setVertexAttributeProvider(taskNode ->
                Collections.singletonMap("Weight", DefaultAttribute.createAttribute(taskNode.getDuration()))
        );
        exporter.setGraphIdProvider(() -> graphId);
        exporter.exportGraph(g, fw);

        fw.close();
    }

    public void parseToDot2(Graph<TaskNode, TaskDependency> g, String graphId, String outputFile) throws IOException {
        File f = new File(outputFile);
        if (!f.exists()) f.createNewFile();
        FileWriter fw = new FileWriter(f);

        fw.append("digraph \"").append(graphId).append("\" {\n");
        g.vertexSet().forEach(v -> {
            try {
                fw.append('\t').append(v.toDot()).append('\n');
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        g.edgeSet().forEach(edge -> {
            try {
                fw.append('\t').append(edge.toDot()).append('\n');
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        fw.append("}");
        fw.close();
    }


    /**
     * A way to get the id of the graph(since we have to reuse it when we export the solution.)
     */
    public String getGraphId() {
        return graphId;
    }
}
