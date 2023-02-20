package ademo.arithmetic;

import ademo.iota.Iota;
import ademo.iota.IotaPredicate;
import ademo.iota.IotaMultiPredicate;
import ademo.iota.Symbol;
import ademo.operator.Operator;
import ademo.operator.OperatorUnary;
import ademo.operator.OperatorBinary;

import java.util.List;
import java.util.function.DoubleUnaryOperator;
import java.util.function.DoubleBinaryOperator;

public enum DoubleArithmetic implements Arithmetic {
	INSTANCE;

	public static final List<Symbol> OPS = List.of(
		new Symbol("add"),
		new Symbol("sub"),
		new Symbol("mul"),
		new Symbol("div"),
		new Symbol("abs"),
		new Symbol("pow"),
		new Symbol("floor"),
		new Symbol("ceil"),
		new Symbol("mod")
	);

	public static final IotaMultiPredicate ACCEPTS = IotaMultiPredicate.all(IotaPredicate.ofClass(Double.class));

	@Override
	public String arithName() {
		return "double_math";
	}

	@Override
	public Iterable<Symbol> opTypes() {
		return OPS;
	}

	@Override
	public Operator getOperator(Symbol name) {
		switch (name.inner()) {
		case "add": return make2(Double::sum);
		case "sub": return make2((p, q) -> p - q);
		case "mul": return make2((p, q) -> p * q);
		case "div": return make2((p, q) -> p / q);
		case "abs": return make1(Math::abs);
		case "pow": return make2(Math::pow);
		case "floor": return make1(Math::floor);
		case "ceil": return make1(Math::ceil);
		case "mod": return make2((p, q) -> p % q);
		}
		return null;
	}
	public static OperatorUnary make1(DoubleUnaryOperator op) {
		return new OperatorUnary(ACCEPTS, i -> new Iota(op.applyAsDouble(i.downcast(Double.class))));
	}
	public static OperatorBinary make2(DoubleBinaryOperator op) {
		return new OperatorBinary(ACCEPTS, (i, j) -> new Iota(op.applyAsDouble(i.downcast(Double.class), j.downcast(Double.class))));
	}
}
