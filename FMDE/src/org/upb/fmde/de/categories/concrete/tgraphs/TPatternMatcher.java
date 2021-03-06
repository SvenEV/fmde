package org.upb.fmde.de.categories.concrete.tgraphs;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

import org.upb.fmde.de.categories.PatternMatcher;
import org.upb.fmde.de.categories.concrete.graphs.GraphMorphism;
import org.upb.fmde.de.categories.concrete.graphs.GraphPatternMatcher;

public class TPatternMatcher extends PatternMatcher<TGraph, TGraphMorphism>{
	
	public TPatternMatcher(TGraph pattern, TGraph host) {
		super(pattern, host);
	}

	public List<TGraphMorphism> determineMatches(boolean mono) {
		List<TGraphMorphism> typedMatches = new ArrayList<>();
		
		GraphPatternMatcher pm = new GraphPatternMatcher(pattern.type().src(), host.type().src());
		for (GraphMorphism m : pm.determineMatches(mono, createEdgeTypeFilter(), createNodeTypeFilter())) {
			try {
				TGraphMorphism typedMatch = new TGraphMorphism("m", m, pattern, host);
				typedMatches.add(typedMatch);
			} catch (Exception e) {
				
			}
		}
		
		return typedMatches;
	}

	private BiPredicate<Object, Object> createNodeTypeFilter() {
		return (from, to) -> {
			Object t_from = pattern.type()._V().map(from);
			Object t_to = host.type()._V().map(to);
			return t_from.equals(t_to);
		};
	}

	private BiPredicate<Object, Object> createEdgeTypeFilter() {
		return (from, to) -> {
			Object t_from = pattern.type()._E().map(from);
			Object t_to = host.type()._E().map(to);
			return t_from.equals(t_to);
		};
	}
}
