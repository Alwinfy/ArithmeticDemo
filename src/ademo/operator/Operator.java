package ademo.operator;

import ademo.iota.Iota;
import ademo.iota.IotaMultiPredicate;
import ademo.iota.Symbol;
import ademo.operator.Operator;

public abstract class Operator {
	public final int arity;

	public final IotaMultiPredicate accepts;

	public Operator(int arity, IotaMultiPredicate accepts) {
		this.arity = arity;
		this.accepts = accepts;
	}

	public abstract Iota apply(Iterable<Iota> iotas);
}
