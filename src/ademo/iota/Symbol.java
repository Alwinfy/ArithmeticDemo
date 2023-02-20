package ademo.iota;

public record Symbol(String inner) {
	@Override
	public String toString() {
		return inner;
	}
}
