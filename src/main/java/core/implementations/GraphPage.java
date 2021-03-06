package core.implementations;

import core.exceptions.IllegalComponentException;
import core.interfaces.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.LinkedList;
import java.util.List;

public class GraphPage<Graph extends STBGraph, Component extends STBComponent> implements STBPage<Component> {

    private Graph graph;
    private StringProperty name = new SimpleStringProperty();

    public GraphPage(Graph graph) throws IllegalComponentException {
        if (graph == null) throw new IllegalComponentException("GraphPage's graph can not be null", null);
        this.graph = graph;
        this.name.set(this.graph.getClass().getSimpleName() + this.graph.hashCode());
    }

    @Deprecated
    @Override
    public List<Component> getAllComponents() {
        LinkedList tempList = new LinkedList<Component>();
        tempList.add(this.graph);
        return tempList;
    }

    public Graph getGraph() {
        return this.graph;
    }

    @Override
    public boolean addComponent(Component component) {
        if (component instanceof STBTerminal) return this.graph.addVertex((STBTerminal) component);
        if (component instanceof STBEdge) return this.graph.addEdge((STBEdge) component);
        if (component instanceof STBGraph) {
            return this.graph.addVertexes(((STBGraph) component).getAllVertexes()) |
                this.graph.addEdges(((STBGraph) component).getAllEdges());
        }
        return false;
    }

    @Override
    public boolean removeComponent(Component component) {
        if (component instanceof STBTerminal) return this.graph.removeVertex((STBTerminal) component);
        if (component instanceof STBEdge) return this.graph.removeEdge((STBEdge) component);
        if (component instanceof STBGraph) {
            return this.graph.removeVertexes(((STBGraph) component).getAllVertexes()) |
                    this.graph.removeEdges(((STBGraph) component).getAllEdges());
        }
        return false;
    }

    @Override
    public StringProperty nameProperty() {
        return this.name;
    }
}
