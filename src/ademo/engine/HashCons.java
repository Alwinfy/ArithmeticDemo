package ademo.engine;

import ademo.iota.Symbol;

// Helper type for hashing lists of types.
public sealed interface HashCons {
	public record Sym(Symbol type) implements HashCons {}

	public record Pair(Class<?> head, HashCons tail) implements HashCons {}
}
