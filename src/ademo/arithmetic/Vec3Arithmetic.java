package ademo.arithmetic;

import ademo.iota.Iota;
import ademo.iota.IotaPredicate;
import ademo.iota.IotaMultiPredicate;
import ademo.iota.Symbol;
import ademo.iota.Vec3;
import ademo.operator.Operator;
import ademo.operator.OperatorUnary;
import ademo.operator.OperatorPack;
import ademo.operator.OperatorVec3Delegating;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.BiFunction;

public enum Vec3Arithmetic implements Arithmetic {
	INSTANCE;

	public static final List<Symbol> OPS;
	static {
		var ops = new ArrayList<>(DoubleArithmetic.OPS);
		ops.add(new Symbol("pack"));
		ops.remove(new Symbol("floor"));
		ops.remove(new Symbol("ceil"));
		OPS = ops;
	}

	public static final IotaMultiPredicate ACCEPTS = IotaMultiPredicate.any(IotaPredicate.ofClass(Vec3.class), IotaPredicate.ofClass(Double.class));

	@Override
	public String arithName() {
		return "vec3_math";
	}

	@Override
	public Iterable<Symbol> opTypes() {
		return OPS;
	}

	@Override
	public Operator getOperator(Symbol name) {
		switch (name.inner()) {
		case "pack":  return OperatorPack.INSTANCE;
		case "add":   return make2(name, null);
		case "sub":   return make2(name, null);
		case "mul":   return make2(name, Vec3::dot);
		case "div":   return make2(name, Vec3::cross);
		case "abs":   return make1(Vec3::len);
		case "pow":   return make2(name, Vec3::proj);
		case "mod":   return make2(name, null);
		}
		return null;
	}
	public static OperatorUnary make1(Function<Vec3, Object> op) {
		return new OperatorUnary(ACCEPTS, i -> new Iota(op.apply(i.downcast(Vec3.class))));
	}
	public static OperatorVec3Delegating make2(Symbol name, BiFunction<Vec3, Vec3, Object> op) {
		return new OperatorVec3Delegating(op, name);
	}
}
