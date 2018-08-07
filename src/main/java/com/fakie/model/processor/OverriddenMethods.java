package com.fakie.model.processor;

import com.fakie.model.graph.Edge;
import com.fakie.model.graph.Graph;
import com.fakie.model.graph.Vertex;
import com.fakie.utils.FakieUtils;
import com.fakie.utils.Keyword;
import com.fakie.utils.exceptions.FakieException;
import com.fakie.utils.paprika.Label;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OverriddenMethods implements Processor {
    private static final Logger logger = LogManager.getFormatterLogger();

    @Override
    public Graph process(Graph graph) throws FakieException {
        logger.info("Compute overridden methods in %s", graph);
        Set<Vertex> vertices = graph.findVerticesByLabel(Label.CLASS.toString());
        Set<Vertex> overridden = graph.findVerticesByLabel(Label.METHOD.toString());
        for (Vertex vertex : vertices) {
            if (FakieUtils.containsACodeSmell(vertex)) {
                Set<Vertex> methods = availableMethods(vertex);
                overridden.retainAll(methods);
            }
        }
        for (Vertex vertex : vertices) {
            if (FakieUtils.containsACodeSmell(vertex)) {
                List<Vertex> methods = methods(vertex);
                for (Vertex method : overridden) {
                    String key = Keyword.OUTPUT_EDGE.format(
                            Label.CLASS_OWNS_METHOD,
                            method.getProperty(Label.FULL_NAME.toString()));
                    vertex.setProperty(key, methods.contains(method));
                }
            }
        }
        return graph;
    }

    private Set<Vertex> availableMethods(Vertex vertex) {
        Set<Vertex> result = new HashSet<>(methods(vertex));
        for (Edge edge : vertex.outputEdges()) {
            if (edge.getType().equals(Label.EXTENDS.toString()) || edge.getType().equals(Label.IMPLEMENTS.toString())) {
                result.addAll(availableMethods(edge.getDestination()));
            }
        }
        return result;
    }

    private List<Vertex> methods(Vertex vertex) {
        List<Vertex> result = new ArrayList<>();
        for (Edge edge : vertex.outputEdges()) {
            if (edge.getType().equals(Label.CLASS_OWNS_METHOD.toString())) {
                result.add(edge.getDestination());
            }
        }
        return result;
    }
}
