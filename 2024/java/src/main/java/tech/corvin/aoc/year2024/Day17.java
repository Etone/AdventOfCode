package tech.corvin.aoc.year2024;

import tech.corvin.aoc.general.Day;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day17 extends Day2024<String, Integer> {

    private Computer computer;

    public static void main(String[] args) throws IOException {
        new Day17().initialize().print();
    }

    public Day17() throws IOException {
        super(17);
    }


    @Override
    public String part1() {
        return computer.execute().stream().map(String::valueOf).collect(Collectors.joining(","));
    }

    @Override
    public Integer part2() {
       return 0;
    }

    @Override
    public Day<?, ?> initialize() throws IOException {
        var input = input().split(System.lineSeparator());
        var initialA = Integer.parseInt(input[0].replace("Register A: ", ""));
        var initialB = Integer.parseInt(input[1].replace("Register B: ", ""));
        var initialC = Integer.parseInt(input[2].replace("Register C: ", ""));

        var program = Arrays.stream(input[4].replace("Program: ", "").split(",")).map(Byte::parseByte).toList();

        computer = new Computer(program, initialA, initialB, initialC);
        return this;
    }

    private static final class Memory {
        private long a;
        private long b;
        private long c;
        private int instructionPointer;

        private final List<Long> output;

        private Memory(
                long a,
                long b,
                long c,
                int instructionPointer
        ) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.instructionPointer = instructionPointer;
            this.output = new ArrayList<>();
        }
    }

    private static class Computer {
        private final Memory mem;

        private final List<Byte> instructionRegister;

        private Computer(List<Byte> instructionRegister, long a, long b, long c) {
            this.instructionRegister = instructionRegister;
            mem = new Memory(a,b,c, 0);
        }


        public List<Long> execute() {
            while (mem.instructionPointer < instructionRegister.size()) {
                var opcode = instructionRegister.get(mem.instructionPointer);
                var operand = instructionRegister.get(mem.instructionPointer + 1);

                switch (opcode) {
                    case 0: adv(operand); break;
                    case 1: bxl(operand); break;
                    case 2: bst(operand); break;
                    case 3: jnz(operand); break;
                    case 4: bxc(operand); break;
                    case 5: out(operand); break;
                    case 6: bdv(operand); break;
                    case 7: cdv(operand); break;
                    default: throw new IllegalStateException("this is a three bit computer and can only handle 8 instructions");
                }
            }
            return mem.output;
        }

        private void adv(byte operand) {
            mem.a = (long) (mem.a / Math.pow(2, combo(operand)));
            mem.instructionPointer += 2;
        }

        private void bxl(byte operand) {
            mem.b = mem.b ^ operand;
            mem.instructionPointer += 2;
        }
        private void bst(byte operand) {
            mem.b = operand % 8;
            mem.instructionPointer += 2;
        }
        private void jnz(byte operand) {
            if (mem.a == 0) {
                mem.instructionPointer += 2;
                return;
            }
            mem.instructionPointer = operand;
        }

        private void bxc(byte operand) {
            mem.b = mem.b ^ mem.c;
            mem.instructionPointer += 2;
        }

        private void out(byte operand) {
            mem.output.add(combo(operand) % 8);
            mem.instructionPointer += 2;
        }

        private void bdv(byte operand) {
            mem.b = (long) (mem.a / Math.pow(2, combo(operand)));
            mem.instructionPointer += 2;
        }

        private void cdv(byte operand) {
            mem.c = (long) (mem.a / Math.pow(2, combo(operand)));
            mem.instructionPointer += 2;
        }

        private long combo(Byte operand) {
            return switch (operand) {
                case 0, 1, 2, 3 -> operand;
                case 4 -> mem.a;
                case 5 -> mem.b;
                case 6 -> mem.c;
                case 7 -> throw new IllegalArgumentException("7 is not a valid combo operand");
                default ->
                        throw new IllegalArgumentException("Operands higher as seven are not allowed, this is a three bit computer");
            };
        }
    }
}
