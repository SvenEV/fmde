package org.upb.fmde.de.categories.colimits.pushouts;

import org.upb.fmde.de.categories.Category;
import org.upb.fmde.de.categories.colimits.CoLimit;

public interface CategoryWithPushouts<Ob, Arr> extends Category<Ob, Arr> {
	
	default CoLimit<CoSpan<Arr>, Arr> pushout(Span<Arr> span) {
		Ob R = target(span.horiz);
		Ob G = target(span.vert);
		
		CoLimit<CoSpan<Arr>, Arr> G_Plus_R = coproduct(R, G);
		
		CoLimit<Arr, Arr> coequaliser = coequaliser(
				compose(span.horiz, G_Plus_R.obj.vert),
				compose(span.vert, G_Plus_R.obj.horiz));
		
		return new CoLimit<>(
				new CoSpan<Arr>(this,
				compose(G_Plus_R.obj.horiz, coequaliser.obj),
				compose(G_Plus_R.obj.vert, coequaliser.obj)),
				cos -> {
					Arr a = G_Plus_R.up.apply(cos);
					return coequaliser.up.apply(a);
				});
	}
	
	CoLimit<Arr, Arr> coequaliser(Arr f, Arr g);
	
	CoLimit<CoSpan<Arr>, Arr> coproduct(Ob a, Ob b);
}
