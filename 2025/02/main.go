package main

import (
	"fmt"
	"os"
	"strconv"
	"strings"
)

func main() {
	// input, _ := os.ReadFile("test.txt")
	input, _ := os.ReadFile("input.txt")

	ranges := strings.SplitSeq(string(input), ",")

	result := 0
	for r_str := range ranges {
		r := parseToRange(r_str)
		invalid := findInvalidIds(r)

		for _, id := range invalid {
			result += id
		}
	}

	//TODO: Part 2
	fmt.Println(result)
}

type Range struct {
	start int
	end   int
}

/*
 * String format must be <start inclusive>-<end inclusive>
 * e.g. 11-22
 */
func parseToRange(i string) (r Range) {
	borders := strings.Split(i, "-")
	start, _ := strconv.Atoi(borders[0])
	end, _ := strconv.Atoi(borders[1])

	r = Range{start, end}
	return
}

func findInvalidIds(r Range) (invalidIDs []int) {
	for i := r.start; i <= r.end; i++ {
		if isInvalidWithTwoRepetitions(i) {
			invalidIDs = append(invalidIDs, i)
		}
	}
	return
}

func isInvalidWithTwoRepetitions(i int) bool {
	i_str := strconv.Itoa(i)
	if len(i_str)%2 == 0 {
		firstHalf := i_str[0 : len(i_str)/2]
		secondHalf := i_str[len(i_str)/2:]
		return firstHalf == secondHalf
	}
	return false
}
