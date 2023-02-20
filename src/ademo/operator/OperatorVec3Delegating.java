package ademo.operator;

import ademo.arithmetic.DoubleArithmetic;
import ademo.iota.Iota;
import ademo.iota.IotaMultiPredicate;
import ademo.iota.IotaPredicate;
import ademo.iota.Vec3;
import ademo.iota.Symbol;
import ademo.iota.Pair;
import ademo.operator.Operator;

import java.util.Objects;
import java.util.function.BiFunction;

public class OperatorVec3Delegating extends Operator {
	private final BiFunction<Vec3, Vec3, Object> op;
	private final Operator fb;
	public OperatorVec3Delegating(BiFunction<Vec3, Vec3, Object> core, Symbol fallback) {
		super(2, IotaMultiPredicate.any(IotaPredicate.ofClass(Vec3.class), IotaPredicate.ofClass(Double.class)));
		op = core;
		fb = Objects.requireNonNull(DoubleArithmetic.INSTANCE.getOperator(fallback));
	}

	@Override
	public Iota apply(Iterable<Iota> iotas) {
		var it = iotas.iterator();
		var left = it.next();
		var right = it.next();
		if (op != null && left.inner instanceof Vec3 lh && right.inner instanceof Vec3 rh) {
			return new Iota(op.apply(lh, rh));
		}
		var lh = left.inner instanceof Vec3 l ? l : triplicate(left.downcast(Double.class));
		var rh = right.inner instanceof Vec3 r ? r : triplicate(right.downcast(Double.class));
		return new Iota(new Vec3(
			fb.apply(new Pair<>(new Iota(lh.x()), new Iota(rh.x()))).downcast(Double.class),
			fb.apply(new Pair<>(new Iota(lh.y()), new Iota(rh.y()))).downcast(Double.class),
			fb.apply(new Pair<>(new Iota(lh.z()), new Iota(rh.z()))).downcast(Double.class)
		));
	}

	public static Vec3 triplicate(double in) {
		return new Vec3(in, in, in);
	}
}
