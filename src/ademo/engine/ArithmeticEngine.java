package ademo.engine;

import ademo.iota.Iota;
import ademo.iota.IotaMultiPredicate;
import ademo.iota.Symbol;
import ademo.arithmetic.Arithmetic;
import ademo.operator.Operator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class ArithmeticEngine {
	private static record OpCandidates(Symbol name, int arity, List<Operator> operators) {
		public void addOp(Operator next) {
			if (next.arity != arity) {
				throw new IllegalArgumentException("Operators exist of differing arity!");
			}
			operators.add(next);
		}
	}

	public final Arithmetic[] arithmetics;
	private final Map<Symbol, OpCandidates> operators = new HashMap<>();
	private final Map<HashCons, Operator> cache = new HashMap<>();

	public ArithmeticEngine(Arithmetic... arithmetics) {
		this.arithmetics = arithmetics;
		for (var arith : arithmetics) {
			for (var op : arith.opTypes()) {
				operators.compute(op, ($, info) -> {
					var operator = arith.getOperator(op);
					if (info == null) {
						info = new OpCandidates(op, operator.arity, new ArrayList<>());
					}
					info.addOp(operator);
					return info;
				});
			}
		}
	}

	public Iterable<Symbol> operatorSyms() {
		return operators.keySet(); 
	}

	public Iota run(Symbol operator, Stack<Iota> iotas) {
		var candidates = operators.get(operator);
		if (candidates == null) {
			throw new IllegalArgumentException("Unknown symbol: " + operator);
		}
		HashCons hash = new HashCons.Sym(operator);
		var args = new ArrayList<Iota>(candidates.arity());
		for (var i = 0; i < candidates.arity(); i++) {
			if (iotas.isEmpty()) {
				throw new IllegalStateException("Not enough args on stack for operator: " + operator);
			}
			var iota = iotas.pop();
			hash = new HashCons.Pair(iota.getClass(), hash);
			args.add(iota);
		}
		Collections.reverse(args);
		var op = resolveCandidates(args, hash, candidates);
		return op.apply(args);
	}

	public Operator resolveCandidates(List<Iota> args, HashCons hash, OpCandidates candidates) {
		return cache.computeIfAbsent(hash, $ -> {
			for (var op : candidates.operators()) {
				if (op.accepts.test(args)) {
					return op;
				}
			}
			throw new IllegalArgumentException("No implementation candidates for op " + candidates.name() + " on args: " + args);
		});
	}
}
