package ademo.operator;

import ademo.iota.Iota;
import ademo.iota.IotaMultiPredicate;
import ademo.iota.IotaPredicate;
import ademo.iota.Vec3;
import ademo.operator.Operator;

public class OperatorPack extends Operator {
	private OperatorPack() {
		super(3, IotaMultiPredicate.all(IotaPredicate.ofClass(Double.class)));
	}

	public static OperatorPack INSTANCE = new OperatorPack();

	@Override
	public Iota apply(Iterable<Iota> iotas) {
		var it = iotas.iterator();
		return new Iota(new Vec3(
			it.next().downcast(Double.class),
			it.next().downcast(Double.class),
			it.next().downcast(Double.class)
		));
	}
}
