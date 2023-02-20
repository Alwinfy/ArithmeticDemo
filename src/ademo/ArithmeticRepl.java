package ademo;

import ademo.arithmetic.DoubleArithmetic;
import ademo.arithmetic.StringArithmetic;
import ademo.arithmetic.Vec3Arithmetic;
import ademo.engine.ArithmeticEngine;
import ademo.iota.Iota;
import ademo.iota.Symbol;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;

public class ArithmeticRepl {
	public static void main(String[] args) throws IOException {
		var iotas = new Stack<Iota>();
		var br = new BufferedReader(new InputStreamReader(System.in));
		String line;
		var engine = new ArithmeticEngine(DoubleArithmetic.INSTANCE, Vec3Arithmetic.INSTANCE, StringArithmetic.INSTANCE);

		System.out.print("Loaded arithmetic modules:");
		for (var arith : engine.arithmetics) System.out.print(" " + arith.arithName());
		System.out.println();

		System.out.print("Valid operators:");
		for (var sym : engine.operatorSyms()) System.out.print(" " + sym);
		System.out.println();

		System.out.println("Stack state: " + iotas);
		System.out.print("cmd> ");
		while ((line = br.readLine()) != null) {
			System.out.flush();
			try {
				String[] tokens = line.split(" ");
				for (var token : tokens) {
					token = token.strip();
					try {
						var dbl = Double.parseDouble(token);
						iotas.push(new Iota(dbl));
					} catch (NumberFormatException e) {
						if (token.length() == 2 && token.charAt(0) == '\\') {
							iotas.push(new Iota(token.substring(1)));
						} else {
							var result = new Symbol(token);
							iotas.push(engine.run(result, iotas));
						}
					}
				}
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
			System.out.println("Stack state: " + iotas);
			System.out.print("cmd> ");
		}
		System.out.println();
		br.close();
	}
}
			
