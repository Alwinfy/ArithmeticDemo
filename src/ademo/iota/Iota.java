package ademo.iota;

public class Iota {
	public final Object inner;

	public Iota(Object inner) { this.inner = inner; }

	public Class<?> getType() { return inner.getClass(); }

	@Override
	public String toString() {
		return inner.toString();
	}

	public <T> T downcast(Class<T> clazz) {
		if (clazz.isInstance(inner)) {
			@SuppressWarnings("unchecked")
			T val = (T) inner;
			return val;
		}
		throw new IllegalArgumentException("Iota " + inner + " is not of type " + clazz);
	}
}
