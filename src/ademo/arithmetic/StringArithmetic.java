package ademo.arithmetic;

import ademo.iota.Iota;
import ademo.iota.IotaPredicate;
import ademo.iota.IotaMultiPredicate;
import ademo.iota.Symbol;
import ademo.operator.Operator;
import ademo.operator.OperatorUnary;
import ademo.operator.OperatorBinary;

import java.util.List;

public enum StringArithmetic implements Arithmetic {
	INSTANCE;

	public static final List<Symbol> OPS = List.of(
		new Symbol("add"),
		new Symbol("mul"),
		new Symbol("abs")
	);

	@Override
	public String arithName() {
		return "string_math";
	}

	@Override
	public Iterable<Symbol> opTypes() {
		return OPS;
	}

	@Override
	public Operator getOperator(Symbol name) {
		switch (name.inner()) {
		case "add": return new OperatorBinary(
				IotaMultiPredicate.all(IotaPredicate.ofClass(String.class)),
				(p, q) -> new Iota(p.downcast(String.class) + q.downcast(String.class)));
		case "mul": return new OperatorBinary(
				IotaMultiPredicate.bofa(IotaPredicate.ofClass(String.class), IotaPredicate.ofClass(Double.class)),
				(p, q) -> new Iota(new String(new char[q.downcast(Double.class).intValue()]).replace("\0", p.downcast(String.class))));
		case "abs": return new OperatorUnary(
				IotaMultiPredicate.all(IotaPredicate.ofClass(String.class)),
				s -> new Iota((double) s.downcast(String.class).length()));
		}
		return null;
	}
}
