package ademo.operator;

import ademo.iota.Iota;
import ademo.iota.IotaMultiPredicate;
import ademo.operator.Operator;

import java.util.function.BinaryOperator;

public class OperatorBinary extends Operator {
	public BinaryOperator<Iota> inner;

	public OperatorBinary(IotaMultiPredicate accepts, BinaryOperator<Iota> inner) {
		super(2, accepts);
		this.inner = inner;
	}

	@Override
	public Iota apply(Iterable<Iota> iotas) {
		var it = iotas.iterator();
		return inner.apply(it.next(), it.next());
	}
}
