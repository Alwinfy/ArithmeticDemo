package ademo.arithmetic;

import ademo.iota.Iota;
import ademo.iota.Symbol;
import ademo.operator.Operator;

public interface Arithmetic {
	public String arithName();

	public abstract Iterable<Symbol> opTypes();

	public abstract Operator getOperator(Symbol name);
}
