package main

import (
	"fmt"
	"os"
	"strconv"
	"strings"
)

func main() {
	input, _ := os.ReadFile("input.txt")
	instructions := strings.Split(string(input), "\n")
	result1 := timesLeftOnZero(instructions)
	fmt.Println(result1)
	// TODO: Puzzle 2
}

func timesLeftOnZero(instructions []string) (timesLeftOnZero int) {
	position := 50
	for _, instruction := range instructions {
		if len(instruction) < 2 {
			continue
		}

		direction := instruction[0:1]
		amount, _ := strconv.Atoi(instruction[1:])

		switch direction {
		case "R":
			position = (position + amount) % 100
		case "L":
			position = (((position - amount) % 100) + 100) % 100 // Fix negatives number. Especially needed in subtraction
		default:
			panic("unrecognized direction")
		}
		if position == 0 {
			timesLeftOnZero += 1
		}
	}
	return
}
