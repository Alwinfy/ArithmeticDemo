package ademo.iota;

@FunctionalInterface
public interface IotaPredicate {
	boolean test(Iota iota);

	static IotaPredicate or(IotaPredicate left, IotaPredicate right) {
		return new Or(left, right);
	}

	static IotaPredicate ofClass(Class<?> clazz) {
		return new OfClass(clazz);
	}

	record Or(IotaPredicate left, IotaPredicate right) implements IotaPredicate {
		@Override
		public boolean test(Iota iota) {
			return left.test(iota) || right.test(iota);
		}
	}

	record OfClass(Class<?> clazz) implements IotaPredicate {
		@Override
		public boolean test(Iota iota) {
			return clazz.isAssignableFrom(iota.getType());
		}
	}
}
