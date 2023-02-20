package ademo.operator;

import ademo.iota.Iota;
import ademo.iota.IotaMultiPredicate;
import ademo.operator.Operator;

import java.util.function.UnaryOperator;

public class OperatorUnary extends Operator {
	public UnaryOperator<Iota> inner;

	public OperatorUnary(IotaMultiPredicate accepts, UnaryOperator<Iota> inner) {
		super(1, accepts);
		this.inner = inner;
	}

	@Override
	public Iota apply(Iterable<Iota> iotas) {
		return inner.apply(iotas.iterator().next());
	}
}
