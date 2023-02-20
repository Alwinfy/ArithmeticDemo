package ademo.iota;

public record Vec3(double x, double y, double z) {
	public double dot(Vec3 other) {
		return x * other.x + y * other.y + z * other.z;
	}
	public Vec3 cross(Vec3 other) {
		return new Vec3(
			y * other.z - z * other.y,
			z * other.x - x * other.z,
			x * other.y - y * other.x
		);
	}
	public double len() {
		return Math.sqrt(x * x + y * y + z * z);
	}
	public Vec3 scale(double n) {
		return new Vec3(n * x, n * y, n * z);
	}
	public Vec3 proj(Vec3 target) {
		var normTarget = target.scale(1 / target.len());
		return normTarget.scale(dot(normTarget));
	}

	@Override
	public String toString() {
		return "[" + x + ", " + y + ", " + z + "]";
	}
}
